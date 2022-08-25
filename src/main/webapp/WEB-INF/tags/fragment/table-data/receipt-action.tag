<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/pages/fragments/taglib.jspf" %>
<%@ attribute name="receipt" required="true" type="com.epam.finalproject.dto.ReceiptDTO" %>
<%@ attribute name="flows" required="true" type="java.util.List<com.epam.finalproject.dto.ReceiptStatusFlowDTO>" %>
<td>
    <a class="btn btn-outline-primary" role="button" <ext:href path="/order/${receipt.id.toString()}"/> >
        View
    </a>
    <sec:authorize expr="hasRole(MANAGER)">
        <a class="btn btn-outline-primary" role="button" <ext:href path="/order/${receipt.id.toString()}/update"/> >
            Update
        </a>
    </sec:authorize>
    <c:if test="${receipt.status.name.equals('WAIT_FOR_PAYMENT')}">
        <sec:authorize expr="hasRole(CUSTOMER)">
            <a class="btn btn-outline-primary" role="button" <ext:href path="/order/${receipt.id.toString()}/pay"/> >
                Pay
            </a>
        </sec:authorize>
    </c:if>
    <div class="dropdown">
        <a aria-expanded="false" class="btn btn-secondary dropdown-toggle" data-bs-toggle="dropdown"
           id="dropdownMenuLink"
           role="button">
            Status
        </a>
        <ul aria-labelledby="dropdownMenuLink" class="dropdown-menu">
            <c:forEach items="${flows}" var="flow">
                <c:if test="${flow.fromStatus.id == receipt.status.id}">
                    <li>
                        <form id="status-form" method="post" <ext:action
                                path="/order/${receipt.id.toString()}/status/change"/> >
                            <input name="statusId" value="${flow.toStatus.id}" type="hidden"/>
                            <c:choose>
                                <c:when test="${flow.toStatus.name.equals('CREATED')}">
                                    <button class="btn dropdown-item btn-outline-primary"
                                            role="button"
                                            type="submit">Created
                                    </button>
                                </c:when>
                                <c:when test="${flow.toStatus.name.equals('WAIT_FOR_PAYMENT')}">
                                    <button class="btn dropdown-item btn-outline-primary"
                                            role="button"
                                            type="submit">Wait for payment
                                    </button>
                                </c:when>
                                <c:when test="${flow.toStatus.name.equals('PAID')}">
                                    <button class="btn dropdown-item btn-outline-primary"
                                            role="button"
                                            type="submit">Paid
                                    </button>
                                </c:when>
                                <c:when test="${flow.toStatus.name.equals('IN_WORK')}">
                                    <button class="btn dropdown-item btn-outline-primary"
                                            role="button"
                                            type="submit">In work
                                    </button>
                                </c:when>
                                <c:when test="${flow.toStatus.name.equals('DONE')}">
                                    <button class="btn dropdown-item btn-outline-primary"
                                            role="button"
                                            type="submit"> Done
                                    </button>
                                </c:when>
                                <c:when test="${flow.toStatus.name.equals('CANCELED')}">
                                    <button class="btn dropdown-item btn-outline-primary"
                                    role="button"
                                    type="submit">Canceled
                                    </button>
                                </c:when>
                            </c:choose>
                        </form>
                    </li>
                </c:if>
            </c:forEach>
        </ul>
    </div>
</td>