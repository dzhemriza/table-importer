/*
 * table-importer
 * Imports tabled data from any source to any destination
 * 
 * File Name: RowData.java
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
