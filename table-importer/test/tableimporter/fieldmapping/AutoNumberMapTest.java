/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: AutoNumberMapTest.java
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
import org.apache.log4j.BasicConfigurator;

/**
 *
 * @author djemriza
 */
public class AutoNumberMapTest {

    public AutoNumberMapTest() {
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
     * Test of write method, of class AutoNumberMap.
     */
    @Test
    public void testWriteGeneralCase() throws Exception {
        Field fld = new Field("A1", FieldType.Integer, false);
        RowData destRowData = new RowData();
        int initialVal = 10;
        int incValue = 4;
        AutoNumberMap autoMap = new AutoNumberMap(fld, initialVal, incValue);
        
        int loopsCount = 100;
        int nextVal = initialVal;
        for (int i = 0; i < loopsCount; ++i) {
            autoMap.write(null, destRowData);
            assertEquals(nextVal, (int) (Integer) destRowData.getFieldValue(fld.getFieldName()));
            nextVal += incValue;
        }
    }

    @Test
    public void testWriteOnNonIntegerField() throws Exception {
        Field fld = new Field("A1", FieldType.Date, false);
        RowData destRowData = new RowData();
        int initialVal = 10;
        int incValue = 4;
        AutoNumberMap autoMap = new AutoNumberMap(fld, initialVal, incValue);

        try {
            autoMap.write(null, destRowData);
            fail("you should never enter here");
        }
        catch (UnableToWriteFieldMapData ex) {
            assertTrue(true);
            System.out.println(ex.getMessage());
        }
    }
}