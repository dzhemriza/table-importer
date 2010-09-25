/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: FieldMappingsBuilder.java
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

package tableimporter.class_builders;

import java.util.ArrayList;
import java.util.Iterator;
import java.lang.reflect.*;
import tableimporter.fieldmapping.*;
import tableimporter.config.ConfigXml;
import tableimporter.fieldmapinfo.FieldMapInfo;
import tableimporter.source_table.ISourceTable;
import tableimporter.dest_table.IDestTable;
import tableimporter.fields.IField;


/**
 *
 * @author djemriza
 */
public class FieldMappingsBuilder {
    private ArrayList<IFieldMap> fieldMappings = new ArrayList<IFieldMap>();
    private ConfigXml confXml = null;
    private ISourceTable srcTable = null;
    private IDestTable dstTable = null;
    private static String empty = "";

    public FieldMappingsBuilder() {
    }

    public ArrayList<IFieldMap> getFieldMappings() {
        return fieldMappings;
    }

    public void setConfigXml(ConfigXml confXml) {
        assert(null != confXml);
        this.confXml = confXml;
    }

    public void setSourceTable(ISourceTable srcTable) {
        assert(null != srcTable);
        this.srcTable = srcTable;
    }

    public void setDestTable(IDestTable dstTable) {
        assert(null != dstTable);
        this.dstTable = dstTable;
    }

    public void buildFieldMappings() throws Exception {
        assert(null != confXml);

        Iterator<FieldMapInfo> itFieldMaps = confXml.getFieldMapping();

        while (itFieldMaps.hasNext()) {
            FieldMapInfo fieldMapInfo = itFieldMaps.next();

            IFieldMap fieldMap = buildFieldMap(fieldMapInfo);
            if (null == fieldMap)
                throw new NullPointerException("Returned field map is null");

            fieldMappings.add(fieldMap);
        }
    }

    private IField findFieldByName(ArrayList<IField> fields, String strFieldName) throws Exception {
        if (empty.equals(strFieldName))
            return null;

        for (int i = 0; i < fields.size(); ++i) {
            IField fld = fields.get(i);

            if (strFieldName.equals(fld.getFieldName())) {
                return fld;
            }
        }

        throw new Exception("Unable to find field with name " + strFieldName);
    }

    private IFieldMap buildFieldMap(FieldMapInfo fieldMapInfo) throws Exception {
        String strSourceField = fieldMapInfo.getSourceField();
        String strDestField = fieldMapInfo.getDestField();

        IField srcField = findFieldByName(srcTable.getFields(), strSourceField);
        IField dstField = findFieldByName(dstTable.getFields(), strDestField);

        Class clazz = Class.forName(fieldMapInfo.getFieldMapClassName());
        final String createFromData = "createFromData";

        Method[] methods = clazz.getMethods();
        for (int i = 0; i < methods.length; ++i) {
            if (Modifier.isPublic(methods[i].getModifiers()) &&
                Modifier.isStatic(methods[i].getModifiers()) &&
                (createFromData.equals(methods[i].getName()))) {
                Object params[] = {fieldMapInfo, srcField, dstField};

                Object fieldMap = methods[i].invoke(null, params);

                if (null == fieldMap)
                    throw new NullPointerException("Created field map object is NULL");

                if (!(fieldMap instanceof IFieldMap))
                    throw new Exception("Create object is not IFieldMap derivated class: " + fieldMap.toString());

                return (IFieldMap) fieldMap;
           }
        }
        
        return null;
    }
    
}
