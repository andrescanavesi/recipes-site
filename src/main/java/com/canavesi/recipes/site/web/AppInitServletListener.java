package com.canavesi.recipes.site.web;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Web application lifecycle listener.
 *
 * @author Andres Canavesi
 */
@WebListener
public class AppInitServletListener implements ServletContextListener {

    private static final Logger LOG = Logger.getLogger(AppInitServletListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent event) {
        LOG.log(Level.INFO, "\n***** Initializing {0}", AppInitServletListener.class.getSimpleName());
        LOG.info("\n***** App initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        LOG.log(Level.INFO, "\n***** Destroying {0}", AppInitServletListener.class.getSimpleName());

        LOG.info("\n***** App destroyed");
    }

}
