package com.epam.servicedesk.service;

import com.epam.servicedesk.database.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.LIST_USER_JSP;
import static com.epam.servicedesk.util.ConstantForApp.USER_LIST_FROM_OPTIONS_PARAMETER;

public class ListUserService implements Service {

    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        httpServletRequest.setAttribute(USER_LIST_FROM_OPTIONS_PARAMETER, userDAO.getAllUsers());
        httpServletRequest.getServletContext().getRequestDispatcher(LIST_USER_JSP).forward(httpServletRequest, httpServletResponse);

    }
}
