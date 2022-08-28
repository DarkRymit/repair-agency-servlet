<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/pages/fragments/taglib.jspf" %>
<%@ attribute name="receipt" required="true" type="com.epam.finalproject.dto.ReceiptDTO" %>
<td>
    <c:choose>
        <c:when test="${receipt.status.name.equals('CREATED')}">
            <span> <fmt:message key="status.created"/></span>
        </c:when>
        <c:when test="${receipt.status.name.equals('WAIT_FOR_PAYMENT')}">
            <span> <fmt:message key="status.waitForPayment"/></span>
        </c:when>
        <c:when test="${receipt.status.name.equals('PAID')}">
            <span> <fmt:message key="status.paid"/></span>
        </c:when>
        <c:when test="${receipt.status.name.equals('IN_WORK')}">
            <span> <fmt:message key="status.inWork"/></span>
        </c:when>
        <c:when test="${receipt.status.name.equals('DONE')}">
            <span> <fmt:message key="status.done"/></span>
        </c:when>
        <c:when test="${receipt.status.name.equals('CANCELED')}">
            <span> <fmt:message key="status.canceled"/></span>
        </c:when>
    </c:choose>
</td>