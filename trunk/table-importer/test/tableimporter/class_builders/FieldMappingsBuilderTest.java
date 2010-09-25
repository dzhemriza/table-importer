/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: FieldMappingsBuilderTest.java
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

package tableimporter.class_builders;

import java.util.ArrayList;
import java.util.Iterator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tableimporter.config.ConfigXml;
import tableimporter.dest_table.IDestTable;
import tableimporter.source_table.ISourceTable;
import tableimporter.fields.*;
import tableimporter.collections.RowData;

class TestSourceTable implements ISourceTable {
    private ArrayList<IField> fields = new ArrayList<IField>();

    public TestSourceTable() {
        fields.add(new Field("persons_id", FieldType.Integer, true));
        fields.add(new Field("persons_name", FieldType.String, false));
        fields.add(new Field("persons_double", FieldType.Double, false));
        fields.add(new Field("persons_float", FieldType.Float, false));
        fields.add(new Field("persons_date", FieldType.Date, false));
        fields.add(new Field("dst_numeric_field", FieldType.Integer, false));
        fields.add(new Field("src_numeric_field", FieldType.Integer, false));
        fields.add(new Field("dest_field", FieldType.Integer, false));
    }
    
    public Iterator<RowData> getRecords() {
        // do nothing
        return null;
    }

    public ArrayList<IField> getFields() {
        return fields;
    }
}

class TestDestTable implements IDestTable {
    private ArrayList<IField> fields = new ArrayList<IField>();

    public TestDestTable() {
        fields.add(new Field("persons_id", FieldType.Integer, true));
        fields.add(new Field("persons_name", FieldType.String, false));
        fields.add(new Field("persons_double", FieldType.Double, false));
        fields.add(new Field("persons_float", FieldType.Float, false));
        fields.add(new Field("persons_date", FieldType.Date, false));
        fields.add(new Field("dst_numeric_field", FieldType.Integer, false));
        fields.add(new Field("src_numeric_field", FieldType.Integer, false));
        fields.add(new Field("dest_field", FieldType.Integer, false));
    }
    
    public void postRecord(RowData rowData) {
        // do nothing
    }

    public ArrayList<IField> getFields() {
        return fields;
    }
}

/**
 *
 * @author djemriza
 */
public class FieldMappingsBuilderTest {

    public FieldMappingsBuilderTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGeneralCase() throws Exception {
        System.out.println("WARNING: This test file contains hard coded paths to files!");
        String fileName = "C:/temp/unit_tests/import-config-test-10.xml";
        ConfigXml confXml = new ConfigXml();
        confXml.loadXml(fileName);
        TestSourceTable srcTable = new TestSourceTable();
        TestDestTable dstTable = new TestDestTable();
        FieldMappingsBuilder builder = new FieldMappingsBuilder();
        builder.setConfigXml(confXml);
        builder.setSourceTable(srcTable);
        builder.setDestTable(dstTable);
        builder.buildFieldMappings();
    }

}