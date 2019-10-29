package com.epam.servicedesk.listener;

import com.epam.servicedesk.database.LanguageDAO;
import com.epam.servicedesk.exception.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import static com.epam.servicedesk.util.ConstantForApp.CONNECTION_NOT_FOUND;
import static com.epam.servicedesk.util.ConstantForApp.LIST_LANGUAGES_PARAMETER;

public class ContextListener implements ServletContextListener {
    private static final Logger LOGGER = LogManager.getRootLogger();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        LanguageDAO languageDAO = new LanguageDAO();
        try {
            context.setAttribute(LIST_LANGUAGES_PARAMETER, languageDAO.getAll());
        } catch (ConnectionException e) {
            LOGGER.error(CONNECTION_NOT_FOUND);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}