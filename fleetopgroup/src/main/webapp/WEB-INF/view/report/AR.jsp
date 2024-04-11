<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
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
					/ <a href="<c:url value="/AR.in"/>">Attendance Report</a>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/Report"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="col-md-offset-2 col-md-8 col-sm-8 col-xs-12">
			<sec:authorize access="hasAuthority('VIEW_DR_AT_REPORT')">
				<div class="panel box box-primary">
					<div class="box-header with-border">
						<h4 class="box-title">
							<a href="<c:url value="/DriverAttReport"/>"> Driver Attendance Report </a>
						</h4>
					</div>
				</div>
			</sec:authorize>
			
			

			<sec:authorize access="hasAuthority('FLAVOR_ONE_PRIVILEGE')">
			
			<!--SRS Travels Group Wise Driver Attendance Report By Dev Yogi Start -->
			<sec:authorize access="hasAuthority('VIEW_GO_DR_AT_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/GroupDriverAttReport"/>">Group Wise Driver Attendance Report </a>
								</h4>
							</div>
						</div>
				</sec:authorize>			
			<!--SRS Travels Group Wise Driver Attendance Report By Dev Yogi End -->
			
			<!--SRS Travels Driver Attendance Point Report By Dev Yogi Start -->
			<sec:authorize access="hasAuthority('VIEW_DR_PO_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/DriverHaltPointReport"/>"> Driver Attendance Point Report </a>
								</h4>
							</div>
						</div>
				</sec:authorize>
			
			<!--SRS Travels Driver Attendance Point Report By Dev Yogi End -->
			
			
			<!--SRS Travels Group Wise Driver Point Report By Dev Yogi Start-->
			<sec:authorize access="hasAuthority('VIEW_GO_DR_PO_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/GroupDriverHaltPointReport"/>">Group Wise Driver Point Report </a>
								</h4>
							</div>
						</div>
				</sec:authorize>			
			<!--SRS Travels Group Wise Driver Point Report By Dev Yogi End-->

			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_DR_LO_HA_REPORT')">
				<div class="panel box box-primary">
					<div class="box-header with-border">
						<h4 class="box-title">
							<a href="<c:url value="/DriverlocalHaltReport"/>"> Driver Local Halt Report</a>
						</h4>
					</div>
				</div>
			</sec:authorize>
			
			<sec:authorize access="hasAuthority('FLAVOR_ONE_PRIVILEGE')">
			<sec:authorize access="hasAuthority('VIEW_TS_HA_BA_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/HaltDateRangeReport"/>">TripSheet HaltBata Report </a>
								</h4>
							</div>
						</div>
			</sec:authorize>
			</sec:authorize>
			<!--SRS Travels TripSheet HaltBata Report By Dev Yogi End -->

			<sec:authorize access="hasAuthority('VIEW_DO_DA_AT_REPORT')">
				<div class="panel box box-primary">
					<div class="box-header with-border">
						<h4 class="box-title">
							<a href="<c:url value="/DepotDriverAttReport"/>">Depot Wise Driver Attendance Report </a>
						</h4>
					</div>
				</div>
			</sec:authorize>
			
			<sec:authorize access="hasAuthority('FLAVOR_TWO_PRIVILEGE')">
				<sec:authorize access="hasAuthority('VIEW_DO_DA_AT_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/DepotDriverAttReport"/>">Depot Wise Attendance Report </a>
								</h4>
							</div>
						</div>
				</sec:authorize>
			</sec:authorize>
		</div>
	</section>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Report.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		});
	</script>
</div>