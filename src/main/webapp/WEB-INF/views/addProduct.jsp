<%@ include file="templates/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Add Device | Velaphanda Trading & Projects</title>

<c:import url="templates/stylesheetlib.jsp"></c:import>
<c:import url="templates/devicestyle.jsp"></c:import>

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
					<div class="panel-heading" align="center">Device Installation</div>
					<div class="panel-body">

						<form:form class="well form-horizontal" method="POST"
							action="saveProduct" modelAttribute="saveProduct" id="addDevice">

							<!--Customer Details-->
							<fieldset>
								<legend style="font-size: 18px;">Customer Details</legend>

								<!--First column-->
								<div class="col-sm-6">
									<!-- Text input Client Name-->
									
																			
									<div id="customerDeviceContainer"
										style="width: auto; display: table;">
										<div class="customerDeviceAddressTitle">
											<p class="customerAddressTitle">Customer Name</p>
											<ul class="addressDeviceList" style="display: block;">
												<li id="customerName">${customer.customerName}</li>
												
											</ul>
										</div>										
										<input type="hidden" id="customerName" name="customerName" value="${customer.customerName}"/>
									</div>
										
									
								</div>

								<div class="col-sm-6">

									<div id="customerDeviceContainer"
										style="width: auto; display: table;">
										<div class="customerDeviceAddressTitle">
											<p class="customerAddressTitle">Customer Address</p>
											<ul class="addressDeviceList" style="display: block;">
												<li id="streetName">${customer.streetNumber}
													${customer.streetName}</li>
												<li id="city_town">${customer.city_town}</li>
												<li id="zipcode">${customer.zipcode}</li>
											</ul>
										</div>
									</div>
								</div>
								<br />

							</fieldset>
							<!--Customer Details-->

							<!-- Contact Person  -->
							<fieldset>
								<legend style="font-size: 18px;">Contact Person</legend>

								<!--First Column-->
								<div class="col-sm-6">
									<!-- Text input Contact Person First Name-->
									<div class="form-group">
										<label class="col-md-3 control-label">First Name</label>
										<div class="col-md-8 inputGroupContainer">
											<div class="input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-user"></i></span> <input id="firstName"
													name="firstName" placeholder="First Name"
													class="form-control" type="text">
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
													class="form-control" type="text">
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
													class="form-control" type="email">
											</div>
										</div>
									</div>

								</div>
								<!--//First Column-->

								<!--Second Column-->
								<div class="col-sm-6">
									<!-- Text input Contact Person Cellphone Number-->
									<div class="form-group">
										<label class="col-md-3 control-label">Cellphone No</label>
										<div class="col-md-8 inputGroupContainer">
											<div class="input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-earphone"></i></span> <input
													id="cellphoneNumber" maxlength="10" name="cellphone"
													placeholder="Cellphone No" class="form-control" type="text"
													onkeypress="return isNumber(event)">
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
													id="telephoneNumber" maxlength="10" name="telephone"
													placeholder="Telephone No" class="form-control" type="text"
													onkeypress="return isNumber(event)">
											</div>
										</div>
									</div>
								</div>
								<!--/Second Column-->

							</fieldset>
							<!-- /Contact Person  -->

							<!-- /Machine Details  -->
							<fieldset>
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
													onkeydown="upperCaseF(this)" placeholder="Serial Number"
													class="form-control" type="text">
											</div>
										</div>
									</div>
									
									<!-- Text input Model No-->
									<div class="form-group">
											<label class="col-md-3 control-label">Model No</label>
											<div class="col-md-8 inputGroupContainer">
												<div class="input-group">
													<span class="input-group-addon"><i
														class="glyphicon glyphicon-hdd"></i></span> <input
														name="modelNumber" list="modelNumbers"
														onkeydown="upperCaseF(this)" id="modelNumber"
														class="form-control" type="text"
														placeholder='Search Model Number'>
												</div>
											</div>
											<!-- Iterating over the list sent from Controller -->
											<datalist id="modelNumbers"> <c:forEach var="list"
												items="${modelNumbers}">
												<option value="${list}">
											</c:forEach> </datalist>
										
									</div>

									<!-- Select type Brand-->
									<div class="form-group">
										<label class="col-md-3 control-label">Brand</label>
										<div class="col-md-8 selectContainer">
											<div class="input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-list"></i></span> <select
													id="modelBrand" name="modelBrand" class="form-control">
													<option value="">Select Brand</option>
													<option value="Canon">Canon</option>												
													<option value="HP">HP</option>
													<option value="Kyocera">Kyocera</option>
													<option value="Nasua">Nasua</option>
													<option value="Oce">Oce</option>
													<option value="Riso">Riso</option>
													<option value="Samsung">Samsung</option>
													<option value="Toshiba">Toshiba</option>
												</select>
											</div>
										</div>
									</div>

									<!-- Text input Contract Start Date-->
									<div class="form-group ">
										<label class="col-xs-3 control-label">Contract Start
											Date</label>
										<div class="col-md-8 inputGroupContainer">
											<div class="input-group input-append date"
												id="startDatePicker">
												<input type="text" class="form-control" name="startDate"
													id="startDate" placeholder="YYYY-MM-DD"> <span
													class="input-group-addon"> <span
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
												<input type="text" class="form-control" name="endDate"
													id="endDate" placeholder="YYYY-MM-DD"> <span
													class="input-group-addon"> <span
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
												<input type="text" class="form-control"
													id="installationDate" name="installationDate"
													placeholder="YYYY-MM-DD"> <span
													class="input-group-addon"> <span
													class="glyphicon glyphicon-calendar"></span>
												</span>
											</div>
										</div>
									</div>
									<!-- Select type Mono/Colour-->
									<div class="form-group">
										<label class="col-md-3 control-label">Mono/Colour</label>
										<div class="col-md-8 selectContainer">
											<div class="input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-list"></i></span> <select
													id="monocolour" name="colour" class="form-control"
													onchange="CheckColors(this.value);">
													<option value="">Select Mono/Colour</option>
													<option value="mono">Mono</option>
													<option value="colour">Colour</option>
												</select>
											</div>
										</div>
									</div>

									<!-- Both mono and colour reading  -->
									<div class="colour" id="colour" style="display: none;">

										<!-- Text checkbox Colour Reading-->
										<div class="form-group">
											<label class="col-md-3 control-label">Colour Reading</label>
											<div class="col-md-8">
												<input type="text" class="form-control"
													onkeypress="return isNumber(event)"
													placeholder="Enter Colour Reading" id="colour"
													name="colourReading">
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
														placeholder="Enter Colour Copy Cost" id="colour"
														name="colourCopyCost" />
												</div>
											</div>
											<br>
										</div>

									</div>
									<!-- Both mono and colour reading  -->

									<!-- Only mono Reading -->
									<!-- Text checkbox Mono Reading-->
									<div class="mono" id="mono" style="display: none;">

										<div class="form-group">
											<label class="col-md-3 control-label">Mono Reading</label>
											<div class="col-md-8">
												<input type="text" class="form-control"
													onkeypress="return isNumber(event)" id="mono"
													name="monoReading" placeholder="Enter Mono Reading">
											</div>
										</div>

										<!-- Text checkbox Mono Copy Cost-->
										<div class="form-group">
											<label class="col-md-3 control-label">Mono Copy Cost</label>
											<div class="col-md-8">
												<div class="input-group">
													<span class="input-group-addon"><i
														class="glyphicon glyphicon">$</i></span> <input type="number"
														min="0" step="0.01" data-number-to-fixed="2"
														data-number-stepfactor="100" class="form-control currency"
														placeholder="Enter Mono Copy Cost" id="colour"
														name="monoCopyCost">
												</div>
											</div>
											<br>
										</div>

									</div>
									<!-- //Only mono Reading -->

								</div>
								<!--/First Column-->

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
													type="text">
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
													class="form-control" type="text">
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
													type="text">
											</div>
										</div>
									</div>
									<!--/S Street Number-->
									<!-- Text input Street Name-->
									<div class="form-group">
										<label class="col-md-3 control-label">Street Name</label>
										<div class="col-md-8 inputGroupContainer">
											<div class="input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-home"></i></span> <input
													id="streetName" name="streetName" placeholder="Street Name"
													class="form-control" type="text">
											</div>
										</div>
									</div>

									<!-- Text input City or Town-->
									<div class="form-group ">
										<label class="col-md-3 control-label">City/Town</label>
										<div class="col-md-8 inputGroupContainer">
											<div class="input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-home"></i></span> <input
													name="city_town" id="city_town" placeholder="City / Town"
													class="form-control" type="text">
											</div>
										</div>
									</div>

									<!-- Select type Province-->
									<div class="form-group ">
										<label class="col-md-3 control-label">Province</label>
										<div class="col-md-8 selectContainer">
											<div class="input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-list"></i></span> <select
													name="province" id="province"
													class="form-control selectpicker">
													<option value="">Select Province</option>
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
													onkeypress="return isNumber(event)">
											</div>
										</div>
									</div>

								</div>

							</fieldset>
							<!-- /Machine Details  -->


							<!--Machine Accessories-->
							<fieldset>
								<legend align="left" style="font-size: 18px;">Machine
									Accessories</legend>

								<div class="tablemachinesacccso">
									<table id="accessories"
										class="table table-striped table-bordered table-hover table-condensed">

										<thead>
											<tr>
												<th>Machine Type</th>
												<th>Serial Number</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td><input type="checkbox" id="bridgeunitserial"
													name="bridgeUnitSerialType" value="Bridge unit">
													Bridge unit</td>
												<td><input type="text" class="form-control"
													onkeydown="upperCaseF(this)" id="bridgeunit"
													name="bridgeUnitSerialTypeSerialNo" disabled="disabled"></td>
											</tr>
											<tr>
												<td><input type="checkbox" class="select" id="finisher"
													name="finisherType" value="Finisher"> Finisher</td>
												<td><input type="text" class="form-control"
													onkeydown="upperCaseF(this)" id="finisherserial"
													name="finisherTypeSerialNo" disabled="disabled"></td>
											</tr>
											<tr>
												<td><input type="checkbox" class="select" id="faxunit"
													name="faxUnitSerialType"> Fax Unit</td>
												<td><input type="text" class="form-control"
													onkeydown="upperCaseF(this)" id="faxunitserial"
													name="faxUnitSerialTypeSerialNo" disabled="disabled"></td>
											</tr>
											<tr>
												<td><input type="checkbox" class="select"
													id="onebintrayserial" name="oneBinSerialType"> One
													bin tray</td>
												<td><input type="text" class="form-control"
													onkeydown="upperCaseF(this)" id="onebintray"
													name="OneBinTrayTypeSerialNo" disabled="disabled"></td>
											</tr>
											<tr>
												<td><input type="checkbox" class="select"
													id="ltcserial" name="ltcType"> LCT</td>
												<td><input type="text" class="form-control"
													onkeydown="upperCaseF(this)" id="lct" name="ltcTypeSerial"
													disabled="disabled"></td>
											</tr>
											<tr>
												<td><input type="checkbox" class="select"
													id="creserial" name="creType"> Credenza</td>
												<td><input type="text" class="form-control"
													onkeydown="upperCaseF(this)" id="cre" name="creTypeserial"
													disabled="disabled"></td>
											</tr>
											<tr>
												<td><input type="checkbox" class="select"
													id="addserial" name="addType"> Additional paper
													trays</td>
												<td><input type="text" class="form-control"
													onkeydown="upperCaseF(this)" id="add" name="addTypeserial"
													disabled="disabled"></td>
											</tr>
										</tbody>
									</table>
								</div>

								<!-- Other Machine Accessories -->
								<div class="otherMachineAccessories"
									id="otherMachineAccessories">
									<br />
									<br />
									<h5>Other Machine Accessories</h5>
									<p>
										<input type="button" class="btn btn-success" value="Add More">
									</p>
									<table id="addOtherMachineAccessories"
										class="table table-striped table-bordered table-hover table-condensed">
										<thead>
											<tr>
												<th>Machine Type</th>
												<th>Serial Number</th>
												<th>Delete</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td><input type="text" class="form-control"
													id="machineType" name="machineType"
													placeholder="Machine Accessory Type" /></td>
												<td><input type="text" class="form-control"
													id="serialNumberOtherAccessory"
													name="serialNumberOtherAccessory"
													onkeydown="upperCaseF(this)" placeholder="Serial Number" />
												</td>
												<td><input type="button" id="remove" name="remove"
													class="btn btn-danger" value="Remove"></td>
											</tr>
										<tbody>
									</table>

								</div>
								<!-- //Other Machine Accessories -->

							</fieldset>
							<!--//Machine Accessories-->

							<div class="form-group row">
								<div class="col-sm-offset-2 col-sm-8">
									<br> <br> <input type="submit" id="addProduct"
										name="addProduct" value="Add Device"
										class="btn btn-primary btn-block btn-lg" tabindex="9"
										id="updateProduct"
										onclick="return confirm('Are you sure you want to Add Device?');">
								</div>
							</div>

						</form:form>
						<!-- /form-content -->
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
	</div>
	<!--/.main-->
	<!-- Scripts -->
	<c:import url="templates/javascriptslib.jsp"></c:import>
	
	<script>
	/*---Create datalist to populate search---*/

	//Get the <datalist> and <input> elements.
	var dataList = document.getElementById('json-datalist');
	var input = document.getElementById('ajax');

	//Create a new XMLHttpRequest.
	var request = new XMLHttpRequest();

	//Handle state changes for the request.
	request.onreadystatechange = function(response) {
		if (request.readyState === 4) {
			if (request.status === 200) {
				// Parse the JSON
				var jsonOptions = JSON.parse(request.responseText);

				// Loop over the JSON array.
				jsonOptions.forEach(function(item) {
					// Create a new <option> element.
					var option = document.createElement('option');
					// Set the value using the item in the JSON array.
					option.value = item;
					// Add the <option> element to the <datalist>.
					var appendChild = "Lets See";
					console.log("WE SEE JUSES:",dataList.appendChild(option));
					dataList.appendChild(option);
					console.log("WE SEE JUSES:",option);
				});

				// Update the placeholder text.
				input.placeholder = "e.g. datalist";
			} else {
				// An error occured :(
				input.placeholder = "Couldn't load datalist options :(";
			}
		}
	};

	//Update the placeholder text.
	var input = "Loading options";
	input.placeholder = "Loading options...";
	console.log("What do we have here : ", input.placeholder);
	console.log("Mine : ", input);

	//Set up and make the request.
	request.open('GET',
			'https://s3-us-west-2.amazonaws.com/s.cdpn.io/4621/html-elements.json',
			true);
	request.send();

	/*---End Create datalist to populate search---*/
	</script>
	<c:import url="templates/sidebar-collapse.jsp"></c:import>
	<!-- /Scripts -->
</body>
</html>