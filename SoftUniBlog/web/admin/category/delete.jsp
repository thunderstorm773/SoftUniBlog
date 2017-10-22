<main>
    <div class="container body-content span=8 offset=2">
        <div class="well">
            <form class="form-horizontal" method="post">
                <fieldset>
                    <legend>Delete Category</legend>

                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="category-name">Category Name</label>
                        <div class="col-sm-4 ">
                            <input type="text" class="form-control" id="category-name" placeholder="Category Name" name="name"
                                   value="${category.name}" disabled="disabled"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-4 col-sm-offset-4">
                            <a class="btn btn-default" href="${pageContext.request.contextPath}/admin/categories/all">Cancel</a>
                            <input type="submit" class="btn btn-danger" value="Delete"/>
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</main>
