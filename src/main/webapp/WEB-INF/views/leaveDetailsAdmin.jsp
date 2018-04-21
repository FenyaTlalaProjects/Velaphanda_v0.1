<%@include file="templates/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Leave Details | Velaphanda Trading & Projects</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<c:import url="templates/tableresizefont.jsp"></c:import>
<c:import url="templates/stylesheetlib.jsp"></c:import>
<style type="text/css">
li {
	list-style: none;
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
					<div class="panel-heading" align="center">
						<b>Leave No : LV0000000${leave.leaveID}</b>
					</div>
					<div class="panel-body">

						<!-- navigation for action taken -->
						<div id="navbar" class="navbar-collapse collapse"
							style="margin-left: -2%">
							<ul class="nav navbar-nav navbar-left">

								<c:if test="${leave.status == 'Pending'}">
									<li class="dropdown"><a href="#" class="dropdown-toggle"
										data-toggle="dropdown" role="button" aria-haspopup="true"
										aria-expanded="false">Leave Action<span class="caret"></span></a>
										<ul class="dropdown-menu">
											<c:choose>
												<c:when test="${leave.status == 'Pending'}">
													<li><a
														href="approveLeave?leaveID= <c:out value='${leave.leaveID}'/>">Approve
															Leave</a></li>
													<li><a
														href="declineLeave?leaveID= <c:out value='${leave.leaveID}'/>">Reject
															Leave</a></li>
												</c:when>
											</c:choose>

										</ul></li>
								</c:if>

							</ul>
						</div>
						<!-- //navigation for action taken -->

						<legend></legend>
						<!-- nav sub menu tabs  -->
						<ul class="nav nav-tabs">
							<li class="active"><a href="#LeaveDetails" data-toggle="tab">Leave
									Details</a></li>
							<li><a href="#updateLeave" data-toggle="tab">Update
									Leave</a></li>
						</ul>
						<!-- // nav sub menu tabs-->
						
						<!-- tab content -->
						<div class="tab-content">

							<!--LeaveDetails tab-->
							<div class="tab-pane active" id="LeaveDetails">
								<br /> <br />

								<div class="col-sm-6">
									<legend style="font-size: 18px; line-height: 1.42857143;">
										Details </legend>


									<li id="Name" name="Name"><b>Name:
											${leave.employee.firstName} ${leave.employee.lastName}</b></li>
									<li id="leaveType" name="leaveType"><b>Leave Type:
											${leave.leaveType} </b></li>
									<li id="startDate" name="startDate">Leave Start Date:
										${leave.startDate}</li>
									<li id="endDate" name="endDate">Leave End Date:
										${leave.endDate}</li>
									<li id="status" name="status">Leave Status:
										${leave.status}</li>
									<c:if test="${leave.status == 'Cancelled'}">
										<p id="lebaka">
											<span style="font-weight: bolder">Reason for Decline</span>:
											<span style="color: red">${leave.comments}</span>
										</p>
									</c:if>
								</div>

								<div class="col-sm-6">
									<legend style="font-size: 18px; line-height: 1.42857143;">Contact
										Details During Leave</legend>
									<li id="cell">Cell No: ${leave.contactNumber}</li>
									<li id="telephone">Address: ${leave.address}</li>
									<li id="email">Email: ${leave.employee.email}</li>
								</div>

							</div>


							<!--updateLeave Details tab-->
							<div class="tab-pane" id="updateLeave">

								<form:form class="well form-horizontal" method="POST"
									action="updateLeave" modelAttribute="updateLeave"
									id="updateLeave">

									<c:if test="${leave.status != 'Active'}">
										
											<c:if test="${leave.status != 'Cancelled'}">

												<div class="col-sm-6">
													<div class="form-group">
														<label class="col-md-3 control-label"></label>
														<div class="col-md-6 selectContainer">
															<div class="input-group">
																<input type="submit" value="Update Leave"
																	class="btn btn-primary btn-block btn-sm" tabindex="9"
																	id="updateLeave">
															</div>
														</div>
													</div>
												</div>
												<div class="col-sm-6">
												<div class="form-group">
													<label class="col-md-3 control-label"></label>
													<div class="col-md-6 selectContainer">
														<div class="input-group">
															<a
																href="leaveCancellation?leaveID=<c:out value='${leave.leaveID}'/>"
																class="btn btn-danger btn-block btn-sm" tabindex
																id="cancelUpdate">Cancel
																Leave</a>
														</div>
													</div>
												</div>
												</div>										
										</c:if>
									</c:if>
							
							<input type="hidden" id="leaveID" name="leaveID"
								value="${leave.leaveID}">

							<!-- Select type Leave Type-->
							<div class="form-group">
								<label class="col-md-3 control-label">Type of Leave</label>
								<div class="col-md-6 selectContainer">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-list"></i></span> <select
											name="leaveType" id="leaveID"
											class="form-control selectpicker">
											<option value="${leave.leaveType}">${leave.leaveType}</option>
											<option value="Annual Vacation Leave">Annual
												Vacation Leave</option>
											<option value="Sick Leave">Sick Leave</option>
											<option value="Emergency Leave">Emergency Leave</option>
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
											id="startDate" placeholder="YYYY-MM-DD"
											value="${leave.startDate}" /> <span
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
											id="endDate" placeholder="YYYY-MM-DD"
											value="${leave.endDate}" /> <span class="input-group-addon">
											<span class="glyphicon glyphicon-calendar"></span>
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
											onkeypress="return isNumber(event)"
											value="${leave.contactNumber}">
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
											class="form-control" type="text" value="${leave.address}">
									</div>
								</div>
							</div>

							</form:form>

						</div>
						<!--//updateLeave Details tab-->

					</div>
					<!-- tab-->

					<!-- .panel-body -->
				</div>
				<!-- .panel panel-default -->
			</div>
		</div>
	</div>
	<!-- /.col-->
	<!-- /.row -->
	<!-- Footer -->
	<c:import url="templates/footer.jsp"></c:import>
	<!--/ Footer -->
	</div>
	<!--/.main-->
	<c:import url="templates/javascriptslib.jsp"></c:import>
	<c:import url="templates/ticketmanagementscript.jsp"></c:import>
	<c:import url="templates/sidebar-collapse.jsp"></c:import>
	<!-- /Scripts -->
</body>
</html>

