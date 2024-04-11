<%@ include file="../taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/monthpicker.css"/>">	
<!-- 	<<style>
/*  td{
margin-left:5px;
font-size:12pt;
text-align:left;
} */  
#issue, #comment td {
 font-size:10pt;
 text-align: left;
 border="1";
 /* padding-left: 10px; */

 
}

#tripCollExpenseName td{
 font-size:10pt;
 padding-left: 10px;
 }
</style> -->
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/FR.in"/>">Fuel Report</a>  / <span>Fuel Efficiency Data Report </span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>					
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('tripCollExpenseName', 'Fuel Efficiency Data Report')" id="exportExcelBtn">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
					</button>
					<button class="btn btn-default btn-sm "
						onclick="saveImageToPdf('tripCollExpenseName')" id="printPdf">
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
											<label class="L-size control-label">Vehicle Name 
											</label>
											<div class="I-size">
												<input type="hidden" id="ReportSelectVehicle" name="vid"
													style="width: 100%;" placeholder="ALL" />
											</div>
										</div>
										
										<div class="row1">
											<label class="L-size control-label"> Route : 
												</label>
											<div class="I-size">
												<input type="hidden" id="TripRouteList" name="TRIP_ROUTE"
													style="width: 100%;" placeholder="ALL" />
											</div>
										</div>
										
										<!-- Month range -->
										<div class="row1">
												<label class="L-size control-label">Date range: <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<div class="input-group">
														<div class="input-group-addon">
															<i class="fa fa-calendar"></i>
														</div>
														<input type="text" id="rangeFuelMileage" class="form-text"
															name="fuelmileage_daterange" required="required"
															style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
													</div>
												</div>
										</div>
										
										<div class="row1">
												<label class="L-size control-label"></label>

												<div class="I-size">
													<div class="pull-left">
														<button type="submit" name="commit"
															class="btn btn-success" id="btn-save">
															<i class="fa fa-search"> Search</i>
														</button>
													</div>
												</div>
										</div>
									</div>
							</div>
						<!-- </div> -->
					</div>
	<section class="content hide" id="ResultContent">
			<div class="box-body">
				<div id="div_print">
					<section class="invoice">
						<div class="row invoice-info">
							<div class="col-xs-12">
								<div class="table-responsive">
									<div id="sorttable-div" align="center" style="font-size: 10px"
										class="table-responsive ">
										<div class="row invoice-info" id="reportHeader" style="font-size: 15px;font-weight: bold;">
										</div>
										<div class="row invoice-info">
											<table id="tripCollExpenseName" style="width: 95%;"
																class="table-hover table-bordered">
												
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
						</div>
					</section>
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
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/monthpicker.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/FU/FuelEfficiencyDataReport.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script>
	<script type="text/javascript" 
		src="resources/QhyvOb0m3EjE7A4/js/fleetop/jspdf/jspdf.min.js"></script>			
	
</div>