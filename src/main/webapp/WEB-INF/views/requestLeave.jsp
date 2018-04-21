<%@include file="templates/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Leave | Velaphanda Trading & Projects</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<c:import url="templates/tableresizefont.jsp"></c:import>
<c:import url="templates/stylesheetlib.jsp"></c:import>
<c:import url="templates/topviewtickets.jsp"></c:import>
<style type="text/css">
input#addLeave {
    width: 45%;
}
</style>
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

		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading" align="center">Request Leave</div>
					<div class="panel-body">


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
		
			
					
					<form:form class="well form-horizontal" method="POST"
						action="makeLeave" modelAttribute="leamakeLeaveve" id="makeLeave">
						
						<div class="form-group row">
							<div class="col-sm-offset-3 col-sm-2">
								<input type="submit" value="Create"
									class="btn btn-primary btn-block btn-sm" tabindex="9"
									id="addLeave">
							</div>
						</div>
						<!-- Select type Leave Type-->
						<div class="form-group">
							<label class="col-md-3 control-label">Type of Leave</label>
							<div class="col-md-6 selectContainer">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="glyphicon glyphicon-list"></i></span> <select name="leaveType"
										id="leaveType" class="form-control selectpicker">
										<option value="">Select Leave</option>
										<option value="Annual Vacation Leave">Annual Vacation
											Leave</option>
										<option value="Sick Leave">Sick Leave</option>
										<option value="Emergency Leave">Emergency Leave</option>
									</select>
								</div>
							</div>
						</div>
						
						<div class="form-group">
									<label class="col-md-3 control-label">Technician</label>
									<div class="col-md-6 selectContainer">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-list"></i></span> <select
												name="technicianUserName" id="technicianUserName" class="form-control selectpicker">
												<option value="">Select Technician</option>
												<c:forEach items="${technicians}" var="technician">
													<option value="${technician.email}">${technician.firstName} ${technician.lastName}</option>
												</c:forEach>
											</select>
										</div>
									</div>
						</div>
						

						<!-- Text input First Date Leave-->
						<div class="form-group">
							<label class="col-xs-3 control-label">Leave Start Date</label>
							<div class="col-md-6 inputGroupContainer">
								<div class="input-group input-append date" id="startDatePicker">
									<input type='text' class="form-control" name="startDate"
										id="startDate" placeholder="YYYY-MM-DD" /> <span
										class="input-group-addon"> <span
										class="glyphicon glyphicon-calendar"></span>
									</span>
								</div>
							</div>
						</div>
						<!-- Text input Last Date Leave-->
						<div class="form-group">
							<label class="col-md-3 control-label">Leave End Date</label>
							<div class="col-md-6 inputGroupContainer">
								<div class="input-group input-append date" id="endDatePicker">
									<input type='text' class="form-control" name="endDate"
										id="endDate" placeholder="YYYY-MM-DD" /> <span
										class="input-group-addon"> <span
										class="glyphicon glyphicon-calendar"></span>
									</span>
								</div>
							</div>
						</div>
						<!-- Text input Contact Number well on leave-->
						<div class="form-group">
							<label class="col-md-3 control-label">Contact Number</label>
							<div class="col-md-6 inputGroupContainer">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="glyphicon glyphicon-earphone"></i></span> <input
										id="contactNumber" name="contactNumber"
										placeholder="Contact Number during absence"
										class="form-control" type="text"
										onkeypress="return isNumber(event)" maxlength="10">
								</div>
							</div>
						</div>
						<!-- Text input Address well on leave-->
						<div class="form-group">
							<label class="col-md-3 control-label">Address</label>
							<div class="col-md-6 inputGroupContainer">
								<div class="input-group">
									<span class="input-group-addon"><i
										class="glyphicon glyphicon-home"></i></span> <input id="address"
										name="address" placeholder="Address during absence"
										class="form-control" type="text">
								</div>
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
	<c:import url="templates/leavescript.jsp"></c:import>	
	<c:import url="templates/sidebar-collapse.jsp"></c:import>
	<!-- /Scripts -->
</body>
</html>


