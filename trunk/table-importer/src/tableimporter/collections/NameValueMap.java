/*
 * table-importer
 * Imports tabled data from any source to any destination
 * 
 * File Name: NameValueMap.java
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

    /**
     * Method checks is such field exist into collection
     * @param strFieldName
     * @return
     */
    public boolean isFieldExist(String strFieldName) {
        Set<String> keySet = mapDataHolder.keySet();
        return keySet.contains(strFieldName);
    }

}
