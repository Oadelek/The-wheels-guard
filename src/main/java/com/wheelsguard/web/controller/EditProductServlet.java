package com.wheelsguard.web.controller;

import com.wheelsguard.model.Category;
import com.wheelsguard.model.Manufacturer;
import com.wheelsguard.model.Product;
import com.wheelsguard.model.User;
import com.wheelsguard.service.ProductService;
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

@WebServlet("/editProduct")
public class EditProductServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            this.productService = new ProductService(Utility.IS_MY_SQL);
        } catch (SQLException e) {
            throw new ServletException("Error initializing the Product Service", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser != null && currentUser.hasPermission("MANAGE_INVENTORY")) {
            int productId = Integer.parseInt(request.getParameter("id"));
            try {
                Product product = productService.getProduct(productId);
                List<Manufacturer> manufacturers = productService.getAllManufacturers();
                List<Category> categories = productService.getAllCategories();
                request.setAttribute("product", product);
                request.setAttribute("manufacturers", manufacturers);
                request.setAttribute("categories", categories);
                request.getRequestDispatcher("/WEB-INF/jsp/editProduct.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new ServletException("Error fetching product details", e);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser != null && currentUser.hasPermission("MANAGE_INVENTORY")) {
            int productId = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            BigDecimal price = new BigDecimal(request.getParameter("price"));
            int quantityInStock = Integer.parseInt(request.getParameter("quantityInStock"));
            int manufacturerId = Integer.parseInt(request.getParameter("manufacturerId"));
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));

            try {
                Product product = productService.getProduct(productId);
                product.setName(name);
                product.setPrice(price);
                product.setQuantityInStock(quantityInStock);
                product.setManufacturerID(manufacturerId);
                product.setCategoryID(categoryId);
                productService.updateProduct(product);
                response.sendRedirect(request.getContextPath() + "/productList");
            } catch (SQLException e) {
                throw new ServletException("Error updating product", e);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}