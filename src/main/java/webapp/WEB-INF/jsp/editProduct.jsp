<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Product - The Wheels Guard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="navbar.jsp" />
    <div class="container mt-5">
        <h2>Edit Product</h2>
        <form action="${pageContext.request.contextPath}/editProduct" method="post">
            <input type="hidden" name="id" value="${product.getProductID()}">
            <div class="mb-3">
                <label for="name" class="form-label">Product Name</label>
                <input type="text" class="form-control" id="name" name="name" value="${product.getName()}" required>
            </div>
            <div class="mb-3">
                <label for="manufacturerId" class="form-label">Manufacturer</label>
                <select class="form-select" id="manufacturerId" name="manufacturerId" required>
                    <c:forEach var="manufacturer" items="${manufacturers}">
                        <option value="${manufacturer.getManufacturerID()}" ${product.getManufacturerID() == manufacturer.getManufacturerID() ? 'selected' : ''}>${manufacturer.getName()}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-3">
                <label for="categoryId" class="form-label">Category</label>
                <select class="form-select" id="categoryId" name="categoryId" required>
                    <c:forEach var="category" items="${categories}">
                        <option value="${category.getCategoryID()}" ${product.getCategoryID() == category.getCategoryID() ? 'selected' : ''}>${category.getName()}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-3">
                <label for="price" class="form-label">Price</label>
                <input type="number" step="0.01" class="form-control" id="price" name="price" value="${product.getPrice()}" required>
            </div>
            <div class="mb-3">
                <label for="quantityInStock" class="form-label">Quantity in Stock</label>
                <input type="number" class="form-control" id="quantityInStock" name="quantityInStock" value="${product.getQuantityInStock()}" required>
            </div>
            <button type="submit" class="btn btn-primary">Update Product</button>
        </form>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>