package com.epam.servicedesk.service;

import com.epam.servicedesk.database.HistoryDAO;
import com.epam.servicedesk.database.RequestDAO;
import com.epam.servicedesk.entity.Request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.*;

public class HistoryService implements Service {

    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        HistoryDAO historyDAO = new HistoryDAO();
        RequestDAO requestDAO = new RequestDAO();
        if (httpServletRequest.getParameter(REQUEST_ID_PARAMETER) != null) {
            Request request = requestDAO.getRequestById(Long.parseLong(httpServletRequest.getParameter(REQUEST_ID_PARAMETER)));
            httpServletRequest.setAttribute(HISTORIES_PARAMETER, historyDAO.getByRequestId(request));
            httpServletRequest.setAttribute(REQUEST_ID_FOR_PAGE_PARAMETER, request.getId());
        }
            httpServletRequest.getServletContext().getRequestDispatcher(HISTORY_OF_REQUEST_JSP).forward(httpServletRequest, httpServletResponse);

    }
}
