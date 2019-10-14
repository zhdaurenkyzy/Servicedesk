package com.epam.servicedesk.service;

import com.epam.servicedesk.database.GroupDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.GROUP_LIST_PARAMETER;
import static com.epam.servicedesk.util.ConstantForApp.LIST_GROUP_JSP;

public class ListGroupService implements Service {
    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        GroupDAO groupDAO = new GroupDAO();
        httpServletRequest.setAttribute(GROUP_LIST_PARAMETER, groupDAO.getAllGroup());
        httpServletRequest.getServletContext().getRequestDispatcher(LIST_GROUP_JSP).forward(httpServletRequest, httpServletResponse);
    }
}
