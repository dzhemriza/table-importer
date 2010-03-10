/*
 * File Name: DBConnectionBuilder.java
 * @author djemriza
 */

package tableimporter.utils.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Driver;
import java.sql.SQLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.MalformedURLException;

/**
 * The DBConnectionBuilder class is responsible to create appropriate db connection
 * throught already registered db drivers or by loading a db driver from
 * jar file
 *
 * @author djemriza
 */
public class DBConnectionBuilder {

    /**
     * Method builds a database connection object according to a class name,
     * connection url, user name and password
     *
     * The method not loads dynamicaly driver using jar file
     *
     * @param strClassName
     * @param url
     * @param user
     * @param pass
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Connection buildConnection(
            String strClassName,
            String url,
            String user,
            String pass) throws ClassNotFoundException, SQLException {
        Class.forName(strClassName);

        Connection result = null;

        result = DriverManager.getConnection(url, user, pass);

        return result;
    }

    /**
     * Method builds a database connection object according to a class name,
     * connection url, user name and password
     *
     * Also the method requres to add a jar file which contains specific
     * db driver information
     *
     * @param driverFileName
     * @param strClassName
     * @param url
     * @param user
     * @param pass
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws MalformedURLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public Connection buildConnection(
            String driverFileName,
            String strClassName,
            String url,
            String user,
            String pass) throws
                ClassNotFoundException, SQLException, MalformedURLException,
                InstantiationException, IllegalAccessException {
        URL[] urls = { new URL(driverFileName) };

        URLClassLoader classLoader = new URLClassLoader(urls);

        Driver dbDriver = (Driver) Class.forName(strClassName, true, classLoader).newInstance();

        DriverManager.registerDriver(new DriverShim(dbDriver));

        Connection result = null;

        result = DriverManager.getConnection(url, user, pass);

        return result;
    }
}
