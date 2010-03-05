/*
 * File Name: IRowImporter.java
 * @author djemriza
 */

package tableimporter.importer;

import java.util.Vector;
import tableimporter.dest_table.IDestTable;
import tableimporter.source_table.ISourceTable;
import tableimporter.fieldmapping.IFieldMap;

/**
 *
 * @author djemriza
 */
public interface IRowImporter {

    /**
     * Method imports data from source table to destination table using
     * field mappings transformation
     * @param srcTable
     * @param dstTable
     * @param fieldMapping
     */
    public void processImport(ISourceTable srcTable, IDestTable dstTable, Vector<IFieldMap> fieldMapping);
}
