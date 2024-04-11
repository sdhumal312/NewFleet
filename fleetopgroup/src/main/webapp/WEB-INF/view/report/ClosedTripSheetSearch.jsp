<%@ include file="taglib.jsp"%>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/monthpicker.css"/>">	
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> / <a
						href="<c:url value="/newTripSheetEntries.in"/>">TripSheets</a> / <span>Search Closed TripSheets</span>
				</div>
				<div class="col-md-off-5">
						<div class="col-md-3">
							<form action="<c:url value="/searchTripSheet.in"/>" method="post">
								<div class="input-group">
									<input class="form-text" id="tripStutes" name="tripStutes" type="text"
										required="required" placeholder="TS-ID, Vehicle,T-Route, T-Bookref" maxlength="20">
									<span class="input-group-btn">
										<button type="submit" name="search" id="search-btn" class="btn btn-success btn-sm">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
							</form>
						</div>
					<a href="<c:url value="/newTripSheetEntries.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('TRIPSHEET_BULK_ACC_CLOSE')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('TRIPSHEET_BULK_ACC_CLOSE')">
			<div class="row">
				<div class="col-md-offset-1 col-md-9">
					<fieldset>
						<legend> Closed Trip Sheet Search</legend>
						<div class="row">
							<div class="box box-success">
								<div class="box-header"></div>
								<div class="box-body no-padding">
									<form action="#" method="post">
										<div class="form-horizontal ">
											<fieldset>
												<!-- Date range -->
												<div class="row1">
											<label class="L-size control-label">Month: <abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<div class="input-group">
													<div class="input-group-addon">
														<i class="fa fa-calendar"></i>
													</div>
													<input type="text" id="reportrange" class="form-text"
														name="Tripsheet_daterange" required="required"
														style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
												</div>
											</div>
											
										</div>
											<div class="row1">
													<label class="L-size control-label">Vehicle Name <abbr
														title="required">*</abbr>
													</label>
													<div class="I-size">
														<input type="hidden" id="ReportSelectVehicle"
															name="repair_vid" style="width: 100%;"
															required="required"
															placeholder="Please Enter 2 or more Vehicle Name" />
														<p class="help-block">Select Vehicle</p>
													</div>
											</div>

											</fieldset>
											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button class="btn btn-success" onclick="getTripSheetDataOnDateRange()"
																	name="commit" type="button">Search All</button>
															<a href="<c:url value="/newTripSheetEntries.in"/>"
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
				</div>
			</div>
		</sec:authorize>
				<div class="row">
					<sec:authorize access="!hasAuthority('TRIPSHEET_BULK_ACC_CLOSE')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('TRIPSHEET_BULK_ACC_CLOSE')">
						<div class="main-body hide" id="ReportTripSheetTableDetails">
							<h4>TripSheets Report</h4>
							<div class="box">
								<div class="box-body">
									<table id="ReportTripSheetTable" class="table table-bordered table-striped">
									</table>
								</div>
							</div>
						</div>
					</sec:authorize>
				</div>
				<div class="row1 hide" id="remarkCol">
					<div class="col-md-6">
						<label class="L-size control-label">Remarks :<abbr title="required">*</abbr></label>
						<div class="I-size">
			    			<input class="string required form-text" id="closeACCTripReference" maxlength="512" type="text" name="remark"
							onkeypress="return Isclosepaidto(event);" ondrop="return false;"> <label class="error" id="errorClosepaidto" style="display: none"> </label>
						</div>
					</div>
					
					<fieldset class="form-actions">
						<div class="row1">
							<label class="L-size control-label"></label>
							<div class="I-size">
								<div class="pull-left">
									<input class="btn btn-success" name="commit" type="submit" id="closeAll" onclick="closeAccountOffSelectedTripSheet();" value="A/C Close All">
										<a href="<c:url value="/newTripSheetEntries.in"/>" class="btn btn-info"> <span id="Can">Cancel</span></a>
								</div>
							</div>
						</div>
					</fieldset>
				</div>
				</form>
	</section>
								
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Reportlanguage.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepicker.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Report.validate.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#vehicle_Group, #closeTripStatus").select2({
				placeholder : "Please Enter"
			}), $("#tagPicker").select2({
				closeOnSelect : !1
			});
		});
	</script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js" />"></script>
		
	<script type="text/javascript">
		$(document).ready(function() {
			$('#reportrange').Monthpicker({
				monthLabels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]
			});
		});
	</script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/BulkAccClose.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/monthpicker.min.js"/>"></script>
</div>