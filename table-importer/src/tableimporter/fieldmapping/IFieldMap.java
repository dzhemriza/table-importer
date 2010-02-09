/*
 * File Name: IFieldMap.java
 * @author djemriza
 */

package tableimporter.fieldmapping;

import tableimporter.collections.RowData;
import tableimporter.*;

/**
 *
 * @author djemriza
 */
public interface IFieldMap {

    /**
     * Method writes field mapping into destination row data
     * @param rowData
     */
    public void write(RowData rowData);
}
