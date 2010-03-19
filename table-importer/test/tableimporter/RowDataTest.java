/*
 * table-importer
 * Imports tabled data from any source to any destination
 * 
 * File Name: RowDataTest.java
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

import tableimporter.collections.RowData;
import tableimporter.collections.NameValueMap;
import java.util.Iterator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class of RowData class
 * @author djemriza
 */
public class RowDataTest {

    public RowDataTest() {
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
     * Test of setFieldValue method, of class RowData.
     */
    @Test
    public void testSetFieldValue() {
        RowData instance = new RowData();
        instance.setFieldValue("fld1", "string value");
        instance.setFieldValue("fld2", 2);
        instance.setFieldValue("fld3", 2.4);
        instance.setFieldValue("fld4", 2.4);

        Object val = instance.getFieldValue("fld1");
        assertNotNull(val);
        assertTrue(val instanceof String);

        val = instance.getFieldValue("fld2");
        assertNotNull(val);
        assertTrue(val instanceof Integer);

        val = instance.getFieldValue("fld3");
        assertNotNull(val);
        assertTrue(val instanceof Double);

        val = instance.getFieldValue("fld4");
        assertNotNull(val);
        assertTrue(val instanceof Double);

        val = instance.getFieldValue("fld5");
        assertNull(val);
    }

    /**
     * Test of getFieldValue method, of class RowData.
     */
    @Test
    public void testGetFieldValue() {
        RowData instance = new RowData();
        instance.setFieldValue("fld1", "string value");
        instance.setFieldValue("fld2", 2);
        instance.setFieldValue("fld3", 2.4);
        instance.setFieldValue("fld4", 2.4);

        Object val = instance.getFieldValue("fld1");
        assertNotNull(val);
        assertTrue(val instanceof String);
        assertEquals((String) val, "string value");

        val = instance.getFieldValue("Fld2");
        assertNotNull(val);
        assertTrue(val instanceof Integer);
        int nval = (Integer) val;
        assertEquals(nval, 2);

        val = instance.getFieldValue("fld3");
        assertNotNull(val);
        assertTrue(val instanceof Double);
        double dval = (Double) val;
        assertTrue(dval == 2.4);

        val = instance.getFieldValue("fld4");
        assertNotNull(val);
        assertTrue(val instanceof Double);
        dval = (Double) val;
        assertTrue(dval == 2.4);

        val = instance.getFieldValue("fld5");
        assertNull(val);
    }

    /**
     * Test of getFields method, of class RowData.
     */
    @Test
    public void testGetFields() {
        RowData instance = new RowData();
        Iterator<String> iterFields = instance.getFields();
        while (iterFields.hasNext()) {
            fail("you should never enter here");
            iterFields.next();
        }
        String[] someFieldNames = {"a", "B", "C",  "d", "FK121_RES_TBL"};
        for (int i = 0; i < someFieldNames.length; ++i)
            instance.setFieldValue(someFieldNames[i], null);
        iterFields = instance.getFields();
        while (iterFields.hasNext()) {
            String fldName = iterFields.next();
            int i = 0;
            for (i = 0; i < someFieldNames.length; ++i)
                if (0 == fldName.compareTo(someFieldNames[i]))
                    break;
            assertTrue(i < someFieldNames.length);
        }
    }

    /**
     * Test of clearAll method, of class RowData
     */
    @Test
    public void testClearAll() {
        RowData instance = new RowData();
        Iterator<String> iterFields = instance.getFields();
        while (iterFields.hasNext()) {
            fail("you should never enter here");
            iterFields.next();
        }
        String[] someFieldNames = {"a", "B", "C",  "d", "FK121_RES_TBL"};
        for (int i = 0; i < someFieldNames.length; ++i)
            instance.setFieldValue(someFieldNames[i], null);
        iterFields = instance.getFields();
        assertTrue(iterFields.hasNext());
        instance.clearAll();
        iterFields = instance.getFields();
        while (iterFields.hasNext()) {
            fail("you should never enter here");
            iterFields.next();
        }
    }

    @Test
    public void testGetFieldAttributes() {
        RowData instance = new RowData();
        instance.setFieldValue("fld1", "string value");
        instance.setFieldValue("fld2", 2);
        instance.setFieldValue("fld3", 2.4);
        instance.setFieldValue("fld4", 2.4);

        NameValueMap fld1Attrs = instance.getFieldAttributes("fld1");
        Iterator<String> fld1AttrsFields = fld1Attrs.getFields();
        if (fld1AttrsFields.hasNext())
            fail("There should no be a next object");
        fld1Attrs.setFieldValue("Atr1", "some string");
        assertEquals((String) fld1Attrs.getFieldValue("Atr1"), "some string");

        fld1Attrs = instance.getFieldAttributes("fld1");
        fld1AttrsFields = fld1Attrs.getFields();
        if (fld1AttrsFields.hasNext())
            assertTrue(true);
        else
            fail("Should have a fields");

        instance.setFieldValue("fld1", "string value 2");
        assertEquals((String) instance.getFieldValue("fld1"), "string value 2");
        
        fld1Attrs = instance.getFieldAttributes("fld1");
        assertEquals((String) fld1Attrs.getFieldValue("Atr1"), "some string");
        fld1AttrsFields = fld1Attrs.getFields();
        if (fld1AttrsFields.hasNext())
            assertTrue(true);
        else
            fail("Should have a fields");
   }
}