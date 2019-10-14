package com.epam.servicedesk.service;

import com.epam.servicedesk.database.*;
import com.epam.servicedesk.enums.Priority;
import com.epam.servicedesk.enums.Role;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.validation.AbstractValidation.isNumeric;

public class RequestCabinetService implements Service {

    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        RequestDAO requestDAO = new RequestDAO();
        getFieldsRequest(httpServletRequest);
        new ListProjectService().execute(httpServletRequest,httpServletResponse);
        if((isNumeric(httpServletRequest.getParameter(REQUEST_ID_PARAMETER)))&&(httpServletRequest.getParameter(REQUEST_ID_PARAMETER)!=null)) {
            httpServletRequest.setAttribute(REQUEST_PARAMETER, requestDAO.getRequestById(Long.parseLong(httpServletRequest.getParameter(REQUEST_ID_PARAMETER))));
            httpServletRequest.setAttribute(URI, UPDATE_REQUEST_URI);
        }
        else{
            httpServletRequest.setAttribute(URI, CREATE_REQUEST_URI);
        }
        httpServletRequest.getServletContext().getRequestDispatcher(REQUEST_CABINET_JSP).forward(httpServletRequest, httpServletResponse);
    }

    private void getFieldsRequest(HttpServletRequest httpServletRequest) {
        StatusDAO statusDAO = new StatusDAO();
        ModeDAO modeDAO = new ModeDAO();
        LevelDAO levelDAO = new LevelDAO();
        GroupDAO groupDAO = new GroupDAO();
        httpServletRequest.setAttribute(STATUS_LIST_PARAMETER, statusDAO.getAllStatus());
        httpServletRequest.setAttribute(MODE_LIST_PARAMETER, modeDAO.getAllMode());
        httpServletRequest.setAttribute(LEVEL_LIST_PARAMETER, levelDAO.getAllLevel());
        httpServletRequest.setAttribute(GROUP_LIST_PARAMETER, groupDAO.getAllGroup());
        httpServletRequest.setAttribute(ROLE_PARAMETER, Role.CLIENT);
        httpServletRequest.setAttribute(PRIORITY_LIST_PARAMETER, Priority.values());
    }
}
