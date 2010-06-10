/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: StringToIntMap.java
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
public class StringToIntMap extends TwoFieldsMapBase {

    public StringToIntMap(IField srcField, IField dstField) {
        super(srcField, dstField);
    }

    public void write(RowData srcRowData, RowData destRowData) throws UnableToWriteFieldMapData {
        if (FieldType.String != srcField.getFieldType())
            throw new UnableToWriteFieldMapData("Source field is not of type String");
        
        if (FieldType.Integer != dstField.getFieldType())
            throw new UnableToWriteFieldMapData("Destination field is not of type Integer");

        String strSrcField = srcField.getFieldName();

        Object srcObj = srcRowData.getFieldValue(strSrcField);
        if (null == srcObj)
            throw new UnableToWriteFieldMapData("Source field contains null data");

        String srcFieldValue = (String) srcObj;

        int intVal = 0;
        
        try {
            intVal = Integer.parseInt(srcFieldValue);
        }
        catch (NumberFormatException numFormatEx) {
            // TODO: Log the exception here
            throw new UnableToWriteFieldMapData(numFormatEx.getMessage());
        }
        
        String strDstField = dstField.getFieldName();
        destRowData.setFieldValue(strDstField, intVal);
    }
}
