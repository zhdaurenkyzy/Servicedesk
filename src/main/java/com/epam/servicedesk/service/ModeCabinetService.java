package com.epam.servicedesk.service;

import com.epam.servicedesk.database.ModeDAO;
import com.epam.servicedesk.database.ProjectDAO;
import com.epam.servicedesk.exception.ConnectionException;
import com.epam.servicedesk.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.validation.AbstractValidation.isNumeric;

public class ModeCabinetService implements Service {
    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, ValidationException, ConnectionException {
        ModeDAO modeDAO = new ModeDAO();
        if(isNumeric(httpServletRequest.getParameter(MODE_ID_GET_METHOD_PARAMETER))){
            httpServletRequest.setAttribute(MODE_PARAMETER, modeDAO.getById(Long.parseLong(httpServletRequest.getParameter(MODE_ID_GET_METHOD_PARAMETER))));
            httpServletRequest.setAttribute(MODE_ID_GET_METHOD_PARAMETER, Long.parseLong(httpServletRequest.getParameter(MODE_ID_GET_METHOD_PARAMETER)));
        }
        httpServletRequest.getServletContext().getRequestDispatcher(MODE_CABINET_JSP).forward(httpServletRequest, httpServletResponse);
    }
}

