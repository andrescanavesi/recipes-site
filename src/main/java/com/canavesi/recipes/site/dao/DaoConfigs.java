package com.canavesi.recipes.site.dao;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * We use environment variables, we do not store in DB so this class does not
 * extends from DaoBase.
 *
 * Go to README file to configure environment variables in Tomcat
 *
 * In Heroku you can add / edit an environment variable like this:
 *
 * heroku config:set CONFIG_1="your_value"
 *
 * @author Andres Canavesi
 */
public class DaoConfigs {

    private static final Logger LOG = Logger.getLogger(DaoConfigs.class.getName());

    /**
     * There is not public instances of this class
     */
    private DaoConfigs() {

    }

    /**
     *
     * @return example "40.0"
     */
    public static String getDatabaseUrl() {
        String value = System.getenv("DATABASE_URL");
        if (value == null) {
            //since this is a sensitive data we do not return a default value
            throw new IllegalStateException("DATABASE_URL environment variable not found");

        }
        return value;
    }

}
