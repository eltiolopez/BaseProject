<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- <div id="central_column" xmlns:jsp="http://java.sun.com/JSP/Page"> -->
<div id="central_column" class="col-lg-middle col-md-middle col-sm-middle col-xs-middle" xmlns:jsp="http://java.sun.com/JSP/Page">

	<h2><spring:message code="message.dashboard.settings.title" htmlEscape="false" /></h2>
			
	<h4><spring:message code="message.dashboard.settings.subtitle" htmlEscape="false" /></h4>
	

	<div id="formulario">
		<spring:url var="form_url" value="/${userName}/settings" />
		<form:form method="POST" action="${form_url}" modelAttribute="userSettingsForm" commandName="userSettingsForm" class="form-horizontal">
			<c:if test="${hasValidationErrors}">
				<div class="row">
					<div class='alert alert-danger alert-dismissable fade in' role="alert">
						<a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>
						<form:errors path="*" cssClass="error"/>
					</div>
				</div>
			</c:if>
			<c:if test="${updateOk}">
				<div class="row">
					<div class='alert alert-success alert-dismissable fade in' role="alert">
						<a href="#" class="close" data-dismiss="alert" aria-label="close">×</a>
						<span id="flash_messages_ok" class="error">
							<c:if test="${updateOk}"><spring:message code="message.users.search.info.updateok" htmlEscape="false" /></c:if>
						</span>
					</div>
				</div>
			</c:if>
			
			<form:hidden path="username" />
			
			<div class="form-group">
				<spring:message var="msg" code="message.dashboard.settings.form.numresults" htmlEscape="false" />
				<form:label class="col-sm-4 control-label" path="numResultsInPage"><c:out value="${msg}" /></form:label>
				<div class="col-sm-4">
					<form:select class="form-control" path="numResultsInPage">
						<form:option value="10">10</form:option>
						<form:option value="40">40</form:option>
						<form:option value="100">100</form:option>
					</form:select>
				</div>
			</div>
			
			<div class="form-group">
				<spring:message var="msgOrderBy" code="message.dashboard.settings.form.orderby" htmlEscape="false" />
				<spring:message var="msgDefault" code="message.dashboard.settings.form.orderby.none" htmlEscape="false" />
				<spring:message var="msgName" code="message.dashboard.settings.form.orderby.name" htmlEscape="false" />
				<spring:message var="msgDate" code="message.dashboard.settings.form.orderby.date" htmlEscape="false" />
				<form:label class="col-sm-4 control-label" path="orderBy"><c:out value="${msgOrderBy}" /></form:label>
				<div class="col-sm-4">
					<form:select class="form-control" path="orderBy">
						<option value="DEFAULT"><c:out value="${msgDefault}"/></option>
						<option value="NAME"><c:out value="${msgName}"/></option>
						<option value="DATE"><c:out value="${msgDate}"/></option>
					</form:select>
				</div>
			</div>
			
			<div class="row">
				<span class="col_100 col-xs-12 col-sm-12 col-md-12 col-lg-12 form-group">
					<spring:message var="msgClear" code="message.common.button.clear" htmlEscape="false" />
					<input type="button" class="btn btn-default btn-sm" value="${msgClear}" />
					
					<spring:message var="msgSubmit" code="message.common.button.save" htmlEscape="false" />
					<input type="submit" class="btn btn-default btn-sm" value="${msgSubmit}" />
				</span>
			</div>

		</form:form>
	</div>
</div>