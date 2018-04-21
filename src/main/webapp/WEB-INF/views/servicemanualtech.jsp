<%@include file="templates/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Service Manual | Velaphanda Trading & Projects</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<c:import url="templates/tableresizefont.jsp"></c:import>
<c:import url="templates/stylesheetlib.jsp"></c:import>

</head>
<body>

	<c:import url="templates/techniciannavbar.jsp"></c:import>

	<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
		<div class="row">
			<ol class="breadcrumb">
				<li><a href="technicianHome.html"><svg class="glyph stroked home">
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
						<b>Service Manuals</b>
					</div>
					<div class="panel-body">						

						
							<div class="row">

								<div class="col-xs-6 col-md-3">
									<div class="panel panel-default">
										<a href='canonForTechnician'>
											<div class="panel-body easypiechart-panel">
												<div class="easypiechart" id="easypiechart-orange"
													data-percent="">
													<span class="percent"><h5>Canon</h5></span>
												</div>
											</div>
										</a>
									</div>
								</div>
								<div class="col-xs-6 col-md-3">
									<div class="panel panel-default">
										<a href='risoForTechnician'>
											<div class="panel-body easypiechart-panel">
												<div class="easypiechart" id="easypiechart-darkgreen"
													data-percent="">
													<span class="percent"><h5>Riso</h5></span>
												</div>
											</div>
										</a>
									</div>
								</div>
								<div class="col-xs-6 col-md-3">
									<div class="panel panel-default">
										<a href="samsungForTechnician">
											<div class="panel-body easypiechart-panel">
												<div class="easypiechart" id="easypiechart-teal"
													data-percent="">
													<span class="percent"><h5>Samsung</h5></span>
												</div>
											</div>
										</a>
									</div>
								</div>
								<div class="col-xs-6 col-md-3">
									<div class="panel panel-default">
										<a href='toshibaForTechnician'>
											<div class="panel-body easypiechart-panel">
												<div class="easypiechart" id="easypiechart-purple"
													data-percent="">
													<span class="percent"><h5>Toshiba</h5></span>
												</div>
											</div>
										</a>
									</div>
								</div>

							</div>
					
						<!--/.row-->

					</div>
					<!-- .panel-body -->
				</div>
				<!-- .panel panel-default -->
			</div>
			<!-- /.col-->
			<!-- /.row -->
			</div>
			<!-- Footer -->
			<c:import url="templates/footer.jsp"></c:import>
			<!--/ Footer -->
		</div>
		<!--/.main-->
		<c:import url="templates/javascriptslib.jsp"></c:import>
		<c:import url="templates/ticketmanagementscript.jsp"></c:import>
		<c:import url="templates/sidebar-collapse.jsp"></c:import>
		<!-- /Scripts -->
</body>
</html>

