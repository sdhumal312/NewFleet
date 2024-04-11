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
.closeAmount th, td {
	text-align: center;
}

.closeRouteAmount td {
	text-align: center;
	font-weight: bold;
	color: blue;
}

.closeGroupAmount td {
	text-align: center;
	font-weight: bold;
}

.actualkm {
	float: center;
}

.columnDaily {
	text-align: center;
}
</style>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/TDR.in"/>">Trip Collection Report</a> / <span>Daily
						Trip Collection Time Report</span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm hide"
						onclick="printDiv('div_print')" id="printBtn">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm hide"
						onclick="advanceTableToExcel('datadetails', 'DMY TripCollection Report')" id="exportExcelBtn">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
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
									<input type="hidden" id="showChalo" value="${configuration}" />	
									<input type="hidden" id="wt" value="${hideWT}">
									<input type="hidden" id="epk" value="${hideEPK}">
									<input type="hidden" id="ot" value="${hideOT}">
									<input type="hidden" id="netCollection" value="${netCollection}">
									<input type="hidden" id="totalBalance" value="${totalBalance}">
									<input type="hidden" id="dieselAmount" value="${showDieselAmount}">
									<input type="hidden" id="diffTotalNetCollectionFormula" value="${diffTotalNetCollectionFormula}">
										<div class="row1">
											<label class="L-size control-label"> Depot Name : <abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input type="hidden" id="TCGroupWise" name="VEHICLEGROUP"
													style="width: 100%;" value="ALL"
													placeholder="Please Enter 2 or more Group Name" />
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
													<input type="text" id="LocWorkOrder" class="form-text"
														name="TRIP_DATERANGE" required="required"
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
			<div id="div_print_ex">

				<div id="div_print">
					<table class="table table-hover table-bordered table-striped" id="datadetails">
						<tr>
							<td>
							<div class="row invoice-info">
							<div class="col-xs-12">
								<div class="table-responsive">
									<table class="table table-hover table-bordered table-striped" id="companyDetails">
										
									</table>
									<div id="sorttable-div" align="center" style="font-size: 10px"
										class="table-responsive ">
										<div class="row invoice-info" id="reportHeader">
										</div>
										<div class="row invoice-info">
											<table id="advanceTable" style="width: 100%"
																class="table table-hover table-bordered table-striped">
												
											</table>
										</div>
									</div>
									<div id="sorttable-div" align="center" style="font-size: 10px"
										class="table-responsive ">
										<div class="row invoice-info">
											<table style="width: 85%">
																<tbody>
																	<tr>
																		<td align="center"><span class="text-bold">
																				CashBook Date Report </span></td>
																	</tr>
																	<tr>
																		<td align="center"><span class="text-bold" id="secondTableHeader"></span></td>
																	</tr>
																</tbody>
															</table>
										</div>
										</div>
										<div class="row invoice-info">
											<table id="cashBookTable" style="width: 75%"
																class="table table-hover table-bordered table-striped">
												
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
							</td>
						</tr>
					</table>
					<section class="invoice">

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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/DailyMonthlyYearlyTripCollection.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script>
</div>