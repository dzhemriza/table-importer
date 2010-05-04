/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: DBDestTableBase.java
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
import java.sql.Connection;
import tableimporter.fields.IField;
import tableimporter.utils.db.ISQLStatementBuilder;


/**
 *
 * @author djemriza
 */
public abstract class DBDestTableBase implements IDestTable {
    protected Vector<IField> fields = null;
    protected Connection dbConn = null;
    protected String tableName = "";
    protected ISQLStatementBuilder sqlStatementBuilder = null;

    public DBDestTableBase(Connection dbConn, Vector<IField> fields, String tableName, ISQLStatementBuilder sqlStatementBuilder) {
        this.fields = fields;
        this.dbConn = dbConn;
        this.tableName = tableName;
        this.sqlStatementBuilder = sqlStatementBuilder;
        assert((null != this.fields) && (0 < this.fields.size()));
        assert(null != this.dbConn);
        assert((null != this.tableName) && (0 < this.tableName.length()));
        assert(null != sqlStatementBuilder);
    }

    public Vector<IField> getFields() {
        return fields;
    }

}
