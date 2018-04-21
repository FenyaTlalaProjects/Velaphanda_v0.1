<%@include file="templates/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Change Password | Velaphanda Trading & Projects</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<c:import url="templates/stylesheetlib.jsp"></c:import>

</head>
<body>
	<c:import url="templates/navbarpassword.jsp"></c:import>

	<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
		<div class="row">
			<ol class="breadcrumb">
				<li><a href="changePassword.html"><svg class="glyph stroked home">
						<use xlink:href="#stroked-home"></use></svg></a></li>

			</ol>
		</div>		

		<c:if test="${not empty retMessage }">
			<div class="alert alert-danger" role="alert">
				<c:out value="${ retMessage}">
				</c:out>
			</div>
		</c:if>

		<c:if test="${noDays == 73}">
			<div class="alert alert-info" role="alert">
				Your password is about to expire in
				<c:out value="${noDays}">
				</c:out>
				days. Please reset your password or <a href="login.html">login</a>
				here.
			</div>
		</c:if>

		<c:if test="${not empty retErrorMessage }">
			<div class="alert alert-danger" role="alert">
				<c:out value="${ retErrorMessage}">
				</c:out>
			</div>
		</c:if>

		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading" align="center">Change Password</div>
					<div class="panel-body">

						<form:form method="post" class="well form-horizontal"
							action="changePasswords" modelAttribute="changePasswords"
							id="changePass">

							<!--First column-->
							<div class="col-sm-6">

								<!-- Text input Email-->
								<div class="form-group">
									<label class="col-md-3 control-label">Email</label>
									<div class="col-md-8 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-envelope"></i></span> <input id="email"
												name="email" placeholder="Email" class="form-control"
												type="email" value="${loggedInUser.email }"
												readonly="readonly">
										</div>
									</div>
								</div>
								<!-- Text input Password-->
								<div class="form-group">
									<label class="col-md-3 control-label">Full Names</label>
									<div class="col-md-8 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-lock"></i></span> <input
												placeholder="Full Names" class="form-control" type="text"
												value="${loggedInUser.firstName } ${loggedInUser.lastName }"
												readonly="readonly">
										</div>
									</div>
								</div>

							</div>
							<!-- /F Column -->

							<div class="col-sm-6">
								<!-- Text input New Password-->
								<div class="form-group">
									<label class="col-md-3 control-label">New Password</label>
									<div class="col-md-8 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-lock"></i></span> <input
												name="newpassword" id="newpassword"
												placeholder="New Password" class="form-control"
												type="password">
										</div>
									</div>
								</div>
								<!-- Text input Confirm Password-->
								<div class="form-group">
									<label class="col-md-3 control-label">Confirm Password</label>
									<div class="col-md-8 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-lock"></i></span> <input
												name="confirmpassword" id="confirmpassword"
												placeholder="Confirm Password" class="form-control"
												type="password">
										</div>
									</div>
								</div>
							</div>
							<div class="form-group row">
								<div class="col-sm-offset-2 col-sm-8">
									<br> <br>
									<button type="submit" name="changepassword" id="changepassword"
										class="btn btn-lg btn-primary btn-block">Change
										Password</button>

								</div>
							</div>
						</form:form>

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
