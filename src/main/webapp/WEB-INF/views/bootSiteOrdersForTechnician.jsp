<%@include file="templates/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Boot Stock | Velaphanda Trading & Projects</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<c:import url="templates/tableresizefont.jsp"></c:import>
<c:import url="templates/stylesheetlib.jsp"></c:import>
</head>
<body>
	<c:import url="templates/techniciannavbar.jsp"></c:import>

	<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
		<div class="row">
			<ol class="breadcrumb">
				<li><a href="technicianHome.html"><svg
							class="glyph stroked home"> <use xlink:href="#stroked-home"></use></svg></a></li>
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
					<div class="panel-heading" align="center">Boot Stock</div>
					<div class="panel-body">

						<c:if test="${not empty technician}">

							<div class="col-sm-12">
								<div class="row">
									<br />
									<div class="col-6 col-md-6">
										<a href='numberOfParts'>
											<div class="well" style="background-color: #ffffff;">
												<h5 class="text-danger">
													<span class="label label-danger pull-right">${countPartForTech}</span>
													Parts
												</h5>
											</div>
										</a>
									</div>
									<div class="col-6 col-md-6">
										<a href='numberOfToners'>
											<div class="well" style="background-color: #ffffff;">
												<h5 class="text-success">
													<span class="label label-success pull-right">${countTonerForTech}</span>
													Toners
												</h5>
											</div>
										</a>
									</div>
								</div>
							</div>

							<!-- tab nav -->
							<div class="tab-content">


								<table data-toggle="table" data-show-refresh="true"
									data-show-toggle="true" data-search="true"
									data-select-item-name="toolbar1" data-pagination="true"
									data-sort-name="partNo" data-sort-order="aesc">
									<thead>
										<tr>
											<th data-toggle="true" data-field="partNo"
												data-sortable="true">Part No</th>
											<th data-field="compatibleDevices" data-sortable="true">Compatible
												Devices</th>
											<th data-field="modelBrand" data-sortable="true">Model
												Brand</th>
											<th data-field="description" data-sortable="true">Description</th>
											<th data-field="quantity" data-sortable="true">Quantity</th>
											<th data-field="itemType" data-sortable="true">Item type</th>

										</tr>
									</thead>

									<tbody>
										<!-- Iterating over the list sent from Controller -->
										<c:forEach var="list" items="${orders}">
											<tr>
												<td><a
													href="loadBootStockHistoryMovement?partNumber=<c:out value='${list.partNumber}'/>">${list.partNumber}</a></td>
												<td>${list.compatibleDevice}</td>
												<td>${list.modelBrand}</td>
												<td>${list.itemDescription}</td>
												<td>${list.quantity}</td>
												<td>${list.itemType}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
						</c:if>

						<!-- Movement History Details -->
						<c:if test="${empty technician}">


							<div class="col-sm-6">
									<h3>Spare History for Receiving: ${partNumber}</h3>
								</div>
								<table data-toggle="table" data-url="" data-show-refresh="true"
									data-show-toggle="true" data-search="true"
									data-select-item-name="toolbar1" data-pagination="true"
									data-sort-name="spareRecievedBy" data-sort-order="aesc">
										<thead>
											
											<tr>
												<th data-field="spareRecievedBy" data-sortable="true">Spare Recieved By</th>
												<th data-field="action" data-sortable="true">Action</th>
												<th data-field="dateSpareRecieved" data-sortable="true">Date Spare Recieved</th>
												<th data-field="supplierName" data-sortable="true">Supplier Name</th>
												<th data-field="supplierOrderNo" data-sortable="true">Supplier Order No</th>
												<th data-field="quantityRecieved" data-sortable="true">Quantity Recieved</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="list" items="${displayHOSparesHistory}">
												<tr>
													<td>${list.hoSpareRecievedBy}</td>
													<td>${list.hoActionSpares}</td>
													<td>${list.hoDateSpareRecieved}</td>
													<td>${list.hoSupplierName}</td>
													<td>${list.hoSupplierOrderNo}</td>
													<td>${list.hoQuantityRecieved}</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>

								<div class="col-sm-6">
									<h3>History Movement for ${partNumber}</h3>
								</div>
								<table data-toggle="table" data-url="" data-show-refresh="true"
									data-show-toggle="true" data-search="true"
									data-select-item-name="toolbar1" data-pagination="true"
									data-sort-name="customername" data-sort-order="aesc">
									<thead>
										<tr>
											<th data-field="movedBy" data-sortable="true">Moved By</th>
											<th data-field="dateTimeMoved" data-sortable="true">Date
												& Time Moved</th>
											<th data-field="novedFrom" data-sortable="true">Moved
												From</th>
											<th data-field="movedTo" data-sortable="true">Moved To</th>
											<th data-field="quantityMoved" data-sortable="true">Quantity
												Moved</th>
											<th data-field="ReasonWhyMoved" data-sortable="true">Reason
												Why Moved</th>
										</tr>
									</thead>

									<tbody>
										<!-- Iterating over the list sent from Controller -->
										<c:forEach var="list" items="${displayBootStockMovement}">
											<tr>
												<td>${list.userName}</td>
												<td>${list.dateTimeMoved}</td>
												<td>${list.movedFrom}</td>
												<td>${list.movedTo}</td>
												<td>${list.quantityMoved}</td>
												<td>${list.description}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>

						</c:if>
						<!-- End Movement History Details -->

						<!-- .panel-body -->
					</div>
					<!-- .panel panel-default -->
				</div>
				<!-- /.col-->
			</div>
		</div>
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