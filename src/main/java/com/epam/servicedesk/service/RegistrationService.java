package com.epam.servicedesk.service;

import com.epam.servicedesk.database.UserDAO;
import com.epam.servicedesk.entity.User;
import com.epam.servicedesk.enums.Role;
import com.epam.servicedesk.exception.ConnectionException;
import com.epam.servicedesk.exception.ValidationException;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.validation.UserValidation.*;

public class RegistrationService implements Service {

    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, ValidationException, ConnectionException {
        UserDAO userDAO = new UserDAO();
        User user = new User();
        user.setName(validateName(httpServletRequest.getParameter(NAME_PARAMETER)));
        user.setPosition(validatePosition(httpServletRequest.getParameter(POSITION_PARAMETER)));
        user.setPhone(validatePhone(httpServletRequest.getParameter(PHONE_PARAMETER)));
        user.setMobile(validatePhone(httpServletRequest.getParameter(MOBILE_PARAMETER)));
        user.setMail(validateMail(httpServletRequest.getParameter(MAIL_PARAMETER)));
        user.setLogin(validateLogin(httpServletRequest.getParameter(LOGIN_PARAMETER)));
        user.setPassword(passwordHashing(validatePassword(httpServletRequest.getParameter(PASSWORD_PARAMETER))));
        user.setUserRole(Role.GUEST);
        if (user.getLogin().equals(userDAO.getByLogin(user.getLogin()).getLogin())) {
            httpServletRequest.setAttribute(MESSAGE, ACCOUNT_ALREADY_EXISTS_MESSAGE_ID);
            httpServletRequest.getRequestDispatcher(ERROR_JSP).forward(httpServletRequest, httpServletResponse);
        } else {
            userDAO.add(user);
            httpServletResponse.sendRedirect(MAIN_URI);
        }
    }

    public static String passwordHashing(String password) {
        String md5hashPassword = DigestUtils.md5Hex(password);
        return md5hashPassword;
    }
}
