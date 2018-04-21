<%@include file="templates/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Display Employees | Velaphanda Trading & Projects</title>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<c:import url="templates/tableresizefont.jsp"></c:import>
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

		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading" align="center">Display Employees</div>
					<div class="panel-body">
					
						<div class="addEmployee">
							<button type="button" id="addemployee" class="btn btn-success"
								name="addemployee" value="Add Employee"
								onclick="window.location.href='registerEmployee.html'">Add
								Employee</button>
						</div>
						
						<table data-toggle="table" data-url="${displayEmployees}"
							data-show-refresh="true" data-show-toggle="true"
							data-search="true" data-select-item-name="toolbar1"
							data-pagination="true" data-sort-name="email"
							data-sort-order="aesc">
							<thead>
								<tr>
									<th data-field="firstname" data-sortable="true">First Name</th>
									<th data-field="lastname" data-sortable="true">Last Name</th>
									<th data-field="email" data-sortable="true">Email</th>
									<th data-field="cellno" data-sortable="true">Cell No</th>
									<th data-field="status" data-sortable="true">Status</th>
									<th data-field="role" data-sortable="true">Role</th>
									<th data-field="loginactivities" data-sortable="true">Activities</th>
									<th data-field="update" data-sortable="true">Update</th>
									<th data-field="reset" data-sortable="true">Reset</th>
									<th data-field="active" data-sortable="true">Action</th>
								</tr>
							</thead>
							<tbody>
								<!-- Iterating over the list sent from Controller -->
								<c:forEach var="list" items="${displayEmployees}">
									<tr>
										<td>${list.firstName}</td>
										<td>${list.lastName}</td>
										<td>${list.email}</td>
										<td>${list.cellNumber}</td>
										<td>${list.status}</td>
										<td>${list.role}</td>
										<td><a
											href="searchEmployeeByNameForActivities?email=<c:out value='${list.email}'/>">Login
												Activities</a></td>
										<td><a
											href="searchEmployeeByName?email=<c:out value='${list.email}'/>">

												<c:choose>
													<c:when test="${list.status=='ACTIVE'}"> Update </c:when>
												</c:choose> <c:choose>
													<c:when test="${list.status=='INACTIVE'}">
													</c:when>
												</c:choose>

										</a></td>
										<td><a
											href="searchEmployeeForPasswordReset?email=<c:out value='${list.email}'/>">

												<c:choose>
													<c:when test="${list.status=='ACTIVE'}"> 
												        Reset
										 	         </c:when>
													<c:when test="${list.status=='BLOCKED'}"> 
												      	Reset
											         </c:when>
													<c:when test="${list.status=='INACTIVE'}">
													</c:when>

													<c:when test="${list.status=='INACTIVE'}">
													</c:when>
												</c:choose>

										</a></td>
										<td><a
											href="searchEmployeeForDeactivation?email=<c:out value='${list.email}'/>">
												<c:choose>
													<c:when test="${list.status=='ACTIVE'}"> 
												        Deactivate
										 	         </c:when>

													<c:when test="${list.status=='BLOCKED'}">

													</c:when>

													<c:when test="${list.status=='INACTIVE'}">
													 	Activate	
													 </c:when>

													<c:when test="${list.status=='INACTIVE'}">

													</c:when>

												</c:choose>
										</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<!-- .panel-body -->
				</div>
				<!-- .panel panel-default -->
			</div>
			<!-- /.col-->
		</div>
		<!-- /.row -->
		<!-- footer -->
		<c:import url="templates/footer.jsp"></c:import>
		<!--/ footer -->
	</div>
	<!--/.main-->
	<c:import url="templates/javascriptslib.jsp"></c:import>
	<c:import url="templates/sidebar-collapse.jsp"></c:import>
	<!-- /Scripts -->
</body>
</html>