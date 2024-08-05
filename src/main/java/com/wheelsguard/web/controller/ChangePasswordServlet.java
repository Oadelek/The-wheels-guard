package com.wheelsguard.web.controller;

import com.wheelsguard.model.User;
import com.wheelsguard.service.UserService;
import com.wheelsguard.util.Utility;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/changePassword")
public class ChangePasswordServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            this.userService = new UserService(Utility.IS_MY_SQL);
        } catch (SQLException e) {
            throw new ServletException("Error initializing the User Service", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser != null) {
            request.getRequestDispatcher("/WEB-INF/jsp/changePassword.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser != null) {
            String currentPassword = request.getParameter("currentPassword");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");

            if (!BCrypt.checkpw(currentPassword, currentUser.getPasswordHash())) {
                request.setAttribute("error", "Current password is incorrect");
                request.getRequestDispatcher("/WEB-INF/jsp/changePassword.jsp").forward(request, response);
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                request.setAttribute("error", "New password and confirm password do not match");
                request.getRequestDispatcher("/WEB-INF/jsp/changePassword.jsp").forward(request, response);
                return;
            }

            try {
                String newPasswordHash = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                currentUser.setPasswordHash(newPasswordHash);
                userService.updateUser(currentUser);
                User updatedUser = userService.getUser(currentUser.getUserID());
                request.getSession().setAttribute("user", updatedUser);
                request.setAttribute("message", "Password changed successfully");
                request.getRequestDispatcher("/WEB-INF/jsp/changePassword.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
