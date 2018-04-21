<%@include file="taglibs.jsp"%>
	
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
				<a class="navbar-brand" href="technicianHome.html"><span style="color:#f3ab45;font-size:1.5em;" >Velaphanda</span> Technical System</a>
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
		
	<div id="sidebar-collapse" class="col-sm-3 col-lg-2 sidebar">
		<form role="search">
			<a href="technicianHome.html"><img src="resources/images/mainlogo_small.jpg" style="margin-left:22%;margin-right:22%;"></a>
		</form>
		<ul class="nav menu">
			<li><a href='<c:url value="techleavemanagement.html"/>'><span class="glyphicon glyphicon-pencil"></span> Leave Management </a></li>
			<li><a href='<c:url value="ordertechmanagement.html"/>'><span class="glyphicon glyphicon-shopping-cart"></span> Order Management</a></li>
			<li><a href='<c:url value="techsparemanagement.html"/>'><span class="glyphicon glyphicon-wrench"></span> Spares Management</a></li>
			<li><a href='<c:url value="techticketmanagement.html"/>'><span class="glyphicon glyphicon-text-width"></span> Tickets Management</a></li>
			<li><a href='<c:url value="servicemanualtech.html"/>'><span class="glyphicon glyphicon-book"></span> Service Manuals </a></li>
			<li role="presentation" class="divider"></li>
		 </ul>		
	</div><!--/.sidebar-->