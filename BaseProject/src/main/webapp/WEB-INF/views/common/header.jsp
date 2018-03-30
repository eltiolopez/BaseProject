<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="utils" tagdir="/WEB-INF/tags/utils" %>

<div id="header">
	<h1>CABECERA DE PÁGINA</h1>
	
	<div id="language">
		<utils:theme label="Default" theme="default" target="/"/>
		<utils:theme label="Bootstrap" theme="bootstrap" target="/"/>

	  	<utils:language label="Español" locale="es_ES" target="/"/>
	  	<utils:language label="English" locale="en_GB" target="/"/>
  	</div>
  	
</div>
