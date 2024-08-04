package com.wheelsguard.dao;


import com.wheelsguard.model.Inventory;
import com.wheelsguard.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {
    private Connection connection;

    public InventoryDAO(boolean isMySQL) throws SQLException {
        if (isMySQL) {
            this.connection = DatabaseUtil.getMySQLConnection();
        } else {
            this.connection = DatabaseUtil.getSQLServerConnection();
        }
    }

    public void insert(Inventory inventory) throws SQLException {
        String query = "INSERT INTO Inventory (InventoryID, ProductID, ReceivedDate, QuantityReceived) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, inventory.getInventoryID());
            stmt.setInt(2, inventory.getProductID());
            stmt.setDate(3, inventory.getReceivedDate());
            stmt.setInt(4, inventory.getQuantityReceived());
            stmt.executeUpdate();
        }
    }

    public Inventory get(int inventoryID) throws SQLException {
        String query = "SELECT * FROM Inventory WHERE InventoryID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, inventoryID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Inventory inventory = new Inventory();
                    inventory.setInventoryID(rs.getInt("InventoryID"));
                    inventory.setProductID(rs.getInt("ProductID"));
                    inventory.setReceivedDate(rs.getDate("ReceivedDate"));
                    inventory.setQuantityReceived(rs.getInt("QuantityReceived"));
                    return inventory;
                }
            }
        }
        return null;
    }

    public List<Inventory> getAll() throws SQLException {
        List<Inventory> inventories = new ArrayList<>();
        String query = "SELECT * FROM Inventory";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Inventory inventory = new Inventory();
                inventory.setInventoryID(rs.getInt("InventoryID"));
                inventory.setProductID(rs.getInt("ProductID"));
                inventory.setReceivedDate(rs.getDate("ReceivedDate"));
                inventory.setQuantityReceived(rs.getInt("QuantityReceived"));
                inventories.add(inventory);
            }
        }
        return inventories;
    }

    public void update(Inventory inventory) throws SQLException {
        String query = "UPDATE Inventory SET ProductID = ?, ReceivedDate = ?, QuantityReceived = ? WHERE InventoryID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, inventory.getProductID());
            stmt.setDate(2, inventory.getReceivedDate());
            stmt.setInt(3, inventory.getQuantityReceived());
            stmt.setInt(4, inventory.getInventoryID());
            stmt.executeUpdate();
        }
    }

    public void delete(int inventoryID) throws SQLException {
        String query = "DELETE FROM Inventory WHERE InventoryID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, inventoryID);
            stmt.executeUpdate();
        }
    }
}

