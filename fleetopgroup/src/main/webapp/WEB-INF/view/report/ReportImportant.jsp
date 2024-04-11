<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />" >
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />" >
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
					<sec:authorize access="hasAuthority('IMPORTANT_REPORT')">
						/ <a href="<c:url value="/ImportantReport"/>">Important Report</a>
					</sec:authorize>
						Report</a>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/ImportantReport"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">



		<div class="row">

			<div class="col-md-offset-2 col-md-8 col-sm-8 col-xs-12">
				<div class="panel box box-primary">
					<div class="box-header with-border">
						<h4 class="box-title">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#TripCollectionWise">Depot Wise Daily Trip Collection
								&amp; CashBook Report </a>
						</h4>
					</div>
					<div id="TripCollectionWise" class="panel-collapse collapse">
						<div class="box-body">
							<form action="DailyTripDailyCashbookReport" method="post">
								<div class="form-horizontal ">
									<div class="row1">
										<label class="L-size control-label"> Depot Name : <abbr
											title="required">*</abbr></label>
										<div class="I-size">
											<input type="hidden" id="TCGroupWise" name="VEHICLEGROUP"
												style="width: 100%;" value="ALL"
												placeholder="Please Enter 2 or more Group Name" />
										</div>
									</div>
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
													<button type="submit" name="commit" class="btn btn-success">
														<i class="fa fa-search"> Search Trip Collection</i>
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

				<div class="panel box box-primary">
					<div class="box-header with-border">
						<h4 class="box-title">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#TripCollectionTime">Vehicle Trip Wise Report </a>
						</h4>
					</div>
					<div id="TripCollectionTime" class="panel-collapse collapse">
						<div class="box-body">
							<form action="DailyTripDailyTimeReport" method="post">
								<div class="form-horizontal ">

									<!-- vehicle Group Service -->
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
													<button type="submit" name="commit" class="btn btn-success">
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
				<sec:authorize access="hasAuthority('VIEW_DE_RO_TI_REPORT')">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
									<a 
										href="<c:url value="/TripDailyRouteTimeReport"/>">Depot Trip Wise
										Report </a>
							</h4>
						</div>
					</div>
				</sec:authorize>
<!-- 				<div class="panel box box-primary">
					<div class="box-header with-border">
						<h4 class="box-title">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#TripCollectionTimeRoute">Depot Wise Route Time Report
							</a>
						</h4>
					</div>
					<div id="TripCollectionTimeRoute" class="panel-collapse collapse">
						<div class="box-body">
							<form action="DailyTripDailyRouteTimeReport" method="post">
								<div class="form-horizontal ">

									<div class="row1">
										<label class="L-size control-label"> Route : <abbr
											title="required">*</abbr></label>
										<div class="I-size">

											<input type="hidden" id="TripRouteNameID" name="ROUTE_ID"
												style="width: 100%;"
												placeholder="Please Enter 3 or more Route Name, NO " />
										</div>
									</div>
									Date range
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
													<button type="submit" name="commit" class="btn btn-success">
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
				</div> -->
				<div class="tab-pane active" id="vehicleReport">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#vehicleServiceReport">Depot Wise All Service Report
								</a>
							</h4>
						</div>
						<div id="vehicleServiceReport" class="panel-collapse collapse">
							<div class="box-body">
								<form action="ALLVehicleRepairReport" method="post">
									<div class="form-horizontal ">
										<div class="row1">
											<label class="L-size control-label">Depot : <abbr
												title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="hidden" id="workOrderGroup"
													name="VEHICLE_GROUP_ID" style="width: 100%;"
													required="required"
													placeholder="Please Enter 2 or more Vehicle Name" />
												<p class="help-block">Select One Vehicle Depot</p>
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
													<input type="text" id="reportrange" class="form-text"
														name="VEHICLE_DATERANGE" required="required"
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
							</div>
						</div>
					</div>
				</div>
				<sec:authorize access="hasAuthority('VIEW_DE_RO_TI_REPORT')">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
									<a 
										href="<c:url value="/DailyMonthlyYearlyTripCollectionReport"/>">Depot Wise Monthly Trip Collection Report </a>
							</h4>
						</div>
					</div>
				</sec:authorize>

				<div class="panel box box-primary">
					<div class="box-header with-border">
						<h4 class="box-title">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#TripVehicleFuel">Vehicle Wise Fuel Mileage Report </a>
						</h4>
					</div>
					<div id="TripVehicleFuel" class="panel-collapse collapse">
						<div class="box-body">
							<form action="VehicleWiseFuelMileageReport" method="post">
								<div class="form-horizontal ">
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
													<button type="submit" name="commit" class="btn btn-success">
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
				<div class="panel box box-primary">
					<div class="box-header with-border">
						<h4 class="box-title">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#TripDriverFuel">Driver Wise Fuel Mileage Report </a>
						</h4>
					</div>
					<div id="TripDriverFuel" class="panel-collapse collapse">
						<div class="box-body">
							<form action="DriverWiseFuelMileageReport" method="post">
								<div class="form-horizontal ">
									<div class="row1">
										<label class="L-size control-label" for="issue_vehicle_id">Driver:
											<abbr title="required">*</abbr>
										</label>
										<div class="I-size">
											<input type="hidden" id="DriverAdList" name="TRIP_DRIVER_ID"
												style="width: 100%;" value="0"
												placeholder="Please Enter 3 or more Driver Name, NO" /> <label
												id="errorDriver2" class="error"></label>
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
													<button type="submit" name="commit" class="btn btn-success">
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
				<div class="panel box box-success">
					<div class="box-header with-border">
						<h4 class="box-title">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#TripCoductor">Conductor Wise Trip Collection Report </a>
						</h4>
					</div>
					<div id="TripCoductor" class="panel-collapse collapse">
						<div class="box-body">
							<div class="form-horizontal ">
								<form action="ConductorWiseCollectionReport" method="post">
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
													<button type="submit" name="commit" class="btn btn-success">
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
				<div class="panel box box-success">
					<div class="box-header with-border">
						<h4 class="box-title">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#TripCollectionThree">Route Wise Date Range Weekly,
								Monthly, Yearly Trip Collection Report </a>
						</h4>
					</div>
					<div id="TripCollectionThree" class="panel-collapse collapse">
						<div class="box-body">
							<div class="form-horizontal ">
								<form action="RouteWiseTripDailyReport" method="post">
									<div class="row1">
										<label class="L-size control-label"> Route : <abbr
											title="required">*</abbr></label>
										<div class="I-size">

											<input type="hidden" id="TripRouteNameList" name="TRIP_ROUTE"
												style="width: 100%;"
												placeholder="Please Enter 3 or more Route Name, NO " />
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
													<button type="submit" name="commit" class="btn btn-success">
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
				<div class="panel box box-danger">
					<div class="box-header with-border">
						<h4 class="box-title">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#collapsedriveresi"> Driver Month wise ESI, PF Date
								Range Report </a>
						</h4>
					</div>
					<div id="collapsedriveresi" class="panel-collapse collapse">
						<div class="box-body">
							<form action="DriverMonthEsiPFReport" method="post">
								<div class="form-horizontal ">
									<div class="row1">
										<label class="L-size control-label">Date range: <abbr
											title="required">*</abbr></label>
										<div class="I-size">
											<div class="input-group">
												<div class="input-group-addon">
													<i class="fa fa-calendar"></i>
												</div>
												<input type="text" id="rangeFuelMileage" class="form-text"
													name="TRIP_DATE_RANGE" required="required"
													style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
											</div>
										</div>
									</div>
									<div class="row1">
										<label class="L-size control-label">Depot: <abbr
											title="required">*</abbr>
										</label>
										<div class="I-size">
											<input type="hidden" id="SelectFuelGroup" name="vehicleGroupId"
												style="width: 100%;" required="required"
												placeholder="Please Select Depot" />
											<p class="help-block">Select One Depot</p>
										</div>
									</div>
									<!-- <div class="row1">
									<label class="L-size control-label"> Job Title :<abbr
										title="required">*</abbr></label>
									<div class="I-size">
										<input type="hidden" id="AttGroupDriverJob"
											name="DRIVER_JOBTITLE" value="ALL" style="width: 100%;"
											placeholder="Please Enter 2 or more Job Type" />
									</div>
								</div> -->

									<fieldset class="form-actions">
										<div class="row1">
											<label class="L-size control-label"></label>
											<div class="I-size">
												<div class="pull-left">
													<button type="submit" name="commit" class="btn btn-success">
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

			</div>
		</div>
	</section>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />" ></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Report.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		})
	</script>
</div>