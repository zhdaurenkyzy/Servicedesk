package com.epam.servicedesk.service;

import com.epam.servicedesk.database.UserDAO;
import com.epam.servicedesk.entity.User;
import com.epam.servicedesk.exception.ValidationException;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.validation.UserValidation.*;

public class UpdateUserByOperatorService implements Service {

    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, ValidationException {
        UserDAO userDAO = new UserDAO();
        User user = new User();
        setFields(user, httpServletRequest);
        if(httpServletRequest.getParameter(PASSWORD_PARAMETER)==null) {
            user.setPassword(userDAO.getById(Long.parseLong(httpServletRequest.getParameter(ID_PARAMETER))).getPassword());
        }
        else {
            user.setPassword(DigestUtils.md5Hex(validatePassword(httpServletRequest.getParameter(PASSWORD_PARAMETER))));
        }
        user.setUserRole(userDAO.getById(Long.parseLong(httpServletRequest.getParameter(ID_PARAMETER))).getUserRole());
        userDAO.updateUser(user);
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
