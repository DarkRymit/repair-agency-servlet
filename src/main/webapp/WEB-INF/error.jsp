<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ include file="/WEB-INF/pages/fragments/taglib.jspf" %>
<%@ taglib uri="/WEB-INF/tld/http" prefix="http"%>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Error ${pageContext.errorData.statusCode}</title>
    <ext:fragment name="import/bootstrap"/>
    <ext:fragment name="import/errorNoise"/>
    <ext:fragment name="import/animation"/>
</head>

<body>

    <div class="container">
        <div class="row justify-content-center">
            <div class="col-lg-4 fadeIn">
                <div class="card shadow-lg my-5">
                    <div class="card-body p-0">
                        <div class="row">
                            <div class="col-lg-12 p-5 text-center">
                                <div class="mx-auto error" >${pageContext.errorData.statusCode}</div>
                                <p class="lead mb-5" >${http:statusText(pageContext.errorData.statusCode)}</p>
                                <a <ext:href path="/"/> > Home</a>
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