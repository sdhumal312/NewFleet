<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/fullcalendar/fullcalendar.min.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/fullcalendar/fullcalendar.print.css" />"
	media="print">
<style>
.box-partheader{border:none;position:relative}.partboxheader{width:100%;margin:0 auto}.partboxheader h3{font-size:15px;font-weight:700;text-align:center}.partboxheader p{font-size:13px;font-weight:600;text-align:center}.workingday{margin:10 auto;width:100%;padding:5px}.workingday .name{float:left;width:60%;font-size:14px;text-align:right}.workingday .value{float:left;width:30%;font-size:18px;text-align:center}.workingday .value span{font-size:13px}
</style>
<div class="content-wrapper">
		<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/vehicle/1/1"/>">Vehicle</a> / <a
						href="<c:url value="/showVehicle.in?vid=${vehicle.vid}"/>"><c:out
							value="${vehicle.vehicle_registration}" /></a> / <span>
						Vehicle BreakDown Calendar</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-link btn-sm"
						href="<c:url value="/showVehicle.in?vid=${vehicle.vid}"/>"> <span
						id="AddVehicle"> Cancel</span>
					</a>
				</div>
			</div>
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_VEHICLE')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
					<div class="pull-left">
						<a href="${pageContext.request.contextPath}/getImageVehicle/${vehicle.vehicle_photoid}.in"
								class="zoom" data-title="Vehicle Photo" data-footer="" 
								data-type="image" data-toggle="lightbox"> 
								<span class="info-box-icon bg-green" id="iconContainer"><i class="fa fa-bus"></i></span>
							        <img src="${pageContext.request.contextPath}/getImageVehicle/${vehicle.vehicle_photoid}.in"
							        class="img-rounded" alt=" " width="100" height="100" id="vehicleImage" onerror="hideImageOnError(this)" />
						</a>
					</div>
					<div class="pull-left1">
						<h3 class="secondary-header-title">
							<a href="showVehicle.in?vid=${vehicle.vid}"> <c:out
									value="${vehicle.vehicle_registration}" />
							</a>
						</h3>
						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Status"><a
										href="#"><c:out value=" ${vehicle.vehicle_Status}" /></a></span></li>
								<li><span class="fa fa-clock-o" aria-hidden="true"
									data-toggle="tip" data-original-title="Odometer"><a
										href="#"><c:out value=" ${vehicle.vehicle_Odometer}" /></a></span></li>
								<li><span class="fa fa-bus" aria-hidden="true"
									data-toggle="tip" data-original-title="Type"><a href="#"><c:out
												value=" ${vehicle.vehicle_Type}" /></a></span></li>
								<li><span class="fa fa-map-marker" aria-hidden="true"
									data-toggle="tip" data-original-title="Location"><a
										href="#"><c:out value=" ${vehicle.vehicle_Location}" /></a></span></li>
								<li><span class="fa fa-users" aria-hidden="true"
									data-toggle="tip" data-original-title="Group"><a
										href="#"><c:out value=" ${vehicle.vehicle_Group}" /></a></span></li>
								<li><span class="fa fa-road" aria-hidden="true"
									data-toggle="tip" data-original-title="Route"><a
										href="#"><c:out value=" ${vehicle.vehicle_RouteName}" /></a></span></li>

							</ul>
						</div>
					</div>
				</sec:authorize>
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content-body">
		<div class="row">
			<sec:authorize access="hasAuthority('VIEW_DRIVER_ATTENDANCE ') or hasAuthority('VIEW_ISSUES')">
				<div class="row">
					<div class="col-md-3 col-sm-3 col-xs-12">
						<div class="box">
							<div class="box-body">
								<p align="center" style="font-weight: bold;">From : ${startDateNew} To: ${startEndNew}</p>
								<div class="col-md-9 col-sm-9 col-xs-12 hide" id="serviceEntrySummaryTable">
								<div class="partboxheader">
										<h3 align="left">Service Entries Details</h3>
									</div>
									<table border="1" class="table table-hover table-striped" style="font-size: 17px; text-align: center;">
										<thead>
											<tr>
												<th>Sr.</th>
												<th>Number</th>
												<th>Invoice Date</th>
												<th>amount</th>
											</tr>
										</thead>
										<tbody id="serviceEntrySummaryTableBody">
											
										</tbody>
									</table>
								 </div>	
								<div class="col-md-9 col-sm-9 col-xs-12 hide" id="workOrderSummaryTable">
								<div class="partboxheader">
										<h3 align="left">Work Order Details</h3>
									</div>
									<table border="1" class="table table-hover table-striped" style="font-size: 14px; text-align: center;">
										<thead>
											<tr>
												<th>Sr.</th>
												<th>Number</th>
												<th>Start Date</th>
												<th>Due Date</th>
												<th>amount</th>
											</tr>
										</thead>
										<tbody id="workOrderSummaryTableBody">
											
										</tbody>
									</table>
								 </div>	
								<div class="col-md-9 col-sm-9 col-xs-12 hide" id="issueSummaryTable">
								<div class="partboxheader">
										<h3 align="left">Issue Details</h3>
									</div>
									<table border="1" class="table table-hover table-striped" style="font-size: 17px; text-align: center;">
										<thead>
											<tr>
												<th>Sr.</th>
												<th>Number</th>
												<th>Date</th>
												<th>Status</th>
											</tr>
										</thead>
										<tbody id="issueSummaryTableBody">
											
										</tbody>
									</table>
								 </div>	
								<div class="col-md-9 col-sm-9 col-xs-12 hide" id="vehicleCommentSummaryTable">
								<div class="partboxheader">
										<h3 align="left">Vehicle Comment Details</h3>
									</div>
									<table border="1" class="table table-hover table-striped" style="font-size: 17px; text-align: center;">
										<thead>
											<tr>
												<th>Sr.</th>
												<th>Date</th>
												<th>Title</th>
												<th>Comment</th>
											</tr>
										</thead>
										<tbody id="vehicleCommentSummaryTableBody">
											
										</tbody>
									</table>
								 </div>	
								<div class="col-md-9 col-sm-9 col-xs-12 hide" id="tripSheetSummaryTable">
								<div class="partboxheader">
										<h3 align="left">Trip Sheet Details</h3>
									</div>
									<table border="1" class="table table-hover table-striped" style="font-size: 17px; text-align: center;">
<!-- 										<thead> -->
<!-- 											<tr> -->
<!-- 												<th>Sr.</th> -->
<!-- 												<th>Number</th> -->
<!-- 												<th>Start Date</th> -->
<!-- 												<th>End Date</th> -->
<!-- 											</tr> -->
<!-- 										</thead> -->
										<tbody id="tripSheetSummaryTableBody">
											
										</tbody>
									</table>
								 </div>	
							</div>
						</div>
					</div>
					<div class="col-md-6 col-sm-7 col-xs-12">
						<div class="box">
							<div class="box-body">
								<input type="hidden" id="start" value="${startDate}"> <input
									type="hidden" id="end" value="${startEnd}">
								<input type="hidden" id="vid" value="${vehicle.vid}">
								<!-- THE CALENDAR -->
								<div id="fullcal"></div>

							</div>
						</div>
						
					</div>
					<div class="col-sm-1 col-md-2  col-xs-12">
					
					<%@include file="VehicleSideMenu.jsp"%>
				</div>
				</div>
			</sec:authorize>
		</div>
	</section>
	<script type="text/javascript">
		 $(document).ready(function() {
	         var img = $("#vehicleImage");
	         var iconContainer = $("#iconContainer");
	
	         // Check if the image is loaded
	         img.on("load", function() {
	             // If loaded, hide the icon
	             iconContainer.hide();
	         });
	     });
	</script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fullcalendar/fullcalendar.min.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/vehicle/VehicleBreakDownDetails.js" />"></script>
	<script type='text/javascript' src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/photoView/ekko-lightbox.js"/>" ></script>
</div>
