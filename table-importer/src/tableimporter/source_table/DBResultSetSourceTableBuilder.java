/*
 * table-importer
 * Imports tabled data from any source to any destination
 * FileName: DBResultSetSourceTableBuilder.java
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

package tableimporter.source_table;

import java.util.Vector;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.SQLException;
import tableimporter.fields.*;
import tableimporter.fields.db.FieldsDBUtils;

/**
 *
 * @author djemriza
 */
public class DBResultSetSourceTableBuilder {
    private Connection dbConn = null;
    private String sqlSelect = "";
    private ResultSet resultSet = null;
    private Vector<IField> fields = new Vector<IField>();
    private DBResultSetSourceTable result = null;

    public DBResultSetSourceTableBuilder() {
    }

    private ResultSet queryDatabase(String sql) throws SQLException {
        Statement st = dbConn.createStatement();

        resultSet = st.executeQuery(sql);

        return resultSet;
    }

    public void setDBConnection(Connection dbConn) {
        this.dbConn = dbConn;
    }

    public void setSQLSelect(String sqlSelect) {
        this.sqlSelect = sqlSelect;
    }

    public void buildResultSet() throws SQLException {
        assert((null != dbConn) && (null == resultSet) && (0 < sqlSelect.length()));

        resultSet = queryDatabase(sqlSelect);
    }

    public void buildFields() throws SQLException {
        assert(null != resultSet);

        ResultSetMetaData rsmd = resultSet.getMetaData();

        for (int i = 1; i <= rsmd.getColumnCount(); ++i) {
            String fldName = rsmd.getColumnName(i);
            int dbFieldType = rsmd.getColumnType(i);
            FieldType fldType = FieldsDBUtils.dbFieldToMyField(dbFieldType);

            Field newField = new Field(fldName, fldType, false);

            fields.add(newField);
        }
    }

    public void buildResultSetDBSourceTable() {
        assert(null == result);
        assert(null != resultSet);
        assert(0 < fields.size());

        result = new DBResultSetSourceTable(resultSet, fields);
    }

    public DBResultSetSourceTable getBuildResult() {
        assert(null != result);
        return result;
    }
    
}
