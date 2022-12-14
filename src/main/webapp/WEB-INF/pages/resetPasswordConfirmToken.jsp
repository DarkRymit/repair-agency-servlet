<%@ include file="/WEB-INF/pages/fragments/page.jspf" %>
<%@ include file="/WEB-INF/pages/fragments/taglib.jspf" %>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title><fmt:message key="password.reset.title"/></title>

    <!-- CSS only -->
    <ext:fragment name="import/bootstrap"/>
    <ext:fragment name="import/animation"/>
</head>

<body class="bg-gradient-primary">
    <ext:fragment name="header"/>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-6 fadeIn">
                <div class="card shadow-lg my-5">
                    <div class="card-body p-0">
                        <!-- Nested Row within Card Body -->
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="p-5">
                                    <div class="text-center">
                                        <h1 class="h4 mb-4"><fmt:message key="password.reset.newPassword"/></h1>
                                    </div>
                                    <c:if test="${requestScope.error}">
                                        <div class="alert alert-danger fadeIn" id="myAlert" role="alert">
                                            <fmt:message key="password.reset.token.invalid"/>
                                        </div>
                                    </c:if>
                                    <script>
                                        document.addEventListener('DOMContentLoaded', () => {
                                            setTimeout(() => {
                                                const bsAlert = bootstrap.Alert.getOrCreateInstance('#myAlert');
                                                bsAlert.close();
                                            }, 3000);
                                        });
                                    </script>

                                    <jsp:useBean id="token" scope="request" type="java.lang.String"/>
                                    <form method="post" <ext:action path="/auth/resetpassword/confirm/${token}"/>
                                          class="user text-center">
                                        <div class="form-group">
                                            <div class="form-floating mb-2">
                                                <input type="text" id="password" name="password"
                                                       class="form-control mb-2" required minlength="8" maxlength="14" placeholder="Password">
                                                <label for="password"><fmt:message key="password.reset.newPassword.title"/></label>
                                            </div>
                                        </div>
                                        <input type="submit" class="btn btn-primary btn-user btn-block" value=<fmt:message key="password.reset.confirm.button"/>>
                                    </form>
                                </div>
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