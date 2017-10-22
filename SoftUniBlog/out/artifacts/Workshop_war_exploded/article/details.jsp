<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<main>
    <div class="container body-content">
        <div class="row">
            <div class="col-md-12">
                <article>
                    <header>
                        <h2>${article.title}</h2>
                    </header>

                    <p>${article.content}</p>
                    <c:forEach var="tag" items="${article.tagsList}">
                        <a class="btn btn-default btn-xs"
                           href="${pageContext.request.contextPath}/article/search-by-tag/${tag.id}">${tag.name}</a>
                    </c:forEach>
                    <small class="author">${article.author.fullName}</small>
                    <c:set var="authorId" value="${article.author.id}"/>
                    <c:set var="loggedInUser" value="${sessionScope.loginModel}"/>
                    <footer>
                        <div class="pull-right">
                            <c:if test="${loggedInUser != null && (loggedInUser.id == authorId || sessionScope.loginModel.role == 'ADMIN')}">
                                <a class="btn btn-success btn-xs" href="${pageContext.request.contextPath}/article/edit/${article.id}">Edit</a>
                                <a class="btn btn-danger btn-xs" href="${pageContext.request.contextPath}/article/delete/${article.id}">Delete</a>
                            </c:if>
                            <a class="btn btn-default btn-xs" href="${pageContext.request.contextPath}/category/${article.category.id}/articles">back
                                &raquo;</a>
                        </div>
                    </footer>
                </article>
            </div>
        </div>
    </div>
</main>
