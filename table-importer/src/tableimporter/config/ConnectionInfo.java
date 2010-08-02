/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: ConnectionInfo.java
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

/**
 *
 * @author djemriza
 */
public class ConnectionInfo {
    private String name;
    private String className;
    private String user;
    private String pass;
    private String jarFile;

    public ConnectionInfo(String name, String className, String user, String pass, String jarFile) {
        this.name = name;
        this.className = className;
        this.user = user;
        this.pass = pass;
        this.jarFile = jarFile;
    }

    public String getConnectionName() {
        return name;
    }

    public String getDriverClassName() {
        return className;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public String getDriverJarFile() {
        return jarFile;
    }

}
