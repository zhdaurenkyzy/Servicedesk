package com.epam.servicedesk.service;

import com.epam.servicedesk.database.*;
import com.epam.servicedesk.entity.Request;
import com.epam.servicedesk.entity.User;
import com.epam.servicedesk.enums.Priority;
import com.epam.servicedesk.exception.ConnectionException;
import com.epam.servicedesk.exception.ValidationException;
import com.epam.servicedesk.validation.FieldsRequestValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.validation.RequestValidation.validateDescriptionOrDecision;
import static com.epam.servicedesk.validation.RequestValidation.validateTheme;

public class UpdateRequestService implements Service {
    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, ValidationException, SQLException, ConnectionException {
        new ListProjectService().execute(httpServletRequest,httpServletResponse);
        RequestDAO requestDAO = new RequestDAO();
        Request request = new Request();
        FieldsRequestValidator fieldsRequestValidator = new FieldsRequestValidator();
        User user = (User)httpServletRequest.getSession().getAttribute(USER_PARAMETER);
        getFieldsRequest(httpServletRequest);
        Long selectStatusId = Long.parseLong(httpServletRequest.getParameter(SELECT_STATUS_PARAMETER));
        Long requestId = Long.parseLong(httpServletRequest.getParameter(REQUEST_ID_PARAMETER));
        Request oldRequest = requestDAO.getById(requestId);
        fieldsRequestValidator.setModeIdRequest(request, user, httpServletRequest.getParameter(SELECT_MODE_PARAMETER));
        fieldsRequestValidator.setGroupIdRequest(request, oldRequest.getGroupId(), user, httpServletRequest.getParameter(SELECT_GROUP_PARAMETER));
        fieldsRequestValidator.setEngineerIdRequest(request,oldRequest.getEngineerId(), user, httpServletRequest.getParameter(SELECT_ENGINEER_ID_PARAMETER));
        fieldsRequestValidator.setProjectIdRequest(request, user, httpServletRequest.getParameter(SELECT_PROJECT_PARAMETER));
        fieldsRequestValidator.setClientIdRequest(request, user, httpServletRequest.getParameter(SELECT_CLIENT_ID_PARAMETER));
        if((selectStatusId!=RESOLVED_STATUS_ID)&&(oldRequest.getAuthorOfDecisionId()==NULL_ID)) {
            setValueRequest(httpServletRequest, request);
            request.setDecision(EMPTY_STRING);
            request.setAuthorOfDecisionId(NULL_ID);
            request.setId(requestId);
            requestDAO.updateRequestById(request, user);
            httpServletResponse.sendRedirect(LIST_REQUEST_URI);
        }
        else if(((selectStatusId==RESOLVED_STATUS_ID)&&(oldRequest.getAuthorOfDecisionId()!=NULL_ID)) |
                ((selectStatusId!=RESOLVED_STATUS_ID)&&(oldRequest.getAuthorOfDecisionId()!=NULL_ID))) {
            setValueRequest(httpServletRequest, request);
            request.setDecision(requestDAO.getById(requestId).getDecision());
            request.setDateOfDecision(oldRequest.getDateOfDecision());
            request.setAuthorOfDecisionId(oldRequest.getAuthorOfDecisionId());
            request.setId(requestId);
            requestDAO.updateRequestById(request, user);
            httpServletResponse.sendRedirect(LIST_REQUEST_URI);
        }
        else {
            httpServletResponse.sendRedirect(LIST_REQUEST_URI);
        }
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

    private void setValueRequest(HttpServletRequest httpServletRequest, Request request) throws ValidationException {
        request.setTheme(validateTheme(httpServletRequest.getParameter(THEME_PARAMETER)));
        request.setDescription(validateDescriptionOrDecision(httpServletRequest.getParameter(DESCRIPTION_PARAMETER)));
        request.setStatusId(Long.parseLong(httpServletRequest.getParameter(SELECT_STATUS_PARAMETER)));
        request.setLevelId(Long.parseLong(httpServletRequest.getParameter(SELECT_LEVEL_PARAMETER)));
        request.setPriority(Priority.getPriority(Long.parseLong(httpServletRequest.getParameter(SELECT_PRIORITY_PARAMETER))));
    }
}
