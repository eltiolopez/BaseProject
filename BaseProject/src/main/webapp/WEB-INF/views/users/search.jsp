<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="pagination" tagdir="/WEB-INF/tags/pagination" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="baseproject" uri="http://com.jld.base/tags" %>

<spring:theme code="style" var="styleName" />

<!-- <div id="central_column" xmlns:jsp="http://java.sun.com/JSP/Page"> -->
<div id="central_column" class="col-lg-middle col-md-middle col-sm-middle col-xs-middle" xmlns:jsp="http://java.sun.com/JSP/Page">

	<h2><spring:message code="message.users.search.title" htmlEscape="false" /></h2>

	<c:if test="${createOk or updateOk or deleteOk}">
		<div class="row">
			<div class='alert alert-success alert-dismissable fade in' role="alert">
				<a href="#" class="close" data-dismiss="alert" aria-label="close">×</a>
				<span id="flash_messages_ok" class="error">
					<c:if test="${createOk}"><spring:message code="message.users.search.info.createok" htmlEscape="false" /></c:if>
					<c:if test="${updateOk}"><spring:message code="message.users.search.info.updateok" htmlEscape="false" /></c:if>
					<c:if test="${deleteOk}"><spring:message code="message.users.search.info.deleteok" htmlEscape="false" /></c:if>
				</span>
			</div>
		</div>
	</c:if>
	<c:if test="${createError or updateError or deleteError}">
		<div>
			<table id="flash_messages_error">
				<tr>
					<c:if test="${createError}"><td><spring:message code="message.users.search.info.createerror" htmlEscape="false" /></td></c:if>
					<c:if test="${updateError}"><td><spring:message code="message.users.search.info.updateerror" htmlEscape="false" /></td></c:if>
					<c:if test="${deleteError}"><td><spring:message code="message.users.search.info.deleteerror" htmlEscape="false" /></td></c:if>
				</tr>
			</table>
		</div>
	</c:if>

	<h4><spring:message code="message.users.search.form.subtitle" htmlEscape="false" /></h4>

	<div id="filtro">
		<spring:url var="form_url" value="/users/search" />
		<form:form method="GET" action="${form_url}" modelAttribute="userSearchForm" commandName="userSearchForm" class="form-group">
			    	
			<div class="row">
			    <span class="col_25 col-xs-4 col-sm-4 col-md-4 col-lg-4 form-group">
					<spring:message var="msgUsername" code="message.users.create.form.username" htmlEscape="false" />
					<form:label class="control-label" path="username"><c:out value="${msgUsername}" /></form:label>
					<form:input id="username" class="form-control input-sm" path="username" placeholder="Username" />
				</span>
				<span class="col_25 col-xs-4 col-sm-4 col-md-4 col-lg-4 form-group">
					<spring:message var="msgEmail" code="message.users.create.form.email" htmlEscape="false" />
					<form:label class="control-label" path="email"><c:out value="${msgEmail}" /></form:label>
					<form:input id="email" class="form-control input-sm" path="email" type="email" placeholder="Email" />
				</span>
				<span class="col_25 col-xs-4 col-sm-4 col-md-4 col-lg-4 form-group">
					<spring:message var="msgGroup" code="message.users.create.form.group" htmlEscape="false" />
					<form:label class="control-label" path="group"><c:out value="${msgGroup}" /></form:label>
					<form:select class="form-control input-sm" path="group">
						<form:option value="" />
						<c:forEach var="group" items="${groupList}">
							<form:option value="${group.idusergroup}">${group.groupname}</form:option>
						</c:forEach>
					</form:select>
				</span>
			</div>

			<div class="row">
				<span class="col_100 col-xs-12 col-sm-12 col-md-12 col-lg-12 form-group">
					<spring:message var="msgClear" code="message.common.button.clear" htmlEscape="false" />
					<input type="button" class="btn btn-default btn-sm" value="${msgClear}" />
					
					<spring:message var="msgSearch" code="message.common.button.search" htmlEscape="false" />
					<input type="submit" class="btn btn-default btn-sm" value="${msgSearch}" onclick="javascript:document.getElementById('page').value=1;" />
				</span>
			</div>

			<form:hidden path="page" id="page" />
		</form:form>
	</div>
	
	<br />

	<h4><spring:message code="message.users.search.results.subtitle" htmlEscape="false" /></h4>
	
	<c:choose>
	    <c:when test="${resultAndMetainfo!=null && resultAndMetainfo.listaResultados!=null && fn:length(resultAndMetainfo.listaResultados)>0}">
	    
			<div id="resultados">
				<div class="table-responsive">
		  			<table class="table">
						<thead>
							<tr>
								<th style="width: 15%;"><spring:message code="message.users.search.results.header.username" htmlEscape="false" /></th>
								<th style="width: 35%;"><spring:message code="message.users.search.results.header.namesurnames" htmlEscape="false" /></th>
								<th style="width: 25%;"><spring:message code="message.users.search.results.header.email" htmlEscape="false" /></th>
								<th style="width: 15%;"><spring:message code="message.users.search.results.header.group" htmlEscape="false" /></th>
								<th><spring:message code="message.users.search.results.header.options" htmlEscape="false" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="user" items="${resultAndMetainfo.listaResultados}">
			    				<tr>
			    					<td>${user.username}</td>
			    					<td>${user.name} ${user.surname1} ${user.surname2}</td>
			    					<td>${user.email}</td>
									<td>${user.usergroup.groupname}</td>
									<td>										
										<spring:url var="details_url" value="details">
											<baseproject:encryptedparam name="userid" value="${user.iduser}" />	
										</spring:url>
										<a href="${details_url}" style="text-decoration: none">
											<spring:url var="imgEdit" value="/resources/images/pen_small.png" />
											<img class="option" src="${imgEdit}" alt="Edit" />
										</a>
										
										<spring:url var="remove_url" value="delete">
											<baseproject:encryptedparam name="userid" value="${user.iduser}" />	
										</spring:url>
										<c:choose>
											<c:when test="${styleName=='Bootstrap'}">
		   										<a href="#" style="text-decoration: none" data-href="${remove_url}" data-toggle="modal" data-target="#confirm-delete">
													<spring:url var="imgRemove" value="/resources/images/trash_small.png" />
													<img class="option" src="${imgRemove}" alt="Remove" />
		   										</a>
	   										</c:when>
		   									<c:otherwise>
		   										<a href="${remove_url}" style="text-decoration: none">
													<spring:url var="imgRemove" value="/resources/images/trash_small.png" />
													<img class="option" src="${imgRemove}" alt="Remove" />
		   										</a>
		   									</c:otherwise>
	   									</c:choose>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			
			<div id="paginacion">
				<fmt:formatNumber var="maxPages" value="${(resultAndMetainfo.totalResultados / userSearchForm.getPageSize()) + ((resultAndMetainfo.totalResultados / userSearchForm.getPageSize()) % 1 == 0 ? 0 : 0.5)}" type="number" pattern="#" />
				<pagination:pagination formName="userSearchForm" maxPages="${maxPages}" currentPage="${userSearchForm.getCurrentPage()}" />
			</div>
		</c:when>
	    <c:otherwise>
	        <spring:message code="message.common.results.noresults" htmlEscape="false" />
	    </c:otherwise>
	</c:choose>

	<br />
</div>




<c:if test="${styleName=='Bootstrap'}">

	<style>
		.modal {
		   top: 50px;
		   z-index: 10000;
		}
	</style>
	
	
	
	<div class="modal fade" id="confirm-delete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title" id="myModalLabel">Confirm Delete</h4>
				</div>
				<div class="modal-body">
					<p>The user will be permanently removed.</p>
					<p>Do you want to proceed?</p>
					<p class="debug-url"/>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<a class="btn btn-danger btn-ok" role="button">Delete</a>
				</div>
			</div>
		</div>
	</div>
	
</c:if>

