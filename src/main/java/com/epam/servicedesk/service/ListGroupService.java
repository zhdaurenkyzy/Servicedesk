package com.epam.servicedesk.service;

import com.epam.servicedesk.database.GroupDAO;
import com.epam.servicedesk.exception.ConnectionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.GROUP_LIST_PARAMETER;
import static com.epam.servicedesk.util.ConstantForApp.LIST_GROUP_JSP;

public class ListGroupService implements Service {
    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, ConnectionException {
        GroupDAO groupDAO = new GroupDAO();
        httpServletRequest.setAttribute(GROUP_LIST_PARAMETER, groupDAO.getAll());
        httpServletRequest.getServletContext().getRequestDispatcher(LIST_GROUP_JSP).forward(httpServletRequest, httpServletResponse);
    }
}
