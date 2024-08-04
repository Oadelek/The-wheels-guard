package com.wheelsguard.dao;

import com.wheelsguard.model.RolePermission;
import com.wheelsguard.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RolePermissionDAO {
    private Connection connection;

    public RolePermissionDAO(boolean isMySQL) throws SQLException {
        if (isMySQL) {
            this.connection = DatabaseUtil.getMySQLConnection();
        } else {
            this.connection = DatabaseUtil.getSQLServerConnection();
        }
    }

    public void insert(RolePermission rolePermission) throws SQLException {
        String query = "INSERT INTO RolePermissions (RolePermissionID, RoleID, PermissionID) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, rolePermission.getRolePermissionID());
            stmt.setInt(2, rolePermission.getRoleID());
            stmt.setInt(3, rolePermission.getPermissionID());
            stmt.executeUpdate();
        }
    }

    public RolePermission get(int rolePermissionID) throws SQLException {
        String query = "SELECT * FROM RolePermissions WHERE RolePermissionID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, rolePermissionID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    RolePermission rolePermission = new RolePermission();
                    rolePermission.setRolePermissionID(rs.getInt("RolePermissionID"));
                    rolePermission.setRoleID(rs.getInt("RoleID"));
                    rolePermission.setPermissionID(rs.getInt("PermissionID"));
                    return rolePermission;
                }
            }
        }
        return null;
    }

    public List<RolePermission> getAll() throws SQLException {
        List<RolePermission> rolePermissions = new ArrayList<>();
        String query = "SELECT * FROM RolePermissions";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRolePermissionID(rs.getInt("RolePermissionID"));
                rolePermission.setRoleID(rs.getInt("RoleID"));
                rolePermission.setPermissionID(rs.getInt("PermissionID"));
                rolePermissions.add(rolePermission);
            }
        }
        return rolePermissions;
    }

    public void update(RolePermission rolePermission) throws SQLException {
        String query = "UPDATE RolePermissions SET RoleID = ?, PermissionID = ? WHERE RolePermissionID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, rolePermission.getRoleID());
            stmt.setInt(2, rolePermission.getPermissionID());
            stmt.setInt(3, rolePermission.getRolePermissionID());
            stmt.executeUpdate();
        }
    }

    public void delete(int rolePermissionID) throws SQLException {
        String query = "DELETE FROM RolePermissions WHERE RolePermissionID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, rolePermissionID);
            stmt.executeUpdate();
        }
    }
}

