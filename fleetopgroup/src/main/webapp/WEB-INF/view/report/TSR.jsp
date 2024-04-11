
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
					/ <a href="<c:url value="/TSR.in"/>">Trip Sheet Report</a>
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
				<sec:authorize access="hasAuthority('VIEW_US_TS_AD_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/TripSheetAdvanceReport"/>">User Wise Trip sheet Advances Report </a>
								</h4>
							</div>
						</div>
				</sec:authorize>				
				<!--SRS Travels User Wise Trip sheet Advances Report By Dev Yogi End-->				
				
				
				<!--SRS Travels Branch Wise Trip sheet Advances Report By Dev Yogi Start-->
				<sec:authorize access="hasAuthority('VIEW_BR_TS_AD_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/BranchwiseTSAdvanceReport"/>">Branch Wise Trip sheet Advances Report </a>
								</h4>
							</div>
						</div>
				</sec:authorize>			
				<!--SRS Travels Branch Wise Trip sheet Advances Report By Dev Yogi End-->
				
				
				
				<!--SRS Travels Day wise Trip sheet Advances Report By Dev Yogi Start-->
				<sec:authorize access="hasAuthority('VIEW_DA_TS_AD_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/TSAllAdvanceReport"/>">Day wise Trip sheet Advances Report </a>
								</h4>
							</div>
						</div>
				</sec:authorize>				
				<!--SRS Travels Day wise Trip sheet Advances Report By Dev Yogi End-->

				<!--SRS Travels Trip sheet date Report By Dev Yogi Start-->
				<sec:authorize access="hasAuthority('VIEW_TS_DA_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
<!-- 										<a  -->
<%-- 											href="<c:url value="/TripSheetDateReport"/>">Trip sheet date Report </a> --%>
											
											<a 
											href="<c:url value="/allGroupTripSheetDateReport"/>">Trip sheet date Report </a>
											
											
								</h4>
							</div>
						</div>
				</sec:authorize>				
				<!--SRS Travels Trip sheet date Report By Dev Yogi End-->
				

				<!--SRS Travels Daily Trip sheet Advances Report By Dev Yogi Start -->
				<sec:authorize access="hasAuthority('VIEW_CL_TS_AD_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/DailyTSAllAdvanceReport"/>">Daily Trip sheet Advances Report </a>
								</h4>
							</div>
						</div>
				</sec:authorize>				
				<!--SRS Travels Daily Trip sheet Advances Report By Dev Yogi End -->
				
				
				<!--SRS Travels Closed wise TripSheet Advances Report By Dev Yogi Start-->
				

				<c:if test="${configuration.hideClosedTripSheet}">
					<sec:authorize access="hasAuthority('SHOW_TRIPSHEET_CLOSE_STATUS')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a href="<c:url value="/DailyClosedTSAllAdvanceReport"/>">Closed
										wise TripSheet Advances Report </a>
								</h4>
							</div>
						</div>
					</sec:authorize>
				</c:if>
				<c:if test="${!configuration.hideClosedTripSheet}">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a href="<c:url value="/DailyClosedTSAllAdvanceReport"/>">Closed
									wise TripSheet Advances Report </a>
							</h4>
						</div>
					</div>
				</c:if>

				<!--SRS Travels Closed wise TripSheet Advances Report By Dev Yogi End-->


				<!--SRS Travels Trip Sheet Collection Report By Dev Yogi Start-->
				<sec:authorize access="hasAuthority('VIEW_TS_CO_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/TripSheetCollectionDateReport"/>">Trip Sheet Collection Report </a>
								</h4>
							</div>
						</div>
				</sec:authorize>				
				<!--SRS Travels Trip Sheet Collection Report By Dev Yogi End-->
				
				
				<!--SRS Travels Trip Sheet Status Report By Dev Yogi Start-->
				<sec:authorize access="hasAuthority('VIEW_TS_ST_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/TSSTATUSReport"/>">Trip Sheet Status Report </a>
								</h4>
							</div>
						</div>
				</sec:authorize>				
				<!--SRS Travels Trip Sheet Status Report By Dev Yogi End-->

				<!--SRS Travels Trip Sheet Route Wise Difference KM & Volume Report By Dev Yogi Start -->
				<sec:authorize access="hasAuthority('VIEW_TS_RO_DI_KM_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/TSDiffKMVOLUMEReport"/>">Trip Sheet Route Wise Difference KM &amp; Volume Report </a>
								</h4>
							</div>
						</div>
				</sec:authorize>
				<!--SRS Travels Trip Sheet Route Wise Difference KM & Volume Report By Dev Yogi End-->
				
				<sec:authorize access="hasAuthority('VIEW_ROUTE_WISE_TS_REPORT')">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
									<a href="<c:url value="/RouteWiseTripSheetReport"/>">Route Wise Trip Sheet Report</a>
							</h4>
						</div>
					</div>
				</sec:authorize>
				<sec:authorize access="hasAuthority('TRIP_SHEET_EXPENSE')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/TripCollectionExpenseReport"/>">Trip Collection Report  </a>
								</h4>
							</div>
						</div>
				</sec:authorize>
				
				<!--CREATE DAY WISE EXP REPORT BY DEV Y START -->
				<sec:authorize access="hasAuthority('Create_Day_Wise_Expense')">
					<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/CreateDayWiseExpenseReport"/>">Date Wise Expense Report</a>
								</h4>
							</div>
					</div>			
				</sec:authorize>
				<sec:authorize access="hasAuthority('Create_Day_Wise_Expense')">
					<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/VoucherWiseExpenseReport"/>">Voucher Date Wise Expense Report</a>
								</h4>
							</div>
					</div>			
				</sec:authorize>	
				<!--CREATE DAY WISE EXP REPORT BY DEV Y END -->
				
				<sec:authorize access="hasAuthority('SHOW_DUE_AMOUNT_REPORT')">
					<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/tripsheetDueAmount"/>">Tripsheet DueAmount Report</a>
								</h4>
							</div>
					</div>			
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('SHOW_DUE_AMOUNT_REPORT')">
					<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/tripsheetDueAmountPayment"/>">Tripsheet DueAmount Payment Report</a>
								</h4>
							</div>
					</div>			
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('REFRESHMENT_CONSUMPTION_REPORT')">
					<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/RefreshmentConsumptionReport"/>">Refreshment Consumption Report</a>
								</h4>
							</div>
					</div>			
				</sec:authorize>
				<sec:authorize access="hasAuthority('TRIPSHEET_COLLECTION_REPORT')">
					<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/tripSheetCollectionReport"/>">TripSheet Collection Report </a>
								</h4>
							</div>
					</div>			
				</sec:authorize>	
				<sec:authorize access="hasAuthority('TRIPSHEET_INCOME_REPORT_BY_INCOME_NAME')">
				<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a href="<c:url value="/tripIncomeReportByIncomeName"/>"> 
											Income Name Wise Summary Report
									</a>
								</h4>
							</div>
				</div>
				</sec:authorize>
				<sec:authorize access="hasAuthority('TRIP_LOADING_SHEET_REPORT')">
				<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a href="<c:url value="getTripsheetWithNoLoadingSheet"/>"> 
											TripSheet Loading Sheet Details Report
									</a>
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