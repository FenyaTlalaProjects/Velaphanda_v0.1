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
					<div class="panel-heading" align="center">Riso Service
									Manuals</div>
					<div class="panel-body">

					<ul id="ticket-summary" class="nav nav-tabs">
							<div class="row">

								<div class="col-xs-6 col-md-6">
									<div class="panel panel-default">
										<a href='#Toners' data-toggle="tab">
											<div class="panel-body easypiechart-panel">
												<div class="easypiechart" id="easypiechart-orange"
													data-percent="">
													<span class="percent"><h5>Service Manual</h5></span>
												</div>
											</div>
										</a>
									</div>
								</div>
								<div class="col-xs-6 col-md-6">
									<div class="panel panel-default">
										<a href='#Parts' data-toggle="tab">
											<div class="panel-body easypiechart-panel">
												<div class="easypiechart" id="easypiechart-darkgreen"
													data-percent="">
													<span class="percent"><h5>Parts Catalog</h5></span>
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

							<div class="tab-pane" id="Toners">

								<legend align=center>Service Manual</legend>


								<table data-toggle="table" data-url="" data-show-refresh="true"
									data-show-toggle="true" data-search="true"
									data-select-item-name="toolbar1" data-pagination="true"
									data-sort-name="modelbrand" data-sort-order="aesc">
									<thead>
										<tr>
											<th data-field="modelbrand" data-sortable="true">Model
												Number</th>
											<th data-field="fileSize" data-sortable="true">File Size</th>
											<th data-field="viewPDF" data-sortable="true">View PDF</th>
											<th data-field="downloadPDF" data-sortable="true">Download
												PDF</th>
										</tr>
									</thead>
									<tbody>

									</tbody>
								</table>
								<!-- table -->

							</div>

							<div class="tab-pane" id="Parts">
								<legend align=center>Parts Catalog</legend>

								<table data-toggle="table" data-url="" data-show-refresh="true"
									data-show-toggle="true" data-search="true"
									data-select-item-name="toolbar1" data-pagination="true"
									data-sort-name="modelbrand" data-sort-order="aesc">
									<thead>
										<tr>
											<th data-field="modelbrand" data-sortable="true">Model
												Number</th>
											<th data-field="fileSize" data-sortable="true">File Size</th>
											<th data-field="viewPDF" data-sortable="true">View PDF</th>
											<th data-field="downloadPDF" data-sortable="true">Download
												PDF</th>
										</tr>
									</thead>
									<tbody>

									</tbody>
								</table>
								<!-- table -->
							</div>
							<!-- Parts -->




						</div>
						<!-- /.content-->

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
