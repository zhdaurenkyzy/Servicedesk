package com.epam.servicedesk.service;

import com.epam.servicedesk.database.ProjectDAO;
import com.epam.servicedesk.exception.ConnectionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.validation.AbstractValidation.isNumeric;

public class ProjectCabinetService implements Service {

    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, ConnectionException {
        ProjectDAO projectDAO = new ProjectDAO();
        if(isNumeric(httpServletRequest.getParameter(PROJECT_ID_GET_METHOD_PARAMETER))){
            httpServletRequest.setAttribute(PROJECT_PARAMETER, projectDAO.getById(Long.parseLong(httpServletRequest.getParameter(PROJECT_ID_GET_METHOD_PARAMETER))));
            httpServletRequest.setAttribute(PROJECT_ID_GET_METHOD_PARAMETER, Long.parseLong(httpServletRequest.getParameter(PROJECT_ID_GET_METHOD_PARAMETER)));
        }
        httpServletRequest.getServletContext().getRequestDispatcher(PROJECT_CABINET_JSP).forward(httpServletRequest, httpServletResponse);
    }
}
