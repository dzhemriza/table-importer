/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: StringCatMap.java
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

package tableimporter.fieldmapping;

import tableimporter.fields.IField;
import tableimporter.fields.FieldType;
import tableimporter.collections.RowData;

/**
 *
 * @author djemriza
 */
public class StringCatMap extends TwoFieldsMapBase {

    public StringCatMap(IField srcField, IField dstField) {
        super(srcField, dstField);
    }

    public void write(RowData srcRowData, RowData destRowData) throws UnableToWriteFieldMapData {
        if (FieldType.String != srcField.getFieldType())
            throw new UnableToWriteFieldMapData("Source field is not string type");
        if (FieldType.String != dstField.getFieldType())
            throw new UnableToWriteFieldMapData("Destination field is not string type");

        try {
            String strSrc = extractString(srcRowData, srcField);
            String strDst = extractString(destRowData, dstField);

            destRowData.setFieldValue(dstField.getFieldName(), strDst + strSrc);
        }
        catch (Exception ex) {
            // TODO: log the exception here
            throw new UnableToWriteFieldMapData("Exception reading data from RowData: " + ex.getMessage());
        }
    }

    private String extractString(RowData rd, IField fld) throws Exception {
        String result = "";

        String fldName = fld.getFieldName();
        Object val = rd.getFieldValue(fldName);

        if (null != val) {
            if (val instanceof String) {
                result = (String) val;
            }
            else {
                throw new Exception("Object not a string");
            }
        }

        return result;
    }

}
