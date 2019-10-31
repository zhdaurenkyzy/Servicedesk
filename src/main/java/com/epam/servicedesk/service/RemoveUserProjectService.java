package com.epam.servicedesk.service;

import com.epam.servicedesk.database.ConnectionPool;
import com.epam.servicedesk.database.UserDAO;
import com.epam.servicedesk.entity.User;
import com.epam.servicedesk.exception.ConnectionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

import static com.epam.servicedesk.util.ConstantForApp.ID_PARAMETER;
import static com.epam.servicedesk.util.ConstantForApp.LIST_USER_URI;

public class RemoveUserProjectService implements Service {
    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, ConnectionException {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getById(Long.parseLong(httpServletRequest.getParameter(ID_PARAMETER)));
        Connection connection = ConnectionPool.getUniqueInstance().retrieve();
        userDAO.deleteUserFromProject(user, connection);
        ConnectionPool.getUniqueInstance().putback(connection);
        httpServletResponse.sendRedirect(LIST_USER_URI);
    }
}
