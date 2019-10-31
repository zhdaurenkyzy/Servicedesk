package com.epam.servicedesk.service;

import com.epam.servicedesk.database.UserDAO;
import com.epam.servicedesk.entity.User;
import com.epam.servicedesk.exception.ConnectionException;
import com.epam.servicedesk.exception.ValidationException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.validation.UserValidation.*;

public class UpdateUserByOperatorService implements Service {
    private static final Logger LOGGER = LogManager.getRootLogger();

    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, ValidationException, ConnectionException {
        UserDAO userDAO = new UserDAO();
        User user = new User();
        User operator = (User) httpServletRequest.getSession().getAttribute(USER_PARAMETER);
        setFields(user, httpServletRequest);
        if (httpServletRequest.getParameter(PASSWORD_PARAMETER).equals(EMPTY_STRING)) {
            user.setPassword(userDAO.getById(Long.parseLong(httpServletRequest.getParameter(ID_PARAMETER))).getPassword());
        } else {
            user.setPassword(DigestUtils.md5Hex(validatePassword(httpServletRequest.getParameter(PASSWORD_PARAMETER))));
        }
        user.setUserRole(userDAO.getById(Long.parseLong(httpServletRequest.getParameter(ID_PARAMETER))).getUserRole());
        userDAO.update(user);
        LOGGER.info(String.format("User was updated, userId =  = %d by operatorId %d", user.getId(), operator.getId()));
        httpServletResponse.sendRedirect(LIST_USER_URI);
    }

    private void setFields(User user, HttpServletRequest httpServletRequest) throws ValidationException {
        user.setName(validateName(httpServletRequest.getParameter(NAME_PARAMETER)));
        user.setPosition(validatePosition(httpServletRequest.getParameter(POSITION_PARAMETER)));
        user.setPhone(validatePhone(httpServletRequest.getParameter(PHONE_PARAMETER)));
        user.setMobile(validatePhone(httpServletRequest.getParameter(MOBILE_PARAMETER)));
        user.setMail(validateMail(httpServletRequest.getParameter(MAIL_PARAMETER)));
        user.setId(Long.parseLong(httpServletRequest.getParameter(ID_PARAMETER)));
    }
}