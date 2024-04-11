<%@ include file="../taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/fullcalendar/fullcalendar.min.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/fullcalendar/fullcalendar.print.css" />"
	media="print">
<style>
.box-partheader {
	border: none;
	position: relative
}

.partboxheader {
	width: 100%;
	margin: 0 auto
}

.partboxheader h3 {
	font-size: 15px;
	font-weight: 700;
	text-align: center
}

.partboxheader p {
	font-size: 13px;
	font-weight: 600;
	text-align: center
}

.workingday {
	margin: 10 auto;
	width: 100%;
	padding: 5px
}

.workingday .name {
	float: left;
	width: 60%;
	font-size: 14px;
	text-align: right
}

.workingday .value {
	float: left;
	width: 30%;
	font-size: 18px;
	text-align: center
}

.workingday .value span {
	font-size: 13px
}
</style>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="viewBusBookingDetails.in">Bus
						booking list</a>/<span> Bus booking Calendar</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-link btn-sm" href="ViewServiceReminderList.in">
						<span id="AddVehicle"> Cancel</span>
					</a>
				</div>
			</div>

		</div>
	</section>
	<!-- Main content -->
	<section class="content-body">
		<div class="row">

			<div class="modal fade" id="editrenewalPeriod" role="dialog">
				<div class="modal-dialog" style="width: 1250px;">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">Bus booking list</h4>
						</div>
						<div class="modal-body">

							<table id="dataTable" style="width: 100%; display: none;"
								class="table-responsive table">
								<thead>
									<tr>
										<th>Id</th>
										<th>Booking Number</th>
										<th>Party Name</th>
										<th>Vehicle Type</th>
										<th>Trip Start Date</th>
										<th>Trip End Date</th>
										<th>PickUp Address</th>

										<th></th>
										<th></th>
									</tr>
								</thead>
								<tbody id="tableBody">

								</tbody>
							</table>

						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close">Close</span>
							</button>
						</div>
					</div>
				</div>
			</div>
			<%-- 			<sec:authorize access="!hasAuthority('VIEW_SERVICE_REMINDER_CALENDAR')"> --%>
			<%-- 				<spring:message code="message.unauth"></spring:message> --%>
			<%-- 			</sec:authorize> --%>
			<%-- 			<sec:authorize access="hasAuthority('VIEW_SERVICE_REMINDER_CALENDAR')"> --%>
			<div class="row">
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="box">
						<div class="box-body">
							<p align="center" style="font-weight: bold;">From :
								${startDate} To: ${startEnd}</p>

						</div>

						<div class="box-body">
							<div align="center">
								<button type="button" class="btn btn-success btn-sm"
									onclick="vehicleTypeWiseCount();" style="font-weight: bold;">
									Total bus booking <label id="totalCountMonth"> </label>
								</button>
							</div>
						</div>
						<div class="box-body">
							<div align="center">

								<table id="VehicleTypeTable" style="display: none;">

									<tbody id="vehickeTypetableBody">

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
							<!-- THE CALENDAR -->
							<div id="fullcal"></div>

						</div>
					</div>

				</div>
				<div class="col-sm-1 col-md-2  col-xs-12"></div>
			</div>
			<%-- 			</sec:authorize> --%>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.6/moment.js"></script>

	<script src="/js/jquery.datetimepicker.full.min.js"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fullcalendar/fullcalendar.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/busbooking/BusBookingCelenderDetails.js" />"></script>
</div>