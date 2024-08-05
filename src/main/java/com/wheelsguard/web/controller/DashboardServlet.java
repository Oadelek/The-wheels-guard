package com.wheelsguard.web.controller;

import com.wheelsguard.service.DashboardService;
import com.wheelsguard.model.DashboardData;
import com.wheelsguard.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private DashboardService mysqlDashboardService = new DashboardService(true);
    private DashboardService serversqlDashboardService = new DashboardService(false);

    public DashboardServlet() throws SQLException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            DashboardData dashboardData = mysqlDashboardService.getDashboardData();
            request.setAttribute("dashboardData", dashboardData);
            request.getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("Error in DashboardServlet doGet: " + e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        }
    }
}