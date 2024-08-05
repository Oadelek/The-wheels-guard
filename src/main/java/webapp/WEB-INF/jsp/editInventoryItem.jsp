<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Inventory Item - The Wheels Guard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="navbar.jsp" />
    <div class="container mt-5">
        <h2>Edit Inventory Item</h2>
        <form action="${pageContext.request.contextPath}/editInventoryItem" method="post">
            <input type="hidden" name="id" value="${item.inventoryID}">
            <div class="mb-3">
                <label for="productId" class="form-label">Product</label>
                <select class="form-select" id="productId" name="productId" required>
                    <c:forEach var="product" items="${products}">
                        <option value="${product.productID}" ${item.productID == product.productID ? 'selected' : ''}>${product.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-3">
                <label for="quantityReceived" class="form-label">Quantity Received</label>
                <input type="number" class="form-control" id="quantityReceived" name="quantityReceived" value="${item.quantityReceived}" required>
            </div>
            <div class="mb-3">
                <label for="receivedDate" class="form-label">Received Date</label>
                <input type="date" class="form-control" id="receivedDate" name="receivedDate" value="${item.receivedDate}" required>
            </div>
            <button type="submit" class="btn btn-primary">Update Inventory Item</button>
        </form>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>