/*
 * File Name: FieldToFieldMap.java
 * @author djemriza
 */

package tableimporter.fieldmapping;

import tableimporter.fields.IField;
import tableimporter.collections.RowData;

/**
 *
 * @author djemriza
 */
public class FieldToFieldMap {
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

    public void write(RowData srcRowData, RowData destRowData) {
        Object srcValue = null;
        String srcFieldName = srcField.getFieldName();
        srcValue = srcRowData.getFieldValue(srcFieldName);

        String dstFieldName = dstField.getFieldName();
        destRowData.setFieldValue(dstFieldName, srcValue);
    }
}
