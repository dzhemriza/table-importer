/*
 * table-importer
 * Imports tabled data from any source to any destination
 * FileName: DBResultSetSourceTableAndBuilderTest.java
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

import java.util.Vector;
import java.util.Iterator;
import java.sql.Connection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tableimporter.source_table.DBResultSetSourceTable;
import tableimporter.source_table.DBResultSetSourceTableBuilder;
import tableimporter.utils.db.DBConnectionBuilder;
import tableimporter.fields.IField;
import tableimporter.fields.FieldType;
import tableimporter.collections.RowData;

/**
 *
 * @author djemriza
 */
public class DBResultSetSourceTableAndBuilderTest {

    public DBResultSetSourceTableAndBuilderTest() {
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

    public static void printRowData(RowData rowData) {
        Iterator<String> iterNames = rowData.getFields();
        System.out.print("  ");
        while (iterNames.hasNext()) {
            String fldName = iterNames.next();
            Object obj = rowData.getFieldValue(fldName);
            if (null != obj) {
                System.out.print(fldName + " : " + rowData.getFieldValue(fldName).toString() + "; ");
            }
            else {
                System.out.print(fldName + " : null; ");
            }
        }
        System.out.println();
    }

    @Test
    public void testDBResultSetSourceTableBuilder() throws Exception {
        System.out.println("testDBResultSetSourceTableBuilder - Warning: The test code is using hard coded paths to databases if you are running this test code on another machine please change the hard coded paths!");

        System.out.println("Phase 1: Building db connection");
        String driverFileName = "file:/C:/temp/unit_tests/mysql-connector-java-5.1.10-bin.jar";
        String strClassName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/test";
        String user = "root";
        String pass = "root";
        DBConnectionBuilder dbConnBuilder = new DBConnectionBuilder();
        Connection conn = dbConnBuilder.buildConnection(driverFileName, strClassName, url, user, pass);
        assertNotNull(conn);

        System.out.println("Phase 2: Building db source table");
        DBResultSetSourceTableBuilder dbSourceTableBuilder = new DBResultSetSourceTableBuilder();

        dbSourceTableBuilder.setDBConnection(conn);
        String sqlSelect = "Select * from persons;";
        dbSourceTableBuilder.setSQLSelect(sqlSelect);
        dbSourceTableBuilder.buildResultSet();
        dbSourceTableBuilder.buildFields();
        dbSourceTableBuilder.buildResultSetDBSourceTable();
        
        DBResultSetSourceTable dbSourceTable = dbSourceTableBuilder.getBuildResult();
        assertNotNull(dbSourceTable);

        System.out.println("Phase 3: Testing is fields are ok according to hard coded db");
        Vector<IField> fields = dbSourceTable.getFields();
        String[] fieldNames = {"persons_id", "persons_name", "persons_age", "persons_address"};
        FieldType[] fieldTypes = {FieldType.Integer, FieldType.String, FieldType.Integer, FieldType.String};

        assertEquals(fieldNames.length, fieldTypes.length);
        assertEquals(fieldNames.length, fields.size());

        for (int i = 0; i < fields.size(); ++i) {
            IField fld = fields.get(i);
            String strFieldName = fld.getFieldName();
            assertEquals(strFieldName, fieldNames[i]);
            FieldType fldTyp = fld.getFieldType();
            assertEquals(fldTyp, fieldTypes[i]);
        }

        System.out.println("Phase 4: Print result sets data and visual test of ResultSetToRowDataIterator");
        Iterator<RowData> iter = dbSourceTable.getRecords();

        while (iter.hasNext()) {
            RowData rowData = iter.next();
            printRowData(rowData);
        }

        System.out.println("Final Phase: Closing db connection and releasing pointers");
        iter = null;
        fields = null;
        dbSourceTable = null;
        dbSourceTableBuilder = null;
        conn.close();
        conn = null;
    }


}