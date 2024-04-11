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
								code="label.master.home" /></a> / <a
							href="<c:url value="/RenewalReminder/1/1.in"/>">Renewal
							Reminders</a> / <a
							href="showRenewalReminder.in?renewal_id=${renewalReminder.renewal_id}"><c:out
								value="RR-${renewalReminder.renewal_R_Number}"></c:out> </a> / <small>Upload
							Renewal Reminders</small>
					</div>
					<div class="pull-right">
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

							<a
								href="showRenewalReminder.in?renewal_id=${renewalReminder.renewal_id}"><c:out
									value=" RR-${renewalReminder.renewal_R_Number}  "></c:out> </a> <a
								href="showVehicle.in?vid=${renewalReminder.vid}"
								data-toggle="tip" data-original-title="Click Vehicle Details">
								<c:out value="  ${renewalReminder.vehicle_registration}" />
							</a>
						</h3>

						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li><span class="fa fa-bell"> Renewal Type :</span> <a
									href="" data-toggle="tip" data-original-title="Renewal Type">
										<c:out value="${renewalReminder.renewal_type}" />
								</a></li>
								<li><span class="fa fa-usb"> Renewal Sub Type :</span> <a
									href="" data-toggle="tip"
									data-original-title="Renewal Sub Type"> <c:out
											value="${renewalReminder.renewal_subtype}" /></a></li>

								<li>Approval Id : <a
									href="<c:url value="/3/ShowRenRemApproval.in?AID=${renewalApproval.renewalApproval_id}&page=1" />"
									data-toggle="tip" data-original-title="Click this Details"><c:out
											value="RA-${renewalApproval.renewalApproval_Number}" /> </a></li>

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

					<form id="formApprovalRenewalUpload"
						action="<c:url value="UploadApprovalRenRem.in"/>" method="post"
						enctype="multipart/form-data" name="formApprovalRenewalUpload"
						role="form" class="form-horizontal"
						onsubmit="return renewalReminder();">
							<input type="hidden" value="${saveRenewalWithoutFile }" id="saveRenewalWithoutFile" />
						<input type="hidden" class="form-control" name="renewal_id"
							value="${renewalReminder.renewal_id}">
						 <input type="hidden" class="form-control" name="renewal_R_Number"
							value="${renewalReminder.renewal_R_Number}">
						<!-- Vehicle Id -->
						<input type="hidden" class="form-control" name="vid"
							value="${renewalReminder.vid}">
						<!-- Renewal Type -->
						<input type="hidden" id="OldRenewalType" name="renewal_type"
							value="${renewalReminder.renewal_type}">
						<!-- Renewal Sub Type -->
						<input type="hidden" class="form-control" name="renewal_subtype"
							value="${renewalReminder.renewal_subtype}">
						<input type="hidden" id="OldRenewalTypeId" name="renewalTypeId"
							value="${renewalReminder.renewalTypeId}">
						<input type="hidden" id="Oldrenewal_Subid" name="renewal_Subid"
							value="${renewalReminder.renewal_Subid}">

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
												<input type="text" id="reservation" class="form-text"
													name="renewal_from" required maxlength="26"
													data-inputmask="'mask': ['99-99-9999  to  99-99-9999']"
													data-mask="" readonly="readonly"
													value="${renewalReminder.renewal_from}  to  ${renewalReminder.renewal_to}">
												<span class="input-group-addon add-on"><span
													class="fa fa-calendar"></span></span>
											</div>
											<span id="renewalDateIcon" class=""></span>
											<div id="renewalDateErrorMsg" class="text-danger"></div>
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
									<div class="row1" id="grprenewalAmount" class="form-group">
										<label class="L-size control-label" for="renewalAmount">Draft
											Amount <abbr title="required">*</abbr> :
										</label>

										<div class="I-size">
											<input type="number" class="form-text" name="renewal_Amount"
												readonly="readonly" id="renewalAmount"
												value="${renewalReminder.renewal_Amount}" maxlength="7"
												onkeypress="return IsNumeric(event);" ondrop="return false;"
												maxlength="8"> <span id="renewalAmountIcon" class=""></span>
											<div id="renewalAmountErrorMsg" class="text-danger"></div>
											<label class="error" id="errorAmount" style="display: none">
											</label>

										</div>
									</div>
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
								<button type="submit" class="btn btn-success">Update
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

		<!-- <script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/RR/RenewalReminderlanguage.js" />"></script> -->
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/RR/RenewalReminder.validate.js" />"></script>

		<script
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
		<script
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	</section>
</div>