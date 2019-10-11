package com.epam.servicedesk.controller;

import com.epam.servicedesk.exception.ValidationException;
import com.epam.servicedesk.service.Factory;
import com.epam.servicedesk.service.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.ERROR_JSP;
import static com.epam.servicedesk.util.ConstantForApp.MESSAGE;

public class ServicedeskController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getRootLogger();

    @Override
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        Factory factory = Factory.getInstance();
        String stringUri = httpServletRequest.getRequestURI();
        Service service = factory.getService(stringUri);
        try {
            service.execute(httpServletRequest, httpServletResponse);
        } catch (ValidationException e) {
            LOGGER.error(e);
            httpServletRequest.getSession().setAttribute(MESSAGE, e.getMessage());
            httpServletRequest.getRequestDispatcher(ERROR_JSP).forward(httpServletRequest, httpServletResponse);
        }catch (Exception e) {
            LOGGER.error(e);
            throw new ServletException(e);
        }
    }
}

