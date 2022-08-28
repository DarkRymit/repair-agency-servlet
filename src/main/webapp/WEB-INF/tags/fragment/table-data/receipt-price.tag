<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/pages/fragments/taglib.jspf" %>
<%@ attribute name="receipt" required="true" type="com.epam.finalproject.dto.ReceiptDTO" %>
<td>
    <c:choose>
        <c:when test="${receipt.totalPrice == null}">
            <i class=" text-dark text-decoration-none">
                <fmt:message key="order.totalPrice.notAssigned"/>
            </i>
        </c:when>
        <c:otherwise>
            <fmt:formatNumber value="${receipt.totalPrice}" minFractionDigits="0"/>
        </c:otherwise>
    </c:choose>
    <i>${receipt.priceCurrency.code}</i>
</td>