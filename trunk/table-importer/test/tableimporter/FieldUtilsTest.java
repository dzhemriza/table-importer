/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: FieldUtilsTest.java
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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tableimporter.fields.*;

/**
 *
 * @author djemriza
 */
public class FieldUtilsTest {

    public FieldUtilsTest() {
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
     * Test of toNamesArray method, of class FieldUtils.
     */
    @Test
    public void testToNamesArray() {
        Vector<IField> fields = new Vector<IField>();
        fields.add(new Field("A", FieldType.Integer, false));
        fields.add(new Field("B", FieldType.Integer, false));
        fields.add(new Field("C", FieldType.Integer, false));
        String[] expResult = {"A", "B", "C"};
        String[] result = FieldUtils.toNamesArray(fields);
        assertEquals(expResult.length, result.length);
        for (int i = 0; i < expResult.length; ++i) {
            assertEquals(expResult[i], result[i]);
        }
    }

    @Test
    public void testKeyFieldToNamesArray() {
        Vector<IField> fields = new Vector<IField>();
        fields.add(new Field("A", FieldType.Integer, true));
        fields.add(new Field("B", FieldType.Integer, false));
        fields.add(new Field("C", FieldType.Integer, false));
        String[] expResult = {"A"};
        String[] result = FieldUtils.keyFieldToNamesArray(fields);
        assertEquals(expResult.length, result.length);
        for (int i = 0; i < expResult.length; ++i) {
            assertEquals(expResult[i], result[i]);
        }
    }

    @Test
    public void testNonKeyFieldToNamesArray() {
        Vector<IField> fields = new Vector<IField>();
        fields.add(new Field("A", FieldType.Integer, true));
        fields.add(new Field("B", FieldType.Integer, false));
        fields.add(new Field("C", FieldType.Integer, false));
        String[] expResult = {"B", "C"};
        String[] result = FieldUtils.nonKeyFieldToNamesArray(fields);
        assertEquals(expResult.length, result.length);
        for (int i = 0; i < expResult.length; ++i) {
            assertEquals(expResult[i], result[i]);
        }
    }

    @Test
    public void testNumericType() {
        assertFalse(FieldUtils.isNumericType(FieldType.Date));
        assertFalse(FieldUtils.isNumericType(FieldType.String));
        assertTrue(FieldUtils.isNumericType(FieldType.Integer));
        assertTrue(FieldUtils.isNumericType(FieldType.Float));
        assertTrue(FieldUtils.isNumericType(FieldType.Double));
        assertFalse(FieldUtils.isNumericType(FieldType.Time));
        assertFalse(FieldUtils.isNumericType(FieldType.DateTime));
        assertFalse(FieldUtils.isNumericType(FieldType.Other));
    }
    
}