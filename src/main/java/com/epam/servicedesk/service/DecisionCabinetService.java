package com.epam.servicedesk.service;

import com.epam.servicedesk.database.RequestDAO;
import com.epam.servicedesk.exception.ConnectionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.validation.AbstractValidation.isNumeric;

public class DecisionCabinetService implements Service {
    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, ConnectionException {
        RequestDAO requestDAO = new RequestDAO();
        String currentUri;
        if ((isNumeric(httpServletRequest.getParameter(REQUEST_ID_PARAMETER))) && (httpServletRequest.getParameter(REQUEST_ID_PARAMETER) != null)) {
            System.out.println((httpServletRequest.getParameter(REQUEST_ID_PARAMETER)));
            httpServletRequest.setAttribute(REQUEST_PARAMETER, requestDAO.getById(Long.parseLong(httpServletRequest.getParameter(REQUEST_ID_PARAMETER))));
            currentUri = DECISION_CABINET_JSP;
        } else {
            currentUri = LIST_REQUEST_URI;
        }
        httpServletRequest.setAttribute(URI, currentUri);
        httpServletRequest.getServletContext().getRequestDispatcher(currentUri).forward(httpServletRequest, httpServletResponse);
    }
}
