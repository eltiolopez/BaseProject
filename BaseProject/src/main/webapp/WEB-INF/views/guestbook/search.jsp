<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="pagination" tagdir="/WEB-INF/tags/pagination" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- <div id="central_column" xmlns:jsp="http://java.sun.com/JSP/Page"> -->
<div id="central_column" class="col-lg-middle col-md-middle col-sm-middle col-xs-middle" xmlns:jsp="http://java.sun.com/JSP/Page">

	<h2><spring:message code="message.guestbook.search.title" htmlEscape="false" /></h2>
	
	<h4><spring:message code="message.guestbook.search.form.subtitle" htmlEscape="false" /></h4>
	
	<div id="filtro">	  	
		<spring:url var="form_url" value="/guestbook/search" />
		<form:form method="GET" action="${form_url}" modelAttribute="guestBookSearchForm" commandName="guestBookSearchForm" class="form-group">
		
			<div class="row">
			    <span class="col_25 col-xs-4 col-sm-4 col-md-4 col-lg-4 form-group">
			    	<spring:message var="msgUsername" code="message.guestbook.search.form.username" htmlEscape="false" />
					<form:label path="username" class="control-label">${msgUsername}</form:label>
					<form:input id="username" class="form-control input-sm" path="username" placeholder="${msgUsername}"/>
				</span>
		  	</div>
		  	
		  	<div class="row">
				<span class="col_100 col-xs-12 col-sm-12 col-md-12 col-lg-12 form-group">
					<spring:message var="msgSearch" code="message.common.button.search" htmlEscape="false" />
					<input type="submit" class="btn btn-default btn-sm" value="${msgSearch}" />
				</span>
			</div>
			
			<form:hidden path="page" id="page" />
		</form:form>
	</div>
	
	<br />
	
	<h4><spring:message code="message.guestbook.search.results.subtitle" htmlEscape="false" /></h4>
	
	<c:choose>
	    <c:when test="${resultAndMetainfo!=null && resultAndMetainfo.listaResultados!=null && fn:length(resultAndMetainfo.listaResultados)>0}">
		    
		    <div id="resultados">
				<div class="table-responsive">
		  			<table class="table">
		  				<thead>
							<tr>
								<th style="width: 20%;"><spring:message code="message.guestbook.search.results.header.username" htmlEscape="false" /></th>
								<th style="width: 60%;"><spring:message code="message.guestbook.search.results.header.message" htmlEscape="false" /></th>
								<th><spring:message code="message.guestbook.search.results.header.options" htmlEscape="false" /></th>
							</tr>
						</thead>
						<tbody>							
							<c:forEach var="msg" items="${resultAndMetainfo.listaResultados}">
			    				<tr>
			    					<td>${msg.user.username}</td>
			    					<td>${msg.message}</td>
			    					<td>Opciones</td>
								</tr>
							</c:forEach>
						</tbody>
		  			</table>
		  		</div>
			</div>
			
			<div id="paginacion">
				<fmt:formatNumber var="maxPages" value="${(resultAndMetainfo.totalResultados / guestBookSearchForm.getPageSize()) + ((resultAndMetainfo.totalResultados / guestBookSearchForm.getPageSize()) % 1 == 0 ? 0 : 0.5)}" type="number" pattern="#" />
				<pagination:pagination formName="guestBookSearchForm" maxPages="${maxPages}" currentPage="${guestBookSearchForm.getCurrentPage()}" />
			</div>
		</c:when>
	    <c:otherwise>
	        <spring:message code="message.common.results.noresults" htmlEscape="false" />
	    </c:otherwise>
	</c:choose>

	<br />
</div>