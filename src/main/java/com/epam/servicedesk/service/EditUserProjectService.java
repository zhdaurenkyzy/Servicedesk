package com.epam.servicedesk.service;

import com.epam.servicedesk.database.ProjectDAO;
import com.epam.servicedesk.database.UserDAO;
import com.epam.servicedesk.entity.Project;
import com.epam.servicedesk.entity.User;
import com.epam.servicedesk.exception.ConnectionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.*;

public class EditUserProjectService implements Service {

    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, ConnectionException {
        UserDAO userDAO = new UserDAO();
        ProjectDAO projectDAO = new ProjectDAO();
        if(httpServletRequest.getParameter(SELECT_PROJECT_PARAMETER)!=null){
            User user=userDAO.getById(Long.parseLong(httpServletRequest.getParameter(ID_PARAMETER)));
            Project selectProject = projectDAO.getById(Long.parseLong(httpServletRequest.getParameter(SELECT_PROJECT_PARAMETER)));
            if(userDAO.getProjectIdByUserId(Long.parseLong(httpServletRequest.getParameter(ID_PARAMETER)))!=null){
                userDAO.updateUserInProject(user, selectProject);
            }
            else {
                userDAO.addUserToProject(userDAO.getById(Long.parseLong(httpServletRequest.getParameter(ID_PARAMETER))), selectProject);
            }
        }
        httpServletResponse.sendRedirect(LIST_USER_URI);
    }
}
