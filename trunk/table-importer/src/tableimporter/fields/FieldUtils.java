/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: FieldUtils.java
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

package tableimporter.fields;

import java.util.Vector;

/**
 *
 * @author djemriza
 */
public class FieldUtils {

    public static String[] toNamesArray(Vector<IField> fields) {
        assert(0 < fields.size());
        String[] fieldNames = new String[fields.size()];

        for (int i = 0; i < fields.size(); ++i) {
            IField field = fields.get(i);
            fieldNames[i] = field.getFieldName();
        }

        return fieldNames;
    }

    public static String[] keyFieldToNamesArray(Vector<IField> fields) {
        Vector<String> keyFields = new Vector<String>();

        for (int i = 0; i < fields.size(); ++i) {
            IField field = fields.get(i);
            if (field.isKeyField()) {
                keyFields.add(field.getFieldName());
            }
        }

        String[] result = new String[keyFields.size()];
        keyFields.toArray(result);

        return result;
    }

    public static String[] nonKeyFieldToNamesArray(Vector<IField> fields) {
        Vector<String> keyFields = new Vector<String>();

        for (int i = 0; i < fields.size(); ++i) {
            IField field = fields.get(i);
            if (!field.isKeyField()) {
                keyFields.add(field.getFieldName());
            }
        }

        String[] result = new String[keyFields.size()];
        keyFields.toArray(result);

        return result;
    }

    public static boolean isNumericType(FieldType ft) {
        return (FieldType.Integer == ft) ||
               (FieldType.Float == ft) ||
               (FieldType.Double == ft);
    }

}
