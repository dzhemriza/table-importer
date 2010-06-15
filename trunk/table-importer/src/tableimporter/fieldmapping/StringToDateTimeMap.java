/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: StringToDateTimeMap.java
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

package tableimporter.fieldmapping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import tableimporter.fields.IField;
import tableimporter.fields.FieldType;

/**
 *
 * @author djemriza
 */
public class StringToDateTimeMap extends StringToTypeBase {
    protected String strDateFormat = "";

    public StringToDateTimeMap(IField srcField, IField dstField, String strDateFormat, FieldType dateType) throws ExpectDateTimeType {
        super(srcField, dstField, dateType);
        this.strDateFormat = strDateFormat;
        assert(0 < this.strDateFormat.length());

        if ((FieldType.Date != dateType) && (FieldType.Time != dateType) && (FieldType.DateTime != dateType))
            throw new ExpectDateTimeType(dateType, "Expect date time type");
    }

    protected Object parseTypeFromString(String srcFieldValue) throws Exception {
        DateFormat dateFormatter = new SimpleDateFormat(strDateFormat);
        return dateFormatter.parse(srcFieldValue);
    }

    // This code is feezed for the moment maybe in feature will be changed
//    @Override
//    protected Object convertToNeededType(Object parsedValue) {
//    }

}
