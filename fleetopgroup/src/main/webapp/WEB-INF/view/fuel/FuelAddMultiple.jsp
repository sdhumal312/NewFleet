<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/Fuel/1.in"/>">Fuel Entry</a> / <small>Add
						Fuel Entry</small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_FUEL')">
						<a href="<c:url value="/MultipleFuelEntries.in"/>"
							class="btn btn-default btn-sm"> <span class="fa fa-plus">
								or Add Multiple Fuel Entries</span>
						</a>

					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content">
		<sec:authorize access="!hasAuthority('ADD_FUEL')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('ADD_FUEL')">
			<div class="row" onload="fuelvehicle()">
				<div class="col-md-offset-1 col-md-9">

					<c:if test="${param.closeStatus eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>

							${VMandatory} You should be close first TripSheet or change
							status or close workOrder .
						</div>
					</c:if>
					<c:if test="${param.Success eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							${Number}<br>This Fuel Entries created successfully .
						</div>
					</c:if>

					<c:if test="${param.danger eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Fuel Entries Fuel Reference ,Vehicle Name, Vendor Name
							Already created. Or This Fuel Entries Can't be Edit or Delete ..
							This Fuel Entry is Approved or Payment Mode
						</div>
					</c:if>
					<c:if test="${param.duplicateFuelEntries eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Fuel EntriesFuel Reference ,Vehicle Name, Vendor Name
							Already created. (or) This Duplicate Entries Reference Number.
						</div>
					</c:if>

					<form id="formFuel" action="<c:url value="/saveMultipleFuel.in"/>"
						method="post" enctype="multipart/form-data" name="formFuel"
						role="form" class="form-horizontal"
						onsubmit="return validateFuel();">
						<input type="hidden" id="vehicle_ExpectedOdameter" name="vehicle_ExpectedOdameter" />
						<input type="hidden" id="vehicle_Odometer" name="vehicle_Odometer" />
						<div class="form-horizontal ">
							<fieldset>
								<legend>Fuel Entries</legend>
								<div class="box">
									<div class="box-body">
										<div class="row1" id="grpvehicleNumber" class="form-group">
											<label class="L-size control-label" for="FuelSelectVehicle">Vehicle<abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input type="hidden" id="FuelSelectVehicle" name="vid"
													style="width: 100%;" required="required"
													placeholder="Please Enter 2 or more Vehicle Name" /> <span
													id="vehicleNumberIcon" class=""></span>
												<div id="vehicleNumberErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Vehicle Group :</label>
											<div class="I-size">

												<input class="form-text" id="vehicle_group"
													required="required" name="vehicle_group" type="text"
													readonly="readonly">

											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">TripSheet ID :</label>
											<div class="I-size">
												<div class="input-group">
													<span class="input-group-addon"> <span
														aria-hidden="true">TS-</span></span> <input type="text"
														class="form-text" name="fuel_TripsheetNumber" value="0"
														placeholder="eg: 160" maxlength="8" id="pcName"
														onkeypress="return RouteKM(event);" ondrop="return false;"
														required="required" />

												</div>

											</div>
										</div>
										<div class="row1" id="grpfuelDate" class="form-group">
											<label class="L-size control-label" for="fuelDate">Date
												<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="input-group input-append date" id="StartDate">
													<input type="text" class="form-text" name="fuel_date"
														id="fuelDate" required="required"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
												<span id="fuelDateIcon" class=""></span>
												<div id="fuelDateErrorMsg" class="text-danger"></div>
											</div>
										</div>

										<div class="row1" id="grpfuelOdometer" class="form-group">
											<label class="L-size control-label" for="fuel_meter">Odometer<abbr
												title="required">*</abbr></label>
											<div class="I-size">

												<input id="fuel_meter_old" name="fuel_meter_old"
													type="hidden"> <input class="form-text"
													id="fuel_meter" name="fuel_meter" type="number" min="0"
													max="" onkeypress="return IsNumericOdometer(event);"
													ondrop="return false;" onblur="validateMaxOdameter()";> <span id="fuelOdometerIcon"
													class=""></span>
												<div id="fuelOdometerErrorMsg" class="text-danger"></div>
												<input type="hidden" id="validateOdometerInFuel" value="${validateOdometerInFuel}">
												<label class="error" id="errorOdometer"
													style="display: none"></label> <label class="error"
													id="errorOdo"></label>

												<p class="help-block">Reading at time of fuel-up</p>
												<div class="help-block checkbox">
													<label for="fuel_meter_entry_attributes_void"> <input
														name="fuel_meter_attributes" type="hidden" value="0">
														<input id="fuel_meter_attributes"
														name="fuel_meter_attributes" type="checkbox" value="1">
														Mark as <strong>void</strong> <a data-toggle="modal"
														href="#void-help" rel="tooltip" tabindex="-1" title=""
														data-original-title="Click for more info"> <i
															class="fa fa-info-circle"></i>
													</a>
													</label>
												</div>
												<div aria-hidden="aria-hidden" class="fade modal"
													id="void-help" role="dialog" tabindex="-1">
													<div class="modal-dialog">
														<div class="modal-content">
															<div class="modal-header">
																<button class="close" data-dismiss="modal" type="button">×</button>
																<h3 class="modal-title">Voiding a Meter Entry</h3>
															</div>

															<div class="modal-body">
																<p>
																	Marking a meter entry as <strong>void</strong> allows
																	you to save an invalid meter value but essentially
																	tells Fleetop to ignore it. Think of it as sort of a
																	"manual override" for saving an invalid meter value.
																</p>
																<p>A voided meter entry is never considered a
																	vehicle's "current meter" even if it's the most recent.
																	Hence, a voided meter entry will never trigger a
																	service reminder or anything else that depends on a
																	vehicle's current meter value.</p>

															</div>

														</div>
													</div>
												</div>
											</div>
										</div>
										
										<div class="row1" id="gpsOdometerRow" class="form-group" style="display: none;">
											<label class="L-size control-label" for="fuel_type">GPS
												Odometer :
											</label>
											<div class="I-size">
												<input type="text" class="form-text" id="gpsOdometer" name="gpsOdometer" readonly="readonly">
											</div>
										</div>

										<div class="row1" id="grpfuelType" class="form-group">
											<label class="L-size control-label" for="fuel_type">Fuel
												Type :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<select id="fuel_type" name="fuelTypeId"
													style="width: 100%;">

												</select> <span id="fuelTypeIcon" class=""></span>
												<div id="fuelTypeErrorMsg" class="text-danger"></div>

											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Fuel Vendor :<abbr
												title="required">*</abbr></label>
											<div class="I-size" id="vendorSelect">
												<input type="hidden" id="selectVendor" name="vendor_name"
													style="width: 100%;" value="0"
													placeholder="Please Select Vendor Name" /> <label
													class="error" id="errorVendorSelect"> </label>
											</div>
										</div>

										<div class="row1" id="grpfuelLiter" class="form-group">
											<label class="L-size control-label" for="fuel_liters">Liter
												:<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input class="form-text" id="fuel_liters" name="fuel_liters"
													type="text" maxlength="8" min="0"
													onkeypress="return isNumberKey(event,this);"
													ondrop="return false;"> <span id="fuelLiterIcon"
													class=""></span>
												<div id="fuelLiterErrorMsg" class="text-danger"></div>
												<label class="error" id="errorLiter" style="display: none"></label>
												<p class="help-block">ex: 23.78</p>

											</div>
										</div>
										<div class="row1" id="grpfuelPrice" class="form-group">
											<label class="L-size control-label" for="fuel_price">Price/Unit
												:<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input class="form-text" id="fuel_price" name="fuel_price"
													type="text" maxlength="8" min="0"
													onkeypress="return isNumberKey(event,this);"
													ondrop="return false;"> <span id="fuelLiterIcon"
													class=""></span>
												<div id="fuelLiterErrorMsg" class="text-danger"></div>
												<label class="error" id="errorPrice" style="display: none"></label>
												<p class="help-block">ex: 56.78</p>
											</div>
										</div>


										<div class="row1" id="ShowOtherPayOption">
											<label class="L-size control-label" for="payMethod">Payment
												Method :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="">
													<div class="btn-group" id="status" data-toggle="buttons">
														<label class="btn btn-default btn-on btn-lg active">
															<input type="radio" value="1" name="paymentTypeId"
															checked="checked">Cash
														</label> <label class="btn btn-default btn-off btn-lg"> <input
															type="radio" value="2" name="paymentTypeId">Credit
														</label>
													</div>
												</div>


											</div>

										</div>
										<div class="row1" id="PayModeOption">
											<label class="L-size control-label" for="payMethod">Payment
												Method :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="">
													<div class="btn-group" id="status" data-toggle="buttons">
														<label class="btn btn-default btn-on btn-lg active">
															<input type="radio" value="1" name="paymentTypeId"
															checked="checked">Cash
														</label>
													</div>
												</div>

											</div>
										</div>

										<div class="row1" id="grpfuelReference" class="form-group">
											<label class="L-size control-label" for="fuel_reference">Reference
												:<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input class="form-text" id="fuel_reference" maxlength="50"
													name="fuel_reference" type="text"
													onkeypress="return IsReference(event);"
													ondrop="return false;"> <span
													id="fuelReferenceIcon" class=""></span>
												<div id="fuelReferenceErrorMsg" class="text-danger"></div>
												<label class="error" id="errorReference"
													style="display: none"> </label>
												<p class="help-block">Optional (e.g. invoice number,
													transaction ID, etc.)</p>

											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Driver Name :</label>
											<div class="I-size" id="driverSelect">
												<input type="hidden" id="VehicleTODriverFuel"
													name="driver_id" style="width: 100%;" required="required"
													placeholder="Please Enter 3 or more Driver Name" />
											</div>
										</div>
										<c:if test="${configuration.showDriver2}">
										<div class="row1">
											<label class="L-size control-label">Driver2 Name :</label>
											<div class="I-size" id="driverSelect">
												<input type="hidden" id="VehicleTODriver2Fuel"
													name="secDriverID" style="width: 100%;" required="required"
													placeholder="Please Enter 3 or more Driver Name" />
											</div>
										</div>
										</c:if>
										<c:if test="${configuration.showCleaner}">
										<div class="row1">
											<label class="L-size control-label">Cleaner :</label>
											<div class="I-size" id="driverSelect">
												<input type="hidden" id="VehicleTOCleanerFuel"
													name="cleanerID" style="width: 100%;" required="required"
													placeholder="Please Enter 3 or more Driver Name" />
											</div>
										</div>
										</c:if>
										<c:if test="${configuration.showRoute}">
										<div class="row1">
											<label class="L-size  control-label">Route Service :</label>
											<div class="I-size" id="driverSelect">
												<input type="hidden" id="TripRouteList" name="routeID"
													style="width: 100%;" value="0" required="required"
													placeholder="Please Enter 3 or more Route Name, NO " />
											</div>
										</div>
										</c:if>

										<div class="form-group boolean optional fuel_personal" id="grpfuelPersonalCheck">
											<div class="col-sm-offset-4 col-sm-10 col-md-8">

												<div class="help-block checkbox">
													<label for="fuel_entry_meter_entry_attributes_void">
														<input name="fuel_personal" type="hidden" value="0">
														<input id="fuel_personal" name="fuel_personal"
														type="checkbox" value="1"> Mark as <span
														class="label label-warning">personal</span> expense

													</label>
												</div>
											</div>
										</div>
									</div>
								</div>
							</fieldset>
							<fieldset>
								<legend>Fuel Economy Calculation Settings</legend>
								<div class="box">
									<div class="box-body">
										<div class="row1">
											<div class="form-group sf_switch optional fuel_entry_partial">
												<label class="L-size control-label" for="fuel_partial">Fuel
													Tank :</label>
												<div class="I-size">
													<div class="">
														<div class="btn-group" id="status" data-toggle="buttons">
															<label class="btn btn-default btn-on btn-lg active">
																<input type="radio" value="0" name="fuel_tank"
																checked="checked">Full
															</label> <label class="btn btn-default btn-off btn-lg"> <input
																type="radio" value="1" name="fuel_tank">Partial
															</label>
														</div>
													</div>

													<p class="help-block">
														Turn On <b>"Partial"</b> if the tank is <strong>not
															filled up to "full"</strong>.
													</p>
												</div>
											</div>
										</div>
									</div>
								</div>
							</fieldset>
							<fieldset id="grpfuelDocument">
								<legend>Document</legend>
								<div class="box">
									<div class="box-body">
										<div class="row1">
											<label class="L-size control-label" for="fuel_partial">Fuel
												Document :</label>
											<div class="I-size">
												<input type="file" name="input-file-preview"
													id="renewalFile" /> <span id="renewalFileIcon" class=""></span>
												<div id="renewalFileErrorMsg" class="text-danger"></div>
												<span class="help-block">Add an optional document</span>
											</div>
										</div>
									</div>
								</div>
							</fieldset>
							<fieldset id="grpfuelComment">
								<legend>Comment</legend>
								<div class="box">
									<div class="box-body">
										<div class="col-sm-offset-2">

											<div class="row1">
												<div class="I-size">
													<textarea class="form-text" id="fuel_comments"
														name="fuel_comments" rows="3" maxlength="240">	
												</textarea>
													<label class="error" id="errorComment"
														style="display: none"> </label> <span class="help-block">Add
														an optional comment</span>
												</div>
											</div>
										</div>
									</div>
								</div>
							</fieldset>

							<div class="panel-footer">
								<div class="L-size"></div>
								<div class="I-size">
									<div class="col-sm-offset-4 I-size">
										<button type="submit" onclick="return validateMaxOdameter();" class="btn btn-success">Create
											Fuel Entry</button>
										<a class="btn btn-default" href="<c:url value="/Fuel/1.in"/>">Cancel</a>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</sec:authorize>
	</section>
	<c:url var="findVehicleURL" value="getFuelVehicleList.in" />
	<c:url var="findDriverURL" value="getFuelDriverList.in" />
	<c:url var="findFuelVendorURL" value="getFuelVendorList.in" />
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/FU/FuelNewlanguage.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/FU/FuelEnter.validate.js" />"></script>
	<script type="text/javascript">
		$(document)
				.ready(
						function() {
									$("#fuel_type")
											.select2(
													{
														placeholder : "Please Enter Vehicle Name Search"
													}), $("#tagPicker")
											.select2({
												closeOnSelect : !1
											})

							$("#selectVendor, #fuel_type, #fuelDate")
									.change(
											function() {
												$
														.getJSON(
																"getFixedFuelPrice.in",
																{
																	Vendor_id : $(
																			'#selectVendor')
																			.val(),
																	fuel_ID : $(
																			'#fuel_type')
																			.val(),
																	fuel_date : $(
																			'#fuelDate')
																			.val(),
																	ajax : "true"
																},
																function(e) {
																	var t = "", o = "", l = 0;
																			t = e.VFFID,
																			o = e.FUEL_PERDAY_COST;

																	if (o != null
																			&& o > 0) {
																				document
																						.getElementById("fuel_price").placeholder = o,
																				document
																						.getElementById("fuel_price").value = o,
																				document
																						.getElementById("fuel_price").max = o;
																		$(
																				"#fuel_price")
																				.prop(
																						"readonly",
																						true);
																	} else {
																				document
																						.getElementById("fuel_price").placeholder = 0,
																				document
																						.getElementById("fuel_price").value = 0,
																				document
																						.getElementById("fuel_price").max = 100;
																		$(
																				"#fuel_price")
																				.prop(
																						"readonly",
																						false);
																	}
																})
											});
									if(!(${showFuelTypeCol})) {
										$("#grpfuelType").addClass("hide");
									}

									if(!(${showReferenceCol})) {
										$("#grpfuelReference").addClass("hide");
										showReferenceCol = ${showReferenceCol};
									}

									if(!(${showPersonalExpenceCheck})) {
										$("#grpfuelPersonalCheck").addClass("hide");
									}

									if(!(${showFuelDocumentSelection})) {
										$("#grpfuelDocument").addClass("hide");
									}
									
									if(!(${showFuelCommentField})) {
										$("#grpfuelComment").addClass("hide");
									}
									
									applyGpsSettings('${gpsConfiguration}');
						});
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>