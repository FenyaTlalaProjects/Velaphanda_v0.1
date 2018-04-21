<%@ include file="templates/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Detailed Product | Velaphanda Trading & Projects</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/custom/css/vela_details.css" />" />
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


		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading" align="center">Machine Details</div>
					<div class="panel-body">

						<div align="right">
							<a target="_blank"
								href="deviceDetailsDownloadPDF?serialNumber=<c:out value='${device.serialNumber}'/>">Download
								PDF </a>
						</div>

						<div class="col-sm-6">

							<legend style="font-size: 18px; ">Machine
								Details</legend>

							<div class="machinedetailsdetailsfloatleft">
								<label id="customerName" name="customerName">Customer
									Name </label> <br> <label id="serialNumber" name="serialNumber">Serial
									No </label> <br> <label id="modelNumber" name="modelNumber">Model
									Number </label> <br> <label id="modelBrand" name="modelBrand">Model
									Brand </label> <br> <label id="startDate" name="startDate">Start
									Date </label> <br> <label id="installationDate"
									name="installationDate">Installation Date </label> <br> <label
									id="endDate" name="endDate">End Date </label> <br>

								<c:if test="${not empty device.colourReading }">
									<label id="colourReading" name="colourReading">Colour
										Reading </label>
									<br>
								</c:if>
								<c:if test="${empty device.colourReading }">
								</c:if>

								<c:if test="${not empty device.colourCopyCost }">
									<label id="colourCopyCost" name="colourCopyCost">Colour
										Copy Cost </label>
									<br>
								</c:if>
								<c:if test="${empty device.colourCopyCost }">
								</c:if>

								<c:if test="${not empty device.monoReading }">
									<label id="monoReading" name="monoReading">Mono Reading
									</label>
									<br>
								</c:if>
								<c:if test="${empty device.monoReading }">
								</c:if>

								<c:if test="${not empty device.monoCopyCost }">
									<label id="monoCopyCost" name="monoCopyCost">Mono Copy
										Cost </label>
									<br>
								</c:if>
								<c:if test="${empty device.monoCopyCost }">
								</c:if>


							</div>
							<div class="machinedetailsfloatright ">
								<label id="customerName" name="customerName">:
									${device.customerDevice.customerName}</label><br> <label
									id="serialNumber" name="serialNumber">: <a
									href="searchDeviceSerialNumber?serialNumber=<c:out value='${device.serialNumber}'/>">${device.serialNumber}</a></label><br>
								<label id="modelNumber" name="modelNumber">:
									${device.modelNumber}</label><br> <label id="modelBrand"
									name="modelBrand">: ${device.modelBrand}</label><br> <label
									id="startDate" name="startDate">: ${device.startDate}</label><br>
								<label id="installationDate" name="installationDate">:
									${device.installationDate}</label><br> <label id="endDate"
									name="endDate">: ${device.endDate}</label><br>

								<c:if test="${not empty device.colourReading }">
									<label id="colourReading" name="colourReading">:
										${device.colourReading}</label>
									<br>
								</c:if>
								<c:if test="${ empty device.colourReading }">
								</c:if>

								<c:if test="${not empty device.colourCopyCost }">
									<label id="colourCopyCost" name="colourCopyCost">:
										R${device.colourCopyCost}</label>
									<br>
								</c:if>
								<c:if test="${ empty device.colourCopyCost }">
								</c:if>

								<c:if test="${not empty device.monoReading }">
									<label id="monoReading" name="monoReading">:
										${device.monoReading}</label>
									<br>
								</c:if>
								<c:if test="${ empty device.monoReading }">
								</c:if>

								<c:if test="${not empty device.monoCopyCost }">
									<label id="monoCopyCost" name="monoCopyCost">:
										R${device.monoCopyCost}</label>
									<br>
								</c:if>
								<c:if test="${ empty device.monoCopyCost}">
								</c:if>

							</div>

						</div>

						<div class="col-sm-6">
							<legend style="font-size: 18px;">Person and
								Address</legend>
							<p class="customerAddress_title">Address
							<ul class="address_list" style="display: block;">
								<li id="streetNumberStreetName">${device.streetNumber}
									${device.streetName}</li>
								<li id="city_town">${device.city_town}</li>
								<li id="province">${device.province}</li>
							</ul>
							</p>
							<p class="customerAddress_title">Contact Person
							<ul class="address_list" style="display: block;">
								<li id="firstNameLastname">${device.contactPerson.firstName}
									${device.contactPerson.lastName}</li>
								<li id="cellphone">${device.contactPerson.cellphone}</li>
								<li id="telephone">${device.contactPerson.telephone}</li>
								<li id="email">${device.contactPerson.email}</li>

							</ul>
							</p>
						</div>


						<div class="col-sm-12">
						<br/>
							<legend style="font-size: 18px;">Machine
								Accessories</legend>

							<table data-toggle="table" data-url="${accessories}"
								data-show-refresh="true" data-show-toggle="true"
								data-search="true" data-select-item-name="toolbar1"
								data-pagination="true" data-sort-name="serialno"
								data-sort-order="aesc">
								<thead>
									<tr>
										<th data-field="serialno" data-sortable="true">Serial No</th>
										<th data-field="accessorytype" data-sortable="true">Accessory
											Type</th>
								</thead>
								<tbody>
									<!-- Iterating over the list sent from Controller -->

									<c:forEach items="${accessories}" var="accessory">
										<tr>
											<td><h6>
													<c:out value="${accessory.accessotyType}" />
												</h6></td>
											<td><h6>
													<c:out value="${accessory.serial}" />
												</h6></td>

										</tr>
									</c:forEach>

								</tbody>
							</table>

						</div>


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
