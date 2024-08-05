<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create Inventory Item - The Wheels Guard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="navbar.jsp" />
    <div class="container mt-5">
        <h2>Create New Inventory Item</h2>
        <form action="${pageContext.request.contextPath}/createInventoryItem" method="post">
            <div class="mb-3">
                <label for="productId" class="form-label">Product</label>
                <select class="form-select" id="productId" name="productId" required>
                    <c:forEach var="product" items="${products}">
                        <option value="${product.productID}">${product.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-3">
                <label for="quantityReceived" class="form-label">Quantity Received</label>
                <input type="number" class="form-control" id="quantityReceived" name="quantityReceived" required>
            </div>
            <div class="mb-3">
                <label for="receivedDate" class="form-label">Received Date</label>
                <input type="date" class="form-control" id="receivedDate" name="receivedDate" required>
            </div>
            <button type="submit" class="btn btn-primary">Create Inventory Item</button>
        </form>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>