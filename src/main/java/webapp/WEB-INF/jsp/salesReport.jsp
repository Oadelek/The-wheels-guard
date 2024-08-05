<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sales Report - The Wheels Guard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <jsp:include page="navbar.jsp" />
    <div class="container mt-4">
        <h1 class="mb-4">Sales Report</h1>
        <c:if test="${canManage}">
            <a href="${pageContext.request.contextPath}/createSale" class="btn btn-primary mb-3">Create New Sale</a>
        </c:if>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Sale ID</th>
                    <th>Customer ID</th>
                    <th>Product ID</th>
                    <th>Date</th>
                    <th>Quantity</th>
                    <th>Total</th>
                    <c:if test="${canManage}">
                        <th>Actions</th>
                    </c:if>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="sale" items="${sales}">
                    <tr>
                        <td>${sale.saleID}</td>
                        <td>${sale.customerID}</td>
                        <td>${sale.productID}</td>
                        <td><fmt:formatDate value="${sale.saleDate}" pattern="yyyy-MM-dd" /></td>
                        <td>${sale.quantity}</td>
                        <td>$${sale.totalPrice}</td>
                        <c:if test="${canManage}">
                            <td>
                                <a href="${pageContext.request.contextPath}/editSale?id=${sale.saleID}" class="btn btn-sm btn-primary">Edit</a>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>