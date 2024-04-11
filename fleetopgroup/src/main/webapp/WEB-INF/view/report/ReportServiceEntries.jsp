<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="row">
					<div class="pull-left">
						<a href="<c:url value="/open"/>"><spring:message
								code="label.master.home" /></a> / <a
							href="<c:url value="/newServiceEntries/1/1.in"/>"> Service
							Entries</a> / <a href="<c:url value="/ServiceEntriesReport"/>">
							Service Entries Report</a>
					</div>
					<div class="col-md-off-5">
						<div class="col-md-3">
							<form action="<c:url value="/searchServiceEntries.in"/>"
								method="post">
								<div class="input-group">
									<input class="form-text" id="vehicle_registrationNumber"
										name="Search" type="number" required="required" min="1"
										placeholder="ID, V-Name, Invoice-No"> <span
										class="input-group-btn">
										<button type="submit" name="search" id="search-btn"
											class="btn btn-success btn-sm">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
							</form>
						</div>
						<a href="<c:url value="/newServiceEntries/1/1.in"/>">Cancel</a>
					</div>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_SERVICE_ENTRIES')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_SERVICE_ENTRIES')">
			<div class="row">
				<div class="col-md-offset-1 col-md-9">
					<fieldset>
						<legend>Service Entries Search</legend>

						<div class="row">
							<div class="box box-success">
								<div class="box-header"></div>
								<div class="box-body no-padding">
									<form action="ReportServiceEntries.in" method="post">
										<div class="form-horizontal ">
											<fieldset>
												<div class="row1">
													<label class="L-size control-label" for="issue_vehicle_id">Vehicle

													</label>
													<div class="I-size">
														<div class="col-md-9">
															<input type="hidden" id="vehicle_vid" name="vid" value=""
																style="width: 100%;" required="required"
																placeholder="Please Enter 2 or more Vehicle Name" />
														</div>
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Driver Name :</label>
													<div class="I-size" id="driverSelect">
														<input type="hidden" id="SelectDriverName"
															name="driver_id" style="width: 100%;" value=""
															required="required"
															placeholder="Please Enter 2 or more Driver Name" />

													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Vendor :</label>
													<div class="I-size" id="vendorSelect">
														<input type="hidden" id="selectVendor" name="Vendor_id"
															style="width: 100%;" required="required" value=""
															placeholder="Please Select Vendor Name" /> <label
															class="error" id="errorVendorSelect"> </label>

													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Invoice Date: </label>
													<div class="I-size">
														<div class="input-group input-append date" id="StartDate">
															<input type="text" class="form-text" name="invoiceDate"
																data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" />
															<span class="input-group-addon add-on"> <span
																class="fa fa-calendar"></span>
															</span>
														</div>
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Modes of
														Payment </label>
													<div class="I-size">
														<select class="form-text" name="service_paymentTypeId"
															id="renPT_option">
															<option value=""></option>
															<option value="1">Cash</option>
															<option value="2">CREDIT</option>
															<option value="3">NEFT</option>
															<option value="4">RTGS</option>
															<option value="5">IMPS</option>

														</select>
													</div>
												</div>
												<!-- Date range -->
												<div class="row1">
													<label class="L-size control-label">Paid Date
														range: <abbr title="required">*</abbr>
													</label>
													<div class="I-size">
														<div class="input-group">
															<div class="input-group-addon">
																<i class="fa fa-calendar"></i>
															</div>
															<input type="text" id="reportrange" class="form-text"
																name="Service_daterange" required="required"
																style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
														</div>
													</div>
												</div>
											</fieldset>
											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<input class="btn btn-success"
																onclick="this.style.visibility = 'hidden'" name="commit"
																type="submit" value="Search All"> <a
																href="<c:url value="/newServiceEntries/1/1.in"/>"
																class="btn btn-info"> <span id="Can">Cancel</span>
															</a>
														</div>
													</div>
												</div>
											</fieldset>

										</div>
									</form>
								</div>
							</div>

						</div>
					</fieldset>
					<div style="height: 200px"></div>
				</div>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/SE/ServiceEntriesValidate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Report.validate.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("dd-mm-yyyy", {
				placeholder : "dd-mm-yyyy"
			}), $("[data-mask]").inputmask()
		});
	</script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js" />"></script>
	<script>
		$(function() {
			function a(a, b) {
				$("#reportrange").val(
						a.format("YYYY-MM-DD") + " to "
								+ b.format("YYYY-MM-DD"))
			}
			a(moment().subtract(1, "days"), moment()), $("#reportrange")
					.daterangepicker(
							{
								ranges : {
									Today : [ moment(), moment() ],
									Yesterday : [ moment().subtract(1, "days"),
											moment().subtract(1, "days") ],
									"Last 7 Days" : [
											moment().subtract(6, "days"),
											moment() ]
								}
							}, a)
		});
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>