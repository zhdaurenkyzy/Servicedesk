package com.epam.servicedesk.service;

import com.epam.servicedesk.database.ModeDAO;
import com.epam.servicedesk.entity.Mode;
import com.epam.servicedesk.exception.ConnectionException;
import com.epam.servicedesk.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.util.ConstantForApp.LIST_MODE_URI;
import static com.epam.servicedesk.validation.GroupAndProjectValidation.validateNameGroupOrProjectOrMode;

public class CreateModeService implements Service {
    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, ValidationException, ConnectionException {
        ModeDAO modeDAO = new ModeDAO();
        Mode mode = new Mode();
        String modeName = validateNameGroupOrProjectOrMode(httpServletRequest.getParameter(MODE_NAME_PARAMETER));
        mode.setName(modeName);
        if (modeDAO.getByName(modeName).getName() != null) {
            httpServletRequest.setAttribute(MESSAGE, MODE_ALREADY_EXISTS_MESSAGE_ID);
            httpServletRequest.getRequestDispatcher(ERROR_JSP).forward(httpServletRequest, httpServletResponse);
        } else {
            modeDAO.add(mode);
            httpServletResponse.sendRedirect(LIST_MODE_URI);
        }
    }
}
