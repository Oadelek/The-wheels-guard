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

@WebServlet("/inventoryList")
public class InventoryListServlet extends HttpServlet {
    private InventoryService inventoryService ;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            // Choose database to use
            this.inventoryService = new InventoryService(Utility.IS_MY_SQL);
        } catch (SQLException e) {
            throw new ServletException("Error initializing InventoryService", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null && user.hasPermission("VIEW_INVENTORY")) {
            // Fetch inventory list
            List<Inventory> inventory = null;
            try {
                inventory = inventoryService.getAllInventories();
                request.setAttribute("inventory", inventory);
                request.setAttribute("canManage", user.hasPermission("MANAGE_INVENTORY"));
                request.getRequestDispatcher("/WEB-INF/jsp/inventoryList.jsp").forward(request, response);
            } catch (SQLException e) {
                System.out.println("Error in InventoryListServlet doGet: " + e);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
