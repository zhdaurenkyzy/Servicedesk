package com.epam.servicedesk.listener;

import com.epam.servicedesk.database.LanguageDAO;
import com.epam.servicedesk.entity.Language;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ArrayList;
import java.util.List;

import static com.epam.servicedesk.util.ConstantForApp.LIST_LANGUAGES_PARAMETER;

public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        LanguageDAO languageDAO = new LanguageDAO();
        List<Language> languages = new ArrayList<>();
        languages = languageDAO.getAllLanguage();
        context.setAttribute(LIST_LANGUAGES_PARAMETER, languages);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
