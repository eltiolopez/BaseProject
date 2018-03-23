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
  	
  	
  	<script lang="javascript">
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
	  	        $('#wrapper').toggleClass('toggled');
	  	  });  
	  	});
  	</script>
</div>
