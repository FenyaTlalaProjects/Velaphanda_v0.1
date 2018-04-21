<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Three login attempt with wrong Credentials | Velaphanda
	Trading & Projects</title>
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

				<div class="row">
					<div class="col-lg-12">
						<div class="panel panel-default">
							<div class="panel-heading" align="center">Login attempt
								with wrong password</div>
							<div class="panel-body">
								<div class="errorLogin">
									<c:if test="${not empty attempMessage }">
										<div class="alert alert-danger" role="alert">
											<c:out value="${ attempMessage}">
											</c:out>
										</div>
									</c:if>
									<a href="login.html">Login</a> with correct credentials
								</div>
							</div>
							<!-- .panel-body -->
						</div>
						<!-- .panel panel-default -->
					</div>
					<!-- /.col-->
				</div>
				<!-- /.row -->
				<!-- Footer -->
				<c:import url="templates/footer.jsp"></c:import>
				<!--/ Footer -->
			</div>
			<!--/.main-->
			<c:import url="templates/javascriptslib.jsp"></c:import>
			<c:import url="templates/sidebar-collapse.jsp"></c:import>
			<!-- /Scripts -->
</body>
</html>
