/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: CommonSQLStatementBuilderTest.java
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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tableimporter.utils.db.CommonSQLStatementBuilder;

/**
 *
 * @author djemriza
 */
public class CommonSQLStatementBuilderTest {

    public CommonSQLStatementBuilderTest() {
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
     * Test of buildParametricInsertStatement method, of class CommonSQLStatementBuilder.
     */
    @Test
    public void testBuildParametricInsertStatement() {
        String tableName = "persons";
        String[] fields = {"persons_name", "persons_age", "persons_address"};
        CommonSQLStatementBuilder instance = new CommonSQLStatementBuilder();
        String expResult = "insert into persons(persons_name, persons_age, persons_address) values(?, ?, ?);";
        String result = instance.buildParametricInsertStatement(tableName, fields);
        assertEquals(expResult, result);
    }

    /**
     * Test of buildParametricUpdateStatement method, of class CommonSQLStatementBuilder.
     */
    @Test
    public void testBuildParametricUpdateStatement() {
        String tableName = "persons";
        String[] fields = {"persons_name", "persons_age", "persons_address"};
        String where = "";
        CommonSQLStatementBuilder instance = new CommonSQLStatementBuilder();
        String expResult = "update persons set persons_name = ?, persons_age = ?, persons_address = ?;";
        String result = instance.buildParametricUpdateStatement(tableName, fields, where);
        assertEquals(expResult, result);
    }

    @Test
    public void testBuildParametricUpdateStatementWithWhere() {
        String tableName = "persons";
        String[] fields = {"persons_name", "persons_age", "persons_address"};
        String where = "persons_age > 20 and persons_name <> 'james'";
        CommonSQLStatementBuilder instance = new CommonSQLStatementBuilder();
        String expResult = "update persons set persons_name = ?, persons_age = ?, persons_address = ? where persons_age > 20 and persons_name <> 'james';";
        String result = instance.buildParametricUpdateStatement(tableName, fields, where);
        assertEquals(expResult, result);
    }

    @Test
    public void testBuildParametricUpdateStatement4ArgsWithoutWhere() {
        String tableName = "persons";
        String[] fields = {"persons_name", "persons_age", "persons_address"};
        String[] keyFields = {"id1", "id2", "id3"};
        String where = "";
        CommonSQLStatementBuilder instance = new CommonSQLStatementBuilder();
        String expResult = "update persons set persons_name = ?, persons_age = ?, persons_address = ? where (id1 = ? and id2 = ? and id3 = ?);";
        String result = instance.buildParametricUpdateStatement(tableName, keyFields, fields, where);
        assertEquals(expResult, result);
    }

    @Test
    public void testBuildParametricUpdateStatement4ArgsWithWhere() {
        String tableName = "persons";
        String[] fields = {"persons_name", "persons_age", "persons_address"};
        String[] keyFields = {"id1", "id2", "id3"};
        String where = "persons_age > 20 and persons_name <> 'james'";
        CommonSQLStatementBuilder instance = new CommonSQLStatementBuilder();
        String expResult = "update persons set persons_name = ?, persons_age = ?, persons_address = ? where (id1 = ? and id2 = ? and id3 = ?) and (persons_age > 20 and persons_name <> 'james');";
        String result = instance.buildParametricUpdateStatement(tableName, keyFields, fields, where);
        assertEquals(expResult, result);
    }

    @Test
    public void testBuildParametricDeleteStatement() {
        String tableName = "persons";
        String[] fields = {"persons_name", "persons_age", "persons_address"};
        CommonSQLStatementBuilder instance = new CommonSQLStatementBuilder();
        String expResult = "delete from persons where (persons_name = ? and persons_age = ? and persons_address = ?);";
        String result = instance.buildParametricDeleteStatement(tableName, fields, "");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testBuildParametricDeleteStatementNoFields() {
        String tableName = "persons";
        String[] fields = {};
        CommonSQLStatementBuilder instance = new CommonSQLStatementBuilder();
        String expResult = "delete from persons where (persons_id = 12);";
        String result = instance.buildParametricDeleteStatement(tableName, fields, "persons_id = 12");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testBuildParametricDeleteStatementFieldsAndWhere() {
        String tableName = "persons";
        String[] fields = {"persons_name", "persons_age", "persons_address"};
        CommonSQLStatementBuilder instance = new CommonSQLStatementBuilder();
        String expResult = "delete from persons where (persons_name = ? and persons_age = ? and persons_address = ?) and (persons_id = 12);";
        String result = instance.buildParametricDeleteStatement(tableName, fields, "persons_id = 12");
        assertEquals(expResult, result);
    }
}