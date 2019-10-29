package com.epam.servicedesk.service;

import com.epam.servicedesk.database.ProjectDAO;
import com.epam.servicedesk.entity.Project;
import com.epam.servicedesk.exception.ConnectionException;
import com.epam.servicedesk.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.validation.GroupAndProjectValidation.validateNameGroupOrProjectOrMode;

public class CreateProjectService implements Service {
    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, ValidationException, ConnectionException {
        ProjectDAO projectDAO = new ProjectDAO();
        Project project = new Project();
        String projectName = validateNameGroupOrProjectOrMode(httpServletRequest.getParameter(PROJECT_NAME_PARAMETER));
        project.setName(projectName);
        if(projectDAO.getByName(projectName).getName()!=null){
            httpServletRequest.setAttribute(MESSAGE, PROJECT_ALREADY_EXISTS_MESSAGE_ID);
            httpServletRequest.getRequestDispatcher(ERROR_JSP).forward(httpServletRequest, httpServletResponse);
        }
        else {
            projectDAO.add(project);
            httpServletResponse.sendRedirect(LIST_PROJECT_STATE_TRUE_URI);
        }
    }
}
