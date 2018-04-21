<%@include file="templates/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Deactivate Employee | Velaphanda Trading & Projects</title>
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
					<div class="panel-heading" align="center">
						<c:choose>
							<c:when test="${employeeObject.status=='INACTIVE' }">
								<b>Activate Employee</b>
							</c:when>
							<c:otherwise>
								<b>Deactivate Employee</b>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="panel-body">
						<form:form class="well form-horizontal" method="post"
							action="deactivateEmp" modelAttribute="deactivateEmp"
							id="deactivateEmp">

							<!--First column-->
							<div class="col-sm-6">

								<!-- Text input First Name-->
								<div class="form-group">
									<label class="col-md-3 control-label">First Name</label>
									<div class="col-md-8 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-user"></i></span> <input
												name="firstName" placeholder="First Name"
												class="form-control" type="text"
												value='${employeeObject.firstName }' readonly="readonly">
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
												placeholder="Last Name" class="form-control" type="text"
												value='${employeeObject.lastName }' readonly="readonly">
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
												class="form-control selectpicker">
												<option value="${ employeeObject.title}">${ employeeObject.title}</option>
												<option value="Mr">Mr</option>
												<option value="Miss">Miss</option>
												<option value="Mrs">Mrs</option>
												<option value="Dr">Dr</option>
											</select>
										</div>
									</div>
								</div>
							</div>
							<!-- / F column -->

							<!--Second column-->
							<div class="col-sm-6">

								<!-- Select type Gender-->
								<div class="form-group">
									<label class="col-md-3 control-label">Gender</label>
									<div class="col-md-8 selectContainer">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-list"></i></span> <select name="gender"
												class="form-control selectpicker">
												<option value=" ${ employeeObject.gender}">${ employeeObject.gender}</option>
												<option value="Mr">Male</option>
												<option value="Miss">Female</option>

											</select>
										</div>
									</div>
								</div>

								<!-- Text input email-->
								<div class="form-group">
									<label class="col-md-3 control-label">E-Mail</label>
									<div class="col-md-8 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-envelope"></i></span> <input
												name="email" placeholder="E-Mail Address"
												class="form-control" type="text"
												value='${ employeeObject.email}' readonly="readonly">
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
												<option value=" ${ employeeObject.role}">${ employeeObject.role}</option>
												<option value="Admin">Admin</option>
												<option value="Manager">Manager</option>
												<option value="Technician">Technician</option>
												<option value="User">User</option>

											</select>
										</div>
									</div>
								</div>

							</div>
							<!-- /S column  -->

							<div class="form-group row">
								<div class="col-sm-offset-2 col-sm-8">
									<br> <br>

									<c:choose>
										<c:when test="${employeeObject.status=='INACTIVE' }">
											<input type="submit" value="Activate Employee"
												class="btn btn-success btn-block btn-lg" id="deactivateEmp"
												name="deactivateEmp"
												onclick="return confirm('Are are sure you want to activate this employee?');">

										</c:when>
										<c:when test="${employeeObject.status=='BLOCKED' }">
											<input type="submit" value="Activate Employee"
												class="btn btn-success btn-block btn-lg" id="deactivateEmp"
												name="deactivateEmp"
												onclick="return confirm('Are are sure you want to activate this employee?');">

										</c:when>
										<c:otherwise>
											<input type="submit" value="Deactivate Employee"
												class="btn btn-danger btn-block btn-lg" id="deactivateEmp"
												name="deactivateEmp" onclick="return confirm('Are are sure you want to deactivate this employee?');"
												>
										</c:otherwise>
									</c:choose>
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
