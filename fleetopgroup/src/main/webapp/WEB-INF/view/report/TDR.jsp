<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/monthpicker.css"/>">
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
					/ <a href="<c:url value="/TDR.in"/>">Trip Collection Report</a>
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
				<sec:authorize access="hasAuthority('VIEW_DE_RO_TI_REPORT')">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
									<a 
										href="<c:url value="/DailyTripCollectionDailyCashbookReport"/>">Depot Wise Daily Trip Collection
									&amp; CashBook Report </a>
							</h4>
						</div>
					</div>
				</sec:authorize>
				<sec:authorize access="hasAuthority('DEPOT_WISE_DAILY_TRIP_COLLECTION_STATUS_REPORT')">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
									<a 
										href="<c:url value="/DepotWiseDailyTripCollectionStatusReport"/>">Depot Wise Daily Trip Collection Status Report</a>
							</h4>
						</div>
					</div>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_DE_DA_TD_REPORT')">
				<div class="panel box box-primary">
					<div class="box-header with-border">
						<h4 class="box-title">
							<a href="<c:url value="/DailyTripDailyReport"/>">Depot Wise Daily Trip Collection
									Report</a>
						</h4>
					</div>
				</div>
			</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_DE_VE_TI_REPORT')">
				<div class="panel box box-primary">
					<div class="box-header with-border">
						<h4 class="box-title">
							<a href="<c:url value="/DailyTripDailyTimeReport"/>">Vehicle Trip Wise Report</a>
						</h4>
					</div>
				</div>
			</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_DE_RO_TI_REPORT')">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
									<a 
										href="<c:url value="/TripDailyRouteTimeReport"/>">Depot Trip Wise
										Report </a>
							</h4>
						</div>
					</div>
				</sec:authorize>
				<sec:authorize access="hasAuthority('DEPOT_WISE_DATE_RANGE_WEEKLY_MONTHLY_YEARLY_TCR')">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
									<a 
										href="<c:url value="/DailyMonthlyYearlyTripCollectionReport"/>">Depot Wise Date Range Weekly,
											Monthly, Yearly Trip Collection Report</a>
							</h4>
						</div>
					</div>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_VE_DA_TD_REPORT')">
				<div class="panel box box-primary">
					<div class="box-header with-border">
						<h4 class="box-title">
							<a href="<c:url value="/VehicleWiseTripDailyReport"/>">Vehicle Wise Date Range Weekly,
									Monthly, Yearly Trip Collection Report </a>
						</h4>
					</div>
				</div>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VEHICLE_WISE_FUEL_MILEAGE_REPORTS')">
				<div class="panel box box-primary">
					<div class="box-header with-border">
						<h4 class="box-title">
							<a href="<c:url value="/VehicleWiseFuelMileageReport"/>"> Vehicle Wise Fuel Mileage Report</a>
						</h4>
					</div>
				</div>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_DA_FU_MI_REPORT')">
				<div class="panel box box-primary">
					<div class="box-header with-border">
						<h4 class="box-title">
							<a href="<c:url value="/DriverWiseFuelMileageReport"/>"> Driver Wise Fuel Mileage Report  </a>
						</h4>
					</div>
				</div>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_CO_TD_REPORT')">
				<div class="panel box box-primary">
					<div class="box-header with-border">
						<h4 class="box-title">
							<a href="<c:url value="/ConductorWiseCollectionReport"/>">Conductor Wise Trip Collection Report </a>
						</h4>
					</div>
				</div>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_RO_DA_TD_REPORT')">
				<div class="panel box box-primary">
					<div class="box-header with-border">
						<h4 class="box-title">
							<a href="<c:url value="/RouteWiseTripDailyReport"/>">Route Wise Date Range Weekly,
									Monthly, Yearly Trip Collection Report </a>
						</h4>
					</div>
				</div>
				</sec:authorize>
				<!-- <div class="panel box box-success">
					<div class="box-header with-border">
						<h4 class="box-title">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#TripCollectionFour">All Route Wise Date Range Weekly,
								Monthly, Yearly Trip Collection Report </a>
						</h4>
					</div>
					<div id="TripCollectionFour" class="panel-collapse collapse">
						<div class="box-body">
							<div class="form-horizontal ">
								Show Group Search Fuel Range
								<form action="DateWiseTripDailyReport" method="post">
									Date range
									<div class="row1">
										<label class="L-size control-label">Date range: <abbr
											title="required">*</abbr></label>
										<div class="I-size">
											<div class="input-group">
												<div class="input-group-addon">
													<i class="fa fa-calendar"></i>
												</div>
												<input type="text" id="DRTC_daterange" class="form-text"
													name="TRIP_DATE_RANGE" required="required"
													style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
											</div>
										</div>
									</div>


									<fieldset class="form-actions">
										<div class="row1">
											<label class="L-size control-label"></label>

											<div class="I-size">
												<div class="pull-left">
													<button type="submit" name="commit" class="btn btn-success">
														<i class="fa fa-search"> Search</i>
													</button>
												</div>
											</div>
										</div>
									</fieldset>

								</form>
								end Show Group Search RR Range
							</div>
						</div>
					</div>
				</div> -->
				<sec:authorize access="hasAuthority('VIEW_AL_RO_DA_TD_REPORT')">
				<div class="panel box box-primary">
					<div class="box-header with-border">
						<h4 class="box-title">
							<a href="<c:url value="/DateWiseGroupTDReport"/>"> All Depot Wise Date Range
									Weekly, Monthly, Yearly Trip Collection Report </a>
						</h4>
					</div>
				</div>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_DA_TD_REPORT')">
				<div class="panel box box-primary">
					<div class="box-header with-border">
						<h4 class="box-title">
							<a href="<c:url value="/DateWiseAllGroupTDReport"/>"> Date Range Wise Trip Collection
									Report</a>
						</h4>
					</div>
				</div>
				</sec:authorize>
				
				
				
				<sec:authorize access="hasAuthority('TRIP_SHEET_EXPENSE_NAME')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/TripCollectionExpenseNameReport"/>">Trip Collection Expense Name Report  </a>
								</h4>
							</div>
						</div>
				</sec:authorize>
			</div>
		</div>
	</section>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/validateReports.js"/>"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
		
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/validateReport1.js"/>"></script>	
		
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
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/monthpicker.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripRouteFixedAdd.js"/>"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask();
			$('#monthRangeSelector').Monthpicker();
			$('#monthRangeSelector2').Monthpicker();
		})
	</script>
</div>