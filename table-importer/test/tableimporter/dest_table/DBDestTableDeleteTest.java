/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: DBDestTableDeleteTest.java
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
public class DBDestTableDeleteTest {

    public DBDestTableDeleteTest() {
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
     * Test of postRecord method, of class DBDestTableDelete.
     */
    @Test
    public void testPostRecordKeyFieldsAsWhere() throws ClassNotFoundException, SQLException, MalformedURLException, InstantiationException, IllegalAccessException {
        System.out.println("DBDestTableDeleteTest.testPostRecord - Warning: The test code is using hard coded paths to databases if you are running this test code on another machine please change the hard coded paths!");
        String driverFileName = "file:/C:/temp/unit_tests/mysql-connector-java-5.1.10-bin.jar";
        String strClassName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/test";
        String user = "root";
        String pass = "root";
        DBConnectionBuilder instance = new DBConnectionBuilder();
        Connection conn = instance.buildConnection(driverFileName, strClassName, url, user, pass);
        assertNotNull(conn);
        
        System.out.println("DBDestTableDeleteTest.testPostRecord - Prepare DB table with data for updating");
        // First delete if there was old data in the database
        Statement st = conn.createStatement();
        int updateCount = st.executeUpdate("delete from persons2 where persons_id = 5 or persons_id = 6;");
        // Create new row in database
        st = conn.createStatement();
        updateCount = st.executeUpdate("insert into persons2(persons_id, persons_name, persons_age, persons_address) values(5, 'Piotor Sanakulov', 57, 'Some Way');");
        System.out.println("testPostRecord - Update count " + ((Integer)updateCount).toString());
        updateCount = st.executeUpdate("insert into persons2(persons_id, persons_name, persons_age, persons_address) values(6, 'James Gorbachov', 39, 'Other Way');");
        System.out.println("testPostRecord - Update count " + ((Integer)updateCount).toString());

        ArrayList<IField> fields = new ArrayList<IField>();
        fields.add(new Field("persons_id", FieldType.Integer, true));
        fields.add(new Field("persons_name", FieldType.String, false));
        fields.add(new Field("persons_age", FieldType.Integer, false));
        fields.add(new Field("persons_address", FieldType.String, false));

        RowData[] rowDatas = new RowData[2];
        rowDatas[0] = new RowData();
        rowDatas[0].setFieldValue("persons_id", 5);
        rowDatas[0].setFieldValue("persons_name", "Piotor Sanakulov");
        rowDatas[0].setFieldValue("persons_age", 57);
        rowDatas[0].setFieldValue("persons_address", "Some Way");

        rowDatas[1] = new RowData();
        rowDatas[1].setFieldValue("persons_id", 6);
        rowDatas[1].setFieldValue("persons_name", "James Gorbachov");
        rowDatas[1].setFieldValue("persons_age", 39);
        rowDatas[1].setFieldValue("persons_address", "Other Way");

        ISQLStatementBuilder statementBuilder = new CommonSQLStatementBuilder();

        DBDestTableDelete deleter = new DBDestTableDelete(conn, fields, "persons2", statementBuilder, true, "");
        System.out.println("testPostRecord - Begin posting records");
        for (int i = 0; i < rowDatas.length; ++i) {
            deleter.postRecord(rowDatas[i]);
        }
        System.out.println("testPostRecord - End posting records");

        System.out.println("testPostRecord - We need to make a query to check is the update is ok");
        st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from persons2 where persons_id = 5 or persons_id = 6;");

        while (rs.next()) {
            fail("You never enter here");
        }

        conn.close();
        System.out.println("DBDestTableUpdateTest.testPostRecordKeyFieldsAsWhere - test complete");
    }

    @Test
    public void testPostRecordNotKeyFieldsAsWhere() throws ClassNotFoundException, SQLException, MalformedURLException, InstantiationException, IllegalAccessException {
        System.out.println("DBDestTableDeleteTest.testPostRecord - Warning: The test code is using hard coded paths to databases if you are running this test code on another machine please change the hard coded paths!");
        String driverFileName = "file:/C:/temp/unit_tests/mysql-connector-java-5.1.10-bin.jar";
        String strClassName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/test";
        String user = "root";
        String pass = "root";
        DBConnectionBuilder instance = new DBConnectionBuilder();
        Connection conn = instance.buildConnection(driverFileName, strClassName, url, user, pass);
        assertNotNull(conn);

        System.out.println("DBDestTableDeleteTest.testPostRecord - Prepare DB table with data for updating");
        // First delete if there was old data in the database
        Statement st = conn.createStatement();
        int updateCount = st.executeUpdate("delete from persons2 where persons_id = 5 or persons_id = 6;");
        // Create new row in database
        st = conn.createStatement();
        updateCount = st.executeUpdate("insert into persons2(persons_id, persons_name, persons_age, persons_address) values(5, 'Piotor Sanakulov', 57, 'Some Way');");
        System.out.println("testPostRecord - Update count " + ((Integer)updateCount).toString());
        updateCount = st.executeUpdate("insert into persons2(persons_id, persons_name, persons_age, persons_address) values(6, 'James Gorbachov', 39, 'Other Way');");
        System.out.println("testPostRecord - Update count " + ((Integer)updateCount).toString());

        ArrayList<IField> fields = new ArrayList<IField>();
        fields.add(new Field("persons_id", FieldType.Integer, true));
        fields.add(new Field("persons_name", FieldType.String, false));
        fields.add(new Field("persons_age", FieldType.Integer, false));
        fields.add(new Field("persons_address", FieldType.String, false));

        RowData[] rowDatas = new RowData[2];
        rowDatas[0] = new RowData();
        rowDatas[0].setFieldValue("persons_id", 5);
        rowDatas[0].setFieldValue("persons_name", "Piotor Sanakulov");
        rowDatas[0].setFieldValue("persons_age", 57);
        rowDatas[0].setFieldValue("persons_address", "Some Way");

        rowDatas[1] = new RowData();
        rowDatas[1].setFieldValue("persons_id", 6);
        rowDatas[1].setFieldValue("persons_name", "James Gorbachov");
        rowDatas[1].setFieldValue("persons_age", 39);
        rowDatas[1].setFieldValue("persons_address", "Other Way");

        ISQLStatementBuilder statementBuilder = new CommonSQLStatementBuilder();

        DBDestTableDelete deleter = new DBDestTableDelete(conn, fields, "persons2", statementBuilder, false, "");
        System.out.println("testPostRecord - Begin posting records");
        for (int i = 0; i < rowDatas.length; ++i) {
            deleter.postRecord(rowDatas[i]);
        }
        System.out.println("testPostRecord - End posting records");

        System.out.println("testPostRecord - We need to make a query to check is the update is ok");
        st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from persons2 where persons_id = 5 or persons_id = 6;");

        while (rs.next()) {
            fail("You never enter here");
        }

        conn.close();
        System.out.println("DBDestTableUpdateTest.testPostRecordKeyFieldsAsWhere - test complete");
    }

    @Test
    public void testPostRecordKeyFieldsAsWherePlusWhere() throws ClassNotFoundException, SQLException, MalformedURLException, InstantiationException, IllegalAccessException {
        System.out.println("DBDestTableDeleteTest.testPostRecord - Warning: The test code is using hard coded paths to databases if you are running this test code on another machine please change the hard coded paths!");
        String driverFileName = "file:/C:/temp/unit_tests/mysql-connector-java-5.1.10-bin.jar";
        String strClassName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/test";
        String user = "root";
        String pass = "root";
        DBConnectionBuilder instance = new DBConnectionBuilder();
        Connection conn = instance.buildConnection(driverFileName, strClassName, url, user, pass);
        assertNotNull(conn);

        System.out.println("DBDestTableDeleteTest.testPostRecord - Prepare DB table with data for updating");
        // First delete if there was old data in the database
        Statement st = conn.createStatement();
        int updateCount = st.executeUpdate("delete from persons2 where persons_id = 5 or persons_id = 6;");
        // Create new row in database
        st = conn.createStatement();
        updateCount = st.executeUpdate("insert into persons2(persons_id, persons_name, persons_age, persons_address) values(5, 'Piotor Sanakulov', 57, 'Some Way');");
        System.out.println("testPostRecord - Update count " + ((Integer)updateCount).toString());
        updateCount = st.executeUpdate("insert into persons2(persons_id, persons_name, persons_age, persons_address) values(6, 'James Gorbachov', 39, 'Other Way');");
        System.out.println("testPostRecord - Update count " + ((Integer)updateCount).toString());

        ArrayList<IField> fields = new ArrayList<IField>();
        fields.add(new Field("persons_id", FieldType.Integer, true));
        fields.add(new Field("persons_name", FieldType.String, false));
        fields.add(new Field("persons_age", FieldType.Integer, false));
        fields.add(new Field("persons_address", FieldType.String, false));

        RowData[] rowDatas = new RowData[2];
        rowDatas[0] = new RowData();
        rowDatas[0].setFieldValue("persons_id", 5);
        rowDatas[0].setFieldValue("persons_name", "Piotor Sanakulov");
        rowDatas[0].setFieldValue("persons_age", 57);
        rowDatas[0].setFieldValue("persons_address", "Some Way");

        rowDatas[1] = new RowData();
        rowDatas[1].setFieldValue("persons_id", 6);
        rowDatas[1].setFieldValue("persons_name", "James Gorbachov");
        rowDatas[1].setFieldValue("persons_age", 39);
        rowDatas[1].setFieldValue("persons_address", "Other Way");

        ISQLStatementBuilder statementBuilder = new CommonSQLStatementBuilder();

        DBDestTableDelete deleter = new DBDestTableDelete(conn, fields, "persons2", statementBuilder, true, "persons_id <> 6");
        System.out.println("testPostRecord - Begin posting records");
        for (int i = 0; i < rowDatas.length; ++i) {
            deleter.postRecord(rowDatas[i]);
        }
        System.out.println("testPostRecord - End posting records");

        System.out.println("testPostRecord - We need to make a query to check is the update is ok");
        st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from persons2 where persons_id = 5 or persons_id = 6;");

        while (rs.next()) {
            assertEquals(rs.getInt("persons_id"), 6);
            assertEquals(rs.getString("persons_name"), "James Gorbachov");
            assertEquals(rs.getInt("persons_age"), 39);
            assertEquals(rs.getString("persons_address"), "Other Way");
        }

        conn.close();
        System.out.println("DBDestTableUpdateTest.testPostRecordKeyFieldsAsWhere - test complete");
    }
}