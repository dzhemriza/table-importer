/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: DBTableDeleterTest.java
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
public class DBTableDeleterTest {

    public DBTableDeleterTest() {
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
     * Test of deleteRow method, of class DBTableDeleter.
     */
    @Test
    public void testDeleteRow() throws Exception {
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

        DBTableDeleter deleter = new DBTableDeleter(conn, sqlStmBld, "persons2");

        Vector<IField> fields = new Vector<IField>();
        fields.add(new Field("persons_id", FieldType.Integer, true));
        fields.add(new Field("persons_name", FieldType.String, false));
        fields.add(new Field("persons_age", FieldType.Integer, false));
        fields.add(new Field("persons_address", FieldType.String, false));

        RowData rd = new RowData();
        rd.setFieldValue("persons_id", 11);
        rd.setFieldValue("persons_name", "Huan Hu");
        rd.setFieldValue("persons_age", 34);
        rd.setFieldValue("persons_address", "Jachapone Bulv");

        System.out.println("Delete the old data before post the new");
        Statement st = conn.createStatement();
        st.executeUpdate("delete from persons2 where persons_id = 11;");

        System.out.println("Insert data for update");
        st.executeUpdate("insert into persons2(persons_id, persons_name, persons_age, persons_address) values(11, 'Huan Hu', 34, 'Jachapone Bulv')");

        System.out.println("Post delete");
        deleter.deleteRow(fields, true, "", rd);

        System.out.println("Checking inserted data");
        st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from persons2 where persons_id = 11;");
        while (rs.next()) {
            fail("You never enter here!");
        }

        System.out.println("Test is finished");
        conn.close();
    }

    @Test
    public void testDeleteRowNonKeyFieldsAsWhere() throws Exception {
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

        DBTableDeleter deleter = new DBTableDeleter(conn, sqlStmBld, "persons2");

        Vector<IField> fields = new Vector<IField>();
        fields.add(new Field("persons_id", FieldType.Integer, true));
        fields.add(new Field("persons_name", FieldType.String, false));
        fields.add(new Field("persons_age", FieldType.Integer, false));
        fields.add(new Field("persons_address", FieldType.String, false));

        RowData rd = new RowData();
        rd.setFieldValue("persons_id", 11);
        rd.setFieldValue("persons_name", "Huan Hu");
        rd.setFieldValue("persons_age", 34);
        rd.setFieldValue("persons_address", "Jachapone Bulv");

        System.out.println("Delete the old data before post the new");
        Statement st = conn.createStatement();
        st.executeUpdate("delete from persons2 where persons_id = 11;");

        System.out.println("Insert data for update");
        st.executeUpdate("insert into persons2(persons_id, persons_name, persons_age, persons_address) values(11, 'Huan Hu', 34, 'Jachapone Bulv')");

        System.out.println("Post delete");
        deleter.deleteRow(fields, false, "", rd);

        System.out.println("Checking inserted data");
        st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from persons2 where persons_id = 11;");
        while (rs.next()) {
            fail("You never enter here!");
        }

        System.out.println("Test is finished");
        conn.close();
    }

    @Test
    public void testDeleteRowNonKeyFieldsAsWhereAndWhere() throws Exception {
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

        DBTableDeleter deleter = new DBTableDeleter(conn, sqlStmBld, "persons2");

        Vector<IField> fields = new Vector<IField>();
        fields.add(new Field("persons_id", FieldType.Integer, true));
        fields.add(new Field("persons_name", FieldType.String, false));
        fields.add(new Field("persons_age", FieldType.Integer, false));
        fields.add(new Field("persons_address", FieldType.String, false));

        RowData rd = new RowData();
        rd.setFieldValue("persons_id", 11);
        rd.setFieldValue("persons_name", "Huan Hu");
        rd.setFieldValue("persons_age", 34);
        rd.setFieldValue("persons_address", "Jachapone Bulv");

        System.out.println("Delete the old data before post the new");
        Statement st = conn.createStatement();
        st.executeUpdate("delete from persons2 where persons_id = 11;");

        System.out.println("Insert data for update");
        st.executeUpdate("insert into persons2(persons_id, persons_name, persons_age, persons_address) values(11, 'Huan Hu', 34, 'Jachapone Bulv')");

        System.out.println("Post delete");
        deleter.deleteRow(fields, false, "persons_id = 11", rd);

        System.out.println("Checking inserted data");
        st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from persons2 where persons_id = 11;");
        while (rs.next()) {
            fail("You never enter here!");
        }

        System.out.println("Test is finished");
        conn.close();
    }


}