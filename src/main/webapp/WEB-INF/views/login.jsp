<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Login | Velaphanda Trading & Projects</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<c:import url="templates/stylesheetlib.jsp"></c:import>

</head>

<body style="background-color: #0d50a1;">

	<div class="row">
		<div
			class="col-xs-10 col-xs-offset-1 col-sm-8 col-sm-offset-2 col-md-4 col-md-offset-4">
			<div class="login-panel panel panel-success">

				<div class="panel-heading">
					<h3 class="panel-title">
						<div align="center">
							<b>Login </b>
							<div class="vela_motto">
								<p>
									<span class="motto">Velaphanda</span> <span class="techsystem">Technical
										System</span>
								</p>
							</div>
						</div>
					</h3>
				</div>

				<div class="mainlogo" align="center">
					<img src="resources/images/mainlogo.jpg" class="img-responsive"
						alt="">
				</div>

				<div class="panel-body">

					<form:form method="post" action="authenticate" role="login"
						modelAttribute="authenticate" id="loginVali">
						<fieldset>
							<!-- Text input email-->
							<div class="form-group">
								<div class="col-md-40 inputGroupContainer">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-user"></i></span> <input id="email"
											name="email" autofocus="autofocus"
											placeholder="Email Address" class="form-control" type="text">
									</div>
								</div>
							</div>
							<!-- Text input Password-->
							<div class="form-group">
								<div class="col-md-40 inputGroupContainer">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-lock"></i></span> <input name="password"
											id="password" autofocus="autofocus" placeholder="Password"
											class="form-control" type="password">
									</div>
								</div>
							</div>
							<button type="submit" name="go"
								class="btn btn-lg btn-primary btn-block">Sign in</button>
						</fieldset>
					</form:form>
					<!--login form-->
					<!-- Footer -->
					<div class="footerLogin">
						<c:import url="templates/footer.jsp"></c:import>
					</div>
					<!--/ .footer -->
				</div>
				<!-- /.panel-body -->
			</div>
			<!-- /.login-panel panel panel-success -->
		</div>
		<!-- /.col-->
	</div>
	<!-- /.row -->
	<!-- Scripts -->
	<c:import url="templates/javascriptslib.jsp"></c:import>
	<!-- /Scripts -->
</body>
</html>