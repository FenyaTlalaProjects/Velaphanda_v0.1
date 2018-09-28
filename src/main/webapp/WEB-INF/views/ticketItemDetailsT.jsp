<%@include file="templates/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Ticket Details | Velaphanda Trading & Projects</title>
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
<body onload="CovertDateToString()">

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
						<b>Ticket No : VTC000${ticketObject.recordID}</b>
					</div>
					<div class="panel-body">

						<!-- Hidden Mono Reading  -->
						<input type="hidden" id="availableMonoReading"
							name="availableMonoReading" class="form-control selectpicker"
							value="${ticketObject.getDevice().getMonoReading()}">
						<!-- Hidden Colour Reading  -->
						<input type="hidden" id="availableColourReading"
							name="availableColourReading" class="form-control selectpicker"
							value="${ticketObject.getDevice().getColourReading()}">

						<div id="navbar" class="navbar-collapse collapse"
							style="margin-left: -2%; display:block;">
							<ul class="nav navbar-nav navbar-left">

								<c:choose>

									<c:when
										test="${ticketObject.status =='Open' || ticketObject.status == 'Re-Open'}">
										<li class="dropdown"><a href="#" class="dropdown-toggle"
											data-toggle="dropdown" role="button" aria-haspopup="true"
											aria-expanded="false">Ticket Action<span class="caret"></span></a>
											<ul class="dropdown-menu">
												<li><a id="tAcknowledgedTicketLink" href="javascript:;"
													data-toggle="tab">Acknowledge</a></li>
											</ul></li>
									</c:when>

									<c:when test="${ticketObject.status =='Acknowledged'}">
										<li class="dropdown"><a href="#" class="dropdown-toggle"
											data-toggle="dropdown" role="button" aria-haspopup="true"
											aria-expanded="false">Ticket Action<span class="caret"></span></a>
											<ul class="dropdown-menu">
												<li><a id="tTakeTicketLink" href="javascript:;"
													data-toggle="tab">Take Ticket</a></li>
											</ul></li>
									</c:when>

									<c:when test="${ticketObject.status =='Taken'}">
										<li class="dropdown"><a href="#" class="dropdown-toggle"
											data-toggle="dropdown" role="button" aria-haspopup="true"
											aria-expanded="false">Ticket Action<span class="caret"></span></a>
											<ul class="dropdown-menu">
												<li><a id="tTicketTakenAwaitingSparesLink"
													href="javascript:;" data-toggle="tab">Awaiting Spares</a></li>
												<li><a id="tTicketTakenEscalateLink"
													href="javascript:;" data-toggle="tab">Escalate</a></li>
												<li><a id="tTicketTakenResolveLink" href="javascript:;"
													data-toggle="tab">Resolve</a></li>
											</ul></li>
									</c:when>

									<c:when test="${ticketObject.status =='Awaiting Spares'}">
										<li class="dropdown"><a href="#" class="dropdown-toggle"
											data-toggle="dropdown" role="button" aria-haspopup="true"
											aria-expanded="false">Ticket Action<span class="caret"></span></a>
											<ul class="dropdown-menu">
												<li><a id="tTicketAwaitingSparesEscalateLink"
													href="javascript:;" data-toggle="tab">Escalate</a></li>
												<li><a id="tTicketAwaitingSparesResolveLink"
													href="javascript:;" data-toggle="tab">Resolve</a></li>

											</ul></li>
									</c:when>

									<c:when test="${ticketObject.status =='SLA Bridged'}">
										<li class="dropdown"><a href="#" class="dropdown-toggle"
											data-toggle="dropdown" role="button" aria-haspopup="true"
											aria-expanded="false">Ticket Action<span class="caret"></span></a>
											<ul class="dropdown-menu">
												<li><a id="tTicketSLABridgedEscalateLink"
													href="javascript:;" data-toggle="tab">Escalate</a></li>
												<li><a id="tTicketSLABridgedResolvedLink"
													href="javascript:;" data-toggle="tab">Resolve</a></li>

											</ul></li>
									</c:when>

								</c:choose>
								<li><a target="_blank"
									href="ticketDownloadPDF?recordID=<c:out value='${ticketObject.recordID}' />">Download
										PDF </a></li>
							</ul>
						</div>
						<legend></legend>
						<ul class="nav nav-tabs">

							<li class="active"><a href="#ticketDetails"
								data-toggle="tab">Ticket Details</a></li>
							<li><a href="#ticketHistoryDetails" data-toggle="tab">Ticket
									History</a></li>

							<c:choose>

								<c:when
									test="${ticketObject.status =='Open' || ticketObject.status == 'Re-Open'}">
									<li class="tAcknowledgedTicket" style="display: none;"><a
										href="#tAcknowledgedTicket" data-toggle="tab">Acknowledged</a>
								</c:when>

								<c:when test="${ticketObject.status =='Acknowledged'}">
									<li class="tTakeTicket" style="display: none;"><a
										href="#tTakeTicket" data-toggle="tab">Take Ticket</a>
								</c:when>

								<c:when test="${ticketObject.status =='Taken'}">
									<li class="tTicketTakenAwaitingSpares" style="display: none;"><a
										href="#tTicketTakenAwaitingSpares" data-toggle="tab">Awaiting
											Spares</a></li>
									<li class="tTicketTakenEscalate" style="display: none;"><a
										href="#tTicketTakenEscalate" data-toggle="tab">Escalate</a></li>
									<li class="tTicketTakenResolve" style="display: none;"><a
										href="#tTicketTakenResolve" data-toggle="tab">Resolve</a></li>
								</c:when>

								<c:when test="${ticketObject.status == 'Awaiting Spares' }">
									<li class="tTicketAwaitingSparesResolve" style="display: none;"><a
										href="#tTicketAwaitingSparesResolve" data-toggle="tab">Resolve</a></li>
									<li class="tTicketAwaitingSparesEscalate"
										style="display: none;"><a
										href="#tTicketAwaitingSparesEscalate" data-toggle="tab">Escalate</a></li>
								</c:when>

								<c:when test="${ticketObject.status =='SLA Bridged'}">
									<li class="tTicketSLABridgedResolved" style="display: none;"><a
										href="#tTicketSLABridgedResolved" data-toggle="tab">Resolve</a></li>
									<li class="tTicketSLABridgedEscalate" style="display: none;"><a
										href="#tTicketSLABridgedEscalate" data-toggle="tab">Escalate</a>
								</c:when>

								<c:when test="${ticketObject.status =='Resolved'}">
									<li><a href="#tTicketReopenResolved" data-toggle="tab">Resolved
											Ticket Details</a></li>
								</c:when>

								<c:when test="${ticketObject.status =='Closed'}">
									<li><a href="#tTicketClosedNoAction" data-toggle="tab">Closed
											Ticket Details</a></li>
								</c:when>
							</c:choose>

						</ul>

						<div class="tab-content">

							<!--#ticketDetails tab-->
							<div class="tab-pane active" id="ticketDetails">
								<br />

								<div class="col-sm-4">
									<legend style="font-size: 18px; line-height: 1.42857143;">Device</legend>

									<label id="serialNumber" name="serialNumber"><b>Serial
											Number: ${ticketObject.device.serialNumber} </b></label> <br>
									<li id="modelNumber" name="modelNumber">Model:
										${ticketObject.device.modelNumber}</li>
									<li id="brand" name="brand">Device Brand:
										${ticketObject.device.modelBrand}</li> <br> <label
										id="location" name=""location""><b>Location</b></label>
									<li id="building" name="building">Floor
										${ticketObject.device.floorNumber}
										${ticketObject.device.buildingName}</li>
									<li id="street" name="street">${ticketObject.device.streetNumber}
										${ticketObject.device.streetName}</li>
									<li id="city" name="city">${ticketObject.device.city_town}</li>
									<li id="province" name=""province"">${ticketObject.device.province}</li>
									<li id="areaCode" name="areaCode">${ticketObject.device.areaCode}</li>


								</div>

								<div class="col-sm-4">
									<legend style="font-size: 18px; line-height: 1.42857143;">Ticket
										Contacts</legend>

									<li id="contactName"><b>${ticketObject.firstName}
											${ticketObject.lastName}</b></li>
									<li id="cell">Cell No: ${ticketObject.contactCellNumber}</li>
									<li id="telephone">Telephone No:
										${ticketObject.contactTelephoneNumber}</li>
									<li id="email">E-Mail: ${ticketObject.contactEmail}</li> <br>
								</div>

								<div class="col-sm-4">
									<legend style="font-size: 18px; line-height: 1.42857143;">Ticket</legend>
									<div class="machinedetailsfloatright ">
										<div class="orderDetails">
											<li style="font-size: 15px;" id="ticketNum"><b>VTC000${ticketObject.recordID}</b></li>
											<li id="customer">Customer:
												${ticketObject.device.customerDevice.customerName}</li>
											<li id=tcketStatus>Status: ${ticketObject.status}</li>
											<li id=ticketDate>Date: ${ticketObject.dateTime}</li>
											<li id=assignedTo>Assigned To:
												${ticketObject.employee.firstName}
												${ticketObject.employee.lastName}</li> <br>
											<li id="ticket_Description"><b>Description</b></li>
											<li id="ticketDescription">${ticketObject.description}</li>

										</div>
										<br>

									</div>

								</div>



								<div class="col-sm-12">
									<br /> <br />
									<legend style="font-size: 18px; line-height: 1.42857143;">Ticket
										Info</legend>
									<table data-toggle="table" data-url="${displayCustomers}"
										data-show-refresh="true" data-show-toggle="true"
										data-search="true" data-select-item-name="toolbar1"
										data-pagination="true" data-sort-name="ticketnumber"
										data-sort-order="desc">
										<thead>
											<tr>
												<th data-field="ticketnumber" data-sortable="true">Ticket
													Number</th>
												<th data-field="status" data-sortable="true">Status</th>
												<th data-field="priority" data-sortable="true">Priority</th>
												<th data-field="technicianemail" data-sortable="true">Technician
													Email</th>
												<th data-field="comments" data-sortable="true">Comments</th>
											</tr>
										</thead>
										<tbody>
											<!-- Iterating over the list sent from Controller -->
											<tr>
												<td>VTC000${ticketObject.recordID}</td>
												<td>${ticketObject.status}</td>
												<td>${ticketObject.priority}</td>
												<td>${ticketObject.employee.email}</td>
												<td>${ticketObject.comments}</td>
											</tr>
										</tbody>
									</table>
									<!-- //Table ticket Info -->
								</div>

							</div>

							<!--Ticket History Details tab-->
							<div class="tab-pane" id="ticketHistoryDetails">
								<div class="groupdetails-row-padding">
									<div class="groupclientdetails">
										<br />
										<legend style="font-size: 18px; line-height: 1.42857143;"
											align="center">
											Ticket History 
										</legend>
										<form:form class="well form-horizontal">
											<div class="panel-body">
												<!-- table tckHistory -->
												<table id="tckHistory" data-toggle="table" data-url=""
													data-show-refresh="true" data-show-toggle="true"
													data-search="true" data-select-item-name="toolbar1"
													data-pagination="true" data-sort-name="date"
													data-sort-order="desc">
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
															<th data-field="comments" data-sortable="true">Comments</th>
														</tr>
													</thead>

													<tbody>
														<!-- Iterating over the list sent from Controller -->
														<c:forEach items="${ticketHistoryList}" var="history">
															<tr>
																<td><c:out value="VTC000${history.ticketNo}" /></td>
																<td><c:out value="${history.escalatedDate}" /></td>
																<td><c:out value="${history.status}" /></td>
																<c:choose>
																	<c:when test="${history.status =='Open'}">
																		<td><c:out value="${history.actionTaken}" /> Log
																			Ticket</td>
																	</c:when>
																	<c:when test="${history.status =='Awaiting Spares'}">
																		<td>Waiting for Order: <c:out
																				value="${orders.recordID}" /></td>
																	</c:when>
																	<c:when test="${history.status =='Escalated'}">
																		<td>Ticket Escalated to Manager</td>
																	</c:when>
																	<c:when test="${history.status =='SLA Bridged'}">
																		<td><c:out value="${history.actionTaken}" />
																			System update</td>
																	</c:when>
																	<c:when test="${history.status =='Re-Open'}">
																		<td>Ticket Re-Opened</td>
																	</c:when>
																	<c:when test="${history.status =='Re-assigned'}">
																		<td>Ticket Re-assigned</td>
																	</c:when>
																	<c:when test="${history.status =='Acknowledged'}">
																		<td><c:out value="${history.actionTaken}" />
																			Ticket Acknowledged</td>
																	</c:when>
																	<c:when test="${history.status =='Taken'}">
																		<td>Ticket Taken</td>
																	</c:when>
																	<c:otherwise>
																		<td><c:out value="${history.actionTaken}" /></td>
																	</c:otherwise>
																</c:choose>
																<td><c:out
																		value="${history.employee.firstName} ${history.employee.lastName}" /></td>
																<td><c:out value="${history.colourReading }" /></td>
																<td><c:out value="${history.monoReading }" /></td>
																<td><c:out value="${history.comment}" /></td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
												<!--// ticket history details -->

											</div>
											<!--// panel body -->
										</form:form>
									</div>
									<!--// group ticket details -->
								</div>
							</div>
							<!-- 	//Ticket History Details tab -->
							

							<!--Acknowledged Ticket when is open-->
							<c:choose>
								<c:when
									test="${ticketObject.status =='Open' || ticketObject.status == 'Re-Open'}">


									<div class="tab-pane" id="tAcknowledgedTicket">

										<div class="panel-body">

											<!-- tTicketTakenAwaitingSpares Details -->
											<form:form action="acknowledgedTicket"
												modelAttribute="performTicketAction" method="post"
												id="updateResolved" class="well form-horizontal">

												<legend style="font-size: 15px; line-height: 1.42857143;"
													align="center">
													<b>Acknowledge</b>
												</legend>

												<!-- Action Action -->
												<input type="hidden" id="ticketAction" name="ticketAction"
													class="form-control selectpicker" value="Acknowledge">


												<!-- Text input Ticket Number-->
												<div class="form-group">
													<label class="col-md-3 control-label">Ticket Number</label>
													<div class="col-md-6 inputGroupContainer">
														<div class="input-group">
															<span class="input-group-addon"><i
																class="glyphicon glyphicon-barcode"></i></span> <input
																name="ticketNumber" id="ticketNumber"
																class="form-control" type="text"
																value="VTC000${ticketObject.recordID}" readonly>
														</div>
													</div>
												</div>
												<!-- Text input Ticket Number-->
												<div class="form-group" style="display: none">
													<div class="col-md-6 inputGroupContainer">
														<div class="input-group">
															<span class="input-group-addon"><i
																class="glyphicon glyphicon-barcode"></i></span> <input
																name="recordID" id="ticketNumber" class="form-control"
																type="text" value="${ticketObject.recordID}">
														</div>
													</div>
												</div>
												<div class="form-group row">
													<div class="col-sm col-sm-8"
														style="margin-left: 26%; width: 48%;">
														<input type="submit" name=resolve
															value="Acknowledge Ticket"
															class="btn btn-primary btn-block btn-lg" tabindex="9"
															id="resolve">
													</div>
												</div>

											</form:form>

										</div>

									</div>

								</c:when>
							</c:choose>


							<!--Take ticket when is  Acknowledged-->
							<c:choose>
								<c:when test="${ticketObject.status =='Acknowledged'}">
									<div class="tab-pane" id="tTakeTicket">
										<div class="panel-body">

											<!-- tTicketTakenAwaitingSpares Details -->
											<form:form action="taketicket"
												modelAttribute="performTicketAction" method="post"
												id="updateResolved" class="well form-horizontal">

												<legend style="font-size: 15px; line-height: 1.42857143;"
													align="center">
													<b>Take Ticket</b>
												</legend>

												<!-- Ticket Action -->
												<input type="hidden" id="ticketAction" name="ticketAction"
													class="form-control selectpicker" value="taketicket">


												<!-- Text input Ticket Number-->
												<div class="form-group">
													<label class="col-md-3 control-label">Ticket Number</label>
													<div class="col-md-6 inputGroupContainer">
														<div class="input-group">
															<span class="input-group-addon"><i
																class="glyphicon glyphicon-barcode"></i></span> <input
																name="ticketNumber" id="ticketNumber"
																class="form-control" type="text"
																value="VTC000${ticketObject.recordID}" readonly>
														</div>
													</div>
												</div>
												<!-- Text input Ticket Number-->
												<div class="form-group" style="display: none">
													<div class="col-md-6 inputGroupContainer">
														<div class="input-group">
															<span class="input-group-addon"><i
																class="glyphicon glyphicon-barcode"></i></span> <input
																name="recordID" id="ticketNumber" class="form-control"
																type="text" value="${ticketObject.recordID}">
														</div>
													</div>
												</div>
												<div class="form-group row">
													<div class="col-sm col-sm-8"
														style="margin-left: 26%; width: 48%;">
														<input type="submit" name=resolve value="Take Ticket"
															class="btn btn-primary btn-block btn-lg" tabindex="9"
															id="resolve">
													</div>
												</div>

											</form:form>

										</div>

									</div>

								</c:when>
							</c:choose>
							<!--// End Take ticket when is  Acknowledged-->

							<!--Taken and Awaiting spares -->
							<c:choose>
								<c:when test="${ticketObject.status =='Taken'}">

									<div class="tab-pane" id="tTicketTakenAwaitingSpares">

										<div class="panel-body">

											<!-- tTicketTakenAwaitingSpares Details -->
											<form:form action="performTicketAction"
												modelAttribute="performTicketAction" method="post"
												id="updateResolved" class="well form-horizontal">

												<legend style="font-size: 15px; line-height: 1.42857143;"
													align="center">
													<b>Awaiting Spares</b>
												</legend>

												<!-- Action Action -->
												<input type="hidden" id="ticketAction" name="ticketAction"
													class="form-control selectpicker" value="awaitingspares">

												<!-- Text input Ticket Number-->
												<div class="form-group">
													<label class="col-md-3 control-label">Ticket Number</label>
													<div class="col-md-6 inputGroupContainer">
														<div class="input-group">
															<span class="input-group-addon"><i
																class="glyphicon glyphicon-barcode"></i></span> <input
																name="ticketNumber" id="ticketNumber"
																class="form-control" type="text"
																value="VTC000${ticketObject.recordID}" readonly>
														</div>
													</div>
												</div>

												<!-- Text input Order No-->
												<div class="form-group">
													<label class="col-md-3 control-label"> Order No</label>
													<div class="col-md-6 inputGroupContainer">
														<div class="input-group">
															<span class="input-group-addon"><i
																class="glyphicon glyphicon-user"></i></span> <select
																id="orderNum" name="orderNum"
																class="form-control selectpicker">
																<option value="">Select Order No</option>
																<c:forEach items="${OrderNumber}" var="orders">
																	<option value="${orders.recordID}">ORD00${orders.recordID}
																	</option>
																</c:forEach>

															</select>
														</div>
													</div>
												</div>

												<div class="form-group row">
													<div class="col-sm col-sm-8"
														style="margin-left: 26%; width: 48%;">
														<input type="submit" name=resolve
															value="Awaiting For Spares"
															class="btn btn-primary btn-block btn-lg" tabindex="9"
															id="resolve">
													</div>
												</div>

											</form:form>

										</div>
									</div>
								</c:when>
							</c:choose>
							<!--// End Taken and Awaiting spares -->

							<!--Taken and Escalate to manager -->
							<c:choose>
								<c:when test="${ticketObject.status =='Taken'}">

									<div class="tab-pane" id="tTicketTakenEscalate">

										<div class="panel-body">

											<!-- If ticket is taken, action available is Escalate -->
											<form:form action="escalateticket"
												modelAttribute="performTicketAction" method="post"
												id="ticketTakenEscalate" class="well form-horizontal">

												<legend style="font-size: 15px; line-height: 1.42857143;"
													align="center">
													<b>Escalate</b>
												</legend>

												<!-- Action Action -->
												<input type="hidden" id="ticketAction" name="ticketAction"
													class="form-control selectpicker" value="escalate">

												<!-- Text input Ticket Number-->
												<div class="form-group">
													<label class="col-md-3 control-label">Ticket Number</label>
													<div class="col-md-6 inputGroupContainer">
														<div class="input-group">
															<span class="input-group-addon"><i
																class="glyphicon glyphicon-barcode"></i></span> <input
																name="ticketNumber" id="ticketNumber"
																class="form-control" type="text"
																value="VTC000${ticketObject.recordID}" readonly>
														</div>
													</div>
												</div>
												<!-- Text input Ticket Number-->
												<div class="form-group" style="display: none">
													<div class="col-md-6 inputGroupContainer">
														<div class="input-group">
															<span class="input-group-addon"><i
																class="glyphicon glyphicon-barcode"></i></span> <input
																name="recordID" id="ticketNumber" class="form-control"
																type="text" value="${ticketObject.recordID}">
														</div>
													</div>
												</div>
												<!-- Text input Manager-->
												<div class="form-group">
													<label class="col-md-3 control-label"> Manager</label>
													<div class="col-md-6 inputGroupContainer">
														<div class="input-group">
															<span class="input-group-addon"><i
																class="glyphicon glyphicon-user"></i></span> <select
																id="escalatedTo" name="escalatedTo"
																class="form-control selectpicker">
																<option value="">Select Manager</option>
																<c:forEach items="${managersList}" var="manager">
																	<option value="${manager.email}">${manager.email}</option>
																</c:forEach>

															</select>
														</div>
													</div>
												</div>

												<!-- display Comments-->

												<div class="form-group">
													<label class="col-md-3 control-label">Comment</label>
													<div class="col-md-6 inputGroupContainer">
														<div class="input-group">
															<span class="input-group-addon"><i
																class="glyphicon glyphicon-edit"></i></span>
															<textarea class="form-control" id="comments"
																name="comments" style="margin: 0px; height: 129px;"
																150"
																	 onkeydown="upperCaseF(this)"
																placeholder="Please enter comment"></textarea>
														</div>
													</div>
												</div>

												<!--// display Comments-->

												<div class="form-group row">
													<div class="col-sm col-sm-8"
														style="margin-left: 26%; width: 48%;">
														<input type="submit" name=resolve value="Escalate Ticket"
															class="btn btn-primary btn-block btn-lg" tabindex="9"
															id="resolve">
													</div>
												</div>

											</form:form>
											<!-- tTicketTakenEscalate Details -->

										</div>
									</div>
								</c:when>
							</c:choose>
							<!--//End Taken and Escalate to manager -->


							<!--Ticket Taken and must be Resolve -->
							<c:choose>
								<c:when test="${ticketObject.status =='Taken'}">

									<div class="tab-pane" id="tTicketTakenResolve">

										<div class="panel-body">

											<!-- If ticket is taken, action available is Resolve -->
											<form:form action="performTicketAction"
												modelAttribute="performTicketAction" method="post"
												id="ticketTakenResolve" class="well form-horizontal">

												<legend style="font-size: 15px; line-height: 1.42857143;"
													align="center">
													<b>Resolve</b>
												</legend>

												<!-- Action Action -->
												<input type="hidden" id="ticketAction" name="ticketAction"
													class="form-control selectpicker" value="Resolve">

												<!-- Text input Ticket Number-->
												<div class="form-group">
													<label class="col-md-3 control-label">Ticket Number</label>
													<div class="col-md-6 inputGroupContainer">
														<div class="input-group">
															<span class="input-group-addon"><i
																class="glyphicon glyphicon-barcode"></i></span> <input
																name="ticketNumber" id="ticketNumber"
																class="form-control" type="text"
																value="VTC000${ticketObject.recordID}" readonly>
														</div>
													</div>
												</div>

												<!-- Text input Ticket Number-->
												<div class="form-group" style="display: none">
													<div class="col-md-6 inputGroupContainer">
														<div class="input-group">
															<span class="input-group-addon"><i
																class="glyphicon glyphicon-barcode"></i></span> <input
																name="recordID" id="ticketNumber" class="form-control"
																type="text" value="${ticketObject.recordID}">
														</div>
													</div>
												</div>
												<!-- Text area Action Taken-->
												<div class="form-group">
													<label class="col-md-3 control-label">Action Taken</label>
													<div class="col-md-6 selectContainer">
														<div class="input-group">
															<span class="input-group-addon"><i
																class="glyphicon glyphicon-list"></i></span><select
																name="actionTaken" id="actionTaken"
																class="form-control selectpicker"
																onchange="Faulty(this.value);">
																<option value="">Please select Action Taken for
																	Repair</option>
																<option value="Replaced Part/Toner">Replaced
																	Part/Toner</option>
																<option value="Cleared Paper Jam">Cleared Paper
																	Jam</option>
																<option value="Installed Drivers">Installed
																	Drivers</option>
																<option value="Configured Drivers">Configured
																	Drivers</option>
																<option value="Configured Printer">Configured
																	Printer</option>
																<option value="User Error">User Error</option>
																<option value="No fault Found">No fault Found</option>
																<option value="Cleaned Mirrors">Cleaned Mirrors</option>
																<option value="Cleaned Laser Unit">Cleaned
																	Laser Unit</option>
																<option value="Cleaned Charge Rollers">Cleaned
																	Charge Rollers</option>
																<option value="Cleaned ADF Class">Cleaned ADF
																	Class</option>
																<option value="Cleaned Rollers">Cleaned Rollers</option>
																<option value="Move Copier To New Location">Move Copier To New Location</option>
																<option value="Firmware Upgrade">Firmware Upgrade</option>														
																<option value="Cleaned Waste Toner Bottle">Cleaned Waste Toner Bottle</option>
																<option value="Replaced Stipples">Replaced Stipples</option>
															
															</select>
														</div>
													</div>
												</div>

												<div class="hideMonoAndColour" id="hideMonoAndColour"
													style="display: none">

													<c:if test="${not empty ticketObject.device.colourReading}">
														<!-- Text checkbox Colour Reading-->
														<div class="form-group">
															<label class="col-md-3 control-label">Colour
																Reading</label>
															<div class="col-md-6 inputGroupContainer">
																<div class="input-group">
																	<span class="input-group-addon"><i
																		class="glyphicon glyphicon-list"></i></span> <input
																		type="text" class="form-control"
																		onkeypress="return isNumber(event)"
																		placeholder="Enter Colour Reading" id="colourReading"
																		name="colourReading"
																		onblur="compareColourReading(this, ${ticketObject.device.colourReading})">
																</div>
															</div>
														</div>
													</c:if>
													<div class="form-group">
														<label class="col-md-3 control-label">Mono Reading</label>
														<div class="col-md-6 inputGroupContainer">
															<div class="input-group">
																<span class="input-group-addon"><i
																	class="glyphicon glyphicon-list"></i></span> <input
																	type="text" class="form-control"
																	onkeypress="return isNumber(event)" id="mono"
																	name="monoReading" placeholder="Enter Mono Reading"
																	onblur="compareMonoReading(this, ${ticketObject.device.monoReading})">
															</div>
														</div>
													</div>

												</div>
												<!-- HideMonoAndColour if no action is selscted -->

												<!-- group Used Part Numbers -->
												<div class="groupsearchdetails">

													<legend id="hideUsedPartNumbersHidding"
														style="display: none; width: 50%; margin-left: 25%;">Used
														Part Numbers </legend>

													<div class="hideIfIsNotPartToner" id="hideIfIsNotPartToner"
														style="display: none">

														<fieldset id="groupstock" style="margin-left: 0%">

															<!-- group Boot Stock -->
															<div class="form-group">
																<label class="col-xs-3 control-label">Boot Stock</label>
																<div class="col-md-6 inputGroupContainer">
																	<input type="radio" name="groupboot" required
																		id="checkBootStock" onclick="BootStockChecked()"
																		value="Boot Stock"
																		tittle="You must check Boot Stock or Site Stock to get Used Part Numbers">
																</div>
															</div>
															<div class="displayNone" id="bootStockItems"
																style="margin-left: 25.5%; margin-right: 26%;">
																<table id="bStock" data-toggle="table"
																	data-url="${bootStock}" data-show-refresh="true"
																	data-show-toggle="true" data-search="true"
																	data-select-item-name="toolbar1" data-pagination="true"
																	data-sort-name="partno" data-sort-order="desc">
																	<thead>
																		<tr>
																			<th data-field="partno" data-sortable="true">Part
																				No</th>
																			<th data-field="description" data-sortable="true">Description</th>
																			<th data-field="itemtype" data-sortable="true">Item
																				Type</th>
																			<th data-field="quantity" data-sortable="true">Quantity</th>
																			<th data-field="assignedto" data-sortable="true">Tick
																				Used Parts</th>

																		</tr>
																	</thead>
																	<tbody>
																		<!-- Iterating over the list sent from Controller -->
																		<c:forEach var="list" items="${bootStock}">

																			<tr>
																				<td>${list.partNumber}</td>
																				<td>${list.itemDescription}</td>
																				<td>${list.itemType}</td>
																				<td>${list.quantity}</td>
																				<td><input type="checkbox"
																					id="${list.partNumber}_selectedItem"
																					name="selectedItem"
																					onClick="checkUsedPartNumbers();"
																					value="${list.partNumber}"></td>
																			</tr>

																		</c:forEach>
																	</tbody>
																</table>
															</div>
															<input type="hidden" class="form-control"
																id="bootStockType" name="bootStockType"
																value="Boot Stock">

															<!-- group Site Stock -->
															<div class="form-group">
																<label class="col-xs-3 control-label">Site Stock</label>
																<div class="col-md-6 inputGroupContainer">
																	<input type="radio" name="groupboot" required
																		id="checkSiteStock" onclick="SiteStockChecked()"
																		value="Site Stock">
																</div>
															</div>
															<div class="displayNone" id="siteStockItems"
																style="margin-left: 25.5%; margin-right: 26%;">
																<table id="sStock" data-toggle="table"
																	data-url="${siteStock}" data-show-refresh="true"
																	data-show-toggle="true" data-search="true"
																	data-select-item-name="toolbar1" data-pagination="true"
																	data-sort-name="partno" data-sort-order="desc">
																	<thead>
																		<tr>
																			<th data-field="partno" data-sortable="true">Part
																				No</th>
																			<th data-field="description" data-sortable="true">Description</th>
																			<th data-field="modelno" data-sortable="true">Model
																				No</th>
																			<th data-field="quantity" data-sortable="true">Quantity</th>
																			<th data-field="assignedto" data-sortable="true">Tick
																				Used Parts</th>

																		</tr>
																	</thead>
																	<tbody>
																		<!-- Iterating over the list sent from Controller -->
																		<c:forEach var="list" items="${siteStock}">
																			<tr>
																				<td>${list.partNumber}</td>
																				<td>${list.itemDescription}</td>
																				<td>${list.itemType}</td>
																				<td>${list.quantity}</td>
																				<td><input type="checkbox"
																					id="${list.partNumber}_selectedItem"
																					name="selectedItem"
																					onClick="checkUsedPartNumbers();"
																					value="${list.partNumber}"></td>
																			</tr>
																		</c:forEach>
																	</tbody>
																</table>
															</div>
															</br> <input type="hidden" class="form-control"
																id="siteStockType" name="siteStockType"
																value="Site Stock">

														</fieldset>


														<!-- display ticked Used Part Numbers-->
														<div class="form-group">
															<label class="col-md-3 control-label">Used Part
																Numbers</label>
															<div class="col-md-6 inputGroupContainer">
																<div class="input-group">
																	<span class="input-group-addon"><i
																		class="glyphicon glyphicon-list"></i></span> <input
																		id="usedPartNumbers" name="usedPartNumbers"
																		class="form-control" readonly="readonly"
																		style="height: 60px; font-size: 11px;">

																</div>
															</div>
														</div>
														<!--// display ticked Used Part Numbers-->

														<!-- Text area comments-->
														<div class="form-group">
															<label class="col-md-3 control-label">Comment</label>
															<div class="col-md-6 inputGroupContainer">
																<div class="input-group">
																	<span class="input-group-addon"><i
																		class="glyphicon glyphicon-edit"></i></span>
																	<textarea class="form-control" style="height: 120px;"
																		id="comments" name="comments" maxlength="150"
																		onkeydown="upperCaseF(this)"
																		placeholder="Please enter comment"></textarea>
																</div>
															</div>
														</div>
														<!--// text area comments-->


													</div>
													<!-- // end hideIfIsNotPartToner -->

													<!-- hideComent-->
													<div class="hideComent" id="hideComent"
														style="display: none">

														<!-- Text area comments-->
														<div class="form-group">
															<label class="col-md-3 control-label">Comment</label>
															<div class="col-md-6 inputGroupContainer">
																<div class="input-group">
																	<span class="input-group-addon"><i
																		class="glyphicon glyphicon-edit"></i></span>
																	<textarea class="form-control" style="height: 120px;"
																		id="comments" name="comments" maxlength="150"
																		onkeydown="upperCaseF(this)"
																		placeholder="Please enter comment"></textarea>
																</div>
															</div>
														</div>
														<!--// text area comments-->



													</div>
													<!-- //hideComent -->

												</div>
												<!-- // group used part numbers -->

												<div class="actionTakenSubmit" id="actionTakenSubmit"
													style="display: none;">

													<div class="form-group row">
														<div class="col-sm col-sm-8"
															style="margin-left: 26%; width: 48%;">
															<input type="submit" name=resolve value="Resolve Ticket"
																class="btn btn-primary btn-block btn-lg" tabindex="9"
																id="resolve">
														</div>
													</div>

												</div>
												<!-- //actionTakenSubmit -->

											</form:form>
										</div>
									</div>
								</c:when>
							</c:choose>
							<!--Ticket Taken and must be Resolve -->

							<!--tTicketAwaitingSparesEscalate -->
							<c:choose>
								<c:when test="${ticketObject.status =='Awaiting Spares'}">
									<div class="tab-pane" id="tTicketAwaitingSparesEscalate">

										<div class="panel-body">

											<!-- tTicketTakenResolve Details -->
											<form:form action="performTicketAction"
												modelAttribute="performTicketAction" method="post"
												id="updateResolved" class="well form-horizontal">

												<legend style="font-size: 15px; line-height: 1.42857143;"
													align="center">
													<b>Escalate</b>
												</legend>

												<!-- Action Action -->
												<input type="hidden" id="ticketAction" name="ticketAction"
													class="form-control selectpicker" value="Escalate">

												<!-- Text input Ticket Number-->
												<div class="form-group">
													<label class="col-md-3 control-label">Ticket Number</label>
													<div class="col-md-6 inputGroupContainer">
														<div class="input-group">
															<span class="input-group-addon"><i
																class="glyphicon glyphicon-barcode"></i></span> <input
																name="ticketNumber" id="ticketNumber"
																class="form-control" type="text"
																value="VTC000${ticketObject.recordID}" readonly>
														</div>
													</div>
												</div>
												<!-- Text input Ticket Number-->
												<div class="form-group" style="display: none">
													<div class="col-md-6 inputGroupContainer">
														<div class="input-group">
															<span class="input-group-addon"><i
																class="glyphicon glyphicon-barcode"></i></span> <input
																name="recordID" id="ticketNumber" class="form-control"
																type="text" value="${ticketObject.recordID}">
														</div>
													</div>
												</div>
												<!-- Text input Manager-->
												<div class="form-group">
													<label class="col-md-3 control-label"> Manager</label>
													<div class="col-md-6 inputGroupContainer">
														<div class="input-group">
															<span class="input-group-addon"><i
																class="glyphicon glyphicon-user"></i></span> <select
																id="escalatedTo" name="escalatedTo"
																class="form-control selectpicker">
																<option value="">Select Manager</option>
																<c:forEach items="${managersList}" var="manager">
																	<option value="${manager.email}">${manager.email}</option>
																</c:forEach>

															</select>
														</div>
													</div>
												</div>


												<!-- display Comments-->

												<div class="form-group">
													<label class="col-md-3 control-label">Comment</label>
													<div class="col-md-6 inputGroupContainer">
														<div class="input-group">
															<span class="input-group-addon"><i
																class="glyphicon glyphicon-edit"></i></span>
															<textarea class="form-control" id="comments"
																name="comments" maxlength="150"
																onkeydown="upperCaseF(this)" style="height: 120px;"
																placeholder="Please enter comment"></textarea>
														</div>
													</div>
												</div>

												<!--// display Comments-->

												<div class="form-group row">
													<div class="col-sm col-sm-8"
														style="margin-left: 26%; width: 48%;">
														<input type="submit" name=resolve value="Escalate Ticket"
															class="btn btn-primary btn-block btn-lg" tabindex="9"
															id="resolve">
													</div>
												</div>

											</form:form>

										</div>
									</div>
								</c:when>
							</c:choose>
							<!--tTicketAwaitingSparesEscalate -->

							<!--tTicketAwaitingSparesResolve -->
							<c:choose>
								<c:when test="${ticketObject.status =='Awaiting Spares'}">

									<div class="tab-pane" id="tTicketAwaitingSparesResolve">

										<div class="panel-body">

											<!-- tTicketTakenResolve Details -->
											<form:form action="performTicketAction"
												modelAttribute="performTicketAction" method="post"
												id="updateResolved" class="well form-horizontal">

												<legend style="font-size: 15px; line-height: 1.42857143;"
													align="center">
													<b>Resolve</b>
												</legend>

												<!-- Action Action -->
												<input type="hidden" id="ticketAction" name="ticketAction"
													class="form-control selectpicker" value="Resolve">

												<!-- Text input Ticket Number-->
												<div class="form-group">
													<label class="col-md-3 control-label">Ticket Number</label>
													<div class="col-md-6 inputGroupContainer">
														<div class="input-group">
															<span class="input-group-addon"><i
																class="glyphicon glyphicon-barcode"></i></span> <input
																name="ticketNumber" id="ticketNumber"
																class="form-control" type="text"
																value="VTC000${ticketObject.recordID}" readonly>
														</div>
													</div>
												</div>
												<!-- Text input Ticket Number-->
												<div class="form-group" style="display: none">
													<div class="col-md-6 inputGroupContainer">
														<div class="input-group">
															<span class="input-group-addon"><i
																class="glyphicon glyphicon-barcode"></i></span> <input
																name="recordID" id="ticketNumber" class="form-control"
																type="text" value="${ticketObject.recordID}">
														</div>
													</div>
												</div>
												<!-- Text area Action Taken-->
												<div class="form-group">
													<label class="col-md-3 control-label">Action Taken</label>
													<div class="col-md-6 selectContainer">
														<div class="input-group">
															<span class="input-group-addon"><i
																class="glyphicon glyphicon-list"></i></span><select
																name="actionTaken" id="actionTaken"
																class="form-control selectpicker"
																onchange="Faulty(this.value);">
																<option value="">Please select Action Taken for
																	Repair</option>
																<option value="Replaced Part/Toner">Replaced
																	Part/Toner</option>
																<option value="Cleared Paper Jam">Cleared Paper
																	Jam</option>
																<option value="Installed Drivers">Installed
																	Drivers</option>
																<option value="Configured Drivers">Configured
																	Drivers</option>
																<option value="Configured Printer">Configured
																	Printer</option>
																<option value="User Error">User Error</option>
																<option value="No fault Found">No fault Found</option>
																<option value="Cleaned Mirrors">Cleaned Mirrors</option>
																<option value="Cleaned Laser Unit">Cleaned
																	Laser Unit</option>
																<option value="Cleaned Charge Rollers">Cleaned
																	Charge Rollers</option>
																<option value="Cleaned ADF Class">Cleaned ADF
																	Class</option>
																<option value="Cleaned Rollers">Cleaned Rollers</option>
																<option value="Move Copier To New Location">Move Copier To New Location</option>
																<option value="Firmware Upgrade">Firmware Upgrade</option>
																<option value="Cleaned Waste Toner Bottle">Cleaned Waste Toner Bottle</option>
																<option value="Replaced Stipples">Replaced Stipples</option>
															
															</select>
														</div>
													</div>
												</div>

												<div class="hideMonoAndColour" id="hideMonoAndColour"
													style="display: none">

													<c:if test="${not empty ticketObject.device.colourReading}">
														<!-- Text checkbox Colour Reading-->
														<div class="form-group">
															<label class="col-md-3 control-label">Colour
																Reading</label>
															<div class="col-md-6 inputGroupContainer">
																<div class="input-group">
																	<span class="input-group-addon"><i
																		class="glyphicon glyphicon-list"></i></span> <input
																		type="text" class="form-control"
																		onkeypress="return isNumber(event)"
																		placeholder="Enter Colour Reading" id="colourReading"
																		name="colourReading"
																		onblur="compareColourReading(this, ${ticketObject.device.colourReading})">
																</div>
															</div>
														</div>
													</c:if>
													<div class="form-group">
														<label class="col-md-3 control-label">Mono Reading</label>
														<div class="col-md-6 inputGroupContainer">
															<div class="input-group">
																<span class="input-group-addon"><i
																	class="glyphicon glyphicon-list"></i></span> <input
																	type="text" class="form-control"
																	onkeypress="return isNumber(event)" id="mono"
																	name="monoReading" placeholder="Enter Mono Reading"
																	onblur="compareMonoReading(this, ${ticketObject.device.monoReading})">
															</div>
														</div>
													</div>

												</div>
												<!-- HideMonoAndColour if no action is selscted -->

												<!-- group Used Part Numbers -->
												<div class="groupsearchdetails">

													<legend id="hideUsedPartNumbersHidding"
														style="display: none; width: 50%; margin-left: 25%;">Used
														Part Numbers </legend>

													<div class="hideIfIsNotPartToner" id="hideIfIsNotPartToner"
														style="display: none">

														<fieldset id="groupstock" style="margin-left: 0%">

															<!-- group Boot Stock -->
															<div class="form-group">
																<label class="col-xs-3 control-label">Boot Stock</label>
																<div class="col-md-6 inputGroupContainer">
																	<input type="radio" name="groupboot" required
																		id="checkBootStock" onclick="BootStockChecked()"
																		value="Boot Stock"
																		tittle="You must check Boot Stock or Site Stock to get Used Part Numbers">
																</div>
															</div>
															<div class="displayNone" id="bootStockItems"
																style="margin-left: 25.5%; margin-right: 26%;">
																<table id="bStock" data-toggle="table"
																	data-url="${bootStock}" data-show-refresh="true"
																	data-show-toggle="true" data-search="true"
																	data-select-item-name="toolbar1" data-pagination="true"
																	data-sort-name="partno" data-sort-order="desc">
																	<thead>
																		<tr>
																			<th data-field="partno" data-sortable="true">Part
																				No</th>
																			<th data-field="description" data-sortable="true">Description</th>
																			<th data-field="itemtype" data-sortable="true">Item
																				Type</th>
																			<th data-field="quantity" data-sortable="true">Quantity</th>
																			<th data-field="assignedto" data-sortable="true">Tick
																				Used Parts</th>

																		</tr>
																	</thead>
																	<tbody>
																		<!-- Iterating over the list sent from Controller -->
																		<c:forEach var="list" items="${bootStock}">

																			<tr>
																				<td>${list.partNumber}</td>
																				<td>${list.itemDescription}</td>
																				<td>${list.itemType}</td>
																				<td>${list.quantity}</td>
																				<td><input type="checkbox"
																					id="${list.partNumber}_selectedItem"
																					name="selectedItem"
																					onClick="checkUsedPartNumbers();"
																					value="${list.partNumber}"></td>
																			</tr>

																		</c:forEach>
																	</tbody>
																</table>
															</div>
															<input type="hidden" class="form-control"
																id="bootStockType" name="bootStockType"
																value="Boot Stock">

															<!-- group Site Stock -->
															<div class="form-group">
																<label class="col-xs-3 control-label">Site Stock</label>
																<div class="col-md-6 inputGroupContainer">
																	<input type="radio" name="groupboot" required
																		id="checkSiteStock" onclick="SiteStockChecked()"
																		value="Site Stock">
																</div>
															</div>
															<div class="displayNone" id="siteStockItems"
																style="margin-left: 25.5%; margin-right: 26%;">
																<table id="sStock" data-toggle="table"
																	data-url="${siteStock}" data-show-refresh="true"
																	data-show-toggle="true" data-search="true"
																	data-select-item-name="toolbar1" data-pagination="true"
																	data-sort-name="partno" data-sort-order="desc">
																	<thead>
																		<tr>
																			<th data-field="partno" data-sortable="true">Part
																				No</th>
																			<th data-field="description" data-sortable="true">Description</th>
																			<th data-field="modelno" data-sortable="true">Model
																				No</th>
																			<th data-field="quantity" data-sortable="true">Quantity</th>
																			<th data-field="assignedto" data-sortable="true">Tick
																				Used Parts</th>

																		</tr>
																	</thead>
																	<tbody>
																		<!-- Iterating over the list sent from Controller -->
																		<c:forEach var="list" items="${siteStock}">
																			<tr>
																				<td>${list.partNumber}</td>
																				<td>${list.itemDescription}</td>
																				<td>${list.itemType}</td>
																				<td>${list.quantity}</td>
																				<td><input type="checkbox"
																					id="${list.partNumber}_selectedItem"
																					name="selectedItem"
																					onClick="checkUsedPartNumbers();"
																					value="${list.partNumber}"></td>
																			</tr>
																		</c:forEach>
																	</tbody>
																</table>
															</div>
															</br> <input type="hidden" class="form-control"
																id="siteStockType" name="siteStockType"
																value="Site Stock">

														</fieldset>


														<!-- display ticked Used Part Numbers-->
														<div class="form-group">
															<label class="col-md-3 control-label">Used Part
																Numbers</label>
															<div class="col-md-6 inputGroupContainer">
																<div class="input-group">
																	<span class="input-group-addon"><i
																		class="glyphicon glyphicon-list"></i></span> <input
																		id="usedPartNumbers" name="usedPartNumbers"
																		class="form-control" readonly="readonly"
																		style="height: 60px; font-size: 11px;">
																</div>
															</div>
														</div>
														<!--// display ticked Used Part Numbers-->

														<!-- Text area comments-->
														<div class="form-group">
															<label class="col-md-3 control-label">Comment</label>
															<div class="col-md-6 inputGroupContainer">
																<div class="input-group">
																	<span class="input-group-addon"><i
																		class="glyphicon glyphicon-edit"></i></span>
																	<textarea class="form-control" style="height: 120px;"
																		id="comments" name="comments" maxlength="150"
																		onkeydown="upperCaseF(this)"
																		placeholder="Please enter comment"></textarea>
																</div>
															</div>
														</div>
														<!--// text area comments-->

													</div>
													<!-- // end hideIfIsNotPartToner -->

													<!-- hideComent-->
													<div class="hideComent" id="hideComent"
														style="display: none">

														<!-- Text area comments-->
														<div class="form-group">
															<label class="col-md-3 control-label">Comment</label>
															<div class="col-md-6 inputGroupContainer">
																<div class="input-group">
																	<span class="input-group-addon"><i
																		class="glyphicon glyphicon-edit"></i></span>
																	<textarea class="form-control" style="height: 120px;"
																		id="comments" name="comments" maxlength="150"
																		onkeydown="upperCaseF(this)"
																		placeholder="Please enter comment"></textarea>
																</div>
															</div>
														</div>
														<!--// text area comments-->

													</div>
													<!-- //hideComent -->

												</div>
												<!-- // group used part numbers -->

												<div class="actionTakenSubmit" id="actionTakenSubmit"
													style="display: none;">

													<div class="form-group row">
														<div class="col-sm col-sm-8"
															style="margin-left: 26%; width: 48%;">
															<input type="submit" name=resolve value="Resolve Ticket"
																class="btn btn-primary btn-block btn-lg" tabindex="9"
																id=2>
														</div>
													</div>

												</div>
												<!-- //actionTakenSubmit -->



											</form:form>

										</div>
									</div>
								</c:when>
							</c:choose>
							<!--tTicketAwaitingSparesResolve -->

							<c:choose>
								<c:when test="${ticketObject.status =='SLA Bridged'}">


									<div class="tab-pane" id="tTicketSLABridgedEscalate">

										<div class="panel-body">

											<!-- tTicketTakenEscalate Details -->
											<form:form action="performTicketAction"
												modelAttribute="performTicketAction" method="post"
												id="ticketTakenEscalate" class="well form-horizontal">

												<legend style="font-size: 15px; line-height: 1.42857143;"
													align="center">
													<b>Escalate</b>
												</legend>

												<!-- Action Action -->
												<input type="hidden" id="ticketAction" name="ticketAction"
													class="form-control selectpicker" value="Escalate">

												<!-- Text input Ticket Number-->
												<div class="form-group">
													<label class="col-md-3 control-label">Ticket Number</label>
													<div class="col-md-6 inputGroupContainer">
														<div class="input-group">
															<span class="input-group-addon"><i
																class="glyphicon glyphicon-barcode"></i></span> <input
																name="ticketNumber" id="ticketNumber"
																class="form-control" type="text"
																value="VTC000${ticketObject.recordID}" readonly>
														</div>
													</div>
												</div>
												<!-- Text input Ticket Number-->
												<div class="form-group" style="display: none">
													<div class="col-md-6 inputGroupContainer">
														<div class="input-group">
															<span class="input-group-addon"><i
																class="glyphicon glyphicon-barcode"></i></span> <input
																name="recordID" id="ticketNumber" class="form-control"
																type="text" value="${ticketObject.recordID}">
														</div>
													</div>
												</div>
												<!-- Text input Manager-->
												<div class="form-group">
													<label class="col-md-3 control-label"> Manager</label>
													<div class="col-md-6 inputGroupContainer">
														<div class="input-group">
															<span class="input-group-addon"><i
																class="glyphicon glyphicon-user"></i></span> <select
																id="escalatedTo" name="escalatedTo"
																class="form-control selectpicker">
																<option value="">Select Manager</option>
																<c:forEach items="${managersList}" var="manager">
																	<option value="${manager.email}">${manager.email}</option>
																</c:forEach>

															</select>
														</div>
													</div>
												</div>

												<!-- display Comments-->

												<div class="form-group">
													<label class="col-md-3 control-label">Comment</label>
													<div class="col-md-6 inputGroupContainer">
														<div class="input-group">
															<span class="input-group-addon"><i
																class="glyphicon glyphicon-edit"></i></span>
															<textarea class="form-control" style="height: 120px;"
																id="comments" name="comments" maxlength="150"
																onkeydown="upperCaseF(this)"
																placeholder="Please enter comment"></textarea>
														</div>
													</div>
												</div>

												<!--// display Comments-->

												<div class="form-group row">
													<div class="col-sm col-sm-8"
														style="margin-left: 26%; width: 48%;">
														<input type="submit" name=resolve value="Escalate Ticket"
															class="btn btn-primary btn-block btn-lg" tabindex="9"
															id="resolve">
													</div>
												</div>

											</form:form>
											<!-- tTicketSLABridgedEscalate Details -->

										</div>
									</div>


								</c:when>
							</c:choose>


							<c:choose>
								<c:when test="${ticketObject.status =='SLA Bridged'}">

									<div class="tab-pane" id="tTicketSLABridgedResolved">

										<div class="panel-body">

											<!-- tTicketTakenResolve Details -->
											<form:form action="performTicketAction"
												modelAttribute="performTicketAction" method="post"
												id="updateResolved" class="well form-horizontal">

												<legend style="font-size: 15px; line-height: 1.42857143;"
													align="center">
													<b>Resolve</b>
												</legend>

												<!-- Action Action -->
												<input type="hidden" id="ticketAction" name="ticketAction"
													class="form-control selectpicker" value="Resolve">

												<!-- Text input Ticket Number-->
												<div class="form-group">
													<label class="col-md-3 control-label">Ticket Number</label>
													<div class="col-md-6 inputGroupContainer">
														<div class="input-group">
															<span class="input-group-addon"><i
																class="glyphicon glyphicon-barcode"></i></span> <input
																name="ticketNumber" id="ticketNumber"
																class="form-control" type="text"
																value="VTC000${ticketObject.recordID}" readonly>
														</div>
													</div>
												</div>

												<!-- Text input Ticket Number-->
												<div class="form-group" style="display: none">
													<div class="col-md-6 inputGroupContainer">
														<div class="input-group">
															<span class="input-group-addon"><i
																class="glyphicon glyphicon-barcode"></i></span> <input
																name="recordID" id="ticketNumber" class="form-control"
																type="text" value="${ticketObject.recordID}">
														</div>
													</div>
												</div>
												<!-- Text area Action Taken-->
												<div class="form-group">
													<label class="col-md-3 control-label">Action Taken</label>
													<div class="col-md-6 selectContainer">
														<div class="input-group">
															<span class="input-group-addon"><i
																class="glyphicon glyphicon-list"></i></span><select
																name="actionTaken" id="actionTaken"
																class="form-control selectpicker"
																onchange="Faulty(this.value);">
																<option value="">Please select Action Taken for
																	Repair</option>
																<option value="Replaced Part/Toner">Replaced
																	Part/Toner</option>
																<option value="Cleared Paper Jam">Cleared Paper
																	Jam</option>
																<option value="Installed Drivers">Installed
																	Drivers</option>
																<option value="Configured Drivers">Configured
																	Drivers</option>
																<option value="Configured Printer">Configured
																	Printer</option>
																<option value="User Error">User Error</option>
																<option value="No fault Found">No fault Found</option>
																<option value="Cleaned Mirrors">Cleaned Mirrors</option>
																<option value="Cleaned Laser Unit">Cleaned
																	Laser Unit</option>
																<option value="Cleaned Charge Rollers">Cleaned
																	Charge Rollers</option>
																<option value="Cleaned ADF Class">Cleaned ADF
																	Class</option>
																<option value="Cleaned Rollers">Cleaned Rollers</option>
																<option value="Move Copier To New Location">Move Copier To New Location</option>
																<option value="Firmware Upgrade">Firmware Upgrade</option>
																<option value="Cleaned Waste Toner Bottle">Cleaned Waste Toner Bottle</option>
																<option value="Replaced Stipples">Replaced Stipples</option>
															
															</select>
														</div>
													</div>
												</div>

												<div class="hideMonoAndColour" id="hideMonoAndColour"
													style="display: none">

													<c:if test="${not empty ticketObject.device.colourReading}">
														<!-- Text checkbox Colour Reading-->
														<div class="form-group">
															<label class="col-md-3 control-label">Colour
																Reading</label>
															<div class="col-md-6 inputGroupContainer">
																<div class="input-group">
																	<span class="input-group-addon"><i
																		class="glyphicon glyphicon-list"></i></span> <input
																		type="text" class="form-control"
																		onkeypress="return isNumber(event)"
																		placeholder="Enter Colour Reading" id="colourReading"
																		name="colourReading"
																		onblur="compareColourReading(this, ${ticketObject.device.colourReading})">
																</div>
															</div>
														</div>
													</c:if>
													<div class="form-group">
														<label class="col-md-3 control-label">Mono Reading</label>
														<div class="col-md-6 inputGroupContainer">
															<div class="input-group">
																<span class="input-group-addon"><i
																	class="glyphicon glyphicon-list"></i></span> <input
																	type="text" class="form-control"
																	onkeypress="return isNumber(event)" id="mono"
																	name="monoReading" placeholder="Enter Mono Reading"
																	onblur="compareMonoReading(this, ${ticketObject.device.monoReading})">
															</div>
														</div>
													</div>

												</div>
												<!-- HideMonoAndColour if no action is selscted -->

												<!-- group Used Part Numbers -->
												<div class="groupsearchdetails">

													<legend id="hideUsedPartNumbersHidding"
														style="display: none; width: 50%; margin-left: 25%;">Used
														Part Numbers </legend>

													<div class="hideIfIsNotPartToner" id="hideIfIsNotPartToner"
														style="display: none">

														<fieldset id="groupstock" style="margin-left: 0%">

															<!-- group Boot Stock -->
															<div class="form-group">
																<label class="col-xs-3 control-label">Boot Stock</label>
																<div class="col-md-6 inputGroupContainer">
																	<input type="radio" name="groupboot" required
																		id="checkBootStock" onclick="BootStockChecked()"
																		value="Boot Stock"
																		tittle="You must check Boot Stock or Site Stock to get Used Part Numbers">
																</div>
															</div>
															<div class="displayNone" id="bootStockItems"
																style="margin-left: 25.5%; margin-right: 26%;">
																<table id="bStock" data-toggle="table"
																	data-url="${bootStock}" data-show-refresh="true"
																	data-show-toggle="true" data-search="true"
																	data-select-item-name="toolbar1" data-pagination="true"
																	data-sort-name="partno" data-sort-order="desc">
																	<thead>
																		<tr>
																			<th data-field="partno" data-sortable="true">Part
																				No</th>
																			<th data-field="description" data-sortable="true">Description</th>
																			<th data-field="itemtype" data-sortable="true">Item
																				Type</th>
																			<th data-field="quantity" data-sortable="true">Quantity</th>
																			<th data-field="assignedto" data-sortable="true">Tick
																				Used Parts</th>

																		</tr>
																	</thead>
																	<tbody>
																		<!-- Iterating over the list sent from Controller -->
																		<c:forEach var="list" items="${bootStock}">

																			<tr>
																				<td>${list.partNumber}</td>
																				<td>${list.itemDescription}</td>
																				<td>${list.itemType}</td>
																				<td>${list.quantity}</td>
																				<td><input type="checkbox"
																					id="${list.partNumber}_selectedItem"
																					name="selectedItem"
																					onClick="checkUsedPartNumbers();"
																					value="${list.partNumber}"></td>
																			</tr>

																		</c:forEach>
																	</tbody>
																</table>
															</div>
															<input type="hidden" class="form-control"
																id="bootStockType" name="bootStockType"
																value="Boot Stock">

															<!-- group Site Stock -->
															<div class="form-group">
																<label class="col-xs-3 control-label">Site Stock</label>
																<div class="col-md-6 inputGroupContainer">
																	<input type="radio" name="groupboot" required
																		id="checkSiteStock" onclick="SiteStockChecked()"
																		value="Site Stock">
																</div>
															</div>
															<div class="displayNone" id="siteStockItems"
																style="margin-left: 25.5%; margin-right: 26%;">
																<table id="sStock" data-toggle="table"
																	data-url="${siteStock}" data-show-refresh="true"
																	data-show-toggle="true" data-search="true"
																	data-select-item-name="toolbar1" data-pagination="true"
																	data-sort-name="partno" data-sort-order="desc">
																	<thead>
																		<tr>
																			<th data-field="partno" data-sortable="true">Part
																				No</th>
																			<th data-field="description" data-sortable="true">Description</th>
																			<th data-field="modelno" data-sortable="true">Model
																				No</th>
																			<th data-field="quantity" data-sortable="true">Quantity</th>
																			<th data-field="assignedto" data-sortable="true">Tick
																				Used Parts</th>

																		</tr>
																	</thead>
																	<tbody>
																		<!-- Iterating over the list sent from Controller -->
																		<c:forEach var="list" items="${siteStock}">
																			<tr>
																				<td>${list.partNumber}</td>
																				<td>${list.itemDescription}</td>
																				<td>${list.itemType}</td>
																				<td>${list.quantity}</td>
																				<td><input type="checkbox"
																					id="${list.partNumber}_selectedItem"
																					name="selectedItem"
																					onClick="checkUsedPartNumbers();"
																					value="${list.partNumber}"></td>
																			</tr>
																		</c:forEach>
																	</tbody>
																</table>
															</div>
															</br> <input type="hidden" class="form-control"
																id="siteStockType" name="siteStockType"
																value="Site Stock">

														</fieldset>


														<!-- display ticked Used Part Numbers-->
														<div class="form-group">
															<label class="col-md-3 control-label">Used Part
																Numbers</label>
															<div class="col-md-6 inputGroupContainer">
																<div class="input-group">
																	<span class="input-group-addon"><i
																		class="glyphicon glyphicon-list"></i></span> <input
																		id="usedPartNumbers" name="usedPartNumbers"
																		class="form-control" readonly="readonly"
																		style="height: 60px; font-size: 11px;">
																</div>
															</div>
														</div>
														<!--// display ticked Used Part Numbers-->

														<!-- Text area comments-->
														<div class="form-group">
															<label class="col-md-3 control-label">Comment</label>
															<div class="col-md-6 inputGroupContainer">
																<div class="input-group">
																	<span class="input-group-addon"><i
																		class="glyphicon glyphicon-edit"></i></span>
																	<textarea class="form-control" style="height: 120px;"
																		id="comments" name="comments" maxlength="150"
																		onkeydown="upperCaseF(this)"
																		placeholder="Please enter comment"></textarea>
																</div>
															</div>
														</div>
														<!--// text area comments-->

														<!-- display Bridged-->
														<div class="reseanBridged" id="reseanBridged">
															<div class="form-group">
																<label class="col-md-3 control-label">Bridged
																	Reason</label>
																<div class="col-md-6 inputGroupContainer">
																	<div class="input-group">
																		<span class="input-group-addon"><i
																			class="glyphicon glyphicon-edit"></i></span>
																		<textarea class="form-control" style="height: 120px;"
																			id="bridgedReason" name="bridgedReason"
																			maxlength="150" onkeydown="upperCaseF(this)"
																			placeholder="Please enter reason why ticket Bridged"></textarea>
																	</div>
																</div>
															</div>
														</div>
														<!-- //End display Bridged-->


													</div>
													<!-- // end hideIfIsNotPartToner -->

													<!-- hideComent-->
													<div class="hideComent" id="hideComent"
														style="display: none">

														<!-- Text area comments-->
														<div class="form-group">
															<label class="col-md-3 control-label">Comment</label>
															<div class="col-md-6 inputGroupContainer">
																<div class="input-group">
																	<span class="input-group-addon"><i
																		class="glyphicon glyphicon-edit"></i></span>
																	<textarea class="form-control" style="height: 120px;"
																		id="comments" name="comments" maxlength="150"
																		onkeydown="upperCaseF(this)"
																		placeholder="Please enter comment"></textarea>
																</div>
															</div>
														</div>
														<!--// text area comments-->

														<!-- display Bridged-->
														<div class="reseanBridged" id="reseanBridged">
															<div class="form-group">
																<label class="col-md-3 control-label">Bridged
																	Reason</label>
																<div class="col-md-6 inputGroupContainer">
																	<div class="input-group">
																		<span class="input-group-addon"><i
																			class="glyphicon glyphicon-edit"></i></span>
																		<textarea class="form-control" style="height: 120px;"
																			id="bridgedReason" name="bridgedReason"
																			maxlength="150" onkeydown="upperCaseF(this)"
																			placeholder="Please enter reason why ticket Bridged"></textarea>
																	</div>
																</div>
															</div>
														</div>
														<!-- //End display Bridged-->

													</div>
													<!-- //hideComent -->

												</div>
												<!-- // group used part numbers -->

												<div class="actionTakenSubmit" id="actionTakenSubmit"
													style="display: none;">

													<div class="form-group row">
														<div class="col-sm col-sm-8"
															style="margin-left: 26%; width: 48%;">
															<input type="submit" name=resolve value="Resolve Ticket"
																class="btn btn-primary btn-block btn-lg" tabindex="9"
																id="resolve">
														</div>
													</div>

												</div>
												<!-- //actionTakenSubmit -->

											</form:form>

										</div>

									</div>
								</c:when>
							</c:choose>

							<c:choose>
								<c:when test="${ticketObject.status =='Resolved'}">

									<!-- ticketReopenResolved -->
									<div class="tab-pane" id="tTicketReopenResolved">

										<div class="panel-body">

											<form:form class="well form-horizontal">

												<legend style="font-size: 15px; line-height: 1.42857143;"
													align="center">
													<b>Resolved Details</b>
												</legend>

												<!-- Text input Serial No-->
												<div class="form-group">
													<label class="col-md-3 control-label">Serial No</label>
													<div class="col-md-6 inputGroupContainer">
														<div class="input-group">
															<span class="input-group-addon"><i
																class="glyphicon glyphicon-barcode"></i></span> <input
																name="serialNumber" placeholder="Serial Number"
																value="${ticketObject.getDevice().getSerialNumber() }"
																class="form-control" type="text" readonly>
														</div>
													</div>
												</div>

												<!-- Text area Action Taken-->
												<div class="actionTaken">
													<div class="form-group">
														<label class="col-md-3 control-label">Action Taken</label>
														<div class="col-md-6 inputGroupContainer">
															<div class="input-group">
																<span class="input-group-addon"><i
																	class="glyphicon glyphicon-barcode"></i></span> <input
																	id="actionTaken" class="form-control" type="text"
																	name="actionTaken" value="${ticketObject.actionTaken}"
																	readonly="readonly">
															</div>
														</div>
													</div>
												</div>

												<c:if test="${empty ticketObject.comments}">
												</c:if>
												<c:if test="${not empty ticketObject.comments}">
													<!-- Text area Comment-->
													<div class="form-group">
														<label class="col-md-3 control-label">Comments</label>
														<div class="col-md-6 inputGroupContainer">
															<div class="input-group">
																<span class="input-group-addon"><i
																	class="glyphicon glyphicon-pencil"></i></span>
																<textarea class="form-control" style="height: 120px;"
																	name="comments" id="comment" readonly="readonly"
																	style="height: 100px;">${ticketObject.comments}</textarea>
															</div>
														</div>
													</div>
												</c:if>

												<c:if test="${empty ticketObject.bridgedReason}">
												</c:if>
												<c:if test="${not empty ticketObject.bridgedReason}">
													<!-- display Bridged-->
													<div class="reseanBridged" id="reseanBridged">
														<div class="form-group">
															<label class="col-md-3 control-label">Bridged
																Reason</label>
															<div class="col-md-6 inputGroupContainer">
																<div class="input-group">
																	<span class="input-group-addon"><i
																		class="glyphicon glyphicon-edit"></i></span>
																	<textarea class="form-control" style="height: 120px;"
																		id="bridgedReason" readonly="readonly"
																		name="bridgedReason" maxlength="150"
																		onkeydown="upperCaseF(this)">${ticketObject.bridgedReason}</textarea>
																</div>
															</div>
														</div>
													</div>
													<!-- //End display Bridged-->
												</c:if>

												<c:if test="${empty ticketObject.usedPartNumbers}">
												</c:if>
												<c:if test="${not empty ticketObject.usedPartNumbers}">

													<!-- Text area Used Spare Part-->
													<div class="usedPartNumbersDetails">
														<div class="form-group">
															<label class="col-md-3 control-label">Used
																Spare/Part</label>
															<div class="col-md-6 inputGroupContainer">
																<div class="input-group">
																	<span class="input-group-addon"><i
																		class="glyphicon glyphicon-barcode"></i></span> <input
																		id="usedPartNumbers" class="form-control" type="text"
																		name="usedPartNumbers"
																		value="${ticketObject.usedPartNumbers}"
																		readonly="readonly"
																		style="height: 60px; font-size: 11px;">
																</div>
															</div>
														</div>
													</div>
												</c:if>

												<c:if test="${ticketObject.getDevice().getColourReading() }">
												</c:if>
												<c:if
													test="${not empty ticketObject.getDevice().getColourReading() }">

													<!-- Text checkbox Colour Reading-->
													<div class="form-group">
														<label class="col-md-3 control-label">Colour
															Reading</label>
														<div class="col-md-6 inputGroupContainer">
															<div class="input-group">
																<span class="input-group-addon"><i
																	class="glyphicon glyphicon-barcode"></i></span> <input
																	type="text" class="form-control" readonly="readonly"
																	onkeypress="return isNumber(event)"
																	placeholder="Enter Colour Reading" id="colour"
																	name="colourReading"
																	value="${ticketObject.getDevice().getColourReading() }"
																	name="colourReading">
															</div>
														</div>
													</div>

												</c:if>


												<c:if test="${ticketObject.getDevice().getColourReading() }">
												</c:if>
												<c:if
													test="${not empty ticketObject.getDevice().getMonoReading() }">

													<div class="form-group">
														<label class="col-md-3 control-label">Mono Reading</label>
														<div class="col-md-6 inputGroupContainer">
															<div class="input-group">
																<span class="input-group-addon"><i
																	class="glyphicon glyphicon-barcode"></i></span> <input
																	type="text" class="form-control"
																	onkeypress="return isNumber(event)" id="mono"
																	readonly="readonly" name="monoReading"
																	placeholder="Enter Mono Reading" name="monoReading"
																	value="${ticketObject.getDevice().getMonoReading() }">
															</div>
														</div>
													</div>
												</c:if>



												<div class="diplayNone" id="getPartTonerResolved"
													style="display: none">

													<!-- display ticked Used Part Numbers-->
													<div class="form-group">
														<label class="col-md-3 control-label">Used Part
															Numbers</label>
														<div class="col-md-6 inputGroupContainer">
															<div class="input-group">
																<span class="input-group-addon"><i
																	class="glyphicon glyphicon-barcode"></i></span> <input
																	id="tickedUsedPartNumbers" class="form-control"
																	readonly="readonly" name="usedPartNumbers"
																	style="height: 60px; font-size: 11px;">
															</div>
														</div>
													</div>
													<!--// display ticked Used Part Numbers-->

												</div>
												<!-- displayNone for getPartToner -->


											</form:form>

										</div>
										<!-- pane body -->

									</div>

								</c:when>
							</c:choose>
							<!-- tTicketReopenResolved -->

							<c:choose>
								<c:when test="${ticketObject.status =='Closed'}">

									<!-- ticketClosedNoAction -->
									<div class="tab-pane" id="tTicketClosedNoAction">

										<br />
										
											<legend style="font-size: 18px; line-height: 1.42857143;"
													align="center">
													Closed Details
												</legend>

											<form:form class="well form-horizontal">												
												<!-- Text input Serial No-->
												<div class="form-group">
													<label class="col-md-3">Serial No</label>
													${ticketObject.getDevice().getSerialNumber() }
												</div>

												<c:if test="${empty ticketObject.actionTaken}">
												</c:if>
												<c:if test="${not empty ticketObject.actionTaken}">

													<!-- Text area Action Taken-->
													<div class="actionTaken">
														<div class="form-group">
															<label class="col-md-3">Action
																Taken</label>${ticketObject.actionTaken}
															</div>
													</div>
												</c:if>

												<c:if test="${empty ticketObject.comments}">
												</c:if>
												<c:if test="${not empty ticketObject.comments}">
													<!-- Text area Comment-->
													<div class="form-group">
														<label class="col-md-3">Comments</label>
														${ticketObject.comments}
													</div>
												</c:if>

												<c:if test="${empty ticketObject.bridgedReason}">
												</c:if>
												<c:if test="${not empty ticketObject.bridgedReason}">
													<!-- display Bridged-->
													<div class="reseanBridged" id="reseanBridged">
														<div class="form-group">
															<label class="col-md-3">Bridged
																Reason</label>${ticketObject.bridgedReason}
														</div>
													</div>
													<!-- //End display Bridged-->
												</c:if>

												<c:if test="${empty ticketObject.usedPartNumbers}">
												</c:if>
												<c:if test="${not empty ticketObject.usedPartNumbers}">

													
														<div class="form-group">
															<!-- Text area Used Spare Part-->
															<div class="usedPartNumbersDetails">
																<label class="col-md-3">Used
																	Spare/Part</label>${ticketObject.usedPartNumbers}
															</div>
														</div>
												</c:if>
												<c:if
													test="${empty $ticketObject.getDevice().getColourReading()}">
												</c:if>
												<c:if
													test="${not empty $ticketObject.getDevice().getColourReading()}">

													<!-- Text checkbox Colour Reading-->
													<div class="form-group">
														<label class="col-md-3">Colour
															Reading</label>${ticketObject.getDevice().getColourReading() }"
															
													</div>
												</c:if>

												<c:if
													test="${empty $ticketObject.getDevice().getMonoReading()}">
												</c:if>
												<c:if
													test="${not empty $ticketObject.getDevice().getMonoReading()}">

													<div class="form-group">
														<label class="col-md-3">Mono Reading</label>
														${ticketObject.getDevice().getMonoReading() }
													</div>
												</c:if>

												<div class="diplayNone" id="getPartTonerResolved"
													style="display: none">

													<!-- display ticked Used Part Numbers-->
													<div class="form-group">
														<label class="col-md-3 control-label">Used Part
															Numbers</label>
														<div class="col-md-6 inputGroupContainer">
															<div class="input-group">
																<span class="input-group-addon"><i
																	class="glyphicon glyphicon-barcode"></i></span> <input
																	id="tickedUsedPartNumbers" class="form-control"
																	readonly="readonly" name="usedPartNumbers"
																	style="height: 60px; font-size: 11px;">
															</div>
														</div>
													</div>
													<!--// display ticked Used Part Numbers-->
												</div>
												<!-- displayNone for getPartToner -->

												<c:if test="${empty ticketObject.reopenReason}">
												</c:if>
												<c:if test="${not empty ticketObject.reopenReason}">
													<!-- Text area reopenReason-->
													<div class="form-group">
														<label class="col-md-3">Re-Open
															Reason</label>${ticketObject.reopenReason}															
													</div>
													<!-- Text area reopenReason-->
												</c:if>
											</form:form>
										</div>
										<!-- pane body -->

								</c:when>
							</c:choose>
							<!-- tTicketClosedNoAction -->
						
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

