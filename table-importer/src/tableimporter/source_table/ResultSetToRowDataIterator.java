/*
 * table-importer
 * Imports tabled data from any source to any destination
 * FileName: ResultSetToRowDataIterator.java
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

import java.util.Iterator;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import tableimporter.collections.RowData;

/**
 *
 * @author djemriza
 */
class ResultSetToRowDataIterator implements Iterator<RowData> {
    protected ResultSet resultSet = null;

    ResultSetToRowDataIterator(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public boolean hasNext() {
        try {
            return !resultSet.isLast();
        } catch (SQLException ex) {
            // TODO: log the exception
            return false;
        }
    }

    public RowData next() {
        RowData rowData = new RowData();

        try {
            resultSet.next();
            getCurrentRowData(rowData);
        }
        catch (SQLException ex) {
            // TODO: log the exception
            return null;
        }

        return rowData;
    }

    public void getCurrentRowData(RowData rowData) throws SQLException {
        ResultSetMetaData rsMetaData = resultSet.getMetaData();

        for (int i = 1; i <= rsMetaData.getColumnCount(); ++i) {
            String strColName = rsMetaData.getColumnName(i);

            assert((null != strColName) && (0 < strColName.length()));
            Object value = resultSet.getObject(strColName);

            rowData.setFieldValue(strColName, value);
        }
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
