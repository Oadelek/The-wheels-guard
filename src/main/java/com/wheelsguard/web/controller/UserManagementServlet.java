package com.wheelsguard.web.controller;

import com.wheelsguard.model.*;
import com.wheelsguard.service.*;
import com.wheelsguard.util.Utility;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/userManagement")
public class UserManagementServlet extends HttpServlet {
    private UserService userService ;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            // Choose database to use
            this.userService = new UserService(Utility.IS_MY_SQL);
        } catch (SQLException e) {
            throw new ServletException("Error initializing UserService", e);
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null && user.hasPermission("MANAGE_USERS")) {
            // Fetch user list
            List<User> users = null;
            try {
                users = userService.getAllUsers();
                request.setAttribute("users", users);
                request.getRequestDispatcher("/WEB-INF/jsp/userManagement.jsp").forward(request, response);
            } catch (SQLException e) {
                System.out.println("Error in UserManagementServlet doGet: " + e);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred");
            }

        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }

    }
}