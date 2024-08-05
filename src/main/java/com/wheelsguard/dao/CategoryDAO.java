package com.wheelsguard.dao;

import com.wheelsguard.model.Category;
import com.wheelsguard.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private Connection connection;

    public CategoryDAO(boolean isMySQL) throws SQLException {
        if (isMySQL) {
            this.connection = DatabaseUtil.getMySQLConnection();
        } else {
            this.connection = DatabaseUtil.getSQLServerConnection();
        }
    }

    public void insert(Category category) throws SQLException {
        String query = "INSERT INTO Categories (Name) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, category.getName());
            stmt.executeUpdate();
        }
    }

    public Category get(int categoryID) throws SQLException {
        String query = "SELECT * FROM Categories WHERE CategoryID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, categoryID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Category category = new Category();
                    category.setCategoryID(rs.getInt("CategoryID"));
                    category.setName(rs.getString("Name"));
                    return category;
                }
            }
        }
        return null;
    }

    public List<Category> getAll() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM Categories";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Category category = new Category();
                category.setCategoryID(rs.getInt("CategoryID"));
                category.setName(rs.getString("Name"));
                categories.add(category);
            }
        }
        return categories;
    }

    public void update(Category category) throws SQLException {
        String query = "UPDATE Categories SET Name = ? WHERE CategoryID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, category.getName());
            stmt.setInt(2, category.getCategoryID());
            stmt.executeUpdate();
        }
    }

    public void delete(int categoryID) throws SQLException {
        String query = "DELETE FROM Categories WHERE CategoryID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, categoryID);
            stmt.executeUpdate();
        }
    }
}

