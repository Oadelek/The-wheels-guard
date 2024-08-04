package com.wheelsguard.dao;

import com.wheelsguard.model.Return;
import com.wheelsguard.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReturnDAO {
    private Connection connection;

    public ReturnDAO(boolean isMySQL) throws SQLException {
        if (isMySQL) {
            this.connection = DatabaseUtil.getMySQLConnection();
        } else {
            this.connection = DatabaseUtil.getSQLServerConnection();
        }
    }

    public void insert(Return returnObj) throws SQLException {
        String query = "INSERT INTO Returns (ReturnID, SaleID, ReturnDate, ReturnReason) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, returnObj.getReturnID());
            stmt.setInt(2, returnObj.getSaleID());
            stmt.setDate(3, returnObj.getReturnDate());
            stmt.setString(4, returnObj.getReturnReason());
            stmt.executeUpdate();
        }
    }

    public Return get(int returnID) throws SQLException {
        String query = "SELECT * FROM Returns WHERE ReturnID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, returnID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Return returnObj = new Return();
                    returnObj.setReturnID(rs.getInt("ReturnID"));
                    returnObj.setSaleID(rs.getInt("SaleID"));
                    returnObj.setReturnDate(rs.getDate("ReturnDate"));
                    returnObj.setReturnReason(rs.getString("ReturnReason"));
                    return returnObj;
                }
            }
        }
        return null;
    }

    public List<Return> getAll() throws SQLException {
        List<Return> returns = new ArrayList<>();
        String query = "SELECT * FROM Returns";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Return returnObj = new Return();
                returnObj.setReturnID(rs.getInt("ReturnID"));
                returnObj.setSaleID(rs.getInt("SaleID"));
                returnObj.setReturnDate(rs.getDate("ReturnDate"));
                returnObj.setReturnReason(rs.getString("ReturnReason"));
                returns.add(returnObj);
            }
        }
        return returns;
    }

    public void update(Return returnObj) throws SQLException {
        String query = "UPDATE Returns SET SaleID = ?, ReturnDate = ?, ReturnReason = ? WHERE ReturnID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, returnObj.getSaleID());
            stmt.setDate(2, returnObj.getReturnDate());
            stmt.setString(3, returnObj.getReturnReason());
            stmt.setInt(4, returnObj.getReturnID());
            stmt.executeUpdate();
        }
    }

    public void delete(int returnID) throws SQLException {
        String query = "DELETE FROM Returns WHERE ReturnID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, returnID);
            stmt.executeUpdate();
        }
    }
}

