<%@ page import="java.util.*"%>
<%@include file="templates/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ticket Management</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<c:import url="templates/tableresizefont.jsp"></c:import>
<c:import url="templates/stylesheetlib.jsp"></c:import>
<c:import url="templates/topviewtickets.jsp"></c:import>
</head>
<body onload="CovertDateToString()">

	<c:import url="templates/usernavbar.jsp"></c:import>

	<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
		<div class="row">
			<ol class="breadcrumb">
				<li><a href="userdashboard.html"><svg
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

		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading" align="center">Tickets</div>
					<div class="panel-body">
						
						<button type="button" id="logTicket" class="btn btn-success"
							name="logTicket" value="Create Ticket"
							onclick="window.location.href='ticket.html'">Create
							Ticket</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button" id="replaceToner" class="btn btn-success"
							name="replaceToner" value="Replace Toner"
							onclick="window.location.href='tonerReplacementUser.html'">Replace Toner</button> 
						<br /> <br />
						
						<form:form action="searchTicket" method="post" id="searchTicket"
							class="searchTicket" modelAttribute="searchTicket">

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
													<option value="${selectedName}">${ selectedName}</option>
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
												<option value="All Technicians" />All Technicians</option>
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
												<input name="ticketNumber" list="ticketNumbers"
													class="form-control" type="text"
													onkeydown="upperCaseF(this)"
													placeholder='Enter Ticket Number' /> <span
													class="input-group-btn">
													<button class="btn btn-success" type="submit">
														<div class="up" style="margin-top: -8%; color: white;">Search</div>
													</button>
												</span>
											</div>
											<!-- /input-group -->
										</div>

										<!-- Iterating over the list sent from Controller -->
										<datalist id="ticketNumbers"> <c:forEach var="list"
											items="${ticketNumbers}">
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
											<a href='<c:url value="openTickets"/>'>
												<div class="well" style="background-color: #ffffff;">
													<h5 class="text-danger">
														<span class="label label-danger pull-right">${countOpenTickets}</span>
														Open
													</h5>
												</div>
											</a>
										</div>
										<div class="col-xs-6 col-md-3">
											<a href='<c:url value="acknowledgedTickets"/>'>
												<div class="well" style="background-color: #ffffff;">
													<h5 class="text-success">
														<span class="label label-success pull-right">${countAcknowledgedTickets}</span>
														Acknowledged
													</h5>
												</div>
											</a>
										</div>
										<div class="col-xs-6 col-md-3">
											<a href='<c:url value="takenTickets"/>'>
												<div class="well" style="background-color: #ffffff;">
													<h5 class="text-primary">
														<span class="label label-primary pull-right">${countTakenTickets}</span>
														Taken
													</h5>
												</div>
											</a>
										</div>
										<div class="col-xs-6 col-md-3">
											<a href='<c:url value="ticketsAwaitingSpares"/>'>
												<div class="well" style="background-color: #ffffff;">
													<h5 class="text-danger">
														<span class="label label-danger pull-right">${countAwaitingSparesTickets}</span>
														Awaiting Spares
													</h5>
												</div>
											</a>
										</div>
									</div>
									<!--/row-->
									<div class="row">

										<div class="col-xs-6 col-md-3">
											<a href='<c:url value="escalatedTickets"/>'>
												<div class="well" style="background-color: #ffffff;">
													<h5 class="text-success">
														<span class="label label-success pull-right">${countEscalatedTickets}</span>
														Escalated
													</h5>
												</div>
											</a>
										</div>
										<div class="col-xs-6 col-md-3">
											<a href='<c:url value="sLABridgedTickets"/>'>
												<div class="well" style="background-color: #ffffff;">
													<h5 class="text-danger">
														<span class="label label-danger pull-right">${countBridgedTickets}</span>
														SLA Bridged
													</h5>
												</div>
											</a>
										</div>
										<div class="col-xs-6 col-md-3">
											<a href='<c:url value="resolvedTickets"/>'>
												<div class="well" style="background-color: #ffffff;">
													<h5 class="text-success">
														<span class="label label-success pull-right">${countResolvedTickets}</span>
														Resolved
													</h5>
												</div>
											</a>
										</div>
										<div class="col-xs-6 col-md-3">
											<a href='<c:url value="closedTickets"/>'>
												<div class="well" style="background-color: #ffffff;">
													<h5 class="text-success">
														<span class="label label-success pull-right">${countClosedTickets}</span>
														Closed
													</h5>
												</div>
											</a>
										</div>
									</div>
									<!--/row-->
								</div>
								<!--/col-12-->
							</ul>
							<!--/tab nav-->
						</div>
						<!--/row-->


						<div class="tab-content">

							<c:choose>
								<c:when test="${heading=='Open' }">
									<div class="panel-heading" align="center">Open Tickets</div>
								</c:when>
								<c:when test="${heading=='Acknowledged' }">
									<div class="panel-heading" align="center">Acknowledged
										Tickets</div>
								</c:when>
								<c:when test="${heading=='Taken' }">
									<div class="panel-heading" align="center">Taken Tickets</div>
								</c:when>
								<c:when test="${heading=='Awaiting Spares' }">
									<div class="panel-heading" align="center">Awaiting Spares
										Tickets</div>
								</c:when>
								<c:when test="${heading=='Escalated' }">
									<div class="panel-heading" align="center">Escalated
										Tickets</div>
								</c:when>
								<c:when test="${heading=='SLA Bridged' }">
									<div class="panel-heading" align="center">SLA Bridged
										Tickets</div>
								</c:when>
								<c:when test="${heading=='Resolved' }">
									<div class="panel-heading" align="center">Resolved
										Tickets</div>
								</c:when>
								<c:when test="${heading=='Closed' }">
									<div class="panel-heading" align="center">Closed Tickets</div>

								</c:when>
								<c:when test="${heading=='All Tickets' }">
									<div class="panel-heading" align="center">All Tickets</div>
								</c:when>

							</c:choose>
							<table data-toggle="table" data-url="${lastForteenList}"
								data-show-refresh="true" data-show-toggle="true"
								data-search="true" data-select-item-name="toolbar1"
								data-pagination="true" data-sort-name="ticketdate"
								data-sort-order="desc">
								<thead>
									<tr>
										<th data-field="ticketnumber" data-sortable="true">Ticket
											Number</th>
										<th data-field="ticketdate" data-sortable="true">Ticket
											Date</th>
										<th data-field="status" data-sortable="true">Status</th>
										<th data-field="description" data-sortable="true">Description</th>
										<th data-field="customername" data-sortable="true">Customer
											Name</th>
										<th data-field="serialnumber" data-sortable="true">Serial
											Number</th>
										<th data-field="model" data-sortable="true">Model</th>
										<th data-field="assignedTo" data-sortable="true">Assigned
											To</th>
									</tr>
								</thead>

								<tbody>
									<!-- Iterating over the list sent from Controller -->
									<c:forEach var="list" items="${lastForteenList}">
										<tr>
											<td><a
												href="ticketItemDetailsU?recordID=<c:out value='${list.recordID}'/>">VTC000${list.recordID}</a></td>
											<td>${list.dateTime}</td>
											<td>${list.status}</td>
											<td>${list.description}</td>
											<td>${list.device.customerDevice.customerName}</td>
											<td>${list.device.serialNumber}</td>
											<td>${list.device.modelNumber}</td>
											<td>${list.employee.firstName} ${list.employee.lastName}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<!-- table ticket -->

						</div>
						<!-- /tab-content -->

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
</body>
</html>

