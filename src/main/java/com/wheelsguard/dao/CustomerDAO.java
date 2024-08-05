package com.wheelsguard.dao;

import com.wheelsguard.model.Customer;
import com.wheelsguard.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private Connection connection;

    public CustomerDAO(boolean isMySQL) throws SQLException {
        if (isMySQL) {
            this.connection = DatabaseUtil.getMySQLConnection();
        } else {
            this.connection = DatabaseUtil.getSQLServerConnection();
        }
    }

    public void insert(Customer customer) {
        String query = "INSERT INTO Customers (FirstName, LastName, Address, PhoneNumber, Email) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getAddress());
            stmt.setString(4, customer.getPhoneNumber());
            stmt.setString(5, customer.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Error in insert: " + e.getMessage());
        }
    }

    public Customer get(int customerID) {
        String query = "SELECT * FROM Customers WHERE CustomerID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, customerID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Customer customer = new Customer();
                    customer.setCustomerID(rs.getInt("CustomerID"));
                    customer.setFirstName(rs.getString("FirstName"));
                    customer.setLastName(rs.getString("LastName"));
                    customer.setAddress(rs.getString("Address"));
                    customer.setPhoneNumber(rs.getString("PhoneNumber"));
                    customer.setEmail(rs.getString("Email"));
                    return customer;
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error in get: " + e.getMessage());
        }
        return null;
    }

    public List<Customer> getAll() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM Customers";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(rs.getInt("CustomerID"));
                customer.setFirstName(rs.getString("FirstName"));
                customer.setLastName(rs.getString("LastName"));
                customer.setAddress(rs.getString("Address"));
                customer.setPhoneNumber(rs.getString("PhoneNumber"));
                customer.setEmail(rs.getString("Email"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error in getAll: " + e.getMessage());
        }
        return customers;
    }

    public void update(Customer customer) {
        String query = "UPDATE Customers SET FirstName = ?, LastName = ?, Address = ?, PhoneNumber = ?, Email = ? WHERE CustomerID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getAddress());
            stmt.setString(4, customer.getPhoneNumber());
            stmt.setString(5, customer.getEmail());
            stmt.setInt(6, customer.getCustomerID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Error in update: " + e.getMessage());
        }
    }

    public void delete(int customerID) {
        String query = "DELETE FROM Customers WHERE CustomerID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, customerID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Error in delete: " + e.getMessage());
        }
    }
}
