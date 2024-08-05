package com.wheelsguard.service;

import com.wheelsguard.dao.InventoryDAO;
import com.wheelsguard.model.Inventory;

import java.sql.SQLException;
import java.util.List;

public class InventoryService {
    private InventoryDAO inventoryDAO;

    public InventoryService(boolean isMySQL) throws SQLException {
        inventoryDAO = new InventoryDAO(isMySQL);
    }

    public void addInventory(Inventory inventory) throws SQLException {
        inventoryDAO.insert(inventory);
    }

    public Inventory getInventory(int inventoryID) throws SQLException {
        return inventoryDAO.get(inventoryID);
    }

    public List<Inventory> getAllInventories() throws SQLException {
        return inventoryDAO.getAll();
    }

    public void updateInventory(Inventory inventory) throws SQLException {
        inventoryDAO.update(inventory);
    }

    public void deleteInventory(int inventoryID) throws SQLException {
        inventoryDAO.delete(inventoryID);
    }
}
