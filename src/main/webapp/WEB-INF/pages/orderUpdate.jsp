<jsp:useBean id="order" scope="request" type="com.epam.finalproject.dto.ReceiptDTO"/>
<jsp:useBean id="flows" scope="request" type="java.util.List<com.epam.finalproject.dto.ReceiptStatusFlowDTO>"/>
<jsp:useBean id="currencies" scope="request" type="java.util.List<com.epam.finalproject.model.entity.AppCurrency>"/>
<%@ include file="/WEB-INF/pages/fragments/page.jspf" %>
<%@ include file="/WEB-INF/pages/fragments/taglib.jspf" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta content="IE=edge" http-equiv="X-UA-Compatible">
    <meta content="width=device-width, initial-scale=1, shrink-to-fit=no" name="viewport">
    <meta content="" name="description">
    <meta content="" name="author">

    <title><fmt:message key="order.title"/></title>
    <ext:fragment name="import/bootstrap"/>
    <ext:fragment name="import/animation"/>
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
                                <strong> <fmt:message key="order.title"/> </strong>
                                <form  id="form">
                                    <ul class="list-group">
                                        <li class="list-group-item">
                                            <strong><fmt:message key="order.id.name"/></strong>
                                            <span>:</span>
                                            <strong id="id">${order.id}</strong>
                                        </li>
                                        <li class="list-group-item">
                                            <strong><fmt:message key="order.user.name"/></strong>
                                            <span>:</span>
                                            <a class=" text-dark text-decoration-none"
                                               <ext:href path="/user/${order.user.id.toString()}/profile"/> >
                                                @${order.user.username}</a>
                                        </li>
                                        <li class="list-group-item">
                                            <strong><fmt:message key="order.status.name"/></strong>
                                            <span>:</span>
                                            <select aria-label="Default select example" class="form-select text-center"
                                                    id="status">
                                                <option selected value="${order.status.name}">
                                                    <ext:statusGet var="${order.status.name}"/>
                                                </option>
                                                <c:forEach var="flow" items="${flows}">
                                                    <option value="${flow.toStatus.name}">
                                                        <ext:statusGet var="${flow.toStatus.name}"/>
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </li>
                                        <li class="list-group-item">
                                            <label class="form-label" for="masterInput"><strong><fmt:message key="order.master.name"/></strong></label>
                                            <div>
                                                <div class="input-group mb-3">
                                                    <span class="input-group-text" id="basic-addon1">@</span>
                                                    <input class="form-control" id="masterInput"
                                                           placeholder="${order.master == null? null: order.master.username}"
                                                           value="${order.master == null? null: order.master.username}"
                                                           type="text" minlength="8" maxlength="14">
                                                </div>
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <strong><fmt:message key="order.category.name"/></strong>
                                            <span>:</span>
                                            ${order.category.name}
                                        </li>
                                        <li class="list-group-item">
                                            <strong><fmt:message key="order.totalPrice.name"/></strong>
                                            <span>:</span>
                                            <fmt:formatNumber value="${order.totalPrice}" minFractionDigits="0"/>
                                            <strong>${order.priceCurrency.code}</strong>
                                            <div class="input-group mb-3">
                                                <label class="input-group-text"
                                                       for="currency"><fmt:message key="order.currency.name"/></label>
                                                <select class="form-select" id="currency">
                                                    <c:forEach var="currency" items="${currencies}">
                                                        <option <ext:selected test="${order.priceCurrency.code == currency.code}"/> >
                                                            <strong>${currency.code}</strong>
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <strong><fmt:message key="order.items.name"/></strong><span>:</span>
                                            <br>
                                            <c:forEach var="item" items="${order.items}">
                                            <ul class="list-group" id="works">
                                                <li class="list-group-item">
                                                        ${item.repairWork.name}
                                                    <br>
                                                    <div class="d-none"> ${item.repairWork.id}</div>
                                                    <div class="input-group mb-3">
                                                        <input class="form-control price"
                                                               placeholder=<fmt:formatNumber value="${item.priceAmount}" minFractionDigits="0"/>
                                                               value="${item.priceAmount}"
                                                               type="number" min="0">
                                                        <span class="input-group-text" id="basic-addon2"><strong>
                                                                ${order.priceCurrency.code}
                                                        </strong></span>
                                                    </div>
                                                </li>
                                            </ul>
                                            </c:forEach>
                                        </li>
                                        <li class="list-group-item">
                                            <strong><fmt:message key="order.delivery.title"/></strong><span>:</span>
                                            <br>
                                            <ul class="list-group">
                                                <li class="list-group-item">
                                                    <div class="input-group mb-3">
                                                        <label class="input-group-text" for="country"><fmt:message key="order.delivery.country.name"/></label>
                                                        <input class="form-control" id="country"
                                                               placeholder="${order.delivery.country}"
                                                               value="${order.delivery.country}"
                                                               type="text" required>
                                                    </div>
                                                </li>
                                                <li class="list-group-item">
                                                    <div class="input-group mb-3">
                                                        <label class="input-group-text" for="state"><fmt:message key="order.delivery.state.name"/></label>
                                                        <input class="form-control" id="state"
                                                               placeholder="${order.delivery.state}"
                                                               value="${order.delivery.state}"
                                                               type="text">
                                                    </div>
                                                </li>
                                                <li class="list-group-item">
                                                    <div class="input-group mb-3">
                                                        <label class="input-group-text" for="city"><fmt:message key="order.delivery.city.name"/></label>
                                                        <input class="form-control" id="city"
                                                               placeholder="${order.delivery.city}"
                                                               value="${order.delivery.city}"
                                                               type="text" required>
                                                    </div>
                                                </li>
                                                <li class="list-group-item">
                                                    <div class="input-group mb-3">
                                                        <label class="input-group-text" for="local-address"><fmt:message key="order.delivery.localAddress.name"/></label>
                                                        <input class="form-control" id="local-address"
                                                               placeholder="${order.delivery.localAddress}"
                                                               value="${order.delivery.localAddress}"
                                                               type="text" required>
                                                    </div>
                                                </li>
                                                <li class="list-group-item">
                                                    <div class="input-group mb-3">
                                                        <label class="input-group-text" for="postal-code"><fmt:message key="order.delivery.postalCode.name"/></label>
                                                        <input class="form-control" id="postal-code"
                                                               placeholder="${order.delivery.postalCode}"
                                                               value="${order.delivery.postalCode}"
                                                               type="text">
                                                    </div>
                                                </li>
                                            </ul>
                                        </li>
                                        <li class="list-group-item">
                                            <strong><fmt:message key="order.creationDate.name"/></strong>
                                            <span>:</span>
                                            <strong ><frmt:dataTime dateTime="${order.creationDate}"/></strong>
                                        </li>
                                        <li class="list-group-item">
                                            <strong><fmt:message key="order.modifiedDate.name"/></strong>
                                            <span>:</span>
                                            <strong><frmt:dataTime dateTime="${order.lastModifiedDate}"/></strong>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="input-group">
                                                <strong class="input-group-text"><fmt:message key="order.note"/></strong>
                                                <textarea aria-label="Note" class="form-control" id="note"
                                                          maxlength="255" > ${order.note} </textarea>
                                            </div>
                                        </li>
                                    </ul>
                                    <button type="submit" class="btn btn-primary" id="submit"><fmt:message key="order.create.submit"/></button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <!-- JavaScript -->
    <ext:fragment name="import/ajax"/>
    <ext:fragment name="import/bootstrapScript"/>
    <script>
        $(document).ready(function () {
            console.log('hi');
            $("#form").submit(function (event) {
                event.preventDefault();
                var receiptItems = []
                $('#works li').each(function (idx, li) {
                    var work = $(li);
                    var workId = work.find(".d-none").text();
                    var price = work.find(".price").val();
                    receiptItems.push(
                        {
                            repairWorkID: workId,
                            priceAmount: price
                        }
                    )
                    console.log(receiptItems);
                })

                var country = $("#country").val();
                var state = $("#state").val();
                var city = $("#city").val();
                var localAddress = $("#local-address").val();
                var postalCode = $("#postal-code").val();

                var receiptDelivery = {}
                Object.assign(receiptDelivery,
                    country && {"country": country},
                    state && {"state": state},
                    city && {"city": city},
                    localAddress && {"localAddress": localAddress},
                    postalCode && {"postalCode": postalCode},
                );


                var formData = {
                    receiptItems: receiptItems,

                    receiptDelivery: receiptDelivery,

                    priceCurrency: $("#currency option:selected").val(),

                    receiptStatus: $("#status option:selected").val(),

                    masterUsername: $("#masterInput").val(),

                    note: $("#note").val()
                };
                var id = $("#id").text();

                var readyData = JSON.stringify(formData)

                console.log(readyData)

                var xhr = new XMLHttpRequest();

                $.ajax({
                    type: "POST",
                    url: "${pageContext.request.requestURL.substring(0,pageContext.request.requestURL.length() - pageContext.request.requestURI.length()).concat(pageContext.request.contextPath)}/order/"+id+"/update",
                    data: readyData,
                    dataType: "json",
                    contentType: "application/json",
                    xhr: function () {
                        return xhr;
                    },
                }).always(function () {
                    if (window.location.href.split(/[?#]/)[0] !== xhr.responseURL){
                        window.location.href = xhr.responseURL;
                    }else {
                        $("html").html(xhr.response);
                    }
                });


            });
        });
    </script>
</body>

</html>