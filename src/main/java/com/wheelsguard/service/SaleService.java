package com.wheelsguard.service;

import com.wheelsguard.dao.SaleDAO;
import com.wheelsguard.model.Sale;

import java.sql.SQLException;
import java.util.List;

public class SaleService {
    private SaleDAO saleDAO;

    public SaleService(boolean isMySQL) throws SQLException {
        saleDAO = new SaleDAO(isMySQL);
    }

    public void addSale(Sale sale) throws SQLException {
        saleDAO.insert(sale);
    }

    public Sale getSale(int saleID) throws SQLException {
        return saleDAO.get(saleID);
    }

    public List<Sale> getAllSales() throws SQLException {
        return saleDAO.getAll();
    }

    public void updateSale(Sale sale) throws SQLException {
        saleDAO.update(sale);
    }

    public void deleteSale(int saleID) throws SQLException {
        saleDAO.delete(saleID);
    }
}
