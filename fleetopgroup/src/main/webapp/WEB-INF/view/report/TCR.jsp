<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<style>
.box-body .affix {
	border-radius: 3px;
	background: #FFF;
	margin-bottom: 5px;
	padding: 5px;
}
</style>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/TCR.in"/>">Trip Collection Report</a>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/Report"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">



		<div class="row">

			<div class="col-md-offset-2 col-md-8 col-sm-8 col-xs-12">
				<sec:authorize access="hasAuthority('VIEW_DA_TC_REPORT')">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#TripCollectionOne"> Daily Trip Collection Report </a>
							</h4>
						</div>
						<div id="TripCollectionOne" class="panel-collapse collapse">
							<div class="box-body">
								<form action="DailyTripCollectionReport" method="post">
									<div class="form-horizontal ">
										<!-- Date range -->
										<div class="row1">
											<label class="L-size control-label">Date <abbr
												title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="input-group input-append date" id="TCDailydate">
													<input type="text" class="form-text" name="TRIP_DATE"
														required="required" data-inputmask="'alias': 'yyyy-mm-dd'"
														data-mask="" /> <span class="input-group-addon add-on">
														<span class="fa fa-calendar"></span>
													</span>
												</div>
											</div>

										</div>

										<fieldset class="form-actions">
											<div class="row1">
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
										</fieldset>
									</div>
								</form>
							</div>
						</div>
					</div>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_VE_TC_REPORT')">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#TripCollectionTwo"> Vehicle Wise Trip Collection
									Report </a>
							</h4>
						</div>
						<div id="TripCollectionTwo" class="panel-collapse collapse">
							<div class="box-body">
								<form action="VehicleWiseTripColReport" method="post">
									<div class="form-horizontal ">
										<!-- vehicle Select -->
										<div class="row1">
											<label class="L-size control-label">Vehicle Name <abbr
												title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="hidden" id="TCVehicle" name="VEHICLE_ID"
													style="width: 100%;" required="required" value="0"
													placeholder="Please Enter 2 or more Vehicle Name" />
												<p class="help-block">Select One Or More Vehicle</p>
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
													<input type="text" id="VTC_daterange" class="form-text"
														name="VEHICLE_TC_DATERAGE" required="required"
														style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
												</div>
											</div>
										</div>
										<fieldset class="form-actions">
											<div class="row1">
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
										</fieldset>
									</div>
								</form>
								<!-- end Repair Report -->
							</div>
						</div>
					</div>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_GR_TC_REPORT')">
					<div class="panel box box-success">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#TripCollectionThree">Group Wise Trip Collection
									Report </a>
							</h4>
						</div>
						<div id="TripCollectionThree" class="panel-collapse collapse">
							<div class="box-body">
								<div class="form-horizontal ">
									<!-- Show Group Search GroupWiseTripColReport -->
									<form action="GroupWiseTripColReport" method="post">
										<!-- vehicle Group Service -->
										<div class="row1">
											<label class="L-size control-label"> Group : <abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input type="hidden" id="TCGroupWise" name="TRIP_GROUP"
													style="width: 100%;" value="ALL"
													placeholder="Please Enter 2 or more Group Name" />
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
													<input type="text" id="GTC_daterange" class="form-text"
														name="VEHICLE_TC_DATERAGE" required="required"
														style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
												</div>
											</div>
										</div>


										<fieldset class="form-actions">
											<div class="row1">
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
										</fieldset>

									</form>
									<!-- end Show Group Search RR Range -->
								</div>
							</div>
						</div>
					</div>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_DA_TC_REPORT')">
					<div class="panel box box-success">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#TripCollectionFour">Date Range Wise Trip Collection
									Report </a>
							</h4>
						</div>
						<div id="TripCollectionFour" class="panel-collapse collapse">
							<div class="box-body">
								<div class="form-horizontal ">
									<!-- Show Group Search Fuel Range -->
									<form action="DateWiseTripColReport" method="post">

										<!-- Date range -->
										<div class="row1">
											<label class="L-size control-label">Date range: <abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<div class="input-group">
													<div class="input-group-addon">
														<i class="fa fa-calendar"></i>
													</div>
													<input type="text" id="DRTC_daterange" class="form-text"
														name="TRIP_DATE_RANGE" required="required"
														style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
												</div>
											</div>
										</div>


										<fieldset class="form-actions">
											<div class="row1">
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
										</fieldset>

									</form>
									<!-- end Show Group Search RR Range -->
								</div>
							</div>
						</div>
					</div>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_TO_DR_BA_REPORT')">
					<div class="panel box box-success">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#TripCollectionNINE">Total Driver Balance Report </a>
							</h4>
						</div>
						<div id="TripCollectionNINE" class="panel-collapse collapse">
							<div class="box-body">
								<div class="form-horizontal ">
									<!-- Show Group Search Fuel Range -->
									<form action="TotalDriverBalanceReport" method="post">

										<div class="row1">
											<label class="L-size control-label" for="issue_vehicle_id">Driver:
											</label>
											<div class="I-size">
												<input type="hidden" id="DriverAdList" name="TRIP_DRIVER_ID"
													style="width: 100%;" value="0"
													placeholder="Please Enter 3 or more Driver Name, NO" /> <label
													id="errorDriver2" class="error"></label>
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
													<input type="text" id="IssuesReportedRange"
														class="form-text" name="TRIP_DATE_RANGE"
														required="required"
														style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
												</div>
											</div>
										</div>
										<fieldset class="form-actions">
											<div class="row1">
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
										</fieldset>
									</form>
								</div>
							</div>
						</div>
					</div>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_TO_CO_BA_REPORT')">
					<div class="panel box box-success">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#TripCollectionTEN">Total Conductor Balance Report </a>
							</h4>
						</div>
						<div id="TripCollectionTEN" class="panel-collapse collapse">
							<div class="box-body">
								<div class="form-horizontal ">
									<!-- Show Group Search Fuel Range -->
									<form action="TotalConductorBalanceReport" method="post">
										<div class="row1">
											<label class="L-size control-label" for="issue_vehicle_id">Conductor:
											</label>
											<div class="I-size">
												<input type="hidden" id="ConductorAdList"
													name="TRIP_CONDUCTOR_ID" style="width: 100%;" value="0"
													placeholder="Please Enter 3 or more Conductor Name, NO" />
												<label id="errorDriver2" class="error"></label>
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
													<input type="text" id="TyreRetreadRange" class="form-text"
														name="TRIP_DATE_RANGE" required="required"
														style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
												</div>
											</div>
										</div>
										<fieldset class="form-actions">
											<div class="row1">
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
										</fieldset>
									</form>
								</div>
							</div>
						</div>
					</div>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_DR_JA_TC_REPORT')">
					<div class="panel box box-success">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#TripCollectionFive">Driver JAMA Collection Report </a>
							</h4>
						</div>
						<div id="TripCollectionFive" class="panel-collapse collapse">
							<div class="box-body">
								<div class="form-horizontal ">
									<!-- Show Group Search Fuel Range -->
									<form action="DriverJamaTripColReport" method="post">

										<div class="row1">
											<label class="L-size control-label" for="issue_vehicle_id">Driver
												: <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="hidden" id="JAMAdriverList"
													name="TRIP_DRIVER_ID" style="width: 100%;"
													placeholder="Please Enter 3 or more Driver Name, No"
													value="0" /> <label id="errorDriver1" class="error"></label>
											</div>
										</div>
										<!-- <div class="row1">
												<label class="L-size control-label" for="issue_vehicle_id">Cleaner
													:</label>
												<div class="I-size" >
													<input type="hidden" id="JAMACleaner" name="TRIP_CLEANER_ID"
														style="width: 100%;" value="0"
														placeholder="Please Enter 3 or more Cleaner Name, No" />
													<label id="errorCleaner" class="error"></label>
												</div>
											</div> -->
										<!-- Date range -->
										<div class="row1">
											<label class="L-size control-label">Date range: <abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<div class="input-group">
													<div class="input-group-addon">
														<i class="fa fa-calendar"></i>
													</div>
													<input type="text" id="DJDateRange" class="form-text"
														name="TRIP_DATE_RANGE" required="required"
														style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
												</div>
											</div>
										</div>


										<fieldset class="form-actions">
											<div class="row1">
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
										</fieldset>

									</form>
									<!-- end Show Group Search RR Range -->
								</div>
							</div>
						</div>
					</div>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_CO_JA_TC_REPORT')">
					<div class="panel box box-success">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#TripCollectionSIX">Conductor JAMA Collection Report
								</a>
							</h4>
						</div>
						<div id="TripCollectionSIX" class="panel-collapse collapse">
							<div class="box-body">
								<div class="form-horizontal ">
									<!-- Show Group Search Fuel Range -->
									<form action="ConductorJamaTripColReport" method="post">

										<div class="row1">
											<label class="L-size control-label" for="issue_vehicle_id">Conductor:

											</label>
											<div class="I-size">
												<input type="hidden" id="JAMAConductorList"
													name="TRIP_CONDUCTOR_ID" style="width: 100%;" value="0"
													placeholder="Please Enter 3 or more Conductor Name, NO" />
												<label id="errorDriver2" class="error"></label>
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
													<input type="text" id="CJDateRange" class="form-text"
														name="TRIP_DATE_RANGE" required="required"
														style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
												</div>
											</div>
										</div>


										<fieldset class="form-actions">
											<div class="row1">
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
										</fieldset>

									</form>
									<!-- end Show Group Search RR Range -->
								</div>
							</div>
						</div>
					</div>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_DR_AD_JA_REPORT')">
					<div class="panel box box-success">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#TripCollectionSEVEN">Driver Advance JAMA Report </a>
							</h4>
						</div>
						<div id="TripCollectionSEVEN" class="panel-collapse collapse">
							<div class="box-body">
								<div class="form-horizontal ">
									<!-- Show Group Search Fuel Range -->
									<form action="AdvanceJamaReport" method="post">

										<div class="row1">
											<label class="L-size control-label" for="issue_vehicle_id">Driver:
											</label>
											<div class="I-size">
												<input type="hidden" id="DriverAdList" name="TRIP_DRIVER_ID"
													style="width: 100%;" value="0"
													placeholder="Please Enter 3 or more Driver Name, NO" /> <label
													id="errorDriver2" class="error"></label>
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
													<input type="text" id="DriAdvDateRange" class="form-text"
														name="TRIP_DATE_RANGE" required="required"
														style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
												</div>
											</div>
										</div>
										<fieldset class="form-actions">
											<div class="row1">
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
										</fieldset>
									</form>
								</div>
							</div>
						</div>
					</div>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_CO_AD_JA_REPORT')">
					<div class="panel box box-success">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#TripCollectionEIGHT">Conductor Advance JAMA Report </a>
							</h4>
						</div>
						<div id="TripCollectionEIGHT" class="panel-collapse collapse">
							<div class="box-body">
								<div class="form-horizontal ">
									<!-- Show Group Search Fuel Range -->
									<form action="AdvanceJamaReport" method="post">
										<div class="row1">
											<label class="L-size control-label" for="issue_vehicle_id">Conductor:
											</label>
											<div class="I-size">
												<input type="hidden" id="ConductorAdList"
													name="TRIP_DRIVER_ID" style="width: 100%;" value="0"
													placeholder="Please Enter 3 or more Conductor Name, NO" />
												<label id="errorDriver2" class="error"></label>
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
													<input type="text" id="ConAdvDateRange" class="form-text"
														name="TRIP_DATE_RANGE" required="required"
														style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
												</div>
											</div>
										</div>
										<fieldset class="form-actions">
											<div class="row1">
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
										</fieldset>
									</form>
								</div>
							</div>
						</div>
					</div>
				</sec:authorize>
			</div>
		</div>
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

	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		});
	</script>
</div>