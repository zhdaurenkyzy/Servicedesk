package com.epam.servicedesk.service;

import com.epam.servicedesk.database.RequestDAO;
import com.epam.servicedesk.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.database.RequestDAO.*;
import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.validation.AbstractValidation.validateId;

public class SearchRequestService implements Service {
    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, ValidationException {
        RequestDAO requestDAO = new RequestDAO();
        String searchCriteria = httpServletRequest.getParameter(SEARCH_CRITERIA_PARAMETER);
        String searchText = httpServletRequest.getParameter(SEARCH_TEXT_PARAMETER);
        switch (searchCriteria){
            case REQUEST_ID_PARAMETER:
                httpServletRequest.setAttribute(REQUEST_STATES_ATTRIBUTE, requestDAO.searchById(validateId(searchText), SEARCH_BY_REQUEST_ID));
                break;
            case REQUEST_THEME_PARAMETER:
                httpServletRequest.setAttribute(REQUEST_STATES_ATTRIBUTE, requestDAO.searchString(searchText, SEARCH_BY_REQUEST_THEME));
                break;
            case REQUEST_STATUS_PARAMETER:
                httpServletRequest.setAttribute(REQUEST_STATES_ATTRIBUTE, requestDAO.searchString(searchText, SEARCH_BY_STATUS_NAME));
                break;
        }
        httpServletRequest.getServletContext().getRequestDispatcher(LIST_REQUEST_JSP).forward(httpServletRequest, httpServletResponse);
    }
}
