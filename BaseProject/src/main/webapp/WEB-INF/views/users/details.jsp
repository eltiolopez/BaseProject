<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">

	function confirmDelete(delForm, delUrl, msg) {
		if (confirm(msg)) {
			delForm.action = delUrl;
			return true;
		}
		return false;
	}
	
</script>

<!-- <div id="central_column" xmlns:jsp="http://java.sun.com/JSP/Page"> -->
<div id="central_column" class="col-lg-middle col-md-middle col-sm-middle col-xs-middle" xmlns:jsp="http://java.sun.com/JSP/Page">

	<h2><spring:message code="message.users.details.title" htmlEscape="false" /></h2>
	
	<h4><spring:message code="message.users.details.form.subtitle" htmlEscape="false" /></h4>

	<div id="formulario">
		<spring:url var="form_url" value="/users/details" />
		<spring:message var="confirmMsg" code="message.users.details.confirm" />
		<form:form method="POST" modelAttribute="userUpdateForm" commandName="userCreateForm" name="userUpdateForm" onsubmit="return confirmDelete(this, '${form_url}', '${confirmMsg}')" class="form-group">
			
			<c:if test="${hasValidationErrors}">
				<div class="row">
					<div class='alert alert-danger alert-dismissable fade in' role="alert">
						<a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>
						<form:errors path="username" cssClass="error"/><br/>
						<form:errors path="email" cssClass="error"/><br/>
						<form:errors path="group" cssClass="error"/><br/>
						<form:errors path="password1" cssClass="error"/><br/>
						<form:errors path="password2" cssClass="error"/><br/>
					</div>
				</div>
			</c:if>
			
			<form:hidden path="userid"/>

			<div class="row">
				<span class="col_25 col-xs-4 col-sm-4 col-md-4 col-lg-4 form-group">
					<spring:message var="msgUsername" code="message.users.create.form.username" htmlEscape="false" />
					<form:label class="control-label" path="username">${msgUsername}</form:label>
					<form:input class="form-control input-sm" path="username" readonly="true"/>
				</span>
				<span class="col_25 col-xs-4 col-sm-4 col-md-4 col-lg-4 form-group">
					<spring:message var="msgEmail" code="message.users.create.form.email" htmlEscape="false" />
					<form:label class="control-label" path="email">${msgEmail}</form:label>
					<form:input type="email" class="form-control input-sm" path="email" />
				</span>
				<span class="col_25 col-xs-4 col-sm-4 col-md-4 col-lg-4 form-group">
					<spring:message var="msgGroup" code="message.users.create.form.group" htmlEscape="false" />
					<form:label class="control-label" path="group">${msgGroup}</form:label>
					<form:select class="form-control input-sm" path="group">
						<c:forEach var="group" items="${groupList}">
							<form:option value="${group.idusergroup}">${group.groupname}</form:option>
						</c:forEach>
					</form:select>
				</span>
			</div>
			<div class="row">
				<span class="col_25 col-xs-4 col-sm-4 col-md-4 col-lg-4 form-group">
					<spring:message var="msgName" code="message.users.create.form.name" htmlEscape="false" />
					<form:label class="control-label" path="name">${msgName}</form:label>
					<form:input class="form-control input-sm" path="name"/>
				</span>
				<span class="col_25 col-xs-4 col-sm-4 col-md-4 col-lg-4 form-group">
					<spring:message var="msgSurname1" code="message.users.create.form.surname1" htmlEscape="false" />
					<form:label class="control-label" path="surname1">${msgSurname1}</form:label>
					<form:input class="form-control input-sm" path="surname1"/>
				</span>
				<span class="col_25 col-xs-4 col-sm-4 col-md-4 col-lg-4 form-group">
					<spring:message var="msgSurname2" code="message.users.create.form.surname2" htmlEscape="false" />
					<form:label class="control-label" path="surname2">${msgSurname2}</form:label>
					<form:input class="form-control input-sm" path="surname2"/>
					</td>
				</tr>
			</div>
			
			<div class="row">
				<span class="col_100 col-xs-12 col-sm-12 col-md-12 col-lg-12 form-group">
					<spring:message var="msgBack" code="message.common.button.back" htmlEscape="false" />
					<input type="button" value="${msgBack}" />
					
					<spring:message var="msgSubmit" code="message.common.button.save" htmlEscape="false" />
					<input type="submit" value="${msgSubmit}" />
				</span>
			</div>
			
		</form:form>
	</div>
</div>