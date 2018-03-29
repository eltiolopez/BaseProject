<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- <div id="central_column" xmlns:jsp="http://java.sun.com/JSP/Page"> -->
<div id="central_column" class="col-lg-middle col-md-middle col-sm-middle col-xs-middle" xmlns:jsp="http://java.sun.com/JSP/Page">

	<h2><spring:message code="message.users.create.title" htmlEscape="false" /></h2>
			
	<h4><spring:message code="message.users.create.form.subtitle" htmlEscape="false" /></h4>
	

	<div id="formulario">
		<spring:url var="form_url" value="/users/create" />
		<form:form method="POST" action="${form_url}" modelAttribute="userCreateForm" commandName="userCreateForm" class="form-group">
			<c:if test="${hasValidationErrors}">
				<div class="row">
					<div class='alert alert-danger alert-dismissable fade in' role="alert">
						<a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>
						<form:errors path="*" cssClass="error"/>
					</div>
				</div>
			</c:if>
			
			<div class="row">
				<span class="col_25 col-xs-4 col-sm-4 col-md-4 col-lg-4 form-group">
					<spring:message var="msgUsername" code="message.users.create.form.username" htmlEscape="false" />
					<form:label class="control-label" path="username"><c:out value="${msgUsername}" /></form:label>
					<form:input class="form-control input-sm" path="username" placeholder="${msgUsername}" />
				</span>
				<span class="col_25 col-xs-4 col-sm-4 col-md-4 col-lg-4 form-group">
					<spring:message var="msgEmail" code="message.users.create.form.email" htmlEscape="false" />
					<form:label class="control-label" path="email"><c:out value="${msgEmail}" /></form:label>
					<form:input type="email" class="form-control input-sm" path="email" placeholder="${msgEmail}" />
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
				<span class="col_25 col-xs-4 col-sm-4 col-md-4 col-lg-4 form-group">
					<spring:message var="msgName" code="message.users.create.form.name" htmlEscape="false" />
					<form:label class="control-label" path="name"><c:out value="${msgName}" /></form:label>
					<form:input class="form-control input-sm" path="name" placeholder="${msgName}" />
				</span>
				<span class="col_25 col-xs-4 col-sm-4 col-md-4 col-lg-4 form-group">
					<spring:message var="msgSurname1" code="message.users.create.form.surname1" htmlEscape="false" />
					<form:label class="control-label" path="surname1"><c:out value="${msgSurname1}" /></form:label>
					<form:input class="form-control input-sm" path="surname1" placeholder="${msgSurname1}" />
				</span>
				<span class="col_25 col-xs-4 col-sm-4 col-md-4 col-lg-4 form-group">
					<spring:message var="msgSurname2" code="message.users.create.form.surname2" htmlEscape="false" />
					<form:label path="surname2"><c:out value="${msgSurname2}" /></form:label>
					<form:input class="form-control input-sm" path="surname2" placeholder="${msgSurname2}" />
				</span>
			</div>
			<div class="row">
				<span class="col_25 col-xs-4 col-sm-4 col-md-4 col-lg-4 form-group">
					<spring:message var="msgPassword1" code="message.users.create.form.password1" htmlEscape="false" />
					<form:label class="control-label" path="password1"><c:out value="${msgPassword1}" /></form:label>
					<form:password class="form-control input-sm" path="password1" placeholder="${msgPassword1}" />
				</span>
				<span class="col_25 col-xs-4 col-sm-4 col-md-4 col-lg-4 form-group">
					<spring:message var="msgPassword2" code="message.users.create.form.password2" htmlEscape="false" />
					<form:label class="control-label" path="password2"><c:out value="${msgPassword2}" /></form:label>
					<form:password class="form-control input-sm" path="password2" placeholder="${msgPassword2}" />
				</span>
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