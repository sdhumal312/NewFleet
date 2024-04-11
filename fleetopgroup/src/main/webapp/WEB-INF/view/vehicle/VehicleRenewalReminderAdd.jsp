<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
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
						href="<c:url value="VehicleRenewalReminder.in?vid=${vehicle.vid}"/>">
						Vehicle Renewal Reminder</a> / <span>Create Renewal Reminder</span>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_RENEWAL')">
						<a class="btn btn-success"
							href="VehicleRenewalReminderAdd.in?vid=${vehicle.vid}"> <i
							class="fa fa-plus"></i> Add Renewal Reminder
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
			<sec:authorize access="!hasAuthority('ADD_RENEWAL')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('ADD_RENEWAL')">
				<div class="col-md-9 col-sm-9 col-xs-12">


					<form id="formRenewal"
						action="<c:url value="/saveVehicleRenewalReminder.in"/>"
						method="post" enctype="multipart/form-data" name="formRenewal"
						role="form" class="form-horizontal">

						<fieldset>
							<legend>Renewal Types</legend>
							<div class="box">
								<div class="box-body">
								<input type="hidden" value="${saveRenewalWithoutFile }" id="saveRenewalWithoutFile" />
									<div class="row1" id="grpvehicleNumber" class="form-group">
										<label class="L-size control-label" for="RenewalSelectVehicle">Vehicle
											Name : <abbr title="required">*</abbr>
										</label>
										<div class="I-size">
											<input type="hidden" class="form-control" id="OldVid"
												name="vid" value="${vehicle.vid}"> <input
												type="hidden" class="form-control" id="OldVehicleName"
												name="vehicle_registration"
												value="${vehicle.vehicle_registration}"> <input
												type="hidden" id="RenewalSelectVehicle" style="width: 100%;"
												readonly="readonly"
												placeholder="Please Enter 2 or more Vehicle Name" /> <span
												id="vehicleNumberIcon" class=""></span>
											<div id="vehicleNumberErrorMsg" class="text-danger"></div>
										</div>
									</div>

									<div class="row1" id="grprenewalType" class="form-group">

										<label class="L-size control-label" for="from">Renewal
											Type <abbr title="required">*</abbr>
										</label>

										<div class="I-size">
											<input type="hidden" id="from" name="renewalTypeId"
												style="width: 100%;"
												placeholder="Please select renewal type" /> <span
												id="renewalTypeIcon" class=""></span>
											<div id="renewalTypeErrorMsg" class="text-danger"></div>
										</div>
										<input id="renewal_typeVal" name="renewal_type" type="hidden" />
									</div>
									<br>
									<div class="row1" id="grprenewalSubType" class="form-group">
										<label class="L-size control-label" for='to'>Renewal
											Sub Type <abbr title="required">*</abbr>
										</label>

										<div class="I-size">
											<select style="width: 100%;" name="renewal_Subid" id='to'>
											</select> <span id="renewalSubTypeIcon" class=""></span>
											<div id="renewalSubTypeErrorMsg" class="text-danger"></div>
										</div>
										<input id="renewal_subtypeVal" name="renewal_subtype" type="hidden" />
									</div>
								</div>
							</div>
						</fieldset>
						<fieldset>
							<legend>Renewal Period</legend>
							<div class="box">
								<div class="box-body">

									<div class="row1" id="grprenewalDate" class="form-group">

										<label class="L-size control-label" for="reservation">Validity
											From &amp; To <abbr title="required">*</abbr>
										</label>
										<div class="I-size">
											<div class="input-group input-append date">
												<input type="text" class="form-text" name="renewal_from"
													required id="reservation" maxlength="26"
													data-inputmask="'mask': ['99-99-9999  to  99-99-9999']"
													data-mask=""> <span
													class="input-group-addon add-on"><span
													class="fa fa-calendar"></span></span>
											</div>
											<span id="renewalDateIcon" class=""></span>
											<div id="renewalDateErrorMsg" class="text-danger"></div>
										</div>
									</div>
									<br> <br>

									<div class="row" id="grprenewalTime" class="form-group">

										<label class="L-size control-label"
											for="renewal_timethreshold">Due Threshold <abbr
											title="required">*</abbr> :
										</label>

										<div class="I-size">
											<div class="col-md-4">
												<input type="text" class="form-text"
													name="renewal_timethreshold" id="renewal_timethreshold"
													min="1" max="6" maxlength="2" value="1" required="required"
													onkeypress="return IsNumericTimeThru(event);"
													ondrop="return false;"><span id="renewalTimeIcon"
													class=""></span>
												<div id="renewalTimeErrorMsg" class="text-danger"></div>
												<label class="error" id="errorTimeThru"
													style="display: none"> </label> <label class="error"
													id="errorTime"> </label>
											</div>
											<div class="col-md-4">
												<select class="form-text" name="renewal_periedthreshold"
													name="renewal_periedthreshold"
													onchange="OnChangeDueThreshold(this)" required="required">
													<option value="0">day(s)</option>
													<option value="7">Week(s)</option>
													<option value="28">Month(s)</option>
												</select>
											</div>
										</div>

									</div>
								</div>
							</div>
						</fieldset>
						<fieldset>
							<legend>Amount Information</legend>
							<div class="box">
								<div class="box-body">
									<c:if test="${configuration.receiptnumbershow}">
									<div class="row1" id="grprenewalReceipt" class="form-group">

										<label class="L-size control-label" for="renewalReceipt">Receipt
											No | Challan No <abbr title="required">*</abbr> :
										</label>

										<div class="I-size">
											<input type="text" class="form-text" name="renewal_receipt"
												onkeypress="return IsAlphaNumeric(event);"
												id="renewalReceipt" ondrop="return false;" maxlength="25">
											<span id="renewalReceiptIcon" class=""></span>
											<div id="renewalReceiptErrorMsg" class="text-danger"></div>
											<label class="error" id="error" style="display: none">
											</label>
										</div>
									</div>
									</c:if>
									<div class="row1" id="grprenewalAmount" class="form-group">

										<label class="L-size control-label" for="renewalAmount">Draft
											Amount <abbr title="required">*</abbr> :
										</label>

										<div class="I-size">
											<input type="text" class="form-text" name="renewal_Amount"
												id="renewalAmount" maxlength="7"
												onkeypress="return IsNumeric(event);" ondrop="return false;"
												maxlength="8"><span id="renewalAmountIcon" class=""></span>
											<div id="renewalAmountErrorMsg" class="text-danger"></div>
											<label class="error" id="errorAmount" style="display: none">
											</label>
										</div>
									</div>
									
										<input type="hidden" id="showVendorCol" value="${configuration.showVendorCol}">
										<c:if test="${configuration.showVendorCol}">
											<div class="row1" id="grpvendorId" class="form-group">
											<label class="L-size control-label"
												for="vendorId">Vendor : <abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input type="hidden" id="vendorId" name="vendorId"
													style="width: 100%;"
													placeholder="Please Enter 2 or more Vendor Name" />
											</div>
										</div>
										</c:if>
									
									<c:if test="${configuration.modeofaaymentshow}">
									<div class="row1">

										<label class="L-size control-label">Modes of Payment <abbr
											title="required">*</abbr> :
										</label>

										<div class="I-size">
												<select class="form-text" name="paymentTypeId"
													id="renPT_option">
														<c:forEach items="${PaymentType}" var="PaymentType">
																<option value="${PaymentType.paymentTypeId}">
																		<c:out value="${PaymentType.paymentTypeName}" />
																</option>
														</c:forEach>
												</select>
											</div>
									</div>
										</c:if>
									
									<c:if test="${configuration.cashtransactionshow}">
									<div class="row1">

										<label class="L-size control-label" id="target1"
											for="renPT_option"> NO : </label>

										<div class="I-size">
											<input type="text" class="form-text" name="renewal_PayNumber"
												onkeypress="return IsAlphaNumericPaynumber(event);"
												ondrop="return false;" maxlength="25"> <label
												class="error" id="errorPaynumber" style="display: none">
											</label>


										</div>
									</div>
									
									</c:if>
									<input type="hidden" id="tallyIntegrationRequired" value="${configuration.tallyIntegrationRequired}">
										<c:if test="${configuration.tallyIntegrationRequired}">
											<div class="row1" id="grpmanufacturer" class="form-group">
											<label class="L-size control-label" for="manufacurer">Tally Company Name :<abbr title="required">*</abbr></label>
												<div class="I-size">
													<input type="hidden" id="tallyCompanyId" name="tallyCompanyId" style="width: 100%;" value="0"
													  placeholder="Please Enter Tally Company Name" />
												</div>
											</div>
											
										</c:if>
									<c:if test="${configuration.paidDateshow}">
									<div class="row1" id="grppaidDate" class="form-group">

										<label class="L-size control-label" for="renewalpaidDate">Paid
											Date : <abbr title="required">*</abbr>
										</label>

										<div class="I-size">
											<div class="input-group input-append date" id="StartDate">
												<input type="text" class="form-text" id="renewalpaidDate"
													name="renewal_dateofpayment"
													data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
													class="input-group-addon add-on"><span
													class="fa fa-calendar"></span></span>
											</div>
											<span id="paidDateIcon" class=""></span>
											<div id="paidDateErrorMsg" class="text-danger"></div>
										</div>
									</div>
									</c:if>
										<c:if test="${configuration.paidbyshow}">
									<div class="row1">

										<label class="L-size control-label">Cashier | Paid By
											: </label>

										<div class="I-size">
											<input type="text" class="form-text" name="renewal_paidby"
												onkeypress="return IsAlphaNumericPaidby(event);"
												ondrop="return false;" maxlength="25" value="${userName}" readonly="readonly" > <label
												class="error" id="errorPaidby" style="display: none">
											</label>
										</div>
										<input type="hidden" name="renewal_paidbyId"  value="${userId }" />
									</div>
									</c:if>
								</div>
							</div>
						</fieldset>

						<fieldset>
							<legend>Upload Renewal Documents</legend>
							<div class="box">
								<div class="box-body">
									<div class="row" id="grprenewalFile" class="form-group">
										<label class="L-size control-label" for="renewalFile">File
											:<abbr title="required">*</abbr>
										</label>
										<div class="I-size">
											<input type="file" name="input-file-preview" id="renewalFile" />
											<span id="renewalFileIcon" class=""></span>
											<div id="renewalFileErrorMsg" class="text-danger"></div>

										</div>
									</div>
								</div>
							</div>
						</fieldset>

						<c:if test="${configuration.optionalInformation}">
						<fieldset>
							<legend>+ Optional Information</legend>
							<div class="box">
								<div class="box-body">
									<div class="row">

										<label class="L-size control-label">Authorization
											States :</label>

										<div class="I-size">
											<div class="form-group">
												<input type="text" class="form-text"
													name="renewal_authorization"
													onkeypress="return IsAlphaNumericAuthor(event);"
													ondrop="return false;" maxlength="25"> <label
													class="error" id="errorAuthor" style="display: none">
												</label>

											</div>

										</div>
									</div>
									<div class="row">

										<label class="L-size control-label">Remarks :</label>

										<div class="I-size">
											<div class="form-group">
												<input type="text" class="form-text" name="renewal_number"
													onkeypress="return IsAlphaNumericNumber(event);"
													ondrop="return false;" maxlength="50"> <label
													class="error" id="errorNumber" style="display: none">
												</label>
											</div>
										</div>
									</div>
								</div>
							</div>

						</fieldset>
						</c:if>
						<fieldset>
							<div class="panel-footer">
								<div class="L-size"></div>
								<div class="I-size">
									<button type="submit" onclick="return validateRenewalSave();" class="btn btn-success">Create
										Renewal Reminder</button>
									<a class="btn btn-info"
										href="<c:url value="VehicleRenewalReminder.in?vid=${vehicle.vid}"/>">Cancel</a>
								</div>
							</div>
						</fieldset>
					</form>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/RR/RenewalReminderlanguage.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/RR/RenewalReminder.validate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>

	<script>
		$(function() {
			//Date range picker
			$('#reservation').daterangepicker();
			$("#to").select2({
				placeholder : "Please Select Type"
			});
		});
	</script>
	<script type="text/javascript">
		$(document).ready(function() {
			var h = $("#OldVid").val(), i = $("#OldVehicleName").val();
			$("#RenewalSelectVehicle").select2("data", {
				id : h,
				text : i
			});
			$(function() {
				$("#renewalpaidDate").datepicker({
				       autoclose: !0,
				       todayHighlight: !0,
				       format: "dd-mm-yyyy",
				       endDate: new Date()
				   })
			}); 
		});
	</script>
			<script type="text/javascript">
						 $(document).ready(function() {
									$('#from').on('change', function() {
										$('#renewal_typeVal').val($("#from option:selected").text().trim());
									}); 
									$('#to').on('change', function() {
										$('#renewal_subtypeVal').val($("#to option:selected").text().trim());
									});
		
						 });
			</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>