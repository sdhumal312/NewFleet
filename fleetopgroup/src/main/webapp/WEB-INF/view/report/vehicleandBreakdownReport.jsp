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
</style>

<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
				<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/IR.in"/>">Issues Report</a> <span id="reportHead"> /Break down analysis report </span>
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
					  <div class="col col-sm-4 col-md-4">
						<label class="L-size control-label">Branch: 
						</label>
						<div class="I-size">
							<input type="hidden" id="depot" name="depot"
								style="width: 100%;"
								placeholder="ALL" />
						</div>
						</div>
						
						<div class="col col-sm-4 col-md-4">
						<div class="L-size control-label">
							Issue Type
						</div>
						<div class="I-size">
							<select name="ISSUES_TYPE_ID" id="IssuesType"
											style="width: 100%;">
													<option value="-1">ALL</option>
													<option value="1">Vehicle Issue</option>
													<option value="6">Vehicle BreakDown</option>
										</select>
						</div>
						
						</div>
						
						<div class="col col-sm-4 col-md-3">
							<label class="L-size control-label">Status : </label>
							<div class="I-size">
								<select class="form-text" name="status" id="status"
									style="width: 100%;" id="partCategory">
									<option value="0">- All -</option>
									<option value="1">OPEN</option>
									<option value="2">CLOSED</option>
									<option value="3">WOCREATED</option>
									<option value="4">RESOLVED</option>
									<option value="5">REJECT</option>
									<option value="7">DSE_CREATED</option>
								</select>
							</div>
						</div>
						
					</div>
				<div class="row">
				
						<div class="col col-sm-4 col-md-4">
							<div class="L-size control-label">
								Vehicle Type
							</div>
							<div class="I-size">
								<input type="hidden" id="VehicleTypeSelect" style="width: 100%;"
									placeholder="All" />
							</div>
							
						</div>
						<div class="col col-sm-4 col-md-4">
						<div class="L-size control-label">
							Vehicle Modal
						</div>
						<div class="I-size">
							<input type="hidden" id="vehicleModel" style="width: 100%;"
								placeholder="All" />
						</div>
						
						</div>
				<div class="col col-sm-4 col-md-3">
						
						<label class="L-size control-label">Vehicle : </label>

						<div class="I-size">
							<input type="hidden" id="fuelVehicle" name="fuelVehicle"
								style="width: 100%;" placeholder="ALL" />
						</div>
						
						</div>
				</div>
				
					<div class="row">
					
						<div class="col col-sm-4 col-md-4">
							<label class="L-size control-label">Label</label>
							<div class="I-size">
								<select class="form-text" name="ISSUES_TYPE_ID" id="issueLabel">
									<option value="0">- All -</option>
									<option value="1">NORMAL</option>
									<c:if test="${!configuration.hideHighStatus}">
										<option value="2">HIGH</option>
									</c:if>
									<c:if test="${!configuration.hideLowStatus}">
										<option value="3">LOW</option>
									</c:if>
									<option value="4">URGENT</option>
									<option value="5">VERY URGENT</option>
									<c:if test="${showBreakdownSelection}">
										<option value="6">BREAKDOWN</option>
									</c:if>
								</select>
							</div>
						</div>

						<div class="col col-sm-4 col-md-4">
							<label class="L-size control-label">Assign To </label>
							<div class="I-size">
								<input id="subscribe1" type="hidden" style="width: 100%"
									placeholder="All ">
							</div>

						</div>

						<div class="col col-sm-4 col-md-3">
							<label class="L-size control-label">Route :</label>
							<div class="I-size">
								<input type="hidden" id="routeList" name="routeID"
									style="width: 100%;"  required="required"
									placeholder="All " />
							</div>

						</div>
						
					</div>


					<div class="row">
					
						
						<div class="col col-sm-4 col-md-4">

							<label class="L-size control-label" for="partCategory">Category
								: </label>
							<div class="I-size">
								<select class="form-text" name="categoryId" style="width: 100%;"
									id="partCategory">
									<option value=""><!-- Please select --></option>
									<c:forEach items="${PartCategories}" var="PartCategories">
										<option value="${PartCategories.pcid}"><c:out
												value="${PartCategories.pcName}" /></option>
									</c:forEach>
								</select>
							</div>

						</div>
						
						<div class="col col-sm-4 col-md-4">
							<div class="L-size control-label">
								Driver
							</div>
							<div class="I-size">
								<input type="hidden" id="driver" style="width: 100%;"
									placeholder="All" />
							</div>
							
						</div>

					</div>

					<div class="row align-items-center">
					<div class="col col-sm-4 col-md-4">
						<label class="L-size control-label">Date range: <abbr
							title="required">*</abbr></label>
						<div class="I-size">
							<div class="input-group">
								<div class="input-group-addon">
									<i class="fa fa-calendar"></i>
								</div>
								<input type="text" id="dateRange" class="form-text"
									name="PART_RANGE_DATE" required="required" readonly="readonly"
									style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
							</div>
						</div>
						</div>
					</div>
					<br />
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
														<td id="avoidableKeyId" style="font-size: 12px; display: none;">Avoidable : <a
															id="avoidableKey" href="#"></a></td>
													</tr>
													<tr>
														<td id="vehicleModalKeyId" style="font-size: 12px; display: none;">Vehicle Modal : <a
															id="vehicleModalKey" href="#"></a></td>
													</tr>
													<tr>
														<td id="driverKeyId" style="font-size: 12px; display: none;">Driver : <a
															id="driverKey" href="#"></a></td>
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
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IS/vehicleandBreakdownReport.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VI/VehicleAjaxDropDown.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Report.js"/>"></script>
	
	
	
