<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit User - The Wheels Guard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="navbar.jsp" />
    <div class="container mt-5">
        <h2>Edit User</h2>
        <form action="${pageContext.request.contextPath}/editUser" method="post">
            <input type="hidden" name="id" value="${user.userID}">
            <div class="mb-3">
                <label for="username" class="form-label">Username</label>
                <input type="text" class="form-control" id="username" name="username" value="${user.username}" required>
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" id="email" name="email" value="${user.email}" required>
            </div>
            <div class="mb-3">
                <label for="firstName" class="form-label">First Name</label>
                <input type="text" class="form-control" id="firstName" name="firstName" value="${user.firstName}" required>
            </div>
            <div class="mb-3">
                <label for="lastName" class="form-label">Last Name</label>
                <input type="text" class="form-control" id="lastName" name="lastName" value="${user.lastName}" required>
            </div>
            <div class="mb-3">
                <label for="roleId" class="form-label">Role</label>
                <select class="form-select" id="roleId" name="roleId" required>
                    <c:forEach var="role" items="${roles}">
                        <option value="${role.id}" ${user.role.id == role.id ? 'selected' : ''}>${role.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-3">
                <label for="roleId" class="form-label">Role</label>
                <select class="form-select" id="roleId" name="roleId" required>
                    <c:forEach var="role" items="${roles}">
                        <option value="${role.roleID}" ${user.roleID == role.roleID ? 'selected' : ''}>${role.roleName}</option>
                    </c:forEach>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Update User</button>
        </form>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>