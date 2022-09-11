<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="var" required="true" type="java.lang.String" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="locale" value="${sessionScope.get('SessionLocaleResolver.LOCALE')}" />

<fmt:setLocale value="${locale}"/>

<fmt:setBundle basename="messages" />

<c:choose>
    <c:when test="${var.equals('UNVERIFIED')}">
        <strong><fmt:message key="role.unverified"/></strong>
    </c:when>
    <c:when test="${var.equals('BLOCKED')}">
        <strong><fmt:message key="role.blocked"/></strong>
    </c:when>
    <c:when test="${var.equals('CUSTOMER')}">
        <strong><fmt:message key="role.customer"/></strong>
    </c:when>
    <c:when test="${var.equals('MASTER')}">
        <strong><fmt:message key="role.master"/></strong>
    </c:when>
    <c:when test="${var.equals('MANAGER')}">
        <strong><fmt:message key="role.manager"/></strong>
    </c:when>
    <c:when test="${var.equals('ADMIN')}">
        <strong><fmt:message key="role.admin"/></strong>
    </c:when>
</c:choose>