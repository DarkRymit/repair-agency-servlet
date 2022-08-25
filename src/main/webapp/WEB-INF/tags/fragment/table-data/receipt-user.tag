<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/pages/fragments/taglib.jspf" %>
<%@ attribute name="receipt" required="true" type="com.epam.finalproject.dto.ReceiptDTO" %>
<td>
    <a class=" text-dark text-decoration-none"
       <ext:href path="/user/${receipt.user.id.toString()}/profile"/>
    >@${receipt.user.username}</a>
</td>