<%@include file="templates/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Add Spare Parts | Velaphanda Trading & Projects</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
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
						<b>Add Spare</b>
					</div>
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
						<form:form class="well form-horizontal" method="POST"
							action="spareToMasterData" modelAttribute="spareToMasterData"
							id="spareParts">

							<!-- Text input Part Number-->
							<div class="form-group">
								<label class="col-md-3 control-label">Part Number</label>
								<div class="col-md-6 inputGroupContainer">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-barcode"></i></span> <input id="partNum"
											name="partNumber" onkeydown="upperCaseF(this)"
											placeholder="Enter Part Number" class="form-control"
											type="text" value="">

									</div>
								</div>
							</div>

							<!-- Text input Machine Model-->
							<div class="form-group">
								<label class="col-md-3 control-label">Compatible Devices</label>
								<div class="col-md-6 inputGroupContainer">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-barcode"></i></span> <input
											name="compitableDevice" onkeydown="upperCaseF(this)"
											id="modelNumber" placeholder="Compatible Devices"
											class="form-control" type="text">
									</div>
								</div>
							</div>

							<!-- Select type Item Type-->
							<div class="form-group">
								<label class="col-md-3 control-label">Item Type</label>
								<div class="col-md-6 selectContainer">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-list"></i></span><select id="itemType"
											class="form-control" name="itemType"
											class="form-control selectpicker" onchange="SelectColours(this.value);">
											<option value="">Select Item Type</option>
											<option value="Toner">Toner</option>
											<option value="Part">Part</option>
										</select>
									</div>
								</div>
							</div>
							
							<!-- Select type Color Type-->
							<div class="colors" id="colors" style="display: none;">
								<div class="form-group">
									<label class="col-md-3 control-label">Colour</label>
									<div class="col-md-6 selectContainer">
										<div class="input-group">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-list"></i></span><select id="color"
											class="form-control" name="color"
											class="form-control selectpicker">
											<option value="">Select Colour</option>
											<option value="Cyan">Cyan</option>
											<option value="Yellow">Yellow</option>
											<option value="Magenta">Magenta</option>
											<option value="Black">Black</option>
											</select>
										</div>
									</div>
								</div>
							</div>
							
							<!-- Select type Brand-->
							<div class="form-group">
								<label class="col-md-3 control-label">Brand</label>
								<div class="col-md-6 selectContainer">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-list"></i></span> <select id="modelBrand"
											name="modelBrand" class="form-control">
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
							<!-- Text input Description-->
							<div class="form-group">
								<label class="col-md-3 control-label">Description</label>
								<div class="col-md-6 inputGroupContainer">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-barcode"></i></span> <input
											id="description" name="itemDescription" type="text"
											class="form-control" placeholder="Description" value="">
									</div>
								</div>
							</div>
							<!-- Text input Received By-->
							<div class="form-group">
								<label class="col-md-3 control-label">Received By</label>
								<div class="col-md-6 inputGroupContainer">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-user"></i></span> <input type="text"
											id="capturedBy" name="capturedBy" class="form-control"
											value="${loggedInUser.firstName} ${loggedInUser.lastName}"
											readonly="readonly">
									</div>
								</div>
							</div>
							<br>
							<div class="form-group row">
								<div class="col-sm-offset-3 col-sm-6">
									<input type="submit" value="Add Spare Parts"
										class="btn btn-primary btn-block btn-lg" tabindex="9"
										id="addSpareP"
										onclick="return confirm('Are you sure you want to add spares?');">
								</div>
							</div>
						</form:form>

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