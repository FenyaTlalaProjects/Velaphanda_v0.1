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
									<div id="customerDeviceContainer"
										style="width: auto; display: table;">
										<div class="customerDeviceAddressTitle">
											<p class="customerAddressTitle">Customer Name</p>
											<ul class="addressDeviceList" style="display: block;">
												<li id="customerName">${productObject.customerDevice.customerName}</li>
												
											</ul>
										</div>
										<input type="hidden" id="customerName" name="customerName" value="${productObject.customerDevice.customerName}"/>
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
										<label class="col-md-3" >First Name</label>
										${productObject.contactPerson.firstName}											
									</div>
									<!-- Text input Contact Person  Last Name-->
									<div class="form-group">
										<label class="col-md-3">Last Name</label>
										${productObject.contactPerson.lastName}											
									</div>

									<!-- Text input Contact Person 1 Email-->
									<div class="form-group">
										<label class="col-md-3">Email</label>
										${productObject.contactPerson.email}"
									</div>
								</div>

								<div class="col-sm-6">

									<!-- Text input Contact Person Cellphone Number-->
									<div class="form-group">
										<label class="col-md-3 ">Cellphone No</label>
										
											${productObject.contactPerson.cellphone}
											
										</div>
									
									<!-- Text input Contact Person Tellphone Number-->
									<div class="form-group">
										<label class="col-md-3 ">Tellphone No</label>
										${productObject.contactPerson.telephone}
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
										<label class="col-md-4">Serial No</label>
										${productObject.serialNumber}
									</div>
									<!-- Text input Machine Model-->
									<div class="form-group">
										<label class="col-md-4">Model No</label>
										${productObject.modelNumber}
									</div>
									<!-- Select type Brand-->
									<div class="form-group">
										<label class="col-md-4">Brand</label>
										${productObject.modelBrand}">${productObject.modelBrand}
									</div>									
									<!-- Text input Contract Start Date-->
									<div class="form-group">
										<label class="col-xs-4">Contract Start
											Date</label>
										${productObject.startDate.toString().substring(0,10) }
									</div>									
									<!-- Text input Contract End Date-->
									<div class="form-group">
										<label class="col-md-4">Contract End
											Date</label>${productObject.endDate.toString().substring(0,10) }												
									</div>
									<!-- Text input Installation Date-->
									<div class="form-group">
										<label class="col-md-4 ">Installation
											Date</label>
										${productObject.installationDate.toString().substring(0,10) }
									</div>
									<c:if test="${not empty productObject.monoReading }">
										<div class="form-group">
											<label class="col-md-4 ">Mono Reading</label>
											
													${productObject.monoReading}
											
										</div>
										<div class="form-group">
											<label class="col-md-4 ">Mono Copy Cost</label>
											${productObject.monoCopyCost}
										</div>
											
									</c:if>
									<c:if test="${not empty productObject.colourReading}">
										<!-- Text checkbox Colour Reading-->
										<div class="form-group">
											<label class="col-md-4 ">Colour Reading</label>
											
											${productObject.colourReading}
											
											<br>
										</div>
										<!-- Text checkbox Colour Copy Cost-->
										<div class="form-group">
											<label class="col-md-4 ">Colour Copy
												Cost</label>
											
												${productObject.colourCopyCost}
												
											
										</div>
											<br>
									</c:if>
								</div>
								<!--/F Column-->

								<!--Second column-->
								<div class="col-sm-6">

									<!-- Text input Building Name-->
									<div class="form-group">
										<label class="col-md-4 ">Building Name</label>
										${productObject.buildingName}
									</div>
									<!-- Text input Floor Number-->
									<div class="form-group">
										<label class="col-md-4 ">Floor Number</label>
										${productObject.floorNumber}
									</div>

									<!-- Text input Street Number-->
									<div class="form-group">
										<label class="col-md-4 ">Street No</label>
										${productObject.streetNumber}
									</div>

									<!-- Text input Street Name-->
									<div class="form-group">
										<label class="col-md-4 ">Street Name</label>
										${productObject.streetName}
									</div>
									<!-- Text input City or Town-->
									<div class="form-group">
										<label class="col-md-4 ">City/Town</label>
										${productObject.city_town}
									</div>
									<!-- Select type Province-->
									<div class="form-group">
										<label class="col-md-4 ">Province</label>
										${productObject.province}">${productObject.province}
									</div>
									<!-- Text input Area Code-->
									<div class="form-group">
										<label class="col-md-4 ">Area Code</label>
										${productObject.areaCode}
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
													<!-- <br />
													<br />
													<h5>Remove Accessories</h5>
													<p style="font-size:12px; color:red;">To remove accessory, check the checkbox</p>
												 -->	<table
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
																<!-- <th data-field="removeaccessory" data-sortable="true">Remove
																	Accessory</th> -->
															</tr>
														</thead>
														<tbody>
															<!-- Iterating over the list sent from Controller -->
															<c:forEach var="list" items="${accessories}">
																<tr>
																	<td>${list.accessotyType}</td>
																	<td>${list.serial}</td>
																	<%-- <td><input type="checkbox" class="chkAccessories"
																		id="${list.accessotyType}" name="chkAccessories"
																		value="${list.recordID}" /></td> --%>
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

