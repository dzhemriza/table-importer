/*
 * table-importer
 * Imports tabled data from any source to any destination
 * 
 * File Name: ObjectWithAttributes.java
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

/**
 *
 * @author djemriza
 */
public class ObjectWithAttributes {

    protected Object objValue = null;
    protected NameValueMap objProps = new NameValueMap();

    public ObjectWithAttributes() {
    }

    public ObjectWithAttributes(Object objValue) {
        this.objValue = objValue;
    }

    /**
     * Returns internal objects value
     * @return
     */
    public Object getObjectValue() {
        return this.objValue;
    }

    /**
     * Sets internal object value
     * @param objValue
     */
    public void setObjectValue(Object objValue) {
        this.objValue = objValue;
    }

    /**
     * Returns name value map which contains a properties of a object
     * @return
     */
    public NameValueMap getAttributes() {
        return objProps;
    }

}
