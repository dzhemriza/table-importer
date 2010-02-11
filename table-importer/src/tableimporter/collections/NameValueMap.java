/*
 * File Name: NameValueMap.java
 * @author djemriza
 */

package tableimporter.collections;

import java.util.TreeMap;
import java.util.Iterator;
import java.util.Set;
import tableimporter.utils.CaseInsensitiveStringComparator;

/**
 *
 * @author djemriza
 */
public class NameValueMap {

    protected TreeMap<String, Object> mapDataHolder = new TreeMap<String, Object>(new CaseInsensitiveStringComparator());

    public NameValueMap() {
    }

    /**
     * Method sets to some specific field value, if such field is not
     * exists method creates new one
     * @param strFieldName - field name
     * @param objValue - field value
     */
    public void setFieldValue(String strFieldName, Object objValue) {
        mapDataHolder.put(strFieldName, objValue);
    }

    /**
     * Method gets for a specific field his value if there are no such field
     * in the collection so returns null
     * @param strFieldName - field name
     * @return - if finds returns non null object
     */
    public Object getFieldValue(String strFieldName) {
        return mapDataHolder.get(strFieldName);
    }

    /**
     * Method returns a iterator to all fields in RowData
     * @return
     */
    public Iterator<String> getFields() {
        Set<String> keySet = mapDataHolder.keySet();
        return keySet.iterator();
    }

    /**
     * Method clears all row data
     */
    public void clearAll() {
        mapDataHolder.clear();
    }

}
