<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content">
		<div class="row">
			<div class="col-md-offset-1 col-md-9 col-sm-8 col-xs-12">
				<div class="box">
					<div class="boxinside">
						<div class="box-header">
							<div class="pull-left">
								<a href="<c:url value="/open"/>"><spring:message
										code="label.master.home" /></a> / <a
									href="<c:url value="/newTripDaily.in"/>">Trip Collection</a> /
								<a href="<c:url value="/manageTripDaily/1.in"/>">Manage Trip</a>
								/ <a href="<c:url value="/closeTripDaily/1.in"/>">Close Trip</a>
								/ <a
									href="<c:url value="/showTripDaily?ID=${TripDaily.TRIPDAILYID}"/>">Show
									TripCollection</a> / Close Trip
							</div>
							<div class="pull-right">
								<sec:authorize access="hasAuthority('ADD_TRIPSHEET')">
									<a class="btn btn-success btn-sm"
										href="<c:url value="/addTripDaily.in"/>"> <span
										class="fa fa-plus"></span> Create TripCollection
									</a>
								</sec:authorize>
							</div>
						</div>
						<div id="div_printTripsheet">
							<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET')">
								<spring:message code="message.unauth"></spring:message>
							</sec:authorize>
							<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
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
									<table class="table  table-striped">
										<thead>
											<tr>
												<th colspan="4">
													<h4 align="center">
														<c:out value="${TripDaily.TRIP_ROUTE_NAME}" />
														<br>
														<c:out value="${TripDaily.VEHICLE_GROUP}" />

													</h4>
												</th>
											</tr>
											<tr>
												<th>Vehicle :</th>
												<th><c:choose>
														<c:when test="${TripDaily.VEHICLEID == 0}">
															<a style="color: #000000;" href="#" data-toggle="tip"
																data-original-title="Click Vehicle Info"> <c:out
																	value="${TripDaily.VEHICLE_REGISTRATION}" />
															</a>
														</c:when>
														<c:otherwise>
															<a style="color: #000000;"
																href="showVehicle.in?VID=${TripDaily.VEHICLEID}"
																data-toggle="tip"
																data-original-title="Click Vehicle Info"> <c:out
																	value="${TripDaily.VEHICLE_REGISTRATION}" />
															</a>
														</c:otherwise>
													</c:choose></th>
												<th>Group :</th>
												<th><c:out value="${TripDaily.VEHICLE_GROUP}" /></th>
											</tr>
											<tr>
												<th>Date of Journey :</th>
												<th><a data-toggle="tip"
													data-original-title="Trip Open Date"> <c:out
															value="${TripDaily.TRIP_OPEN_DATE}" /></a></th>
												<th>Total Passenger:</th>
												<th><a data-toggle="tip"
													data-original-title="total passenger"> <c:out
															value="${TripDaily.TRIP_TOTALPASSNGER}" />
												</a></th>
											</tr>
											<tr>
												<th>Driver:</th>
												<th><a data-toggle="tip" data-original-title="Driver">
														<c:out value="${TripDaily.TRIP_DRIVER_NAME}" />
												</a></th>
												<th>RFID Pass :</th>
												<th><a data-toggle="tip"
													data-original-title="RFID pass"><c:out
															value="${TripDaily.TRIP_RFIDPASS}" /></a></th>

											</tr>
											<tr>
												<th>Conductor :</th>
												<th><a data-toggle="tip" data-original-title="Driver 2"><c:out
															value="${TripDaily.TRIP_CONDUCTOR_NAME}" /></a></th>

												<th>Cleaner :</th>
												<th><a data-toggle="tip" data-original-title="Cleaner"><c:out
															value="${TripDaily.TRIP_CLEANER_NAME}" /></a></th>
											</tr>
											<tr>
												<th>Diesel :</th>
												<th><a data-toggle="tip" data-original-title="Diesel"><c:out
															value="${TripDaily.TRIP_DIESEL}" /></a></th>

												<th>Opening KM:</th>
												<th><a data-toggle="tip"
													data-original-title="Opening KM"><c:out
															value="${TripDaily.TRIP_OPEN_KM}" /></a></th>
											</tr>
											<tr>
												<th>Diesel KMPL:</th>
												<th><a data-toggle="tip"
													data-original-title="Diesel kmpl"><c:out
															value="${TripDaily.TRIP_DIESELKMPL}" /></a></th>
												<th>Closing KM:</th>
												<th><a data-toggle="tip"
													data-original-title="closing KM"> <c:out
															value="${TripDaily.TRIP_CLOSE_KM}" /></a></th>
											</tr>
											<tr>
												<th>OverTime :</th>
												<th><a data-toggle="tip"
													data-original-title="over time"><c:out
															value="${TripDaily.TRIP_OVERTIME}" /></a></th>
												<th>Total KM:</th>
												<th><a data-toggle="tip" data-original-title="usage KM">
														<c:out value="${TripDaily.TRIP_USAGE_KM}" />
												</a></th>
											</tr>
											
											<c:if test="${companyconfig.showChaloDetails}"> 
											<tr>
												<th>Chalo KM :</th>
												<th><a data-toggle="tip"
													data-original-title="over time"><c:out
															value="${TripDaily.CHALO_KM}" /></a></th>
												<th>Chalo Amount:</th>
												<th><a data-toggle="tip" data-original-title="usage KM">
														<c:out value="${TripDaily.CHALO_AMOUNT}" />
												</a></th>
											</tr>
											</c:if>	
										</thead>
										<tbody>
											<tr>
												<td colspan="2">
													<table class="table table-bordered">
														<thead>
															<tr class="breadcrumb">
																<th class="fit ar">Income Name</th>
																<th class="fit ar">Amount</th>
															</tr>
														</thead>
														<tbody>
															<c:if test="${!empty TripColIncome}">

																<c:forEach items="${TripColIncome}" var="TripColIncome">

																	<tr data-object-id="" class="ng-scope">
																		<td class="fit ar"><c:out
																				value="${TripColIncome.incomeName}" /></td>
																		<td class="fit ar"><c:out
																				value="${TripColIncome.incomeAmount}" /></td>
																	</tr>
																</c:forEach>
																<tr>
																	<td>
																		<h4 style="text-align: right;">Total Income :</h4>
																	</td>
																	<td>
																		<h4>
																			<i class="fa fa-inr"></i> ${TripDaily.TOTAL_INCOME}
																		</h4>
																	</td>
																</tr>
															</c:if>
														</tbody>
													</table>
												</td>
												<td colspan="2">
													<table class="table table-bordered">
														<thead>
															<tr class="breadcrumb">
																<th class="fit ar">Expense Name</th>
																<th class="fit ar">Amount</th>

															</tr>
														</thead>
														<tbody>
															<c:if test="${!empty TripColExpense}">

																<c:forEach items="${TripColExpense}"
																	var="TripColExpense">

																	<tr data-object-id="" class="ng-scope">
																		<td class="fit ar"><c:out
																				value="${TripColExpense.expenseName}" /></td>

																		<td class="fit ar"><c:out
																				value="${TripColExpense.expenseAmount}" /></td>

																	</tr>
																</c:forEach>
																<tr>
																	<td>
																		<h4 style="text-align: right;">Total Expense :</h4>
																	</td>
																	<td>
																		<h4>
																			<i class="fa fa-inr"></i> ${TripDaily.TOTAL_EXPENSE}
																		</h4>
																	</td>
																</tr>
															</c:if>
														</tbody>
													</table>
												</td>
											</tr>
											<tr>
												<td colspan="4"><c:if test="${!empty DriverAdvanvce}">

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
																			</tr>
																		</thead>
																		<tbody>
																			<%
																				Integer hitsCount = 1;
																			%>
																			<c:forEach items="${DriverAdvanvce}"
																				var="DriverAdvanvce">
																				<tr data-object-id="" class="ng-scope">
																					<td class="fit">
																						<%
																							out.println(hitsCount);
																										hitsCount += 1;
																						%>
																					</td>
																					<td class="fit ar"><c:out
																							value="${DriverAdvanvce.driver_empnumber} - " />
																						<c:out value="${DriverAdvanvce.driver_firstname} " />
																						<c:out value="${DriverAdvanvce.driver_Lastname}" /></td>
																					<td class="fit ar"><c:out
																							value="${DriverAdvanvce.ADVANCE_DATE}" /></td>
																					<td class="fit ar"><c:out
																							value="${DriverAdvanvce.ADVANCE_AMOUNT}" /></td>
																					<td class="fit ar"><c:out
																							value="${DriverAdvanvce.ADVANCE_PAID_BY}" /></td>

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
																						<td class="key"><h4>Total Penalty (or)
																								WT:</h4></td>
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
													</c:if></td>
											</tr>

											<tr>
												<td colspan="4"><form action="CloseTripDaily.in"
														method="post">
														<input type="hidden" name="TRIPDAILYID"
															value="${TripDaily.TRIPDAILYID}">
														<div class="form-horizontal">
															<table class="table">
																<tbody>
																	<tr data-object-id="" class="ng-scope">
																		<c:if test="${!configuration.showDieselAmount}">
																			<td class="key"><h4>
																				Balance = (Income - WT) - (Expense + OverTime) : 
																			</h4></td>
																		</c:if>	
																		<c:if test="${configuration.showDieselAmount}">
																			<td class="key"><h4>
																				Balance = (Total Income - Total Expenses) : 
																			</h4></td>
																		</c:if>
																		<td class="value"><h4><i
																					class="fa fa-inr"></i> ${incomeBalanceAmount}</h4></td>
																	</tr>
																</tbody>
															</table>
															<fieldset>
																<legend>Office Use</legend>
																<div class="row1">
																	<label class="L-size control-label">No of Round Trip:<abbr
																		title="required">*</abbr></label>
																<div class="I-size">
																	<input readonly="readonly" value="${TripDaily.noOfRoundTrip}" class="required form-text type="number" name="noOfRoundTrip" id ="noOfRoundTrip"/>
																</div>
																</div>
																<div class="row1">
																	<label class="L-size control-label">Closing KM:<abbr
																		title="required">*</abbr></label>
																	<input type="hidden" id="routeApproxKM" value="${tripRoute}"/>
																	<input type="hidden" id="tripOpenKM" value="${TripDaily.TRIP_OPEN_KM}"/>	
																	<div class="I-size">
																		<input class="string required form-text" id="TRIP_CLOSE_KM"
																			name="TRIP_CLOSE_KM" maxlength="10" type="text"
																			required="required" value="${CurrentOdometer}"
																			placeholder="OPEN-KM =${TripDaily.TRIP_OPEN_KM}"
																			onkeypress="return IsClosingKM(event);"
																			ondrop="return false;"> 
																			<input class="form-text" type="text" style="background-color: orange;display: none;" id="range" disabled="disabled"/>
																			<label class="error" id="errorClosingKM">  </label>
																	</div>
																</div>
																<div class="row1">
																	<label class="L-size control-label">Balance Amount :<abbr
																		title="required">*</abbr></label>
																	<div class="I-size">
																		<input class="string required form-text"
																			name="TOTAL_BALANCE" maxlength="50" type="text"
																			required="required" readonly="readonly"
																			value="${incomeBalance}"
																			onkeypress="return IsAdvanceAmount(event);"
																			ondrop="return false;"> <label class="error"
																			id="errorAdvanceAmount" style="display: none">
																		</label>

																	</div>
																</div>
																<div class="row1">
																	<label class="L-size control-label"> Closed By
																		:<abbr title="required">*</abbr>
																	</label>
																	<div class="I-size">
																		<input class="string required form-text"
																			name="LASTMODIFIEDBY" maxlength="50" type="text"
																			required="required" value="${CloseBy}"
																			readonly="readonly">
																	</div>
																</div>
																<div class="box-footer h-padded">
																	<fieldset class="col-md-offset-3">
																		<input class="btn btn-success"
																			onclick="return validateCloseOdometer();"
																			name="commit" type="submit" id="submit"
																			value="Close Trip Collection"> <a
																			class="btn btn-link"
																			href="<c:url value="/showTripDaily?ID=${TripDaily.TRIPDAILYID}"/>">Cancel</a>
																	</fieldset>
																</div>
															</fieldset>
														</div>
													</form></td>
											</tr>
										</tbody>
									</table>
								</div>
							</sec:authorize>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-1 col-sm-2 col-xs-12">
				<ul class="nav nav-list">

					<li class="active"><a class="btn btn-default btn-sm"
						href="newTripDaily.in">Overview</a></li>

					<c:choose>
						<c:when test="${TripDaily.TRIP_CLOSE_STATUS == 'OPEN'}">

							<li class="divider"></li>
							<li><sec:authorize
									access="hasAuthority('ADD_EXPENSE_TRIPSHEET')">
									<a class="btn btn-warning btn-sm"
										href="addDailyExpense.in?ID=${TripDaily.TRIPDAILYID}"> <span
										class="fa fa-plus"></span> Expense
									</a>
									<a class="btn btn-default btn-sm"
										href="addTripDailyFuel.in?ID=${TripDaily.TRIPDAILYID}"> <span
										class="fa fa-plus"></span> Fuel Entries
									</a>
								</sec:authorize></li>
							<li class="divider"></li>
							<li><sec:authorize
									access="hasAuthority('ADD_INCOME_TRIPSHEET')">
									<a class="btn btn-info btn-sm"
										href="addDailyIncome.in?ID=${TripDaily.TRIPDAILYID}"> <span
										class="fa fa-plus"></span> Income
									</a>
								</sec:authorize></li>
							<li class="divider"></li>
							<li><sec:authorize access="hasAuthority('CLOSE_TRIPSHEET')">
									<a class="btn btn-danger btn-sm"
										href="addCloseTripDaily.in?ID=${TripDaily.TRIPDAILYID}"
										class="confirmation"
										onclick="validatePassengerEntry(${TripDaily.TRIP_TOTALPASSNGER})"> <span
										class="fa fa-times"></span> Close Trip
									</a>
								</sec:authorize></li>

							<li class="divider"></li>
							<li><sec:authorize access="hasAuthority('EDIT_TRIPSHEET')">
									<a class="btn btn-default btn-sm"
										href="editTripDaily.in?ID=${TripDaily.TRIPDAILYID}"
										class="confirmation"
										onclick="return confirm('Are you sure? Edit sheet ')">
										<span class="fa fa-rocket"> </span> Edit
									</a>
								</sec:authorize></li>
							<li><sec:authorize access="hasAuthority('DELETE_TRIPSHEET')">
									<a class="btn btn-default btn-sm"
										href="deleteTripDaily.in?ID=${TripDaily.TRIPDAILYID}"
										class="confirmation"
										onclick="return confirm('Are you sure? Delete ')"> <span
										class="fa fa-trash"></span> Delete
									</a>
								</sec:authorize></li>
						</c:when>
						<c:when test="${TripDaily.TRIP_CLOSE_STATUS == 'CLOSED'}">
							<li class="divider"></li>
						</c:when>
					</c:choose>
				</ul>
			</div>
		</div>
	</section>
	<script type="text/javascript">
			function validatePassengerEntry(totalPassenger){
					if(totalPassenger == undefined || totalPassenger == null || totalPassenger <= 0){
						showMessage('info', 'Please Enter Passenger Details Before Closing Trip Sheet!');
						return false;
					}
					return confirm('Are you sure? Close ');
			}	
	</script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripDailyClose.js"/>"></script>
</div>