<%@include file="templates/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>View Device History Details | Velaphanda Trading &
	Projects</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/custom/css/vela_details.css" />" />
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
					<div class="panel-heading" align="center">Device History</div>
					<div class="panel-body">
						<div align="right">
							<a target="_blank"
								href="deviceHistoryDownloadPDF?recordID=<c:out value='${ticket.recordID}'/>">Download
								PDF </a>
						</div>
						<div class="col-sm-4">
							<b>Serial Number: </b>${ticket.device.serialNumber}</div>
						<div class="col-sm-4">
							<b>Model Number: </b>${ticket.device.modelNumber}</div>
						<div class="col-sm-4">
							<b>Customer Name: </b>${ticket.device.customerDevice.customerName}</div>
							
								<div class="col-sm-6"><br/>						
									<legend style="font-size: 14px; line-height: 1.42857143;">
										Contact person</legend>
									<div class="machinedetailsfloatright ">
										<div class="orderDetails">
											<li id="contactName">First & Last Name:<b>${ticket.firstName} ${ticket.lastName}</b></li>
										    <li id="cell">Cell No: ${ticket.contactCellNumber}</li>
										    <li id="telephone">Telephone No: ${ticket.contactTelephoneNumber}</li>
											<li id="email">E-Mail: ${ticket.contactEmail}</li>
										</div>
										<br>
									</div>
								</div>								
								<div class="col-sm-6"><br/>						
									<legend style="font-size: 14px; line-height: 1.42857143;">
										Problem Description</legend>
									<div class="machinedetailsfloatright ">
										${ticket.description}
									<br>
									</div>
								</div>
						<br />
						<br />	<br />
						<br />	<br />
						<br />
						<!-- table tckHistory -->
						<table id="deviceHistory" data-toggle="table"
							data-url="deviceHistoryList" data-show-refresh="true"
							data-show-toggle="true" data-search="true"
							data-select-item-name="toolbar1" data-pagination="true"
							data-sort-name="date" data-sort-order="desc">
							<thead>
								<tr>
									<th data-field="ticketnumber" data-sortable="true">Ticket
										Number</th>
									<th data-field="date" data-sortable="true">Date</th>
									<th data-field="status" data-sortable="true">Status</th>
									<th data-field="actiontaken" data-sortable="true">Action
										Taken</th>
									<th data-field="assignedto" data-sortable="true">Assigned
										To</th>									
									<th data-field="colourreading" data-sortable="true">Colour
										Reading</th>
									<th data-field="monoreading" data-sortable="true">Mono
										Reading</th>
									<!-- <th data-field="description" data-sortable="true">Description</th>  -->
									<th data-field="comments" data-sortable="true">Comments</th>
								</tr>
							</thead>

							<tbody>
								<!-- Iterating over the list sent from Controller -->
								<c:forEach items="${ticketHistory}" var="ticket">
									<tr>
										<td>VTC000<c:out value="${ticket.ticketNo}" /></td>
										<td><c:out value="${ticket.escalatedDate}" /></td>
										<td><c:out value="${ticket.status}" /></td>
										<c:choose>
											<c:when test="${ticket.status =='Open'}">
												<td><c:out value="${ticket.actionTaken}" /> Log
													Ticket</td>
											</c:when>
											<c:when test="${ticket.status =='Awaiting Spares'}">
												<td>Waiting for Order: <c:out
														value="${orders.recordID}" /></td>
											</c:when>
											<c:when test="${ticket.status =='Escalated'}">
												<td>Ticket Escalated to Manager</td>
											</c:when>
											<c:when test="${ticket.status =='SLA Bridged'}">
												<td><c:out value="${ticket.actionTaken}" /> System
													update</td>
											</c:when>
											<c:when test="${ticket.status =='Re-Open'}">
												<td>Ticket Re-Opened</td>
											</c:when>
											<c:when test="${ticket.status =='Re-assigned'}">
												<td>Ticket Re-assigned</td>
											</c:when>
											<c:when test="${ticket.status =='Acknowledged'}">
												<td><c:out value="${ticket.actionTaken}" /> Ticket
													Acknowledged</td>
											</c:when>
											<c:when test="${ticket.status =='Taken'}">
												<td>Ticket Taken</td>
											</c:when>
											<c:otherwise>
												<td><c:out value="${ticket.actionTaken}" /></td>
											</c:otherwise>
										</c:choose>
										<td><c:out value="${ticket.employee.firstName} ${ticket.employee.lastName}" /></td>
										<td><c:out value="${ticket.colourReading }" /></td>
										<td><c:out value="${ticket.monoReading }" /></td>
										<%-- <td><c:out value="${ticket.description}" /></td> --%>
										<td><c:out value="${ticket.comment}" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<!--// ticket history details -->
						<!-- .panel-body -->
					</div>
					<!-- .panel panel-default -->
				</div>
				<!-- /.col-->
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
