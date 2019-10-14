package com.epam.servicedesk.service;

import com.epam.servicedesk.database.GroupDAO;
import com.epam.servicedesk.database.UserDAO;
import com.epam.servicedesk.entity.Group;
import com.epam.servicedesk.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.validation.AbstractValidation.isNumeric;

public class AddUserToGroupService implements Service {
    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        GroupDAO groupDAO = new GroupDAO();
        String currentGroupId = httpServletRequest.getParameter(GROUP_ID_PARAMETER);
        if(isNumeric(currentGroupId)) {
            Group currentGroup = groupDAO.getById(Long.parseLong(currentGroupId));
            String[] stringUserIdList = httpServletRequest.getParameterValues(ALL_USER_LIST_PARAMETER);
            List<Long> userIdListOfSelectedUsers = new ArrayList<>();
            for (String userId : stringUserIdList) {
                userIdListOfSelectedUsers.add(Long.parseLong(userId));
            }
            if (checkId(userIdListOfSelectedUsers, Long.parseLong(currentGroupId))) {
                List<User> selectedUsers = new ArrayList<>();
                for (long id : userIdListOfSelectedUsers) {
                    selectedUsers.add(userDAO.getById(id));
                }
                userDAO.addListUserToGroup(currentGroup, selectedUsers);
            }
        }
        httpServletRequest.getServletContext().getRequestDispatcher(LIST_GROUP_URI).forward(httpServletRequest, httpServletResponse);
    }

    private boolean checkId(List<Long> userIdListOfSelectedUsers, long groupID) {
        for (long id : userIdListOfSelectedUsers) {
            UserDAO userDAO = new UserDAO();
            for (User user:userDAO.getAllUserByGroupId(groupID)) {
                if(id==user.getId()){
                    return false;
                }
            }
        }
        return true;
    }
}
