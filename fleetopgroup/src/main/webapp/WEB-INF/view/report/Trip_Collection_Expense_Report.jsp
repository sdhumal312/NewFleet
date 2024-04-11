<%@ include file="taglib.jsp"%>
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
					/ <a href="<c:url value="/TSR.in"/>">Trip Collection Report</a> / <span>Trip Collection Report</span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm hide"
						onclick="printDiv('div_print')" id="printBtn">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm hide"
						onclick="advanceTableToExcel('tripCollectionExpenseList', 'Trip Collection Report')" id="exportExcelBtn">
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
											<label class="L-size control-label"> Vehicle Group : <abbr
												title="required">*</abbr></label>
												 
											<div class="I-size">
												<input type="hidden" name="VEHICLE_GROUP"
													id="Reportvehiclegroup" value="0" style="width: 100%;" placeholder="All" />
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Route: </label>
											<div class="I-size">
												<input type="hidden" name="VEHICLE_ROUTE"
													id="TripRouteNameList" value="0" style="width: 100%;" placeholder="All" />
											</div>
										</div>
										<c:if test="${configuration.showLoadType}">
												<div class="row1">
													<label class="L-size control-label">Load Type : </label>
													<div class="I-size">
														<select class="form-text" name="loadTypeId" id="loadTypeId">
																	<option value="-1">All</option>
																	<option value="1">Own Load</option>
																	<option value="2">Market Load</option>
																</select>
													</div>
												</div>
										</c:if>	
										
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
										<div class="row invoice-info" id="reportHeader" style="font-size: 15px;font-weight: bold;">
										</div>
										<div class="row invoice-info">
											<table id="tripCollectionExpenseList" style="width: 95%;"
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
	
	<div class="modal fade" id="getExpensesModel" role="dialog">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close btn btn-danger" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">Trip Expenses</h4>
						</div>
						<div class="modal-body">

									<fieldset>
							<div class="box">
								<div class="box-body" id="modelBodyExpense">
								
								</div>
							</div>
						</fieldset>
							<br />
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close">Close</span>
							</button>
						</div>
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="getIncomeModel" role="dialog">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close btn btn-danger" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">Trip Income</h4>
						</div>
						<div class="modal-body">

									<fieldset>
							<div class="box">
								<div class="box-body" id="modelBodyIncome">
								
								</div>
							</div>
						</fieldset>
							<br />
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close">Close</span>
							</button>
						</div>
				</div>
			</div>
		</div>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/TripCollectionExpense.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script>
</div>