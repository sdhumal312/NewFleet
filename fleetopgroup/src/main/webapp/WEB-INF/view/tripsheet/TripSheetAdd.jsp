<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/bootstrap-clockpicker.min.css" />">	
<div class="content-wrapper" onload="javascript:loadTripSheet();">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/newTripSheetEntries.in"/>">TripSheet</a> / <a
						href="<c:url value="/newTripSheetEntries.in?loadTypeId=2"/>">Dispatch</a> / <a
						href="<c:url value="/newTripSheetEntries.in?loadTypeId=3"/>">Manage</a> / <a
						href="<c:url value="/newTripSheetEntries.in?loadTypeId=4"/>">Advance Close</a> / <a
						href="<c:url value="/newTripSheetEntries.in?loadTypeId=5"/>">Payment</a> / <a
						href="<c:url value="/newTripSheetEntries.in?loadTypeId=6"/>">A/C Closed</a> /
					<span>Add Trip Sheet</span>
				</div>
				<div class="pull-right">
					<a href="newTripSheetEntries.in">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<c:if test="${param.success eq true}">
		<div class="alert alert-success">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This TripSheet Created Successfully.
		</div>
	</c:if>
	<c:if test="${param.danger eq true}">
		<div class="alert alert-danger">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This TripSheet Already Exists
		</div>
	</c:if>
	<section class="content">
		<div class="row">
			<div class="col-md-offset-1 col-md-9 col-sm-9 col-xs-12">
				
				<div class="alert alert-info hide" id="showDriverDetailsFromItsGatewayApi" >
					<button class="close" data-dismiss="alert" type="button">x</button>
						<label id="buttonMessageDriverDetails"  class="col-md-offset-1">
						</label> 
				</div>

				<c:if test="${param.Close eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						You should be close first Driver Account in those Tripsheet
						${CloseTS} <br> ${VMandatory}<br>You can save TripSheet.
						You can't Dispatch.
					</div>
				</c:if>
				<sec:authorize access="!hasAuthority('ADD_TRIPSHEET')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('ADD_TRIPSHEET')">
					<form action="saveTripSheet.in" method="post">
						<input type="hidden" name="ipAddress" id="ipAddress" /> <input
							type="hidden" name="vehicle_ExpectedOdameter"
							id="vehicle_ExpectedOdameter"> <input type="hidden"
							name="vehicle_Odometer" id="vehicle_Odometer"> <input
							type="hidden" id="validateOdometerInTripSheet"
							value="${validateOdometerInTripSheet}"> <input
							type="hidden" id="validateMinOdometerInTripSheet"
							value="${validateMinOdometerInTripSheet}"> <input
							type="hidden" id="hexLhpvIds" value="${lHPVDetailsIds}"
							name="hexLhpvIds"> <input type="hidden"
							name="vehicle_registration" id="vehicle_registration">
							<input type="hidden" id="gpsConfiguration" value="${gpsConfiguration}">
							<input type="hidden" id="noOfDaysForBackDate" value="${configuration.noOfDaysForBackDate}">
							<input type="hidden" id="allowGPSIntegration" value="${allowGPSIntegration}">
							<input type="hidden" id="allowITSGatewayDriverDetails" value="${allowITSGatewayDriverDetails}">
							<input type="hidden" id="companyId" value="${companyId}">
						<div class="form-horizontal">
							<fieldset>
								<legend>Trip Sheet Details</legend>
								<div class="box">
									<div class="box-body">
									
										<c:if test="${!empty busBooking}">
										<input type="hidden" value="${busBooking.busBookingDetailsId }" id="busBookingDetailsId" name="busBookingDetailsId">
											<div class="row1">
												<label class="L-size control-label" for="issue_vehicle_id">Booking Ref :
													: </label>
												<div class="I-size">
													<div class="col-md-9">
														<input type="text" class="form-text" id="bookingRefNumber" name="bookingRefNumber"
															style="width: 100%;" readonly="readonly" value="${busBooking.bookingRefNumber}" />
													</div>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label" for="issue_vehicle_id">Party Name :
													: </label>
												<div class="I-size">
													<div class="col-md-9">
														<input type="text" class="form-text" id="corporateName" name="corporateName"
															style="width: 100%;" readonly="readonly" value="${busBooking.corporateName}" />
													</div>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label" for="issue_vehicle_id">Pick Up Point :
													: </label>
												<div class="I-size">
													<div class="col-md-9">
														<input type="text" class="form-text" id="pickUpAddress" name="pickUpAddress"
															style="width: 100%;" readonly="readonly" value="${busBooking.pickUpAddress}" />
													</div>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label" for="issue_vehicle_id">Drop Point :
													: </label>
												<div class="I-size">
													<div class="col-md-9">
														<input type="text" class="form-text" id="dropAddress" name="dropAddress"
															style="width: 100%;" readonly="readonly" value="${busBooking.dropAddress}" />
													</div>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label" for="issue_vehicle_id"> Rate :
													: </label>
												<div class="I-size">
													<div class="col-md-9">
														<input type="text" class="form-text" id="rate" name="rate"
															style="width: 100%;" readonly="readonly" value="${busBooking.rate}" />
													</div>
												</div>
											</div>
										</c:if>
									
										<div class="row1">
											<label class="L-size control-label" for="issue_vehicle_id">Vehicle
												: </label>
											<div class="I-size" id="vehicleSelect">
												<div class="col-md-9">
													<input type="hidden" id="TripSelectVehicle" name="vid"
														style="width: 100%;" value="0"
														placeholder="Please Enter 2 or more Vehicle Name" />
												</div>
												<label id="errorVehicle" class="error"></label>
											</div>
										</div>
										<div class="help-block" id="last_occurred">
											<span class="loading ng-hide" id="loading"> <img
												alt="Loading" class="loading-img"
												src="<c:url value="/resources/QhyvOb0m3EjE7A4/images/ajax-loader.gif" />">
											</span>
										</div>
										<div class="row1" id="gpsLocationRow" style="display: none;">
											<label class="L-size control-label" for="issue_vehicle_id">GPS Location :
												: </label>
											<div class="I-size">
												<div class="col-md-9">
													<input type="text" class="form-text" id="gpsLocation" name=""gpsLocation""
														style="width: 100%;" readonly="readonly"/>
												</div>
												<label id="errorVehicle" class="error"></label>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Date Of Journey :
												<abbr title="required">*</abbr>
											</label>
											<div class="I-size">

												<div class="I-size">
													<div class="input-group input-append date">
														<input type="text" class="form-text" name="tripOpenDate"
															data-inputmask="'mask': ['99-99-9999  to  99-99-9999']"
															data-mask="" required id="reservationToTripSheet" readonly="readonly"
															onchange="getDriverDetails();"
															maxlength="26"> <span
															class="input-group-addon add-on"><span
															class="fa fa-calendar"></span></span>
													</div>
												</div>
											</div>
										</div>
										<div class="row1"  class="form-group" style="display: none;" id="dispatchDateTime">
											<label class="L-size control-label" for="reportDate">Dispatch
												Time <abbr title="required">*</abbr>
											</label>
											<div class="L-size">
												<div class="input-group input-append date" id="StartDate">
													<input type="text" class="form-text" id="dispatchedByTime" readonly="readonly"
														name="dispatchedByTime" onchange="getVehicleGPSDataAtTime();"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
												<span id="reportDateIcon" class=""></span>
												<div id="reportDateErrorMsg" class="text-danger"></div>
											</div>
											<div class="L-size">
												<div class="input-group clockpicker" >
													<input type="text" class="form-text" readonly="readonly"
														name="dispatchTime" id="dispatchTime" onchange="getVehicleGPSDataAtTime();"> <span
														class="input-group-addon"> <i
														class="fa fa-clock-o" aria-hidden="true"></i>
													</span>
												</div>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="issue_vehicle_id">Driver
												1 : </label>
											<div class="I-size" id="driverSelect">
												<div class="col-md-9">
													<input type="hidden" id="driverList"
														name="tripFristDriverID" style="width: 100%;"
														placeholder="Please Enter 3 or more Driver Name, No"
														value="0" />

												</div>
												<label id="errorDriver1" class="error"></label>
											</div>
											<div id="contactTwo" class="contact_Hide">

												<div class="I-size">
													<input type="text" class="form-text col-md-8"
														id="tripFristDriverName" name="tripFristDriverName"
														placeholder="Enter Driver1 Name"> <a
														class=" btn btn-link col-sm-offset-1"
														onclick="visibility('contactTwo', 'driverSelect');"> <i
														class="fa fa-minus"> Select</i>
													</a> <label id="errorDriverName" class="error"></label>
												</div>
											</div>
										</div>
										<c:if test="${configuration.driver2}">
											<div class="row1">
												<label class="L-size control-label" for="issue_vehicle_id">Driver
													2 :</label>
												<div class="I-size" id="driver2Select">
													<div class="col-md-9">
														<input type="hidden" id="driverList2"
															name="tripSecDriverID" style="width: 100%;" value="0"
															placeholder="Please Enter 3 or more Driver Name, NO" />

													</div>
													<label id="errorDriver2" class="error"></label>
												</div>

												<div id="driver2enter" class="contact_Hide">

													<div class="I-size">
														<input type="text" class="form-text col-md-8"
															id="tripSecDriverName" name="tripSecDriverName"
															placeholder="Enter Driver 2 Name"> <a
															class=" btn btn-link col-sm-offset-1"
															onclick="visibility('driver2enter', 'driver2Select');">
															<i class="fa fa-minus"> Select</i>
														</a> <label id="errorDriver2Name" class="error"></label>
													</div>
												</div>
											</div>
										</c:if>
										<c:if test="${configuration.cleaner}">
											<div class="row1">
												<label class="L-size control-label" for="issue_vehicle_id">Cleaner
													:</label>
												<div class="I-size" id="cleanerSelect">

													<div class="col-md-9">
														<input type="hidden" id="Cleaner" name="tripCleanerID"
															style="width: 100%;" value="0"
															placeholder="Please Enter 3 or more Cleaner Name, No" />

													</div>
													<label id="errorCleaner" class="error"></label>
												</div>

												<div id="cleanerEnter" class="contact_Hide">

													<div class="I-size">
														<input type="text" class="form-text col-md-8"
															id="tripCleanerName" name="tripCleanerName"
															placeholder="Enter Clener Name"> <a
															class=" btn btn-link col-sm-offset-1"
															onclick="visibility('cleanerEnter', 'cleanerSelect');">
															<i class="fa fa-minus"> Select</i>
														</a> <label id="errorCleanerName" class="error"></label>
													</div>
												</div>
											</div>
										</c:if>


										<div class="row1">

											<label class="L-size  control-label">Route Service :<abbr
												title="required">*</abbr></label>
											<div class="I-size" id="routeSelect">

												<div class="col-md-9">
													<input type="hidden" id="TripRouteList" name="routeID"
														style="width: 100%;" value="0"
														placeholder="Please Enter 3 or more Route Name, NO " />


												</div>
												<sec:authorize access="hasAuthority('ADD_TRIPSHEET_ROUTE')">
													<div class="col-md-1">
														<a class=" btn btn-link"
															onclick="visibilityRoute('routeEnter', 'routeSelect');">
															<i class="fa fa-plus"> New</i>
														</a>
													</div>
												</sec:authorize>
												<label id="errorRoute" class="error"></label>
											</div>
											<div id="routeEnter" class="contact_Hide">

												<div class="I-size">
													<input type="text" class="form-text col-md-8"
														id="routeName" name="routeName"
														placeholder="Enter Route Name"> <a
														class=" btn btn-link col-sm-offset-1"
														onclick="visibilityRoute('routeEnter', 'routeSelect');">
														<i class="fa fa-minus"> Select</i>
													</a> <label id="errorRouteName" class="error"></label>
												</div>
											</div>
											<input type="hidden" name="isNewRoute" id="isNewRoute"
												value="0" />
										</div>

										<c:if test="${configuration.showSubroute}">
											<div class="row1">
												<label class="L-size control-label">SubRoute Service
													: </label>
												<div class="I-size">
													<input class="form-text" placeholder="" id="subRouteName"
														name="subRouteName" type="text" maxlength="50"> <label
														class="error" id="errortripBook" style="display: none">
													</label>
												</div>
											</div>

										</c:if>

										<div class="row1" id="groupService">

											<label class="L-size control-label">Group Service :</label>
											<div class="I-size">
												<input class="form-text" name="vehicle_Group"
													id="vehicle_Group" placeholder="" type="text"
													maxlength="50" readonly="readonly"
													onkeypress="return IsTripSheetPanNO(event);"
													ondrop="return false;"> <label class="error"
													id="errorTripSheetPanNO" style="display: none"> </label>
											</div>

										</div>
										<div class="row1" id="manualKM">
											<label class="L-size control-label">Opening KM : </label>
											<div class="I-size">
												<input class="form-text" name="tripOpeningKM"
													id="tripOpeningKM" placeholder="" type="number" min="0"
													max="" maxlength="10" onblur="validateOdometer();"
													onkeypress="return tripOpening(event);"
													ondrop="return false;"> <label class="error"
													id="errortripOpening" style="display: none"> </label> <label
													id="errorOpening" class="error"></label> 
											</div>
										</div>
										<div class="row1" id="gpsKMRow" style="display: none;">
											<label class="L-size control-label">GPS Opening KM : </label>
											<div class="I-size">
												<input class="form-text" name="tripGpsOpeningKM"
													id="tripGpsOpeningKM" placeholder="" type="number" min="0"
													max="" maxlength="10" onblur="validateOdometer();"
													onkeypress="return tripOpening(event);"
													ondrop="return false;" readonly="readonly">
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Booking
												References : </label>
											<div class="I-size">
												<input class="form-text" name="tripBookref" placeholder=""
													id="tripBookref" type="text" maxlength="50"
													onkeypress="return tripBook(event);" ondrop="return false;">
												<label class="error" id="errortripBook"
													style="display: none"> </label>

											</div>
										</div>
										<input type="hidden" id="tripOpenCloseFuelRequired" value="${configuration.tripOpenCloseFuelRequired}">
										<c:if test="${configuration.tripOpenCloseFuelRequired}">
											<div class="row1">
												<label class="L-size control-label">Last Fuel :<abbr
													title="required">*</abbr> </label>
												<div class="I-size">
													<input class="form-text" name="tripStartDiesel" placeholder=""
														onkeypress="return isDecimalNumber(event, this);"
														id="tripStartDiesel" type="text" max="1000">
													<label class="error" id="errortripBook"
														style="display: none"> </label>
	
												</div>
											</div>
										</c:if>
										
										<!--newy-->
										<c:if test="${configuration.showLoadType}">
											<div class="row1">
												<label class="L-size control-label">Load Type :<abbr title="required">*</abbr> </label>
												<div class="I-size">															
													<div class="col-md-9">													
															<input type="hidden" id="loadListId"
																name="loadTypeId" style="width: 100%;"
																placeholder="Please Enter Load"
																value="0" />
													</div>														
												</div>
											</div>
										</c:if>
										<!--newy-->
										
										<c:if test="${configuration.showPODdetails}">
										<div class="row1">
											<label class="L-size control-label">Number
												of POD : </label>
											<div class="I-size">
												<input class="form-text" name="noOfPOD" 
													id="noOfPOD" type="text" maxlength="50"
													placeholder="Please Enter Number of POD">
												<label class="error" id="errortripBook"
													style="display: none"> </label>
											</div>
										</div>
										</c:if>
									</div>
								</div>

							</fieldset>
							<fieldset>
								<legend>
									Advance Payment Details <a class=" btn btn-default"
										onclick="visibility('advance');"> <i class="fa fa-plus"></i>
									</a>
								</legend>
								<div id="advance" class="contact_Hide">
									<div class="box">
										<div class="box-body">

											<c:if test="${configuration.showAdvanceDriver}">

												<div class="row1">
													<label class="L-size control-label" for="issue_vehicle_id">Driver
														: </label>
													<div class="I-size" id="driverSelect">
														<div class="col-md-9">
															<input type="hidden" id="advanceDriverId"
																name="advanceDriverId" style="width: 100%;"
																placeholder="Please Enter 3 or more Driver Name, No"
																value="0" />

														</div>
														<label id="errorDriver1" class="error"></label>
													</div>

												</div>

											</c:if>



											<div class="row1">
												<label class="L-size control-label">Advance Amount :</label>
												<div class="I-size">
													<input class="string required form-text"
														name="AdvanceAmount" maxlength="50" type="text"
														onkeypress="return IsAdvanceAmount(event);"
														ondrop="return false;"> <label class="error"
														id="errorAdvanceAmount" style="display: none"> </label>

												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Advance
													Reference :</label>
												<div class="I-size">
													<input class="string required form-text"
														name="advanceRefence" maxlength="50" type="text"
														onkeypress="return IsAdvanceRefence(event);"
														ondrop="return false;"> <label class="error"
														id="errorAdvanceRefence" style="display: none"> </label>

												</div>

												<a class=" btn btn-default col-sm-offset-1"
													onclick="visibility('advance');"> <i
													class="fa fa-minus"></i>
												</a>
											</div>
											<div class="row1">
												<label class="L-size control-label">Advance Paid By
													: </label>
												<div class="I-size">
													<input class="string required form-text" value="${paidBy}"
														readonly="readonly" id="advancePaidby"
														name="advancePaidby" maxlength="50" type="text"
														onkeypress="return IsAdvancePaidby(event);"
														ondrop="return false;"> <input type="hidden"
														id="advancePaidbyId" name="advancePaidbyId"
														value="${paidById}" /> <label class="error"
														id="errorAdvancePaidby" style="display: none"> </label>

												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Place :</label>
												<div class="I-size">
													<input class="string required form-text" value="${place}"
														readonly="readonly" name="advancePlace" maxlength="50"
														type="text" onkeypress="return IsAdvancePlace(event);"
														ondrop="return false;"> <input type="hidden"
														id="advancePlaceId" name="advancePlaceId"
														value="${placeId}" /> <label class="error"
														id="errorAdvancePlace" style="display: none"> </label>

												</div>
											</div>


										</div>
									</div>

								</div>
							</fieldset>
							<c:if test="${!empty lHPVDetails}">
								<fieldset>
								<legend>Lhpv Details :  </legend>
								<div class="box">
									<div class="box-body">
											<div class="row1">
												<table class="table table-bordered table-striped">
													<thead>
														<tr class="breadcrumb">
															<th>Sr</th>
															<th>Lhpv Number</th>
															<th>Advance</th>
															<th>Lorry Hire</th>
														</tr>
													</thead>
														<tbody>
															<%
																Integer hitsCount = 1;
															%>
															<c:forEach items="${lHPVDetails}" var="lhpvDetails">
																<tr class="ng-scope">
																	<td class="fit">
																		<%
																			out.println(hitsCount);
																						hitsCount += 1;
																		%>
																	</td>
																	<td class="fit">${lhpvDetails.lHPVNumber}</td>
																	<td class="fit">${lhpvDetails.advanceAmount}</td>
																	<td class="fit">${lhpvDetails.lorryHire}</td>
																</tr>
															</c:forEach>
															
														</tbody>
												</table>
											</div>
										</div>
								</div>
								
							</fieldset>
								
							</c:if>
							
							<fieldset>
								<legend>Remarks </legend>
								<div class="box">
									<div class="box-body">

										<div class="row1">
											<label class="L-size control-label">Remarks :</label>
											<div class="I-size">
												<textarea class="form-text" id="fuel_comments"
													name="advanceRemarks" rows="3" maxlength="250"
													onkeypress="return IsAdvanceRemarks(event);"
													ondrop="return false;">
														
													</textarea>
												<label class="error" id="errorAdvanceRemarks"
													style="display: none"> </label>
											</div>
										</div>

										<input type="hidden" value="" name="tripStutesId"
											id="tripStutes">

									</div>
								</div>
							</fieldset>

							<fieldset class="form-actions">
								<div class="row1">

							<input type="hidden" id="showAlwaysDispatchTime" value="${configuration.showAlwaysDispatchTime}">		
									<div class="pull-right">
										<button type="submit" class="btn btn-default"
											onclick="return validateTrip();">Save TripSheet</button>
										<button type="submit" class="btn btn-success"
											onclick="return validateDispatchTrip();">Dispatch
											vehicle</button>
										<a class="btn btn-default" href="newTripSheetEntries.in">Cancel</a>
									</div>
								</div>
							</fieldset>
						</div>
					</form>
				</sec:authorize>
			</div>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripValidate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripSheetAdd.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/CreateTripFromLhpv.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />" /></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepicker.js" />"></script>
	<script>
		$(function() {
					$("#reservationToTripSheet").daterangepicker({
						minDate : moment().subtract("days", Number($('#noOfDaysForBackDate').val()))
					})
				}),
				$(document)
						.ready(
								function() {
									$("input").bind("keydown", function(event) {
									    if (event.which === 13) {
									        event.stopPropagation();
									        event.preventDefault();
									        $(this).next("input").focus();
									    }
									});
									$("#TripSelectVehicle")
											.change(
													function() {
														
															GetTSVehicleValidate($(this).val());
													});
									
									$('#vehicleGroupId').on('change', function() {
										$('#vehicle_Group').val($("#vehicleGroupId option:selected").text().trim());
									});

									$("#TripRouteList").change(function() {
								        $.getJSON("getTripRouteSubListById.in", {
								            vehicleGroup: $(this).val(), ajax: "true"
								        }
								        , function(a) {
								            for(var b='', c=a.length, d=0;
								            c>d;
								            d++)b+='<option value="'+a[d].routeID+'">'+a[d].routeNo + " " + a[d].routeName+"</option>";
								            b+="</option>", $("#TripRouteSubList").html(b)
								        }
								        )
								    }
								    );
									
									$("#TripRouteSubList").select2();
									if(!${newTripSheet}){
										CreateTripFromLhpv('${vehicle.vid}', '${vehicle.vehicle_registration}', '${lhpvNumber}', ${newTripSheet});
									}

								});
		
		$(document).ready(function() {
			$('.clockpicker').clockpicker({
				placement: 'bottom',
				align: 'right',
				autoclose: true
			});
			$("#reservationToTripSheet").change(function() {
		       var  reservationToTripSheet = $('#reservationToTripSheet').val();
		       var array = reservationToTripSheet.split('to');
		       
		       var today = new Date();
		       var dd = today.getDate();

		       var mm = today.getMonth()+1; 
		       var yyyy = today.getFullYear();
		       if(dd<10) 
		       {
		           dd='0'+dd;
		       } 

		       if(mm<10) 
		       {
		           mm='0'+mm;
		       } 
		       today = yyyy+'-'+mm+'-'+dd;
		       
		      // var selectedDate = new Date('10-06-2019');
		       var date = array[0].trim().split("-").reverse().join("-");
		       var selectedDate = new Date(date);
		       var currentDate  = new Date(today);
		       
		       if(selectedDate < currentDate){
		    	   $('#dispatchedByTime').val(array[0].trim());
		    	   $('#dispatchDateTime').show();
		    	   showMessage('info', 'You are creating Back Date Tripsheet. Please enter dispatch time !');
		    	   $('#dispatchTime').focus();
		       }else if(${configuration.showAlwaysDispatchTime}){
		    	   $('#dispatchedByTime').val(array[0].trim());
		    	   $('#dispatchDateTime').show();
		    	   $('#dispatchTime').focus();
		       }else{
		    	   $('#dispatchedByTime').val('');
		    	   $('#dispatchDateTime').hide();
		       }
		      
		    });
			if(${!empty busBooking}){
				$('#reservationToTripSheet').val('${busBookingDate}');
			}
			if(${configuration.hideGroupService}){
				$('#groupService').hide();
			}
			setGpsReletedFeilds('${gpsConfiguration}');
		});
	</script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/bootstrap-clockpicker.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/script.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/ipaddress.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>
</div>