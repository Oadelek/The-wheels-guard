-- Create the Bicycle_Shop database
CREATE DATABASE Bicycle_Shop;

-- Use the Bicycle_Shop database
USE Bicycle_Shop;

-- Create the Categories table
CREATE TABLE Categories (
    CategoryID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255) NOT NULL
);

-- Create the Manufacturers table
CREATE TABLE Manufacturers (
    ManufacturerID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255) NOT NULL
);

-- Create the Products table
CREATE TABLE Products (
    ProductID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    ManufacturerID INT NOT NULL,
    CategoryID INT NOT NULL,
    Price DECIMAL(10,2) NOT NULL,
    QuantityInStock INT NOT NULL,
    FOREIGN KEY (ManufacturerID) REFERENCES Manufacturers(ManufacturerID),
    FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID)
);

-- Create the Customers table
CREATE TABLE Customers (
    CustomerID INT AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(255) NOT NULL,
    LastName VARCHAR(255) NOT NULL,
    Address VARCHAR(255),
    PhoneNumber VARCHAR(20),
    Email VARCHAR(255)
);

-- Create the Sales table
CREATE TABLE Sales (
    SaleID INT AUTO_INCREMENT PRIMARY KEY,
    CustomerID INT NOT NULL,
    ProductID INT NOT NULL,
    SaleDate DATE NOT NULL,
    Quantity INT NOT NULL,
    TotalPrice DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID),
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID)
);

-- Create the Services table
CREATE TABLE Services (
    ServiceID INT AUTO_INCREMENT PRIMARY KEY,
    CustomerID INT NOT NULL,
    ProductID INT,
    ServiceType VARCHAR(255),
    ServiceDate DATE NOT NULL,
    ServiceCost DECIMAL(10,2),
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID),
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID)
);

-- Create the Returns table
CREATE TABLE Returns (
    ReturnID INT AUTO_INCREMENT PRIMARY KEY,
    SaleID INT NOT NULL,
    ReturnDate DATE NOT NULL,
    ReturnReason VARCHAR(255),
    FOREIGN KEY (SaleID) REFERENCES Sales(SaleID)
);

-- Create the Inventory table
CREATE TABLE Inventory (
    InventoryID INT AUTO_INCREMENT PRIMARY KEY,
    ProductID INT NOT NULL,
    ReceivedDate DATE NOT NULL,
    QuantityReceived INT NOT NULL,
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID)
);
