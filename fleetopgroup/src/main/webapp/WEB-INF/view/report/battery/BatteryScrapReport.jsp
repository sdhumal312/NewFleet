<%@ include file="../../taglib.jsp"%>
<%@page import="org.springframework.security.core.authority.SimpleGrantedAuthority"%>
<%@page import="org.springframework.security.core.GrantedAuthority"%>
<%@page import="java.util.Collection"%>
<%@page import="org.springframework.web.servlet.ModelAndView"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/monthpicker.css"/>">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">	
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/batteryReport.in"/>">Battery Reports</a> / Battery Scrap Report
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm hide"
						onclick="printDiv('div_print')" id="printBtn">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm hide"
						onclick="advanceTableToExcel('batteryScrapDetails', 'Battery Scrap Report')" id="exportExcelBtn">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
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
									<div class="row1" id="grpmanufacturer" class="form-group">
													<label class="L-size control-label" for="manufacurer">Battery
														Manufacturer :
													</label>
													<div class="I-size">
														<input type="hidden" id="manufacurer"
															name="batteryManufacturerId" style="width: 100%;"
															placeholder="Please Enter 2 or more Battery Manufacturer Name" />
														<span id="manufacturerIcon" class=""></span>
														<div id="manufacturerErrorMsg" class="text-danger"></div>
													</div>
									</div>
									<div class="row1" id="grptyreModel" class="form-group">
													<label class="L-size control-label" for="tyremodel">Battery
														Model :
													</label>
													<div class="I-size">
														<select style="width: 100%;" id="batterryTypeId"
														 name="batteryTypeId">
														</select> <span id="tyreModelIcon" class=""></span>
														<div id="tyreModelErrorMsg" class="text-danger"></div>
													</div>
									</div>
									<div class="row1" id="grptyreSize" class="form-group">
													<label class="L-size control-label" for="tyreSize">Battery
														Capacity :
													</label>
													<div class="I-size">
														<input type="hidden" id="batteryCapacityId" name="batteryCapacityId"
															style="width: 100%;"
															placeholder="Please select Battery Capacity" /> <span
															id="tyreSizeIcon" class=""></span>
														<div id="tyreSizeErrorMsg" class="text-danger"></div>
													</div>
									</div>
										<div class="row1">
											<label class="L-size control-label">Warehouse
												location </label>
											<div class="I-size">
												<input type="hidden" name="BatteryLocationFrom"
													id="BatteryLocationFrom" value="0" style="width: 100%;" placeholder="All" />
											</div>
										</div>
										
										<!-- Date range -->
										<div class="row1">
											<label class="L-size control-label">Date range: <abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<div class="input-group">
													<div class="input-group-addon">
														<i class="fa fa-calendar"></i>
													</div>
													<input type="text" id="BatteryTransferRange" class="form-text"
														name="BatteryTransferRange" required="required"
														style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
												</div>
											</div>
										</div>
										
										<div class="row1">
												<label class="L-size control-label"></label>

												<div class="I-size">
													<div class="pull-left">
														<button name="commit"
															class="btn btn-success" id="scrapSearch">
															<i class="fa fa-search"> Search</i>
														</button>
													</div>
												</div>
										</div>
									</div>
							</div>
						<!-- </div> -->
					</div>
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
										<div class="row invoice-info" id="reportHeader" style="font-size: 15px;font-weight: bold;">
										</div>
										<div class="row invoice-info">
											<table id="batteryScrapDetails" style="width: 95%;"
																class="table-hover table-bordered">
												
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/BatteryScrapReport.js"/>"></script>
	<script type="text/javascript" 
		src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/battery/batteryUtility.js"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script>
</div>