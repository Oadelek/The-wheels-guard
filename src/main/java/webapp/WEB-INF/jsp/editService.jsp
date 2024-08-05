<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Service - The Wheels Guard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="navbar.jsp" />
    <div class="container mt-5">
        <h2>Edit Service</h2>
        <form action="${pageContext.request.contextPath}/editService" method="post">
            <input type="hidden" name="id" value="${service.serviceID}">
            <div class="mb-3">
                <label for="customerId" class="form-label">Customer</label>
                <select class="form-select" id="customerId" name="customerId" required>
                    <c:forEach var="customer" items="${customers}">
                        <option value="${customer.customerID}" ${service.customerID == customer.customerID ? 'selected' : ''}>${customer.firstName} ${customer.lastName}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-3">
                <label for="productId" class="form-label">Product</label>
                <select class="form-select" id="productId" name="productId" required>
                    <c:forEach var="product" items="${products}">
                        <option value="${product.productID}" ${service.productID == product.productID ? 'selected' : ''}>${product.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-3">
                <label for="serviceType" class="form-label">Service Type</label>
                <input type="text" class="form-control" id="serviceType" name="serviceType" value="${service.serviceType}" required>
            </div>
            <div class="mb-3">
                <label for="serviceDate" class="form-label">Service Date</label>
                <input type="date" class="form-control" id="serviceDate" name="serviceDate" value="<fmt:formatDate value="${service.serviceDate}" pattern="yyyy-MM-dd" />" required>
            </div>
            <div class="mb-3">
                <label for="serviceCost" class="form-label">Service Cost</label>
                <input type="number" step="0.01" class="form-control" id="serviceCost" name="serviceCost" value="${service.serviceCost}" required>
            </div>
            <button type="submit" class="btn btn-primary">Update Service</button>
        </form>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>