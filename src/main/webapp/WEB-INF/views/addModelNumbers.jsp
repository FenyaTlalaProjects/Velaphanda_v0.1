<%@include file="templates/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Add Model Numbers | Velaphanda Trading & Projects</title>
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
						<b>Add Model Number</b>
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
							action="modelNumbersToMasterData"
							modelAttribute="modelNumbersToMasterData" id="spareParts">

							<div class="alert alert-danger alert-dismissible fade in">
								<a href="#" class="close" data-dismiss="alert"
									aria-label="close">&times;</a> <strong>Danger!</strong> Model
								Number must be in the following format. Eg: CLX-9290. If Model number does not have a color, it wont be required.
							</div>

							<!-- Text input Part Number-->
							<div class="form-group">
								<label class="col-md-3 control-label">Model Number</label>
								<div class="col-md-6 inputGroupContainer">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-barcode"></i></span> <input
											id="modelNumber" name="modelNumber"
											onkeydown="upperCaseF(this)" placeholder="Enter Model Number"
											class="form-control" type="text" value="">

									</div>
								</div>
							</div>

							<!-- Text input Supplier Name-->
							<div class="form-group">
								<label class="col-md-3 control-label">Supplier Name</label>
								<div class="col-md-6 selectContainer">
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
											<option value="Toshiba South Africa">Toshiba South
												Africa</option>
											<option value="Riso South Africa">Riso South Africa</option>
										</select>
									</div>
								</div>
							</div>

							<!-- Select type Brand-->
							<div class="form-group">
								<label class="col-md-3 control-label">Brand</label>
								<div class="col-md-6 selectContainer">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-list"></i></span> <select id="brand"
											name="brand" class="form-control">
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

							<!-- Text input Added By-->
							<div class="form-group">
								<label class="col-md-3 control-label">Added By</label>
								<div class="col-md-6 inputGroupContainer">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-user"></i></span> <input type="text"
											id="userName" " name="userName" class="form-control"
											value="${loggedInUser.firstName} ${loggedInUser.lastName}"
											readonly="readonly">
									</div>
								</div>
							</div>

							<br>
							<div class="form-group row">
								<div class="col-sm-offset-3 col-sm-6">
									<input type="submit" value="Add Model Number"
										class="btn btn-primary btn-block btn-lg" tabindex="9"
										id="addModelNumber"
										onclick="return confirm('Are you sure you want to add model number?');">
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