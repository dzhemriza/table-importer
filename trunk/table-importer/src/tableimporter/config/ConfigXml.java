/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: ConfigXml.java
 *
 * Copyright (C) 2010 Dzhem Riza
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package tableimporter.config;

import tableimporter.fieldmapinfo.FieldMapInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import tableimporter.fields.IField;
import tableimporter.fields.FieldType;
import tableimporter.fields.Field;

/**
 *
 * @author djemriza
 */
public class ConfigXml {
    private int majorVersion = 0;
    private int minorVersion = 0;
    private String log4jConfigFile = "";
    private ArrayList<ConnectionInfo> vConnectionInfo = new ArrayList<ConnectionInfo>();
    private String sourceTableConnection = "";
    private String sourceSql = "";
    private String destTableType = "";
    private String destTableConnection = "";
    private String destTableName = "";
    private ArrayList<IField> destTableFields = new ArrayList<IField>();
    private boolean destTableUseKeyFieldsAsWhere = false;
    private String destTableSqlWhere = "";
    private ArrayList<FieldMapInfo> vFieldMapInfo = new ArrayList<FieldMapInfo>();

    public ConfigXml() {
        init();
    }

    private void init() {
        this.majorVersion = 0;
        this.minorVersion = 0;
        this.log4jConfigFile = "";
        this.vConnectionInfo.clear();
        this.sourceTableConnection = "";
        this.sourceSql = "";
        this.destTableType = "";
        this.destTableConnection = "";
        this.destTableName = "";
        this.destTableFields.clear();
        this.destTableUseKeyFieldsAsWhere = false;
        this.destTableSqlWhere = "";
        this.vFieldMapInfo.clear();
    }

    public int getMajorVersion() {
        return majorVersion;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    public String getLog4jConfigFile() {
        return log4jConfigFile;
    }

    public Iterator<ConnectionInfo> getConnectionInfo() {
        return vConnectionInfo.iterator();
    }

    public String getSourceTableConnection() {
        return sourceTableConnection;
    }

    public String getSourceTableSql() {
        return sourceSql;
    }

    public String getDestTableType() {
        return destTableType;
    }

    public String getDestTableConnection() {
        return destTableConnection;
    }

    public String getDestTableName() {
        return destTableName;
    }

    public Iterator<IField> getDestTableFields() {
        return destTableFields.iterator();
    }

    public boolean getDestTableUseKeyFieldsAsWhere() {
        return destTableUseKeyFieldsAsWhere;
    }

    public String getDestTableSqlWhere() {
        return destTableSqlWhere;
    }

    public Iterator<FieldMapInfo> getFieldMapping() {
        return vFieldMapInfo.iterator();
    }

    public void loadXml(String fileName) throws Exception {
        // TODO: Use schema validation instead of throwing exceptions
        init();

        File xmlFile = new File(fileName);
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(xmlFile);

        Element root = doc.getDocumentElement();
        root.normalize();
        String strRootElement = "import";

        if (!strRootElement.equals(root.getNodeName()))
            // TODO: Change the exception class
            throw new Exception("This is not configuration for importing");

        boolean commit = false;

        try {
            loadVersion(root);
            loadLog4jPropertiesFile(root);
            loadConnections(root);
            loadSourceTable(root);
            loadDestTable(root);
            loadFieldMapping(root);

            commit = true;
        }
        finally {
            if (!commit) {
                init();
            }
        }
    }

    private void loadFieldMapping(Element root) throws Exception {
        NodeList fieldMappings = root.getElementsByTagName("field_mapping");

        if (0 == fieldMappings.getLength())
            throw new Exception("Missing field mapping declaration");

        if (1 < fieldMappings.getLength())
            throw new Exception("Too many field mapping declarations");
        
        Node fieldMappingsNode = fieldMappings.item(0);
        NodeList childNodes = fieldMappingsNode.getChildNodes();

        String strFieldMap = "field_map";
        for (int i = 0; i < childNodes.getLength(); ++i) {
            Node node = childNodes.item(i);

            if (Node.ELEMENT_NODE == node.getNodeType()) {
                if (strFieldMap.equals(node.getNodeName())) {
                    String className = "";
                    String srcField = "";
                    String dstField = "";
                    int inital = 0;
                    int increment = 0;
                    String value = "";
                    String dateFormat = "";
                    FieldType fieldType = FieldType.Other;

                    if (isNodeContainsAttribute(node, "class")) {
                        className = getNodeAttrValue(node, "class");
                    }
                    if (isNodeContainsAttribute(node, "source_field")) {
                        srcField = getNodeAttrValue(node, "source_field");
                    }
                    if (isNodeContainsAttribute(node, "dest_field")) {
                        dstField = getNodeAttrValue(node, "dest_field");
                    }
                    if (isNodeContainsAttribute(node, "date_format")) {
                        dateFormat = getNodeAttrValue(node, "date_format");
                    }
                    if (isNodeContainsAttribute(node, "value")) {
                        value = getNodeAttrValue(node, "value");
                    }
                    if (isNodeContainsAttribute(node, "field_type")) {
                        fieldType = FieldType.valueOf(getNodeAttrValue(node, "field_type"));
                    }
                    if (isNodeContainsAttribute(node, "initial")) {
                        inital = Integer.parseInt(getNodeAttrValue(node, "initial"));
                    }
                    if (isNodeContainsAttribute(node, "increment")) {
                        increment = Integer.parseInt(getNodeAttrValue(node, "increment"));
                    }

                    FieldMapInfo fldMapInfo = new FieldMapInfo(className,
                            srcField, dstField, dateFormat,
                            fieldType, inital, increment, value);
                    vFieldMapInfo.add(fldMapInfo);
                }
            }
        }
    }

    private void loadDestTable(Element root) throws Exception {
        NodeList dstTable = root.getElementsByTagName("dest_table");

        if (0 == dstTable.getLength())
            throw new Exception("Missing destination table declaration");

        if (1 < dstTable.getLength())
            throw new Exception("Too many destination table declarations");

        Node dstTableNode = dstTable.item(0);
        
        this.destTableType = getNodeAttrValue(dstTableNode, "type");

        NodeList childNodes = dstTableNode.getChildNodes();

        String con = "connection";
        String table = "table";
        String fields = "fields";
        String sqlWhere = "sql_where";
        String useKeyFieldsAsWhere = "use_key_fields_as_where";

        for (int i = 0; i < childNodes.getLength(); ++i) {
            Node node = childNodes.item(i);

            if (Node.ELEMENT_NODE == node.getNodeType()) {
                if (con.equals(node.getNodeName())) {
                    this.destTableConnection = getNodeAttrValue(node, "name");
                }
                else if (table.equals(node.getNodeName())) {
                    this.destTableName = getNodeAttrValue(node, "name");
                }
                else if (sqlWhere.equals(node.getNodeName())) {
                    this.destTableSqlWhere = node.getTextContent();
                }
                else if (useKeyFieldsAsWhere.equals(node.getNodeName())) {
                    this.destTableUseKeyFieldsAsWhere = Boolean.parseBoolean(node.getTextContent());
                }
                else if (fields.equals(node.getNodeName())) {
                    NodeList fieldNodes = node.getChildNodes();

                    for (int j = 0; j < fieldNodes.getLength(); ++j) {
                        Node fieldNode = fieldNodes.item(j);

                        if (Node.ELEMENT_NODE == fieldNode.getNodeType()) {
                            String strFieldNode = "field";
                            if (strFieldNode.equals(fieldNode.getNodeName())) {
                                String name = getNodeAttrValue(fieldNode, "name");
                                String type = getNodeAttrValue(fieldNode, "type");
                                FieldType fldType = FieldType.valueOf(type);
                                boolean keyField = Boolean.parseBoolean(getNodeAttrValue(fieldNode, "key_field"));

                                Field fld = new Field(name, fldType, keyField);
                                this.destTableFields.add(fld);
                            }
                        }
                    }
                }
            }
        }
    }

    private void loadSourceTable(Element root) throws Exception {
        NodeList srcTable = root.getElementsByTagName("source_table");

        if (0 == srcTable.getLength())
            throw new Exception("Missing source table declaration");

        if (1 < srcTable.getLength())
            throw new Exception("Too many source table declarations");

        Node srcTableNode = srcTable.item(0);
        NodeList childNodes = srcTableNode.getChildNodes();

        String con = "connection";
        String sql = "sql";
        
        for (int i = 0; i < childNodes.getLength(); ++i) {
            Node node = childNodes.item(i);

            if (Node.ELEMENT_NODE == node.getNodeType()) {
                if (con.equals(node.getNodeName())) {
                    this.sourceTableConnection = getNodeAttrValue(node, "name");
                }
                else if (sql.equals(node.getNodeName())) {
                    this.sourceSql = node.getTextContent();
                }
            }
        }
    }

    private void loadConnections(Element root) throws Exception {
        NodeList cons = root.getElementsByTagName("db_connection");

        if (0 == cons.getLength())
            throw new Exception("Missing connections definitions");

        for (int i = 0; i < cons.getLength(); ++i) {
            Node item = cons.item(i);

            NodeList childNodes = item.getChildNodes();

            for (int j = 0; j < childNodes.getLength(); ++j) {
                Node childItem = childNodes.item(j);

                if (Node.ELEMENT_NODE == childItem.getNodeType()) {
                    String name = getNodeAttrValue(childItem, "name");
                    String clazz = getNodeAttrValue(childItem, "class");
                    String user = getNodeAttrValue(childItem, "user");
                    String pass = getNodeAttrValue(childItem, "pass");
                    String jar = getNodeAttrValue(childItem, "jar");
                    String url = getNodeAttrValue(childItem, "url");

                    ConnectionInfo conInfo = new ConnectionInfo(name, clazz, user, pass, jar, url);
                    vConnectionInfo.add(conInfo);
                }
            }
        }
    }

    private void loadLog4jPropertiesFile(Element root) throws Exception {
        NodeList log4j = root.getElementsByTagName("log4j_properties");

        if (1 < log4j.getLength())
            throw new Exception("Too many log4j configurations");

        if (1 == log4j.getLength()) {
            Node node = log4j.item(0);

            this.log4jConfigFile = getNodeAttrValue(node, "file");
        }
    }

    private void loadVersion(Element root) throws Exception {
        NodeList ver = root.getElementsByTagName("version");
        
        if (1 != ver.getLength())
            // TODO: Change the exception class
            throw new Exception("We have too many version elements in configuration");

        Node verNode = ver.item(0);

        if (0 < verNode.getChildNodes().getLength())
            // TODO: Change the exception class
            throw new Exception("Version should not contains child nodes");

        this.majorVersion = Integer.parseInt(getNodeAttrValue(verNode, "major"));
        this.minorVersion = Integer.parseInt(getNodeAttrValue(verNode, "minor"));
    }

    private String getNodeAttrValue(Node node, String attrName) throws Exception {
        NamedNodeMap attributes = node.getAttributes();
        
        for (int i = 0; i < attributes.getLength(); ++i) {
            Node attr = attributes.item(i);
            if (attrName.equals(attr.getNodeName())) {
                return attr.getNodeValue();
            }
        }

        throw new Exception("Unable to find attribute with name " + attrName);
    }

    private boolean isNodeContainsAttribute(Node node, String attrName) {
        boolean result = false;
        
        NamedNodeMap attributes = node.getAttributes();

        for (int i = 0; i < attributes.getLength(); ++i) {
            Node attr = attributes.item(i);
            if (attrName.equals(attr.getNodeName())) {
                result = true;
                break;
            }
        }

        return result;
    }
}
