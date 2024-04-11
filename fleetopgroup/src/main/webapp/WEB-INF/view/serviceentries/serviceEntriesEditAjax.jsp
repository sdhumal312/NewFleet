<%@ include file="taglib.jsp"%>
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
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> /
					<a href="<c:url value="/viewServiceEntries.in"/>"> Service Entries</a> / 
					<small>Edit Service Entries</small>
				</div>
				<div class="pull-right">
					<a class="btn btn-danger" href="<c:url value="/viewServiceEntries.in"/>">Cancel</a>
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
		<div class="row">
			<sec:authorize access="!hasAuthority('EDIT_SERVICE_REMINDER')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('EDIT_SERVICE_REMINDER')">
				<div class="col-md-offset-1 col-md-9">
					
						<div class="form-horizontal">
							<fieldset>
								<legend>Service Entries Info</legend>
								<div class="box">
									<div class="box-body">
										<input type="hidden" name="serviceEntries_id" id="serviceEntryId" value="${ServiceEntries_id}">
										<input type="hidden" name="serviceEntries_Number" id="serviceNumber" value="${ServiceEntries.serviceEntries_Number}">
										<input type="hidden" id="Ovid" required="required" value="${ServiceEntries.vid}" />
										<input type="hidden" id="Ovname" required="required" value="${ServiceEntries.vehicle_registration}" />
										<input type="hidden" id="tallyConfig" value="${tallyConfig}">
										<input type="hidden" id="backDateString" value="${minBackDate}">
										<input type="hidden" id="showTripSheet" value="${config.showTripSheet}">
										<input type="hidden" id="issueId" value="${issueId}">
										<input type="hidden" id="serverDate" value="${serverDate}">
										<input type="hidden" id="accidentId" value="${ServiceEntries.accidentId}">
										<input type="hidden" id="oldVehicleId" value="${ServiceEntries.vid}">
										<input type="hidden" id="Did" required="required" value="${ServiceEntries.driver_id}" />
										<input type="hidden" id="Dname" required="required" value="${ServiceEntries.driver_name}" />
										<input type="hidden" id="editIssueId" value="${ServiceEntries.ISSUES_ID}">		
										<input type="hidden" id="editIssueNumber" value="${ServiceEntries.issueNumber}">		
										<input type="hidden" id="editIssueSummary" value="${ServiceEntries.issueSummary}">	
										<input type="hidden" id="showPendingIssueWhileCreatingSE" value="${config.showPendingIssueWhileCreatingSE}">
										<input type="hidden" id="validatePaidDateForServEnt" value="${configuration.validatePaidDateForServEnt}">	
										<input type="hidden" id="validatePaidDateForServEntOnCash" value="${configuration.validatePaidDateForServEntOnCash}">
										
										<div class="row1" id="grpvehicleNumber" class="form-group">
											<label class="L-size control-label" for="vehicle_vid">Vehicle
												<abbr title="required">*</abbr>
											</label>
											<div id="SEVehicle" class="I-size">
												<input type="hidden" id="vehicle_vid" name="vid" style="width: 100%;" required="required"
													placeholder="Please Enter 2 or more Vehicle Name" /> 
													<span id="vehicleNumberIcon" class=""></span>
												<div id="vehicleNumberErrorMsg" class="text-danger"></div>
											</div>
											<div id="issueVehicle" class="col-md-9 " style="display: none;">
												<input type="text" readonly="readonly" value="${ServiceEntries.vehicle_registration}" class="form-text">
											</div>
											
										</div>

										<div class="row1">
											<label class="L-size control-label">Driver Name :</label>
											<div class="I-size" id="driverSelect">
												<div class="col-md-9">
													<input type="hidden" id="SelectDriverName" name="driver_id" style="width: 100%;" required="required"
														placeholder="Please Enter 2 or more Driver Name" />
												</div>
												<div class="col-md-1">
													<a class=" btn btn-link "
														onclick="visibility('driverEnter', 'driverSelect');">
														<i class="fa fa-plus"> New</i>
													</a>
												</div>
											</div>
											<div id="driverEnter" class="contact_Hide">
												<div class="I-size">
													<div class="col-md-9">
														<input class="form-text row1" name="driver_name" maxlength="25" type="text"
															value="${ServiceEntries.driver_name}"
															placeholder="Enter Driver Name" readonly="readonly" onkeypress="return IsVendorName(event);"
															ondrop="return false;"> 
															<label class="error" id="errorVendorName" style="display: none"> </label>
													</div>
													<div class="col-md-1">
														<a class=" btn btn-link col-sm-offset-1"
															onclick="visibility('driverEnter', 'driverSelect');">
															<i class="fa fa-minus"> Select</i>
														</a>
													</div>
												</div>
											</div>
										</div>
										<c:if test="${config.showPendingIssueWhileCreatingSE}">
											<div class="row1" id="issueDiv">
												<label class="L-size control-label" for="issue">Issue Details</label>
												<div class="I-size">
													 <input type="hidden" class="select" id="issue" style="width: 100%;" readonly="readonly"/>
												</div>
											</div>
										</c:if>
										
										<div class="row1" id="grpodoMeter" class="form-group">
											<label class="L-size control-label" for="vehicle_Odometer">Vehicle
												Odometer: <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input class="form-text" id="vehicle_Odometer" placeholder="${ServiceEntries.vehicle_Odometer}"
													name="vehicle_Odometer" onkeypress="return isNumberKey(event)" value="${ServiceEntries.vehicle_Odometer}" type="text">
												<span id="odoMeterIcon" class=""></span>
												<div id="odoMeterErrorMsg" class="text-danger"></div>
											</div>
										</div>
										
										<c:if test="${ServiceEntries.gpsOdometer != null && ServiceEntries.gpsOdometer > 0}">
											<div class="row1" id="grpgpsOdometer" class="form-group">
												<label class="L-size control-label" for="gpsOdometer">Vehicle GPS
													Odometer : <abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<input class="form-text" id="gpsOdometer" readonly="readonly"
														placeholder="" name="gpsOdometer" type="text" value="${ServiceEntries.gpsOdometer}">
												</div>
											</div>
										</c:if>

										<div class="row1">
											<label class="L-size control-label">Service Vendor :<abbr
												title="required">*</abbr></label>
											<div class="I-size" id="vendorSelect">
												<div class="col-md-9">

													<input type="hidden" id="Venid" required="required" value="${ServiceEntries.vendor_id}" />
													<input type="hidden" id="Venname" required="required" value="${ServiceEntries.vendor_name} - ${ServiceEntries.vendor_location}" />
													<input type="hidden" id="selectVendor" name="Vendor_id" style="width: 100%;" required="required"
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
														<input class="form-text row1" name="Vendor_name" maxlength="25" type="text" id="enterVendorName"
														readonly="readonly" placeholder="Enter Vendor Name" value="${ServiceEntries.vendor_name}"
														onkeypress="return IsVendorName(event);" ondrop="return false;"> 
														<label class="error" id="errorVendorName" style="display: none"> </label> 
														<input class="form-text row1" name="Vendor_location" readonly="readonly" maxlength="25" type="text"
														id="enterVendorLocation" placeholder="Enter Fuel Vendor Location" 
														value="${ServiceEntries.vendor_location}"
														onkeypress="return IsVendorLocation(event);" ondrop="return false;"> 
														<label class="error" id="errorVendorLocation" style="display: none"> </label>
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
										
										<div class="row1" id="grpinvoiceNO" class="form-group">
											<label class="L-size control-label" for="invoiceNumber">Invoice Number :
												<c:if test="${configuration.validateInvNumForServEnt}">
													<abbr title="required">*</abbr>
												</c:if>	
											</label>
											<div class="I-size">
												<input class="form-text" id="invoiceNumber" value="${ServiceEntries.invoiceNumber}" required="required"
													name="invoiceNumber" type="text"> <span id="invoiceNOIcon" class=""></span>
												<div id="invoiceNOErrorMsg" class="text-danger"></div>
											</div>
										</div>
										
										
										<div class="row1" id="grpstartDate" class="form-group">
											<label class="L-size control-label" for="invoicestartDate">Invoice Date
												<c:if test="${configuration.validateInvDateForServEnt}">
												 	<abbr title="required">*</abbr>
												 </c:if>
											</label>
											<div class="I-size">
												<div class="input-group input-append date" id="StartDate">
													<input type="text" class="form-text" name="invoiceDate" readonly="readonly"
														id="invoicestartDate" onchange="getVehicleTripSheetDetails();"
														value="${ServiceEntries.invoiceDate}" required="required"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
												<span id="startDateIcon" class=""></span>
												<div id="startDateErrorMsg" class="text-danger"></div>
											</div>
										</div>
										
										<c:if test="${config.showTripSheet}">	
											<div class="row1">
												<label class="L-size control-label">TripSheet Number : </label>
												<div class="I-size">
													<select class="form-text" name="tripSheetId"
														id="tripSheetId" required="required">
														<c:if test="${ServiceEntries.tripSheetNumber == null }">
															<option value="0">Select TripSheet</option>
														</c:if>
														<c:if test="${ServiceEntries.tripSheetNumber != null }">
															<option value="${ServiceEntries.tripSheetId}">TS-${ServiceEntries.tripSheetNumber}</option>
														</c:if>
													</select>
												</div>
											</div>
										</c:if>

										<div class="row1">
											<label class="L-size control-label">Job Number </label>
											<div class="I-size">
												<input class="string required form-text" value="${ServiceEntries.jobNumber}"
													id="workorders_location" name="jobNumber" type="text">
											</div>
										</div>
										
										<div class="row1">
											<label class="L-size control-label">Modes of Payment
												<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<select class="form-text" name="service_paymentTypeId" id="serviceEdit_option">
													<option value="${ServiceEntries.service_paymentTypeId}" selected="selected">
													<c:out  value="${ServiceEntries.service_paymentType}" /></option>
													<option value="1">CASH</option>
													<option value="2">CREDIT</option>
													<option value="3">NEFT</option>
													<option value="4">RTGS</option>
													<option value="5">IMPS</option>
													<option value="5">IMPS</option>
													<option value="6">DD</option>
													<option value="7">CHEQUE</option>
													<option value="10">ON ACCOUNT</option>
												</select>
											</div>
										</div>

										<div class="row1">
											<label class="L-size control-label" id="target1"
												for="renPT_option">Enter </label>
											<div class="I-size">
												<input type="text" class="form-text" name="service_PayNumber" id="service_PayNumber" onkeypress="return IsAlphaNumericPaynumber(event);"
													value="${ServiceEntries.service_PayNumber}" ondrop="return false;" maxlength="25">
													<label class="error" id="errorPaynumber" style="display: none">
												</label>
											</div>
										</div>
										
									<c:if test="${config.showPaidDateForServEnt}">	
										<div class="row1" id="grppaidDate" class="form-group">
											<label class="L-size control-label" for="servicepaiddate">Paid Date :
												<c:if test="${configuration.validatePaidDateForServEnt}">
													<abbr title="required">*</abbr>
												</c:if>	
											</label>
											<div class="I-size">
												<div class="input-group input-append date" id="StartDate1">
													<input type="text" class="form-text" required="required" name="service_paiddate" id="servicepaiddate"
														value="${ServiceEntries.service_paiddate}" data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> 
														<span class="input-group-addon add-on"><span
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
												<input type="text" class="form-text" name="service_paidby" value="${ServiceEntries.service_paidby}"
													readonly="readonly" onkeypress="return IsAlphaNumericPaidby(event);"
													ondrop="return false;" maxlength="25"> <label
													class="error" id="errorPaidby" style="display: none">
												</label>
											<input type="hidden" name="service_paidbyId" value="${ServiceEntries.service_paidbyId}" />
											</div>
										</div>
										
										<c:if test="${config.TallyCompanyMasterInSE}">
											<div class="row1" id="grpmanufacturer" class="form-group">
											<input type="hidden" id="editTyallycompanyId" required="required" value="${ServiceEntries.tallyCompanyId}" />
											<input type="hidden" id="editTyallycompanyName" required="required" value="${ServiceEntries.tallyCompanyName}" />
											<input type="hidden" id="editTallyExpenseId" required="required" value="${ServiceEntries.tallyExpenseId}" />
											<input type="hidden" id="editTallyExpenseName" required="required" value="${ServiceEntries.tallyExpenseName}" />
											
											<label class="L-size control-label" for="manufacurer">Tally Company Name :<abbr title="required">*</abbr></label>
												<div class="I-size">
													<input type="hidden" id="tallyCompanyId" name="tallyCompanyId" style="width: 100%;" value="0"
													  placeholder="Please Enter Tally Company Name" />
												</div>
											</div>
											<div class="row1" id="grpmanufacturer" class="form-group">
											<label class="L-size control-label" for="manufacurer">Tally Expense Head :<abbr title="required">*</abbr></label>
												<div class="I-size">
													<input type="hidden" id="tallyExpenseId" name="tallyExpenseId" style="width: 100%;" value="0"
													  placeholder="Please Enter Tally Expense Name" />
												</div>
											</div>
										</c:if>
										
										<c:if test="${configuration.workshopInvoiceAmount}">
											<div class="row1">
												<label class="L-size control-label">WorkShop Invoice Amount
													: </label>
												<div class="I-size">
													<input type="number" class="form-text" name="workshopInvoiceAmount" onkeypress="return isNumberKeyEach(event,this);"
													value="${ServiceEntries.workshopInvoiceAmount}" id="workshopInvoiceAmountId">
												</div>
											</div>
										</c:if>

									</div>
									<br> <label class="error" id="errorINEACH" style="display: none"></label>
								</div>
							</fieldset>

							<fieldset class="form-actions">
								<div class="row">
									<div class="col-md-10 col-md-offset-2">
										<button type="submit" class="btn btn-success" onclick="return updateServiceEntry();"> Update
											Service Entries</button>
										<a class="btn btn-danger" href="<c:url value="/viewServiceEntries.in"/>">Cancel</a>
									</div>
								</div>
							</fieldset>
							
						</div>
				</div>
			</sec:authorize>
		</div>
	</section>
	
	<%-- <script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/SE/ServiceEntriesValidate.js" />"></script> --%>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/SE/serviceEntriesEditAjax.js" />"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript">
	
	var previousDate = $('#backDateString').val();
	var previousDateForBackDate =   previousDate.split("-")[0] + '-' +  previousDate.split("-")[1] + '-' +  previousDate.split("-")[2];
	var serverDate	= $('#serverDate').val();	
	var serverDateStr =   serverDate.split("-")[0] + '-' +  serverDate.split("-")[1] + '-' +  serverDate.split("-")[2];
	$(function() {
	$("#invoicestartDate").datepicker({
		defaultDate: serverDateStr,
	      autoclose: !0,
	      todayHighlight: !0,
	      format: "dd-mm-yyyy",
	      setDate: "0",
	      endDate: serverDateStr,
	  startDate:previousDateForBackDate
	  })
	});
		function showoption() {
			var a = $("#serviceEdit_option :selected"), b = a.text();
			"CASH" == b ? $("#target1").text(b + " Receipt NO : ") : $(
					"#target1").text(b + " Transaction NO : ")
		}
		$("#serviceEdit_option").on("change", function() {
			showoption()
		})
		$(document).ready(function() {
			showoption(), $("#datemask").inputmask("dd-mm-yyyy", {
				placeholder : "dd-mm-yyyy"
			}), $("[data-mask]").inputmask()
		});
	</script>
	<script>
		$(function() {
			$("#vid").select2();
		});
	</script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#priority").select2(), $("#tagPicker").select2({
				closeOnSelect : !1
			});
			var e = $("#Ovid").val(), f = $("#Ovname").val();
			$("#vehicle_vid").select2("data", {
				id : e,
				text : f
			});
			var a = $("#Did").val(), b = $("#Dname").val();
			$("#SelectDriverName").select2("data", {
				id : a,
				text : b
			});
			var c = $("#Venid").val(), d = $("#Venname").val();
			$("#selectVendor").select2("data", {
				id : c,
				text : d
			});
			if(${ServiceEntries.gpsOdometer != null && ServiceEntries.gpsOdometer > 0}){
				$('#grpodoMeter').hide();
			}
			var editTyallycompanyId = $("#editTyallycompanyId").val(), editTyallycompanyName = $("#editTyallycompanyName").val();
			$("#tallyCompanyId").select2("data", {
				id : editTyallycompanyId,
				text : editTyallycompanyName
			});
			
			$("#tallyExpenseId").select2("data", {
				id : $('#editTallyExpenseId').val(),
				text : $('#editTallyExpenseName').val()
			});
		});
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
		
		<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>  
</div>