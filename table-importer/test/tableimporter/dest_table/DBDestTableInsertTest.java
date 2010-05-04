/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: DBDestTableInsertTest.java
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

package tableimporter.dest_table;

import java.net.MalformedURLException;
import java.util.Vector;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class DBDestTableInsertTest {

    public DBDestTableInsertTest() {
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
     * Test of postRecord method, of class DBDestTableInsert.
     */
    @Test
    public void testPostRecord() throws ClassNotFoundException, SQLException, MalformedURLException, InstantiationException, IllegalAccessException {
        System.out.println("testPostRecord - Warning: The test code is using hard coded paths to databases if you are running this test code on another machine please change the hard coded paths!");
        String driverFileName = "file:/C:/temp/unit_tests/mysql-connector-java-5.1.10-bin.jar";
        String strClassName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/test";
        String user = "root";
        String pass = "root";
        DBConnectionBuilder instance = new DBConnectionBuilder();
        Connection conn = instance.buildConnection(driverFileName, strClassName, url, user, pass);
        assertNotNull(conn);

        RowData postRow = new RowData();
        postRow.setFieldValue("persons_id", 1);
        postRow.setFieldValue("persons_name", "James Peterson");
        postRow.setFieldValue("persons_age", 43);
        postRow.setFieldValue("persons_address", "Jameson st");

        System.out.println("testPostRecord - Before runing the test we need to clear old id == 1");
        Statement st = conn.createStatement();
        int updateCount = st.executeUpdate("delete from persons2 where persons_id = 1;");
        System.out.println("testPostRecord - Update count " + ((Integer)updateCount).toString());

        Vector<IField> fields = new Vector<IField>();
        fields.add(new Field("persons_id", FieldType.Integer, true));
        fields.add(new Field("persons_name", FieldType.String, false));
        fields.add(new Field("persons_age", FieldType.Integer, false));
        fields.add(new Field("persons_address", FieldType.String, false));

        ISQLStatementBuilder statementBuilder = new CommonSQLStatementBuilder();

        DBDestTableInsert insertTable = new DBDestTableInsert(conn, fields, "persons2", statementBuilder);

        System.out.println("testPostRecord - Posting begin");
        insertTable.postRecord(postRow);
        System.out.println("testPostRecord - Posting ends");

        System.out.println("testPostRecord - We need to make a query to check is there is posted record");
        st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from persons2 where persons_id = 1;");

        while (rs.next()) {
            assertEquals(rs.getInt("persons_id"), 1);
            assertEquals(rs.getString("persons_name"), "James Peterson");
            assertEquals(rs.getInt("persons_age"), 43);
            assertEquals(rs.getString("persons_address"), "Jameson st");
        }

        conn.close();

        System.out.println("testPostRecord - Test compleate");
    }

}