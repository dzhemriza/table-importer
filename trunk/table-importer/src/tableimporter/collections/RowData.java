/*
 * File Name: RowData.java
 * @author djemriza
 */

package tableimporter.collections;

import java.util.Iterator;

/**
 * Class is responsible to hold row data that will be posted to destination
 * table
 * @author djemriza
 */
public class RowData {

    protected NameValueMap mapDataHolder = new NameValueMap();

    public RowData() {
    }

    /**
     * Method sets to some specific field value, if such field is not
     * exists method creates new one
     * @param strFieldName - field name
     * @param objValue - field value
     */
    public void setFieldValue(String strFieldName, Object objValue) {
        if (mapDataHolder.isFieldExist(strFieldName)) {
            Object obj1= mapDataHolder.getFieldValue(strFieldName);
            assert(obj1 instanceof ObjectWithAttributes);
            ObjectWithAttributes objWithProps = (ObjectWithAttributes) obj1;
            objWithProps.setObjectValue(objValue);
        }
        else {
            ObjectWithAttributes objWithProps = new ObjectWithAttributes(objValue);
            mapDataHolder.setFieldValue(strFieldName, objWithProps);
        }
    }

    /**
     * Method gets for a specific field his value if there are no such field
     * in the collection so returns null
     * @param strFieldName - field name
     * @return - if finds returns non null object
     */
    public Object getFieldValue(String strFieldName) {
        Object objResult = null;

        if (mapDataHolder.isFieldExist(strFieldName)) {
            Object obj1 = mapDataHolder.getFieldValue(strFieldName);
            assert(obj1 instanceof ObjectWithAttributes);
            ObjectWithAttributes objWithProps = (ObjectWithAttributes) obj1;
            objResult = objWithProps.getObjectValue();
        }

        return objResult;
    }

    /**
     * Method returns attributes for a given field
     * @param strFieldName
     * @return
     */
    public NameValueMap getFieldAttributes(String strFieldName) {
        if (mapDataHolder.isFieldExist(strFieldName)) {
            Object obj1 = mapDataHolder.getFieldValue(strFieldName);
            assert(obj1 instanceof ObjectWithAttributes);
            ObjectWithAttributes objWithProps = (ObjectWithAttributes) obj1;
            return objWithProps.getAttributes();
        }
        
        return new NameValueMap();
    }

    /**
     * Method returns a iterator to all fields in RowData
     * @return
     */
    public Iterator<String> getFields() {
        return mapDataHolder.getFields();
    }

    /**
     * Method clears all row data
     */
    public void clearAll() {
        mapDataHolder.clearAll();
    }

}
