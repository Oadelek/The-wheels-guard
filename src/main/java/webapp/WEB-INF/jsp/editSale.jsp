<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Sale - The Wheels Guard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="navbar.jsp" />
    <div class="container mt-5">
        <h2>Edit Sale</h2>
        <form action="${pageContext.request.contextPath}/editSale" method="post">
            <input type="hidden" name="id" value="${sale.saleID}">
            <div class="mb-3">
                <label for="customerId" class="form-label">Customer</label>
                <select class="form-select" id="customerId" name="customerId" required>
                    <c:forEach var="customer" items="${customers}">
                        <option value="${customer.customerID}" ${sale.customerID == customer.customerID ? 'selected' : ''}>${customer.firstName} ${customer.lastName}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-3">
                <label for="productId" class="form-label">Product</label>
                <select class="form-select" id="productId" name="productId" required>
                    <c:forEach var="product" items="${products}">
                        <option value="${product.productID}" ${sale.productID == product.productID ? 'selected' : ''}>${product.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-3">
                <label for="saleDate" class="form-label">Sale Date</label>
                <input type="date" class="form-control" id="saleDate" name="saleDate" value="<fmt:formatDate value="${sale.saleDate}" pattern="yyyy-MM-dd" />" required>
            </div>
            <div class="mb-3">
                <label for="quantity" class="form-label">Quantity</label>
                <input type="number" class="form-control" id="quantity" name="quantity" value="${sale.quantity}" required>
            </div>
            <div class="mb-3">
                <label for="totalPrice" class="form-label">Total Price</label>
                <input type="number" step="0.01" class="form-control" id="totalPrice" name="totalPrice" value="${sale.totalPrice}" required>
            </div>
            <button type="submit" class="btn btn-primary">Update Sale</button>
        </form>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>