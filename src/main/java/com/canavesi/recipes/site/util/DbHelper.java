package com.canavesi.recipes.site.util;

import com.canavesi.recipes.site.dao.DaoConfigs;
import com.canavesi.recipes.site.exceptions.DatabaseConfigurationException;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author acanavesi
 */
public class DbHelper {

    /**
     * Heroku configs:
     *
     * -Provision the Postgres database through the Heroku dashboard: go to
     * resources and search postgres
     *
     * To connect to the database using a client, just go to Heroku configs and
     * look for DATABASE_URL. Splitting up that value you can get the
     * credentials
     */
    private static final Logger LOG = Logger.getLogger(DbHelper.class.getName());

    private static DbHelper instance;

    private DbHelper() {

    }

    /**
     *
     * @return
     */
    public static DbHelper getInstance() {
        if (instance == null) {
            instance = new DbHelper();
        }
        return instance;
    }

    /**
     *
     * @return
     *
     * @throws DatabaseConfigurationException
     */
    public Connection getConnection() throws DatabaseConfigurationException {
        try {
            String urlConnection = DaoConfigs.getDataBaseUrl();
            if (urlConnection == null || urlConnection.trim().equals("")) {
                throw new Exception("Database URL connection is null or empty");
            }
            URI dbUri = new URI(urlConnection);
            if (dbUri.getUserInfo() == null) {
                throw new Exception("Malformed database URL connection, unable to get user info from Uri object");
            }

            String[] dbUriArray = dbUri.getUserInfo().split(":");
            if (dbUriArray == null || dbUriArray.length != 2) {
                throw new Exception("Malformed database URL connection");
            }

            String username = dbUriArray[0];
            String password = dbUriArray[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath()
                    + "?ssl=true&sslmode=require";

            String driverName = "org.postgresql.Driver";
            Class.forName(driverName);

            Connection connection = DriverManager.getConnection(dbUrl, username, password);
            return connection;
        } catch (Exception e) {
            throw new DatabaseConfigurationException("Database conneciton failed. Be sure url connection is valid. ", e);
        }

    }

    /**
     *
     * @param resultSet
     * @param statement
     * @param connection
     */
    public static void tryToCloseResources(ResultSet resultSet, PreparedStatement statement, Connection connection) {
        LOG.info("Closing DB resources...");
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
