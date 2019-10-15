package com.epam.servicedesk.service;

import com.epam.servicedesk.database.UserDAO;
import com.epam.servicedesk.entity.User;
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

public class UpdateUserService implements Service {
    private static final Logger LOGGER = LogManager.getRootLogger();

    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, ValidationException {
        UserDAO userDAO = new UserDAO();
        User user = new User();
        user.setName(validateName(httpServletRequest.getParameter(NAME_PARAMETER)));
        user.setPosition(validatePosition(httpServletRequest.getParameter(POSITION_PARAMETER)));
        user.setPhone(validatePhone(httpServletRequest.getParameter(PHONE_PARAMETER)));
        user.setMobile(validatePhone(httpServletRequest.getParameter(MOBILE_PARAMETER)));
        user.setMail(validateMail(httpServletRequest.getParameter(MAIL_PARAMETER)));
        String password = validatePassword(httpServletRequest.getParameter(PASSWORD_PARAMETER));
        String repeatPassword = validatePassword(httpServletRequest.getParameter(REPEAT_PASSWORD_PARAMETER));
        user.setUserRole(((User) httpServletRequest.getSession().getAttribute(USER_PARAMETER)).getUserRole());
        User currentUser = (User) httpServletRequest.getSession().getAttribute(USER_PARAMETER);
        user.setId((currentUser).getId());
        if (((password != null) & (repeatPassword != null)) & (password.equals(repeatPassword))) {
            user.setPassword(DigestUtils.md5Hex(password));
            userDAO.updateUser(user);
            LOGGER.info("User was updated userId = " + user.getId());
        }
        httpServletResponse.sendRedirect(USER_CABINET_JSP);
    }
}

