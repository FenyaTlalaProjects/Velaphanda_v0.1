<%@include file="templates/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Ticket Details | Velaphanda Trading & Projects</title>
<c:import url="templates/tableresizefont.jsp"></c:import>
<c:import url="templates/stylesheetlib.jsp"></c:import>
<style type="text/css">li { list-style: none; }</style>
</head>
<body>

	<c:import url="templates/usernavbar.jsp"></c:import>

	<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
		<div class="row">
			<ol class="breadcrumb">
				<li><a href="userdashboard.html"><svg class="glyph stroked home">
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

						<!-- navigation for action taken -->
						<div id="navbar" class="navbar-collapse collapse"
							style="margin-left: -2%; display:block;">
							<ul class="nav navbar-nav navbar-left">
								<!-- resolved details -->
								<c:choose>
								
									<c:when
										test="${ticketObject.status =='Open' || ticketObject.status == 'Re-Open'}">
										<li class="dropdown"><a href="#" class="dropdown-toggle"
											data-toggle="dropdown" role="button" aria-haspopup="true"
											aria-expanded="false">Ticket Action<span class="caret"></span></a>
											<ul class="dropdown-menu">
												<li><a id="mTicketOpenReassignLink" href="javascript:;"
													data-toggle="tab">Re-assign</a></li>
													<li><a  href="acknowledgeTicketsForTech?recordID=<c:out value='${ticketObject.recordID}'/>">Acknowledge</a></li>
													
											</ul></li>
									</c:when>

									<c:when test="${ticketObject.status =='Acknowledged'}">
										<li class="dropdown"><a href="#" class="dropdown-toggle"
											data-toggle="dropdown" role="button" aria-haspopup="true"
											aria-expanded="false">Ticket Action<span class="caret"></span></a>
											<ul class="dropdown-menu">
												<li><a id="mTicketAcknowledgedReassignLink"
													href="javascript:;" data-toggle="tab">Re-assign</a></li>
													<li><a  href="takeTicketsForTech?recordID=<c:out value='${ticketObject.recordID}'/>">
													Take Ticket</a></li>
											</ul></li>
									</c:when>

									<c:when test="${ticketObject.status =='Taken'}">
										<li class="dropdown"><a href="#" class="dropdown-toggle"
											data-toggle="dropdown" role="button" aria-haspopup="true"
											aria-expanded="false">Ticket Action<span class="caret"></span></a>
											<ul class="dropdown-menu">
												<li><a id="mTicketTakenAwaitingLink"
													href="javascript:;" data-toggle="tab">Awaiting Spares</a></li>
												<li><a id="mTicketTakenEscalateLink"
													href="javascript:;" data-toggle="tab">Escalate</a></li>
												<li><a id="mTicketTakenResolveLink" href="javascript:;"
													data-toggle="tab">Resolve</a></li>
											</ul></li>
									</c:when>

									<c:when test="${ticketObject.status =='Escalated'}">
										<li class="dropdown"><a href="#" class="dropdown-toggle"
											data-toggle="dropdown" role="button" aria-haspopup="true"
											aria-expanded="false">Ticket Action<span class="caret"></span></a>
											<ul class="dropdown-menu">
												<li><a id="mTicketEscalatedReassignLink"
													href="javascript:;" data-toggle="tab">Re-assign</a></li>
												<li><a id="mTicketEscalatedResolvedDetailsLink"
													href="javascript:;" data-toggle="tab">Resolve</a></li>
											</ul></li>
									</c:when>

									<c:when test="${ticketObject.status =='SLA Bridged'}">
										<li class="dropdown"><a href="#" class="dropdown-toggle"
											data-toggle="dropdown" role="button" aria-haspopup="true"
											aria-expanded="false">Ticket Action<span class="caret"></span></a>
											<ul class="dropdown-menu">
												<li><a id="mTicketSLABridgedResolvedLink"
													href="javascript:;" data-toggle="tab">Resolve</a></li>

											</ul></li>
									</c:when>

									<c:when test="${ticketObject.status =='Resolved'}">
										<li class="dropdown"><a href="#" class="dropdown-toggle"
											data-toggle="dropdown" role="button" aria-haspopup="true"
											aria-expanded="false">Ticket Action<span class="caret"></span></a>
											<ul class="dropdown-menu">
												<li><a id="mTicketReopenResolvedLink"
													href="javascript:;" data-toggle="tab">Re-open</a></li>
											</ul></li>
									</c:when>
								</c:choose>

							<li><a target="_blank"
									href="ticketDownloadPDF?recordID=<c:out value='${ticketObject.recordID}' />">Download
										PDF </a></li>
							</ul>
						</div>
						<!-- //navigation for action taken -->


						<legend></legend>
						<!-- nav sub menu tabs  -->
						<ul class="nav nav-tabs">
							<li class="active"><a href="#ticketDetails"
								data-toggle="tab">Ticket Details</a></li>
							<li><a href="#ticketHistoryDetails" data-toggle="tab">Ticket
									History</a></li>
							<c:choose>

								<c:when
									test="${ticketObject.status == 'Open' || ticketObject.status == 'Re-Open'}">
									<li class="mTicketOpenReassign" style="display: none;"><a
										href="#mTicketOpenReassign" data-toggle="tab">Re-assign</a></li>
								</c:when>

								<c:when test="${ticketObject.status =='Acknowledged'}">
									<li class="mTicketAcknowledgedReassign" style="display: none;"><a
										href="#mTicketAcknowledgedReassign" data-toggle="tab">Re-assign</a></li>
								</c:when>

								<c:when test="${ticketObject.status =='Taken'}">
									<li class="mTicketTakenEscalate" style="display: none;"><a
										href="#mTicketTakenEscalate" data-toggle="tab">Escalate</a></li>
									<li class="mTicketTakenAwaiting" style="display: none;"><a
										href="#mTicketTakenAwaiting" data-toggle="tab">Awaiting
											Spares</a></li>
									<li class="mTicketTakenResolve" style="display: none;"><a
										href="#mTicketTakenResolve" data-toggle="tab">Resolve</a></li>
								</c:when>

								<c:when test="${ticketObject.status =='Escalated'}">
									<li class="mTicketEscalatedResolvedDetails"
										style="display: none;"><a
										href="#mTicketEscalatedResolvedDetails" data-toggle="tab">Resolve</a></li>
									<li class="mTicketEscalatedReassign" style="display: none;"><a
										href="#mTicketEscalatedReassign" data-toggle="tab">Re-assign</a></li>
								</c:when>

								<c:when test="${ticketObject.status =='SLA Bridged'}">
									<li class="mTicketSLABridgedResolved" style="display: none;"><a
										href="#mTicketSLABridgedResolved" data-toggle="tab">Resolve</a></li>
								</c:when>

								<c:when test="${ticketObject.status =='Resolved'}">
									<li class="mTicketReopenResolved" style="display: none;"><a
										href="#mTicketReopenResolved" data-toggle="tab">Resolved
											Ticket Details</a></li>
								</c:when>

								<c:when test="${ticketObject.status =='Closed'}">
									<li><a href="#mTicketClosedNoAction" data-toggle="tab">Closed
											Ticket Details</a></li>
								</c:when>

							</c:choose>

						</ul>
						<!-- // nav sub menu tabs-->

						<!-- tab content -->
						<div class="tab-content">

							<!--Ticket Details tab-->
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
									<div class="machinedetailsfloatright ">
										<div class="orderDetails">
											<li id="contactName"><b>${ticketObject.firstName}
													${ticketObject.lastName}</b></li>
											<li id="cell">Cell No: ${ticketObject.contactCellNumber}</li>
											<li id="telephone">Telephone No:
												${ticketObject.contactTelephoneNumber}</li>
											<li id="email">E-Mail: ${ticketObject.contactEmail}</li>
										</div>
										<br>
									</div>
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
												<td> VTC000${ticketObject.recordID}</td>
												<td> ${ticketObject.status}</td>
												<td> ${ticketObject.priority}</td>
												<td> ${ticketObject.employee.email}</td>
												<td> ${ticketObject.comments}</td>
											</tr>
										</tbody>
									</table>
									<!-- //Table ticket Info -->
								</div>

							</div>
							<!-- tab for Ticket Details -->

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


							<!-- Re-assign ticket if status is Acknowledged -->
							<c:choose>
								<c:when test="${ticketObject.status =='Acknowledged'}">

									<div class="tab-pane" id="mTicketAcknowledgedReassign">

										<div class="panel-body">

											<!-- ticketReassign Details -->
											<form:form action="performTicketAction"
												modelAttribute="performTicketAction" method="post"
												id="ticketAcknowledgedReassign" class="well form-horizontal">

												<legend style="font-size: 15px; line-height: 1.42857143;"
													align="center">
													<b>Re-assign</b>
												</legend>

												<!-- Action Action -->
												<input type="hidden" id="ticketAction" name="ticketAction"
													class="form-control selectpicker" value="Reassign">

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
												<!-- Assign Technician -->
												<div class="form-group">
													<label class="col-md-3 control-label">Assign
														Technician</label>
													<div class="col-md-6 selectContainer">
														<div class="input-group">
															<span class="input-group-addon"><i
																class="glyphicon glyphicon-list"></i></span> <select
																id="technicianUserName" name="technicianUserName"
																class="form-control selectpicker">
																<option value="">Select Technician</option>
																<c:forEach items="${reassignToTechnician}"
																	var="technician">
																	<c:choose>
																		<c:when test="${technician.leaveStatus =='Active'}">
																			<option class="onleave" value="${technician.email}">${technician.firstName}
																				${technician.lastName} (Leave Active)</option>
																		</c:when>
																		<c:when test="${technician.leaveStatus !='Active'}">
																			<option value="${technician.email}">${technician.firstName}
																				${technician.lastName}</option>
																		</c:when>
																	</c:choose>
																</c:forEach>
															</select>
														</div>
													</div>
												</div>
												<div class="form-group row">
													<div class="col-sm col-sm-8"
														style="margin-left: 26%; width: 48%;">
														<input type="submit" name=resolve value="Re-assign Ticket"
															class="btn btn-primary btn-block btn-lg" tabindex="9"
															id="resolve">
													</div>
												</div>

											</form:form>
											<!-- ticketReassign Details -->

										</div>
										<!-- /panel body -->
									</div>
									<!-- /re-assign ticket -->
								</c:when>
							</c:choose>
							<!--// Re-assign ticket if status is Acknowledged -->


							<!-- Re-assign ticket if status is Escalated -->
							<c:choose>
								<c:when test="${ticketObject.status =='Escalated'}">

									<div class="tab-pane" id="mTicketEscalatedReassign">

										<div class="panel-body">

											<!-- ticketReassign Details -->
											<form:form action="performTicketAction"
												modelAttribute="performTicketAction" method="post"
												id="ticketEscalatedReassign" class="well form-horizontal">

												<legend style="font-size: 15px; line-height: 1.42857143;"
													align="center">
													<b>Re-assign</b>
												</legend>

												<!-- Action Action -->
												<input type="hidden" id="ticketAction" name="ticketAction"
													class="form-control selectpicker" value="Reassign">

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
												<!-- Assign Technician -->
												<div class="form-group">
													<label class="col-md-3 control-label">Assign
														Technician</label>
													<div class="col-md-6 selectContainer">
														<div class="input-group">
															<span class="input-group-addon"><i
																class="glyphicon glyphicon-list"></i></span> <select
																id="technicianUserName" name="technicianUserName"
																class="form-control selectpicker">
																<option value="">Select Technician</option>
																<c:forEach items="${reassignToTechnician}"
																	var="technician">
																	<c:choose>
																		<c:when test="${technician.leaveStatus =='Active'}">
																			<option class="onleave" value="${technician.email}">${technician.firstName}
																				${technician.lastName} (Leave Active)</option>
																		</c:when>
																		<c:when test="${technician.leaveStatus !='Active'}">
																			<option value="${technician.email}">${technician.firstName}
																				${technician.lastName}</option>
																		</c:when>
																	</c:choose>
																</c:forEach>

															</select>
														</div>
													</div>
												</div>

												<div class="form-group row">
													<div class="col-sm col-sm-8"
														style="margin-left: 26%; width: 48%;">
														<input type="submit" name=resolve value="Re-assign Ticket"
															class="btn btn-primary btn-block btn-lg" tabindex="9"
															id="resolve">
													</div>
												</div>

											</form:form>
											<!-- ticketReassign Details -->


										</div>
										<!-- /panel body -->
									</div>
									<!-- /re-assign ticket -->

								</c:when>
							</c:choose>
							<!-- //Re-assign ticket if status is Escalated -->


							<!-- Re-assign ticket if status is Open -->
							<c:choose>
								<c:when
									test="${ticketObject.status =='Open' || ticketObject.status == 'Re-Open'}">

									<div class="tab-pane" id="mTicketOpenReassign">

										<div class="panel-body">

											<!-- ticketReassign Details -->
											<form:form action="performTicketAction"
												modelAttribute="performTicketAction" method="post"
												id="ticketOpenReassign" class="well form-horizontal">

												<legend style="font-size: 15px; line-height: 1.42857143;"
													align="center">
													<b>Re-assign</b>
												</legend>

												<!-- Action Action -->
												<input type="hidden" id="ticketAction" name="ticketAction"
													class="form-control selectpicker" value="Reassign">

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
												<!-- Assign Technician -->
												<div class="form-group">
													<label class="col-md-3 control-label">Assign
														Technician</label>
													<div class="col-md-6 selectContainer">
														<div class="input-group">
															<span class="input-group-addon"><i
																class="glyphicon glyphicon-list"></i></span> <select
																id="technicianUserName" name="technicianUserName"
																class="form-control selectpicker">
																<option value="">Select Technician</option>
																<c:forEach items="${reassignToTechnician}"
																	var="technician">
																	<c:choose>
																		<c:when test="${technician.leaveStatus =='Active'}">
																			<option class="onleave" value="${technician.email}">${technician.firstName}
																				${technician.lastName} (Leave Active)</option>
																		</c:when>
																		<c:when test="${technician.leaveStatus !='Active'}">
																			<option value="${technician.email}">${technician.firstName}
																				${technician.lastName}</option>
																		</c:when>
																	</c:choose>
																</c:forEach>
															</select>
														</div>
													</div>
												</div>

												<div class="form-group row">
													<div class="col-sm col-sm-8"
														style="margin-left: 26%; width: 48%;">
														<input type="submit" name=resolve value="Re-assign Ticket"
															class="btn btn-primary btn-block btn-lg" tabindex="9"
															id="resolve">
													</div>
												</div>

											</form:form>
											<!-- ticketReassign Details -->


										</div>
										<!-- /panel body -->
									</div>
									<!-- /re-assign ticket -->

								</c:when>
							</c:choose>
							<!-- //Re-assign ticket if status is Open -->


							<!-- Escalate ticket if status is Taken -->
							<c:choose>
								<c:when test="${ticketObject.status =='Taken'}">

									<div class="tab-pane" id="mTicketTakenEscalate">

										<div class="panel-body">

											<!-- mTicketTakenEscalate Details -->
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
																	<option value="${manager.email}">${manager.email}
																	</option>
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
																onkeydown="upperCaseF(this)"
																placeholder="Please enter comment"
																style="height: 120px;"></textarea>
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
											<!-- escalate ticket Details -->


										</div>
										<!-- /panel body -->
									</div>
									<!-- /escalate ticket -->

								</c:when>
							</c:choose>
							<!-- // Escalate ticket if status is Taken -->

							<!-- Ticket Awaiting Spare if status is Taken -->
							<c:choose>
								<c:when test="${ticketObject.status =='Taken'}">

									<div class="tab-pane" id="mTicketTakenAwaiting">

										<div class="panel-body">

											<!-- mTicketTakenEscalate Details -->
											<form:form id="ticketTakenAwaiting"
												class="well form-horizontal" action="performTicketAction"
												modelAttribute="performTicketAction" method="post">

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
											<!-- Awaiting ticket Details -->

										</div>
									</div>
								</c:when>
							</c:choose>
							<!-- //Ticket Awaiting Spare if status is Taken -->

							<!--Ticket Taken and must be Resolve -->
							<c:choose>
								<c:when test="${ticketObject.status =='Taken'}">

									<div class="tab-pane" id="mTicketTakenResolve">

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
																				<td>${list.siteStock}</td>
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


							<!-- Details for Resolving ticket if status is Escalated  -->
							<c:choose>
								<c:when test="${ticketObject.status =='Escalated'}">

									<div class="tab-pane" id="mTicketEscalatedResolvedDetails">

										<div class="panel-body">

											<!-- resolved details -->
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
																				<td>${list.siteStock}</td>
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
									<!-- //resolvedSolution  -->

								</c:when>
							</c:choose>
							<!-- // Details for Resolving ticket if status is Escalated  -->


							<!-- Detials for SLA bridged tickets -->
							<c:choose>
								<c:when test="${ticketObject.status =='SLA Bridged'}">

									<div class="tab-pane" id="mTicketSLABridgedResolved">

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
																				<td>${list.siteStock}</td>
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

							<!-- Re-open ticket if status is Resolved and display resolved details -->
							<c:choose>
								<c:when test="${ticketObject.status =='Resolved'}">

									<!-- ticketReopenResolved -->
									<div class="tab-pane" id="mTicketReopenResolved">

										<div class="panel-body">

											<!-- mTicketTakenEscalate Details -->
											<form:form id="ticketTakenAwaiting"
												class="well form-horizontal" action="performTicketAction"
												modelAttribute="performTicketAction" method="post">

												<legend style="font-size: 15px; line-height: 1.42857143;"
													align="center">
													<b>Resolved Details</b>
												</legend>

												<!-- Action Action -->
												<input type="hidden" id="ticketAction" name="ticketAction"
													class="form-control selectpicker" value="reopen">

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

												<!-- Text area Comment-->
												<div class="form-group">
													<label class="col-md-3 control-label">Comments</label>
													<div class="col-md-6 inputGroupContainer">
														<div class="input-group">
															<span class="input-group-addon"><i
																class="glyphicon glyphicon-pencil"></i></span>
															<textarea class="form-control" name="comments"
																id="comments" readonly="readonly" style="height: 120px;">${ticketObject.comments}</textarea>
														</div>
													</div>
												</div>

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

												<c:if
													test="${empty $ticketObject.getDevice().getColourReading()}">
												</c:if>
												<c:if
													test="${not empty $ticketObject.getDevice().getColourReading()}">

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

												<c:if
													test="${empty $ticketObject.getDevice().getMonoReading()}">
												</c:if>
												<c:if
													test="${not empty $ticketObject.getDevice().getMonoReading()}">

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
																	readonly="readonly"
																	style="height: 60px; font-size: 11px;"
																	name="usedPartNumbers">
															</div>
														</div>
													</div>
													<!--// display ticked Used Part Numbers-->

												</div>
												<!-- displayNone for getPartToner -->


												<!-- Text area reopenReason-->
												<div class="form-group">
													<label class="col-md-3 control-label">Re-Open
														Reason</label>
													<div class="col-md-6 inputGroupContainer">
														<div class="input-group">
															<span class="input-group-addon"><i
																class="glyphicon glyphicon-pencil"></i></span>
															<textarea class="form-control" name="reopenReason"
																id="reopenReason" onkeydown="upperCaseF(this)"
																style="height: 120px;"></textarea>
														</div>
													</div>
												</div>
												<!-- Text area reopenReason-->


												<div class="form-group row">
													<div class="col-sm col-sm-8"
														style="margin-left: 26%; width: 48%;">
														<input type="submit" name=resolve value="Re-Open Ticket"
															class="btn btn-primary btn-block btn-lg" tabindex="9"
															id="resolve">
													</div>
												</div>

											</form:form>
										</div>
										<!-- pane body -->
									</div>
								</c:when>
							</c:choose>
							<!-- //Re-open ticket if status is Resolved and display resolved details -->

							<!-- Show ticket details if status is closed with no action and display resolved details -->
							<c:choose>
								<c:when test="${ticketObject.status =='Closed'}">

									<!-- ticketClosedNoAction -->
									<div class="tab-pane" id="mTicketClosedNoAction">

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
								</c:when>
							</c:choose>
							<!-- //Show ticket details if status is closed with no action and display resolved details -->
						
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

