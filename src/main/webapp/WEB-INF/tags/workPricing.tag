<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="locale" value="${sessionScope.get('SessionLocaleResolver.LOCALE')}" />

<fmt:setLocale value="${locale.language}"/>

<fmt:setBundle basename="messages"/>

<%@ attribute name="lower" required="true" type="java.math.BigDecimal" %>
<%@ attribute name="upper" required="true" type="java.math.BigDecimal" %>

<c:choose>
    <c:when test="${lower != null && upper == null }">
        <fmt:message key="work.price.from"/> <fmt:formatNumber value="${lower}" minFractionDigits="0"/>
    </c:when>
    <c:when test="${lower == null && upper != null }">
        <fmt:message key="work.price.up"/> <fmt:formatNumber value="${upper}" minFractionDigits="0"/>
    </c:when>
    <c:when test="${lower != null && upper != null && lower.compareTo(upper)==0}">
        <fmt:formatNumber value="${lower}" minFractionDigits="0"/>
    </c:when>
    <c:otherwise>
        <fmt:message
                key="work.price.fromUp.from"/> <fmt:formatNumber value="${lower}" minFractionDigits="0"/>
        <fmt:message
                key="work.price.fromUp.to"/> <fmt:formatNumber value="${upper}" minFractionDigits="0"/> </c:otherwise>
</c:choose>