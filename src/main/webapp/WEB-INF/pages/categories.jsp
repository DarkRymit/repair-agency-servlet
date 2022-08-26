<jsp:useBean id="categories" scope="request" type="java.util.List<com.epam.finalproject.dto.RepairCategoryDTO>"/>
<jsp:useBean id="currency" scope="request" type="com.epam.finalproject.model.entity.AppCurrency"/>
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
    <title><fmt:message key="categories.title"/></title>
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
                                <c:forEach items="${categories}" var="category">
                                <ul class="list-group">
                                    <li class="list-group-item">
                                        <ul class="list-group">
                                            <li class=" list-group-item">
                                                <i>${category.name}</i>
                                            </li>
                                            <li class=" list-group-item accordion accordion-flush"
                                                id="accordion${category.keyName}">
                                                <div class="accordion-item">
                                                    <h2 class="accordion-header" id="heading${category.keyName}">
                                                        <button aria-expanded="false"
                                                                class="accordion-button collapsed text-center"
                                                                data-bs-toggle="collapse"
                                                                data-bs-target="#collapse${category.keyName}"
                                                                type="button">
                                                            <fmt:message key="categories.listWorks"/>
                                                        </button>
                                                    </h2>
                                                    <div class="accordion-collapse collapse"
                                                         data-bs-parent="#accordion${category.keyName}"
                                                         id="collapse${category.keyName}">
                                                        <c:forEach items="${category.works}" var="work">
                                                        <ul class="list-group">
                                                            <li class="list-group-item">
                                                                ${work.name}
                                                                <br>
                                                                    <ext:workPricing lower="${work.lowerBorder}" upper="${work.upperBorder}"/>
                                                                <strong>${currency.code}</strong>
                                                            </li>
                                                        </ul>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                            </li>
                                            <li class=" list-group-item">
                                                <a class="btn btn-outline-primary" <ext:href path="/category/${category.keyName}"/> role="button">
                                                    <fmt:message key="categories.moreAbout"/>
                                                </a>
                                            </li>
                                            <li class=" list-group-item">
                                                <a class="btn btn-outline-primary" <ext:href path="/order/create?category=${category.keyName}&currency=USD"/> role="button">
                                                    <fmt:message key="categories.makeOrder"/>
                                                </a>
                                            </li>
                                        </ul>
                                    </li>
                                </ul>
                                </c:forEach>
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