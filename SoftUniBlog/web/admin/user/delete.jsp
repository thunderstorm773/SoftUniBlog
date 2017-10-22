<main>
    <div class="container body-content span=8 offset=2">
        <div class="well">
            <form class="form-horizontal" method="post">
                <fieldset>
                    <legend>Delete User</legend>
                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="user_email">Email</label>
                        <div class="col-sm-4 ">
                            <input class="form-control" type="email" id="user_email" placeholder="Email" name="email" value="${user.email}" disabled="disabled"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="user_fullname">Full Name</label>
                        <div class="col-sm-4 ">
                            <input class="form-control" type="text" id="user_fullname" placeholder="Full Name" name="fullName" value="${user.fullName}" disabled="disabled"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-4 col-sm-offset-4">
                            <a class="btn btn-default" href="${pageContext.request.contextPath}/admin/users/all">Cancel</a>
                            <input value="Delete" type="submit" class="btn btn-danger"/>
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</main>
