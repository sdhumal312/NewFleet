<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<style>
.row {
	width: 100%;
	margin: 10px auto;
}
</style>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/vehicle/1/1"/>"> Vehicle</a> / <span
						id="NewVehicle">Create New Vehicle</span>
				</div>
				<div class="pull-right">

					<a class="btn btn-link" href="<c:url value="/vehicle/1/1"/>">
						<span id="AddVehicle"> Cancel</span>
					</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('ADD_VEHICLE')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('ADD_VEHICLE')">
			<div class="row">
				<div class="col-sm-2 col-md-2 ">
					<div class="box fixed">
						<div class="box-body">
							<ul class="nav nav-pills nav-stacked" id="myTabs">
								<li class="active"><a href="#vehicleInfo"
									data-toggle="pill">Vehicle Information <abbr
										title="required">*</abbr></a></li>
								<li><a href="#vehicleGroup" data-toggle="pill">Classification
										<abbr title="required">*</abbr>
								</a></li>
								<li><a href="#speciInfo" data-toggle="pill">Specification
								</a></li>
								<li><a href="#OwnInfo" data-toggle="pill">Ownership
										Information <abbr title="required">*</abbr>
								</a></li>
								<li><a href="#FuelInfo" data-toggle="pill">Fuel
										Information<abbr title="required">*</abbr>
								</a></li>
								<li><a href="#Settings" data-toggle="pill">Settings <abbr
										title="required">*</abbr></a></li>
							</ul>
						</div>
					</div>
				</div>
				<div class="col-sm-8 col-md-9">
					<form id="formVehicle" action="saveNewVehicle.in" method="post"
						name="formVehicle" role="form" class="form-horizontal">
						<div class="tab-content">
							<div class="tab-pane active" id="vehicleInfo">
								<div class="box">
									<div class="box-body">
										<input name="userID" type="hidden" value="${user.id}"
											required="required" /> <input name="createdBy" type="hidden"
											value="${user.email}" required="required" />
										<div class="row" id="grpvehicleNumber" class="form-group">
											<label for="vehicle_registrationNumber"
												class="L-size control-label"><span
												id="RegistrationNumber"> <abbr title="required">*</abbr>
													Registration Number :
											</span></label>
											<div class="I-size">
												<input class="form-text" id="vehicle_registrationNumber"
													name="vehicle_registration" type="text"
													data-inputmask="'mask': ['aa-99-a-9','aa-99-aa-9', 'aa-99-9','aa-99-a-99','aa-99-aa-99', 'aa-99-99','aa-99-a-999','aa-99-aa-999', 'aa-99-999','aa-99-a-9999','aa-99-aa-9999', 'aa-99-9999']"
													data-mask="" required> <span id="vehicleNameIcon"
													class=""></span>
												<div id="vehicleNameErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<div class="row" id="grpchasisNumber" class="form-group">
											<label class="L-size control-label"
												for="vehicle_chasisNumber"><abbr title="required">*</abbr>
												Chasis Number : </label>
											<div class="I-size">
												<input class="form-text" id="vehicle_chasisNumber"
													name="vehicle_chasis" type="text" maxlength="25"> <span
													id="chasisNameIcon" class=""></span>
												<div id="chasisNameErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<div class="row" id="grpengineNumber" class="form-group">
											<label class="L-size control-label"
												for="vehicle_engineNumber"><abbr title="required">*</abbr>
												Engine Number : </label>
											<div class="I-size">
												<input class="form-text" id="vehicle_engineNumber"
													name="vehicle_engine" type="text" maxlength="25"> <span
													id="engineNameIcon" class=""></span>
												<div id="engineNameErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<div class="row" id="grptypeNumber" class="form-group">
											<label class="L-size control-label" for="VehicleTypeSelect"><abbr
												title="required">*</abbr> Vehicle Type : </label>
											<div class="I-size">

												<input type="hidden" id="VehicleTypeSelect"
													name="vehicleTypeId" style="width: 100%;"
													placeholder="Please Enter 2 or more Type Name" /> <span
													id="typeNameIcon" class=""></span>
												<div id="typeNameErrorMsg" class="text-danger"></div>

											</div>
										</div>
										<div class="row">
											<label class="L-size control-label" id="ManufactureYear">Manufacture
												Year : </label>
											<div class="I-size">
												<div class="input-group input-append date" id="vehicle_year">
													<input class="form-text" id="vehicle_year"
														name="vehicle_year" type="text" maxlength="11"
														data-inputmask="'mask': ['9999']" data-mask=""> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
											</div>
										</div>

										<div class="row">
											<label class="L-size control-label" id="VehicleMaker">Vehicle
												Maker : </label>
											<div class="I-size">
												<input class="form-text" id="vehicle_maker"
													name="vehicle_maker" type="text" maxlength="25">
											</div>
										</div>
										<div class="row">
											<label class="L-size control-label" id="VehicleModel">Vehicle
												Model : </label>
											<div class="I-size">
												<input class="form-text" id="vehicle_Model"
													name="vehicle_Model" type="text" maxlength="25">

											</div>
										</div>

										<div class="row">
											<label class="L-size control-label" id="RegistrationState">Registration
												State : </label>
											<div class="I-size">
												<input class="form-text" id="registrationState"
													name="vehicle_registrationState" type="text" maxlength="25">
											</div>
										</div>
										<div class="row">
											<label class="L-size control-label" id="VehicleRegisterDate">Vehicle
												Register Date : </label>
											<div class="I-size">
												<div class="input-group input-append date"
													id="vehicle_RegisterDate">
													<input class="form-text" id="vehicleRegisterDate"
														name="vehicle_RegisterDate" type="text"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="">
													<span class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
											</div>
										</div>
										<div class="row">
											<label class="L-size control-label" id="Registeredupto">Registered
												Upto : </label>
											<div class="I-size">
												<div class="input-group input-append date"
													id="Registeredupto">
													<input class="form-text" id="vehicle_Registeredupto"
														name="vehicle_Registeredupto" type="text"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="">
													<span class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
											</div>
										</div>
											<c:if test="${configuration.importDataToTally}">
											
											<div class="row">
												<label class="L-size control-label" id="ledgerName">Ledger Name</label>
												<div class="I-size">
													<input class="form-text" id="ledgerName"
														name="ledgerName" type="text"
														maxlength="250">
												</div>
											</div>
										</c:if>
										<c:if test="${gpsConfiguration.allowGPSIntegration}">
											<div class="row">
												<label class="L-size control-label" id="RegistrationState">GPS
													ID : </label>
												<div class="I-size">
													<input class="form-text" id="vehicleGPSId"
														name="vehicleGPSId" type="text"
														maxlength="25">
												</div>
											</div>
											<div class="row">
												<label class="L-size control-label" id="gpsVendorId">GPS Vendor : </label>
												<div class="I-size">
													<select class="select2"
														name="gpsVendorId" style="width: 100%;">
														<option value="0">Please Select</option>
														<option value="1">OMNI Talk</option>
														<option value="2">Intangles</option>
														<option value="3">Ideagami</option>
														<option value="4">SMTC</option>
													</select>
												</div>
											</div>
										</c:if>
									
										<c:if test="${configuration.showMobileNumber}">
											<div class="row">
												<label class="L-size control-label" id="gpsVendorId">Mobile Number : </label>
												<div class="I-size">
													<input type="text" name="mobileNumber" id="mobileNumber"
													  maxlength="10" pattern=".{0}|.{10,10}"	onkeypress="return isNumberKey(event)" class="form-text">
												</div>
											</div>
										</c:if>	
									</div>
								</div>
								<div>
									<div class="pull-left"></div>
									<div class="pull-right">

										<a href="#vehicleGroup" data-toggle="pill"
											class="btn btn-success">Next </a>
									</div>
								</div>
							</div>
							<div class="tab-pane" id="vehicleGroup">
								<div class="box">
									<div class="box-body">
										<div class="row" id="grpstatusNumber" class="form-group">
											<label class="L-size control-label" for="vehicleStatus"><abbr
												title="required">*</abbr> Vehicle Status : </label>
											<div class="I-size">
												<select class="form-control select2" name="vStatusId"
													id="vehicleStatus" style="width: 100%;">
													<c:forEach items="${vehiclestatus}" var="vehiclestatus">
														<option value="${vehiclestatus.sid}">
															<c:out value="${vehiclestatus.vStatus}" />
														</option>
													</c:forEach>
												</select> <span id="statusNameIcon" class=""></span>
												<div id="statusNameErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<div class="row" id="grpGroupSelectNumber" class="form-group">
											<label class="L-size control-label" for="VehicleGroupSelect"><abbr
												title="required">*</abbr> Vehicle Group : </label>
											<div class="I-size">
												<input type="hidden" id="VehicleGroupSelect"
													name="vehicleGroupId" style="width: 100%;"
													placeholder="Please Enter 2 or more Group Name" /> 
													<span id="GroupSelectNameIcon" class=""></span>
												<div id="statusGroupNameErrorMsg" class="text-danger"></div>
											</div>

										</div>
										<c:if test="${configuration.showRoute}">
										<div class="row">
											<label class="L-size control-label" id="VehicleRouteName"><abbr
												title="required">*</abbr>Route
												/ Factory Name :</label>
											<div class="I-size">

												<input type="hidden" id="VehicleRouteSelect"
													name="routeID" style="width: 100%;"
													placeholder="Please Enter 2 or more Route Name" />
											</div>
										</div>
										</c:if>
									<c:if test="${configuration.showVehicleColor}">
										<div class="row">
											<label class="L-size control-label" id="VehicleColor">Vehicle
												Color : </label>
											<div class="I-size">
												<input class="form-text" id="vehicle_Color"  onpaste="return false" 
													name="vehicle_Color" type="text" maxlength="25"
													onkeypress="return /[a-z]/i.test(event.key)";
													>
											</div>
										</div>
										</c:if>
										<c:if test="${configuration.showClassOfVehicle}">
										<div class="row">
											<label class="L-size control-label" id="ClassofVehicle">Class
												of Vehicle : </label>
											<div class="I-size">
												<input class="form-text" id="vehicle_Class"
													name="vehicle_Class" type="text" maxlength="25">
											</div>
										</div>
										</c:if>
										<c:if test="${configuration.showVehicleBody}">
										<div class="row">
											<label class="L-size control-label" id="VehicleBody">Vehicle
												Body : </label>
											<div class="I-size">
												<input class="form-text" id="vehicle_body"
													name="vehicle_body" type="text" maxlength="25">
											</div>
										</div>
										</c:if>
										<c:if test="${configuration.showACType}">
										<div class="row">
											<label class="L-size control-label" id="VehicleActype">Vehicle
												A/C Type : </label>
											<div class="I-size">
												<label for="vehicle_meter_unit_mi"> <input
													checked="checked" class="radio_buttons required"
													id="vehicle_ac" name="acTypeId" type="radio"
													value="1"> <span>A/C </span>
												</label> <label for="vehicle_meter_unit_mi"> <input
													class="radio_buttons required" id="vehicle_nonac"
													name="acTypeId" type="radio" value="2">
													<span>Non A/C </span>
												</label> <label for="vehicle_meter_unit_mi"> <input
													class="radio_buttons required" id="vehicle_fullac"
													name="acTypeId" type="radio" value="3">
													<span>A/C &amp; Non A/C</span>
												</label>
											</div>
										</div>
										</c:if>
									</div>
								</div>
								<div>
									<div class="pull-left">
										<a href="#vehicleInfo" data-toggle="pill" class="btn btn-info">Prev
										</a>
									</div>
									<div class="pull-right">

										<a href="#speciInfo" data-toggle="pill"
											class="btn btn-success">Next </a>
									</div>
								</div>
							</div>
							<div class="tab-pane" id="speciInfo">
								<div class="box">
									<div class="box-body">
									<c:if test="${configuration.showCylinder}">
										<div class="row">
											<label class="L-size control-label" id="Cylinders">Cylinders
												: </label>
											<div class="I-size">
												<input class="form-text" id="vehicle_cylinders"
													name="vehicle_Cylinders" type="text" maxlength="5">
											</div>
										</div>
										</c:if>
										<c:if test="${configuration.showCubicCapacity}">
										<div class="row">
											<label class="L-size control-label" id="CubicCapacity">Cubic
												Capacity : </label>
											<div class="I-size">
												<input class="form-text" id="vehicle_cubicCapacity"
													name="vehicle_CubicCapacity" type="text" maxlength="5">
											</div>
										</div>
										</c:if>
										<c:if test="${configuration.showPower}">
										<div class="row">
											<label class="L-size control-label" id="Power">Power
												: </label>
											<div class="I-size">
												<input class="form-text" id="vehicle_Power"
													name="vehicle_Power" type="text" maxlength="25">
											</div>
										</div>
										</c:if>
										<c:if test="${configuration.showWheelBase}">
										<div class="row">
											<label class="L-size control-label" id="WheelBase">Wheel
												Base : </label>
											<div class="I-size">
												<input class="form-text" id="vehicle_wheelBase"
													name="vehicle_wheelBase" type="text" maxlength="25">
											</div>
										</div>
										</c:if>
										<c:if test="${configuration.showSeatCapacity}">
										<div class="row">
											<label class="L-size control-label" id="SeatCapacity">Seat
												Capacity : </label>
											<div class="I-size">
												<input class="form-text" id="vehicle_seatCapacity"
													name="vehicle_SeatCapacity" type="text" maxlength="10">
											</div>
										</div>
										</c:if>
										
										<div class="row">
											<label class="L-size control-label" id="unladenWeight">unladen
												Weight : </label>
											<div class="I-size">
												<input class="form-text" id="vehicle_unladenWeight"
													name="vehicle_UnladenWeight" type="text" maxlength="25">
											</div>
										</div>
										<div class="row">
											<label class="L-size control-label" id="LadenWeight">Laden
												Weight : </label>
											<div class="I-size">
												<input class="form-text" id="vehicle_ladenWeight"
													name="vehicle_LadenWeight" type="text" maxlength="25">
											</div>
										</div>
									</div>
								</div>
								<div>
									<div class="pull-left">
										<a href="#vehicleGroup" data-toggle="pill"
											class="btn btn-info">Prev </a>
									</div>
									<div class="pull-right">

										<a href="#OwnInfo" data-toggle="pill" class="btn btn-success">Next
										</a>
									</div>
								</div>
							</div>
							<div class="tab-pane" id="OwnInfo">
								<div class="box">
									<div class="box-body">
										<div class="row">
											<label class="L-size control-label" id="VehicleOwnership"><abbr
												title="required">*</abbr>Vehicle Ownership : </label>
											<div class="I-size">
												<select class="form-control select2"
													name="vehicleOwnerShipId" style="width: 100%;">
													<option value="1">Owned</option>
													<option value="2">Leased</option>
													<option value="3">Rented</option>
													<option value="4">Attached</option>
													<option value="5">Customer</option>
												</select>
											</div>
										</div>
										<c:if test="${configuration.showVehicleLocation }">
										<div class="row">
											<label class="L-size control-label" id="VehicleLocation">Vehicle
												Location : </label>
											<div class="I-size">
												<input class="form-text" id="VehicleLocation"
													name="Vehicle_Location" type="text" maxlength="148">
											</div>
										</div>
										</c:if>
									</div>
								</div>
								<div>
									<div class="pull-left">
										<a href="#speciInfo" data-toggle="pill" class="btn btn-info">Prev
										</a>
									</div>
									<div class="pull-right">

										<a href="#FuelInfo" data-toggle="pill" class="btn btn-success">Next
										</a>
									</div>
								</div>
							</div>
							<div class="tab-pane" id="FuelInfo">
								<div class="box">
									<div class="box-body">
										<div class="row" id="grpfuelNumber" class="form-group">
											<label class="L-size control-label" for="vehicleFuel"><abbr
												title="required">*</abbr> Fuel Type : </label>
											<div class="I-size">
												<select class="select2" id="vehicleFuel" name="vehicleFuelId"
													multiple="multiple" style="width: 100%;">
													<c:forEach items="${vehiclefuel}" var="vehiclefuel">
														<option value="${vehiclefuel.fid}">
															<c:out value="${vehiclefuel.vFuel}" />
														</option>
													</c:forEach>

												</select> <span id="fuelNameIcon" class=""></span>
												<div id="fuelNameErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<div class="row" id="grpvehicleFuelTank" class="form-group">
											<label class="L-size control-label" for="vehicleFuelTank"><abbr
												title="required">*</abbr> ${configuration.fuelTankText } = </label>
											<div class="I-size">
												<input class="form-text" id="vehicleFuelTank"
													name="vehicle_FuelTank1" type="number" min="0"
													maxlength="11"> <span id="vehicleFuelTankIcon"
													class=""></span>
												<div id="vehicleFuelTankErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<div class="row" id="grpvehicleOilCapacity" class="form-group">
											<label class="L-size control-label" for="vehicleOilCapacity"><abbr
												title="required">*</abbr>Engine Oil Capacity : </label>
											<div class="I-size">
												<input class="form-text" id="vehicleOilCapacity"
													name="vehicle_Oil" type="number" min="0" maxlength="11">
												<span id="vehicleOilCapacityIcon" class=""></span>
												<div id="vehicleOilCapacityErrorMsg" class="text-danger"></div>
											</div>
										</div>
									</div>
								</div>
								<div>
									<div class="pull-left">
										<a href="#OwnInfo" data-toggle="pill" class="btn btn-info">Prev
										</a>
									</div>
									<div class="pull-right">

										<a href="#Settings" data-toggle="pill" class="btn btn-success">Next
										</a>
									</div>
								</div>
							</div>
							<div class="tab-pane" id="Settings">
								<div class="box">
									<div class="box-body">
										<div class="row">
											<label class="L-size control-label" id="primarymeterunit"><abbr
												title="required">*</abbr>primary meter unit : </label>
											<div class="I-size3">
												<span class="radio"> <label
													for="vehicle_meter_unit_km"> <input
														checked="checked" class="radio_buttons required"
														id="vehicle_MeterUnit" name="vehicleMeterUnitId"
														type="radio" value="1"> <span id="Kilometers">Kilometers</span>
												</label>
												</span> <span class="radio"> <label
													for="vehicle_meter_unit_mi"> <input
														class="radio_buttons required" id="vehicle_MeterUnit"
														name="vehicleMeterUnitId" type="radio" value="2">
														<span id="Miles">Miles</span>
												</label>
												</span><span class="radio"> <label
													for="vehicle_Meter_Unit_hr"> <input
														class="radio_buttons required" id="vehicle_meter_unit_hr"
														name="vehicleMeterUnitId" type="radio" value="3">
														<span id="Hours">Hours</span>
												</label>
												</span>
												<p class="help-block" id="meterunithelp"></p>

											</div>
										</div>

										<div class="row" id="grpodometerNumber" class="form-group">
											<label class="L-size control-label" for="vehicleOdometer"><abbr
												title="required">*</abbr>Vehicle OdoMeter : </label>
											<div class="I-size3">
												<input class="form-text" id="vehicleOdometer"
													name="vehicle_Odometer" type="text" min="0" value="0"
													onkeypress="return isNumberKey(event)"
													onpaste="return false"
													maxlength="11"> <span id="odometerNameIcon"
													class=""></span>
												<div id="odometerNameErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<div class="row" id="grpExpectedMileage" class="form-group">
											<label class="L-size control-label" for="ExpectedMileage"><abbr
												title="required">*</abbr>Expected Mileage : </label>
											<div class="I-size3">
												<div class="row">
													<div class="input-group">
														<!--onkeypress="return ExpectedMileage(event,this);  Original-->
														<input class="form-text" id="ExpectedMileage_from"
															type="text" name="vehicle_ExpectedMileage" maxlength="5"
															min="0"  placeholder="EM-form"
															onpaste="return false"
															onkeypress="return isNumberKeyWithDecimal(event,this.id)"
															ondrop="return false;" value="0">
														<div class="input-group-addon">To</div>
														<!--onkeypress="return ExpectedMileage(event,this);  Original-->
														<input class="form-text" id="ExpectedMileage_to"
															type="text" name="vehicle_ExpectedMileage_to"
															maxlength="5" min="0" 
															placeholder="EM-To"
															onpaste="return false"
															onkeypress="return isNumberKeyWithDecimal(event,this.id)"
															ondrop="return false;" value="0">
													</div>
													<span id="ExpectedMileageIcon" class=""></span>
													<div id="ExpectedMileageErrorMsg" class="text-danger"></div>
												</div>
											</div>
										</div>
										<div class="row" id="grpMaximumOdometer" class="form-group">
											<label class="L-size control-label"
												for="MaximumOdometer"><abbr title="required">*</abbr>Allow
												Maximum OdoMeter: </label>
											<div class="I-size3">
												<input class="form-text" id="MaximumOdometer"
													name="vehicle_ExpectedOdameter"
													onpaste="return false" max="5000"
													onkeypress="return isNumberKey(event)"
													type="number" min="0" value="1000" maxlength="11" >
												<span id="MaximumOdometerIcon" class=""></span>
												<div id="MaximumOdometerErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<div class="row">
											<label class="L-size control-label" id="Fuelvolumeunit"><abbr
												title="required">*</abbr>Fuel Volume unit : </label>
											<div class="I-size3">
												<span class="radio"> <label
													for="vehicle_meter_unit_mi"> <input
														checked="checked" class="radio_buttons required"
														id="vehicle_meter_unit_mi" name="vehicleFuelUnitId"
														type="radio" value="1"> <span id="liters">Liters</span>
												</label>
												</span> <span class="radio"> <label
													for="vehicle_meter_unit_km"> <input
														class="radio_buttons required" id="vehicle_meter_unit_km"
														name="vehicleFuelUnitId" type="radio" value="2">
														<span id="Gallons">Gallons</span>
												</label>
												</span>
												<p class="help-block" id="fuelUnitHelp"></p>
											</div>
										</div>
										<input class="form-text" name="vehicle_photoid" type="hidden"
											value="1">
									</div>
								</div>
							</div>
							<div class="row">
								<fieldset class="form-actions">
									<div class="pull-center">
										<button type="submit" class="btn btn-success">Create
											Vehicle</button>
										<a class=" btn btn-info"
											href="<c:url value="/vehicle/1/1"/>"><span
											id="Cancel">Cancel</span></a>
									</div>
								</fieldset>
							</div>

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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VI/Vehicle.validate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VI/VehicleAjaxDropDown.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VI/VehicleOldlanguage.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>  

</div>