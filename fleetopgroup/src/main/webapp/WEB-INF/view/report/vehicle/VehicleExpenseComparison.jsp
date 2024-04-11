<%@ include file="../../taglib.jsp"%>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">	
<style>
   /*  .ranges li:last-child { display: none; }
    .calendar-date{display: none;}
    .range_inputs{display: none;} */

 table.myFormat tr td { font-size: 12px; }

</style>	
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>">
					<spring:message code="label.master.home" /></a> / 
					<a href="<c:url value="/Report"/>">Report</a> / 
					<a href="<c:url value="/ProfitAndLoss.in"/>">Profit And Loss Report</a> / Route wise Profit And Loss report
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm " onclick="printDiv('reportDiv')" id="printBtn">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm " id="exportPDF">
						<span class="fa fa-pdf"> Export Pdf</span>
					</button>
					<button class="btn btn-default btn-sm hide" onclick="advanceTableToExcel('reportDiv', 'Vehicle Expense Comparison Report')" id="exportExcelBtn">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
					</button>
					<a href="<c:url value="/Report"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<div class="form-horizontal">
		<div class="box box-primary">
			<div class="box-body" id="ElementDiv">
			    <input type="hidden" id="vehicleOneNoOfTripDays" value="0">
			    <input type="hidden" id="vehicleTwoNoOfTripDays" value="0">
			    <input type="hidden" id="vehicleThreeNoOfTripDays" value="0">
			    <input type="hidden" id="vehicleFourNoOfTripDays" value="0">
			    <input type="hidden" id="vehicleFiveNoOfTripDays" value="0">
			    
			    <input type="hidden" id="vehicleOneTripRunKM" value="0">
			    <input type="hidden" id="vehicleTwoTripRunKM" value="0">
			    <input type="hidden" id="vehicleThreeTripRunKM" value="0">
			    <input type="hidden" id="vehicleFourTripRunKM" value="0">
			    <input type="hidden" id="vehicleFiveTripRunKM" value="0">
			    
			     <input type="hidden" id="vehicleOneEPK" value="0">
			    <input type="hidden" id="vehicleTwoEPK" value="0">
			    <input type="hidden" id="vehicleThreeEPK" value="0">
			    <input type="hidden" id="vehicleFourEPK" value="0">
			    <input type="hidden" id="vehicleFiveEPK" value="0">
				<div class="row1" >
						<label class="col-md-1 col-sm-2 col-xs-12 control-label">Route :</label>
						<div class="col-md-3 col-sm-3 col-xs-6">
							<input type="hidden" id="routeId" name="routeId"
								style="width: 100%;" required="required" onchange="getCompareDataOnDateChange(this);"
								placeholder="Please Enter 2 or more Vehicle Name" /> 
						</div>
						<label class="col-md-1 col-sm-2 col-xs-6 control-label">Date range :<abbr
							title="required">*</abbr></label>
						<div class="col-md-3 col-sm-3 col-xs-6">
							<div class="input-group">
								<div class="input-group-addon">
									<i class="fa fa-calendar"></i>
								</div>
								<input type="text" id="compareRange" class="form-text" readonly="readonly" required="required"
									style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%"
									 onchange="getCompareDataOnDateChange(this);" >
							</div> 
							<input type="hidden" id="companyId" value="${companyId}" />
							<input type="hidden" id="userId" value="${userId}" />
						</div>
				</div>
				<div class="row1" >
						<label class="col-md-1 col-sm-2 col-xs-12 control-label">Vehicle Type :</label>
						<div class="col-md-3 col-sm-3 col-xs-6">
							<input type="hidden" id="vehicleTypeId" name="vehicleTypeId"
								style="width: 100%;" required="required" onchange="getCompareDataOnDateChange(this);"
								placeholder="Please Enter 2 or more Vehicle Name" /> 
						</div>
						<label class="col-md-1 col-sm-2 col-xs-12 control-label">Vehicles :</label>
						<div class="col-md-3 col-sm-3 col-xs-6" style="margin-top: 10px;">
						<a href="#" id="vehicleCount" onclick="showVehicleList();">0</a>
					</div>
						
				</div>
				
				</div>
			</div>
		</div>

	<div class="form-horizontal">
		<div class="box box-primary">
			<div class="box-body" id="printDiv">
				<div class="row invoice-info" id="reportDiv">
					<table id="comparisonResult" class="table-hover myFormat" style="width: 100%;">
						<tr>
							<td valign="top" style="width: 22%">
								<table id="expenseName" style="width: 100%" class="table-hover">
									<tr>
										<th style="font-size: 15px;"><input type="hidden"
											id="noOfDays" value="0"> <input type="hidden"
											id="defaultFilter" value="true">
											<div class="btn-group" id="status" data-toggle="buttons"
												onclick="callFunctionToSetAvg(this);">
												<label class="btn btn-default btn-on btn-md active">
													<input type="radio" value="0" name="fuel_tank"
													id="allExpenses" checked="checked">Total
												</label> <label class="btn btn-default btn-off btn-md"> <input
													type="radio" value="1" name="fuel_tank"
													id="perDaysExpenses">Avg/Day
												</label>
												<input type="hidden" id="perDaysExpensesSelected" value="false">
											</div></th>
									</tr>
								</table>
							</td>
							<td valign="top" style="width: 16%; padding: 5px;">
								<table style="width: 100%" class="table-hover ">
									<tr>
										<th style="font-size: 15px;"><select class="select2"
											onchange="getVehicleComparisionData(this);" name="state"
											id="vehicleOneSearch" style="width: 100%">
										</select></th>
									</tr>

								</table>
							</td>
							<td valign="top" style="width: 16%; padding: 5px;">
								<table style="width: 100%" class="table-hover ">
									<tr>
										<th style="font-size: 15px;"><select class="select2"
											onchange="getVehicleComparisionData(this);" name="state"
											id="vehicleTwoSearch" style="width: 100%">
										</select></th>
									</tr>

								</table>
							</td>
							<td valign="top" style="width: 16%; padding: 5px;">
								<table style="width: 100%" class="table-hover ">
									<tr>
										<th style="font-size: 15px;"><select class="select2"
											onchange="getVehicleComparisionData(this);" name="state"
											id="vehicleThreeSearch" style="width: 100%">
										</select></th>
									</tr>
								</table>
							</td>
							<td valign="top" style="width: 16%; padding: 5px;">
								<table style="width: 100%" class="table-hover ">
									<tr>
										<th style="font-size: 15px;"><select class="select2"
											onchange="getVehicleComparisionData(this);" name="state"
											id="vehicleFourSearch" style="width: 100%">
										</select></th>
									</tr>
								</table>
							</td>
							<td valign="top" style="width: 16%; padding: 5px;">
								<table style="width: 100%" class="table-hover ">
									<tr>
										<th style="font-size: 15px;"><select class="select2"
											onchange="getVehicleComparisionData(this);" name="state"
											id="vehicleFiveSearch" style="width: 100%">
										</select></th>
									</tr>
								</table>
							</td>
						</tr>

						<tr style="display: none;" id="resultRow">
							<td valign="top" style="width: 22%">
								<table id="expenseName" style="width: 100%"
									class="table-hover table-bordered">
									<tbody id="expenseNameBody">

									</tbody>
								</table>
							</td>
							<td valign="top" style="width: 16%">
								<table id="vehicleOne" style="width: 100%; font-size: 18px;"
									class="table-hover table-bordered">
									<tbody id="vehicleOneBody">
									</tbody>
								</table>
							</td>
							<td valign="top" style="width: 16%">
								<table id="vehicleTwo" style="width: 100%; font-size: 18px;"
									class="table-hover table-bordered">
									<tbody id="vehicleTwoBody">
									</tbody>
								</table>
							</td>
							<td valign="top" style="width: 16%">
								<table id="vehicleThree" style="width: 100%; font-size: 18px;"
									class="table-hover table-bordered">
									<tbody id="vehicleThreeBody">
									</tbody>
								</table>
							</td>
							<td valign="top" style="width: 16%">
								<table id="vehicleFour" style="width: 100%; font-size: 18px;"
									class="table-hover table-bordered">
									<tbody id="vehicleFourBody">
									</tbody>
								</table>
							</td>
							<td valign="top" style="width: 16%">
								<table id="vehicleFive" style="width: 100%; font-size: 18px;"
									class="table-hover table-bordered">
									<tbody id="vehicleFiveBody">
									</tbody>
								</table>
							</td>
						</tr>


					</table>
				</div>
			</div>
		</div>
		<div id="infoDiv"  class="box-body" style="margin-left: 20px;display: none;margin-top: 20px;">
			<a href="#"> Expense Per KM : </a> This will show cost of vehicle to run per km. <br>
			<a href="#"> No of Trip : </a> This will show the no of trip vehicle has been within selected date range <br>
			<a href="#"> Trip Run KM : </a> This will show the no of km vehicle runs within selected date range  <br>
			<a href="#"> No Of Trip Days : </a> This will show No Of Days Vehicle Was in trip within Select date range <br>
		</div>
	</div>
	<div class="modal fade" id="vehicleListModal" role="dialog">
				<div class="modal-dialog" style="width:400px;">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">Vehicle Details</h4>
						</div>
						<div class="modal-body">
							<table id="dataExpenseTable" style="width: 100%; " class="table-responsive table">
								<thead id="expenseHead"> </thead>
								<tbody id="tablevehicleBody"> 
									
								</tbody>
							</table>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">
								<span id="Close">Close</span>
							</button>
						</div>
					</div>
				</div>
			</div>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>	
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/VehicleExpenseComparison.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js"/>"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.5.3/jspdf.min.js"></script>
	<script type="text/javascript" src="https://html2canvas.hertzen.com/dist/html2canvas.js"></script>
	
	
	<script>
		$(document).ready(function() {
			setMisseleniousExpMap('${misseleniousExp}');
			setRenewalExpenses('${renewalList}');
			setTripSheetExpenses('${tripExpenseList}');
			setVehicleList('${vehicleList}');
		});
		
	</script>
		
</div>