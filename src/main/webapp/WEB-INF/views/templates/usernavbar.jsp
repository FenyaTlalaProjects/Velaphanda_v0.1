<%@include file="taglibs.jsp"%>
	<style type="text/css">	
		/* For mobile phones: */
		[class*="col-"] {
		    width: 100%;
		}
		@media only screen and (min-width: 768px) {
		    /* For desktop: */
		    .col-1 {width: 8.33%;}
		    .col-2 {width: 16.66%;}
		    .col-3 {width: 25%;}
		    .col-4 {width: 33.33%;}
		    .col-5 {width: 41.66%;}
		    .col-6 {width: 50%;}
		    .col-7 {width: 58.33%;}
		    .col-8 {width: 66.66%;}
		    .col-9 {width: 75%;}
		    .col-10 {width: 83.33%;}
		    .col-11 {width: 91.66%;}
		    .col-12 {width: 100%;}
		}

	</style>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/bootstrap-3.3.7/css/bootstrap.min.css" />"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/custom/css/styles.css" />" />
	
	<nav class="navbar navbar-inverse navbar-fixed-top" style="background-color:#0d50a1;" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#sidebar-collapse">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="userdashboard.html"><span style="color:#f3ab45;font-size:1.5em;" >Velaphanda</span> Technical System</a>
				<ul class="user-menu">
					<li class="dropdown pull-right">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown"><svg class="glyph stroked male-user"><use xlink:href="#stroked-male-user"></use></svg> ${user}<span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="login.html"><svg class="glyph stroked cancel"><use xlink:href="#stroked-cancel"></use></svg> Logout</a></li>
						</ul>
					</li>
				</ul>
			</div>							
		</div><!-- /.container-fluid -->
	</nav>
		
	<div id="sidebar-collapse" class="col-sm-2 col-md-2 sidebar">
		<form role="search">
			<a href="userdashboard.html"><img src="resources/images/mainlogo_small.jpg" style="margin-left:22%;margin-right:22%;"></a>
		</form>
		<ul class="nav menu">
			<li class="parent ">
				<a href="#">
					<span data-toggle="collapse" href="#sub-item-2"><svg class="glyph stroked chevron-down"><use xlink:href="#stroked-chevron-down"></use></svg> Customers </span>
				</a>
				<ul class="children collapse" id="sub-item-2">
					<li>
						<a href="#">
							<span data-toggle="collapse" href="#sub-item-3"> Customer Management </span>
						</a>
						<ul class="children collapse" id="sub-item-3">
							<ul style="list-style:none;">
								<li>
									<a class=""  href='<c:url value="userDisplayCustomers.html"/>'>
										View Customer
									</a>
								</li>
							</ul>	
						</ul>
					</li>
					<li>
						<a href="#">
							<span data-toggle="collapse" href="#sub-item-4"> Device Management </span>
						</a>
						<ul class="children collapse" id="sub-item-4">
							<ul style="list-style:none;">
								<li>
									<a class="" href='<c:url value="userSearchDevice.html"/>'>
									View Device
									</a>
								</li>
							</ul>
						</ul>
					</li>					
				</ul>
			</li>
			
			<li><a href='<c:url value="userleavemanagement.html"/>'><span class="glyphicon glyphicon-pencil"></span> Leave Management </a></li>
			<li><a href='<c:url value="userordermanagement.html"/>'><span class="glyphicon glyphicon-shopping-cart"></span> Order Management</a></li>
			<li><a href='<c:url value="usersparemanagement.html"/>'><span class="glyphicon glyphicon-wrench"></span> Spares Management</a></li>
			<li><a href='<c:url value="userticketmanagement.html"/>'><span class="glyphicon glyphicon-text-width"></span> Tickets Management</a></li>
			<li><a href='<c:url value="servicemanualuser.html"/>'><span class="glyphicon glyphicon-book"></span> Service Manuals </a></li>
				
		
			<li role="presentation" class="divider"></li>
		 </ul>		
	</div><!--/.sidebar-->
