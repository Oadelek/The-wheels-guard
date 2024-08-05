package com.wheelsguard.dao;


import com.wheelsguard.model.Service;
import com.wheelsguard.util.DatabaseUtil;
import com.wheelsguard.util.Utility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAO {
    private Connection connection;

    public ServiceDAO(boolean isMySQL) throws SQLException {
        if (isMySQL) {
            this.connection = DatabaseUtil.getMySQLConnection();
        } else {
            this.connection = DatabaseUtil.getSQLServerConnection();
        }
    }

    public void insert(Service service) throws SQLException {
        String query = "INSERT INTO Services (ServiceID, CustomerID, ProductID, ServiceType, ServiceDate, ServiceCost) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, service.getServiceID());
            stmt.setInt(2, service.getCustomerID());
            stmt.setInt(3, service.getProductID());
            stmt.setString(4, service.getServiceType());
            stmt.setDate(5, service.getServiceDate());
            stmt.setBigDecimal(6, service.getServiceCost());
            stmt.executeUpdate();
        }
    }

    public Service get(int serviceID) throws SQLException {
        String query = "SELECT * FROM Services WHERE ServiceID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, serviceID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Service service = new Service();
                    service.setServiceID(rs.getInt("ServiceID"));
                    service.setCustomerID(rs.getInt("CustomerID"));
                    service.setProductID(rs.getInt("ProductID"));
                    service.setServiceType(rs.getString("ServiceType"));
                    service.setServiceDate(rs.getDate("ServiceDate"));
                    service.setServiceCost(rs.getBigDecimal("ServiceCost"));
                    return service;
                }
            }
        }
        return null;
    }

    public List<Service> getUpcomingServices(int limit) throws SQLException {
        List<Service> services = new ArrayList<>();
        String sql;

        if (Utility.IS_MY_SQL) {
            sql = "SELECT ServiceID, CustomerID, ProductID, ServiceType, ServiceDate, ServiceCost " +
                    "FROM Services " +
                    "WHERE ServiceDate > CURRENT_TIMESTAMP " +
                    "ORDER BY ServiceDate ASC LIMIT ?";
        } else {
            sql = "SELECT TOP (?) ServiceID, CustomerID, ProductID, ServiceType, ServiceDate, ServiceCost " +
                    "FROM Services " +
                    "WHERE ServiceDate > CURRENT_TIMESTAMP " +
                    "ORDER BY ServiceDate ASC";
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Service service = new Service();
                    service.setServiceID(rs.getInt("ServiceID"));
                    service.setCustomerID(rs.getInt("CustomerID"));
                    service.setProductID(rs.getInt("ProductID"));
                    service.setServiceType(rs.getString("ServiceType"));
                    service.setServiceDate(rs.getDate("ServiceDate"));
                    service.setServiceCost(rs.getBigDecimal("ServiceCost"));
                    services.add(service);
                }
            }
        }
        return services;
    }

    public List<Service> getAll() throws SQLException {
        List<Service> services = new ArrayList<>();
        String query = "SELECT * FROM Services";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Service service = new Service();
                service.setServiceID(rs.getInt("ServiceID"));
                service.setCustomerID(rs.getInt("CustomerID"));
                service.setProductID(rs.getInt("ProductID"));
                service.setServiceType(rs.getString("ServiceType"));
                service.setServiceDate(rs.getDate("ServiceDate"));
                service.setServiceCost(rs.getBigDecimal("ServiceCost"));
                services.add(service);
            }
        }
        return services;
    }

    public void update(Service service) throws SQLException {
        String query = "UPDATE Services SET CustomerID = ?, ProductID = ?, ServiceType = ?, ServiceDate = ?, ServiceCost = ? WHERE ServiceID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, service.getCustomerID());
            stmt.setInt(2, service.getProductID());
            stmt.setString(3, service.getServiceType());
            stmt.setDate(4, service.getServiceDate());
            stmt.setBigDecimal(5, service.getServiceCost());
            stmt.setInt(6, service.getServiceID());
            stmt.executeUpdate();
        }
    }

    public void delete(int serviceID) throws SQLException {
        String query = "DELETE FROM Services WHERE ServiceID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, serviceID);
            stmt.executeUpdate();
        }
    }
}

