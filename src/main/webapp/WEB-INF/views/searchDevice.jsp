<%@include file="templates/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Display Devices | Velaphanda Trading & Projects</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<c:import url="templates/tableresizefont.jsp"></c:import>
<c:import url="templates/datatablesstyles.jsp"></c:import>
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
					<div class="panel-heading" align="center">Devices</div>
					<div class="panel-body">
						
						<a target="_blank"
								href="deviceListDownloadPDF">Download
								PDF </a><br/><br/>
							
						<!-- Below table will be displayed as Data table -->
						<table  id="deviceHistory" class="display">
							<thead>
								<tr>
									<th></th>
									<th data-field="customername" data-sortable="true">Customer</th>
									<th data-field="serialNo" data-sortable="true">Serial No</th>
									<th data-field="address" data-sortable="true">Address</th>
									<th data-field="modelNo" data-sortable="true">Model No</th>
									<th data-field="brand" data-sortable="true">Brand</th>
									<th data-field="viewDetails" data-sortable="true">View Details</th>
									<th data-field="deviceHistory" data-sortable="true">Device History</th>
									<th data-field="UpdateDevice" data-sortable="true">Update Device</th>
								</tr>
							</thead>
							<tbody>
								<!-- Iterating over the list sent from Controller -->
								<c:forEach var="list" items="${deviceList}">
									<tr>
										<td class="details-control" onclick="window.location='searchDevice.html?serialNumber=<c:out value='${list.serialNumber}'/>';"></td>
										<td><a
											href="viewCustomer?customerName=<c:out value='${list.customerDevice.customerName}'/>">
												${list.customerDevice.customerName}</a></td>
										<td><a
											href="detailedProduct?serialNumber=<c:out value='${list.serialNumber}'/>">
												${list.serialNumber}</a></td>
										<td>${list.buildingName } ${list.streetNumber } 
											${list.streetName }</td>
										<td>${list.modelNumber}</td>
										<td>${list.modelBrand}</td>
										<td><a
											href="detailedProduct?serialNumber=<c:out value='${list.serialNumber}'/>">
												Device Details</a></td>
										<td><a
											href="deviceHistory?serialNumber=<c:out value='${list.serialNumber}'/>">
												Device History</a></td>
										<td><a
											href="searchDeviceSerialNumber?serialNumber=<c:out value='${list.serialNumber}'/>">
												Update Device</a></td>
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
	<c:import url="templates/datatablesscripts.jsp"></c:import>
	<c:import url="templates/sidebar-collapse.jsp"></c:import>
	<script type="text/javascript">
			//device history table
			function deviceHistory() {
				return '<table id="deviceHistoryDetails" cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;"><thead><tr><th colspan="4" style="text-align:center;">Customer History</th></tr><tr><th>Name</th><th>Action</th><th>Date</th><th>Comment</th></tr></thead><tbody><c:forEach var="list" items="${displayDeviceHistory}"><tr><td>${list.userName}</td><td>${list.action}</td><td>${list.dateTime}</td><td>${list.description}</td></tr></c:forEach></tbody></table>';
			}
	</script>
	<!-- /Scripts -->
</body>
</html>