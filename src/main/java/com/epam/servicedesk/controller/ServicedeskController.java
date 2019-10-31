package com.epam.servicedesk.controller;

import com.epam.servicedesk.exception.ValidationException;
import com.epam.servicedesk.service.ServiceFactory;
import com.epam.servicedesk.service.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.servicedesk.util.ConstantForApp.*;

public class ServicedeskController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getRootLogger();

    @Override
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        String stringUri = httpServletRequest.getRequestURI();
        Service service = serviceFactory.getService(stringUri);
        try {
            service.execute(httpServletRequest, httpServletResponse);
        } catch (SQLException e) {
            LOGGER.error(CANNOT_EXECUTE_DAO_METHOD, e);
        } catch (ValidationException e) {
            LOGGER.error(e.getMessage());
            httpServletRequest.getSession().setAttribute(MESSAGE, e.getMessage());
            httpServletRequest.getRequestDispatcher(ERROR_JSP).forward(httpServletRequest, httpServletResponse);
        } catch (Exception e) {
            LOGGER.error(e);
            throw new ServletException(e);
        }
    }
}

