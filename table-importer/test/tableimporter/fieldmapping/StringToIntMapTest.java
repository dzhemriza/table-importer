/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: StringToIntMapTest.java
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
public class StringToIntMapTest {

    public StringToIntMapTest() {
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
     * Test of write method, of class StringToIntMap.
     */
    @Test
    public void testWriteGeneralCase() throws Exception {
        RowData src = new RowData();
        RowData dst = new RowData();
        Field fldSrc = new Field("str1", FieldType.String, false);
        Field fldDst = new Field("int1", FieldType.Integer, false);
        StringToIntMap map = new StringToIntMap(fldSrc, fldDst);
        src.setFieldValue(fldSrc.getFieldName(), "12");
        map.write(src, dst);
        assertEquals(12, (int) (Integer) dst.getFieldValue(fldDst.getFieldName()));
    }

    @Test
    public void testWriteSrcFieldNotString() throws Exception {
        RowData src = new RowData();
        RowData dst = new RowData();
        Field fldSrc = new Field("str1", FieldType.Integer, false);
        Field fldDst = new Field("int1", FieldType.Integer, false);
        StringToIntMap map = new StringToIntMap(fldSrc, fldDst);
        src.setFieldValue(fldSrc.getFieldName(), 12);
        try {
            map.write(src, dst);
            fail("you should never enter here");
        }
        catch (UnableToWriteFieldMapData ex) {
            assertTrue(true);
        }
    }

    @Test
    public void testWriteDstFieldNotInteger() throws Exception {
        RowData src = new RowData();
        RowData dst = new RowData();
        Field fldSrc = new Field("str1", FieldType.String, false);
        Field fldDst = new Field("int1", FieldType.DateTime, false);
        StringToIntMap map = new StringToIntMap(fldSrc, fldDst);
        src.setFieldValue(fldSrc.getFieldName(), "12");
        try {
            map.write(src, dst);
            fail("you should never enter here");
        }
        catch (UnableToWriteFieldMapData ex) {
            assertTrue(true);
        }
    }

    @Test
    public void testWriteNullData() throws Exception {
        RowData src = new RowData();
        RowData dst = new RowData();
        Field fldSrc = new Field("str1", FieldType.String, false);
        Field fldDst = new Field("int1", FieldType.Integer, false);
        StringToIntMap map = new StringToIntMap(fldSrc, fldDst);
        try {
            map.write(src, dst);
            fail("you should never enter here");
        }
        catch (UnableToWriteFieldMapData ex) {
            assertTrue(true);
        }
    }
}