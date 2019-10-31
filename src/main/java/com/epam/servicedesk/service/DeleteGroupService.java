package com.epam.servicedesk.service;

import com.epam.servicedesk.database.GroupDAO;
import com.epam.servicedesk.entity.Group;
import com.epam.servicedesk.entity.User;
import com.epam.servicedesk.exception.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.validation.AbstractValidation.isNumeric;

public class DeleteGroupService implements Service {
    private static final Logger LOGGER = LogManager.getRootLogger();

    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, SQLException, ConnectionException {
        GroupDAO groupDAO = new GroupDAO();
        Group group = new Group();
        User operator = (User) httpServletRequest.getSession().getAttribute(USER_PARAMETER);
        if (isNumeric(httpServletRequest.getParameter(GROUP_ID_PARAMETER))) {
            group = groupDAO.getById(Long.parseLong(httpServletRequest.getParameter(GROUP_ID_PARAMETER)));
        }
        groupDAO.delete(group);
        LOGGER.info(String.format("Group was deleted,  groupId = %d, by operatorId %d", group.getId(), operator.getId()));
        httpServletResponse.sendRedirect(LIST_GROUP_URI);
    }
}
