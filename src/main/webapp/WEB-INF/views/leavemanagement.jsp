<%@include file="templates/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Leave Management</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<c:import url="templates/tableresizefont.jsp"></c:import>
<c:import url="templates/stylesheetlib.jsp"></c:import>
<c:import url="templates/topviewtickets.jsp"></c:import>
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
					<div class="panel-heading" align="center">Leave Management</div>
					<div class="panel-body">


						<button type="button" id="createLeave" class="btn btn-success"
							name="createLeave" value="Create Leave"
							onclick="window.location.href='requestLeave.html'">Create
							Leave</button>
						<br /> <br />

						<form:form action="searchOrderNumber" method="post"
							id="searchOrderNumber" modelAttribute="searchOrderNumber">

							<div style="margin-bottom: -3px; margin-left: -1px;" align=left>

								<!-- Select type Technician-->
								<div class="form-group ">
									<div class="col-md-3 selectContainer">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-list"></i></span> <select
												name="technicianName" id="technicianName"
												class="form-control selectpicker"
												onchange="location = this.value;">
												<c:if test="${not empty selectedTechnician }">
													<option>${ selectedTechnician.firstName}${ selectedTechnician.lastName}</option>
												</c:if>
												<option
													value="getTechnicianName1?technicianName=<c:out value="All Technicians"/>">All
													Technicians</option>
												<c:forEach items="${technicians}" var="technician">
													<option
														value="getTechnicianName1?technicianName=<c:out value='${technician.email}'/>">${technician.firstName}
														${technician.lastName}</option>
												</c:forEach>

											</select>
										</div>
									</div>
								</div>

								<!-- Select type selectDateRange-->
								<!-- Select type selectDateRange-->
								<div class="form-group ">
									<div class="col-md-3 selectContainer">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-list"></i></span> <select
												name="selectDateRange" id="selectDateRange"
												class="form-control selectpicker"
												onchange="location = this.value;">
												<c:if test="${empty newDate }">
													<option>Select a date</option>
												</c:if>
												<c:if test="${not empty newDate }">
													<option value="${ newDate}">${ newDate}</option>
												</c:if>
												<c:forEach items="${dates}" var="date">
													<option
														value="getUserSelectedLeaveDate?selectedDate=<c:out value='${date}'/>">${date}</option>
												</c:forEach>
											</select>

										</div>
									</div>
								</div>
							</div>

						</form:form>


						<div class="row">
							<ul class="nav nav-tabs">
								<div class="col-sm-12">
									<div class="row">
										<br />
										<div class="col-xs-6 col-md-3">
											<a href='<c:url value="pendingLeave"/>'>
												<div class="well" style="background-color: #ffffff;">
													<h5 class="text-primary">
														<span class="label label-primary pull-right">${countPendingLeave}</span>
														Pending Leave
													</h5>
												</div>
											</a>
										</div>
										<div class="col-xs-6 col-md-3">
											<a href='<c:url value="approvedLeave"/>'>
												<div class="well" style="background-color: #ffffff;">
													<h5 class="text-success">
														<span class="label label-success pull-right">${countApprovedLeave}</span>
														Approved Leave
													</h5>
												</div>
											</a>
										</div>
										<div class="col-xs-6 col-md-3">
											<a href='<c:url value="activeLeave"/>'>
												<div class="well" style="background-color: #ffffff;">
													<h5 class="text-success">
														<span class="label label-success pull-right">${countActiveLeave}</span>
														Active Leave
													</h5>
												</div>
											</a>
										</div>
										<div class="col-xs-6 col-md-3">
											<a href='<c:url value="cancelledLeave"/>'>
												<div class="well" style="background-color: #ffffff;">
													<h5 class="text-danger">
														<span class="label label-danger pull-right">${countCancelledLeave}</span>
														Cancelled Leave
													</h5>
												</div>
											</a>
										</div>
									</div>
									<!--/row-->
									<div class="row">

										<div class="col-xs-6 col-md-3">
											<a href='<c:url value="leaveHistory"/>'>
												<div class="well" style="background-color: #ffffff;">
													<h5 class="text-success">
														<span class="label label-success pull-right"></span> Leave
														History
													</h5>
												</div>
											</a>
										</div>
									</div>
								</div>
							</ul>
						</div>


						<!-- tab nav -->
						<div class="tab-content">
							<c:choose>
								<c:when test="${heading=='All Leaves' }">
									<div class="panel-heading" align="center">All Leaves</div>
								</c:when>
								<c:when test="${heading=='Leave History' }">
									<div class="panel-heading" align="center">Leave History</div>
								</c:when>
								<c:when test="${heading=='Pending' }">
									<div class="panel-heading" align="center">Pending Leave</div>
								</c:when>								
								<c:when test="${heading=='Approved' }">
									<div class="panel-heading" align="center">Approved Leave</div>
								</c:when>
								<c:when test="${heading=='Cancelled' }">
									<div class="panel-heading" align="center">Cancelled Leave</div>
								</c:when>
								<c:when test="${heading=='Active' }">
									<div class="panel-heading" align="center">Active Leave</div>
								</c:when>
							</c:choose>
							<table data-toggle="table" data-url="${leaveList}"
								data-show-refresh="true" data-show-toggle="true"
								data-search="true" data-select-item-name="toolbar1"
								data-pagination="true" data-sort-name="startdate"
								data-sort-order="desc">
								<thead>
									<tr>
										<th data-field="leavenumber" data-sortable="true">Leave
											Number</th>
										<th data-field="startdate" data-sortable="true">Start
											Date</th>
										<th data-field="enddate" data-sortable="true">End Date</th>
										<th data-field="fullname" data-sortable="true">Full Name</th>
										<th data-field="email" data-sortable="true">Email</th>
										<th data-field="leavestatus" data-sortable="true">Leave
											Status</th>
										<th data-field="leavetype" data-sortable="true">Leave
											Type</th>
										<th data-field="contact" data-sortable="true">Contact</th>
										<th data-field="addressduringleave" data-sortable="true">Address
											During Leave</th>
									</tr>
								</thead>
								<tbody>
									<!-- Iterating over the list sent from Controller -->
									<c:forEach items="${leaveList}" var="leave" varStatus="itr">
										<tr>
											<td><a
												href="leaveDetailsAdmin?leaveID=<c:out value='${leave.leaveID}'/>">LV0000000${leave.leaveID}</a></td>
											<td><c:out value="${leave.startDate}" /></td>
											<td><c:out value="${leave.endDate}" /></td>
											<td><c:out
													value="${leave.employee.firstName} ${leave.employee.lastName}" /></td>
											<td><c:out value="${leave.employee.email}" /></td>
											<td><c:out value="${leave.status}" /></td>
											<td><c:out value="${leave.leaveType}" /></td>

											<td><c:out value="${leave.contactNumber}" /></td>
											<td><c:out value="${leave.address}" /></td>

										</tr>
									</c:forEach>
								</tbody>
							</table>
							<!-- table leave -->
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
	<c:import url="templates/ticketmanagementscript.jsp"></c:import>
	<c:import url="templates/sidebar-collapse.jsp"></c:import>
	<!-- /Scripts -->
</body>
</html>

