package com.epam.servicedesk.service;

import com.epam.servicedesk.database.ProjectDAO;
import com.epam.servicedesk.entity.Project;
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

public class UpdateProjectService implements Service {
    private static final Logger LOGGER = LogManager.getRootLogger();

    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, ValidationException, ConnectionException {
        ProjectDAO projectDAO = new ProjectDAO();
        Project project = new Project();
        project.setName(validateNameGroupOrProjectOrMode(httpServletRequest.getParameter(NAME_PARAMETER)));
        User user = (User) httpServletRequest.getSession().getAttribute(USER_PARAMETER);
        if (isNumeric(httpServletRequest.getParameter(ID_PARAMETER))) {
            project.setId(Long.parseLong(httpServletRequest.getParameter(ID_PARAMETER)));
            if (!project.getName().equals(projectDAO.getByName(project.getName()).getName())) {
                projectDAO.update(project);
                LOGGER.info(String.format("Project name was updated  projectId = %d by userId %d", project.getId(), user.getId()));
            }
        }
        httpServletRequest.getServletContext().getRequestDispatcher(LIST_PROJECT_STATE_TRUE_URI).forward(httpServletRequest, httpServletResponse);
    }
}
