<%@ include file="../taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/monthpicker.css"/>">
<style>
.scrollit {
    overflow:scroll;
    height:600px;
}
.sticky {
  position: sticky;
  top: 0;
  width: 100%;
}
</style>		
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/ProfitAndLoss.in"/>">Profit And Loss Report</a> / Group Wise Profit & Loss Report
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm hide"
						onclick="printDiv('div_print')" id="printBtn">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('profitAndLossTable', 'Group Wise Profit & Loss Report')" id="exportExcelBtn">
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
											<label class="L-size control-label">Month: <abbr
												title="required">*</abbr></label>
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
	<section class="content" id="ResultContent">
			<div class="box-body">
			<div id="div_print">

				<div id="div_print">
					
					<section class="invoice">
						<div class="row invoice-info">
							<div class="col-xs-12">
								<div class="table-responsive">
									<div id="sorttable-div" align="center" style="font-size: 10px"
										class="table-responsive ">
										
										<div class="row invoice-info" id="reportHeader" style="font-size: 16px;font-weight: bold;"><br/>
										</div>
						<br/>				
					<div id="vehicleInfo" style="display: none;">
						<table class="" style="width:95%">
							<tr>
								<td style="font-size: 16px;" >Vehicle Group : </td>
								<td style="font-size: 16px;"><a  id="vehicleGroup" href="#"></a></td>
								<td style="font-size: 16px;">Month : </td>
								<td style="font-size: 16px;"><a  id="month" href="#"></a></td>
								<td style="font-size: 16px;">Days In Month : </td>
								<td style="font-size: 16px;"><a  id="daysInMonth" href="#"></a></td>
							</tr>
							
							<tr>
								<td style="font-size: 16px;" >Total no of Vehicle in this Group : </td>
								<td colspan="5" style="font-size: 16px; text-align: left"><a  id="noOfVehicle" href="#"></a></td>
								
							</tr>
							
						</table>
					</div>
										<br/><br/>
										<div id="scrollId" class="row invoice-info scrollit">
											<table id="profitAndLossTable"
																class="table-hover table-bordered " style="width: 100%; display: none;">
														<thead style="background-color: aqua; width: 100%;" class="sticky">
															<th style="font-size: 14px;">SR.</th>
															<th style="font-size: 14px;">Vehicle</th>
															<th style="font-size: 14px;">No Of Trips</th>
															<th style="font-size: 14px;">No Of Days RUN</th>
															<th style="font-size: 14px;">No Of Days Ideal</th>
															<th style="font-size: 14px;">Total KM RUN</th>
															<th style="font-size: 14px;">Total Income</th>
															<th style="font-size: 14px;">Total Expense</th>
															<th style="font-size: 14px;">Balance</th>
														</thead>
														<tbody id="tableBody">
															
														</tbody>		
													
													
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
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/profitandloss/groupWiseProfitAndLossReport.js" />"></script>
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
	</script>
	
		
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js" />"></script>
		<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/jspdf/jspdf.min.js"></script>	
		
</div>