<%@include file="templates/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Order Management</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<c:import url="templates/tableresizefont.jsp"></c:import>
<c:import url="templates/stylesheetlib.jsp"></c:import>
<c:import url="templates/topviewtickets.jsp"></c:import>
</head>
<body onload="CovertDateToString()">

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
					<div class="panel-heading" align="center">Orders</div>
					<div class="panel-body">

						<button type="button" id="placeOrderForTechnician"
							class="btn btn-success" name="placeOrderForTechnician"
							value="Create Ticket"
							onclick="window.location.href='placeOrderForTechnician.html'">Create
							Order</button>
						<br /> <br />

						<form:form action="searchOrder" method="post" id="searchOrder"
							modelAttribute="searchOrder">

							<div style="margin-bottom: -3px; margin-left: -1px;" align=left>
								<!-- Select type customers-->
								<div class="form-group ">
									<div class="col-md-3 selectContainer">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-list"></i></span> <select
												name="customerName" id="customerName"
												class="form-control selectpicker">
												<c:if test="${not empty selectedName }">
													<option value="${selectedName}">${selectedName}</option>
												</c:if>
												<option value="<c:out value="All Customers"/>">All
													Customers</option>
												<c:forEach items="${customers}" var="customer">
													<option value="<c:out value='${customer.customerName}'/>">${customer.customerName}</option>
												</c:forEach>
											</select>

										</div>
									</div>
								</div>
								<!-- Select type selectDateRange-->
								<div class="form-group">
									<div class="col-md-3 selectContainer">
										<div class="input-group">
											<input type="text" class="form-control"
												name="selectDateRange" id="selectDateRange"
												value="${newDate}"> <span class="input-group-addon"><i
												class="glyphicon glyphicon-calendar"></i></span>
										</div>
									</div>
								</div>

								<!-- Select type selectTehnnician-->
								<div class="form-group ">
									<div class="col-md-3 selectContainer">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-list"></i></span> <select
												name="technicianName" id="technicianName"
												class="form-control selectpicker">
												<c:if test="${not empty selectedTechnician }">
													<option>${ selectedTechnician.firstName}${ selectedTechnician.lastName}</option>
												</c:if>
												<option value="<c:out value="All Technicians"/>">All
													Technicians</option>
												<c:forEach items="${technicians}" var="technician">
													<option value="<c:out value='${technician.email}'/>">${technician.firstName}
														${technician.lastName}</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>
								<div align=right>
									<!-- Text input Search-->
									<div class="form-group">
										<div class="col-md-3 inputGroupContainer">
											<div class="input-group">
												<input name="orderNumber" list="orderNumbers"
													class="form-control" type="text"
													onkeydown="upperCaseF(this)"
													placeholder='Enter Order Number' /> <span
													class="input-group-btn">
													<button class="btn btn-success" type="submit">
														<div class="up" style="margin-top: -8%; color: white;">Search</div>
													</button>
												</span>
											</div>
											<!-- /input-group -->
										</div>
										<!-- Iterating over the list sent from Controller -->
										<datalist id="orderNumbers"> <c:forEach var="list"
											items="${orderNumbers}">
											<option value="${list}">
										</c:forEach> </datalist>
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
											<a href='<c:url value="ordersToApprove"/>'>
												<div class="well" style="background-color: #ffffff;">
													<h5 class="text-primary">
														<span class="label label-primary pull-right">${countNewOrders}</span>
														Orders to Approve
													</h5>
												</div>
											</a>
										</div>
										<div class="col-xs-6 col-md-3">
											<a href='<c:url value="ordersToShip"/>'>
												<div class="well" style="background-color: #ffffff;">
													<h5 class="text-success">
														<span class="label label-success pull-right">${countApprovedOrder}</span>
														Orders to Ship
													</h5>
												</div>
											</a>
										</div>
										<div class="col-xs-6 col-md-3">
											<a href='<c:url value="ShippedOrders"/>'>
												<div class="well" style="background-color: #ffffff;">
													<h5 class="text-success">
														<span class="label label-success pull-right">${countShippedOrder}</span>
														Shipped Orders
													</h5>
												</div>
											</a>
										</div>
										<div class="col-xs-6 col-md-3">
											<a href='<c:url value="rejectedOrders"/>'>
												<div class="well" style="background-color: #ffffff;">
													<h5 class="text-danger">
														<span class="label label-danger pull-right">${countRejectedOrder}</span>
														Rejected Orders
													</h5>
												</div>
											</a>
										</div>
									</div>
									<!--/row-->
									<div class="row">
										<div class="col-xs-6 col-md-3">
											<a href='<c:url value="closedOrders"/>'>
												<div class="well" style="background-color: #ffffff;">
													<h5 class="text-success">
														<span class="label label-success pull-right">${countClosedOrder}</span>
														Closed Orders
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
								<c:when test="${heading=='All Orders' }">
									<div class="panel-heading" align="center">All Orders</div>
								</c:when>
								<c:when test="${heading=='Pending' }">
									<div class="panel-heading" align="center">Orders to
										Approve</div>
								</c:when>
								<c:when test="${heading=='Approved' }">
									<div class="panel-heading" align="center">Orders to Ship</div>
								</c:when>
								<c:when test="${heading=='Shipped' }">
									<div class="panel-heading" align="center">Shipped Orders</div>
								</c:when>
								<c:when test="${heading=='Declined' }">
									<div class="panel-heading" align="center">Rejected Orders</div>
								</c:when>
								<c:when test="${heading=='Received' }">
									<div class="panel-heading" align="center">Closed Orders</div>
								</c:when>
							</c:choose>

							<table data-toggle="table" data-url="${orderList}"
								data-show-refresh="true" data-show-toggle="true"
								data-search="true" data-select-item-name="toolbar1"
								data-pagination="true" data-sort-name="dateordered"
								data-sort-order="desc">
								<thead>
									<tr>
										<th data-field="orderno" data-sortable="true">Order No</th>
										<th data-field="dateordered" data-sortable="true">Date
											Ordered</th>
										<th data-field="orderstatus" data-sortable="true">Order
											Status</th>
										<th data-field="customer" data-sortable="true">Customer</th>
										<th data-field="Stock Type" data-sortable="true">Stock
											Type</th>
										<th data-field="orderedby" data-sortable="true">Ordered
											By</th>
									</tr>
								</thead>
								<tbody>
									<!-- Iterating over the list sent from Controller -->
									<c:forEach var="list" items="${orderList}">
										<tr>
											<td><a
												href="orderItemHistory?recordID=<c:out value='${list.recordID}'/>">ORD00${list.recordID}</a></td>
											<td>${list.dateOrdered}</td>
											<td>${list.status}</td>
											<c:if test="${empty list.customer.customerName }">
												<td>N/A</td>
											</c:if>
											<c:if test="${not empty list.customer.customerName }">
												<td>${list.customer.customerName }</td>
											</c:if>
											<td>${list.stockType}</td>
											<td>${list.employee.firstName} ${list.employee.lastName}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<!-- table order -->
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
	<!-- scripts -->
	<c:import url="templates/ticketandorderselectdate.jsp"></c:import>
	<script type="text/javascript">
	  //covert a date and store a variable from server
	  function CovertDateToString() {
	  	
	  	var date = "${newDate}";
	  	var myDate = new Date();
	  	var selectDate = myDate.toDateString();
	  	document.getElementsByName('selectDateRange')[0].value = selectDate;
	  	selectDate = date;
	  	document.getElementById('selectDateRange').value = date;
	  	console.log("Set Date to Select Date: ", date);
	  	console.log("Set Date to Selected Date: ", date);
	  }
	  // selectDateRange
	  $(function() {
	
	  	$('input[name="selectDateRange"]').daterangepicker({
	  		//startDate: moment().subtract(6, 'days')		       
	  		locale : {
	  			format : 'YYYY-MM-DD'
	  		}
	  	});
	  });
    </script>
	<c:import url="templates/sidebar-collapse.jsp"></c:import>
	<!-- /Scripts -->
	<!-- /Scripts -->
</body>
</html>