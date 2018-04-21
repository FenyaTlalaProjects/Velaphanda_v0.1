<%@include file="templates/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Home | Velaphanda Trading & Projects</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
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
					<div class="panel-heading" align="center">Tickets</div>
					<div class="panel-body">

						<div class="row">
							<div class="col-xs-6 col-md-3">
								<div class="panel panel-default">
									<a href='<c:url value="openTickets"/>'>
										<div class="panel-body easypiechart-panel">
											<h5>Open</h5>
											<div class="easypiechart" id="easypiechart-blue"
												data-percent="${openTickets}">
												<span class="percent">${openTickets}</span>
											</div>
										</div>
									</a>
								</div>
							</div>
							<div class="col-xs-6 col-md-3">
								<div class="panel panel-default">
									<a href='<c:url value="acknowledgedTickets"/>'>
										<div class="panel-body easypiechart-panel">
											<h5>Acknowledged</h5>
											<div class="easypiechart" id="easypiechart-orange"
												data-percent="${countAcknowledgedTickets}">
												<span class="percent">${countAcknowledgedTickets}</span>
											</div>
										</div>
									</a>
								</div>
							</div>
							<div class="col-xs-6 col-md-3">
								<div class="panel panel-default">
									<a href='<c:url value="takenTickets"/>'>
										<div class="panel-body easypiechart-panel">
											<h5>Taken</h5>
											<div class="easypiechart" id="easypiechart-teal"
												data-percent="${countTakenTickets}">
												<span class="percent">${countTakenTickets}</span>
											</div>
										</div>
									</a>
								</div>
							</div>
							<div class="col-xs-6 col-md-3">
								<div class="panel panel-default">
									<a href='<c:url value="ticketsAwaitingSpares"/>'>
										<div class="panel-body easypiechart-panel">
											<h5>Awaiting Spare</h5>
											<div class="easypiechart" id="easypiechart-purple"
												data-percent="${awaitingSparesTickets}">
												<span class="percent">${awaitingSparesTickets}</span>
											</div>
										</div>
									</a>
								</div>
							</div>
						</div>
						<!--/.row-->

						<div class="row">
							<div class="col-xs-6 col-md-3">
								<div class="panel panel-default">
									<a href='<c:url value="escalatedTickets"/>'>
										<div class="panel-body easypiechart-panel">
											<h5>Escalated</h5>
											<div class="easypiechart" id="easypiechart-yellow"
												data-percent="${escalatedTickets}">
												<span class="percent">${escalatedTickets}</span>
											</div>
										</div>
									</a>
								</div>
							</div>
							<div class="col-xs-6 col-md-3">
								<div class="panel panel-default">
									<a href='<c:url value="sLABridgedTickets"/>'>
										<div class="panel-body easypiechart-panel">
											<h5>SLA Bridged</h5>
											<div class="easypiechart" id="easypiechart-red"
												data-percent="${bridgedTickets}">
												<span class="percent">${bridgedTickets}</span>
											</div>
										</div>
									</a>
								</div>
							</div>
							<div class="col-xs-6 col-md-3">
								<div class="panel panel-default">
									<a href='<c:url value="resolvedTickets"/>'>
										<div class="panel-body easypiechart-panel">
											<h5>Resolved</h5>
											<div class="easypiechart" id="easypiechart-darkgreen"
												data-percent="${resolvedTickets}">
												<span class="percent">${resolvedTickets}</span>
											</div>
										</div>
									</a>
								</div>
							</div>
							<div class="col-xs-6 col-md-3">
								<div class="panel panel-default">
									<a href='<c:url value="closedTickets"/>'>
										<div class="panel-body easypiechart-panel">
											<h5>Closed</h5>
											<div class="easypiechart" id="easypiechart-green"
												data-percent="${closedTickets}">
												<span class="percent">${closedTickets}</span>
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
