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

@WebServlet("/editSale")
public class EditSaleServlet extends HttpServlet {
    private SaleService saleService;
    private CustomerService customerService;
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            this.saleService = new SaleService(Utility.IS_MY_SQL);
            this.customerService = new CustomerService(Utility.IS_MY_SQL);
            this.productService = new ProductService(Utility.IS_MY_SQL);
        } catch (SQLException e) {
            throw new ServletException("Error initializing services", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser != null && currentUser.hasPermission("MANAGE_SALES")) {
            try {
                int saleId = Integer.parseInt(request.getParameter("id"));
                Sale sale = saleService.getSale(saleId);
                List<Customer> customers = customerService.getAllCustomers();
                List<Product> products = productService.getAllProducts();

                request.setAttribute("sale", sale);
                request.setAttribute("customers", customers);
                request.setAttribute("products", products);
                request.getRequestDispatcher("/WEB-INF/jsp/editSale.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new ServletException("Error fetching sale details", e);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser != null && currentUser.hasPermission("MANAGE_SALES")) {
            try {
                int saleId = Integer.parseInt(request.getParameter("id"));
                int customerId = Integer.parseInt(request.getParameter("customerId"));
                int productId = Integer.parseInt(request.getParameter("productId"));
                Date saleDate = Date.valueOf(request.getParameter("saleDate"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                BigDecimal totalPrice = new BigDecimal(request.getParameter("totalPrice"));

                Sale sale = saleService.getSale(saleId);
                sale.setCustomerID(customerId);
                sale.setProductID(productId);
                sale.setSaleDate(saleDate);
                sale.setQuantity(quantity);
                sale.setTotalPrice(totalPrice);

                saleService.updateSale(sale);
                response.sendRedirect(request.getContextPath() + "/salesReport");
            } catch (SQLException e) {
                throw new ServletException("Error updating sale", e);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}