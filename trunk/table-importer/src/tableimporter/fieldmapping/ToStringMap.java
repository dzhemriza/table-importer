/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: ToStringMap.java
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
import tableimporter.fieldmapinfo.FieldMapInfo;

/**
 *
 * @author djemriza
 */
public class ToStringMap extends TwoFieldsMapBase {

    public static IFieldMap createFromData(FieldMapInfo info, IField srcField, IField dstField) {
        return new ToStringMap(srcField, dstField);
    }

    public ToStringMap(IField srcField, IField dstField) {
        super(srcField, dstField);
    }

    public void write(RowData srcRowData, RowData destRowData) throws UnableToWriteFieldMapData {
        if (FieldType.String != dstField.getFieldType()) {
            throw new UnableToWriteFieldMapData("Destination field is not from type of String");
        }

        String strSrcField = srcField.getFieldName();
        String strDstField = dstField.getFieldName();

        Object fieldValue = srcRowData.getFieldValue(strSrcField);

        if (null == fieldValue) {
            throw new UnableToWriteFieldMapData("Source field contains null data");
        }

        destRowData.setFieldValue(strDstField, fieldValue.toString());
    }
}
