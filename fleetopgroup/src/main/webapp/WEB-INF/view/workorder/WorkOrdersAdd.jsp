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
						href="<c:url value="/viewWorkOrder.in"/>">Work Orders</a> / <span
						id="AllVehicl">Create Work Orders</span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/viewWorkOrder.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<c:if test="${param.emptyWO eq true}">
		<div class="alert alert-danger">
			<button class="close" data-dismiss="alert" type="button">x</button>
			Create WorkOrders Required Vehicle Name, Assigned To, Start date,
			WorkLocation and Tasks.. please enter.
		</div>
	</c:if>
	<section class="content">
		<sec:authorize access="!hasAuthority('ADD_WORKORDER')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('ADD_WORKORDER')">
			<div class="row">
				<div class="col-md-offset-1 col-md-9">
				
				<c:if test="${param.closeStatus eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						
						 ${VMandatory}<br>
						You should be close first TripSheet or Change Status or close workOrder .
					</div>
				</c:if>
				
				
					<form id="formWorkOrder"
						action="<c:url value="/saveWorkOrder.in"/>" method="post"
						enctype="multipart/form-data" name="formWorkOrder" role="form"
						class="form-horizontal">
						<div class="form-horizontal ">
							<fieldset>
								<legend>Work Order Info</legend>
								<div class="box">
									<div class="box-body">
										<input type="hidden" id="validateOdometerInWorkOrder" value="${validateOdometerInWorkOrder}">
										<input type="hidden" id="validateMinOdometerInWorkOrder" value="${validateMinOdometerInWorkOrder}">
										<input type="hidden" name="vehicle_ExpectedOdameter" id="vehicle_ExpectedOdameter">
										<input type="hidden" name="Odometer" id="Odometer">
										<input type="hidden" id="allowGPSIntegration" value="${gpsConfiguration.allowGPSIntegration}">
										<input type="hidden" id="companyId" value="${companyId}">
										<input type="hidden" id="tallyConfig" value="${tallyConfig}">
										<input type="hidden" id="backDateString" value="${minBackDate}">
										
										<div class="row1" id="grpvehicleName" class="form-group">
											<label class="L-size control-label" for="select3">Vehicle
												<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="col-md-9">
													<input type="hidden" id="select3" name="vehicle_vid"
														style="width: 100%;" required="required"
														placeholder="Please Enter 2 or more Vehicle Name" /> <span
														id="vehicleNameIcon" class=""></span>
													<div id="vehicleNameErrorMsg" class="text-danger"></div>
													
												</div>
												<input type="hidden" value="${configuration.showStartAndDueTimeField}" id="startTimeForGroup">
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
										
										
										<c:if test="${configuration.showServRemindWhileCreatingWO}">
										<div class="row1" id="grpvehicleNumber" class="form-group">
												<label class="L-size control-label"
													for="ServiceReminder">Service Reminder</label>
												<div class="I-size">
													 <input type="hidden" id="ServiceReminder"
														name="service_id" style="width: 100%;"/>
													 <span
														id="vehicleNumberIcon" class=""></span>
													<div id="vehicleNumberErrorMsg" class="text-danger"></div>
													<p class="help-block">Select One Or More Service Reminder To
														Create WorkOrder</p>
												</div>
										</div>
										</c:if>
										
										<c:if test="${!configuration.showServRemindWhileCreatingWO}">
										<input type="hidden" name="service_id" style="width: 100%;"/>
										</c:if>
										
										<c:if test="${configuration.showDriverNumberCol}">
											<div class="row1">
												<label class="L-size control-label">
													Driver Number
												</label>
												<div class="I-size">
													<input class="string required form-text" id="driverNumber"
														name="workorders_driver_number" type="text" maxlength="10">
												</div>
											</div>
										</c:if>
										<div class="row1" id="grpwoAssigned" class="form-group">
											<label class="L-size control-label" for="subscribe">Assigned
												To <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input class="" placeholder="assignee users" id="subscribe"
													type="hidden" style="width: 100%" name="assigneeId"
													onkeypress="return Isservice_subscribeduser(event);"
													required="required" ondrop="return false;"> <span
													id="woAssignedIcon" class=""></span>
												<div id="woAssignedErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<div class="row1" id="grpwoStartDate" class="form-group">
											<label class="L-size control-label" for="woStartDate">Start
												Date <abbr title="required">*</abbr>
											</label>
											<div class="col-md-3">
												<div class="input-group input-append date" id="StartDate1">
													<input type="text" class="form-text" name="start_date"
														id="woStartDate" required="required" onchange="getVehicleGPSDataAtTime();"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
												<span id="woStartDateIcon" class=""></span>
												<div id="woStartDateErrorMsg" class="text-danger"></div>
											</div>
											<c:if test="${configuration.showStartAndDueTimeField}">
												<div class="L-size">
														<div class="input-group clockpicker">
															<input type="text" class="form-text"
																name="start_time" id="woStartTime" required="required"> <span
																class="input-group-addon" onchange="getVehicleGPSDataAtTime();"> <i
																class="fa fa-clock-o" aria-hidden="true"></i>
															</span>
														</div>
												</div>
											</c:if>
										</div>
										<div class="row1" id="grpwoEndDate" class="form-group">
											<label class="L-size control-label" for="woEndDate">Due  <!--latest-->
												Date <abbr title="required">*</abbr> </label>
											<div class="col-md-3">
												<div class="input-group input-append date" id="LeaveDate1">
													<input type="text" class="form-text" name="due_date"
														id="woEndDate" data-inputmask="'alias': 'dd-mm-yyyy'"
														data-mask="" /> <span class="input-group-addon add-on">
														<span class="fa fa-calendar"></span>
													</span>
												</div>
												<span id="woEndDateIcon" class=""></span>
												<div id="woEndDateErrorMsg" class="text-danger"></div>
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
										<div class="row1" id="gpsWorkLocationRow" style=" display: none;">
												<label class="L-size control-label">
													GPS Location : 
												</label>
												<div class="I-size">
													<input class="string  form-text" id="gpsWorkLocation"
														name="gpsWorkLocation" type="text" readonly="readonly">
												</div>
										</div>
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
										<div class="row1" id="grpwoOdometer" class="form-group">  <!--latest-->
											<label class="L-size control-label" for="vehicle_Odometer">Odometer<abbr title="required">*</abbr></label>
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
												<label class="L-size control-label">
													Diesel
												</label>
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
											<label class="L-size control-label" for="issue_description">Initial
												Notes</label>
											<div class="I-size">
											<script language="javascript" src="jquery.maxlength.js"></script>
				                                 <textarea class="text optional form-text"
																id="initial_note" name="initial_note"
																rows="3" maxlength="250"></textarea>
											</div>
										</div>
										<c:if test="${configuration.TallyCompanyMasterInWO}">
											<div class="row1" id="grpmanufacturer" class="form-group">
											<label class="L-size control-label" for="manufacurer">Tally Company Name :<abbr title="required">*</abbr></label>
												<div class="I-size">
													<input type="hidden" id="tallyCompanyId" name="tallyCompanyId" style="width: 100%;" 
													  placeholder="Please Enter Tally Company Name" />
												</div>
										</div>
										</c:if>
										
									</div>
								</div>
							</fieldset>
							<fieldset>
								<legend>Tasks</legend>
								<div class="box">
									<div class="box-body">
									
									<c:if test="${configuration.showServRemindWhileCreatingWO}">
									<div class="row1 hide" id="grpJobType" >
												<label class="L-size control-label"
													for="JobType1">Job Type</label>
												<div class="I-size">
													<input type="text" class="form-text" id="JobType1" readonly="readonly"
													name="JobType1" style="width: 100%;" /> 
												</div>
										</div>
										<div class="row1 hide" id="grpJobSubType" >
												<label class="L-size control-label"
													for="JobSubType1">Job SubType</label>
												<div class="I-size">
													<input type="text" class="form-text" id="JobSubType1" readonly="readonly"
													name="JobSubType1" style="width: 100%;" /> 
												</div>
										</div>
									
									</c:if>
									<input type="hidden" id="job_serviceId" name="job_serviceId_Name"  />
										<div class="row1" id="grpwoJobType" class="form-group">
											<label class="L-size control-label" for="from">Job
												Type </label>
											<div class="I-size">
												<div class="col-md-8">
													<input type="hidden" id="from" name="job_typetaskId"
														style="width: 100%;" required="required"
														placeholder="Please Enter 3 or more Job Type Name" /> <span
														id="woJobTypeIcon" class=""></span>
													<div id="woJobTypeErrorMsg" class="text-danger"></div>
												</div>
											</div>
										</div>
										<div class="row1" id="grpwoJobSubType" class="form-group">
											<label class="L-size control-label" for="to">Job Sub
												Type <abbr title="required">*</abbr>
											</label>
											<div class="I-size" id="JoBSelect">
												<div class="col-md-8">
													<input type="hidden" id="to" name="job_subtypetask"
														style="width: 100%;" value="0" required="required"
														placeholder="Please Enter 3 or more Job Sub Type Name" />
													<span id="woJobSubTypeIcon" class=""></span>
													<div id="woJobSubTypeErrorMsg" class="text-danger"></div>

												</div>
												<div class="col-md-1">
													<a class=" btn btn-link "
														onclick="visibility('JoBEnter', 'JoBSelect');"> <i
														class="fa fa-plus"> New32</i>
													</a>
												</div>
											</div>
											
											<div id="JoBEnter" class="contact_Hide">
												<div class="I-size">
													<div class="col-md-8">
														<input type="text" class="form-text" id="SubReType"  onpaste="return false" 
															name="Job_ROT" maxlength="150" value=""
															placeholder="Enter ROT Name" onkeypress="return /[a-z]/i.test(event.key)";/> <br> 
															<input type="text" class="form-text" id="SubReType"  onpaste="return false" 
															name="Job_ROT_number" maxlength="30"
															placeholder="Enter ROT Number" /> <br>
													</div>
													<div class="col-md-1">
														<a class=" btn btn-link col-sm-offset-1"
															onclick="visibility('JoBEnter', 'JoBSelect');"> <i
															class="fa fa-minus"> Select</i>
														</a>
													</div>
												</div>
											</div>
												<input type="hidden" id="showJobTypeRemarkCol" value="${configuration.showJobTypeRemarkCol}" />
												<div class="row1 form-group jobTypeRemarkCol">
													<label class="L-size control-label" for="to">Job Type Remark<abbr title="required">*</abbr></label>
													<div class="I-size">
														<div class="col-md-8">
															<textarea class="form-text" id="JobTypeRemark"
																	name="JobTypeRemark" rows="5" maxlength="2240">	
																</textarea>
														</div>
													</div>
												</div>
											<input id="count" value="1" type="hidden">
											<div class="input_fields_wrap">
												<button class="add_field_button btn btn-info"
													data-toggle="tip"
													data-original-title="Please Enter single Job & Sub Job Task">
													<i class="fa fa-plus"></i> Add Tasks
												</button>
											</div>
											<p class="help-block">Please Enter Single Job &amp; Sub
												Job Task</p>
										</div>
									</div>
								</div>
							</fieldset>

							
							<fieldset class="form-actions">
								<div class="row">
									<div class="col-md-10 col-md-offset-2">
										<button type="submit"  onclick="return validateOdometer();" class="btn btn-success">Create
											Work Order</button>
										<a class="btn btn-link"
											href="<c:url value="/viewWorkOrder.in"/>">Cancel</a>
									</div>
								</div>
							</fieldset>
						</div>
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
										if($("#SelectDriverName").val() > 0) {
											var driverDetails = $("#select2-chosen-3").html().split(" - ");
											$("#driverNumber").val(driverDetails[2]);
											$("#select2-chosen-3").html(driverDetails[0]+" - "+ driverDetails[1]);
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
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/error/error.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>+
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/bootstrap-clockpicker.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/WO/WorkOrdersValidate.js" />"></script>
		<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
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
			$("#location").select2();
			$("#tagPicker").select2({
				closeOnSelect : !1
			})
			$('#workorders_location').val($("#location option:selected").text().trim());
			$('#location').on('change', function() {
				$('#workorders_location').val($("#location option:selected").text().trim());
			});
			if($("#showJobTypeRemarkCol").val() == false || $("#showJobTypeRemarkCol").val() == 'false') {
	        	$(".jobTypeRemarkCol").addClass("hide");        	
	        }
		});
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
		
	
</div>