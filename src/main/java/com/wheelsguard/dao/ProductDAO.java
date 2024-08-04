package com.wheelsguard.dao;

import com.wheelsguard.model.Manufacturer;
import com.wheelsguard.model.Product;
import com.wheelsguard.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private Connection connection;

    public ProductDAO(boolean isMySQL) throws SQLException {
        if (isMySQL) {
            this.connection = DatabaseUtil.getMySQLConnection();
        } else {
            this.connection = DatabaseUtil.getSQLServerConnection();
        }
    }

    public void insert(Product product) throws SQLException {
        String query = "INSERT INTO Products (ProductID, Name, ManufacturerID, CategoryID, Price, QuantityInStock) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, product.getProductID());
            stmt.setString(2, product.getName());
            stmt.setInt(3, product.getManufacturerID());
            stmt.setInt(4, product.getCategoryID());
            stmt.setBigDecimal(5, product.getPrice());
            stmt.setInt(6, product.getQuantityInStock());
            stmt.executeUpdate();
        }
    }

    public Product get(int productID) throws SQLException {
        String query = "SELECT * FROM Products WHERE ProductID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, productID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Product product = new Product();
                    product.setProductID(rs.getInt("ProductID"));
                    product.setName(rs.getString("Name"));
                    product.setManufacturerID(rs.getInt("ManufacturerID"));
                    product.setCategoryID(rs.getInt("CategoryID"));
                    product.setPrice(rs.getBigDecimal("Price"));
                    product.setQuantityInStock(rs.getInt("QuantityInStock"));
                    return product;
                }
            }
        }
        return null;
    }

    public List<Product> getAll() throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Products";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Product product = new Product();
                product.setProductID(rs.getInt("ProductID"));
                product.setName(rs.getString("Name"));
                product.setManufacturerID(rs.getInt("ManufacturerID"));
                product.setCategoryID(rs.getInt("CategoryID"));
                product.setPrice(rs.getBigDecimal("Price"));
                product.setQuantityInStock(rs.getInt("QuantityInStock"));
                products.add(product);
            }
        }
        return products;
    }

    public void update(Product product) throws SQLException {
        String query = "UPDATE Products SET Name = ?, ManufacturerID = ?, CategoryID = ?, Price = ?, QuantityInStock = ? WHERE ProductID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, product.getName());
            stmt.setInt(2, product.getManufacturerID());
            stmt.setInt(3, product.getCategoryID());
            stmt.setBigDecimal(4, product.getPrice());
            stmt.setInt(5, product.getQuantityInStock());
            stmt.setInt(6, product.getProductID());
            stmt.executeUpdate();
        }
    }

    public void delete(int productID) throws SQLException {
        String query = "DELETE FROM Products WHERE ProductID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, productID);
            stmt.executeUpdate();
        }
    }
}


