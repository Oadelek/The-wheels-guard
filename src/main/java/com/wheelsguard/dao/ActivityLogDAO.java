package com.wheelsguard.dao;

import com.wheelsguard.model.ActivityLog;
import com.wheelsguard.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityLogDAO {
    private Connection connection;

    public ActivityLogDAO(boolean isMySQL) throws SQLException {
        if (isMySQL) {
            this.connection = DatabaseUtil.getMySQLConnection();
        } else {
            this.connection = DatabaseUtil.getSQLServerConnection();
        }
    }

    public void insertLog(ActivityLog log) throws SQLException {
        String sql = "INSERT INTO ActivityLog (UserID, ActivityType, ActivityDescription, Timestamp) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, log.getUserID());
            pstmt.setString(2, log.getActivityType());
            pstmt.setString(3, log.getActivityDescription());
            pstmt.setTimestamp(4, new Timestamp(log.getTimestamp().getTime()));
            pstmt.executeUpdate();
        }
    }

    public List<ActivityLog> getLogs(int userID, Date startDate, Date endDate) throws SQLException {
        List<ActivityLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM ActivityLog WHERE UserID = ? AND Timestamp BETWEEN ? AND ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            pstmt.setDate(2, new java.sql.Date(startDate.getTime()));
            pstmt.setDate(3, new java.sql.Date(endDate.getTime()));
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ActivityLog log = new ActivityLog();
                    log.setLogID(rs.getInt("LogID"));
                    log.setUserID(rs.getInt("UserID"));
                    log.setActivityType(rs.getString("ActivityType"));
                    log.setActivityDescription(rs.getString("ActivityDescription"));
                    log.setTimestamp(rs.getTimestamp("Timestamp"));
                    logs.add(log);
                }
            }
        }
        return logs;
    }
}
