/*
 * File Name: NameValueMapTest.java
 * @author djemriza
 */

package tableimporter;

import tableimporter.collections.*;
import java.util.Iterator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author djemriza
 */
public class NameValueMapTest {

    public NameValueMapTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of setFieldValue method, of class NameValueMap.
     */
    @Test
    public void testSetFieldValue() {
        NameValueMap instance = new NameValueMap();
        instance.setFieldValue("fld1", "string value");
        instance.setFieldValue("fld2", 2);
        instance.setFieldValue("fld3", 2.4);
        instance.setFieldValue("fld4", 2.4);

        Object val = instance.getFieldValue("fld1");
        assertNotNull(val);
        assertTrue(val instanceof String);

        val = instance.getFieldValue("fld2");
        assertNotNull(val);
        assertTrue(val instanceof Integer);

        val = instance.getFieldValue("fld3");
        assertNotNull(val);
        assertTrue(val instanceof Double);

        val = instance.getFieldValue("fld4");
        assertNotNull(val);
        assertTrue(val instanceof Double);

        val = instance.getFieldValue("fld5");
        assertNull(val);
    }

    /**
     * Test of getFieldValue method, of class NameValueMap.
     */
    @Test
    public void testGetFieldValue() {
        NameValueMap instance = new NameValueMap();
        instance.setFieldValue("fld1", "string value");
        instance.setFieldValue("fld2", 2);
        instance.setFieldValue("fld3", 2.4);
        instance.setFieldValue("fld4", 2.4);

        Object val = instance.getFieldValue("fld1");
        assertNotNull(val);
        assertTrue(val instanceof String);
        assertEquals((String) val, "string value");

        val = instance.getFieldValue("Fld2");
        assertNotNull(val);
        assertTrue(val instanceof Integer);
        int nval = (Integer) val;
        assertEquals(nval, 2);

        val = instance.getFieldValue("fld3");
        assertNotNull(val);
        assertTrue(val instanceof Double);
        double dval = (Double) val;
        assertTrue(dval == 2.4);

        val = instance.getFieldValue("fld4");
        assertNotNull(val);
        assertTrue(val instanceof Double);
        dval = (Double) val;
        assertTrue(dval == 2.4);

        val = instance.getFieldValue("fld5");
        assertNull(val);
    }

    /**
     * Test of getFields method, of class NameValueMap.
     */
    @Test
    public void testGetFields() {
        NameValueMap instance = new NameValueMap();
        Iterator<String> iterFields = instance.getFields();
        while (iterFields.hasNext()) {
            fail("you should never enter here");
            iterFields.next();
        }
        String[] someFieldNames = {"a", "B", "C",  "d", "FK121_RES_TBL"};
        for (int i = 0; i < someFieldNames.length; ++i)
            instance.setFieldValue(someFieldNames[i], null);
        iterFields = instance.getFields();
        while (iterFields.hasNext()) {
            String fldName = iterFields.next();
            int i = 0;
            for (i = 0; i < someFieldNames.length; ++i)
                if (0 == fldName.compareTo(someFieldNames[i]))
                    break;
            assertTrue(i < someFieldNames.length);
        }
    }

    /**
     * Test of clearAll method, of class NameValueMap
     */
    @Test
    public void testClearAll() {
        NameValueMap instance = new NameValueMap();
        Iterator<String> iterFields = instance.getFields();
        while (iterFields.hasNext()) {
            fail("you should never enter here");
            iterFields.next();
        }
        String[] someFieldNames = {"a", "B", "C",  "d", "FK121_RES_TBL"};
        for (int i = 0; i < someFieldNames.length; ++i)
            instance.setFieldValue(someFieldNames[i], null);
        iterFields = instance.getFields();
        assertTrue(iterFields.hasNext());
        instance.clearAll();
        iterFields = instance.getFields();
        while (iterFields.hasNext()) {
            fail("you should never enter here");
            iterFields.next();
        }
    }

    @Test
    public void testIsFieldExist() {
        NameValueMap instance = new NameValueMap();
        instance.setFieldValue("fld1", "string value");
        instance.setFieldValue("fld2", 2);
        instance.setFieldValue("fld3", 2.4);
        instance.setFieldValue("fld4", 2.4);

        assertTrue(instance.isFieldExist("fld1"));
        assertTrue(instance.isFieldExist("fld2"));
        assertTrue(instance.isFieldExist("fld3"));
        assertTrue(instance.isFieldExist("fld4"));

        assertTrue(instance.isFieldExist("fLd1"));
        assertFalse(instance.isFieldExist("AfLd1"));
   }

}