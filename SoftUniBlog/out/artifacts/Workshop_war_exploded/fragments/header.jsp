<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
    <div class="navbar navbar-default navbar-static-top" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <a href="${pageContext.request.contextPath}/index" class="navbar-brand">SOFTUNI BLOG</a>
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            </div>
            <div class="navbar-collapse collapse">
                <c:set var="loginModel" value="${sessionScope.loginModel}"/>
                <ul class="nav navbar-nav navbar-right">
                    <c:choose>
                        <c:when test="${loginModel != null}">
                            <c:if test="${loginModel.role == 'ADMIN'}">
                                <li class="dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                                       aria-haspopup="true" aria-expanded="false">ADMIN <span class="caret"></span></a>
                                    <ul class="dropdown-menu">
                                        <li><a href="${pageContext.request.contextPath}/admin/users/all">Users</a></li>
                                        <li><a href="${pageContext.request.contextPath}/admin/categories/all">Categories</a></li>
                                    </ul>
                                </li>
                            </c:if>
                            <li>
                                <a href="${pageContext.request.contextPath}/article/create">
                                    CREATE ARTICLE
                                </a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/user/my-profile">
                                    MY PROFILE
                                </a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/user/logout">
                                    LOGOUT
                                </a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li>
                                <a href="${pageContext.request.contextPath}/user/register">
                                    REGISTER
                                </a>
                            </li>

                            <li>
                                <a href="${pageContext.request.contextPath}/user/login">
                                    LOGIN
                                </a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </div>
</header>