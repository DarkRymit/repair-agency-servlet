<jsp:useBean id="order" scope="request" type="com.epam.finalproject.dto.ReceiptDTO"/>
<%@ include file="/WEB-INF/pages/fragments/page.jspf" %>
<%@ include file="/WEB-INF/pages/fragments/taglib.jspf" %>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">

<head>

    <meta charset="utf-8">
    <meta content="IE=edge" http-equiv="X-UA-Compatible">
    <meta content="width=device-width, initial-scale=1, shrink-to-fit=no" name="viewport">
    <meta content="" name="description">
    <meta content="" name="author">

    <title><fmt:message key="order.title"/></title>
    <ext:fragment name="import/bootstrap"/>
    <ext:fragment name="import/font-awesome"/>
    <ext:fragment name="import/animation"/>
    <ext:fragment name="import/rating"/>
</head>

<body>
    <ext:fragment name="header"/>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-4 fadeIn">
                <div class="card shadow-lg my-5">
                    <div class="card-body p-0">
                        <div class="row">
                            <div class="col-lg-12 p-5 text-center">
                                <strong> <fmt:message key="order.name"/> </strong>
                                <ul class="list-group">
                                    <li class="list-group-item">
                                        <strong><fmt:message key="order.id.name"/></strong>
                                        <span>:</span>
                                        <strong>${order.id}</strong>
                                    </li>
                                    <li class="list-group-item">
                                        <strong><fmt:message key="order.user.name"/></strong>
                                        <span>:</span>
                                        <a class=" text-dark text-decoration-none"
                                                <ext:href path="/user/${order.user.id.toString()}/profile"/>>
                                            @${order.user.username}</a>
                                    </li>
                                    <li class="list-group-item">
                                        <strong><fmt:message key="order.status.name"/></strong>
                                        <span>:</span>
                                        <ext:statusGet var="${order.status.name}"/>
                                    </li>
                                    <li class="list-group-item">
                                        <strong><fmt:message key="order.master.name"/></strong>
                                        <span>:</span>
                                        <c:choose>
                                            <c:when test="${order.master == null}">
                                                <i class=" text-dark text-decoration-none">
                                                    <fmt:message key="order.master.notAssigned"/>
                                                </i>
                                            </c:when>
                                            <c:otherwise>
                                                <a class=" text-dark text-decoration-none"
                                                        <ext:href path="/user/${order.master.id.toString()}/profile"/> >
                                                    @${order.master.username}</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </li>
                                    <li class="list-group-item">
                                        <strong> <fmt:message key="order.category.name"/></strong>
                                        <span>:</span>
                                        ${order.category.name}
                                    </li>
                                    <li class="list-group-item">
                                        <strong><fmt:message key="order.totalPrice.name"/></strong>
                                        <span>:</span>
                                        <c:choose>
                                            <c:when test="${order.totalPrice == null}">
                                                <i class=" text-dark text-decoration-none">
                                                    <fmt:message key="order.totalPrice.notAssigned"/>
                                                </i>
                                            </c:when>
                                            <c:otherwise>
                                                <fmt:formatNumber value="${order.totalPrice}" minFractionDigits="0"/>
                                            </c:otherwise>
                                        </c:choose>
                                        <strong>${order.priceCurrency.code}</strong>
                                    </li>
                                    <li class="list-group-item">
                                        <strong><fmt:message key="order.items.name"/></strong>
                                        <span>:</span>
                                        <br>
                                        <c:forEach var="item" items="${order.items}">
                                            <ul class="list-group">
                                                <li class="list-group-item">
                                                        ${item.repairWork.name}
                                                    <br>
                                                    <c:choose>
                                                        <c:when test="${item.priceAmount == null}">
                                                            <i class=" text-dark text-decoration-none">
                                                                <fmt:message key="order.items.priceAmount.notAssigned"/>
                                                            </i>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <fmt:formatNumber value="${item.priceAmount}" minFractionDigits="0"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <strong>${order.priceCurrency.code}</strong>
                                                </li>
                                            </ul>
                                        </c:forEach>
                                    </li>
                                    <li class="list-group-item">
                                        <strong><fmt:message key="order.delivery.title"/></strong><span>:</span>
                                        <br>
                                        <ul class="list-group">
                                            <c:if test="${order.delivery.country != null}">
                                                <li class="list-group-item">
                                                    <strong><fmt:message key="order.delivery.country.name"/></strong>
                                                    <span>:</span>
                                                    <span>${order.delivery.country}</span>
                                                </li>
                                            </c:if>
                                            <c:if test="${order.delivery.state != null}">
                                                <li class="list-group-item">
                                                    <strong><fmt:message key="order.delivery.state.name"/></strong>
                                                    <span>:</span>
                                                    <span>${order.delivery.state}</span>
                                                </li>
                                            </c:if>
                                            <c:if test="${order.delivery.city != null}">
                                                <li class="list-group-item">
                                                    <strong><fmt:message key="order.delivery.city.name"/></strong>
                                                    <span>:</span>
                                                    <span>${order.delivery.city}</span>
                                                </li>
                                            </c:if>
                                            <c:if test="${order.delivery.localAddress != null}">
                                                <li class="list-group-item">
                                                    <strong><fmt:message key="order.delivery.localAddress.name"/></strong>
                                                    <span>:</span>
                                                    <span>${order.delivery.localAddress}</span>
                                                </li>
                                            </c:if>
                                            <c:if test="${order.delivery.postalCode != null}">
                                                <li class="list-group-item">
                                                    <strong><fmt:message key="order.delivery.postalCode.name"/></strong>
                                                    <span>:</span>
                                                    <span>${order.delivery.postalCode}</span>
                                                </li>
                                            </c:if>
                                        </ul>
                                    </li>
                                    <li class="list-group-item">
                                        <strong><fmt:message key="order.creationDate.name"/></strong>
                                        <span>:</span>
                                        <strong><frmt:dataTime dateTime="${order.creationDate}"/></strong>
                                    </li>
                                    <li class="list-group-item">
                                        <strong><fmt:message key="order.note.name"/></strong>
                                        <span>:</span>
                                        ${order.note}
                                    </li>
                                </ul>

                                <c:if test="${order.status.name.equals('DONE')}">
                                    <c:set var="response" scope="request" value="${requestScope.response}"/>
                                    <c:if test="${response!=null}">
                                        <strong><fmt:message key="order.response.name"/></strong>
                                        <ul class="list-group">
                                            <li class="list-group-item">
                                                <strong><fmt:message key="order.response.text.name"/></strong>
                                                <span>:</span>
                                                    ${response.text}
                                            </li>
                                            <li class="list-group-item">
                                                <strong><fmt:message key="order.response.rating.name"/></strong>
                                                <span>:</span>
                                                <c:forEach begin="0" end="${response.rating}">
                                                    <i class="fa-solid fa-star"></i>
                                                </c:forEach>
                                            </li>
                                        </ul>
                                    </c:if>
                                    <c:if test="${response==null and order.user.username == sec:name()}">
                                        <strong> <fmt:message key="order.response.create.name"/></strong>
                                        <form method="post" id="form" <ext:action
                                                path="/order/${order.id.toString()}/response/create"/> >
                                            <ul class="list-group">
                                                <li class="list-group-item">
                                                    <div class="input-group">
                                                        <strong class="input-group-text"><fmt:message key="order.response.text.name"/></strong>
                                                        <textarea maxlength="255" aria-label="Note" class="form-control"
                                                                  name="text" id="text"></textarea>
                                                    </div>
                                                </li>
                                                <li class="list-group-item">
                                                    <strong><fmt:message key="order.response.rating.name"/></strong>
                                                    <span>:</span>
                                                    <div class="rating">
                                                        <input class="star star-5" id="star-5" type="radio" value="5"
                                                               name="rating"/>
                                                        <label class="star star-5 fa-solid fa-star"
                                                               for="star-5"></label>
                                                        <input class="star star-4" id="star-4" type="radio" value="4"
                                                               name="rating"/>
                                                        <label class="star star-4 fa-solid fa-star"
                                                               for="star-4"></label>
                                                        <input class="star star-3" id="star-3" type="radio" value="3"
                                                               name="rating"/>
                                                        <label class="star star-3 fa-solid fa-star"
                                                               for="star-3"></label>
                                                        <input class="star star-2" id="star-2" type="radio" value="2"
                                                               name="rating"/>
                                                        <label class="star star-2 fa-solid fa-star"
                                                               for="star-2"></label>
                                                        <input class="star star-1" id="star-1" type="radio" value="1"
                                                               name="rating"/>
                                                        <label class="star star-1 fa-solid fa-star"
                                                               for="star-1"></label>
                                                    </div>
                                                </li>
                                            </ul>
                                            <button type="submit" class="btn btn-primary" id="submit"><fmt:message key="order.response.submit"/></button>
                                        </form>
                                    </c:if>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <!-- JavaScript -->
    <ext:fragment name="import/bootstrapScript"/>
</body>

</html>