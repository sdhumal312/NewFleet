<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<meta http-equiv="Cache-Control"
	content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/vehicle/1/1"/>">Vehicle</a> / <a
						href="<c:url value="showVehicle.in?vid=${vehicle.vid}"/>"><c:out
							value="${vehicle.vehicle_registration}" /></a> / <a
						href="<c:url value="VehicleFuelDetails/1.in?vid=${vehicle.vid}"/>">
						Vehicle Fuel Entries</a> / <span>Create Fuel Entries</span>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_FUEL')">
						<a class="btn btn-success"
							href="addFuelEntries.in"> <i
							class="fa fa-plus"></i> Add Fuel
						</a>
					</sec:authorize>
					<a class="btn btn-link"
						href="<c:url value="VehicleFuelDetails.in?vid=${vehicle.vid}"/>">Cancel</a>
				</div>
			</div>
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_VEHICLE')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
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
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Status"><a
										href="#"><c:out value=" ${vehicle.vehicle_Status}" /></a></span></li>
								<li><span class="fa fa-clock-o" aria-hidden="true"
									data-toggle="tip" data-original-title="Odometer"><a
										href="#"><c:out value=" ${vehicle.vehicle_Odometer}" /></a></span></li>
								<li><span class="fa fa-bus" aria-hidden="true"
									data-toggle="tip" data-original-title="Type"><a href="#"><c:out
												value=" ${vehicle.vehicle_Type}" /></a></span></li>
								<li><span class="fa fa-map-marker" aria-hidden="true"
									data-toggle="tip" data-original-title="Location"><a
										href="#"><c:out value=" ${vehicle.vehicle_Location}" /></a></span></li>
								<li><span class="fa fa-users" aria-hidden="true"
									data-toggle="tip" data-original-title="Group"><a
										href="#"><c:out value=" ${vehicle.vehicle_Group}" /></a></span></li>
								<li><span class="fa fa-road" aria-hidden="true"
									data-toggle="tip" data-original-title="Route"><a
										href="#"><c:out value=" ${vehicle.vehicle_RouteName}" /></a></span></li>
							</ul>
						</div>
					</div>
				</sec:authorize>
			</div>
		</div>
	</section>
	<section class="content-body">
		<div class="row">
			<sec:authorize access="!hasAuthority('ADD_FUEL')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('ADD_FUEL')">
				<div class="col-md-9 col-sm-9 col-xs-12">

					<c:if test="${saveVehicleDocument}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Vehicle Document was Successfully Created.
						</div>
					</c:if>
					<c:if test="${param.revise eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Driver Document was successfully Edited.
						</div>
					</c:if>
					<c:if test="${deleteVehicleDocument}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							The Vehicle Document was successfully Removed.
						</div>
					</c:if>
					<c:if test="${alreadyVehicleDocument}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Document was Already created.
						</div>
					</c:if>
					<c:if test="${param.success eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Fuel Entries created successfully .
						</div>
					</c:if>
					<c:if test="${param.danger eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Fuel Entries was Already created.
						</div>
					</c:if>
					<section class="content">

						<div class="row">

							<form id="formEditFuel"
								action="<c:url value="/saveVehicleDetailsFuel.in"/>"
								method="post" enctype="multipart/form-data" name="formEditFuel"
								role="form" class="form-horizontal"
								onsubmit="return validateFuel();">
						<input type="hidden" id="vehicle_ExpectedOdameter" name="vehicle_ExpectedOdameter" value="${vehicle.vehicle_ExpectedOdameter}" />
						<input type="hidden" id="vehicle_Odometer" name="vehicle_Odometer" value="${vehicle.vehicle_Odometer}" />
								<div class="form-horizontal ">
									<fieldset>
										<legend>Fuel Entries</legend>
										<div class="box">
											<div class="box-body">

												<div class="row1" id="grpvehicleNumber" class="form-group">
													<label class="L-size control-label" for="FuelSelectVehicle">Vehicle<abbr
														title="required">*</abbr></label>
													<div class="I-size">
														<select name="vid" style="width: 100%;"
															id="selectVIDTOFuel" required="required">
															<option value="">--Please Select --></option>
															<option value="${vehicle.vid}"><c:out
																	value="${vehicle.vehicle_registration}" /></option>
														</select> <span id="vehicleNumberIcon" class=""></span>
														<div id="vehicleNumberErrorMsg" class="text-danger"></div>
													</div>
												</div>
												<!-- get client side validate only  of get Current odometer -->
												<div class="hidden" id='hidden'>

													<!-- get current odometer of the vehicle -->
													<select class="form-control" id='vehicle_MeterINVehicle'>
														<option value="${vehicle.vid}"><c:out
																value="${vehicle.vehicle_Odometer}" /></option>
													</select>

													<!-- Get type of the vehicle -->
													<select class="form-control" id='vehicle_FuelINVehicle'>
														<option value="${vehicle.vid}"><c:out
																value="${vehicle.vehicleFuelId}" /></option>
													</select>

													<!-- Get type of the vehicle Group -->
													<select class="form-control" id='vehicle_GroupINVehicle'>
														<option value="${vehicle.vid}"><c:out
																value="${vehicle.vehicle_Group}" /></option>
													</select> <input type="hidden" id="fuelTypeName"
														value="${vehicle.vehicle_Fuel} " />
												</div>
												<div class="row1" id="grpvehicleDepot">
													<label class="L-size control-label">Vehicle Group :</label>
													<div class="I-size">

														<input class="form-text" id="vehicle_group"
															name="vehicle_group" type="text" readonly="readonly">

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
																onkeypress="return RouteKM(event);"
																ondrop="return false;" required="required" />

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
																required="required" id="fuelDate"
																data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" />
															<span class="input-group-addon add-on"> <span
																class="fa fa-calendar"></span>
															</span>
														</div>
														<span id="fuelDateIcon" class=""></span>
														<div id="fuelDateErrorMsg" class="text-danger"></div>
													</div>
												</div>

												<div class="row1" id="grpfuelOdometer">
													<label class="L-size control-label" for="fuel_meter">Odometer<abbr
														title="required">*</abbr></label>
													<div class="I-size">

														<input id="fuel_meter_old" name="fuel_meter_old"
															type="hidden"> <input class="form-text"
															id="fuel_meter" name="fuel_meter" type="text"
															maxlength="10" value="${vehicle.vehicle_Odometer }" onblur="validateMaxOdameter();"
															onkeypress="return IsNumericOdometer(event);"
															ondrop="return false;"> <span
															id="fuelOdometerIcon" class=""></span>
															<input type="hidden" id="validateOdometerInFuel" value="${validateOdometerInFuel}">
														<div id="fuelOdometerErrorMsg" class="text-danger"></div>
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
																		<button class="close" data-dismiss="modal"
																			type="button">×</button>
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
																			vehicle's "current meter" even if it's the most
																			recent. Hence, a voided meter entry will never
																			trigger a service reminder or anything else that
																			depends on a vehicle's current meter value.</p>

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
												<input type="text" class="form-text" id="gpsOdometer" name="gpsOdometer" readonly="readonly" value="${VEHICLE_TOTAL_KM}">
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
													<label class="L-size control-label">Vendor :<abbr
														title="required">*</abbr></label>
													<div class="I-size" id="vendorSelect">
														<input type="hidden" id="selectVendor" name="vendor_name"
															style="width: 100%;" required="required"
															placeholder="Please Select Vendor Name" /> <label
															class="error" id="errorVendorSelect"> </label>

													</div>

												</div>

												<div class="row1" id="grpfuelLiter" class="form-group">
													<label class="L-size control-label" for="fuel_liters">Liter
														:<abbr title="required">*</abbr>
													</label>
													<div class="I-size">
														<input class="form-text" id="fuel_liters"
															name="fuel_liters" type="text" maxlength="8" min="0"
															onkeypress="return isNumberKey(event,this);"
															ondrop="return false;"><span id="fuelLiterIcon"
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
																</label> <label class="btn btn-default btn-off btn-lg">
																	<input type="radio" value="2" name="paymentTypeId">Credit
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
														: <abbr title="required">*</abbr>
													</label>
													<div class="I-size">
														<input class="form-text" id="fuel_reference"
															maxlength="50" name="fuel_reference" type="text"
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
														<div class="col-md-9">
															<input type="hidden" id="VehicleTODriverFuel"
																name="driver_id" style="width: 100%;"
																required="required"
																placeholder="Please Enter 3 or more Driver Name" />

														</div>
														<div class="col-md-1">
															<a class=" btn btn-link "
																onclick="visibility('driverEnter', 'driverSelect');">
																<i class="fa fa-plus"> New</i>
															</a>
														</div>

													</div>
													<div id="driverEnter" class="contact_Hide">

														<div class="I-size">
															<div class="col-md-9">
																<input class="form-text row1" name="driver_name"
																	maxlength="25" type="text"
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
											<div class="I-size" id="driverSelect">
												<input type="hidden" id="VehicleTODriver2Fuel"
													name="driver_id" style="width: 100%;" required="required"
													placeholder="Please Enter 3 or more Driver Name" />
											</div>
										</div>
										</c:if>
										<c:if test="${configuration.showCleaner}">
										<div class="row1">
											<label class="L-size control-label">Cleaner :</label>
											<div class="I-size" id="driverSelect">
												<input type="hidden" id="VehicleTOCleanerFuel"
													name="driver_id" style="width: 100%;" required="required"
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
													<div
														class="form-group sf_switch optional fuel_entry_partial">
														<div class="col-sm-offset-1">
															<label class="L-size control-label" for="fuel_partial">Fuel
																Tank :</label>
														</div>
														<div class="I-size">
															<div class="">
																<div class="btn-group" id="status" data-toggle="buttons">
																	<label class="btn btn-default btn-on btn-lg active">
																		<input type="radio" value="0" name="fuel_tank"
																		checked="checked">Full
																	</label> <label class="btn btn-default btn-off btn-lg">
																		<input type="radio" value="1" name="fuel_tank">Partial
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
														<!-- <img alt="g.satheesh48@gmail.com" class="img-circle media-object pull-left" src="#">
				 -->
														<div class="I-size">
															<textarea class="form-text" id="fuel_comments"
																name="fuel_comments" rows="3" maxlength="50"
																onkeypress="return IsComment(event);"
																ondrop="return false;"></textarea>
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
												<a class="btn btn-link"
													href="<c:url value="/VehicleFuelDetails/1.in?vid=${vehicle.vid}"/>">Cancel</a>
											</div>
										</div>
									</div>
								</div>
							</form>
						</div>
					</section>
				</div>
			</sec:authorize>
			<div class="col-md-2 col-sm-2 col-xs-12">
				<%@include file="VehicleSideMenu.jsp"%>
			</div>
		</div>
	</section>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/FU/FuelNewlanguage.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabs.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/FU/FuelEnter.validate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			$("#fuel_type, #selectVIDTOFuel").select2({
				placeholder : "Please Enter Vehicle Name Search"
			}), $("#tagPicker").select2({
				closeOnSelect : !1
			});
			
			applyGpsSettings('${gpsConfiguration}');
			
			$("#selectVendor, #fuel_type, #fuelDate").change(function() {
		        $.getJSON("getFixedFuelPrice.in", {
		        	Vendor_id: $('#selectVendor').val(),
		        	fuel_type: $('#fuel_type').val(),
		        	fuel_date: $('#fuelDate').val(),
		            ajax: "true"
		        }, function(e) {
		            var t = "",
		                o = "",
		                l = 0;
		            t = e.VFFID, 
		            o = e.FUEL_PERDAY_COST; 
		            
		            if(o != null && o > 0){
		            	document.getElementById("fuel_price").placeholder = o, 
			        document.getElementById("fuel_price").value = o, 
			        document.getElementById("fuel_price").max = o;
		            $("#fuel_price").prop("readonly", true);
		            }
		            else{
		            	document.getElementById("fuel_price").placeholder = 0, 
			          document.getElementById("fuel_price").value = 0, 
			            document.getElementById("fuel_price").max = 100;
		            	 $("#fuel_price").prop("readonly", false);
		            }
		        })
		    });
			
			if(!(${showVehicleCol})) {
				$("#grpvehicleNumber").addClass("hide");
				$.getJSON("getFuelVehicleOdoMerete.in", {
			        FuelvehicleID: ${vehicle.vid},
			        ajax: "true"
			    }, function(e) {
			        var n = "",
			            t = "",
			            l = "";
			        n = e.vehicle_Odometer, l = e.vehicle_Group, document.getElementById("fuel_meter").placeholder = n, $("#fuel_meter_old").val(n), $("#vehicle_group").val(l);
			        $('#vehicleGroupId').val(e.vehicleGroupId);
			        $('#vehicle_Odometer').val(e.vehicle_Odometer);
			        $('#vehicle_ExpectedOdameter').val(e.vehicle_ExpectedOdameter);
					$("#fuel_reference").val(e.vehicle_Odometer);
			        var a = [];
			        var fuelTypeId = [];
			        a = e.vehicle_Fuel.split(",");
			        fuelTypeId = e.vehicleFuelId.split(",");
			        
			        for (var i = a.length, o = 0; i > o; o++) t += '<option value="' + fuelTypeId[o] + '" selected>' + a[o] + "</option>";
			        t += "</option>", $("#fuel_type").html(t)
			    })
			    $( "#selectVIDTOFuel" ).val(${vehicle.vid});
			}
			
			
			if(!(${showVehicleDepotCol})) {
				$("#grpvehicleDepot").addClass("hide");
			}

			if(!(${showFuelTypeCol})) {
				$("#grpfuelType").addClass("hide");
			}

			if(!(${showReferenceCol})) {
				$("#grpfuelReference").addClass("hide");
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
		});
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>