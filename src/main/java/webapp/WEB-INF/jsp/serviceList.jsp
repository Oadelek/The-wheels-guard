<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Service List - The Wheels Guard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <jsp:include page="navbar.jsp" />
    <div class="container mt-4">
        <h1 class="mb-4">Service List</h1>
        <c:if test="${canManage}">
            <a href="${pageContext.request.contextPath}/createService" class="btn btn-primary mb-3">Create New Service</a>
        </c:if>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Customer ID</th>
                    <th>Product ID</th>
                    <th>Service Type</th>
                    <th>Date</th>
                    <th>Cost</th>
                    <c:if test="${canManage}">
                        <th>Actions</th>
                    </c:if>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="service" items="${services}">
                    <tr>
                        <td>${service.serviceID}</td>
                        <td>${service.customerID}</td>
                        <td>${service.productID}</td>
                        <td>${service.serviceType}</td>
                        <td><fmt:formatDate value="${service.serviceDate}" pattern="yyyy-MM-dd" /></td>
                        <td>${service.serviceCost}</td>
                        <c:if test="${canManage}">
                            <td>
                                <a href="${pageContext.request.contextPath}/editService?id=${service.serviceID}" class="btn btn-sm btn-primary">Edit</a>
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