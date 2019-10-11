package com.epam.servicedesk.service;

import com.epam.servicedesk.database.ProjectDAO;
import com.epam.servicedesk.entity.Project;
import com.epam.servicedesk.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.validation.AbstractValidation.isNumeric;
import static com.epam.servicedesk.validation.GroupAndProjectValidation.validateNameGroupOrProject;

public class UpdateProjectService implements Service {

    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, ValidationException {
        ProjectDAO projectDAO = new ProjectDAO();
        Project project = new Project();
        project.setName(validateNameGroupOrProject(httpServletRequest.getParameter(NAME_PARAMETER)));
        if(isNumeric(httpServletRequest.getParameter(ID_PARAMETER))) {
            project.setId(Long.parseLong(httpServletRequest.getParameter(ID_PARAMETER)));
            if (!project.getName().equals(projectDAO.getByName(project.getName()).getName())) {
                projectDAO.updateProject(project);
            }
        }
        httpServletRequest.getServletContext().getRequestDispatcher(LIST_PROJECT_STATE_TRUE_URI).forward(httpServletRequest, httpServletResponse);
    }
}
