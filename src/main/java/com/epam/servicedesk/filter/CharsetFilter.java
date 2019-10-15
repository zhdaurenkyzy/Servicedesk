package com.epam.servicedesk.filter;

import javax.servlet.*;
import java.io.IOException;

import static com.epam.servicedesk.util.ConstantForApp.*;

public class CharsetFilter implements Filter {
    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter(CHARSET_REQUEST_ENCODING);
        if (encoding == null) encoding = UTF_8;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain nextFilter) throws IOException, ServletException {
        if (null == servletRequest.getCharacterEncoding()) {
            servletRequest.setCharacterEncoding(encoding);
        }
        servletResponse.setContentType(SET_CONTENT_TYPE);
        servletResponse.setCharacterEncoding(UTF_8);
        nextFilter.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
