/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: DestTableBuilder.java
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

package tableimporter.class_builders;

import java.util.ArrayList;
import java.util.Iterator;
import java.sql.Connection;
import tableimporter.dest_table.IDestTable;
import tableimporter.dest_table.DBDestTableInsert;
import tableimporter.dest_table.DBDestTableUpdate;
import tableimporter.dest_table.DBDestTableDelete;
import tableimporter.dest_table.DBDestTableUpdateInsert;
import tableimporter.fields.IField;
import tableimporter.config.ConfigXml;
import tableimporter.utils.db.CommonSQLStatementBuilder;
import tableimporter.utils.db.ConnectionPool;
import tableimporter.utils.db.ISQLStatementBuilder;

/**
 *
 * @author djemriza
 */
public class DestTableBuilder {
    private IDestTable destTable = null;
    private ConfigXml confXml = null;
    private ConnectionPool connPool = null;

    public DestTableBuilder() {
    }

    public void setConnectionPool(ConnectionPool connPool) {
        this.connPool = connPool;
    }

    public void setConfigXml(ConfigXml confXml) {
        this.confXml = confXml;
    }

    public IDestTable getDestinationTable() {
        return this.destTable;
    }

    public void buildDestinationTable() throws Exception {
        String strDestTableConn = confXml.getDestTableConnection();
        Connection conn = connPool.getConnection(strDestTableConn);
        if (null == conn)
            throw new Exception("Null db connection");
        
        final String destTableInsert = "insert";
        final String destTableDelete = "delete";
        final String destTableUpdate = "update";
        final String destTableUpdateInsert = "update_insert";

        String strDestTableType = confXml.getDestTableType();
        ArrayList<IField> fields = getDestTableFields();
        ISQLStatementBuilder sqlStatementBuilder = new CommonSQLStatementBuilder();
        
        if (destTableInsert.equals(strDestTableType)) {
            DBDestTableInsert insTable = new DBDestTableInsert(conn, fields, confXml.getDestTableName(), sqlStatementBuilder);
            this.destTable = insTable;
        }
        else if (destTableDelete.equals(strDestTableType)) {
            DBDestTableDelete delTable = new DBDestTableDelete(conn, fields, confXml.getDestTableName(), sqlStatementBuilder, confXml.getDestTableUseKeyFieldsAsWhere(), confXml.getDestTableSqlWhere());
            this.destTable = delTable;
        }
        else if (destTableUpdate.equals(strDestTableType)) {
            DBDestTableUpdate updateTable = new DBDestTableUpdate(conn, fields, confXml.getDestTableName(), sqlStatementBuilder, confXml.getDestTableUseKeyFieldsAsWhere(), confXml.getDestTableSqlWhere());
            this.destTable = updateTable;
        }
        else if (destTableUpdateInsert.equals(strDestTableType)) {
            DBDestTableUpdateInsert updateInsTable = new DBDestTableUpdateInsert(conn, fields, confXml.getDestTableName(), sqlStatementBuilder);
            this.destTable = updateInsTable;
        }
        else
            throw new Exception("Unknown destination table table - " + strDestTableType);
    }

    private ArrayList<IField> getDestTableFields() {
        assert(null != confXml);
        
        ArrayList<IField> fields = new ArrayList<IField>();

        Iterator<IField> itFields = confXml.getDestTableFields();
        while (itFields.hasNext()) {
            IField fld = itFields.next();
            fields.add(fld);
        }

        return fields;
    }

}
