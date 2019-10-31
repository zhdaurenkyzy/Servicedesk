package com.epam.servicedesk.filter;

import com.epam.servicedesk.enums.Role;

import java.util.*;

import static com.epam.servicedesk.util.ConstantForApp.*;

public class SecurityConfig {

    private SecurityConfig() {
    }

    private static final EnumMap<Role, List<String>> mapConfig = new EnumMap<Role, List<String>>(Role.class);

    static {
        init();
    }

    private static void init() {
        List<String> urlPatternsGuest = new ArrayList<>();
        urlPatternsGuest.add(CREATE_REQUEST_URI);
        urlPatternsGuest.add(LIST_REQUEST_URI);
        urlPatternsGuest.add(LIST_REQUEST_JSP);
        urlPatternsGuest.add(REQUEST_CABINET_URI);
        urlPatternsGuest.add(REQUEST_CABINET_JSP);
        urlPatternsGuest.add(SELECT_CLIENT_FOR_REQUEST_CABINET_JSP);
        urlPatternsGuest.add(SELECT_ENGINEER_FOR_REQUEST_CABINET_JSP);
        urlPatternsGuest.add(UPDATE_USER_URI);
        urlPatternsGuest.add(SEARCH_REQUEST_URI);
        mapConfig.put(Role.GUEST, urlPatternsGuest);
        List<String> urlPatternsClient = new ArrayList<>();
        setUrl(urlPatternsGuest, urlPatternsClient);
        urlPatternsClient.add(DECISION_CABINET_URI);
        urlPatternsClient.add(DECISION_CABINET_JSP);
        urlPatternsClient.add(ENGINEER_OF_REQUEST_URI);
        urlPatternsClient.add(HISTORY_OF_REQUEST_URI);
        urlPatternsClient.add(HISTORY_OF_REQUEST_JSP);
        urlPatternsClient.add(LIST_CLIENT_URI);
        urlPatternsClient.add(RESOLVE_REQUEST_URI);
        urlPatternsClient.add(UPDATE_REQUEST_URI);
        mapConfig.put(Role.CLIENT, urlPatternsClient);
        List<String> urlPatternsEngineer = new ArrayList<>();
        setUrl(urlPatternsClient, urlPatternsEngineer);
        mapConfig.put(Role.ENGINEER, urlPatternsEngineer);
        List<String> urlPatternsOperator = new ArrayList<>();
        setUrl(urlPatternsEngineer, urlPatternsOperator);
        urlPatternsOperator.add(ADD_USER_TO_GROUP_URI);
        urlPatternsOperator.add(LIST_GROUP_URI);
        urlPatternsOperator.add(LIST_MODE_URI);
        urlPatternsOperator.add(CREATE_MODE_URI);
        urlPatternsOperator.add(CHANGE_STATE_URI);
        urlPatternsOperator.add(DELETE_GROUP_URI);
        urlPatternsOperator.add(DELETE_MODE_URI);
        urlPatternsOperator.add(DELETE_REQUEST_URI);
        urlPatternsOperator.add(DELETE_PROJECT_URI);
        urlPatternsOperator.add(DELETE_USER_URI);
        urlPatternsOperator.add(EDIT_USER_BY_OPERATOR_URI);
        urlPatternsOperator.add(EDIT_USER_BY_OPERATOR_JSP);
        urlPatternsOperator.add(EDIT_USER_PROJECT_URI);
        urlPatternsOperator.add(MODE_CABINET_URI);
        urlPatternsOperator.add(GROUP_CABINET_URI);
        urlPatternsOperator.add(GROUP_CABINET_JSP);
        urlPatternsOperator.add(MODE_CABINET_JSP);
        urlPatternsOperator.add(LIST_ENGINEER_JSP);
        urlPatternsOperator.add(LIST_GROUP_JSP);
        urlPatternsOperator.add(LIST_MODE_JSP);
        urlPatternsOperator.add(LIST_PROJECT_URI);
        urlPatternsOperator.add(LIST_REQUEST_JSP);
        urlPatternsOperator.add(LIST_USER_URI);
        urlPatternsOperator.add(LIST_USER_JSP);
        urlPatternsOperator.add(OPTIONS_JSP);
        urlPatternsOperator.add(PROJECT_CABINET_URI);
        urlPatternsOperator.add(PROJECT_CABINET_JSP);
        urlPatternsOperator.add(REGISTRATION_USER_BY_OPERATOR_URI);
        urlPatternsOperator.add(REMOVE_USER_FROM_GROUP_URI);
        urlPatternsOperator.add(REMOVE_USER_PROJECT_URI);
        urlPatternsOperator.add(UPDATE_GROUP_NAME_URI);
        urlPatternsOperator.add(UPDATE_MODE_URI);
        urlPatternsOperator.add(UPDATE_PROJECT_URI);
        urlPatternsOperator.add(UPDATE_USER_BY_OPERATOR_URI);
        mapConfig.put(Role.OPERATOR, urlPatternsOperator);
    }

    public static Set<Role> getAllRoles() {
        return mapConfig.keySet();
    }

    public static List<String> getUrlForRole(Role role) {
        return mapConfig.get(role);
    }

    private static void setUrl(List<String> urlPatterns, List<String> otherUrlPatterns) {
        for (String url : urlPatterns) {
            otherUrlPatterns.add(url);
        }
    }
}
