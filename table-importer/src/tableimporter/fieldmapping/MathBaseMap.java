/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: MathBaseMap.java
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
import tableimporter.fields.FieldUtils;
import tableimporter.fields.FieldType;
import tableimporter.collections.RowData;
import org.apache.log4j.Logger;

/**
 *
 * @author djemriza
 */
public abstract class MathBaseMap extends TwoFieldsMapBase {

    public MathBaseMap(IField srcField, IField dstField) {
        super(srcField, dstField);
    }

    private void checkAllConstrains(RowData srcRowData, RowData destRowData) throws UnableToWriteFieldMapData {
        if (!FieldUtils.isNumericType(srcField.getFieldType()))
            throw new UnableToWriteFieldMapData("Source field is not of numeric type - " + srcField.getFieldType().toString());
        if (!FieldUtils.isNumericType(dstField.getFieldType()))
            throw new UnableToWriteFieldMapData("Destination field is not of numeric type - " + dstField.getFieldType().toString());
        if (srcField.getFieldType() != dstField.getFieldType())
            throw new UnableToWriteFieldMapData("Source field and destination field are not from same numeric type");

        Object srcVal = srcRowData.getFieldValue(srcField.getFieldName());
        Object dstVal = destRowData.getFieldValue(dstField.getFieldName());

        if (null == srcVal)
            throw new UnableToWriteFieldMapData("Source field do not contains data");
        if (null == dstVal)
            throw new UnableToWriteFieldMapData("Destination field do not contains data");

        if (!(srcVal instanceof Number))
            throw new UnableToWriteFieldMapData("Source field do not contains numeric data");

        if (!(dstVal instanceof Number))
            throw new UnableToWriteFieldMapData("Destination field do not contains numeric data");
    }

    public void write(RowData srcRowData, RowData destRowData) throws UnableToWriteFieldMapData {
        checkAllConstrains(srcRowData, destRowData);

        Object srcVal = srcRowData.getFieldValue(srcField.getFieldName());
        assert(null != srcVal);
        assert(srcVal instanceof Number);
        Number srcNum = (Number) srcVal;

        Object dstVal = destRowData.getFieldValue(dstField.getFieldName());
        assert(null != dstVal);
        assert(dstVal instanceof Number);
        Number dstNum = (Number) dstVal;

        if (FieldType.Integer != dstField.getFieldType() &&
            FieldType.Float == dstField.getFieldType() &&
            FieldType.Double == dstField.getFieldType())
            throw new UnableToWriteFieldMapData("Unknown field type");

        try {
            if (FieldType.Integer == dstField.getFieldType()) {
                int val = operateInt(srcNum.intValue(), dstNum.intValue());
                destRowData.setFieldValue(dstField.getFieldName(), val);
            }
            else if (FieldType.Float == dstField.getFieldType()) {
                float val = operateFloat(srcNum.floatValue(), dstNum.floatValue());
                destRowData.setFieldValue(dstField.getFieldName(), val);
            }
            else if (FieldType.Double == dstField.getFieldType()) {
                double val = operateDouble(srcNum.doubleValue(), dstNum.doubleValue());
                destRowData.setFieldValue(dstField.getFieldName(), val);
            }
        } catch (Exception ex) {
            Logger logger = Logger.getLogger("tableimporter.fieldmapping");

            logger.error(ex.getMessage());
            logger.debug("Full stack trace of exception:", ex);

            throw new UnableToWriteFieldMapData("Inner exception: " + ex.getMessage());
        }
    }

    protected abstract int operateInt(int src, int dst) throws Exception;

    protected abstract float operateFloat(float src, float dst) throws Exception;

    protected abstract double operateDouble(double src, double dst) throws Exception;

}
