<%@ page import="java.util.*"%>
<%@include file="templates/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Log a ticket | Velaphanda Trading & Projects</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<c:import url="templates/stylesheetlib.jsp"></c:import>
<style type="text/css">
.onleave {
	background: red;
	color: white;
}
</style>
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
						<b>Log Ticket</b>
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

							<form:form action="searchSerialNumberLogtickr" method="post"
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
								action="logTicketAdmin" modelAttribute="logTicketAdmin"
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

										<!-- Assign Technician -->
										<div class="form-group">
											<label class="col-md-3 control-label">
												Technician</label>
											<div class="col-md-8 selectContainer">
												<div class="input-group">
													<span class="input-group-addon"><i
														class="glyphicon glyphicon-list"></i></span> <select
														id="selectedTechnician" name="technicianUserName"
														id="selectedTechnician" class="form-control selectpicker">
														<option value="">Select Technician</option>
														<c:forEach items="${technicians}" var="technician">
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
										<!-- Select type Priority-->
										<div class="form-group">
											<label class="col-md-3 control-label">Priority</label>
											<div class="col-md-8 selectContainer">
												<div class="input-group">
													<span class="input-group-addon"><i
														class="glyphicon glyphicon-list"></i></span> <select
														name="priority" id="priority"
														class="form-control selectpicker">
														<option value="">Select Priority</option>
														<option value="High">High</option>
														<option value="Medium">Medium</option>
														<option value="Low">Low</option>
													</select>
												</div>
											</div>
										</div>
									</div>

									<div class="col-sm-6">
										<!-- Text area -->
										<div class="form-group">
											<label class="col-md-3 control-label">Description</label>
											<div class="col-md-8 inputGroupContainer">
												<div class="input-group">
													<span class="input-group-addon"><i
														class="glyphicon glyphicon-pencil"></i></span>
													<textarea class="form-control" name="description"
														id="description" placeholder="Description"
														onkeydown="upperCaseF(this)"
														style="margin: 0px; height: 201px; width: 275px;" maxlength="187px;"></textarea>
												</div>
											</div>
										</div>

									</div>
								</fieldset>

								<fieldset>
									<legend>
										<b style="font-size: 18px; color: red;">Contact Person</b>
									</legend>

									<div class="col-sm-6">

										<!-- Text input Contact Person First Name-->
										<div class="form-group">
											<label class="col-md-3 control-label">First Name</label>
											<div class="col-md-8 inputGroupContainer">
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

										<div class="form-group">
											<label class="col-md-3 control-label">Email</label>
											<div class="col-md-8 inputGroupContainer">
												<div class="input-group">
													<span class="input-group-addon"><i
														class="glyphicon glyphicon-envelope"></i></span> <input
														id="contactEmail" name="contactEmail"
														placeholder="Email Address" class="form-control"
														type="email">
												</div>
											</div>
										</div>
									</div>
									<!--/F Column-->


									<!--Second column-->
									<div class="col-sm-6">

										<!-- Text input Contact Person Cellphone Number-->
										<div class="form-group">
											<label class="col-md-3 control-label">Cellphone No</label>
											<div class="col-md-8 inputGroupContainer">
												<div class="input-group">
													<span class="input-group-addon"><i
														class="glyphicon glyphicon-earphone"></i></span> <input
														id="contactCellNumber" name="contactCellNumber"
														placeholder="Cellphone No (Optional)" class="form-control"
														maxlength="10" type="text"
														onkeypress="return isNumber(event)">
												</div>
											</div>
										</div>
										<!-- Text input Contact Person Tellphone Number-->
										<div class="form-group">
											<label class="col-md-3 control-label">Tellphone No </label>
											<div class="col-md-8 inputGroupContainer">
												<div class="input-group">
													<span class="input-group-addon"><i
														class="glyphicon glyphicon-earphone"></i></span> <input
														id="contactTelephoneNumber" name="contactTelephoneNumber"
														placeholder="Telephone No (Optional)" class="form-control"
														maxlength="10" type="text"
														onkeypress="return isNumber(event)">
												</div>
											</div>
										</div>

									</div>
									<!--/S Column-->

								</fieldset>


								<div class="form-group row">
									<div class="col-sm-offset-2 col-sm-8">
										<br> <br> <input type="submit" value="Log Ticket"
											class="btn btn-primary btn-block btn-lg" tabindex="9"
											id="logTicket"
											onclick="return confirm('Are you sure you want to log ticket?');">
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
	<!-- /Scripts -->
</body>
</html>
