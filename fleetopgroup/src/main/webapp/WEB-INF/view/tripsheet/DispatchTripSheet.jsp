<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/bootstrap-clockpicker.min.css" />">		
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
						href="<c:url value="/newTripSheetEntries.in"/>">TripSheets</a> / <span
						id="NewVehi">New Dispatch Trip Sheet</span>
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
			  			 				<input type="hidden" id="tripsheetId" name="tripSheetID" value="${TripSheet.tripSheetID}">
										<input type="hidden" id="tripSheetNumber" value="${TripSheet.tripSheetNumber}">
										<input type="hidden" id="preVehicleGroupId" name="vehicleGroupId" value="${TripSheet.vehicleGroupId}">
										<input type="hidden" id="noOfDaysForBackDate" value="${configuration.noOfDaysForBackDate}">	
										<input type="hidden" name="vehicle_registration" id="vehicle_registration">	
										<input type="hidden" value="${validateOdometerInTripSheet}" id="validateOdometerInTripSheet">	
										<input type="hidden" value="${validateMinOdometerInTripSheet}" id="validateMinOdometerInTripSheet">	
										<input type="hidden" value="${minAllowed}" id="vehicle_Odometer">
										<input type="hidden" value="${vehicle_ExpectedOdameter}" id="vehicle_ExpectedOdameter">	
										<input type="hidden" id="minOdometer" value="${minAllowed}">
										<input type="hidden" id="maxOdometer" value="${maxAllowed}">
										<input type="hidden" name="OldFristDriverID" value="${TripSheet.tripFristDriverID}">
										<input type="hidden" id="OldSecDriverID" value="${TripSheet.tripSecDriverID}">
										<input type="hidden" id="OldCleanerID" value="${TripSheet.tripCleanerID}">
										<input type="hidden" id="OldRouteID" value="${TripSheet.routeID}">
										<input type="hidden" id="OldSubRouteID" value="${TripSheet.subRouteID}">	
										<input type="hidden" id="vregist" value="${TripSheet.vehicle_registration}">
										<input type="hidden" id="prevehicle_group" value="${TripSheet.vehicle_Group}">
										<input type="hidden" id="tripStatusId" value="${TripSheet.tripStutesId}">
										<input type="hidden" id="did" value="${TripSheet.tripFristDriverID}">
										<input type="hidden" id="dname" value="${TripSheet.tripFristDriverName} ${TripSheet.tripFristDriverLastName} - ${TripSheet.tripFristDriverFatherName}">
										<input type="hidden" id="dsid" value="${TripSheet.tripSecDriverID}">
										<input type="hidden" id="dsname" value="${TripSheet.tripSecDriverName} ${TripSheet.tripSecDriverLastName} - ${TripSheet.tripSecDriverFatherName}">
										<input type="hidden" id="cid" value="${TripSheet.tripCleanerID}">
										<input type="hidden" id="cname" value="${TripSheet.tripCleanerName}">
										<input type="hidden" id="Rid" value="${TripSheet.routeID}">
										<input type="hidden" id="Rname" value="${TripSheet.routeName}">
										<input type="hidden" id="loadId" value="${TripSheet.loadTypesId}">													
										<input type="hidden" id="loadName" value="${TripSheet.loadTypeName}">
										<input type="hidden" id="vid" value="${TripSheet.vid}">
										<input type="hidden" id="companyId" value="${companyId}">
										<input type="hidden" id="userId" value="${userId}">		
										<input type="hidden" id="dispatchTrip" value="true">		
										<input type="hidden" id="tripClosingKM" value="${TripSheet.tripClosingKM}">	
										<input type="hidden" id="validateOdometerInTripSheet" value="${configuration.validateOdometerInTripSheet}">
										<input type="hidden" class="form-text" id="dispatchedByTime" readonly="readonly"/>	
										<input type="hidden" id="preDispatchTime" value="${TripSheet.dispatchedTime}" >	
										<input type="hidden" id="backDateStr" value="${backDateStr}">	
										<input type="hidden" id="showGPSodoInOpeningKm" value="${configuration.showGPSodoInOpeningKm}">	
										<input type="hidden" id="allowGPSIntegration" value="${allowGPSIntegration}">
										<input type="hidden" id="meterWorking" value="${TripSheet.meterNotWorking}">
							
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
                               <div class="input-group"><span class="input-group-addon"><i class="fa fa-user"></i>
                               </span>
                              	<input class="form-text" name="vehicle_Group" value="${TripSheet.vehicle_Group}"
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
                               <input type="text" class="form-text" name="tripOpenDate"
														required id="reservationToTripSheet" maxlength="26"
														readonly="readonly" onchange="getDriverDetails();"
														data-inputmask="'mask': ['99-99-9999  to  99-99-9999']"
														data-mask=""
														value="${TripSheet.tripOpenDate}  to  ${TripSheet.closetripDate}">
                                 </div>
                            </div>
                         </div>
                         <div class="form-group column" id="dispatchDateTime">
                            <label class="col-md-4 control-label">Dispatch Time</label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group clockpicker">
                                <span
														class="input-group-addon"> <i
														class="fa fa-clock-o" aria-hidden="true"></i>
													</span>
                            	 <input type="text" class="form-text" readonly="readonly" value="${TripSheet.dispatchedTime}"
														name="dispatchTime" id="dispatchTime" onchange="getLastNextTripSheetDetails();">
                               
                               </div>
                            </div>
                         </div>
                       
                         <div class="form-group column">
                            <label class="col-md-4 control-label">Driver 1 : </label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon"><i class="fa fa-user"></i>
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
                               <div class="input-group"><span class="input-group-addon"><i class="fa fa-user"></i>
                               </span>
                             <input type="hidden" id="driverList2" name="tripSecDriverID"
									style="width: 100%;" value="0"
									placeholder="Please Enter 3 or more Driver Name, NO" />

                               </div>
                            </div>
                         </div>
                         <div class="form-group column" id="cleanerRow" style="display: none;">
                            <label class="col-md-4 control-label">Cleaner : </label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon"><i class="fa fa-user"></i></span>
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
                                  <input class="form-text" placeholder="" id="subRouteName"
														name="subRouteName" type="text" maxlength="50">
                               </div>
                            </div>
                         </div>
                         <div class="form-group column" style="padding-bottom: 50px;">
                            <label class="col-md-4 control-label">Opening KM : </label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group">
	                               <span class="input-group-addon">
	                                <i class="fa fa-tachometer"></i>
	                               </span>
	                               <input class="form-text" name="tripOpeningKM"
													id="tripOpeningKM" placeholder="" type="number" min="0"
													max="" maxlength="10" onblur="validateOdometer();"
														onkeypress="return isNumberKey(event)" 
													onkeypress="return tripOpening(event);" value="${TripSheet.tripOpeningKM}"
													ondrop="return false;"> 
													  <label class="control-label" for="meterNotWorking">Meter
									Not Working/Reset</label>
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
                               <input class="form-text" name="tripGpsOpeningKM"
													id="tripGpsOpeningKM" placeholder="" type="number" min="0"
													max="" maxlength="10" onblur="validateOdometer();"
													onkeypress="return tripOpening(event);" value="${TripSheet.tripGpsOpeningKM}"
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
                             	 <input class="form-text" name="tripBookref" placeholder="" value="${TripSheet.tripBookref}"
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
                               <input class="form-text" name="tripStartDiesel" value="${TripSheet.tripStartDiesel}"
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
                                <input class="form-text" name="noOfPOD"  value="${TripSheet.noOfPOD}"
													id="noOfPOD" type="text" maxlength="50"
													placeholder="Please Enter Number of POD">
                               </div>
                            </div>
                         </div>
                          </div>
                         </div>
                         <div class="box">
							<div class="box-body">
                         <b style="text-align: left;">Advance Payment Details <a class=" btn btn-default"
										onclick="visibility('advance');"> <i class="fa fa-plus"></i>
									</a></b>

 				<c:forEach items="${TripSheetAdvance}" var="TripSheetAdvance">
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
					                             	<c:if test="${configuration.disableAdvanceDuringDispatch}">
					                             	 <input class="string required form-text"
					                             		value="${TripSheetAdvance.advanceAmount}"
														name="AdvanceAmount" maxlength="50" readonly="readonly" type="text" id="AdvanceAmount"
														onkeypress="return isNumberKeyWithDecimal(event,this.id);"
														ondrop="return false;">
					                             	</c:if>
					                             	<c:if test="${!configuration.disableAdvanceDuringDispatch}">
					                             	 <input class="string required form-text"
					                             		value="${TripSheetAdvance.advanceAmount}"
														name="AdvanceAmount" maxlength="50" type="text" id="AdvanceAmount"
														onkeypress="return isNumberKeyWithDecimal(event,this.id);"
														ondrop="return false;">
					                             	</c:if>
					                             	
					                               </div>
					                            </div>
					                         </div>
											<div class="form-group column ">
					                            <label class="col-md-4 control-label">Advance Reference : </label>
					                            <div class="col-md-8 inputGroupContainer">
					                               <div class="input-group">
					                               <span class="input-group-addon"><i class="fa fa-book"></i></span>
					                             	 <input class="string required form-text"
					                             		 value="${TripSheetAdvance.advanceRefence}"
														name="advanceRefence" maxlength="50" type="text" id="advanceRefence"
														onkeypress="return IsAdvanceRefence(event);"
														ondrop="return false;"> 
					                               </div>
					                            </div>
					                         </div>
					                         
					                        <c:if test="${configuration.showPaymentTypeInIncomeExpense}"> 
					                         <div class="form-group column ">
					                            <label class="col-md-4 control-label">Payment mode : </label>
					                            <div class="col-md-8 inputGroupContainer">
					                               <div class="input-group">
					                               <span class="input-group-addon"><i class="fa fa-book"></i></span>
					                             	 <select class="string required form-control" name="ADVANCE_PAID_TYPE_ID"
																id="renPT_option" required="required">
																<option value="${TripSheetAdvance.paymentTypeId}">${TripSheetAdvance.paymentTypeName}</option>
															</select>
					                               </div>
					                            </div>
					                         </div>
					                        </c:if>
					                         
										</div>
									</div>
								</div>
								</fieldset>
						
 				</c:forEach>
 		  </div>
              </div>			
								
						<fieldset class="form-actions">
								<div class="row1">

									<div class="pull-right">

										<input class="btn btn-success" name="commit" type="submit"
											value="Dispatch TripSheet"
											onclick="return updateTripSheetDetails();"> <a
											class="btn btn-default"
											href="<c:url value="/newTripSheetEntries.in?loadTypeId=2"/>">Cancel</a>
									</div>
								</div>
							</fieldset>
							
              </div>
              </div>
              
	</section>
	
<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/bootstrap-clockpicker.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripValidate.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/script.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/ipaddress.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripSheetAdd.js" />"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripSheetEdit.js" />"></script>	
	<script type="text/javascript">
		$(document).ready(function() {
			$('.clockpicker').clockpicker({
				placement: 'bottom',
				align: 'right',
				autoclose: true
			});
			$("#reservationToTripSheet").daterangepicker({
				minDate : moment($('#backDateStr').val()) 
			});
			if(${TripSheet.tripGpsOpeningKM} > 0){
				$('#gpsKMRow').show();
			}
			if($('#meterWorking').val() == true ||$('#meterWorking').val() === 'true' ){
				$("#meterNotWorking").prop('checked',true);
				$('#meterNotWorking').trigger('change');
			}
		});
		$(document).ready(function() {
			var daterange = 	$('#reservationToTripSheet').val().split("to");
			 $('#dispatchedByTime').val(daterange[0].replace(/ /g,''));
			if(${configuration.hideGroupService}){
				$('#VGroupTab').hide();
			}
			//old Vehicle Select edit time
			var Ovid = $("#vid").val();
			var Otext = $("#vregist").val();
			$('#TripSelectVehicle').select2('data', {
				id : Ovid,
				text : Otext
			})
			
			//old Driver 1 Select edit time
			var Did = $("#did").val();
			var Dtext = $("#dname").val();
			$('#driverList').select2('data', {
				id : Did,
				text : Dtext
			})

			//old Driver Second Select edit time
			var Dsid = $("#dsid").val();
			var Dstext = $("#dsname").val();
			if(Dsid != undefined && Dsid != null && Number(Dsid) > 0){
				$('#driverList2').select2('data', {
					id : Dsid,
					text : Dstext
				})
			}

			//old Cleaner Second Select edit time
			var Csid = $("#cid").val();
			var Cstext = $("#cname").val();
			if(Csid != undefined && Csid != null && Number(Csid) > 0){
				$('#Cleaner').select2('data', {
					id : Csid,
					text : Cstext
				})
			}

			//old Route Second Select edit time
			var Rid = $("#Rid").val();
			var Rtext = $("#Rname").val();
			$('#TripRouteList').select2('data', {
				id : Rid,
				text : Rtext
			});
			
			
			//newy Start
			var LoadId = $("#loadId").val();
			var LoadName = $("#loadName").val();
			$('#loadListId').select2('data', {
				id : LoadId,
				text : LoadName
			});
			//newy Stop
			$('#vehicleGroupId').on('change', function() {
				$('#vehicle_Group').val($("#vehicleGroupId option:selected").text().trim());
			});
			
			$("#TripRouteSubList").select2();
			$("#TripSelectVehicle").select2("readonly", true);
			setConfigurationFeilds('${config}');
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
	</script>


</div>