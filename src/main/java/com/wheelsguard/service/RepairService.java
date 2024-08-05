package com.wheelsguard.service;

import com.wheelsguard.dao.ServiceDAO;
import com.wheelsguard.model.Service;

import java.sql.SQLException;
import java.util.List;

public class RepairService {
    private ServiceDAO serviceDAO;

    public RepairService(boolean isMySQL) throws SQLException {
        serviceDAO = new ServiceDAO(isMySQL);
    }

    public void addService(Service service) throws SQLException {
        serviceDAO.insert(service);
    }

    public Service getService(int serviceID) throws SQLException {
        return serviceDAO.get(serviceID);
    }

    public List<Service> getAllServices() throws SQLException {
        return serviceDAO.getAll();
    }

    public void updateService(Service service) throws SQLException {
        serviceDAO.update(service);
    }

    public void deleteService(int serviceID) throws SQLException {
        serviceDAO.delete(serviceID);
    }
}
