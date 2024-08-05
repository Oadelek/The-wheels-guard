package com.wheelsguard.web.filters;

import com.wheelsguard.model.User;
import com.wheelsguard.service.ActivityLogService;
import com.wheelsguard.service.UserService;
import com.wheelsguard.service.UserSessionService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebFilter("/*") // Filter all requests
public class AuthenticationFilter implements Filter {
    private UserSessionService userSessionService = new UserSessionService(true);

    public AuthenticationFilter() throws SQLException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false); // Don't create a new session if it doesn't exist

        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);
        String loginURI = httpRequest.getContextPath() + "/login";
        boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI);
        boolean isLoginPage = httpRequest.getRequestURI().endsWith("login.jsp");

        if (isLoggedIn && (isLoginRequest || isLoginPage)) {
            // Redirect to dashboard if already logged in and trying to access login
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/dashboard");
        } else if (isLoggedIn || isLoginRequest || isLoginPage) {
            // Continue if logged in, requesting login, or on login page
            chain.doFilter(request, response);
        } else {
            // Redirect to login if not logged in
            httpResponse.sendRedirect(loginURI);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization (if needed)
    }

    @Override
    public void destroy() {
        // Cleanup (if needed)
    }
}
