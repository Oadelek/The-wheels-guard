package com.wheelsguard.dao;

import com.wheelsguard.model.UserRole;
import com.wheelsguard.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRoleDAO {
    private Connection connection;

    public UserRoleDAO(boolean isMySQL) throws SQLException {
        if (isMySQL) {
            this.connection = DatabaseUtil.getMySQLConnection();
        } else {
            this.connection = DatabaseUtil.getSQLServerConnection();
        }
    }

    public void insert(UserRole userRole) throws SQLException {
        String query = "INSERT INTO UserRoles (UserID, RoleID) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userRole.getUserID());
            stmt.setInt(2, userRole.getRoleID());
            stmt.executeUpdate();
        }
    }

    public UserRole get(int userRoleID) throws SQLException {
        String query = "SELECT * FROM UserRoles WHERE UserRoleID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userRoleID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    UserRole userRole = new UserRole();
                    userRole.setUserRoleID(rs.getInt("UserRoleID"));
                    userRole.setUserID(rs.getInt("UserID"));
                    userRole.setRoleID(rs.getInt("RoleID"));
                    return userRole;
                }
            }
        }
        return null;
    }

    public List<UserRole> getAll() throws SQLException {
        List<UserRole> userRoles = new ArrayList<>();
        String query = "SELECT * FROM UserRoles";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                UserRole userRole = new UserRole();
                userRole.setUserRoleID(rs.getInt("UserRoleID"));
                userRole.setUserID(rs.getInt("UserID"));
                userRole.setRoleID(rs.getInt("RoleID"));
                userRoles.add(userRole);
            }
        }
        return userRoles;
    }

    public void update(UserRole userRole) throws SQLException {
        String query = "UPDATE UserRoles SET UserID = ?, RoleID = ? WHERE UserRoleID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userRole.getUserID());
            stmt.setInt(2, userRole.getRoleID());
            stmt.setInt(3, userRole.getUserRoleID());
            stmt.executeUpdate();
        }
    }

    public void delete(int userRoleID) throws SQLException {
        String query = "DELETE FROM UserRoles WHERE UserRoleID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userRoleID);
            stmt.executeUpdate();
        }
    }

    public List<String> getPermissionsByUserId(int userId) throws SQLException {
        List<String> permissions = new ArrayList<>();
        String query = "SELECT DISTINCT p.PermissionName " +
                "FROM UserRoles ur " +
                "JOIN RolePermissions rp ON ur.RoleID = rp.RoleID " +
                "JOIN Permissions p ON rp.PermissionID = p.PermissionID " +
                "WHERE ur.UserID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    permissions.add(rs.getString("PermissionName"));
                }
            }
        }
        return permissions;
    }

    public List<String> getRoleNamesByUserId(int userId) throws SQLException {
        List<String> roleNames = new ArrayList<>();
        String query = "SELECT r.RoleName " +
                "FROM UserRoles ur " +
                "JOIN Roles r ON ur.RoleID = r.RoleID " +
                "WHERE ur.UserID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    roleNames.add(rs.getString("RoleName"));
                }
            }
        }
        return roleNames;
    }


}

