<%@ include file="/WEB-INF/pages/fragments/page.jspf" %>
<%@ include file="/WEB-INF/pages/fragments/taglib.jspf" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta content='text/html; charset=UTF-8' http-equiv='Content-Type'/>
    <meta content="width=device-width, initial-scale=1" name="viewport">
    <ext:fragment name="import/bootstrap"/>
    <ext:fragment name="import/animation"/>
    <link href="${pageContext.request.contextPath}/static/css/index.css" rel="stylesheet">
   <title><fmt:message key="main.title"/></title>
</head>
<body>
    <ext:fragment name="header"/>
    <main  class="fadeIn">
        <div class="b-example-divider"></div>

        <div class="px-4 pt-5 my-5 text-center border-bottom">
            <h1 class="display-4 fw-bold"><fmt:message key="main.title"/></h1>
            <div class="col-lg-6 mx-auto">
                <p class="lead mb-4"><fmt:message key="main.text1"/></p>
            </div>
            <div class="overflow-hidden" >
                <div class="container px-5">
                    <img src="static/img/repair.jpg" class="img-fluid border rounded-3 shadow-lg mb-4" alt="" width="1080" loading="lazy">
                </div>
            </div>
        </div>

        <div class="b-example-divider"></div>

        <div class="container col-xxl-8 px-4 py-5">
            <div class="row flex-lg-row-reverse align-items-center g-5 py-5">
                <div class="col-10 col-sm-8 col-lg-6">
                    <img src="static/img/right-to-repair.jpg" class="d-block mx-lg-auto img-fluid" alt="Bootstrap Themes" width="700" height="500" loading="lazy">
                </div>
                <div class="col-lg-6">
                    <h1 class="display-5 fw-bold lh-1 mb-3"><fmt:message key="main.text2"/></h1>
                    <p class="lead"><fmt:message key="main.text3"/></p>
                </div>
            </div>
        </div>

        <div class="b-example-divider"></div>

        <div class="container my-5">
            <div class="row p-4 pb-0 pe-lg-0 pt-lg-5 align-items-center rounded-3 border shadow-lg">
                <div class="col-lg-7 p-3 p-lg-5 pt-lg-3">
                    <h1 class="display-4 fw-bold lh-1"><fmt:message key="main.text4"/></h1>
                    <p class="lead"><fmt:message key="main.text5"/></p>
                </div>
                <div class="col-lg-4 offset-lg-1 p-0 overflow-hidden shadow-lg">
                    <img class="rounded-lg-3" src="static/img/last.png" alt="" width="400">
                </div>
            </div>
        </div>

        <div class="b-example-divider mb-0"></div>
    </main>


    <footer class="footer mt-auto py-3 bg-light">
        <div class="container">
            <span class="text-muted"><fmt:message key="main.footer"/></span>
        </div>
    </footer>
    <ext:fragment name="import/bootstrapScript"/>
</body>
</html>