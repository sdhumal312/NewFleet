<%@ include file="taglib.jsp"%>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<style>
.row {
	width: 100%;
	margin: 10px auto;
	padding:1%;
}
.label_font{
	font-weight: bold;
	font-size: larger;
}
</style>

<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"> <spring:message code="label.master.home" /> </a> / 
					<a href="<c:url value="/vehicle/1/1"/>"> Vehicle</a> / 
					<span id="NewVehicle">Create New Vehicle</span>
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
								<li id="basicInformation" class="active">
								<a href="#vehicleInfo" data-toggle="pill">Vehicle Information 
									</a></li>
								<li id="classInformation" class="" ><a href="#vehicleGroup" data-toggle="pill">Classification/Specification
									</a></li>
								<li id="ownInformation"  class="" ><a href="#OwnInfo" data-toggle="pill">Ownership/Fuel Information 
									</a></li>
								<li id="settingInformation" class="" ><a href="#Settings" data-toggle="pill">Settings 
									</a></li>
							</ul>
						</div>
					</div>
				</div>
				<div class="col-sm-8 col-md-9">
					<div class="tab-content">
						<div class="tab-pane active" id="vehicleInfo">
							<div class="box">
								<div class="box-body">
									<div class="row" class="form-group">
										<div class="col-sm-8 col-md-6" >
											<input type="hidden" id="validateVGroup" value="${configuration.validateVGroup}">
											<input type="hidden" id="validateVType" value="${configuration.validateVehicleType}">
											<input type="hidden" id="validateVStatus" value="${configuration.validateVehicleStatus}">
											<input type="hidden" id="validateChasisNo" value="${configuration.validateChasisNo}">
											<input type="hidden" id="validateEngineNo" value="${configuration.validateEngineNo}">
											<input type="hidden" id="validateVFuelType" value="${configuration.validateVehicleFuelType}">
											<input type="hidden" id="validateVOdometer" value="${configuration.validateVehicleOdometer}">
											<input type="hidden" id="validateVExpectedOdometer" value="${configuration.validateVehicleExpectedOdometer}">
											<input type="hidden" id="validateVExpectedMileage" value="${configuration.validateVehicleExpectedMileage}">
											<input type="hidden" id="validateVehicleModal" value="${configuration.validateVehicleModal}">
											<input type="hidden" id="selectServiceProgramOnModal" value="${configuration.selectServiceProgramOnModal}">
											<input type="hidden" id="allowMaximumOdometer" value="${configuration.allowMaximumOdometer}">
											<div class="col-lg-4 label_font" >Registration Number <abbr title="required">*</abbr></div>
											<div class="col-lg-6" >
												<input class="form-text" id="vehicleRegNo" type="text" onChange="vehicleNumberValidation();"
													data-inputmask="'mask': ['aa-99-a-9','aa-99-aa-9', 'aa-99-9','aa-99-a-99','aa-99-aa-99', 'aa-99-99','aa-99-a-999','aa-99-aa-999', 'aa-99-999','aa-99-a-9999','aa-99-aa-9999', 'aa-99-9999']"
													data-mask="" required> 
											</div>
										</div>
										<div class="col-sm-8 col-md-5" >
											<div class="col-lg-4 label_font " >Chasis Number
												<c:if test="${configuration.validateChasisNo}">
													<abbr title="required">*</abbr>
												</c:if>
											</div>
											<div class="col-lg-6" >
												<input class="form-text" id="vehicleChasisNo" type="text" maxlength="25">
											</div>
										</div>
									</div>
									
									<div class="row" class="form-group">
										<div class="col-sm-8 col-md-6" >
											<div class="col-lg-4 label_font" >Engine Number
												<c:if test="${configuration.validateEngineNo}">
													<abbr title="required">*</abbr>
												</c:if> 
											</div>
											<div class="col-lg-6" >
												<input class="form-text" id="vehicleEngineNo" type="text" maxlength="25">
											</div>
										</div>
										<div class="col-sm-8 col-md-5" >
											<div class="col-lg-4 label_font" >Vehicle Type
												 <c:if test="${configuration.validateVehicleType}">
													<abbr title="required">*</abbr> 
												</c:if>	
											</div>
											<div class="col-lg-6" >
												<input type="hidden" id="VehicleTypeSelect" style="width: 100%;"
												placeholder="Please Enter 2 or more Type Name" /> 
											</div>
										</div>
									</div>
									
									<div class="row" class="form-group">
										<div class="col-sm-8 col-md-6" >
											<div class="col-lg-4 label_font" >Vehicle Status
												<c:if test="${configuration.validateVehicleStatus}">
													<abbr title="required">*</abbr> 
												</c:if>
											</div>
											<div class="col-lg-6" >
												<select class="form-control select2" id="vehicleStatus" style="width: 100%;">
												<c:forEach items="${vehiclestatus}" var="vehiclestatus">
													<c:choose>
														<c:when test="${vehiclestatus.sid == 1}">
															<option value="${vehiclestatus.sid}" selected>
																<c:out value="${vehiclestatus.vStatus}" />
															</option>
														</c:when>
														<c:otherwise>
															<option value="${vehiclestatus.sid}">
																<c:out value="${vehiclestatus.vStatus}" />
															</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select>
											</div>
										</div>
										<div class="col-sm-8 col-md-5" >
											<div class="col-lg-4 label_font" >Vehicle Group
												<c:if test="${configuration.validateVGroup}">
													<abbr title="required">*</abbr> 
												</c:if>
											</div>
											<div class="col-lg-6" >
												<input type="hidden" id="VehicleGroupSelect" style="width: 100%;" placeholder="Please Enter Group Name" />
											</div>
										</div>
									</div>
									
									<div class="row" class="form-group">
										<div class="col-sm-8 col-md-6" >
											<div class="col-lg-4 label_font" >Manufacture Year
											
											</div>
											<div class="col-lg-6" >
												<div class="input-group input-append date">
													<input class="form-text" id="vehicle_year" type="text" maxlength="11"
														data-inputmask="'mask': ['9999']" data-mask=""> 
														<span class="input-group-addon add-on">
														<span class="fa fa-calendar"></span>
													</span>
												</div>
											</div>
										</div>
										<div class="col-sm-8 col-md-5" >
											<div class="col-lg-4 label_font" >Vehicle Maker
											
											</div>
											<div class="col-lg-6" >
												<!-- <input class="form-text" id="vehicleMaker"
												name="vehicle_Model" type="text" maxlength="25"> -->
												<input type="hidden" id="vehicleMaker" style="width: 100%;"
												placeholder="Please Enter 2 or more Type Name" /> 
											</div>
										</div>
									</div>
									
									<div class="row" class="form-group">
										
										<div class="col-sm-8 col-md-6" >
											<div class="col-lg-4 label_font" > Vehicle Model
											<c:if test="${configuration.validateVehicleModal}">
													<abbr title="required"> *</abbr> 
												</c:if>
											</div>
											<div class="col-lg-6" >
												<input type="hidden" id="vehicleModel" style="width: 100%;"
												placeholder="Please Enter 2 or more Type Name" /> 	
											</div>
										</div>
										<div class="col-sm-8 col-md-5" >
											<div class="col-lg-4 label_font" >Registration State
											</div>
											<div class="col-lg-6" >
												<!-- <input class="form-text" id="registrationState"
												name="vehicle_registrationState" type="text" maxlength="25"> -->
												
												<select id="registrationState" class="select2" style="width: 100%;"></select>
											</div>
										</div>
									</div>
									
									<div class="row" class="form-group">
										<div class="col-sm-8 col-md-6" >
											<div class="col-lg-4 label_font" >Vehicle Register Date
											</div>
											<div class="col-lg-6" >
												<div class="input-group input-append date">
													<input class="form-text" id="vehicle_RegisterDate" name="vehicle_RegisterDate" type="text"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="">
													<span class="input-group-addon add-on"> 
													<span class="fa fa-calendar"></span>
													</span>
												</div>
											</div>
										</div>
										<div class="col-sm-8 col-md-5" >
											<div class="col-lg-4 label_font" >Registered Upto
											</div>
											<div class="col-lg-6" >
												<div class="input-group input-append date">
													<input class="form-text" id="vehicle_Registeredupto" name="vehicle_Registeredupto" type="text"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="">
													<span class="input-group-addon add-on"> 
													<span class="fa fa-calendar"></span>
													</span>
												</div>
											</div>
										</div>
									</div>
									
									<div class="row" class="form-group">
										<c:if test="${configuration.showSubCompanyDropdown}">
										<div class="col-sm-8 col-md-6" >
											<div class="col-lg-4 label_font" >Select Sub Company
											</div>
											<div class="col-lg-6" >
												<input id="subCompany" name="subCompany" type="hidden" 
												style="width: 100%;" placeholder="Please Enter 2 or more Characters" value="0">
											</div>
										</div>
										</c:if>
									</div>
									
									<div class="row" class="form-group">
									<input type="hidden" id="allowVehicleNameInLedger" value="${configuration.allowVehicleNameInLedger}" />
									
										<c:if test="${configuration.importDataToTally}">
											<div class="col-sm-8 col-md-6" >
												<div class="col-lg-4 label_font" >Ledger Name
												</div>
												<div class="col-lg-6" >
												<input class="form-text" id="ledgerName" type="text" maxlength="250">
												</div>
											</div>
										</c:if>	
										<c:if test="${gpsConfiguration.allowGPSIntegration}">
											<div class="col-sm-8 col-md-5" >
												<div class="col-lg-4 label_font" >GPS ID
												</div>
												<div class="col-lg-6" >
													<input class="form-text" id="vehicleGPSId" name="vehicleGPSId" type="text" maxlength="25">
												</div>
											</div>
										</c:if>
										<c:if test="${configuration.showTollIdFeild}">
											<div class="col-sm-8 col-md-5" >
												<div class="col-lg-4 label_font" >Toll ID
												</div>
												<div class="col-lg-6" >
													<input class="form-text" id="vehicleTollId" name="vehicleTollId" type="text" maxlength="25">
												</div>
											</div>
										</c:if>
									</div>
									
									<div class="row" class="form-group">
										<c:if test="${gpsConfiguration.allowGPSIntegration}">
											<div class="col-sm-8 col-md-6" >
												<div class="col-lg-4 label_font" >GPS Vendor
												</div>
												<div class="col-lg-6" >
													<select class="select2" id="gpsVendorId" name="gpsVendorId" style="width: 100%;">
													<option value="0">Please Select</option>
													<option value="1">OMNI Talk</option>
													<option value="2">Intangles</option>
													<option value="3">Ideagami</option>
													<option value="4">SMTC</option>
													<option value="6">Track Now</option>
													<option value="8">TATA</option>
													<option value="9">Eicher</option>
												</select>
												</div>
											</div>
										</c:if>	
										<c:if test="${configuration.showMobileNumber}">
											<div class="col-sm-8 col-md-5" >
												<div class="col-lg-4 label_font" >Mobile Number
												</div>
												<div class="col-lg-6" >
													<input type="text" name="mobileNumber" id="mobileNumber"
												  maxlength="10" pattern=".{0}|.{10,10}"	
												  onkeypress="return isNumberKey(event)" class="form-text">
												</div>
											</div>
										</c:if>
									</div>
									
									<div class="row" class="form-group" style="display: none;" id="serviceRow">
											<div class="col-sm-8 col-md-6" >
												<div class="col-lg-4 label_font" >Service Program
												</div>
												<div class="col-lg-6" >
												  <select id="serviceProgramId" style="width: 100%">
												     	 <option value="0">Please select</option>
														<c:forEach items="${serviceProgramList}" var="serviceProgramList">
																<option value="${serviceProgramList.vehicleServiceProgramId}">
																	<c:out value="${serviceProgramList.programName}" />
																</option>
														</c:forEach>
												  </select>
												</div>
											</div>
									</div>
								</div>
							</div>
							
							<div class="pull-right">
								<a href="#vehicleGroup" data-toggle="pill" class="btn btn-success activeClassification">Next </a>
							</div>
						</div>
						
						<div class="tab-pane" id="vehicleGroup">
							<div class="box">
								<div class="box-body">
									<div class="row" class="form-group">
										<c:if test="${configuration.showRoute}">
											<div class="col-sm-8 col-md-6" >
												<div class="col-lg-4 label_font" >Route/Factory Name
												</div>
												<div class="col-lg-6" >
													<input type="hidden" id="VehicleRouteSelect" style="width: 100%;"
													placeholder="Please Enter Route Name" />
												</div>
											</div>
										</c:if>
										<c:if test="${configuration.showVehicleColor}">
											<div class="col-sm-8 col-md-5" >
												<div class="col-lg-4 label_font" >Vehicle Color
												</div>
												<div class="col-lg-6" >
													<input class="form-text" id="vehicle_Color" type="text" maxlength="25"
													onkeypress="return /[a-z]/i.test(event.key);" >
												</div>
											</div>
										</c:if>	
									</div>
									
									<div class="row" class="form-group">
										<c:if test="${configuration.showClassOfVehicle}">
											<div class="col-sm-8 col-md-6" >
												<div class="col-lg-4 label_font" >Class of Vehicle
												</div>
												<div class="col-lg-6" >
													<input class="form-text" id="vehicle_Class" type="text" maxlength="25">
												</div>
											</div>
										</c:if>	
										<c:if test="${configuration.showVehicleBody}">
											<div class="col-sm-8 col-md-5" >
												<div class="col-lg-4 label_font" >Vehicle Body
												</div>
												<div class="col-lg-6" >
													<input class="form-text" id="vehicle_body" type="text" maxlength="25">
												</div>
											</div>
										</c:if>	
										
											<c:if test="${configuration.showVehicleBodyMaker}">
											<div class="col-sm-8 col-md-5" >
												<div class="col-lg-4 label_font" >Body Maker
												</div>
												<div class="col-lg-6" >
													<input type="hidden" id="bodyMakerSelect" style="width: 100%;"
													placeholder="Please Body Maker Name" />
												</div>
											</div>
										</c:if>	
									</div>
									
									<div class="row" class="form-group">
										<c:if test="${configuration.showACType}">
											<div class="col-sm-8 col-md-6" >
												<div class="col-lg-4 label_font" >Vehicle A/C Type
												</div>
												<div class="col-lg-6" >
													<label for="vehicle_meter_unit_mi"> 
													<input checked="checked" class="radio_buttons required" id="vehicle_ac" name="acTypeId" onclick="showAcType();" type="radio" value="1"> 
													<span>A/C </span>
												</label> 
												<label for="vehicle_meter_unit_mi"> 
													<input class="radio_buttons required" id="vehicle_nonac" name="acTypeId" type="radio" onclick="showAcType();" value="2">
													<span>Non A/C </span>
												</label> 
												<label for="vehicle_meter_unit_mi"> 
													<input class="radio_buttons required" id="vehicle_fullac" name="acTypeId" type="radio" onclick="showAcType();" value="3">
													<span>A/C &amp; Non A/C</span>
												</label>
												</div>
											</div>
										</c:if>	
										<c:if test="${configuration.showCylinder}">
											<div class="col-sm-8 col-md-5" >
												<div class="col-lg-4 label_font" >Cylinders
												</div>
												<div class="col-lg-6" >
													<input class="form-text" id="vehicle_cylinders" type="text" maxlength="5">
												</div>
											</div>
										</c:if>	
									</div>
									
									<div class="row" class="form-group">
										<c:if test="${configuration.showCubicCapacity}">
											<div class="col-sm-8 col-md-6" >
												<div class="col-lg-4 label_font" >Cubic Capacity
												</div>
												<div class="col-lg-6" >
													<input class="form-text" id="vehicle_cubicCapacity" type="text" maxlength="5">
												</div>
											</div>
										</c:if>	
										<c:if test="${configuration.showPower}">
											<div class="col-sm-8 col-md-5" >
												<div class="col-lg-4 label_font" >Power
												</div>
												<div class="col-lg-6" >
													<input class="form-text" id="vehicle_Power" type="text" maxlength="25">
												</div>
											</div>
										</c:if>	
									</div>
									
									<div class="row" class="form-group">
										<c:if test="${configuration.showWheelBase}">
											<div class="col-sm-8 col-md-6" >
												<div class="col-lg-4 label_font" >Wheel Base
												</div>
												<div class="col-lg-6" >
													<input class="form-text" id="vehicle_wheelBase"  type="text" maxlength="25">
												</div>
											</div>
										</c:if>	
										<c:if test="${configuration.showSeatCapacity}">
											<div class="col-sm-8 col-md-5" >
												<div class="col-lg-4 label_font" >Seat Capacity
												</div>
												<div class="col-lg-6" >
													<input class="form-text" id="vehicle_seatCapacity" type="text" maxlength="10">
												</div>
											</div>
										</c:if>	
									</div>
									
									<div class="row" class="form-group">
										<div class="col-sm-8 col-md-6" >
											<div class="col-lg-4 label_font" >unladen Weight
											</div>
											<div class="col-lg-6" >
												<input class="form-text" id="vehicle_unladenWeight" type="text" maxlength="25">
											</div>
										</div>
										<div class="col-sm-8 col-md-5" >
											<div class="col-lg-4 label_font" >Laden Weight
											</div>
											<div class="col-lg-6" >
												<input class="form-text" id="vehicle_ladenWeight" type="text" maxlength="25">
											</div>
										</div>
									</div>
								</div>
							</div>
							<div>
								<div class="pull-left">
									<a href="#vehicleInfo" data-toggle="pill" class="btn btn-info activeVehicleInfo">Prev </a>
								</div>
								<div class="pull-right">
									<a href="#OwnInfo" data-toggle="pill" class="btn btn-success activeOwnership">Next </a>
								</div>
							</div>
						</div>
						<div class="tab-pane" id="OwnInfo">
							<div class="box">
								<div class="box-body">
									<div class="row" class="form-group">
										<div class="col-sm-8 col-md-6" >
											<div class="col-lg-4 label_font" >Vehicle Ownership
											</div>
											<div class="col-lg-6" >
												<select class="form-control select2" id ="ownership" style="width: 100%;">
												<option value="1">Owned</option>
												<option value="2">Leased</option>
												<option value="3">Rented</option>
												<option value="4">Attached</option>
												<option value="5">Customer</option>
											</select>
											</div>
										</div>
										<c:if test="${configuration.showVehicleLocation }">
											<div class="col-sm-8 col-md-5" >
												<div class="col-lg-4 label_font" >Vehicle Location
												</div>
												<div class="col-lg-6" >
												<input type="hidden" id="VehicleLocation" style="width: 100%;" placeholder="Please Enter Branch Name" />
													<!-- <input class="form-text" id="VehicleLocation" type="text" maxlength="148"> -->
												</div>
											</div>
										</c:if>	
									</div>
									
									<div class="row" class="form-group">
										<div class="col-sm-8 col-md-6" >
											<div class="col-lg-4 label_font" >Fuel Type
												<c:if test="${configuration.validateVehicleFuelType}">
													<abbr title="required">*</abbr>
												</c:if>
											</div>
											<div class="col-lg-6" >
												<select class="select2" id="vehicleFuel" multiple="multiple" style="width: 100%;">
													<c:forEach items="${vehiclefuel}" var="vehiclefuel">
														<c:choose>
															<c:when test="${vehiclefuel.fid == 1}">
																<option value="${vehiclefuel.fid}" selected>
																	<c:out value="${vehiclefuel.vFuel}" />
																</option>
															</c:when>
															<c:otherwise>
																<option value="${vehiclefuel.fid}">
																	<c:out value="${vehiclefuel.vFuel}" />
																</option>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="col-sm-8 col-md-5" >
											<div class="col-lg-4 label_font" >${configuration.fuelTankText}  
											</div>
											<div class="col-lg-6" >
												<input type="hidden" id="fuelTankText" value="${configuration.fuelTankText}">
												<input class="form-text" id="vehicleFuelTank" type="number" min="0" maxlength="11" onkeypress="return isNumberKeyWithDecimal(event,this.id);">
											</div>
										</div>
									</div>
									
									<div class="row" class="form-group">
										<div class="col-sm-8 col-md-6" >
											<div class="col-lg-4 label_font" >Engine Oil Capacity
											</div>
											<div class="col-lg-6" >
												<input class="form-text" id="vehicleOilCapacity" type="number" min="0" maxlength="11"  onkeypress="return isNumberKeyWithDecimal(event,this.id);" >
											</div>
										</div>
										<div class="col-sm-8 col-md-5" >
											<div class="col-lg-4 label_font" >
											</div>
											<div class="col-lg-6" >
												
											</div>
										</div>
									</div>
								</div>
							</div>
							<div>
								<div class="pull-left">
									<a href="#vehicleGroup" data-toggle="pill" class="btn btn-info activeClassification">Prev </a>
								</div>
								<div class="pull-right">
									<a href="#Settings" data-toggle="pill" class="btn btn-success activeSetting">Next </a>
								</div>
							</div>
						</div>
						<div class="tab-pane" id="Settings">
							<div class="box">
								<div class="box-body">
									<div class="row" class="form-group">
										<div class="col-sm-8 col-md-6" >
											<div class="col-lg-4 label_font" >Primary meter unit
											</div>
											<div class="col-lg-6" >
												<span class="radio"> 
													<label for="vehicle_meter_unit_km"> 
														<input checked="checked" class="radio_buttons required" id="vehicle_MeterUnit" name="vehicleMeterUnitId" onclick="showMeterSelected();" type="radio" value="1"> 
														<span id="Kilometers">Kilometers</span>
													</label>
												</span> 
												<span class="radio">
												 	<label for="vehicle_meter_unit_mi">
													 	<input class="radio_buttons required" id="vehicleMeterUnitId" name="vehicleMeterUnitId"  onclick="showMeterSelected();" type="radio" value="2">
														<span id="Miles">Miles</span>
													</label>
												</span>
												<span class="radio"> 
													<label for="vehicle_Meter_Unit_hr"> 
														<input class="radio_buttons required" id="vehicle_meter_unit_hr" name="vehicleMeterUnitId"  onclick="showMeterSelected();" type="radio" value="3">
														<span id="Hours">Hours</span>
													</label>
												</span>
												<p class="help-block" id="meterunithelp"></p>
											</div>
										</div>
										<div class="col-sm-8 col-md-5" >
											<div class="col-lg-4 label_font" >Vehicle OdoMeter
											</div>
											<div class="col-lg-6" >
												<input class="form-text" id="vehicleOdometer" type="text" min="0" value="0" onkeypress="return isNumberKey(event)" maxlength="7">
											</div>
										</div>
									</div>
									
									<div class="row" class="form-group">
										<div class="col-sm-8 col-md-6" >
											<div class="col-lg-4 label_font" >Expected Mileage
											</div>
											<div class="col-lg-6" >
												<div class="input-group">
													<input class="form-text" id="ExpectedMileage_from"
														type="text" name="vehicle_ExpectedMileage" maxlength="5"
														min="0"  placeholder="EM-form"
														onpaste="return false"
														onkeypress="return isNumberKeyWithDecimal(event,this.id)"
														ondrop="return false;" value="0">
													<div class="input-group-addon">To</div>
													<input class="form-text" id="ExpectedMileage_to"
														type="text" name="vehicle_ExpectedMileage_to"
														maxlength="5" min="0" 
														placeholder="EM-To"
														onpaste="return false"
														onkeypress="return isNumberKeyWithDecimal(event,this.id)"
														ondrop="return false;" value="0">
												</div>
											</div>
										</div>
										<div class="col-sm-8 col-md-5" >
											<div class="col-lg-4 label_font" >Allow Maximum OdoMeter
											</div>
											<div class="col-lg-6" >
												<input class="form-text" id="MaximumOdometer" 
												onpaste="return false" max="5000"
												onkeypress="return isNumberKey(event)"
												type="number" min="0" value="1000" maxlength="11" >
											</div>
										</div>
									</div>
									
									<div class="row" class="form-group">
										<div class="col-sm-8 col-md-6" >
											<div class="col-lg-4 label_font" >Fuel Volume unit
											</div>
											<div class="col-lg-6" >
												<span class="radio" > 
												<label for="vehicle_meter_unit_mi"> 
													<input checked="checked" class="radio_buttons required" id="vehicle_meter_unit_mi" name="vehicleFuelUnitId" onclick="showSelectedfuel();" type="radio" value="1"> 
													<span id="liters">Liters</span>
												</label>
											</span> 
											<span class="radio"> 
												<label for="vehicle_meter_unit_km"> 
													<input class="radio_buttons required" id="vehicle_meter_unit_km" name="vehicleFuelUnitId" onclick="showSelectedfuel();" type="radio" value="2">
													<span id="Gallons">Gallons</span>
												</label>
											</span>
											<p class="help-block" id="fuelUnitHelp"></p>
											</div>
										</div>
										<div class="col-sm-8 col-md-5" >
											<div class="col-lg-4 label_font" >
											</div>
											<div class="col-lg-6" >
												
											</div>
										</div>
									</div>
									<input class="form-text" name="vehicle_photoid" id="photoId" type="hidden" value="1">
								</div>
							</div>
						</div>
						<div class="row">
							<fieldset class="form-actions">
								<div class="pull-center">
									<button type="submit" class="btn btn-success" >Create Vehicle</button>
									<a class=" btn btn-info" href="<c:url value="/vehicle/1/1"/>">
									<span id="Cancel">Cancel</span></a>
								</div>
							</fieldset>
						</div>
					</div>
				</div>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>	
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
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VI/addVehicleDetails.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/location.js" />"></script>	 	

</div>