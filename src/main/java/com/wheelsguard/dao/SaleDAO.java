package com.wheelsguard.dao;

import com.wheelsguard.model.Sale;
import com.wheelsguard.util.DatabaseUtil;
import com.wheelsguard.util.Utility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleDAO {
    private Connection connection;
    private boolean isMySQL;

    public SaleDAO(boolean isMySQL) throws SQLException {
        if (isMySQL) {
            this.connection = DatabaseUtil.getMySQLConnection();
        } else {
            this.connection = DatabaseUtil.getSQLServerConnection();
        }
    }

    public void insert(Sale sale) throws SQLException {
        String query = "INSERT INTO Sales (SaleID, CustomerID, ProductID, SaleDate, Quantity, TotalPrice) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, sale.getSaleID());
            stmt.setInt(2, sale.getCustomerID());
            stmt.setInt(3, sale.getProductID());
            stmt.setDate(4, sale.getSaleDate());
            stmt.setInt(5, sale.getQuantity());
            stmt.setBigDecimal(6, sale.getTotalPrice());
            stmt.executeUpdate();
        }
    }

    public Sale get(int saleID) throws SQLException {
        String query = "SELECT * FROM Sales WHERE SaleID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, saleID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Sale sale = new Sale();
                    sale.setSaleID(rs.getInt("SaleID"));
                    sale.setCustomerID(rs.getInt("CustomerID"));
                    sale.setProductID(rs.getInt("ProductID"));
                    sale.setSaleDate(rs.getDate("SaleDate"));
                    sale.setQuantity(rs.getInt("Quantity"));
                    sale.setTotalPrice(rs.getBigDecimal("TotalPrice"));
                    return sale;
                }
            }
        }
        return null;
    }

    public List<Sale> getRecentSales(int limit) throws SQLException {
        List<Sale> sales = new ArrayList<>();
        String sql;

        if (Utility.IS_MY_SQL) {
            sql = "SELECT s.SaleID, s.CustomerID, s.ProductID, s.SaleDate, s.Quantity, s.TotalPrice " +
                    "FROM Sales s " +
                    "ORDER BY s.SaleDate DESC LIMIT ?";
        } else {
            sql = "SELECT TOP (?) s.SaleID, s.CustomerID, s.ProductID, s.SaleDate, s.Quantity, s.TotalPrice " +
                    "FROM Sales s " +
                    "ORDER BY s.SaleDate DESC";
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Sale sale = new Sale();
                    sale.setSaleID(rs.getInt("SaleID"));
                    sale.setCustomerID(rs.getInt("CustomerID"));
                    sale.setProductID(rs.getInt("ProductID"));
                    sale.setSaleDate(rs.getDate("SaleDate"));
                    sale.setQuantity(rs.getInt("Quantity"));
                    sale.setTotalPrice(rs.getBigDecimal("TotalPrice"));
                    sales.add(sale);
                }
            }
        }
        return sales;
    }

    public List<Sale> getAll() throws SQLException {
        List<Sale> sales = new ArrayList<>();
        String query = "SELECT * FROM Sales";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Sale sale = new Sale();
                sale.setSaleID(rs.getInt("SaleID"));
                sale.setCustomerID(rs.getInt("CustomerID"));
                sale.setProductID(rs.getInt("ProductID"));
                sale.setSaleDate(rs.getDate("SaleDate"));
                sale.setQuantity(rs.getInt("Quantity"));
                sale.setTotalPrice(rs.getBigDecimal("TotalPrice"));
                sales.add(sale);
            }
        }
        return sales;
    }

    public void update(Sale sale) throws SQLException {
        String query = "UPDATE Sales SET CustomerID = ?, ProductID = ?, SaleDate = ?, Quantity = ?, TotalPrice = ? WHERE SaleID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, sale.getCustomerID());
            stmt.setInt(2, sale.getProductID());
            stmt.setDate(3, sale.getSaleDate());
            stmt.setInt(4, sale.getQuantity());
            stmt.setBigDecimal(5, sale.getTotalPrice());
            stmt.setInt(6, sale.getSaleID());
            stmt.executeUpdate();
        }
    }

    public void delete(int saleID) throws SQLException {
        String query = "DELETE FROM Sales WHERE SaleID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, saleID);
            stmt.executeUpdate();
        }
    }
}

