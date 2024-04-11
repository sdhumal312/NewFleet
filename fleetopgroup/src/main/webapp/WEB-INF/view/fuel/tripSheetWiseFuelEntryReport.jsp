<%@ include file="../../taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/slick.grid.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/slick.pager.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/smoothness/jquery-ui-1.11.3.custom.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/examples.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/slick.columnpicker.css"/>">
	

<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
				<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/FR.in"/>">Fuel </a> / <span id="reportHead">TripSheet Wise Fuel Entry Report </span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm hide"
						 id="printBtn">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm hide"
						onclick="advanceTableToExcel('tripCollectionExpenseList', 'Tripsheet Wise Fuel Entry Report')" id="exportExcelBtn">
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
								<label class="L-size control-label">Driver  :</label>
								<div class="I-size">
									<input type="text" id="DriverId" name="DRIVER_ID" 
										   style="width: 100%;"  placeholder="All" />
									<label id="errorDriver1" class="error"></label>
								</div>
					</div>

					<c:if test="${configuration.showDriver2 }">
					<div class="row1">
								<label class="L-size control-label">Driver 2 :</label>
								<div class="I-size">
									<input type="text" id="secDriverId" name="DRIVER_ID" 
										   style="width: 100%;"  placeholder="All" />
									<label id="errorDriver1" class="error"></label>
								</div>
					</div>
					</c:if>
					
					<c:if test="${configuration.showRoute }">
					<div class="row1">
								<label class="L-size control-label">Route :</label>
								<div class="I-size">
									 <input type="text" id="routeId" name="TRIP_ROUTE" style="width: 100%;" placeholder="ALL" />
								</div>
					</div>
					</c:if>

					<div class="row1">
						<label class="L-size control-label">Vehicle : </label>
						<!-- <div class="I-size">
							<input type="hidden" id="vehicleId" name="vehicleName" style="width: 100%;" placeholder="All" />
						</div> -->
						<div class="I-size">
							<input type="hidden" id="fuelVehicle" name="fuelVehicle"
								style="width: 100%;"
								placeholder="ALL" />
						</div>
					</div>
					<!-- <div class="row1" id="grppartLocation" class="form-group">
						<label class="L-size control-label" for="warehouselocation">
							Date : </label>
						<div class="input-group input-append date" id="TCDailydate">
							<input type="text" class="form-text" id="dateRange"
								name="TRIP_DATE_RANGE" required="required"
								data-inputmask="'alias': 'yyyy-mm-dd'" data-mask="" /> <span
								class="input-group-addon add-on"> <span
								class="fa fa-calendar"></span>
							</span>
						</div>
					</div> -->
					
					<div class="row1">
						<label class="L-size control-label">Date range: <abbr
							title="required">*</abbr></label>
						<div class="I-size">
							<div class="input-group">
								<div class="input-group-addon">
									<i class="fa fa-calendar"></i>
								</div>
								<input type="text" id="dateRange" class="form-text"
									name="PART_RANGE_DATE" required="required"
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
									id="btn-save">
									<i class="fa fa-search"> Search</i>
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- </div> -->
					</div>
	<section class="content" id="ResultContent" style="display: none;">
			<div class="box-body">
				<div id="div_print">
					<div id="div_print">
						<section class="invoice">
						
							<div class="row invoice-info">
							<div class="col-xs-12">
								<div class="table-responsive">
									<div id="sorttable-div" align="center" style="font-size: 10px"
										class="table-responsive ">
										<div class="row invoice-info" id="reportHeader"
												style="font-size: 15px; font-weight: bold;"></div>
										
										<div id="invoicePurchase" style="display: none;">
												<table class="" style="width:95%">
													<tr>
														<td style="font-size: 12px;" >Driver  :  <a  id="driver1Identity" href="#"></a></td>
													</tr>
													<c:if test="${configuration.showDriver2 }">
													<tr>
														<td style="font-size: 12px;" >Driver 2 :  <a  id="driver2Identity" href="#"></a></td>
													</tr>
													</c:if>
													<c:if test="${configuration.showRoute }">
													<tr>
														<td style="font-size: 12px;" >Route:  <a  id="routeIdentity" href="#"></a></td>
													</tr>
													</c:if>
													<tr>
														<td style="font-size: 12px;" >Vehicle :  <a  id="vehicleIdentity" href="#"></a></td>
													</tr>
													<tr>
														<td style="font-size: 12px;" >Date Range :  <a  id="dateRangeIdentity" href="#"></a></td>
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

															</div>
														</td>

													</tr>
												</table>
											</div>
										</div>
									</div>
								</div>
							</div>
						</section>
					</div>
				</div>
			</div>
		</section>
				</div>
			</div>
	</section>
	
	
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/lib/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/lib/jquery-ui-1.11.3.min.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/lib/jquery.event.drag-2.3.0.js"></script>

	
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
	
	
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.core.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.formatters.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.editors.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/plugins/slick.cellrangedecorator.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/plugins/slick.cellrangeselector.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/plugins/slick.cellselectionmodel.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.grid.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.groupitemmetadataprovider.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/plugins/slickgrid-print-plugin.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.dataview.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/controls/slick.pager.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/controls/slick.columnpicker.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slickgridwrapper2.js"></script>
	
	<script type="text/javascript" 
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/FU/tripSheetWiseFuelEntryReport.js"/>"></script>
	
	
</div>