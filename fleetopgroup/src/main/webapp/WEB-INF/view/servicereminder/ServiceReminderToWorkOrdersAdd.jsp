<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/bootstrap-clockpicker.min.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="ViewServiceReminderList.in"> Service
						Reminder</a> / <span id="NewVehi">Add Work Order</span>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_SERVICE_REMINDER')">
						<a class="btn btn-success" href="addServiceReminder.in"> <span
							class="fa fa-plus"></span> Add Multiple Service Reminders
						</a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>

	<c:if test="${param.success eq true}">
		<div class="alert alert-success">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This Vendor Created Successfully.
		</div>
	</c:if>
	<c:if test="${param.danger eq true}">
		<div class="alert alert-danger">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This Vendor Already Exists
		</div>
	</c:if>
	<section class="content">
		<sec:authorize access="!hasAuthority('WORKORDER_SERVICE_REMINDER')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('WORKORDER_SERVICE_REMINDER')">
			<div class="row">
				<div class="col-md-offset-1 col-md-9">

					<!-- <form action="saveWorkOrderToServiceReminder.in" method="post"> -->
					<div class="form-horizontal ">
						<fieldset>
							<legend>Work Order Info</legend>
							<div class="box">
								<div class="box-body">
									<form action="saveServiceWorkOrder.in" method="post">
										<input type="hidden" id="validateOdometerInWorkOrder" value="${validateOdometerInWorkOrder}">
										<input type="hidden" name="vehicle_ExpectedOdameter" id="vehicle_ExpectedOdameter" value="${vehicle.vehicleExpectedKm}">
										<input type="hidden" name="Odometer" id="Odometer" value="${vehicle.vehicle_Odometer}">
										<input type="hidden" id="allowGPSIntegration" value="${gpsConfiguration.allowGPSIntegration}">
										<input type="hidden" id="companyId" value="${companyId}">
										<input type="hidden"  id="vid" value="${Service.vid}">
										
										<div class="row1">
											<label class="L-size control-label" for="issue_vehicle_id">Vehicle
												<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="col-md-9">

													<input type="hidden" name="service_id" style="width: 100%;"
														value="${Service.service_id}" required="required" /> <input
														type="hidden" name="vehicle_vid" style="width: 100%;"
														value="${Service.vid}" required="required" /> <input
														type="text" class="form-text" name="vehicle_registration"
														readonly="readonly" style="width: 100%;"
														required="required"
														value="${Service.vehicle_registration}" />
												</div>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="issue_vehicle_id">Driver
												Name </label>
											<div class="I-size">
												<div class="col-md-9">
													<input type="hidden" id="SelectDriverName"
														name="workorders_driver_id" style="width: 100%;"
														required="required"
														placeholder="Please Enter 2 or more Driver Name" />

												</div>
											</div>
										</div>
										<c:if test="${configuration.showDriverNumberCol}">
											<div class="row1">
												<label class="L-size control-label">
													Driver Number
												</label>
												<div class="I-size">
													<input class="string required form-text" id="driverNumber"
														name="workorders_driver_number" type="number" maxlength="10">
												</div>
											</div>
										</c:if>
										<div class="row1">
											<label class="L-size control-label" for="assignee">Assigned
												To :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input class="" placeholder="assignee users" id="subscribe"
													type="hidden" style="width: 100%" name="assigneeId"
													onkeypress="return Isservice_subscribeduser(event);"
													required="required" ondrop="return false;">

											</div>
										</div>
										<input type="hidden" value="${configuration.showStartAndDueTimeField}" id="startTimeForGroup">
										<div class="row1">
											<label class="L-size control-label">Start Date <abbr
												title="required">*</abbr>
											</label>
											<div class="col-md-3">
												<div class="input-group input-append date" id="StartDate">
													<input type="text" class="form-text" name="start_date" onchange="getVehicleGPSDataAtTime();"
														id="woStartDate" required="required" data-inputmask="'alias': 'dd-mm-yyyy'"
														data-mask="" /> <span class="input-group-addon add-on">
														<span class="fa fa-calendar"></span>
													</span>
												</div>
											</div>
											<c:if test="${configuration.showStartAndDueTimeField}">
												<div class="L-size">
														<div class="input-group clockpicker">
															<input type="text" class="form-text" onchange="getVehicleGPSDataAtTime();"
																name="start_time" id="woStartTime" required="required"> <span
																class="input-group-addon"> <i
																class="fa fa-clock-o" aria-hidden="true"></i>
															</span>
														</div>
												</div>
											</c:if>
										</div>
										<div class="row1">
											<label class="L-size control-label">Due Date </label>
											<div class="col-md-3">
												<div class="input-group input-append date" id="LeaveDate">
													<input type="text" class="form-text" name="due_date"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
											</div>
											<c:if test="${configuration.showStartAndDueTimeField}">
												<div class="L-size">
														<div class="input-group clockpicker">
															<input type="text" class="form-text"
																name="due_time" id="woEndTime" required="required"> <span
																class="input-group-addon"> <span
																class="fa fa-clock-o" aria-hidden="true"></span>
															</span>
														</div>
												</div>
											</c:if>
										</div>
										<c:if test="${configuration.showOutWorkStationCol}">
											<div class="row1">
												<label class="L-size control-label">
													Out Work Station
												</label>
												<div class="I-size">
													<input class="string required form-text" id="outWorkStation"
														name="out_work_station" type="text" maxlength="50">
												</div>
											</div>
										</c:if>
										<div class="row1">
											<label class="L-size control-label">Work location : <abbr
												title="required">*</abbr></label>
											<div class="I-size" id="workLocationSelect">
												<div class="col-md-8">
													<select id="location" class="select2 required"
														style="width: 100%;" name="workorders_location_ID">
														<c:forEach items="${partLocationPermission}"
															var="partLocationPermission">
															<option value="${partLocationPermission.partLocationId}">
																<c:out value="${partLocationPermission.partLocationName}" />
															</option>
														</c:forEach>
													</select>
													
												<input type="hidden" name="workorders_location" id="workorders_location"/>
												</div>
												<c:if test="${configuration.AddNewWorkLocation}">
													<div class="col-md-1">
														<a class=" btn btn-link "
															onclick="visibility('workLocationNew', 'workLocationSelect');"> <i
															class="fa fa-plus"> New</i>
														</a>
													</div>
												</c:if>
											</div>
											<div id="workLocationNew" class="contact_Hide">
												<div class="I-size">
													<div class="col-md-8">
														<input type="text" class="form-text" id="new_workorders_location"
															name="new_workorders_location" maxlength="150"
															placeholder="Enter Work Location" />
													</div>
													<div class="col-md-1">
														<a class=" btn btn-link col-sm-offset-1"
															onclick="visibility('workLocationNew', 'workLocationSelect');"> <i
															class="fa fa-minus"> Select</i>
														</a>
													</div>

												</div>
											</div>
										</div>
										<c:if test="${configuration.showRouteCol}">
											<div class="row1">
												<label class="L-size control-label">
													Route
												</label>
												<div class="I-size">
													<input class="string required form-text" id="workorders_route"
														name="workorders_route" type="text" maxlength="50">
												</div>
											</div>
										</c:if>
									<%-- 	<div class="row1">
											<label class="L-size control-label">Odometer</label>
											<div class="I-size">
												<input name="vehicle_Odometer_old" type="hidden"
													value="${Service.vehicle_currentOdometer}"> <input
													class="form-text" name="vehicle_Odometer" type="number"
													placeholder="${Service.vehicle_currentOdometer}"
													maxlength="10" id="vehicle_Odometer"
													onblur="validateOdometer();"
													ondrop="return false;"> <label class="error"
													id="errorOdometer" style="display: none"></label> <label
													class="error" id="errorOdo"></label>

											</div>
										</div> --%>
										
										<div class="row1" id="grpwoOdometer" class="form-group">
											<label class="L-size control-label" for="vehicle_Odometer">Odometer</label>
											<div class="I-size">
												<input id="vehicle_Odometer_old" name="vehicle_Odometer_old"
													type="hidden"> <input class="form-text"
													name="vehicle_Odometer" id="vehicle_Odometer" type="number"
													maxlength="10"
													onkeypress="return IsNumericOdometer(event);"
													ondrop="return false;"> <span id="woOdometerIcon"
													class=""></span>
												<div id="woOdometerErrorMsg" class="text-danger"></div>
												<label class="error" id="errorOdometer"
													style="display: none"></label> <label class="error"
													id="errorOdo"></label>
											</div>
										</div>
											<div class="row1" id="gpsOdometerRow" style=" display: none;">
												<label class="L-size control-label">
													GPS Odometer : 
												</label>
												<div class="I-size">
													<input class="string required form-text" id="gpsOdometer"
														name="gpsOdometer" type="number" maxlength="3" readonly="readonly" >
												</div>
											</div>
										
										
										
										<c:if test="${configuration.showDieselCol}">
											<div class="row1">
											<label class="L-size control-label">Diesel</label>
												<div class="I-size">
													<input class="string required form-text" id="workorders_diesel"
														name="workorders_diesel" type="number" maxlength="3">
												</div>
											</div>
										</c:if>
									<c:if test="${configuration.showPONoFeild}">	
										<div class="row1">
											<label class="L-size control-label">Manual Indent |
												PO. No </label>
											<div class="I-size">
												<input class="string required form-text" id="indentno"
													name="indentno" type="text" maxlength="25">
											</div>
										</div>
									</c:if>	
										<div class="row1">
											<label class="L-size control-label" for="priority">Priority
											</label>
											<div class="I-size">
												<div class="col-md-9">
													<select style="width: 100%;" name="priorityId" id="priority"
														required="required">
														<option value="1">NORMAL</option>
														<option value="2">HIGH</option>
														<option value="3">LOW</option>
														<option value="4">URGENT</option>
														<option value="5">VERY URGENT</option>
													</select>
												</div>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="priority">Job
												Type </label>
											<div class="I-size">
												<div class="col-md-9">
													<input type="text" class="form-text" name="job_typetask"
														value="${Service.service_type}" style="width: 100%;"
														required="required" readonly="readonly" />
													<input type="hidden" class="form-text" name="job_typetaskId"
														value="${Service.serviceTypeId}" id="job_typetaskId" style="width: 100%;"
														required="required" readonly="readonly" />
												</div>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="priority">Job
												Sub Type </label>
											<div class="I-size">
												<div class="col-md-9">
													<input type="text" class="form-text" name="job_subtypetask"
														value="${Service.service_subtype}" style="width: 100%;"
														required="required" readonly="readonly" />
													<input type="hidden" class="form-text" name="job_subtypetask_id"
														value="${Service.serviceSubTypeId}" id="job_subtypetaskId" style="width: 100%;"
														required="required" readonly="readonly" />
												</div>
											</div>
										</div>
										<c:if test="${configuration.showJobTypeRemarkCol}">
											<div class="row1">
												<label class="L-size control-label" for="to">Job Type Remark<abbr title="required">*</abbr></label>
												<div class="I-size">
															<div class="col-md-9">
																<input type="text" class="form-text" id="JobTypeRemark"
																	name="JobTypeRemark" maxlength="150"
																	placeholder="Enter Remark" />
															</div>
														</div>
											</div>
										</c:if>
										<div class="row1">
											<label class="L-size control-label" for="issue_description">Initial
												Notes</label>
											<div class="I-size">
												<textarea class="text optional form-text" id="initial_note"
													name="initial_note" rows="3">
				                                 </textarea>
											</div>
										</div>

										<fieldset class="form-actions">
											<div class="row">
												<div class="col-md-10 col-md-offset-2">

													<input class="btn btn-success" type="submit"
														value="Save Work Order" onclick="return validateOdometer();">
												 <a class="btn btn-link"
														href="ViewServiceReminderList.in">Cancel</a>
												</div>
											</div>
										</fieldset>
										<c:if test="${configuration.autoCapitalAllTestFeilds}">
							<script type="text/javascript">
								$(document).ready(function() {
									$('input[type=text]').keyup(function(){
								        $(this).val($(this).val().toUpperCase());
								    });
									$('input[id=initial_note]').keyup(function(){
								        $(this).val($(this).val().toUpperCase());
								    });
									
									$("#woStartTime, #woStartDate, #woEndTime, #woEndDate").change(function(){
										validateFromToDateTime();
								    });
									
									$("#SelectDriverName").change(function(){
											var driverDetails = $("#select2-chosen-2").html().split(" - ");
											$("#driverNumber").val(driverDetails[2]);
											$("#select2-chosen-2").html(driverDetails[0]+" - "+ driverDetails[1]);
										}
									});
								});
								function validateFromToDateTime() {
									if($("#woStartDate").val() != "" 
										&& $("#woStartTime").val() != ""
										&& $("#woEndDate").val() != ""
										&& $("#woEndTime").val() != "") {
										if(($("#woEndDate").val()) < ($("#woStartDate").val())) {
											showMessage("info","Enter Proper Due Date");
											return;
										} else if ((($("#woEndDate").val()) == ($("#woStartDate").val())) 
												&& (($("#woEndTime").val()) < ($("#woStartTime").val()) 
														|| ($("#woEndTime").val()) == ($("#woStartTime").val()))) {
											showMessage("info","Enter Proper Due Date and Time");
										}										
									}
								}
							</script>
						</c:if>
									</form>
								</div>
							</div>
						</fieldset>
					</div>
				</div>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/bootstrap-clockpicker.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/WO/WorkOrdersValidate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/WO/VehicleGPSDetails.js" />"></script>		
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("dd-mm-yyyy", {
				placeholder : "dd-mm-yyyy"
			}), $("[data-mask]").inputmask()
			$('.clockpicker').clockpicker({
				placement: 'bottom',
				align: 'right',
				autoclose: true
			});
		});
	</script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#priority").select2();
			$("#jobSubTask").select2();
			$("#tagPicker").select2({
				closeOnSelect : !1
			})
		});
		$(document).ready(function() {
			var startTimeForGroup = $('#startTimeForGroup').val();

			if(startTimeForGroup == 'true' || startTimeForGroup == true){
					var today = new Date();
					var dd = today.getDate();
					var mm = today.getMonth()+1; //January is 0!
					var time = today.getHours()+':' ;
					var minute = today.getMinutes();
					if(today.getMinutes()<10){
						minute = '0'+minute;
					} 
					
					$('#woStartTime').val(today.getHours()+':'+minute);
					var yyyy = today.getFullYear();
					if(dd<10){
					    dd='0'+dd;
					} 
					if(mm<10){
					    mm='0'+mm;
					} 
					var today = dd+'-'+mm+'-'+yyyy;
					document.getElementById("woStartDate").value = today;
			}
			
			getVehicleGPSDataAtTime();
		});
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>