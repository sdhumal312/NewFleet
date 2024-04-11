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
	<c:if test="${param.InAnotherTrip eq true}">
		<div class="alert alert-danger">
			<button class="close" data-dismiss="alert" type="button">x</button>
			${InAnother} Please select..
		</div>
	</c:if>
	<section class="content">
		<div class="row">
			<div class="col-md-offset-1 col-md-9 col-sm-9 col-xs-12">

				<c:if test="${param.Close eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						You should be close first Driver Account in those Tripsheet
						${CloseTS} <br> You can save TripSheet. You can't Dispatch.
					</div>
				</c:if>
				<sec:authorize access="!hasAuthority('ADD_TRIPSHEET')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('ADD_TRIPSHEET')">
					<form action="saveTripSheet.in" method="post">
						<div class="form-horizontal">
							<fieldset>
								<legend>Trip Sheet Details</legend>
								<div class="box">
									<div class="box-body">
										<div class="row1">
											<label class="L-size control-label" for="issue_vehicle_id">Vehicle
												: </label>
											<div class="I-size" id="vehicleSelect">
												<div class="col-md-9">
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
												<div class="col-md-1">
													<a class=" btn btn-link "
														onclick="visibilityvehicle('TripVehicle', 'vehicleSelect');">
														<i class="fa fa-plus"> New</i>
													</a>
												</div>
												<label id="errorVehicle" class="error"></label>
											</div>
											<div id="TripVehicle" class="contact_Hide">

												<div class="I-size">
													<input type="text" class="form-text col-md-8"
														name="vehicle_registration" id="vehicle_registration"
														placeholder="Enter Vehicle Number eg: KA-45-6767">
													<a class=" btn btn-link col-sm-offset-1"
														onclick="visibilityvehicle('TripVehicle', 'vehicleSelect');">
														<i class="fa fa-minus"> Select</i>
													</a> <label id="errorVehicleName" class="error"></label>
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

										</div>
										<div class="help-block" id="last_occurred">
											<span class="loading ng-hide" id="loading"> <img
												alt="Loading" class="loading-img"
												src="resources/images/ajax-loader.gif">
											</span>
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
															data-mask="" required id="reservationToTripSheet"
															maxlength="26"> <span
															class="input-group-addon add-on"><span
															class="fa fa-calendar"></span></span>
													</div>
												</div>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="issue_vehicle_id">Driver
												1 : </label>
											<div class="I-size" id="driverSelect">
												<div class="col-md-9">
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
												<!-- <div class="col-md-1">
													<a class=" btn btn-link "
														onclick="visibility('contactTwo', 'driverSelect');"> <i
														class="fa fa-plus"> New</i>
													</a>
												</div> -->
												<label id="errorDriver1" class="error"></label>
											</div>

											<div id="contactTwo" class="contact_Hide">

												<div class="I-size">
													<input type="text" class="form-text col-md-8"
														id="tripFristDriverName" name="tripFristDriverName"
														value="${TripSheet.tripFristDriverMobile}"
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
												2 :</label>
											<div class="I-size" id="driver2Select">
												<div class="col-md-9">
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
												<div class="col-md-1">
													<!-- <a class=" btn btn-link"
														onclick="visibility('driver2enter', 'driver2Select');">
														<i class="fa fa-plus"> New</i>
													</a> -->
												</div>
												<label id="errorDriver2" class="error"></label>
											</div>

											<div id="driver2enter" class="contact_Hide">

												<div class="I-size">
													<input type="text" class="form-text col-md-8"
														value="${TripSheet.tripSecDriverName}"
														id="tripSecDriverName" name="tripSecDriverName"
														placeholder="Enter Driver 2 Name"> <a
														class=" btn btn-link col-sm-offset-1"
														onclick="visibility('driver2enter', 'driver2Select');">
														<i class="fa fa-minus"> Select</i>
													</a> <label id="errorDriver2Name" class="error"></label>
												</div>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="issue_vehicle_id">Cleaner
												:</label>
											<div class="I-size" id="cleanerSelect">

												<div class="col-md-9">
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
												<div class="col-md-1">
													<!-- <a class=" btn btn-link"
														onclick="visibility('cleanerEnter', 'cleanerSelect');">
														<i class="fa fa-plus"> New</i>
													</a> -->
												</div>
												<label id="errorCleaner" class="error"></label>
											</div>

											<div id="cleanerEnter" class="contact_Hide">

												<div class="I-size">
													<input type="text" class="form-text col-md-8"
														value="${TripSheet.tripCleanerMobile}"
														id="tripCleanerName" name="tripCleanerName"
														placeholder="Enter Clener Name"> <a
														class=" btn btn-link col-sm-offset-1"
														onclick="visibility('cleanerEnter', 'cleanerSelect');">
														<i class="fa fa-minus"> Select</i>
													</a> <label id="errorCleanerName" class="error"></label>
												</div>
											</div>
										</div>

									</div>
								</div>
							</fieldset>
							<fieldset>
								<legend>Route</legend>
								<div class="box">
									<div class="box-body">
										<div class="row1">

											<label class="L-size  control-label">Route Service :<abbr
												title="required">*</abbr></label>
											<div class="I-size" id="routeSelect">

												<div class="col-md-9">
													<input type="hidden" id="Rid" value="${TripSheet.routeID}"
														required="required">
													<!-- registr -->
													<input type="hidden" id="Rname"
														value="${TripSheet.routeName}" required="required">
													<!-- Select Route -->
													<input type="hidden" id="TripRouteList" name="routeID"
														style="width: 100%;" value="0"
														placeholder="Please Enter 3 or more Route Name, NO " />

												</div>
												<sec:authorize access="hasAuthority('ADD_TRIPSHEET_ROUTE')">
													<div class="col-md-1">
														<a class=" btn btn-link"
															onclick="visibility('routeEnter', 'routeSelect');"> <i
															class="fa fa-plus"> New</i>
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
														onclick="visibility('routeEnter', 'routeSelect');"> <i
														class="fa fa-minus"> Select</i>
													</a> <label id="errorRouteName" class="error"></label>
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
													ondrop="return false;"> <label class="error"
													id="errortripOpening" style="display: none"> </label> <label
													id="errorOpening" class="error"></label>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Booking
												References : </label>
											<div class="I-size">
												<input class="form-text" name="tripBookref" placeholder=""
													type="text" maxlength="50"
													onkeypress="return tripBook(event);" ondrop="return false;">
												<label class="error" id="errortripBook"
													style="display: none"> </label>

											</div>
										</div>
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
														ondrop="return false;"> <label class="error"
														id="errorAdvancePaidby" style="display: none"> </label>
											<input type="hidden" name="advancePaidbyId" value="${paidById}" />
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Place :</label>
												<div class="I-size">
													<input class="string required form-text" value="${place}"
														readonly="readonly" name="advancePlace" maxlength="50"
														type="text" onkeypress="return IsAdvancePlace(event);"
														ondrop="return false;"> <label class="error"
														id="errorAdvancePlace" style="display: none"> </label>

												</div>
												<input type="hidden" name="advancePlaceId" value="${placeId}" />
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepicker.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/script.js" />"></script>

	<script>
		$(function() {
			$("#reservationToTripSheet").daterangepicker({
				minDate : moment().subtract("days", 0)
			}), window.onload = loadDispatch
		}), $(document).ready(function() {
			var a = $("#vid").val(), e = $("#vregist").val();
			$("#TripSelectVehicle").select2("data", {
				id : a,
				text : e
			});
			var t = $("#did").val(), d = $("#dname").val();
			$("#driverList").select2("data", {
				id : t,
				text : d
			});
			var i = $("#dsid").val(), l = $("#dsname").val();
			$("#driverList2").select2("data", {
				id : i,
				text : l
			});
			var r = $("#cid").val(), v = $("#cname").val();
			$("#Cleaner").select2("data", {
				id : r,
				text : v
			});
			var n = $("#Rid").val(), c = $("#Rname").val();
			$("#TripRouteList").select2("data", {
				id : n,
				text : c
			});
			$('#vehicleGroupId').on('change', function() {
				$('#vehicle_Group').val($("#vehicleGroupId option:selected").text().trim());
			});
		});
	</script>
</div>