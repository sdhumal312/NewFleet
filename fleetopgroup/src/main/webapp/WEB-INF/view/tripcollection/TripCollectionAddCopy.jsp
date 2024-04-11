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
						href="<c:url value="/newTripCol.in"/>">Trip Collection</a> / <span>Add
						Trip Collection</span>
				</div>
				<div class="pull-right">
					<a href="newTripCol.in">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<c:if test="${param.success eq true}">
		<div class="alert alert-success">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This TripCollection Created Successfully.
		</div>
	</c:if>
	<c:if test="${param.danger eq true}">
		<div class="alert alert-danger">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This TripCollection Already Exists
		</div>
	</c:if>
	<c:if test="${param.alreadyClose eq true}">
		<div class="alert alert-danger">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This TripCollection Date ${closeDate} Already Closed.
		</div>
	</c:if>
	<section class="content">
		<div class="row">
			<div class="col-md-offset-1 col-md-9 col-sm-9 col-xs-12">
				<sec:authorize access="!hasAuthority('ADD_TRIPSHEET')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('ADD_TRIPSHEET')">
					<form id="formTripCollection"
						action="<c:url value="/saveTripCol.in"/>" method="post"
						enctype="multipart/form-data" name="formTripCollection"
						role="form" class="form-horizontal">
						<div class="form-horizontal">
							<fieldset>
								<legend>Trip Collection Details</legend>
								<div class="box">
									<div class="box-body">
										<div class="row1" id="grpvehicleName" class="form-group">
											<label class="L-size control-label" for="TripSelectVehicle">Vehicle
												: <abbr title="required">*</abbr>
											</label>
											<div class="I-size" id="vehicleSelect">
												<input type="hidden" id="Ovid" value="${TripCol.VID}">
												<input type="hidden" id="Ovname"
													value="${TripCol.VEHICLE_REGISTRATION}"> <input
													type="hidden" id="TripSelectVehicle" name="VID"
													style="width: 100%;"
													placeholder="Please Enter 2 or more Vehicle Name" /><span
													id="vehicleNameIcon" class=""></span>
												<div id="vehicleNameErrorMsg" class="text-danger"></div>
												<label id="errorVehicle" class="error"></label>
											</div>
										</div>
										<div class="row1" id="grpvehicleGroup" class="form-group">
											<label class="L-size control-label" for="vehicle_Group">Group
												Service :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input class="form-text" name="VEHICLE_GROUP"
													value="${TripCol.VEHICLE_GROUP}" id="vehicle_Group"
													placeholder="" type="text" maxlength="50"
													readonly="readonly"
													onkeypress="return IsTripSheetPanNO(event);"
													ondrop="return false;"><span id="vehicleGroupIcon"
													class=""></span>
												<div id="vehicleGroupErrorMsg" class="text-danger"></div>
												<label class="error" id="errorTripSheetPanNO"
													style="display: none"> </label>
											</div>
											<input id="VEHICLE_GROUP_ID"  name="VEHICLE_GROUP_ID" value="${TripCol.VEHICLE_GROUP_ID}" type="hidden" />
										</div>
										<div class="row1" id="grpdriverName" class="form-group">
											<label class="L-size control-label" for="driverList">Driver
												: <abbr title="required">*</abbr>
											</label>
											<div class="I-size" id="driverSelect">
												<input type="hidden" id="Odid"
													value="${TripCol.TRIP_DRIVER_ID}"> <input
													type="hidden" id="Odname"
													value="${TripCol.TRIP_DRIVER_NAME}"> <input
													type="hidden" id="driverList" name="TRIP_DRIVER_ID"
													style="width: 100%;"
													placeholder="Please Enter 3 or more Driver Name, No" /><span
													id="driverNameIcon" class=""></span>
												<div id="driverNameErrorMsg" class="text-danger"></div>
												<label id="errorDriver1" class="error"></label>
											</div>
										</div>
										<div class="row1" id="grpconductorName" class="form-group">
											<label class="L-size control-label" for="ConductorList">Conductor:
												<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="hidden" id="Ocoid"
													value="${TripCol.TRIP_CONDUCTOR_ID}"> <input
													type="hidden" id="Oconame"
													value="${TripCol.TRIP_CONDUCTOR_NAME}"> <input
													type="hidden" id="ConductorList" name="TRIP_CONDUCTOR_ID"
													style="width: 100%;"
													placeholder="Please Enter 3 or more Conductor Name, NO" />
												<span id="conductorNameIcon" class=""></span>
												<div id="conductorNameErrorMsg" class="text-danger"></div>
												<label id="errorDriver2" class="error"></label>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="issue_vehicle_id">Cleaner
												:</label>
											<div class="I-size" id="cleanerSelect">
												<input type="hidden" id="Oclid"
													value="${TripCol.TRIP_CLEANER_ID}"> <input
													type="hidden" id="Oclname"
													value="${TripCol.TRIP_CLEANER_NAME}"> <input
													type="hidden" id="Cleaner" name="TRIP_CLEANER_ID"
													style="width: 100%;" value="0"
													placeholder="Please Enter 3 or more Cleaner Name, No" /> <label
													id="errorCleaner" class="error"></label>
											</div>
										</div>
									</div>
								</div>
							</fieldset>
							<fieldset>
								<legend>Route</legend>
								<div class="box">
									<div class="box-body">
										<div class="row1" id="grptripRouteName" class="form-group">

											<label class="L-size  control-label" for="TripRouteList">Route
												Service :<abbr title="required">*</abbr>
											</label>
											<div class="I-size" id="routeSelect">
												<input type="hidden" id="Orid"
													value="${TripCol.TRIP_ROUTE_ID}"> <input
													type="hidden" id="Orname"
													value="${TripCol.TRIP_ROUTE_NAME}"> <input
													type="hidden" id="TripRouteList" name="TRIP_ROUTE_ID"
													style="width: 100%;"
													placeholder="Please Enter 3 or more Route Name, NO " /> <span
													id="tripRouteNameIcon" class=""></span>
												<div id="tripRouteNameErrorMsg" class="text-danger"></div>
												<label id="errorRoute" class="error"></label>
											</div>
										</div>
										<div class="row1" id="grptripDate" class="form-group">
											<label class="L-size control-label" for="tripDate">Date
												Of Journey : <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="input-group input-append date"
													id="TripStartDate">
													<input type="text" class="form-text" name="TRIP_OPEN_DATE"
														required="required" id="tripDate"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
												<span id="tripDateIcon" class=""></span>
												<div id="tripDateErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Opening KM : </label>
											<div class="I-size">
												<input class="form-text" name="TRIP_OPEN_KM"
													id="tripOpeningKM" placeholder="" type="text"
													maxlength="50" onkeypress="return tripOpening(event);"
													ondrop="return false;"> <label class="error"
													id="errortripOpening" style="display: none"> </label> <label
													id="errorOpening" class="error"></label>
											</div>
										</div>
										<div class="row1" id="grptripliter" class="form-group">
											<label class="L-size control-label" for="tripliter">Diesel
												Liter :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input class="form-text" name="TRIP_DIESEL_LITER"
													id="tripliter" placeholder="" type="text" maxlength="50"
													onkeypress="return tripOpening(event);"
													ondrop="return false;"><span id="tripliterIcon"
													class=""></span>
												<div id="tripliterErrorMsg" class="text-danger"></div>
												<label class="error" id="errortripOpening"
													style="display: none"> </label> <label id="errorOpening"
													class="error"></label>
											</div>
										</div>
										<div class="row1" id="grptripSingl" class="form-group">
											<label class="L-size control-label" for="tripSingl">No
												Of Running Singl :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input class="form-text" name="TRIP_SINGL" id="tripSingl"
													placeholder="" type="text" maxlength="50"
													onkeypress="return tripOpening(event);"
													ondrop="return false;"><span id="tripSinglIcon"
													class=""></span>
												<div id="tripSinglErrorMsg" class="text-danger"></div>
												<label class="error" id="errortripOpening"
													style="display: none"> </label> <label id="errorOpening"
													class="error"></label>
											</div>
										</div>


										<div class="row1">
											<label class="L-size control-label">Remarks :</label>
											<div class="I-size">
												<textarea class="form-text" id="fuel_comments"
													name="TRIP_REMARKS" rows="3" maxlength="250"
													onkeypress="return IsAdvanceRemarks(event);"
													ondrop="return false;">
														
													</textarea>
												<label class="error" id="errorAdvanceRemarks"
													style="display: none"> </label>
											</div>
										</div>

										<input type="hidden" value="" name="tripStutes"
											id="tripStutes">

									</div>
								</div>
							</fieldset>

							<fieldset class="form-actions">
								<div class="row1">
									<div class="pull-right">
										<button type="submit" class="btn btn-success">Create
											Trip Collection</button>
										<a class="btn btn-default" href="newTripCol.in">Cancel</a>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripCollection.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepicker.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			var h = $("#Ovid").val(), i = $("#Ovname").val();
			$("#TripSelectVehicle").select2("data", {
				id : h,
				text : i
			});
			var j = $("#Odid").val(), k = $("#Odname").val();
			$("#driverList").select2("data", {
				id : j,
				text : k
			});
			var l = $("#Ocoid").val(), m = $("#Oconame").val();
			$("#ConductorList").select2("data", {
				id : l,
				text : m
			});
			var l = $("#Oclid").val(), m = $("#Oclname").val();
			$("#Cleaner").select2("data", {
				id : l,
				text : m
			});
			var l = $("#Orid").val(), m = $("#Orname").val();
			$("#TripRouteList").select2("data", {
				id : l,
				text : m
			})
		});
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>