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
						id="AllVehicl">Edit Work Orders</span>
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

					<form id="formEditWorkOrder"
						action="<c:url value="/updateWorkOrder.in"/>" method="post"
						enctype="multipart/form-data" name="formEditWorkOrder" role="form"
						class="form-horizontal">
						<div class="form-horizontal ">
							<fieldset>
								<legend>Work Order Info</legend>
								<div class="box">
									<div class="box-body">
									<input type="hidden" id="validateOdometerInWorkOrder" value="${validateOdometerInWorkOrder}">
										<input type="hidden" name="vehicle_ExpectedOdameter" id="vehicle_ExpectedOdameter" value="${vehicle.vehicleExpectedKm}">
										<input type="hidden" name="Odometer" id="Odometer" value="${vehicle.vehicle_Odometer}">
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
													<input type="hidden" name="workorders_id"
														required="required" value="${WorkOrder.workorders_id}" />
													<input type="hidden" name="workorders_Number"
														required="required" value="${WorkOrder.workorders_Number}" />
													<input type="hidden" name="workorders_document"
														required="required"
														value="${WorkOrder.workorders_document}" /> <input
														type="hidden" name="workorders_document_id"
														required="required"
														value="${WorkOrder.workorders_id}" />
													<!--  -->
													<input type="hidden" id="Ovid" required="required"
														value="${WorkOrder.vehicle_vid}" /> <input type="hidden"
														id="Ovname" required="required"
														value="${WorkOrder.vehicle_registration}" />
													<!--  -->
													<input type="hidden" id="select3" name="vehicle_vid"
														style="width: 100%;" required="required"
														placeholder="Please Enter 2 or more Vehicle Name" /> <span
														id="vehicleNameIcon" class=""></span>
													<div id="vehicleNameErrorMsg" class="text-danger"></div>
												</div>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="issue_vehicle_id">Driver
												Name </label>
											<div class="I-size">
												<div class="col-md-9">
													<input type="hidden" id="Odid" required="required"
														value="${WorkOrder.workorders_driver_id}" /> <input
														type="hidden" id="Odname" required="required"
														value="${WorkOrder.workorders_drivername}" />
													<!--  -->
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
													  value="${WorkOrder.workorders_driver_number}"	name="workorders_driver_number" type="text" maxlength="10">
												</div>
											</div>
										</c:if>
										<div class="row1" id="grpwoAssigned" class="form-group">
											<label class="L-size control-label" for="subscribe">Assigned
												To <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="hidden" id="Oassignee" required="required"
													value="${WorkOrder.assignee}" />
												<input type="hidden" id="OassigneeId" required="required"
													value="${WorkOrder.assigneeId}" />		
												<!--  -->
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
														value="${WorkOrder.start_date}"
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
															<input type="text" class="form-text" onchange="getVehicleGPSDataAtTime();"
																name="start_time" id="woStartTime" value="${WorkOrder.start_time}" required="required"> <span
																class="input-group-addon"> <i
																class="fa fa-clock-o" aria-hidden="true"></i>
															</span>
														</div>
												</div>
											</c:if>
										</div>
										<div class="row1" id="grpwoEndDate" class="form-group">
											<label class="L-size control-label" for="woEndDate">Due
												Date </label>
											<div class="col-md-3">
												<div class="input-group input-append date" id="LeaveDate1">
													<input type="text" class="form-text" name="due_date"
														value="${WorkOrder.due_date}" id="woEndDate"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
												<span id="woEndDateIcon" class=""></span>
												<div id="woEndDateErrorMsg" class="text-danger"></div>
											</div>
											<c:if test="${configuration.showStartAndDueTimeField}">
												<div class="L-size">
														<div class="input-group clockpicker">
															<input type="text" class="form-text"
																name="due_time" id="woEndTime" value="${WorkOrder.due_time}" required="required"> <span
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
													 value="${WorkOrder.out_work_station}" 	name="out_work_station" type="text" maxlength="50">
												</div>
											</div>
										</c:if>
										<c:if test="${WorkOrder.gpsWorkLocation != null}">
											<div class="row1" id="gpsWorkLocationRow">
												<label class="L-size control-label">
													GPS Location : 
												</label>
												<div class="I-size">
													<input class="string  form-text" id="gpsWorkLocation"
														name="gpsWorkLocation" type="text" value="${WorkOrder.gpsWorkLocation}" readonly="readonly">
												</div>
											</div>
										</c:if>
										
										<div class="row1">
											<label class="L-size control-label">Work location : <abbr
												title="required">*</abbr></label>
											<div class="I-size">
													<div class="col-md-8">
														<input type="text" class="form-text" required="required"
															readonly="readonly" name="workorders_location"
															value="${WorkOrder.workorders_location}"
															value="${PartLocations}">
														<input type="hidden" class="form-text" required="required"
															readonly="readonly" name="workorders_location_ID"
															value="${WorkOrder.workorders_location_ID}"/>
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
													  value="${WorkOrder.workorders_route}"	name="workorders_route" type="text" maxlength="50">
												</div>
											</div>
										</c:if>
										<div class="row1" id="grpwoOdometer" class="form-group">
											<label class="L-size control-label" for="vehicle_Odometer">Odometer</label>
											<div class="I-size">
												<input id="vehicle_Odometer_old" name="vehicle_Odometer_old"
													type="hidden"> <input class="form-text"
													value="${WorkOrder.vehicle_Odometer}"
													name="vehicle_Odometer" id="vehicle_Odometer" type="number"
													maxlength="10" 
													onblur="validateOdometer();"
													ondrop="return false;"> <span id="woOdometerIcon"
													class=""></span>
												<div id="woOdometerErrorMsg" class="text-danger"></div>
												<label class="error" id="errorOdometer"
													style="display: none"></label> <label class="error"
													id="errorOdo"></label>
											</div>
										</div>
										<c:if test="${WorkOrder.gpsOdometer != null && WorkOrder.gpsOdometer > 0}">
											<div class="row1" id="gpsOdometerRow">
												<label class="L-size control-label">
													GPS Odometer : 
												</label>
												<div class="I-size">
													<input class="string required form-text" id="gpsOdometer"
														name="gpsOdometer" type="number" maxlength="3" readonly="readonly" value="${WorkOrder.gpsOdometer}" >
												</div>
											</div>
										</c:if>
										
										<c:if test="${configuration.showDieselCol}">
											<div class="row1">
												<label class="L-size control-label">
													Diesel
												</label>
												<div class="I-size">
													<input class="string required form-text" id="workorders_diesel"
														name="workorders_diesel" type="number" maxlength="3" value="${WorkOrder.workorders_diesel}">
												</div>
											</div>
										</c:if>
									<c:if test="${configuration.showPONoFeild}">
										<div class="row1">
											<label class="L-size control-label">Manual Indent |
												PO. No </label>
											<div class="I-size">
												<input class="string required form-text" id="indentno"
													name="indentno" type="text" maxlength="25"
													value="${WorkOrder.indentno}">
											</div>
										</div>
									</c:if>	
										<div class="row1">
											<label class="L-size control-label" for=" 	">Priority
											</label>
											<div class="I-size">
												<div class="col-md-9">
													<select style="width: 100%;" name="priorityId" id="priority"
														required="required">
														<option value="${WorkOrder.priorityId}">${WorkOrder.priority}</option>
														<option value="1">NORMAL</option>
														<option value="2">HIGH</option>
														<option value="3">LOW</option>
														<option value="4">URGENT</option>
														<option value="5">VERY URGENT</option>
													</select>
												</div>
											</div>
										</div>
										<c:if test="${configuration.TallyCompanyMasterInWO}">
											<div class="row1" id="grpmanufacturer" class="form-group">
											<input type="hidden" id="editTyallycompanyId" required="required" value="${WorkOrder.tallyCompanyId}" />
											<input type="hidden" id="editTyallycompanyName" required="required" value="${WorkOrder.tallyCompanyName}" />
											<label class="L-size control-label" for="manufacurer">Tally Company Name :<abbr title="required">*</abbr></label>
												<div class="I-size">
													<input type="hidden" id="tallyCompanyId" name="tallyCompanyId" style="width: 100%;" value="0" 
													  placeholder="Please Enter Tally Company Name" />
												</div>
										</div>
										</c:if>
										<div class="row1">
											<label class="L-size control-label" for="issue_description">Initial
												Notes</label>
											<div class="I-size">
												<textarea class="text optional form-text" id="initial_note"
													name="initial_note" rows="3">${WorkOrder.initial_note}
				                                 </textarea>
											</div>
										</div>
									</div>
								</div>
							</fieldset>
							<fieldset class="form-actions">
								<div class="row">
									<div class="col-md-10 col-md-offset-2">
										<button type="submit" class="btn btn-success" onclick="return validateOdometer();">Update
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

			$("#tagPicker").select2({
				closeOnSelect : !1
			})
		});
	</script>

	<script type="text/javascript">
		$(document).ready(function() {

			var h = $("#Ovid").val(), i = $("#Ovname").val();
			$("#select3").select2("data", {
				id : h,
				text : i
			});
			var j = $("#Odid").val(), k = $("#Odname").val();
			$("#SelectDriverName").select2("data", {
				id : j,
				text : k
			});
			var l = $("#OassigneeId").val(), m = $("#Oassignee").val();
			$("#subscribe").select2("data", {
				id : l,
				text : m
			})
			
			if(${WorkOrder.gpsOdometer != null && WorkOrder.gpsOdometer > 0}){
				$('#grpwoOdometer').hide();
			}
			
			var editTyallycompanyId = $("#editTyallycompanyId").val(), editTyallycompanyName = $("#editTyallycompanyName").val();
			$("#tallyCompanyId").select2("data", {
				id : editTyallycompanyId,
				text : editTyallycompanyName
			});
			
		});
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>