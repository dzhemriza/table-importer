/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tableimporter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tableimporter.collections.NameValueMap;
import tableimporter.collections.ObjectWithAttributes;

/**
 *
 * @author Administrator
 */
public class ObjectWithPropertiesTest {

    public ObjectWithPropertiesTest() {
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

    @Test
    public void testObjectWithProperties() {
        ObjectWithAttributes instance = new ObjectWithAttributes();
        instance.setObjectValue(12); // int
        assertTrue((Integer) instance.getObjectValue() == 12);
    }

}