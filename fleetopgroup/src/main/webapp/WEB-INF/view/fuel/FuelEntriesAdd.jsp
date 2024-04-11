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
						href="<c:url value="/Fuel/1.in"/>">Fuel Entry</a><c:if test="${!noTripSheet}">/<a href="showTripSheet.in?tripSheetID=${TripSheet.tripSheetID}"> TS- ${TripSheet.tripSheetNumber}</a></c:if> / <small>Add
						Fuel Entry</small>
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
	<!-- Main content -->
	<section class="content">
			<div class="box">
			<input type="hidden" id="roundFigureAmount" value="${configuration.roundFigureAmount}">
			<input type="hidden" id="ownPetrolPump" value="0">
					<c:if test="${!noTripSheet}">
						<div class="boxinside">
						<div class="row">
							<div class="row">
								<div class="pull-left">
									<h4>Trip Number : <a href="showTripSheet.in?tripSheetID=${TripSheet.tripSheetID}">TS- ${TripSheet.tripSheetNumber}</a></h4>
									<input type="hidden" id="tripSheetNumber" value="${TripSheet.tripSheetNumber}"/>
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
									<input type="hidden" id="vid" value="${TripSheet.vid}" />
									<input type="hidden" id="vehicle_registration" value="${TripSheet.vehicle_registration}" />
									<input type="hidden" id="tripFristDriverID" value="${TripSheet.tripFristDriverID}">
									<input type="hidden" id="tripSecDriverID" value="${TripSheet.tripSecDriverID}">
									<input type="hidden" id="tripCleanerID" value="${TripSheet.tripCleanerID}">
									<input type="hidden" id="routeID" value="${TripSheet.routeID}">
									<input type="hidden" id="routeName" value="${TripSheet.routeName}">
									<input type="hidden" id="tripFristDriverName" value="${TripSheet.tripFristDriverName}">
									<input type="hidden" id="tripSecDriverName" value="${TripSheet.tripSecDriverName}">
									<input type="hidden" id="tripCleanerName" value="${TripSheet.tripCleanerName}">
									<input type="hidden" id="tripOpenDateStr" value="${TripSheet.tripOpenDate}">
									<input type="hidden" id="tripCloseDateStr" value="${TripSheet.closetripDate}">
									
									<input type="hidden" id="showDriverFullName" value="${configuration.showDriverFullNameInFuel}">
									<input type="hidden" id="tripFristDriverFatherName" value="${TripSheet.tripFristDriverFatherName}">
									<input type="hidden" id="tripFristDriverLastName" value="${TripSheet.tripFristDriverLastName}">
									<input type="hidden" id="tripSecDriverFatherName" value="${TripSheet.tripSecDriverFatherName}">
									<input type="hidden" id="tripSecDriverLastName" value="${TripSheet.tripSecDriverLastName}">
									
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
								
								<li>Driver 1 : <a data-toggle="tip" data-original-title="Driver 1"> 
<%-- 									<c:if test="${configuration.showDriverFullNameInFuel}"> --%>
										<c:out value="${TripSheet.tripFristDriverName}  ${TripSheet.tripFristDriverLastName} - ${TripSheet.tripFristDriverFatherName}" />
<%-- 									</c:if>			 --%>
<%-- 									<c:if test="${!configuration.showDriverFullNameInFuel}"> --%>
<%-- 										<c:out value="${TripSheet.tripFristDriverName}" /> --%>
<%-- 									</c:if>			 --%>
								</a></li>
								<li>Driver 2 : <a data-toggle="tip" data-original-title="Driver 2">
<%-- 									<c:if test="${configuration.showDriverFullNameInFuel}"> --%>
										<c:out value="${TripSheet.tripSecDriverName}  ${TripSheet.tripSecDriverLastName} - ${TripSheet.tripSecDriverFatherName}" />
<%-- 									</c:if> --%>
<%-- 									<c:if test="${!configuration.showDriverFullNameInFuel}"> --%>
<%-- 										<c:out value="${TripSheet.tripSecDriverName}" /> --%>
<%-- 									</c:if> --%>
								</a></li>
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
						
			</c:if>
					
		<sec:authorize access="!hasAuthority('ADD_FUEL')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('ADD_FUEL')">
		<div class="box">
		<div class="box-body">
			<div class="row" onload="fuelvehicle()">
				<div class="col-md-offset-1 col-md-9">

				<div class="alert alert-success hide" id="showData" >
					<button class="close" data-dismiss="alert" type="button">x</button>
					 This Fuel Entry FT -<a id="fuelID" href=""></a> created successfully .
				</div>	
				
				<div class="alert alert-info hide" id="showBackDateMessage" >
					<button class="close" data-dismiss="alert" type="button">x</button>
						<label id="buttonMessageBackDate"  class="col-md-offset-1">
						</label> 
				</div>	
						
					<form method="POST" enctype="multipart/form-data" id="fileUploadForm">	
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
						<input type="hidden" id="serverDate" value="${serverDate}">
						<input type="hidden" id="fuelTankCapacity" value="0">
						<input type="hidden" id="previousFuelEntryCapacity" value="0">
						<input type="hidden" id="nextFuelEntryFound" value="false">
						<input type="hidden" id="validateLastFuelEntryInEdit" value="false">
						<input type="hidden" id="defaultFuelPrice" value="${defaultFuelPrice}">
						<input type="hidden" id="firstFuelEntry">
						<input type="hidden" id="validateOdometerInFuel" value="${validateOdometerInFuel}">
						<input type="hidden" id="lastFuelAsOpen" value="${configuration.lastFuelAsOpen}">
						<input type="hidden" id="tripSheetId" value="${tripSheetId}">
						<input type="hidden" id="minOdometer">
						<input type="hidden" id="maxOdometer">
						<input type="hidden" id="doNotValidateBackDateEntries" value="${configuration.doNotValidateBackDateEntries}">
						<input type="hidden" id="bindMinMaxOdometerOnTripSheet" value="${configuration.bindMinMaxOdometerOnTripSheet}">
						<input type="hidden" id="allowToAddFuelAmount" value="${configuration.allowToAddFuelAmount}">
						<input type="hidden" id="addFuelDocument" value="${addFuelDocument}">
						<input type="hidden" id="accessToken" value="${accessToken}">
						<input type="hidden" id="maxStockQuantity" value="0">
						<input type="hidden" id="avgPrice" value="0">
						<input type="hidden" id="setDefaultDriverForVehicle" value="${configuration.setDefaultDriverForVehicle}">
						<input type="hidden" id="defaultDriverIdForVehicle">
						<input type="hidden" id="defaultDriverNameForVehicle">
						<input type="hidden" id="allowFuelEntryTime" value="${configuration.allowFuelEntryTime}">
						<input type="hidden" id="allowManualOdometerEntry" value="${configuration.allowManualOdometerEntry}">
						<input type="hidden" id="maxFuelCapacity" value="${maxFuelCapacity}">
						<input type="hidden" id="getStockFromInventoryConfig" value="${configuration.getStockFromInventory}">
						<input type="hidden" id="fuelInvoiceId">
						<input type="hidden" id="showCurrnetDateAndTime" value="${configuration.showCurrnetDateAndTime}">
						<input type="hidden" id="fuelFinalPriceWithoutGST">
						<input type="hidden" id="invoiceGst">
						<input type="hidden" id="invoiceDiscount">
						<input type="hidden" id="maxFuelLiters" value="${configuration.maxFuelLiters}">
						<input type="hidden" id="maxFuelPrice" value="${configuration.maxFuelPrice}">
						<input type="hidden" id="paymentOption" value="${configuration.fuleEntryPaymentOptions}">
						<input type="hidden" id="twelveHourClock" value="${configuration.showTwelveHourClockFormat}" >
						<input type="hidden" id="userBranchId" value="${UserProfile.branch_id}">
						<input type="hidden" id="userBranch" value="${UserProfile.branch_name}"> 
						
						<div class="form-horizontal ">
							<fieldset>
								<div class="box">
									<div class="box-body">
									
									<div class="row1" style="width: 120%">
											<label class="col-md-1 col-sm-2 col-xs-12 control-label">Vehicle :<abbr
												title="required">*</abbr></label>
											<div class="col-md-4 col-sm-3 col-xs-12">
												<input type="hidden" id="FuelSelectVehicle" name="vid"
													style="width: 100%;" required="required"
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
									
										<div class="row1" style="width: 120%">
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
									<div class="row1" style="width: 120%">
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
									
										<div class="row1" style="width: 120%">
											<label class="col-md-1 col-sm-1 col-xs-12 control-label">Old
												Odometer :
											</label>
											<div class="col-md-4 col-sm-3 col-xs-12">
												<input type="hidden" id="prefuel_meter_old">
												<input type="text" onkeypress="return IsNumericOdometer(event);" class="form-text" id="fuel_meter_old" name="fuel_meter_old" >
											</div>
											<input type="hidden" id="tripStatusId" value="${TripSheet.tripStutesId}"/>
											<input type="hidden" id="maxOdometerAsTripCloseKM" value="${configuration.maxOdometerAsTripCloseKM}"/>
											<input type="hidden" id="tripCloseKM" value="${TripSheet.tripClosingKM}"/>
											
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
									<div class="row1" style="width: 120%">
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
									
									
										<div class="row1" style="width: 120%">
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
													onkeypress="return isNumberKeyWithDecimal(event,this.id);"
													ondrop="return false;">
												<p class="help-block">ex: 23.78</p>
											</div>
									</div>
									<div id="priceAndAmount">
									<div class="row1" style="width: 120%">
											<label class="col-md-1 col-sm-2 col-xs-12 control-label">Price/Unit
												:<abbr title="required">*</abbr></label>
											<div class="col-md-4 col-sm-3 col-xs-12">
												<input class="form-text" id="fuel_price" name="fuel_price"
													type="text" maxlength="6" min="0" onkeyup="calculateFuelAmount();"
													onkeypress="return isNumberKeyWithDecimal(event,this.id);"
													ondrop="return false;">
													<p class="help-block">ex: 56.78</p>
											</div>
											<label class="col-md-1 col-sm-1 col-xs-12 control-label">Total Amount :
											</label>
											<div class="col-md-4 col-sm-3 col-xs-12">
												<input class="form-text" id="fuelAmount" name="fuelAmount"
													type="text" maxlength="8" min="0" readonly="readonly" 
													 onkeypress="return isNumberKeyWithDecimal(event,this.id);"
													onkeyup="calculateFuelRate();"
													ondrop="return false;">
											</div>
									</div>
									</div>
									<div class="row1" style="width: 120%">
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
												<p class="help-block">
														Note : Turn On <b>"Partial"</b> if the tank is <strong>not
															filled up to "full"</strong>.
													</p>
											</div>
											
											<c:choose>
													<c:when test="${configuration.fuleEntryPaymentOptions}">
														<label class="col-md-1 col-sm-1 col-xs-12 control-label">Payment
																	Method :
														</label>
														<div style="width: 120%">
															<div class="col-md-2" id="paymentDiv">
																<select class="form-text" name=paymentTypeId"
																	id="renPT_option" required="required">
																		<option value="1">Cash</option>
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
									<div class="row1" style="width: 120%">
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
									<input type="hidden" value="${configuration.paidByOptionInAddFuleEntry}" id="paidByOptionInAddFuleEntry">
									<c:if test="${configuration.paidByOptionInAddFuleEntry}">
									  <div id="paidByOption">
										<div class="row1" style="width: 120%">
											<label class="col-md-1 col-sm-2 col-xs-12 control-label">Paid By:
												<abbr title="required">*</abbr></label>
											<div class="col-md-4 col-sm-3 col-xs-12">
												
												<select name="paidBy" class="form-text" onChange="Branch();" >
												  <option id="paidByDriver" value="1">Paid By Driver</option>
												  <option id="paidByBranch" value="2">Paid By Branch</option>
												</select>
											</div>
											
											<div id="showBranch">
												<label class="col-md-1 col-sm-1 col-xs-12 control-label">Branch :
												</label>
												<div class="col-md-4 col-sm-3 col-xs-12">
													<input type="hidden" id="selectBranch" name="branch_name"
													style="width: 100%;" value="0" placeholder="Please Select banch Name" />
												</div>
											</div>
										</div>
									  </div>
									</c:if>
												
									<div class="row1" style="width: 120%">
											<label class="col-md-1 col-sm-2 col-xs-12 control-label">Fuel
													Tank :</label>
											<div class="col-md-4 col-sm-3 col-xs-12">
													<div class="btn-group" id="status" data-toggle="buttons">
														<label class="btn btn-default btn-on btn-lg active">
															<input type="radio" value="0" name="fuel_tank"
															id="fuel_tank" checked="checked">Full
														</label> <label class="btn btn-default btn-off btn-lg"> <input
															type="radio" value="1" name="fuel_tank" id="fuel_tank_partial">Partial
														</label>
													</div>
													<p class="help-block">
													Note : Turn On <b>"Partial"</b> if the tank is <strong>not
														filled up to "full"</strong>.
													</p>
											</div>
											
									</div>
										
									<div class="row1" style="width: 120%">
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
									<div class="row1" style="width: 120%">
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
									<div class="row1" style="width: 120%">
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
									<!-- <div class="form-group boolean optional fuel_personal" id="grpfuelPersonalCheck">
										<div class="col-sm-offset-4 col-sm-10 col-md-8">

											<div class="help-block checkbox">
												<label for="fuel_entry_meter_entry_attributes_void">
													<input name="fuel_personal" id="fuel_personal" type="hidden" value="0">
													<input id="fuel_personal" name="fuel_personal"
													type="checkbox" value="1"> Mark as <span
													class="label label-warning">personal</span> expense

												</label>
											</div>
										</div>
									</div> -->
								</div>
							</fieldset>

							<fieldset id="grpfuelDocument">
								<legend>Document</legend>
								<div class="box">
									<div class="box-body">
										<div class="row1">
											<label class="L-size control-label" for="fuel_partial">Fuel Document :
												<c:if test="${addFuelDocument}">
													<abbr title="required">*</abbr>
												</c:if> 	
											</label>
											<div class="I-size">
												<input type="file" name="input-file-preview"
													id="renewalFile" /> <span id="renewalFileIcon" class=""></span>
												<div id="renewalFileErrorMsg" class="text-danger"></div>
<!-- 												<span class="help-block">Add an optional document</span> -->
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
														name="fuel_comments" rows="3" maxlength="240">	
												</textarea>
													<label class="error" id="errorComment"
														style="display: none"> </label> 
<!-- 														<span class="help-block">Add -->
<!-- 														an optional comment</span> -->
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
										<input type="button" value="Submit" id="btnSubmit" class="btn btn-success" />	
										<a class="btn btn-default" href="<c:url value="/Fuel/1.in"/>">Cancel</a>
									</div>
								</div>
							</div>
						</div>
					  </form>	
					<!-- </form> -->
				</div>
			</div>
			</div>
			</div>
		</sec:authorize>
		
		
		<div class="modal fade" id="addKMPL" role="dialog">
			<div class="modal-dialog" style="width:750px">
				<!-- Modal content-->
				<div class="modal-content">
					
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="JobType">Vehicle Km/L Details Haven't Been Added.
								</br>
								Please Update Km/L Details to Continue With Fuel Entries.
							</h4>
						</div>
						<div class="modal-body">
						
							<div class="row">
								<label class="L-size control-label" id="Type"> Vehicle Name :
								<abbr title="required">*</abbr>
								</label>
								<input type="hidden" name="vehicleKmplId" id="vehicleKmplId">
								<div class="I-size">
									<input type="text" class="form-text" required="required"
										maxlength="150" name="vehicleKmplName" id="vehicleKmplName" readonly="readonly"/>
								</div>
							</div>
							
							<br/>
							
							<div class="row">
								<label class="L-size control-label"><abbr title="required">*</abbr> Expected Mileage
									: 
								</label>
								<div class="I-size3">
									<div class="row">
										<div class="input-group">

											<input class="form-text" id="expectedMileageFrom"
												type="text" name="expectedMileageFrom" maxlength="4"
												min="0" required="required" onkeypress="return isNumberKeyWithDecimal(event,this.id);" placeholder="EM-form">
												
											<div class="input-group-addon">To</div>
											
											<input class="form-text" id="expectedMileageTo"
												type="text" name="expectedMileageTo" 
												maxlength="4" min="0" required="required" onkeypress="return isNumberKeyWithDecimal(event,this.id);"
												placeholder="EM-To">
										</div>
									</div>
								</div>
							</div>
							
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary" onclick="saveKMPL();">
								<span><spring:message code="label.master.Save" /></span>
							</button>
							<%-- <button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close"><spring:message
										code="label.master.Close" /></span>
							</button> --%>
						</div>
				</div>
			</div>
		</div>
		<div class="modal fade" id="stockInvoiceModal" role="dialog">
			<div class="modal-dialog" style="width:750px">
				<!-- Modal content-->
				<div class="modal-content">
					
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="JobType">Fuel Invoice Stock <span id="balanceInvoiceStock"></span>
							</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<div class="L-size btn-group" id="status" data-toggle="buttons">
									<label class="btn btn-default btn-on btn-mg active" style="width: 25%;"     >
										<input type="radio" value="1" id="activeStatusId" name="stockStatusName" checked="checked" >SHORT
									</label> 
									<label class="btn btn-default btn-off btn-mg" style="width: 35%;"> 
										<input type="radio" value="2" id="inactiveStatusId" name="stockStatusName">EXCESS
									</label>
								</div>
								<div class="I-size">
									<input type="number" id="shrotExcessQuantity" onkeypress="return isNumberKeyWithDecimal(event,this.id);" class="form-text" > 
								</div>
							</div>
							
							<br/>
							
							<div class="row">
							<label class="L-size control-label" id="Type">Remark :</label>
							<div class="I-size">
								<input type="text" class="form-text" id="remark"
									maxlength="249" 
									placeholder="Enter description" />
							</div>
						</div>
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary" onclick="updateFuelInvoiceStock();">
								<span><spring:message code="label.master.Save" /></span>
							</button>
						</div>
				</div>
			</div>
		</div>
		
		<%@ include file="/WEB-INF/view/payment/PaymentDetails.jsp"%>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>  
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
	<script type="text/javascript">
	
	var previousDate = $('#backDateString').val();
	var serverDate	= $('#serverDate').val();	
	var previousDateForBackDate =   previousDate.split("-")[0] + '-' +  previousDate.split("-")[1] + '-' +  previousDate.split("-")[2];
	var serverDateStr =   serverDate.split("-")[0] + '-' +  serverDate.split("-")[1] + '-' +  serverDate.split("-")[2];
	
	if(Number($('#tripSheetId').val()) > 0){
		previousDateForBackDate = $('#tripOpenDateStr').val();
		serverDateStr			 = $('#tripCloseDateStr').val();
	}
	
		$(function() {
			$("#fuelDate").datepicker({
			       defaultDate: serverDateStr,
			       autoclose: !0,
			       todayHighlight: true,
			       format: "dd-mm-yyyy",
			       endDate: serverDateStr,
				   startDate:previousDateForBackDate
			   })
		}); 
		
		$(document).ready(function() {
			$("#fuel_type").select2({
				placeholder : "Please Enter Vehicle Name Search"
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
				if($('#allowManualOdometerEntry').val() == 'true' || $('#allowManualOdometerEntry').val() == true) {
					$('#fuel_meter_old').attr('readonly', false);
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
			
			if($('#getStockFromInventoryConfig').val() == true || $('#getStockFromInventoryConfig').val() =='true'){
					$('#priceAndAmount').hide();
			}
			$("#datemask").inputmask("dd-mm-yyyy", {
				placeholder : "dd-mm-yyyy"
			}), $("[data-mask]").inputmask()
			
			
			if($("#twelveHourClock").val() == "true")
			{
				$('.clockpicker').clockpicker({
					placement: 'bottom',
					align: 'right',
					autoclose: true,
					twelvehour: true
				});
			}
			else
			{
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