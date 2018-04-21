<%@include file="templates/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Customer Device List | Velaphanda Trading & Projects</title>
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

		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading" align="center">Customers Device
						List</div>
					<div class="panel-body">
						<table data-toggle="table" data-url="${deviceList}"
							data-show-refresh="true" data-show-toggle="true"
							data-search="true" data-select-item-name="toolbar1" data-pagination="true"
							data-sort-name="serialno" data-sort-order="aesc">
							<thead>
								<tr>
									<th data-field="serialno" data-sortable="true">Serial No</th>
									<th data-field="devicemodel" data-sortable="true">Device Model</th>
									<th data-field="contactperson" data-sortable="true">Contact Person</th>
									<th data-field="cellno" data-sortable="true">Cell No</th>
									<th data-field="devicedetails" data-sortable="true">Device Details</th>
									<th data-field="update" data-sortable="true">Update Device</th>
								</tr>
							</thead>
							<tbody>
								<!-- Iterating over the list sent from Controller -->
								<c:forEach var="list" items="${deviceList}">
									<tr>
										<td>${list.serialNumber}</td>
										<td>${list.modelNumber}</td>
										<td>${list.contactPerson.firstName}
											${list.contactPerson.lastName}</td>
										<td>${list.contactPerson.cellphone}</td>
										<td><a
											href="detailedProduct?serialNumber=<c:out value='${list.serialNumber}'/>">Device
												Details</a></td>
										<td><a
											href="searchDeviceSerialNumber?serialNumber=<c:out value='${list.serialNumber}'/>">Update
												Device</a></td>
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
