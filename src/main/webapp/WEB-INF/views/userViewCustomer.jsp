<%@include file="templates/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>View Customer Details | Velaphanda Trading & Projects</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<c:import url="templates/stylesheetlib.jsp"></c:import>
</head>
<body>
	<c:import url="templates/usernavbar.jsp"></c:import>

	<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
		<div class="row">
			<ol class="breadcrumb">
				<li><a href="userdashboard.html""><svg class="glyph stroked home">
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
					<div class="panel-heading" align="center">View Customer</div>
					<div class="panel-body">
					
					<div align="right">
							<a target="_blank"
								href="viewCustomerDownloadPDF?customerName=<c:out value='${customer.customerName}'/>">Download
								PDF </a>
					</div>
					 
					<form:form class="well form-horizontal" method="post"
							action="viewCustomerData" modelAttribute="viewCustomerData"
							id="updateClient">
							<fieldset>
								<!-- Customer Details -->
								<legend>
									<b style="font-size: 15px;">Customer Details </b>
								</legend>	
														
								<div class="row">															
									<div class="col-sm-6">
									    <b>Customer Name:</b> ${customer.customerName}<br/>
									    <b>Telephone No:</b> ${customer.telephoneNumber}<br/>
										<b>Fax Number:</b> ${customer.faxNumber}<br/>
										<b>Street No:</b> ${customer.streetNumber}
								    </div>
								    <div class="col-sm-6">
									    <b>Street Name:</b> ${customer.streetName}<br/>
										<b>City/Town:</b> ${customer.city_town}<br/>
									    <b>Province: ${customer.province}</b><br/>
									    <b>Area Code:</b> ${customer.zipcode}<br/>
									</div>												
								</div>
								<br/>
								<!-- Customer Details -->
							</fieldset>
							

							<fieldset>
								<legend>
									<b style="font-size: 15px;">Contact Person 1 </b>
								</legend>

								<!-- Contact Person 1 -->
								<div class="row">															
									<div class="col-sm-6">
									    <b>First Name:</b> ${customerDetails.firstName}<br/>
									    <b>Last Name:</b> ${customerDetails.lastName}<br/>
										<b>Cell Phone No:</b> ${customerDetails.contactCellNumber}<br/>
								    </div>
								    <div class="col-sm-6">
									    <b>Telephone No:</b> ${customerDetails.contactTelephoneNumber}<br/>
										<b>Email:</b> ${customerDetails.contactEmail}<br/>									    
									</div>												
								</div>
								<br/><!-- //Contact Person 1 -->

							</fieldset>


							<fieldset>
							<!-- //Contact Person 2 -->
								<legend>
									<b class="optionalFields" style="color: red; font-size: 15px;">Contact
										Person 2 (Optional Fields)</b>
								</legend>
								<div class="row">															
									<div class="col-sm-6">
									    <b>First Name:</b> ${customerDetails.firstName1}<br/>
									    <b>Last Name:</b> ${customerDetails.lastName1}<br/>
										<b>Cell Phone No:</b> ${customerDetails.contactCellNumber1}<br/>
								    </div>
								    <div class="col-sm-6">
									    <b>Telephone No:</b> ${customerDetails.contactTelephoneNumber1}<br/>
										<b>Email:</b> ${customerDetails.contactEmail1}<br/>									    
									</div>												
								</div>
								<br/>								
								<!--/Contact Person 2 -->
							</fieldset>
							
							<%--
							<fieldset>
								<!-- //History Details-->
								<legend>
									<b class="optionalFields" style="font-size: 15px;">Customer
										History</b>
								</legend>
								<div class="row">

									 <table data-toggle="table" data-url="${customerHistory}"
										data-show-refresh="true" data-show-toggle="true"
										data-search="true" data-select-item-name="toolbar1"
										data-pagination="true" data-sort-name="cleintRegisteredBy"
										data-sort-order="aesc">
										<thead>
											<tr>
												<!-- <th></th> -->
												<th data-field="cleintRegisteredBy" data-sortable="true">Client
													Registered By</th>
												<th data-field="timeClientRegistered" data-sortable="true">Time
													Client Registered</th>
												<th data-field="clientUpdatedBy" data-sortable="true">Client
													Updated By</th>
												<th data-field="timeClientUpdated" data-sortable="true">Time
													Client Updated</th>

											</tr>
										</thead>

										<tbody>
											<!-- Iterating over the list sent from Controller -->
											<c:forEach var="list" items="">
												<tr>
													<!-- <td class="details-control"></td> -->
													<td></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
											</c:forEach>
										</tbody>
									</table> 
									
								</div>
								<br />
								<!-- //end History Details-->
							</fieldset>--%>

						</form:form>

					</div>
					<!-- .panel-body -->
				</div>
				<!-- .panel panel-default -->
			</div>
			<!-- /.col-->
		</div>
		<!-- /.row -->
		<!-- footer -->
		<c:import url="templates/footer.jsp"></c:import>
		<!--/ footer -->
	</div>
	<!--/.main-->
	<c:import url="templates/javascriptslib.jsp"></c:import>
	<c:import url="templates/sidebar-collapse.jsp"></c:import>
	<!-- /Scripts -->
</body>
</html>
