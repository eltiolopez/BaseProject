<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">

	function countChar(val) {
		var len = val.value.length;
		if (len > 255) {
			val.value = val.value.substring(0, 255);
		} else {
			document.getElementById("charNum").innerHTML = 255 - len;
		}
	};
	
</script>

<!-- <div id="central_column" xmlns:jsp="http://java.sun.com/JSP/Page"> -->
<div id="central_column" class="col-lg-middle col-md-middle col-sm-middle col-xs-middle" xmlns:jsp="http://java.sun.com/JSP/Page">

	<h2><spring:message code="message.guestbook.create.title" htmlEscape="false" /></h2>
	
	<h4><spring:message code="message.guestbook.create.form.subtitle" htmlEscape="false" /></h4>

	<div id="formulario">
		<spring:url var="form_url" value="/guestbook/create" />
		<form:form method="POST" action="${form_url}" modelAttribute="guestbookCreateForm" commandName="guestbookCreateForm" class="form-group">
			<c:if test="${hasValidationErrors}">
				<div class="row">
					<div class='alert alert-danger alert-dismissable fade in' role="alert">
						<a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>
						<form:errors path="message" cssClass="error"/>
					</div>
				</div>
			</c:if>
			
			<div class="row">
				<span class="col_100 col-xs-12 col-sm-12 col-md-12 col-lg-12 form-group">
					<spring:message var="msgMessage" code="message.guestbook.create.form.message" htmlEscape="false" />
					<form:label class="control-label" path="message">${msgMessage}</form:label>
					<form:textarea id="message" class="form-control" path="message" onkeyup="countChar(this)" placeholder="Escribe aquí tu mensaje" />
					<div id="charNum">255</div>
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