/*
 * table-importer
 * Imports tabled data from any source to any destination
 * 
 * File Name: IDestTable.java
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

import java.util.Vector;
import tableimporter.fields.IField;
import tableimporter.collections.RowData;

/**
 *
 * @author djemriza
 */
public interface IDestTable {

    /**
     * Method post current RowData into destination table
     * @param rowData
     */
    public void postRecord(RowData rowData);

    /**
     * Returns a collection with description of all fields
     * @return
     */
    public Vector<IField> getFields();
}
