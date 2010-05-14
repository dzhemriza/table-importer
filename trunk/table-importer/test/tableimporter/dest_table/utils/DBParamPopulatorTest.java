/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: DBParamPopulatorTest.java
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

package tableimporter.dest_table.utils;

import java.net.MalformedURLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tableimporter.collections.RowData;
import tableimporter.fields.IField;
import tableimporter.fields.Field;
import tableimporter.fields.FieldType;
import tableimporter.utils.db.*;

/**
 *
 * @author djemriza
 */
public class DBParamPopulatorTest {

    public DBParamPopulatorTest() {
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

    private Vector<IField> createTestFields() {
        Vector<IField> result = new Vector<IField>();

        result.add(new Field("persons_name", FieldType.String, false));
        result.add(new Field("persons_id", FieldType.Integer, true));
        result.add(new Field("persons_age", FieldType.Integer, false));
        result.add(new Field("persons_address", FieldType.String, false));

        return result;
    }

    private RowData createRowData() {
        RowData rd = new RowData();
        rd.setFieldValue("persons_id", 7);
        rd.setFieldValue("persons_name", "Solomon Kane");
        rd.setFieldValue("persons_age", 78);
        rd.setFieldValue("persons_address", "wood str.");
        return rd;
    }

    @Test
    public void testPopulateParametersFieldsOrder() throws ClassNotFoundException, SQLException, MalformedURLException, InstantiationException, IllegalAccessException {
        System.out.println("Warning: The test code is using hard coded paths to databases if you are running this test code on another machine please change the hard coded paths!");

        Vector<IField> fields = createTestFields();
        RowData rd = createRowData();

        String driverFileName = "file:/C:/temp/unit_tests/mysql-connector-java-5.1.10-bin.jar";
        String strClassName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/test";
        String user = "root";
        String pass = "root";
        DBConnectionBuilder instance = new DBConnectionBuilder();
        Connection conn = instance.buildConnection(driverFileName, strClassName, url, user, pass);
        assertNotNull(conn);

        // delete first old id 7
        Statement st = conn.createStatement();
        st.executeUpdate("delete from persons2 where persons_id = 7;");

        PreparedStatement pst = conn.prepareStatement("insert into persons2(persons_name, persons_id, persons_age, persons_address) values(?, ?, ?, ?)");
        DBParamPopulator.populateParameters(fields, pst, rd, ParametersPopulationOrder.FieldsOrder);
        assertEquals(pst.executeUpdate(), 1);

        st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from persons2 where persons_id = 7;");
        while (rs.next()) {
            assertEquals(rs.getInt("persons_id"), (int) (Integer) rd.getFieldValue("persons_id"));
            assertEquals(rs.getString("persons_name"), (String) rd.getFieldValue("persons_name"));
            assertEquals(rs.getInt("persons_age"), (int) (Integer) rd.getFieldValue("persons_age"));
            assertEquals(rs.getString("persons_address"), (String) rd.getFieldValue("persons_address"));
        }

        conn.close();
    }

    @Test
    public void testPopulateParametersFieldsKeyFieldsFirst() throws Exception {
        System.out.println("Warning: The test code is using hard coded paths to databases if you are running this test code on another machine please change the hard coded paths!");

        Vector<IField> fields = createTestFields();
        RowData rd = createRowData();

        String driverFileName = "file:/C:/temp/unit_tests/mysql-connector-java-5.1.10-bin.jar";
        String strClassName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/test";
        String user = "root";
        String pass = "root";
        DBConnectionBuilder instance = new DBConnectionBuilder();
        Connection conn = instance.buildConnection(driverFileName, strClassName, url, user, pass);
        assertNotNull(conn);

        // delete first old id 7
        Statement st = conn.createStatement();
        st.executeUpdate("delete from persons2 where persons_id = 7;");

        PreparedStatement pst = conn.prepareStatement("insert into persons2(persons_id, persons_name, persons_age, persons_address) values(?, ?, ?, ?)");
        DBParamPopulator.populateParameters(fields, pst, rd, ParametersPopulationOrder.KeyFieldsFirst);
        assertEquals(pst.executeUpdate(), 1);

        st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from persons2 where persons_id = 7;");
        while (rs.next()) {
            assertEquals(rs.getInt("persons_id"), (int) (Integer) rd.getFieldValue("persons_id"));
            assertEquals(rs.getString("persons_name"), (String) rd.getFieldValue("persons_name"));
            assertEquals(rs.getInt("persons_age"), (int) (Integer) rd.getFieldValue("persons_age"));
            assertEquals(rs.getString("persons_address"), (String) rd.getFieldValue("persons_address"));
        }

        conn.close();
    }

    @Test
    public void testPopulateParametersFieldsKeyFieldsLast() throws Exception {
        System.out.println("Warning: The test code is using hard coded paths to databases if you are running this test code on another machine please change the hard coded paths!");

        Vector<IField> fields = createTestFields();
        RowData rd = createRowData();

        String driverFileName = "file:/C:/temp/unit_tests/mysql-connector-java-5.1.10-bin.jar";
        String strClassName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/test";
        String user = "root";
        String pass = "root";
        DBConnectionBuilder instance = new DBConnectionBuilder();
        Connection conn = instance.buildConnection(driverFileName, strClassName, url, user, pass);
        assertNotNull(conn);

        // delete first old id 7
        Statement st = conn.createStatement();
        st.executeUpdate("delete from persons2 where persons_id = 7;");

        PreparedStatement pst = conn.prepareStatement("insert into persons2(persons_name, persons_age, persons_address, persons_id) values(?, ?, ?, ?)");
        DBParamPopulator.populateParameters(fields, pst, rd, ParametersPopulationOrder.KeyFieldsLast);
        assertEquals(pst.executeUpdate(), 1);

        st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from persons2 where persons_id = 7;");
        while (rs.next()) {
            assertEquals(rs.getInt("persons_id"), (int) (Integer) rd.getFieldValue("persons_id"));
            assertEquals(rs.getString("persons_name"), (String) rd.getFieldValue("persons_name"));
            assertEquals(rs.getInt("persons_age"), (int) (Integer) rd.getFieldValue("persons_age"));
            assertEquals(rs.getString("persons_address"), (String) rd.getFieldValue("persons_address"));
        }

        conn.close();
    }

    @Test
    public void testPopulateParametersFieldsOnlyKeyFields() throws Exception {
        System.out.println("Warning: The test code is using hard coded paths to databases if you are running this test code on another machine please change the hard coded paths!");

        Vector<IField> fields = createTestFields();
        RowData rd = createRowData();

        String driverFileName = "file:/C:/temp/unit_tests/mysql-connector-java-5.1.10-bin.jar";
        String strClassName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/test";
        String user = "root";
        String pass = "root";
        DBConnectionBuilder instance = new DBConnectionBuilder();
        Connection conn = instance.buildConnection(driverFileName, strClassName, url, user, pass);
        assertNotNull(conn);

        // delete first old id 7
        Statement st = conn.createStatement();
        st.executeUpdate("delete from persons2 where persons_id = 7;");

        PreparedStatement pst = conn.prepareStatement("insert into persons2(persons_id) values(?)");
        DBParamPopulator.populateParameters(fields, pst, rd, ParametersPopulationOrder.OnlyKeyFields);
        assertEquals(pst.executeUpdate(), 1);

        st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from persons2 where persons_id = 7;");
        while (rs.next()) {
            assertEquals(rs.getInt("persons_id"), (int) (Integer) rd.getFieldValue("persons_id"));
        }

        conn.close();
    }

}