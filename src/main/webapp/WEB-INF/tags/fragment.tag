<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="name" required="true" type="java.lang.String" %>

<jsp:include page="/WEB-INF/pages/fragments/${name}.jspf"/>