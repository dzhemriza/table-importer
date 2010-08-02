/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: DBDestTableUpdateInsertTest.java
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
public class DBDestTableUpdateInsertTest {

    public DBDestTableUpdateInsertTest() {
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
     * Test of postRecord method, of class DBDestTableUpdateInsert.
     */
    @Test
    public void testPostRecord() throws ClassNotFoundException, SQLException, MalformedURLException, InstantiationException, IllegalAccessException {
        System.out.println("DBDestTableUpdateInsertTest.testPostRecord - Warning: The test code is using hard coded paths to databases if you are running this test code on another machine please change the hard coded paths!");
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
        int updateCount = st.executeUpdate("delete from persons2 where persons_id = 3 or persons_id = 4;");
        System.out.println("testPostRecord - Delete Update count " + ((Integer)updateCount).toString());
        // Create new rows in database
        st = conn.createStatement();
        // we need only one record to be inserted in db because and the next
        // record with id 4 we will try to insert into db
        updateCount = st.executeUpdate("insert into persons2(persons_id, persons_name, persons_age, persons_address) values(3, 'Cristof Lambert', 23, 'Sea way');");
        System.out.println("testPostRecord - Update count " + ((Integer)updateCount).toString());

        ArrayList<IField> fields = new ArrayList<IField>();
        fields.add(new Field("persons_id", FieldType.Integer, true));
        fields.add(new Field("persons_name", FieldType.String, false));
        fields.add(new Field("persons_age", FieldType.Integer, false));
        fields.add(new Field("persons_address", FieldType.String, false));

        RowData[] rowDatas = new RowData[2];
        rowDatas[0] = new RowData();
        rowDatas[0].setFieldValue("persons_id", 3);
        rowDatas[0].setFieldValue("persons_name", "Cristof Lambert + Updated");
        rowDatas[0].setFieldValue("persons_age", 24);
        rowDatas[0].setFieldValue("persons_address", "Sea way + Updated");

        rowDatas[1] = new RowData();
        rowDatas[1].setFieldValue("persons_id", 4);
        rowDatas[1].setFieldValue("persons_name", "Amber Mahony + Inserted");
        rowDatas[1].setFieldValue("persons_age", 19);
        rowDatas[1].setFieldValue("persons_address", "Sea way + Inserted");

        ISQLStatementBuilder statementBuilder = new CommonSQLStatementBuilder();

        DBDestTableUpdateInsert updateInsert = new DBDestTableUpdateInsert(conn, fields, "persons2", statementBuilder);

        System.out.println("testPostRecord - Begin posting records");
        for (int i = 0; i < rowDatas.length; ++i) {
            updateInsert.postRecord(rowDatas[i]);
        }
        System.out.println("testPostRecord - End posting records");

        System.out.println("testPostRecord - We need to make a query to check is the update is ok");
        st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from persons2 where persons_id = 3 or persons_id = 4 order by persons_id asc;");
        int recordsCount = 0;

        while (rs.next()) {
            ++recordsCount;
            if (recordsCount == 1)
            {
                assertEquals(rs.getInt("persons_id"), 3);
                assertEquals(rs.getString("persons_name"), "Cristof Lambert + Updated");
                assertEquals(rs.getInt("persons_age"), 24);
                assertEquals(rs.getString("persons_address"), "Sea way + Updated");
            }
            else if (recordsCount == 2) {
                assertEquals(rs.getInt("persons_id"), 4);
                assertEquals(rs.getString("persons_name"), "Amber Mahony + Inserted");
                assertEquals(rs.getInt("persons_age"), 19);
                assertEquals(rs.getString("persons_address"), "Sea way + Inserted");
            }
            else {
                fail("you need never get here");
            }
        }

        assertEquals(recordsCount, 2);

        System.out.println("DBDestTableUpdateTest.testPostRecordKeyFieldsAsNotWhere - closing db connection");
        conn.close();
        System.out.println("DBDestTableUpdateTest.testPostRecordKeyFieldsAsNotWhere - test complete");
    }

}