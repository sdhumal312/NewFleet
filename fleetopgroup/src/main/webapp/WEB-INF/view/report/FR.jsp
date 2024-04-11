<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<style>
.box-body .affix {
	border-radius: 3px;
	background: #FFF;
	margin-bottom: 5px;
	padding: 5px;
}
</style>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/FR.in"/>">Fuel Report</a>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/Report"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">

			<div class="col-md-offset-2 col-md-8 col-sm-8 col-xs-12">
				<div class="tab-pane" id="FuelReport">
					<sec:authorize access="hasAuthority('VIEW_VE_FU_MI_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a href="<c:url value="/FuelMileageReport"/>">Vehicle Wise Fuel Mileage Report </a>
								</h4>
							</div>
						</div>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_FU_RA_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a href="<c:url value="/VehicleFuelRange"/>">Fuel Range Report </a>
								</h4>
							</div>
						</div>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_FU_CO_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a href="<c:url value="/DailyFuelConsumption"/>"> Daily Fuel Consumption Report </a>
								</h4>
							</div>
						</div>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_VE_FU_MI_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a href="<c:url value="/VendorWiseFuelMileageReport"/>">Vendor Wise Fuel Mileage Report </a>
								</h4>
							</div>
						</div>
					</sec:authorize>
					<%-- <sec:authorize access="hasAuthority('VIEW_MO_VE_WI_FU_REPORT')"> --%>
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a href="<c:url value="/MonthlyVehicleWiseFuelReport"/>">Monthly vehicle wise fuel report </a>
								</h4>
							</div>
						</div>
						
						<sec:authorize access="hasAuthority('ALL_VEHICLES_MILEAGE_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a href="<c:url value="AllVehiclesMileageReport"/>">All Vehicles Mileage Report </a>
								</h4>
							</div>
						</div>
					 </sec:authorize> 
					
					<!--Dev Y Code Start New Module Fuel Date Range Cash/Credit Wise Report -->
					<%-- <sec:authorize access="hasAuthority('VIEW_VE_FU_MI_REPORT')">  --%> <!-- Mod needed-->
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a href="<c:url value="/FuelDateRangeCashOrCreditWiseReport"/>">Fuel Date Range Cash/Credit Wise Report</a>
								</h4>
							</div>
						</div>
					<%-- </sec:authorize> --%>
					
					
					<sec:authorize access="hasAuthority('DATE_WISE_FUEL_ENTRY_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a href="<c:url value="/dateWiseFuelEntryReport"/>">Date Wise Fuel Entry Report</a>
								</h4>
							</div>
						</div>
					</sec:authorize>
					
					<sec:authorize access="hasAuthority('USER_WISE_FUEL_ENTRY_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a href="<c:url value="/userWiseFuelEntryReport"/>">User Wise Fuel Entry Report</a>
								</h4>
							</div>
						</div>
					</sec:authorize>
					
					<sec:authorize access="hasAuthority('DATE_WISE_FUEL_ENTRY_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a href="<c:url value="/creationWiseFuelEntryReport"/>">Creation Date Wise Fuel Entry Report</a>
								</h4>
							</div>
						</div>
					</sec:authorize>
					
					<sec:authorize access="hasAuthority('TRIPSHEET_WISE_FUEL_ENTRY_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a href="<c:url value="/tripSheetWiseFuelEntryReport"/>">TripSheet Wise Fuel Entry Report</a>
								</h4>
							</div>
						</div>
					</sec:authorize>
					
					<sec:authorize access="hasAuthority('Driver_Wise_Fuel_Entry_Report')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a href="<c:url value="/driverWiseFuelEntryReport"/>">Driver Wise Fuel Entry Report</a>
								</h4>
							</div>
						</div>
					</sec:authorize>
					
				</div>
			</div>
		</div>
	</section>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/validateReports.js"/>"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Report.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"/>"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		});
	</script>
</div>