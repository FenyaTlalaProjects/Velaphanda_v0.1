<%@include file="templates/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<c:import url="templates/tableresizefont.jsp"></c:import>
<c:import url="templates/stylesheetlib.jsp"></c:import>
<title>Decline Leave</title>
<style type="text/css">
.declineButton {
	margin-left: 25%;
	margin-right: 10%;
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
						<b>Leave No : LV0000000${leave.leaveID}</b>
					</div>
					<div class="panel-body">

						<form:form class="well form-horizontal" method="post"
							action="declinedLeave" id="decline"
							modelAttribute="declinedLeave">


							<input id="leaveID" name="leaveID" class="form-control"
								type="hidden" value='${leave.leaveID}' readonly="readonly">

							<!-- Text area Decline Reason-->
							<div class="form-group">
								<label class="col-md-3 control-label">Reason Decline</label>
								<div class="col-md-6 inputGroupContainer">
									<div class="input-group">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-pencil"></i></span>
										<textarea cols="10" rows="10" class="form-control"
											id="comments" name="comments" required="required"></textarea>
									</div>
								</div>
							</div>
							<br>
							<div class="declineButton">
								<div class="form-group row">
									<div class="col-sm-offset-2 col-md-5">
										<input type="submit" value="Decline Leave"
											class="btn btn-primary btn-block btn-lg" tabindex="9"
											id="declineLeave" name="declineLeave">
									</div>
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
	<c:import url="templates/ticketmanagementscript.jsp"></c:import>
	<c:import url="templates/sidebar-collapse.jsp"></c:import>
	<!-- /Scripts -->
	<!-- Validate decline order -->
	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							$('#decline')
									.bootstrapValidator(
											{
												feedbackIcons : {
													valid : 'glyphicon glyphicon-ok',
													invalid : 'glyphicon glyphicon-remove',
													validating : 'glyphicon glyphicon-refresh'
												},
												fields : {
													reasonDeclined : {
														validators : {
															stringLength : {
																min : 2,

															},
															notEmpty : {
																message : 'Decline reason is required and cannot be empty'
															}
														}
													}
												}

											});
						});
	</script>
</body>
</html>