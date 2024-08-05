package com.wheelsguard.dao;

import com.wheelsguard.model.Manufacturer;
import com.wheelsguard.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManufacturerDAO {
    private Connection connection;

    public ManufacturerDAO(boolean isMySQL) throws SQLException {
        if (isMySQL) {
            this.connection = DatabaseUtil.getMySQLConnection();
        } else {
            this.connection = DatabaseUtil.getSQLServerConnection();
        }
    }

    public void insert(Manufacturer manufacturer) throws SQLException {
        String query = "INSERT INTO Manufacturers (Name) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, manufacturer.getName());
            stmt.executeUpdate();
        }
    }

    public Manufacturer get(int manufacturerID) throws SQLException {
        String query = "SELECT * FROM Manufacturers WHERE ManufacturerID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, manufacturerID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Manufacturer manufacturer = new Manufacturer();
                    manufacturer.setManufacturerID(rs.getInt("ManufacturerID"));
                    manufacturer.setName(rs.getString("Name"));
                    return manufacturer;
                }
            }
        }
        return null;
    }

    public List<Manufacturer> getAll() throws SQLException {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String query = "SELECT * FROM Manufacturers";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setManufacturerID(rs.getInt("ManufacturerID"));
                manufacturer.setName(rs.getString("Name"));
                manufacturers.add(manufacturer);
            }
        }
        return manufacturers;
    }

    public void update(Manufacturer manufacturer) throws SQLException {
        String query = "UPDATE Manufacturers SET Name = ? WHERE ManufacturerID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, manufacturer.getName());
            stmt.setInt(2, manufacturer.getManufacturerID());
            stmt.executeUpdate();
        }
    }

    public void delete(int manufacturerID) throws SQLException {
        String query = "DELETE FROM Manufacturers WHERE ManufacturerID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, manufacturerID);
            stmt.executeUpdate();
        }
    }
}
