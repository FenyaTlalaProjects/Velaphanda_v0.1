<%@include file="templates/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Display User Log Activities | Velaphanda Trading &
	Projects</title>

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
					<div class="panel-heading" align="center">Login Activities</div>
					<div class="panel-body">

						<table data-toggle="table" data-url="${loginUsers}"
							data-show-refresh="true" data-show-toggle="true"
							data-search="true"
							data-select-item-name="toolbar1" data-pagination="true"
							data-sort-name="customername" data-sort-order="aesc">
							<thead>
								<tr>
									<th data-field="firstname" data-sortable="true">First Name</th>
									<th data-field="lastname" data-sortable="true">Last Name</th>
									<th data-field="email" data-sortable="true">Email</th>
									<th data-field="logindatetime" data-sortable="true">Login
										Date Time</th>
									<th data-field="logoutdatetime" data-sortable="true">Logout
										Date Time</th>
								</tr>
							</thead>

							<tbody>
								<!-- Iterating over the list sent from Controller -->
								<c:forEach var="list" items="${loginUsers}">
									<tr>
										<td>${list.employee.firstName}</td>
										<td>${list.employee.lastName}</td>
										<td>${list.employee.email}</td>
										<td>${list.loginDateTime}</td>
										<td>${list.logoutDateTime}</td>
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