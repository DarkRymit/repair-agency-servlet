<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="var" required="true" type="java.lang.String" %>
<c:choose>
    <c:when test="${var.equals('CREATED')}">
        <strong>Created</strong>
    </c:when>
    <c:when test="${var.equals('WAIT_FOR_PAYMENT')}">
        <strong>Wait for payment</strong>
    </c:when>
    <c:when test="${var.equals('PAID')}">
        <strong>Paid</strong>
    </c:when>
    <c:when test="${var.equals('IN_WORK')}">
        <strong>In work</strong>
    </c:when>
    <c:when test="${var.equals('DONE')}">
        <strong>Done</strong>
    </c:when>
    <c:when test="${var.equals('CANCELED')}">
        <strong>Canceled</strong>
    </c:when>
</c:choose>