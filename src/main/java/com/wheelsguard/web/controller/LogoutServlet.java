package com.wheelsguard.web.controller;

import com.wheelsguard.model.User;
import com.wheelsguard.service.ActivityLogService;
import com.wheelsguard.service.UserService;
import com.wheelsguard.util.Utility;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private ActivityLogService activityLogService ;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            // Choose database to use
            this.activityLogService = new ActivityLogService(Utility.IS_MY_SQL);
        } catch (SQLException e) {
            throw new ServletException("Error initializing ActivityLogService", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) request.getSession().getAttribute("user");
            int userId = user.getUserID();
            activityLogService.logActivity(userId, "LOGOUT", "User logged out");
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() + "/login");
    }
}
