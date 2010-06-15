/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: StringToDateTimeMapTest.java
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import tableimporter.fields.FieldType;

/**
 *
 * @author djemriza
 */
public class StringToDateTimeMapTest {

    public StringToDateTimeMapTest() {
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
     * Test of parseTypeFromString method, of class StringToDateTimeMap.
     */
    @Test
    public void testParseTypeFromStringGeneralCaseDate() throws Exception {
        String srcFieldValue = "02-01-2001";
        StringToDateTimeMap instance = new StringToDateTimeMap(null, null, "dd-mm-yyyy", FieldType.Date);

        DateFormat dateFormatter = new SimpleDateFormat("dd-mm-yyyy");
        Object expResult = dateFormatter.parse(srcFieldValue);

        Object result = instance.parseTypeFromString(srcFieldValue);
        assertEquals(expResult, result);

        System.out.println(result.toString());
    }

    @Test
    public void testParseTypeFromStringGeneralCaseTime() throws Exception {
        String srcFieldValue = "22:15:21";
        StringToDateTimeMap instance = new StringToDateTimeMap(null, null, "HH:mm:ss", FieldType.Time);

        DateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss");
        Object expResult = dateFormatter.parse(srcFieldValue);

        Object result = instance.parseTypeFromString(srcFieldValue);
        assertEquals(expResult, result);

        System.out.println(result.toString());
    }

    @Test
    public void testParseTypeFromStringGeneralCaseDateTime() throws Exception {
        String srcFieldValue = "02-01-2001 22:15:21";
        StringToDateTimeMap instance = new StringToDateTimeMap(null, null, "dd-mm-yyyy HH:mm:ss", FieldType.DateTime);

        DateFormat dateFormatter = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss");
        Object expResult = dateFormatter.parse(srcFieldValue);

        Object result = instance.parseTypeFromString(srcFieldValue);
        assertEquals(expResult, result);

        System.out.println(result.toString());
    }

    @Test
    public void testParseTypeFromStringWithException() throws Exception {
        String srcFieldValue = "02:0s1-2d001";
        StringToDateTimeMap instance = new StringToDateTimeMap(null, null, "dd-mm-yyyy", FieldType.Date);

        try {
            instance.parseTypeFromString(srcFieldValue);
            fail("you should never enter here!");
        }
        catch (Exception ex) {
            System.out.println("Exception : " + ex.getMessage());
            assertTrue(true);
        }
    }

    @Test
    public void testUnknownType() throws Exception {
        try {
            new StringToDateTimeMap(null, null, "dd-mm-yyyy", FieldType.Integer);
            fail("you should never enter here!");
        }
        catch (ExpectDateTimeType ex) {
            System.out.println("Exception : " + ex.getMessage());
            assertTrue(true);
        }
    }

}