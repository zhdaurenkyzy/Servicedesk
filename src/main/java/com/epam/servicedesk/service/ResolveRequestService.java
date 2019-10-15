package com.epam.servicedesk.service;

import com.epam.servicedesk.database.RequestDAO;
import com.epam.servicedesk.entity.Request;
import com.epam.servicedesk.entity.User;
import com.epam.servicedesk.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.validation.RequestValidation.validateDescriptionOrDecision;

public class ResolveRequestService implements Service {

    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, ValidationException {
        RequestDAO requestDAO = new RequestDAO();
        Request request = requestDAO.getRequestById(Long.parseLong(httpServletRequest.getParameter(REQUEST_ID_PARAMETER)));
        request.setStatusId(RESOLVED_STATUS_ID);
        request.setDecision(validateDescriptionOrDecision(httpServletRequest.getParameter(DECISION_PARAMETER)));
        User user = (User) httpServletRequest.getSession().getAttribute(USER_PARAMETER);
        request.setAuthorOfDecisionId(user.getId());
        request.setDateOfDecision(LocalDateTime.now());
        requestDAO.addDecisionByRequestById(request, user);
        httpServletResponse.sendRedirect(LIST_REQUEST_URI);
    }
}
