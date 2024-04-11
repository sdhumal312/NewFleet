<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/bootstrap-clockpicker.min.css" />">	
	
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
* {
  box-sizing: border-box;
}

/* Create three equal columns that floats next to each other */
.column {
  float: left;
  width: 50%;
  padding: 10px;
  height: 30px; /* Should be removed. Only for demonstration */
}
.column100 {
  float: left;
  width: 100%;
  padding: 10px;
  height: 30px; /* Should be removed. Only for demonstration */
}
.column75 {
  float: left;
  width: 40%;
  padding: 10px;
  padding-left : 40px;
  height: 30px; /* Should be removed. Only for demonstration */
}

/* Clear floats after the columns */
.row:after {
  content: "";
  display: table;
  clear: both;
}

/* Responsive layout - makes the three columns stack on top of each other instead of next to each other */
@media screen and (max-width: 600px) {
  .column {
    width: 100%;
  }
}
@media screen and (max-width: 600px) {
  .column75 {
    width: 75%;
  }
}
</style>
	
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
	
	<section class="content">
	<legend>Trip Sheet Details</legend>
			<div class="box">
			   <div class="box-body">	
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
							<input type="hidden" id="userId" value="${userId}">
							<input type="hidden" id="branchId" value="${branchId}">
			  						 <fieldset>
			  						 <div class="row1">
											<div style="display: none;" class="help-block" id="last_occurred">
											<span class="loading ng-hide" id="loading"> <img
												alt="Loading" class="loading-img"
												src="<c:url value="/resources/QhyvOb0m3EjE7A4/images/ajax-loader.gif" />">
											</span>
										</div>
										</div> 
										
						<div class="form-group column">
                            <label class="col-md-4 control-label">Vehicle : <abbr title="required">*</abbr></label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group">
                               <span class="input-group-addon">
                              	 <i class="fa fa-bus"></i>
                               </span>
                              	 <input type="hidden" id="TripSelectVehicle" name="vid" style="width: 100%;" value="0" 
                              	 	placeholder="Please Enter 2 or more Vehicle Name" />
                               </div>
                            </div>
                         </div>
						<div class="form-group column" >
                            <label class="col-md-4 control-label">Group Service</label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-user"></i>
                               </span>
                              	<input class="form-text" name="vehicle_Group"
													id="vehicle_Group" placeholder="" type="text"
													maxlength="50" readonly="readonly">
                               </div>
                            </div>
                         </div>
										<div class="row1 column"  class="form-group" style="display: none;" id="dispatchDateTime">
											<label class="L-size control-label" for="reportDate">Dispatch
												Time <abbr title="required">*</abbr>
											</label>
											<div class="L-size">
											<input type="hidden" class="form-text" id="dispatchedByTime" readonly="readonly"/>
												<div class="input-group clockpicker" >
													<input type="text" class="form-text" readonly="readonly"
														name="dispatchTime" id="dispatchTime" onchange="getVehicleGPSDataAtTime();"> <span
														class="input-group-addon"> <i
														class="fa fa-clock-o" aria-hidden="true"></i>
													</span>
												</div>
											</div>
										</div>
										<div class="row1 column">
											<label class="L-size control-label" for="issue_vehicle_id">Driver
												1 : </label>
											<div class="I-size" id="driverSelect">
												<div class="col-md-9">
													<input type="hidden" id="driverList"
														name="tripFristDriverID" style="width: 100%;"
														placeholder="Please Enter 3 or more Driver Name, No"
														value="0" />

												</div>
											</div>
										</div>
					<div class="row1 column" id="driver2Row" style="display: none;">
						<label class="L-size control-label" for="issue_vehicle_id">Driver
							2 :</label>
						<div class="I-size" id="driver2Select">
							<div class="col-md-9">
								<input type="hidden" id="driverList2" name="tripSecDriverID"
									style="width: 100%;" value="0"
									placeholder="Please Enter 3 or more Driver Name, NO" />

							</div>
						</div>
					</div>
					<div class="row1 column" id="cleanerRow" style="display: none;">
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
									    </div>
									    <div class="row1 column">
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
										<div class="row1 column" id="subRouteRow" style="display: none;">
												<label class="L-size control-label">SubRoute
													: </label>
												<div class="I-size">
													<input class="form-text" placeholder="" id="subRouteName"
														name="subRouteName" type="text" maxlength="50"> <label
														class="error" id="errortripBook" style="display: none">
													</label>
												</div>
										</div>
										<div class="row1 column" id="manualKM">
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
										<div class="row1 column" id="gpsKMRow" style="display: none;">
											<label class="L-size control-label">GPS Opening KM : </label>
											<div class="I-size">
												<input class="form-text" name="tripGpsOpeningKM"
													id="tripGpsOpeningKM" placeholder="" type="number" min="0"
													max="" maxlength="10" onblur="validateOdometer();"
													onkeypress="return tripOpening(event);"
													ondrop="return false;" readonly="readonly">
											</div>
										</div>
										<div class="row1 column">
											<label class="L-size control-label">Booking
												Ref : </label>
											<div class="I-size">
												<input class="form-text" name="tripBookref" placeholder=""
													id="tripBookref" type="text" maxlength="50"
													onkeypress="return tripBook(event);" ondrop="return false;">
												<label class="error" id="errortripBook"
													style="display: none"> </label>

											</div>
										</div>
										<div class="row1 column" id="lastFuelRow" style="display: none;">
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
										<div class="row1 column" id="loadTypeRow" style="display: none;">
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
										<div class="row1 column" id="noOfOwnerRow" style="display: none;">
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
										</div>
										</div>
									</fieldset>	
								<legend>
									Advance Payment Details <a class=" btn btn-default"
										onclick="visibility('advance');"> <i class="fa fa-plus"></i>
									</a>
								</legend>
								<fieldset>
								<div id="advance" class="contact_Hide">
									<div class="box">
										<div class="box-body">

											<c:if test="${configuration.showAdvanceDriver}">

												<div class="row1 column">
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

											<div class="row1 column">
												<label class="L-size control-label">Advance Amount :</label>
												<div class="I-size">
													<input class="string required form-text"
														name="AdvanceAmount" maxlength="50" type="text" id="AdvanceAmount"
														onkeypress="return IsAdvanceAmount(event);"
														ondrop="return false;"> <label class="error"
														id="errorAdvanceAmount" style="display: none"> </label>

												</div>
											</div>
											<div class="row1 column">
												<label class="L-size control-label">Advance
													Reference :</label>
												<div class="I-size">
													<input class="string required form-text"
														name="advanceRefence" maxlength="50" type="text" id="advanceRefence"
														onkeypress="return IsAdvanceRefence(event);"
														ondrop="return false;"> <label class="error"
														id="errorAdvanceRefence" style="display: none"> </label>

												</div>

												<a class=" btn btn-default col-sm-offset-1"
													onclick="visibility('advance');"> <i
													class="fa fa-minus"></i>
												</a>
											</div>
										</div>
									</div>
								</div>
								</fieldset>
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
											onclick="return saveTripSheet(1);">Save TripSheet</button>
										<button type="submit" class="btn btn-success"
											onclick="return saveTripSheet(2);">Dispatch
											vehicle</button>
										<a class="btn btn-default" href="newTripSheetEntries.in">Cancel</a>
									</div>
								</div>
							</fieldset>
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
									/* if(!${newTripSheet}){
										CreateTripFromLhpv('${vehicle.vid}', '${vehicle.vehicle_registration}', '${lhpvNumber}', ${newTripSheet});
									} */

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
		    	   backDateTripSheet = true;
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
			setConfigurationFeilds('${config}');
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