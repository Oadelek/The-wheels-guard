package com.wheelsguard.web.filters;

import com.wheelsguard.model.User;
import com.wheelsguard.service.RoleService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

//@WebFilter("/*")
public class AuthorizationFilter implements Filter {
    //private RoleService roleService = new RoleService();


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            String requestURI = httpRequest.getRequestURI();

            if (hasPermission(user, requestURI)) {
                chain.doFilter(request, response);
            } else {
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean hasPermission(User user, String requestURI) {
        //Set<String> userPermissions = roleService.getUserPermissions(user.getUserID());
        // Implement logic to check if the user has permission to access the requestURI
        // This is a simplified example, you should implement more robust logic
        //return userPermissions.contains(getRequiredPermission(requestURI));
        return false;
    }

    private String getRequiredPermission(String requestURI) {
        // Map URI to required permission
        // This is a simplified example, you should implement more robust logic
        if (requestURI.contains("/admin")) {
            return "ADMIN_ACCESS";
        } else if (requestURI.contains("/sales")) {
            return "SALES_ACCESS";
        }
        // Add more mappings as needed
        return "DEFAULT_ACCESS";
    }

    // init() and destroy() methods
}
