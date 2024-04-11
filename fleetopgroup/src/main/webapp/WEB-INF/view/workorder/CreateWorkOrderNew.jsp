<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/bootstrap-clockpicker.min.css" />">

<style>
.select2-container .select2-choice {
    height: 36px;
}
.row {
	width: 100%;
	margin: 10px auto;
	padding:1%;
}
.label_font{
	font-weight: bold;
	font-size: larger;
}


.col{
	margin-top: 20px;
}
.custom-select{
	height: 38px;
 }
.select2-container {
	width: 100%;
	padding: 0;
}
.select2-container-multi .select2-choices {
    min-height: 38px;
}

</style>

<div class="content-wrapper">
	
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> /
					<a href="<c:url value="/viewWorkOrder.in"/>">Work Orders</a> /
					<span id="AllVehicl">Create Work Orders</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-danger" href="<c:url value="/viewWorkOrder.in"/>">Cancel</a>
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
				<div class="col-sm-8 col-md-12">
				
				<c:if test="${param.closeStatus eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						 ${VMandatory}<br>
						You should be close first TripSheet or Change Status or close workOrder .
					</div>
				</c:if>
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
										<input type="hidden" id="serverDate" value="${serverDate}">
										<input type="hidden" value="${configuration.showStartAndDueTimeField}" id="startTimeForGroup">
										<input type="hidden" id="issueVid" value="${vid}">
										<input type="hidden" id="issueVehicleReg" value="${vehicleReg}">
										<input type="hidden" id="issueId" value="${issueId}">
										<input type="hidden" id="accessToken" value="${accessToken}">
										<input type="hidden" id="showSubLocation" value="${showSubLocation}">
										<input type="hidden" id="mainLocationIds" value="${mainLocationIds}">
										<input type="hidden" id="validateSubLocation">	
										<input type="hidden" id="accidentId">
										<input type="hidden" id="validateIssue">
										<input type="hidden" id="showPendingIssueWhileCreatingWO" value="${configuration.showPendingIssueWhileCreatingWO}">
										<input type="hidden" id="showVehicleHealthStatus" value="${configuration.showVehicleHealthStatus}">
										
										 <div class="col col-sm-12 col-md-4">
												  <label class="has-float-label">
												    <input class="form-control" type="text" value="" id="select3" style="text-transform:uppercase;" />
												    <span style="color: #2e74e6;font-size: 14px;"> Vehicle <abbr title="required">*</abbr></span>
												  </label>
										</div>
										<div class="col col-sm-12 col-md-4">
												  <label class="has-float-label">
												    <input class="form-control" type="text" value="" id="SelectDriverName" style="text-transform:uppercase;" />
												    <span style="color: #2e74e6;font-size: 14px;"> Driver Name </span>
												  </label>
										</div>
										
										<c:if test="${configuration.showPendingIssueWhileCreatingWO}">
											<div id="issueDiv" class="col col-sm-12 col-md-4">
												  <label class="has-float-label">
													<select id="issue"  class="browser-default custom-select">
														<option value="-1"></option>
													</select>
												    <span style="color: #2e74e6;font-size: 14px;"> Issue Details<abbr title="required">*</abbr> </span>
												  </label>
											  </div>
										</c:if>
										
										 <!-- <div class="col col-sm-12 col-md-4">
												  <label class="has-float-label">
													<input class="form-control" type="text" value=" " id="healthStatusId" style="text-transform:uppercase;"  placeholder="Part SubCategory"/>
												   <span id="healthStatusName" style="padding-left: 10px;"></span>
												    <span style="color: #2e74e6;font-size: 14px;"> Health Status </span>
												  </label>
											  </div> -->
										<!-- <div class="row1 form-group ">
											<span class=" L-size control-label" style="padding-top: 2px;" id="healthStatusId" class= "bold"></span>
											<span id="healthStatusName" style="padding-left: 10px;"></span>
										</div> -->
										<div class="col col-sm-12 col-md-4">
												  <label class="has-float-label">
												    <input class="form-control" type="text" value="" id="subscribe" style="text-transform:uppercase;" />
												    <span style="color: #2e74e6;font-size: 14px;"> Assigned To <abbr title="required">*</abbr></span>
												  </label>
										</div>
										<c:if test="${!configuration.showServRemindWhileCreatingWO}">
											<input type="hidden" name="service_id" style="width: 100%;"/>
										</c:if>
										<c:if test="${configuration.showDriverNumberCol}">
												<div class="col col-sm-12 col-md-4">
												  <label class="has-float-label">
													<input class="form-control" type="text" value=" " id="driverNumber" style="text-transform:uppercase;"  placeholder="Part SubCategory"/>
												    <span style="color: #2e74e6;font-size: 14px;"> Driver Number </span>
												  </label>
											  </div> 
										</c:if>
										<div class="col col-sm-12 col-md-4">
										  <label class="has-float-label">
										    <div class="input-group input-append date" id="StartDate1">
													<input type="text" class="form-control"
														onchange="return validateDate();"   readonly="readonly"
														id="woStartDate" required="required" onchange="getVehicleGPSDataAtTime();" data-inputmask="'alias': 'dd-mm-yyyy'" data-mask=""> 
														<button type="submit" class=" input-group-addon add-on btn btn-sm"><i class="fa fa-calendar"></i></button>
												</div>
										    <span style="color: #2e74e6;font-size: 18px;">Start Date </span>
										  </label>
									</div>
									<div class="col col-sm-12 col-md-4">
										  <label class="has-float-label">
										    <div class="input-group input-append date" id="LeaveDate1">
													<input type="text" class="form-control"
														onchange="return validateDate();"   readonly="readonly"
														id="woEndDate" required="required" data-inputmask="'alias': 'dd-mm-yyyy'" data-mask=""> 
														<button type="submit" class=" input-group-addon add-on btn btn-sm"><i class="fa fa-calendar"></i></button>
												</div>
										    <span style="color: #2e74e6;font-size: 18px;">Due Date </span>
										  </label>
									</div>
										
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
														style="width: 100%;" name="workorders_location_ID" onchange="showSubLocationDropDown();" > 
														<c:forEach items="${partLocationPermission}"
															var="partLocationPermission">
															<option value="${partLocationPermission.partLocationId}">
																<c:out value="${partLocationPermission.partLocationName}" />
															</option>
														</c:forEach>
													</select>
													
												<input type="hidden" name="workorders_location" id="workorders_location" onchange="showSubLocationDropDown();"/>
												</div>
												<c:if test="${configuration.AddNewWorkLocation}">
													<div class="col-md-1">
														<a class=" btn btn-link "
															onclick="visibility('workLocationNew', 'workLocationSelect');">
														 <em class="fa fa-plus"> New</em>
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
															onclick="visibility('workLocationNew', 'workLocationSelect');"> 
															<em class="fa fa-minus"> Select</em>
														</a>
													</div>

												</div>
											</div>
										</div>
										  <div class="row1" id="subLocation" class="form-group" style="display:none">
											<label class="L-size control-label" for="location">Sub location :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="hidden" name="subLocationId"
													id="subLocationId" style="width: 100%;"
													required="required"
													placeholder="Please Enter 2 or more location Name" /> <span
													id="partLocationIcon" class=""></span>
												<div id="partLocationErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<div class="row1" id="grpwoOdometer" class="form-group">  <!--latest-->
											<label class="L-size control-label" for="vehicle_Odometer">Odometer<abbr title="required">*</abbr></label>
											<div class="I-size">
												<input id="vehicle_Odometer_old" name="vehicle_Odometer_old" type="hidden">
												<input class="form-text" name="vehicle_Odometer" id="vehicle_Odometer" type="number"
													maxlength="10" 	onkeypress="return isNumberKey(event,this);"
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
										
										<div class="row1">
											<c:if test="${configuration.showOutWorkStationCol}">
												<label class="L-size control-label">
													Out Work Station
												</label>
												<div class="col-md-3">
													<input class="string required form-text" id="outWorkStation"
														name="out_work_station" type="text" maxlength="50">
												</div>
											</c:if>
											
											<c:if test="${configuration.showRouteCol}">
												<label class="L-size control-label">
													Route
												</label>
												<div class="col-md-2">
													<input class="string required form-text" id="workorders_route"
														name="workorders_route" type="text" maxlength="50">
												</div>
											</c:if>
										</div>
										
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
											<div class="col-md-3">
												<select style="width: 100%;" name="priorityId" id="priority"
													required="required">
													<option value="1">NORMAL</option>
													<option value="2">HIGH</option>
													<option value="3">LOW</option>
													<option value="4">URGENT</option>
													<option value="5">VERY URGENT</option>
												</select>
											</div>
												
											<c:if test="${configuration.showDieselCol}">
												<label class="L-size control-label">
													Fuel
												</label>
												<div class="L-size">
													<input class="string required form-text" id="workorders_diesel"
														name="workorders_diesel" type="number" maxlength="3">
												</div>
											</c:if>
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
													style="width: 100%;" value="0"  required="required"
													placeholder="Please Enter 3 or more Job Sub Type Name" />
												<span id="woJobSubTypeIcon" class=""></span>
												<div id="woJobSubTypeErrorMsg" class="text-danger"></div>

											</div>
											<div id="newTask" class="col-md-1">
												<a id="new" class=" btn btn-link "
													onclick="visibility('JoBEnter', 'JoBSelect');""> <em
													class="fa fa-plus"> New</em>
												</a>
											</div>
										</div>
											
										<div id="JoBEnter" class="contact_Hide">
											<div class="I-size">
												<div class="col-md-8">
													<input type="text" class="form-text" id="SubReTypeROTName"  onpaste="return false" 
														name="Job_ROT" maxlength="150" value="0"
														placeholder="Enter ROT Name" onkeypress="return /[a-z]/i.test(event.key)";/> <br> 
														<input type="text" class="form-text" id="SubReTypeRotNum"  onpaste="return false" 
														name="Job_ROT_number" maxlength="30" value="0"
														placeholder="Enter ROT Number" /> <br>
												</div>
												<div class="col-md-1">
													<a class=" btn btn-link col-sm-offset-1"
														onclick="visibility('JoBEnter', 'JoBSelect');setValue(SubReTypeROTName,SubReTypeRotNum)"> <em
														class="fa fa-minus"> Select</em>
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
												data-original-title="Please Enter Job & Sub Job Task">
												<i class="fa fa-plus"></i>Add Tasks
											</button>
										</div>
										<p class="help-block">Please Enter Job &amp; Sub
											Job Task</p>
									</div>
								</div>
							</div>
						</fieldset>
							
						<fieldset class="form-actions">
							<div class="row">
								<div class="col-md-10 col-md-offset-2">
									<button type="submit"  onclick="return createWorkOrder();" id="saveWO" class="btn btn-success">Create
										Work Order</button>
									<a class="btn btn-danger" href="<c:url value="/viewWorkOrder.in"/>">Cancel</a>
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
								
								$("#SelectDriverName").change(function(){
									if($("#SelectDriverName").val() > 0) {
										var driverDetails = $("#select2-chosen-3").html().split(" - ");
										$("#driverNumber").val(driverDetails[2]);
										$("#select2-chosen-3").html(driverDetails[0]+" - "+ driverDetails[1]);
									}
								});
							});
							
						</script>
					</c:if>
			</div>
		</div>
	</sec:authorize>
</section>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/error/error.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>+
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/WO/VehicleGPSDetails.js" />"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/WO/CreateWorkOrder.js" />"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/bootstrap-clockpicker.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/WO/workOrderUtility.js" />"></script>			
			
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
			setServiceReminderDetails('${Service}');
			setAccidentDetails('${detailsDto}');
		});
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
		
		<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>  
		
</div>