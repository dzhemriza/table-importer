/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: DBDestTableUpdateInsert.java
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

package tableimporter.dest_table;

import java.util.Vector;
import java.sql.Connection;
import java.sql.SQLException;
import tableimporter.fields.IField;
import tableimporter.collections.RowData;
import tableimporter.utils.db.ISQLStatementBuilder;
import tableimporter.dest_table.db_operations.DBTableUpdater;
import tableimporter.dest_table.db_operations.DBTableInserter;
import org.apache.log4j.Logger;

/**
 *
 * @author djemriza
 */
public class DBDestTableUpdateInsert extends DBDestTableBase {
    protected DBTableUpdater rowUpdater = null;
    protected DBTableInserter rowInserter = null;

    public DBDestTableUpdateInsert(Connection dbConn, Vector<IField> fields, String tableName, ISQLStatementBuilder sqlStatementBuilder) {
        super(dbConn, fields, tableName, sqlStatementBuilder);

        rowUpdater = new DBTableUpdater(dbConn, sqlStatementBuilder, tableName);
        rowInserter = new DBTableInserter(dbConn, sqlStatementBuilder, tableName);
    }

    public void postRecord(RowData rowData) {
        Logger logger = Logger.getLogger("tableimporter.dest_table");
        
        try {
            int updateCount = rowUpdater.updateRow(fields, true, "", rowData);

            if (0 == updateCount) {
                int insertCount = rowInserter.insertRow(fields, rowData);

                logger.info("DBDestTableUpdateInsert.postRecord: Insert Count = " + ((Integer) insertCount).toString());
            }
        }
        catch (SQLException ex) {
            logger.error(ex.getMessage());
            logger.debug("Full stack trace of exception:", ex);

            ex.printStackTrace();
        }
    }

}
