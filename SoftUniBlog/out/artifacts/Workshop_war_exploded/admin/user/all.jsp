<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<main>
    <div class="container body-content">
        <jsp:include page="../../error.jsp"/>
        <div class="well">
            <h2>All Users</h2>
            <div class="row">
                <table class="table table-striped table-hover ">
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th>Full Name</th>
                        <th>Email</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="user" items="${users}">
                        <c:set var="rowColor" value=""/>
                        <c:if test="${user.role == 'ADMIN'}">
                            <c:set var="rowColor" value="info"/>
                        </c:if>
                        <tr class="${rowColor}">
                            <td>${user.id}</td>
                            <td>${user.fullName}</td>
                            <td>${user.email}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/users/edit/${user.id}">Edit</a>
                                <a href="${pageContext.request.contextPath}/admin/users/delete/${user.id}">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</main>
