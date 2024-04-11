<%@ include file="taglib.jsp"%>				<!--DDDDDDDDDDDDDDDDDDDDDDDD  -->
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<div class="content-wrapper">
	
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="issuesOpen.in"/>">Issues</a> / <a
						href="<c:url value="/showIssues?Id=${IssuesAESID}"/>">I-${Issues.ISSUES_NUMBER}</a>
					/ Create Service Entries
				</div>
				<div class="pull-right">
					<a href="<c:url value="/showIssues?Id=${IssuesAESID}"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<c:if test="${param.closeStatus eq true}">
		<div class="alert alert-danger">
			<button class="close" data-dismiss="alert" type="button">x</button>

			${VMandatory}<br> You should be close first TripSheet or change
			status or close workOrder .
		</div>
	</c:if>
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
	<c:if test="${param.already eq true}">
		<div class="alert alert-danger">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This Service Entries Vendor name &amp; Invoice number Already
			Exists.. please enter right invoice number.
		</div>
	</c:if>
	<section class="content">
		<sec:authorize access="!hasAuthority('ADD_SERVICE_ENTRIES')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('ADD_SERVICE_ENTRIES')">
			<div class="row">
				<div class="col-md-offset-1 col-md-9">

					<%-- <form id="formServiceEntries"
						action="<c:url value="/saveServiceEntries.in"/>" method="post"
						enctype="multipart/form-data" name="formServiceReminder"
						role="form" class="form-horizontal"> --%>
						<div class="form-horizontal ">
						
						<input type="hidden" id="allowGPSIntegration" value="${gpsConfiguration.allowGPSIntegration}">
						<input type="hidden" id="companyId" value="${companyId}">
						
							<fieldset>
								<legend>Service Entries Info</legend>
								<div class="box">
									<div class="box-body">
									
										<div class="row1">
											<div class="I-size">
												<input type="hidden" name="ISSUES_ID" style="width: 100%;" id="ISSUES_ID"
													required="required" value="${Issues.ISSUES_ID}" /> <input
													type="hidden" name="ISSUES_STATUS" style="width: 100%;"
													required="required" value="${Issues.ISSUES_STATUS}" />
												<input type="hidden" name="ISSUES_STATUS_ID" style="width: 100%;" id="ISSUES_STATUS_ID"
													required="required" value="${Issues.ISSUES_STATUS_ID}" />	
											</div>
										</div>
										
									<div class="row1">
										<label class="L-size control-label">Vehicle: <abbr
											title="required">*</abbr>
										</label>
										<!-- <div class="I-size">
											<input type="hidden" id="vehicle_vid" name="vid"
												style="width: 100%;" required="required"
												placeholder="Please Select Vehicle" />
										</div> -->
										<%-- <div class="I-size">
											<input type="text" class="form-text" id="vehicle_vid"  readonly="readonly" value ="${vehicleRegistration}"
												style="width: 100%;"  />
										</div> --%>
										
										<div class="I-size">
												<input type="hidden" id="vehicle_vid" name="vehicle_vid" style="width: 100%;"
													required="required" value="${ISSUES_VID}" /> <input
													type="text" class="form-text"
													name="ISSUES_VEHICLE_REGISTRATION" readonly="readonly"
													style="width: 100%;" required="required"
													value="${vehicleRegistration}" onload="setElements(this)"/>
										</div>
									</div>
									<div class="row1">
											<label class="L-size control-label">Driver Name :</label>
											<div class="I-size" id="driverSelect">

												<input type="hidden" id="SelectDriverName" name="driver_id"
													style="width: 100%;" value="0" required="required"
													placeholder="Please Enter 2 or more Driver Name"
													value="${Issues.ISSUES_DRIVER_ID}" />
											</div>
										</div>
										<c:if test="${configuration.showServRemindWhileCreatingWO}">
										<div class="row1" id="grpvehicleNumber" class="form-group">
												<label class="L-size control-label"
													for="ServiceReminder">Service Reminder</label>
												<div class="I-size">
													<input type="hidden" id="ServiceReminder"
														name="service_id" style="width: 100%;"/> <span
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
										
										<div class="row1" id="grpodoMeter" class="form-group">
											<label class="L-size control-label" for="vehicle_Odometer">Vehicle
												Odometer: <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input class="form-text" id="vehicle_Odometer" onpaste="return false" value="${Issues.ISSUES_ODOMETER}"
													placeholder="" name="vehicle_Odometer" type="text" maxlength="10"  onkeypress="return isNumberKey(event)">
												<span id="odoMeterIcon" class=""></span>
												<div id="odoMeterErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<div class="row1" id="grpgpsOdometer" class="form-group" style="display: none;">
											<label class="L-size control-label" for="gpsOdometer">Vehicle GPS
												Odometer : <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input class="form-text" id="gpsOdometer" readonly="readonly"
													placeholder="" name="gpsOdometer" type="text"
													value="${Issues.GPS_ODOMETER}"
													/>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Service Vendor :<abbr
												title="required">*</abbr></label>
											<div class="I-size" id="vendorSelect">
												<div class="col-md-9">
													<input type="hidden" id="selectVendor" name="Vendor_id" onchange=" validateVehicle();"
														style="width: 100%;" required="required" value="0"
														placeholder="Please Select Vendor Name" /> <label
														class="error" id="errorVendorSelect"> </label>
												</div>
												<div class="col-md-1">
													<a class=" btn btn-link "
														onclick="visibility('vendorEnter', 'vendorSelect');">
														<i class="fa fa-plus"> New</i>
													</a>
												</div>
											</div>
											<div id="vendorEnter" class="contact_Hide">
												<div class="I-size">
													<div class="col-md-9">
														<input class="form-text row1" name="Vendor_name"
															maxlength="150" type="text" id="enterVendorName"
															placeholder="Enter Fuel Vendor Name"
															onkeypress="return IsVendorName(event);"
															ondrop="return false;"> <label class="error"
															id="errorVendorName" style="display: none"> </label> <input
															class="form-text row1" name="Vendor_location"
															maxlength="150" type="text" id="enterVendorLocation"
															placeholder="Enter Fuel Vendor Location"
															onkeypress="return IsVendorLocation(event);"
															ondrop="return false;"> <label class="error"
															id="errorVendorLocation" style="display: none"> </label>
													</div>
													<div class="col-md-1">
														<a class=" btn btn-link col-sm-offset-1"
															onclick="visibility('vendorEnter', 'vendorSelect');">
															<i class="fa fa-minus"> Select</i>
														</a>
													</div>
												</div>
											</div>
										</div>
										
										<c:if test="${configuration.validateInvNumForServEnt}">
										<div class="row1" id="grpinvoiceNO" class="form-group">
											<label class="L-size control-label" for="invoiceNumber">Invoice
												Number <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input class="form-text" id="invoiceNumber"
													placeholder="eg: SDS34343" required="required"
													name="invoiceNumber" type="text" maxlength="25"> <span
													id="invoiceNOIcon" class=""></span>
												<div id="invoiceNOErrorMsg" class="text-danger"></div>
											</div>
										</div>
										</c:if>
										
										<c:if test="${!configuration.validateInvNumForServEnt}">
										<div class="row1" id="grpinvoiceNO" class="form-group">
											<label class="L-size control-label" for="invoiceNumber">Invoice
												Number 
											</label>
											<div class="I-size">
												<input class="form-text" id="invoiceNumber"
													placeholder="eg: SDS34343"
													name="invoiceNumber" type="text" maxlength="25"> <span
													id="invoiceNOIcon" class=""></span>
												<div id="invoiceNOErrorMsg" class="text-danger"></div>
											</div>
										</div>
										</c:if>
										
										<c:if test="${configuration.validateInvDateForServEnt}">
										<div class="row1" id="grpstartDate" class="form-group">
											<label class="L-size control-label" for="invoicestartDate">Invoice
												Date <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="input-group input-append date" id="StartDate">
													<input type="text" class="form-text" name="invoiceDate"
														id="invoicestartDate" required="required"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
												<span id="startDateIcon" class=""></span>
												<div id="startDateErrorMsg" class="text-danger"></div>
											</div>
										</div>
										</c:if>
										
										<c:if test="${!configuration.validateInvDateForServEnt}">
										<div class="row1" id="grpstartDate" class="form-group">
											<label class="L-size control-label" for="invoicestartDate">Invoice
												Date 
											</label>
											<div class="I-size">
												<div class="input-group input-append date" id="StartDate">
													<input type="text" class="form-text" name="invoiceDate"
														id="invoicestartDate" 
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
												<span id="startDateIcon" class=""></span>
												<div id="startDateErrorMsg" class="text-danger"></div>
											</div>
										</div>
										</c:if>
										
										<div class="row1">
											<label class="L-size control-label">Job Number </label>
											<div class="I-size">
												<input class="string required form-text"
													id="workorders_location" name="jobNumber" type="text">
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Modes of Payment
												<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<select class="form-text" name="service_paymentTypeId"
													id="renPT_option" required="required">
													<option value="1">Cash</option>
												</select>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label" id="target1"
												for="renPT_option">Enter </label>
											<div class="I-size">
												<input type="text" class="form-text"
													name="service_PayNumber"
													onkeypress="return IsAlphaNumericPaynumber(event);"
													ondrop="return false;" maxlength="25"> <label
													class="error" id="errorPaynumber" style="display: none">
												</label>
											</div>
										</div>
										
										<c:if test="${configuration.validatePaidDateForServEnt}">
										<div class="row1" id="grppaidDate" class="form-group">
											<label class="L-size control-label" for="servicepaiddate">Paid
												Date :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="input-group input-append date" id="paidDate">
													<input type="text" class="form-text"
														name="service_paiddate" id="servicepaiddate" required="required"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"><span
														class="fa fa-calendar"></span></span>
												</div>
												<span id="paidDateIcon" class=""></span>
												<div id="paidDateErrorMsg" class="text-danger"></div>
											</div>
										</div>
										</c:if>
										
										<c:if test="${!configuration.validatePaidDateForServEnt}">
										<div class="row1" id="grppaidDate" class="form-group">
											<label class="L-size control-label" for="servicepaiddate">Paid
												Date :
											</label>
											<div class="I-size">
												<div class="input-group input-append date" id="paidDate">
													<input type="text" class="form-text"
														name="service_paiddate" id="servicepaiddate"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"><span
														class="fa fa-calendar"></span></span>
												</div>
												<span id="paidDateIcon" class=""></span>
												<div id="paidDateErrorMsg" class="text-danger"></div>
											</div>
										</div>
										</c:if>
										
										
										<div class="row1"> 
											<label class="L-size control-label">Cashier | Paid By
												: </label>
											<div class="I-size">
												<input type="text" class="form-text" name="service_paidby" 
													onkeypress="return IsAlphaNumericPaidby(event);"
													value="${userName}" ondrop="return false;" maxlength="50"
													readonly="readonly"> <label class="error"
													id="errorPaidby" style="display: none"> </label> <input
													type="hidden" name="service_paidbyId" id="service_paidbyId"
													value="${userId}" />
											</div>
										</div>
										<c:if test="${configuration.workshopInvoiceAmount}">
										<div class="row1">
											<label class="L-size control-label">WorkShop Invoice Amount
												: </label>
											<div class="I-size">
												<input type="number" class="form-text" name="workshopInvoiceAmount" onkeypress="return isNumberKeyEach(event,this);" id="workshopInvoiceAmountId"/>
											</div>
										</div>
										
										</c:if>
									</div>
									<br> <label class="error" id="errorINEACH"
														style="display: none"></label>
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
									
										<div class="row1" id="grpjobType" class="form-group">
											<label class="L-size control-label" for="from">Job*
												Type </label>
											<div class="I-size">
												<div class="col-md-8">
													<input type="hidden" id="from" name="service_typetask"
														style="width: 100%;"
														placeholder="Please Enter 3 or more Job Type Name" /> <span
														id="jobTypeIcon" class=""></span>
													<div id="jobTypeErrorMsg" class="text-danger"></div>
												</div>
											</div>
										</div>
										<div class="row1" id="grpjobSubType" class="form-group">
											<label class="L-size control-label" for="to">Service
												Sub Jobs <abbr title="required">*</abbr>
											</label>
											<div class="I-size" id="JoBSelect">
												<div class="col-md-8">
													<input type="hidden" id="to" name="service_subtypetask"
														style="width: 100%;" required="required"
														placeholder="Please Enter 3 or more Job Sub Type Name" />
													<span id="jobSubTypeIcon" class=""></span>
													<div id="jobSubTypeErrorMsg" class="text-danger"></div>
												</div>
												<div class="col-md-1">
													<a class=" btn btn-link "
														onclick="visibility('JoBEnter', 'JoBSelect');"> <i
														class="fa fa-plus"> New</i>
													</a>
												</div>
											</div>
											<div id="JoBEnter" class="contact_Hide">
												<div class="I-size">
													<div class="col-md-8">
														<input type="text" class="form-text" name="service_ROT" id="SubReType"
															maxlength="150" placeholder="Enter ROT Name55" /> <br>
														<input type="text" class="form-text"
															name="service_ROT_number" maxlength="30" id="SubReTypeNum"
															placeholder="Enter ROT Number66" /> <br>
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
													<div class="row1 form-group" style="display: none;" id="jobTypeRemarkCol">
														<label class="L-size control-label" for="to">Remark: </label>
														<div class="I-size">
															<div class="col-md-8">
																<input type="text" class="form-text" id="taskRemark"
																	name="taskRemark" maxlength="250"
																	placeholder="Enter Remark" />
															</div>
														</div>
													</div>
											
											<input id="count" value="1" type="hidden">
											<div class="input_fields_wrap">
												<button class="add_field_button btn btn-info"
													data-toggle="tip"
													data-original-title="Please Enter single Job & Sub Job">
													<i class="fa fa-plus"></i> Add Next
												</button>
											</div>
											<p class="help-block">Please Enter Single Job &amp; Sub
												Job</p>
										</div>
									</div>
								</div>
							</fieldset>
							<fieldset class="form-actions">
								<div class="row">
									<div class="col-md-10 col-md-offset-2">
										<button type="submit" class="btn btn-success" onclick="return validateServiceEntry();" >Create
											Service Entries</button>
										<a class="btn btn-link"
											href="<c:url value="/showIssues?Id=${IssuesAESID}"/>">Cancel</a>
									</div>
								</div>
							</fieldset>
						</div>
					<!-- </form> -->
				</div>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#priority").select2();
			$("#tagPicker").select2({
				closeOnSelect : !1
			})

		});
	</script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<%-- <script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/SE/ServiceEntriesValidate.js" />"></script> --%>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/SE/VehicleGPSDetails.js" />"></script>	
	<%-- <script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script> --%>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("dd-mm-yyyy", {
				placeholder : "dd-mm-yyyy"
			}), $("[data-mask]").inputmask();
			
			if($("#showJobTypeRemarkCol").val() == true || $("#showJobTypeRemarkCol").val() == 'true') {
	        	$("#jobTypeRemarkCol").show();        	
	        }

		});
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/SE/IssuesServiceEntriesAdd.js" />"></script>	
</div>