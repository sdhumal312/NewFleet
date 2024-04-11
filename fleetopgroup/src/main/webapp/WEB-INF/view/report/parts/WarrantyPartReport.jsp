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
					<a href="<c:url value="/open"/>">
						<spring:message code="label.master.home" /></a> / 
						<a href="<c:url value="/Report"/>">Report</a> / 
						<a href="<c:url value="/PR.in"/>">Part Report</a>  
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>					
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('tripCollExpenseName', 'Tyre Expense Details Report')" id="exportExcelBtn">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
					</button>
					<a href="<c:url value="/Report"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>

	<input type="hidden" id="companyId" value="${companyId}">
	<input type="hidden" id="userId" value="${userId}">
	<input type="hidden" id="serviceType" value="0">
	
	<section class="content">
		<div class="panel box box-primary">
			<div class="box-body" id="ElementDiv">
				<div class="form-horizontal ">
					<div class="row1">
						<label class="L-size control-label">Vehicle: </label>
						<div class="I-size">
							<input type="hidden" id="vehicleId" style="width:100%">
						</div>
					</div>
					<div class="row1">
						<label class="L-size control-label" >Part: </label>
						<div class="I-size">
							<input type="hidden" id="partId" style="width:100%">
						</div>
					</div>
					<div class="row1">
						<label class="L-size control-label" >Service Type</label>
						<div class="I-size">
							<select id="serviceTypeId" style="width:100%">
								<option value="2">WorkOrders</option>
								<option value="3">Dealer Service</option>
							</select>
						</div>
					</div>
				</div>
				<div class="row1">
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
		
		<!--latest start  -->
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
											<table class="table table-hover table-bordered table-striped"
												id="companyTable" style="display: none;">
												<tbody id="companyHeader">

												</tbody>
											</table>

											<div class="row invoice-info" id="reportHeader"
												style="font-size: 15px; font-weight: bold;"></div>
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
																<div id="pager" style="width: 100%; height: 20px;"></div>

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
	</section>
	
	<div class="modal fade" id="additionalPartModal" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" >Additional Parts </h4>
				</div>
				<div class="modal-body">
					<table class="table" id="ownAdditionalPartTable" style="display:none">
					<thead>
						<tr>
							<th class="fit ar">Sr No</th>
							<th class="fit ar">Part</th>
							<th class="fit ar">Qty</th>
							<th class="fit ar">Unit Cost</th>
							<th class="fit ar">Total Cost</th>
						</tr>
					</thead>
					<tbody id="additionalOwnPartTable">
					
					</tbody>
					
					</table>
					
					<table class="table" id="dealerAdditionalPartTable" style="display:none">
					<thead>
						<tr>
							<th class="fit ar">Sr No</th>
							<th class="fit ar">Part</th>
							<th class="fit ar">Qty</th>
							<th class="fit ar">Unit Cost</th>
							<th class="fit ar">Total Cost</th>
						</tr>
					</thead>
					<tbody id="additionalDealerPartTable">
					</tbody>

					</table>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<span id="Close"><spring:message code="label.master.Close" /></span>
					</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="additionalLabourModal" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" >Additional Labour </h4>
				</div>
				<div class="modal-body">
					<table class="table" id="ownAdditionalLabourTable" style="display:none">
					<thead>
						<tr>
							<th class="fit ar">Sr No</th>
							<th class="fit ar">Labour</th>
							<th class="fit ar">Hour</th>
						</tr>
					</thead>
					<tbody id="additionalOwnLabourTable">
					
					</tbody>
					
					</table>
					
					<table class="table" id="dealerAdditionalLabourTable" style="display:none">
					<thead>
						<tr>
							<th class="fit ar">Sr No</th>
							<th class="fit ar">Part</th>
							<th class="fit ar">Qty</th>
							<th class="fit ar">Unit Cost</th>
							<th class="fit ar">Dis</th>
							<th class="fit ar">Gst</th>
							<th class="fit ar">Total Cost</th>
						</tr>
					</thead>
					<tbody id="additionalDealerLabourTable">
					</tbody>

					</table>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<span id="Close"><spring:message code="label.master.Close" /></span>
					</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="addWarrantyParts" role="dialog">
			<div class="modal-dialog" style="width: 780px;">
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">Warranty Part List</h4>
						</div>
						<div class="modal-body">
							<input type="hidden" id="warrantyPartId">
							<input type="hidden" id="warrantyPartTaskId">
							<div>
								<a href="#">Already Assigned Parts</a>
							</div>
							<table class="table" id="assingedDataBody">
								<thead>
									<tr>
										<th>Sr </th>
										<th>Part Serial Number</th>
									</tr>
								</thead>
								<tbody id="assingedDataBody">
									
								</tbody>
							</table>
							
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
	
	
	
	
	
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/lib/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/lib/jquery-ui-1.11.3.min.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/lib/jquery.event.drag-2.3.0.js"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"/>"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"/>"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/Print/print.js"></script>
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
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/part/WarrantyPartReport.js"/>"></script>
		
</div>