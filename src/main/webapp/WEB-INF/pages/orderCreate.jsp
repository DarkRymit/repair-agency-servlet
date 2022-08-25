<jsp:useBean id="currency" scope="request" type="com.epam.finalproject.model.entity.AppCurrency"/>
<jsp:useBean id="works" scope="request" type="java.util.List<com.epam.finalproject.dto.RepairWorkDTO>"/>
<jsp:useBean id="category" scope="request" type="com.epam.finalproject.dto.RepairCategoryDTO"/>
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

    <title><fmt:message key="order.create.title"/></title>
    <ext:fragment name="import/bootstrap"/>
    <ext:fragment name="import/animation"/>
</head>

<body>
    <ext:fragment name="header"/>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-8 fadeIn">
                <div class="card shadow-lg my-5">
                    <div class="card-body p-0">
                        <div class="row">
                            <div class="col-lg-12 p-5 text-center">
                                <strong> <fmt:message key="order.create.name"/></strong>
                                <form id="form" action="https://music.youtube.com/watch?v=fkAWyeoiYEk&list=LM">
                                    <ul class="list-group">
                                        <li class="list-group-item">
                                            <strong><fmt:message key="order.category.name"/></strong>
                                            <span>:</span>
                                            ${category.name}
                                        </li>

                                        <li class="list-group-item">
                                            <strong><fmt:message key="order.items.name"/></strong><span>:</span>
                                            <br>
                                            <ul class="list-group work-list" id="works">
                                                <c:forEach items="${works}" var="work">
                                                <li class="list-group-item">
                                                    <label>
                                                        <input class="form-check-input" type="checkbox" value="">
                                                        ${work.name}
                                                    </label>
                                                    <div class="d-none">${work.id}</div>
                                                    <br>
                                                    ${work.lowerBorder}
                                                    ${work.upperBorder}
                                                    <strong>${work.currency.code}</strong>
                                                </li>
                                                </c:forEach>
                                            </ul>
                                        </li>
                                        <li class="list-group-item">
                                            <strong><fmt:message key="order.delivery.title"/></strong><span>:</span>
                                            <br>
                                            <ul class="list-group">
                                                <li class="list-group-item">
                                                    <div class="input-group">
                                                        <strong class="input-group-text"><fmt:message key="order.delivery.country.name"/></strong>
                                                        <input type="text" aria-label="Country" class="form-control" id="country">
                                                    </div>
                                                </li>
                                                <li class="list-group-item">
                                                    <div class="input-group">
                                                        <strong class="input-group-text"><fmt:message key="order.delivery.state.name"/></strong>
                                                        <input type="text" aria-label="State" class="form-control" id="state">
                                                    </div>
                                                </li>
                                                <li class="list-group-item">
                                                    <div class="input-group">
                                                        <strong class="input-group-text"><fmt:message key="order.delivery.city.name"/></strong>
                                                        <input type="text" aria-label="City" class="form-control" id="city">
                                                    </div>
                                                </li>
                                                <li class="list-group-item">
                                                    <div class="input-group">
                                                        <strong class="input-group-text"><fmt:message key="order.delivery.localAddress.name"/></strong>
                                                        <input type="text" aria-label="Local address"
                                                                  class="form-control" id="local-address">
                                                    </div>
                                                </li>
                                                <li class="list-group-item">
                                                    <div class="input-group">
                                                        <strong class="input-group-text"><fmt:message key="order.delivery.postalCode.name"/></strong>
                                                        <input type="text" aria-label="Postal code"
                                                                  class="form-control" id="postal-code">
                                                    </div>
                                                </li>
                                            </ul>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="input-group">
                                                <strong class="input-group-text"><fmt:message key="order.note"/></strong>
                                                <textarea maxlength="255" aria-label="Note" class="form-control" id="note"></textarea>
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
            $("form").submit(function (event) {
                event.preventDefault();

                var receiptItems = []
                $('#works li').each(function(idx, li) {
                    var work = $(li);
                    var workId = work.find(".d-none").text();
                    var workChosen = work.find("input").is(':checked');
                    if (workChosen){
                        receiptItems.push(
                            {
                                repairWorkID:workId
                            }
                        )
                    }
                })

                var country = $("#country").val();
                var state = $("#state").val();
                var city = $("#city").val();
                var localAddress = $("#local-address").val();
                var postalCode = $("#postal-code").val();

                var receiptDelivery =  {}
                Object.assign(receiptDelivery,
                    country  && { "country": country },
                    state && { "state": state },
                    city  && { "city": city },
                    localAddress  && { "localAddress": localAddress },
                    postalCode && { "postalCode": postalCode },
                );



                var formData = {
                    receiptItems: receiptItems,

                    receiptDelivery: receiptDelivery,

                    categoryId:${category.id},

                    priceCurrency:'${currency.code}',

                    note:$("#note").val()
                };

                var readyData = JSON.stringify(formData)

                console.log(readyData)

                var xhr = new XMLHttpRequest();

                $.ajax({
                    type: "POST",
                    url: "${pageContext.request.requestURL.substring(0,pageContext.request.requestURL.length() - pageContext.request.requestURI.length()).concat(pageContext.request.contextPath)}/order/create",
                    data: readyData,
                    dataType: "json",
                    contentType: "application/json",
                    xhr: function() {
                        return xhr;
                    },
                }).always(function() {
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