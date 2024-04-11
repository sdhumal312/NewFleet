<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<div class="content-wrapper">
	<section class="content">
		<div class="row">
			<div class="col-md-offset-1 col-md-9 col-sm-9 col-xs-12">
				<div class="box">
					<div class="boxinside">
						<div class="box-header">
							<div class="pull-left">
								<a href="<c:url value="/open"/>"><spring:message
										code="label.master.home" /></a> / <a
									href="<c:url value="/newTripDaily.in"/>">TripCollection</a> / <a
									href="<c:url value="/manageTripDaily/1.in"/>">Manage Trip</a> /
								<a href="<c:url value="/closeTripDaily/1.in"/>">Close Trip</a> /
								<a
									href="<c:url value="/showTripDaily?ID=${TripDaily.TRIPDAILYID}"/>">Show
									TripCollection</a> / Add Penalty
							</div>
							<div class="pull-right"></div>
						</div>

						<sec:authorize access="!hasAuthority('ADD_INCOME_TRIPSHEET')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('ADD_INCOME_TRIPSHEET')">
							<div class="row">
								<div class="pull-left">
									<h4>
										Trip Number : <a
											href="showTripDaily.in?ID=${TripDaily.TRIPDAILYID}">TS-
											${TripDaily.TRIPDAILYNUMBER}</a>
									</h4>
								</div>
								<div class="pull-right">
									<h5>Created Date : ${TripDaily.CREATED}</h5>
								</div>
							</div>
							<div class="row">
								<h4 align="center">
									<c:choose>
										<c:when test="${TripDaily.VEHICLEID == 0}">
											<a style="color: #000000;" href="#" data-toggle="tip"
												data-original-title="Click Vehicle Info"> <c:out
													value="${TripDaily.VEHICLE_REGISTRATION}" />
											</a>
										</c:when>
										<c:otherwise>
											<a style="color: #000000;"
												href="showVehicle.in?VID=${TripDaily.VEHICLEID}"
												data-toggle="tip" data-original-title="Click Vehicle Info">
												<c:out value="${TripDaily.VEHICLE_REGISTRATION}" />
											</a>
										</c:otherwise>
									</c:choose>
									<br>
									<c:out value="${TripDaily.TRIP_ROUTE_NAME}" />
									<br>
									<c:out value="${TripDaily.VEHICLE_GROUP}" />
								</h4>
							</div>

							<div class="secondary-header-title">
								<ul class="breadcrumb">
									<li>Date of Journey : <a data-toggle="tip"
										data-original-title="Trip Open Date"> <c:out
												value="${TripDaily.TRIP_OPEN_DATE}" /></a></li>
									<li>Depot : <a data-toggle="tip"
										data-original-title="Group Service"><c:out
												value="${TripDaily.VEHICLE_GROUP}" /></a></li>

									<li>Driver: <a data-toggle="tip"
										data-original-title="Driver"> <c:out
												value="${TripDaily.TRIP_DRIVER_NAME}" /></a></li>
									<li>Conductor : <a data-toggle="tip"
										data-original-title="Driver 2"><c:out
												value="${TripDaily.TRIP_CONDUCTOR_NAME}" /></a></li>
									<li>Cleaner : <a data-toggle="tip"
										data-original-title="Cleaner"><c:out
												value="${TripDaily.TRIP_CLEANER_NAME}" /></a></li>
									<li>Opening KM: <a data-toggle="tip"
										data-original-title="Opening KM"><c:out
												value="${TripDaily.TRIP_OPEN_KM}" /></a></li>
									<li>Closing KM: <a data-toggle="tip"
										data-original-title="closing KM"> <c:out
												value="${TripDaily.TRIP_CLOSE_KM}" /></a></li>

									<li>Usage KM: <a data-toggle="tip"
										data-original-title="usage KM"> <c:out
												value="${TripDaily.TRIP_USAGE_KM}" /></a></li>



								</ul>
							</div>
							<br>
							<c:if test="${!empty DriverAdvanvce}">

								<fieldset>
									<div class="row">
										<div class="">
											<table class="table table-bordered table-striped">
												<thead>
													<tr class="breadcrumb">
														<th class="fit">No</th>
														<th class="fit ar">Name</th>
														<th class="fit ar">Penalty date</th>
														<th class="fit ar">Amount</th>
														<th class="fit ar">Paid By</th>
														<th class="fit ar">Actions</th>
													</tr>
												</thead>
												<tbody>
													<%
														Integer hitsCount = 1;
													%>
													<c:forEach items="${DriverAdvanvce}" var="DriverAdvanvce">
														<tr data-object-id="" class="ng-scope">
															<td class="fit">
																<%
																	out.println(hitsCount);
																				hitsCount += 1;
																%>
															</td>
															<td class="fit ar"><c:out
																	value="${DriverAdvanvce.driver_empnumber} - " /> <c:out
																	value="${DriverAdvanvce.driver_firstname} " /> <c:out
																	value="${DriverAdvanvce.driver_Lastname}" /></td>
															<td class="fit ar"><c:out
																	value="${DriverAdvanvce.ADVANCE_DATE}" /></td>
															<td class="fit ar"><c:out
																	value="${DriverAdvanvce.ADVANCE_AMOUNT}" /></td>
															<td class="fit ar"><c:out
																	value="${DriverAdvanvce.ADVANCE_PAID_BY}" /></td>

															<td class="fit ar"><a
																href="removeDailyPenalty.in?TSID=${DriverAdvanvce.TRIPDAILYID}&AID=${DriverAdvanvce.DSAID}"
																data-toggle="tip" data-original-title="Click Remove"><font
																	color="red"><i class="fa fa-times"> Remove</i></font></a></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
										<div class="row">
											<div class="col-md-11">
												<div class="col-md-offset-6">
													<table class="table">
														<tbody>
															<tr data-object-id="" class="ng-scope">
																<td class="key"><h4>Total Penalty (or) WT:</h4></td>
																<td class="value"><h4>
																		<i class="fa fa-inr"></i> ${TripDaily.TOTAL_WT}
																	</h4></td>

															</tr>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div>
								</fieldset>
							</c:if>
							<form action="saveTripDailyPenalty" method="post">
								<input type="hidden" name="TRIPDAILYID"
									value="${TripDaily.TRIPDAILYID}">
								<div class="form-horizontal">

									<fieldset>
										<legend>Trip Daily Penalty Details</legend>
										<div class="row1">
											<label class="L-size control-label">Conductor/Driver
												Name <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<select class="form-text select2" name="DRIVER_ID">
													<option value="${TripDaily.TRIP_CONDUCTOR_ID}">${TripDaily.TRIP_CONDUCTOR_NAME}</option>
													<option value="${TripDaily.TRIP_DRIVER_ID}">${TripDaily.TRIP_DRIVER_NAME}</option>

													<option value="${TripDaily.TRIP_CLEANER_ID}">${TripDaily.TRIP_CLEANER_NAME}</option>
												</select>
											</div>
										</div>
										<div class="row1">
											<div class="L-size control-label">
												<label> Penalty Amount : <abbr title="required">*</abbr>
												</label>
											</div>
											<div class="I-size">
												<input type="number" class="form-text" id="advanceAmount"
													name="ADVANCE_AMOUNT" required="required"
													placeholder="eg: 1000"> <label class="error"
													id="errorNumber" style="display: none"></label>
											</div>
										</div>
										<div class="row1" id="modeOfPaymentCol">
											<label class="L-size control-label">Modes of Payment
												: <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<select class="form-text" name="ADVANCE_PAID_TYPE_ID"
													id="renPT_option" required="required">
													<option value="1">CASH</option>
													<option value="2">CREDIT</option>
													<option value="3">NEFT</option>
													<option value="4">RTGS</option>
													<option value="5">IMPS</option>
												</select>
											</div>
										</div>
										<div class="row1" id="cashReceiptNoCol">
											<label class="L-size control-label" id="target1"
												for="renPT_option">Enter </label>
											<div class="I-size">
												<input type="text" class="form-text"
													name="ADVANCE_PAID_NUMBER"
													onkeypress="return IsAlphaNumericPaynumber(event);"
													ondrop="return false;" maxlength="25"> <label
													class="error" id="errorPaynumber" style="display: none">
												</label>
											</div>
										</div>
										<div class="row1" id="grpfuelDate" class="form-group">
											<label class="L-size control-label" for="fuelDate">Penalty
												Date: <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="input-group input-append date" id="StartDate">
													<input type="text" class="form-text" name="ADVANCE_DATE"
														id="fuelDate" data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
												<span id="fuelDateIcon" class=""></span>
												<div id="fuelDateErrorMsg" class="text-danger"></div>
											</div>

										</div>
										<div class="row1" id="penaltyPaidByCol">
											<div class="L-size control-label">
												<label>Penalty Paid BY : </label>
											</div>
											<div class="I-size">
												<input type="text" class="form-text" readonly="readonly"
													name="ADVANCE_PAID_BY" required="required"
													ondrop="return false;" maxlength="150" value="${userName}">
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="issue_description">Penalty
												Remarks :</label>
											<div class="I-size">
												<textarea class="text optional form-text" id="initial_note"
													name="ADVANCE_REMARK" rows="3">
				                                            </textarea>
											</div>
										</div>

									</fieldset>
									<div class="box-footer h-padded">
										<fieldset class="form-actions">
											<input class="btn btn-success" data-disable-with="Saving..."
												onclick="this.style.visibility = 'hidden'" name="commit"
												name="commit" type="submit" value="Save Penalty"> <a
												class="btn btn-link"
												href="<c:url value="/showTripDaily?ID=${TripDaily.TRIPDAILYID}"/>">Cancel</a>
										</fieldset>
									</div>
								</div>
							</form>
						</sec:authorize>
					</div>
				</div>
			</div>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"/>"></script>
	<script>
		$(document).ready(
				function() {
					$("#renPT_option").on("change", function() {
						showoption()
					}), $("#renPT_option").change()
					function showoption() {
						var a = $("#renPT_option :selected"), b = a.text();
						"CASH" == b ? $("#target1").text(b + " Receipt NO : ")
								: $("#target1").text(b + " Transaction NO : ")
					}

					if(!(${showModeOfPaymentCol})) {
						$("#modeOfPaymentCol").addClass("hide");
					}
					if(!(${showCashReceiptCol})) {
						$("#cashReceiptNoCol").addClass("hide");
					}
					if(!(${showPenaltyDateCol})) {
						$("#grpfuelDate").addClass("hide");
					} else {
						$("#fuelDate").attr("required","required");
					}
					if(!(${showPenaltyPaidByCol})) {
						$("#penaltyPaidByCol").addClass("hide");
					}
					
				});
	</script>
</div>
