<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/bootstrap-clockpicker.min.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/Fuel/1.in"/>">Fuel Entry</a>/ <small> 
						Fuel Entry  Edit</small>
				</div>
				<div class="pull-right">
						<a href="#" onclick="resetAllFeilds();"
							class="btn btn-warning btn-sm"> <span class="fa fa-refresh">
								Reset All</span>
						</a>

				</div> 
			</div>
		</div>
	</section>
		<sec:authorize access="!hasAuthority('ADD_FUEL')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('ADD_FUEL')">
			<div class="row" onload="fuelvehicle()">

				<div class="alert alert-success hide" id="showData" >
					<button class="close" data-dismiss="alert" type="button">x</button>
					 This Fuel Entry FT -<a id="fuelID" href=""></a> created successfully .
				</div>	
				
				<div class="alert alert-info hide" id="showBackDateMessage" >
					<button class="close" data-dismiss="alert" type="button">x</button>
						<label id="buttonMessageBackDate"  class="col-md-offset-1">
						</label> 
				</div>	
						
						<input type="hidden" id="vehicle_ExpectedOdameter" name="vehicle_ExpectedOdameter" />
						<input type="hidden" id="vehicle_Odometer" name="vehicle_Odometer" />
						<input type="hidden" id="validateMinOdometerInFuel" value="${validateMinOdometerInFuel}">
						<input type="hidden" id="selectAutoCredit" value="${configuration.selectAutoCredit}">
						<input type="hidden" id="validateReference" value="${configuration.validateReference}">
						<input type="hidden" id="companyId" value="${companyId}">
						<input type="hidden" id="allowGPSIntegration" value="${allowGPSIntegration}">
						<input type="hidden" id="gpsWorking" value="false">
						<input type="hidden" id="userId" value="${userId}">
						<input type="hidden" id="validateDriver1" value="${validateDriver1}">
						<input type="hidden" id="validateTripSheetNumber" value="${validateTripSheetNumber}">
						<input type="hidden" id="creatingBackDateFuel" value="false">
						<input type="hidden" id="nextFuelIdOfBackDate" value="0">
						<input type="hidden" id="nextFuelMeterOfBackDate" value="0">
						<input type="hidden" id="actualOdameterReading" value="0">
						<input type="hidden" id="validateVehicleKMPL" value="false">
						<input type="hidden" id="noOfDaysForBackDate" value="${configuration.noOfDaysForBackDate}">
						<input type="hidden" id="backDateString" value="${minBackDate}">
						<input type="hidden" id="fuelTankCapacity" value="0">
						<input type="hidden" id="previousFuelEntryCapacity" value="0">
						<input type="hidden" id="nextFuelEntryFound" value="false">
						<input type="hidden" id="validateLastFuelEntryInEdit" value="false">
						<input type="hidden" id="defaultFuelPrice" value="${defaultFuelPrice}">
						<input type="hidden" id="firstFuelEntry">
						<input type="hidden" id="validateOdometerInFuel" value="${validateOdometerInFuel}">
						<input type="hidden" id="lastFuelAsOpen" value="${configuration.lastFuelAsOpen}">
						<input type="hidden" id="fuel_id" value="${fuel_id}">
						<input type="hidden" id="fuelNumber">
						<input type="hidden" id="previousFuelDate">
						<input type="hidden" id="previousFuelAmount">
						<input type="hidden" id="minOdometer">
						<input type="hidden" id="maxOdometer">
						<input type="hidden" id="doNotValidateBackDateEntries" value="${configuration.doNotValidateBackDateEntries}">
						<input type="hidden" id="bindMinMaxOdometerOnTripSheet" value="${configuration.bindMinMaxOdometerOnTripSheet}">
						<input type="hidden" id="allowToAddFuelAmount" value="${configuration.allowToAddFuelAmount}">
						<input type="hidden" id="serverDate" value="${serverDate}">
						<input type="hidden" id="roundFigureAmount" value="${configuration.roundFigureAmount}">
						<input type="hidden" id="maxStockQuantity" value="0">
						<input type="hidden" id="avgPrice" value="0">
						<input type="hidden" id="ownPetrolPump" value="0">
						<input type="hidden" id="preOwnPetrolPump" value="0">
						<input type="hidden" id="defaultDriverIdForVehicle">
						<input type="hidden" id="defaultDriverNameForVehicle">
						<input type="hidden" id="allowManualOdometerEntry" value="${configuration.allowManualOdometerEntry}">
						<input type="hidden" id="maxFuelCapacity" value="${maxFuelCapacity}">
						<input type="hidden" id="getStockFromInventoryConfig" value="${getStockFromInventory}">
						<input type="hidden" id="isEditFuelEntry" value=true>
						<input type="hidden" id="fuelInvoiceId">
						<input type="hidden" id="oldFuelInvoiceId">
						<input type="hidden" id="oldVendorId">
						<input type="hidden" id="maxFuelLiters" value="${configuration.maxFuelLiters}">
						<input type="hidden" id="maxFuelPrice" value="${configuration.maxFuelPrice}">
						<input type="hidden" id="previousFuelPrice">
						<input type="hidden" id="twelveHourClock" value="${configuration.showTwelveHourClockFormat}" >
						<div class="form-horizontal ">
						<input type="hidden" id="paymentOption" value="${configuration.fuleEntryPaymentOptions}">
						<input type="hidden" id="isEditpage" value="true">
							<fieldset>
								<div class="box">
									<div class="box-body">
									
									<div class="row1">
											<label class="col-md-1 col-sm-2 col-xs-12 control-label">Vehicle :<abbr
												title="required">*</abbr></label>
											<div class="col-md-4 col-sm-3 col-xs-12">
												<input type="hidden" id="FuelSelectVehicle" name="vid"
													style="width: 100%;" required="required"  readonly="readonly"
													placeholder="Please Enter 2 or more Vehicle Name" /> 
											</div>
											<label class="col-md-1 col-sm-1 col-xs-12 control-label">Vehicle Group:
											</label>
											<div class="col-md-4 col-sm-3 col-xs-12">
												<input class="form-text" id="vehicle_group"
													required="required" name="vehicle_group" type="text"
													readonly="readonly">
											</div>
									</div>
									
										<div class="row1">
											<label class="col-md-1 col-sm-1 col-xs-12 control-label">Date <abbr title="required">*</abbr>
											</label>
											<div class="col-md-4 col-sm-3 col-xs-12">
												<div class="input-group input-append date" id="StartDate1">
													<input type="text" class="form-text" name="fuel_date" onchange="clearTime();"
														id="fuelDate" data-inputmask="'alias': 'dd-mm-yyyy'" readonly="readonly"
														data-mask="" /> <span class="input-group-addon add-on">
														<span class="fa fa-calendar"></span>
													</span>
												</div>

											</div>
											<label class="col-md-1 col-sm-1 col-xs-12 control-label">Time
											<c:if test="${configuration.allowFuelEntryTime}">
													<abbr title="required">*</abbr>
												</c:if>		
											</label>
											<div class="col-md-4 col-sm-3 col-xs-12">
												<div class="input-group clockpicker">
															<input type="text" class="form-text" readonly="readonly"
																name="fuelTime" id="fuelTime" required="required" onchange="getGPSAndActiveTripData(false);"> <span
																class="input-group-addon"> <span
																class="fa fa-clock-o" aria-hidden="true"></span>
															</span>
														</div>
											</div>
									</div>
									<div class="row1">
											<label class="col-md-1 col-sm-1 col-xs-12 control-label">TripSheet ID :
											</label>
											<div class="col-md-4 col-sm-3 col-xs-12">
												<select style="width: 100%;" name="fuel_TripsheetNumber" id='tripsheetNumberSelect' class="form-text"
														required>
													</select>
													<input type="hidden" id="showTripSheetDropDown" value="${configuration.showTripSheetDropDown}"/>
											</div>
											<label class="col-md-1 col-sm-1 col-xs-12 control-label">Fuel
												Type :<abbr title="required">*</abbr>
											</label>
											<div class="col-md-4 col-sm-3 col-xs-12">
												<select id="fuel_type" name="fuelTypeId"
													style="width: 100%;">
												</select>
											</div>
									</div>
									
										<div class="row1">
										
											<label class="col-md-1 col-sm-1 col-xs-12 control-label">Old
												Odometer :
											</label>
											<div class="col-md-4 col-sm-3 col-xs-12">
												<input type="hidden" id="prefuel_meter_old">
												<input type="text" onkeypress="return isNumberKey(event);" class="form-text" id="fuel_meter_old" name="fuel_meter_old" readonly="readonly">
											</div>
											
											<input type="hidden" id="maxOdometerAsTripCloseKM" value="${configuration.maxOdometerAsTripCloseKM}"/>
											<input type="hidden" id="tripStatusId" value="" />
											<input type="hidden" id="tripClosingKM" value="" />
											
											<label class="col-md-1 col-sm-1 col-xs-12 control-label">Odometer<abbr
												title="required">*</abbr>
											</label>
											<div class="col-md-4 col-sm-3 col-xs-12">
												<input class="form-text"
													id="fuel_meter" name="fuel_meter" type="number" min="0"
													max="" onkeypress="return IsNumericOdometer(event);" onkeyup="validateMaxOdameter();"
													ondrop="return false;" onblur="validateMaxOdameter();"> <span id="fuelOdometerIcon"
													class=""></span>
												<div id="fuelOdometerErrorMsg" class="text-danger"></div>
												<div id="backDateRange" style="display: none; font-size: 14px;">
													 <label id="odometerRangeBackDate" class="error">
														</label> 
												</div>	
												<label class="error" id="errorOdometer"
													style="display: none"></label> <label class="error"
													id="errorOdo"></label>
												
											</div>
									</div>
									<div class="row1">
											<label class="col-md-1 col-sm-2 col-xs-12 control-label"></label>
											<div class="col-md-4 col-sm-3 col-xs-12">
												<div class="help-block checkbox">
													<label for="fuel_meter_entry_attributes_void"> <input
														name="fuel_meter_attributes" id="fuel_meter_attributes" 
														type="hidden" value="0">
														<input id="meter_attributes"
														name="meter_attributes" type="checkbox" value="1" onclick="changeOpenOdometer();">
														Meter Not Working</strong> <a data-toggle="modal"
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
											<div id="gpsOdometerRow" style="display: none;">
												<label class="col-md-1 col-sm-2 col-xs-12 control-label">GPS
													Odometer :</label>
												<div class="col-md-4 col-sm-3 col-xs-12">
													<input type="text" class="form-text" id="gpsOdometer" name="gpsOdometer" readonly="readonly">
												</div>
											</div>
									</div>
									
									
										<div class="row1">
											<label class="col-md-1 col-sm-2 col-xs-12 control-label">Vendor :<abbr
												title="required">*</abbr></label>
											<div class="col-md-4 col-sm-3 col-xs-12">
												<input type="hidden" id="selectVendor" name="vendor_name"
													style="width: 100%;" value="0"
													placeholder="Please Select Vendor Name" />
													<p id="stock" style="color: blue;"> </p>
											</div>
											<label class="col-md-1 col-sm-1 col-xs-12 control-label">Liter
												:<abbr title="required">*</abbr>
											</label>
											<div class="col-md-4 col-sm-3 col-xs-12">
												<input class="form-text" id="fuel_liters" name="fuel_liters"
													type="text" maxlength="6" min="0" onkeyup="calculateFuelRate();"
														onkeypress="return isNumberKeyWithDecimal(event,this.id)"
													ondrop="return false;">
												<p class="help-block">ex: 23.78</p>
											</div>
									</div>
									<div class="row1" id="priceAndAmount">
											<label class="col-md-1 col-sm-2 col-xs-12 control-label">Price/Unit
												:<abbr title="required">*</abbr></label>
											<div class="col-md-4 col-sm-3 col-xs-12">
												<input class="form-text" id="fuel_price" name="fuel_price"
													type="text" maxlength="6" min="0"  onkeyup="calculateFuelAmount();"
													onkeypress="return isNumberKeyWithDecimal(event,this.id)"
													ondrop="return false;">
													<p class="help-block">ex: 56.78</p>
											</div>
											<label class="col-md-1 col-sm-1 col-xs-12 control-label">Total Amount :
											</label>
											<div class="col-md-4 col-sm-3 col-xs-12">
												<input class="form-text" id="fuelAmount" name="fuelAmount"
													type="text" maxlength="8" min="0" readonly="readonly" 
													 onkeypress="return isNumberKeyWithDecimal(event,this.id);"
													
													ondrop="return false;">
											</div>
									</div>
									<div class="row1">
											<label class="col-md-1 col-sm-1 col-xs-12 control-label">Ref/Bill No:
												<c:if test="${configuration.validateReference}">
													 <abbr title="required">*</abbr> 
												</c:if>											
											</label>
											<div class="col-md-4 col-sm-3 col-xs-12">
												<input class="form-text" id="fuel_reference" maxlength="50"
														name="fuel_reference" type="text"
														onkeypress="return IsReference(event);"
														ondrop="return false;">
											</div>
											
											<c:choose>
													<c:when test="${configuration.fuleEntryPaymentOptions}">
														<label class="col-md-1 col-sm-1 col-xs-12 control-label">Payment
																	Method :
														</label>
														<div style="width: 120%" id="paymentDiv">
															<div class="col-md-2">
																<select class="form-text" name="ADVANCE_PAID_TYPE_ID"
																	id="renPT_option" required="required">
																		<option value="1">CASH</option>
																		<option value="2">CREDIT</option>
																		<option value="11">UPI</option>
																		<option value="3">NEFT</option>
																		<option value="4">RTGS</option>
																		<option value="5">IMPS</option>
																		<option value="12">CARD</option>
																		<option value="7">CHEQUE</option>
																</select>
															</div>	
														</div>										
													</c:when>
													<c:otherwise>
													<div style="width: 120%" id="ShowOtherPayOption">
														<label class="col-md-1 col-sm-1 col-xs-12 control-label">Payment
															Method :
														</label>
														<div class="col-md-4 col-sm-3 col-xs-12">
															<div class="btn-group" id="status" data-toggle="buttons">
																	<label id="debitlebel" class="btn btn-default btn-on btn-lg">
																		<input type="radio" value="1" name="paymentTypeId"
																		id="paymentTypeId">Cash
																	</label> <label id="creditlebel" class="btn btn-default btn-off btn-lg"> <input
																		type="radio" value="2" name="paymentTypeId" id="paymentTypeCreditId">Credit
																	</label>
																</div>
											<div style="width: 120%" id="ShowOtherPayOption">
												<label class="col-md-1 col-sm-1 col-xs-12 control-label">Payment
													Method :
												</label>
												<div class="col-md-4 col-sm-3 col-xs-12">
													<div class="btn-group" id="status" data-toggle="buttons">
															<label id="debitlebel" class="btn btn-default btn-on btn-lg">
																<input type="radio" value="1" name="paymentTypeId"
																id="paymentTypeId">Cash
															</label> <label id="creditlebel" class="btn btn-default btn-off btn-lg"> <input
																type="radio" value="2" name="paymentTypeId" id="paymentTypeCreditId">Credit
															</label>
											<input type="hidden" value="${configuration.paidByOptionInAddFuleEntry}" id="paidByOptionInAddFuleEntry">
											
											<div style="width: 120%" id="ShowOtherPayOption">
												<label class="col-md-1 col-sm-1 col-xs-12 control-label">Payment
													Method :
												</label>
												<div class="col-md-4 col-sm-3 col-xs-12">
													<div class="btn-group" id="status" data-toggle="buttons">
															<label id="debitlebel" class="btn btn-default btn-on btn-lg">
																<input type="radio" value="1" name="paymentTypeId"
																id="paymentTypeId">Cash
															</label> <label id="creditlebel" class="btn btn-default btn-off btn-lg"> <input
																type="radio" value="2" name="paymentTypeId" id="paymentTypeCreditId">Credit
															</label>
														</div>
													</div>
													<div  style="width: 120%" id="PayModeOption">
														<label class="col-md-1 col-sm-1 col-xs-12 control-label">Payment
															Method :
														</label>
														<div class="col-md-4 col-sm-3 col-xs-12">
															<div class="btn-group" id="status" data-toggle="buttons">
																<label class="btn btn-default btn-on btn-lg active">
																	<input type="radio" value="1" name="paymentTypeId"
																	id="paymentTypeId" checked="checked">Cash
																</label>
															</div>
														</div>
													</div>
											</c:otherwise>
										</c:choose>
									</div>
									<div class="row1">
											<div class="I-size" id="partialFuelSuggestion" style="display: none;">
											    <label class="error" class="col-md-offset-1">
											 	Suggestion : As per previous fuel entry, this fuel entry should be Partial.
												</label> 
											</div>
										
											<div class="I-size" id="fullFuelSuggestion" style="display: none;">
												 <label class="error" class="col-md-offset-1">
												 	Suggestion : As per previous fuel entry, this fuel entry should be Full.
												</label> 
											</div>
										</div>
									
									<input type="hidden" id="paidByOptionInAddFuleEntry" value="${configuration.paidByOptionInAddFuleEntry}">	
									<c:if test="${configuration.paidByOptionInAddFuleEntry}">
									  <div id="paidByOption">
										<div class="row1" style="width: 97%">
											<label class="col-md-1 col-sm-2 col-xs-12 control-label">Paid By:
												<abbr title="required">*</abbr></label>
												
											<div class="col-md-4 col-sm-3 col-xs-12">
												
												<select id="paidBy" name="paidBy" class="form-text" onChange="Branch();" >
												  <option value="1">Paid By Driver</option>
												  <option value="2">Paid By Branch</option>
												</select>
											</div>
											
											<div id="showBranch">
												<label class="col-md-1 col-sm-1 col-xs-12 control-label">Branch :
												</label>
												<div class="col-md-4 col-sm-3 col-xs-12">
													<input type="hidden" id="selectBranch" name="branch_name"
													style="width: 95%;" value="0" placeholder="Please Select banch Name" />
												</div>
											</div>
										</div>
									  </div>
									</c:if>
									
									<div class="row1">
											<label class="col-md-1 col-sm-2 col-xs-12 control-label">Fuel
													Tank :</label>
											<div class="col-md-4 col-sm-3 col-xs-12">
												<div class="btn-group" id="status" data-toggle="buttons">
															<label id="fullTankLabel" class="btn btn-default btn-on btn-lg">
																<input type="radio" value="0" name="fuel_tank"
																id="fuel_tank">Full
															</label> <label  id="partialTankLabel" class="btn btn-default btn-off btn-lg"> <input
																type="radio" value="1" name="fuel_tank" id="fuel_tank_partial">Partial
															</label>
														</div>
														<p class="help-block">
														Note : Turn On <b>"Partial"</b> if the tank is <strong>not
															filled up to "full"</strong>.
													</p>
											</div>
											
									</div>
										
									<div class="row1">
											<label class="col-md-1 col-sm-2 col-xs-12 control-label">Driver1 :<abbr
												title="required">*</abbr></label>
											<div class="col-md-4 col-sm-3 col-xs-12" id="driverSelect">
												<input type="hidden" id="DriverFuel"
													name="driver_id" style="width: 100%;" required="required"
													 />
											</div>
											<c:if test="${configuration.tallyImportRequired}">
												<label class="col-md-1 col-sm-1 col-xs-12 control-label">Tally Company :<abbr title="required">*</abbr>
												</label>
												<div class="col-md-4 col-sm-3 col-xs-12">
													<input type="hidden" id="tallyCompanyId" name="tallyCompanyId" style="width: 100%;" 
														  placeholder="Please Enter Tally Company Name" />
												</div>
											</c:if>
									</div>
									<div class="row1">
										<c:if test="${configuration.showDriver2}">
											<label class="col-md-1 col-sm-2 col-xs-12 control-label">Driver2 :<abbr
												title="required">*</abbr></label>
											<div class="col-md-4 col-sm-3 col-xs-12">
												<input type="hidden" id="Driver2Fuel"
													name="secDriverID" style="width: 100%;" required="required"
													placeholder="Please Enter 3 or more Driver Name" />
											</div>
											</c:if>
											<c:if test="${configuration.showCleaner}">
												<label class="col-md-1 col-sm-1 col-xs-12 control-label">Cleaner : 
												</label>
												<div class="col-md-4 col-sm-3 col-xs-12">
													<input type="hidden" id="VehicleTOCleanerFuel"
													name="cleanerID" style="width: 100%;" required="required"
													placeholder="Please Enter 3 or more Driver Name" />
												</div>
											</c:if>
									</div>
									<div class="row1">
										<c:if test="${configuration.showRoute}">
											<label class="col-md-1 col-sm-2 col-xs-12 control-label">Route :<abbr
												title="required">*</abbr></label>
											<div class="col-md-4 col-sm-3 col-xs-12">
												<input type="hidden" id="FuelRouteList" name="routeID"
													style="width: 100%;" value="0" required="required"
													placeholder="Please Enter 3 or more Route Name, NO " />
											</div>
											</c:if>
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
														style="display: none"> </label> 
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
										<!-- <button onclick="saveFuelEntry();" type="submit" class="btn btn-success">Create
											Fuel Entry</button> -->
										<input type="button" value="Update" id="upSubmit" onclick="updateFuelEntry();" class="btn btn-success" />	
										<a class="btn btn-default" href="<c:url value="/Fuel/1.in"/>">Cancel</a>
									</div>
								</div>
							</div>
						</div>
			</div>
			
		</sec:authorize>
	<%@ include file="/WEB-INF/view/payment/PaymentDetails.jsp"%>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/FU/FuelNewlanguage.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/FU/FuelEnter.validate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/FU/FuelAdd.js" />"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/FU/VehicleGPSDetails.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/FU/FuelEntriesEdit.js" />"></script>	
			<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>
	<script type="text/javascript">
	
	var previousDate = $('#backDateString').val();
	var serverDate	= $('#serverDate').val();	
	var previousDateForBackDate =   previousDate.split("-")[0] + '-' +  previousDate.split("-")[1] + '-' +  previousDate.split("-")[2];
	var serverDateStr =   serverDate.split("-")[0] + '-' +  serverDate.split("-")[1] + '-' +  serverDate.split("-")[2];
		$(function() {
			$("#fuelDate").datepicker({
				 defaultDate: serverDateStr,
			       autoclose: !0,
			       todayHighlight: !0,
			       format: "dd-mm-yyyy",
			       setDate: "0",
			       endDate: serverDateStr,
				   startDate:previousDateForBackDate
			   })
		}); 
		
		$(document).ready(function() {
			
			$("#fuel_type").select2({
				placeholder : "Please Fuel  Type"
			}), $("#tagPicker").select2({
				closeOnSelect : !1
			});
			
			applyGpsSettings('${gpsConfiguration}');

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
				if((${showMarkAsVoidAutoSelected})) {
					$("#meter_attributes").prop('checked', true);
				}
				if($('#allowToAddFuelAmount').val() == 'true' || $('#allowToAddFuelAmount').val() == true) {
					$('#fuelAmount').attr('readonly', false);
					$('#fuel_price').attr('readonly', true);
				}
		});
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/bootstrap-clockpicker.min.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("dd-mm-yyyy", {
				placeholder : "dd-mm-yyyy"
			}), $("[data-mask]").inputmask()


			if($("#twelveHourClock").val() == "true"){
				$('.clockpicker').clockpicker({
					placement: 'bottom',
					align: 'right',
					autoclose: true,
					twelvehour: true
				});
			}
			else{
				$('.clockpicker').clockpicker({
					placement: 'bottom',
					align: 'right', 
					autoclose: true
				});
			}	
		});
		 function Branch() {
			    // Function code here
			    console.log("inside select branch function ... ");
			    var selectElement = document.getElementsByName("paidBy")[0]; // Get the select element
  				var selectedOption = selectElement.value;
  				
			    if(document.querySelector('select[name="paidBy"]').value == '2')
				{
					$("#showBranch").show();
				}
			    else
				{
					$("#showBranch").hide();
				}
			    
			    // Additional code...
			  }
			 
		
	</script>
</div>