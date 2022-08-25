<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/pages/fragments/taglib.jspf" %>
<%@ attribute name="receipt" required="true" type="com.epam.finalproject.dto.ReceiptDTO" %>
<td>
    <ul class="list-group">
        <li class="list-group-item">${receipt.delivery.country}</li>
        <li class="list-group-item">${receipt.delivery.city}</li>
    </ul>
</td>