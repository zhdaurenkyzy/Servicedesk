package com.epam.servicedesk.service;

import com.epam.servicedesk.database.LanguageDAO;
import com.epam.servicedesk.entity.Language;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.*;

public class SetLanguageService implements Service {
    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        LanguageDAO languageDAO = new LanguageDAO();
        Language language;
        if (httpServletRequest.getParameter(LANGUAGE_ID_PARAMETER) == null) {
            language = languageDAO.getById(LANGUAGE_ID_DEFAULT);
        } else {
            language = languageDAO.getById(Long.parseLong(httpServletRequest.getParameter(LANGUAGE_ID_PARAMETER)));
        }
        httpServletRequest.getSession().setAttribute(LOCAL_PARAMETER, language.getLocal());
        if (httpServletRequest.getParameter(PAGE_PARAMETER) == null) {
            httpServletResponse.sendRedirect(USER_CABINET_JSP);
        } else {
            httpServletResponse.sendRedirect(httpServletRequest.getParameter(PAGE_PARAMETER));
        }
    }
}
