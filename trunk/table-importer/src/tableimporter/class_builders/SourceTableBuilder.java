/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: SourceTableBuilder.java
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

import java.sql.Connection;
import tableimporter.source_table.ISourceTable;
import tableimporter.source_table.DBResultSetSourceTableBuilder;
import tableimporter.utils.db.ConnectionPool;
import tableimporter.config.ConfigXml;

/**
 *
 * @author djemriza
 */
public class SourceTableBuilder {
    private ISourceTable srcTable = null;
    private ConnectionPool connPool = null;
    private ConfigXml confXml = null;

    public SourceTableBuilder() {
    }

    public void setConnectionPool(ConnectionPool connPool) {
        assert(null != connPool);
        this.connPool = connPool;
    }

    public void setConfigXml(ConfigXml confXml) {
        assert(null != confXml);
        this.confXml = confXml;
    }

    public void buildSourceTable() throws Exception {
        DBResultSetSourceTableBuilder srcTableBuilder = new DBResultSetSourceTableBuilder();

        String srcTabConnName = confXml.getSourceTableConnection();
        Connection conn = connPool.getConnection(srcTabConnName);
        if (null == conn)
            throw new Exception("Null db connection");

        srcTableBuilder.setDBConnection(conn);
        srcTableBuilder.setSQLSelect(confXml.getSourceTableSql());

        srcTableBuilder.buildResultSet();
        srcTableBuilder.buildFields();
        srcTableBuilder.buildResultSetDBSourceTable();

        this.srcTable = srcTableBuilder.getBuildResult();
    }

    public ISourceTable getSourceTable() {
        return this.srcTable;
    }

}
