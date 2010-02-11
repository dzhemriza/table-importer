/*
 * File Name: FieldTest.java
 * @author djemriza
 */

package tableimporter;

import tableimporter.fields.FieldType;
import tableimporter.fields.Field;
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
public class FieldTest {

    public FieldTest() {
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
     * Test of getFieldName method, of class Field.
     */
    @Test
    public void testGetFieldName() {
        Field instance = new Field("A", FieldType.String, false);
        assertEquals(instance.getFieldName(), "A");
    }

    /**
     * Test of getFieldType method, of class Field.
     */
    @Test
    public void testGetFieldType() {
        Field instance = new Field("A", FieldType.String, false);
        assertEquals(instance.getFieldType(), FieldType.String);
    }

    /**
     * Test of getDataIndex method, of class Field.
     */
    @Test
    public void testGetDataIndex() {
        Field instance = new Field("A", FieldType.String, true);
        assertEquals(instance.isKeyField(), true);
    }

}