<%@ include file="/WEB-INF/pages/fragments/page.jspf" %>
<%@ include file="/WEB-INF/pages/fragments/taglib.jspf" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title><fmt:message key="create"/></title>


    <!-- CSS only -->
    <ext:fragment name="import/bootstrap"/>
    <ext:fragment name="import/animation"/>
</head>

<body class="bg-gradient-primary">
    <ext:fragment name="header"/>
    <div class="container">
        <div class="card fadeIn shadow-lg my-5">
            <div class="card-body p-0">
                <!-- Nested Row within Card Body -->
                <div class="row">
                    <div class="col-lg-12">
                        <div class="p-5">
                            <div class="text-center">
                                <h1 class="h4 mb-4"><fmt:message key="create"/></h1>
                            </div>
                            <form name="signup" method="post" <ext:action path="/auth/signup"/>
                                  class="user text-center">
                                <div class="form-floating mb-2">
                                    <input type="text" id="username" name="username"
                                           class="form-control form-control-user mb-2" required minlength="8" maxlength="14" placeholder="Username">
                                    <label for="username"><fmt:message key="user.username"/></label>
                                </div>
                                <c:if test="${requestScope.usernameError}">
                                    <div class="alert alert-danger fadeIn" id="usernameError" role="alert">
                                        <fmt:message key="create.error.username"/>
                                    </div>
                                </c:if>
                                <script>
                                    document.addEventListener('DOMContentLoaded', () => {
                                        setTimeout(() => {
                                            const bsAlert = bootstrap.Alert.getOrCreateInstance('#usernameError');
                                            bsAlert.close();
                                        }, 3000);
                                    });
                                </script>
                                <div class="row row-cols-2 gx-2 mb-2">
                                    <div class="form-floating">
                                        <input type="text"  id="firstName" name="firstName"
                                               class="form-control form-control-user" required minlength="1" maxlength="20" placeholder="First Name">
                                        <label for="firstName"><fmt:message key="user.firstName"/></label>
                                    </div>
                                    <div class="form-floating">
                                        <input type="text"  id="lastName" name="lastName"
                                               class="form-control form-control-user" required minlength="1" maxlength="20" placeholder="Last Name">
                                        <label for="lastName"><fmt:message key="user.lastName"/></label>
                                    </div>
                                </div>
                                <div class="form-floating  mb-2">
                                    <input type="email"  id="email" name="email"
                                           class="form-control form-control-user mb-2" required placeholder="Email">
                                    <label for="email"><fmt:message key="user.email"/></label>
                                </div>
                                <c:if test="${requestScope.emailError}">
                                    <div class="alert alert-danger fadeIn" id="emailAlert" role="alert">
                                        <fmt:message key="create.error.email"/>
                                    </div>
                                </c:if>
                                <script>
                                    document.addEventListener('DOMContentLoaded', () => {
                                        setTimeout(() => {
                                            const bsAlert = bootstrap.Alert.getOrCreateInstance('#emailAlert');
                                            bsAlert.close();
                                        }, 3000);
                                    });
                                </script>
                                <div class="form-floating mb-2">
                                    <input type="tel"  id="phone" name="phone"
                                           class="form-control form-control-user mb-2" required placeholder="Phone">
                                    <label for="phone"><fmt:message key="user.phone"/></label>
                                </div>
                                <div class="form-floating  mb-2">
                                    <div class="form-floating">
                                        <input type="password"  id="password" name="password"
                                               class="form-control form-control-user" required minlength="8" maxlength="14" placeholder="Password">
                                        <label for="password"><fmt:message key="user.password"/></label>
                                    </div>
                                </div>
                                <input type="submit" class="btn btn-primary btn-user btn-block" value=<fmt:message key="create"/>>
                            </form>
                            <hr>
                            <div class="text-center">
                                <a class="small" <ext:href path ="/auth/resetpassword"/> ><fmt:message key="password.reset.title"/></a>
                            </div>
                            <div class="text-center">
                                <a class="small" <ext:href path="/auth/signin"/> ><fmt:message key="signIn"/></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <ext:fragment name="import/bootstrapScript"/>
</body>

</html>