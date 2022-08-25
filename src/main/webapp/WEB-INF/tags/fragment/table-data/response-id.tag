<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/pages/fragments/taglib.jspf" %>
<%@ attribute name="response" required="true" type="com.epam.finalproject.dto.ReceiptResponseDTO" %>
<td>
    <a class=" text-dark text-decoration-none"
       <ext:href path="/order/${response.receipt.id.toString()}"/>
    >${response.receipt.id}</a>
</td>