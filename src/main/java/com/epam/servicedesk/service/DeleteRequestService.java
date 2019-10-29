package com.epam.servicedesk.service;

import com.epam.servicedesk.database.RequestDAO;
import com.epam.servicedesk.entity.Request;
import com.epam.servicedesk.entity.User;
import com.epam.servicedesk.exception.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.validation.AbstractValidation.isNumeric;

public class DeleteRequestService implements Service {
    private static final Logger LOGGER = LogManager.getRootLogger();

    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws SQLException, ServletException, IOException, ConnectionException {
        RequestDAO requestDAO = new RequestDAO();
        Request request = new Request();
        User operator = (User)httpServletRequest.getSession().getAttribute(USER_PARAMETER);
        if(isNumeric(httpServletRequest.getParameter(REQUEST_ID_PARAMETER))) {
            request = requestDAO.getById(Long.parseLong(httpServletRequest.getParameter(REQUEST_ID_PARAMETER)));
        }
        User user = (User) httpServletRequest.getSession().getAttribute(USER_PARAMETER);
        if(user.getUserRole().getId()==OPERATOR_ROLE_ID_DEFAULT) {
            requestDAO.delete(request);
            LOGGER.info(String.format("Request was deleted, requestId = %d, by operatorId %d", request.getId(), operator.getId()));
        }
        httpServletResponse.sendRedirect(LIST_REQUEST_URI);
    }
}
