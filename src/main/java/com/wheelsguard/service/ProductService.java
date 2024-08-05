package com.wheelsguard.service;

import com.wheelsguard.dao.CategoryDAO;
import com.wheelsguard.dao.ManufacturerDAO;
import com.wheelsguard.dao.ProductDAO;
import com.wheelsguard.model.Category;
import com.wheelsguard.model.Manufacturer;
import com.wheelsguard.model.Product;

import java.sql.SQLException;
import java.util.List;

public class ProductService {
    private ProductDAO productDAO;
    private ManufacturerDAO manufacturerDAO;
    private CategoryDAO categoryDAO;

    public ProductService(boolean isMySQL) throws SQLException {
        this.productDAO = new ProductDAO(isMySQL);
        this.manufacturerDAO = new ManufacturerDAO(isMySQL);
        this.categoryDAO = new CategoryDAO(isMySQL);
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

    public List<Manufacturer> getAllManufacturers() throws SQLException {
        return manufacturerDAO.getAll();
    }

    public List<Category> getAllCategories() throws SQLException {
        return categoryDAO.getAll();
    }
}

