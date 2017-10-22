<main>
    <div class="container body-content span=8 offset=2">
        <jsp:include page="../error.jsp"/>
        <div class="well">
            <form class="form-horizontal" method="post">
                <fieldset>
                    <legend>Login</legend>
                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="user-email">Email</label>
                        <div class="col-sm-4">
                            <input type="email" class="form-control" id="user-email" name="email"
                                   placeholder="Email" required="required" autofocus/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="user-password-first">Password</label>
                        <div class="col-sm-4">
                            <input type="password" class="form-control" id="user-password-first" name="password"
                                   placeholder="Password" required="required"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-4 col-sm-offset-4">
                            <a class="btn btn-default" href="${pageContext.request.contextPath}/index">Cancel</a>
                            <input type="submit" class="btn btn-primary" value="Login"/>
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</main>
