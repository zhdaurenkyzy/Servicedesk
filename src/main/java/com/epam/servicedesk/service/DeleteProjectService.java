package com.epam.servicedesk.service;

import com.epam.servicedesk.database.ProjectDAO;
import com.epam.servicedesk.entity.Project;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.ID_PARAMETER;
import static com.epam.servicedesk.util.ConstantForApp.LIST_PROJECT_STATE_TRUE_URI;
import static com.epam.servicedesk.validation.AbstractValidation.isNumeric;

public class DeleteProjectService implements Service {
    private static final Logger LOGGER = LogManager.getRootLogger();

    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

        ProjectDAO projectDAO = new ProjectDAO();
        Project project = new Project();
        if(isNumeric(httpServletRequest.getParameter(ID_PARAMETER))) {
            project = projectDAO.getById(Long.parseLong(httpServletRequest.getParameter(ID_PARAMETER)));
        }
        projectDAO.deleteProject(project);
        LOGGER.info("Project was deleted  projectId =" + project.getId());
        httpServletResponse.sendRedirect(LIST_PROJECT_STATE_TRUE_URI);
    }
}
