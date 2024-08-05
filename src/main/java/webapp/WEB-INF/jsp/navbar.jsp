<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="#"><i class="fas fa-shield-alt"></i> The Wheels Guard</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
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
                    <a class="nav-link" href="${pageContext.request.contextPath}/userProfile">User Profile</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/logout">Logout</a>
                </li>
            </ul>
        </div>
    </div>
</nav>