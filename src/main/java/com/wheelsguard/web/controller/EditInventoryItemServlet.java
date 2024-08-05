package com.wheelsguard.web.controller;

import com.wheelsguard.model.Inventory;
import com.wheelsguard.model.Product;
import com.wheelsguard.model.User;
import com.wheelsguard.service.InventoryService;
import com.wheelsguard.service.ProductService;
import com.wheelsguard.util.Utility;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/editInventoryItem")
public class EditInventoryItemServlet extends HttpServlet {
    private InventoryService inventoryService;
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            this.inventoryService = new InventoryService(Utility.IS_MY_SQL);
            this.productService = new ProductService(Utility.IS_MY_SQL);
        } catch (SQLException e) {
            throw new ServletException("Error initializing the Inventory and Product services", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser != null && currentUser.hasPermission("MANAGE_INVENTORY")) {
            int itemId = Integer.parseInt(request.getParameter("id"));
            try {
                Inventory item = inventoryService.getInventory(itemId);
                List<Product> products = productService.getAllProducts();
                request.setAttribute("item", item);
                request.setAttribute("products", products);
                request.getRequestDispatcher("/WEB-INF/jsp/editInventoryItem.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser != null && currentUser.hasPermission("MANAGE_INVENTORY")) {
            int itemId = Integer.parseInt(request.getParameter("id"));
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantityReceived = Integer.parseInt(request.getParameter("quantityReceived"));
            Date receivedDate = Date.valueOf(request.getParameter("receivedDate"));
            try {
                Inventory item = inventoryService.getInventory(itemId);
                item.setProductID(productId);
                item.setQuantityReceived(quantityReceived);
                item.setReceivedDate(receivedDate);
                inventoryService.updateInventory(item);
                response.sendRedirect(request.getContextPath() + "/inventoryList");
            } catch (SQLException e) {
                throw new ServletException("Error updating inventory item", e);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
