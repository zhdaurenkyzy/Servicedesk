package com.epam.servicedesk.service;

import com.epam.servicedesk.database.GroupDAO;
import com.epam.servicedesk.entity.Group;
import com.epam.servicedesk.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.validation.AbstractValidation.isNumeric;
import static com.epam.servicedesk.validation.GroupAndProjectValidation.validateNameGroupOrProject;

public class UpdateGroupNameService implements Service {

    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, ValidationException {
        GroupDAO groupDAO = new GroupDAO();
        Group group = new Group();
        group.setName(validateNameGroupOrProject(httpServletRequest.getParameter(GROUP_NAME_PARAMETER)));
        if(isNumeric(httpServletRequest.getParameter(GROUP_ID_PARAMETER))) {
            group.setId(Long.parseLong(httpServletRequest.getParameter(GROUP_ID_PARAMETER)));
            if(!group.getName().equals(groupDAO.getByName(group.getName()).getName())) {
                groupDAO.updateGroup(group);
            }
        }
        httpServletRequest.getServletContext().getRequestDispatcher(LIST_GROUP_URI).forward(httpServletRequest, httpServletResponse);
    }
}
