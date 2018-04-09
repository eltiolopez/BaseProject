<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- <div id="central_column" xmlns:jsp="http://java.sun.com/JSP/Page"> -->
<div id="central_column" class="col-lg-middle col-md-middle col-sm-middle col-xs-middle" xmlns:jsp="http://java.sun.com/JSP/Page">

	<c:choose>
		<c:when test="${not empty numHttpError}">
			<h2>Error ${numHttpError}</h2>
		</c:when>
		<c:otherwise>
			<h2>Unknown error</h2>
		</c:otherwise>
	</c:choose>
	
	<c:if test="${not empty errorMsg}">
		<p>${errorMsg}</p>
	</c:if>
	
	<c:if test="${not empty starkTrace}">
		<p>Trace: ${starkTrace}</p>
	</c:if>

	<br />
</div>