<%@include file="templates/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Activate Employee | Velaphanda Trading & Projects</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/custom/css/vela_custom.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/bootstrap-3.3.7/fonts/font-awesome.min.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/bootstrap-3.3.7/css/bootstrap.min.css" />" />
<link rel="stylesheet" type="text/css" 	href="<c:url value="/resources/bootstrapValidator-0.5.3/css/bootstrapValidator.min.css" />" />


</head>
<body>
	<div class="velaphanda_containter">
		<c:import url="templates/navbar.jsp"></c:import>
		<div class="container">
			<c:if test="${not empty retMessage }">
				<div class="alert alert-info" role="alert">
					<c:out value="${ retMessage}">
					</c:out>
				</div>
			</c:if>
			<div class="panel panel-success">
				<div class="panel-heading">
					<h3 class="panel-title">
						<div align="center">
							<b>Activate Employee</b>
						</div>
					</h3>
				</div>
				<div class="panel-body">
					<div class="tab-content">

						<form action="searchEmployeeForActivation" method="post"
							id="searchEmployeeForActivation">
							<div class="row">



							</div>

							<hr>
						</form>
						<br>

						<div class="bb-alert alert alert-info" style="display: none;">
							<span>This was logged in the callback: false</span>
						</div>

						<form:form class="well form-horizontal" method="post"
							action="deactivateEmp" modelAttribute="deactivateEmp"
							id="deactivateEmp">

							<!--First column-->
							<div class="col-sm-6">

								<!-- Text input First Name-->
								<div class="form-group">
									<label class="col-md-3 control-label">First Name</label>
									<div class="col-md-6 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-user"></i></span> <input
												name="firstName" placeholder="First Name"
												class="form-control" readonly="readonly" type="text"
												value='${employeeObject.firstName }'>
										</div>
									</div>
								</div>

								<!-- Text input Last Name-->
								<div class="form-group">
									<label class="col-md-3 control-label">Last Name</label>
									<div class="col-md-6 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-user"></i></span> <input name="lastName"
												placeholder="Last Name" class="form-control"
												readonly="readonly" type="text"
												value='${employeeObject.lastName }'>
										</div>
									</div>
								</div>

								<!-- Select type Title-->
								<div class="form-group">
									<label class="col-md-3 control-label">Title</label>
									<div class="col-md-6 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-user"></i></span> <input name="title"
												class="form-control" type="text" readonly="readonly"
												value="${ employeeObject.title}">${ employeeObject.title}>
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
									<div class="col-md-6 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-user"></i></span> <input name="gender"
												class="form-control" type="text" readonly="readonly"
												value=" ${ employeeObject.gender}">${ employeeObject.gender}>
										</div>
									</div>
								</div>


								<!-- Text input email-->
								<div class="form-group">
									<label class="col-md-3 control-label">E-Mail</label>
									<div class="col-md-6 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-envelope"></i></span> <input
												name="email" placeholder="E-Mail Address"
												class="form-control" readonly="readonly" type="text"
												value='${ employeeObject.email}'>
										</div>
									</div>
								</div>

								<!-- Select type Role-->
								<div class="form-group">
									<label class="col-md-3 control-label">Role</label>
									<div class="col-md-6 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-user"></i></span> <input name="role"
												class="form-control" type="text" readonly="readonly"
												value=" ${ employeeObject.role}">${ employeeObject.role}>
										</div>
									</div>
								</div>



							</div>
							<!-- /S column  -->

							<div class="form-group row">
								<div class="col-sm-offset-2 col-sm-8">
									<br>
									<br> <input type="submit" value="Activate Employee"
										class="btn btn-primary btn-block btn-lg" tabindex="9"
										id="activateEmp" name="activateEmp"
										data-confirm="Are are sure you want to activate this employee?">


								</div>
							</div>

						</form:form>
					</div>
					<!-- /tab-content -->
				</div>
				<!-- /panel body -->
			</div>
			<!--/panel success class-->
		</div>
		<!-- /Container -->
		<!-- Footer -->
		<c:import url="templates/footer.jsp"></c:import>
		<!--/ Footer -->
	</div>
	<!-- / velaphanda_containter -->

	<script type="text/javascript"
		src="<c:url value="/resources/jquery/1.12.4/jquery.min.js" />"></script>
	<script type="text/javascript"
	src="<c:url value="/resources/jquery/1.13.1/jquery.validate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/bootstrap-3.3.7/js/bootstrap.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/bootstrapValidator-0.5.3/js/bootstrapValidator.min.js"/>"></script>
		
	<script type="text/javascript" src="<c:url value="/resources/custom/js/velas_validations.js"/>"></script>

</body>
</html>
