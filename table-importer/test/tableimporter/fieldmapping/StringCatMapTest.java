/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: StringCatMapTest.java
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
import tableimporter.fields.Field;
import tableimporter.fields.FieldType;

/**
 *
 * @author djemriza
 */
public class StringCatMapTest {

    public StringCatMapTest() {
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
    public void testWriteGeneralCase() throws Exception {
        Field srcFld = new Field("A1", FieldType.String, true);
        Field dstFld = new Field("B1", FieldType.String, true);

        RowData rd = new RowData();
        rd.setFieldValue(srcFld.getFieldName(), "ALO");
        rd.setFieldValue(dstFld.getFieldName(), "OLA");

        StringCatMap map = new StringCatMap(srcFld, dstFld);
        map.write(rd, rd);
        assertEquals("OLAALO", rd.getFieldValue(dstFld.getFieldName()));
    }

    @Test
    public void testWriteWrongType1() throws Exception {
        Field srcFld = new Field("A1", FieldType.DateTime, true);
        Field dstFld = new Field("B1", FieldType.String, true);

        RowData rd = new RowData();
        rd.setFieldValue(srcFld.getFieldName(), "ALO");
        rd.setFieldValue(dstFld.getFieldName(), "OLA");

        StringCatMap map = new StringCatMap(srcFld, dstFld);
        try {
            map.write(rd, rd);
            fail("You should never enter here");
        }
        catch (UnableToWriteFieldMapData ex) {
            System.out.println(ex.getMessage());
            assertTrue(true);
        }
    }

    @Test
    public void testWriteWrongType2() throws Exception {
        Field srcFld = new Field("A1", FieldType.String, true);
        Field dstFld = new Field("B1", FieldType.Date, true);

        RowData rd = new RowData();
        rd.setFieldValue(srcFld.getFieldName(), "ALO");
        rd.setFieldValue(dstFld.getFieldName(), "OLA");

        StringCatMap map = new StringCatMap(srcFld, dstFld);
        try {
            map.write(rd, rd);
            fail("You should never enter here");
        }
        catch (UnableToWriteFieldMapData ex) {
            System.out.println(ex.getMessage());
            assertTrue(true);
        }
    }

    @Test
    public void testWriteWrongData1() throws Exception {
        Field srcFld = new Field("A1", FieldType.String, true);
        Field dstFld = new Field("B1", FieldType.String, true);

        RowData rd = new RowData();
        rd.setFieldValue(srcFld.getFieldName(), 12);
        rd.setFieldValue(dstFld.getFieldName(), "OLA");

        StringCatMap map = new StringCatMap(srcFld, dstFld);
        try {
            map.write(rd, rd);
            fail("You should never enter here");
        }
        catch (UnableToWriteFieldMapData ex) {
            System.out.println(ex.getMessage());
            assertTrue(true);
        }
    }

    @Test
    public void testWriteWrongData2() throws Exception {
        Field srcFld = new Field("A1", FieldType.String, true);
        Field dstFld = new Field("B1", FieldType.String, true);

        RowData rd = new RowData();
        rd.setFieldValue(srcFld.getFieldName(), "OLA");
        rd.setFieldValue(dstFld.getFieldName(), 12);

        StringCatMap map = new StringCatMap(srcFld, dstFld);
        try {
            map.write(rd, rd);
            fail("You should never enter here");
        }
        catch (UnableToWriteFieldMapData ex) {
            System.out.println(ex.getMessage());
            assertTrue(true);
        }
    }

    @Test
    public void testWriteNullSrc() throws Exception {
        Field srcFld = new Field("A1", FieldType.String, true);
        Field dstFld = new Field("B1", FieldType.String, true);

        RowData rd = new RowData();
//        rd.setFieldValue(srcFld.getFieldName(), "ALO");
        rd.setFieldValue(dstFld.getFieldName(), "OLA");

        StringCatMap map = new StringCatMap(srcFld, dstFld);
        map.write(rd, rd);
        assertEquals("OLA", rd.getFieldValue(dstFld.getFieldName()));
   }

    @Test
    public void testWriteNullDst() throws Exception {
        Field srcFld = new Field("A1", FieldType.String, true);
        Field dstFld = new Field("B1", FieldType.String, true);

        RowData rd = new RowData();
        rd.setFieldValue(srcFld.getFieldName(), "ALO");
//        rd.setFieldValue(dstFld.getFieldName(), "OLA");

        StringCatMap map = new StringCatMap(srcFld, dstFld);
        map.write(rd, rd);
        assertEquals("ALO", rd.getFieldValue(dstFld.getFieldName()));
   }

    @Test
    public void testWriteNullSrcDst() throws Exception {
        Field srcFld = new Field("A1", FieldType.String, true);
        Field dstFld = new Field("B1", FieldType.String, true);

        RowData rd = new RowData();
//        rd.setFieldValue(srcFld.getFieldName(), "ALO");
//        rd.setFieldValue(dstFld.getFieldName(), "OLA");

        StringCatMap map = new StringCatMap(srcFld, dstFld);
        map.write(rd, rd);
        assertEquals("", rd.getFieldValue(dstFld.getFieldName()));
   }
}