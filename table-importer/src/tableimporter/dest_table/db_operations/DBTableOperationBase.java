/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: DBTableOperationBase.java
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

import java.sql.Connection;
import tableimporter.utils.db.ISQLStatementBuilder;

/**
 *
 * @author djemriza
 */
public abstract class DBTableOperationBase {
    protected Connection dbConn = null;
    protected ISQLStatementBuilder sqlStatementBuilder = null;
    protected String tableName = "";

    public DBTableOperationBase(Connection dbConn, ISQLStatementBuilder sqlStatementBuilder, String tableName) {
        this.dbConn = dbConn;
        this.sqlStatementBuilder = sqlStatementBuilder;
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public ISQLStatementBuilder getStatementBuilder() {
        return sqlStatementBuilder;
    }

    public Connection getConnection() {
        return dbConn;
    }

}
