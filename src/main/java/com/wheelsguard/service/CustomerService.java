package com.wheelsguard.service;

import com.wheelsguard.dao.CustomerDAO;
import com.wheelsguard.model.Customer;

import java.sql.SQLException;
import java.util.List;

public class CustomerService {
    private CustomerDAO customerDAO;

    public CustomerService(boolean isMySQL) throws SQLException {
        customerDAO = new CustomerDAO(isMySQL);
    }

    public void createCustomer(Customer customer) throws SQLException {
        customerDAO.insert(customer);
    }

    public List<Customer> getAllCustomers() throws SQLException {
        return customerDAO.getAll();
    }

    public Customer getCustomerById(int id) throws SQLException {
        return customerDAO.get(id);
    }

    public void updateCustomer(Customer customer) throws SQLException {
        customerDAO.update(customer);
    }

    public void deleteCustomer(int id) throws SQLException {
        customerDAO.delete(id);
    }
}
