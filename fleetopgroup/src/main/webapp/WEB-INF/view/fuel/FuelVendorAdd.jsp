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
						href="<c:url value="/FuelVendor/1.in"/>">Fuel Vendor Entry</a> / <small>Add
						Fuel Entry</small>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/FuelVendor/1.in"/>"
						class="btn btn-link btn-sm"> Cancel </a>
				</div>
			</div>
		</div>
	</section>
	<!-- Main content -->
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

		<sec:authorize access="!hasAuthority('ADD_FUELVENDOR')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('ADD_FUELVENDOR')">
			<div class="row" onload="fuelvehicle()">
				<div class="col-md-offset-1 col-md-9">

					<div class="row">
						<h1 style="text-align: center;">${Vendor.vendorName}</h1>
					</div>
					<c:choose>
						<c:when test="${tripSheetFlavor == 1}">
									<div class="row">
								<label class="L-size control-label">Vehicle:</label>
								<h5 class="L-size control-label">${TripSheet.vehicle_registration}</h5>
								<label class="L-size control-label">Driver 1:</label>
								<h5 class="L-size control-label">${TripSheet.tripFristDriverName}</h5>
							</div>
							<div class="row">
								<label class="L-size control-label">TripSheet ID :</label>
								<h5 class="L-size control-label">TS-${TripSheet.tripSheetNumber}</h5>
								<label class="L-size control-label">Driver 2:</label>
								<h5 class="L-size control-label">${TripSheet.tripSecDriverName}</h5>
							</div>
	
						</c:when>
						<c:when test="${tripSheetFlavor == 2}">
							<div class="row">
								<label class="L-size control-label">Vehicle:</label>
								<h5 class="L-size control-label">${TripSheet.VEHICLE_REGISTRATION}</h5>
								<label class="L-size control-label">Driver :</label>
								<h5 class="L-size control-label">${TripSheet.TRIP_DRIVER_NAME}</h5>
							</div>
							<div class="row">
								<label class="L-size control-label">TripSheet ID :</label>
								<h5 class="L-size control-label">TS-${TripSheet.TRIPCOLLNUMBER}</h5>
								<label class="L-size control-label">Conductor :</label>
								<h5 class="L-size control-label">${TripSheet.TRIP_CONDUCTOR_NAME}</h5>
							</div>
						</c:when>
						<c:when test="${tripSheetFlavor == 3}">
							<div class="row">
								<label class="L-size control-label">Vehicle:</label>
								<h5 class="L-size control-label">${TripSheet.VEHICLE_REGISTRATION}</h5>
								<label class="L-size control-label">Driver :</label>
								<h5 class="L-size control-label">${TripSheet.TRIP_DRIVER_NAME}</h5>
							</div>
							<div class="row">
								<label class="L-size control-label">TripSheet ID :</label>
								<h5 class="L-size control-label">TS-${TripSheet.TRIPDAILYNUMBER}</h5>
								<label class="L-size control-label">Conductor :</label>
								<h5 class="L-size control-label">${TripSheet.TRIP_CONDUCTOR_NAME}</h5>
							</div>
						</c:when>
					</c:choose>
					
					<form id="formFuelVendorAdd"
						action="<c:url value="/saveFuelVendor.in"/>" method="post"
						enctype="multipart/form-data" name="formFuelVendorAdd" role="form"
						class="form-horizontal" onsubmit="return validateFuel();">

						<div class="form-horizontal ">
							<fieldset>
								<legend>Fuel Entries</legend>
								<div class="box">
									<div class="box-body">
										<div class="row1">
										
										
										<c:choose>
											<c:when test="${tripSheetFlavor == 1}">
												<!-- Vehicle Id -->
												<input type="hidden" name="vid" value="${TripSheet.vid}"
													style="width: 100%;" required="required" />
												<!-- vehicle Group -->
												<input class="form-text" name="vehicle_group"
													value="${TripSheet.vehicle_Group}" type="hidden">
												<!-- TripSheet ID : -->
												<input type="hidden" class="form-text"
													name="fuel_TripsheetID" value="${TripSheet.tripSheetID}"
													required="required" />
												<!-- Fuel Vendor : -->
												<input type="hidden" name="Vendor_id" id="Vendor_id" style="width: 100%;"
													value="${Vendor.vendorId}" required="required" />
												<!-- Driver Name : -->
												<input type="hidden" name="driver_id"
													value="${TripSheet.tripFristDriverID}" style="width: 100%;"
													required="required" />
											</c:when>
											
											<c:when test="${tripSheetFlavor == 2}">
												<!-- Vehicle Id -->
												<input type="hidden" name="vid" value="${TripSheet.VID}"
													style="width: 100%;" required="required" />
												<!-- vehicle Group -->
												<input class="form-text" name="vehicle_group"
													value="${TripSheet.VEHICLE_GROUP}" type="hidden">
												<!-- TripSheet ID : -->
												<input type="hidden" class="form-text"
													name="fuel_TripsheetID" value="${TripSheet.TRIPCOLLID}"
													required="required" />
												<!-- Fuel Vendor : -->
												<input type="hidden" name="Vendor_id" id="Vendor_id" style="width: 100%;"
													value="${Vendor.vendorId}" required="required" />
												<!-- Driver Name : -->
												<input type="hidden" name="driver_id"
													value="${TripSheet.TRIP_DRIVER_ID}" style="width: 100%;"
													required="required" />
											</c:when>
											<c:when test="${tripSheetFlavor == 3}">
												<!-- Vehicle Id -->
												<input type="hidden" name="vid" value="${TripSheet.VEHICLEID}"
													style="width: 100%;" required="required" />
												<!-- vehicle Group -->
												<input class="form-text" name="vehicle_group"
													value="${TripSheet.VEHICLE_GROUP}" type="hidden">
												<!-- TripSheet ID : -->
												<input type="hidden" class="form-text"
													name="fuel_TripsheetID" id="fuel_TripsheetID" value="${TripSheet.TRIPDAILYID}"
													required="required" />
												<!-- Fuel Vendor : -->
												<input type="hidden" name="Vendor_id" id="Vendor_id" style="width: 100%;"
													value="${Vendor.vendorId}" required="required" />
												<!-- Driver Name : -->
												<input type="hidden" name="driver_id"
													value="${TripSheet.TRIP_DRIVER_ID}" style="width: 100%;"
													required="required" />
											</c:when>
										</c:choose>
					
											

											<!-- Mark as personal expense -->
											<input name="fuel_personal" type="hidden" value="0">
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
											<label class="L-size control-label" for="fuel_meter">Current
												Odometer :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
											<c:choose>
													<c:when test="${tripSheetFlavor == 1}">
														<input id="fuel_meter_old" name="fuel_meter_old"
															type="hidden" value="${TripSheet.tripOpeningKM}"> 
														<input
															class="form-text" id="fuel_meter" name="fuel_meter"
															type="text" maxlength="10"
															placeholder="${TripSheet.tripOpeningKM}"
															onkeypress="return IsNumericOdometer(event);"
															ondrop="return false;">
													</c:when>
												<c:when test="${tripSheetFlavor == 2}">
													<input id="fuel_meter_old" name="fuel_meter_old"
														type="hidden" value="${TripSheet.TRIP_OPEN_KM}"> 
													<input
														class="form-text" id="fuel_meter" name="fuel_meter"
														type="text" maxlength="10"
														placeholder="${TripSheet.TRIP_OPEN_KM}"
														onkeypress="return IsNumericOdometer(event);"
														ondrop="return false;">
												</c:when>
												<c:when test="${tripSheetFlavor == 3}">
													<input id="fuel_meter_old" name="fuel_meter_old"
														type="hidden" value="${TripSheet.TRIP_OPEN_KM}"> 
													<input
														class="form-text" id="fuel_meter" name="fuel_meter"
														type="text" maxlength="10"
														placeholder="${TripSheet.TRIP_OPEN_KM}"
														onkeypress="return IsNumericOdometer(event);"
														ondrop="return false;">
												</c:when>
											
										 </c:choose>
												
												 <span id="fuelOdometerIcon"
													class=""></span>
												<div id="fuelOdometerErrorMsg" class="text-danger"></div>

												<input name="fuel_meter_attributes" type="hidden" value="0">
											</div>
										</div>

										<div class="row1" id="grpfuelType" class="form-group">
											<label class="L-size control-label" for="fuel_type">Fuel
												Type :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
											<c:choose>
													<c:when test="${tripSheetFlavor == 1}">
														<input type="hidden" id="VehicleFuelTypeId"
															value="${TripSheet.routeName}"> 
														<input type="hidden" id="VehicleFuelType"
															value="${TripSheet.dispatchedBy}">
													</c:when>
													<c:when test="${tripSheetFlavor == 2}">
														<input type="hidden" id="VehicleFuelTypeId"
															value="${TripSheet.TRIP_ROUTE_NAME}"> 
														<input type="hidden" id="VehicleFuelType"
															value="${TripSheet.CREATEDBY}">
													</c:when>
													<c:when test="${tripSheetFlavor == 3}">
														<input type="hidden" id="VehicleFuelTypeId"
															value="${TripSheet.TRIP_ROUTE_NAME}"> 
														<input type="hidden" id="VehicleFuelType"
															value="${TripSheet.CREATEDBY}">
													</c:when>
											</c:choose>
												<select  class="form-text"
													id="fuel_type" name="fuelTypeId" style="width: 100%;">

												</select> <span id="fuelTypeIcon" class=""></span>
												<div id="fuelTypeErrorMsg" class="text-danger"></div>

											</div>
										</div>

										<div class="row1" id="grpfuelLiter" class="form-group">
											<label class="L-size control-label" for="fuel_liters">Total
												Liter :<abbr title="required">*</abbr>
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
											<label class="L-size control-label" for="fuel_price">Per
												liter Price :<abbr title="required">*</abbr>
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
										<div class="row1">
											<label class="L-size control-label" for="payMethod">Payment
												Method :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="">
													<div class="btn-group" data-toggle="buttons">
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
							<fieldset>
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
							<fieldset>
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
										<button type="submit" class="btn btn-success">Create
											Fuel Entry</button>
										<a class="btn btn-default"
											href="<c:url value="/FuelVendor/1.in"/>">Cancel</a>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/FU/FuelEnter.validate.js" />"></script>
	<script type="text/javascript">
		$(document).ready(
				function() {
					/* $("#fuel_type").select2({
						placeholder : "Please Enter Vehicle Name Search"
					}), $("#tagPicker").select2({
						closeOnSelect : !1
					}); */
					var vehicle_Fuel = document.getElementById("VehicleFuelType").value, t = "";
					var vehicleFuelId = document.getElementById("VehicleFuelTypeId").value, t = "";
					var a = [];
					var b = [];
					a = vehicle_Fuel.split(",");
					b = vehicleFuelId.split(",");
					for (var i = a.length, o = 0; i > o; o++)
						t += '<option value="'+b[o]+'" >' + a[o] + "</option>";
					t += "</option>", $("#fuel_type").html(t)

				});
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>