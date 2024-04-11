<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">

<div class="content-wrapper">
	<section class="content">
		<div class="row">
			<div class="col-md-offset-1 col-md-9 col-sm-9 col-xs-12">
				<div class="box">
					<div class="boxinside">
						<div class="box-header">
							<div class="pull-left">
								<a href="<c:url value="/open"/>"><spring:message
										code="label.master.home" /></a> / <a
									href="<c:url value="/newTripSheetEntries.in"/>">TripSheet</a> / <a
									href="<c:url value="/showTripSheet?tripSheetID=${TripSheet.tripSheetID}"/>">Show
									TripSheet</a> / <small>Add Halt TripSheet</small>
							</div>
							<div class="pull-right"></div>
						</div>
						<div class="row">
							<div class="row">
								<div class="pull-left">
									<h4>Trip Number : TS- ${TripSheet.tripSheetNumber}</h4>
								</div>
								<div class="pull-right">
									<h5>Created Date : ${TripSheet.created}</h5>
								</div>
							</div>

							<div class="row">
								<h4 align="center">
									<a href="showVehicle.in?vid=${TripSheet.vid}" data-toggle="tip"
										data-original-title="Click Vehicle Info"> <c:out
											value="${TripSheet.vehicle_registration}" />
									</a>
								</h4>
							</div>
							<div class="col-md-3"></div>
						</div>
						<div class="row">
							<h4 align="center">${TripSheet.routeName}</h4>
						</div>
						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li>Date of Journey : <a data-toggle="tip"
									data-original-title="Trip Open Date"> <c:out
											value="${TripSheet.tripOpenDate}  TO" /></a> <a
									data-toggle="tip" data-original-title="Trip Close Date"> <c:out
											value="  ${TripSheet.closetripDate}" /></a></li>
								<li>Group Service : <a data-toggle="tip"
									data-original-title="Group Service"><c:out
											value="${TripSheet.vehicle_Group}" /></a></li>
								<li>Booking No : <a data-toggle="tip"
									data-original-title="Booking No"> <c:out
											value="${TripSheet.tripBookref}" /></a></li>
								<li>Driver 1 : <a data-toggle="tip"
									data-original-title="Driver 1"> <c:out
											value="${TripSheet.tripFristDriverName}" /></a></li>
								<li>Driver 2 : <a data-toggle="tip"
									data-original-title="Driver 2"><c:out
											value="${TripSheet.tripSecDriverName}" /></a></li>
								<li>Cleaner : <a data-toggle="tip"
									data-original-title="Cleaner"><c:out
											value="${TripSheet.tripCleanerName}" /></a></li>
								<li>Opening KM : <a data-toggle="tip"
									data-original-title="Opening KM"><c:out
											value="${TripSheet.tripOpeningKM}" /></a></li>
								<li>Closing KM : <a data-toggle="tip"
									data-original-title="closing KM"> <c:out
											value="${TripSheet.tripClosingKM}" /></a></li>
							</ul>
						</div>
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
						<c:if test="${param.closeStatus eq true}">
							<div class="alert alert-danger">
								<button class="close" data-dismiss="alert" type="button">x</button>

								${VMandatory}<br> You should be close first TripSheet or
								change status or close workOrder .
							</div>
						</c:if>

						<c:if test="${!empty fuel}">
							<table id="FuelTable" class="table table-hover table-bordered">
								<thead>
									<tr>
										<th>ID</th>
										<th>Vehicle</th>
										<th>Date</th>
										<th>Close(Km)</th>
										<th>Usage</th>
										<th>Volume</th>
										<th>Amount</th>
										<th>FE</th>
										<th>Cost</th>
										<th>Actions</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${fuel}" var="fuel">
										<tr data-object-id="" class="ng-scope">
											<td><a
												href="<c:url value="/showFuel.in?FID=${fuel.fuel_id}"/>"
												data-toggle="tip" data-original-title="Click Fuel Details"><c:out
														value="FT-${fuel.fuel_Number}" /></a></td>
											<td><a
												href="<c:url value="/VehicleFuelDetails/1.in?vid=${fuel.vid}"/>"
												data-toggle="tip"
												data-original-title="Click Vehicle Details"><c:out
														value="${fuel.vehicle_registration}" /> </a></td>
											<td><c:out value="${fuel.fuel_date}" /><br>
												<h6>
													<a data-toggle="tip" data-original-title="Vendor Name">
														<c:out value="${fuel.vendor_name}" />-( <c:out
															value="${fuel.vendor_location}" /> )
													</a>
												</h6></td>
											<td><c:out value="${fuel.fuel_meter}" /></td>

											<td><c:out value="${fuel.fuel_usage} km" /></td>

											<td><abbr data-toggle="tip" data-original-title="Liters"><c:out
														value="${fuel.fuel_liters}" /></abbr> <c:if
													test="${fuel.fuel_tank_partial==1}">
													<abbr data-toggle="tip"
														data-original-title="Partial fuel-up"> <i
														class="fa fa-adjust"></i>
													</abbr>
												</c:if> <br> <c:out value="${fuel.fuel_type}" /></td>
											<td><i class="fa fa-inr"></i> <c:out
													value="${fuel.fuel_amount}" /> <br> <abbr
												data-toggle="tip" data-original-title="Price"> <c:out
														value="${fuel.fuel_price}/liters" />
											</abbr></td>
											<td><c:out value="${fuel.fuel_kml} " /> <c:if
													test="${fuel.fuel_kml != null}">
													Km/L
													</c:if></td>
											<td><c:out value="${fuel.fuel_cost} " /> <c:if
													test="${fuel.fuel_cost != null}">
													/Km
													</c:if></td>
											<td><a
												href="removeTSFuel.in?TSID=${fuel.fuel_TripsheetID}&FID=${fuel.fuel_id}"
												data-toggle="tip" data-original-title="Click Remove"><font
													color="red"><i class="fa fa-times"> Remove</i></font></a></td>
										</tr>
									</c:forEach>
									<tr>
										<td colspan="5" class="text-right"
											style="font-size: 15px; font-weight: bold;">Total Volume
											:</td>
										<td colspan="2" style="font-size: 15px; font-weight: bold;"><c:out
												value="${fTDiesel} " /></td>
									</tr>
								</tbody>
							</table>
						</c:if>
					</div>
				</div>

				<sec:authorize access="!hasAuthority('ADD_FUEL')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('ADD_FUEL')">
					<div class="row" onload="fuelvehicle()">



						<form id="formEditFuel"
							action="<c:url value="/saveTripSheetFuel.in"/>" method="post"
							enctype="multipart/form-data" name="formEditFuel" role="form"
							class="form-horizontal" onsubmit="return validateFuel();">
							
							<input type="hidden" id="vehicle_ExpectedOdameter" name="vehicle_ExpectedOdameter" />
							<input type="hidden" id="vehicle_Odometer" name="vehicle_Odometer" />
							<input type="hidden" id="validateMinOdometerInFuel" value="${validateMinOdometerInFuel}">

							<input type="hidden" name="TRIPSHEETID"
								value="${TripSheet.tripSheetID}"> <input type="hidden"
								name="PAIDBY" value="${PAIDBY}">

							<div class="form-horizontal ">
								<fieldset>
									<legend>Fuel Entries</legend>
									<div class="box">
										<div class="boxinside">
											<div class="row1" id="grpvehicleNumber" class="form-group">
												<label class="L-size control-label">Vehicle<abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<select name="vid" style="width: 100%;" id="TRIPFUELADD"
														required="required" class="select2">
														<option value="">Please Select</option>
														<option value="${TripSheet.vid}"><c:out
																value="${TripSheet.vehicle_registration}" /></option>
													</select> <span id="vehicleNumberIcon" class=""></span>
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
														id="fuel_meter" name="fuel_meter" type="number"
														maxlength="10" max="" min="0"
														onkeypress="return IsNumericOdometer(event);"
														ondrop="return false;" onblur="validateMaxOdameter();"> <span
														id="fuelOdometerIcon" class=""></span>
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
																		recent. Hence, a voided meter entry will never trigger
																		a service reminder or anything else that depends on a
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

											<div class="row1" id="grpfuelLiter" class="form-group">
												<label class="L-size control-label" for="fuel_liters">Liter
													:<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<input class="form-text" id="fuel_liters"
														name="fuel_liters" type="text" maxlength="8" min="0"
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
											<c:if test="${configuration.showDriver2}">
											<div class="row1">
											<label class="L-size control-label">Driver Name :</label>
											<div class="I-size" id="driverSelect">
												<input type="hidden" id="VehicleTODriverFuel"
													name="driver_id" style="width: 100%;" required="required"
													placeholder="Please Enter 3 or more Driver Name" />
											</div>
										</div>
										</c:if>
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
										
											<div class="row1">

												<input type="hidden" value="${TripSheet.tripFristDriverID}"
													name="driver_id" style="width: 100%;" required="required"
													placeholder="Please Enter 3 or more Driver Name" />
												<!-- driver Name -->
												<input class="form-text row1" name="driver_name"
													maxlength="25" type="hidden"
													value="${TripSheet.tripFristDriverName}"
													placeholder="Enter Fuel Driver Name"
													onkeypress="return IsVendorName(event);"
													ondrop="return false;"> <input name="fuel_personal"
													type="hidden" value="0">

											</div>

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
												<div
													class="form-group sf_switch optional fuel_entry_partial">
													<label class="L-size control-label" for="fuel_partial">Fuel
														Tank :</label>
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
											<button type="submit" onclick="return validateMaxOdameter();" class="btn btn-success">Create
												Fuel Entry</button>
											<a class="btn btn-default"
												href="<c:url value="/showTripSheet?tripSheetID=${TripSheet.tripSheetID}"/>">Cancel</a>
										</div>
									</div>
								</div>
							</div>
						</form>
					</div>
				</sec:authorize>

			</div>
		</div>
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
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/FU/FuelNewlanguage.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/FU/FuelEnter.validate.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#fuel_type, #TRIPFUELADD").select2({
				placeholder : "Please Enter Vehicle Name Search"
			}), $("#tagPicker").select2({
				closeOnSelect : !1
			});
			
			applyGpsSettings('${gpsConfiguration}');
		});
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>