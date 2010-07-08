/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: ConstDataMap.java
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

import java.util.TreeMap;
import tableimporter.fields.IField;
import tableimporter.fields.Field;
import tableimporter.fields.FieldType;
import tableimporter.collections.RowData;

/**
 *
 * @author djemriza
 */
public class ConstDataMap implements IFieldMap {
    private IField dstField;
    private TreeMap<FieldType, IFieldMap> mapConversions;
    private RowData privateRowData;

    public ConstDataMap(IField dstField, String constValue) {
        this.dstField = dstField;

        Field dummyField = new Field("DUMMY_FIELD", FieldType.String, false);

        mapConversions = new TreeMap<FieldType, IFieldMap>();
        mapConversions.put(FieldType.String, new FieldToFieldMap(dummyField, dstField));
        mapConversions.put(FieldType.Integer, new StringToIntMap(dummyField, dstField));
        mapConversions.put(FieldType.Float, new StringToFloatMap(dummyField, dstField));
        mapConversions.put(FieldType.Double, new StringToDoubleMap(dummyField, dstField));

        privateRowData = new RowData();
        privateRowData.setFieldValue(dummyField.getFieldName(), constValue);
    }

    public void write(RowData srcRowData, RowData destRowData) throws UnableToWriteFieldMapData {
        if (!mapConversions.containsKey(dstField.getFieldType()))
            throw new UnableToWriteFieldMapData("Unsuported field type " + dstField.getFieldType().toString());

        IFieldMap map = mapConversions.get(dstField.getFieldType());
        assert(null != map);

        map.write(privateRowData, destRowData);
    }

}
