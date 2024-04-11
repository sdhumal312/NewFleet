<%@ include file="../../taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/monthpicker.css"/>">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/inspectionReport.in"/>">Vehicle Inspection Report</a> / Vehicle wise inspection report
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm hide"
						onclick="printDiv('div_print')" id="printBtn">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm hide"
						onclick="advanceTableToExcel('batteryTransferDetails', 'Vehicle Wise Inspection Report')" id="exportExcelBtn">
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
											<div class="row1">
														<label class="L-size control-label">Vehicle Name <abbr
															title="required">*</abbr>
														</label>
														<div class="I-size">
															<input type="hidden" id="ReportSelectVehicle"
																name="repair_vid" style="width: 100%;"
																required="required"
																placeholder="Please Enter 2 or more Vehicle Name" />
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
															<input type="text" id="reportrange" class="form-text"
																name="VEHICLE_DATERANGE" required="required"
																style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
														</div>
													</div>
												</div>
												
										<div class="row1">
											<label class="L-size control-label"> Inspected By :</label>
											<div class="col-md-4 col-sm-2 col-xs-12">
												<input class="" placeholder="Inspected By" id="subscribe"
																type="hidden" style="width: 100%" name="inspectedBy"
																onkeypress="return Isservice_subscribeduser(event);"
																required="required" ondrop="return false;">
											</div>
										<label class="col-md-1 col-sm-1 col-xs-12 control-label">Frequency :</label>
											<div class="col-md-2 col-sm-3 col-xs-12">
												<input type="number" class="form-text" id="frequency" name="frequency"
													min="0.0" max="1" maxlength="10" placeholder="ex: 23"
													value="0">
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label"> Inspection Parameter :</label>
											<div class="col-md-4 col-sm-2 col-xs-12">
												<input class="" placeholder="Parameter" id="inspectionParameter"
																type="hidden" style="width: 100%" name="inspectionParameter"
																onkeypress="return Isservice_subscribeduser(event);"
																required="required" ondrop="return false;">
											</div>
										<label class="col-md-1 col-sm-1 col-xs-12 control-label">Test Result :</label>
											<div class="col-md-2 col-sm-3 col-xs-12">
												<select class="form-text" id="testResult"
															name="testResult">
															<option value="0">ALL</option>
															<option value="1">PASS</option>
															<option value="2">FAIL</option>
															<option value="3">BOTH( PASS/FAIL )</option>
															<option value="4">NOT INSPECTED</option>
															
												</select>
											</div>
										</div>
											<div class="row1">
												<label class="L-size control-label"></label>

												<div class="I-size">
													<div class="pull-left">
														<button  name="commit"
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

				<div id="div_print">

					<section class="invoice">
						<div class="row invoice-info">
							<div class="col-xs-12">
								<div class="table-responsive">
									<div id="sorttable-div" align="center" style="font-size: 10px"
										class="table-responsive ">
										<div class="row invoice-info" id="reportHeader" style="font-size: 18px;font-weight: bold;"></div><br/>
										<div class="row" id="vehicleInfo">
												<table class="table">
													<tbody>
														<tr style="font-size: 16px;">
															<td style="padding-right: 10px; font-weight: bold; font-size: 17px;">Vehicle : </td>
															<td style="padding-right: 10px; font-weight: bold;" id="vehicleId"></td>
															<td style="padding-right: 10px; font-weight: bold;">Vehicle Group : </td>
															<td style="padding-right: 10px; font-weight: bold;" id="vehicleGroup"></td>
															<td style="padding-right: 10px; font-weight: bold;">Date Range : </td>
															<td style="padding-right: 10px; font-weight: bold;" id="reportRange"></td>
														</tr>
														<tr style="font-size: 16px;">
															<td style="padding-right: 10px; font-weight: bold;">TEST PASS % : </td>
															<td style="padding-right: 10px; font-weight: bold;" id="testPassPer"></td>
															<td style="padding-right: 10px; font-weight: bold;">TEST FAIL % : </td>
															<td style="padding-right: 10px; font-weight: bold;" id="testFailPer"></td>
															<td style="padding-right: 10px; font-weight: bold;">NOT TESTED % : </td>
															<td style="padding-right: 10px; font-weight: bold;" id="notTestedPer"></td>
														</tr>
														<tr style="font-size: 16px;">
															<td class="inspectedBy" style="padding-right: 10px; font-weight: bold;display: none;">Inspected By : </td>
															<td class="inspectedBy" style="padding-right: 10px; font-weight: bold;display: none;" id="inspectedByVal"></td>
															<td class="inspectionParam" style="padding-right: 10px; font-weight: bold;display: none;">Inspection Parameter : </td>
															<td class="inspectionParam" style="padding-right: 10px; font-weight: bold;display: none;" id="inspectionParameterVal"></td>
															<td class="frequency" style="padding-right: 10px; font-weight: bold;display: none;">Frequency : </td>
															<td class="frequency" style="padding-right: 10px; font-weight: bold;display: none;" id="frequecyVal"></td>
														</tr>
													</tbody>
												</table>
										</div><br/>
										<div class="row invoice-info">
											<table id="batteryTransferDetails" style="width: 95%;"
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/inspection/vehicleWiseInspectionReport.js" />"></script>
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
</div>