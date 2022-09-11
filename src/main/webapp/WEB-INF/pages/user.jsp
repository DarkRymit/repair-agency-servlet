<jsp:useBean id="user" scope="request" type="com.epam.finalproject.dto.UserDTO"/>
<%@ include file="/WEB-INF/pages/fragments/page.jspf" %>
<%@ include file="/WEB-INF/pages/fragments/taglib.jspf" %>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta charset="utf-8">
    <meta content="IE=edge" http-equiv="X-UA-Compatible">
    <meta content="width=device-width, initial-scale=1, shrink-to-fit=no" name="viewport">
    <meta content="" name="description">
    <meta content="" name="author">
    <title>${user.username}</title>
    <ext:fragment name="import/bootstrap"/>
    <ext:fragment name="import/animation"/>
</head>

<body>
    <ext:fragment name="header"/>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-4 fadeIn">
                <div class="card shadow-lg my-5">
                    <div class="card-body p-0">
                        <div class="row">
                            <div class="col-lg-12 p-5 text-center">
                                <strong>${user.username}</strong>
                                <ul class="list-group">
                                    <li class="list-group-item">
                                        <strong><fmt:message key="user.email"/></strong>
                                        <span>:</span>
                                        ${user.email}
                                    </li>
                                    <li class="list-group-item">
                                        <strong><fmt:message key="user.name"/></strong>
                                        <span>:</span>
                                        <div class="row">
                                            <div class="col-6">
                                                ${user.firstName}
                                            </div>
                                            <div class="col-6">
                                                ${user.lastName}
                                            </div>
                                        </div>
                                    </li>
                                    <li class="list-group-item">
                                        <strong> <fmt:message key="user.creationDate"/></strong>
                                        <span>:</span>
                                        <frmt:dataTime dateTime="${user.creationDate}"/>
                                    </li>
                                    <li class="list-group-item">
                                        <strong><fmt:message key="user.roles"/></strong>
                                        <span>:</span>
                                        <br>
                                        <ul class="list-group">
                                            <c:forEach var="role" items="${user.roles}">
                                                <li class="list-group-item">
                                                    <ext:roleGet var="${role.name}"/>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </li>
                                    <sec:authorize expr="hasRole('MANAGER') || hasRole('ADMIN')">
                                        <c:if test="${user.roles.stream().map(r -> r.name).filter(n -> n.equals('CUSTOMER')).findFirst().orElse(null) != null}">
                                            <li class="list-group-item">
                                                <strong><fmt:message key="user.wallets"/></strong>
                                                <span>:</span>
                                                <br>
                                                <ul class="list-group">
                                                    <c:forEach var="wallet" items="${user.wallets}">
                                                        <li class="list-group-item">
                                                            <strong>${wallet.name}</strong>
                                                            <strong><fmt:formatNumber value="${wallet.moneyAmount}"
                                                                                      minFractionDigits="0"/></strong>
                                                            <strong>${wallet.moneyCurrency.code}</strong>
                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                            </li>

                                            <li class="list-group-item">
                                                <form method="post" <ext:action path="/wallet/addMoney"/> >
                                                    <input hidden name="redirectUrl" value="/user/${user.id}/profile">
                                                    <label for="walletId"><fmt:message
                                                            key="wallet.price.change.pickUp"/> </label>
                                                    <select
                                                            class="form-select" id="walletId" name="id">
                                                        <c:forEach var="wallet" items="${user.wallets}">
                                                            <option value="${wallet.id}">
                                                                <strong>${wallet.name}</strong>
                                                                <strong><fmt:formatNumber value="${wallet.moneyAmount}"
                                                                                          minFractionDigits="0"/></strong>
                                                                <strong>${wallet.moneyCurrency.code}</strong>
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                    <div class="form-floating  mb-2">
                                                        <input class="form-control price"
                                                               id="moneyToAdd"
                                                               name="moneyToAdd"
                                                               required
                                                               type="number">
                                                        <label for="moneyToAdd">
                                                            <fmt:message key="wallet.price.change.add.name"/>
                                                        </label>
                                                    </div>
                                                    <button type="submit" class="btn btn-primary" id="submit">
                                                        <fmt:message
                                                                key="wallet.price.change.add.submit"/></button>
                                                </form>
                                            </li>
                                        </c:if>
                                    </sec:authorize>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <!-- JavaScript -->
    <ext:fragment name="import/bootstrapScript"/>
</body>

</html>