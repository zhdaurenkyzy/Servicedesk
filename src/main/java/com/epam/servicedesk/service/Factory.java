package com.epam.servicedesk.service;

import java.util.HashMap;
import java.util.Map;

import static com.epam.servicedesk.util.ConstantForApp.*;

public class Factory  {

    private static final Map<String, Service> STRING_SERVICE_MAP = new HashMap<>();
    private static final Factory FACTORY = new Factory();

    private Factory() {
        init();
    }

    private void init(){
        STRING_SERVICE_MAP.put(ADD_USER_TO_GROUP_URI, new AddUserToGroupService());
        STRING_SERVICE_MAP.put(CHANGE_STATE_URI, new ChangeProjectStateService());
        STRING_SERVICE_MAP.put(CREATE_GROUP_URI, new CreateGroupService());
        STRING_SERVICE_MAP.put(CREATE_PROJECT_URI, new CreateProjectService());
        STRING_SERVICE_MAP.put(CREATE_REQUEST_URI, new CreateRequestService());
        STRING_SERVICE_MAP.put(DECISION_CABINET_URI, new DecisionCabinetService());
        STRING_SERVICE_MAP.put(DELETE_GROUP_URI, new DeleteGroupService());
        STRING_SERVICE_MAP.put(DELETE_PROJECT_URI, new DeleteProjectService());
        STRING_SERVICE_MAP.put(DELETE_REQUEST_URI, new DeleteRequestService());
        STRING_SERVICE_MAP.put(DELETE_USER_URI, new DeleteUserService());
        STRING_SERVICE_MAP.put(EDIT_USER_BY_OPERATOR_URI, new EditUserByOperator());
        STRING_SERVICE_MAP.put(EDIT_USER_PROJECT_URI, new EditUserProjectService());
        STRING_SERVICE_MAP.put(ENGINEER_OF_REQUEST_URI, new EngineerRequestService());
        STRING_SERVICE_MAP.put(GROUP_CABINET_URI, new GroupCabinetService());
        STRING_SERVICE_MAP.put(HISTORY_OF_REQUEST_URI, new HistoryService());
        STRING_SERVICE_MAP.put(LIST_CLIENT_URI, new ListClientService());
        STRING_SERVICE_MAP.put(LIST_GROUP_URI, new ListGroupService());
        STRING_SERVICE_MAP.put(LIST_PROJECT_URI, new ListProjectService());
        STRING_SERVICE_MAP.put(LIST_REQUEST_URI, new ListRequestService());
        STRING_SERVICE_MAP.put(LIST_USER_URI, new ListUserService());
        STRING_SERVICE_MAP.put(LOGIN_URI, new LoginService());
        STRING_SERVICE_MAP.put(LOGOUT_URI, new LogOutService());
        STRING_SERVICE_MAP.put(PROJECT_CABINET_URI, new ProjectCabinetService());
        STRING_SERVICE_MAP.put(REGISTRATION_URI, new RegistrationService());
        STRING_SERVICE_MAP.put(REGISTRATION_USER_BY_OPERATOR_URI, new RegistrationUserByOperatorService());
        STRING_SERVICE_MAP.put(REMOVE_USER_FROM_GROUP_URI, new RemoveUserFromGroupService());
        STRING_SERVICE_MAP.put(REMOVE_USER_PROJECT_URI, new RemoveUserProjectService());
        STRING_SERVICE_MAP.put(REQUEST_CABINET_URI, new RequestCabinetService());
        STRING_SERVICE_MAP.put(RESOLVE_REQUEST_URI, new ResolveRequestService());
        STRING_SERVICE_MAP.put(SET_LANGUAGE_URI, new SetLanguageService());
        STRING_SERVICE_MAP.put(UPDATE_GROUP_NAME_URI, new UpdateGroupNameService());
        STRING_SERVICE_MAP.put(UPDATE_PROJECT_URI, new UpdateProjectService());
        STRING_SERVICE_MAP.put(UPDATE_REQUEST_URI, new UpdateRequestService());
        STRING_SERVICE_MAP.put(UPDATE_USER_URI, new UpdateUserService());
        STRING_SERVICE_MAP.put(UPDATE_USER_BY_OPERATOR_URI, new UpdateUserByOperatorService());
    }

    public static Factory getInstance(){
        return FACTORY;
    }

    public Service getService(String stringUri){
        return STRING_SERVICE_MAP.get(stringUri);
    }
}
