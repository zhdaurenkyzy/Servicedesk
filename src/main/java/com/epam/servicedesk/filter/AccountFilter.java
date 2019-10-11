package com.epam.servicedesk.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.*;

public class AccountFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String servletPath = httpServletRequest.getServletPath();
        if(servletPath.equals(LOGIN_JSP)){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
            if (SecurityUtils.isSecurityPage(httpServletRequest)) {
                if (httpServletRequest.getSession().getAttribute(USER_PARAMETER) == null) {
                    httpServletResponse.sendRedirect(LOGIN_JSP);
                    return;
                }
                if (!SecurityUtils.hasPermission(httpServletRequest)) {
                    httpServletRequest.getSession().setAttribute(MESSAGE, ACCESS_DENIED);
                    httpServletRequest.getRequestDispatcher(ERROR_JSP).forward(httpServletRequest, httpServletResponse);
                    return;
                }
            }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Override
    public void destroy() {

    }
}
