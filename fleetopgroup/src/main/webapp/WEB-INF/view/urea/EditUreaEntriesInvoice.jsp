<%@ include file="../taglib.jsp"%>
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
						href="UreaEntriesShowList.in">Urea Entry</a> / <small>Edit
						Urea Entry</small>
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

					<%-- <form id="formFuel" action="<c:url value="/saveFuel.in"/>"
						method="post" enctype="multipart/form-data" name="formFuel"
						role="form" class="form-horizontal"
						onsubmit="return validateFuel();"> --%>
				<div class="alert alert-success hide" id="showData" >
					<button class="close" data-dismiss="alert" type="button">x</button>
					 This Urea Entry UT -<a id="fuelID" href=""></a> created successfully .
				</div>		
						
					<form method="POST" enctype="multipart/form-data" id="fileUploadForm">
						<input type="hidden" id="ureaEntriesInvoiceId" value="${UreaEntriesInvoiceId}">	
						<input type="hidden" id="vid" >	
						<input type="hidden" id="ureaEntriesNumber" >	
						<input type="hidden" id="ureaInvoiceToDetailsId" >	
						<input type="hidden" id="newUreaInvoiceToDetailsId" >	
						<input type="hidden" id="minOdometer">
						<input type="hidden" id="maxOdometer">
						<input type="hidden" id="companyId" value="${companyId}">
						<input type="hidden" id="isNextUreaEntry" >
						<input type="hidden" id="nextUreaEntryId" >
						<input type="hidden" id="tempFuel_meter" >
						<input type="hidden" id="tempUreaOdometerOld" >
						<input type="hidden" id="isEdit" value="true" >
						<input type="hidden" id="serverDate" value="${serverDate}">
						<input type="hidden" id="showFilledLocation" value="${configuration.showFilledLocation}">
						<div class="form-horizontal ">
							<fieldset>
								<legend>Edit Urea Entries</legend>
								<div class="box">
									<div class="box-body">
										<div class="row1" id="grpvehicleNumber" class="form-group">
											<label class="L-size control-label" for="FuelSelectVehicle">Vehicle<abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input type="text" id="FuelSelectVehicle" name="vid"
													style="width: 100%;" readonly="readonly" class="form-text"
													 /> <span
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
														class="form-text" name="fuel_TripsheetNumber" value="${UreaEntriesInvoice.tripSheetNumber}"
														placeholder="eg: 160" maxlength="8" id="fuel_TripsheetNumber"
														onkeypress="return isNumberKey(event);" ondrop="return false;"
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
													<input type="text" class="form-text" name="fuel_date" readonly="readonly"
														id="fuelDate" required="required"  onchange="getPreNextUreaEntiresByDate();"
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

											 <input class="form-text"
												id="fuel_meter" name="fuel_meter" type="number" min="0"
												max="" onkeypress="return isNumberKey(event);" onkeyup="validateMaxOdameter();" onblur="validateMaxOdameter();" placeholder="0"
												ondrop="return false;" > <span id="fuelOdometerIcon"
												class=""></span>
												<div id="odometerRangeDiv" style="display: none; font-size: 14px;">
													<label id="odometerRange" class="error"> </label> 
												</div>
												<input type="hidden" class="form-text" id="ureaOdometerOld" 
												name="ureaOdometerOld" readonly="readonly">
												<input type="hidden" class="form-text" id="maxQuantity" name="maxQuantity" readonly="readonly">
												<input type="hidden" class="form-text" id="manufacturerId" name="manufacturerId" readonly="readonly">
												<input type="hidden" class="form-text" id="wareHouseLocation" name="wareHouseLocation" readonly="readonly">	
												<input type="hidden" class="form-text" id="vehicle_ExpectedOdameter" name="vehicle_ExpectedOdameter" readonly="readonly">	
											</div>
										</div>
										<div class="row1" id="grpfuelOdometer" class="form-group">
											<label class="L-size control-label" for="fuel_meter">Old Odometer<abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input class="form-text" id="fuel_meter_old" name="fuel_meter" onkeyup="validateMaxOdameter();" placeholder="0"
													type="number" min="0" max="" onkeypress="return isNumberKey(event);" onblur="validateMaxOdameter();" readonly ="readonly"
													ondrop="return false;" > 
												<span id="fuelOdometerIcon" class=""></span>
											</div>
										</div>
										<sec:authorize access="hasAuthority('METER_NOT_WORKING')">
										<div class="row1" >
											<label class="col-md-1 col-sm-2 col-xs-12 control-label"></label>
											<div class="col-md-4 col-sm-3 col-xs-12">
												<div class="help-block checkbox">
													<label for="fuel_meter_entry_attributes_void"> <input
														name="fuel_meter_attributes" id="fuel_meter_attributes" 
														type="hidden" >
														<input id="meter_attributes"
														name="meter_attributes" type="checkbox"  onclick="validateMaxOdameter();">
														Meter Not Working/Reset</strong> <a data-toggle="modal"
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
									</sec:authorize>
										<div class="row1">
											<label class="L-size control-label">From Location Stock :<abbr
												title="required">*</abbr></label>
											<div class="I-size" id="driverSelect">
												<input type="hidden" id="ureaLocation"
													name="driver_id" style="width: 100%;" required="required"
													 />
											</div>
										</div>
										
										<div class="row1" id="grpfuelPrice" class="form-group">
											
											<div class="col-md-2" style="padding-left: 220px;">
											<label class="L-size control-label" for="fuel_price">Price/Unit</label>
												<input class="form-text" id="ureaPrice" name="ureaPrice"
													type="text" maxlength="8" min="0" readonly="readonly"
													onkeypress="return isNumberKey(event,this);"
													ondrop="return false;" >
												<p class="help-block">ex: 56.78</p>
											</div>
											<div class="col-md-2">
											<label class="L-size control-label" for="fuel_price">Dis :</label>
												<input class="form-text" id="discount" name="discount"
													type="text" maxlength="8" min="0" readonly="readonly"
													onkeypress="return isNumberKey(event,this);"
													ondrop="return false;">
											</div>
											<div class="col-md-2">
											<label class="L-size control-label" for="fuel_price">GST :</label>
												<input class="form-text" id="gst" name="gst"
													type="text" maxlength="8" min="0" readonly="readonly"
													onkeypress="return isNumberKey(event,this);"
													ondrop="return false;">
											</div>
											
										</div>
										
										<div class="row1" id="grpfuelLiter" class="form-group">
											<label class="L-size control-label" for="fuel_liters">Liter
												:<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input class="form-text" id="ureaLiters" name="ureaLiters"
													type="text" maxlength="8" min="0"  onblur="return validateUreaLiters();"
													onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="sumthere('ureaLiters','ureaPrice','discount', 'gst','ureaAmount');"
													ondrop="return false;">
												<p class="help-block">ex: 23.78</p>

											</div>
										</div>
										
										<div class="row1" id="grpfuelLiter" class="form-group">
											<label class="L-size control-label" for="fuel_liters">Urea Amount
												:<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input class="form-text" id="ureaAmount" name="ureaAmount"
													type="text" maxlength="8" min="0" readonly="readonly"
												>
											</div>
										</div>
										
										
										<div class="row1" id="grpfuelReference" class="form-group">
											<label class="L-size control-label" for="fuel_reference">Reference
												:
											</label>
											<div class="I-size">
												<input class="form-text" id="fuel_reference" maxlength="50"
													name="fuel_reference" type="text"
													onkeypress="return IsReference(event);"
													ondrop="return false;">
												<p class="help-block">Optional (e.g. invoice number,
													transaction ID, etc.)</p>

											</div>
										</div>
										<c:if test="${!configuration.hideDriverAndRoute}">
										<div class="row1">
											<label class="L-size control-label">Driver Name :</label>
											<div class="I-size" id="driverSelect">
												<input type="hidden" id="DriverFuel"
													name="driver_id" style="width: 100%;" required="required"
													placeholder="Please Enter 3 or more Driver Name" />
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Driver2 Name :</label>
											<div class="I-size" id="driverSelect">
												<input type="hidden" id="Driver2Fuel"
													name="secDriverID" style="width: 100%;" required="required"
													placeholder="Please Enter 3 or more Driver Name" />
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Cleaner :</label>
											<div class="I-size" id="driverSelect">
												<input type="hidden" id="CleanerFuel"
													name="cleanerID" style="width: 100%;" required="required"
													placeholder="Please Enter 3 or more Driver Name" />
											</div>
										</div>
										<div class="row1">
											<label class="L-size  control-label">Route Service :</label>
											<div class="I-size" id="driverSelect">
												<input type="hidden" id="FuelRouteList" name="routeID"
													style="width: 100%;" value="0" required="required"
													placeholder="Please Enter 3 or more Route Name, NO " />
											</div>
										</div>
										</c:if>
											<c:if test="${configuration.showFilledLocation}">
											<div class="row1">
											<label class="L-size control-label">Filled Location :</label>
											<div class="I-size" id="driverSelect">
												<input type="hidden" id="subLocationId"
													name="subLocationId" style="width: 100%;" required="required"
													placeholder="Please Enter 2 or more Words" />
											</div>
										</div>
										
										<div class="row1" class="form-group">
											<label class="L-size control-label" for="filledBy">Filled By
												:
											</label>
											<div class="I-size">
												<input class="form-text" id="filledBy" maxlength="50"
													name="filledBy" type="text"
													ondrop="return false;">

											</div>
										</div>
									</c:if>
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
										<input type="button" value="Update" onclick="updateUreaEntry();" id="btnSubmit" class="btn btn-success" />	
										<a class="btn btn-default" href="UreaEntriesShowList.in">Cancel</a>
									</div>
								</div>
							</div>
						</div>
					  </form>	
					<!-- </form> -->
				</div>
			</div>
		</sec:authorize>
	</section>
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/urea/EditUreaEntriesInvoice.js" />"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/urea/UreaEntriesValidate.js" />"></script>		
	<script type="text/javascript">
		$(document).ready(function() {
			$("#fuel_type").select2({
				placeholder : "Please Enter Vehicle Name Search"
			}), $("#tagPicker").select2({
				closeOnSelect : !1
			});
			
		});
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
		
		<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script> 
		
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>