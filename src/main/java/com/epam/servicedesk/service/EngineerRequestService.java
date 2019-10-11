package com.epam.servicedesk.service;

import com.epam.servicedesk.database.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.validation.AbstractValidation.isNumeric;

public class EngineerRequestService implements Service {

    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        if(httpServletRequest.getAttribute(ID_PARAMETER)==null) {
            httpServletRequest.setAttribute(LIST_USER_PARAMETER, userDAO.getUsersByRole());
        }
        if (isNumeric(httpServletRequest.getParameter(ID_PARAMETER))) {
                httpServletRequest.setAttribute(LIST_USER_PARAMETER, userDAO.getAllUserByGroupId(Long.parseLong(httpServletRequest.getParameter(ID_PARAMETER))));
        }
        httpServletRequest.getServletContext().getRequestDispatcher(SELECT_ENGINEER_FOR_REQUEST_CABINET_JSP).forward(httpServletRequest, httpServletResponse);

    }
}
