<%@ include file="../../taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
	
<style>
td, .demo{
	font-size:14px;	
	text-align:left;
}

tr:nth-child(even) {
     background-color: #eaeded;
}
</style>
<script>
function validateReport(){
	showMessage('errors','no records found');
		return false;
}
</script>

 <div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
				<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/DR.in"/>">Driver </a> / <span id="reportHead">Driver Ledger Report </span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('driverLedgerTable', 'Driver Details Report')">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
					</button>
					<a href="<c:url value="/Report"/>">Cancel</a>

				</div>
			</div>
		</div>
	   </section>
	   <section class="content">
		<sec:authorize access="hasAuthority('VIEW_DR_DE_REPORT')">
				<div class="tab-pane" id="driverReport">
					<div class="panel box box-danger">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#driverOne"> Driver Ledger Details Report </a>
							</h4>
						</div>
						<div class="box-body">
							<form>
								<div class="form-horizontal ">
								
									<div class="row1">
										<label class="L-size control-label">Driver : </label>
										<div class="I-size">
											<input type="hidden" id="driverId" name="DRIVER_ID"
												style="width: 100%;" placeholder="Enter Driver Name" />
										</div>
									</div>
									<div class="row1">
										<label class="L-size control-label">Date range: <abbr
											title="required">*</abbr></label>
										<div class="I-size">
											<div class="input-group">
												<div class="input-group-addon">
													<i class="fa fa-calendar"></i>
												</div>
												<input type="text" id="dateRangeReport" class="form-text"
													name="DATE_RANGE" required="required" readonly="readonly"
													style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
											</div>
										</div>
									</div>
									<br />
									<div class="row1">
										<label class="L-size control-label"></label>
										<div class="I-size">
											<div class="pull-left">
												<button type="submit" name="commit" class="btn btn-success"
													id="btn-save" onclick="return validateDriverLedger();">
													<i class="fa fa-search"> Search</i>
												</button>
											</div>
										</div>
									</div>
								</div>
						 </form>
						</div>
					</div>
				</div>
				</sec:authorize>
		    <sec:authorize access="hasAuthority('VIEW_DRIVER')">
		    
		    <section class="content hide" id="ResultContent">
			<div class="box-body">
				<div id="div_print">
					<section class="invoice">
						<div class="row invoice-info">
						<div class="col-xs-12">
							<div class="table-responsive">
								<div id="sorttable-div" align="center" style="font-size: 10px" class="table-responsive ">
									<div class="row invoice-info" id="reportHeader" style="font-size: 17px;font-weight: bold;">
									</div>
									
									<div id="driverLedger" style="text-align:left;display: none;">
											<table class="row" style="width:30%;">
												<tr><td>Driver Name :  <a class="demo" id="driver" href="#"></a></td></tr>
												<tr><td>Date Range  :  <a class="demo"id="date" href="#"></a></td></tr>
												<tr><td>Opening Balance  :  <a class="demo" id="openBal" href="#"></a></td></tr>
												<tr><td>Close Balance  :  <a  class="demo" id="closeBal" href="#"></a></td></tr>
											</table>
									</div>
									
									<div class="row invoice-info" style="margin-top:31px;">
										<table id="driverLedgerTable" style="width: 100%;"
										class="table-hover table-bordered">
										</table>
									</div>
								</div>
							</div>
						</div>
						</div>
					</section>
				</div>
		  </section>
		</sec:authorize>
	</section>
</div>	   
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js" />"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/lib/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/lib/jquery-ui-1.11.3.min.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/lib/jquery.event.drag-2.3.0.js"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>	
	
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/driver/DriverLedgerReport.js"/>"></script>
	<script	type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Report.js"/>"></script>
