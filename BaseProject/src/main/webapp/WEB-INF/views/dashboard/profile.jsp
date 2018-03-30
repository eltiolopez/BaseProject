<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- <div id="central_column" xmlns:jsp="http://java.sun.com/JSP/Page"> -->
<div id="central_column" class="col-lg-middle col-md-middle col-sm-middle col-xs-middle" xmlns:jsp="http://java.sun.com/JSP/Page">

	<h2><spring:message code="message.dashboard.profile.title" htmlEscape="false" /></h2>
			
	<h4><spring:message code="message.dashboard.profile.subtitle" htmlEscape="false" /></h4>
	

	<div id="formulario">
		<spring:url var="form_url" value="/${userName}/profile" />
		<form:form method="POST" action="${form_url}" modelAttribute="userProfileForm" commandName="userProfileForm" class="form-horizontal">
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

			<div class="form-group">
				<spring:url var="select_img_url" value="/${userName}/images" />
				<spring:message var="msgPicture" code="message.dashboard.profile.form.picture" htmlEscape="false" />
				<form:label class="col-sm-4 control-label" path="imageUrl"><c:out value="${msgPicture}" /></form:label>
				<div class="col-sm-4">
					<c:choose>
						<c:when test="${not empty userProfileForm.imageUrl}">
							<spring:url var="img" value="/pictures/${userProfileForm.imageUrl}" />
						</c:when>
						<c:otherwise>
							<spring:url var="img" value="/resources/images/anonymous-user.png" />
						</c:otherwise>
					</c:choose>
					
					<a href="${select_img_url}" role="button" aria-haspopup="true" aria-expanded="false"><img src="${fn:escapeXml(img)}" class="img-thumbnail img-responsive" alt="Image" width="160" height="160"/></a>
 					<form:hidden path="imageUrl" />
				</div>
			</div>
			
			<div class="form-group">
				<spring:message var="msgUsername" code="message.dashboard.profile.form.username" htmlEscape="false" />
				<form:label class="col-sm-4 control-label" path="username"><c:out value="${msgUsername}" /></form:label>
				<div class="col-sm-4">
					<form:input class="form-control input-sm" path="username" placeholder="${msgUsername}" />
				</div>
			</div>
			
			<div class="form-group">
				<spring:message var="msgEmail" code="message.dashboard.profile.form.email" htmlEscape="false" />
				<form:label class="col-sm-4 control-label" path="email"><c:out value="${msgEmail}" /></form:label>
				<div class="col-sm-4">
					<form:input type="email" class="form-control input-sm" path="email" placeholder="${msgEmail}" />
				</div>
			</div>
			
			<div class="form-group">
				<spring:message var="msgName" code="message.dashboard.profile.form.name" htmlEscape="false" />
				<form:label class="col-sm-4 control-label" path="name"><c:out value="${msgName}" /></form:label>
				<div class="col-sm-4">
					<form:input class="form-control input-sm" path="name" placeholder="${msgName}" />
				</div>
			</div>
			
			<div class="form-group">
				<spring:message var="msgSurname1" code="message.dashboard.profile.form.surname1" htmlEscape="false" />
				<form:label class="col-sm-4 control-label" path="surname1"><c:out value="${msgSurname1}" /></form:label>
				<div class="col-sm-4">
					<form:input class="form-control input-sm" path="surname1" placeholder="${msgSurname1}" />
				</div>
			</div>
			
			<div class="form-group">
				<spring:message var="msgSurname2" code="message.dashboard.profile.form.surname2" htmlEscape="false" />
				<form:label class="col-sm-4 control-label" path="surname2"><c:out value="${msgSurname2}" /></form:label>
				<div class="col-sm-4">
					<form:input class="form-control input-sm" path="surname2" placeholder="${msgSurname2}" />
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