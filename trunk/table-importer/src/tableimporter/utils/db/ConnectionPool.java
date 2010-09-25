/*
 * table-importer
 * Imports tabled data from any source to any destination
 *
 * File Name: ConnectionPool.java
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

package tableimporter.utils.db;

import java.util.TreeMap;
import java.util.Set;
import java.util.Iterator;
import java.sql.Connection;
import java.sql.SQLException;
import java.net.MalformedURLException;
import tableimporter.config.ConfigXml;
import tableimporter.config.ConnectionInfo;
import org.apache.log4j.Logger;

/**
 *
 * @author djemriza
 */
public class ConnectionPool {
    private TreeMap<String, Connection> map = new TreeMap<String, Connection>();

    public ConnectionPool() {
    }

    public Connection getConnection(String connName) {
        Connection result = null;

        if (map.containsKey(connName)) {
            result = map.get(connName);
        }

        return result;
    }

    public void clear() {
        Logger logger = Logger.getLogger("tableimporter.utils.db");

        Set<String> keys = map.keySet();
        Iterator<String> iter = keys.iterator();
        
        while (iter.hasNext()) {
            String key = iter.next();
            Connection con = map.get(key);
            try {
                con.close();
            }
            catch (SQLException sqlEx) {
                logger.warn("Unable to close connection in pool with key name " + key, sqlEx);
            }
        }

        map.clear();
    }

    public void loadFromConfig(ConfigXml conf) throws ClassNotFoundException,
                SQLException, MalformedURLException,
                InstantiationException, IllegalAccessException, Exception {
        Iterator<ConnectionInfo> iter = conf.getConnectionInfo();
        while (iter.hasNext()) {
            ConnectionInfo info = iter.next();

            DBConnectionBuilder conBuilder = new DBConnectionBuilder();
            Connection con = null;
            
            if (info.getDriverJarFile().equals("")) {
                con = conBuilder.buildConnection(
                        info.getDriverClassName(), info.getUrl(),
                        info.getUser(),
                        info.getPass());
            }
            else {
                con = conBuilder.buildConnection(
                        info.getDriverJarFile(), info.getDriverClassName(),
                        info.getUrl(), info.getUser(), info.getPass());
            }

            assert(null != con);

            final String empty = "";

            if (empty.equals(info.getConnectionName()))
                throw new Exception("The connection name cannot be assiciated because contains empty string");

            if (map.containsKey(info.getConnectionName()))
                throw new Exception("The connection pool contians connection with key " + info.getConnectionName());

            if (null == con)
                throw new Exception("Connection object is null");
            
            map.put(info.getConnectionName(), con);
        }
    }

}
