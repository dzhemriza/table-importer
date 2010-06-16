/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: AutoNumberMap.java
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
public class AutoNumberMap implements IFieldMap {
    protected IField dstField = null;
    protected int nextValue = 0;
    protected int incValue = 0;

    public AutoNumberMap(IField dstField, int initialValue, int incValue) {
        this.dstField = dstField;
        assert(null != this.dstField);
        this.nextValue = initialValue;
        this.incValue = incValue;
    }

    public void write(RowData srcRowData, RowData destRowData) throws UnableToWriteFieldMapData {
        if (FieldType.Integer != dstField.getFieldType())
            throw new UnableToWriteFieldMapData("Destination field should be of integer type but receive " + dstField.getFieldType().toString());

        String strFieldName = dstField.getFieldName();

        destRowData.setFieldValue(strFieldName, nextValue);

        nextValue += incValue;
    }
}
