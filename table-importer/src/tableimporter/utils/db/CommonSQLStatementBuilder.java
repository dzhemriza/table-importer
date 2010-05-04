/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: CommonSQLStatementBuilder.java
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

package tableimporter.utils.db;

/**
 *
 * @author djemriza
 */
public class CommonSQLStatementBuilder implements ISQLStatementBuilder {

    public String buildParametricInsertStatement(String tableName, String[] fields) {
        assert(0 < fields.length);
        assert(!tableName.isEmpty());

        StringBuilder sb = new StringBuilder();
        sb.append("insert into ");
        sb.append(tableName);

        sb.append("(");
        for (int i = 0; i < fields.length; ++i) {
            assert((null != fields[i]) && (!fields[i].isEmpty()));

            if (0 != i) {
                sb.append(", ");
            }

            sb.append(fields[i]);
        }
        sb.append(")");

        sb.append(" values");

        sb.append("(");
        for (int i = 0; i < fields.length; ++i) {
            if (0 != i) {
                sb.append(", ");
            }

            sb.append("?");
        }
        sb.append(");");

        return sb.toString();
    }

    public String buildParametricUpdateStatement(String tableName, String[] fields, String where) {
        assert(0 < fields.length);
        assert(!tableName.isEmpty());
        
        StringBuilder sb = new StringBuilder();

        sb.append("update ");
        sb.append(tableName);
        addUpdateFields(sb, fields);

        if (!where.isEmpty()) {
            sb.append(" where ");
            sb.append(where);
        }
        sb.append(";");
        
        return sb.toString();
    }

    public String buildParametricUpdateStatement(String tableName, String[] keyFields, String[] fields, String where) {
        assert(0 < fields.length);
        assert(0 < keyFields.length);
        assert(!tableName.isEmpty());

        StringBuilder sb = new StringBuilder();
        sb.append("update ");
        sb.append(tableName);
        addUpdateFields(sb, fields);

        sb.append(" where (");
        for (int i = 0; i < keyFields.length; ++i) {
            if (0 != i) {
                sb.append(" and ");
            }
            sb.append(keyFields[i]);
            sb.append(" = ?");
        }
        sb.append(")");

        if (!where.isEmpty()) {
            sb.append(" and (");
            sb.append(where);
            sb.append(")");
        }
        sb.append(";");

        return sb.toString();
    }

    public String buildParametricDeleteStatement(String tableName, String[] deleteWhereFields, String additionalWhere) {
        assert(!tableName.isEmpty());

        StringBuilder sb = new StringBuilder();

        sb.append("delete from ");
        sb.append(tableName);

        if (!additionalWhere.isEmpty() || 0 < deleteWhereFields.length) {
            sb.append(" where ");
        }

        if (0 < deleteWhereFields.length) {
            sb.append("(");
            addFields(sb, deleteWhereFields, " and ");
            sb.append(")");
        }

        if (0 < deleteWhereFields.length && !additionalWhere.isEmpty()) {
            sb.append(" and ");
        }

        if (!additionalWhere.isEmpty()) {
            sb.append("(");
            sb.append(additionalWhere);
            sb.append(")");
        }

        sb.append(";");

        return sb.toString();
    }

    private void addUpdateFields(StringBuilder sb, String[] fields) {
        assert(0 < fields.length);
        assert(null != sb);

        sb.append(" set ");

        addFields(sb, fields, ", ");
    }

    private void addFields(StringBuilder sb, String[] fields, String separator) {
        assert(0 < fields.length);
        assert(null != sb);
        
        for (int i = 0; i < fields.length; ++i) {
            if (0 != i) {
                sb.append(separator);
            }
            sb.append(fields[i]);
            sb.append(" = ?");
        }
    }
    
}
