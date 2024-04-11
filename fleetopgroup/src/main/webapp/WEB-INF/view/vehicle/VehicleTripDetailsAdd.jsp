<%@ include file="taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<div class="content-wrapper" onload="javascript:loadTripSheet();">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="vehicle.in"><spring:message code="label.master.home" /></a>
					/ <small><a
						href="<c:url value="showVehicle.in?vid=${vehicle.vid}"/>"><c:out
								value="${vehicle.vehicle_registration}" /></a> / <a
						href="<c:url value="VehicleTripDetails.in?vid=${vehicle.vid}"/>">New
							Vehicle TripSheet</a></small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_TRIPSHEET')">
						<a class="btn btn-success" href="addTripSheetEntries.in"> <i
							class="fa fa-plus"></i> Create TripSheet
						</a>
					</sec:authorize>
					<a class="btn btn-link" href="showVehicle.in?vid=${vehicle.vid}">Cancel</a>
				</div>
			</div>
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_VEHICLE')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
					<div class="pull-left">
						<a
							href="${pageContext.request.contextPath}/getImageVehicle/${vehicle.vehicle_photoid}.in"
							class="zoom" data-title="Amazing Nature"
							data-footer="The beauty of nature" data-type="image"
							data-toggle="lightbox"> <img
							src="${pageContext.request.contextPath}/getImageVehicle/${vehicle.vehicle_photoid}.in"
							class="img-rounded" alt="Cinque Terre" width="100" height="100" />
						</a>
					</div>
					<div class="pull-left1">
						<h3 class="secondary-header-title">
							<a href="showVehicle.in?vid=${vehicle.vid}"> <c:out
									value="${vehicle.vehicle_registration}" />
							</a>
						</h3>

						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Status"><a
										href="#"><c:out value=" ${vehicle.vehicle_Status}" /></a></span></li>
								<li><span class="fa fa-clock-o" aria-hidden="true"
									data-toggle="tip" data-original-title="Odometer"><a
										href="#"><c:out value=" ${vehicle.vehicle_Odometer}" /></a></span></li>
								<li><span class="fa fa-bus" aria-hidden="true"
									data-toggle="tip" data-original-title="Type"><a href="#"><c:out
												value=" ${vehicle.vehicle_Type}" /></a></span></li>
								<li><span class="fa fa-map-marker" aria-hidden="true"
									data-toggle="tip" data-original-title="Location"><a
										href="#"><c:out value=" ${vehicle.vehicle_Location}" /></a></span></li>
								<li><span class="fa fa-users" aria-hidden="true"
									data-toggle="tip" data-original-title="Group"><a
										href="#"><c:out value=" ${vehicle.vehicle_Group}" /></a></span></li>
								<li><span class="fa fa-road" aria-hidden="true"
									data-toggle="tip" data-original-title="Route"><a
										href="#"><c:out value=" ${vehicle.vehicle_RouteName}" /></a></span></li>

							</ul>
						</div>
					</div>
				</sec:authorize>
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content-body">
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
				<div class="col-md-9 col-sm-9 col-xs-12">
					<c:if test="${param.deleteFuel eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Fuel Data successfully Deleted.
						</div>
					</c:if>
					<c:if test="${param.saveFuel eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Fuel Data Successfully Created.
						</div>
					</c:if>
					<c:if test="${param.duplicateFuelEntries eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Fuel Entries was Already created..(or) This Duplicate
							Entries Reference Number.
						</div>
					</c:if>
					<div class="row">
						<div class="main-body">

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
														<div class="col-md-9">
															<select name="vid" id="SelectVehicle"
																style="width: 100%;">

																<option value=""><!-- please select --></option>

																<option value="${vehicle.vid}"><c:out
																		value="${vehicle.vehicle_registration}" /></option>

															</select> <label id="errorVehicle" class="error"></label>
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
																placeholder="Please Enter 3 or more Driver Name, No" />
														</div>
														<div class="col-md-1">
															<a class=" btn btn-link "
																onclick="visibility('contactTwo', 'driverSelect');">
																<i class="fa fa-plus"> New</i>
															</a>
														</div>
														<label id="errorDriver1" class="error"></label>
													</div>

													<div id="contactTwo" class="contact_Hide">

														<div class="I-size">
															<input type="text" class="form-text col-md-8"
																id="tripFristDriverName" name="tripFristDriverName"
																placeholder="Enter Driver1 Name"> <a
																class=" btn btn-link col-sm-offset-1"
																onclick="visibility('contactTwo', 'driverSelect');">
																<i class="fa fa-minus"> Select</i>
															</a> <label id="errorDriverName" class="error"></label>
														</div>
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label" for="issue_vehicle_id">Driver
														2 :</label>
													<div class="I-size" id="driver2Select">
														<div class="col-md-9">
															<input type="hidden" id="driverList2"
																name="tripSecDriverID" style="width: 100%;"
																placeholder="Please Enter 3 or more Driver Name, NO" />

														</div>
														<div class="col-md-1">
															<a class=" btn btn-link"
																onclick="visibility('driver2enter', 'driver2Select');">
																<i class="fa fa-plus"> New</i>
															</a>
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
												<div class="row1">
													<label class="L-size control-label" for="issue_vehicle_id">Cleaner
														:</label>
													<div class="I-size" id="cleanerSelect">

														<div class="col-md-9">
															<input type="hidden" id="Cleaner" name="tripCleanerID"
																style="width: 100%;"
																placeholder="Please Enter 3 or more Cleaner Name, No" />
														</div>
														<div class="col-md-1">
															<a class=" btn btn-link"
																onclick="visibility('cleanerEnter', 'cleanerSelect');">
																<i class="fa fa-plus"> New</i>
															</a>
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

											</div>
										</div>
									</fieldset>
									<fieldset>
										<legend>Route</legend>
										<div class="box">
											<div class="box-body">
												<div class="row1">

													<label class="L-size  control-label">Route Service
														:<abbr title="required">*</abbr>
													</label>
													<div class="I-size" id="routeSelect">

														<div class="col-md-9">
															<input type="hidden" id="TripRouteList" name="routeID"
																style="width: 100%;"
																placeholder="Please Enter 3 or more Route Name, NO " />

														</div>
														<div class="col-md-1">
															<a class=" btn btn-link"
																onclick="visibility('routeEnter', 'routeSelect');">
																<i class="fa fa-plus"> New</i>
															</a>
														</div>
														<label id="errorRoute" class="error"></label>
													</div>
													<div id="routeEnter" class="contact_Hide">

														<div class="I-size">
															<input type="text" class="form-text col-md-8"
																id="routeName" name="routeName"
																placeholder="Enter Route Name"> <a
																class=" btn btn-link col-sm-offset-1"
																onclick="visibility('routeEnter', 'routeSelect');">
																<i class="fa fa-minus"> Select</i>
															</a> <label id="errorRouteName" class="error"></label>
														</div>
													</div>
												</div>

												<div class="row1">
													<label class="L-size control-label">Date Of Journey
														: <abbr title="required">*</abbr>
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
															onkeypress="return tripBook(event);"
															ondrop="return false;"> <label class="error"
															id="errortripBook" style="display: none"> </label>

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
													<label class="L-size control-label">Advance Amount
														:</label>
													<div class="I-size">
														<input class="string required form-text"
															name="AdvanceAmount" maxlength="50" type="text"
															onkeypress="return IsAdvanceAmount(event);"
															ondrop="return false;"> <label class="error"
															id="errorAdvanceAmount" style="display: none"> </label>

													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Place :</label>
													<div class="I-size">
														<input class="string required form-text"
															name="advancePlace" maxlength="50" type="text"
															onkeypress="return IsAdvancePlace(event);"
															ondrop="return false;"> <label class="error"
															id="errorAdvancePlace" style="display: none"> </label>

													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Advance Paid By
														: </label>
													<div class="I-size">
														<input class="string required form-text"
															id="advancePaidby" name="advancePaidby" maxlength="50"
															type="text" onkeypress="return IsAdvancePaidby(event);"
															ondrop="return false;"> <label class="error"
															id="errorAdvancePaidby" style="display: none"> </label>

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

												<input type="hidden" value="" name="tripStutes"
													id="tripStutes">

											</div>
										</div>
									</fieldset>

									<fieldset class="form-actions">
										<div class="row1">

											<div class="pull-right">

												<input class="btn btn-default" name="commit" type="submit"
													value="Save TripSheet" onclick="return validateTrip();">
												<input class="btn btn-success" name="commit" type="submit"
													value="Dispatch vehicle"
													onclick="return validateDispatchTrip();"> <a
													class="btn btn-default" href="newTripSheetEntries.in">Cancel</a>
											</div>
										</div>
									</fieldset>
								</div>
							</form>
						</div>
					</div>
				</div>
			</sec:authorize>
			<div class="col-md-2 col-sm-2 col-xs-12">
				<%@include file="VehicleSideMenu.jsp"%>
			</div>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripValidateToVehicle.js" />"></script>
	<script>
		$(function() {
			$("#SelectVehicle").select2();
		});
		$(function() {
			$('#reservationToTripSheet').daterangepicker();
		});
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/script.js" />"></script>
</div>