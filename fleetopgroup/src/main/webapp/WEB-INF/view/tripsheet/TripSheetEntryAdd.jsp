<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/bootstrap-clockpicker.min.css" />">	

<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/newTripSheetEntries.in?loadTypeId=1"/>">TripSheet</a> / <a
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
	 <form action="#" method="POST" enctype="multipart/form-data" id="fileUploadForm">
	<section class="content">
		<div id="copyTrip" style="text-align: center;display: none;"></div>
		<div class="box">
			   <div class="box-body">
			   <input type="hidden" name="ipAddress" id="ipAddress" /> <input
							type="hidden" name="vehicle_ExpectedOdameter"
							id="vehicle_ExpectedOdameter"> <input type="hidden"
							name="vehicle_Odometer" id="vehicle_Odometer"> <input
							type="hidden" id="validateOdometerInTripSheet"
							value="${configuration.validateOdometerInTripSheet}"> <input
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
							<input type="hidden" id="accessToken" value="${accessToken}">
							<input type="hidden" id="minOdometer">
							<input type="hidden" id="maxOdometer">
							<input type="hidden" class="form-text" id="dispatchedByTime" readonly="readonly"/>
							<input type="hidden" class="form-text" id="dispatchedToByTime" readonly="readonly"/>
							<input type="hidden" id="driverFuelMileageAlert" value="${configuration.driverFuelMileageAlert}">
							<input type="hidden" id="showDriverFullName" value="${configuration.showDriverFullName}">
							<input type="hidden" id="showGPSodoInOpeningKm" value="${configuration.showGPSodoInOpeningKm}">
							<input type="hidden" id="twelveHourClock" value="${configuration.showTwelveHourClockFormat}" >
							<input type="hidden" id="validateDriverOnTripsheet" value="${configuration.validateDriverOnTripsheet}">
							<input type="hidden" id="vehicle_ExpectedMileage">
							<input type="hidden" value="${configuration.showTimeDuringAddTripsheet}" id="showTimeDuringAddTripsheet"> 
							<input type="hidden" value="${DefaultTime}" id="defaultTime">
							
							 		<div class="row1" style="display: none;color: red;" class="help-block" id="last_occurred">
										<div >
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
                              	<input class="form-control" name="vehicle_Group"
													id="vehicle_Group" placeholder="" type="text"
													maxlength="50" readonly="readonly">
                               </div>
                            </div>
                         </div>
                         <div class="form-group column">
                            <label class="col-md-4 control-label">Journey Date : </label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group">
                               <span class="input-group-addon">
                               		<span class="fa fa-calendar"></span>
                               </span>
                                <input type="text" class="form-control" name="tripOpenDate"
															data-inputmask="'mask': ['99-99-9999  to  99-99-9999']"
															data-mask="" required id="reservationToTripSheet" readonly="readonly"
															onchange="getDriverDetails();"
															maxlength="26"> 
                                 </div>
                            </div>
                         </div>

                         <div class="form-group column" id="dispatchDateTime" style="display: none;">
                            <label class="col-md-4 control-label">Dispatch Time <abbr title="required">*</abbr></label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group clockpicker">
                                <span
														class="input-group-addon"> <i
														class="glyphicon glyphicon-time" aria-hidden="true"></i>
													</span>
                            	 <input type="text" class="form-control"
														name="dispatchTime" id="dispatchTime" onchange="getLastNextTripSheetDetails();">
                               
                               </div>
                            </div>
                         </div>

                         <div class="form-group column">
                            <label class="col-md-4 control-label"> Driver 1 : <abbr title="required">*</abbr></label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-user"></i>
                               </span>
                              <input type="hidden" id="driverList" name="tripFristDriverID" style="width: 100%;"
								 placeholder="Please Enter 3 or more Driver Name, No" value="0" onchange="getLastTripsheetFuelMileage(this);" />
                               </div>
                            </div>
                         </div>
                         <div class="form-group column" id="driver2Row"  style="display: none;">
                            <label class="col-md-4 control-label">Driver 2 : </label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-user"></i>
                               </span>
                             <input type="hidden" id="driverList2" name="tripSecDriverID"
									style="width: 100%;" value="0" onchange="getLastTripsheetFuelMileage(this);"
									placeholder="Please Enter 3 or more Driver Name, NO" />

                               </div>
                            </div>
                         </div>
                         <div class="form-group column" id="cleanerRow" style="display: none;">
                            <label class="col-md-4 control-label">Cleaner : </label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                            <input type="hidden" id="Cleaner" name="tripCleanerID"
															style="width: 100%;" value="0"
															placeholder="Please Enter 3 or more Cleaner Name, No" />

                               </div>
                            </div>
                         </div>
                         
                         				<div class="form-group column">
											<label class="col-md-4  control-label">Route :<abbr
												title="required">*</abbr></label>
											<div class="I-size" id="routeSelect">

												<div class="col-md-10">
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
                        
                         <div class="form-group column" id="subRouteRow" style="display: none;">
                            <label class="col-md-4 control-label">SubRoute</label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group">
                                  <span class="input-group-addon" style="max-width: 100%;"><i class="glyphicon glyphicon-list"></i></span>
                                  <input class="form-control" placeholder="" id="subRouteName"
														name="subRouteName" type="text" maxlength="50">
                               </div>
                            </div>
                         </div>
                         <div class="form-group column" style="padding-bottom: 65px;">
                            <label class="col-md-4 control-label">Opening KM : </label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group">
	                               <span class="input-group-addon">
	                                <i class="fa fa-tachometer"></i>
	                               </span>
	                               <input class="form-control" name="tripOpeningKM"
													id="tripOpeningKM" placeholder="" type="number" min="0"
													max="" maxlength="10" onblur="validateOdometer();"
														onkeypress="return isNumberKey(event);"
													onkeypress="return tripOpening(event);"
													ondrop="return false;"> 
														
								  
                               </div>
                            </div>
                              <label class="col-md-4 control-label" for="meterNotWorking">Meter
									Not Working/Reset</label>
						<div class="col-md-8 inputGroupContainer">
							<div class=" input-group">
								<input type="checkbox" id="meterNotWorking"
									name="MeternotWorking" onchange="meterNotWorkingOnChange(this,errorOpening)">
									<label id="errorOpening" class="error"></label><br>
							</div>
						</div>
					</div>
                         <div class="form-group column" id="gpsKMRow" style="display: none;">
                            <label class="col-md-4 control-label">GPS Opening KM : </label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group">
                               <span class="input-group-addon"><i class="glyphicon glyphicon-earphone"></i></span>
                               <input class="form-control" name="tripGpsOpeningKM"
													id="tripGpsOpeningKM" placeholder="" type="number" min="0"
													max="" maxlength="10" onblur="validateOdometer();"
													onkeypress="return tripOpening(event);"
													ondrop="return false;" readonly="readonly">
                               </div>
                            </div>
                         </div>
                         <div class="form-group column">
                            <label class="col-md-4 control-label">Booking
												Ref : </label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group">
                               <span class="input-group-addon"><i class="fa fa-book"></i></span>
                             	 <input class="form-control" name="tripBookref" placeholder=""
													id="tripBookref" type="text" maxlength="50"
													onkeypress="return tripBook(event);" ondrop="return false;">
                               </div>
                            </div>
                         </div>
                         
                          <div class="form-group column"  id="lastFuelRow" style="display: none;">
                            <label class="col-md-4 control-label">Last Fuel :<abbr
													title="required">*</abbr> </label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group">
                               <span class="input-group-addon"><i class="glyphicon glyphicon-earphone"></i></span>
                               <input class="form-control" name="tripStartDiesel" placeholder=""
														onkeypress="return isDecimalNumber(event, this);"
														id="tripStartDiesel" type="text" max="1000">
                               </div>
                            </div>
                         </div>
                         
                          <div class="form-group column"  id="loadTypeRow" style="display: none;">
                            <label class="col-md-4 control-label">Load Type :<abbr title="required">*</abbr> </label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group">
                               <span class="input-group-addon"><i class="glyphicon glyphicon-road"></i></span>
                               <input type="hidden" id="loadListId"
																name="loadTypeId" style="width: 100%;"
																placeholder="Please Enter Load"
																value="0" />
                               </div>
                            </div>
                         </div>
                         
                          <div class="form-group column" id="noOfOwnerRow" style="display: none;">
                            <label class="col-md-4 control-label">Number
												of POD : </label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group">
                               <span class="input-group-addon"><i class="glyphicon glyphicon-eye-open"></i></span>
                                <input class="form-control" name="noOfPOD" 
													id="noOfPOD" type="text" maxlength="50"
													placeholder="Please Enter Number of POD">
                               </div>
                            </div>
                         </div>
                   </div>
              </div>			<b>Advance Payment Details <a class=" btn btn-default"
										onclick="visibility('advance');"> <i class="fa fa-plus"></i>
									</a></b>
									
						<fieldset>
								<div id="advance" class="contact_Hide">
									<div class="box">
										<div class="box-body">

											<c:if test="${configuration.showAdvanceDriver}">
												<div class="form-group column id="noOfOwnerRow" style="display: none;">
                            										<label class="col-md-4 control-label">Driver: </label>
													<div class="col-md-8 inputGroupContainer" id="driverSelect">
														<div class="input-group">
															<input type="hidden" id="advanceDriverId"
																name="advanceDriverId" style="width: 100%;"
																placeholder="Please Enter 3 or more Driver Name, No"
																value="0" />

														</div>
													</div>

												</div>

											</c:if>

											<div class="form-group column ">
					                            <label class="col-md-4 control-label">Advance Amount : </label>
					                            <div class="col-md-8 inputGroupContainer">
					                               <div class="input-group">
					                               <span class="input-group-addon"><i class="fa fa-inr"></i></span>
					                             	 <input class="string required form-control"
														name="AdvanceAmount" maxlength="50" type="text" id="AdvanceAmount"
														onkeypress="return isNumberKeyWithDecimal(event, this.id);"
														ondrop="return false;">
					                               </div>
					                            </div>
					                         </div>
											<div class="form-group column ">
					                            <label class="col-md-4 control-label">Advance Reference : </label>
					                            <div class="col-md-8 inputGroupContainer">
					                               <div class="input-group">
					                               <span class="input-group-addon"><i class="fa fa-book"></i></span>
					                             	 <input class="string required form-control"
														name="advanceRefence" maxlength="50" type="text" id="advanceRefence"
														onkeypress="return IsAdvanceRefence(event);"
														ondrop="return false;"> 
					                               </div>
					                            </div>
					                         </div>
					                        <c:if test="${configuration.showPaymentTypeInIncomeExpense}"> 
					                         <div class="form-group column ">
					                            <label class="col-md-4 control-label">Payment mode : </label>
					                            <div class="col-md-8 inputGroupContainer" id="paymentDiv">
					                               <div class="input-group">
					                               <span class="input-group-addon"><i class="fa fa-book"></i></span>
					                             	 <select class="string required form-control" name="ADVANCE_PAID_TYPE_ID"
																id="renPT_option" required="required">
													<option value="1">Cash</option>
													<option value="11">UPI</option>
													<option value="3">NEFT</option>
													<option value="4">RTGS</option>
													<option value="5">IMPS</option>
													<option value="6">DD</option>
													<option value="7">CHEQUE</option>
															</select>
					                               </div>
					                            </div>
					                         </div>
					                        </c:if>
					                        
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
								<div class="box">
									<div class="box-body">
										<div class="row1">
											<label class="L-size control-label">Remarks :</label>
											<div class="I-size">
												<textarea class="form-control" id="fuel_comments"
													name="advanceRemarks" rows="3" maxlength="250"
													onkeypress="return IsAdvanceRemarks(event);"
													ondrop="return false;" style="margin-bottom: 15px;">
														
													</textarea>
												<label class="error" id="errorAdvanceRemarks"
													style="display: none"> </label>
											</div>
										</div>
										 <c:if test="${configuration.addTripDocument}">
										 
											<div class="row1">
												<label class="L-size control-label" for="fuel_partial">TripSheet Document : </label>
												<div class="I-size">
													<input type="file" name="input-file-preview" id="tripDocument" data-max-size="512000"/> 
													<span id="renewalFileIcon" 	class=""></span>
													<div id="renewalFileErrorMsg" class="text-danger"></div>
													<span class="help-block">Add an optional document</span>
												</div>
											</div>
										</c:if>
										<c:if test="${!configuration.addTripDocument}">
											<input type="file" class="hide" name="input-file-preview"  />
										</c:if>	

										<input type="hidden" value="" name="tripStutesId"
											id="tripStutes">

									</div>
								</div>
						<fieldset class="form-actions">
								<div class="row1">
								<%@ include file="/WEB-INF/view/payment/PaymentDetails.jsp"%>
									<input type="hidden" id="showAlwaysDispatchTime" value="${configuration.showAlwaysDispatchTime}">	
									<input type="hidden" id="backDateStr" value="${backDateStr}">		
									<input type="hidden" id="serverDate" value="${serverDate}">		
									<div class="pull-right">
										<input type="button" value="Save TripSheet" onclick="saveTripSheet(1);" id="btnSubmit" class="btn btn-success" />
										<input type="button" value="Dispatch Vehicle" onclick="saveTripSheet(2);" id="btnSubmit" class="btn btn-success" />
										
										
										<!-- <input type="button" id="saveTripSheet" class="btn btn-default"
											onclick="return saveTripSheet(1);">Save TripSheet</input>
										<input type="button" value="Submit"  id="dispatchTripSheet" class="btn btn-success"
											onclick="return saveTripSheet(2);">Dispatch
											vehicle</input> -->
										<a class="btn btn-default" href="newTripSheetEntries.in">Cancel</a>
									</div>
								</div>
							</fieldset>
              </div>
         </div>
	</section>
	</form>
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
						minDate : moment($('#backDateStr').val()) 
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
					autoclose: true,
				});
			}
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
		     //  var todate = array[1].trim().split("-").reverse().join("-");
		       var selectedDate = new Date(date);
		       var currentDate  = new Date(today);
		   
			const [day, month, year] = array[0].split("-");
			const newFromDate = new Date(year, month - 1, day);

			currentDate.setHours(0, 0, 0, 0);
			newFromDate.setHours(0, 0, 0, 0);
			    
		      if(selectedDate < currentDate){
		    	   $('#dispatchedByTime').val(array[0].trim());
		    	   $('#dispatchedToByTime').val(array[1].trim());
		    	   $('#dispatchDateTime').show();
		    	   showMessage('info', 'You are creating Back Date Tripsheet. Please enter dispatch time !');
		    	   backDateTripSheet = true;
		    	   $('#dispatchTime').focus();
		    	   $('#saveTripSheet').hide();
		       }else if(${configuration.showAlwaysDispatchTime}){
		    	   $('#dispatchedByTime').val(array[0].trim());
		    	   $('#dispatchedToByTime').val(array[1].trim());
		    	   $('#dispatchDateTime').show();
		    	   $('#dispatchTime').focus();
		       }else{
		    	   $('#dispatchedByTime').val('');
		    	   $('#dispatchDateTime').hide();
		       } 
		        
		      var currentTime = new Date();

		      var formattedTime = currentTime.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', hour12: false });
		    
		      if (currentDate < newFromDate || currentDate > newFromDate ) {
		    	   $("#dispatchTime").val('');
			        
			    }else{ 
			    	if ($("#showTimeDuringAddTripsheet").val() === "true" || $("#showTimeDuringAddTripsheet").val() === true) {
			            
			    		document.getElementById('dispatchTime').value = formattedTime;

			        }else{
				    	   $("#dispatchTime").val('');
			        }
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
