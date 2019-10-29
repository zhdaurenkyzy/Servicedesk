package com.epam.servicedesk.service;

import com.epam.servicedesk.database.ModeDAO;
import com.epam.servicedesk.exception.ConnectionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.util.ConstantForApp.LIST_MODE_JSP;

public class ListModeService implements Service {
    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, ConnectionException {
        ModeDAO modeDAO = new ModeDAO();
        httpServletRequest.setAttribute(MODE_LIST_PARAMETER, modeDAO.getAll());
        httpServletRequest.getServletContext().getRequestDispatcher(LIST_MODE_JSP).forward(httpServletRequest, httpServletResponse);
    }
}
