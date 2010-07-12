/*
 * table-importer
 * Imports tabled data from any source to any destination
 * 
 * File Name: RowImporterTest.java
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

import java.util.Vector;
import java.util.Iterator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tableimporter.dest_table.IDestTable;
import tableimporter.importer.RowImporter;
import tableimporter.source_table.ISourceTable;
import tableimporter.fields.*;
import tableimporter.collections.*;
import tableimporter.fieldmapping.*;
import org.apache.log4j.BasicConfigurator;

class TestDestTable implements IDestTable {
    private Vector<IField> vFields = new Vector<IField>();
    private Vector<RowData> vPostedRecords = new Vector<RowData>();

    public TestDestTable() {
        vFields.add(new Field("A", FieldType.Integer, false));
        vFields.add(new Field("B", FieldType.String, false));
    }

    public void postRecord(RowData rowData) {
        vPostedRecords.add(rowData);

        Iterator<String> fields = rowData.getFields();
        while (fields.hasNext()) {
            String field = fields.next();
            System.out.print("Field: " + field);
            System.out.print(" Value: ");
            Object val = rowData.getFieldValue(field);
            System.out.print(val);
            System.out.println();
        }
    }

    public Vector<IField> getFields() {
        return vFields;
    }

    public Iterator<RowData> getPostedRecords() {
        return vPostedRecords.iterator();
    }
}

class TestSourceTable implements ISourceTable {
    private Vector<IField> vFields = new Vector<IField>();
    private Vector<RowData> vRecords = new Vector<RowData>();

    public TestSourceTable() {
        vFields.add(new Field("A", FieldType.Integer, false));
        vFields.add(new Field("B", FieldType.String, false));

        RowData rowData = new RowData();
        rowData.setFieldValue("A", 12);
        rowData.setFieldValue("B", "Hello");
        vRecords.add(rowData);

        rowData = new RowData();
        rowData.setFieldValue("A", 14);
        rowData.setFieldValue("B", "World");
        vRecords.add(rowData);

        rowData = new RowData();
        rowData.setFieldValue("A", 16);
        rowData.setFieldValue("B", "Internet");
        vRecords.add(rowData);
    }

    public Iterator<RowData> getRecords() {
        return vRecords.iterator();
    }

    public Vector<IField> getFields() {
        return vFields;
    }
}

/**
 *
 * @author djemriza
 */
public class RowImporterTest {

    public RowImporterTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        BasicConfigurator.configure();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of processImport method, of class RowImporter.
     */
    @Test
    public void testProcessImport() {
        System.out.println("Begin test: testProcessImport");

        TestSourceTable srcTable = new TestSourceTable();
        TestDestTable dstTable = new TestDestTable();

        IField srcTblA = srcTable.getFields().get(0);
        IField srcTblB = srcTable.getFields().get(1);

        IField dstTblA = dstTable.getFields().get(0);
        IField dstTblB = dstTable.getFields().get(1);

        Vector<IFieldMap> fieldMapping = new Vector<IFieldMap>();
        fieldMapping.add(new FieldToFieldMap(srcTblA, dstTblA));
        fieldMapping.add(new FieldToFieldMap(srcTblB, dstTblB));

        RowImporter instance = new RowImporter();

        instance.processImport(srcTable, dstTable, fieldMapping);

        Iterator<RowData> srcTableRows = srcTable.getRecords();
        Iterator<RowData> dstTableImportedRows = dstTable.getPostedRecords();

        assertTrue(srcTableRows.hasNext() && dstTableImportedRows.hasNext());

        while (srcTableRows.hasNext() && dstTableImportedRows.hasNext()) {
            RowData srcRowData = srcTableRows.next();
            RowData dstRowData = dstTableImportedRows.next();

            Iterator<String> iterSrcFields = srcRowData.getFields();
            Iterator<String> iterDstFields = dstRowData.getFields();

            assertTrue(iterSrcFields.hasNext() && iterDstFields.hasNext());

            while (iterSrcFields.hasNext() && iterDstFields.hasNext()) {
                String srcFieldName = iterSrcFields.next();
                String dstFieldName = iterDstFields.next();
                
                assertEquals(srcFieldName, dstFieldName);

                Object srcFldData = srcRowData.getFieldValue(srcFieldName);
                Object dstFldData = dstRowData.getFieldValue(dstFieldName);
                assertEquals(srcFldData, dstFldData);
            }

            assertTrue((!iterSrcFields.hasNext()) && (!iterDstFields.hasNext()));
        }

        assertTrue((!srcTableRows.hasNext()) && (!dstTableImportedRows.hasNext()));
    }

}