package com.wheelsguard.service;

import com.wheelsguard.dao.ProductDAO;
import com.wheelsguard.model.Product;

import java.sql.SQLException;
import java.util.List;

public class ProductService {
    private ProductDAO productDAO;

    public ProductService(boolean isMySQL) throws SQLException {
        productDAO = new ProductDAO(isMySQL);
    }

    public void addProduct(Product product) throws SQLException {
        productDAO.insert(product);
    }

    public Product getProduct(int productID) throws SQLException {
        return productDAO.get(productID);
    }

    public List<Product> getAllProducts() throws SQLException {
        return productDAO.getAll();
    }

    public void updateProduct(Product product) throws SQLException {
        productDAO.update(product);
    }

    public void deleteProduct(int productID) throws SQLException {
        productDAO.delete(productID);
    }
}

