package com.epam.servicedesk.filter;

import com.epam.servicedesk.entity.User;
import com.epam.servicedesk.enums.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

import static com.epam.servicedesk.util.ConstantForApp.USER_PARAMETER;

public class SecurityUtils {

    private SecurityUtils() {
    }

    public static boolean isSecurityPage(HttpServletRequest httpServletRequest) {
        String urlPattern = httpServletRequest.getRequestURI();
        Set<Role> roles = SecurityConfig.getAllRoles();
        for (Role role : roles) {
            List<String> urlPatterns = SecurityConfig.getUrlForRole(role);
            if (urlPatterns != null && urlPatterns.contains(urlPattern)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasPermission(HttpServletRequest httpServletRequest) {
        String urlPattern = httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length());
        Set<Role> allRoles = SecurityConfig.getAllRoles();
        for (Role role : allRoles) {
            User user = (User) httpServletRequest.getSession().getAttribute(USER_PARAMETER);
            if (!role.equals(user.getUserRole())) {
                continue;
            }
            List<String> urlPatterns = SecurityConfig.getUrlForRole(role);
            if (urlPatterns != null && urlPatterns.contains(urlPattern)) {
                return true;
            }
        }
        return false;
    }
}
