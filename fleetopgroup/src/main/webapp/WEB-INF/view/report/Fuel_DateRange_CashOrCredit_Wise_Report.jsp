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
.box-body .affix {
	border-radius: 3px;
	background: #FFF;
	margin-bottom: 5px;
	padding: 5px;
}
</style>
<script>
 function validateReport()
{
	
	showMessage('errors','no records found');
		return false;
}  
 </script>
<div class="content-wrapper">


	<!-- Header Part  Start-->
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/FR.in"/>">Fuel Report</a>  / <span>Fuel Date Range Cash/Credit Wise Report</span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>					
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('advanceTable', 'Fuel Date Range Report')" id="exportExcelBtn">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
					</button>
					<button class="btn btn-default btn-sm "
						onclick="saveImageToPdf('advanceTable')" id="printPdf">
						<span class="fa fa-file-excel-o"> Export to PDF</span>
					</button>					
					<a href="<c:url value="/Report"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<!-- Header Part  End-->
	

	<section class="content">
		<div class="panel box box-primary">
			<div class="box-body">
				<div class="form-horizontal ">
										
					<!--Vehicle Submodule 1  Start-->
					<div class="row1">
						<label class="L-size control-label">Vehicle Name 
						</label>
						<div class="I-size">
							<input type="hidden" id="ReportSelectVehicle" name="repair_vid"
								style="width: 100%;" required="required"
								placeholder="Please Enter 2 or more Vehicle Name" />
							<p class="help-block">Select One Or More Vehicle</p>
						</div>
					</div>
					<!--Vehicle Submodule 1  End-->


					<!-- Vendor Name  Submodule 2 Start-->
					<div class="row1">
						<label class="L-size control-label">Vendor Name :</label>
						<div class="I-size">
							<input type="hidden" id="selectVendor" name="vendor_id"
								style="width: 100%;" required="required"
								placeholder="Please Enter 3 or more Vendor Name" />
						</div>
					</div>
					<!-- Vendor Name  Submodule 2 End-->
					
					<!-- Payment Type Cash OR Credit Submodule 3 Start-->
					<div class="row1">
						<label class="L-size control-label">Payment Type :</label>
						<div class="I-size">
							<select name="fuel_vendor_paymodeId" id="fuel_vendor_paymode"
								style="width: 100%;">
								<option value=""><!-- please select --></option>
								<option value="1" name="Cash">Cash</option>
								<option value="2" name="Credit">Credit</option>
								<option value="-1" name="ALL">ALL</option>
							</select>
						</div>
					</div>
					<!--Payment  Type Cash OR Credit Submodule 3 End-->
					
					<!-- Date Range Submodule 4 Start-->
					<div class="row1">
						<label class="L-size control-label">Date range: <abbr
							title="required">*</abbr></label>
						<div class="I-size">
							<div class="input-group">
								<div class="input-group-addon">
									<i class="fa fa-calendar"></i>
								</div>
								<input type="text" id="rangeFuelMileage" class="form-text"
									name="fuelmileage_daterange" required="required"
									style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
							</div>
						</div>
					</div>
					<!-- Date Range Submodule 4 End-->		
					
					<!--Search Button Start -->
					<fieldset class="form-actions">
						<div class="row1">
							<label class="L-size control-label"></label>

							<div class="I-size">
								<div class="pull-left">
									<button type="submit" name="commit" class="btn btn-success"
										id="btn-search">
										<i class="fa fa-search"> Search </i>
									</button>
								</div>
							</div>
						</div>
					</fieldset>
					<!--Search Button End -->
					
				</div>
			</div>			
		</div>
		
		
		
		
		
		
			
		
		<!-- Final Output After Click on Search Button Logic Start -->
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
											<div class="row invoice-info" id="reportHeader"
												style="font-size: 15px; font-weight: bold;"></div>
											<div class="row invoice-info">
												<table id="advanceTable1" style="width: 100%"
													class="table table-hover table-bordered table-striped">
													
													
													<tbody>
													
														<tr style="font-size: 16px;">
															<td class="vehicleNameSelector" style="padding-right: 10px; font-weight: bold;display: none;">Vehicle Name : </td>
															<td class="vehicleNameSelector" style="padding-right: 10px; font-weight: bold;display: none;" id="vehicleName"></td>
															<td class="vendorNameSelector" style="padding-right: 10px; font-weight: bold;display: none;">Vendor Name : </td>
															<td class="vendorNameSelector" style="padding-right: 10px; font-weight: bold;display: none;" id="vendorName"></td>
															<td class="paymentTypeSelector" style="padding-right: 10px; font-weight: bold;display: none;">Payment Type : </td>
															<td class="paymentTypeSelector" style="padding-right: 10px; font-weight: bold;display: none;" id="paymentType"></td>
														</tr>
													</tbody> 

												</table>
												
												<table id="advanceTable" style="width: 100%"
													class="table table-hover table-bordered table-striped">
													
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
		<!-- Final Output After Click on Search Button Logic End -->
		
		
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/FU/FuelDateRangeCashOrCreditWiseReport.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script>
	<script type="text/javascript" 
		src="resources/QhyvOb0m3EjE7A4/js/fleetop/jspdf/jspdf.min.js"></script>		
</div>