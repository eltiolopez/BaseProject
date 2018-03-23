<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:directive.attribute name="url" type="java.lang.String" required="false" rtexprvalue="true" description="The url fo the page to be invoked in pagination" />
<jsp:directive.attribute name="formName" type="java.lang.String" required="false" rtexprvalue="true" description="The form name to be invoked in pagination" />
<jsp:directive.attribute name="maxPages" type="java.lang.Integer" required="true" rtexprvalue="true" description="The maximum number of pages available (ie tableRecordCount / size)" />
<jsp:directive.attribute name="currentPage" type="java.lang.Integer" required="false" rtexprvalue="true" description="The current page (not required, defaults to 1)" />
<jsp:directive.attribute name="render" type="java.lang.Boolean" required="false" rtexprvalue="true" description="Indicate if the contents of this tag and all enclosed tags should be rendered (default 'true')" />

<%-- *** 
	- Si se quiere que la paginación se recupere simplemente haciendo un GET, usar el parametro 'url'.
	- Si se quiere que la paginación incluya el filtro de un formulario, haciendo un GET, usar el parametro 'formName'.
	- El parámetro 'maxPages' es el número máximo de paginas que se pueden recuperar.
	- El parámetro 'currentPage' es el número de la página en la que actualmente estamos.
--%>

<c:if test="${empty render or render}">
	<c:if test="${empty currentPage || currentPage lt 1}">
      <c:set var="currentPage" value="1" />
    </c:if>
	
	<c:set var="ventana" value="5" />
	<fmt:formatNumber var="offset" value="${ventana / 2}" maxFractionDigits="0" />
	<c:set var="ventanaIni" value="${currentPage - offset}" />
	<c:set var="ventanaFin" value="${currentPage + offset}" />
	
	<%-- Compose the pagination window --%>
	<c:if test="${ventanaIni < 1}">
		<c:forEach var="i" begin="0" end="${offset}">
			<c:if test="${ventanaIni < 1}">
				<c:set var="ventanaIni" value="${ventanaIni + 1}" />
				<c:set var="ventanaFin" value="${ventanaFin + 1}" />
			</c:if>
		</c:forEach>
	</c:if>
	
	<c:if test="${ventanaFin > maxPages}">
		<c:forEach var="i" begin="0" end="${offset+1}">
			<c:if test="${ventanaFin > maxPages}">
				<c:set var="ventanaFin" value="${ventanaFin - 1}" />
				<c:if test="${ventanaIni - 1 > 0}">
					<c:set var="ventanaIni" value="${ventanaIni - 1}" />
				</c:if>
			</c:if>
		</c:forEach>
	</c:if>


	<%-- Print the pagination window --%>
	<nav aria-label="Search results pages">
		<ul class="pagination pagination-sm">
			<li><a href="#" onclick="javascript:document.getElementById('page').value=1;document.getElementById('${formName}').submit();"  aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
			<c:forEach var="i" begin="${ventanaIni}" end="${ventanaFin}">
				<c:choose>
					<c:when test="${i eq currentPage}">
						<li class="active"><a href="#">${i}</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="#" onclick="javascript:document.getElementById('page').value=${i};document.getElementById('${formName}').submit();" >${i}</a></li>
					</c:otherwise>
				</c:choose>			
			</c:forEach>
			<li><a href="#" onclick="javascript:document.getElementById('page').value=${maxPages};document.getElementById('${formName}').submit();" aria-label="Next"> <span aria-hidden="true">&raquo;</span></a></li>
		</ul>
	</nav>
</c:if>