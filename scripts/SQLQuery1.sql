-- Create the databaseCREATE DATABASE Bicycle_Shop;

-- Use the database--USE Bicycle_Shop;

-- Create the Categories tableCREATE TABLE Categories (    CategoryID INT PRIMARY KEY IDENTITY(1,1),    Name VARCHAR(50) NOT NULL);

-- Create the Manufacturers tableCREATE TABLE Manufacturers (    ManufacturerID INT PRIMARY KEY IDENTITY(1,1),    Name VARCHAR(50) NOT NULL);

-- Create the Products tableCREATE TABLE Products (    ProductID INT PRIMARY KEY IDENTITY(1,1),    Name VARCHAR(100) NOT NULL,    ManufacturerID INT NOT NULL,    CategoryID INT NOT NULL,    Price DECIMAL(10,2) NOT NULL,    QuantityInStock INT NOT NULL,    CONSTRAINT FK_Products_Manufacturers FOREIGN KEY (ManufacturerID) REFERENCES Manufacturers(ManufacturerID),    CONSTRAINT FK_Products_Categories FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID));

-- Create the Customers tableCREATE TABLE Customers (    CustomerID INT PRIMARY KEY IDENTITY(1,1),    FirstName VARCHAR(50) NOT NULL,    LastName VARCHAR(50) NOT NULL,    Address VARCHAR(255) NOT NULL,    PhoneNumber VARCHAR(20) NOT NULL,    Email VARCHAR(100) NOT NULL);

-- Create the Sales tableCREATE TABLE Sales (    SaleID INT PRIMARY KEY IDENTITY(1,1),    CustomerID INT NOT NULL,    ProductID INT NOT NULL,    SaleDate DATE NOT NULL,    Quantity INT NOT NULL,    TotalPrice DECIMAL(10,2) NOT NULL,    CONSTRAINT FK_Sales_Customers FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID),    CONSTRAINT FK_Sales_Products FOREIGN KEY (ProductID) REFERENCES Products(ProductID));

-- Create the Services tableCREATE TABLE Services (    ServiceID INT PRIMARY KEY IDENTITY(1,1),    CustomerID INT NOT NULL,    ProductID INT NOT NULL,    ServiceType VARCHAR(50) NOT NULL,    ServiceDate DATE NOT NULL,    ServiceCost DECIMAL(10,2) NOT NULL,    CONSTRAINT FK_Services_Customers FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID),    CONSTRAINT FK_Services_Products FOREIGN KEY (ProductID) REFERENCES Products(ProductID));

-- Create the Returns tableCREATE TABLE Returns (    ReturnID INT PRIMARY KEY IDENTITY(1,1),    SaleID INT NOT NULL,    ReturnDate DATE NOT NULL,    ReturnReason VARCHAR(255) NOT NULL,    CONSTRAINT FK_Returns_Sales FOREIGN KEY (SaleID) REFERENCES Sales(SaleID));

-- Create the Inventory tableCREATE TABLE Inventory (    InventoryID INT PRIMARY KEY IDENTITY(1,1),    ProductID INT NOT NULL,    ReceivedDate DATE NOT NULL,    QuantityReceived INT NOT NULL,    CONSTRAINT FK_Inventory_Products FOREIGN KEY (ProductID) REFERENCES Products(ProductID));
