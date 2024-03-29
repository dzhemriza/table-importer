/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: StringToDoubleMapTest.java
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
import org.apache.log4j.BasicConfigurator;

/**
 *
 * @author djemriza
 */
public class StringToDoubleMapTest {

    public StringToDoubleMapTest() {
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
     * Test of parseTypeFromString method, of class StringToDoubleMap.
     */
    @Test
    public void testParseTypeFromStringGeneralCase() throws Exception {
        String srcFieldValue = "12.34";
        StringToDoubleMap instance = new StringToDoubleMap(null, null);
        Object expResult = (double) 12.34;
        Object result = instance.parseTypeFromString(srcFieldValue);
        assertEquals(expResult, result);
    }

    @Test
    public void testParseTypeFromStringException() throws Exception {
        String srcFieldValue = "12d.geg34";
        StringToDoubleMap instance = new StringToDoubleMap(null, null);
        try {
            instance.parseTypeFromString(srcFieldValue);
            fail("you should never enter here!");
        }
        catch (Exception ex) {
            System.out.println("Exception : " + ex.getMessage());
            assertTrue(true);
        }
    }

}