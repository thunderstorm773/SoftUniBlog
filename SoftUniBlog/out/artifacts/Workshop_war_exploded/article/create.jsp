<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<main>
    <div class="container body-content span=8 offset=2">
        <jsp:include page="../error.jsp"/>
        <div class="well">
            <form class="form-horizontal" method="post">
                <fieldset>
                    <legend>New Article</legend>
                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="article-title">Article Title</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="article-title" name="title"
                                   placeholder="Article Title" required="required" autofocus/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="article-content">Content</label>
                        <div class="col-sm-4">
                            <textarea class="form-control" id="article-content"
                                      name="content" rows="6" required="required"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="categoryId">Category</label>
                        <div class="col-sm-4">
                            <select class="form-control" name="categoryId" id="categoryId" required="required">
                                <c:forEach var="category" items="${categories}">
                                    <option value="${category.id}">${category.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="article-tags">Tags</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="article-tags" name="tagsName"
                                   placeholder="Tags"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-4 col-sm-offset-4">
                            <a class="btn btn-default" href="${pageContext.request.contextPath}/index">Cancel</a>
                            <input type="submit" class="btn btn-primary" value="Create"/>
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</main>
