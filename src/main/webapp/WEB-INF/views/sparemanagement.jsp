<%@include file="templates/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Spares Management</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<c:import url="templates/tableresizefont.jsp"></c:import>
<c:import url="templates/stylesheetlib.jsp"></c:import>
<c:import url="templates/datatablesstyles.jsp"></c:import>
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
					<div class="panel-heading" align="center">Spare Management</div>
					<div class="panel-body">

						<ul id="ticket-summary" class="nav nav-tabs">
							<div class="row">

								<div class="col-xs-6 col-md-3">
									<div class="panel panel-default">
										<a href='receiveParts.html'>
											<div class="panel-body easypiechart-panel">
												<h5>Receive Parts</h5>
												<div class="easypiechart" id="easypiechart-darkgreen"
													data-percent="">
													<span class="percent" style="font-size: 15px;">Receive
														Parts</span>
												</div>
											</div>
										</a>
									</div>
								</div>
								<div class="col-xs-6 col-md-3">
									<div class="panel panel-default">
										<a href='#hoSpareParts' data-toggle="tab">
											<div class="panel-body easypiechart-panel">
												<h5>Head Office</h5>
												<div class="easypiechart" id="easypiechart-orange"
													data-percent="${hoCount}">
													<span class="percent">${hoCount}</span>
												</div>
											</div>
										</a>
									</div>
								</div>
								<div class="col-xs-6 col-md-3">
									<div class="panel panel-default">
										<a href="#siteStock" data-toggle="tab">
											<div class="panel-body easypiechart-panel">
												<h5>Site Stock</h5>
												<div class="easypiechart" id="easypiechart-teal"
													data-percent="${siteCount}">
													<span class="percent">${siteCount}</span>
												</div>
											</div>
										</a>
									</div>
								</div>
								<div class="col-xs-6 col-md-3">
									<div class="panel panel-default">
										<a href='#bootStock' data-toggle="tab">
											<div class="panel-body easypiechart-panel">
												<h5>Boot Stock</h5>
												<div class="easypiechart" id="easypiechart-purple"
													data-percent="${bootCount}">
													<span class="percent">${bootCount}</span>
												</div>
											</div>
										</a>
									</div>
								</div>

							</div>
						</ul>
						<!--/.row-->

						<!-- tab nav -->
						<div class="tab-content">

							<div class="tab-pane active" id="receiveParts"></div>

							<div class="tab-pane" id="hoSpareParts">

								<legend align=center>Head Office Stock</legend>


								<table id="example" class="display">
									<thead>
										<tr>
											<th data-field="" data-sortable="true"></th>
											<th data-field="partNo" data-sortable="true">Part No</th>
											<th data-field="compatibledevices" data-sortable="true">Compatible
												Devices</th>
											<th data-field="modelbrand" data-sortable="true">Model
												Brand</th>
											<th data-field="description" data-sortable="true">Description</th>
											<th data-field="itemtype" data-sortable="true">Item Type</th>
											<th data-field="qty" data-sortable="true">QTY</th>
											<th data-field="color" data-sortable="true">Colour</th>
										</tr>
									</thead>
									<tbody>
										<!-- Iterating over the list sent from Controller -->
										<c:forEach var="list" items="${spareParts}">
											<tr>
												<td class="details-control"></td>
												<td>${list.partNumber}</td>
												<td>${list.compitableDevice}</td>
												<td>${list.modelBrand}</td>
												<td>${list.itemDescription}</td>
												<td>${list.itemType}</td>
											    <td>${list.quantity}</td>
												<td>${list.color}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<!-- table order -->

							</div>
							<div class="tab-pane" id="siteStock">
								<legend align=center>Site Stock</legend>

								<table data-toggle="table" data-url="${customer}"
									data-show-refresh="true" data-show-toggle="true"
									data-search="true" data-select-item-name="toolbar1"
									data-pagination="true" data-sort-name="customername"
									data-sort-order="aesc">
									<thead>
										<tr>
											<th data-field="customername" data-sortable="true">Customer
												Name</th>
											<th data-field="compatibledevices" data-sortable="true">Parts
												Quantity</th>
											<th data-field="modelbrand" data-sortable="true">Toners
												Quantity</th>
											
										</tr>
									</thead>
									<tbody>
										<!-- Iterating over the list sent from Controller -->
										<c:forEach var="list" items="${customer}">
											<c:choose>
												<c:when test="${list.tonerQuantity > 0 || list.partQuanty > 0}">													
													<tr>											
														<td><a href="loadStockSite?customerName=<c:out value='${list.customerName}'/>">${list.customerName}</a></td>												
														<td>${list.partQuanty}</td>
														<td>${list.tonerQuantity}</td>											
													</tr>
												</c:when>
												<c:otherwise>
												</c:otherwise>
											</c:choose>											
										</c:forEach>
									</tbody>
								</table>
							</div>
							<!-- siteStock -->
							
							
							<div class="tab-pane" id="bootStock">
								<legend align=center>Boot Stock</legend>
								<table data-toggle="table" data-url="${customer}"
									data-show-refresh="true" data-show-toggle="true"
									data-search="true" data-select-item-name="toolbar1"
									data-pagination="true" data-sort-name="technicianname"
									data-sort-order="aesc">
									<thead>
										<tr>
											<th data-field="technicianname" data-sortable="true">Technician
												Name</th>
											<th data-field="partsquantity" data-sortable="true">Parts
												Quantity</th>
											<th data-field="tonersquantity" data-sortable="true">Toners
												Quantity</th>
																					</tr>
									</thead>
									<tbody>
										<!-- Iterating over the list sent from Controller -->
										<c:forEach var="list" items="${employees}">
										
											<c:choose>
												<c:when test="${list.tonerQuantity > 0 || list.partQuanty > 0}">													
													<tr>											
														<td><a href="loadBootStock?technician=<c:out value='${list.customerName}'/>">${list.customerName}</a></td>												
														<td>${list.partQuanty}</td>
														<td>${list.tonerQuantity}</td>											
													</tr>
												</c:when>
												<c:otherwise>
												</c:otherwise>
											</c:choose>	
										
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
				<!-- /.row -->
				<!-- Footer -->
				<c:import url="templates/footer.jsp"></c:import>
				<!--/ Footer -->
			</div>
			<!--/.main-->
			<c:import url="templates/javascriptslib.jsp"></c:import>
			<c:import url="templates/datatablesscripts.jsp"></c:import>
			<c:import url="templates/sidebar-collapse.jsp"></c:import>
			
			<script>
			 function format(d){
			        
		         // `d` is the original data object for the row
		         return '<table  border="0">' +
				     '<tr>' +
				         '<td>Name and Surname:</td>' +
				         '<td>' + " " + '</td>' +
				     '</tr>' +
		             '<tr>' +
		                 '<td>Date Time:</td>' +
		                 '<td>' + " " + '</td>' +
		             '</tr>' +
		             '<tr>' +
		                 '<td>Order number:</td>' +
		                 '<td>' + " " + '</td>' +
		             '</tr>' +
		             '<tr>' +
	                 '<td>Supplier details:</td>' +
	                 '<td>' + " " + '</td>' +
	            	 '</tr>' +		             
		            
		         '</table>';  
		    }
			</script>
			<!-- /Scripts -->
</body>
</html>
