<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<main>
    <div class="container body-content span=8 offset=2">
        <div class="well">
            <form class="form-horizontal" method="post">
                <fieldset>
                    <legend>Delete Article</legend>
                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="article-title">Article Title</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="article-title"
                                   placeholder="Article Title" value="${article.title}" disabled/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="article-content">Content</label>
                        <div class="col-sm-4">
                            <textarea class="form-control" id="article-content" rows="6" disabled>
                                ${article.content}
                            </textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="categoryId">Category</label>
                        <div class="col-sm-4">
                            <select class="form-control" name="categoryId" id="categoryId" disabled>
                                <option value="${article.category.id}" selected>${article.category.name}</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="article-tags">Tags</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="article-tags"
                                   placeholder="Tags" value="${tagsStr}" disabled/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-4 col-sm-offset-4">
                            <a class="btn btn-default" href="${pageContext.request.contextPath}/article/details/${article.id}">Cancel</a>
                            <input type="submit" class="btn btn-danger" value="Delete"/>
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</main>


