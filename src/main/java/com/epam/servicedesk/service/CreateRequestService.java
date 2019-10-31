package com.epam.servicedesk.service;

import com.epam.servicedesk.database.RequestDAO;
import com.epam.servicedesk.entity.Request;
import com.epam.servicedesk.entity.User;
import com.epam.servicedesk.enums.Priority;
import com.epam.servicedesk.exception.ConnectionException;
import com.epam.servicedesk.exception.ValidationException;
import com.epam.servicedesk.validation.FieldsRequestValidation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.validation.RequestValidation.validateDescriptionOrDecision;
import static com.epam.servicedesk.validation.RequestValidation.validateTheme;

public class CreateRequestService implements Service {
    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, ValidationException, ConnectionException {
        RequestDAO requestDAO = new RequestDAO();
        Request request = new Request();
        FieldsRequestValidation fieldsRequestValidation = new FieldsRequestValidation();
        User user = (User) httpServletRequest.getSession().getAttribute(USER_PARAMETER);
        if (Long.parseLong(httpServletRequest.getParameter(SELECT_STATUS_PARAMETER)) != RESOLVED_STATUS_ID) {
            request.setTheme(validateTheme(httpServletRequest.getParameter(THEME_PARAMETER)));
            request.setDescription(validateDescriptionOrDecision(httpServletRequest.getParameter(DESCRIPTION_PARAMETER)));
            request.setStatusId(Long.parseLong(httpServletRequest.getParameter(SELECT_STATUS_PARAMETER)));
            request.setLevelId(Long.parseLong(httpServletRequest.getParameter(SELECT_LEVEL_PARAMETER)));
            fieldsRequestValidation.setModeIdRequest(request, user, httpServletRequest.getParameter(SELECT_MODE_PARAMETER));
            request.setPriority(Priority.getPriority(Long.parseLong(httpServletRequest.getParameter(SELECT_PRIORITY_PARAMETER))));
            fieldsRequestValidation.setGroupIdRequest(request, NULL_ID, user, httpServletRequest.getParameter(SELECT_GROUP_PARAMETER));
            fieldsRequestValidation.setEngineerIdRequest(request, NULL_ID, user, httpServletRequest.getParameter(SELECT_ENGINEER_ID_PARAMETER));
            fieldsRequestValidation.setProjectIdRequest(request, user, httpServletRequest.getParameter(SELECT_PROJECT_PARAMETER));
            fieldsRequestValidation.setClientIdRequest(request, user, httpServletRequest.getParameter(SELECT_CLIENT_ID_PARAMETER));
            request.setAuthorOfCreationId(user.getId());
            request.setDateOfCreation(LocalDateTime.now());
            request.setDecision(EMPTY_STRING);
            request.setAuthorOfDecisionId(AUTHOR_OF_DECISION__ID_DEFAULT);
            requestDAO.add(request);
        }
        httpServletResponse.sendRedirect(LIST_REQUEST_URI);
    }
}