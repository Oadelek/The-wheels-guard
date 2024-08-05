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

@WebServlet("/serviceList")
public class ServiceListServlet extends HttpServlet {
    private RepairService repairService ;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            // Choose database to use
            this.repairService = new RepairService(Utility.IS_MY_SQL);
        } catch (SQLException e) {
            throw new ServletException("Error initializing RepairService", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null && user.hasPermission("VIEW_SERVICES")) {
            // Fetch service list
            List<Service> services = null;
            try {
                services = repairService.getAllServices();
                request.setAttribute("services", services);
                request.setAttribute("canManage", user.hasPermission("MANAGE_SERVICES"));
                request.getRequestDispatcher("/WEB-INF/jsp/serviceList.jsp").forward(request, response);
            } catch (SQLException e) {
                System.out.println("Error in ServiceListServlet doGet: " + e);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

}
