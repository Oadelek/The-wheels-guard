package com.wheelsguard.web.controller;

import com.wheelsguard.model.User;
import com.wheelsguard.service.UserService;
import com.wheelsguard.util.Utility;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/deactivateUser")
public class DeactivateUserServlet extends HttpServlet {

    private UserService userService;

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
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser != null && currentUser.hasPermission("MANAGE_USERS")) {
            try {
                int userId = Integer.parseInt(request.getParameter("id"));
                boolean success = userService.deactivateUser(userId);
                if (success) {
                    request.getSession().setAttribute("message", "User successfully deactivated.");
                } else {
                    request.getSession().setAttribute("error", "Failed to deactivate user.");
                }
            } catch (NumberFormatException e) {
                request.getSession().setAttribute("error", "Invalid user ID.");
            } catch (Exception e) {
                getServletContext().log("Error in DeactivateUserServlet", e);
                request.getSession().setAttribute("error", "An error occurred while deactivating the user.");
            }
            response.sendRedirect(request.getContextPath() + "/userManagement");
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}