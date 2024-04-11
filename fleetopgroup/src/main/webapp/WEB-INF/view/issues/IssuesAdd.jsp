<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
	<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/bootstrap-clockpicker.min.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="issuesOpen.in"/>">Issues</a> / <small>Add
						Issue</small>
				</div>
				<div class="pull-right">
					<a href="<c:url value="issuesOpen.in"/>">Close</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('ADD_ISSUES')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('ADD_ISSUES')">
			<div class="row" onload="fuelvehicle()">
				<div class="col-md-offset-1 col-md-9">

					<form id="formIssues" action="<c:url value="/saveVehicleIssues"/>"
						method="post" enctype="multipart/form-data" name="formIssues"
						role="form" class="form-horizontal">
						<div class="form-horizontal ">
							<fieldset>
							
						<input type="hidden" id="companyId" value="${companyId}">
						<input type="hidden" id="allowGPSIntegration" value="${gpsConfiguration.allowGPSIntegration}">
							
								<legend>Issue Entries</legend>
								<div class="box">
									<div class="box-body">
										<div class="row1">
											<label class="L-size control-label">Issues Type :<abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<select class="form-text" name="ISSUES_TYPE_ID" id="IssuesType">
													<!-- <option value="VEHICLE_ISSUE">Vehicle Issue</option>
													<option value="DRIVER_ISSUE">Driver Issue</option>
													<option value="OTHER_ISSUE">Other Issue</option>
													<option value="REFUND_ISSUE">Refund  Issue</option> -->
													<c:forEach items="${IssueType}" var="issueType">
																<option value="${issueType.issueTypeId}">
																		<c:out value="${issueType.issueTypeName}" />
																</option>
													</c:forEach>
												</select>
												<input type="hidden" id="validateOdometerInIssues" value="${validateOdometerInIssues}">
												<input type="hidden" id="validateMinOdometerInIssues" value="${validateMinOdometerInIssues}">
											</div>
										</div>
										<div class="row" id="vehicle_group_id" style="display: none;">
												<div class="form-group">
													<label class="L-size string required control-label">Vehicle
														Group :<abbr
												title="required">*</abbr></label>
													<div class="I-size">
														<select class="form-text" name="VEHICLE_GROUP_ID" id="vehiclegroupid">
															<c:forEach items="${vehiclegroup}" var="vehiclegroup">
																<option value="${vehiclegroup.gid}">
																		<c:out value="${vehiclegroup.vGroup}" />
																</option>
															</c:forEach>
														</select>
													</div>
												</div>
										</div>
										<div id="Vehicle_DriverID">
											<div id="VehicleID">
												<div class="row1">
													<label class="L-size control-label">Vehicle <abbr
														title="required">*</abbr></label>
													<div class="I-size">
														<input type="hidden" id="IssuesSelectVehicle"
															name="ISSUES_VID" style="width: 100%;"
															placeholder="Please Enter 2 or more Vehicle Name" />

													</div>
												</div>
												<div class="row1" id="VehicleOdoRow">
													<label class="L-size control-label">Odometer:</label>
													<div class="I-size">
														<input class="form-text" id="Issues_Odometer"
															name="ISSUES_ODOMETER" type="text" maxlength="7"
															onkeypress="return IsNumericOdometer(event);"
															ondrop="return false;"> <label class="error"
															id="errorOdometer" style="display: none"></label> <input
															type="hidden" id="vehicle_ExpectedOdameter" /> <input
															type="hidden" id="vehicle_Odameter" />
													</div>
												</div>

												<div class="row1" id="gpsOdometerRow" style="display: none;">
													<label class="L-size control-label">GPS Odometer :</label>
													<div class="I-size">
														<input class="form-text" id="GPS_ODOMETER" readonly="readonly"
															name="GPS_ODOMETER" type="text">
													</div>
												</div>
											</div>

											<div class="row1">
												<label class="L-size control-label">Driver Name : </label>
												<div class="I-size" id="driverSelect">
														<input type="hidden" id="VehicleTODriverFuel"
															name="ISSUES_DRIVER_ID" style="width: 100%;"
															placeholder="Please Enter 3 or more Driver Name" />
												</div>
											</div>
										</div>
										<c:if test="${configuration.customerIssue}">
										<div id="Customer_ID" >
										<div class="row1" id="grpissuesSummary1" class="form-group">
											<label class="L-size control-label" for="issuesSummary1">customer Name<abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input class="form-text" name="CUSTOMER_NAME" type="text"
													maxlength="150" id="issuesSummary1">
													<span id="issuesSummaryIcon1"
													class=""></span>
												<div id="issuesSummaryErrorMsg1" class="text-danger"></div>
												<label class="error" id="errorIssue_Summary"
													style="display: none"></label>
											</div>
										</div>
										</div>
										</c:if>
										<div id="Branch_Depart_hide" class="contact_Hide">
											<div class="row1">
												<label class="L-size control-label" id="Type">Branch
													:<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<input type="hidden" id="IssuesBranch"
														name="ISSUES_BRANCH_ID" style="width: 100%;"
														placeholder="Please Enter 3 or more Branch Name" />
												</div>
											</div>
											<div class="row1" >
												<label class="L-size control-label" id="Type">Department
													: </label>
												<div class="I-size">
													<input type="hidden" id="IssuesDepartnment"
														name="ISSUES_DEPARTNMENT_ID" style="width: 100%;"
														placeholder="Please Enter 2 or more Departnment Name" />
												</div>
											</div>
										</div>
										<div class="row1" id="grpreportDate" class="form-group">
											<label class="L-size control-label" for="reportDate">Reported
												Date <abbr title="required">*</abbr>
											</label>
											<div class="L-size">
												<div class="input-group input-append date" id="StartDate">
													<input type="text" class="form-text" id="woStartDate" onchange="getVehicleGPSDataAtTime();"
														name="ISSUES_REPORTED_DATE" required="required"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
												<span id="reportDateIcon" class=""></span>
												<div id="reportDateErrorMsg" class="text-danger"></div>
											</div>
											<div class="L-size">
												<div class="input-group clockpicker">
													<input type="text" class="form-text"
														name="issue_start_time" onchange="getVehicleGPSDataAtTime();" id="woStartTime" required="required"> <span
														class="input-group-addon"> <i
														class="fa fa-clock-o" aria-hidden="true"></i>
													</span>
												</div>
											</div>
										</div>
										<div class="row1" id="grpissuesSummary" class="form-group">
											<label class="L-size control-label" for="issuesSummary">Summary<abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input class="form-text" name="ISSUES_SUMMARY" type="text"
													maxlength="150" id="issuesSummary"
													onkeypress="return IsIssue_Summary(event);"
													ondrop="return false;"><span id="issuesSummaryIcon"
													class=""></span>
												<div id="issuesSummaryErrorMsg" class="text-danger"></div>
												<label class="error" id="errorIssue_Summary"
													style="display: none"></label>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Description</label>
											<div class="I-size">
												<textarea class="form-text" id="fuel_comments"
													name="ISSUES_DESCRIPTION" rows="5" maxlength="1500">
												</textarea>
												<label class="error" id="errorIssue_Description"
													style="display: none"></label>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Labels</label>
											<div class="I-size">
												<select class="form-text" name="ISSUES_LABELS_ID" id="priority"
													required="required">
													<option value="1">NORMAL</option>
													<option value="2">HIGH</option>
													<option value="3">LOW</option>
													<option value="4">URGENT</option>
													<option value="5">VERY URGENT</option>
													<c:if test="${showBreakdownSelection}">
														<option value="6">BREAKDOWN</option>													
													</c:if>
												</select>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Reported By <abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input class="form-text" name="ISSUES_REPORTED_BY"
													readonly="readonly" type="text" maxlength="150"
													value="${userName}"
													onkeypress="return IsIssues_Reported_By(event);"
													ondrop="return false;"> <label class="error"
													id="errorIssues_Reported_By" style="display: none"></label>
												<label class="error" id="errorOdo"></label>
												<input type="hidden" name="ISSUES_REPORTED_BY_ID" id="ISSUES_REPORTED_BY_ID" value="${userId}"/>
											</div>
										</div>
										<div class="row1" id="grpissuesAssigned" class="form-group">
											<label class="L-size control-label" for="subscribe">
												Assigned To : <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input class="" placeholder="Plese Select." id="subscribe"
													type="hidden" style="width: 100%" name="ISSUES_ASSIGN_TO">
												<span id="issuesAssignedIcon" class=""></span>
												<div id="issuesAssignedErrorMsg" class="text-danger"></div>
											</div>
										</div>
									</div>
									<div class="box-footer">
										<div class="L-size"></div>
										<div class="I-size">
											<div class="col-sm-offset-4 I-size">
												<button id="submit" type="submit" class="btn btn-success" onclick="return validateMaxOdameter();">Create
													Issues</button>
												<a class="btn btn-default"
													href="<c:url value="issuesOpen.in"/>">Cancel</a>
											</div>
										</div>
									</div>
								</div>
							</fieldset>
						</div>
					</form>
				</div>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/bootstrap-clockpicker.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IS/IssuesValidate.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IS/VehicleGPSDetails.js" />"></script>		
	<script type="text/javascript">
		$(document).ready(function() {
			$('.clockpicker').clockpicker({
				placement: 'bottom',
				align: 'right',
				autoclose: true
			});
			
			var today = new Date();
			var time = today.getHours();
			var minute = today.getMinutes();
			
			if(today.getMinutes()<10){
				minute = '0'+minute;
			} 
			
			if(today.getHours()<10){
				time = '0'+time;
			}
			
			$('#woStartTime').val(time+':'+minute);
		});
		function register(event) {
			event.preventDefault();
			$(".alert").html("").hide();
			$(".error-list").html("");
			if ($("#to").val() == "") {
				$("#emptyto").show().html(
						'Please specify at least one recipient.');
				return;
			}
			var formData = $('form').serialize();
			var issuestype = $("#IssuesType").val();
			$
					.post(
							"<c:url value="/saveVehicleIssues"/>",
							formData,
							function(data) {
								if (data.message == "success"
										&& data.error != null) {
									window.location.href = '<c:url value="/showIssues?Id='
											+ data.error + '"></c:url>';
								} else {
									window.location.href = '<c:url value="/issues/1.in"></c:url>';

								}
							})
					.fail(
							function(data) {
								if (data.responseJSON.error
										.indexOf("MailError") > -1) {
									$("#globalError").show().html(
											'Mail id is empty');

								} else if (data.responseJSON.error == "MailSentExist") {
									$("#emailError").show().html(
											data.responseJSON.message);
								} else {
									var errors = $
											.parseJSON(data.responseJSON.message);
									$.each(errors, function(index, item) {
										$("#" + item.field + "Error").show()
												.html(item.defaultMessage);
									});
									errors = $
											.parseJSON(data.responseJSON.error);
									$.each(errors, function(index, item) {
										$("#globalError").show().append(
												item.defaultMessage + "<br>");
									});
								}
							});
		}
		function closeArert() {
			$("#success").css("display", "none");

		}
	</script>
</div>