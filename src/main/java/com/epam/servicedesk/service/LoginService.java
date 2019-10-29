package com.epam.servicedesk.service;

import com.epam.servicedesk.database.UserDAO;
import com.epam.servicedesk.entity.User;
import com.epam.servicedesk.exception.ConnectionException;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.*;

public class LoginService implements Service {

    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, ConnectionException {
        UserDAO userDAO = new UserDAO();
        String login = httpServletRequest.getParameter(LOGIN_PARAMETER);
        String password = httpServletRequest.getParameter(PASSWORD_PARAMETER);
        User user = userDAO.getByLogin(login);
        if ((user.getLogin() != null)&&(DigestUtils.md5Hex(password).equals(user.getPassword()))) {
            HttpSession session = httpServletRequest.getSession();
            session.setAttribute(USER_PARAMETER, user);
            httpServletRequest.setAttribute(URI, UPDATE_USER_URI);
            httpServletRequest.getServletContext().getRequestDispatcher(USER_CABINET_JSP).forward(httpServletRequest, httpServletResponse);
        }
        else {
            httpServletRequest.getSession().setAttribute(MESSAGE, WRONG_LOGIN_OR_PASS_MESSAGE_ID);
            httpServletRequest.getServletContext().getRequestDispatcher(ERROR_JSP).forward(httpServletRequest, httpServletResponse);
        }
    }
}
