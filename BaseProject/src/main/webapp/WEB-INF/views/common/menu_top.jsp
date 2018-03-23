<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="menu_top">
	<div id="left_side">
		<ul>
			<li>
				<spring:url var="home" value="/" />
				<a href="${home}">Home</a>
			</li>
			<li>
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
	</div>

	<div id="right_side">
		<sec:authorize access="isAnonymous()">
			<div id="login">
				<form name='loginForm' action="<c:url value='/login' />" method='POST'>
					<table>
						<tr>
							<td>
								<c:if test="${not empty param.error}">
									<div id='menu_alert'><spring:message code="security_login_unsuccessful" /></div>
								</c:if>
							</td>
							<td>
								<label for="username">User:</label>
								<input type='text' id="username" name="username" />
							</td>
							<td>
								<label for="password">Password:</label>
								<input type='password' id="password" name="password" />
							</td>
							<td style="padding-top: 10px;">
								<input name="submit" type="submit" value="OK" />
							</td>
						</tr>
					</table>
				</form>
			</div>
		</sec:authorize>
		<sec:authorize access="isAuthenticated()">
			<sec:authentication var="userName" property="principal.username" />
			<c:url var="logoutUrl" value="/j_spring_security_logout" />
			
			<ul>
				<li>
					<spring:message var="welcomeMessage" code="global.welcome" arguments="${userName}" htmlEscape="false" />
					<span><c:out value="${welcomeMessage}" /></span>
				</li>
				<li>
					<span>|</span>
				</li>
				<li>
					<a href="${logoutUrl}"><spring:message code="global.logout" htmlEscape="false" /></a>
				</li>
			</ul>
		</sec:authorize>
	</div>
	
</div>
