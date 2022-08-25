<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/pages/fragments/taglib.jspf" %>
<%@ attribute name="receipt" required="true" type="com.epam.finalproject.dto.ReceiptDTO" %>
<td>
    <c:choose>
        <c:when test="${receipt.status.name.equals('CREATED')}">
            <span>Created</span>
        </c:when>
        <c:when test="${receipt.status.name.equals('WAIT_FOR_PAYMENT')}">
            <span>Wait for payment</span>
        </c:when>
        <c:when test="${receipt.status.name.equals('PAID')}">
            <span>Paid</span>
        </c:when>
        <c:when test="${receipt.status.name.equals('IN_WORK')}">
            <span>In work</span>
        </c:when>
        <c:when test="${receipt.status.name.equals('DONE')}">
            <span>Done</span>
        </c:when>
        <c:when test="${receipt.status.name.equals('CANCELED')}">
            <span>Canceled</span>
        </c:when>
    </c:choose>
</td>