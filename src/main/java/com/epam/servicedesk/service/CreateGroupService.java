package com.epam.servicedesk.service;

import com.epam.servicedesk.database.GroupDAO;
import com.epam.servicedesk.entity.Group;
import com.epam.servicedesk.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.validation.GroupAndProjectValidation.validateNameGroupOrProject;

public class CreateGroupService implements Service {

    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, ValidationException {
        GroupDAO groupDAO = new GroupDAO();
        String groupName = httpServletRequest.getParameter(GROUP_NAME_PARAMETER);
        Group group = new Group();
        group.setName(validateNameGroupOrProject(groupName));
        if(groupDAO.getByName(groupName).getName()!=null){
            httpServletRequest.getSession().setAttribute(MESSAGE, GROUP_ALREADY_EXISTS_MESSAGE_ID);
            httpServletRequest.getRequestDispatcher(ERROR_JSP).forward(httpServletRequest, httpServletResponse);
        }
        else {
            groupDAO.add(group);
            httpServletRequest.getRequestDispatcher(LIST_GROUP_URI).forward(httpServletRequest, httpServletResponse);

        }
    }
}
