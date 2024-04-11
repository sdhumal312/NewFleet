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
					/ <a href="<c:url value="/TR.in"/>">Tyre Report</a>
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
			
			<!--Tyre Purchase Report Code Modification By Dev Yogi Starting-->
			<sec:authorize access="hasAuthority('VIEW_TY_PO_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/TyrePurchaseReport"/>">Tyre Purchase Report </a>
								</h4>
							</div>
						</div>
				</sec:authorize>			
			<!--Tyre Purchase Report Code Modification By Dev Yogi Ending-->
			
			
			
			<!--Tyre Stock Report Code Modification By Dev Yogi Starting -->
			<sec:authorize access="hasAuthority('VIEW_TY_ST_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/TyreStockReport"/>">Tyre Stock Report </a>
								</h4>
							</div>
						</div>
				</sec:authorize>
			<!--Tyre Stock Report Code Modification By Dev Yogi Ending-->

			<!--Tyre Retread Report Code Modification By Dev Yogi Starting -->
			<sec:authorize access="hasAuthority('VIEW_TY_RE_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/TyreRetreadStockReport"/>">Tyre Retread Report</a>
								</h4>
							</div>
						</div>
				</sec:authorize>
			<!--Tyre Retread Report Code Modification By Dev Yogi Ending  -->



			<sec:authorize access="hasAuthority('VIEW_TY_RE_REPORT')">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
									<a href="<c:url value="/TyreTransferedReport"/>">Tyre Transfered Report</a>
							</h4>
						</div>
					</div>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('TYRE_SENT_FOR_RETREADING_REPORT')">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
									<a href="<c:url value="/TyreSentForRethreadingReport"/>">Tyre Sent for Retreading Report</a>
							</h4>
						</div>
					</div>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('Tyre_Retread_Invoice_Report')">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
									<a href="<c:url value="/TyreRetreadInvoiceReport"/>">Tyre Retread Invoice Report</a>
							</h4>
						</div>
					</div>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VEHICLE_WISE_TYRE_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a href="<c:url value="/VehicleWiseTyreReport"/>">All Vehicle Tyre Asignment Report</a>
								</h4>
							</div>
						</div>
				</sec:authorize>
				
				<!--Vehicle Tyre Assignment History Report By Devy Start -->
				<%-- <sec:authorize access="hasAuthority('VEHICLE_TYRE_ASSIGNMENT_HISTORY_REPORT')"> --%>
				<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a href="<c:url value="/vehicleTyreAssignmentHistoryReport"/>"> 
											Vehicle Tyre Assignment History Report
									</a>
								</h4>
							</div>
				</div>
				<%-- </sec:authorize> --%>
				<!--Vehicle Tyre Assignment History Report Devy Stop -->
				
				<div class="panel box box-primary">
					<div class="box-header with-border">
						<h4 class="box-title">
							<a href="<c:url value="/TyreExpenseDetailsReport"/>"> 
									Tyre Expense Report
							</a>
						</h4>
					</div>
				</div>
					<sec:authorize access="hasAuthority('VEHICLEWISE_TYRE_ASSIGN_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a href="<c:url value="/VehicleWiseTyreAssignReport"/>">Vehicle Wise Tyre Assign Report</a>
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
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Report.js"/>"></script>
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