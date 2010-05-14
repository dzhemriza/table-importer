/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: DBTableInserterTest.java
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

package tableimporter.dest_table.db_operations;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tableimporter.collections.RowData;
import tableimporter.utils.db.*;
import tableimporter.fields.*;

/**
 *
 * @author djemriza
 */
public class DBTableInserterTest {

    public DBTableInserterTest() {
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
     * Test of insertRow method, of class DBTableInserter.
     */
    @Test
    public void testInsertRow() throws ClassNotFoundException, SQLException, MalformedURLException, InstantiationException, IllegalAccessException {
        System.out.println("Warning: The test code is using hard coded paths to databases if you are running this test code on another machine please change the hard coded paths!");
        String driverFileName = "file:/C:/temp/unit_tests/mysql-connector-java-5.1.10-bin.jar";
        String strClassName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/test";
        String user = "root";
        String pass = "root";
        DBConnectionBuilder instance = new DBConnectionBuilder();
        Connection conn = instance.buildConnection(driverFileName, strClassName, url, user, pass);
        assertNotNull(conn);

        ISQLStatementBuilder sqlStmBld = new CommonSQLStatementBuilder();

        DBTableInserter inserter = new DBTableInserter(conn, sqlStmBld, "persons2");
        
        Vector<IField> fields = new Vector<IField>();
        fields.add(new Field("persons_id", FieldType.Integer, true));
        fields.add(new Field("persons_name", FieldType.String, false));
        fields.add(new Field("persons_age", FieldType.Integer, false));
        fields.add(new Field("persons_address", FieldType.String, false));

        RowData rd = new RowData();
        rd.setFieldValue("persons_id", 8);
        rd.setFieldValue("persons_name", "Inchi Mehmed");
        rd.setFieldValue("persons_age", 26);
        rd.setFieldValue("persons_address", "Californa Bulvard");

        System.out.println("Delete the old data before post the new");
        Statement st = conn.createStatement();
        st.executeUpdate("delete from persons2 where persons_id = 8;");

        System.out.println("Post the new data");
        inserter.insertRow(fields, rd);

        System.out.println("Checking inserted data");
        st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from persons2 where persons_id = 8;");
        while (rs.next()) {
            assertEquals(rs.getInt("persons_id"), rd.getFieldValue("persons_id"));
            assertEquals(rs.getString("persons_name"), rd.getFieldValue("persons_name"));
            assertEquals(rs.getInt("persons_age"), rd.getFieldValue("persons_age"));
            assertEquals(rs.getString("persons_address"), rd.getFieldValue("persons_address"));
        }

        System.out.println("Test is finished");
        conn.close();
    }

}