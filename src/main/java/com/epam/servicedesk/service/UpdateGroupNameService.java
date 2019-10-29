package com.epam.servicedesk.service;

import com.epam.servicedesk.database.GroupDAO;
import com.epam.servicedesk.entity.Group;
import com.epam.servicedesk.entity.User;
import com.epam.servicedesk.exception.ConnectionException;
import com.epam.servicedesk.exception.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.validation.AbstractValidation.isNumeric;
import static com.epam.servicedesk.validation.GroupAndProjectValidation.validateNameGroupOrProjectOrMode;

public class UpdateGroupNameService implements Service {
    private static final Logger LOGGER = LogManager.getRootLogger();

    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, ValidationException, ConnectionException {
        GroupDAO groupDAO = new GroupDAO();
        Group group = new Group();
        group.setName(validateNameGroupOrProjectOrMode(httpServletRequest.getParameter(GROUP_NAME_PARAMETER)));
        User user = (User)httpServletRequest.getSession().getAttribute(USER_PARAMETER);
        if(isNumeric(httpServletRequest.getParameter(GROUP_ID_PARAMETER))) {
            group.setId(Long.parseLong(httpServletRequest.getParameter(GROUP_ID_PARAMETER)));
            if(!group.getName().equals(groupDAO.getByName(group.getName()).getName())) {
                groupDAO.update(group);
                LOGGER.info(String.format("Group name was updated groupId = %d by userId %d", group.getId(), user.getId()));
            }
        }
        httpServletRequest.getServletContext().getRequestDispatcher(LIST_GROUP_URI).forward(httpServletRequest, httpServletResponse);
    }
}
