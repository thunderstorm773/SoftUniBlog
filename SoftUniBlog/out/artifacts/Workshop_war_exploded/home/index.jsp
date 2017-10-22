<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<main>
    <div class="container body-content">
        <div class="row">
            <c:forEach var="category" items="${categories}">
                <div class="col-md-4 text-center">
                    <h2><a href="${pageContext.request.contextPath}/category/${category.id}/articles">${category.name}(${fn:length(category.articles)})</a></h2>
                </div>
            </c:forEach>
        </div>
    </div>
</main>