<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Password Expired | Velaphanda Trading & Projects</title>
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
							<b>Password Expired </b>
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

					<form method="post" action="loginattempted" role="login">
						<div class="errorLogin">
							<h2>Your account password has expired.</h2>
							Click to <a href="changePassword.html"
								class="btn btn-info" tabindex
								id="changePassword">Change password</a> or click <a
								href="login.html"
								class="btn btn-success" tabindex
								id="changePassword">here</a> to ignore.
						</div>
					</form>
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
