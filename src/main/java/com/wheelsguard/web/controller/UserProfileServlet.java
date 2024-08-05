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

@WebServlet("/userProfile")
public class UserProfileServlet extends HttpServlet {
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
        if (user != null) {
            try {
                user = userService.getUser(user.getUserID());
                request.setAttribute("user", user);
                request.getRequestDispatcher("/WEB-INF/jsp/userProfile.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new ServletException("Error fetching user data", e);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
