package com.epam.servicedesk.service;

import com.epam.servicedesk.database.GroupDAO;
import com.epam.servicedesk.database.UserDAO;
import com.epam.servicedesk.exception.ConnectionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.validation.AbstractValidation.isNumeric;


public class GroupCabinetService implements Service {
    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, ConnectionException {
        GroupDAO groupDAO = new GroupDAO();
        UserDAO userDAO = new UserDAO();
        String GroupIDFromGetMethod = httpServletRequest.getParameter(GROUP_ID_GET_METHOD);
        if (isNumeric(GroupIDFromGetMethod) && (GroupIDFromGetMethod != null)) {
            httpServletRequest.setAttribute(GROUP_PARAMETER, groupDAO.getById(Long.parseLong(GroupIDFromGetMethod)));
            httpServletRequest.setAttribute(USER_LIST, userDAO.getAllUserWithoutGroup(Long.parseLong(GroupIDFromGetMethod)));
            httpServletRequest.setAttribute(USERS_BY_GROUP_ID_PARAMETER, userDAO.getAllUserByGroupId(Long.parseLong(GroupIDFromGetMethod)));
            httpServletRequest.setAttribute(URI, UPDATE_GROUP_NAME_URI);
        } else {
            httpServletRequest.setAttribute(URI, CREATE_GROUP_URI);
            httpServletRequest.setAttribute(USER_LIST, userDAO.getAll());
        }
        httpServletRequest.getServletContext().getRequestDispatcher(GROUP_CABINET_JSP).forward(httpServletRequest, httpServletResponse);
    }
}
