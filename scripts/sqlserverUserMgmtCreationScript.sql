-- Use the Bicycle_Shop database
USE Bicycle_Shop;
GO

-- Create Users table
CREATE TABLE Users (
    UserID INT IDENTITY(1,1) PRIMARY KEY,
    Username NVARCHAR(50) NOT NULL UNIQUE,
    PasswordHash NVARCHAR(255) NOT NULL,
    Email NVARCHAR(100) NOT NULL UNIQUE,
    FirstName NVARCHAR(50),
    LastName NVARCHAR(50),
    DateCreated DATETIME DEFAULT GETDATE(),
    LastLoginDate DATETIME,
    IsActive BIT DEFAULT 1
);
GO

-- Create Roles table
CREATE TABLE Roles (
    RoleID INT IDENTITY(1,1) PRIMARY KEY,
    RoleName NVARCHAR(50) NOT NULL UNIQUE,
    Description NVARCHAR(255)
);
GO

-- Create UserRoles table
CREATE TABLE UserRoles (
    UserRoleID INT IDENTITY(1,1) PRIMARY KEY,
    UserID INT NOT NULL,
    RoleID INT NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (RoleID) REFERENCES Roles(RoleID)
);
GO

-- Create UserProfiles table
CREATE TABLE UserProfiles (
    UserProfileID INT IDENTITY(1,1) PRIMARY KEY,
    UserID INT NOT NULL,
    Address NVARCHAR(255),
    PhoneNumber NVARCHAR(20),
    DateOfBirth DATE,
    ProfilePicture VARBINARY(MAX),
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);
GO

-- Create UserSessions table
CREATE TABLE UserSessions (
    SessionID INT IDENTITY(1,1) PRIMARY KEY,
    UserID INT NOT NULL,
    SessionToken NVARCHAR(255) NOT NULL UNIQUE,
    LoginTime DATETIME DEFAULT GETDATE(),
    LogoutTime DATETIME,
    IsActive BIT DEFAULT 1,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);
GO

-- Create Permissions table
CREATE TABLE Permissions (
    PermissionID INT IDENTITY(1,1) PRIMARY KEY,
    PermissionName NVARCHAR(50) NOT NULL UNIQUE,
    Description NVARCHAR(255)
);
GO

-- Create RolePermissions table
CREATE TABLE RolePermissions (
    RolePermissionID INT IDENTITY(1,1) PRIMARY KEY,
    RoleID INT NOT NULL,
    PermissionID INT NOT NULL,
    FOREIGN KEY (RoleID) REFERENCES Roles(RoleID),
    FOREIGN KEY (PermissionID) REFERENCES Permissions(PermissionID)
);
GO

-- Create PasswordResetTokens table
CREATE TABLE PasswordResetTokens (
    TokenID INT IDENTITY(1,1) PRIMARY KEY,
    UserID INT NOT NULL,
    Token NVARCHAR(255) NOT NULL UNIQUE,
    ExpiryDate DATETIME NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);
GO