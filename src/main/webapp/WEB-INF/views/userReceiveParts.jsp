<%@include file="templates/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Receive Spares | Velaphanda Trading & Projects</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">


<style>
.model {
	margin-left: 3%
}

.form-group-model {
	margin-left: 10%;
}

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
	<c:import url="templates/usernavbar.jsp"></c:import>

	<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
		<div class="row">
			<ol class="breadcrumb">
				<li><a href="userdashboard.html"><svg
							class="glyph stroked home"> <use xlink:href="#stroked-home"></use></svg></a></li>
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
					<div class="panel-heading" align="center">Receive Spares</div>

					<div class="panel-body">

						<c:if test="${not empty retMessage }">

							<div class="alert alert-info" role="alert">
								<c:out value="${ retMessage}">
								</c:out>
							</div>
						</c:if>
						<c:if test="${not empty errorRetMessage }">

							<div class="alert alert-danger" role="alert">
								<c:out value="${ errorRetMessage}">
								</c:out>

							</div>
						</c:if>


						<div class="tab-content">

							<c:if test="${empty sparePart.partNumber }">

								<form:form action="searchpartNumber" method="post"
									id="searchpartNumber">
									<div class="row">
										<!-- Text input Search-->
										<div class="form-group">
											<label class="col-md-3 control-label">Part Number </label>
											<div class="col-md-4 inputGroupContainer">
												<div class="input-group">
													<span class="input-group-addon"><i
														class="glyphicon glyphicon-hdd"></i></span> <input
														name="partNumber" list="spareParts"
														onkeydown="upperCaseF(this)" id="partNumber"
														class="form-control" type="text"
														placeholder='Search By Part Number'>
												</div>
											</div>
											<!-- Iterating over the list sent from Controller -->
											<datalist id="spareParts"> <c:forEach var="list"
												items="${spareParts}">
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
							<c:if test="${not empty sparePart.partNumber }">

							</c:if>


							<c:if test="${not empty sparePart.itemDescription }">

								<form:form class="well form-horizontal" action="saveSpareParts"
									modelAttribute="saveSpareParts" method="post"
									id="saveSpareParts">

									<!-- group spare details -->
									<div class="groupsparedetails">
										<legend>Spares</legend>


										<!--First Column-->
										<div class="col-md-12">
											<!-- Text input Part Number-->
											<div class="form-group">
												<label class="col-md-3 control-label">Part Number</label>
												<div class="col-md-8 inputGroupContainer">
													<div class="input-group">
														<span class="input-group-addon"><i
															class="glyphicon glyphicon-barcode"></i></span> <input
															id="partNum" readOnly name="partNumber"
															onkeydown="upperCaseF(this)"
															placeholder="Enter Part Number" class="form-control"
															type="text" value="${sparePart.partNumber}">
													</div>
												</div>
											</div>

												<!-- Select type Item Type-->
												<div class="form-group">
												<label class="col-md-3 control-label">Item Type</label>
												<div class="col-md-8 inputGroupContainer">
													<div class="input-group">
														<span class="input-group-addon"><i
															class="glyphicon glyphicon-barcode"></i></span> <input
															id="itemType" readOnly name="itemType" class="form-control"
															type="text" value="${sparePart.itemType}">
													</div>
												</div>
											</div>
											
											<div class="form-group">
												<label class="col-md-3 control-label">Colour</label>
												<div class="col-md-8 selectContainer">
													<div class="input-group">
														<span class="input-group-addon"><i
															class="glyphicon glyphicon-list"></i></span> <input id="color"
															class="form-control" readOnly name="color"  value="${sparePart.color}" />
													</div>
												</div>
											</div>

											<!-- Select type Brand-->
											<div class="form-group">
												<label class="col-md-3 control-label">Brand</label>
												<div class="col-md-8 selectContainer">
													<div class="input-group">
														<span class="input-group-addon"><i
															class="glyphicon glyphicon-list"></i></span> <input
															id="modelBrand" readOnly name="modelBrand" type="text"
															class="form-control" value="${sparePart.modelBrand}">

													</div>
												</div>
											</div>

											<!-- Text input Description-->
											<div class="form-group">
												<label class="col-md-3 control-label">Description</label>
												<div class="col-md-8 inputGroupContainer">
													<div class="input-group">
														<span class="input-group-addon"><i
															class="glyphicon glyphicon-barcode"></i></span> <input
															id="itemDescription" readOnly name="itemDescription"
															type="text" class="form-control"
															value="${sparePart.itemDescription}">
													</div>
												</div>
											</div>

											<!-- Text input Quantity-->
											<div class="form-group">
												<label class="col-md-3 control-label">Quantity</label>
												<div class="col-md-8 inputGroupContainer">
													<div class="input-group">
														<span class="input-group-addon"><i
															class="glyphicon glyphicon-barcode"></i></span> <input
															type="number" placeholder="Enter Quantity" id="quantity"
															name="quantity" class="form-control">
													</div>
												</div>
											</div>

											<!-- Text input Received By-->
											<div class="form-group">
												<label class="col-md-3 control-label">Received By</label>
												<div class="col-md-8 inputGroupContainer">
													<div class="input-group">
														<span class="input-group-addon"><i
															class="glyphicon glyphicon-user"></i></span> <input type="text"
															id="receivedBy" name="receivedBy" class="form-control"
															value="${loggedInUser.firstName} ${loggedInUser.lastName}"
															readonly="readonly">
													</div>
												</div>
											</div>
										</div>

									</div>
									<!-- //group spare details -->

									<!-- group search details -->
									<div class="col-sm-6">
										<legend>Compatible Devices </legend>

										<table
											class="table table-striped table-bordered table-hover table-condensed">
											<label class="model">Model No</label>
											<c:forEach var="compitableDevice" items="${models}">
												<tr>

													<td><input type="text" readOnly class="form-control"
														id="compitableDevice" name="compitableDevice"
														value="${compitableDevice}"></td>
												</tr>
											</c:forEach>

										</table>
										<!-- //group search details -->

										<br /> <br />
										<!-- Supplier Details -->
										<legend>Supplier Details</legend>

										<!-- Text input Supplier Name-->
										<div class="form-group">
											<label class="col-md-3 control-label">Supplier Name</label>
											<div class="col-md-8 selectContainer">
												<div class="input-group">
													<span class="input-group-addon"><i
														class="glyphicon glyphicon-list"></i></span><select
														id="supplierName" class="form-control" name="supplierName"
														class="form-control selectpicker">
														<option value="">Select Supplier Name</option>
														<option value="Canon South Africa">Canon South
															Africa</option>
														<option value="Taropa Technologies">Taropa
															Technologies</option>
														<option value="Magenta">Toshiba South Africa</option>
														<option value="Riso South Africa">Riso South
															Africa</option>
													</select>
												</div>
											</div>
										</div>

										<!-- Text input Supplier Order number-->
										<div class="form-group">
											<label class="col-md-3 control-label">Supplier Order
												No</label>
											<div class="col-md-8 inputGroupContainer">
												<div class="input-group">
													<span class="input-group-addon"><i
														class="glyphicon glyphicon-user"></i></span> <input type="text"
														id="supplierOrderNo" name="supplierOrderNo"
														class="form-control" value=""
														placeholder="Provide Supplier Order No">
												</div>
											</div>
										</div>
									</div>
									<!-- //Supplier Details -->


									<!--  History Details 
									<div id="recieveSpare" class="modal fade" role="dialog"
										aria-hidden="true">
										<div class="modal-dialog modal-lg">

											<div class="modal-content">

												<div class="modal-header">
													<button type="button" class="close" data-dismiss="modal"
														aria-hidden="true">×</button>
													<h3 class="modal-title">Spare Recieve Comment</h3>
												</div>
												<p>
												<div class="modal-body">
													<div class="row">

														<div class="col-sm-12">
															<div class="form-group">
																<div class="alert alert-info" role="alert">
																	<p>
																		<strong>Note:</strong>You need to provide what you
																		have are updating and new data!
																	</p>
																</div>
															</div>


															Customer Action
															<input type="hidden" id="customerAction"
																name="customerAction" class="form-control"
																value="Update Customer">

															Text area comments
															<label class="col-md-3 control-label">Provide
																Comment</label>
															<div class="form-group">
																<div class="col-md-6 inputGroupContainer">
																	<div class="input-group">
																		<span class="input-group-addon"><i
																			class="glyphicon glyphicon-edit"></i></span>
																		<textarea class="form-control" style="height: 120px;"
																			id="description" name="description" maxlength="150"
																			placeholder="Please enter what was updated and new updated data"></textarea>
																	</div>
																</div>
															</div>
															// text area comments

														</div>
													</div>

												</div>
												modal-body
												<div class="modal-footer">
													<button type="button" class="btn btn-default"
														data-dismiss="modal">Cancel</button>
													<input type="submit" value="Recieve Spare"
														class="btn btn-primary"
														onclick="return confirm('Are you sure you want to recieve parts?');">
												</div>
											</div>
											/.modal-content
										</div>
										/.modal-dialog
									</div>
									/.modal -- History Details -->


									<div class="form-group row">
										<div class="col-sm-offset-2 col-sm-8">
											<br> <br> <input type="submit"
												value="Recieve Spare(s)"
												class="btn btn-primary btn-block btn-lg" tabindex="9"
												id="addSpare">
										</div>
									</div>

								</form:form>

							</c:if>

							<c:if test="${empty sparePart.itemDescription }">
							</c:if>

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
		<c:import url="templates/sidebar-collapse.jsp"></c:import>

		<!-- /Scripts -->
</body>
</html>