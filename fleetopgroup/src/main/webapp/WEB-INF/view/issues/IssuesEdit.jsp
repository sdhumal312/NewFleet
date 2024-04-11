<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="issuesOpen.in"/>">Issues</a> / <small>Edit
						Issue</small>
				</div>
				<div class="pull-right">
					<a href="<c:url value="issuesOpen.in"/>">Close</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('EDIT_ISSUES')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('EDIT_ISSUES')">
			<div class="row" onload="fuelvehicle()">
				<div class="col-md-offset-1 col-md-9">
					<form id="formEditIssues"
						action="<c:url value="/updateIssues.in"/>" method="post"
						enctype="multipart/form-data" name="formEditIssues" role="form"
						class="form-horizontal">

						<div class="form-horizontal ">
							<fieldset>
								<legend>Issue Entries</legend>
								<div class="box">
									<div class="box-body">
										<div class="row1">
											<input type="hidden" name="ISSUES_ID" class="form-text"
												value="${Issues.ISSUES_ID}" />
											<input type="hidden" name="ISSUES_TYPE_ID" class="form-text"
												value="${Issues.ISSUES_TYPE_ID}" />
												<input type="hidden" name="VEHICLE_GROUP_ID" class="form-text"
												value="${Issues.VEHICLE_GROUP_ID}" />
											<input type="hidden" name="ISSUES_STATUS_ID" class="form-text"
												value="${Issues.ISSUES_STATUS_ID}" />	
										</div>
										<div class="row1">
											<label class="L-size control-label">Issues Type :<abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<%-- <input type="text" readonly="readonly" name="ISSUES_TYPE"
													class="form-text" value="${Issues.ISSUES_TYPE}"
													style="width: 100%;"
													placeholder="Please Enter 2 or more Vehicle Name" /> --%>
												<select disabled="disabled" class="form-text" name="ISSUES_TYPE_ID" id="IssuesType"
													required="required">
													<option value="${Issues.ISSUES_TYPE_ID}" selected="selected">${Issues.ISSUES_TYPE}</option>
												</select>
											</div>
										</div>
										<c:choose>
											<c:when test="${Issues.ISSUES_TYPE_ID == 1}">
												<div class="row1">
													<label class="L-size control-label">Vehicle <abbr
														title="required">*</abbr></label>
													<div class="I-size">
														<input type="hidden" name="ISSUES_VID" class="form-text"
															value="${Issues.ISSUES_VID}" readonly="readonly" /> <input
															type="text" name="ISSUES_VEHICLE_REGISTRATION"
															class="form-text"
															value="${Issues.ISSUES_VEHICLE_REGISTRATION}"
															readonly="readonly" />
															<input type="hidden" id="validateOdometerInIssues" value="${validateOdometerInIssues}">
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Odometer:</label>
													<div class="I-size">
														<input class="form-text" id="Issues_Odometer"
															value="${Issues.ISSUES_ODOMETER}" name="ISSUES_ODOMETER"
															type="text" maxlength="7"
															onkeypress="return IsNumericOdometer(event);"
															ondrop="return false;"> <label class="error"
															id="errorOdometer" style="display: none"></label>
															<input type="hidden" id="vehicle_ExpectedOdameter" value="${vehicle.vehicleExpectedKm}" />
															<input type="hidden" id="vehicle_Odameter" value="${vehicle.vehicle_Odometer}" />
													</div>
												</div>
												<c:if test="${Issues.GPS_ODOMETER != null && Issues.GPS_ODOMETER > 0}">
													<div class="row1" id="gpsOdometerRow">
													<label class="L-size control-label">GPS Odometer :</label>
													<div class="I-size">
														<input class="form-text" id="GPS_ODOMETER" readonly="readonly"
															name="GPS_ODOMETER" type="text" value="${Issues.GPS_ODOMETER}">
													</div>
												</div>
												</c:if>
												
												<div class="row1">
													<label class="L-size control-label">Driver Name :</label>
													<div class="I-size" id="driverSelect">
														<div class="col-md-9">
															<input type="text" id="VehicleTODriverFuel"
																name="ISSUES_DRIVER_ID" style="width: 100%;"
																placeholder="${Issues.ISSUES_DRIVER_NAME}" /> <input
																type="hidden" id="Odid"
																value="${Issues.ISSUES_DRIVER_ID}" /> <input
																type="hidden" id="Odname"
																value="${Issues.ISSUES_DRIVER_NAME}" />
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
																<input class="form-text row1" name="ISSUES_DRIVER_NAME"
																	value="${Issues.ISSUES_DRIVER_NAME}" maxlength="149"
																	type="text" onkeypress="return IsDriverName(event);"
																	ondrop="return false;"> <label class="error"
																	id="errorDriverName" style="display: none"> </label>
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
											</c:when>
											<c:when test="${Issues.ISSUES_TYPE_ID == 2}">
												<div class="row1">
													<label class="L-size control-label">Driver Name :</label>
													<div class="I-size" id="driverSelect">
														<input type="hidden" value="${Issues.ISSUES_DRIVER_ID}"
															name="ISSUES_DRIVER_ID" /> <input class="form-text row1"
															name="ISSUES_DRIVER_NAME" maxlength="149" type="text"
															readonly="readonly"
															placeholder="Enter Issues Driver Name"
															value="${Issues.ISSUES_DRIVER_NAME}"
															onkeypress="return IsDriverName(event);"
															ondrop="return false;"> <label class="error"
															id="errorDriverName" style="display: none"> </label>
													</div>
												</div>
											</c:when>
											<c:when test="${Issues.ISSUES_TYPE_ID == 4}">
												<div class="row1">
													<label class="L-size control-label">Customer Name :</label>
													<div class="I-size" id="driverSelect">
														<input type="hidden" value="${Issues.CUSTOMER_NAME}"
															name="ISSUES_CUSTOMER_ID" /> <input class="form-text row1"
															name="ISSUES_CUSTOMER_ID" maxlength="149" type="text"
															readonly="readonly"
															placeholder="Enter Issues Customer Name"
															value="${Issues.CUSTOMER_NAME}"
															onkeypress="return IsDriverName(event);"
															ondrop="return false;"> <label class="error"
															id="errorDriverName" style="display: none"> </label>
													</div>
												</div>
											</c:when>
											<c:otherwise>
										
													<div class="row1">
														<label class="L-size control-label">Vehicle Group :<abbr
															title="required">*</abbr></label>
														<div class="I-size">
															<select disabled="disabled" class="form-text" name="VEHICLE_GROUP_ID" id="VEHICLE_GROUP_ID"
																		required="required">
																		<option value="${Issues.VEHICLE_GROUP_ID}" selected="selected">${Issues.ISSUES_VEHICLE_GROUP}</option>
															</select>
														</div>
													</div>
												<div class="row1">
													<label class="L-size control-label" id="Type">Branch
														: </label>
													<div class="I-size">
														<input type="hidden" id="IssuesBranch"
															name="ISSUES_BRANCH_ID" style="width: 100%;"
															placeholder="Please Enter 3 or more Branch Name" /> <input
															type="hidden" id="Bid" value="${Issues.ISSUES_BRANCH_ID}" />
														<input type="hidden" id="Bname"
															value="${Issues.ISSUES_BRANCH_NAME}" />
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label" id="Type">Department
														: </label>
													<div class="I-size">
														<input type="hidden" id="IssuesDepartnment"
															name="ISSUES_DEPARTNMENT_ID" style="width: 100%;"
															placeholder="Please Enter 3 or more Departnment Name" />

														<input type="hidden" id="Mid"
															value="${Issues.ISSUES_DEPARTNMENT_ID}" /> <input
															type="hidden" id="Mname"
															value="${Issues.ISSUES_DEPARTNMENT_NAME}" />
													</div>
												</div>
											</c:otherwise>
										</c:choose>
										<div class="row1" id="grpreportDate" class="form-group">
											<label class="L-size control-label" for="reportDate">Reported
												Date <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="input-group input-append date" id="StartDate">
													<input type="text" class="form-text" id="reportDate"
														value="${Issues.ISSUES_REPORTED_DATE}"
														name="ISSUES_REPORTED_DATE" required="required"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
												<span id="reportDateIcon" class=""></span>
												<div id="reportDateErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<div class="row1" id="grpissuesSummary" class="form-group">
											<label class="L-size control-label" for="issuesSummary">Summary<abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input class="form-text" name="ISSUES_SUMMARY" type="text"
													maxlength="150" id="issuesSummary"
													value="${Issues.ISSUES_SUMMARY}"
													onkeypress="return IsIssue_Summary(event);"
													ondrop="return false;"> <span
													id="issuesSummaryIcon" class=""></span>
												<div id="issuesSummaryErrorMsg" class="text-danger"></div>
												<label class="error" id="errorIssue_Summary"
													style="display: none"></label>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Description</label>
											<div class="I-size">
												<textarea class="form-text" id="fuel_comments"
													name="ISSUES_DESCRIPTION" rows="5" maxlength="490">${Issues.ISSUES_DESCRIPTION}
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
													<option value="${Issues.ISSUES_LABELS_ID}" selected="selected">${Issues.ISSUES_LABELS}</option>
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
											<label class="L-size control-label">Reported By :<abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input class="form-text" name="ISSUES_REPORTED_BY"
													type="text" maxlength="150" readonly="readonly"
													value="${Issues.ISSUES_REPORTED_BY}"
													onkeypress="return IsIssues_Reported_By(event);"
													ondrop="return false;"> <label class="error"
													id="errorIssues_Reported_By" style="display: none"></label>
												<label class="error" id="errorOdo"></label>
												<input type="hidden" id="ISSUES_REPORTED_BY_ID" name="ISSUES_REPORTED_BY_ID" value="${Issues.ISSUES_REPORTED_BY_ID}" />
											</div>
										</div>
										<div class="row1" id="grpissuesAssigned" class="form-group">
											<label class="L-size control-label" for="subscribe">
												Assigned To : <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<!-- -- -->
												<input id="assignedtoSelectOld" type="hidden"
													style="width: 100%" value="${Issues.ISSUES_ASSIGN_TO}">
												<!--  -->
												<input id="assignedtoNameSelectOld" type="hidden"
													style="width: 100%" value="${Issues.ISSUES_ASSIGN_TO_NAME}">
												<input class="" placeholder="Plese Select new assigned to"
													id="subscribe" type="hidden" style="width: 100%"
													name="ISSUES_ASSIGN_TO"> <span
													id="issuesAssignedIcon" class=""></span>
												<div id="issuesAssignedErrorMsg" class="text-danger"></div>

											</div>
										</div>
									</div>
									<div class="box-footer">
										<div class="L-size"></div>
										<div class="I-size">
											<div class="col-sm-offset-4 I-size">
												<button type="submit" onclick="return validateMaxOdameter();" class="btn btn-success">Update
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IS/IssuesValidate.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							var a = $("#assignedtoSelectOld").val().split(","), b = $(
									"#assignedtoNameSelectOld").val()
									.split(","), c = a.length - 1, d = "";
							for ( var e in a)
								d += e != c ? '{"id":"' + a[e] + '","text":"'
										+ b[e] + '" },' : '{"id":"' + a[e]
										+ '","text":"' + b[e] + '" }';
							var f = "[" + d + "]", g = JSON.parse(f);
							$("#subscribe").select2("data", g);
							var h = $("#Odid").val(), i = $("#Odname").val();
							$("#VehicleTODriverFuel").select2("data", {
								id : h,
								text : i
							});
							var j = $("#Bid").val(), k = $("#Bname").val();
							$("#IssuesBranch").select2("data", {
								id : j,
								text : k
							});
							var l = $("#Mid").val(), m = $("#Mname").val();
							$("#IssuesDepartnment").select2("data", {
								id : l,
								text : m
							})
						});
	</script>
</div>