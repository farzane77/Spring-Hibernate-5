package org.j2os.controller;

import org.j2os.entity.Role;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AccessMonitor implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestPanel = request.getRequestURI().split("/")[1];
        List<Role> roleList = (List<Role>) request.getSession().getAttribute("roles");
        if (roleList == null) {
            response.sendRedirect("/index.jsp");
            return;
        }
        boolean hasAccess = false;
        for (Role role : roleList) {
            if (requestPanel.equals(role.getRoleName())) {
                hasAccess = true;
                break;
            }
        }
        if (hasAccess) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            response.sendRedirect("/index.jsp");
        }
    }

    @Override
    public void destroy() {

    }
}
