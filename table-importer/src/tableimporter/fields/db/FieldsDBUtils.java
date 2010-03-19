/*
 * table-importer
 * Imports tabled data from any source to any destination
 * FileName: FieldsDBUtils.java
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

package tableimporter.fields.db;

import java.sql.Types;
import tableimporter.fields.FieldType;

/**
 *
 * @author djemriza
 */
public class FieldsDBUtils {

    public static FieldType dbFieldToMyField(int dbFieldType) {
        FieldType result = FieldType.Other;

        switch (dbFieldType) {
            case Types.VARCHAR: result = FieldType.String; break;
            case Types.INTEGER: result = FieldType.Integer; break;
            case Types.FLOAT: result = FieldType.Float; break;
            case Types.DOUBLE: result = FieldType.Double; break;
            case Types.DATE: result = FieldType.Date; break;
            case Types.TIME: result = FieldType.Time; break;
            case Types.TIMESTAMP: result = FieldType.DateTime; break;
        }

        return result;
    }

}
