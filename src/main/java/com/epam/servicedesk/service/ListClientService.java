package com.epam.servicedesk.service;

import com.epam.servicedesk.database.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.validation.AbstractValidation.isNumeric;

public class ListClientService implements Service {

    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        if(httpServletRequest.getAttribute(ID_PROJECT_PARAMETER)==null) {
            httpServletRequest.setAttribute(CLIENT_LIST_PARAMETER, userDAO.getALLClient());
        }
        if(isNumeric(httpServletRequest.getParameter(ID_PROJECT_PARAMETER))){
            httpServletRequest.setAttribute(CLIENT_LIST_PARAMETER, userDAO.getAllClientByProjectId(Long.parseLong(httpServletRequest.getParameter(ID_PROJECT_PARAMETER))));
        }
        httpServletRequest.getServletContext().getRequestDispatcher(SELECT_CLIENT_FOR_REQUEST_CABINET_JSP).forward(httpServletRequest, httpServletResponse);
    }
}
