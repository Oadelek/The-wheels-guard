<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - The Wheels Guard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
    <script src="${pageContext.request.contextPath}/js/dashboard.js"></script>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="#"><i class="fas fa-shield-alt"></i> The Wheels Guard</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link active" href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
                    </li>
                    <c:if test="${user.hasPermission('VIEW_CUSTOMERS')}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/customerList">Customers</a>
                        </li>
                    </c:if>
                    <c:if test="${user.hasPermission('VIEW_PRODUCTS')}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/productList">Products</a>
                        </li>
                    </c:if>
                    <c:if test="${user.hasPermission('VIEW_SALES')}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/salesReport">Sales</a>
                        </li>
                    </c:if>
                    <c:if test="${user.hasPermission('VIEW_INVENTORY')}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/inventoryList">Inventory</a>
                        </li>
                    </c:if>
                    <c:if test="${user.hasPermission('VIEW_SERVICES')}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/serviceList">Services</a>
                        </li>
                    </c:if>
                    <c:if test="${user.hasPermission('MANAGE_USERS')}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/userManagement">User Management</a>
                        </li>
                    </c:if>
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/logout">Logout</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <h1 class="mb-4">Welcome, ${user.firstName} ${user.lastName}</h1>
        <div class="row">
            <div class="col-md-4 mb-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Recent Sales</h5>
                        <ul class="list-group list-group-flush">
                            <c:forEach var="sale" items="${dashboardData.recentSales}">
                                <li class="list-group-item">Sale ID: ${sale.saleID}, Total: $${sale.totalPrice}</li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="col-md-4 mb-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Low Stock Products</h5>
                        <ul class="list-group list-group-flush">
                            <c:forEach var="product" items="${dashboardData.lowStockProducts}">
                                <li class="list-group-item">${product.name}: ${product.quantityInStock} left</li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="col-md-4 mb-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Upcoming Services</h5>
                        <ul class="list-group list-group-flush">
                            <c:forEach var="service" items="${dashboardData.upcomingServices}">
                                <li class="list-group-item">${service.serviceType} for Customer ID: ${service.customerID}</li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>