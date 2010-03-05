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
    
    public void processImport(ISourceTable srcTable, IDestTable dstTable, Vector<IFieldMap> fieldMapping) {
        Iterator<RowData> srcTableIter = srcTable.getRecords();

        while (srcTableIter.hasNext()) {
            RowData rowData = srcTableIter.next();
            RowData destRowData = new RowData();
            // todo djemriza: add code for populating field names and field
            // attributes for destRowData

            for (int i = 0; i < fieldMapping.size(); ++i) {
                IFieldMap fieldMap = fieldMapping.get(i);

                fieldMap.write(rowData, destRowData);
            }

            dstTable.postRecord(destRowData);
        }
    }
}
