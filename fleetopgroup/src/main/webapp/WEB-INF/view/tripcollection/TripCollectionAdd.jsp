<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<div class="content-wrapper" onload="javascript:loadTripSheet();">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/newTripCol.in"/>">Trip Collection</a> / <span>Add
						Trip Collection</span>
				</div>
				<div class="pull-right">
					<a href="newTripCol.in">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<div class="col-md-offset-1 col-md-9 col-sm-9 col-xs-12">
				<c:if test="${param.success eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This TripCollection Created Successfully.
					</div>
				</c:if>
				<c:if test="${param.danger eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This TripCollection Already Exists
					</div>
				</c:if>
				<c:if test="${param.alreadyClose eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This TripCollection Date ${closeDate} Already Closed.
					</div>
				</c:if>
				<c:if test="${param.already eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This TripCollection Date ${closeDate} Already Created.
					</div>
				</c:if>
				<c:if test="${param.sequenceNotFound eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						Sequence Not Found Please Contact To System AdminisTrator !
					</div>
				</c:if>
				<sec:authorize access="!hasAuthority('ADD_TRIPSHEET')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('ADD_TRIPSHEET')">
					<form id="formTripCollection"
						action="<c:url value="/saveTripCol.in"/>" method="post"
						enctype="multipart/form-data" name="formTripCollection"
						role="form" class="form-horizontal">
						<div class="form-horizontal">
							<fieldset>
								<legend>Trip Collection Details</legend>
								<div class="box">
									<div class="box-body">
										<div class="row1" id="grpvehicleName" class="form-group">
											<label class="L-size control-label" for="TripSelectVehicle">Vehicle
												: <abbr title="required">*</abbr>
											</label>
											<div class="I-size" id="vehicleSelect">
												<input type="hidden" id="TripSelectVehicle" name="VID"
													style="width: 100%;"
													placeholder="Please Enter 2 or more Vehicle Name" /><span
													id="vehicleNameIcon" class=""></span>
												<div id="vehicleNameErrorMsg" class="text-danger"></div>
												<label id="errorVehicle" class="error"></label>
											</div>
										</div>
										<div class="row1" id="grpvehicleGroup" class="form-group">
											<label class="L-size control-label" for="vehicle_Group">Group
												Service :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input class="form-text" name="VEHICLE_GROUP"
													id="vehicle_Group" placeholder="" type="text"
													maxlength="50" readonly="readonly"
													onkeypress="return IsTripSheetPanNO(event);"
													ondrop="return false;"><span id="vehicleGroupIcon"
													class=""></span>
												<div id="vehicleGroupErrorMsg" class="text-danger"></div>
												<label class="error" id="errorTripSheetPanNO"
													style="display: none"> </label>
											</div>
											<input id="VEHICLE_GROUP_ID" name="VEHICLE_GROUP_NAME" type="hidden"/>
										</div>
										<div class="row1" id="grpdriverName" class="form-group">
											<label class="L-size control-label" for="driverList">Driver
												: <abbr title="required">*</abbr>
											</label>
											<div class="I-size" id="driverSelect">
												<input type="hidden" id="driverList" name="TRIP_DRIVER_ID"
													style="width: 100%;"
													placeholder="Please Enter 3 or more Driver Name, No" /><span
													id="driverNameIcon" class=""></span>
												<div id="driverNameErrorMsg" class="text-danger"></div>
												<label id="errorDriver1" class="error"></label>
											</div>
										</div>
										<div class="row1" id="grpconductorName" class="form-group">
											<label class="L-size control-label" for="ConductorList">Conductor:
												<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="hidden" id="ConductorList"
													name="TRIP_CONDUCTOR_ID" style="width: 100%;"
													placeholder="Please Enter 3 or more Conductor Name, NO" />
												<span id="conductorNameIcon" class=""></span>
												<div id="conductorNameErrorMsg" class="text-danger"></div>
												<label id="errorDriver2" class="error"></label>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="issue_vehicle_id">Cleaner
												:</label>
											<div class="I-size" id="cleanerSelect">
												<input type="hidden" id="Cleaner" name="TRIP_CLEANER_ID"
													style="width: 100%;" value="0"
													placeholder="Please Enter 3 or more Cleaner Name, No" /> <label
													id="errorCleaner" class="error"></label>
											</div>
										</div>
									</div>
								</div>
							</fieldset>
							<fieldset>
								<legend>Route</legend>
								<div class="box">
									<div class="box-body">
										<div class="row1" id="grptripRouteName" class="form-group">

											<label class="L-size  control-label" for="TripRouteList">Route
												Service :<abbr title="required">*</abbr>
											</label>
											<div class="I-size" id="routeSelect">

												<input type="hidden" id="TripRouteList" name="TRIP_ROUTE_ID"
													style="width: 100%;"
													placeholder="Please Enter 3 or more Route Name, NO " /> <span
													id="tripRouteNameIcon" class=""></span>
												<div id="tripRouteNameErrorMsg" class="text-danger"></div>
												<label id="errorRoute" class="error"></label>
											</div>
										</div>
										<div class="row1" id="grptripDate" class="form-group">
											<label class="L-size control-label" for="tripDate">Date
												Of Journey : <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="input-group input-append date"
													id="TripStartDate">
													<input type="text" class="form-text" name="TRIP_OPEN_DATE"
														required="required" id="tripDate"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
												<span id="tripDateIcon" class=""></span>
												<div id="tripDateErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Opening KM : </label>
											<div class="I-size">
												<input class="form-text" name="TRIP_OPEN_KM"
													id="tripOpeningKM" placeholder="" type="text"
													maxlength="50" onkeypress="return tripOpening(event);"
													ondrop="return false;"> <label class="error"
													id="errortripOpening" style="display: none"> </label> <label
													id="errorOpening" class="error"></label>
											</div>
										</div>
										<div class="row1" id="grptripliter" class="form-group">
											<label class="L-size control-label" for="tripliter">Diesel
												Liter :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input class="form-text" name="TRIP_DIESEL_LITER"
													id="tripliter" placeholder="" type="text" maxlength="50"
													onkeypress="return tripOpening(event);"
													ondrop="return false;"><span id="tripliterIcon"
													class=""></span>
												<div id="tripliterErrorMsg" class="text-danger"></div>
												<label class="error" id="errortripOpening"
													style="display: none"> </label> <label id="errorOpening"
													class="error"></label>
											</div>
										</div>
										<div class="row1" id="grptripSingl" class="form-group">
											<label class="L-size control-label" for="tripSingl">No
												Of Running Singl :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input class="form-text" name="TRIP_SINGL" id="tripSingl"
													placeholder="" type="text" maxlength="50"
													onkeypress="return tripOpening(event);"
													ondrop="return false;"><span id="tripSinglIcon"
													class=""></span>
												<div id="tripSinglErrorMsg" class="text-danger"></div>
												<label class="error" id="errortripOpening"
													style="display: none"> </label> <label id="errorOpening"
													class="error"></label>
											</div>
										</div>


										<div class="row1">
											<label class="L-size control-label">Remarks :</label>
											<div class="I-size">
												<textarea class="form-text" id="fuel_comments"
													name="TRIP_REMARKS" rows="3" maxlength="250"
													onkeypress="return IsAdvanceRemarks(event);"
													ondrop="return false;">
														
													</textarea>
												<label class="error" id="errorAdvanceRemarks"
													style="display: none"> </label>
											</div>
										</div>

										<input type="hidden" value="" name="tripStutes"
											id="tripStutes">

									</div>
								</div>
							</fieldset>

							<fieldset class="form-actions">
								<div class="row1">
									<div class="pull-right">
										<button type="submit" class="btn btn-success">Create
											Trip Collection</button>
										<a class="btn btn-default" href="newTripCol.in">Cancel</a>
									</div>
								</div>
							</fieldset>
						</div>
					</form>
				</sec:authorize>
			</div>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripCollection.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepicker.js" />"></script>
	<script>
		$(function() {
			$('#reservationToTripSheet').daterangepicker();
		});
		$(document)
				.ready(
						function() {

							var g_UnFocusElementStyle = "";
							var g_FocusBackColor = "#FFC";
							var g_reEmail = /^[\w\.=-]+\@[\w\.-]+.[a-z]{2,4}$/;
							var g_reCell = /^\d{10}$/;
							var g_invalidFields = 0;

							function initFormElements(sValidElems) {

								var inputElems = document
										.getElementsByTagName('textarea');
								for (var i = 0; i < inputElems.length; i++) {
									com_satheesh.EVENTS.addEventHandler(
											inputElems[i], 'focus',
											highlightFormElement, false);
									com_satheesh.EVENTS.addEventHandler(
											inputElems[i], 'blur',
											unHightlightFormElement, false);
								}
								/* Add the code for the input elements */
								inputElems = document
										.getElementsByTagName('input');
								for (var i = 0; i < inputElems.length; i++) {
									if (sValidElems.indexOf(inputElems[i]
											.getAttribute('type') != -1)) {
										com_satheesh.EVENTS.addEventHandler(
												inputElems[i], 'focus',
												highlightFormElement, false);
										com_satheesh.EVENTS.addEventHandler(
												inputElems[i], 'blur',
												unHightlightFormElement, false);
									}
								}

								/* submit handler */
								com_satheesh.EVENTS.addEventHandler(document
										.getElementById('formWorkOrder'),
										'submit', validateAllfields, false);

								com_satheesh.EVENTS.addEventHandler(document
										.getElementById('formEditWorkOrder'),
										'submit', validateEditAllfields, false);

								/* Add the default focus handler */
								document.getElementsByTagName('input')[0]
										.focus();

								/* Add the event handlers for validation */
								com_satheesh.EVENTS.addEventHandler(
										document.forms[0].select3, 'blur',
										validateVehicleName, false);
								com_satheesh.EVENTS.addEventHandler(
										document.forms[0].subscribe, 'blur',
										validateWoAssigned, false);
								com_satheesh.EVENTS.addEventHandler(
										document.forms[0].woStartDate, 'blur',
										validateWoStartDate, false);

								com_satheesh.EVENTS.addEventHandler(
										document.forms[0].woEndDate, 'blur',
										validateWoEndDate, false);
								com_satheesh.EVENTS.addEventHandler(
										document.forms[0].vehicle_Odometer,
										'blur', validateWoOdometer, false);
								com_satheesh.EVENTS.addEventHandler(
										document.forms[0].from, 'blur',
										validateWoJobType, false);
								com_satheesh.EVENTS.addEventHandler(
										document.forms[0].to, 'blur',
										validateWoJobSubType, false);

							}

							function highlightFormElement(evt) {
								var elem = com_satheesh.EVENTS
										.getEventTarget(evt);
								if (elem != null) {
									elem.style.backgroundColor = g_FocusBackColor;
								}
							}

							function unHightlightFormElement(evt) {
								var elem = com_satheesh.EVENTS
										.getEventTarget(evt);
								if (elem != null) {
									elem.style.backgroundColor = "";
								}
							}

							function validateVehicleName() {
								var formField = document
										.getElementById('select3');
								var ok = (formField.value != null && formField.value.length != 0);
								var grpEle = document
										.getElementById('grpvehicleName');
								if (grpEle != null) {
									if (ok) {
										grpEle.className = "form-group has-success has-feedback";
										document
												.getElementById('vehicleNameIcon').className = "fa fa-check form-text-feedback";
										document
												.getElementById('vehicleNameErrorMsg').innerHTML = "";
									} else {
										grpEle.className = "form-group has-error has-feedback";
										document
												.getElementById('vehicleNameIcon').className = "fa fa-remove form-text-feedback";
										document
												.getElementById('vehicleNameErrorMsg').innerHTML = "Please select vehicle name";
									}
									return ok;
								}
							}

							function validateWoAssigned() {
								var formField = document
										.getElementById('subscribe');
								var ok = (formField.value != null && formField.value.length != 0);
								var grpEle = document
										.getElementById('grpwoAssigned');
								if (grpEle != null) {
									if (ok) {
										grpEle.className = "form-group has-success has-feedback";
										document
												.getElementById('woAssignedIcon').className = "fa fa-check form-text-feedback";
										document
												.getElementById('woAssignedErrorMsg').innerHTML = "";
									} else {
										grpEle.className = "form-group has-error has-feedback";
										document
												.getElementById('woAssignedIcon').className = "fa fa-remove form-text-feedback";
										document
												.getElementById('woAssignedErrorMsg').innerHTML = "Please select assigned to user";
									}
									return ok;
								}
							}

							function validateWoStartDate() {
								var formField = document
										.getElementById('woStartDate');
								var ok = (formField.value != null && formField.value.length != 0);
								var grpEle = document
										.getElementById('grpwoStartDate');
								if (grpEle != null) {
									if (ok) {
										grpEle.className = "form-group has-success has-feedback";
										document
												.getElementById('woStartDateIcon').className = "fa fa-check form-text-feedback";
										document
												.getElementById('woStartDateErrorMsg').innerHTML = "";
									} else {
										grpEle.className = "form-group has-error has-feedback";
										document
												.getElementById('woStartDateIcon').className = "fa fa-remove form-text-feedback";
										document
												.getElementById('woStartDateErrorMsg').innerHTML = "Please select start date";
									}
									return ok;
								}
							}

							function validateWoEndDate() {
								var formField = document
										.getElementById('woEndDate');
								var ok = (formField.value != null && formField.value.length != 0);
								var grpEle = document
										.getElementById('grpwoEndDate');
								if (grpEle != null) {
									if (ok) {
										grpEle.className = "form-group has-success has-feedback";
										document
												.getElementById('woEndDateIcon').className = "fa fa-check  form-text-feedback";
										document
												.getElementById('woEndDateErrorMsg').innerHTML = "";
									} else {
										grpEle.className = "form-group has-error has-feedback";
										document
												.getElementById('woEndDateIcon').className = "fa fa-remove form-text-feedback";
										document
												.getElementById('woEndDateErrorMsg').innerHTML = "Please select end date";
									}
								}
								return ok;
							}
							function validateWoOdometer() {
								var formField = document
										.getElementById('vehicle_Odometer');
								var ok = (formField.value != null && formField.value.length != 0);
								var grpEle = document
										.getElementById('grpwoOdometer');
								if (grpEle != null) {
									if (ok) {
										grpEle.className = "form-group has-success has-feedback";
										document
												.getElementById('woOdometerIcon').className = "fa fa-check  form-text-feedback";
										document
												.getElementById('woOdometerErrorMsg').innerHTML = "";
									} else {
										grpEle.className = "form-group has-error has-feedback";
										document
												.getElementById('woOdometerIcon').className = "fa fa-remove form-text-feedback";
										document
												.getElementById('woOdometerErrorMsg').innerHTML = "Please enter odometer";
									}
								}
								return ok;
							}

							function validateWoJobType() {
								var formField = document.getElementById('from');
								var ok = (formField.value != null && formField.value.length != 0);
								var grpEle = document
										.getElementById('grpwoJobType');
								if (grpEle != null) {
									if (ok) {
										grpEle.className = "form-group has-success has-feedback";
										document
												.getElementById('woJobTypeIcon').className = "fa fa-check  form-text-feedback";
										document
												.getElementById('woJobTypeErrorMsg').innerHTML = "";
									} else {
										grpEle.className = "form-group has-error has-feedback";
										document
												.getElementById('woJobTypeIcon').className = "fa fa-remove form-text-feedback";
										document
												.getElementById('woJobTypeErrorMsg').innerHTML = "Please select job type";
									}
								}
								return ok;
							}

							function validateWoJobSubType() {
								var formField = document.getElementById('to');
								var ok = (formField.value != null && formField.value.length != 0);
								var grpEle = document
										.getElementById('grpwoJobSubType');
								if (grpEle != null) {
									if (ok) {
										grpEle.className = "form-group has-success has-feedback";
										document
												.getElementById('woJobSubTypeIcon').className = "fa fa-check  form-text-feedback";
										document
												.getElementById('woJobSubTypeErrorMsg').innerHTML = "";
									} else {
										grpEle.className = "form-group has-error has-feedback";
										document
												.getElementById('woJobSubTypeIcon').className = "fa fa-remove form-text-feedback";
										document
												.getElementById('woJobSubTypeErrorMsg').innerHTML = "Please select job sub type";
									}
								}
								return ok;
							}

							function validateAllfields(e) {
								/* Need to do it this way to make sure all the functions execute */
								var bOK = validateVehicleName();
								bOK &= validateWoAssigned();
								bOK &= validateWoStartDate();
								bOK &= validateWoEndDate();
								bOK &= validateWoOdometer();
								bOK &= validateWoJobType();
								bOK &= validateWoJobSubType();

								if (!bOK) {
									// alert("The fields that are marked bold and red are
									// required. Please supply valid\n values for these fields
									// before sending.");
									com_satheesh.EVENTS.preventDefault(e);
								}

							}

							function validateEditAllfields(e) {
								/* Need to do it this way to make sure all the functions execute */
								var bOK = validateVehicleName();
								bOK &= validateWoAssigned();
								bOK &= validateWoOdometer();

								if (!bOK) {
									com_satheesh.EVENTS.preventDefault(e);
								}

							}

							com_satheesh.EVENTS.addEventHandler(window, "load",
									function() {
										initFormElements("text");
									}, false);
						});
		/* $('#TripSelectVehicle').on('change', function() {
			$('#VEHICLE_GROUP_ID').val($("#vehicleGroupId option:selected").text().trim());
		}); */
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>