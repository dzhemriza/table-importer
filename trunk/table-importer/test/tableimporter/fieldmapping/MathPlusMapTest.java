/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: MathPlusMapTest.java
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

/**
 *
 * @author djemriza
 */
public class MathPlusMapTest {

    public MathPlusMapTest() {
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

    /**
     * Test of operateInt method, of class MathPlusMap.
     */
    @Test
    public void testWriteInt() throws Exception {
        Field srcFld = new Field("A1", FieldType.Integer, false);
        Field dstFld = new Field("B1", FieldType.Integer, false);
        RowData rd = new RowData();
        rd.setFieldValue(srcFld.getFieldName(), 10);
        rd.setFieldValue(dstFld.getFieldName(), 10);
        MathPlusMap map = new MathPlusMap(srcFld, dstFld);
        map.write(rd, rd);
        assertEquals(rd.getFieldValue(dstFld.getFieldName()), 20);
    }

    /**
     * Test of operateFloat method, of class MathPlusMap.
     */
    @Test
    public void testWriteFloat() throws Exception {
        Field srcFld = new Field("A1", FieldType.Float, false);
        Field dstFld = new Field("B1", FieldType.Float, false);
        RowData rd = new RowData();
        rd.setFieldValue(srcFld.getFieldName(), 10.12f);
        rd.setFieldValue(dstFld.getFieldName(), 10.12f);
        MathPlusMap map = new MathPlusMap(srcFld, dstFld);
        map.write(rd, rd);
        assertEquals(rd.getFieldValue(dstFld.getFieldName()), 20.24f);
    }

    /**
     * Test of operateDouble method, of class MathPlusMap.
     */
    @Test
    public void testWriteDouble() throws Exception {
        Field srcFld = new Field("A1", FieldType.Double, false);
        Field dstFld = new Field("B1", FieldType.Double, false);
        RowData rd = new RowData();
        rd.setFieldValue(srcFld.getFieldName(), 10.12);
        rd.setFieldValue(dstFld.getFieldName(), 10.12);
        MathPlusMap map = new MathPlusMap(srcFld, dstFld);
        map.write(rd, rd);
        assertEquals(rd.getFieldValue(dstFld.getFieldName()), 20.24);
    }

    @Test
    public void testSrcFieldNotNumeric() throws Exception {
        Field srcFld = new Field("A1", FieldType.Date, false);
        Field dstFld = new Field("B1", FieldType.Double, false);
        RowData rd = new RowData();
        rd.setFieldValue(srcFld.getFieldName(), 10.12);
        rd.setFieldValue(dstFld.getFieldName(), 10.12);
        MathPlusMap map = new MathPlusMap(srcFld, dstFld);
        try {
            map.write(rd, rd);
            fail("you should never enter here");
        }
        catch (UnableToWriteFieldMapData ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }

    @Test
    public void testDstFieldNotNumeric() throws Exception {
        Field srcFld = new Field("A1", FieldType.Double, false);
        Field dstFld = new Field("B1", FieldType.Date, false);
        RowData rd = new RowData();
        rd.setFieldValue(srcFld.getFieldName(), 10.12);
        rd.setFieldValue(dstFld.getFieldName(), 10.12);
        MathPlusMap map = new MathPlusMap(srcFld, dstFld);
        try {
            map.write(rd, rd);
            fail("you should never enter here");
        }
        catch (UnableToWriteFieldMapData ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }
    
    @Test
    public void testDifferentTypes() throws Exception {
        Field srcFld = new Field("A1", FieldType.Double, false);
        Field dstFld = new Field("B1", FieldType.Integer, false);
        RowData rd = new RowData();
        rd.setFieldValue(srcFld.getFieldName(), 10.12);
        rd.setFieldValue(dstFld.getFieldName(), 10.12);
        MathPlusMap map = new MathPlusMap(srcFld, dstFld);
        try {
            map.write(rd, rd);
            fail("you should never enter here");
        }
        catch (UnableToWriteFieldMapData ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }

    @Test
    public void testSrcNullValue() throws Exception {
        Field srcFld = new Field("A1", FieldType.Integer, false);
        Field dstFld = new Field("B1", FieldType.Integer, false);
        RowData rd = new RowData();
//        rd.setFieldValue(srcFld.getFieldName(), 10.12);
        rd.setFieldValue(dstFld.getFieldName(), 10.12);
        MathPlusMap map = new MathPlusMap(srcFld, dstFld);
        try {
            map.write(rd, rd);
            fail("you should never enter here");
        }
        catch (UnableToWriteFieldMapData ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }

    @Test
    public void testDstNullValue() throws Exception {
        Field srcFld = new Field("A1", FieldType.Integer, false);
        Field dstFld = new Field("B1", FieldType.Integer, false);
        RowData rd = new RowData();
        rd.setFieldValue(srcFld.getFieldName(), 10.12);
//        rd.setFieldValue(dstFld.getFieldName(), 10.12);
        MathPlusMap map = new MathPlusMap(srcFld, dstFld);
        try {
            map.write(rd, rd);
            fail("you should never enter here");
        }
        catch (UnableToWriteFieldMapData ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }

    @Test
    public void testSrcValueNotNumeric() throws Exception {
        Field srcFld = new Field("A1", FieldType.Integer, false);
        Field dstFld = new Field("B1", FieldType.Integer, false);
        RowData rd = new RowData();
        rd.setFieldValue(srcFld.getFieldName(), "Alo");
        rd.setFieldValue(dstFld.getFieldName(), 10.12);
        MathPlusMap map = new MathPlusMap(srcFld, dstFld);
        try {
            map.write(rd, rd);
            fail("you should never enter here");
        }
        catch (UnableToWriteFieldMapData ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }

    @Test
    public void testDstValueNotNumeric() throws Exception {
        Field srcFld = new Field("A1", FieldType.Integer, false);
        Field dstFld = new Field("B1", FieldType.Integer, false);
        RowData rd = new RowData();
        rd.setFieldValue(srcFld.getFieldName(), 10.12);
        rd.setFieldValue(dstFld.getFieldName(), "Alo");
        MathPlusMap map = new MathPlusMap(srcFld, dstFld);
        try {
            map.write(rd, rd);
            fail("you should never enter here");
        }
        catch (UnableToWriteFieldMapData ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }
}