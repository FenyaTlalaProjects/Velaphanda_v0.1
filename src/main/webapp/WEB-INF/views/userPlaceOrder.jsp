<%@ include file="templates/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Order | Velaphanda Trading & Projects</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<c:import url="templates/tableresizefont.jsp"></c:import>
<c:import url="templates/stylesheetlib.jsp"></c:import>
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
						<b>New Order</b>
					</div>
					<div class="panel-body">

						<c:if test="${not empty retMessage }">
							<div class="alert alert-info" role="alert">

								<c:out value="${ retMessage}">
								</c:out>
							</div>
						</c:if>
						<c:if test="${not empty retErrorMessage }">
							<div class="alert alert-info" role="alert">

								<c:out value="${ retErrorMessage}">
								</c:out>
							</div>
						</c:if>



						<div class="tab-content">

							<form:form class="well form-horizontal"
								modelAttribute="makeOrder" method="post" action="makeOrder"
								id="putInOrder">

								<!-- Select type Stock Type-->
								<div class="form-group">
									<label class="col-md-3 control-label">Stock Type</label>
									<div class="col-md-6 selectContainer">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-list"></i></span><select id="stockType"
												name="stockType" class="form-control"
												onchange='CheckStockType(this.value);'
												class="form-control selectpicker">
												<option value="">Select Stock Type</option>
												<option value="Boot">Boot</option>
												<option value="Site">Site</option>
											</select>
										</div>
									</div>
								</div>

								<div id="Site" style='display: none;'>
									<!-- Text input Customer Name-->
									<div class="form-group">
										<label class="col-md-3 control-label">Customer Name</label>
										<div class="col-md-6 inputGroupContainer">
											<div class="input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-user"></i></span><select id="Site"
													name="customer" class="form-control selectpicker">
													<option value="">Customer Name</option>
													<c:forEach items="${customerList}" var="customer">
														<option value="${customer.customerName}">${customer.customerName}</option>
													</c:forEach>
												</select>
											</div>
										</div>
									</div>
								</div>
								
								<div id="Boot" style='display: none;'>
								 <div class="form-group">
									<label class="col-md-3 control-label">Technician</label>
									<div class="col-md-6 selectContainer">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-list"></i></span> <select
												name="technicianUserName" id="technicianUserName"
												class="form-control selectpicker">
												<option value="">Select Technician</option>
												<c:forEach items="${technicianList}" var="technician">
													<option value="${technician.email}">${technician.firstName}
														${technician.lastName}</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>
								</div>

								<!-- Text input Approver-->
								<div class="form-group">
									<label class="col-md-3 control-label">Approver</label>
									<div class="col-md-6 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-user"></i></span><select name="approver"
												id="approver" class="form-control selectpicker">
												<option value="">Select Manager</option>
												<c:forEach items="${managersList}" var="approver">
													<option value="${approver.email}">${approver.firstName}
														${approver.lastName}</option>
												</c:forEach>
											</select>

										</div>
									</div>
								</div>


								<div id="makeOrders">
									
										<div class="col-sm-12">
										
										    <fieldset>
										    	<legend>
										    	<br/><br/>
										    		<h5 align="center">
														<b>Available Head Office Stock</b>
													</h5>
										    	</legend>		
													<table id="stockForOrder" data-toggle="table"
														data-url="${orderList}" data-show-refresh="true"
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
																<th data-field="customer" data-sortable="true">Available
																	QTY</th>
																<th data-field="provideqty" data-sortable="true">Provide
																	QTY</th>
																<th data-field="action" data-sortable="true">Action</th>
															</tr>
														</thead>

														<tbody>
															<c:forEach var="list" items="${compatibility}">
																<tr>
																	<td> ${list.partNumber}</td>
																	<td> ${list.itemDescription}</td>
																	<td> ${list.compitableDevice}</td>
																	<td><input type="text"
																		id="${list.partNumber}_avaliableQuantity"
																		name="avaliableQuantity" class="form-control"
																		readonly="readonly" value="${list.quantity}"></td>
																	<td><input type="text"
																		id="${list.partNumber}_quantity" name="quantity"
																		class="form-control"
																		onkeypress="return isNumber(event)"
																		onblur="compareQuantity(this, ${list.quantity})"
																		value="" /></td>
																	<td><input class="addLineItem" type="button"
																		value="Add"></td>
																</tr>
															</c:forEach>

														</tbody>
													</table>
													
													</fieldset>
													
												</div>
																						
											<div class="col-sm-12">
											
											    <fieldset>
										    	<legend>
										    	<br/>
										    			<h5 align="center">
														<b>Selected Order Line Items</b>
													</h5>
										    	</legend>
												
														<table id="toOrder" data-toggle="table" data-url="${orderList}"
															data-show-refresh="true" data-show-toggle="true"
															data-search="true" data-select-item-name="toolbar1"
															data-pagination="true" data-sort-name="partno"
															data-sort-order="desc">
															<thead>
																<tr>
																	<th data-field="partno" data-sortable="true">Part
																		No</th>
																	<th data-field="description" data-sortable="true">Description</th>
																	<th data-field="modelno" data-sortable="true">Model
																		No</th>
																	<th data-field="customer" data-sortable="true">Available
																	QTY</th>
																	<th data-field="qtyprovide" data-sortable="true">																	
																		QTY Provide</th>
																	<th data-field="action" data-sortable="true">Action</th>
																</tr>
															</thead>

															<tbody>
																
															</tbody>

														</table>
														</fieldset>
													</div>
												
									<!-- part Number and Quantity Entered -->
									<input type="hidden" id="quantityList" name="quantityList"
										class="form-control" value="" /> <input type="hidden"
										id="partNumberList" name="partNumberList" class="form-control"
										value="" />
								</div>
								<!-- //make order -->
								
									<div class="form-group row">
										<div class="col-sm-offset-2 col-sm-8">
											<br> <br> <input type="submit" value="Place Order" 
												 class="orderSubmit btn-primary btn-block btn-lg" tabindex="9"
												id="putorder" name="putorder"
												onclick="return confirm('Are you sure you want to place order?');">
										</div>
									</div>

							</form:form>

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
	<c:import url="templates/javascriptslib.jsp"></c:import>
	<c:import url="templates/ticketmanagementscript.jsp"></c:import>
	<c:import url="templates/sidebar-collapse.jsp"></c:import>
    <c:import url="templates/orderscript.jsp"></c:import>	
	<!-- /Scripts -->
</body>
</html>
