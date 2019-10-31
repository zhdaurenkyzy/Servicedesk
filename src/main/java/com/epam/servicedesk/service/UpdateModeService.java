package com.epam.servicedesk.service;

import com.epam.servicedesk.database.ModeDAO;
import com.epam.servicedesk.entity.Mode;
import com.epam.servicedesk.entity.User;
import com.epam.servicedesk.exception.ConnectionException;
import com.epam.servicedesk.exception.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.util.ConstantForApp.LIST_MODE_URI;
import static com.epam.servicedesk.validation.AbstractValidation.isNumeric;
import static com.epam.servicedesk.validation.GroupAndProjectValidation.validateNameGroupOrProjectOrMode;

public class UpdateModeService implements Service {
    private static final Logger LOGGER = LogManager.getRootLogger();

    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, ValidationException, ConnectionException {
        ModeDAO modeDAO = new ModeDAO();
        Mode mode = new Mode();
        mode.setName(validateNameGroupOrProjectOrMode(httpServletRequest.getParameter(NAME_PARAMETER)));
        User user = (User) httpServletRequest.getSession().getAttribute(USER_PARAMETER);
        if (isNumeric(httpServletRequest.getParameter(ID_PARAMETER))) {
            mode.setId(Long.parseLong(httpServletRequest.getParameter(ID_PARAMETER)));
            if (!mode.getName().equals(modeDAO.getByName(mode.getName()).getName())) {
                modeDAO.update(mode);
                LOGGER.info(String.format("Mode name was updated  modeId = %d by userId %d", mode.getId(), user.getId()));
            }
        }
        httpServletRequest.getServletContext().getRequestDispatcher(LIST_MODE_URI).forward(httpServletRequest, httpServletResponse);
    }
}
