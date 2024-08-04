package com.wheelsguard.dao;

import com.wheelsguard.model.Permission;
import com.wheelsguard.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PermissionDAO {
    private Connection connection;

    public PermissionDAO(boolean isMySQL) throws SQLException {
        if (isMySQL) {
            this.connection = DatabaseUtil.getMySQLConnection();
        } else {
            this.connection = DatabaseUtil.getSQLServerConnection();
        }
    }

    public void insert(Permission permission) throws SQLException {
        String query = "INSERT INTO Permissions (PermissionID, PermissionName, Description) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, permission.getPermissionID());
            stmt.setString(2, permission.getPermissionName());
            stmt.setString(3, permission.getDescription());
            stmt.executeUpdate();
        }
    }

    public Permission get(int permissionID) throws SQLException {
        String query = "SELECT * FROM Permissions WHERE PermissionID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, permissionID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Permission permission = new Permission();
                    permission.setPermissionID(rs.getInt("PermissionID"));
                    permission.setPermissionName(rs.getString("PermissionName"));
                    permission.setDescription(rs.getString("Description"));
                    return permission;
                }
            }
        }
        return null;
    }

    public List<Permission> getAll() throws SQLException {
        List<Permission> permissions = new ArrayList<>();
        String query = "SELECT * FROM Permissions";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Permission permission = new Permission();
                permission.setPermissionID(rs.getInt("PermissionID"));
                permission.setPermissionName(rs.getString("PermissionName"));
                permission.setDescription(rs.getString("Description"));
                permissions.add(permission);
            }
        }
        return permissions;
    }

    public void update(Permission permission) throws SQLException {
        String query = "UPDATE Permissions SET PermissionName = ?, Description = ? WHERE PermissionID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, permission.getPermissionName());
            stmt.setString(2, permission.getDescription());
            stmt.setInt(3, permission.getPermissionID());
            stmt.executeUpdate();
        }
    }

    public void delete(int permissionID) throws SQLException {
        String query = "DELETE FROM Permissions WHERE PermissionID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, permissionID);
            stmt.executeUpdate();
        }
    }
}

