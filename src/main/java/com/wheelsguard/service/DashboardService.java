package com.wheelsguard.service;

import com.wheelsguard.dao.*;
import com.wheelsguard.model.DashboardData;
import com.wheelsguard.model.Sale;
import com.wheelsguard.model.Product;
import com.wheelsguard.model.Service;

import java.sql.SQLException;
import java.util.List;

public class DashboardService {
    private SaleDAO mysqlSaleDAO;
    private ProductDAO mysqlProductDAO;
    private ServiceDAO mysqlServiceDAO;

    public DashboardService(boolean isMySQL) throws SQLException {
        mysqlSaleDAO = new SaleDAO(isMySQL);
        mysqlProductDAO = new ProductDAO(isMySQL);
        mysqlServiceDAO = new ServiceDAO(isMySQL);
    }

    public DashboardData getDashboardData() throws SQLException {
        DashboardData dashboardData = new DashboardData();

        // Get recent sales (last 5 sales)
        List<Sale> recentSales = mysqlSaleDAO.getRecentSales(5);
        dashboardData.setRecentSales(recentSales);

        // Get low stock products (products with quantity less than 10)
        List<Product> lowStockProducts = mysqlProductDAO.getLowStockProducts(10);
        dashboardData.setLowStockProducts(lowStockProducts);

        // Get upcoming services (next 5 scheduled services)
        List<Service> upcomingServices = mysqlServiceDAO.getUpcomingServices(5);
        dashboardData.setUpcomingServices(upcomingServices);

        return dashboardData;
    }
}