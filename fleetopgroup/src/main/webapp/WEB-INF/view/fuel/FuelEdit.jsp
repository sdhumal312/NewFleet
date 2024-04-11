<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/bootstrap-clockpicker.min.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left" id="normalEdit">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/Fuel/1.in"/>">Fuel Entry</a> / Edit Fuel
					Entry
				</div>
				<div class="pull-left" id="vehicleEdit" style="display: none;">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/vehicle/1/1"/>">Vehicle</a> / <a
						href="<c:url value="showVehicle.in?vid=${fuel.vid}"/>"><c:out
							value="${fuel.vehicle_registration}" /></a> / <a
						href="<c:url value="VehicleFuelDetails/1.in?vid=${fuel.vid}"/>">
						Vehicle Fuel Entries</a> / <span>Edit Fuel Entries</span>
				</div>
				<div class="pull-right">
					<a href="showFuel.in?FID=${fuel.fuel_id}">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<c:if test="${param.success eq true}">
		<div class="alert alert-success">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This Fuel Entries was successfully created.
		</div>
	</c:if>
	<c:if test="${param.danger eq true}">
		<div class="alert alert-danger">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This Fuel Entries was Already created.
		</div>
	</c:if>
	<section class="content">
		<sec:authorize access="!hasAuthority('EDIT_FUEL')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('EDIT_FUEL')">
			<div class="row">
				<div class="col-md-offset-1 col-md-9">
				
					<div class="alert alert-info hide" id="showBackDateMessage" >
					<button class="close" data-dismiss="alert" type="button">x</button>
						<label id="buttonMessageBackDate"  class="col-md-offset-1">
						</label> 
					</div>
				
					<form id="formEditFuel" action="<c:url value="/updateFuel.in"/>"
						method="post" enctype="multipart/form-data" name="formEditFuel"
						role="form" class="form-horizontal"
						>

						<input name="fuel_id" type="hidden" value="${fuel.fuel_id}">
						<input name="fuel_Number" id="fuel_Number" type="hidden" value="${fuel.fuel_Number}">
						<input name="previousFuelAmount" type="hidden" value="${fuel.fuel_amount}">
						<input name="previousFuelDate" type="hidden" id="fuel_D_date" value="${fuel.fuel_date}">
						<input name="fuel_D_date" type="hidden" id="fuel_D_date" value="${fuel.fuel_D_date}">
						<input type="hidden" id="editvid" value="<%= request.getParameter("vid")%>">
						<input type="hidden" id="allowGPSIntegration" value="${gpsConfiguration.allowGPSIntegration}">
						<input type="hidden" id="companyId" value="${companyId}">
						<input type="hidden" name="creatingBackDateFuel" id="creatingBackDateFuel" value="false">
						<input type="hidden" id="nextFuelIdOfBackDate" value="0">
						<input type="hidden" id="nextFuelMeterOfBackDate" value="0">
						<input type="hidden" id="actualOdameterReading" value="0">
						<input type="hidden" id="validateVehicleKMPL" value="false">
						<input type="hidden" id="noOfDaysForBackDate" value="${configuration.noOfDaysForBackDate}">
						<input type="hidden" id="backDateString" value="${minBackDate}">
						<input type="hidden" id="fuelTankCapacity" value="0">
						<input type="hidden" id="previousFuelEntryCapacity" value="0">
						<input type="hidden" name="nextFuelEntryFound" id="nextFuelEntryFound" value="${nextFuelEntryFound}">
						<input type="hidden" name="nextFuelEntryFuelId" id="nextFuelEntryFuelId" value="${nextFuelEntry.fuel_id}">
						<input type="hidden" id="nextFuelLowerLimit" value="${nextFuelEntry.lastFuelOdometer}">
						<input type="hidden" id="nextFuelUpperLimit" value="${nextFuelEntry.fuelMeter}">
						<input type="hidden" id="validateLastFuelEntryInEdit" value="true">
						<input type="hidden" id="defaultFuelPrice" value="${defaultFuelPrice}">
						<input type="hidden" id="allowToEditFuelRef" value="${configuration.allowToEditFuelRef}">
						<input type="hidden" id="validateDriver1" value="${configuration.validateDriver1}">
						<input type="hidden" id="validateReference" value="${configuration.validateReference}">
						
						<div class="form-horizontal">
							<fieldset>
								<legend>Fuel Vehicle</legend>
								<div class="box">
									<div class="box-body">
										<div class="row1" id="grpvehicleNumber" class="form-group">
											<label class="L-size control-label" for="FuelSelectVehicle">Vehicle<abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input type="hidden" name="vid" id="vid" value="${fuel.vid}">
												<input type="hidden" name="fuel_document" value="${fuel.fuel_document}">
												<input type="hidden" name="fuel_document_id" value="${fuel.fuel_document_id}">
												<input type="text" name="vehicle_registration"
													value="${fuel.vehicle_registration}" class="form-text"
													readonly="readonly"> <span id="vehicleNumberIcon"
													class=""></span>
												<div id="vehicleNumberErrorMsg" class="text-danger"></div>
												<input type="hidden" name="vehicle_OwnershipId"
													value="${fuel.vehicle_OwnershipId}">
												<input type="hidden" name="createdById"
													value="${fuel.createdById}">
											</div>
										</div>

										<div class="row1">
											<label class="L-size control-label">Vehicle Group :</label>
											<div class="I-size">

												<input class="form-text" id="vehicle_group"
													name="vehicle_group" type="text"
													value="${fuel.vehicle_group}" readonly="readonly">
												<input class="form-text" id="vehicleGroupId"
													name="vehicleGroupId" type="text"
													value="${fuel.vehicleGroupId}" readonly="readonly" style="display: none;">

											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">TripSheet ID :</label>
											<div class="I-size">
												<div class="input-group">
													<span class="input-group-addon"> <span
														aria-hidden="true">TS-</span></span> <input type="text"
														class="form-text" name="fuel_TripsheetNumber"
														value="${fuel.fuel_TripsheetNumber}" placeholder="eg: 160"
														maxlength="8" id="pcName"
														onkeypress="return RouteKM(event);" ondrop="return false;"
														required="required" />

												</div>
												<input type="hidden" id="fuel_TripsheetID" name="fuel_TripsheetID" value="${fuel.fuel_TripsheetID}"/>
											</div>
										</div>

										<div class="row1" id="grpfuelDate" class="form-group">
											<label class="L-size control-label" for="fuelDate">
												Date <abbr title="required">*</abbr> </label>
											<div class="col-md-3">
												<div class="input-group input-append date" id="StartDate">
													<input type="text" class="form-text" name="fuel_date" onchange="clearTime();"
														id="fuelDate" data-inputmask="'alias': 'dd-mm-yyyy'" value="${fuel.fuel_date}"
														data-mask="" /> <span class="input-group-addon add-on">
														<span class="fa fa-calendar"></span>
													</span>
												</div>
											</div>
										
												<div class="L-size">
													<label class="L-size control-label" for="fuelDate">Time
														<abbr title="required">*</abbr>
													</label>
														<div class="input-group clockpicker">
															<input type="text" class="form-text" readonly="readonly"
																name="fuelTime" id="fuelTime" value="${fuel.fuelTime}" required="required" onchange="getGPSAndActiveTripData(false);"> <span
																class="input-group-addon"> <span
																class="fa fa-clock-o" aria-hidden="true"></span>
															</span>
														</div>
												</div>
										</div>
										
										<div class="row1" class="form-group">
											<label class="L-size control-label" for="fuel_type">Open
												Odometer :
											</label>
											<div class="I-size">
												<input type="text" class="form-text" id="fuel_meter_old" 
												name="fuel_meter_old" readonly="readonly" value="${fuel.fuel_meter_old}">
											</div>
										</div>
										
										
										<div class="row1" id="grpfuelOdometer" class="form-group">
											<label class="L-size control-label" for="fuel_meter">Odometer<abbr
												title="required">*</abbr></label>
											<div class="I-size">
													
													<input	class="form-text" id="fuel_meter" name="fuel_meter"
													placeholder="${fuel.fuel_meter_old}" type="text"
													value="${fuel.fuel_meter}" maxlength="10"
													onkeypress="return IsNumericOdometer(event);"
													ondrop="return false;"> <span id="fuelOdometerIcon"
													class=""></span>
													<input type="hidden" id="validateOdometerInFuel" value="${validateOdometerInFuel}">
													<input type="hidden" id="vehicle_ExpectedOdameter" value="${vehicle.vehicleExpectedKm}" />
													<input type="hidden" id="vehicle_Odometer" value="${vehicle.vehicle_Odometer}" />
												<div id="fuelOdometerErrorMsg" class="text-danger"></div>
												<label class="error" id="errorOdometer"
													style="display: none"></label> <label class="error"
													id="errorOdo"></label>
												
												<div class="I-size" id="backDateRange" style="display: none;">
													 <label id="odometerRangeBackDate" class="error" class="col-md-offset-1">
														</label> 
												</div>		
													
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
										<c:if test="${fuel.gpsOdometer != null && fuel.gpsOdometer > 0}">
											<div class="row1" id="gpsOdometerRow" class="form-group">
											<label class="L-size control-label" for="fuel_type">GPS
												Odometer :
											</label>
											<div class="I-size">
												<input type="text" class="form-text" id="gpsOdometer" name="gpsOdometer" value="${fuel.gpsOdometer}" readonly="readonly">
											</div>
										</div>
										</c:if>
										
										<div class="row1" id="grpfuelType" class="form-group">
											<label class="L-size control-label" for="fuel_type">Fuel
												Type</label>
											<div class="I-size">

												<input class="form-text" id="fuel_type" name="fuel_type"
													type="text" value="${fuel.fuel_type}" readonly="readonly">
												<span id="fuelTypeIcon" class=""></span>
												<div id="fuelTypeErrorMsg" class="text-danger"></div>

											</div>
											<input name="fuelTypeId" id="fuelTypeId" value="${fuel.fuelTypeId}" type="hidden" />
										</div>
										<div class="row1" id="grpfuelLiter" class="form-group">
											<label class="L-size control-label" for="fuel_liters">Liter<abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input class="form-text" id="fuel_liters" name="fuel_liters"
													type="text" value="${fuel.fuel_liters}" maxlength="8"
													min="0" onkeypress="return isNumberKey(event,this);"
													ondrop="return false;"> <span id="fuelLiterIcon"
													class=""></span>
												<div id="fuelLiterErrorMsg" class="text-danger"></div>
												<label class="error" id="errorLiter" style="display: none"></label>
												<p class="help-block">(e.g. 16.796)</p>
											</div>
										</div>
										<div class="row" id="grpfuelPrice" class="form-group">
											<label class="L-size control-label" for="fuel_price">Price/Unit<abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input class="form-text" id="fuel_price" name="fuel_price"
													type="text" value="${fuel.fuel_price}" maxlength="8"
													min="0" onkeypress="return isNumberKey(event,this);"
													ondrop="return false;"> <span id="fuelLiterIcon"
													class=""></span>
												<div id="fuelLiterErrorMsg" class="text-danger"></div>
												<label class="error" id="errorPrice" style="display: none"></label>
												<p class="help-block">Optional(e.g. 56.00)</p>
											</div>
										</div>

										<div class="row1">
											<label class="L-size control-label">Vendor :<abbr
												title="required">*</abbr></label>
											<div class="I-size" id="vendorSelect">
												<!-- <div class="col-md-9"> -->

													<input type="hidden" value="${fuel.fuel_payment}"
														id="fuel_payment"> <input type="hidden"
														value="${fuel.vendor_id}" id="Ovid"> <input
														type="hidden" id="selectVendor" name="Vendor_id"
														style="width: 100%;" required="required"
														placeholder="Please Select Vendor Name" /> <label
														class="error" id="errorVendorSelect"> </label>
												<!-- </div>
												<div class="col-md-1">
													<a class=" btn btn-link "
														onclick="visibility('vendorEnter', 'vendorSelect');">
														<i class="fa fa-plus"> New</i>
													</a>
												</div> -->

											</div>
											<div id="vendorEnter" class="contact_Hide">

												<div class="I-size">
													<div class="col-md-9">
														<input class="form-text row1" name="Vendor_name"
															maxlength="25" type="text" value="${fuel.vendor_name}"
															placeholder="Enter Fuel Vendor Name" id="enterVendorName"
															onkeypress="return IsVendorName(event);"
															ondrop="return false;"> <label class="error"
															id="errorVendorName" style="display: none"> </label> <input
															class="form-text row1" name="Vendor_location"
															id="enterVendorLocation" maxlength="25" type="text"
															value="${fuel.vendor_location}"
															placeholder="Enter Fuel Vendor Location"
															onkeypress="return IsVendorLocation(event);"
															ondrop="return false;"> <label class="error"
															id="errorVendorLocation" style="display: none"> </label>
													</div>
													<div class="col-md-1">
														<a class=" btn btn-link col-sm-offset-1"
															onclick="visibility('vendorEnter', 'vendorSelect');">
															<i class="fa fa-minus"> Select</i>
														</a>
													</div>
												</div>
											</div>
										</div>
										<input type="hidden" id="IsAutoVendor" value="${fuel.autoVendor}">
										<div class="row1" id="ShowOtherPayOptionEdit">
											<label class="L-size control-label" for="payMethod">Payment
												Method :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="">
													<div class="btn-group" id="status" data-toggle="buttons">
														<label id="cash-Label"
															class="btn btn-default btn-off btn-lg"> <input
															type="radio" value="1" id="cash" name="paymentTypeId">Cash
														</label> <label id="credit-Label"
															class="btn btn-default btn-off btn-lg"> <input
															type="radio" value="2" id="credit"
															name="paymentTypeId">Credit
														</label>
													</div>
												</div>
											</div>
										</div>
										<div class="row1" id="PayModeOptionEdit">
											<label class="L-size control-label" for="payMethod">Payment
												Method :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="">
													<div class="btn-group" id="status" data-toggle="buttons">
														<label class="btn btn-default btn-on btn-lg active">
															<input type="radio" value="1" name="paymentTypeId">Cash
														</label>
													</div>
												</div>
											</div>
										</div>
										<!-- This is Configure For Katira "Editable Field" -->
										<c:if test="${configuration.referenceName}">
										<div class="row1" id="grpfuelReference" class="form-group">
											<label class="L-size control-label" for="fuel_reference">Reference
												:<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input class="form-text" id="fuel_reference"
													name="fuel_reference" type="text"
													value="${fuel.fuel_reference}" maxlength="50"
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
										</c:if>
										<!-- This is Configure for Every Group "Read only " -->
										<c:if test="${configuration.showReferenceCol}">
										<div class="row1" id="grpfuelReference" class="form-group">
											<label class="L-size control-label" for="fuel_reference">Reference
												:<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input class="form-text" id="fuel_reference"
													name="fuel_reference" type="text" readonly="readonly"
													value="${fuel.fuel_reference}" maxlength="50"
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
										</c:if>
										
										
										<c:if test="${configuration.tallyImportRequired}">
											<div class="row1" id="grpmanufacturer" class="form-group">
											<input type="hidden" value="${fuel.tallyCompanyId}" id="tallyId">
											<input type="hidden" value="${fuel.tallyCompanyName}" id="tallyName">
											<label class="L-size control-label" for="manufacurer">Tally Company Name :<abbr title="required">*</abbr></label>
												<div class="I-size">
													<input type="hidden" id="tallyCompanyId" name="tallyCompanyId" style="width: 100%;" 
													  placeholder="Please Enter Tally Company Name" />
												</div>
										</div>
										</c:if>
										<div class="row1">
											<label class="L-size control-label">Driver Name :</label>
											<div class="I-size" id="driverSelect">
												<!-- <div class="col-md-9"> -->
													<input type="hidden" value="${fuel.driver_id}" id="Odid">
													<input type="hidden" id="DriverFuel"
														name="driver_id" style="width: 100%;" required="required"
														placeholder="Please Enter 3 or more Driver Name" />
												<!-- </div>
												<div class="col-md-1">
													<a class=" btn btn-link "
														onclick="visibility('driverEnter', 'driverSelect');">
														<i class="fa fa-plus"> New</i>
													</a>
												</div> -->
											</div>
											<div id="driverEnter" class="contact_Hide">
												<div class="I-size">
													<div class="col-md-9">
														<input class="form-text row1" name="driver_name"
															id="Odname" maxlength="25" type="text"
															value="${fuel.driver_name}"
															placeholder="Enter Fuel Driver Name"
															onkeypress="return IsVendorName(event);"
															ondrop="return false;"> <label class="error"
															id="errorVendorName" style="display: none"> </label>
													</div>
													<div class="col-md-1">
														<a class=" btn btn-link col-sm-offset-1"
															onclick="visibility('driverEnter', 'driverSelect');">
															<i class="fa fa-minus"> Select</i>
														</a>
													</div>

												</div>
											</div>
											
										</div>
										
										<c:if test="${configuration.showDriver2}">
										<div class="row1">
											<label class="L-size control-label">Driver2 Name :</label>
											<input type="hidden" value="${fuel.secDriverID}" id="secdid" >
											<input type="hidden" value="${fuel.fuelSecDriverName}" id="secdName" >
											
											<div class="I-size" id="secdriverSelect">
												<input type="hidden" id="Driver2Fuel" name="secDriverID"
													name="driver_id" style="width: 100%;" required="required"
													placeholder="Please Enter 3 or more Driver Name" />
											</div>
											
											<%-- <div id="secdriverEnter" class="contact_Hide">
												<div class="I-size">
													<div class="col-md-9">
														<input class="form-text row1" name="driver_name"
															id="Osecdname" maxlength="25" type="text"
															value="${fuel.fuelSecDriverName}"
															placeholder="Enter Fuel Driver Name"
															onkeypress="return IsVendorName(event);"
															ondrop="return false;"> <label class="error"
															id="errorVendorName" style="display: none"> </label>
													</div>
													<div class="col-md-1">
														<a class=" btn btn-link col-sm-offset-1"
															onclick="visibility('secdriverEnter', 'secdriverSelect');">
															<i class="fa fa-minus"> Select</i>
														</a>
													</div>

												</div>
											</div> --%>
										</div>
										</c:if>
										<c:if test="${configuration.showCleaner}">
										<div class="row1">
											<label class="L-size control-label">Cleaner :</label>
											<input type="hidden" value="${fuel.cleanerID}" id="cleanerId" >
											<input type="hidden" value="${fuel.fuelCleanerName}" id="cleanerName" >
											<div class="I-size" id="cleanerSelect">
												<input type="hidden" id="VehicleTOCleanerFuel"
													name="cleanerID" style="width: 100%;" required="required"
													placeholder="Please Enter 3 or more Driver Name" />
											</div>
										</div>
										</c:if>
										<c:if test="${configuration.showRoute}">
										<div class="row1">
											<label class="L-size  control-label">Route Service :</label>
											<input type="hidden" value="${fuel.routeID}" id="fuelRouteId" >
											<input type="hidden" value="${fuel.fuelRouteName}" id="fuelRouteName" >
											<div class="I-size" id="driverSelect">
												<input type="hidden" id="TripRouteList" name="routeID" value ="${fuel.routeID}"
													style="width: 100%;" value="0" required="required"
													placeholder="Please Enter 3 or more Route Name, NO " />
											</div>
										</div>
										</c:if>
										
										<div class="form-group boolean optional fuel_personal">
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
														<input type="hidden" value="${fuel.fuel_tank_partial}"
															name="fuel_tank_partial" id="fuelEditRedioButton">
														<input type="hidden" name="fuel_usage"
															value="${fuel.fuel_usage}">
														<div class="btn-group" id="status" data-toggle="buttons">
															<label id="Full-Label"
																class="btn btn-default btn-off btn-lg"> <input
																type="radio" value="0" name="fuel_tank"
																id="fuel_tank_Full">Full
															</label> <label id="Partial-Label"
																class="btn btn-default btn-off btn-lg"> <input
																type="radio" value="1" name="fuel_tank"
																id="fuel_tank_Partial">Partial
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
							<fieldset id="grpfuelComment">
								<legend>Comment</legend>
								<div class="box">
									<div class="box-body">
										<div class="col-sm-offset-2">
											<div class="row1">
												<div class="I-size">
													<textarea class="form-text" id="fuel_comments"
														name="fuel_comments" rows="3" maxlength="240"
														onkeypress="return IsComment(event);"
														ondrop="return false;">${fuel.fuel_comments}
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
							<fieldset class="form-actions">
								<div class="col-sm-offset-4 I-size">

									<button type="submit" onclick="return validateFuelEntries();" class="btn btn-success">Update
										Fuel Entry</button>
									<a class="btn btn-default"
										href="showFuel.in?FID=${fuel.fuel_id}">Cancel</a>
								</div>
							</fieldset>
						</div>
					</form>

				</div>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/FU/FuelNewlanguage.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabs.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/FU/FuelEnter.validate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/FU/VehicleGPSDetails.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/FU/FuelAdd.js" />"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/bootstrap-clockpicker.min.js" />"></script>	
	<script type="text/javascript">
	$(document).ready(function(){var e=document.getElementById("fuelEditRedioButton").value;switch(e){case"0":document.getElementById("fuel_tank_Full").checked=!0,document.getElementById("Full-Label").className="btn btn-default btn-on btn-lg active";break;case"1":document.getElementById("fuel_tank_Partial").checked=!0,document.getElementById("Partial-Label").className="btn btn-default btn-on btn-lg active"}}),$(document).ready(function(){var e=document.getElementById("fuel_payment").value;"cash"===e.toLowerCase()?(document.getElementById("cash").checked=!0,document.getElementById("cash-Label").className="btn btn-default btn-on btn-lg active"):(document.getElementById("credit").checked=!0,document.getElementById("credit-Label").className="btn btn-default btn-on btn-lg active")});
	</script>
	<script type="text/javascript">
	$(document).ready(function(){var h=$("#Ovid").val(),i=$("#enterVendorName").val();$("#selectVendor").select2("data",{id:h,text:i});var j=$("#Odid").val(),k=$("#Odname").val();$("#DriverFuel").select2("data",{id:j,text:k});
	
	$("#tallyCompanyId").select2("data",{id:$('#tallyId').val(),text:$('#tallyName').val()});
	
	});
	</script>
	<script type="text/javascript">
	$(document).ready(function(){$(".select2").select2();$("#tagPicker").select2({closeOnSelect:!1})});	
	$(document).ready(function(){($("#IsAutoVendor").val() == true || $("#IsAutoVendor").val() == "true")?($("#ShowOtherPayOptionEdit").hide(),$("#PayModeOptionEdit").show()):($("#ShowOtherPayOptionEdit").show(),$("#PayModeOptionEdit").hide()),$("#selectVendor").on("change",function(){var t=$("#selectVendor").val();t > 0?($("#editOption").show(),$("#selectVen").hide(),$("#ShowOtherPayOptionEdit").show(),$("#PayModeOptionEdit").hide()):($("#editOption").hide(),$("#PayModeOptionEdit").show(),$("#ShowOtherPayOptionEdit").hide())})});
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
		
		<script type="text/javascript">
			$(document).ready(function() {
				
				$('.clockpicker').clockpicker({
					placement: 'bottom',
					align: 'right',
					autoclose: true
				});
				
				if($('#allowToEditFuelRef').val() == 'true' || $('#allowToEditFuelRef').val() == true ){
					$('#fuel_reference').attr('readonly', false);
				}
				
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
				/* if((${gpsConfiguration.allowGPSIntegration})) {
					$("#grpfuelOdometer").addClass("hide");
				} */
				
				if(!${fuel.gpsOdometer != null && fuel.gpsOdometer > 0}) {
					$("#grpfuelOdometer").removeClass("hide");
				}
		});
	</script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			
			
			if($("#editvid").val() != undefined && $("#editvid").val() > 0 ) {
	   	 		$('#normalEdit').hide();
	   	 		$('#vehicleEdit').show();
	   	 	}
			
			
		});
	</script>
	<script>
	$(document).ready(function() {
		var secDriverID 	= $("#secdid").val();
		var secDriverName 	= $("#secdName").val();
		var cleanerId 		= $("#cleanerId").val();
		var cleanerName 	= $("#cleanerName").val();
		var fuelRouteId 	= $("#fuelRouteId").val();
		var fuelRouteName 	= $("#fuelRouteName").val();
		
		
		$('#VehicleTODriver2Fuel').select2('data', {
		id : secDriverID,
		text : secDriverName
		});
		$('#VehicleTOCleanerFuel').select2('data', {
			id : cleanerId,
			text : cleanerName
		});
		$('#TripRouteList').select2('data', {
			id : fuelRouteId,
			text : fuelRouteName
		});
	});
	</script>
</div>