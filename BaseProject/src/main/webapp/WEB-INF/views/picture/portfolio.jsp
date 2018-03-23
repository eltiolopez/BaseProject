<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="gallery" tagdir="/WEB-INF/tags/gallery" %>

<!-- <div id="central_column" xmlns:jsp="http://java.sun.com/JSP/Page"> -->
<div id="central_column" class="col-lg-middle col-md-middle col-sm-middle col-xs-middle" xmlns:jsp="http://java.sun.com/JSP/Page">

	<h2><spring:message code="message.pictures.portfolio.title" htmlEscape="false" /></h2>
	
	<h4><spring:message code="message.pictures.portfolio.subtitle" htmlEscape="false" /></h4>

	<div id="formulario">
		<spring:url var="form_url" value="/pictures/add" />
		<form:form method="POST" action="${form_url}" modelAttribute="pictureAddForm" commandName="pictureAddForm" enctype="multipart/form-data" class="form-group">
		
			<c:choose>
			    <c:when test="${resultAndMetainfo!=null && resultAndMetainfo.listaResultados!=null && fn:length(resultAndMetainfo.listaResultados)>0}">
			    	<gallery:gallery items="${resultAndMetainfo.listaResultados}" numColumns="4" render="true" />
			    </c:when>
			    <c:otherwise>
			        <spring:message code="message.common.results.noresults" htmlEscape="false" />
			    </c:otherwise>
			</c:choose>
		
			<div class="row">
				<span class="col_100 col-xs-12 col-sm-12 col-md-12 col-lg-12 form-group">
					<form:label class="control-label" path="files">File Input 1</form:label>
					<form:input path="files" id="files" type="file" accept="video/*,  video/x-m4v, video/webm, video/x-ms-wmv, video/x-msvideo, video/3gpp, video/flv, video/x-flv, video/mp4, video/quicktime, video/mpeg, video/ogv, .ts, .mkv, image/*" />
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

	<br />
</div>