/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: ConfigXmlTest.java
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

import java.util.Iterator;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tableimporter.fields.IField;
import tableimporter.fields.Field;
import tableimporter.fields.FieldType;

/**
 *
 * @author djemriza
 */
public class ConfigXmlTest {

    public ConfigXmlTest() {
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
    public void testXmlLoadingGeneralCase1() throws Exception {
        System.out.println("WARNING: This test file contains hard coded paths to files!");
        String fileName = "C:/temp/unit_tests/import-config-test-1.xml";
        ConfigXml confXml = new ConfigXml();
        confXml.loadXml(fileName);
        assertEquals(confXml.getMajorVersion(), 1);
        assertEquals(confXml.getMinorVersion(), 0);
        assertEquals(confXml.getLog4jConfigFile(), "log4j.properties");
        Iterator<ConnectionInfo> iter = confXml.getConnectionInfo();
        while (iter.hasNext()) {
            ConnectionInfo conInfo = iter.next();
            assertEquals(conInfo.getConnectionName(), "MySQL");
            assertEquals(conInfo.getUser(), "root");
            assertEquals(conInfo.getPass(), "root");
            assertEquals(conInfo.getDriverClassName(), "com.mysql.jdbc.Driver");
            assertEquals(conInfo.getDriverJarFile(), "file:/C:/temp/unit_tests/mysql-connector-java-5.1.10-bin.jar");
        }
        assertEquals(confXml.getSourceTableConnection(), "MySQL");
        assertEquals(confXml.getSourceTableSql(), "select * from persons;");

        ArrayList<IField> tstFlds = new ArrayList<IField>();
        tstFlds.add(new Field("persons_id", FieldType.Integer, true));
        tstFlds.add(new Field("persons_name", FieldType.String, false));
        tstFlds.add(new Field("persons_age", FieldType.Integer, false));
        tstFlds.add(new Field("persons_address", FieldType.String, false));
        Iterator<IField> tstFldIter = tstFlds.iterator();
        Iterator<IField> fldIter = confXml.getDestTableFields();
        while (fldIter.hasNext()) {
            IField fldA = fldIter.next();
            IField fldB = tstFldIter.next();

            assertEquals(fldB.getFieldName(), fldA.getFieldName());
            assertEquals(fldB.getFieldType(), fldA.getFieldType());
            assertEquals(fldB.isKeyField(), fldA.isKeyField());
        }

        assertEquals(confXml.getDestTableConnection(), "MySQL");
        assertEquals(confXml.getDestTableName(), "persons2");
        assertEquals(confXml.getDestTableSqlWhere(), "");
        assertEquals(confXml.getDestTableType(), "insert");
        assertEquals(confXml.getDestTableUseKeyFieldsAsWhere(), false);

        ArrayList<FieldMapInfo> tstFieldMaps = new ArrayList<FieldMapInfo>();
        tstFieldMaps.add(new FieldMapInfo("FieldToFieldMap", "persons_id", "persons_id", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("ToStringMap", "persons_id", "persons_name", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("StringToIntMap", "persons_name", "persons_id", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("StringToDoubleMap", "persons_name", "persons_double", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("StringToFloatMap", "persons_name", "persons_float", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("StringToDateTimeMap", "persons_name", "persons_date", "some date format", FieldType.Date, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("AutoNumberMap", "", "persons_id", "", FieldType.Other, 100, 1, ""));
        tstFieldMaps.add(new FieldMapInfo("StringCatMap", "persons_name", "persons_name", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("MathPlusMap", "src_numeric_field", "dst_numeric_field", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("MathMinusMap", "src_numeric_field", "dst_numeric_field", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("MathMultMap", "src_numeric_field", "dst_numeric_field", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("MathDivMap", "src_numeric_field", "dst_numeric_field", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("ConstDataMap", "", "dest_field", "", FieldType.Other, 0, 0, "1232"));
        Iterator<FieldMapInfo> tstFldMapInfoIter = tstFieldMaps.iterator();
        Iterator<FieldMapInfo> fldMapInfoIter = confXml.getFieldMapping();
        while (fldMapInfoIter.hasNext()) {
            FieldMapInfo a = tstFldMapInfoIter.next();
            FieldMapInfo b = fldMapInfoIter.next();
            assertEquals(a.getConstValue(), b.getConstValue());
            assertEquals(a.getDateFormat(), b.getDateFormat());
            assertEquals(a.getDestField(), b.getDestField());
            assertEquals(a.getFieldMapClassName(), b.getFieldMapClassName());
            assertEquals(a.getFieldType(), b.getFieldType());
            assertEquals(a.getIncValue(), b.getIncValue());
            assertEquals(a.getInitialValue(), b.getInitialValue());
            assertEquals(a.getSourceField(), b.getSourceField());
        }
    }

    @Test
    public void testXmlLoadingGeneralCase2() throws Exception {
        System.out.println("WARNING: This test file contains hard coded paths to files!");
        String fileName = "C:/temp/unit_tests/import-config-test-2.xml";
        ConfigXml confXml = new ConfigXml();
        confXml.loadXml(fileName);
        assertEquals(confXml.getMajorVersion(), 1);
        assertEquals(confXml.getMinorVersion(), 0);
        assertEquals(confXml.getLog4jConfigFile(), "log4j.properties");
        Iterator<ConnectionInfo> iter = confXml.getConnectionInfo();
        while (iter.hasNext()) {
            ConnectionInfo conInfo = iter.next();
            assertEquals(conInfo.getConnectionName(), "MySQL");
            assertEquals(conInfo.getUser(), "root");
            assertEquals(conInfo.getPass(), "root");
            assertEquals(conInfo.getDriverClassName(), "com.mysql.jdbc.Driver");
            assertEquals(conInfo.getDriverJarFile(), "file:/C:/temp/unit_tests/mysql-connector-java-5.1.10-bin.jar");
        }
        assertEquals(confXml.getSourceTableConnection(), "MySQL");
        assertEquals(confXml.getSourceTableSql(), "select * from persons;");

        ArrayList<IField> tstFlds = new ArrayList<IField>();
        tstFlds.add(new Field("persons_id", FieldType.Integer, true));
        tstFlds.add(new Field("persons_name", FieldType.String, false));
        tstFlds.add(new Field("persons_age", FieldType.Integer, false));
        tstFlds.add(new Field("persons_address", FieldType.String, false));
        Iterator<IField> tstFldIter = tstFlds.iterator();
        Iterator<IField> fldIter = confXml.getDestTableFields();
        while (fldIter.hasNext()) {
            IField fldA = fldIter.next();
            IField fldB = tstFldIter.next();

            assertEquals(fldB.getFieldName(), fldA.getFieldName());
            assertEquals(fldB.getFieldType(), fldA.getFieldType());
            assertEquals(fldB.isKeyField(), fldA.isKeyField());
        }

        assertEquals(confXml.getDestTableConnection(), "MySQL");
        assertEquals(confXml.getDestTableName(), "persons2");
        assertEquals(confXml.getDestTableSqlWhere(), "1=1");
        assertEquals(confXml.getDestTableType(), "delete");
        assertEquals(confXml.getDestTableUseKeyFieldsAsWhere(), true);

        ArrayList<FieldMapInfo> tstFieldMaps = new ArrayList<FieldMapInfo>();
        tstFieldMaps.add(new FieldMapInfo("FieldToFieldMap", "persons_id", "persons_id", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("ToStringMap", "persons_id", "persons_name", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("StringToIntMap", "persons_name", "persons_id", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("StringToDoubleMap", "persons_name", "persons_double", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("StringToFloatMap", "persons_name", "persons_float", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("StringToDateTimeMap", "persons_name", "persons_date", "some date format", FieldType.Date, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("AutoNumberMap", "", "persons_id", "", FieldType.Other, 100, 1, ""));
        tstFieldMaps.add(new FieldMapInfo("StringCatMap", "persons_name", "persons_name", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("MathPlusMap", "src_numeric_field", "dst_numeric_field", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("MathMinusMap", "src_numeric_field", "dst_numeric_field", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("MathMultMap", "src_numeric_field", "dst_numeric_field", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("MathDivMap", "src_numeric_field", "dst_numeric_field", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("ConstDataMap", "", "dest_field", "", FieldType.Other, 0, 0, "1232"));
        Iterator<FieldMapInfo> tstFldMapInfoIter = tstFieldMaps.iterator();
        Iterator<FieldMapInfo> fldMapInfoIter = confXml.getFieldMapping();
        while (fldMapInfoIter.hasNext()) {
            FieldMapInfo a = tstFldMapInfoIter.next();
            FieldMapInfo b = fldMapInfoIter.next();
            assertEquals(a.getConstValue(), b.getConstValue());
            assertEquals(a.getDateFormat(), b.getDateFormat());
            assertEquals(a.getDestField(), b.getDestField());
            assertEquals(a.getFieldMapClassName(), b.getFieldMapClassName());
            assertEquals(a.getFieldType(), b.getFieldType());
            assertEquals(a.getIncValue(), b.getIncValue());
            assertEquals(a.getInitialValue(), b.getInitialValue());
            assertEquals(a.getSourceField(), b.getSourceField());
        }
    }

    @Test
    public void testXmlLoadingGeneralCase3() throws Exception {
        System.out.println("WARNING: This test file contains hard coded paths to files!");
        String fileName = "C:/temp/unit_tests/import-config-test-3.xml";
        ConfigXml confXml = new ConfigXml();
        confXml.loadXml(fileName);
        assertEquals(confXml.getMajorVersion(), 1);
        assertEquals(confXml.getMinorVersion(), 0);
        assertEquals(confXml.getLog4jConfigFile(), "log4j.properties");
        Iterator<ConnectionInfo> iter = confXml.getConnectionInfo();
        while (iter.hasNext()) {
            ConnectionInfo conInfo = iter.next();
            assertEquals(conInfo.getConnectionName(), "MySQL");
            assertEquals(conInfo.getUser(), "root");
            assertEquals(conInfo.getPass(), "root");
            assertEquals(conInfo.getDriverClassName(), "com.mysql.jdbc.Driver");
            assertEquals(conInfo.getDriverJarFile(), "file:/C:/temp/unit_tests/mysql-connector-java-5.1.10-bin.jar");
        }
        assertEquals(confXml.getSourceTableConnection(), "MySQL");
        assertEquals(confXml.getSourceTableSql(), "select * from persons;");

        ArrayList<IField> tstFlds = new ArrayList<IField>();
        tstFlds.add(new Field("persons_id", FieldType.Integer, true));
        tstFlds.add(new Field("persons_name", FieldType.String, false));
        tstFlds.add(new Field("persons_age", FieldType.Integer, false));
        tstFlds.add(new Field("persons_address", FieldType.String, false));
        Iterator<IField> tstFldIter = tstFlds.iterator();
        Iterator<IField> fldIter = confXml.getDestTableFields();
        while (fldIter.hasNext()) {
            IField fldA = fldIter.next();
            IField fldB = tstFldIter.next();

            assertEquals(fldB.getFieldName(), fldA.getFieldName());
            assertEquals(fldB.getFieldType(), fldA.getFieldType());
            assertEquals(fldB.isKeyField(), fldA.isKeyField());
        }

        assertEquals(confXml.getDestTableConnection(), "MySQL");
        assertEquals(confXml.getDestTableName(), "persons2");
        assertEquals(confXml.getDestTableSqlWhere(), "1=1");
        assertEquals(confXml.getDestTableType(), "update");
        assertEquals(confXml.getDestTableUseKeyFieldsAsWhere(), true);

        ArrayList<FieldMapInfo> tstFieldMaps = new ArrayList<FieldMapInfo>();
        tstFieldMaps.add(new FieldMapInfo("FieldToFieldMap", "persons_id", "persons_id", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("ToStringMap", "persons_id", "persons_name", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("StringToIntMap", "persons_name", "persons_id", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("StringToDoubleMap", "persons_name", "persons_double", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("StringToFloatMap", "persons_name", "persons_float", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("StringToDateTimeMap", "persons_name", "persons_date", "some date format", FieldType.Date, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("AutoNumberMap", "", "persons_id", "", FieldType.Other, 100, 1, ""));
        tstFieldMaps.add(new FieldMapInfo("StringCatMap", "persons_name", "persons_name", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("MathPlusMap", "src_numeric_field", "dst_numeric_field", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("MathMinusMap", "src_numeric_field", "dst_numeric_field", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("MathMultMap", "src_numeric_field", "dst_numeric_field", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("MathDivMap", "src_numeric_field", "dst_numeric_field", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("ConstDataMap", "", "dest_field", "", FieldType.Other, 0, 0, "1232"));
        Iterator<FieldMapInfo> tstFldMapInfoIter = tstFieldMaps.iterator();
        Iterator<FieldMapInfo> fldMapInfoIter = confXml.getFieldMapping();
        while (fldMapInfoIter.hasNext()) {
            FieldMapInfo a = tstFldMapInfoIter.next();
            FieldMapInfo b = fldMapInfoIter.next();
            assertEquals(a.getConstValue(), b.getConstValue());
            assertEquals(a.getDateFormat(), b.getDateFormat());
            assertEquals(a.getDestField(), b.getDestField());
            assertEquals(a.getFieldMapClassName(), b.getFieldMapClassName());
            assertEquals(a.getFieldType(), b.getFieldType());
            assertEquals(a.getIncValue(), b.getIncValue());
            assertEquals(a.getInitialValue(), b.getInitialValue());
            assertEquals(a.getSourceField(), b.getSourceField());
        }
    }

    @Test
    public void testXmlLoadingGeneralCase4() throws Exception {
        System.out.println("WARNING: This test file contains hard coded paths to files!");
        String fileName = "C:/temp/unit_tests/import-config-test-4.xml";
        ConfigXml confXml = new ConfigXml();
        confXml.loadXml(fileName);
        assertEquals(confXml.getMajorVersion(), 1);
        assertEquals(confXml.getMinorVersion(), 0);
        assertEquals(confXml.getLog4jConfigFile(), "log4j.properties");
        Iterator<ConnectionInfo> iter = confXml.getConnectionInfo();
        while (iter.hasNext()) {
            ConnectionInfo conInfo = iter.next();
            assertEquals(conInfo.getConnectionName(), "MySQL");
            assertEquals(conInfo.getUser(), "root");
            assertEquals(conInfo.getPass(), "root");
            assertEquals(conInfo.getDriverClassName(), "com.mysql.jdbc.Driver");
            assertEquals(conInfo.getDriverJarFile(), "file:/C:/temp/unit_tests/mysql-connector-java-5.1.10-bin.jar");
        }
        assertEquals(confXml.getSourceTableConnection(), "MySQL");
        assertEquals(confXml.getSourceTableSql(), "select * from persons;");

        ArrayList<IField> tstFlds = new ArrayList<IField>();
        tstFlds.add(new Field("persons_id", FieldType.Integer, true));
        tstFlds.add(new Field("persons_name", FieldType.String, false));
        tstFlds.add(new Field("persons_age", FieldType.Integer, false));
        tstFlds.add(new Field("persons_address", FieldType.String, false));
        Iterator<IField> tstFldIter = tstFlds.iterator();
        Iterator<IField> fldIter = confXml.getDestTableFields();
        while (fldIter.hasNext()) {
            IField fldA = fldIter.next();
            IField fldB = tstFldIter.next();

            assertEquals(fldB.getFieldName(), fldA.getFieldName());
            assertEquals(fldB.getFieldType(), fldA.getFieldType());
            assertEquals(fldB.isKeyField(), fldA.isKeyField());
        }

        assertEquals(confXml.getDestTableConnection(), "MySQL");
        assertEquals(confXml.getDestTableName(), "persons2");
        assertEquals(confXml.getDestTableSqlWhere(), "");
        assertEquals(confXml.getDestTableType(), "update_insert");
        assertEquals(confXml.getDestTableUseKeyFieldsAsWhere(), false);

        ArrayList<FieldMapInfo> tstFieldMaps = new ArrayList<FieldMapInfo>();
        tstFieldMaps.add(new FieldMapInfo("FieldToFieldMap", "persons_id", "persons_id", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("ToStringMap", "persons_id", "persons_name", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("StringToIntMap", "persons_name", "persons_id", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("StringToDoubleMap", "persons_name", "persons_double", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("StringToFloatMap", "persons_name", "persons_float", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("StringToDateTimeMap", "persons_name", "persons_date", "some date format", FieldType.Date, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("AutoNumberMap", "", "persons_id", "", FieldType.Other, 100, 1, ""));
        tstFieldMaps.add(new FieldMapInfo("StringCatMap", "persons_name", "persons_name", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("MathPlusMap", "src_numeric_field", "dst_numeric_field", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("MathMinusMap", "src_numeric_field", "dst_numeric_field", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("MathMultMap", "src_numeric_field", "dst_numeric_field", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("MathDivMap", "src_numeric_field", "dst_numeric_field", "", FieldType.Other, 0, 0, ""));
        tstFieldMaps.add(new FieldMapInfo("ConstDataMap", "", "dest_field", "", FieldType.Other, 0, 0, "1232"));
        Iterator<FieldMapInfo> tstFldMapInfoIter = tstFieldMaps.iterator();
        Iterator<FieldMapInfo> fldMapInfoIter = confXml.getFieldMapping();
        while (fldMapInfoIter.hasNext()) {
            FieldMapInfo a = tstFldMapInfoIter.next();
            FieldMapInfo b = fldMapInfoIter.next();
            assertEquals(a.getConstValue(), b.getConstValue());
            assertEquals(a.getDateFormat(), b.getDateFormat());
            assertEquals(a.getDestField(), b.getDestField());
            assertEquals(a.getFieldMapClassName(), b.getFieldMapClassName());
            assertEquals(a.getFieldType(), b.getFieldType());
            assertEquals(a.getIncValue(), b.getIncValue());
            assertEquals(a.getInitialValue(), b.getInitialValue());
            assertEquals(a.getSourceField(), b.getSourceField());
        }
    }

}