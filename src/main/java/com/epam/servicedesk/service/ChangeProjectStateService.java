package com.epam.servicedesk.service;

import com.epam.servicedesk.database.ProjectDAO;
import com.epam.servicedesk.entity.Project;
import com.epam.servicedesk.entity.User;
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
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        ProjectDAO projectDAO = new ProjectDAO();
        Project project = new Project();
        User operator = (User) httpServletRequest.getSession().getAttribute(USER_PARAMETER);
        if(isNumeric(httpServletRequest.getParameter(ID_PARAMETER))) {
            project = projectDAO.getById(Long.parseLong(httpServletRequest.getParameter(ID_PARAMETER)));
        }
        boolean currentState = project.isState();
        if(currentState){
            project.setState(false);
        }
        else project.setState(true);
        project.setId(project.getId());
        projectDAO.changeProjectState(project);
        LOGGER.info("Project state was changed, projectId =" + project.getId() + " by operatorId " + operator.getId());
        httpServletRequest.getServletContext().getRequestDispatcher(LIST_PROJECT_WITH_STATE_URI +project.isState()).forward(httpServletRequest, httpServletResponse);
    }
}
