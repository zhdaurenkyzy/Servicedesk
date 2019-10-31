package com.epam.servicedesk.service;

import com.epam.servicedesk.database.UserDAO;
import com.epam.servicedesk.entity.User;
import com.epam.servicedesk.exception.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.validation.AbstractValidation.isNumeric;

public class RemoveUserFromGroupService implements Service {
    private static final Logger LOGGER = LogManager.getRootLogger();

    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, SQLException, ConnectionException {
        UserDAO userDAO = new UserDAO();
        String currentGroupId = httpServletRequest.getParameter(GROUP_ID_PARAMETER);
        if (isNumeric(currentGroupId)) {
            String[] stringUserIdList = httpServletRequest.getParameterValues(USERS_LIST_OF_GROUP);
            List<Long> userIdListOfSelectedUsers = new ArrayList<>();
            for (String userId : stringUserIdList) {
                userIdListOfSelectedUsers.add(Long.parseLong(userId));
            }
            List<User> selectedUsers = new ArrayList<>();
            for (long id : userIdListOfSelectedUsers) {
                selectedUsers.add(userDAO.getById(id));
            }
            userDAO.deleteUserByGroupId(Long.parseLong(currentGroupId), selectedUsers);
            for (User user : selectedUsers) {
                LOGGER.info(String.format("User %s was removed from groupId", user.getName(), currentGroupId));
            }
        }
        httpServletRequest.getServletContext().getRequestDispatcher(GROUP_CABINET_WITH_ID_URI + currentGroupId).forward(httpServletRequest, httpServletResponse);
    }
}
