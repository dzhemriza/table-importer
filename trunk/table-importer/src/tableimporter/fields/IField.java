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
     * Returns field data index
     * @return
     */
    public int getDataIndex();
}
