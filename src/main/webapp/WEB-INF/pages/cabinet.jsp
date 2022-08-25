<%@ include file="/WEB-INF/pages/fragments/page.jspf" %>
<%@ include file="/WEB-INF/pages/fragments/taglib.jspf" %>
<jsp:useBean id="active" scope="request" type="java.lang.String"/>
<jsp:useBean id="type" scope="request" type="java.lang.String"/>
<!DOCTYPE html >
<html lang="en">
<head>
    <meta content='text/html; charset=UTF-8' http-equiv='Content-Type'/>
    <meta content="width=device-width, initial-scale=1" name="viewport">
    <ext:fragment name="import/bootstrap"/>
    <ext:fragment name="import/animation"/>
    <ext:fragment name="import/font-awesome"/>
    <link href="${pageContext.request.contextPath}/static/css/cabinet.css" rel="stylesheet">
    <title><fmt:message key="header.cabinet"/></title>
</head>
<body>
    <ext:fragment name="svg"/>
    <ext:fragment name="header"/>
    <main>
        <c:if test="${type.equals('manager')}">
            <ext:fragment name="side/manager"/>
        </c:if>
        <c:if test="${type.equals('master')}">
            <ext:fragment name="side/master"/>
        </c:if>
        <c:if test="${type.equals('customer')}">
            <ext:fragment name="side/customer"/>
        </c:if>

        <div class="b-example-divider sticky-top"></div>

        <div class="flex-shrink-0 flex-grow-1 customContent p-3 bg-white">
            <c:if test="${type == 'manager'}">
                <c:if test="${active.equals('orders')}">
                    <ext:fragment name="manager/orders"/>
                </c:if>
                <c:if test="${active.equals('users')}">
                    <ext:fragment name="manager/users"/>
                </c:if>
                <c:if test="${active.equals('masters')}">
                    <ext:fragment name="manager/masters"/>
                </c:if>
                <c:if test="${active.equals('responses')}">
                    <ext:fragment name="manager/responses"/>
                </c:if>
            </c:if>
            <c:if test="${type == 'master'}">
                <c:if test="${active.equals('orders')}">
                    <ext:fragment name="master/orders"/>
                </c:if>
                <c:if test="${active.equals('responses')}">
                    <ext:fragment name="master/responses"/>
                </c:if>
            </c:if>
            <c:if test="${type == 'customer'}">
                <c:if test="${active.equals('orders')}">
                    <ext:fragment name="customer/orders"/>
                </c:if>
                <c:if test="${active.equals('responses')}">
                    <ext:fragment name="customer/responses"/>
                </c:if>
                <c:if test="${active.equals('wallets')}">
                    <ext:fragment name="customer/wallets"/>
                </c:if>
            </c:if>
        </div>

    </main>
    <ext:fragment name="import/ajax"/>
    <ext:fragment name="import/bootstrapScript"/>
    <script  xmlns:th="http://www.thymeleaf.org" th:fragment="clearForm">
        $(document).ready(function () {
            $('form[method="get"]').submit(function () {
                $(this).find(':input').each(function () {
                    var inp = $(this);
                    if (!inp.val()) {
                        inp.remove();
                    }
                });
            });
        });
    </script>
</body>
</html>