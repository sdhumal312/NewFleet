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
					/ <a href="<c:url value="/UR.in"/>">Upholstery Report</a>
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
					
					
					<sec:authorize access="hasAuthority('UPHOLSTERY_PURCHASE_INVOICE_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a href="<c:url value="/upholsteryPurchaseInvoiceReport.in"/>"> Upholstery Purchase Invoice Report </a>
								</h4>
							</div>
						</div>
					</sec:authorize>
					
				</div>
			</div>
		</div>
		
		<!--Laundry Upholstery receive report  Start-->
		<div class="row">
			<div class="col-md-offset-2 col-md-8 col-sm-8 col-xs-12">
				<div class="tab-pane" id="FuelReport">
					
					
					<%-- <sec:authorize access="hasAuthority('UPHOLSTERY_PURCHASE_INVOICE_REPORT')"> --%>
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a href="<c:url value="/laundryUpholsteryReceiveReport.in"/>"> Laundry Upholstery receive report </a>
								</h4>
							</div>
						</div>
					<%-- </sec:authorize> --%>
					
				</div>
			</div>
		</div>		
		<!--Laundry Upholstery receive report  Stop-->
		
		
		<!-- Upholstery Stock Report Start -->
		<div class="row">
			<div class="col-md-offset-2 col-md-8 col-sm-8 col-xs-12">
				<div class="tab-pane" id="FuelReport">					
					<%-- <sec:authorize access="hasAuthority('UPHOLSTERY_STOCK_REPORT')"> --%>
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a href="<c:url value="/upholsteryStockReport.in"/>"> Upholstery Stock Report </a>
								</h4>
							</div>
						</div>
					<%-- </sec:authorize> --%>					
				</div>
			</div>
		</div>		
		<!-- Upholstery Stock Report Stop -->
		<%-- <sec:authorize access="hasAuthority('UPHOLSTERY_ASSIGNMENT_REPORT')"> --%>
		<div class="row">
			<div class="col-md-offset-2 col-md-8 col-sm-8 col-xs-12">
				<div class="tab-pane" id="FuelReport">					
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a href="<c:url value="/upholsteryAssignmentReport.in"/>">Upholstery Assignment report </a>
							</h4>
						</div>
					</div>
				</div>
			</div>
		</div>		
		<%-- </sec:authorize> --%>
		
		<%-- <sec:authorize access="hasAuthority('UPHOLSTERY_STOCK_TRANSFER_REPORT')"> --%>
		<div class="row">
			<div class="col-md-offset-2 col-md-8 col-sm-8 col-xs-12">
				<div class="tab-pane" id="FuelReport">					
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a href="<c:url value="/upholsteryStockTransferReport.in"/>">Stock Transfer report </a>
							</h4>
						</div>
					</div>
				</div>
			</div>
		</div>		
		<%-- </sec:authorize> --%>
		
		
		<!--Upholstery Sent To Laundry Report Start-->
		<div class="row">
			<div class="col-md-offset-2 col-md-8 col-sm-8 col-xs-12">
				<div class="tab-pane" id="FuelReport">					
					<%-- <sec:authorize access="hasAuthority('UPHOLSTERY_SENT_TO_LAUNDRY_REPORT')"> --%>
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a href="<c:url value="/upholsterySentToLaundryReport.in"/>"> Upholstery Sent To Laundry Report </a>
								</h4>
							</div>
						</div>
					<%-- </sec:authorize> --%>					
				</div>
			</div>
		</div>		
		<!--Upholstery Sent To Laundry Report Stop-->
		
		
		
		<!--Upholstery Loss Report Start-->
		<div class="row">
			<div class="col-md-offset-2 col-md-8 col-sm-8 col-xs-12">
				<div class="tab-pane" id="FuelReport">					
					<sec:authorize access="hasAuthority('UPHOLSTERY_LOSS_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a href="<c:url value="/upholsteryLossReport.in"/>"> Upholstery Loss Report </a>
								</h4>
							</div>
						</div>
					</sec:authorize>					
				</div>
			</div>
		</div>	
		<!--Upholstery Loss Report Stop-->
		
		
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