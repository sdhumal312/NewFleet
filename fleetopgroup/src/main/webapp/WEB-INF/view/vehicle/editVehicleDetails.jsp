<%@ include file="taglib.jsp"%>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/vehicle.css" />">
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
					<a href="<c:url value="/open.html"/>"><spring:message code="label.master.home" /></a> / 
					<a href="<c:url value="/vehicle/1/1"/>">New Vehicle</a> / 
					<span id="AllVehicle"> <c:out value="${vehicle.vehicle_registration}" /></span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/vehicle/1/1"/>"> <span id="AddVehicle"> Cancel</span> </a>
				</div>
			</div>
		</div>
	</section>
	<section class="content-header" style="padding: 0px 15px 0;">
		<sec:authorize access="!hasAuthority('VIEW_VEHICLE')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
			<div class="box">
				<div class="box-body">
					<!-- Show the User Profile -->
					<div class="pull-left">
						<a
							href="${pageContext.request.contextPath}/getImageVehicle/${vehicle.vehicle_photoid}.in"
							class="zoom" data-title="Amazing Nature"
							data-footer="The beauty of nature" data-type="image"
							data-toggle="lightbox"> <img
							src="${pageContext.request.contextPath}/getImageVehicle/${vehicle.vehicle_photoid}.in"
							class="img-rounded" alt="Cinque Terre" width="100" height="100" />
						</a>
					</div>
					<div class="pull-left1">
						<h3 class="secondary-header-title">
							<a href="showVehicle.in?vid=${vehicle.vid}"> <c:out
									value="${vehicle.vehicle_registration}" />
							</a>
						</h3>

						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li>
									<span class="fa fa-black-tie" aria-hidden="true" data-toggle="tip" data-original-title="Status">
										<a href="#"><c:out value=" ${vehicle.vehicle_Status}" /></a>
									</span>
								</li>
								<li>
									<span class="fa fa-clock-o" aria-hidden="true" data-toggle="tip" data-original-title="Odometer">
										<a href="#"><c:out value=" ${vehicle.vehicle_Odometer}" /></a>
									</span>
								</li>
								<li>
									<span class="fa fa-bus" aria-hidden="true" data-toggle="tip" data-original-title="Type">
										<a href="#"><c:out value=" ${vehicle.vehicle_Type}" /></a>
									</span>
								</li>
								<c:if test="${configuration.showVehicleLocation }">				
									<li><span class="fa fa-map-marker" aria-hidden="true" data-toggle="tip" data-original-title="Location">
										<a href="#"><c:out value=" ${vehicle.vehicle_Location}" /></a></span>
									</li>
								</c:if>
								<li>
									<span class="fa fa-users" aria-hidden="true" data-toggle="tip" data-original-title="Group">
										<a href="#"><c:out value=" ${vehicle.vehicle_Group}" /></a>
									</span>
								</li>
								<c:if test="${configuration.showRoute}">
									<li>
										<span class="fa fa-road" aria-hidden="true" data-toggle="tip" data-original-title="Route">
										<a href="#"><c:out value=" ${vehicle.vehicle_RouteName}" /></a></span>
									</li>
								</c:if>

							</ul>
						</div>
					</div>
				</div>
			</div>
		</sec:authorize>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('EDIT_VEHICLE')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('EDIT_VEHICLE')">
			<div class="row">
				<div class="col-sm-2 col-md-2 ">
					<div class="box fixed">
						<div class="box-body">
							<ul class="nav nav-pills nav-stacked" id="myTabs">
								<li id="basicInformation"  class="active">
								<a href="#vehicleInfo" data-toggle="pill">Vehicle Information 
									</a></li>
								<li id="classInformation" class="" ><a href="#vehicleGroup" data-toggle="pill">Classification/Specification
									</a></li>
								<li id="ownInformation"  class="" ><a href="#OwnInfo" data-toggle="pill">Ownership/Fuel Information 
									</a></li>
								<li  id="settingInformation" class="" ><a href="#Settings" data-toggle="pill">Settings 
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
											<input name="userID" type="hidden" value="${user.id}" required="required" /> 
											<input name="lastModifiedBy" type="hidden" value="${user.email}" required="required" />
											<input name="createdBy" type="hidden" value="${vehicle.createdBy}" required="required" /> 
											<input name="created" type="hidden" value="${vehicle.created}" required="required" />
											<input type="hidden" id="Ovtname" value="${vehicle.vehicle_Type}" style="width: 100%;" /> 
											<input type="hidden" id="OvtId" value="${vehicle.vehicleTypeId}" style="width: 100%;" />
											<input type="hidden" id="editVehicleStatusId" value="${vehicle.vStatusId}">
											<input type="hidden" id="editVehicleStatusName" value="${vehicle.vehicle_Status}">
											<input type="hidden" id="Ovgname" value="${vehicle.vehicle_Group}" /> 
											<input type="hidden" id="OvgnameId" value="${vehicle.vehicleGroupId}" style="width: 100%;" />
											<input type="hidden" id="Ovrname" value="${vehicle.vehicle_RouteName}" /> 
											<input type="hidden" id="OvrId" value="${vehicle.routeID}" />
											<input id="vehicle_type" type="hidden" value="${vehicle.vehicle_actype}"> 
											<input id=acTypeId type="hidden" value="${vehicle.acTypeId}"> 
											<input type="hidden" id="editFuleId" value="${vehicle.vehicleFuelId}">
											<input type="hidden" id="editFuleName" value="${vehicle.vehicle_Fuel}">
											<input id="vehicle_primarymeterunit" type="hidden" value="${vehicle.vehicle_MeterUnit}"> 
											<input id="vehicle_vehicleMeterUnitId" type="hidden" value="${vehicle.vehicleMeterUnitId}">
											<input id="vehicle_Fuelvolumeunit" type="hidden" value="${vehicle.vehicle_FuelUnit}">
											<input id="vehicle_vehicleFuelUnitId" type="hidden" onclick="showSelectedfuel();" value="${vehicle.vehicleFuelUnitId}">
											<input class="form-text" name="vehicle_photoid" type="hidden" value="${vehicle.vehicle_photoid}">
											<input type="hidden" id="oldVehicleMaker" value="${vehicle.vehicle_maker}" />
											<input type="hidden" id="oldVehicleMakerId" value="${vehicle.vehicleMakerId}" />
											<input type="hidden" id="oldVehicleModel"  value="${vehicle.vehicle_Model}" />
											<input type="hidden" id="oldVehicleModelId"  value="${vehicle.vehicleModelId}" />
											<input type="hidden" id="validateVehicleModal" value="${configuration.validateVehicleModal}">
											<input type="hidden" id="selectServiceProgramOnModal" value="${configuration.selectServiceProgramOnModal}">
											<input type="hidden" id="oldBranchId" value="${vehicle.branchId}">
											<input type="hidden" id="statusRemarkConfiguration" value="${configuration.addVehicleStatusRemark}">
											<input type="hidden" id="companyId" value="${companyId}">
											<input type="hidden" id="userId" value="${userId}">
											<input type="hidden" id="changeToStatusId" value="${changeToStatusId}">
											<input type="hidden" id="vehicleBodyMakerId" value="${vehicle.vehicleBodyMakerId}">
											<input type="hidden" id="bodyMakerName" value="${vehicle.bodyMakerName}">
											<input type="hidden" id="allowMaximumOdometer" value="${configuration.allowMaximumOdometer}">
											<input type="hidden" id="oldSubCompany" value="${vehicle.subCompanyName}">
											<input type="hidden" id="oldSubCompanyId" value="${vehicle.subCompanyId}">
											
											<div class="col-lg-4 label_font" >Registration Number <abbr title="required">*</abbr></div>
											<input class="form-text" id="vid" name="vid" type="hidden" value="${vehicle.vid}"> 
											<div class="col-lg-6" >
												<input class="form-text" id="vehicle_registrationNumber" type="text" readonly="readonly"
												      	 value="${vehicle.vehicle_registration}"> 
											</div>
										</div>
										<div class="col-sm-8 col-md-5" >
											<div class="col-lg-4 label_font " >Chasis Number
												<c:if test="${configuration.validateChasisNo}">
													<abbr title="required">*</abbr>
												</c:if>
											</div>
											<div class="col-lg-6" >
												<input class="form-text" id="vehicle_chasisNumber" type="text" maxlength="25" value="${vehicle.vehicle_chasis}">
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
												<input class="form-text" id="vehicle_engineNumber" type="text" maxlength="25" value="${vehicle.vehicle_engine}" >
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
												placeholder="Please Enter 2 or more Type Name" value="${vehicle.vehicle_Type}" /> 
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
														data-inputmask="'mask': ['9999']" data-mask=""
														<c:if test="${vehicle.vehicle_year != 0}">
														value="${vehicle.vehicle_year}"
														</c:if> > 
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
												<input type="hidden" id="vehicle_maker" style="width: 100%;"
												placeholder="Please Enter 2 or more Type Name" /> 
											</div>
										</div>
									</div>
									<div class="row" class="form-group">
										<div class="col-sm-8 col-md-6" >
											<div class="col-lg-4 label_font" > Vehicle Model
											   <c:if test="${configuration.validateVGroup}">
													<abbr title="required">*</abbr> 
												</c:if>
											</div>
											<div class="col-lg-6" >
												<%-- <input class="form-text" id="vehicle_Model"
													name="vehicle_Model" type="text" maxlength="25" value="${vehicle.vehicle_Model}" > --%>
													<input type="hidden" id="vehicle_Model" style="width: 100%;"
												placeholder="Please Enter 2 or more Type Name" /> 
											</div>
										</div>
										<div class="col-sm-8 col-md-5" >
											<div class="col-lg-4 label_font" >Registration State
											</div>
											<div class="col-lg-6" >
												<input type="hidden" id="editRegistrationState" value="${vehicle.vehicle_registrationState}">
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
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" value="${vehicle.vehicle_RegisterDate}" readonly="readonly">
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
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" value="${vehicle.vehicle_Registeredupto}" readonly="readonly">
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
													style="width: 100%;" placeholder="Please Enter 2 or more Characters">
												</div>
											</div>
										</c:if>
									</div>	
									
									<div class="row" class="form-group">
										<c:if test="${configuration.importDataToTally}">
											<div class="col-sm-8 col-md-6" >
												<div class="col-lg-4 label_font" >Ledger Name
												</div>
												<div class="col-lg-6" >
												<input class="form-text" id="ledgerName" type="text" maxlength="250" value="${vehicle.ledgerName}">
												</div>
											</div>
										</c:if>	
										<c:if test="${gpsConfiguration.allowGPSIntegration}">
											<div class="col-sm-8 col-md-5" >
												<div class="col-lg-4 label_font" >GPS ID
												</div>
												<div class="col-lg-6" >
													<input class="form-text" id="vehicleGPSId" name="vehicleGPSId" type="text" maxlength="25" value="${vehicle.vehicleGPSId}">
												</div>
											</div>
										</c:if>
										<c:if test="${configuration.showTollIdFeild}">
											<div class="col-sm-8 col-md-5" >
												<div class="col-lg-4 label_font" >Toll ID
												</div>
												<div class="col-lg-6" >
													<input class="form-text" id="vehicleTollId" name="vehicleTollId" type="text" maxlength="25" value="${vehicle.vehicleTollId}">
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
													<select class="select2"  id="gpsVendorId" name="gpsVendorId" style="width: 100%;">
													<c:if test="${vehicle.gpsVendorId == null || vehicle.gpsVendorId <= 0}">
														<option value="0">Please Select</option>
													</c:if>
													<c:if test="${vehicle.gpsVendorId != null && vehicle.gpsVendorId > 0}">
														<option value="${vehicle.gpsVendorId}">${vehicle.gpsVendorName}</option>
													</c:if>
													<option value="1">OMNI Talk</option>
													<option value="2">Intangles</option>
													<option value="3">Ideagami</option>
													<option value="4">SMTC</option>
													<option value="6">Track Now</option>
													<option value="7">Fleetex</option>
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
												  maxlength="10" pattern=".{0}|.{10,10}" value="${vehicle.mobileNumber}"	
												  onkeypress="return isNumberKey(event)" class="form-text">
												</div>
											</div>
										</c:if>
										<div class="row" class="form-group" style="display: none;" id="serviceRow">
											<div class="col-sm-8 col-md-6" >
												<div class="col-lg-4 label_font" >Service Program
												</div>
												<div class="col-lg-6" >
												  <input type="hidden" id="serviceId" value="${serviceProgram.vehicleServiceProgramId}">
												  <select id="serviceProgramId" style="width: 100%">
												     	 <option value="${serviceProgram.vehicleServiceProgramId}">${serviceProgram.programName}</option>
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
													onkeypress="return /[a-z]/i.test(event.key);" value="${vehicle.vehicle_Color}" >
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
													<input class="form-text" id="vehicle_Class"
													value="${vehicle.vehicle_Class}" type="text" maxlength="25">
												</div>
											</div>
										</c:if>	
										<c:if test="${configuration.showVehicleBody}">
											<div class="col-sm-8 col-md-5" >
												<div class="col-lg-4 label_font" >Vehicle Body
												</div>
												<div class="col-lg-6" >
													<input class="form-text" id="vehicle_body" 
													value="${vehicle.vehicle_body}"  type="text" maxlength="25">
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
													<input class="radio_buttons required" id="vehicle_ac"
														onclick="showAcType();" name="acTypeId" type="radio"
														value="1"> <span>A/C </span>
													</label> 
													<label for="vehicle_meter_unit_mi"> 
													<input class="radio_buttons required" id="vehicle_nonac"
														onclick="showAcType();" name="acTypeId" type="radio"
														value="2"> <span>Non A/C </span>
													</label> 
													<label for="vehicle_meter_unit_mi"> 
													<input class="radio_buttons required" id="vehicle_fullac"
														onclick="showAcType();" name="acTypeId" type="radio"
														value="3"> <span>A/C &amp; Non A/C</span>
													</label>
												</div>
											</div>
										</c:if>	
										<c:if test="${!configuration.showACType}">
											<input id="vehicle_type" type="hidden" value="1"> 
											<input id="vehicle_fullac" type="hidden" value="3"> 
										</c:if>
										
										<c:if test="${configuration.showCylinder}">
											<div class="col-sm-8 col-md-5" >
												<div class="col-lg-4 label_font" >Cylinders
												</div>
												<div class="col-lg-6" >
													<input class="form-text" id="vehicle_cylinders" 
													value="${vehicle.vehicle_Cylinders}" type="text" maxlength="5">
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
													<input class="form-text" id="vehicle_cubicCapacity"
													value="${vehicle.vehicle_CubicCapacity}" type="text" maxlength="5">
												</div>
											</div>
										</c:if>	
										<c:if test="${configuration.showPower}">
											<div class="col-sm-8 col-md-5" >
												<div class="col-lg-4 label_font" >Power
												</div>
												<div class="col-lg-6" >
													<input class="form-text" id="vehicle_Power" 
													value="${vehicle.vehicle_Power}" type="text" maxlength="25">
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
													<input class="form-text" id="vehicle_wheelBase"  
													value="${vehicle.vehicle_wheelBase}"  type="text" maxlength="25">
												</div>
											</div>
										</c:if>	
										<c:if test="${configuration.showSeatCapacity}">
											<div class="col-sm-8 col-md-5" >
												<div class="col-lg-4 label_font" >Seat Capacity
												</div>
												<div class="col-lg-6" >
													<input class="form-text" id="vehicle_seatCapacity" 
													value="${vehicle.vehicle_SeatCapacity}" type="text" maxlength="10">
												</div>
											</div>
										</c:if>	
									</div>
									
									<div class="row" class="form-group">
										<div class="col-sm-8 col-md-6" >
											<div class="col-lg-4 label_font" >unladen Weight
											</div>
											<div class="col-lg-6" >
												<input class="form-text" id="vehicle_unladenWeight" 
												value="${vehicle.vehicle_UnladenWeight}" type="text" maxlength="25">
											</div>
										</div>
										<div class="col-sm-8 col-md-5" >
											<div class="col-lg-4 label_font" >Laden Weight
											</div>
											<div class="col-lg-6" >
												<input class="form-text" id="vehicle_ladenWeight" 
												value="${vehicle.vehicle_LadenWeight}"  type="text" maxlength="25">
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
												<option value="${vehicle.vehicleOwnerShipId}">
													<c:out value="${vehicle.vehicle_Ownership}" />
												</option>
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
												<input type="hidden" id="editVehicleLocation" value="${vehicle.vehicle_Location}">
												<input type="hidden" id="editBranchId" value="${vehicle.branchId}">
												<input type="hidden" id="VehicleLocation" style="width: 100%;" placeholder="Please Enter Branch Name" />
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
												<input type="hidden" id="editFuleId" value="${vehicle.vehicleFuelId}">
												<input type="hidden" id="editFuleName" value="${vehicle.vehicle_Fuel}">
												<select id="fuelId" class="select2 required" style="width: 100%;" name=vehicleFuelId multiple="multiple">
													<c:forEach items="${vehiclefuel}" var="vehiclefuel">
														<option value="${vehiclefuel.fid}">
														      <c:out value="${vehiclefuel.vFuel}" />
														</option>
												           </c:forEach>
												</select>

											</div>
										</div>
										<div class="col-sm-8 col-md-5" >
											<div class="col-lg-4 label_font" >${configuration.fuelTankText}  
											</div>
											<div class="col-lg-6" >
												<input type="hidden" id="fuelTankText" value="${configuration.fuelTankText}"  >
												<input class="form-text" id="vehicle_FuelTank1" onkeypress="return isNumberKeyWithDecimal(event,this.id)"
												value="${vehicle.vehicle_FuelTank1}" type="number" min="0" maxlength="11">
											</div>
										</div>
									</div>
									
									<div class="row" class="form-group">
										<div class="col-sm-8 col-md-6" >
											<div class="col-lg-4 label_font" >Engine Oil Capacity
											</div>
											<div class="col-lg-6" >
												<input class="form-text" id="vehicle_Oil" onkeypress="return isNumberKeyWithDecimal(event,this.id)"
												value="${vehicle.vehicle_Oil}" type="number" min="0" maxlength="11">
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
														<input checked="checked" class="radio_buttons required"
														id="vehicle_Kilometers" name="vehicleMeterUnitId" onclick="showMeterSelected();"
														type="radio" value="1"> <span id="Kilometers">Kilometers</span>
													</label>
												</span> 
												<span class="radio"> 
													<label for="vehicle_meter_unit_mi"> 
														<input class="radio_buttons required" id="vehicle_Miles" onclick="showMeterSelected();"
															name="vehicleMeterUnitId" type="radio" value="2">
															<span id="Miles">Miles</span>
													</label>
												</span> 
												<span class="radio"> 
													<label for="vehicle_Meter_Unit_hr"> 
														<input class="radio_buttons required" id="vehicle_Hours" onclick="showMeterSelected();"
															name="vehicleMeterUnitId" type="radio" value="3">
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
												<input class="form-text" id="vehicleOdometer" 
												value="${vehicle.vehicle_Odometer}" type="text" min="0" value="0" onkeypress="return isNumberKey(event)" maxlength="7">
											</div>
										</div>
									</div>
									<div class="row" class="form-group">
										<div class="col-sm-8 col-md-6" >
											<div class="col-lg-4 label_font" >Expected Mileage
											</div>
											<div class="col-lg-6" >
												<div class="input-group">
													<input class="form-text" id="vehicle_ExpectedMileage" 
														type="text" name="vehicle_ExpectedMileage" maxlength="5"
														min="0" required="required" placeholder="EM-form"
														
														onkeypress="return isNumberKeyWithDecimal(event,this.id)"
														ondrop="return false;"
														value="${vehicle.vehicle_ExpectedMileage}">
													<div class="input-group-addon">To</div>
													<input class="form-text" id="vehicle_ExpectedMileageTo"
														type="text" name="vehicle_ExpectedMileage_to"
														maxlength="5" min="0" required="required"
														placeholder="EM-To"
												        onkeypress="return isNumberKeyWithDecimal(event,this.id)"
														ondrop="return false;"
														value="${vehicle.vehicle_ExpectedMileage_to}">
												</div>
											</div>
										</div>
										<div class="col-sm-8 col-md-5" >
											<div class="col-lg-4 label_font" >Allow Maximum OdoMeter
											</div>
											<div class="col-lg-6" >
												<input class="form-text" id="MaximumOdometer" 
												onpaste="return false" max="5000" value="${vehicle.vehicle_ExpectedOdameter}"
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
												<span class="radio"> 
												<label for="vehicle_meter_unit_mi"> 
													<input class="radio_buttons required" id="vehicle_Liters"
														name="vehicleFuelUnitId" type="radio" value="1">
														<span id="Liters">Liters</span>
													</label>
												</span> 
												<span class="radio"> 
													<label for="vehicle_meter_unit_km"> 
														<input class="radio_buttons required" id="vehicle_gallons" onclick="showSelectedfuel();"
															name="vehicleFuelUnitId" type="radio" value="2">
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
								</div>
							</div>
						</div>
						<div class="row">
							<fieldset class="form-actions">
								<div class="pull-center">
									<button type="button" class="btn btn-primary" onclick="editVehicleDetails();">Update
										Vehicle</button>
									<a class="btn btn-info"
										href="<c:url value="/showVehicle?vid=${vehicle.vid}"/>">Cancel</a>
								</div>
							</fieldset>
						</div>
					</div>
				</div>
			</div>
		</sec:authorize>
	</section>
	
	<div class="modal" id="remarkModal" tabindex="-2">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Remark</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
				<div class="main-body">
				<div class="box-body">
					<div class="row">
						<div class="col col-sm-12 col-md-5">
							<label>Change Status From : ${vehicle.vehicle_Status} </label>
						</div>
						<div class="col col-sm-12 col-md-5">
							<label>Change Status To : <span id="changeStatusTo">
							</span>
							</label>
						</div>
					</div>
					<div class="row" id="soldDiv1">
								<div class="col col-md-4" >
											<div class="col-lg-4 label_font" >Sold Date
											</div>
											<div class="col-lg-6" >
												<div class="input-group input-append date">
													<input class="form-text" id="StartDate" name="soldDate" type="text"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask=""  readonly="readonly">
													<span class="input-group-addon add-on"> 
													<span class="fa fa-calendar"></span>
													</span>
												</div>
											</div>
										</div>
									<div class="col col-md-4">
											<div class="col-lg-4 label_font" >Party Name :</div> 
											 <div class="col-lg-6" >
												<input class="form-text" id="partyName" type="text" maxlength="25" ></div>
										</div>
					</div>
							<div class="row" id="soldDiv2">
								<div class="col col-md-4">
									<div class="col-lg-4 label_font">Mobile no.</div>
									<div class="col-lg-6">
										<input type="text" class="form-text" id="mobileNo"
											onkeypress="return isNumberKey(event);"
											onblur="return isMobileNum(this);" ondrop="return false;"
											maxlength="10">
									</div>
								</div>
								<div class="col col-md-4">
									<div class="col-lg-4 label_font">Sold Amount</div>
									<div class="col-lg-6">
										<input class="form-text" id="soldAmount" type="text"
											maxlength="25"
											onkeypress="return isNumberKeyWithDecimal(event,this.id);">
									</div>
								</div>
							</div>
							<div class="row1">
						  <label class="L-size control-label" style="color: #2e74e6;font-size: 18px;" >Remark:  </label>
						  <textarea  class="form-text" id="remark" rows="3" cols="" style="height: 80px;"></textarea>
					  </div>
					  </div>
					  </div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary"
						onclick="editVehicleDetails(true);">Save Remark And Update Vehicle</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabs.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VI/VehicleOldlanguage.js" />"></script>
	<%-- <script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VI/VehicleAjaxDropDown.js" />"></script> --%>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VI/editVehicleDetails.js" />"></script> 	
		
	<script type="text/javascript">
		$(document)
						.ready(
								function() {
									$('#fuelId').select2().val([${fuelType}]).trigger("change");
									var e = document
											.getElementById("vehicle_type").value;
									"a/c" === e.toLowerCase() ? document
											.getElementById("vehicle_ac").checked = !0
											: "non a/c" === e.toLowerCase() ? document
													.getElementById("vehicle_nonac").checked = !0
													: document
															.getElementById("vehicle_fullac").checked = !0
								}),
				$(document)
						.ready(
								function() {
									
									var e = document
											.getElementById("vehicle_vehicleMeterUnitId").value;
									1 == e ? document.getElementById("vehicle_Kilometers").checked = !0
											: 2 == e ? document.getElementById("vehicle_Miles").checked = !0
													: document
															.getElementById("vehicle_Hours").checked = !0
								}),
				$(document)
						.ready(
								function() {
									var e = document
											.getElementById("vehicle_vehicleFuelUnitId").value;
									1 == e ? document
											.getElementById("vehicle_Liters").checked = !0
											: document
													.getElementById("vehicle_gallons").checked = !0
								});
		$(document).ready(function(){
			var h=$("#OvtId").val(),i=$("#Ovtname").val();
			$("#VehicleTypeSelect").select2("data",{id:h,text:i});
			
			var j=$("#OvgnameId").val(),k=$("#Ovgname").val();
			$("#VehicleGroupSelect").select2("data",{id:j,text:k});
			
			var n=$("#oldVehicleMaker").val();
			$("#vehicle_maker").select2("data",{id:$('#oldVehicleMakerId').val(),text:n});
			
			var o=$("#oldVehicleModel").val();
			$("#vehicle_Model").select2("data",{id:$('#oldVehicleModelId').val(),text:o});
			
			var s=$("#oldSubCompany").val();
			$("#subCompany").select2("data",{id:$('#oldSubCompanyId').val(),text:s});
			
			$('#bodyMakerSelect').select2('data',{id:$('#vehicleBodyMakerId').val(),text:$('#bodyMakerName').val()});
			var l=$("#OvrId").val(),m=$("#Ovrname").val();
			$("#VehicleRouteSelect").select2("data",{id:l,text:m})});
		   
		     var serviceId = $('#serviceId').val();
		     if(Number(serviceId) > 0){
		    	 setTimeout(function(){ 
		    		 $("#serviceProgramId").select2("readonly", true);
		    		 $("#VehicleTypeSelect").select2("readonly", true);
		    		 $("#vehicle_Model").select2("readonly", true);
		    	 }, 200);
		     }
		
	</script>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>
		<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/location.js" />"></script>		  
</div>
