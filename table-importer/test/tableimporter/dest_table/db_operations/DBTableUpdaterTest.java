/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: DBTableUpdaterTest.java
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
public class DBTableUpdaterTest {

    public DBTableUpdaterTest() {
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
     * Test of updateRow method, of class DBTableUpdater.
     */
    @Test
    public void testUpdateRowKeyFieldsAsWhere() throws ClassNotFoundException, SQLException, MalformedURLException, InstantiationException, IllegalAccessException {
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

        DBTableUpdater updater = new DBTableUpdater(conn, sqlStmBld, "persons2");

        Vector<IField> fields = new Vector<IField>();
        fields.add(new Field("persons_id", FieldType.Integer, true));
        fields.add(new Field("persons_name", FieldType.String, false));
        fields.add(new Field("persons_age", FieldType.Integer, false));
        fields.add(new Field("persons_address", FieldType.String, false));

        RowData rd = new RowData();
        rd.setFieldValue("persons_id", 9);
        rd.setFieldValue("persons_name", "Murat Tatlises + Updated");
        rd.setFieldValue("persons_age", 35);
        rd.setFieldValue("persons_address", "Beyoglu Bulv + Updated");

        System.out.println("Delete the old data before post the new");
        Statement st = conn.createStatement();
        st.executeUpdate("delete from persons2 where persons_id = 9;");

        System.out.println("Insert data for update");
        st.executeUpdate("insert into persons2(persons_id, persons_name, persons_age, persons_address) values(9, 'Murat Tatlises', 34, 'Beyoglu Bulv')");

        System.out.println("Post the new data");
        updater.updateRow(fields, true, "", rd);

        System.out.println("Checking inserted data");
        st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from persons2 where persons_id = 9;");
        while (rs.next()) {
            assertEquals(rs.getInt("persons_id"), rd.getFieldValue("persons_id"));
            assertEquals(rs.getString("persons_name"), rd.getFieldValue("persons_name"));
            assertEquals(rs.getInt("persons_age"), rd.getFieldValue("persons_age"));
            assertEquals(rs.getString("persons_address"), rd.getFieldValue("persons_address"));
        }

        System.out.println("Test is finished");
        conn.close();
    }

    @Test
    public void testUpdateRowKeyFieldsAsNonWhere() throws ClassNotFoundException, SQLException, MalformedURLException, InstantiationException, IllegalAccessException {
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

        DBTableUpdater updater = new DBTableUpdater(conn, sqlStmBld, "persons2");

        Vector<IField> fields = new Vector<IField>();
        fields.add(new Field("persons_id", FieldType.Integer, true));
        fields.add(new Field("persons_name", FieldType.String, false));
        fields.add(new Field("persons_age", FieldType.Integer, false));
        fields.add(new Field("persons_address", FieldType.String, false));

        RowData rd = new RowData();
        rd.setFieldValue("persons_id", 9);
        rd.setFieldValue("persons_name", "Murat Tatlises + Updated");
        rd.setFieldValue("persons_age", 35);
        rd.setFieldValue("persons_address", "Beyoglu Bulv + Updated");

        System.out.println("Delete the old data before post the new");
        Statement st = conn.createStatement();
        st.executeUpdate("delete from persons2 where persons_id = 9;");
        st.executeUpdate("delete from persons2 where persons_id = 10;");

        System.out.println("Insert data for update");
        st.executeUpdate("insert into persons2(persons_id, persons_name, persons_age, persons_address) values(9, 'Murat Tatlises', 34, 'Beyoglu Bulv');");
        st.executeUpdate("insert into persons2(persons_id, persons_name, persons_age, persons_address) values(10, 'Orhan Pamuk', 36, 'Beyoglu Bulv');");

        System.out.println("Post the new data");
        try {
            updater.updateRow(fields, false, "", rd);
            fail("you never enter here because there is no where statement");
        }
        catch (SQLException ex) {
            System.out.println("Intended exception: " + ex.getMessage() + " Sql State: " + ex.getSQLState());
        }

        System.out.println("Test is finished");
        conn.close();
    }

    @Test
    public void testUpdateRowKeyFieldsAsNonWhereAndWhere() throws ClassNotFoundException, SQLException, MalformedURLException, InstantiationException, IllegalAccessException {
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

        DBTableUpdater updater = new DBTableUpdater(conn, sqlStmBld, "persons2");

        Vector<IField> fields = new Vector<IField>();
        fields.add(new Field("persons_id", FieldType.Integer, true));
        fields.add(new Field("persons_name", FieldType.String, false));
        fields.add(new Field("persons_age", FieldType.Integer, false));
        fields.add(new Field("persons_address", FieldType.String, false));

        RowData rd = new RowData();
        rd.setFieldValue("persons_id", 9);
        rd.setFieldValue("persons_name", "Murat Tatlises + Updated");
        rd.setFieldValue("persons_age", 35);
        rd.setFieldValue("persons_address", "Beyoglu Bulv + Updated");

        System.out.println("Delete the old data before post the new");
        Statement st = conn.createStatement();
        st.executeUpdate("delete from persons2 where persons_id = 9;");

        System.out.println("Insert data for update");
        st.executeUpdate("insert into persons2(persons_id, persons_name, persons_age, persons_address) values(9, 'Murat Tatlises', 34, 'Beyoglu Bulv')");

        System.out.println("Post the new data");
        updater.updateRow(fields, false, "persons_id = 9", rd);

        System.out.println("Checking inserted data");
        st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from persons2 where persons_id = 9;");
        while (rs.next()) {
            assertEquals(rs.getInt("persons_id"), rd.getFieldValue("persons_id"));
            assertEquals(rs.getString("persons_name"), rd.getFieldValue("persons_name"));
            assertEquals(rs.getInt("persons_age"), rd.getFieldValue("persons_age"));
            assertEquals(rs.getString("persons_address"), rd.getFieldValue("persons_address"));
        }

        System.out.println("Test is finished");
        conn.close();
    }

    @Test
    public void testUpdateRowKeyFieldsAsWhereAndWhere() throws ClassNotFoundException, SQLException, MalformedURLException, InstantiationException, IllegalAccessException {
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

        DBTableUpdater updater = new DBTableUpdater(conn, sqlStmBld, "persons2");

        Vector<IField> fields = new Vector<IField>();
        fields.add(new Field("persons_id", FieldType.Integer, true));
        fields.add(new Field("persons_name", FieldType.String, false));
        fields.add(new Field("persons_age", FieldType.Integer, false));
        fields.add(new Field("persons_address", FieldType.String, false));

        RowData rd = new RowData();
        rd.setFieldValue("persons_id", 9);
        rd.setFieldValue("persons_name", "Murat Tatlises + Updated");
        rd.setFieldValue("persons_age", 35);
        rd.setFieldValue("persons_address", "Beyoglu Bulv + Updated");

        System.out.println("Delete the old data before post the new");
        Statement st = conn.createStatement();
        st.executeUpdate("delete from persons2 where persons_id = 9;");

        System.out.println("Insert data for update");
        st.executeUpdate("insert into persons2(persons_id, persons_name, persons_age, persons_address) values(9, 'Murat Tatlises', 34, 'Beyoglu Bulv')");

        System.out.println("Post the new data");
        updater.updateRow(fields, true, "persons_age = 34", rd);

        System.out.println("Checking inserted data");
        st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from persons2 where persons_id = 9;");
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