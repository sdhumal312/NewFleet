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
					/ <a href="<c:url value="/PR.in"/>">Part Report</a>
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
			
			<!--Part Stock Report Code Modification By Dev Yogi  Starting-->
			<sec:authorize access="hasAuthority('VIEW_PA_ST_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/PartStockReport"/>">Part Stock Report </a>
								</h4>
							</div>
						</div>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_DA_IN_ST_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/DateWiseInventoryStockReport"/>">Date Wise Inventory Stock Report</a>
								</h4>
							</div>
						</div>
				</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_PA_PU_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/PartPurchaseReport"/>">Part Purchase Report</a>
								</h4>
							</div>
						</div>
				</sec:authorize>			
			<sec:authorize access="hasAuthority('VIEW_PA_TR_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/PartTransferLocReport"/>">Part Transfered Report</a>
								</h4>
							</div>
						</div>
				</sec:authorize>			
			<sec:authorize access="hasAuthority('VIEW_PA_TR_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/MostFrequentlyConsumedPart"/>">Most frequently consumed part</a>
								</h4>
							</div>
						</div>
			</sec:authorize>	
			<sec:authorize access="hasAuthority('VIEW_PA_TR_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/partWiseConsumptionReport"/>">Part Consumption Report </a>
								</h4>
							</div>
						</div>
			</sec:authorize>
			<sec:authorize access="hasAuthority('PART_PURCHASE_INVOICE_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/partPurchaseInvoiceReport"/>">Part Purchase Invoice Report </a>
								</h4>
							</div>
						</div>
			</sec:authorize>	
				
			<%-- <sec:authorize access="hasAuthority('TECHNICIAN_WISE_PART_REPORT')"> --%>		
				<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/TechnicianWisePartReport"/>">Technician Wise Part Report </a>
								</h4>
							</div>
						</div>		
			<%-- </sec:authorize> --%>
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
				<sec:authorize access="hasAuthority('REFRESHMENT_STOCK_REPORT')">
					<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/RefreshmentStockReport"/>">Refreshment Stock Report</a>
								</h4>
							</div>
					</div>			
				</sec:authorize>
				<sec:authorize access="hasAuthority('WARRANTY_PART_REPORT')">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a href="<c:url value="/warrantyPartReport.in"/>">Warranty Part Report</a>
							</h4>
						</div>
					</div>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('PART_REQUISITION_STATUS_REPORT')">
				<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a href="<c:url value="/PartRequisitionStatusReport"/>">Part Requisition Status Wise Report</a>
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