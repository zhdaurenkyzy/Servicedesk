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
import static com.epam.servicedesk.validation.AbstractValidation.isNumeric;

public class DeleteModeService implements Service {
    private static final Logger LOGGER = LogManager.getRootLogger();

    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, ValidationException, ConnectionException {
        ModeDAO modeDAO = new ModeDAO();
        Mode mode = new Mode();
        User operator = (User) httpServletRequest.getSession().getAttribute(USER_PARAMETER);
        if (isNumeric(httpServletRequest.getParameter(ID_PARAMETER))) {
            mode = modeDAO.getById(Long.parseLong(httpServletRequest.getParameter(ID_PARAMETER)));
        }
        modeDAO.delete(mode);
        LOGGER.info(String.format("Mode was deleted,  modeId = %d, by operatorId %d", mode.getId(), operator.getId()));
        httpServletResponse.sendRedirect(LIST_MODE_URI);
    }
}
