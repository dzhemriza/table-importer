/*
 * table-importer
 * Imports tabled data from any source to any destination
 * 
 * File Name: Field.java
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
