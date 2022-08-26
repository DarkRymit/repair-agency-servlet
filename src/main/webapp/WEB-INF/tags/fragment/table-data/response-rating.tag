<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/pages/fragments/taglib.jspf" %>
<%@ attribute name="response" required="true" type="com.epam.finalproject.dto.ReceiptResponseDTO" %>
<td>
    <c:forEach begin="0" end="${response.rating}">
        <i class="fa-solid fa-star"></i>
    </c:forEach>
</td>