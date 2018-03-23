<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


	
<div class="modal fade" role="dialog" id="myModal" tabindex="-1"
	aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
				<h4 class="modal-title" id="myModalLabel">Iniciar sesión</h4>
			</div>
			<div class="modal-body" style="padding: 20px 60px 60px 60px">
				<%-- <form name='loginForm' action="<c:url value='/login' />" method='POST'> --%>
				<form name='loginForm' id="loginForm" action="<c:url value='/login' />" method='POST'>

					<div class="form-group">
						<label for="username" class="control-label">Username</label>
						<input type='text' class="form-control" id="username" name="username" placeholder="User" />
					</div>

					<div class="form-group">
						<label for="password" class="control-label">Password</label>
						<input type='password' class="form-control" id="password" name="password" placeholder="Password" />
					</div>

					<div class="form-group" style="text-align: center;">
						<input type="button" class="btn btn-default" value="Clear" style="width: 100px" />
						<input type="submit" class="btn btn-success" name="submit" value="Sign in" style="width: 100px" />
					</div>

					<div class="form-group" style="text-align: center;">
						<button type="submit" id='ajaxbtn' class="btn btn-default" style="width: 100px">Submit</button>
					</div>

					<%
						String errorString = (String) request.getAttribute("error");
						if (errorString != null && errorString.trim().equals("true")) {
							out.println("<div class='form-group'><div class='alert alert-danger alert-dismissable fade in'><a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>Nombre de usuario o contraseña incorrecto.</div></div>");
						}
					%>

				</form>
			</div>
		</div>
	</div>
</div>