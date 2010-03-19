/*
 * table-importer
 * Imports tabled data from any source to any destination
 * 
 * File Name: DBConnectionBuilderTest.java
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

import java.sql.Connection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tableimporter.utils.db.DBConnectionBuilder;

/**
 *
 * @author djemriza
 */
public class DBConnectionBuilderTest {

    public DBConnectionBuilderTest() {
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
     * Test of buildConnection method, of class DBConnectionBuilder.
     */
    @Test
    public void testBuildConnection_4args() throws Exception {
        System.out.println("testBuildConnection_4args - Warning: The test code is using hard coded paths to databases if you are running this test code on another machine please change the hard coded paths!");

        String strClassName = "sun.jdbc.odbc.JdbcOdbcDriver";
        String url = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=C:/temp/unit_tests/test_mdb.mdb";
        String user = "admin";
        String pass = "";
        DBConnectionBuilder instance = new DBConnectionBuilder();
        Connection conn = instance.buildConnection(strClassName, url, user, pass);
        assertNotNull(conn);
        conn.close();
    }

    /**
     * Test of buildConnection method, of class DBConnectionBuilder.
     */
    @Test
    public void testBuildConnection_5args() throws Exception {
        System.out.println("testBuildConnection_5args - Warning: The test code is using hard coded paths to databases if you are running this test code on another machine please change the hard coded paths!");

        String driverFileName = "file:/C:/temp/unit_tests/mysql-connector-java-5.1.10-bin.jar";
        String strClassName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/test";
        String user = "root";
        String pass = "root";
        DBConnectionBuilder instance = new DBConnectionBuilder();
        Connection conn = instance.buildConnection(driverFileName, strClassName, url, user, pass);
        assertNotNull(conn);
    }

}