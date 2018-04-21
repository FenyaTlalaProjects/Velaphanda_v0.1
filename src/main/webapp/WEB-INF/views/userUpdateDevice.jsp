<%@ include file="templates/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>View Device | Velaphanda Trading & Projects</title>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<c:import url="templates/devicestyle.jsp"></c:import>
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
		<c:if test="${not empty retMessage }">
			<div class="alert alert-info" role="alert">
				<c:out value="${ retMessage}">
				</c:out>
			</div>
		</c:if>
		<c:if test="${not empty retErrorMessage }">
			<div class="alert alert-danger" role="alert">
				<c:out value="${ retErrorMessage}">
				</c:out>
			</div>
		</c:if>

		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading" align="center">View Device</div>
					<div class="panel-body">

						<!--Search-->
						<form:form class="well form-horizontal" method="POST"
							action="updateProduct" modelAttribute="updateProduct"
							id="updateDevice">

							<fieldset>
								<!-- Customer Details  -->
								<legend style="font-size: 18px;">Customer Details</legend>
								<!--First column-->
								<div class="col-sm-6">

									<!-- Text input Client Name-->
									<div class="form-group">
										<label class="col-md-3 control-label">Customer Name</label>
										<div class="col-md-8 inputGroupContainer">
											<div class="input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-user"></i></span> <input type="text"
													id="customerName" name="customerName" readonly="readonly"
													placeholder="Client Name" class="form-control"
													value="${productObject.customerDevice.customerName}">
											</div>
										</div>
									</div>
								</div>

								<div class="col-sm-6">

									<div id="customerDeviceContainer"
										style="width: auto; display: table;">
										<div class="customerDeviceAddressTitle">
											<p class="customerAddressTitle">Customer Address</p>
											<ul class="addressDeviceList" style="display: block;">
												<li id="streetName">${productObject.customerDevice.streetNumber}
													${productObject.customerDevice.streetName}</li>
												<li id="city_town">${productObject.customerDevice.city_town}</li>
												<li id="zipcode">${productObject.customerDevice.zipcode}</li>
											</ul>
										</div>
									</div>
								</div>

								<br>
							</fieldset>
							<!-- //Customer Details  -->

							<fieldset>
								<!-- Contact Person  -->
								<legend style="font-size: 18px;">Contact Person</legend>
								<!-- Contact Person  -->
								<div class="col-sm-6">

									<!-- Text input Contact Person First Name-->
									<div class="form-group">
										<label class="col-md-3 control-label">First Name</label>
										<div class="col-md-8 inputGroupContainer">
											<div class="input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-user"></i></span> <input id="firstName"
													name="firstName" placeholder="First Name"
													class="form-control" type="text"
													value="${productObject.contactPerson.firstName}" readonly="readonly">
											</div>
										</div>
									</div>
									<!-- Text input Contact Person  Last Name-->
									<div class="form-group">
										<label class="col-md-3 control-label">Last Name</label>
										<div class="col-md-8 inputGroupContainer">
											<div class="input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-user"></i></span> <input id="lastName"
													name="lastName" placeholder="Last Name"
													class="form-control" type="text"
													value="${productObject.contactPerson.lastName}" readonly="readonly">
											</div>
										</div>
									</div>

									<!-- Text input Contact Person 1 Email-->
									<div class="form-group">
										<label class="col-md-3 control-label">Email</label>
										<div class="col-md-8 inputGroupContainer">
											<div class="input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-envelope"></i></span> <input id="email"
													name="email" placeholder="Email Address"
													class="form-control" type="email"
													value="${productObject.contactPerson.email}" readonly="readonly">
											</div>
										</div>
									</div>
								</div>

								<div class="col-sm-6">

									<!-- Text input Contact Person Cellphone Number-->
									<div class="form-group">
										<label class="col-md-3 control-label">Cellphone No</label>
										<div class="col-md-8 inputGroupContainer">
											<div class="input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-earphone"></i></span> <input
													id="cellphoneNumber" name="cellphone"
													placeholder="Cellphone No" maxlength="10"
													class="form-control" type="text"
													onkeypress="return isNumber(event)"
													value="${productObject.contactPerson.cellphone}" readonly="readonly">
											</div>
										</div>
									</div>

									<!-- Text input Contact Person Tellphone Number-->
									<div class="form-group">
										<label class="col-md-3 control-label">Tellphone No</label>
										<div class="col-md-8 inputGroupContainer">
											<div class="input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-earphone"></i></span> <input
													id="tellphoneNumber" name="telephone"
													placeholder="Tellphone No" maxlength="10"
													class="form-control" type="text"
													onkeypress="return isNumber(event)"
													value="${productObject.contactPerson.telephone}" readonly="readonly">

											</div>
										</div>
									</div>

								</div>
								<!-- /Contact Person  -->

							</fieldset>
							<!-- //Contact Person  -->


							<fieldset>
								<!-- Device Details  -->
								<legend style="font-size: 18px;">Machine Details</legend>

								<!--First Column-->
								<div class="col-sm-6">

									<!-- Text input Serial No-->
									<div class="form-group">
										<label class="col-md-3 control-label">Serial No</label>
										<div class="col-md-8 inputGroupContainer">
											<div class="input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-barcode"></i></span> <input
													name="serialNumber" id="serialNumber"
													onkeydown="upperCaseF(this)" readonly="readonly"
													placeholder="Serial Number" class="form-control"
													type="text" value="${productObject.serialNumber}" readonly="readonly">
											</div>
										</div>
									</div>
									<!-- Text input Machine Model-->
									<div class="form-group">
										<label class="col-md-3 control-label">Model No</label>
										<div class="col-md-8 inputGroupContainer">
											<div class="input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-barcode"></i></span> <input
													name="modelNumber" id="modelNumber"
													onkeydown="upperCaseF(this)" placeholder="Model Number"
													class="form-control" type="text"
													value="${productObject.modelNumber}" readonly>
											</div>
										</div>
									</div>

									<!-- Select type Brand-->
									<div class="form-group">
										<label class="col-md-3 control-label">Brand</label>
										<div class="col-md-8 selectContainer">
											<div class="input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-list"></i></span> <select
													id="modelBrand" name="modelBrand" class="form-control" readonly="readonly">
													<option value="${productObject.modelBrand}">${productObject.modelBrand}</option>
													<option value="Samsung">Samsung</option>
													<option value="Canon">Canon</option>
													<option value="Oce">Oce</option>
													<option value="HP">HP</option>
													<option value="Kyocera">Kyocera</option>
													<option value="Nasua">Nasua</option>
													<option value="Ricoh">Ricoh</option>
													<option value="Toshiba">Toshiba</option>
												</select>
											</div>
										</div>
									</div>
									<!-- Text input Contract Start Date-->
									<div class="form-group">
										<label class="col-xs-3 control-label">Contract Start
											Date</label>
										<div class="col-md-8 inputGroupContainer">
											<div class="input-group input-append date"
												id="startDatePicker">
												<input type='text' class="form-control" name="startDate"
													id="startDate" placeholder="YYYY-MM-DD"
													value="${productObject.startDate.toString().substring(0,10) }" readonly="readonly"/>
												<span class="input-group-addon"> <span
													class="glyphicon glyphicon-calendar"></span>
												</span>
											</div>
										</div>
									</div>
									<!-- Text input Contract End Date-->
									<div class="form-group">
										<label class="col-md-3 control-label">Contract End
											Date</label>
										<div class="col-md-8 inputGroupContainer">
											<div class="input-group input-append date" id="endDatePicker">
												<input type='text' class="form-control" name="endDate"
													id="endDate" placeholder="YYYY-MM-DD"
													value="${productObject.endDate.toString().substring(0,10) }" readonly="readonly"/>
												<span class="input-group-addon"> <span
													class="glyphicon glyphicon-calendar"></span>
												</span>
											</div>
										</div>
									</div>
									<!-- Text input Installation Date-->
									<div class="form-group">
										<label class="col-md-3 control-label">Installation
											Date</label>
										<div class="col-md-8 inputGroupContainer">
											<div class="input-group input-append date"
												id="installDatePicker">
												<input type='text' class="form-control"
													id="installationDate" name="installationDate" id="endDate"
													placeholder="YYYY-MM-DD"
													value="${productObject.installationDate.toString().substring(0,10) }" readonly="readonly"/>
												<span class="input-group-addon"> <span
													class="glyphicon glyphicon-calendar"></span>
												</span>
											</div>
										</div>
									</div>
									<c:if test="${not empty productObject.monoReading }">
										<div class="form-group">
											<label class="col-md-3 control-label">Mono Reading</label>
											<div class="col-md-8">
												<input type="text" class="form-control"
													onkeypress="return isNumber(event)" id="mono"
													name="monoReading" placeholder="Enter Mono Reading"
													value="${productObject.monoReading}" readonly="readonly">
											</div>
										</div>
										<div class="form-group">
											<label class="col-md-3 control-label">Mono Copy Cost</label>
											<div class="col-md-8">
												<div class="input-group">
													<span class="input-group-addon"><i
														class="glyphicon glyphicon">$</i></span> <input type="number"
														min="0" step="0.01" data-number-to-fixed="2"
														data-number-stepfactor="100" class="form-control currency"
														placeholder="Enter Mono Copy Cost" id="colour"
														name="monoCopyCost" value="${productObject.monoCopyCost}" readonly="readonly">
												</div>
											</div>
											<br>
										</div>
									</c:if>
									<c:if test="${not empty productObject.colourReading}">
										<!-- Text checkbox Colour Reading-->
										<div class="form-group">
											<label class="col-md-3 control-label">Colour Reading</label>
											<div class="col-md-8">
												<input type="text" class="form-control"
													onkeypress="return isNumber(event)" id="colour"
													name="colourReading" placeholder="Enter Colour Reading"
													value="${productObject.colourReading}" readonly="readonly">
											</div>
											<br>
										</div>
										<!-- Text checkbox Colour Copy Cost-->
										<div class="form-group">
											<label class="col-md-3 control-label">Colour Copy
												Cost</label>
											<div class="col-md-8">
												<div class="input-group">
													<span class="input-group-addon"><i
														class="glyphicon glyphicon">$</i></span> <input type="number"
														min="0" step="0.01" data-number-to-fixed="2"
														data-number-stepfactor="100" class="form-control currency"
														placeholder="Enter Mono Copy Cost" id="colour"
														name="colourCopyCost"
														value="${productObject.colourCopyCost}" readonly="readonly"/>
												</div>
											</div>
											<br>
										</div>
									</c:if>
								</div>
								<!--/F Column-->

								<!--Second column-->
								<div class="col-sm-6">

									<!-- Text input Building Name-->
									<div class="form-group">
										<label class="col-md-3 control-label">Building Name</label>
										<div class="col-md-8 inputGroupContainer">
											<div class="input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-home"></i></span> <input
													name="buildingName" id="buildingName"
													placeholder="Building Name" class="form-control"
													type="text" value="${productObject.buildingName}" readonly="readonly">
											</div>
										</div>
									</div>
									<!-- Text input Floor Number-->
									<div class="form-group">
										<label class="col-md-3 control-label">Floor Number</label>
										<div class="col-md-8 inputGroupContainer">
											<div class="input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-home"></i></span> <input
													name="floorNumber" id="floorNumber"
													placeholder="Floor Number" maxlength="4"
													class="form-control" type="text"
													value="${productObject.floorNumber}" readonly="readonly">
											</div>
										</div>
									</div>

									<!-- Text input Street Number-->
									<div class="form-group">
										<label class="col-md-3 control-label">Street No</label>
										<div class="col-md-8 inputGroupContainer">
											<div class="input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-home"></i></span> <input
													name="streetNumber" id="streetNumber"
													placeholder="Street Number" class="form-control"
													type="text" value="${productObject.streetNumber}" readonly="readonly">
											</div>
										</div>
									</div>

									<!-- Text input Street Name-->
									<div class="form-group">
										<label class="col-md-3 control-label">Street Name</label>
										<div class="col-md-8 inputGroupContainer">
											<div class="input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-home"></i></span> <input
													id="streetName" name="streetName" placeholder="Street Name"
													class="form-control" type="text"
													value="${productObject.streetName}" readonly="readonly">
											</div>
										</div>
									</div>
									<!-- Text input City or Town-->
									<div class="form-group">
										<label class="col-md-3 control-label">City/Town</label>
										<div class="col-md-8 inputGroupContainer">
											<div class="input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-home"></i></span> <input
													name="city_town" id="city_town" placeholder="City / Town"
													class="form-control" type="text"
													value="${productObject.city_town}" readonly="readonly">
											</div>
										</div>
									</div>
									<!-- Select type Province-->
									<div class="form-group">
										<label class="col-md-3 control-label">Province</label>
										<div class="col-md-8 selectContainer">
											<div class="input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-list"></i></span> <select
													name="province" id="province"
													class="form-control selectpicker" readonly="readonly">
													<option value="${productObject.province}">${productObject.province}</option>
													<option value="Gauteng">Gauteng</option>
													<option value="Limpopo">Limpopo</option>
													<option value="North West">North West</option>
													<option value="Free State">Free State</option>
													<option value="Mpumalanga">Mpumalanga</option>
													<option value="KwaZulu Natal">KwaZulu Natal</option>
													<option value="Northern Cape">Northern Cape</option>
													<option value="Eastern Cape">Eastern Cape</option>
													<option value="Western Cape">Western Cape</option>
												</select>
											</div>
										</div>
									</div>
									<!-- Text input Area Code-->
									<div class="form-group">
										<label class="col-md-3 control-label">Area Code</label>
										<div class="col-md-8 inputGroupContainer">
											<div class="input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-home"></i></span> <input name="zipcode"
													id="zipcode" placeholder="Area Code" maxlength="4"
													class="form-control" type="text"
													onkeypress="return isNumber(event)"
													value="${productObject.areaCode}" readonly="readonly">
											</div>
										</div>
									</div>

								</div>
								<!--/S Column-->

							</fieldset>
							<!-- //Device Details  -->

							<fieldset>
								<!-- Machine Accessories -->
								<legend align="left" style="font-size: 18px;">Machine
									Accessories</legend>
								

								<div class="row">

									<div class="col-sm-12">

										<c:if test="${not empty accessories}">
											<form:form class="well form-horizontal"
												modelAttribute="removeAccessory" action="removeAccessory"
												method="post">
												<!--Machine Accessories-->
												<div class="tablemachinesacccso" name="removeAccessory">
													<br />
													<br />
													<h5>Remove Accessories</h5>
													<p style="font-size:12px; color:red;">To remove accessory, check the checkbox</p>
													<table
														class="table table-striped table-bordered table-hover table-condensed"
														data-show-refresh="true" data-show-toggle="true"
														data-search="true" data-pagination="true"
														data-sort-name="machinetype" data-sort-order="aesc">
														<thead>
															<tr>
																<th data-field="machinetype" data-sortable="true">Machine
																	Type</th>
																<th data-field="serialnumber" data-sortable="true">Serial
																	Number</th>
																<th data-field="removeaccessory" data-sortable="true">Remove
																	Accessory</th>
															</tr>
														</thead>
														<tbody>
															<!-- Iterating over the list sent from Controller -->
															<c:forEach var="list" items="${accessories}">
																<tr>
																	<td>${list.accessotyType}</td>
																	<td>${list.serial}</td>
																	<td><input type="checkbox" class="chkAccessories"
																		id="${list.accessotyType}" name="chkAccessories"
																		value="${list.recordID}" /></td>
																</tr>
															</c:forEach>

														</tbody>

													</table>

												</div>
											</form:form>

										</c:if>
										<!--Machine Accessories-->
									</div>

								</div>

								<!-- <div id="removeAccessory" class="form-group row">
									<div class="col-sm-offset-2 col-sm-8">
										<br> <br> <input type="submit" id="removeAccessory"
											name="removeAccessory" value="Remove Accessory"
											class="btn btn-danger btn-block btn-lg" tabindex="9"
											onclick="return confirm('Are you sure you want to delete this?');">
									</div>
								</div>
								<div id="hideUpdateProduct" class="hideUpdateProduct">
									<div class="form-group row">
										<div class="col-sm-offset-2 col-sm-8">
											<br> <br> <input type="submit" id="updateProduct"
												name="updateProduct" value="Update Device"
												class="btn btn-primary btn-block btn-lg" tabindex="9"
												id="updateProduct"
												onclick="return confirm('Are you sure you want to update device?');">
										</div>
									</div>
								</div> -->

							</fieldset>
							<!-- //Machine Accessories -->

						</form:form>

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
	<!-- scripts -->
	<c:import url="templates/javascriptslib.jsp"></c:import>
	<c:import url="templates/updatedevicescript.jsp"></c:import>
	<c:import url="templates/sidebar-collapse.jsp"></c:import>
	<!-- /scripts -->
</body>
</html>

