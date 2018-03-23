<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<nav id="menu_top" class="navbar navbar-default" data-spy="affix" data-offset-top="195">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
			</button>
			<spring:url var="home" value="/" />
			<a class="navbar-brand" href="${home}">Base Project</a>
		</div>
	    
	    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
			    <li class="active">
			    	<spring:url var="spring_link" value="/spring" />
					<a href="${spring_link}">Spring</a>
				</li>
			    <li>
			    	<spring:url var="hibernate_link" value="/hibernate" />
					<a href="${hibernate_link}">Hibernate</a>
			    </li>
			    <li>
			    	<spring:url var="mysql_link" value="/mysql" />
					<a href="${mysql_link}">MySQL</a>
			    </li>
			    <li>
			    	<spring:url var="frontend_link" value="/frontend" />
					<a href="${frontend_link}">Front End</a>
			    </li>
			</ul>
			
			<!-- <div class="navbar-right">
				<button type="button" class="btn btn-success navbar-btn" data-toggle="modal" data-target="#myModal">Sign in</button>
			</div> -->
			
			<sec:authorize access="isAnonymous()">										
				<form name='loginForm' action="<c:url value='/login' />" method='POST' class="navbar-form navbar-right">
					<div class="form-group">
						<c:if test="${not empty param.error}">
							<div id='menu_alert' class='alert alert-danger alert-dismissable fade in'><a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a><spring:message code="security_login_unsuccessful" /></div>
							<!-- <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" /> -->
						</c:if>
					</div>
					<div class="form-group">
						<input type='text' class="form-control input-sm" id="username" name="username" placeholder="User" />
					</div>
					<div class="form-group">
						<input type='password' class="form-control input-sm" id="password" name="password" placeholder="Password" />
					</div>
					<input type="submit" class="form-control btn btn-success btn-sm" name="submit" value="Sign in" />
				
				<!-- <div class="nav navbar-right">
					<p class="navbar-text"><c:out value="${welcomeMessage}" /></p>
			
					<a class="btn btn-danger" href="${logoutUrl}" role="button">
						<span class="glyphicon glyphicon-log-out" aria-hidden="true"/>
						<spring:message code="global.logout" htmlEscape="false" />
					</a>
				</div> -->
				</form>
			</sec:authorize>
			<sec:authorize access="isAuthenticated()">
			
				<sec:authentication var="userName" property="principal.username" />
				<spring:message var="welcomeMessage" code="global.welcome" arguments="${userName}" htmlEscape="false" />
				
				
				<!-- <div class="nav navbar-right">
					<p class="navbar-text"><c:out value="${welcomeMessage}" /></p>
			
					<a class="btn btn-danger" href="${logoutUrl}" role="button">
						<span class="glyphicon glyphicon-log-out" aria-hidden="true"/>
						<spring:message code="global.logout" htmlEscape="false" />
					</a>
				</div> -->
				
				<sec:authentication var="imgUrl" property="principal.userPreferences.profilePicture" />
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown">
						<!-- <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-user"/> ${userName} <span class="caret"/></a> -->

						<c:choose>
							<c:when test="${not empty imgUrl}"><spring:url var="img" value="/pictures/${imgUrl}" /></c:when>
							<c:otherwise><spring:url var="img" value="/resources/images/anonymous-user.png" /></c:otherwise>
						</c:choose>
						
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><img src="${fn:escapeXml(img)}" class="img-circle" alt="${userName}" width="32" height="32"/><span class="caret"/></a> 
        				<ul class="dropdown-menu">
        					<li><a href="#" class="text-username">${userName}</a></li>
        					<li role="separator" class="divider"></li>
        					<li>
        						<spring:url var="profile_link" value="/profile/${userName}" />
        						<a href="${profile_link}"><span class="glyphicon glyphicon-user"><!-- Para cerrar la etiqueta --></span> <spring:message code="global.profile" /></a>
        					</li>
        					<li>
        						<spring:url var="settings_link" value="/users/settings" />
        						<a href="${settings_link}"><span class="glyphicon glyphicon-cog"><!-- Para cerrar la etiqueta --></span> <spring:message code="global.preferences" /></a>
        					</li>
        					<li>
        						<spring:url var="stats_link" value="/users/statistics" />
        						<a href="${stats_link}"><span class="glyphicon glyphicon-stats"><!-- Para cerrar la etiqueta --></span> <spring:message code="global.statistics" /></a>
        					</li>
        					<li role="separator" class="divider"/>
        					<li>
        						<c:url var="logoutUrl" value="/j_spring_security_logout" />
        						<a href="${logoutUrl}"><span class="glyphicon glyphicon-log-in text-danger"><!-- Para cerrar la etiqueta --></span> <spring:message code="global.logout" htmlEscape="false" /></a>
        					</li>
        				</ul>
					</li>
				</ul>
			</sec:authorize>
		</div>
	</div>
</nav>


<!-- <jsp:include page="modal_login_bootstrap.jsp" /> -->

<!-- <c:url value="/resources/js/modal.js" var="jsUrl" />
<script src="${jsUrl}"> -->
<!-- Para cerrar la etiqueta -->
<!-- </script>	-->
