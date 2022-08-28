<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/pages/fragments/taglib.jspf" %>
<%@ attribute name="receipt" required="true" type="com.epam.finalproject.dto.ReceiptDTO" %>
<td>
    <c:if test="${receipt.master == null}">
        <i class=" text-dark text-decoration-none">
            <fmt:message key="order.master.notAssigned"/>
        </i>
    </c:if>
    <c:if test="${receipt.master != null}">

        <a class=" text-dark text-decoration-none"
                <ext:href path="/user/${receipt.master.id.toString()}/profile"/>
        >@${receipt.master.username}</a>

    </c:if>
</td>