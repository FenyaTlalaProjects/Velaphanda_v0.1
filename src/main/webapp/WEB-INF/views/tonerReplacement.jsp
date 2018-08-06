<%@ page import="java.util.*"%>
<%@include file="templates/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Toner Replacement | Velaphanda Trading & Projects</title>
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
					<div class="panel-heading" align="center">
						<b>Replace Toner</b>
					</div>
					<div class="panel-body">
						<c:if test="${not empty retMessage }">
							<div class="alert alert-info" role="alert">
								<c:out value="${ retMessage}">
								</c:out>
							</div>
						</c:if>
						<c:if test="${not empty message }">
							<div class="alert alert-danger" role="alert">
								<c:out value="${ message}">
								</c:out>
							</div>
						</c:if>

						<c:if test="${empty product.modelNumber}">

							<form:form action="searchSerialNumberReplaceToner" method="post"
								id="searchBylogTicket">

								<div class="row">
									<!-- Text input Search-->
									<div class="form-group">
										<label class="col-md-3 control-label">Search Device </label>
										<div class="col-md-4 inputGroupContainer">
											<div class="input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-hdd"></i></span> <input
													name="serialNumber" list="serialNumbers"
													class="form-control" type="text"
													onkeydown="upperCaseF(this)"
													placeholder='Enter Serial Number' />
											</div>
										</div>
										<!-- Iterating over the list sent from Controller -->
										<datalist id="serialNumbers"> <c:forEach var="list"
											items="${serialNumbers}">
											<option value="${list}">
										</c:forEach> </datalist>

										<div class="col-md-2">
											<input class="btn btn-success" type='submit' value='Search' />
										</div>
									</div>
								</div>
								<hr>
							</form:form>
							<!--Search-->
						</c:if>

						<c:if test="${not empty product.serialNumber }">

							<form:form method="post" class="well form-horizontal"
								action="tonerReplaceAdmin" modelAttribute="tonerReplaceAdmin"
								id="logTicket">

								<fieldset>
									<legend>
										<b style="font-size: 18px;">Device Details</b>
									</legend>

									<!--First Column-->
									<div class="col-sm-6">
										<!-- Text input Serial No-->
										<div class="form-group">
											<label class="col-md-3 control-label">Serial No</label>
											<div class="col-md-8 inputGroupContainer">
												<div class="input-group">
													<span class="input-group-addon"><i
														class="glyphicon glyphicon-barcode"></i></span> <input
														name="device" id="device" readonly="readonly"
														value="${product.serialNumber }" class="form-control"
														type="text">
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
														value="${product.modelNumber }" class="form-control"
														type="text" readonly="readonly">
												</div>
											</div>
										</div>

										<!-- Text input Customer Name-->
										<div class="form-group">
											<label class="col-md-3 control-label">Customer</label>
											<div class="col-md-8 inputGroupContainer">
												<div class="input-group">
													<span class="input-group-addon"><i
														class="glyphicon glyphicon-barcode"></i></span> <input
														value="${product.customerDevice.customerName }"
														class="form-control" id="customerName" name="customerName"
														type="text" readonly="readonly">
												</div>
											</div>
										</div>

									</div>

									<div class="col-sm-6">
										<div class="machinedetailsdetailsfloatleft">
											<label id="address">Address</label>
										</div>
										<div class="machinedetailsfloatright ">
											${productObject.customerDevice.streetNumber}
											${productObject.customerDevice.streetName}<br />
											${productObject.customerDevice.city_town}<br />
											${productObject.customerDevice.zipcode}
										</div>
									</div>


								</fieldset>


								<fieldset>

									<legend>
										<b style="font-size: 18px;">Details</b>
									</legend>

									<div class="col-sm-4">

										<fieldset>

											<legend>
												<b style="font-size: 12px;">Toner Details</b>
											</legend>

											<table id="sStock" data-toggle="table"
												data-url="${siteStock}" data-show-refresh="true"
												data-show-toggle="true" data-search="true"
												data-select-item-name="toolbar1" data-pagination="true"
												data-sort-name="partno" data-sort-order="desc">
												<thead>
													<tr>
														<th data-field="partno" data-sortable="true">Part No</th>
														<th data-field="colour" data-sortable="true">Colour</th>
														<th data-field="tickToner" data-sortable="true">Action</th>

													</tr>
												</thead>
												<tbody>

													<!-- Iterating over the list sent from Controller -->
													<c:forEach var="list" items="${compitableSiteStock}">
														<tr>
															<td>${list.partNumber}</td>
															<td>${list.color}</td>
															<td><input type="checkbox"
																id="${list.partNumber}_compitableSiteStock"
																name="compitableSiteStock" value="${list.partNumber}"></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>

										</fieldset>

									</div>
									<!--/F Column-->


									<!--Second column-->
									<div class="col-sm-4">

										<fieldset>
											<legend>
												<b style="font-size: 12px;">Last Readings</b>
											</legend>

											<div class="machinedetailsdetailsfloatleft">
												<c:if test="${not empty productObject.monoReading }">
													<label id="monoReading" name="monoReading">Mono:</label>
													<br>
													<br>
												</c:if>
												<c:if test="${not empty productObject.colourReading }">
													<label id="colourReading" name="colourReading">Colour:
													</label>
												</c:if>
												<c:if test="${empty productObject.monoReading }">
												</c:if>
												<c:if test="${empty productObject.colourReading }">
												</c:if>
											</div>
											<div class="machinedetailsfloatright ">
												<c:if test="${not empty productObject.monoReading}">
													<label id="mono">${productObject.monoReading}</label>
													<br />
													<br />
												</c:if>
												<c:if test="${ empty productObject.monoReading }">
												</c:if>
												<c:if test="${not empty productObject.colourReading}">
													<label id="mono">${productObject.colourReading}</label>
												</c:if>
												<c:if test="${ empty productObject.colourReading }">
												</c:if>

											</div>

										</fieldset>

									</div>
									<!--/S Column-->

									<!--Second column-->
									<div class="col-sm-4">

										<fieldset>
											<legend>
												<b style="font-size: 12px;">Current Readings</b>
											</legend>
											<c:if test="${not empty productObject.monoReading }">
											<!-- Text input Mono-->
											<div class="form-group">
												<label class="col-md-3 control-label">Mono</label>
												<div class="col-md-8 inputGroupContainer">
													<div class="input-group">
														<span class="input-group-addon"><i
															class="glyphicon glyphicon-barcode"></i></span> <input
															id="currentMonoReading" name="currentMonoReading"
															placeholder="Mono Reading" class="form-control"
															type="text">
													</div>
												</div>
											</div>
											</c:if>
											<c:if test="${empty productObject.monoReading }">
											</c:if>
											<c:if test="${not empty productObject.colourReading}">
											<!-- Text input Color-->
											<div class="form-group">
												<label class="col-md-3 control-label">Colour</label>
												<div class="col-md-8 inputGroupContainer">
													<div class="input-group">
														<span class="input-group-addon"><i
															class="glyphicon glyphicon-barcode"></i></span> <input
															id="currentColourReading" name="currentColourReading"
															placeholder="Color Reading" class="form-control"
															type="text">
													</div>
												</div>
											</div>
											</c:if>											
											<c:if test="${empty productObject.colourReading }">
											</c:if>

										</fieldset>


									</div>
									<!--/S Column-->

								</fieldset>

								<fieldset>
									<legend>
										<b style="font-size: 18px; color: red;">Replaced By</b>
									</legend>


									<!--Second column-->
									<div class="col-sm-6">

										<fieldset>
											<legend>
												<b style="font-size: 12px;">User Logged In</b>
											</legend>

										<!-- Text input Contact Person  Last Name-->
											<div class="form-group">
												<div class="col-md-10 inputGroupContainer">
													<div class="input-group">
														<span class="input-group-addon"><i
															class="glyphicon glyphicon-user"></i></span> <input
															id="loggedInUser" name="loggedInUser"
															class="form-control" type="text" readonly="readonly" value="${user}">
													</div>
												</div>
											</div>

										</fieldset>
										
										<fieldset>
											<legend>
												<b style="font-size: 12px;">Provide Comments</b>
											</legend>

											<!-- Text area -->
											<div class="form-group">
												<div class="col-md-8 inputGroupContainer">
													<div class="input-group">
														<span class="input-group-addon"><i
															class="glyphicon glyphicon-pencil"></i></span>
														<textarea class="form-control" name="description"
															id="description" placeholder="Description"
															onkeydown="upperCaseF(this)"
															style="margin: 0px; height: 124px; width: 361px;"
															maxlength="187px;"></textarea>
													</div>
												</div>
											</div>

										</fieldset>
										

									</div>
									<!--/S Column-->
									
									

									<div class="col-sm-6">

										<fieldset>
											<legend>
												<b style="font-size: 12px;">Customer Details</b>
											</legend>


											<!-- Text input Contact Person First Name-->
											<div class="form-group">
												<div class="col-md-10 inputGroupContainer">
													<div class="input-group">
														<span class="input-group-addon"><i
															class="glyphicon glyphicon-user"></i></span> <input
															id="firstName" name="firstName" placeholder="First Name"
															class="form-control" type="text">
													</div>
												</div>
											</div>

											<!-- Text input Contact Person  Last Name-->
											<div class="form-group">
												<div class="col-md-10 inputGroupContainer">
													<div class="input-group">
														<span class="input-group-addon"><i
															class="glyphicon glyphicon-user"></i></span> <input
															id="lastName" name="lastName" placeholder="Last Name"
															class="form-control" type="text">
													</div>
												</div>
											</div>										
											

											<div class="form-group">
												
												<div class="col-md-10 inputGroupContainer">
													<div class="input-group">
														<span class="input-group-addon"><i
															class="glyphicon glyphicon-envelope"></i></span> <input
															id="contactEmail" name="contactEmail"
															placeholder="Email Address" class="form-control"
															type="email">
													</div>
												</div>
											</div>

											<!-- Text input Contact Person Tellphone Number-->
											<div class="form-group">
												<div class="col-md-10 inputGroupContainer">
													<div class="input-group">
														<span class="input-group-addon"><i
															class="glyphicon glyphicon-earphone"></i></span> <input
															id="contactTelephoneNumber" name="contactTelephoneNumber"
															placeholder="Telephone Number (Optional)"
															class="form-control" maxlength="10" type="text"
															onkeypress="return isNumber(event)">
													</div>
												</div>
											</div>
											
										<!-- Text input Contact Person Cellphone Number-->
											<div class="form-group">
												<div class="col-md-10 inputGroupContainer">
													<div class="input-group">
														<span class="input-group-addon"><i
															class="glyphicon glyphicon-earphone"></i></span> <input
															id="contactCellNumber" name="contactCellNumber"
															placeholder="Cellphone Number (Optional)"
															class="form-control" maxlength="10" type="text"
															onkeypress="return isNumber(event)">
													</div>
												</div>
											</div>

										</fieldset>

									</div>
									<!--/F Column-->

								</fieldset>


								<div class="form-group row">
									<div class="col-sm-offset-2 col-sm-8">
										<br> <br> <input type="submit" value="Replace Toner"
											class="btn btn-primary btn-block btn-lg" tabindex="9"
											id="replaceToner" onclick='return validate()'>
									</div>
								</div>

							</form:form>
						</c:if>

						<c:if test="${empty product.serialNumber}">

						</c:if>

						<!-- .panel-body -->
					</div>
					<!-- .panel panel-default -->
				</div>
			</div>
		</div>
		<!-- /.col-->
		<!-- /.row -->
		<!-- Footer -->
		<c:import url="templates/footer.jsp"></c:import>
		<!--/ Footer -->
	</div>
	<!--/.main-->
	<c:import url="templates/javascriptslib.jsp"></c:import>
	<c:import url="templates/sidebar-collapse.jsp"></c:import>


	<script type="text/javascript">
		function validate(form) {
			var checkboxs = document.getElementsByName("compitableSiteStock");
			var okay = false;
			for (var i = 0, l = checkboxs.length; i < l; i++) {
				if (checkboxs[i].checked) {
					okay = true;
					break;
				}
			}
			if (okay)
				return true;
			else {
				alert("Please select atleast one item of Toner Details to be replaced");
				return false;
			}
		}
	</script>
	<!-- /Scripts -->
</body>
</html>
