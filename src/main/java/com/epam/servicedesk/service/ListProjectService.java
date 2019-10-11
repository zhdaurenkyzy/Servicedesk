package com.epam.servicedesk.service;

import com.epam.servicedesk.database.ProjectDAO;
import com.epam.servicedesk.entity.Project;
import com.epam.servicedesk.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.*;

public class ListProjectService implements Service {

    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        ProjectDAO projectDAO = new ProjectDAO();
        boolean projectState = Boolean.parseBoolean(httpServletRequest.getParameter(STATE_PARAMETER));
        if (httpServletRequest.getParameter(STATE_PARAMETER) == null) {
            httpServletRequest.setAttribute(PROJECT_LIST_PARAMETER, projectDAO.getAllProjectByState(true));
        } else {
            httpServletRequest.setAttribute(PROJECT_LIST_PARAMETER, projectDAO.getAllProjectByState(projectState));
            httpServletRequest.setAttribute(STATE_PARAMETER, projectState);
            httpServletRequest.getServletContext().getRequestDispatcher(LIST_PROJECT_JSP).forward(httpServletRequest, httpServletResponse);
        }
    }
}
