-- Use the Bicycle_Shop database
USE Bicycle_Shop;

-- Create Users table
CREATE TABLE Users (
    UserID INT AUTO_INCREMENT PRIMARY KEY,
    Username VARCHAR(50) NOT NULL UNIQUE,
    PasswordHash VARCHAR(255) NOT NULL,
    Email VARCHAR(100) NOT NULL UNIQUE,
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    DateCreated DATETIME DEFAULT CURRENT_TIMESTAMP,
    LastLoginDate DATETIME,
    IsActive BOOLEAN DEFAULT TRUE
);

-- Create Roles table
CREATE TABLE Roles (
    RoleID INT AUTO_INCREMENT PRIMARY KEY,
    RoleName VARCHAR(50) NOT NULL UNIQUE,
    Description VARCHAR(255)
);

-- Create UserRoles table
CREATE TABLE UserRoles (
    UserRoleID INT AUTO_INCREMENT PRIMARY KEY,
    UserID INT NOT NULL,
    RoleID INT NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (RoleID) REFERENCES Roles(RoleID)
);

-- Create UserProfiles table
CREATE TABLE UserProfiles (
    UserProfileID INT AUTO_INCREMENT PRIMARY KEY,
    UserID INT NOT NULL,
    Address VARCHAR(255),
    PhoneNumber VARCHAR(20),
    DateOfBirth DATE,
    ProfilePicture BLOB,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

-- Create UserSessions table
CREATE TABLE UserSessions (
    SessionID INT AUTO_INCREMENT PRIMARY KEY,
    UserID INT NOT NULL,
    SessionToken VARCHAR(255) NOT NULL UNIQUE,
    LoginTime DATETIME DEFAULT CURRENT_TIMESTAMP,
    LogoutTime DATETIME,
    IsActive BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

-- Create Permissions table
CREATE TABLE Permissions (
    PermissionID INT AUTO_INCREMENT PRIMARY KEY,
    PermissionName VARCHAR(50) NOT NULL UNIQUE,
    Description VARCHAR(255)
);

-- Create RolePermissions table
CREATE TABLE RolePermissions (
    RolePermissionID INT AUTO_INCREMENT PRIMARY KEY,
    RoleID INT NOT NULL,
    PermissionID INT NOT NULL,
    FOREIGN KEY (RoleID) REFERENCES Roles(RoleID),
    FOREIGN KEY (PermissionID) REFERENCES Permissions(PermissionID)
);

-- Create PasswordResetTokens table
CREATE TABLE PasswordResetTokens (
    TokenID INT AUTO_INCREMENT PRIMARY KEY,
    UserID INT NOT NULL,
    Token VARCHAR(255) NOT NULL UNIQUE,
    ExpiryDate DATETIME NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);