<%@include file="templates/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Site Stock | Velaphanda Trading & Projects</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<c:import url="templates/tableresizefont.jsp"></c:import>
<style type="text/css">
.groupsparedetails, .groupsearchdetails {
	padding: 20px;
}

.groupsparedetails {
	float: left;
	width: 50%;
}

.groupsearchdetails {
	overflow: hidden;
}
</style>
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
					<div class="panel-heading" align="center">Site Stock</div>
					<div class="panel-body">

						<form:form method="post" class="well form-horizontal"
							action="moveSpares" modelAttribute="moveSpares" id="moveSpares">

							<c:if test="${not empty customerName}">

								<div class="col-sm-12">
									<div class="row">
										<br />
										<div class="col-6 col-md-6">
											<a href='numberOfParts'>
												<div class="well" style="background-color: #ffffff;">
													<h5 class="text-danger">
														<span class="label label-danger pull-right">${countPartForCustomer}</span>
														Parts
													</h5>
												</div>
											</a>
										</div>
										<div class="col-6 col-md-6">
											<a href='numberOfToners'>
												<div class="well" style="background-color: #ffffff;">
													<h5 class="text-success">
														<span class="label label-success pull-right">${countTonerForCustomer}</span>
														Toners
													</h5>
												</div>
											</a>
										</div>
									</div>
								</div>
								<!-- tab nav -->
								<div class="tab-content">

									<div class="col-sm-6">
										<b>Customer Name:${customerName}</b>
									</div>


									<table id="stockOnSite" data-toggle="table"
										data-url="${orders}" data-show-refresh="true"
										data-show-toggle="true" data-search="true"
										data-select-item-name="toolbar1" data-pagination="true"
										data-sort-name="partno" data-sort-order="desc">
										<colgroup></colgroup>
										<colgroup span="2"></colgroup>
										<thead>
											<tr>
												<th data-toggle="true" data-field="partNo"
													data-sortable="true">Part No</th>
												<th data-field="compatibleDevices" data-sortable="true">Compatible
													Devices</th>
												<th data-field="customer" data-sortable="true">Customer</th>
												<th data-field="modelBrand" data-sortable="true">Model
													Brand</th>
												<th data-field="description" data-sortable="true">Description</th>
												<th data-field="stockType" data-sortable="true">Stock
													Type</th>
												<th data-field="colour" data-sortable="true">Color</th>
												<th data-field="quantity" data-sortable="true">Quantity</th>
												<th colspan="2" data-field="provideqty" data-sortable="true">Provide
													QTY</th>
												<th colspan="2" data-field="action" data-sortable="true">Action</th>
										</thead>
										<tbody>
											<c:forEach var="list" items="${orders}">
												<c:choose>
													<c:when test="${list.quantity > 0}">

														<tr>
															<td><a
																href="loadStockSiteHistoryMovement?partNumber=<c:out value='${list.partNumber}'/>">${list.partNumber}</a></td>
															<td>${list.compatibleDevice}</td>
															<td>${list.customerName}</td>
															<td>${list.modelBrand}</td>
															<td>${list.itemDescription}</td>
															<td>${list.itemType}</td>
															<c:choose>
																<c:when test="${list.itemType=='Toner'}">
																	<td>${list.color}</td>
																</c:when>
																<c:otherwise>
																	<td>-</td>
																</c:otherwise>
															</c:choose>
															<td><input type="text"
																id="${list.partNumber}_avaliableQuantity"
																name="avaliableQuantity" class="form-control"
																readonly="readonly" value="${list.quantity}"></td>
															<td><input type="text"
																id="${list.partNumber}_quantity" name="quantity"
																class="form-control" onkeypress="return isNumber(event)"
																onblur="compareQuantity(this, ${list.quantity})"
																value="" /></td>
															<td><input class="addLineItem" type="button"
																value="Move Part"></td>
														</tr>
													</c:when>
													<c:otherwise>
													</c:otherwise>
												</c:choose>
											</c:forEach>

										</tbody>
									</table>

									<fieldset>
										<legend>
											<br />
											<h5 align="center">
												<b>Spares To Move</b>
											</h5>
										</legend>

										<table id="spareToMove"
											class="table table-striped table-bordered table-hover table-condensed"
											data-url="${orders}" data-show-refresh="true"
											data-show-toggle="true" data-search="true"
											data-select-item-name="toolbar1" data-pagination="true"
											data-sort-name="partno" data-sort-order="desc">
											<thead>
												<tr>
													<th data-toggle="true" data-field="partNo"
														data-sortable="true">Part No</th>
													<th data-field="compatibleDevices" data-sortable="true">Compatible
														Devices</th>
													<th data-field="customer" data-sortable="true">Customer</th>
													<th data-field="modelBrand" data-sortable="true">Model
														Brand</th>
													<th data-field="description" data-sortable="true">Description</th>
													<th data-field="stockType" data-sortable="true">Stock
														Type</th>
													<th data-field="colour" data-sortable="true">Color</th>
													<th data-field="quantity" data-sortable="true">Quantity</th>
													<th data-field="provideqty" data-sortable="true">QTY
														Provided</th>
													<th data-field="action" data-sortable="true">Action</th>
												</tr>
											</thead>

											<tbody>

											</tbody>

										</table>
									</fieldset>

									<!-- Customer Name and Technician Name -->
									<input type="hidden" id="fromCustomerName"
										name="fromCustomerName" class="form-control"
										value="${customerName}" /> <input type="hidden"
										id="fromTechnicianName" name="fromTechnicianName"
										class="form-control" value="${technicianName.email}" />

									<!-- part Number and Quantity Entered -->
									<input type="hidden" id="quantityList" name="quantityList"
										class="form-control" value="" /> <input type="hidden"
										id="partNumberList" name="partNumberList" class="form-control"
										value="" />

									<div class="form-group row">
										<div class="col-sm-offset-2 col-sm-8">
											<br> <br> <input type="button" value="Move Spares"
												class="moveSparesSubmit btn-primary btn-block btn-lg"
												tabindex="9" name="moveSparesSubmit" data-toggle="modal"
												data-target="#moveParts"
												onclick="return confirm('You are about to move spares, are you sure?');">
										</div>
									</div>

									<div id="moveParts" class="modal fade" role="dialog"
										aria-hidden="true">
										<div class="modal-dialog modal-lg">

											<div class="modal-content">

												<div class="modal-header">
													<button type="button" class="close" data-dismiss="modal"
														aria-hidden="true">×</button>
													<h3 class="modal-title">Details</h3>
												</div>

												<div class="modal-body">

													<div class="well form-horizontal">
														<div class="row">
															<!--First Column-->
															<div class="col-md-4">

																<legend align="left" style="font-size: 12px;">Tick
																	For Part Movement</legend>

																<div class="btn-group" data-toggle="buttons">
																	<label class="btn btn-primary"> <input
																		type="radio" name="options"
																		id="moveFromSiteStockToSiteStock" autocomplete="off"
																		value="moveFromSite" class="trigger"
																		data-rel="moveFromSiteStockToSiteStock">Site
																	</label>
																</div>
																<br> <br>

																<div class="btn-group" data-toggle="buttons">
																	<label class="btn btn-primary"> <input
																		type="radio" name="options"
																		id="moveFromSiteStockToBootStock" autocomplete="off"
																		value="moveFromBootStock" class="trigger"
																		data-rel="moveFromSiteStockToBootStock">Boot
																	</label>
																</div>
																<br> <br>

																<div class="btn-group" data-toggle="buttons">
																	<label class="btn btn-primary"> <input
																		type="radio" name="options"
																		id="moveFromSiteToHeadOffice" autocomplete="off"
																		value="moveFromSiteToHeadOffice" class="trigger"
																		data-rel="moveFromSiteStockToHO">Head Office
																	</label>
																</div>
																<br> <br>


															</div>

															<!-- move From Site Stock To Site Stock -->
															<div class="moveFromSiteStockToSiteStock tick">

																<!--Second Column-->
																<div class="col-md-8">
																	<legend style="font-size: 12px;">Select
																		Customer </legend>

																	<div class="form-group">
																		<label class="col-md-4 control-label">Move To</label>
																		<div class="col-md-8 selectContainer">
																			<div class="input-group">
																				<span class="input-group-addon"><i
																					class="glyphicon glyphicon-list"></i></span> <select
																					id="customerName" name="customerName"
																					class="form-control">
																					<option value="">Select Customer</option>
																					<c:forEach items="${customerList}" var="customer">
																						<option value="${customer.customerName}">${customer.customerName}</option>

																					</c:forEach>
																				</select>
																			</div>
																		</div>
																	</div>

																	<div class="form-group">
																		<label class="col-md-4 control-label">Reason</label>
																		<div class="col-md-8 selectContainer">
																			<div class="input-group">
																				<span class="input-group-addon"><i
																					class="glyphicon glyphicon-edit"></i></span>
																				<textarea id="reason" name="reasonForSite"
																					class="form-control"></textarea>
																			</div>
																		</div>
																	</div>
																</div>
															</div>

															<!-- move From Site Stock To Boot Stock -->
															<div class="moveFromSiteStockToBootStock tick">


																<div class="col-md-8">
																	<legend style="font-size: 12px;">Select
																		Technician </legend>

																	<div class="form-group">
																		<label class="col-md-4 control-label">Move To</label>
																		<div class="col-md-8 selectContainer">
																			<div class="input-group">
																				<span class="input-group-addon"><i
																					class="glyphicon glyphicon-list"></i></span> <select
																					id="technicianName" name="technicianName"
																					class="form-control">
																					<option value="">Select Technician</option>
																					<c:forEach items="${technicianList}"
																						var="technician">
																						<option value="${technician.email}">${technician.firstName}
																							${technician.lastName}</option>

																					</c:forEach>

																				</select>
																			</div>
																		</div>
																	</div>

																	<div class="form-group">
																		<label class="col-md-4 control-label">Reason</label>
																		<div class="col-md-8 selectContainer">
																			<div class="input-group">
																				<span class="input-group-addon"><i
																					class="glyphicon glyphicon-edit"></i></span>
																				<textarea id="reason" name="reasonForBoot"
																					class="form-control"></textarea>

																			</div>
																		</div>
																	</div>
																</div>
															</div>
															
															<!-- move From Site Stock To HO -->
															<div class="moveFromSiteStockToHO tick">


																<div class="col-md-8">
																	<legend style="font-size: 12px;">Select Head
																		Office </legend>

																	<div class="form-group">
																		<label class="col-md-4 control-label">Move To</label>
																		<div class="col-md-8 selectContainer">
																			<div class="input-group">
																				<span class="input-group-addon"><i
																					class="glyphicon glyphicon-list"></i></span> <select
																					id="headOffice" name="headOffice"
																					class="form-control">
																					<option value="">Select</option>
																					<option value="Head Office">Head Office</option>
																				</select>
																			</div>
																		</div>
																	</div>

																	<div class="form-group">
																		<label class="col-md-4 control-label">Reason</label>
																		<div class="col-md-8 selectContainer">
																			<div class="input-group">
																				<span class="input-group-addon"><i
																					class="glyphicon glyphicon-edit"></i></span>
																				<textarea id="reason" name="reasonForHeadOffice"
																					class="form-control"></textarea>

																			</div>
																		</div>
																	</div>
																</div>
															</div>
														</div>

													</div>
												</div>
												<!-- modal-body -->
												<div class="modal-footer">
													<button type="button" class="btn btn-default"
														data-dismiss="modal">Cancel</button>
													<button type="submit"
														class="moveSparesSubmit btn btn-default">Submit</button>
												</div>
											</div>
											<!-- /.modal-content -->
										</div>
										<!-- /.modal-dialog -->
									</div>
									<!-- /.modal moveParts-->
							</c:if>

							<!-- Movement History Details -->
							<c:if test="${empty customerName}">
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
										<c:forEach var="list" items="${displaySiteStockMovement}">
											<tr>
												<td>${list.userName}</td>
												<td>${list.dateTime}</td>
												<td>${list.dataField1}</td>
												<td>${list.dataField2}</td>
												<td>${list.quantity}</td>
												<td>${list.description}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>

							</c:if>
							<!-- End Movement History Details -->

						</form:form>

					</div>
					<!-- //tab content -->

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
	<c:import url="templates/ticketmanagementscript.jsp"></c:import>
	<c:import url="templates/sidebar-collapse.jsp"></c:import>
	<c:import url="templates/sitesparesscript.jsp"></c:import>
	<!-- /Scripts -->
	<script>
		$('.trigger').change(function() {
			$('.tick').hide();
			$('.' + $('.trigger:checked').data('rel')).show();
		}).change(); //Show content on page load
	</script>
</body>
</html>
