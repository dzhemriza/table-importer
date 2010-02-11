/*
 * File Name: ObjectWithProperties.java
 * @author djemriza
 */

package tableimporter.collections;

/**
 *
 * @author djemriza
 */
public class ObjectWithProperties {

    protected Object objValue = null;
    protected NameValueMap objProps = new NameValueMap();

    public ObjectWithProperties() {
    }

    public ObjectWithProperties(Object objValue) {
        this.objValue = objValue;
    }

    /**
     * Returns internal objects value
     * @return
     */
    public Object getObjectValue() {
        return this.objValue;
    }

    /**
     * Sets internal object value
     * @param objValue
     */
    public void setObjectValue(Object objValue) {
        this.objValue = objValue;
    }

    /**
     * Returns name value map which contains a properties of a object
     * @return
     */
    public NameValueMap getProperties() {
        return objProps;
    }

}
