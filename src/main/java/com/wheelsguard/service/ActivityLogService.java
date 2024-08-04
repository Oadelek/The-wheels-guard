package com.wheelsguard.service;

import com.wheelsguard.dao.ActivityLogDAO;
import com.wheelsguard.model.ActivityLog;

import java.sql.SQLException;
import java.sql.*;
import java.util.List;

public class ActivityLogService {
    private ActivityLogDAO activityLogDAO = new ActivityLogDAO();

    public void logActivity(int userID, String activityType, String description) {
        ActivityLog log = new ActivityLog();
        log.setUserID(userID);
        log.setActivityType(activityType);
        log.setActivityDescription(description);
        log.setTimestamp(new Timestamp(System.currentTimeMillis()));
        try {
            activityLogDAO.insertLog(log);
        } catch (SQLException e) {
            e.printStackTrace();
            // exception to be handled appropriately
        }
    }

    public List<ActivityLog> getActivityLogs(int userID, Date startDate, Date endDate) {
        try {
            return activityLogDAO.getLogs(userID, startDate, endDate);
        } catch (SQLException e) {
            e.printStackTrace();
            // exception to be handled appropriately
            return null;
        }
    }
}