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
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/editService")
public class EditServiceServlet extends HttpServlet {
    private RepairService repairService;
    private CustomerService customerService;
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            this.repairService = new RepairService(Utility.IS_MY_SQL);
            this.customerService = new CustomerService(Utility.IS_MY_SQL);
            this.productService = new ProductService(Utility.IS_MY_SQL);
        } catch (SQLException e) {
            throw new ServletException("Error initializing services", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser != null && currentUser.hasPermission("MANAGE_SERVICES")) {
            try {
                int serviceId = Integer.parseInt(request.getParameter("id"));
                Service service = repairService.getService(serviceId);
                List<Customer> customers = customerService.getAllCustomers();
                List<Product> products = productService.getAllProducts();

                request.setAttribute("service", service);
                request.setAttribute("customers", customers);
                request.setAttribute("products", products);
                request.getRequestDispatcher("/WEB-INF/jsp/editService.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new ServletException("Error fetching service details", e);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser != null && currentUser.hasPermission("MANAGE_SERVICES")) {
            try {
                int serviceId = Integer.parseInt(request.getParameter("id"));
                int customerID = Integer.parseInt(request.getParameter("customerId"));
                int productID = Integer.parseInt(request.getParameter("productId"));
                String serviceType = request.getParameter("serviceType");
                Date serviceDate = Date.valueOf(request.getParameter("serviceDate"));
                BigDecimal serviceCost = new BigDecimal(request.getParameter("serviceCost"));

                Service service = repairService.getService(serviceId);
                service.setCustomerID(customerID);
                service.setProductID(productID);
                service.setServiceType(serviceType);
                service.setServiceDate(serviceDate);
                service.setServiceCost(serviceCost);

                repairService.updateService(service);
                response.sendRedirect(request.getContextPath() + "/serviceList");
            } catch (SQLException e) {
                throw new ServletException("Error updating service", e);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}