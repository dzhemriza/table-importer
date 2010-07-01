/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: MathMultMapTest.java
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

/**
 *
 * @author djemriza
 */
public class MathMultMapTest {

    public MathMultMapTest() {
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
     * Test of operateInt method, of class MathMultMap.
     */
    @Test
    public void testOperateInt() throws Exception {
        int src = 2;
        int dst = 4;
        MathMultMap instance = new MathMultMap(null, null);
        int expResult = 8;
        int result = instance.operateInt(src, dst);
        assertEquals(expResult, result);
    }

    /**
     * Test of operateFloat method, of class MathMultMap.
     */
    @Test
    public void testOperateFloat() throws Exception {
        float src = 2.0F;
        float dst = 4.0F;
        MathMultMap instance = new MathMultMap(null, null);
        float expResult = 8.0F;
        float result = instance.operateFloat(src, dst);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of operateDouble method, of class MathMultMap.
     */
    @Test
    public void testOperateDouble() throws Exception {
        double src = 2.0;
        double dst = 4.0;
        MathMultMap instance = new MathMultMap(null, null);
        double expResult = 8.0;
        double result = instance.operateDouble(src, dst);
        assertEquals(expResult, result, 0.0);
    }

}