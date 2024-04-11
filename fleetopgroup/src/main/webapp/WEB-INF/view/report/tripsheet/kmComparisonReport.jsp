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
					<a href="<c:url value="/open"/>">
					<spring:message code="label.master.home" /></a> / 
					<a href="<c:url value="/Report"/>">Report</a> / 
					<a href="<c:url value="/comparisionReport.in"/>">Comparison Report</a> / Tripsheet Manual VS GPS KM Comparison Report
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>					
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('advanceTable', 'Tripsheet Manual VS GPS KM Comparison Report')">
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
											<label class="L-size control-label"> Vehicle : 
												</label>
											<div class="I-size">
												<input type="hidden" id="vid" name="vname"
													style="width: 100%;" placeholder="ALL" />
											</div>
										</div>
										
										<div class="row1">
											<label class="L-size control-label"> Vehicle Group : 
												</label>
											<div class="I-size">
												<input type="hidden" id="groupId" name="groupName"
													style="width: 100%;" placeholder="ALL" />
											</div>
										</div>
									
										<div class="row1">
											<label class="L-size control-label"> Route : 
												</label>
											<div class="I-size">
												<input type="hidden" id="tripRouteList" name="routeName"
													style="width: 100%;" placeholder="ALL" />
											</div>
										</div>

									</div>
										
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
										</br>
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
					</section>
	
					
		<section class="content" id="ResultContent">
			<div class="box-body">
				<div id="div_print">
					<section class="invoice">
						<div class="row invoice-info">
							<div class="col-xs-12">
								<div class="table-responsive">
									<div id="sorttable-div" align="center" style="font-size: 12px" class="table-responsive ">
										<div class="row invoice-info" id="reportHeader" style="font-size: 15px;font-weight: bold;">
										</div>
										<div class="table-responsive scroll-box" id="advanceTable">
											<table id="issueTableDataDetails" class="table table-hover table-bordered" width="100%">
											</table>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/report/kmComparisonReport.js"/>"></script>
	<script type="text/javascript" 
		src="resources/QhyvOb0m3EjE7A4/js/Print/print.js"></script>
	
	
	
</div>