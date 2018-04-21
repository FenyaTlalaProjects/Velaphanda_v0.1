<%@include file="templates/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Register Employee | Velaphanda Trading & Projects</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<c:import url="templates/stylesheetlib.jsp"></c:import>

</head>
<body>
	<c:import url="templates/navbar.jsp"></c:import>

	<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
		<div class="row">
			<ol class="breadcrumb">
				<li><a href="home.html"><svg class="glyph stroked home">
						<use xlink:href="#stroked-home"></use></svg></a></li>
				<div class="nav navbar-nav navbar-right" style="margin-top: -1%;">
					<a href="#" onclick="history.go(-1);"><span
						class="glyphicon glyphicon-circle-arrow-left btn-lg"
						title="Previous Page"></span></a> <a href="#" onclick="history.go(1);"><span
						class="glyphicon glyphicon-circle-arrow-right btn-lg"
						title="Go Forward"></span></a>
				</div>
			</ol>
		</div>
		<!--/.row-->

		<c:if test="${not empty retMessage }">
			<div class="alert alert-info" role="alert">
				<c:out value="${ retMessage}">
				</c:out>
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
					<div class="panel-heading" align="center">Add Employee</div>
					<div class="panel-body">

						<form:form class="well form-horizontal" method="post"
							action="addEmployee" modelAttribute="addEmployee"
							id="addEmployee">

							<div class="col-md-6">

								<!-- Text input First Name-->
								<div class="form-group">
									<label class="col-md-3 control-label">First Name</label>
									<div class="col-md-8 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-user"></i></span> <input id="firstName"
												name="firstName" placeholder="First Name"
												class="form-control" type="text">
										</div>
									</div>
								</div>

								<!-- Text input Last Name-->
								<div class="form-group">
									<label class="col-md-3 control-label">Last Name</label>
									<div class="col-md-8 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-user"></i></span> <input name="lastName"
												placeholder="Last Name" class="form-control" type="text">
										</div>
									</div>
								</div>

								<!-- Select type Title-->
								<div class="form-group">
									<label class="col-md-3 control-label">Title</label>
									<div class="col-md-8 selectContainer">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-list"></i></span> <select name="title"
												id="title" class="form-control selectpicker"
												onchange="CheckGender(this.value);">
												<option value="">Select Title</option>
												<option value="Mr">Mr</option>
												<option value="Miss">Miss</option>
												<option value="Mrs">Mrs</option>
												<option value="Doc">Dr</option>
											</select>
										</div>
									</div>
								</div>

								<!-- Select type Gender-->
								<div class="form-group">
									<label class="col-md-3 control-label">Gender</label>
									<div class="col-md-8 selectContainer">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-list"></i></span> <select
												onchange="CheckGender(this.value);" id="gender"
												name="gender" class="form-control selectpicker">
												<option value="">Select Gender</option>
												<option value="Male">Male</option>
												<option value="Female">Female</option>

											</select>
										</div>
									</div>
								</div>
							</div>
							
							<div class="col-md-6">

								<!-- Text input Cellphone Number-->
								<div class="form-group">
									<label class="col-md-3 control-label">Cellphone No</label>
									<div class="col-md-8 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-earphone"></i></span> <input
												id="cellphoneNumber" name="cellNumber" maxlength="10"
												placeholder="Cellphone No" class="form-control" type="text"
												onkeypress="return isNumber(event)">
										</div>
									</div>
								</div>

								<!-- Text input email-->
								<div class="form-group">
									<label class="col-md-3 control-label">Email Address</label>
									<div class="col-md-8 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-envelope"></i></span> <input
												name="email" placeholder="Email Address"
												class="form-control" type="text">
										</div>
									</div>
								</div>

								<!-- Select type Role-->
								<div class="form-group">
									<label class="col-md-3 control-label">Role</label>
									<div class="col-md-8 selectContainer">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-list"></i></span> <select name="role"
												class="form-control selectpicker">
												<option value=" ">Select Role</option>
												<option value="Admin">Admin</option>
												<option value="Manager">Manager</option>
												<option value="Technician">Technician</option>
												<option value="User">User</option>

											</select>
										</div>
									</div>
								</div>

							</div>
							<div class="form-group row">
								<div class="col-sm-offset-2 col-sm-8">
									<br> <br> <input type="submit"
										value="Register Employee"
										class="btn btn-primary btn-block btn-lg" tabindex="9"
										id="registere">
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
