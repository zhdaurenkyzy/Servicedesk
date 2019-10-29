package com.epam.servicedesk.service;

import com.epam.servicedesk.database.ProjectDAO;
import com.epam.servicedesk.entity.Project;
import com.epam.servicedesk.entity.User;
import com.epam.servicedesk.exception.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.validation.AbstractValidation.isNumeric;

public class ChangeProjectStateService implements Service {
    private static final Logger LOGGER = LogManager.getRootLogger();

    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, ConnectionException {
        ProjectDAO projectDAO = new ProjectDAO();
        Project project = new Project();
        User operator = (User) httpServletRequest.getSession().getAttribute(USER_PARAMETER);
        if(isNumeric(httpServletRequest.getParameter(ID_PARAMETER))) {
            project = projectDAO.getById(Long.parseLong(httpServletRequest.getParameter(ID_PARAMETER)));
        }
        setState(project);
        project.setId(project.getId());
        projectDAO.changeProjectState(project);
        LOGGER.info(String.format("Project state was changed, projectId %d, by operatorId %d", project.getId(),operator.getId()));
        httpServletRequest.getServletContext().getRequestDispatcher(LIST_PROJECT_WITH_STATE_URI +project.isState()).forward(httpServletRequest, httpServletResponse);
    }

    public void setState(Project project) {
        if (project.isState()) {
            project.setState(false);
        } project.setState(true);
    }
}
