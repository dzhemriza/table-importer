/*
 * table-importer
 * Imports tabled data from any source to any destination
 * 
 * File Name: RowImporter.java
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

package tableimporter.importer;

import java.util.ArrayList;
import java.util.Iterator;
import tableimporter.dest_table.IDestTable;
import tableimporter.source_table.ISourceTable;
import tableimporter.fieldmapping.IFieldMap;
import tableimporter.collections.RowData;
import tableimporter.fieldmapping.UnableToWriteFieldMapData;
import org.apache.log4j.Logger;

/**
 *
 * @author djemriza
 */
public class RowImporter implements IRowImporter {

    public RowImporter() {
    }

//    Question: Do we need all this information or not
//    because the when we just call postRecord the Destination
//    table already has this information about the type and is field is
//    key field. I will comment this code but will not remove it
//    because maybe tomorrow i will need it
//
//    private void copyDestFieldNames(IDestTable dstTable, RowData rowData) {
//        ArrayList<IField> vFields = dstTable.getFields();
//
//        for (int i = 0; i < vFields.size(); ++i) {
//            IField field = vFields.get(i);
//
//            rowData.setFieldValue(field.getFieldName(), null);
//
//            NameValueMap attributes = rowData.getFieldAttributes(field.getFieldName());
//
//            attributes.setFieldValue("type", field.getFieldType());
//            attributes.setFieldValue("key", field.isKeyField());
//        }
//    }
    
    public void processImport(ISourceTable srcTable, IDestTable dstTable, ArrayList<IFieldMap> fieldMapping) {
        Iterator<RowData> srcTableIter = srcTable.getRecords();

        while (srcTableIter.hasNext()) {
            RowData rowData = srcTableIter.next();
            RowData destRowData = new RowData();

            for (int i = 0; i < fieldMapping.size(); ++i) {
                IFieldMap fieldMap = fieldMapping.get(i);

                try {
                    fieldMap.write(rowData, destRowData);
                } catch (UnableToWriteFieldMapData unableToWriteFieldMapData) {
                    Logger logger = Logger.getLogger("tableimporter.importer");

                    logger.error(unableToWriteFieldMapData.getMessage());
                    logger.debug("Full stack trace of exception:", unableToWriteFieldMapData);
                }
            }

            dstTable.postRecord(destRowData);
        }
    }
}
