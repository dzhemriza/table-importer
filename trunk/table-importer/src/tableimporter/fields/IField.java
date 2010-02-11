/*
 * File Name: IField.java
 * @author djemriza
 */

package tableimporter.fields;

/**
 *
 * @author djemriza
 */
public interface IField {

    /**
     * Returns fields name
     * @return
     */
    public String getFieldName();

    /**
     * Returns field type
     * @return
     */
    public FieldType getFieldType();

    /**
     * Method is responsible to give information is this is unique field
     * or not
     * @return
     */
    public boolean isKeyField();
}
