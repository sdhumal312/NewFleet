<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/RenewalReminder/1/1.in"/>">Renewal
						Reminders</a> / <small>Add Renewal Reminders</small>
				</div>
				<div class="pull-right">

					<a href="<c:url value="/RenewalReminder/1/1.in"/>">Cancel</a>

				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<sec:authorize access="!hasAuthority('ADD_RENEWAL')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('ADD_RENEWAL')">
				<div class="col-md-offset-1 col-md-9">

					<c:if test="${param.closeStatus eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							${VMandatory}<br> You should be close first TripSheet or
							change status or close workOrder .
						</div>
					</c:if>

					<form id="formRenewal"
						action="<c:url value="/saveRenewalReminder.in"/>" method="post"
						enctype="multipart/form-data" name="formRenewal" role="form"
						class="form-horizontal" onsubmit="return renewalReminder();">
						<div class="form-horizontal ">
							<input type="hidden" value="${saveRenewalWithoutFile }" id="saveRenewalWithoutFile" />
							<fieldset>
								<legend>Vehicle Information</legend>
								<div class="box">
									<div class="box-body">
										<div class="row1" id="grpvehicleNumber" class="form-group">
											<label class="L-size control-label"
												for="RenewalSelectVehicle">Vehicle Name : <abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input type="hidden" id="RenewalSelectVehicle" name="vid"
													style="width: 100%;"
													placeholder="Please Enter 2 or more Vehicle Name" /> <span
													id="vehicleNumberIcon" class=""></span>
												<div id="vehicleNumberErrorMsg" class="text-danger"></div>
											</div>
										</div>
									</div>
								</div>
							</fieldset>
							<fieldset>
								<legend>Renewal Types</legend>
								<div class="box">
									<div class="box-body">
										<div class="row1" id="grprenewalType" class="form-group">

											<label class="L-size control-label" for="from">Renewal
												Type <abbr title="required">*</abbr>
											</label>

											<div class="I-size">
												<input type="hidden" id="from" name="renewalTypeId"
													style="width: 100%;"
													placeholder="Please Enter 2 or more Renewal Name" /> <span
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
														data-inputmask="'mask': ['99-99-9999  to  99-99-9999']"
														data-mask="" required id="reservation" maxlength="26">
													<span class="input-group-addon add-on"><span
														class="fa fa-calendar"></span></span>
												</div>
												<span id="renewalDateIcon" class=""></span>
												<div id="renewalDateErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<br> <br>

										<div class="row1" id="grprenewalTime" class="form-group">

											<label class="L-size control-label"
												for="renewal_timethreshold">Due Threshold <abbr
												title="required">*</abbr></label>

											<div class="I-size">
												<div class="col-md-4">
													<input type="text" class="form-text"
														name="renewal_timethreshold" min="1" max="6" maxlength="2"
														value="1" required="required" id="renewal_timethreshold"
														onkeypress="return IsNumericTimeThru(event);"
														ondrop="return false;"> <span id="renewalTimeIcon"
														class=""></span>
													<div id="renewalTimeErrorMsg" class="text-danger"></div>
													<label class="error" id="errorTimeThru"
														style="display: none"> </label> <label class="error"
														id="errorTime"> </label>
												</div>
												<div class="col-md-4">
													<select class="form-text" id="renewal_periedthreshold"
														name="renewal_periedthreshold"
														onchange="OnChangeDueThreshold(this)" required="required">
														<option value="0">Day(s)</option>
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
										<c:if test="${!configuration.receiptnumbershow}">
										<div class="row1" id="grprenewalReceipt" class="form-group">
											<input class="hide" id="renewalReceipt" name="renewal_receipt" value="0" style="width: 100%;"/>
											<span id="renewalReceiptIcon" class=""></span>
											<div id="renewalReceiptErrorMsg" class="text-danger"></div>
											<label class="error" id="error" style="display: none"></label>
										</div>
										</c:if>

										<div class="row1" id="grprenewalAmount" class="form-group">

											<label class="L-size control-label" for="renewalAmount">Draft
												Amount <abbr title="required">*</abbr>
											</label>

											<div class="I-size">
												<input type="text" class="form-text" name="renewal_Amount"
													maxlength="7" id="renewalAmount"
													onkeypress="return IsNumeric(event);"
													ondrop="return false;" maxlength="8"> <span
													id="renewalAmountIcon" class=""></span>
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
											<label class="L-size control-label">Modes of Payment
												<abbr title="required">*</abbr>
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
										<c:if test="${!configuration.cashtransactionshow}">
										<input type="hidden" name=paymentTypeId value="0" style="width: 100%;"/>
										</c:if>
										
										<c:if test="${configuration.cashtransactionshow}">
										<div class="row1">

											<label class="L-size control-label" id="target1"
												for="renPT_option">Enter </label>

											<div class="I-size">
												<input type="text" class="form-text"
													name="renewal_PayNumber"
													onkeypress="return IsAlphaNumericPaynumber(event);"
													ondrop="return false;" maxlength="25"> <label
													class="error" id="errorPaynumber" style="display: none">
												</label>


											</div>
										</div>
										</c:if>
										<c:if test="${!configuration.cashtransactionshow}">
								<input type="hidden" name=renewal_PayNumber value="0" style="width: 100%;"/>
								</c:if>
					
										<c:if test="${configuration.paidDateshow}">
										<div class="row1" id="grppaidDate" class="form-group">

											<label class="L-size control-label" for="renewalpaidDate">Paid
												Date :<abbr title="required">*</abbr>
											</label>

											<div class="I-size">
												<div class="input-group input-append date" id="paidDate">
													<input type="text" class="form-text"
														name="renewal_dateofpayment" id="renewalpaidDate"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"><span
														class="fa fa-calendar"></span></span>
												</div>
												<span id="paidDateIcon" class=""></span>
												<div id="paidDateErrorMsg" class="text-danger"></div>
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
										<c:if test="${!configuration.paidDateshow}">
										<input type="hidden" name=renewal_dateofpayment value="" style="width: 100%;"/>
										</c:if>
										
										<input type="hidden" name= "renewal_paidbyId" value="${userId}" />
										<c:if test="${configuration.paidbyshow}">
										<div class="row1">
											<label class="L-size control-label">Cashier | Paid By
												: </label>
											<div class="I-size">
												<input type="text" value="${userName}" class="form-text"
													name="renewal_paidby"
													onkeypress="return IsAlphaNumericPaidby(event);"
													ondrop="return false;" maxlength="50" readonly="readonly">
												<label class="error" id="errorPaidby" style="display: none">
												</label>
											</div>
											
										</div>
										</c:if>
										<c:if test="${!configuration.paidbyshow}">
										<input type="hidden" name=renewal_paidby value="" style="width: 100%;"/>
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
												:</label>
											<div class="I-size">
												<input type="file" name="input-file-preview"
													id="renewalFile" /> <span id="renewalFileIcon" class=""></span>
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
									<c:if test="${configuration.showAuthorizationState}">
										<div class="row1">
											<label class="L-size control-label">Authorization
												States</label>

											<div class="I-size">
												<input type="text" class="form-text"
													name="renewal_authorization" placeholder=""
													onkeypress="return IsAlphaNumericAuthor(event);"
													ondrop="return false;" maxlength="25"> <label
													class="error" id="errorAuthor" style="display: none">
												</label>
											</div>
										</div>
									</c:if>	
										<div class="row1">

											<label class="L-size control-label">Remarks</label>

											<div class="I-size">
												<input type="text" class="form-text" name="renewal_number"
													onkeypress="return IsAlphaNumericNumber(event);"
													ondrop="return false;" maxlength="50"> <label
													class="error" id="errorNumber" style="display: none">
												</label>
											</div>
										</div>
									</div>
								</div>
							</fieldset>
							</c:if>

							<div class="panel-footer">
								<div class="L-size"></div>
								<div class="I-size">

									<button type="submit" onclick="return validateRenewalSave();" class="btn btn-success">Create
										Renewal Reminder</button>
									<a class="btn btn-link"
										href="<c:url value="/RenewalReminder/1/1.in"/>">Cancel</a>
								</div>
							</div>


						</div>
					</form>
				</div>
			</sec:authorize>
		</div>
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

		<script>
			$(function() {
				$('#reservation').daterangepicker();
				$("#to").select2({
					placeholder : "Please Select Type"
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
		<script
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
		<script
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
			
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
			
	</section>
</div>