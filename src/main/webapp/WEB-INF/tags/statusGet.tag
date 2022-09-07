<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="var" required="true" type="java.lang.String" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="locale" value="${sessionScope.get('SessionLocaleResolver.LOCALE')}" />

<fmt:setLocale value="${locale}"/>

<fmt:setBundle basename="messages" />

<c:choose>
    <c:when test="${var.equals('CREATED')}">
        <strong><fmt:message key="status.created"/></strong>
    </c:when>
    <c:when test="${var.equals('WAIT_FOR_PAYMENT')}">
        <strong><fmt:message key="status.waitForPayment"/></strong>
    </c:when>
    <c:when test="${var.equals('PAID')}">
        <strong><fmt:message key="status.paid"/></strong>
    </c:when>
    <c:when test="${var.equals('IN_WORK')}">
        <strong><fmt:message key="status.inWork"/></strong>
    </c:when>
    <c:when test="${var.equals('DONE')}">
        <strong><fmt:message key="status.done"/></strong>
    </c:when>
    <c:when test="${var.equals('CANCELED')}">
        <strong><fmt:message key="status.canceled"/></strong>
    </c:when>
</c:choose>