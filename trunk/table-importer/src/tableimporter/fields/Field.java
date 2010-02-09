/*
 * File Name: Field.java
 * @author djemriza
 */

package tableimporter.fields;

/**
 *
 * @author djemriza
 */
public class Field implements IField {
    protected String strFieldName;
    protected FieldType fieldType;
    protected int fieldDataIndex;

    /**
     * Constructor of a Field class
     * @param fieldName
     * @param fieldType
     * @param fieldDataIndex
     */
    public Field(String fieldName, FieldType fieldType, int fieldDataIndex) {
        this.strFieldName = fieldName;
        this.fieldType = fieldType;
        this.fieldDataIndex = fieldDataIndex;
    }

    /**
     * Returns fields name
     * @return
     */
    public String getFieldName() {
        return strFieldName;
    }

    /**
     * Returns field type
     * @return
     */
    public FieldType getFieldType() {
        return fieldType;
    }

    /**
     * Returns field data index
     * @return
     */
    public int getDataIndex() {
        return fieldDataIndex;
    }
}
