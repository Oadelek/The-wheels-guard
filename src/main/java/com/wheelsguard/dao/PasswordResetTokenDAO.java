package com.wheelsguard.dao;

import com.wheelsguard.model.PasswordResetToken;
import com.wheelsguard.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PasswordResetTokenDAO {
    private Connection connection;

    public PasswordResetTokenDAO(boolean isMySQL) throws SQLException {
        if (isMySQL) {
            this.connection = DatabaseUtil.getMySQLConnection();
        } else {
            this.connection = DatabaseUtil.getSQLServerConnection();
        }
    }

    public void insert(PasswordResetToken token) throws SQLException {
        String query = "INSERT INTO PasswordResetTokens (TokenID, UserID, Token, ExpiryDate) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, token.getTokenID());
            stmt.setInt(2, token.getUserID());
            stmt.setString(3, token.getToken());
            stmt.setTimestamp(4, token.getExpiryDate());
            stmt.executeUpdate();
        }
    }

    public PasswordResetToken get(int tokenID) throws SQLException {
        String query = "SELECT * FROM PasswordResetTokens WHERE TokenID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, tokenID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    PasswordResetToken token = new PasswordResetToken();
                    token.setTokenID(rs.getInt("TokenID"));
                    token.setUserID(rs.getInt("UserID"));
                    token.setToken(rs.getString("Token"));
                    token.setExpiryDate(rs.getTimestamp("ExpiryDate"));
                    return token;
                }
            }
        }
        return null;
    }

    public List<PasswordResetToken> getAll() throws SQLException {
        List<PasswordResetToken> tokens = new ArrayList<>();
        String query = "SELECT * FROM PasswordResetTokens";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                PasswordResetToken token = new PasswordResetToken();
                token.setTokenID(rs.getInt("TokenID"));
                token.setUserID(rs.getInt("UserID"));
                token.setToken(rs.getString("Token"));
                token.setExpiryDate(rs.getTimestamp("ExpiryDate"));
                tokens.add(token);
            }
        }
        return tokens;
    }

    public void update(PasswordResetToken token) throws SQLException {
        String query = "UPDATE PasswordResetTokens SET UserID = ?, Token = ?, ExpiryDate = ? WHERE TokenID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, token.getUserID());
            stmt.setString(2, token.getToken());
            stmt.setTimestamp(3, token.getExpiryDate());
            stmt.setInt(4, token.getTokenID());
            stmt.executeUpdate();
        }
    }

    public void delete(int tokenID) throws SQLException {
        String query = "DELETE FROM PasswordResetTokens WHERE TokenID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, tokenID);
            stmt.executeUpdate();
        }
    }
}

