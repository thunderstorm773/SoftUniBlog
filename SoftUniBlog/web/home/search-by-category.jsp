<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<main>
    <div class="container body-content">
        <div class="row">
            <c:forEach var="article" items="${category.articles}">
                <div class="col-md-6 equal-height">
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
                        <footer>
                            <div class="pull-right">
                                <a class="btn btn-default btn-xs"
                                   href="${pageContext.request.contextPath}/article/details/${article.id}">Read more
                                    &raquo;</a>
                            </div>
                        </footer>
                    </article>
                </div>
            </c:forEach>
        </div>
    </div>
</main>
