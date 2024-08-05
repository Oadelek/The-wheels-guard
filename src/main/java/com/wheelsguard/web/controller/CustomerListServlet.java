package com.wheelsguard.web.controller;

import com.wheelsguard.model.Customer;
import com.wheelsguard.model.User;
import com.wheelsguard.service.CustomerService;
import com.wheelsguard.service.DashboardService;
import com.wheelsguard.model.DashboardData;
import com.wheelsguard.service.UserService;
import com.wheelsguard.util.Utility;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/customerList")
public class CustomerListServlet extends HttpServlet {
    private CustomerService customerService ;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            // Choose database to use
            this.customerService = new CustomerService(Utility.IS_MY_SQL);
        } catch (SQLException e) {
            throw new ServletException("Error initializing CustomerService", e);
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null && user.hasPermission("VIEW_CUSTOMERS")) {
            // Fetch customer list
            List<Customer> customers = null;
            try {
                customers = customerService.getAllCustomers();
                request.setAttribute("customers", customers);
                request.setAttribute("canManage", user.hasPermission("MANAGE_CUSTOMERS"));
                request.getRequestDispatcher("/WEB-INF/jsp/customerList.jsp").forward(request, response);
            } catch (SQLException e) {
                System.out.println("Error in CustomerListServlet doGet: " + e);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
