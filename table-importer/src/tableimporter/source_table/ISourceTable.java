/*
 * File Name: ISourceTable.java
 * @author djemriza
 */

package tableimporter.source_table;

import java.util.Iterator;
import java.util.Vector;
import tableimporter.collections.RowData;
import tableimporter.fields.IField;

/**
 *
 * @author djemriza
 */
public interface ISourceTable {

    /**
     * Returns iterator to all containing data into source table
     * @return
     */
    public Iterator<RowData> getRecords();

    /**
     * Returns a collection with description of all fields
     * @return
     */
    public Vector<IField> getFields();
}
