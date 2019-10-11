package com.epam.servicedesk.service;

import com.epam.servicedesk.database.RequestDAO;
import com.epam.servicedesk.entity.Request;
import com.epam.servicedesk.entity.User;
import com.epam.servicedesk.enums.Priority;
import com.epam.servicedesk.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.validation.AbstractValidation.isNumeric;
import static com.epam.servicedesk.validation.RequestValidation.validateDescriptionOrDecision;
import static com.epam.servicedesk.validation.RequestValidation.validateTheme;

public class CreateRequestService implements Service {
    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, ValidationException {
        RequestDAO requestDAO = new RequestDAO();
        Request request = new Request();

        if(Long.parseLong(httpServletRequest.getParameter(SELECT_STATUS_PARAMETER))!=4) {
            request.setTheme(validateTheme(httpServletRequest.getParameter(THEME_PARAMETER)));
            request.setDescription(validateDescriptionOrDecision(httpServletRequest.getParameter(DESCRIPTION_PARAMETER)));
            request.setStatusId(Long.parseLong(httpServletRequest.getParameter(SELECT_STATUS_PARAMETER)));
            request.setLevelId(Long.parseLong(httpServletRequest.getParameter(SELECT_LEVEL_PARAMETER)));
            request.setModeId(Long.parseLong(httpServletRequest.getParameter(SELECT_MODE_PARAMETER)));
            request.setPriority(Priority.getPriority(Long.parseLong(httpServletRequest.getParameter(SELECT_PRIORITY_PARAMETER))));
            if (httpServletRequest.getParameter(SELECT_GROUP_PARAMETER) != null&&isNumeric(httpServletRequest.getParameter(SELECT_GROUP_PARAMETER))) {
               request.setGroupId(Long.parseLong(httpServletRequest.getParameter(SELECT_GROUP_PARAMETER)));
            }
            request.setEngineerId(Long.parseLong(httpServletRequest.getParameter(SELECT_ENGINEER_ID_PARAMETER)));
            if (httpServletRequest.getParameter(SELECT_PROJECT_PARAMETER) != null&&isNumeric(httpServletRequest.getParameter(SELECT_PROJECT_PARAMETER))) {
               request.setProjectId(Long.parseLong(httpServletRequest.getParameter(SELECT_PROJECT_PARAMETER)));
            }
            request.setClientId(Long.parseLong(httpServletRequest.getParameter(SELECT_CLIENT_ID_PARAMETER)));
            User user = (User) httpServletRequest.getSession().getAttribute(USER_PARAMETER);
            request.setAuthorOfCreationId(user.getId());
            request.setDateOfCreation(LocalDateTime.now());
            request.setDecision(EMPTY_STRING);
            request.setAuthorOfDecisionId(AUTHOR_OF_DECISION__ID_DEFAULT );
            requestDAO.addNewRequest(request);
        }
            httpServletResponse.sendRedirect(LIST_REQUEST_URI);
    }
}
