package com.wheelsguard.web.controller;

import com.wheelsguard.model.User;
import com.wheelsguard.service.DashboardService;
import com.wheelsguard.service.UserService;
import com.wheelsguard.service.ActivityLogService;
import com.wheelsguard.util.Utility;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService mysqlUserService ;
    //private UserService sqlserverUserService ;
    private ActivityLogService mysqlActivityLogService ;
    //private ActivityLogService sqlserverActivityLogService ;


    @Override
    public void init() throws ServletException {
        super.init();
        try {
            // Choose database of choice
            Utility.IS_MY_SQL = false;
            this.mysqlUserService = new UserService(Utility.IS_MY_SQL);
            this.mysqlActivityLogService = new ActivityLogService(Utility.IS_MY_SQL);
        } catch (SQLException e) {
            throw new ServletException("Error initializing DashboardService", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = null;
        try {
            user = mysqlUserService.authenticateUser(username, password);
            System.out.println("User has been authenticated: "+ user.getFirstName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (user != null) {
            System.out.println("User is indeed not equal to null");
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            mysqlActivityLogService.logActivity(user.getUserID(), "LOGIN", "User logged in");
            response.sendRedirect(request.getContextPath() + "/dashboard");
        } else {
            request.setAttribute("error", "Invalid username or password");
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
        }
    }
}




