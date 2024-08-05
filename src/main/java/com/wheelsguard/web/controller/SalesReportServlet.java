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

@WebServlet("/salesReport")
public class SalesReportServlet extends HttpServlet {
    private SaleService saleService ;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            // Choose database to use
            this.saleService = new SaleService(Utility.IS_MY_SQL);
        } catch (SQLException e) {
            throw new ServletException("Error initializing SaleService", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null && user.hasPermission("VIEW_SALES")) {
            // Fetch sales report data
            List<Sale> sales = null;
            try {
                sales = saleService.getAllSales();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            request.setAttribute("sales", sales);
            request.setAttribute("canManage", user.hasPermission("MANAGE_SALES"));
            request.getRequestDispatcher("/WEB-INF/jsp/salesReport.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
