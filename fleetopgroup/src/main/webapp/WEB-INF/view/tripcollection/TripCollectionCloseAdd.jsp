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
									href="<c:url value="/newTripCol.in"/>">Trip Collection</a> / <a
									href="<c:url value="/manageTripCol/1.in"/>">Manage Trip</a> / <a
									href="<c:url value="/closeTripCol/1.in"/>">Close Trip</a> / <a
									href="<c:url value="/showTripCol?ID=${TripCol.TRIPCOLLID}"/>">Show
									TripCollection</a> / Close Trip
							</div>
							<div class="pull-right">
								<sec:authorize access="hasAuthority('ADD_TRIPSHEET')">
									<a class="btn btn-success btn-sm"
										href="<c:url value="/addTripCol.in"/>"> <span
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
										<h4>Trip Number : TS- ${TripCol.TRIPCOLLNUMBER}</h4>
									</div>
									<div class="pull-right">
										<h5>Created Date : ${TripCol.CREATED}</h5>
									</div>
								</div>
								<div class="row">
									<table class="table  table-striped">
										<thead>
											<tr>
												<th colspan="4">
													<h4 align="center">
														<c:out value="${TripCol.VEHICLE_GROUP}" />

													</h4>
												</th>
											</tr>
											<tr>
												<th>Vehicle :</th>
												<th><c:choose>
														<c:when test="${TripCol.VID == 0}">
															<a style="color: #000000;" href="#" data-toggle="tip"
																data-original-title="Click Vehicle Info"> <c:out
																	value="${TripCol.VEHICLE_REGISTRATION}" />
															</a>
														</c:when>
														<c:otherwise>
															<a style="color: #000000;"
																href="showVehicle.in?VID=${TripCol.VID}"
																data-toggle="tip"
																data-original-title="Click Vehicle Info"> <c:out
																	value="${TripCol.VEHICLE_REGISTRATION}" />
															</a>
														</c:otherwise>
													</c:choose></th>
												<th colspan="2">
													<h5>${TripCol.TRIP_ROUTE_NAME}</h5>
												</th>
											</tr>
											<tr>
												<th>Date of Journey :</th>
												<th><a data-toggle="tip"
													data-original-title="Trip Open Date"> <c:out
															value="${TripCol.TRIP_OPEN_DATE}" /></a></th>
												<th>Singl:</th>
												<th><a data-toggle="tip" data-original-title="Singl">
														<c:out value="${TripCol.TRIP_SINGL}" />
												</a></th>
											</tr>
											<tr>
												<th>Driver:</th>
												<th><a data-toggle="tip" data-original-title="Driver">
														<c:out value="${TripCol.TRIP_DRIVER_NAME}" />
												</a></th>
												<th>Cleaner :</th>
												<th><a data-toggle="tip" data-original-title="Cleaner"><c:out
															value="${TripCol.TRIP_CLEANER_NAME}" /></a></th>
											</tr>
											<tr>
												<th>Conductor :</th>
												<th><a data-toggle="tip"
													data-original-title="Conductor"><c:out
															value="${TripCol.TRIP_CONDUCTOR_NAME}" /></a></th>

												<th>Opening KM:</th>
												<th><a data-toggle="tip"
													data-original-title="Opening KM"><c:out
															value="${TripCol.TRIP_OPEN_KM}" /></a></th>
											</tr>
											<tr>
												<th>Closing KM:</th>
												<th><a data-toggle="tip"
													data-original-title="closing KM"> <c:out
															value="${TripCol.TRIP_CLOSE_KM}" /></a></th>
												<th>Usage KM:</th>
												<th><a data-toggle="tip" data-original-title="usage KM">
														<c:out value="${TripCol.TRIP_USAGE_KM}" />
												</a></th>
											</tr>
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
																			<i class="fa fa-inr"></i> ${TripCol.TOTAL_INCOME}
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
																			<i class="fa fa-inr"></i> ${TripCol.TOTAL_EXPENSE}
																		</h4>
																	</td>
																</tr>
															</c:if>
														</tbody>
													</table>
												</td>
											</tr>

											<tr>
												<td colspan="4"><form action="CloseTripCollection.in"
														method="post">
														<input type="hidden" name="TRIPCOLLID"
															value="${TripCol.TRIPCOLLID}">
														<div class="form-horizontal">
															<table class="table">
																<tbody>
																	<tr data-object-id="" class="ng-scope">
																		<td class="key"><h4>
																				Balance ( Income - Expense ) = <i class="fa fa-inr"></i>
																			</h4></td>
																		<td class="value"><h4>${incomeBalance}</h4></td>
																	</tr>
																</tbody>
															</table>
															<fieldset>
																<legend>office Use</legend>

																<div class="row1">
																	<label class="L-size control-label">Closing KM:<abbr
																		title="required">*</abbr></label>
																	<div class="I-size">
																		<input class="string required form-text"
																			name="TRIP_CLOSE_KM" maxlength="10" type="text"
																			required="required"
																			placeholder="OPEN-KM =${TripCol.TRIP_OPEN_KM}"
																			onkeypress="return IsClosingKM(event);"
																			ondrop="return false;"> <label class="error"
																			id="errorClosingKM" style="display: none"> </label>
																	</div>
																</div>
																<div class="row1">
																	<label class="L-size control-label">Amount :<abbr
																		title="required">*</abbr></label>
																	<div class="I-size">
																		<input class="string required form-text"
																			name="TOTAL_BALANCE" maxlength="50" type="text"
																			required="required" readonly="readonly"
																			value="${TripCol.TOTAL_INCOME - TripCol.TOTAL_EXPENSE}"
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
																		<input type = "hidden" name="LASTMODIFIEDBYID" value="${CloseById}"/>	
																	</div>
																</div>
																<div class="box-footer h-padded">
																	<fieldset class="col-md-offset-3">
																		<input class="btn btn-success"
																			onclick="this.style.visibility = 'hidden'"
																			name="commit" type="submit"
																			value="Close Trip Collection"> <a
																			class="btn btn-link"
																			href="<c:url value="/showTripCol?ID=${TripCol.TRIPCOLLID}"/>">Cancel</a>
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
						href="newTripCol.in">Overview</a></li>

					<c:choose>
						<c:when test="${TripCol.TRIP_CLOSE_STATUS_ID == 1}">

							<li class="divider"></li>
							<li><sec:authorize access="hasAuthority('ADD_EXPENSE_TRIPSHEET')">
									<a class="btn btn-warning btn-sm"
										href="addColExpense.in?ID=${TripCol.TRIPCOLLID}"> <span
										class="fa fa-plus"></span> Expense
									</a>
								</sec:authorize></li>
							<li class="divider"></li>
							<li><sec:authorize access="hasAuthority('ADD_INCOME_TRIPSHEET')">
									<a class="btn btn-info btn-sm"
										href="addColIncome.in?ID=${TripCol.TRIPCOLLID}"> <span
										class="fa fa-plus"></span> Income
									</a>
								</sec:authorize></li>
							<li class="divider"></li>
							<li><sec:authorize access="hasAuthority('CLOSE_TRIPSHEET')">
									<a class="btn btn-danger btn-sm"
										href="addCloseTripCol.in?ID=${TripCol.TRIPCOLLID}"
										class="confirmation"
										onclick="return confirm('Are you sure? Close ')"> <span
										class="fa fa-times"></span> Close Trip
									</a>
								</sec:authorize></li>

							<li class="divider"></li>
							<li><sec:authorize access="hasAuthority('EDIT_TRIPSHEET')">
									<a class="btn btn-default btn-sm"
										href="editTripCol.in?ID=${TripCol.TRIPCOLLID}"
										class="confirmation"
										onclick="return confirm('Are you sure? Dispatch sheet ')">
										<span class="fa fa-rocket"> </span> Edit
									</a>
								</sec:authorize></li>
							<li><sec:authorize access="hasAuthority('DELETE_TRIPSHEET')">
									<a class="btn btn-default btn-sm"
										href="deleteTripCol.in?ID=${TripCol.TRIPCOLLID}"
										class="confirmation"
										onclick="return confirm('Are you sure? Delete ')"> <span
										class="fa fa-trash"></span> Delete
									</a>
								</sec:authorize></li>
						</c:when>
						<c:when test="${TripCol.TRIP_CLOSE_STATUS_ID == 2}">
							<li class="divider"></li>
						</c:when>
					</c:choose>
				</ul>
			</div>
		</div>
	</section>
	<!-- <script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/printTripsheet.js" />"></script> -->
</div>