package com.epam.servicedesk.service;

import com.epam.servicedesk.database.UserDAO;
import com.epam.servicedesk.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.validation.AbstractValidation.isNumeric;

public class RemoveUserFromGroupService implements Service {
    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        String currentGroupId = httpServletRequest.getParameter(GROUP_ID_PARAMETER);
        if(isNumeric(currentGroupId)) {
            String[] stringUserIdList = httpServletRequest.getParameterValues(USERS_LIST_OF_GROUP);
            List<Long> userIdListOfSelectedUsers = new ArrayList<>();
            for (String userId : stringUserIdList) {
                userIdListOfSelectedUsers.add(Long.parseLong(userId));
            }
            List<User> selectedUsers = new ArrayList<>();
            for (long id:userIdListOfSelectedUsers) {
                selectedUsers.add(userDAO.getById(id));
            }
            userDAO.deleteUserByGroupId(Long.parseLong(currentGroupId), selectedUsers);
        }
        httpServletRequest.getServletContext().getRequestDispatcher(GROUP_CABINET_WITH_ID_URI+currentGroupId).forward(httpServletRequest, httpServletResponse);
    }
}
