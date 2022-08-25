<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/pages/fragments/taglib.jspf" %>
<%@ tag description="Make option selected given if statement" pageEncoding="UTF-8" %>
<%@ attribute name="page" required="true" type="com.epam.finalproject.framework.data.Page" %>

<c:if test="${page.totalPages > 0 && !(page.isLast() && page.isFirst())}">
<nav>
    <ul class="pagination">
        <c:if test="${!page.isFirst()}">
        <li class="page-item" >
            <a class="page-link"
            <ext:hrefReplaceParam name='page' value="${page.getNumber()-1}"/>
            <span aria-hidden="true">&laquo;</span>
            </a>
        </li>
        </c:if>
    <c:if test="${page.getNumber()>=2}">
        <li class="page-item" ><a class="page-link"
                <ext:hrefReplaceParam name='page' value='0'/>> 1</a></li>
    </c:if>
    <c:if test="${page.getNumber()>=3}">
        <li class="page-item" ><a class="page-link"
                <ext:hrefReplaceParam name='page' value="${page.getNumber()-2}"/>>...</a>
        </li>
    </c:if>
    <c:if test="${!page.isFirst()}">
        <li class="page-item" th:if="${!page.isFirst()}"><a class="page-link"
                <ext:hrefReplaceParam name='page' value="${page.getNumber()-1}"/>> ${page.getNumber()}</a>
        </li>
    </c:if>
        <li class="page-item active"><a class="page-link"> ${page.getNumber()+1}</a></li>
    <c:if test="${!page.isLast()}">
        <li class="page-item" ><a class="page-link"
                <ext:hrefReplaceParam name='page' value="${page.getNumber()+1}"/>>${page.getNumber()+2}</a>
        </li>
    </c:if>
    <c:if test="${page.getTotalPages()-page.getNumber()>=4}">
        <li class="page-item"><a
                class="page-link"
                <ext:hrefReplaceParam name='page' value="${page.getNumber()+2}"/>>...</a>
        </li>
    </c:if>
    <c:if test="${page.getTotalPages()-page.getNumber()>=3}">
        <li class="page-item"><a
                class="page-link"
                <ext:hrefReplaceParam name='page' value="${page.getTotalPages()-1}"/>>${page.getTotalPages()}</a></li>
    </c:if>
    <c:if test="${!page.isLast()}">
        <li class="page-item">
            <a class="page-link"
                    <ext:hrefReplaceParam name='page' value="${page.getNumber()+1}"/>>
                <span aria-hidden="true">&raquo;</span>
            </a>
        </li>
    </c:if>
    </ul>
</nav>
</c:if>