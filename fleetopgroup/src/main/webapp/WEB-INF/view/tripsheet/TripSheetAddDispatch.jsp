<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<div class="content-wrapper" onload="javascript:load();">
	<section class="content-header">
		<h1>
			<small> <a class="btn btn-default"
				onclick="history.go(-1); return true;"> <span
					class="fa fa-arrow-left"> Back</span>
			</a> <a href="<c:url value="/open"/>"><spring:message
						code="label.master.home" /></a> / <a
				href="<c:url value="/newTripSheetEntries.in"/>">TripSheet</a> / <a
				href="<c:url value="/newTripSheetEntries.in?loadTypeId=2"/>">Dispatch</a> / <a
				href="<c:url value="/newTripSheetEntries.in?loadTypeId=3"/>">Manage</a> / <a
				href="<c:url value="/newTripSheetEntries.in?loadTypeId=4"/>">Advance Close</a> / <a
				href="<c:url value="/newTripSheetEntries.in?loadTypeId=5"/>">Payment</a> / <a
				href="<c:url value="/newTripSheetEntries.in?loadTypeId=6"/>">A/C Closed</a> / <span
				id="NewVehi">New Trip Sheet</span></small>
		</h1>
		<div class="breadcrumb">
			<div id="langSelect"></div>
			<a href="#"><i class="fa fa-dashboard"></i> Level</a>
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
	    <script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>  
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Triplanguage.js" />"></script>
		<script type="text/javascript">
		$(document).ready(function(){$.getJSON("getVehicleTripList.in",function(e){$("#TripSelectVehicle").empty(),$("#TripSelectVehicle").append($("<option>").text("Please Select Vehicle").attr("value",0)),$.each(e,function(e,t){$("#TripSelectVehicle").append($("<option>").text(t.vehicle_registration).attr("value",t.vid))})}),$.getJSON("getDriverTripList.in",function(e){$("#driverList").empty(),$("#driverList").append($("<option>").text("Please Select Driver").attr("value",0)),$.each(e,function(e,t){$("#driverList").append($("<option>").text(t.driver_empnumber+" - "+t.driver_firstname).attr("value",t.driver_id))}),$("#driverList2").empty(),$("#driverList2").append($("<option>").text("Please  Select Driver").attr("value",0)),$.each(e,function(e,t){$("#driverList2").append($("<option>").text(t.driver_empnumber+" - "+t.driver_firstname).attr("value",t.driver_id))})}),$.getJSON("getTripRouteList.in",function(e){$("#TripRouteList").empty(),$("#TripRouteList").append($("<option>").text("Please  Select Route").attr("value",0)),$.each(e,function(e,t){$("#TripRouteList").append($("<option>").text(t.routeNo+" - "+t.routeName).attr("value",t.routeID))})})});
		</script>
		<div class="row">
			<div class="col-md-offset-1 col-md-9 col-sm-9 col-xs-12">

				<form action="saveTripSheet.in" method="post">
					<div class="form-horizontal">
						<fieldset>
							<legend>Trip Sheet Details</legend>
							<div class="box">
								<div class="box-body">
									<div class="row1">
										<label class="L-size control-label" for="issue_vehicle_id">Vehicle
											: </label>
										<div class="I-size">

											<select class="form-text select2" name="vid"
												id="TripSelectVehicle">
											</select> <label id="errorVehicle" class="error"></label>

										</div>
									</div>
									<div class="row1">
										<label class="L-size control-label" for="issue_vehicle_id">Driver
											1 : </label>
										<div class="I-size">

											<select class="form-text select2" name="tripFristDriverID"
												id="driverList">
											</select> <label id="errorDriver1" class="error"></label>
										</div>
									</div>
									<div class="row1">
										<label class="L-size control-label" for="issue_vehicle_id">Driver
											2 :</label>
										<div class="I-size">

											<select class="form-text select2" name="tripSecDriverID"
												id="driverList2">
											</select> <label id="errorDriver2" class="error"></label>
										</div>
									</div>
									<div class="row1">
										<label class="L-size control-label" for="issue_vehicle_id">Cleaner
											:</label>
										<div class="I-size">

											<input type="text" class="form-text" name="tripCleanerName">
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
										<div class="I-size">
											<select class="form-text select2" name="routeID"
												id="TripRouteList" required="required">
											</select> <label id="errorRoute" class="error"></label>
										</div>
									</div>

									<div class="row1">
										<label class="L-size control-label">Date Of Journey :
											<abbr title="required">*</abbr>
										</label>
										<div class="I-size">
											<div class="input-group input-append date" id="TripStartDate">
												<input type="text" class="form-text" name="tripOpenDate"
													required="required" data-inputmask="'alias': 'dd-mm-yyyy'"
													data-mask="" /> <span class="input-group-addon add-on">
													<span class="fa fa-calendar"></span>
												</span>
											</div>
										</div>
									</div>

									<div class="row1">

										<label class="L-size control-label">Group Service :</label>
										<div class="I-size">
											<input class="form-text" name="vehicle_Group"
												id="vehicle_Group" placeholder="" type="text" maxlength="50"
												readonly="readonly"
												onkeypress="return IsTripSheetPanNO(event);"
												ondrop="return false;"> <label class="error"
												id="errorTripSheetPanNO" style="display: none"> </label>
										</div>

									</div>
									<div class="row1">
										<label class="L-size control-label">Opening KM : </label>
										<div class="I-size">
											<input class="form-text" name="tripOpeningKM"
												id="tripOpeningKM" placeholder="" type="text" maxlength="50"
												 onkeypress="return isNumberKey(event,this);"
												ondrop="return false;"> <label class="error"
												id="errortripOpening" style="display: none"> </label> <label
												id="errorOpening" class="error"></label>
										</div>
									</div>
									<div class="row1">
										<label class="L-size control-label">Booking References
											: </label>
										<div class="I-size">
											<input class="form-text" name="tripBookref" placeholder=""
												type="text" maxlength="50"
												onkeypress="return tripBook(event);" ondrop="return false;">
											<label class="error" id="errortripBook" style="display: none">
											</label>

										</div>
									</div>
								</div>
							</div>
						</fieldset>
						<fieldset>
							<legend> Advance Payment Details </legend>
							<div class="box">
								<div class="box-body">

									<div class="row1">
										<label class="L-size control-label">Advance Amount :<abbr
											title="required">*</abbr></label>
										<div class="I-size">
											<input class="string required form-text" name="AdvanceAmount"
												maxlength="50" type="text" required="required"
												onkeypress="return IsAdvanceAmount(event);"
												ondrop="return false;"> <label class="error"
												id="errorAdvanceAmount" style="display: none"> </label>

										</div>
									</div>
									<div class="row1">
										<label class="L-size control-label">Place :</label>
										<div class="I-size">
											<input class="string required form-text" name="advancePlace"
												maxlength="50" type="text"
												onkeypress="return IsAdvancePlace(event);"
												ondrop="return false;"> <label class="error"
												id="errorAdvancePlace" style="display: none"> </label>

										</div>
									</div>
									<div class="row1">
										<label class="L-size control-label">Advance Paid By :
											<abbr title="required">*</abbr>
										</label>
										<div class="I-size">
											<input class="string required form-text" id="advancePaidby"
												name="advancePaidby" maxlength="50" type="text"
												required="required"
												onkeypress="return IsAdvancePaidby(event);"
												ondrop="return false;"> <label class="error"
												id="errorAdvancePaidby" style="display: none"> </label>

										</div>
									</div>

									<div class="row1">
										<label class="L-size control-label">Advance Reference
											:</label>
										<div class="I-size">
											<input class="string required form-text"
												name="advanceRefence" maxlength="50" type="text"
												onkeypress="return IsAdvanceRefence(event);"
												ondrop="return false;"> <label class="error"
												id="errorAdvanceRefence" style="display: none"> </label>

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
								</div>
							</div>
						</fieldset>
						<fieldset class="form-actions">
							<div class="row1">
								<div class="col-md-10">
									<a class="btn btn-link" href="ShowTripSheet.in">Cancel</a>
								</div>
								<div class="pull-right">
									<input class="btn btn-default" name="commit" type="submit"
										value="Save TripSheet" onclick="return validateTrip();">
									<input class="btn btn-success" name="commit" type="submit"
										value="Dispatch vehicle"
										onmouseup="return validateDispatchTrip();">
								</div>
							</div>
						</fieldset>
					</div>
				</form>
			</div>
		</div>
	</section>
</div>