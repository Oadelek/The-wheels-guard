package com.wheelsguard.model;

import java.util.List;

public class DashboardData {
    private List<Sale> recentSales;
    private List<Product> lowStockProducts;
    private List<Service> upcomingServices;

    // Getters and setters
    public List<Sale> getRecentSales() { return recentSales; }
    public void setRecentSales(List<Sale> recentSales) { this.recentSales = recentSales; }

    public List<Product> getLowStockProducts() { return lowStockProducts; }
    public void setLowStockProducts(List<Product> lowStockProducts) { this.lowStockProducts = lowStockProducts; }

    public List<Service> getUpcomingServices() { return upcomingServices; }
    public void setUpcomingServices(List<Service> upcomingServices) { this.upcomingServices = upcomingServices; }
}