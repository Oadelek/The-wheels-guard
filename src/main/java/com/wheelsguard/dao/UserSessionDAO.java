package com.wheelsguard.dao;

import com.wheelsguard.model.UserSession;
import com.wheelsguard.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserSessionDAO {
    private Connection connection;

    public UserSessionDAO(boolean isMySQL) throws SQLException {
        if (isMySQL) {
            this.connection = DatabaseUtil.getMySQLConnection();
        } else {
            this.connection = DatabaseUtil.getSQLServerConnection();
        }
    }

    public void insert(UserSession userSession) throws SQLException {
        String query = "INSERT INTO UserSessions (SessionID, UserID, SessionToken, LoginTime, LogoutTime, IsActive) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userSession.getSessionID());
            stmt.setInt(2, userSession.getUserID());
            stmt.setString(3, userSession.getSessionToken());
            stmt.setTimestamp(4, userSession.getLoginTime());
            stmt.setTimestamp(5, userSession.getLogoutTime());
            stmt.setBoolean(6, userSession.isActive());
            stmt.executeUpdate();
        }
    }

    public UserSession get(int sessionID) throws SQLException {
        String query = "SELECT * FROM UserSessions WHERE SessionID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, sessionID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    UserSession userSession = new UserSession();
                    userSession.setSessionID(rs.getInt("SessionID"));
                    userSession.setUserID(rs.getInt("UserID"));
                    userSession.setSessionToken(rs.getString("SessionToken"));
                    userSession.setLoginTime(rs.getTimestamp("LoginTime"));
                    userSession.setLogoutTime(rs.getTimestamp("LogoutTime"));
                    userSession.setActive(rs.getBoolean("IsActive"));
                    return userSession;
                }
            }
        }
        return null;
    }

    public List<UserSession> getAll() throws SQLException {
        List<UserSession> userSessions = new ArrayList<>();
        String query = "SELECT * FROM UserSessions";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                UserSession userSession = new UserSession();
                userSession.setSessionID(rs.getInt("SessionID"));
                userSession.setUserID(rs.getInt("UserID"));
                userSession.setSessionToken(rs.getString("SessionToken"));
                userSession.setLoginTime(rs.getTimestamp("LoginTime"));
                userSession.setLogoutTime(rs.getTimestamp("LogoutTime"));
                userSession.setActive(rs.getBoolean("IsActive"));
                userSessions.add(userSession);
            }
        }
        return userSessions;
    }

    public void update(UserSession userSession) throws SQLException {
        String query = "UPDATE UserSessions SET UserID = ?, SessionToken = ?, LoginTime = ?, LogoutTime = ?, IsActive = ? WHERE SessionID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userSession.getUserID());
            stmt.setString(2, userSession.getSessionToken());
            stmt.setTimestamp(3, userSession.getLoginTime());
            stmt.setTimestamp(4, userSession.getLogoutTime());
            stmt.setBoolean(5, userSession.isActive());
            stmt.setInt(6, userSession.getSessionID());
            stmt.executeUpdate();
        }
    }

    public void delete(int sessionID) throws SQLException {
        String query = "DELETE FROM UserSessions WHERE SessionID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, sessionID);
            stmt.executeUpdate();
        }
    }
}

