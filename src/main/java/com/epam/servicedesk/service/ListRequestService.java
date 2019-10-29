package com.epam.servicedesk.service;

import com.epam.servicedesk.database.RequestDAO;
import com.epam.servicedesk.entity.User;
import com.epam.servicedesk.enums.Role;
import com.epam.servicedesk.exception.ConnectionException;
import com.epam.servicedesk.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.database.RequestDAO.*;
import static com.epam.servicedesk.database.RequestDAO.GET_VIEW_ALL_REQUEST_BY_ENGINEER_ID;
import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.validation.AbstractValidation.isNumeric;

public class ListRequestService implements Service {

    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, ValidationException, ConnectionException {
        RequestDAO requestDAO = new RequestDAO();
        User user = (User)httpServletRequest.getSession().getAttribute(USER_PARAMETER);
        String clientId = httpServletRequest.getParameter(CLIENT_ID_PARAMETER);
        String statusId = httpServletRequest.getParameter(STATUS_ID_PARAMETER);
        String authorId = httpServletRequest.getParameter(AUTHOR_ID_PARAMETER);
        String engineerId = httpServletRequest.getParameter(ENGINEER_ID_PARAMETER);
        httpServletRequest.setAttribute(ROLE_PARAMETER, Role.OPERATOR);
        if(clientId!=null&&isNumeric(clientId)){
            httpServletRequest.setAttribute(REQUEST_STATES_ATTRIBUTE, requestDAO.getAllByUser(user.getId(), GET_VIEW_ALL_REQUEST_CLIENT_ID));
            httpServletRequest.getSession().setAttribute(COLUMN_FOR_SEARCH, REQUEST_CLIENT_USER_ID);
            httpServletRequest.getSession().setAttribute(USER_ID_FOR_SEARCH, user.getId());
        }
        else if(statusId!=null&&isNumeric(statusId)){
            httpServletRequest.setAttribute(REQUEST_STATES_ATTRIBUTE, requestDAO.getAllRequestByStatus(user.getId(), Long.parseLong(statusId), GET_VIEW_ALL_REQUEST_BY_STATUS_ID));
            httpServletRequest.getSession().setAttribute(COLUMN_FOR_SEARCH, REQUEST_STATUS_ID);
            httpServletRequest.getSession().setAttribute(STATUS_ID_PARAMETER, statusId);
            httpServletRequest.getSession().setAttribute(USER_ID_FOR_SEARCH, user.getId());
        }
        else if(authorId!=null&&isNumeric(authorId)){
            httpServletRequest.setAttribute(REQUEST_STATES_ATTRIBUTE, requestDAO.getAllByUser(user.getId(), GET_VIEW_ALL_REQUEST_BY_AUTHOR_OF_CREATION_ID));
            httpServletRequest.getSession().setAttribute(COLUMN_FOR_SEARCH, REQUEST_AUTHOR_OF_CREATION);
            httpServletRequest.getSession().setAttribute(USER_ID_FOR_SEARCH, user.getId());
        }
        else if((engineerId!=null && isNumeric(engineerId)) && Long.parseLong(engineerId)==user.getId()){
            httpServletRequest.setAttribute(REQUEST_STATES_ATTRIBUTE, requestDAO.getAllByUser(user.getId(), GET_VIEW_ALL_REQUEST_BY_ENGINEER_ID));
            httpServletRequest.getSession().setAttribute(COLUMN_FOR_SEARCH,  REQUEST_ENGINEER_USER_ID);
            httpServletRequest.getSession().setAttribute(USER_ID_FOR_SEARCH, user.getId());
        }
        else if((engineerId!=null && isNumeric(engineerId)) && Long.parseLong(engineerId)==NULL_ID ){
            httpServletRequest.setAttribute(REQUEST_STATES_ATTRIBUTE, requestDAO.getAllByUser(NULL_ID , GET_VIEW_ALL_REQUEST_BY_ENGINEER_ID));
            httpServletRequest.getSession().setAttribute(COLUMN_FOR_SEARCH,  REQUEST_ENGINEER_USER_ID);
            httpServletRequest.getSession().setAttribute(USER_ID_FOR_SEARCH, NULL_ID);
        }
        else{
            if(user.getUserRole()== Role.OPERATOR){
                httpServletRequest.setAttribute(REQUEST_STATES_ATTRIBUTE, requestDAO.getAllRequestView());
                httpServletRequest.getSession().setAttribute(COLUMN_FOR_SEARCH,  EMPTY_STRING);
            }
            else{
                httpServletRequest.setAttribute(REQUEST_STATES_ATTRIBUTE, requestDAO.getAllRequest(user.getId(), GET_VIEW_ALL_REQUEST));
                httpServletRequest.getSession().setAttribute(COLUMN_FOR_SEARCH,  USER_PARAMETER);
                httpServletRequest.getSession().setAttribute(USER_ID_FOR_SEARCH, user.getId());
            }
        }
        httpServletRequest.getServletContext().getRequestDispatcher(LIST_REQUEST_JSP).forward(httpServletRequest, httpServletResponse);
    }
}
