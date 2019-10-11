package com.epam.servicedesk.service;

import com.epam.servicedesk.database.*;
import com.epam.servicedesk.entity.Request;
import com.epam.servicedesk.entity.User;
import com.epam.servicedesk.enums.Priority;
import com.epam.servicedesk.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.validation.AbstractValidation.isNumeric;
import static com.epam.servicedesk.validation.RequestValidation.validateDescriptionOrDecision;
import static com.epam.servicedesk.validation.RequestValidation.validateTheme;

public class UpdateRequestService implements Service {
    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, ValidationException {
        new ListProjectService().execute(httpServletRequest,httpServletResponse);
        RequestDAO requestDAO = new RequestDAO();
        Request request = new Request();
        User user = (User)httpServletRequest.getSession().getAttribute(USER_PARAMETER);
        getFieldsRequest(httpServletRequest);
        Long selectStatusId = Long.parseLong(httpServletRequest.getParameter(SELECT_STATUS_PARAMETER));
        Long requestId = Long.parseLong(httpServletRequest.getParameter(REQUEST_ID_PARAMETER));
        Request currentRequest = requestDAO.getRequestById(requestId);

        if((selectStatusId!=4)&&(currentRequest.getAuthorOfDecisionId()==0)) {
            setValueRequest(httpServletRequest, request);
            if(httpServletRequest.getParameter(SELECT_GROUP_PARAMETER)!=null) {
                String selectGroup = httpServletRequest.getParameter(SELECT_GROUP_PARAMETER);
                if(isNumeric(selectGroup)) {
                    request.setGroupId(Long.parseLong(selectGroup));
                }
            }
            request.setEngineerId(Long.parseLong(httpServletRequest.getParameter(SELECT_ENGINEER_ID_PARAMETER)));
            if(httpServletRequest.getParameter(SELECT_PROJECT_PARAMETER)!=null) {
                String selectProject = httpServletRequest.getParameter(SELECT_PROJECT_PARAMETER);
                if(isNumeric(selectProject)) {
                    request.setProjectId(Long.parseLong(selectProject));
                }
            }
            request.setClientId(Long.parseLong(httpServletRequest.getParameter(SELECT_CLIENT_ID_PARAMETER)));
            request.setDecision(EMPTY_STRING);
            request.setAuthorOfDecisionId(0);
            request.setId(requestId);
            requestDAO.updateRequestById(request, user);
            httpServletResponse.sendRedirect(LIST_REQUEST_URI);
        }
        else if(((selectStatusId==4)&&(currentRequest.getAuthorOfDecisionId()!=0)) |
                ((selectStatusId!=4)&&(currentRequest.getAuthorOfDecisionId()!=0))) {
            setValueRequest(httpServletRequest, request);
            if(httpServletRequest.getParameter(SELECT_GROUP_PARAMETER)!=null) {
                if(isNumeric(httpServletRequest.getParameter(SELECT_GROUP_PARAMETER))) {
                    request.setGroupId(Long.parseLong(httpServletRequest.getParameter(SELECT_GROUP_PARAMETER)));
                }
            }
            request.setEngineerId(Long.parseLong(httpServletRequest.getParameter(SELECT_ENGINEER_ID_PARAMETER)));
            if(httpServletRequest.getParameter(SELECT_PROJECT_PARAMETER)!=null) {
                if(isNumeric(httpServletRequest.getParameter(SELECT_PROJECT_PARAMETER))) {
                    request.setProjectId(Long.parseLong(httpServletRequest.getParameter(SELECT_PROJECT_PARAMETER)));
                }
            }
            request.setClientId(Long.parseLong(httpServletRequest.getParameter(SELECT_CLIENT_ID_PARAMETER)));
            request.setDecision(requestDAO.getRequestById(requestId).getDecision());
            request.setDateOfDecision(currentRequest.getDateOfDecision());
            request.setAuthorOfDecisionId(currentRequest.getAuthorOfDecisionId());
            request.setId(requestId);
            requestDAO.updateRequestById(request, user);
            httpServletResponse.sendRedirect(LIST_REQUEST_URI);
        }
        else {
            httpServletResponse.sendRedirect(LIST_REQUEST_URI);
        }
    }

    private void getFieldsRequest(HttpServletRequest httpServletRequest) {
        StatusDAO statusDAO = new StatusDAO();
        ModeDAO modeDAO = new ModeDAO();
        LevelDAO levelDAO = new LevelDAO();
        GroupDAO groupDAO = new GroupDAO();
        httpServletRequest.setAttribute(STATUS_LIST_PARAMETER, statusDAO.getAllStatus());
        httpServletRequest.setAttribute(MODE_LIST_PARAMETER, modeDAO.getAllMode());
        httpServletRequest.setAttribute(LEVEL_LIST_PARAMETER, levelDAO.getAllLevel());
        httpServletRequest.setAttribute(GROUP__LIST_PARAMETER, groupDAO.getAllGroup());
        httpServletRequest.setAttribute(PRIORITY_LIST_PARAMETER, Priority.values());
    }

    private void setValueRequest(HttpServletRequest httpServletRequest, Request request) throws ValidationException {
        request.setTheme(validateTheme(httpServletRequest.getParameter(THEME_PARAMETER)));
        request.setDescription(validateDescriptionOrDecision(httpServletRequest.getParameter(DESCRIPTION_PARAMETER)));
        request.setStatusId(Long.parseLong(httpServletRequest.getParameter(SELECT_STATUS_PARAMETER)));
        request.setLevelId(Long.parseLong(httpServletRequest.getParameter(SELECT_LEVEL_PARAMETER)));
        request.setModeId(Long.parseLong(httpServletRequest.getParameter(SELECT_MODE_PARAMETER)));
        request.setPriority(Priority.getPriority(Long.parseLong(httpServletRequest.getParameter(SELECT_PRIORITY_PARAMETER))));

    }
}
