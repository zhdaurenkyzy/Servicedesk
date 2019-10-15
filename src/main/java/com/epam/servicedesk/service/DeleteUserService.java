package com.epam.servicedesk.service;

import com.epam.servicedesk.database.UserDAO;;
import com.epam.servicedesk.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.validation.AbstractValidation.isNumeric;

public class DeleteUserService implements Service {
    private static final Logger LOGGER = LogManager.getRootLogger();

    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        User user = new User();
        if(isNumeric(httpServletRequest.getParameter(ID_PARAMETER))) {
            user = userDAO.getById(Long.parseLong(httpServletRequest.getParameter(ID_PARAMETER)));
        }
        User operator = (User) httpServletRequest.getSession().getAttribute(USER_PARAMETER);
        if(operator.getUserRole().getId()==OPERATOR_ROLE_ID_DEFAULT) {
            userDAO.deleteUser(user);
            LOGGER.info("User was deleted, userName =" + user.getName() + " by operatorId " + operator.getId());
        }
        httpServletResponse.sendRedirect(LIST_USER_URI);
    }
}
