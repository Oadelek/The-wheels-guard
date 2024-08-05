package com.wheelsguard.web.controller;

import com.wheelsguard.model.*;
import com.wheelsguard.service.CustomerService;
import com.wheelsguard.service.DashboardService;
import com.wheelsguard.service.ProductService;
import com.wheelsguard.service.UserService;
import com.wheelsguard.util.Utility;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/createProduct")
public class CreateProductServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            this.productService = new ProductService(Utility.IS_MY_SQL);
        } catch (SQLException e) {
            throw new ServletException("Error initializing ProductService", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser != null && currentUser.hasPermission("MANAGE_INVENTORY")) {
            try {
                List<Manufacturer> manufacturers = productService.getAllManufacturers();
                List<Category> categories = productService.getAllCategories();
                request.setAttribute("manufacturers", manufacturers);
                request.setAttribute("categories", categories);
                request.getRequestDispatcher("/WEB-INF/jsp/createProduct.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new ServletException("Error fetching manufacturers and categories", e);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser != null && currentUser.hasPermission("MANAGE_INVENTORY")) {
            String name = request.getParameter("name");
            BigDecimal price = new BigDecimal(request.getParameter("price"));
            int quantityInStock = Integer.parseInt(request.getParameter("quantityInStock"));
            int manufacturerId = Integer.parseInt(request.getParameter("manufacturerId"));
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));

            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setQuantityInStock(quantityInStock);
            product.setManufacturerID(manufacturerId);
            product.setCategoryID(categoryId);

            try {
                productService.addProduct(product);
                response.sendRedirect(request.getContextPath() + "/productList");
            } catch (SQLException e) {
                System.out.println("Error in CreateProductServlet doPost: " + e);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}