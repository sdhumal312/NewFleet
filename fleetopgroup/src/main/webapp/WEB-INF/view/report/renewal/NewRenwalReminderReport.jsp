<%@ include file="../../taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/slick.grid.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/slick.pager.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/smoothness/jquery-ui-1.11.3.custom.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/examples.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/slick.columnpicker.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/bootstrap/bootstrap-float-label.min.css" />">

<style>
.row {
	padding: 1%
}

.center {
	margin: 0;
	position: absolute;
	top: 50%;
	left: 50%;
	-ms-transform: translate(-50%, -50%);
	transform: translate(-50%, -50%);
}

.aligncenter {
	float: left;
	width: 33.33333%;
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
					/ <a href="<c:url value="/RRR.in"/>">Renewal Report</a> <span
						id="reportHead"> /Renewal Reminder Report </span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm hide" id="printBtn">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm hide"
						onclick="advanceTableToExcel('tripCollectionExpenseList', 'Date Wise Fuel Entry Report')"
						id="exportExcelBtn">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
					</button>
					<a href="<c:url value="/Report"/>">Cancel</a>

				</div>
			</div>
		</div>
	</section>

	<section class="content">
		<div class="panel box box-primary">
			<div class="box-body" id="ElementDiv">
				<div class="form-horizontal ">
					<input type="hidden" id="companyId" value="${companyId}">
					<div class="row">
						<div class="col col-sm-4 col-md-4">
							<label class="has-float-label"> <input type="hidden"
								id="vehicleId" style="width: 100%;" placeholder="All" /><span
								style="color: #2e74e6; font-size: 15px;">Vehicle</span>
							</label>
						</div>
						<div class="col col-sm-4 col-md-3">
							<label class="has-float-label"> <input type="hidden"
								id="VehicleTypeSelect" name="VehicleTypeSelect"
								style="width: 100%;" required="required" placeholder="All" /> <span
								style="color: #2e74e6; font-size: 15px;">Vehicle Type</span></label>
						</div>
						<div class="col col-sm-4 col-md-4">
							<label class="has-float-label"> <input type="hidden"
								id="branchList" name="depot" style="width: 100%;"
								placeholder="ALL" /> <span
								style="color: #2e74e6; font-size: 15px;">Depot</span>
							</label>
						</div>
					</div>
					<div class="row">
						<div class="col col-sm-4 col-md-4">
							<label class="has-float-label"> <input type="hidden"
								id="from" name="renewalTypeId" style="width: 100%;"
								placeholder="All" /> <span
								style="color: #2e74e6; font-size: 15px;">Renewal Type</span></label>
						</div>
						<div class="col col-sm-4 col-md-3">
							<label class="has-float-label"> <select
								style="width: 100%;" name="renewal_Subid" id="to">
							</select> <span style="color: #2e74e6; font-size: 15px;">Renewal
									SubType</span>
							</label>
						</div>
						<div class="col col-sm-4 col-md-4">
							<div class="input-group">
								<div class="input-group-addon">
									<i class="fa fa-calendar"></i>
								</div>
								<label class="has-float-label"> <input type="text"
									id="dateRange" class="form-text" name="PART_RANGE_DATE"
									required="required" readonly="readonly"
									style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 95%">
									<span style="color: #2e74e6; font-size: 15px;">Date
										range: </span></label>
							</div>
						</div>
					</div>

					<div class="row">
						<label class="L-size control-label"></label>
						<div class="I-size">
							<div class="center">
								<button type="submit" name="commit" class="btn btn-success"
									id="btn-save">
									<i class="fa fa-search"> Search</i>
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<section class="content" id="ResultContent" style="display: none;">
		<div class="box-body">
			<div id="div_print">

				<div id="div_print">
					<section class="invoice">

						<!--exp  start-->
						<div class="row invoice-info">
							<div class="col-xs-12">
								<div class="table-responsive">
									<div id="sorttable-div" align="center" style="font-size: 10px"
										class="table-responsive ">
										<div class="row invoice-info" id="reportHeader"
											style="font-size: 15px; font-weight: bold;"></div>

										<div id="invoicePurchase" style="display: none;">
											<table class="" style="width: 95%">
												<tr>
													<td id="dateRangeId" style="font-size: 12px;">Date
														Range : <a id="dateRangeval" href="#"></a>
													</td>
												</tr>
											</table>
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
						<!--exp  stop-->

						<div class="row invoice-info">
							<div class="col-xs-12">
								<div class="table-responsive">
									<div id="sorttable-div" align="center" style="font-size: 10px"
										class="table-responsive ">
										<table class="table table-hover table-bordered table-striped"
											id="companyTable" style="display: none;">
											<tbody id="companyHeader">

											</tbody>
										</table>


										<div id="selectedData" style="text-align: left;">
											<table>
												<tr>
													<td style="display: none; font-weight: bold;"
														id="companyName"></td>
												</tr>
												<tbody id="selectedReportDetails">
												</tbody>
											</table>
										</div>
										<br /> <br />
										<div class="row invoice-info">
											<table width="100%">
												<tr>
													<td valign="top" width="100%">

														<div id="gridContainer">
															<div id="myGrid"></div>
															<div id="pager" style="width: 100%; height: 20px;"></div>

														</div>
													</td>

												</tr>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
				</div>
	</section>
</div>



<script type="text/javascript"
	src="resources/QhyvOb0m3EjE7A4/js/slickgrid/lib/jquery-1.11.2.min.js"></script>
<script type="text/javascript"
	src="resources/QhyvOb0m3EjE7A4/js/slickgrid/lib/jquery-ui-1.11.3.min.js"></script>
<script type="text/javascript"
	src="resources/QhyvOb0m3EjE7A4/js/slickgrid/lib/jquery.event.drag-2.3.0.js"></script>


<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
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
<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>



<script type="text/javascript"
	src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.core.js"></script>
<script type="text/javascript"
	src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.formatters.js"></script>
<script type="text/javascript"
	src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.editors.js"></script>
<script type="text/javascript"
	src="resources/QhyvOb0m3EjE7A4/js/slickgrid/plugins/slick.cellrangedecorator.js"></script>
<script type="text/javascript"
	src="resources/QhyvOb0m3EjE7A4/js/slickgrid/plugins/slick.cellrangeselector.js"></script>
<script type="text/javascript"
	src="resources/QhyvOb0m3EjE7A4/js/slickgrid/plugins/slick.cellselectionmodel.js"></script>
<script type="text/javascript"
	src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.grid.js"></script>
<script type="text/javascript"
	src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.groupitemmetadataprovider.js"></script>
<script type="text/javascript"
	src="resources/QhyvOb0m3EjE7A4/js/slickgrid/plugins/slickgrid-print-plugin.js"></script>
<script type="text/javascript"
	src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.dataview.js"></script>
<script type="text/javascript"
	src="resources/QhyvOb0m3EjE7A4/js/slickgrid/controls/slick.pager.js"></script>
<script type="text/javascript"
	src="resources/QhyvOb0m3EjE7A4/js/slickgrid/controls/slick.columnpicker.js"></script>
<script type="text/javascript"
	src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slickgridwrapper2.js"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/RR/RenewalReminderReport.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/RR/RenewalReminder.validate.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VI/VehicleAjaxDropDown.js" />"></script>
<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Report.js"/>"></script>

