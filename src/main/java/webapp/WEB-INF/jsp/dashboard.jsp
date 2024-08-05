<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard - The Wheels Guard</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
    <script src="${pageContext.request.contextPath}/js/dashboard.js"></script>
</head>
<body>
    <div class="container">
        <header>
            <h1>Welcome, ${user.firstName} ${user.lastName}</h1>
            <nav>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/dashboard">Dashboard</a></li>
                    <c:if test="${user.hasPermission('VIEW_CUSTOMERS')}">
                        <li><a href="${pageContext.request.contextPath}/customerList">Customers</a></li>
                    </c:if>
                    <c:if test="${user.hasPermission('VIEW_PRODUCTS')}">
                        <li><a href="${pageContext.request.contextPath}/productList">Products</a></li>
                    </c:if>
                    <c:if test="${user.hasPermission('VIEW_SALES')}">
                        <li><a href="${pageContext.request.contextPath}/salesReport">Sales</a></li>
                    </c:if>
                    <c:if test="${user.hasPermission('VIEW_INVENTORY')}">
                        <li><a href="${pageContext.request.contextPath}/inventoryList">Inventory</a></li>
                    </c:if>
                    <c:if test="${user.hasPermission('VIEW_SERVICES')}">
                        <li><a href="${pageContext.request.contextPath}/serviceList">Services</a></li>
                    </c:if>
                    <c:if test="${user.hasPermission('MANAGE_USERS')}">
                        <li><a href="${pageContext.request.contextPath}/userManagement">User Management</a></li>
                    </c:if>
                    <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
                </ul>
            </nav>
        </header>
        <main>
            <h2>Dashboard</h2>
            <div class="dashboard-widgets">
                <div class="widget">
                    <h3>Recent Sales</h3>
                    <ul>
                        <c:forEach var="sale" items="${dashboardData.recentSales}">
                            <li>Sale ID: ${sale.saleID}, Total: $${sale.totalPrice}</li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="widget">
                    <h3>Low Stock Products</h3>
                    <ul>
                        <c:forEach var="product" items="${dashboardData.lowStockProducts}">
                            <li>${product.name}: ${product.quantityInStock} left</li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="widget">
                    <h3>Upcoming Services</h3>
                    <ul>
                        <c:forEach var="service" items="${dashboardData.upcomingServices}">
                            <li>${service.serviceType} for Customer ID: ${service.customerID}</li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </main>
    </div>
</body>
</html>