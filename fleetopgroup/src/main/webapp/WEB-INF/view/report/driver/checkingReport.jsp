<%@ include file="../taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					 / <span>Checking
						 Report</span>
				</div>
				<div class="pull-right">
					<div style="display: inline-block; width: 100px"></div>

					<sec:authorize access="hasAuthority('VIEW_CHECKING_ENTRY')">
						<button class="btn btn-default" onclick="printDiv('div_print')">
							<span class="fa fa-print"> Print</span>
						</button>
						<button class="btn btn-default btn-sm"
							onclick="advanceTableToExcel('advanceTable', 'Checking Report')">
							<span class="fa fa-file-excel-o"> Export to Excel</span>
						</button>
					</sec:authorize>
					<a href="<c:url value="/Report"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
			<div class="panel-group">
				<div class="panel panel-info" id="top-border-boxshadow">
							<div class="panel-heading text-center"><h4> Checking Report</h4></div>
					<div class="panel-body">
										<div class="row1 col-xs-8">
											<label class="L-size control-label">Depot : <abbr
												title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="hidden" id="vehicleGroupId"
													name="vehicleGroupId" style="width: 100%;"
													required="required" placeholder="Please Select Group"
													value="0" />
											</div>
										</div>
								<div class="row1 col-xs-8">
										<label class="L-size control-label">Checking Inspector: 
										</label>
										<div class="I-size">
											<select style="width: 100%;" id="checkingInspectorId"
														 name="checkingInspectorId">
														</select>
										</div>
								</div>
								<div class="row1 col-xs-8" id="vehicle">
									<label class="L-size control-label"> Vehicle :</label>
									<div class="I-size">
										<input  type="hidden" id="vid"
											required="required" name="vid"
												style="width: 100%;"
												placeholder="Please Enter 2 or more Vehicle" />
									</div>
								</div>
								<br>		
										<div class="row1 col-xs-8">
													<label class="L-size control-label">Date range: <abbr
														title="required">*</abbr></label>
													<div class="I-size">
														<div class="input-group">
															<div class="input-group-addon">
																<i class="fa fa-calendar"></i>
															</div>
															<input type="text" id="reportrange" class="form-text"
																name="VEHICLE_DATERANGE" required="required"
																style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
														</div>
													</div>
										</div>		
													<div class="row1 col-xs-8">
														<label class="L-size control-label"></label>

														<div class="I-size">
															<div class="pull-left">
																<button type="submit" name="commit"
																	class="btn btn-success">
																	<i class="fa fa-search"> Search</i>
																</button>
															</div>
														</div>
													</div>
					</div>
			</div>
		</div>		
	</section>		
	<section class="content" id="reportData" style="display: none;">
		<sec:authorize access="!hasAuthority('VIEW_CHECKING_ENTRY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_CHECKING_ENTRY')">
			<div id="div_print">
				<section class="invoice">
					<div class="row invoice-info">
						<div class="col-xs-12">
						<div class="panel-heading text-center"><h4> Checking Report</h4></div>
							<div class="table-responsive">
								<table class="table table-hover table-bordered table-striped" id="companyTable" style="display: none;">
										<tbody id="tbodyHeader">
											
										</tbody>
									</table>
								<div id="sorttable-div" align="center" style="font-size: 10px"
									class="table-responsive ">
									<div class="row invoice-info">
										<table id="advanceTable"
											class="table table-hover table-bordered table-striped" style="display: none;">
											<caption id="reportDetails"></caption>
													<thead id="tHeadId">
														
													</thead>
													<tbody id="tableBody">
		
													</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</section>
			</div>
		</sec:authorize>
	</section>
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js" />"></script>
		<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/checkinReport.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/script.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	
	
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		});
	</script>
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script>

</div>