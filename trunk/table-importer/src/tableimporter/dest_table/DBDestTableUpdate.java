/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: DBDestTableUpdate.java
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

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;
import tableimporter.fields.IField;
import tableimporter.collections.RowData;
import tableimporter.utils.db.ISQLStatementBuilder;
import tableimporter.dest_table.db_operations.DBTableUpdater;
import org.apache.log4j.Logger;

/**
 *
 * @author djemriza
 */
public class DBDestTableUpdate extends DBDestTableBase {
    protected boolean useKeyFieldsAsWhere;
    protected String where = "";
    protected DBTableUpdater rowUpdater = null;

    public DBDestTableUpdate(Connection dbConn, ArrayList<IField> fields, String tableName, ISQLStatementBuilder sqlStatementBuilder, boolean useKeyFieldsAsWhere, String where) {
        super(dbConn, fields, tableName, sqlStatementBuilder);
        this.useKeyFieldsAsWhere = useKeyFieldsAsWhere;
        this.where = where;

        rowUpdater = new DBTableUpdater(dbConn, sqlStatementBuilder, tableName);
    }

    public void postRecord(RowData rowData) {
        Logger logger = Logger.getLogger("tableimporter.dest_table");

        try {
            int updateCount = rowUpdater.updateRow(fields, useKeyFieldsAsWhere, where, rowData);
            
            logger.info("DBDestTableUpdate.postRecord: Insert Count = " + ((Integer) updateCount).toString());
        }
        catch (SQLException ex) {
            logger.error(ex.getMessage());
            logger.debug("Full stack trace of exception:", ex);
        }
    }
}
