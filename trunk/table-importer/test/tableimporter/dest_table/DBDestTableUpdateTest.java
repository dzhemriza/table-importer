/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: DBDestTableUpdateTest.java
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
import java.util.ArrayList;
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
import org.apache.log4j.BasicConfigurator;

/**
 *
 * @author djemriza
 */
public class DBDestTableUpdateTest {

    public DBDestTableUpdateTest() {
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
     * Test of postRecord method, of class DBDestTableUpdate.
     */
    @Test
    public void testPostRecordKeyFieldsAsWhere() throws ClassNotFoundException, SQLException, MalformedURLException, InstantiationException, IllegalAccessException {
        System.out.println("DBDestTableUpdateTest.testPostRecord - Warning: The test code is using hard coded paths to databases if you are running this test code on another machine please change the hard coded paths!");
        String driverFileName = "file:/C:/temp/unit_tests/mysql-connector-java-5.1.10-bin.jar";
        String strClassName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/test";
        String user = "root";
        String pass = "root";
        DBConnectionBuilder instance = new DBConnectionBuilder();
        Connection conn = instance.buildConnection(driverFileName, strClassName, url, user, pass);
        assertNotNull(conn);

        System.out.println("DBDestTableUpdateTest.testPostRecord - Prepare DB table with data for updating");
        // First delete if there was old data in the database
        Statement st = conn.createStatement();
        int updateCount = st.executeUpdate("delete from persons2 where persons_id = 2;");
        // Create new row in database
        st = conn.createStatement();
        updateCount = st.executeUpdate("insert into persons2(persons_id, persons_name, persons_age, persons_address) values(2, 'Charles Dicinson', 34, 'Washington Way');");
        System.out.println("testPostRecord - Update count " + ((Integer)updateCount).toString());

        ArrayList<IField> fields = new ArrayList<IField>();
        fields.add(new Field("persons_id", FieldType.Integer, true));
        fields.add(new Field("persons_name", FieldType.String, false));
        fields.add(new Field("persons_age", FieldType.Integer, false));
        fields.add(new Field("persons_address", FieldType.String, false));

        ISQLStatementBuilder statementBuilder = new CommonSQLStatementBuilder();

        DBDestTableUpdate updateTable = new DBDestTableUpdate(conn, fields, "persons2", statementBuilder, true, ""/*no where*/);

        RowData updateRow = new RowData();
        updateRow.setFieldValue("persons_id", 2);
        updateRow.setFieldValue("persons_name", "Charles Dicinson + Updated");
        updateRow.setFieldValue("persons_age", 35);
        updateRow.setFieldValue("persons_address", "Washington Way + Updated");

        System.out.println("testPostRecord - Begin posting record");
        updateTable.postRecord(updateRow);
        System.out.println("testPostRecord - End posting record");

        System.out.println("testPostRecord - We need to make a query to check is the update is ok");
        st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from persons2 where persons_id = 2;");

        while (rs.next()) {
            assertEquals(rs.getInt("persons_id"), 2);
            assertEquals(rs.getString("persons_name"), "Charles Dicinson + Updated");
            assertEquals(rs.getInt("persons_age"), 35);
            assertEquals(rs.getString("persons_address"), "Washington Way + Updated");
        }

        conn.close();
        System.out.println("DBDestTableUpdateTest.testPostRecordKeyFieldsAsWhere - test complete");
    }

    @Test
    public void testPostRecordKeyFieldsAsWhereAndAdditionalWhere() throws ClassNotFoundException, SQLException, MalformedURLException, InstantiationException, IllegalAccessException {
        System.out.println("DBDestTableUpdateTest.testPostRecord - Warning: The test code is using hard coded paths to databases if you are running this test code on another machine please change the hard coded paths!");
        String driverFileName = "file:/C:/temp/unit_tests/mysql-connector-java-5.1.10-bin.jar";
        String strClassName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/test";
        String user = "root";
        String pass = "root";
        DBConnectionBuilder instance = new DBConnectionBuilder();
        Connection conn = instance.buildConnection(driverFileName, strClassName, url, user, pass);
        assertNotNull(conn);

        System.out.println("DBDestTableUpdateTest.testPostRecord - Prepare DB table with data for updating");
        // First delete if there was old data in the database
        Statement st = conn.createStatement();
        int updateCount = st.executeUpdate("delete from persons2 where persons_id = 2;");
        // Create new row in database
        st = conn.createStatement();
        updateCount = st.executeUpdate("insert into persons2(persons_id, persons_name, persons_age, persons_address) values(2, 'Charles Dicinson', 34, 'Washington Way');");
        System.out.println("testPostRecord - Update count " + ((Integer)updateCount).toString());

        ArrayList<IField> fields = new ArrayList<IField>();
        fields.add(new Field("persons_id", FieldType.Integer, true));
        fields.add(new Field("persons_name", FieldType.String, false));
        fields.add(new Field("persons_age", FieldType.Integer, false));
        fields.add(new Field("persons_address", FieldType.String, false));

        ISQLStatementBuilder statementBuilder = new CommonSQLStatementBuilder();

        DBDestTableUpdate updateTable = new DBDestTableUpdate(conn, fields, "persons2", statementBuilder, true, "persons_age = 34");

        RowData updateRow = new RowData();
        updateRow.setFieldValue("persons_id", 2);
        updateRow.setFieldValue("persons_name", "Charles Dicinson + Updated");
        updateRow.setFieldValue("persons_age", 35);
        updateRow.setFieldValue("persons_address", "Washington Way + Updated");

        System.out.println("testPostRecord - Begin posting record");
        updateTable.postRecord(updateRow);
        System.out.println("testPostRecord - End posting record");

        System.out.println("testPostRecord - We need to make a query to check is the update is ok");
        st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from persons2 where persons_id = 2;");

        while (rs.next()) {
            assertEquals(rs.getInt("persons_id"), 2);
            assertEquals(rs.getString("persons_name"), "Charles Dicinson + Updated");
            assertEquals(rs.getInt("persons_age"), 35);
            assertEquals(rs.getString("persons_address"), "Washington Way + Updated");
        }

        conn.close();
        System.out.println("DBDestTableUpdateTest.testPostRecordKeyFieldsAsWhere - test complete");
    }

    @Test
    public void testPostRecordKeyFieldsAsNotWhere() throws ClassNotFoundException, SQLException, MalformedURLException, InstantiationException, IllegalAccessException {
        System.out.println("DBDestTableUpdateTest.testPostRecord - Warning: The test code is using hard coded paths to databases if you are running this test code on another machine please change the hard coded paths!");
        String driverFileName = "file:/C:/temp/unit_tests/mysql-connector-java-5.1.10-bin.jar";
        String strClassName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/test";
        String user = "root";
        String pass = "root";
        DBConnectionBuilder instance = new DBConnectionBuilder();
        Connection conn = instance.buildConnection(driverFileName, strClassName, url, user, pass);
        assertNotNull(conn);

        System.out.println("DBDestTableUpdateTest.testPostRecord - Prepare DB table with data for updating");
        // First delete if there was old data in the database
        Statement st = conn.createStatement();
        int updateCount = st.executeUpdate("delete from persons2 where persons_id = 2;");
        // Create new row in database
        st = conn.createStatement();
        updateCount = st.executeUpdate("insert into persons2(persons_id, persons_name, persons_age, persons_address) values(2, 'Charles Dicinson', 34, 'Washington Way');");
        System.out.println("testPostRecord - Update count " + ((Integer)updateCount).toString());

        ArrayList<IField> fields = new ArrayList<IField>();
        fields.add(new Field("persons_id", FieldType.Integer, true));
        fields.add(new Field("persons_name", FieldType.String, false));
        fields.add(new Field("persons_age", FieldType.Integer, false));
        fields.add(new Field("persons_address", FieldType.String, false));

        ISQLStatementBuilder statementBuilder = new CommonSQLStatementBuilder();

        DBDestTableUpdate updateTable = new DBDestTableUpdate(conn, fields, "persons2", statementBuilder, false, "persons_age = 34");

        RowData updateRow = new RowData();
        updateRow.setFieldValue("persons_id", 2);
        updateRow.setFieldValue("persons_name", "Charles Dicinson + Updated");
        updateRow.setFieldValue("persons_age", 35);
        updateRow.setFieldValue("persons_address", "Washington Way + Updated");

        System.out.println("testPostRecord - Begin posting record");
        updateTable.postRecord(updateRow);
        System.out.println("testPostRecord - End posting record");

        System.out.println("testPostRecord - We need to make a query to check is the update is ok");
        st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from persons2 where persons_id = 2;");

        while (rs.next()) {
            assertEquals(rs.getInt("persons_id"), 2);
            assertEquals(rs.getString("persons_name"), "Charles Dicinson + Updated");
            assertEquals(rs.getInt("persons_age"), 35);
            assertEquals(rs.getString("persons_address"), "Washington Way + Updated");
        }

        conn.close();
        System.out.println("DBDestTableUpdateTest.testPostRecordKeyFieldsAsNotWhere - test complete");
    }

    @Test
    public void testPostRecordKeyFieldsAsNotWhere2() throws ClassNotFoundException, SQLException, MalformedURLException, InstantiationException, IllegalAccessException {
        System.out.println("DBDestTableUpdateTest.testPostRecord - Warning: The test code is using hard coded paths to databases if you are running this test code on another machine please change the hard coded paths!");
        String driverFileName = "file:/C:/temp/unit_tests/mysql-connector-java-5.1.10-bin.jar";
        String strClassName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/test";
        String user = "root";
        String pass = "root";
        DBConnectionBuilder instance = new DBConnectionBuilder();
        Connection conn = instance.buildConnection(driverFileName, strClassName, url, user, pass);
        assertNotNull(conn);

        System.out.println("DBDestTableUpdateTest.testPostRecord - Prepare DB table with data for updating");
        // First delete if there was old data in the database
        Statement st = conn.createStatement();
        int updateCount = st.executeUpdate("delete from persons2 where persons_id = 2;");
        // Create new row in database
        st = conn.createStatement();
        updateCount = st.executeUpdate("insert into persons2(persons_id, persons_name, persons_age, persons_address) values(2, 'Charles Dicinson', 34, 'Washington Way');");
        System.out.println("testPostRecord - Update count " + ((Integer)updateCount).toString());

        ArrayList<IField> fields = new ArrayList<IField>();
        fields.add(new Field("persons_name", FieldType.String, false));
        fields.add(new Field("persons_age", FieldType.Integer, false));
        fields.add(new Field("persons_address", FieldType.String, false));

        ISQLStatementBuilder statementBuilder = new CommonSQLStatementBuilder();

        DBDestTableUpdate updateTable = new DBDestTableUpdate(conn, fields, "persons2", statementBuilder, false, "" /*no where here*/);

        RowData updateRow = new RowData();
        updateRow.setFieldValue("persons_name", "Charles Dicinson + Updated");
        updateRow.setFieldValue("persons_age", 35);
        updateRow.setFieldValue("persons_address", "Washington Way + Updated");

        System.out.println("testPostRecord - Begin posting record");
        updateTable.postRecord(updateRow);
        System.out.println("testPostRecord - End posting record");

        System.out.println("testPostRecord - We need to make a query to check is the update is ok");
        st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from persons2 where persons_id = 2;");

        while (rs.next()) {
            assertEquals(rs.getString("persons_name"), "Charles Dicinson + Updated");
            assertEquals(rs.getInt("persons_age"), 35);
            assertEquals(rs.getString("persons_address"), "Washington Way + Updated");
        }

        conn.close();
        System.out.println("DBDestTableUpdateTest.testPostRecordKeyFieldsAsNotWhere - test complete");
    }
}