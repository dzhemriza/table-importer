/*
 * File Name: RowImporter.java
 * @author djemriza
 */

package tableimporter.importer;

import java.util.Vector;
import java.util.Iterator;
import tableimporter.dest_table.IDestTable;
import tableimporter.source_table.ISourceTable;
import tableimporter.fieldmapping.IFieldMap;
import tableimporter.collections.RowData;

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
//        Vector<IField> vFields = dstTable.getFields();
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
    
    public void processImport(ISourceTable srcTable, IDestTable dstTable, Vector<IFieldMap> fieldMapping) {
        Iterator<RowData> srcTableIter = srcTable.getRecords();

        while (srcTableIter.hasNext()) {
            RowData rowData = srcTableIter.next();
            RowData destRowData = new RowData();

            for (int i = 0; i < fieldMapping.size(); ++i) {
                IFieldMap fieldMap = fieldMapping.get(i);

                fieldMap.write(rowData, destRowData);
            }

            dstTable.postRecord(destRowData);
        }
    }
}
