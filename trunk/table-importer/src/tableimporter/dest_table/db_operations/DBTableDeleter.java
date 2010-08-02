/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: DBTableDeleter.java
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

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import tableimporter.fields.IField;
import tableimporter.collections.RowData;
import tableimporter.utils.db.ISQLStatementBuilder;
import tableimporter.fields.FieldUtils;
import tableimporter.dest_table.utils.DBParamPopulator;
import tableimporter.dest_table.utils.ParametersPopulationOrder;

/**
 *
 * @author djemriza
 */
public class DBTableDeleter extends DBTableOperationBase {

    public DBTableDeleter(Connection dbConn, ISQLStatementBuilder sqlStatementBuilder, String tableName) {
        super(dbConn, sqlStatementBuilder, tableName);
    }

    public int deleteRow(ArrayList<IField> fields, boolean useKeyFieldsAsWhere, String where, RowData rowData) throws SQLException {
        String sql = null;

        if (useKeyFieldsAsWhere) {
            String[] keyFieldNames = FieldUtils.keyFieldToNamesArray(fields);
            sql = sqlStatementBuilder.buildParametricDeleteStatement(tableName, keyFieldNames, where);
        }
        else {
            String[] allFieldNames = FieldUtils.toNamesArray(fields);
            sql = sqlStatementBuilder.buildParametricDeleteStatement(tableName, allFieldNames, where);
        }

        assert((null != sql) && !sql.isEmpty());

        PreparedStatement ps = dbConn.prepareStatement(sql);

        if (useKeyFieldsAsWhere) {
            DBParamPopulator.populateParameters(fields, ps, rowData, ParametersPopulationOrder.OnlyKeyFields);
        }
        else {
            DBParamPopulator.populateParameters(fields, ps, rowData, ParametersPopulationOrder.FieldsOrder);
        }

        int updateCount = ps.executeUpdate();

        return updateCount;
    }

}
