<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<main>
    <div class="container body-content">
        <div class="well">
            <h2>All Categories -
                <a href="${pageContext.request.contextPath}/admin/category/create" class="btn btn-warning">Create
                    New</a>
            </h2>
            <div class="row">
                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th>Id</th>
                        <th>Name</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="category" items="${categories}">
                        <tr>
                            <td>${category.id}</td>
                            <td>${category.name}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/categories/edit/${category.id}">Edit</a>
                                <a href="${pageContext.request.contextPath}/admin/categories/delete/${category.id}">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</main>
