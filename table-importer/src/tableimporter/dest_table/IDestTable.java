/*
 * File Name: IDestTable.java
 * @author djemriza
 */

package tableimporter.dest_table;

import java.util.Vector;
import tableimporter.fields.IField;
import tableimporter.collections.RowData;

/**
 *
 * @author djemriza
 */
public interface IDestTable {

    /**
     * Method post current RowData into destination table
     * @param rowData
     */
    public void postRecord(RowData rowData);

    /**
     * Returns a collection with description of all fields
     * @return
     */
    public Vector<IField> getFields();
}
