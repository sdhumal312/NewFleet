<%@ include file="../../taglib.jsp"%>
<%@page import="org.springframework.security.core.authority.SimpleGrantedAuthority"%>
<%@page import="org.springframework.security.core.GrantedAuthority"%>
<%@page import="java.util.Collection"%>
<%@page import="org.springframework.web.servlet.ModelAndView"%>
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css">	
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/summary/vendor/css/vendor.bundle.base.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/summary/vendor/mdi/css/materialdesignicons.min.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/consumption/consumption.css"/>">

<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/UA.in"/>">User Activity</a> / <span>User wise Activity Report</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-danger" href="<c:url value="/Report"/>">Cancel</a>
				</div>
			</div>
			<br>
		</div>
	</section>

	<section class="content">
		<div class="box">
		<br>
			<div class="box-body">
			<input type="hidden" id="userId" value="${userId}">
			<input type="hidden" id="userName" value="${userName}">

				<div class="row" id="proBanner">
					<div class="col-sm-6">
						<label class="L-size control-label" style="width: auto;">Date range: <abbr
							title="required">*</abbr>
						</label>
						<div class="I-size">
							<div class="input-group">
								<div class="input-group-addon">
									<i class="fa fa-calendar"></i>
								</div>
								<input type="text" id="dateRange" class="form-text"
									name="TRIP_DATE_RANGE" required="required" onchange="getActivityCount()"
									style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
							</div>
						</div>
					</div>
						<div class="col-sm-6">
											<label class="L-size control-label" for="subscribe" >
												User <abbr title="required">*</abbr>
											</label>
											<div class="col-md-3" style="width: 60%">
												<input class="" placeholder="assignee users" id="subscribe"
													type="hidden" style="width: 100%" onchange="getActivityCount()" name="assigneeId"
													required="required" ondrop="return false;"> 
											</div>
										</div>

	      		</div>
	      	</div>
	      	<br>	
      	</div>
      	<div class="main-body">
			<div class="box">
			<br>
				<div class="box-body">
					 <div class="row">
						<div id="workOrderActivityDiv" class="col-sm-4 stretch-card grid-margin">
							<div class="card bg-gradient-blueOne card-img-holder text-white  ">
								<div class="card-body" style="width: auto;">

									<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" alt="Fuel" class="card-img-absolute">
									
										<div class="row" >
									<div class="row" align="center">
										<span class="font-weight-normal mb-3" >	
											 Work Order Created  : <a class="mb-3 text-white" id="woCreateCount"></a>
										</span>
									</div>
									<br>
									<br>
											<div class="row">
											<div class="col-sm-6" align="left">
								
										<span class="font-weight-normal mb-3">	
											 Modified  : </span>
											 <br>
											  <a class="mb-3 text-white"id="woUpdatedCount"></a> </div>
										<div class="col-sm-6" >
										<span class="font-weight-normal mb-3">	
											  Deleted  : 	</span><br>
											   <a class="mb-3 text-white" id="woDeletedCount"></a> 
										</div>
									</div>
								</div>
							</div>
						</div>
						</div>
						
						 <div id="woActivityGif" class="col-sm-4 stretch-card grid-margin">
							<div class="card bg-gradient-greenOne card-img-holder text-white ">
								<img src="resources\QhyvOb0m3EjE7A4\images\consumption\part.gif" alt="Fuel" style="width: 100%;height: 160px;" >
							</div>
						</div>

						<div id="ServiceEntryActivityDiv" class="col-sm-4 stretch-card grid-margin">
							<div class="card bg-gradient-blueTwo  card-img-holder text-white ">
								<div class="card-body">
									<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" alt="Urea" class="card-img-absolute">
											<div class="row" >
									<div class="row" align="center">
										<span class="font-weight-normal mb-3" >	
											 Service Entries Created  : <a class="mb-3 text-white" id="CreateSECount"></a>
										</span>
									
									</div>
									<br>
									<br>
											<div class="row">
											<div class="col-sm-6" align="left">
								
										<span class="font-weight-normal mb-3">	
											 Modified  : </span>
											 <br>
											  <a class="mb-3 text-white"id="UpdatedSECount"></a> </div>
										
										
											
										<div class="col-sm-6" >
										<span class="font-weight-normal mb-3">	
											  Deleted  : 	</span><br>
											   <a class="mb-3 text-white" id="DeletedSECount"></a> 
									
										</div>
									</div>
								</div>
								</div>
							</div>
						</div>
						
						<div id="seActivityGif" class="col-sm-4 stretch-card grid-margin">
							<div class="card bg-gradient-greenOne card-img-holder text-white ">
								<img src="resources\QhyvOb0m3EjE7A4\images\consumption\part.gif" alt="Fuel" style="width: 100%;height: 160px;" >
							</div>
						</div>

						<div id="TripSheetActivityDiv" class="col-sm-4 stretch-card grid-margin">
							<div class="card bg-gradient-blueThree card-img-holder text-white ">
								<div class="card-body">
									<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" alt="Refreshment" class="card-img-absolute">
											<div class="row" >
									<div class="row" align="center">
										<span class="font-weight-normal mb-3" >	
											 Trip Sheet Created  : <a class="mb-3 text-white" id="CreateTSCount"></a>
										</span>
									
									</div>
									<br>
									<br>
											<div class="row">
											<div class="col-sm-6" align="left">
								
										<span class="font-weight-normal mb-3">	
											 Modified  : </span>
											 <br>
											  <a class="mb-3 text-white"id="UpdatedTSCount"></a> </div>
										
										
											
										<div class="col-sm-6" >
										<span class="font-weight-normal mb-3">	
											  Deleted  : 	</span><br>
											   <a class="mb-3 text-white" id="DeletedTSCount"></a> 
									
										</div>
									</div>
								</div>
								</div>
							</div>
						</div>
						<div id="tsActivityGif" class="col-sm-4 stretch-card grid-margin">
							<div class="card bg-gradient-greenOne card-img-holder text-white ">
								<img src="resources\QhyvOb0m3EjE7A4\images\consumption\part.gif" alt="Fuel" style="width: 100%;height: 160px;" >
							</div>
						</div>
	
					 </div>
					  <div class="row">
					 	<div id="fuelEntriesDiv" class="col-sm-4 stretch-card grid-margin">
							<div class="card bg-gradient-greenOne card-img-holder text-white ">
								<div class="card-body">
									<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" alt="Part" class="card-img-absolute">
									 			<div class="row" >
									<div class="row" align="center">
										<span class="font-weight-normal mb-3" >	
											 Fuel Entries Created  : <a class="mb-3 text-white" id="CreateFECount"></a>
										</span>
									
									</div>
									<br>
									<br>
											<div class="row">
											<div class="col-sm-6" align="left">
								
										<span class="font-weight-normal mb-3">	
											 Modified  : </span>
											 <br>
											  <a class="mb-3 text-white"id="UpdatedFECount"></a> </div>
										
										
											
										<div class="col-sm-6" >
										<span class="font-weight-normal mb-3">	
											  Deleted  : 	</span><br>
											   <a class="mb-3 text-white" id="DeletedFECount"></a> 
									
										</div>
									</div>
								</div>
									 
									 
								</div>
							</div>
						</div>
						<div id="fuelActivityGif"
							class="col-sm-4 stretch-card grid-margin">
							<div class="card bg-gradient-blueOne card-img-holder text-white ">
								<img src="resources\QhyvOb0m3EjE7A4\images\consumption\fuel.gif"
									alt="Fuel" style="width: 100%; height: 160px;">
							</div>
						</div>
						<div id="rrActivityDiv" class="col-sm-4 stretch-card grid-margin">
							<div class="card bg-gradient-greenTwo card-img-holder text-white ">
								<div class="card-body">
									<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" alt="Tyre" class="card-img-absolute">
									<div class="row" >
									<div class="row" align="center">
										<span class="font-weight-normal mb-3" >	
											 Renewal Reminder Created: <a class="mb-3 text-white" id="CreateRRCount"></a>
										</span>
									
									</div>
									<br>
									<br>
											<div class="row">
											<div class="col-sm-6" align="left">
								
										<span class="font-weight-normal mb-3">	
											 Modified  : </span>
											 <br>
											  <a class="mb-3 text-white"id="UpdatedRRCount"></a> </div>
										
										
											
										<div class="col-sm-6" >
										<span class="font-weight-normal mb-3">	
											  Deleted  : 	</span><br>
											   <a class="mb-3 text-white" id="DeletedRRCount"></a> 
									
										</div>
									</div>
								</div>
									 
								</div>
							</div>
						</div>
						<div id="rrActivityGif" class="col-sm-4 stretch-card grid-margin">
							<div class="card bg-gradient-greenOne card-img-holder text-white ">
								<img src="resources\QhyvOb0m3EjE7A4\images\consumption\part.gif" alt="Fuel" style="width: 100%;height: 160px;" >
							</div>
						</div>
						
							<div id="IssuesActivityDiv" class="col-sm-4 stretch-card grid-margin">
							<div class="card bg-gradient-greenTwo card-img-holder text-white ">
								<div class="card-body">
									<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" alt="Tyre" class="card-img-absolute">
									<div class="row" >
									<div class="row" align="center">
										<span class="font-weight-normal mb-3" >	
											 Issues Created: <a class="mb-3 text-white" id="CreateIssuesCount"></a>
										</span>
									
									</div>
									<br>
									<br>
											<div class="row">
											<div class="col-sm-6" align="left">
								
										<span class="font-weight-normal mb-3">	
											 Modified  : </span>
											 <br>
											  <a class="mb-3 text-white"id="UpdatedIssuesCount"></a> </div>
										
										
											
										<div class="col-sm-6" >
										<span class="font-weight-normal mb-3">	
											  Deleted  : 	</span><br>
											   <a class="mb-3 text-white" id="DeletedIssuesCount"></a> 
									
										</div>
									</div>
								</div>
									 
								</div>
							</div>
						</div>
						
							<div id="IssuesActivityGif" class="col-sm-4 stretch-card grid-margin">
							<div class="card bg-gradient-greenOne card-img-holder text-white ">
								<img src="resources\QhyvOb0m3EjE7A4\images\consumption\part.gif" alt="Fuel" style="width: 100%;height: 160px;" >
							</div>
						</div>
						<div class ="row">
						
							<div id="poActivityDiv" class="col-sm-4 stretch-card grid-margin">
							<div class="card bg-gradient-orangeOne card-img-holder text-white ">
								<div class="card-body">
									<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" alt="Tyre" class="card-img-absolute">
									<div class="row" >
									<div class="row" align="center">
										<span class="font-weight-normal mb-3" >	
											 Purchase order Created: <a class="mb-3 text-white" id="CreatePoCount"></a>
										</span>
									
									</div>
									<br>
									<br>
											<div class="row">
											<div class="col-sm-6" align="left">
								
										<span class="font-weight-normal mb-3">	
											 Modified  : </span>
											 <br>
											  <a class="mb-3 text-white"id="UpdatedPoCount"></a> </div>
										
										
											
										<div class="col-sm-6" >
										<span class="font-weight-normal mb-3">	
											  Deleted  : 	</span><br>
											   <a class="mb-3 text-white" id="DeletedPoCount"></a> 
									
										</div>
									</div>
								</div>
									 
								</div>
							</div>
						</div>
						<div id="PoActivityGif" class="col-sm-4 stretch-card grid-margin">
							<div class="card bg-gradient-greenOne card-img-holder text-white ">
								<img src="resources\QhyvOb0m3EjE7A4\images\consumption\part.gif" alt="Fuel" style="width: 100%;height: 160px;" >
							</div>
						</div>
						
							<div id="dseActivityDiv" class="col-sm-4 stretch-card grid-margin">
							<div class="card bg-gradient-orangeOne card-img-holder text-white ">
								<div class="card-body">
									<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" alt="Tyre" class="card-img-absolute">
									<div class="row" >
									<div class="row" align="center">
										<span class="font-weight-normal mb-3" >	
											 Dealer service Created: <a class="mb-3 text-white" id="CreateDSECount"></a>
										</span>
									
									</div>
									<br>
									<br>
											<div class="row">
											<div class="col-sm-6" align="left">
								
										<span class="font-weight-normal mb-3">	
											 Modified  : </span>
											 <br>
											  <a class="mb-3 text-white"id="UpdatedDSECount"></a> </div>
										
										
											
										<div class="col-sm-6" >
										<span class="font-weight-normal mb-3">	
											  Deleted  : 	</span><br>
											   <a class="mb-3 text-white" id="DeletedDSECount"></a> 
									
										</div>
									</div>
								</div>
									 
								</div>
							</div>
						</div>
						<div id="dseActivityGif" class="col-sm-4 stretch-card grid-margin">
							<div class="card bg-gradient-greenOne card-img-holder text-white ">
								<img src="resources\QhyvOb0m3EjE7A4\images\consumption\part.gif" alt="Fuel" style="width: 100%;height: 160px;" >
							</div>
						</div>
						
						</div>


				</div>
			</div>
		</div>	
		</div>
	</section>
</div>




	
	<div id="loader"></div>
	<script type="text/javascript">
		$(function() {
			$('[data-toggle="popover"]').popover()
		})
	</script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/userActivity/userActivity.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/userActivity/viewUserActivities.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/summary/jquery.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/summary/Chart.min.js"/>"></script>	
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"/>"></script>	
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>	
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"/>"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"/>"></script>
