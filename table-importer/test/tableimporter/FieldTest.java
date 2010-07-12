/*
 * table-importer
 * Imports tabled data from any source to any destination
 * 
 * File Name: FieldTest.java
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

import tableimporter.fields.FieldType;
import tableimporter.fields.Field;
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
public class FieldTest {

    public FieldTest() {
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
     * Test of getFieldName method, of class Field.
     */
    @Test
    public void testGetFieldName() {
        Field instance = new Field("A", FieldType.String, false);
        assertEquals(instance.getFieldName(), "A");
    }

    /**
     * Test of getFieldType method, of class Field.
     */
    @Test
    public void testGetFieldType() {
        Field instance = new Field("A", FieldType.String, false);
        assertEquals(instance.getFieldType(), FieldType.String);
    }

    /**
     * Test of getDataIndex method, of class Field.
     */
    @Test
    public void testGetDataIndex() {
        Field instance = new Field("A", FieldType.String, true);
        assertEquals(instance.isKeyField(), true);
    }

}