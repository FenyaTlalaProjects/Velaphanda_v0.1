<%@include file="templates/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<c:import url="templates/stylesheetlib.jsp"></c:import>
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

		<c:if test="${not empty retMessage }">
			<div class="alert alert-info" role="alert">
				<c:out value="${ retMessage}">
				</c:out>
			</div>
		</c:if>
		<c:if test="${not empty retErrorMessage }">
			<div class="alert alert-danger" role="alert">
				<c:out value="${ retErrorMessage}">
				</c:out>
			</div>
		</c:if>

		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading" align="center">
						<b>Decline Order</b>
					</div>
					<div class="panel-body">


						<div class="tab-content">

							<form:form class="well form-horizontal" method="post"
								action="declinedOrder" id="decline"
								modelAttribute="declinedOrder">


								<!-- Text input Order Number-->
								<div class="form-group">
									<label class="col-md-3 control-label">Order Number</label>
									<div class="col-md-6 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-barcode"></i></span> <input
												id="orderNum" name="orderNum" class="form-control"
												type="text" value='ORD00${OrderNum.recordID}'
												readonly="readonly">
										</div>
									</div>
								</div>
								<input id="recordID" name="recordID" class="form-control"
									type="hidden" value='${OrderNum.recordID}' readonly="readonly">


								<!-- Text area Decline Reason-->
								<div class="form-group">
									<label class="col-md-3 control-label">Reason for
										Declining</label>
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
											<input type="submit" value="Decline Order"
												class="btn btn-primary btn-block btn-lg" tabindex="9"
												id="declineOrder" name="declineOrder"
												onclick="return confirm('Are you sure you want to decline this order?');">
										</div>
									</div>
									</div>
								
									
							</form:form>

						</div>
						<!-- /tab-content -->
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