package com.epam.servicedesk.service;

import com.epam.servicedesk.database.RequestDAO;
import com.epam.servicedesk.entity.User;
import com.epam.servicedesk.enums.Role;
import com.epam.servicedesk.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.database.RequestDAO.*;
import static com.epam.servicedesk.database.RequestDAO.GET_VIEW_ALL_REQUEST_BY_ENGINEER_ID;
import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.validation.AbstractValidation.isNumeric;
import static com.epam.servicedesk.validation.AbstractValidation.validateLong;


public class ListRequestService implements Service {

    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, ValidationException {
        RequestDAO requestDAO = new RequestDAO();
        User user = (User)httpServletRequest.getSession().getAttribute(USER_PARAMETER);
        String clientId = httpServletRequest.getParameter(CLIENT_ID_PARAMETER);
        String statusId = httpServletRequest.getParameter(STATUS_ID_PARAMETER);
        String authorId = httpServletRequest.getParameter(AUTHOR_ID_PARAMETER);
        String engineerId = httpServletRequest.getParameter(ENGINEER_ID_PARAMETER);
        httpServletRequest.setAttribute(ROLE_PARAMETER, Role.OPERATOR);
        if(clientId!=null&&isNumeric(clientId)){
            httpServletRequest.setAttribute(REQUEST_STATES_ATTRIBUTE, requestDAO.getAll(user.getId(), GET_VIEW_ALL_REQUEST_CLIENT_ID));
        }
        else if(statusId!=null&&isNumeric(statusId)){
            httpServletRequest.setAttribute(REQUEST_STATES_ATTRIBUTE, requestDAO.getAllRequestSts(user.getId(), Long.parseLong(statusId), GET_VIEW_ALL_REQUEST_BY_STATUS_ID));
        }
        else if(authorId!=null&&isNumeric(authorId)){
            httpServletRequest.setAttribute(REQUEST_STATES_ATTRIBUTE, requestDAO.getAll(user.getId(), GET_VIEW_ALL_REQUEST_BY_AUTHOR_OF_CREATION_ID));
        }
        else if((engineerId!=null && isNumeric(engineerId)) && Long.parseLong(engineerId)==user.getId()){
            httpServletRequest.setAttribute(REQUEST_STATES_ATTRIBUTE, requestDAO.getAll(user.getId(), GET_VIEW_ALL_REQUEST_BY_ENGINEER_ID));
        }
        else if((engineerId!=null && isNumeric(engineerId)) && Long.parseLong(engineerId)==0){
            httpServletRequest.setAttribute(REQUEST_STATES_ATTRIBUTE, requestDAO.getAll(0, GET_VIEW_ALL_REQUEST_BY_ENGINEER_ID));
        }
        else{
            if(user.getUserRole()== Role.OPERATOR){
                httpServletRequest.setAttribute(REQUEST_STATES_ATTRIBUTE, requestDAO.getAllRequestView());
            }
            else{
                httpServletRequest.setAttribute(REQUEST_STATES_ATTRIBUTE, requestDAO.getAllRequest(user.getId(), GET_VIEW_ALL_REQUEST));
            }
        }
        httpServletRequest.getServletContext().getRequestDispatcher(LIST_REQUEST_JSP).forward(httpServletRequest, httpServletResponse);
    }
}
