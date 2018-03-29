<?xml version="1.0" encoding="UTF-8" standalone="no"?>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="es">
	
	<jsp:directive.page contentType="text/html;charset=UTF-8" />  
	<jsp:directive.page pageEncoding="UTF-8" /> 
	
	<spring:theme code="style" var="styleName" />

	<head>
		<title>BaseProject 1.0</title>
		
		<!-- Required meta tags -->
		<meta charset="utf-8"/>
		<meta name="viewport" content="width=device-width, initial-scale=1"/>
	    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

		<!-- Import CSS Styles -->
		<c:choose>
			<c:when test="${styleName=='Bootstrap'}">
				<!-- <spring:theme code="bootstrap.css1" var="ccs1"/>
				<c:url value="${ccs1}" var="styleUrl_1" />
				<link rel="stylesheet" type="text/css" media="screen" href="${styleUrl_1}" /> -->
				
				<spring:theme code="bootstrap.css2" var="ccs2"/>
				<c:url value="${ccs2}" var="styleUrl_2" />
				<link rel="stylesheet" type="text/css" media="screen" href="${styleUrl_2}" />
				
				<spring:theme code="stylesheet" var="aux"/>
				<c:url value="${aux}" var="styleUrl" />
				<link rel="stylesheet" type="text/css" media="screen" href="${styleUrl}" />
			</c:when>
			<c:otherwise>
				<spring:theme code="stylesheet" var="aux"/>
				<c:url value="${aux}" var="styleUrl" />
				<link rel="stylesheet" type="text/css" media="screen" href="${styleUrl}" />
			</c:otherwise>
		</c:choose>	

	</head>
	
	
	<body>	    
		<div id="wrapper">
			<tiles:insertAttribute name="header" ignore="true" />
			
			
			<c:choose>
				<c:when test="${styleName=='Bootstrap'}">
					<tiles:insertAttribute name="menu_top_bootstrap" ignore="true" />
				</c:when>
				<c:otherwise>
					<tiles:insertAttribute name="menu_top" ignore="true" />
				</c:otherwise>
			</c:choose>
			
			<div id="main" class="container-fluid">
				<div id="main_row" class="row">
					<c:choose>
						<c:when test="${styleName=='Bootstrap'}">
							<tiles:insertAttribute name="menu_left_bootstrap" ignore="true" />
			    			<tiles:insertAttribute name="body" ignore="true" />
			    			<tiles:insertAttribute name="right_column" />
						</c:when>
						<c:otherwise>
							<tiles:insertAttribute name="menu_left" ignore="true" />
							<tiles:insertAttribute name="body" />
							<tiles:insertAttribute name="right_column" />
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<hr />
			<tiles:insertAttribute name="footer" ignore="true" />
		</div>
		
		
		
		
		<c:choose>
			<c:when test="${styleName=='Bootstrap'}">
				
				<!-- Librería jQuery requerida por los plugins de JavaScript -->
	    		<script src="http://code.jquery.com/jquery.js"> <!-- Para cerrar la etiqueta --> </script>			
				
				<!-- Librería Bootstrap -->
				<c:url value="/resources/bootstrap-3.3.7-dist/js/bootstrap.min.js" var="jsUrl" />
			    <script src="${jsUrl}"> <!-- Para cerrar la etiqueta --> </script>
			    
			    
			    
			    
			    <script>
					$(document).ready(function () {
						var trigger = $('.hamburger'),
							overlay = $('.overlay'),
						isClosed = false;
						
						trigger.click(function () {
							hamburger_cross();      
						});
						
						function hamburger_cross() {
							if (isClosed == true) {          
								overlay.hide();
								trigger.removeClass('is-open');
								trigger.addClass('is-closed');
								isClosed = false;
							} else {   
								overlay.show();
								trigger.removeClass('is-closed');
								trigger.addClass('is-open');
								isClosed = true;
							}
						}
						  
						$('[data-toggle="offcanvas"]').click(function () {
							$('#main_row').toggleClass('toggled');
						});  
					});
					
					
					/* Functions for Bootstrap confirmation Modal */
					$('#confirm-delete').on('show.bs.modal', function(e) {
			            $(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));
			            
			            $('.debug-url').html('Delete URL: <strong>' + $(this).find('.btn-ok').attr('href') + '</strong>');
			        });
			    </script>
			</c:when>
			<c:otherwise>
				<!-- No JS to be imported -->
			</c:otherwise>
		</c:choose>	
		
	</body>
</html>

