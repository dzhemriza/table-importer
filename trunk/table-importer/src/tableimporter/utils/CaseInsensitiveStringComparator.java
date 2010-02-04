/*
 * File Name: CaseInsensitiveStringComparator.java
 * @author djemriza
 */

package tableimporter.utils;

import java.util.Comparator;

/**
 * Comparator which compares case insensitive strings
 * @author djemriza
 */
public class CaseInsensitiveStringComparator implements Comparator<String> {

    /**
     * Compare method
     * @param lhs
     * @param rhs
     * @return
     */
    public int compare(String lhs, String rhs) {
        return lhs.compareToIgnoreCase(rhs);
    }

}
