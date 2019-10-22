package com.epam.servicedesk.service;

import com.epam.servicedesk.database.RequestDAO;
import com.epam.servicedesk.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.validation.AbstractValidation.isNumeric;

public class SearchRequestService implements Service {
    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, ValidationException {
        RequestDAO requestDAO = new RequestDAO();
        String searchCriteria = httpServletRequest.getParameter(SEARCH_CRITERIA_PARAMETER);
        String searchText = httpServletRequest.getParameter(SEARCH_TEXT_PARAMETER);
        String column = (String)httpServletRequest.getSession().getAttribute(COLUMN_FOR_SEARCH);
        Long userId = (Long) httpServletRequest.getSession().getAttribute(USER_ID_FOR_SEARCH);
        long searchId = NULL_ID;
        long statusId = NULL_ID;
        if(isNumeric(searchText)){
            searchId = Long.parseLong(searchText);
        }
        else searchId=NULL_ID;

        if(httpServletRequest.getSession().getAttribute(STATUS_ID_PARAMETER)!=null) {
            String string = (String)httpServletRequest.getSession().getAttribute(STATUS_ID_PARAMETER);
            statusId = Long.parseLong(string);
        }
        else statusId=NULL_ID;
        if(column==EMPTY_STRING){
            httpServletRequest.setAttribute(REQUEST_STATES_ATTRIBUTE,requestDAO.searchByOperator(searchCriteria, searchText, searchId));
        }
        else {
            httpServletRequest.setAttribute(REQUEST_STATES_ATTRIBUTE, requestDAO.search(column, userId, statusId, searchCriteria, searchText, searchId));
        }
        httpServletRequest.getServletContext().getRequestDispatcher(LIST_REQUEST_JSP).forward(httpServletRequest, httpServletResponse);
    }
}
