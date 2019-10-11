package com.epam.servicedesk.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.MAIN_URI;
import static com.epam.servicedesk.util.ConstantForApp.USER_PARAMETER;

public class LogOutService implements Service {

    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        if (httpServletRequest.getSession().getAttribute(USER_PARAMETER) != null) {
            httpServletRequest.getSession().removeAttribute(USER_PARAMETER);
        }
        httpServletRequest.getSession().invalidate();
        httpServletResponse.sendRedirect(MAIN_URI);
    }
}
