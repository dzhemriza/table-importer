/*
 * table-importer
 * Imports tabled data from any source to any destination
 * FileName: DBResultSetSourceTable.java
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

import java.util.ArrayList;
import java.util.Iterator;
import java.sql.ResultSet;
import tableimporter.collections.RowData;
import tableimporter.fields.IField;

/**
 *
 * @author djemriza
 */
public class DBResultSetSourceTable implements ISourceTable {
    protected ResultSet resultSet = null;
    protected ArrayList<IField> fields = null;

    /**
     * Constructor of class DBResultSetSourceTable
     * @param resultSet
     * @param fields
     */
    public DBResultSetSourceTable(ResultSet resultSet, ArrayList<IField> fields) {
        this.resultSet = resultSet;
        this.fields = fields;
    }

    public Iterator<RowData> getRecords() {
        ResultSetToRowDataIterator iter = new ResultSetToRowDataIterator(resultSet);

        return iter;
    }

    public ArrayList<IField> getFields() {
        return fields;
    }

}
