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
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/bootstrap/bootstrap-float-label.min.css" />">

<style>
.row{
padding : 1%
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
  width:33.33333%;
  text-align:center;
}

/* .col{ */
/* 	margin-top: 20px; */
/* } */
.custom-select{
	height: 42px;
    font-size: 15px;
 }
.select2-container {
	width: 100%;
	padding: 0;
}
.select2-container-multi .select2-choices {
    min-height: 30px;
}


.select2-container .select2-choice {
/*    height: 36px; */
   padding: 5px 0 0 8px;
}
</style>

<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
				<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/DSEREPORT.in"/>">DSE Report</a> <span id="reportHead"> /Dealer Service Report </span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm hide"
						 id="printBtn">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm hide"
						onclick="advanceTableToExcel('tripCollectionExpenseList', 'Date Wise Fuel Entry Report')" id="exportExcelBtn">
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
			
					<div class="row">
						<div class="col col-sm-4">
							<label class="has-float-label"> <input type="hidden"
								id="fuelVehicle" name="fuelVehicle" style="width: 100%;"
								placeholder="ALL" /><span
								style="color: #2e74e6; font-size: 15px;">Vehicle</span></label>
						</div>
						<div class="col col-sm-4">
							<label class="has-float-label"> <input type="hidden"
								id="vendorId"  style=" font-size: 15px; height: 35px; width: 100%" placeholder="All">
								<span style="color: #2e74e6; font-size: 18px;">Vendor </span>
							</label>
						</div>
	
					</div>
					<div class="row">
						<div class="col col-sm-4">
							<label class="has-float-label"> <select class="select2"
								name="status" id="status" style="width: 100%;">
									<option value="0">All</option>
									<option value="1">IN-PROCESS</option>
									<option value="2">ON HOLD</option>
									<option value="3">PAYMENT PENDING</option>
									<option value="4">ACCOUNT CLOSE</option>
							</select> <span style="color: #2e74e6; font-size: 15px;">Status</span>
							</label>
						</div>
						<div class="col col-sm-4">
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
					<div class="col col-sm-8">
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
													<td id="dateRangeId" style="font-size: 12px; ">Date Range :
														 <a id="dateRangeval" href="#"></a>
													</td>
												</tr>
												<tr>
														<td id="depotKeyId" style="font-size: 12px; display: none;">Depot : <a
															id="depotKey" href="#"></a></td>
													</tr>
														<tr>
															<td id="vehicleTypetKeyId" style="font-size: 12px; display: none;">Vehicle Type : <a
																id="vehicleTypetKey" href="#"></a></td>
														</tr>
														<tr>
															<td id="vehicleKeyId" style="font-size: 12px; display: none;">Vehicle <a
																id="vehicleKey" href="#"></a></td>
														</tr>
													<tr>
														<td id="assignedToKeyId" style="font-size: 12px; display: none;">Assign To  <a
															id="assignedToKey" href="#"></a></td>
													</tr>
													<tr>
														<td id="routeKeyId" style="font-size: 12px; display: none;">Route : <a
															id="routeKey" href="#"></a></td>
													</tr>
													<tr>
														<td id="issueLabelKeyId" style="font-size: 12px; display: none;">Label : <a
															id="issueLabelKey" href="#"></a></td>
													</tr>
													<tr>
														<td id="statusKeyId" style="font-size: 12px; display: none;">Status  : <a
															id="statusKey" href="#"></a></td>
													</tr>
													<tr>
														<td id="partCategoryKeyId" style="font-size: 12px; display: none;">Category  : <a
															id="partCategoryKey" href="#"></a></td>
													</tr>
													<tr>
														<td id="driverKeyId" style="font-size: 12px; display: none;">Driver : <a
															id="driverKey" href="#"></a></td>
													</tr>
													<tr>
														<td id="modelKeyId" style="font-size: 12px; display: none;">Vehicle Model: <a
															id="modelKey" href="#"></a></td>
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
									<table class="table table-hover table-bordered table-striped" id="companyTable" style="display: none;">
										<tbody id="companyHeader">
											
										</tbody>
									</table>
										
										
										<div id="selectedData" style="text-align: left;">
													<table>
															<tr>
																<td style="display: none; font-weight: bold;" id="companyName"> </td>
															</tr>
														<tbody id="selectedReportDetails">
														</tbody>
													</table>
											</div>
											<br/>
											<br/>
										<div class="row invoice-info">
											<table width="100%">
												  <tr>
												    <td valign="top" width="100%">
												    
														<div id="gridContainer">
														      <div id="myGrid"></div>
														      <div id="pager" style="width:100%;height:20px;"></div>
														      
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
					
<div class="modal fade" id="showPartLabourDetails">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title">DSE Cost Details</h3>
<!-- 					<button type="button" class="close" data-dismiss="modal" -->
<!-- 						aria-label="Close"> -->
<!-- 						<span aria-hidden="true">&times;</span> -->
<!-- 					</button> -->
				</div>
				<div class="modal-body">
					<div class="table-responsive" id="labourTableDiv">
						<table class="table table-hover table-bordered">
						<thead>
							<tr>
								<th class="fit ar" style='font-size: 15px;'>SR NO</th>
								<th class="fit ar" style='font-size: 15px;'>Labour Type</th>
								<th class="fit ar" style='font-size: 15px;'>Hour/KM</th>
								<th class="fit ar" style='font-size: 15px;'>Rate/(Hour/KM)</th>
								<th class="fit ar" style='font-size: 15px;'><span id="labourDisType" style="font-weight: bolder;font-size: 12pt;"></span> Discount</th>
								<th class="fit ar" style='font-size: 15px;'><span id="labourTaxType" style="font-weight: bolder;font-size: 12pt;"></span> Tax</th>
								<th class="fit ar" style='font-size: 15px;'>Total Cost</th>
							</tr>
						</thead>
						<tbody id="dealerServiceEntriesLabourTable">
						
						</tbody>
	
						</table>
					</div>
					
				<div class="table-responsive" id="partTableDiv">
						<table class="table table-hover table-bordered">
						<thead>
							<tr>
								<th class="fit ar"  style='font-size: 15px;'>SR NO</th>
								<th class="fit ar" style='font-size: 15px;'>Part Name</th>
								<th class="fit ar" style='font-size: 15px;'>Quantity</th>
								<th class="fit ar" style='font-size: 15px;'>Each Cost</th>
								<th class="fit ar" style='font-size: 15px;'><span id="partDisType" style="font-weight: bolder;font-size: 12pt;"></span> Discount</th>
								<th class="fit ar" style='font-size: 15px;'><span id="partTaxType" style="font-weight: bolder;font-size: 12pt;"></span> Tax </th>
								<th class="fit ar" style='font-size: 15px;'>Total Cost</th>
							</tr>
						</thead>
						<tbody id="dealerServiceEntriesPartTable">
						
						</tbody>
	
						</table>
					</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default"
							data-dismiss="modal">
							<span>Cancel</span>
						</button>
					</div>
				</div>
			</div>
	</div>
				</div>

	
	
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
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>	

	
	
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
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/DSE/DealerServiceReport.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VI/VehicleAjaxDropDown.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Report.js"/>"></script>
	
