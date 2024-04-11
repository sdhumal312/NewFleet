<%@page import="org.apache.log4j.Logger"%>
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
				<div class="row">
					<div class="pull-left">
						<a href="<c:url value="/open"/>"><spring:message
								code="label.master.home" /></a> / <small><a
							href="<c:url value="/RenewalReminder/1/1.in"/>">Renewal
								Reminders</a></small> / <small><a
							href="showRenewalReminder.in?renewal_id=${renewalReminder.renewal_id}">Show
								Renewal Reminders </a></small> / <small>Edit Renewal Reminders</small>
					</div>
					<div class="pull-right">
						<sec:authorize access="hasAuthority('ADD_RENEWAL')">
							<a class="btn btn-success btn-sm" href="addRenewalReminder.in">
								<span class="fa fa-plus"></span> Add Renewal
							</a>
						</sec:authorize>
						<sec:authorize access="hasAuthority('EDIT_RENEWAL')">
							<a class="btn btn-info btn-sm"
								href="reviseRenewalReminder.in?renewal_id=${renewalReminder.renewal_id}">
								<i class="fa fa-plus"></i> Revise Renewal
							</a>
						</sec:authorize>
						<a class="btn btn-link"
							href="showRenewalReminder.in?renewal_id=${renewalReminder.renewal_id}">Cancel</a>

					</div>
				</div>
				<sec:authorize access="!hasAuthority('VIEW_RENEWAL')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_RENEWAL')">
					<div class="pull-left1">
						<h3 class="secondary-header-title">
							<a href="showVehicle.in?vid=${renewalReminder.vid}"
								data-toggle="tip" data-original-title="Click Vehicle Details">
								<c:out value="${renewalReminder.vehicle_registration}" />
							</a>
						</h3>

						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li><span class="fa fa-bell"> Type :</span> <a href=""
									data-toggle="tip" data-original-title="Renewal Type"> <c:out
											value="${renewalReminder.renewal_type}" />
								</a></li>
								<li><span class="fa fa-usb"> Sub Type :</span> <a href=""
									data-toggle="tip" data-original-title="Renewal Sub Type"> <c:out
											value="${renewalReminder.renewal_subtype}" /></a></li>

							</ul>
						</div>

					</div>
				</sec:authorize>
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="row">
			<sec:authorize access="!hasAuthority('EDIT_RENEWAL')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('EDIT_RENEWAL')">
				<div class="col-md-offset-1 col-md-9">
					<form id="formRenewal"
						action="<c:url value="updateRenewalReminder.in"/>" method="post"
						enctype="multipart/form-data" name="formRenewal" role="form"
						class="form-horizontal" onsubmit="return renewalReminder();">
						
						<input type="hidden" value="${saveRenewalWithoutFile }" id="saveRenewalWithoutFile" />

						<input type="hidden" class="form-control" name="renewal_id"
							value="${renewalReminder.renewal_id}" id="renewal_id"> <input
							type="hidden" class="form-control" name="renewal_approvedID"
							value="${renewalReminder.renewal_approvedID}">
						<input type="hidden" class="form-control" name="renewal_R_Number"
							value="${renewalReminder.renewal_R_Number}" id="renewal_R_Number"> 
							
						<input type="hidden" class="form-control" name="renewal_staus_id"
							value="${renewalReminder.renewal_staus_id}" id="renewal_staus_id">
							
						<div class="box">
							<div class="box-body">
								<div class="row1" id="grpvehicleNumber" class="form-group">
									<label class="L-size control-label" for="RenewalSelectVehicle">Vehicle
										Name : <abbr title="required">*</abbr>
									</label>
									<div class="I-size">

										<input type="hidden" class="form-control" id="OldVid"
											name="vid" value="${renewalReminder.vid}"> <input
											type="hidden" class="form-control" id="OldVehicleName"
											name="vehicle_registration"
											value="${renewalReminder.vehicle_registration}"> <input
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
										<input type="hidden" id="OldRenewalType" name="renewal_type"
											value="${renewalReminder.renewal_type}"> <input
											type="hidden" id="from" style="width: 100%;"
											readonly="readonly"
											placeholder="Please Enter 2 or more Renewal Name" /> <span
											id="renewalTypeIcon" class=""></span>
										<div id="renewalTypeErrorMsg" class="text-danger"></div>
									</div>
									<input type="hidden" class="form-control" name="renewalTypeId"
										value="${renewalReminder.renewalTypeId}" id="renewalTypeId">
								</div>
								<br>
								<div class="row1" id="grprenewalSubType" class="form-group">
									<label class="L-size control-label" for='to'>Renewal
										Sub Type <abbr title="required">*</abbr>
									</label>

									<div class="I-size">
										<input type="hidden" class="form-control"
											name="renewal_subtype"
											value="${renewalReminder.renewal_subtype}"> <select
											style="width: 100%;" id='to' disabled="disabled">
											<option value="${renewalReminder.renewal_subtype}"
												selected="selected">${renewalReminder.renewal_subtype}</option>
										</select> <span id="renewalSubTypeIcon" class=""></span>
										<div id="renewalSubTypeErrorMsg" class="text-danger"></div>
									</div>
									<input type="hidden" class="form-control" name="renewal_Subid"
										value="${renewalReminder.renewal_Subid}" id="renewal_Subid">
								</div>
							</div>
						</div>

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
													data-mask=""
													value="${renewalReminder.renewal_from}  to  ${renewalReminder.renewal_to}">
												<span class="input-group-addon add-on"><span
													class="fa fa-calendar"></span></span>
											</div>
											<span id="renewalDateIcon" class=""></span>
											<div id="renewalDateErrorMsg" class="text-danger"></div>
										</div>
									</div>
									<br>

									<div class="row" id="grprenewalTime" class="form-group">

										<label class="L-size control-label"
											for="renewal_timethreshold">Due Threshold <abbr
											title="required">*</abbr> :
										</label>

										<div class="I-size">
											<div class="col-md-4">
												<input type="text" class="form-text"
													name="renewal_timethreshold" id="renewal_timethreshold"
													value="${renewalReminder.renewal_timethreshold}" min="1"
													max="6" maxlength="2" value="1" required="required"
													onkeypress="return IsNumericTimeThru(event);"
													ondrop="return false;"> <span id="renewalTimeIcon"
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
												maxlength="25" value="${renewalReminder.renewal_receipt}"
												id="renewalReceipt"
												onkeypress="return IsAlphaNumeric(event);"
												ondrop="return false;" maxlength="25"> <span
												id="renewalReceiptIcon" class=""></span>
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
											Amount <abbr title="required">*</abbr> :
										</label>

										<div class="I-size">
											<input type="number" class="form-text" name="renewal_Amount"
												id="renewalAmount" value="${renewalReminder.renewal_Amount}"
												maxlength="7" onkeypress="return IsNumeric(event);"
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
											  <input type="hidden" id="venId" value="${renewalReminder.vendorId }">
											  <input type="hidden" id="venName" value="${renewalReminder.vendorName }">
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
													<option value="${renewalReminder.paymentTypeId }">${renewalReminder.renewal_paymentType} </option>
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
											for="renPT_option"><c:out
												value="${renewalReminder.renewal_paymentType}" /> NO : </label>

										<div class="I-size">
											<input type="text" class="form-text"
												value="${renewalReminder.renewal_PayNumber}"
												name="renewal_PayNumber"
												onkeypress="return IsAlphaNumericPaynumber(event);"
												ondrop="return false;" maxlength="25"> <label
												class="error" id="errorPaynumber" style="display: none">
											</label>
										</div>
									</div>
									</c:if>
									<c:if test="${configuration.paidDateshow}">
									<div class="row1" id="grppaidDate" class="form-group">

										<label class="L-size control-label" for="renewalpaidDate">Paid
											Date :<abbr title="required">*</abbr>
										</label>

										<div class="I-size">
											<div class="input-group input-append date" id="StartDate">
												<input type="text" class="form-text"
													name="renewal_dateofpayment" id="renewalpaidDate"
													value="${renewalReminder.renewal_dateofpayment}"
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
												value="${renewalReminder.renewal_paidby}"
												readonly="readonly"
												onkeypress="return IsAlphaNumericPaidby(event);"
												ondrop="return false;" maxlength="25"> <label
												class="error" id="errorPaidby" style="display: none">
											</label>
										</div>
										<input type="hidden" name = "renewal_paidbyId"  value="${renewalReminder.renewal_paidbyId}" />
										<input type="hidden" name = "renewal_approvedbyId"  value="${renewalReminder.renewal_approvedbyId}" />
									</div>
									</c:if>
								</div>
							</div>
						</fieldset>

						<fieldset>
							<legend>Upload Renewal Documents</legend>
							<div class="box">
								<div class="box-body">
									<div class="row1" id="grprenewalFile" class="form-group">
										<label class="L-size control-label" for="renewalFile">File
											:</label>
										<div class="I-size">
											<input type="file" id="renewalFile" name="input-file-preview" />
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
									<c:if test="${configuration.showAuthorizationState}">
									<div class="row">
										<label class="L-size control-label">Authorization
											States : </label>

										<div class="I-size">
											<div class="form-group">
												<input type="text" class="form-text"
													name="renewal_authorization"
													value="${renewalReminder.renewal_authorization}"
													onkeypress="return IsAlphaNumericAuthor(event);"
													ondrop="return false;" maxlength="25"> <label
													class="error" id="errorAuthor" style="display: none">
												</label>

											</div>

										</div>
									</div>
									</c:if>
									<div class="row">

										<label class="L-size control-label">Remarks : </label>

										<div class="I-size">
											<div class="form-group">
												<input type="text" class="form-text" name="renewal_number"
													value="${renewalReminder.renewal_number}"
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
						<div class="panel-footer">
							<div class="L-size"></div>
							<div class="I-size">
								<button type="submit" onclick="return validateRenewalSave();" class="btn btn-success">Update
									Renewal Reminder</button>
								<a class="btn btn-link"
									href="showRenewalReminder.in?renewal_id=${renewalReminder.renewal_id}">Cancel</a>
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
				//Date range picker
				$('#reservation').daterangepicker();
				$("#to").select2({
					placeholder : "Please Select Type"
				});
			});
		</script>
		<script type="text/javascript">
			$(document).ready(
					function() {
						var h = $("#OldVid").val(), i = $("#OldVehicleName")
								.val();
						$("#RenewalSelectVehicle").select2("data", {
							id : h,
							text : i
						});
						var j = $("#OldRenewalType").val(), k = $(
								"#OldRenewalType").val();
						$("#from").select2("data", {
							id : j,
							text : k
						});
						$("#vendorId").select2("data", {
							id : $('#venId').val(),
							text : $('#venName').val(),
						})
					});
		</script>
		<script
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
		<script
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	</section>
</div>