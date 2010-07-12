/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: ConstDataMapTest.java
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

package tableimporter.fieldmapping;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tableimporter.collections.RowData;
import tableimporter.fields.FieldType;
import tableimporter.fields.Field;
import org.apache.log4j.BasicConfigurator;

/**
 *
 * @author djemriza
 */
public class ConstDataMapTest {

    public ConstDataMapTest() {
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

    @Test
    public void testWriteGeneralCaseString() throws Exception {
        Field fld = new Field("A1", FieldType.String, false);
        ConstDataMap map = new ConstDataMap(fld, "ALO");
        RowData rd = new RowData();
        map.write(null, rd);
        assertEquals("ALO", rd.getFieldValue(fld.getFieldName()));
    }

    @Test
    public void testWriteGeneralCaseInt() throws Exception {
        Field fld = new Field("A1", FieldType.Integer, false);
        ConstDataMap map = new ConstDataMap(fld, "12");
        RowData rd = new RowData();
        map.write(null, rd);
        assertEquals(12, rd.getFieldValue(fld.getFieldName()));
    }
    
    @Test
    public void testWriteExceptionCaseInt() throws Exception {
        Field fld = new Field("A1", FieldType.Integer, false);
        ConstDataMap map = new ConstDataMap(fld, "34d32");
        RowData rd = new RowData();
        try {
            map.write(null, rd);
            fail("You should never get here");
        }
        catch (UnableToWriteFieldMapData ex) {
            System.out.println("Expected Exception: " + ex.getMessage());
            assertTrue(true);
        }
    }

    @Test
    public void testWriteGeneralCaseFloat() throws Exception {
        Field fld = new Field("A1", FieldType.Float, false);
        ConstDataMap map = new ConstDataMap(fld, "12.3");
        RowData rd = new RowData();
        map.write(null, rd);
        assertEquals(12.3f, rd.getFieldValue(fld.getFieldName()));
    }

    @Test
    public void testWriteExceptionCaseFloat() throws Exception {
        Field fld = new Field("A1", FieldType.Float, false);
        ConstDataMap map = new ConstDataMap(fld, "34d32");
        RowData rd = new RowData();
        try {
            map.write(null, rd);
            fail("You should never get here");
        }
        catch (UnableToWriteFieldMapData ex) {
            System.out.println("Expected Exception: " + ex.getMessage());
            assertTrue(true);
        }
    }

    @Test
    public void testWriteGeneralCaseDouble() throws Exception {
        Field fld = new Field("A1", FieldType.Double, false);
        ConstDataMap map = new ConstDataMap(fld, "12.3");
        RowData rd = new RowData();
        map.write(null, rd);
        assertEquals(12.3, rd.getFieldValue(fld.getFieldName()));
    }

    @Test
    public void testWriteExceptionCaseDouble() throws Exception {
        Field fld = new Field("A1", FieldType.Double, false);
        ConstDataMap map = new ConstDataMap(fld, "34d32");
        RowData rd = new RowData();
        try {
            map.write(null, rd);
            fail("You should never get here");
        }
        catch (UnableToWriteFieldMapData ex) {
            System.out.println("Expected Exception: " + ex.getMessage());
            assertTrue(true);
        }
    }

    @Test
    public void testUnsuportedType() throws Exception {
        Field fld = new Field("A1", FieldType.Date, false);
        ConstDataMap map = new ConstDataMap(fld, "34d32");
        RowData rd = new RowData();
        try {
            map.write(null, rd);
            fail("You should never get here");
        }
        catch (UnableToWriteFieldMapData ex) {
            System.out.println("Expected Exception: " + ex.getMessage());
            assertTrue(true);
        }
    }

}