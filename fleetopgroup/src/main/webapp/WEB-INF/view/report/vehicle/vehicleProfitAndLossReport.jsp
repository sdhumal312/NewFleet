<%@ include file="../taglib.jsp"%>
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
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> / 
					<a href="<c:url value="/Report"/>">Report</a> / 
					<a href="<c:url value="/ProfitAndLoss.in"/>">Profit And Loss Report</a> / Vehicle Wise Profit & Loss Report
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm hide"
						onclick="printDiv('div_print')" id="printBtn">
						<span class="fa fa-print"> Print</span>
					</button>
					
					<button class="btn btn-default btn-sm hide"
						onclick="advanceTableToExcel('div_print', 'Vehicle Wise Profit And Loss Report')" id="exportExcelBtn">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
					</button>
					<button class="btn btn-default btn-sm hide"
						onclick="saveImageToPdf('div_print')" id="printPdf">
						<span class="fa fa-file-excel-o"> Export to PDF</span>
					</button>
					<a href="<c:url value="/Report"/>">Cancel</a>

				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="panel box box-primary">
		<input type ="hidden" id="showTyreExpense" value="${configuration.showTyreExpense}">
		<input type ="hidden" id="showBatteryExpense" value="${configuration.showBatteryExpense}">
		<input type ="hidden" id="showDriverSalary" value="${configuration.showDriverSalary}">
		<%
		   String vehicleId = request.getParameter("vid");
		   String startDateOfMonth = request.getParameter("startDateOfMonth");
		%>
		<input type="hidden" id="vehicleId" name="vid" value="<%= vehicleId %>">
		<input type="hidden" id="startDateOfMonth" name="startDateOfMonth" value="<%= startDateOfMonth %>">
		
			<div class="box-body">
				<div class="form-horizontal ">
					<div class="row1">
						<label class="L-size control-label">Vehicle Name <abbr title="required">*</abbr>
						</label>
						<div class="I-size">
							<input type="hidden" id="ReportSelectVehicle"
								name="repair_vid" style="width: 100%;"
								required="required"
								placeholder="Please Enter 2 or more Vehicle Name" />
							<p class="help-block">Select Vehicle</p>
						</div>
					</div>
					<div class="row1">
						<input type="hidden" id="reportrange">
						<label class="L-size control-label">Month: <abbr title="required">*</abbr></label>
						<div class="I-size">
							<div class="input-group">
								<div class="input-group-addon">
									<i class="fa fa-calendar"></i>
								</div>
								<input type="text" id="monthRangeSelector" class="form-text"
									name="FROM_DATERANGE" required="required"
									style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
							</div>
						</div>
					</div>
					<div class="row1">
						<label class="L-size control-label"></label>
						<div class="I-size">
							<div class="pull-left">
								<button  name="commit" class="btn btn-success" id="btn-save">
									<i class="fa fa-search"> Search</i>
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	
		<section class="content" id="ResultContent">
			<div class="box-body">
				<div class="modal fade" id="vehicleIncomeModel" role="dialog">
					<div class="modal-dialog" style="width:1250px;">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title">Vehicle Income Details</h4>
							</div>
							<div class="modal-body">
								<table id="dataIncomeTable" style="width: 100%; display: none;" class="table-responsive table">
									<thead>
										<tr>
											<th>Sr</th>
											<th>Income Type</th> 
											<th>TripSheet Number</th>
											<th>Trip Open Date</th>
											<th>Trip Close Dater</th>
											<th style="text-align: right;">Amount</th>
										</tr>
									</thead>
									<tbody id="tableIncomeBody">
										
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
			</div>
			
			<div class="modal fade" id="vehicleExpenseModel" role="dialog">
				<div class="modal-dialog" style="width:1250px;">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">Vehicle Expense Details</h4>
						</div>
						<div class="modal-body">
							<table id="dataExpenseTable" style="width: 100%; display: none;" class="table-responsive table">
								<thead id="expenseHead"> </thead>
								<tbody id="tableExpenseBody"> </tbody>
							</table>
							
							<table id="dataExpenseTable1" style="width: 100%; display: none;" class="table-responsive table">
								<thead id="expenseHead1"> </thead>
								<tbody id="tableExpenseBody1"> </tbody>
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
	
			<div id="div_print">
				<section class="invoice">
					<div class="row invoice-info">
						<div class="col-xs-12">
							<div class="table-responsive">
								<div id="sorttable-div" align="center" style="font-size: 10px" class="table-responsive ">
									<div class="row invoice-info" id="reportHeader" style="font-size: 16px;font-weight: bold;"><br/>
									</div>
									
									<div id="vehicleInfo" style="display: none;">
										<table class="" style="width:95%">
											<tr>
												<td style="font-size: 16px;">Vehicle : </td>
												<td style="font-size: 16px;"><a  id="Vehicle_registration" href="#"></a></td>
												<td style="font-size: 16px;" >Vehicle Group : </td>
												<td style="font-size: 16px;"><a  id="vehicleGroup" href="#"></a></td>
												<td style="font-size: 16px;">Vehicle Location : </td>
												<td style="font-size: 16px;"><a  id="location" href="#"></a></td>
												<td style="font-size: 16px;">Month : </td>
												<td style="font-size: 16px;"><a  id="month" href="#"></a></td>
												<td style="font-size: 16px;">Days In Month : </td>
												<td style="font-size: 16px;"><a  id="daysInMonth" href="#"></a></td>
											</tr>
											
											<tr>
											</tr>
											
										</table>
									</div>
									
									<div id="tripInfo" style="display: none;"> <br/>
										<p>No of Trips And Days : <a href="#" id="noOftrip"></a> Trips <a href="#" id="noOftripDays"></a> Days. &emsp;&emsp;&emsp;&emsp; No of days Ideal : <a href="#" id="noOfDaysIdeal"></a>
											 Total KM RUN : <a href="#" id="totalRunKM"></a>. &emsp;&emsp;&emsp; EPK : <a href="#" id="EPK"> </a>
										 </p>
									</div>
									<div class="row invoice-info">
										<table id="profitAndLossTable" class="table-hover table-bordered" style="width: 100%; display: none;">
											<tr>
												<td valign="top">
													<table  id="incomeTable" style="width: 100%" class="table-hover table-bordered">
														<tr>
															<th style="font-size: 15px;">Income Type</th>
															<th style="font-size: 15px;">Amount</th>
														</tr>
														<tbody id="incomeBody">
														</tbody>
													</table>
												</td>
												<td valign="top">
													<table id="expenseTable" style="width: 100%" class="table-hover table-bordered">
														<tr>
															<th style="font-size: 15px;">Expense Type</th>
															<th style="font-size: 15px;">Amount</th>
														</tr>
														<tbody id="expenseBody">
														</tbody>
													</table>
												</td>
											</tr>	
												
											<tr>
												<th style="font-size: 15px;">Balance</th>
												<th style="font-weight: bold;font-size: 15px;" id="balance"></th>
											</tr>		
											
										</table>
										</br>
										<table id="profitAndLossSummaryTable" class="table-hover table-bordered" style="width: 100%; display: none;">
											<tr>
												<td valign="top">
													<table  id="summaryTable" style="width: 100%" class="table-hover table-bordered">
														<tr>
															<th style="font-size: 15px;">Summary Type</th>
															<th style="font-size: 15px;">Amount</th>
														</tr>
														<tr>
															<td style="font-size: 12px; text-align: left; padding-left: 10px; font-weight: bolder; ">TOTAL DIESEL </td>
															<td style="font-size: 12px; text-align: right; padding-right: 10px; font-weight: bolder;" id="totalDiesel" ></td>
														</tr>
														<tr>
															<td style="font-size: 12px; text-align: left; padding-left: 10px; font-weight: bolder; ">KMPL(DIESL AVG) </td>
															<td style="font-size: 12px; text-align: right; padding-right: 10px; font-weight: bolder; " id="dieselKmpl" ></td> 
														</tr>
														<tr>
															<td style="font-size: 12px; text-align: left; padding-left: 10px; font-weight: bolder; ">EARNING PER KM </td>	
															<td style="font-size: 12px; text-align: right; padding-right: 10px; font-weight: bolder; " id="earningPerKm" > </td> 
														</tr>
														<tr>
															<td style="font-size: 12px; text-align: left; padding-left: 10px; font-weight: bolder; ">EXPENSE PER KM </td>	
															<td style="font-size: 12px; text-align: right; padding-right: 10px; font-weight: bolder; " id="expensePerKm" > </td> 
														</tr>
														<tr>
															<td style="font-size: 12px; text-align: left; padding-left: 10px; font-weight: bolder; ">BALANCE PER KM </td>	
															<td style="font-size: 12px; text-align: right; padding-right: 10px; font-weight: bolder; " id="balancePerKm" > </td> 
														</tr>	
													</table>
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
		</section>	
	</section>
</div>

	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/profitandloss/vehicleProfitAndLossReport.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/monthpicker.min.js"/>"></script>
		
	<script type="text/javascript">
		$(document).ready(function() {
			$('#monthRangeSelector').Monthpicker({
				monthLabels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]
			});
		});
	
	$(document).ready(function() {
		if(($("#startDateOfMonth").val() != "" && $("#startDateOfMonth").val() != "null" ) && ($("#vehicleId").val() != "" && $("#vehicleId").val() != "null")){    
				
					   var  object         = new Object();
					   
					   object["vid"] 				=  $("#vehicleId").val();
					   object["dateOfMonth"] 	= $("#startDateOfMonth").val();
		
					   showLayer();
					   ajaxCallForgetVehicleWiseProfitAndLossReport(object);
					   
				}
			}); 
	</script>
	
		
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js" />"></script>
		<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/jspdf/jspdf.min.js"></script>	
