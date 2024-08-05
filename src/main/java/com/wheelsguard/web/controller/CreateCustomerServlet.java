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

@WebServlet("/createCustomer")
public class CreateCustomerServlet extends HttpServlet {

    private CustomerService customerService;

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
        if (user != null && user.hasPermission("MANAGE_CUSTOMERS")) {
            request.getRequestDispatcher("/WEB-INF/jsp/createCustomer.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null && user.hasPermission("MANAGE_CUSTOMERS")) {
            try {
                System.out.println("I came to create new customer!");
                // Extract form data
                String firstName = Utility.sanitizeInput(request.getParameter("firstName"));
                String lastName = Utility.sanitizeInput(request.getParameter("lastName"));
                String email = Utility.sanitizeInput(request.getParameter("email"));
                String phone = Utility.sanitizeInput(request.getParameter("phone"));
                String address = Utility.sanitizeInput(request.getParameter("address"));


                // Validate data
                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
                    request.setAttribute("error", "First name, last name, and email are required fields.");
                    request.getRequestDispatcher("/WEB-INF/jsp/createCustomer.jsp").forward(request, response);
                    return;
                }

                if (!Utility.isValidEmail(email)) {
                    request.setAttribute("error", "Invalid email format.");
                    request.getRequestDispatcher("/WEB-INF/jsp/createCustomer.jsp").forward(request, response);
                    return;
                }

                // Create new customer
                Customer newCustomer = new Customer();
                newCustomer.setFirstName(firstName);
                newCustomer.setLastName(lastName);
                newCustomer.setEmail(email);
                newCustomer.setPhoneNumber(phone);
                newCustomer.setAddress(address);

                // Save customer
                customerService.createCustomer(newCustomer);

                // Redirect to customer list on success
                response.sendRedirect(request.getContextPath() + "/customerList");
            } catch (Exception e) {
                // Set error message and forward back to the form
                request.setAttribute("error", "An error occurred while creating the customer. Please try again.");
                request.getRequestDispatcher("/WEB-INF/jsp/createCustomer.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}