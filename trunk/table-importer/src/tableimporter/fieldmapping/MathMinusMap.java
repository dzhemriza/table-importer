/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: MathMinusMap.java
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

import tableimporter.fields.IField;
import tableimporter.fieldmapinfo.FieldMapInfo;

/**
 *
 * @author djemriza
 */
public class MathMinusMap extends MathBaseMap {

    public static IFieldMap createFromData(FieldMapInfo info, IField srcField, IField dstField) {
        return new MathMinusMap(srcField, dstField);
    }

    public MathMinusMap(IField srcField, IField dstField) {
        super(srcField, dstField);
    }

    protected int operateInt(int src, int dst) throws Exception {
        return src - dst;
    }

    protected float operateFloat(float src, float dst) throws Exception {
        return src - dst;
    }

    protected double operateDouble(double src, double dst) throws Exception {
        return src - dst;
    }
}