/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: FieldMapInfo.java
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

package tableimporter.fieldmapinfo;

import tableimporter.fields.FieldType;

/**
 *
 * @author djemriza
 */
public class FieldMapInfo {
    private String strClass = "";
    private String sourceField = "";
    private String destField = "";
    // StringToDateTimeMap
    private String dateFormat = "";
    private FieldType fieldType = FieldType.Other;
    // AutoNumberMap
    private int initialVal = 0;
    private int incValue = 0;
    // ConstDataMap
    private String strConstValue = "";

    public FieldMapInfo(
            String strClass, String sourceField, String destField,
            String dateFormat, FieldType fieldType, int initialVal,
            int incValue, String strConstValue) {
        this.strClass = strClass;
        this.sourceField = sourceField;
        this.destField = destField;
        this.dateFormat = dateFormat;
        this.fieldType = fieldType;
        this.initialVal = initialVal;
        this.incValue = incValue;
        this.strConstValue = strConstValue;
    }

    public String getFieldMapClassName() {
        return strClass;
    }

    public String getSourceField() {
        return sourceField;
    }

    public String getDestField() {
        return destField;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public int getInitialValue() {
        return initialVal;
    }

    public int getIncValue() {
        return incValue;
    }

    public String getConstValue() {
        return strConstValue;
    }

}
