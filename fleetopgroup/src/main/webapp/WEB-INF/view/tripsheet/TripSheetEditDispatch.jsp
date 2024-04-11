<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
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
			<sec:authorize access="!hasAuthority('EDIT_TRIPSHEET')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('EDIT_TRIPSHEET')">
				<div class="col-md-offset-1 col-md-9 col-sm-9 col-xs-12">

					<c:if test="${param.Close eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							You should be close first Driver Account in those Tripsheet
							${CloseTS} <br> ${VMandatory}<br> You can save TripSheet. You can't Dispatch.
						</div>
					</c:if>
					<form action="uploadTripSheet.in" method="post">
						<input type="hidden" name="ipAddress" id="ipAddress" />
						<div class="form-horizontal">
							<fieldset>
								<legend>Trip Sheet Details</legend>
								<div class="box">
									<div class="box-body">
									
										<input type="hidden" value="${validateOdometerInTripSheet}" id="validateOdometerInTripSheet">	
										<input type="hidden" value="${validateMinOdometerInTripSheet}" id="validateMinOdometerInTripSheet">	
										<input type="hidden" value="${minAllowed}" id="vehicle_Odometer">
										<input type="hidden" value="${vehicle_ExpectedOdameter}" id="vehicle_ExpectedOdameter">	
										<input type="hidden" id="minAllowed" value="${minAllowed}">
										<input type="hidden" id="maxAllowed" value="${maxAllowed}">

										<input type="hidden" name="tripSheetID"
											value="${TripSheet.tripSheetID}">
										<input type="hidden" name="tripSheetNumber"
											value="${TripSheet.tripSheetNumber}">
										<input type="hidden" name="preGroupService"
										  id="preGroupService"	value="${TripSheet.vehicle_Group}">

										<div class="row1">
											<label class="L-size control-label" for="issue_vehicle_id">Vehicle
												: </label>
											<div class="I-size" id="vehicleSelect">
												<div class="col-md-8">
													<input type="hidden" id="vid" value="${TripSheet.vid}"
														required="required">
													
													<!-- registr -->
													<input type="hidden" id="vregist"
														value="${TripSheet.vehicle_registration}"
														required="required">
													<!-- Select vehicle -->
													<input type="hidden" id="TripSelectVehicle" name="vid"
														style="width: 100%;" value="0"
														placeholder="Please Enter 2 or more Vehicle Name" />
												</div>
												<a class=" btn btn-link col-sm-offset-1"
													onclick="visibilityvehicleEdit('TripVehicle', 'vehicleSelect');">
													<i class="fa fa-plus"> New</i>
												</a> <label id="errorVehicle" class="error"></label>
											</div>
											<div id="TripVehicle" class="contact_Hide">
												<div class="I-size">
													<input type="text" class="form-text col-md-9"
														name="vehicle_registration" id="vehicle_registration"
														value="${TripSheet.vehicle_registration}"
														placeholder="Enter Vehicle Number eg: KA-45-6767">
													<a class=" btn btn-link col-sm-offset-1"
														onclick="visibilityvehicleEdit('TripVehicle', 'vehicleSelect');">
														<i class="fa fa-minus"> Select</i>
													</a> <label id="errorVehicleName" class="error"></label>

												</div>
											</div>
										</div>
										
										<div class="row" id="vehicle_group_id" style="display: none;">
												<div class="form-group">
													<label class="L-size string required control-label">Vehicle
														Group :<abbr
												title="required">*</abbr></label>
													<div class="I-size">
														<select class="form-text" name="vehicleGroupId" id="vehicleGroupId">
															<c:forEach items="${vehiclegroup}" var="vehiclegroup">
																<option value="${vehiclegroup.gid}">
																		<c:out value="${vehiclegroup.vGroup}" />
																</option>
															</c:forEach>
														</select>
													</div>
												</div>
										</div>

										<div class="help-block" id="last_occurred">
											<span class="loading ng-hide" id="loading"> <img
												alt="Loading" class="loading-img"
												src="resources/images/ajax-loader.gif">
											</span>

										</div>

										<div class="row1">
											<label class="L-size control-label" for="issue_vehicle_id">Driver
												1 : </label>
											<div class="I-size" id="driverSelect">
												<div class="col-md-8">
													<input type="hidden" id="did"
														value="${TripSheet.tripFristDriverID}" required="required">
													<!-- registr -->
													<input type="hidden" id="dname"
														value="${TripSheet.tripFristDriverName}"
														required="required">
													<!-- Select frist Driver -->
													<input type="hidden" id="driverList"
														name="tripFristDriverID" style="width: 100%;"
														placeholder="Please Enter 3 or more Driver Name, No"
														value="0" />
												</div>
												<!-- <a class=" btn btn-link col-sm-offset-1"
													onclick="visibility('contactTwo', 'driverSelect');"> <i
													class="fa fa-plus"> New</i>
												</a> --> <label id="errorDriver1" class="error"></label>
											</div>

											<div id="contactTwo" class="contact_Hide">
												<div class="I-size">
													<input type="text" class="form-text col-md-8"
														id="tripFristDriverName" name="tripFristDriverName"
														value="${TripSheet.tripFristDriverName}"
														placeholder="Enter Driver1 Name"> <a
														class=" btn btn-link col-sm-offset-1"
														onclick="visibility('contactTwo', 'driverSelect');"> <i
														class="fa fa-minus"> Select</i>
													</a> <label id="errorDriverName" class="error"></label>
												</div>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="issue_vehicle_id">Driver
												1 Mobile: </label>
											<div class="I-size">
												<div class="col-md-8">
													<!-- registr -->
													<input type="text" class="form-text" maxlength="15"
														name="tripFristDriverMobile"
														value="${TripSheet.tripFristDriverMobile}"
														required="required">
												</div>
											</div>
										</div>
										<c:if test="${configuration.driver2}">
										<div class="row1">
											<label class="L-size control-label" for="issue_vehicle_id">Driver
												2 :</label>
											<div class="I-size" id="driver2Select">
												<div class="col-md-8">
													<input type="hidden" id="dsid"
														value="${TripSheet.tripSecDriverID}" required="required">
													<!-- registr -->
													<input type="hidden" id="dsname"
														value="${TripSheet.tripSecDriverName}" required="required">
													<!-- Select SECOUND Driver -->
													<input type="hidden" id="driverList2"
														name="tripSecDriverID" style="width: 100%;" value="0"
														placeholder="Please Enter 3 or more Driver Name, NO" />
												</div>
												<!-- <a class=" btn btn-link col-sm-offset-1"
													onclick="visibility('driver2enter', 'driver2Select');">
													<i class="fa fa-plus"> New</i>
												</a> --> <label id="errorDriver2" class="error"></label>
											</div>

											<div id="driver2enter" class="contact_Hide">
												<div class="I-size">
													<input type="text" class="form-text col-md-8"
														id="tripSecDriverName" name="tripSecDriverName"
														value="${TripSheet.tripSecDriverName}"
														placeholder="Enter Driver 2 Name"> <a
														class=" btn btn-link col-sm-offset-1"
														onclick="visibility('driver2enter', 'driver2Select');">
														<i class="fa fa-minus"> Select</i>
													</a> <label id="errorDriver2Name" class="error"></label>
												</div>
											</div>
										</div>
										</c:if>
										<c:if test="${configuration.driver2}">
										<div class="row1">
											<label class="L-size control-label" for="issue_vehicle_id">Driver
												2 Mobile: </label>
											<div class="I-size">
												<div class="col-md-8">
													<!-- registr -->
													<input type="text" class="form-text" maxlength="15"
														name="tripSecDriverMobile"
														value="${TripSheet.tripSecDriverMobile}"
														required="required">
												</div>
											</div>
										</div>
										</c:if>
										<c:if test="${configuration.cleaner}">
										<div class="row1">
											<label class="L-size control-label" for="issue_vehicle_id">Cleaner
												:</label>
											<div class="I-size" id="cleanerSelect">
												<div class="col-md-8">
													<input type="hidden" id="cid"
														value="${TripSheet.tripCleanerID}" required="required">
													<!-- registr -->
													<input type="hidden" id="cname"
														value="${TripSheet.tripCleanerName}" required="required">
													<!-- Select Cleaner -->
													<input type="hidden" id="Cleaner" name="tripCleanerID"
														style="width: 100%;" value="0"
														placeholder="Please Enter 3 or more Cleaner Name, No" />
												</div>
												<!-- <a class=" btn btn-link col-sm-offset-1"
													onclick="visibility('cleanerEnter', 'cleanerSelect');">
													<i class="fa fa-plus"> New</i>
												</a> --> <label id="errorCleaner" class="error"></label>
											</div>

											<div id="cleanerEnter" class="contact_Hide">
												<div class="I-size">
													<input type="text" class="form-text col-md-8"
														id="tripCleanerName" name="tripCleanerName"
														value="${TripSheet.tripCleanerName}"
														placeholder="Enter Clener Name"> <a
														class=" btn btn-link col-sm-offset-1"
														onclick="visibility('cleanerEnter', 'cleanerSelect');">
														<i class="fa fa-minus"> Select</i>
													</a> <label id="errorCleanerName" class="error"></label>
												</div>
											</div>
										</div>
										</c:if>
										<c:if test="${configuration.cleaner}">
										<div class="row1">
											<label class="L-size control-label" for="issue_vehicle_id">Cleaner
												Mobile: </label>
											<div class="I-size">
												<div class="col-md-8">
													<!-- registr -->
													<input type="text" class="form-text" maxlength="15"
														name="tripCleanerMobile"
														value="${TripSheet.tripCleanerMobile}" required="required">
												</div>
											</div>
										</div>
										</c:if>
									</div>
								</div>
								<div class="box">
									<div class="box-body">
										<c:if test="${!empty TripSheet}">
										<div class="row1">
											<label class="L-size  control-label">Route Service :<abbr
												title="required">*</abbr></label>
											<div class="I-size" id="routeSelect">
												<div class="col-md-8">
													<input type="hidden" id="Rid" value="${TripSheet.routeID}"
														required="required">
														
													<!-- registr -->
													<input type="hidden" id="Rname"
														value="${TripSheet.routeName}" required="required">
														
														
													<!-- Select Route -->
													<input type="hidden" id="TripRouteList" name="routeID"
														style="width: 100%;" value="0"
														placeholder="Please Enter 3 or more Route Name, NO " />
												</div><sec:authorize access="hasAuthority('ADD_TRIPSHEET_ROUTE')">
												<a class=" btn btn-link col-sm-offset-1"
													onclick="visibilityRoute('routeEnter', 'routeSelect');"> <i
													class="fa fa-plus"> New</i>
												</a> </sec:authorize><label id="errorRoute" class="error"></label>
												<input type="hidden" name="isNewRoute" id="isNewRoute" value="0"/>
											</div>
											<div id="routeEnter" class="contact_Hide">

												<div class="I-size">
													<input type="text" class="form-text col-md-8"
														id="routeName" name="routeName"
														value="${TripSheet.routeName}"
														placeholder="Enter Route Name"> <a
														class=" btn btn-link col-sm-offset-1"
														onclick="visibilityRoute('routeEnter', 'routeSelect');"> <i
														class="fa fa-minus"> Select</i>
													</a> <label id="errorRouteName" class="error"></label>
												</div>
											</div>
										</div>
										</c:if>

									<%-- 	<c:if test="${configuration.showSubroute}">
											<div class="row1">
												<label class="L-size  control-label">SubRoute
													Service :</label>
												<div class="I-size" id="subRouteSelect">
													<div class="col-md-8">
														<input type="hidden" id="SRid"
															value="${TripSheet.subRouteID}" required="required">


														<!-- registr -->
														<input type="hidden" id="SRname"
															value="${TripSheet.subRouteName}" required="required">

														<!-- Select SubRoute -->
														<!-- <input type="hidden" id="TripRouteSubList" name="subRouteID"
														style="width: 100%;" value="0"
														placeholder="Please Enter 3 or more Route Name, NO " /> -->
														<select class="form-control select2" name="subRouteID"
															id="TripRouteSubList" style="width: 100%;">
															<option value="${TripSheet.subRouteID}">${TripSheet.subRouteName}</option>
														</select>
													</div>
												</div>
											</div>
										</c:if> --%>


										<c:if test="${configuration.showSubroute}">
											<div class="row1">
												<label class="L-size control-label">SubRoute Service
													: </label>
												<div class="I-size">
													<input class="form-text" placeholder="" id="SRid"
														name="subRouteName" type="text" maxlength="50"
														value="${TripSheet.subRouteName}"> <label
														class="error" id="errortripBook" style="display: none">
													</label>
												</div>
											</div>
										</c:if>


										<div class="row1">
											<label class="L-size control-label">Date Of Journey :
												<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="input-group input-append date">
													<input type="text" class="form-text" name="tripOpenDate"
														required id="reservation" maxlength="26"
														data-inputmask="'mask': ['99-99-9999  to  99-99-9999']"
														data-mask=""
														value="${TripSheet.tripOpenDate}  to  ${TripSheet.closetripDate}">
													<span class="input-group-addon add-on"><span
														class="fa fa-calendar"></span></span>
												</div>
											</div>
										</div>

										<div class="row1">

											<label class="L-size control-label">Group Service :</label>
											<div class="I-size">
												<input class="form-text" name="vehicle_Group"
													id="vehicle_Group" placeholder="" type="text"
													maxlength="50" readonly="readonly"
													value="${TripSheet.vehicle_Group}"
													onkeypress="return IsTripSheetPanNO(event);"
													ondrop="return false;"> <label class="error"
													id="errorTripSheetPanNO" style="display: none"> </label>
											</div>

										</div>
										<div class="row1">
											<label class="L-size control-label">Opening KM : </label>
											<div class="I-size">
												<input class="form-text" name="tripOpeningKM"
													id="tripOpeningKM" placeholder="" type="text"
													maxlength="50" onkeypress="return tripOpening(event);"
													onkeyup="return validateOdometerOnKeyUp();"
													value="${TripSheet.tripOpeningKM}" ondrop="return false;">
													<div style="color: red">Odometer Range : ${minAllowed} - ${maxAllowed}</div>
												<label class="error" id="errortripOpening"
													style="display: none"> </label> <label id="errorOpening"
													class="error"></label>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Booking
												References : </label>
											<div class="I-size">
												<input class="form-text" name="tripBookref" placeholder=""
													type="text" maxlength="50"
													onkeypress="return tripBook(event);"
													value="${TripSheet.tripBookref}" ondrop="return false;">
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
													<input class="form-text" name="tripStartDiesel" value="${TripSheet.tripStartDiesel}"
														onkeypress="return isDecimalNumber(event, this);"
														id="tripStartDiesel" type="text" max="1000" required="required">
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
													<input type="hidden" id="loadId" value="${TripSheet.loadTypesId}"
														required="required">													
													<input type="hidden" id="loadName"
														value="${TripSheet.loadTypeName}" required="required">												
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
							<c:forEach items="${TripSheetAdvance}" var="TripSheetAdvance">
								<fieldset>

									<legend> Advance Payment Details </legend>
									<div class="box">
										<div class="box-body">

											<input type="hidden" name="tripAdvanceID"
												value="${TripSheetAdvance.tripAdvanceID}">

											<div class="row1">
												<label class="L-size control-label">Advance Amount :<abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<input class="string required form-text"
														name="AdvanceAmount" maxlength="50" type="text"
														required="required"
														onkeypress="return IsAdvanceAmount(event);"
														value="${TripSheetAdvance.advanceAmount}"
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
														value="${TripSheetAdvance.advanceRefence}"
														ondrop="return false;"> <label class="error"
														id="errorAdvanceRefence" style="display: none"> </label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Advance PaidBy :
													<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<input class="string required form-text" id="advancePaidby"
														name="advancePaidby" maxlength="50" type="text"
														required="required" readonly="readonly"
														value="${TripSheetAdvance.advancePaidby}"
														onkeypress="return IsAdvancePaidby(event);"
														ondrop="return false;"> 
														<input type="hidden" name = "advancePaidbyId" value="${TripSheetAdvance.advancePaidbyId}" />
														<input type="hidden" name = "createdById" value="${TripSheetAdvance.createdById}" />
														<label class="error"
														id="errorAdvancePaidby" style="display: none"> </label>

												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Place :</label>
												<div class="I-size">
													<input class="string required form-text"
														readonly="readonly" name="advancePlace" maxlength="50"
														type="text" value="${TripSheetAdvance.advancePlace}"
														onkeypress="return IsAdvancePlace(event);"
														ondrop="return false;">
														<input type="hidden" name = "advancePlaceId" value="${TripSheetAdvance.advancePlaceId}" />
														 <label class="error"
														id="errorAdvancePlace" style="display: none"> </label>

												</div>
											</div>


											<input type="hidden" name="tripTotalAdvance"
												value="${TripSheet.tripTotalAdvance}"> <input
												type="hidden" name="tripTotalexpense"
												value="${TripSheet.tripTotalexpense}"> <input
												type="hidden" name="tripTotalincome"
												value="${TripSheet.tripTotalincome}">

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
														ondrop="return false;">${TripSheetAdvance.advanceRemarks}
													</textarea>
													<label class="error" id="errorAdvanceRemarks"
														style="display: none"> </label>
												</div>
											</div>
										</div>
									</div>
								</fieldset>
							</c:forEach>

							<input type="hidden" value="" name="tripStutesId" id="tripStutes">
							<fieldset class="form-actions">
								<div class="row1">

									<div class="pull-right">

<!-- 										<input class="btn btn-default" name="commit" type="submit" -->
<!-- 											value="Save TripSheet" onclick="return validateTrip();"> -->
										<input class="btn btn-success" name="commit" type="submit"
											value="Dispatch vehicle"
											onclick="return validateDispatchTrip();"> <a
											class="btn btn-default" href="<c:url value="/newTripSheetEntries.in?loadTypeId=2"/>">Cancel</a>
									</div>
								</div>
							</fieldset>
						</div>
					</form>
				</div>
			</sec:authorize>
		</div>
	</section>

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
	<script type="text/javascript">
	$(document).ready(function(){
		$("#reservation").daterangepicker({minDate:moment().subtract("days",0)}),window.onload=loadDispatch}),$(document).ready(function(){var a=$("#vid").val(),e=$("#vregist").val();$("#TripSelectVehicle").select2("data",{id:a,text:e});var t=$("#did").val(),d=$("#dname").val();$("#driverList").select2("data",{id:t,text:d});var i=$("#dsid").val(),l=$("#dsname").val();$("#driverList2").select2("data",{id:i,text:l});var r=$("#cid").val(),v=$("#cname").val();$("#Cleaner").select2("data",{id:r,text:v});var Srid=$("#SRid").val(),SRname=$("#SRname").val();$("#subRouteSelect").select2("data",{id:Srid,text:SRname});var n=$("#Rid").val(),c=$("#Rname").val();$("#TripRouteList").select2("data",{id:n,text:c});
			$('#vehicleGroupId').on('change', function() {
			$('#vehicle_Group').val($("#vehicleGroupId option:selected").text().trim());
		});		
			
			var LoadId = $("#loadId").val();
			var LoadName = $("#loadName").val();
			$('#loadListId').select2('data', {
			id : LoadId,
			text : LoadName
			});
			
			$("#TripRouteList").change(function() {
		        $.getJSON("getTripRouteSubListById.in", {
		            vehicleGroup: $(this).val(), ajax: "true"
		        }
		        , function(a) {
		        	$("#select2-chosen-6").html('select');
		            for( var b='',c=a.length, d=0; c>d;d++){
		            	b+='<option value="0" selected>select</option>';
		            	b+='<option value="'+a[d].routeID+'">'+a[d].routeNo + " " + a[d].routeName+"</option>";
			            b+="</option>", $("#TripRouteSubList").html(b)
		            }
		            	
		        }
		        )
		    } 
		    );
			
			$("#TripRouteSubList").select2();
			
		});
	
	function visibilityvehicleEdit(e, r) {
	    var t 			 = document.getElementById(e),
	        n 			 = document.getElementById(r),
	        vehicleGroup = document.getElementById("vehicle_group_id");
	    if(n.style.display == "block"){
	    	 vehicleGroupVal = $('#vehicle_Group').val();
	    }
	    "block" == t.style.display ? (t.style.display = "none", n.style.display = "block", vehicleGroup.style.display = "none") : (t.style.display = "block", n.style.display = "none", vehicleGroup.style.display = "block")
	   
	    if(t.style.display == 'block'){
	    	$('#vehicle_Group').val($("#vehicleGroupId option:selected").text().trim());
	    }else{
	    	$('#vehicle_Group').val($('#preGroupService').val());
	    }
	}
	</script>


</div>