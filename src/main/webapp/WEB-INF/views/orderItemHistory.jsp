<%@include file="templates/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Order Management | Velaphanda Trading & Projects</title>
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

	<c:import url="templates/techniciannavbar.jsp"></c:import>

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
						<b>Order No : ORD00${OrderNum.recordID}</b>
					</div>
					<div class="panel-body">


						<div id="navbar" class="navbar-collapse collapse"
							style="margin-left: -2%">
							<ul class="nav navbar-nav navbar-left">

								<c:if test="${OrderNum.status == 'Shipped'}">
									<li class="dropdown"><a href="#" class="dropdown-toggle"
										data-toggle="dropdown" role="button" aria-haspopup="true"
										aria-expanded="false">Order Action<span class="caret"></span></a>
										<ul class="dropdown-menu">
											<c:choose>
												<c:when test="${OrderNum.status == 'Shipped'}">
													<li><a
														href="receive?recordID=<c:out value='${OrderNum.recordID}'/>">Receive
															Order</a></li>
												</c:when>
											</c:choose>
										</ul></li>

								</c:if>

								<li><a target="_blank"
									href="orderDownloadPDF?recordID=<c:out value='${OrderNum.recordID}' />">Download
										PDF </a></li>

							</ul>
						</div>
						<legend></legend>
						<ul class="nav nav-tabs">
							<li class="active"><a href="#orderDetails" data-toggle="tab">Order
									Details</a></li>
							<li><a href="#orderHistoryDetails" data-toggle="tab">History</a></li>
						</ul>

						<div class="tab-content">

							<!--#orderDetails tab-->
							<div class="tab-pane active" id="orderDetails">
								<div class="col-sm-4">
								
									<legend style="font-size: 18px; line-height: 1.42857143;">From</legend>

									<div class="machinedetailsdetailsfloatleft">

										<label id="companyName" name="companyName"><b>${velaphandaAddres.companyName}
										</b></label> <br>
										<li id="streetNumberStreetName" name="streetNumberStreetName">${velaphandaAddres.streetNumber}
											${velaphandaAddres.streetName}</li>
										<li id="city" name="city">${velaphandaAddres.city}</li>
										<li id="areaCode" name="areaCode">${velaphandaAddres.areaCode}</li>
										<li id="telephoneNumber" name="telephoneNumber">Switchboard:
											${velaphandaAddres.telephoneNumber}</li>
										<li id="faxNumber" name="faxNumber">Fax :
											${velaphandaAddres.faxNumber}</li>
										<li id="emailAdress" name="emailAdress">Email :
											${velaphandaAddres.emailAdress}</li>

									</div>
								</div>
								<div class="col-sm-4">

									<legend style="font-size: 18px; line-height: 1.42857143;">To</legend>

									<c:if test="${not empty OrderNum.customer.customerName}">
										<li id="siteStock"><b>${OrderNum.customer.customerName}</b></li>
										<li id="streetNumberStreetName">${OrderNum.customer.streetNumber}
											${OrderNum.customer.streetName}</li>
										<li id="city_town">${OrderNum.customer.city_town}</li>
										<li id="province">${OrderNum.customer.province}</li>
										<br />
										<li id="placedBy ">Placed By :
											${OrderNum.employee.firstName} ${OrderNum.employee.lastName}</li>
										<li id="approvedDate">Approved By : ${approver}</li>

									</c:if>
									<c:if test="${empty OrderNum.customer.customerName}">
										<li id="placedBy "><b>Placed By :
												${OrderNum.employee.firstName} ${OrderNum.employee.lastName}</b></li>
										<li id="Email">Email: ${OrderNum.employee.email}</li>
										<li id="Contact Number">Contact Number:
											${OrderNum.employee.cellNumber}</li>
										<br />
										<li id="approvedDate">Approved By : ${approver}</li>

									</c:if>

								</div>

								<div class="col-sm-4">

									<legend style="font-size: 18px; line-height: 1.42857143;">Order</legend>
									<li style="font-size: 15px;" id="orderNum"><b>
											ORD00${OrderNum.recordID}</b></li>
									<li id="status">Order Status: ${OrderNum.status}</li>
									<li id="dateOrdered">Stock Type: ${OrderNum.stockType}</li>
									<li id="dateOrdered">Ordered Date: ${OrderNum.dateOrdered}</li>
									<c:if test="${OrderNum.status == 'Declined'}">
										<p id="lebaka">
											<span style="font-weight: bolder">Reason for Decline</span>:
											<span style="color: red">${OrderNum.comments}</span>
										</p>
									</c:if>
									
								</div>

								<div class="col-sm-12">
									<br />
									<legend style="font-size: 18px; line-height: 1.42857143;">Line
										Items</legend>

									<table data-toggle="table" data-url="${pendingOrderList}"
										data-show-refresh="true" data-show-toggle="true"
										data-search="true" data-select-item-name="toolbar1"
										data-pagination="true" data-sort-name="dateordered"
										data-sort-order="desc">
										<thead>
											<tr>
												<th data-field="partno" data-sortable="true">Part No</th>
												<th data-field="compatibledevices" data-sortable="true">Compatible
													Devices</th>
												<th data-field="description" data-sortable="true">Description</th>
												<th data-field="quantity" data-sortable="true">Quantity</th>
											</tr>
										</thead>
										<tbody>
											<!-- Iterating over the list sent from Controller -->
											<c:forEach var="list" items="${pendingOrderList}">
												<tr>
													<td>${list.partNumber}</td>
													<td>${list.model}</td>
													<td>${list.itemDescription}</td>
													<td>${list.quantity}</td>													
												</tr>
											</c:forEach>
										</tbody>
									</table>

								</div>

							</div>

							<!--#orderHistoryDetails tab-->
							<div class="tab-pane" id="orderHistoryDetails">

								<div class="groupdetails-row-padding">

									<div class="groupclientdetails">
										<br />
										<legend>Order Status</legend>
										<form:form modelAttribute="orderHistory" method="post"
											action="orderHistory" id="orderHistory" name="orderHistory">										

											<!-- Below table will be displayed as Data table -->
											<table id="orderDetails"
												class="table table-striped table-bordered table-hover table-condensed">
												<thead>
													<tr>
														<th>Order Status</th>
														<th>Date/Time</th>
														<!-- <th>User</th>
 -->
													</tr>
												</thead>
												<tbody>
													<!-- Iterating over the list sent from Controller -->
													<c:forEach var="list" items="${status}">
														<tr>

															<td>${list.orderStatus}</td>
															<td>${list.statusDateTime}</td>
															<!-- <td>Who Approved/Recieved/Shipped</td> -->
														</tr>
													</c:forEach>
												</tbody>
											</table>
											<!-- table order History -->

										</form:form>
										<!-- form order History -->

									</div>
									<!-- group client details -->

								</div>
								<!-- group details-row-padding -->
							</div>

						</div>
						<!-- /tab-content -->
					</div>
					<!-- .panel-body -->
				</div>
				<!-- .panel panel-default -->
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
