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
							<input type="hidden" id="minOdometer">
							<input type="hidden" id="maxOdometer">
							<input type="hidden" class="form-text" id="dispatchedByTime" readonly="readonly"/>
							
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
                            <label class="col-md-4 control-label">Dispatch Time</label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group clockpicker">
                                <span
														class="input-group-addon"> <i
														class="glyphicon glyphicon-time" aria-hidden="true"></i>
													</span>
                            	 <input type="text" class="form-control" readonly="readonly"
														name="dispatchTime" id="dispatchTime" onchange="getLastNextTripSheetDetails();">
                               
                               </div>
                            </div>
                         </div>
                       
                         <div class="form-group column">
                            <label class="col-md-4 control-label">Driver 1 : </label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-user"></i>
                               </span>
                              <input type="hidden" id="driverList"
														name="tripFristDriverID" style="width: 100%;"
														placeholder="Please Enter 3 or more Driver Name, No"
														value="0" />

                               </div>
                            </div>
                         </div>
                         <div class="form-group column" id="driver2Row"  style="display: none;">
                            <label class="col-md-4 control-label">Driver 2 : </label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-user"></i>
                               </span>
                             <input type="hidden" id="driverList2" name="tripSecDriverID"
									style="width: 100%;" value="0"
									placeholder="Please Enter 3 or more Driver Name, NO" />

                               </div>
                            </div>
                         </div>
                         <div class="form-group column" id="cleanerRow" style="display: none;">
                            <label class="col-md-4 control-label">Clear : </label>
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
                         <div class="form-group column" style="padding-bottom: 50px;">
                            <label class="col-md-4 control-label">Opening KM : </label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group" style="padding-bottom: 50px;">
	                               <span class="input-group-addon">
	                                <i class="fa fa-tachometer"></i>
	                               </span>
	                               <input class="form-control" name="tripOpeningKM"
													id="tripOpeningKM" placeholder="" type="number" min="0"
													max="" maxlength="10" onblur="validateOdometer();"
													onkeypress="return tripOpening(event);"
													ondrop="return false;"> 
									<label id="errorOpening" class="error"></label> 
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
														onkeypress="return IsAdvanceAmount(event);"
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
						<fieldset class="form-actions">
								<div class="row1">

									<input type="hidden" id="showAlwaysDispatchTime" value="${configuration.showAlwaysDispatchTime}">		
									<div class="pull-right">
										<button type="submit" id="saveTripSheet" class="btn btn-default"
											onclick="return saveTripSheet(1);">Save TripSheet</button>
										<button type="submit" id="dispatchTripSheet" class="btn btn-success"
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