/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: SourceTableBuilderTest.java
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

package tableimporter.class_builders;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tableimporter.config.ConfigXml;
import tableimporter.utils.db.ConnectionPool;

/**
 *
 * @author djemriza
 */
public class SourceTableBuilderTest {

    public SourceTableBuilderTest() {
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
    public void testSrcTableBuilderGeneralCase() throws Exception {
        System.out.println("WARNING: This test file contains hard coded paths to files!");
        String fileName = "C:/temp/unit_tests/import-config-test-5.xml";
        ConfigXml confXml = new ConfigXml();
        confXml.loadXml(fileName);
        ConnectionPool connPool = new ConnectionPool();
        connPool.loadFromConfig(confXml);

        SourceTableBuilder srcTabBuilder = new SourceTableBuilder();
        srcTabBuilder.setConfigXml(confXml);
        srcTabBuilder.setConnectionPool(connPool);
        srcTabBuilder.buildSourceTable();
        assertNotNull(srcTabBuilder.getSourceTable());

        connPool.clear();
    }

}