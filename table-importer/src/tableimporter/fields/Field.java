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
    protected boolean keyField;

    /**
     * Constructor of a Field class
     * @param fieldName
     * @param fieldType
     * @param fieldDataIndex
     */
    public Field(String fieldName, FieldType fieldType, boolean keyField) {
        this.strFieldName = fieldName;
        this.fieldType = fieldType;
        this.keyField = keyField;
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
     * Method is responsible to give information is this is unique field
     * or not
     * @return
     */
    public boolean isKeyField() {
        return keyField;
    }
}
