<%@ include file="taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/vehicle/1/1"/>">Vehicle</a> / <a
						href="<c:url value="showVehicle.in?vid=${vehicle.vid}"/>"><c:out
							value="${vehicle.vehicle_registration}" /></a> / <a
						href="<c:url value="VehicleServiceDetails.in?vid=${vehicle.vid}"/>">
						Vehicle Service Reminder</a> / <span>Create Service Reminder</span>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_SERVICE_REMINDER')">
						<a class="btn btn-success"
							href="VehicleServiceAdd.in?vid=${vehicle.vid}"> <i
							class="fa fa-plus"></i> Add Service Reminder
						</a>
					</sec:authorize>
					<a class="btn btn-link"
						href="<c:url value="VehicleServiceDetails.in?vid=${vehicle.vid}"/>">Cancel</a>
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
			<sec:authorize access="!hasAuthority('ADD_SERVICE_REMINDER')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('ADD_SERVICE_REMINDER')">
				<div class="col-md-9 col-sm-9 col-xs-12">
					<script type="text/javascript"
						src="<c:url value="/resources/js/SeviceReminderValidate.js" />"></script>
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

							<form id="formServiceReminder"
								action="<c:url value="/saveVehicleServiceReminder.in"/>"
								method="post" enctype="multipart/form-data"
								name="formServiceReminder" role="form" class="form-horizontal">
								<div class="form-horizontal ">
									<fieldset>
										<legend>Service Reminder Details</legend>
										<div class="box">
											<div class="box-body">
												<div class="form-horizontal ">
													<div class="row1" id="grpvehicleNumber" class="form-group">
														<label class="L-size control-label"
															for="ServiceSelectVehicle">Vehicle Name <abbr
															title="required">*</abbr></label>
														<div class="I-size">
															<input type="hidden" class="form-control" id="OldVid"
																name="vid" value="${vehicle.vid}"> <input
																type="hidden" class="form-control" id="OldVehicleName"
																name="vehicle_registration"
																value="${vehicle.vehicle_registration}"> <input
																type="hidden" id="ServiceSelectVehicle"
																style="width: 100%;"
																placeholder="Please select Vehicle name"
																readonly="readonly" /> <span id="vehicleNumberIcon"
																class=""></span>
															<div id="vehicleNumberErrorMsg" class="text-danger"></div>
														</div>
													</div>
													<div class="row" id="grprenewalType" class="form-group">
														<label class="L-size control-label" for="from">Service
															Jobs <abbr title="required">*</abbr>
														</label>
														<div class="I-size">
															<input type="hidden" id="from" name="serviceTypeId"
																style="width: 100%;"
																placeholder="Please Enter 2 or more Job Name" /> <span
																id="renewalTypeIcon" class=""></span>
															<div id="renewalTypeErrorMsg" class="text-danger"></div>
															<p class="help-block">Select an existing Service Jobs</p>
														</div>
													</div>

													<div class="row" id="grprenewalSubType" class="form-group">
														<label class="L-size control-label" for='to'>Service
															Sub Jobs <abbr title="required">*</abbr>
														</label>

														<div class="I-size">
															<select style="width: 100%;" name="serviceSubTypeId"
																id='to'>

															</select> <span id="renewalSubTypeIcon" class=""></span>
															<div id="renewalSubTypeErrorMsg" class="text-danger"></div>
															<p class="help-block">Select an existing Service Sub
																Jobs</p>
														</div>
													</div>
													<div class="row" id="grpmeterInterval" class="form-group">
														<label class="L-size control-label" for="meter_interval">Meter
															Interval <abbr title="required">*</abbr>
														</label>

														<div class="I-size">
															<input type="number" class="form-text"
																name="meter_interval" min="0" maxlength="6" value="1"
																id="meter_interval"
																onkeypress="return Ismeter_interval(event);"
																ondrop="return false;"> <span
																id="meterIntervalIcon" class=""></span>
															<div id="meterIntervalErrorMsg" class="text-danger"></div>
															<label class="error" id="errormeter_interval"
																style="display: none"> </label>
															<p class="help-block">Repeat based on usage(e.g. Oil
																Change every 5000 miles). Leave blank if you don't want
																to use this option. Sub Types</p>
														</div>
													</div>
													<div class="row" id="grptimeInterval" class="form-group">

														<label class="L-size control-label" for="time_interval">Time
															Interval <abbr title="required">*</abbr>
														</label>

														<div class="I-size">
															<div class="col-md-4">
																<input type="number" class="form-text"
																	name="time_interval" min="0" maxlength="2" value="1"
																	id="time_interval"
																	onkeypress="return Istime_interval(event);"
																	ondrop="return false;"> <span
																	id="timeIntervalIcon" class=""></span>
																<div id="timeIntervalErrorMsg" class="text-danger"></div>
																<p class="help-block">(e.g. Car Wash every 1 month)</p>
																<label class="error" id="errortime_interval"
																	style="display: none"> </label> <label class="error"
																	id="errorTime"> </label>
															</div>
															<div class="col-md-4">
																<select class="form-text" id="time_intervalperiod"
																	name="time_intervalperiodId"
																	onchange="OnChangeDueThreshold(this)"
																	required="required">
																	<option value="1">day(s)</option>
																	<option value="2">Week(s)</option>
																	<option value="3">Month(s)</option>
																	<option value="4">Year(s)</option>
																</select>
															</div>

														</div>

													</div>
													
											<div class="row1" id="grprenewalSubType" class="form-group">
												<label class="L-size control-label" for='to'>Service
													Type <abbr title="required">*</abbr>
												</label>

												<div class="I-size">
													<select class="form-text" id="serviceType"
															name="serviceType"
															onchange="OnChangeDueThreshold(this)" required="required">
															<option value="1">OPTIONAL</option>
															<option value="2">MANDATORY</option>
													</select>
												</div>
											</div>
													
												</div>
											</div>
										</div>
									</fieldset>
									<fieldset>
										<legend>Due Soon Details</legend>
										<div class="box">
											<div class="box-body">
												<div class="form-horizontal ">

													<div class="row" id="grpmeterThreshold" class="form-group">
														<label class="L-size control-label"
															for="renewal_timethreshold">Due Meter Threshold <abbr
															title="required">*</abbr>
														</label>

														<div class="I-size">
															<input type="number" class="form-text"
																name="meter_threshold" maxlength="6" min="0" value="1"
																id="renewal_timethreshold"
																onkeypress="return Ismeter_threshold(event);"
																ondrop="return false;"> <span
																id="meterThresholdIcon" class=""></span>
															<div id="meterThresholdErrorMsg" class="text-danger"></div>
															<label class="error" id="errormeter_threshold"
																style="display: none"> </label>
															<p class="help-block">Number of miles/km/hours in
																advance you consider this reminder to be "due soon" (ex:
																500 miles is common for a typical fleet vehicle)</p>
														</div>
													</div>


													<div class="row" id="grptimeThreshold" class="form-group">

														<label class="L-size control-label" for="time_threshold">Due
															Time Threshold <abbr title="required">*</abbr>
														</label>

														<div class="I-size">
															<div class="col-md-4">
																<input type="number" class="form-text"
																	name="time_threshold" min="0" maxlength="2" value="1"
																	id="time_threshold"
																	onkeypress="return Istime_threshold(event);"
																	ondrop="return false;"> <span
																	id="timeThresholdIcon" class=""></span>
																<div id="timeThresholdErrorMsg" class="text-danger"></div>
																<p class="help-block">(ex: 2 weeks is common for a
																	typical fleet)</p>

																<label class="error" id="errortime_threshold"
																	style="display: none"> </label> <label class="error"
																	id="errorTime"> </label>
															</div>
															<div class="col-md-4">
																<select class="form-text" id="time_thresholdperiod"
																	name="time_thresholdperiodId"
																	onchange="OnChangeDueThreshold(this)"
																	required="required">
																	<option value="1">day(s)</option>
																	<option value="2">Week(s)</option>
																	<option value="3">Month(s)</option>
																	<option value="4">Year(s)</option>
																</select>
															</div>
														</div>
													</div>

													<div class="row" id="grpsubscribed" class="form-group">

														<label class="L-size control-label" for="subscribe">
															Subscribed Users : </label>
														<div class="I-size">

															<input class="" placeholder="Subscribe users"
																id="subscribe" type="hidden" style="width: 100%"
																name="service_subScribedUserId"
																onkeypress="return Isservice_subscribeduser(event);"
																required="required" ondrop="return false;"> <span
																id="subscribedIcon" class=""></span>
															<div id="subscribedErrorMsg" class="text-danger"></div>
															<label class="error" id="errorservice_subscribeduser"
																style="display: none"> </label> <label class="error"
																id="errorTime"> </label>
															<p class="help-block">The users you select will get
																an email when this reminder is due soon or overdue</p>

														</div>
													</div>
												</div>
											</div>
										</div>
									</fieldset>
									<fieldset class="form-actions">
										<div class="L-size"></div>
										<div class="I-size">
											<button type="submit" class="btn btn-success">Create
												Service Reminder</button>
											<a class="btn btn-link"
												href="VehicleServiceDetails.in?vid=${vehicle.vid}">Cancel</a>
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
	<script>
		$(function() {
			$("#to").select2();
		});
	</script>


	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/SR/SeviceReminderValidate.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			var h = $("#OldVid").val(), i = $("#OldVehicleName").val();
			$("#ServiceSelectVehicle").select2("data", {
				id : h,
				text : i
			});
		});
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>