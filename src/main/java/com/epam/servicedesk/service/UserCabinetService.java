package com.epam.servicedesk.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.*;

public class UserCabinetService implements Service {

    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String uri="";
        if(httpServletRequest.getSession().getAttribute(USER_PARAMETER)!=null) {
           uri = UPDATE_USER_URI;
        }
        else if(httpServletRequest.getSession().getAttribute(USER_PARAMETER)==null){
            uri = REGISTRATION_URI;
        }
        httpServletRequest.setAttribute(URI, uri );
        httpServletRequest.getServletContext().getRequestDispatcher(USER_CABINET_JSP).forward(httpServletRequest, httpServletResponse);
    }
}
