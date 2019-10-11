package com.epam.servicedesk.service;

import com.epam.servicedesk.database.GroupDAO;
import com.epam.servicedesk.entity.Group;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.GROUP_ID_PARAMETER;
import static com.epam.servicedesk.util.ConstantForApp.LIST_GROUP_URI;
import static com.epam.servicedesk.validation.AbstractValidation.isNumeric;

public class DeleteGroupService implements Service {
    private static final Logger LOGGER = LogManager.getRootLogger();

    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        GroupDAO groupDAO = new GroupDAO();
        Group group = new Group();
        if(isNumeric(httpServletRequest.getParameter(GROUP_ID_PARAMETER))) {
            group = groupDAO.getById(Long.parseLong(httpServletRequest.getParameter(GROUP_ID_PARAMETER)));
        }
        groupDAO.deleteGroup(group);
        LOGGER.info("Group was deleted  groupId =" + group.getId());
        httpServletResponse.sendRedirect(LIST_GROUP_URI);
    }
}
