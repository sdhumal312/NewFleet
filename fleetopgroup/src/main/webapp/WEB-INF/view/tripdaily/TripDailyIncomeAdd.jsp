<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
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
									TripCollection</a> / Add Income
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
							<c:if test="${!empty TripColIncome}">
								<fieldset>
									<div class="row">
										<div class="">
											<table class="table table-bordered table-striped">
												<thead>
													<tr class="breadcrumb">
														<th class="fit">No</th>
														<th class="fit ar">Income Name</th>
														<th class="fit ar">Collected By</th>
														<th class="fit ar">Reference</th>
														<th class="fit ar">Amount</th>
														<th></th>
														<th class="fit ar">Actions</th>
													</tr>
												</thead>
												<tbody>
													<%
														Integer hitsCount = 1;
													%>
													<c:forEach items="${TripColIncome}" var="TripColIncome">

														<tr data-object-id="" class="ng-scope">
															<td class="fit">
																<%
																	out.println(hitsCount);
																				hitsCount += 1;
																%>
															</td>
															<td class="fit ar"><c:out
																	value="${TripColIncome.incomeName}" /></td>

															<td class="fit ar"><c:out
																	value="${TripColIncome.incomeCollectedBy}" /></td>

															<td class="fit ar"><c:out
																	value="${TripColIncome.incomeRefence}" /></td>
															<td class="fit ar"><c:out
																	value="${TripColIncome.incomeAmount}" /></td>
															<td><a
																id="incomeAmount${TripColIncome.tripincomeID}"
																data-remote="true"
																href="javascript:toggle2Tax('addincomeAmount${TripColIncome.tripincomeID}','incomeAmount${TripColIncome.tripincomeID}');">
																	<font color="green"><i class="fa fa-plus-circle"></i></font>
															</a>
																<div class="popup-edit hide-on-escape"
																	id="addincomeAmount${TripColIncome.tripincomeID}">
																	<form accept-charset="UTF-8"
																		action="UpdateIncomeAddAmount.in" method="post"
																		novalidate="novalidate">
																		<div class="row1">
																			<label class="control-label">Amount Cost</label>
																			<div class="input float optional work_order_tax_cost">
																				<input name="TRIPDAILYID" type="hidden"
																					required="required"
																					value="${TripDaily.TRIPDAILYID}"> <input
																					name="tripincomeID" type="hidden"
																					required="required"
																					value="${TripColIncome.tripincomeID}"> <input
																					class="form-text" required="required"
																					name="incomeAmount" type="number"
																					value="${TripColIncome.incomeAmount}">
																			</div>
																		</div>
																		<div class="row1">
																			<input class="btn btn-success" name="commit"
																				type="submit" value="Apply">
																		</div>
																	</form>

																</div></td>
															<td class="fit ar"><a
																href="removeDailyIncome.in?ID=${TripColIncome.tripincomeID}"
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
																<td class="key"><h4>Total Income:</h4></td>
																<td class="value"><h4>
																		<i class="fa fa-inr"></i> ${incomeTotal}
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
							<form action="updateDailyIncome.in" method="post">
								<input type="hidden" name="TRIPDAILYID"
									value="${TripDaily.TRIPDAILYID}">
								<div class="form-horizontal">

									<fieldset>
										<legend>Income Details</legend>
										<div class="row1">
											<div class="col-md-4">
												<select class="select2" name="incomeName" style="width: 100%;"
													id="Income" required="required">
												</select>
											</div>
											<div class="col-md-2">
												<input type="number" class="form-text" min="0" name="Amount" id = "Amount"
													placeholder="Amount" required="required">
											</div>
											<div class="col-md-2">
												<input type="text" class="form-text" name="incomeRefence"
													placeholder="Reference" value="X0">
											</div>
											<div class="input_fields_wrap">
												<button class="add_field_button btn btn-success">
													<i class="fa fa-plus"></i>
												</button>
											</div>
										</div>
										<br>
									</fieldset>
									<div class="box-footer h-padded">
										<fieldset class="form-actions">
											<input class="btn btn-success"
												onclick="return validateIncome();" name="commit"
												type="submit" value="Save Trip Income"> <a
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripDaily.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"/>"></script>
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripValidate.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripCollectionIncome.js"/>"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".select2").select2();
		});
		function toggle2Tax(e,n){var t=document.getElementById(e),r=document.getElementById(n);"block"==t.style.display?(t.style.display="none",r.innerHTML='<font color="green"><i class="fa fa-plus-circle"></i></font>'):(t.style.display="block",r.innerHTML="Cancel")}
	</script>
	<script type="text/javascript">
	$(function() {
		$('[data-toggle="popover"]').popover()
		
	})
</script>


</div>