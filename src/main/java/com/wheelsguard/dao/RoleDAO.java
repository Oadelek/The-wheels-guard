package com.wheelsguard.dao;

import com.wheelsguard.model.Role;
import com.wheelsguard.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO {
    private Connection connection;

    public RoleDAO(boolean isMySQL) throws SQLException {
        if (isMySQL) {
            this.connection = DatabaseUtil.getMySQLConnection();
        } else {
            this.connection = DatabaseUtil.getSQLServerConnection();
        }
    }

    public void insert(Role role) throws SQLException {
        String query = "INSERT INTO Roles (RoleName, Description) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, role.getRoleName());
            stmt.setString(2, role.getDescription());
            stmt.executeUpdate();
        }
    }

    public Role get(int roleID) throws SQLException {
        String query = "SELECT * FROM Roles WHERE RoleID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, roleID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Role role = new Role();
                    role.setRoleID(rs.getInt("RoleID"));
                    role.setRoleName(rs.getString("RoleName"));
                    role.setDescription(rs.getString("Description"));
                    return role;
                }
            }
        }
        return null;
    }

    public int getRoleIDByRoleName(String roleName) throws SQLException {
        String query = "SELECT RoleID FROM Roles WHERE RoleName = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, roleName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("RoleID");
                } else {
                    throw new SQLException("Role with name " + roleName + " not found.");
                }
            }
        }
    }

    public List<Role> getAll() throws SQLException {
        List<Role> roles = new ArrayList<>();
        String query = "SELECT * FROM Roles";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Role role = new Role();
                role.setRoleID(rs.getInt("RoleID"));
                role.setRoleName(rs.getString("RoleName"));
                role.setDescription(rs.getString("Description"));
                roles.add(role);
            }
        }
        return roles;
    }

    public void update(Role role) throws SQLException {
        String query = "UPDATE Roles SET RoleName = ?, Description = ? WHERE RoleID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, role.getRoleName());
            stmt.setString(2, role.getDescription());
            stmt.setInt(3, role.getRoleID());
            stmt.executeUpdate();
        }
    }

    public void delete(int roleID) throws SQLException {
        String query = "DELETE FROM Roles WHERE RoleID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, roleID);
            stmt.executeUpdate();
        }
    }
}

