<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/pages/fragments/taglib.jspf" %>
<%@ attribute name="receipt" required="true" type="com.epam.finalproject.dto.ReceiptDTO" %>
<%@ attribute name="flows" required="true" type="java.util.List<com.epam.finalproject.dto.ReceiptStatusFlowDTO>" %>
<td>
    <a class="btn btn-outline-primary" role="button" <ext:href path="/order/${receipt.id.toString()}"/> >
        <fmt:message key="order.action.view"/>
    </a>
    <sec:authorize expr="hasRole('MANAGER') || hasRole('ADMIN')">
        <a class="btn btn-outline-primary" role="button" <ext:href path="/order/${receipt.id.toString()}/update"/> >
            <fmt:message key="order.action.update"/>
        </a>
    </sec:authorize>
    <c:if test="${receipt.status.name.equals('WAIT_FOR_PAYMENT')}">
        <sec:authorize expr="hasRole('CUSTOMER')">
            <a class="btn btn-outline-primary" role="button" <ext:href path="/order/${receipt.id.toString()}/pay"/> >
                <fmt:message key="order.action.pay"/>
            </a>
        </sec:authorize>
    </c:if>
    <div class="dropdown">
        <a aria-expanded="false" class="btn btn-secondary dropdown-toggle" data-bs-toggle="dropdown"
           id="dropdownMenuLink"
           role="button">
            <fmt:message key="order.action.status"/>
        </a>
        <ul aria-labelledby="dropdownMenuLink" class="dropdown-menu">
            <c:forEach items="${flows}" var="flow">
                <c:if test="${flow.fromStatus.id == receipt.status.id}">
                    <li>
                        <form id="status-form" method="post" <ext:action
                                path="/order/${receipt.id.toString()}/status/change"/> >
                            <input name="statusId" value="${flow.toStatus.id}" type="hidden"/>
                            <button class="btn dropdown-item btn-outline-primary"
                                    role="button"
                                    type="submit">
                                <ext:statusGet var="${flow.toStatus.name}"/>
                            </button>
                        </form>
                    </li>
                </c:if>
            </c:forEach>
        </ul>
    </div>
</td>