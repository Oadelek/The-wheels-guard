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

@WebServlet("/editProfile")
public class EditProfileServlet extends HttpServlet {
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
            request.setAttribute("user", currentUser);
            request.getRequestDispatcher("/WEB-INF/jsp/editProfile.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser != null) {
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");

            currentUser.setFirstName(firstName);
            currentUser.setLastName(lastName);
            currentUser.setEmail(email);

            try {
                userService.updateUser(currentUser);
                User updatedUser = userService.getUser(currentUser.getUserID());
                request.getSession().setAttribute("user", updatedUser);
                request.setAttribute("message", "Profile updated successfully");
                request.getRequestDispatcher("/WEB-INF/jsp/editProfile.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
