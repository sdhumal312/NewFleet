<%@ include file="../../taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
	

<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/TR.in"/>">Tyre Report</a> / Tyre Retread Invoice Report
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>					
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('tripCollExpenseName', 'Tyre Retread Invoice Report')" id="exportExcelBtn">
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
										<div class="row1">
										<label class="L-size control-label">Tyre Model : </label>
											<div class="I-size">
												<input type="text" id="tyreModelId" name="TYRE_MODEL_ID"
													style="width: 100%;" placeholder="Please select Tyre Model" />
											</div>
										</div>
										
										<div class="row1">
										<label class="L-size control-label">Tyre Size : </label>
											<div class="I-size">
												<input type="text" id="tyreSizeId" name="TYRE_SIZE_ID"
													style="width: 100%;" placeholder="Please select Tyre Size" />
											</div>
										</div>
										
										<div class="row1">
										<label class="L-size control-label">Warehouse location
											: </label>
											<div class="I-size">
												<input type="hidden" name="WAREHOUSE_LOCATION_ID"
													id="warehouselocationId" style="width: 100%;"
													placeholder="Please Enter 2 or more location Name" />
											</div>
										</div>
										
										<div class="row1">
											<label class="L-size control-label">Vendor :</label>
											<div class="I-size">
												<input type="text" id="TyrePurchaseVendor" name="Vendor_id"
													style="width: 100%;"
													placeholder="Please Select Vendor Name" />
													
											</div>
										</div>
										

									</div>
										
										<!-- Date range -->
										<div class="row1">
											<label class="L-size control-label">Date range: <abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<div class="input-group">
													<div class="input-group-addon">
														<i class="fa fa-calendar"></i>
													</div>
													<input type="text" id="TripCollectionExpenseRange" class="form-text"
														name="PART_RANGE_DATE" required="required"
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
										
										<div id="invoicePurchase" style="display: none;">
												<table class="" style="width:95%">
													<tr>
														<td style="font-size: 12px;" > Size :  <a  id="sizId" href="#"></a></td>
													</tr>
													<tr>
														<td style="font-size: 12px;" > Model :  <a  id="modId" href="#"></a></td>
													</tr>
													<tr>
														<td style="font-size: 12px;" > Vendor :  <a  id="venId" href="#"></a></td>
													</tr>
													<tr>
														<td style="font-size: 12px;" > Location :  <a  id="locId" href="#"></a></td>
													</tr>
													<tr>
														<td style="font-size: 12px;" >Date Range :  <a  id="dateRange" href="#"></a></td>
													</tr>
												</table>
										</div>
										
										<div class="row invoice-info">
											<table id="tripCollExpenseName" style="width: 100%;"
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IT/TyreRetreadInvoiceReport.js"/>"></script>
	<script type="text/javascript" 
	src="resources/QhyvOb0m3EjE7A4/js/Print/print.js"></script>
	
	
	
</div>