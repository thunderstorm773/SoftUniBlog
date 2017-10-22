<main>
    <div class="container body-content span=8 offset=2">
        <jsp:include page="../../error.jsp"/>
        <div class="well">
            <form class="form-horizontal" method="post">
                <fieldset>
                    <legend>Edit Category</legend>

                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="category-name">Category Name</label>
                        <div class="col-sm-4 ">
                            <input type="text" class="form-control" id="category-name"
                                   value="${category.name}" placeholder="Category Name" name="name" required="required"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-4 col-sm-offset-4">
                            <a class="btn btn-default" href="${pageContext.request.contextPath}/admin/categories/all">Cancel</a>
                            <input type="submit" class="btn btn-primary" value="Create"/>
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</main>
