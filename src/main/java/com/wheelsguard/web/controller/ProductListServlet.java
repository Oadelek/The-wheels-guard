package com.wheelsguard.web.controller;

import com.wheelsguard.model.Customer;
import com.wheelsguard.model.Product;
import com.wheelsguard.model.User;
import com.wheelsguard.service.ProductService;
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


@WebServlet("/productList")
public class ProductListServlet extends HttpServlet {
    private ProductService productService ;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            // Choose database to use
            this.productService = new ProductService(Utility.IS_MY_SQL);
        } catch (SQLException e) {
            throw new ServletException("Error initializing ProductService", e);
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null && user.hasPermission("VIEW_PRODUCTS")) {
            // Fetch product list
            List<Product> products = null;
            try {
                products = productService.getAllProducts();
                request.setAttribute("products", products);
                request.setAttribute("canManage", user.hasPermission("MANAGE_INVENTORY"));
                request.getRequestDispatcher("/WEB-INF/jsp/productList.jsp").forward(request, response);
            } catch (SQLException e) {
                System.out.println("Error in ProductListServlet doGet: " + e);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}