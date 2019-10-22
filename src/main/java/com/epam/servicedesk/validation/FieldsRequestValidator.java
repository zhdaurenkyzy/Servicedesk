package com.epam.servicedesk.validation;

import com.epam.servicedesk.database.UserDAO;
import com.epam.servicedesk.entity.Request;
import com.epam.servicedesk.entity.User;
import com.epam.servicedesk.enums.Role;
import com.epam.servicedesk.exception.ValidationException;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.validation.AbstractValidation.*;

public class FieldsRequestValidator {

    public void setModeIdRequest(Request request, User user, String parameter) throws ValidationException {
        if(user.getUserRole().getId()>=CLIENT_ROLE_ID){
            request.setModeId(MODE_WEB_FORM);
        }
        else request.setModeId(validateLong(parameter));
    }

    public void setProjectIdRequest(Request request, User user, String parameter) {
        UserDAO userDAO = new UserDAO();
        if((userDAO.getProjectIdByUserId(user.getId())==null&&user.getUserRole().getId()>=CLIENT_ROLE_ID)){
            request.setProjectId(NULL_ID);
        }
        else if(userDAO.getProjectIdByUserId(user.getId())!=null){
            request.setProjectId(userDAO.getProjectIdByUserId(user.getId()));
        }
        else if (parameter.equals(NULL_STRING)){
            request.setProjectId(NULL_ID);
        }
        else {
            request.setProjectId(Long.parseLong(parameter));
        }
    }

    public void setClientIdRequest(Request request, User user, String parameter) {

        if(user.getUserRole().getId()>=CLIENT_ROLE_ID){
            request.setClientId(user.getId());
        }
        else request.setClientId(Long.parseLong(parameter));
    }

    public void setGroupIdRequest(Request request, long oldGroupId, User user, String parameter) {
        if(user.getUserRole().getId()>=CLIENT_ROLE_ID&&oldGroupId==NULL_ID){
            request.setGroupId(NULL_ID);
        }
        else if (user.getUserRole().getId()>=CLIENT_ROLE_ID&&oldGroupId!=NULL_ID){
            request.setGroupId(oldGroupId);
        }
        else if(parameter.equals(NULL_STRING)){
            request.setGroupId(NULL_ID);
        }
        else {
            request.setGroupId(Long.parseLong(parameter));
        }
    }

    public void setEngineerIdRequest(Request request, long oldEngineerId,  User user, String parameter) {
        if(user.getUserRole().getId()>=CLIENT_ROLE_ID&&oldEngineerId==NULL_ID){
            request.setEngineerId(NULL_ID);
        }
        else if (user.getUserRole().getId()>=CLIENT_ROLE_ID&&oldEngineerId!=NULL_ID){
            request.setEngineerId(oldEngineerId);
        }
        else request.setEngineerId(Long.parseLong(parameter));
    }
}
