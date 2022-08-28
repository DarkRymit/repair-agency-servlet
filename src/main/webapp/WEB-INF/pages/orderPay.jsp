<jsp:useBean id="order" scope="request" type="com.epam.finalproject.dto.ReceiptDTO"/>
<%@ include file="/WEB-INF/pages/fragments/page.jspf" %>
<%@ include file="/WEB-INF/pages/fragments/taglib.jspf" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta content="IE=edge" http-equiv="X-UA-Compatible">
    <meta content="width=device-width, initial-scale=1, shrink-to-fit=no" name="viewport">
    <meta content="" name="description">
    <meta content="" name="author">

    <title><fmt:message key="order.title"/></title>
    <ext:fragment name="import/bootstrap"/>
    <ext:fragment name="import/font-awesome"/>
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
                                <strong> Order </strong>
                                <ul class="list-group">
                                    <li class="list-group-item">
                                        <strong><fmt:message key="order.id.name"/></strong>
                                        <span>:</span>
                                        <strong> ${order.id} </strong>
                                    </li>
                                    <li class="list-group-item">
                                        <strong><fmt:message key="order.user.name"/></strong>
                                        <span>:</span>
                                        <a class=" text-dark text-decoration-none"
                                        <ext:action path="/user/${order.user.id.toString()}/profile"/> >
                                        @${order.user.username}
                                        </a>
                                    </li>
                                    <li class="list-group-item">
                                        <strong><fmt:message key="order.category.name"/></strong>
                                        <span>:</span>
                                            ${order.category.name}
                                    </li>
                                    <li class="list-group-item">
                                        <strong><fmt:message key="order.totalPrice.name"/></strong>
                                        <span>:</span>
                                        <fmt:formatNumber value="${order.totalPrice}" minFractionDigits="0"/>
                                        <strong>${order.priceCurrency.code}</strong>
                                    </li>
                                </ul>
                                <strong> <fmt:message key="order.pay.pickWallet"/> </strong>
                                <form id="form" method="post"
                                        <ext:action path="/order/${order.id.toString()}/pay"/> >
                                    <ul class="list-group">
                                        <li class="list-group-item">
                                            <select class="form-select text-center" name="walletId"
                                                    id="walletId">
                                                <jsp:useBean id="wallets" scope="request" type="java.util.List<com.epam.finalproject.dto.WalletDTO>"/>
                                                <c:forEach items="${wallets}" var="wallet">
                                                    <option value="${wallet.id}">
                                                        <strong><c:out value="${wallet.name}"/></strong>
                                                        <strong><fmt:formatNumber value="${wallet.moneyAmount}" minFractionDigits="0"/></strong>
                                                        <strong><c:out value="${wallet.moneyCurrency.code}"/></strong>
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </li>
                                    </ul>
                                    <button class="btn btn-primary" id="submit" type="submit"><fmt:message key="order.pay.button"/></button>
                                </form>
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