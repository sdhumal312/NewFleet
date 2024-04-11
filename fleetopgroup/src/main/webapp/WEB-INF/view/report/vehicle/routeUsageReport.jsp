<%@ include file="../taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/monthpicker.css"/>">
<style>
.scrollit {
    overflow:scroll;
    height:600px;
}
.sticky {
  position: sticky;
  top: 0;
  width: 100%;
}
</style>	
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> / 
					<a href="<c:url value="/Report"/>">Report</a> / 
					<a href="<c:url value="/ProfitAndLoss.in"/>">Profit And Loss Report</a> / Route wise usage and movement report
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm hide" onclick="printDiv('div_print')" id="printBtn">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm hide" onclick="advanceTableToExcel('batteryTransferDetails', 'Route wise usage and movement report')" id="exportExcelBtn">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
					</button>
					<button class="btn btn-default btn-sm hide"
						onclick="saveImageToPdf('div_print')" id="printPdf">
						<span class="fa fa-file-excel-o"> Export to PDF</span>
					</button>
					<a href="<c:url value="/Report"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	
	<section class="content">
		<div class="panel box box-primary">
			<div class="box-body">
				<div class="form-horizontal ">
					<div class="row1">
						<label class="L-size control-label"> Route : <abbr title="required">*</abbr></label>
						<div class="I-size">
							<input type="hidden" id="TripRouteNameList" name="TRIP_ROUTE" style="width: 100%;"
								placeholder="Please Enter 3 or more Route Name, NO " />
						</div>
					</div>
					<c:if test="${configuration.showVehicleFilter}">
						<div class="row1">
							<label class="L-size control-label">Vehicle Name </label>
							<div class="I-size">
								<input type="hidden" id="vid"
									name="vid" style="width: 100%;"
									required="required"
									placeholder="All" />
							</div>
						</div>
					</c:if>
					<div class="row1">
						<label class="L-size control-label">Date range: <abbr title="required">*</abbr></label>
						<div class="I-size">
							<div class="input-group">
								<div class="input-group-addon">
									<i class="fa fa-calendar"></i>
								</div>
								<input type="text" id="reportrange" class="form-text" name="VEHICLE_DATERANGE" required="required"
									style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
							</div>
						</div>
					</div>
					<div class="row1">
						<label class="L-size control-label"></label>
						<div class="I-size">
							<div class="pull-left">
								<button  name="commit" class="btn btn-success" id="btn-save">
									<i class="fa fa-search"> Search</i>
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<section class="content hide" id="ResultContent">
		<div class="box-body">
			<div id="div_print">
				<section class="invoice">
					<div class="row invoice-info">
						<div class="col-xs-12">
							<div class="table-responsive">
								<div id="sorttable-div" align="center" style="font-size: 10px" class="table-responsive ">
									<div id="selectedData" style="text-align: left;">
										<table> <tbody id="selectedReportDetails"> </tbody> </table>
									</div>
									<div class="row invoice-info" id="reportHeader" style="font-size: 15px;font-weight: bold;">
									</div>
									<div id="scrollId" class="row invoice-info scrollit">
										<table id="batteryTransferDetails" style="width: 95%;" class="table-hover table-bordered">
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</section>
			</div>
		</div>
	</section>
</div>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/routeUsageReport.js" />"></script>
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
	
		
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js" />"></script>
			<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/jspdf/jspdf.min.js"></script>	
