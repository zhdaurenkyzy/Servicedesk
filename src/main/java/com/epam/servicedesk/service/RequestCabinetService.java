package com.epam.servicedesk.service;

import com.epam.servicedesk.database.*;
import com.epam.servicedesk.enums.Priority;
import com.epam.servicedesk.exception.ConnectionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.validation.AbstractValidation.isNumeric;

public class RequestCabinetService implements Service {

    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, ConnectionException {
        RequestDAO requestDAO = new RequestDAO();
        getFieldsRequest(httpServletRequest);
        new ListProjectService().execute(httpServletRequest,httpServletResponse);
        if((isNumeric(httpServletRequest.getParameter(REQUEST_ID_PARAMETER)))&&(httpServletRequest.getParameter(REQUEST_ID_PARAMETER)!=null)) {
            httpServletRequest.setAttribute(REQUEST_PARAMETER, requestDAO.getById(Long.parseLong(httpServletRequest.getParameter(REQUEST_ID_PARAMETER))));
            httpServletRequest.setAttribute(URI, UPDATE_REQUEST_URI);
        }
        else{
            httpServletRequest.setAttribute(URI, CREATE_REQUEST_URI);
        }
        httpServletRequest.getServletContext().getRequestDispatcher(REQUEST_CABINET_JSP).forward(httpServletRequest, httpServletResponse);
    }

    private void getFieldsRequest(HttpServletRequest httpServletRequest) throws ConnectionException {
        StatusDAO statusDAO = new StatusDAO();
        ModeDAO modeDAO = new ModeDAO();
        LevelDAO levelDAO = new LevelDAO();
        GroupDAO groupDAO = new GroupDAO();
        httpServletRequest.setAttribute(STATUS_LIST_PARAMETER, statusDAO.getAll());
        httpServletRequest.setAttribute(MODE_LIST_PARAMETER, modeDAO.getAll());
        httpServletRequest.setAttribute(LEVEL_LIST_PARAMETER, levelDAO.getAll());
        httpServletRequest.setAttribute(GROUP_LIST_PARAMETER, groupDAO.getAll());
        httpServletRequest.setAttribute(PRIORITY_LIST_PARAMETER, Priority.values());
    }
}
