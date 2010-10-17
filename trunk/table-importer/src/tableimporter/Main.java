/*
 * table-importer
 * Imports tabled data from any source to any destination
 * 
 * File Name: Main.java
 *
 * Copyright (C) 2010 Dzhem Riza
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package tableimporter;

import java.io.File;
import java.util.ArrayList;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import tableimporter.class_builders.DestTableBuilder;
import tableimporter.class_builders.SourceTableBuilder;
import tableimporter.class_builders.FieldMappingsBuilder;
import tableimporter.config.ConfigXml;
import tableimporter.dest_table.IDestTable;
import tableimporter.fieldmapping.IFieldMap;
import tableimporter.importer.IRowImporter;
import tableimporter.importer.RowImporter;
import tableimporter.source_table.ISourceTable;
import tableimporter.utils.db.ConnectionPool;

/**
 *
 * @author djemriza
 */
public class Main {

    private static void tableImporterInfo() {
        System.out.println("Table Importer 0.1.0");
    }

    private static void usage() {
        System.out.println("usage: table-importer <imp_def_1.xml> <imp_def_2.xml> .. <imp_def_N.xml>");
    }

    private static void initLog4j(String log4jProps) {
        boolean useBasicConf = log4jProps.equals("");

        if (!useBasicConf) {
            File file = new File(log4jProps);
            useBasicConf = !file.exists();
        }

        if (useBasicConf) {
            BasicConfigurator.configure();
        }
        else {
            PropertyConfigurator.configure(log4jProps);
        }
    }

    private static void startImporting(ConfigXml conf) throws Exception {
        // First step in importing is to create a connection pool
        ConnectionPool connPool = new ConnectionPool();

        try {
            connPool.loadFromConfig(conf);

            // We need to create a source table
            SourceTableBuilder sourceTableBuilder = new SourceTableBuilder();
            sourceTableBuilder.setConfigXml(conf);
            sourceTableBuilder.setConnectionPool(connPool);
            sourceTableBuilder.buildSourceTable();
            ISourceTable srcTable = sourceTableBuilder.getSourceTable();

            // We need to create a destination table
            DestTableBuilder destTableBuilder = new DestTableBuilder();
            destTableBuilder.setConfigXml(conf);
            destTableBuilder.setConnectionPool(connPool);
            destTableBuilder.buildDestinationTable();
            IDestTable destTable = destTableBuilder.getDestinationTable();

            // Creation of field mapping array
            FieldMappingsBuilder fieldMappingsBuilder = new FieldMappingsBuilder();
            fieldMappingsBuilder.setConfigXml(conf);
            fieldMappingsBuilder.setSourceTable(srcTable);
            fieldMappingsBuilder.setDestTable(destTable);
            fieldMappingsBuilder.buildFieldMappings();
            ArrayList<IFieldMap> fieldMappings = fieldMappingsBuilder.getFieldMappings();

            // Creation of row importer
            IRowImporter rowImporter = new RowImporter();
            rowImporter.processImport(srcTable, destTable, fieldMappings);
        }
        finally {
            // At the end of the operation we might need to be shure that the
            // connection pool is empty
            connPool.clear();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        tableImporterInfo();

        if (0 == args.length) {
            usage();
            return;
        }

        for (int i = 0; i < args.length; ++i) {
            System.out.println("Start processing '" + args[i] + "' configuration...");

            boolean success = true;
            ConfigXml conf = null;

            try {
                conf = new ConfigXml();
                conf.loadXml(args[i]);
            }
            catch (Exception e) {
                System.out.println("Exception while loading configarion: " + e.getMessage());
                success = false;
            }

            if (success) {
            	try {
			        initLog4j(conf.getLog4jConfigFile());
			        startImporting(conf);
            	}
            	catch (Exception e) {
                    System.out.println("Exception while importing: " + e.getMessage());
            	}
            }
        }
    }

}
