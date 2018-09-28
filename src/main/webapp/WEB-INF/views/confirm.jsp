<%@include file="templates/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Confirmation | Velaphanda Trading & Projects</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<c:import url="templates/tableresizefont.jsp"></c:import>
<c:import url="templates/stylesheetlib.jsp"></c:import>

</head>
<body>

	<c:import url="templates/confirmationPopup.jsp"></c:import>
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
		<!--/.row-->

		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading" align="center">
						<b>Confirmation</b>
					</div>
					<div class="panel-body">
						<div class="tab-content">

							<!-- Successful message -->
							<c:if test="${not empty retMessage }">

								<div class="alert alert-info" role="alert">

									<!-- Log a Ticket -->
									<c:choose>
										<c:when test="${tickets =='tickets' }">
											<c:out value="${retMessage}"></c:out> Click<a
												href="ticket.html" class="confirmtions"><b> here</b></a> to log another Ticket.
							 	   </c:when>
									</c:choose>
									
									
											<!-- Replace Toner -->
									<c:choose>
										<c:when test="${replaceToner =='replaceToner' }">
											<c:out value="${retMessage}"></c:out>. Click<a
												href="tonerReplacementUser.html" class="confirmtions"><b> here</b></a> to replace another toner.
							   </c:when>
									</c:choose>
									<c:choose>
										<c:when test="${replaceToner =='replaceToner' }">
											<c:if test="${not empty message}">
												<c:out value="${message}"></c:out>  Click<a
													href="tonerReplacementUser.html" class="confirmtions" class="danger"><b>
														here</b></a> to replace another toner.
							   </c:if>
										</c:when>
									</c:choose>
									

									<!-- Place an Orders -->
									<c:choose>
										<c:when test="${orders =='orders' }">
											<c:out value="${retMessage}"></c:out> 
						 	        Click<a href="userPlaceOrder.html" class="confirmtions"><b>
													here</b></a> to place another Order.                             
						 	    </c:when>
									</c:choose>

									<!-- create leave -->
									<c:choose>
										<c:when test="${techAddLeave =='techAddLeave' }">
											<c:out value="${retMessage}"></c:out> 
							 	           Click<a href="userleavemanagement.html"
												class="confirmtions"><b> here</b></a> to go to leave management.                         
							 	    </c:when>
									</c:choose>

									<!-- performTicketAciton -->
									<c:choose>

										<c:when test="${performTicketAction =='performTicketAction' }">
											<c:out value="${retMessage}"></c:out> 
							 	          Click<a href="techticketmanagement.html"
												class="confirmtions"><b> here</b></a> to go to ticket management.	                            
							 	    </c:when>
									</c:choose>
									
										<!-- move Spare Parts -->
									<c:choose>
										<c:when test="${moveSpares =='moveSpares' }">
											<c:out value="${retMessage}"></c:out> Click<a
												href="sparemanagement.html" class="confirmtions"><b>
													here</b></a> to go to spare management.
				 	   		     
				 	   					 </c:when>
									</c:choose>
									
									<!-- acknowledge Tickets For Tech -->
									<c:choose>
										<c:when test="${acknowledgeTicketsForTech =='acknowledgeTicketsForTech' }">
											<c:out value="${retMessage}"></c:out> 
					 	       			   Click<a href="ticketmanagement.html"
												class="confirmtions"><b> here</b></a> to go to ticket management.	                            
					 	   			 </c:when>
									</c:choose>
									
									<!-- acknowledge Tickets For Tech -->
									<c:choose>
										<c:when test="${takeTicketsForTech =='takeTicketsForTech' }">
											<c:out value="${retMessage}"></c:out> 
					 	       			   Click<a href="ticketmanagement.html"
												class="confirmtions"><b> here</b></a> to go to ticket management.	                            
					 	   			 </c:when>
									</c:choose>	

								</div>

							</c:if>
							<!-- Successful message -->

							<!-- On failure returned message -->
							<c:if test="${not empty retErrorMessage}">

								<div class="alert alert-danger" role="alert">
									<!-- Place an Orders -->
									<c:choose>
										<c:when test="${orders =='orders' }">
											<c:out value="${retErrorMessage}"></c:out> 
						 	        	Click<a href="userPlaceOrder.html" class="confirmtions">
												<b> here</b>
											</a> to place another Order.
		                            	
						 	    	</c:when>
									</c:choose>
									<!-- Log a Ticket -->
									<c:choose>
										<c:when test="${tickets =='tickets' }">
											<c:out value="${retErrorMessage}"></c:out> Click<a
												href="ticket.html" class="confirmtions"><b> here</b></a> to log another Ticket. 
							 	   </c:when>
									</c:choose>
									<!-- create leave -->
									<c:choose>
										<c:when test="${techAddLeave =='techAddLeave' }">
											<c:out value="${retErrorMessage}"></c:out> 
							 	           Click<a href="userleavemanagement.html"
												class="confirmtions"><b> here</b></a> to go to leave management.                         
							 	    </c:when>
									</c:choose>

									<!-- performTicketAciton -->
									<c:choose>
										<c:when test="${performTicketAction =='performTicketAction' }">
											<c:out value="${retErrorMessage}"></c:out> 
							 	          Click<a href="techticketmanagement.html"
												class="confirmtions"><b> here</b></a> to go to ticket management.	                            
							 	    </c:when>
									</c:choose>
									
									<!-- moveSpares -->
									<c:choose>
										<c:when test="${moveSpares == 'moveSpares'}">
											<c:out value="${retErrorMessage}"> Click<a
													href="sparemanagement.html" class="confirmtions"><b>
														here</b></a> to go to spare management.</c:out>
										</c:when>
									</c:choose>

								</div>
							</c:if>

						</div>
						<!-- /tab-content -->


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