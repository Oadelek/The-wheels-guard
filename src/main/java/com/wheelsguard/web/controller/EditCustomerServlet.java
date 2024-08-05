package com.wheelsguard.web.controller;

import com.wheelsguard.model.Customer;
import com.wheelsguard.model.User;
import com.wheelsguard.service.CustomerService;
import com.wheelsguard.util.Utility;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/editCustomer")
public class EditCustomerServlet extends HttpServlet {
    private CustomerService customerService;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            this.customerService = new CustomerService(Utility.IS_MY_SQL);
        } catch (SQLException e) {
            throw new ServletException("Error initializing the Customer Service", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") != null) {
            int customerId = Integer.parseInt(request.getParameter("id"));
            try {
                Customer customer = customerService.getCustomerById(customerId);
                request.setAttribute("customer", customer);
                request.getRequestDispatcher("/WEB-INF/jsp/editCustomer.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") != null) {
            int customerId = Integer.parseInt(request.getParameter("id"));
            String lastName = request.getParameter("lastName");
            String firstName = request.getParameter("firstName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");

            try {
                Customer customer = customerService.getCustomerById(customerId);
                customer.setFirstName(firstName);
                customer.setLastName(lastName);
                customer.setEmail(email);
                customer.setPhoneNumber(phone);
                customerService.updateCustomer(customer);
                response.sendRedirect(request.getContextPath() + "/customerList");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
