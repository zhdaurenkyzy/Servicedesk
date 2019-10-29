package com.epam.servicedesk.service;

import com.epam.servicedesk.database.ProjectDAO;
import com.epam.servicedesk.database.UserDAO;
import com.epam.servicedesk.enums.Role;
import com.epam.servicedesk.exception.ConnectionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.validation.AbstractValidation.isNumeric;

public class EditUserByOperator implements Service {

    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, ConnectionException {
        UserDAO userDAO = new UserDAO();
        ProjectDAO projectDAO = new ProjectDAO();
        if(httpServletRequest.getParameter(USER_ID_FROM_OPTIONS_PARAMETER)!=null&&isNumeric(httpServletRequest.getParameter(USER_ID_FROM_OPTIONS_PARAMETER ))){
            httpServletRequest.setAttribute(USER_FROM_OPTIONS_PARAMETER , userDAO.getById(Long.parseLong(httpServletRequest.getParameter(USER_ID_FROM_OPTIONS_PARAMETER ))));
            httpServletRequest.setAttribute(USER_ID_FROM_OPTIONS_PARAMETER , Long.parseLong(httpServletRequest.getParameter(USER_ID_FROM_OPTIONS_PARAMETER)));
            if(userDAO.getById(Long.parseLong(httpServletRequest.getParameter(USER_ID_FROM_OPTIONS_PARAMETER))).getUserRole()== Role.CLIENT){
                httpServletRequest.setAttribute(LIST_PROJECTS_PARAMETER, projectDAO.getAllProjectByState(true));
                httpServletRequest.setAttribute(USER_OF_PROJECT_PARAMETER, userDAO.getProjectIdByUserId(Long.parseLong(httpServletRequest.getParameter(USER_ID_FROM_OPTIONS_PARAMETER))));
            }
            httpServletRequest.setAttribute(URI, UPDATE_USER_BY_OPERATOR_URI);
        }
        else if(httpServletRequest.getParameter(USER_ID_FROM_OPTIONS_PARAMETER)==null){
            httpServletRequest.setAttribute(URI, REGISTRATION_USER_BY_OPERATOR_URI);
        }
        httpServletRequest.getServletContext().getRequestDispatcher(EDIT_USER_BY_OPERATOR_JSP).forward(httpServletRequest, httpServletResponse);
    }
}
