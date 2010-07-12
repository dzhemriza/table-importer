/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: MathDivMapTest.java
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
public class MathDivMapTest {

    public MathDivMapTest() {
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
     * Test of operateInt method, of class MathDivMap.
     */
    @Test
    public void testOperateInt() throws Exception {
        int src = 20;
        int dst = 10;
        MathDivMap instance = new MathDivMap(null, null);
        int expResult = 2;
        int result = instance.operateInt(src, dst);
        assertEquals(expResult, result);
    }

    /**
     * Test of operateFloat method, of class MathDivMap.
     */
    @Test
    public void testOperateFloat() throws Exception {
        float src = 20.0F;
        float dst = 10.0F;
        MathDivMap instance = new MathDivMap(null, null);
        float expResult = 2.0F;
        float result = instance.operateFloat(src, dst);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of operateDouble method, of class MathDivMap.
     */
    @Test
    public void testOperateDouble() throws Exception {
        double src = 20.0;
        double dst = 2.0;
        MathDivMap instance = new MathDivMap(null, null);
        double expResult = 10.0;
        double result = instance.operateDouble(src, dst);
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testOperateIntDivZero() throws Exception {
        int src = 20;
        int dst = 0;
        MathDivMap instance = new MathDivMap(null, null);
        try {
            instance.operateInt(src, dst);
            fail("you should never enter here");
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void testOperateFloatDivZero() throws Exception {
        float src = 20.0F;
        float dst = 0.0F;
        MathDivMap instance = new MathDivMap(null, null);
        try {
            instance.operateFloat(src, dst);
            fail("you should never enter here");
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void testOperateDoubleDivZero() throws Exception {
        double src = 20.0;
        double dst = 0.0;
        MathDivMap instance = new MathDivMap(null, null);
        try {
            instance.operateDouble(src, dst);
            fail("you should never enter here");
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}