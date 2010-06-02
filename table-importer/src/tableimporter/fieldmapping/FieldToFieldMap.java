/*
 * table-importer
 * Imports tabled data from any source to any destination
 * 
 * File Name: FieldToFieldMap.java
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
import tableimporter.collections.RowData;

/**
 *
 * @author djemriza
 */
public class FieldToFieldMap implements IFieldMap {
    protected IField srcField = null;
    protected IField dstField = null;

    /**
     * Constructor of a class FieldToFieldMap
     * @param srcField
     * @param dstField
     */
    public FieldToFieldMap(IField srcField, IField dstField) {
        this.srcField = srcField;
        this.dstField = dstField;
    }

    public void write(RowData srcRowData, RowData destRowData) throws UnableToWriteFieldMapData {
        Object srcValue = null;
        String srcFieldName = srcField.getFieldName();
        srcValue = srcRowData.getFieldValue(srcFieldName);

        String dstFieldName = dstField.getFieldName();
        destRowData.setFieldValue(dstFieldName, srcValue);
    }
}
