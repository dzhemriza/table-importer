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

package tableimporter.dest_table.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;
import tableimporter.collections.RowData;
import tableimporter.fields.IField;

/**
 *
 * @author djemriza
 */
public class DBParamPopulator {

    public static void populateParameters(Vector<IField> fields, PreparedStatement ps, RowData rd, ParametersPopulationOrder populationOrder) throws SQLException {
        if (ParametersPopulationOrder.FieldsOrder == populationOrder) {
            for (int i = 0; i < fields.size(); ++i) {
                IField field = fields.get(i);
                String strField = field.getFieldName();
                Object fieldValue = rd.getFieldValue(strField);
                ps.setObject(i + 1, fieldValue);
            }
        }
        else if (ParametersPopulationOrder.KeyFieldsFirst == populationOrder) {
            int position = 1;

            for (int i = 0; i < fields.size(); ++i) {
                IField field = fields.get(i);

                if (field.isKeyField()) {
                    String strField = field.getFieldName();
                    Object fieldValue = rd.getFieldValue(strField);
                    ps.setObject(position, fieldValue);
                    ++position;
                }
            }

            for (int i = 0; i < fields.size(); ++i) {
                IField field = fields.get(i);

                if (!field.isKeyField()) {
                    String strField = field.getFieldName();
                    Object fieldValue = rd.getFieldValue(strField);
                    ps.setObject(position, fieldValue);
                    ++position;
                }
            }
        }
        else if (ParametersPopulationOrder.KeyFieldsLast == populationOrder) {
            int position = 1;

            for (int i = 0; i < fields.size(); ++i) {
                IField field = fields.get(i);

                if (!field.isKeyField()) {
                    String strField = field.getFieldName();
                    Object fieldValue = rd.getFieldValue(strField);
                    ps.setObject(position, fieldValue);
                    ++position;
                }
            }

            for (int i = 0; i < fields.size(); ++i) {
                IField field = fields.get(i);

                if (field.isKeyField()) {
                    String strField = field.getFieldName();
                    Object fieldValue = rd.getFieldValue(strField);
                    ps.setObject(position, fieldValue);
                    ++position;
                }
            }
        }
        else if (ParametersPopulationOrder.OnlyKeyFields == populationOrder) {
            int position = 1;
            
            for (int i = 0; i < fields.size(); ++i) {
                IField field = fields.get(i);

                if (field.isKeyField()) {
                    String strField = field.getFieldName();
                    Object fieldValue = rd.getFieldValue(strField);
                    ps.setObject(position, fieldValue);
                    ++position;
                }
            }
        }
        else {
            assert(false);
        }
    }

}
