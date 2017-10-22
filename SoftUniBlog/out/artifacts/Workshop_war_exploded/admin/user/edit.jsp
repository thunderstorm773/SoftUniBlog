<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<main>
    <div class="container body-content span=8 offset=2">
        <jsp:include page="../../error.jsp"/>
        <div class="well">
            <form class="form-horizontal" method="post">
                <fieldset>
                    <legend>Edit User - ${user.fullName}</legend>
                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="user-email">Email</label>
                        <div class="col-sm-4 ">
                            <input class="form-control" type="email" id="user-email" placeholder="Email" name="email"
                                   required="required" value="${user.email}" disabled="disabled"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="user-fullname">Full Name</label>
                        <div class="col-sm-4 ">
                            <input class="form-control" type="text" id="user-fullname" placeholder="Full Name"
                                   name="fullName" required="required" value="${user.fullName}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="user-password-first">Password</label>
                        <div class="col-sm-4">
                            <input type="password" class="form-control" id="user-password-first" placeholder="Password"
                                   name="password"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="user-password-second">Confirm Password</label>
                        <div class="col-sm-4">
                            <input type="password" class="form-control" id="user-password-second" placeholder="Password"
                                   name="confirmPassword"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-4 control-label">Role</label>
                        <div class="col-sm-4">
                            <c:choose>
                                <c:when test="${user.role == 'ADMIN'}">
                                    <label><input type="radio" name="roleIndex" value="1" checked="checked"/>Admin</label>
                                    <div>
                                        <label><input type="radio" name="roleIndex" value="0"/>User</label>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <label><input type="radio" name="roleIndex" value="1"/>Admin</label>
                                    <div>
                                        <label><input type="radio" name="roleIndex" value="0" checked="checked"/>User</label>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-4 col-sm-offset-4">
                            <a class="btn btn-default"
                               href="${pageContext.request.contextPath}/admin/users/all">Cancel</a>
                            <input value="Edit" type="submit" class="btn btn-success"/>
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</main>
