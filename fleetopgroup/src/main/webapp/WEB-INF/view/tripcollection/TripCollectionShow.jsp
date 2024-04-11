<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="row">
					<div class="pull-left">
						<a href="<c:url value="/open"/>"><spring:message
								code="label.master.home" /></a> / <a
							href="<c:url value="/newTripCol.in"/>">Trip Collection</a> / <a
							href="<c:url value="/manageTripCol/1.in"/>">Manage Trip</a> / <a
							href="<c:url value="/closeTripCol/1.in"/>">Close Trip</a> / Show
						TripCollection
					</div>
					<div class="col-md-off-5">
						<div class="col-md-3">
							<form action="searchTripColShow.in" method="post">
								<div class="input-group">
									<span class="input-group-addon"> <span
										aria-hidden="true">TS-</span></span> <input class="form-text"
										id="tripStutes" name="tripStutes" type="number" min="1"
										required="required" placeholder="TS-ID" maxlength="20">
									<span class="input-group-btn">
										<button type="submit" name="search" id="search-btn"
											class="btn btn-success btn-sm">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
							</form>
						</div>
						<sec:authorize access="hasAuthority('ADD_TRIPSHEET')">
							<a class="btn btn-success btn-sm"
								href="<c:url value="/addTripCol.in"/>"> <span
								class="fa fa-plus"></span> Create TripCollection
							</a>
						</sec:authorize>
						<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
							<a href="showTripColPrint.in?ID=${TripCol.TRIPCOLLID}"
								target="_blank" class="btn btn-default btn-sm"><i
								class="fa fa-print"></i> Print</a>
						</sec:authorize>
					</div>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<div class="col-md-offset-1 col-md-9 col-sm-8 col-xs-12">
				<c:if test="${param.updateAdvance eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Trip Advance Entries Updated Successfully.
					</div>
				</c:if>
				<c:if test="${param.RO eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Trip Collection Entries ReOpen Successfully.
					</div>
				</c:if>
				<c:if test="${param.notUpdateAdvance eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Trip Advance Entries was Already created.
					</div>
				</c:if>
				<c:if test="${param.updateExpense eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Trip Expense Entries Updated Successfully.
					</div>
				</c:if>
				<c:if test="${param.alreadyExpense eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Trip Expense Entries was Already created.
					</div>
				</c:if>
				<c:if test="${param.updateIncome eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Trip Income Entries Updated Successfully.
					</div>
				</c:if>
				<c:if test="${param.alreadyIncome eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Trip Income Entries was Already created.
					</div>
				</c:if>
				<div class="box">
					<div class="boxinside">
						<div id="div_printTripsheet">
							<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET')">
								<spring:message code="message.unauth"></spring:message>
							</sec:authorize>
							<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
								<div class="row">
									<div class="pull-left">
										<h4>
											Trip Number : <a
												href="showTripCol.in?ID=${TripCol.TRIPCOLLID}"> TS-
												${TripCol.TRIPCOLLNUMBER}</a>
										</h4>
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
												<th><a data-toggle="tip" data-original-title="Driver 2"><c:out
															value="${TripCol.TRIP_CONDUCTOR_NAME}" /></a></th>

												<th>Diesel :</th>
												<th><a data-toggle="tip" data-original-title="Diesel"><c:out
															value="${TripCol.TRIP_DIESEL_LITER}" /></a></th>
											</tr>
											<tr>
												<th>Opening KM:</th>
												<th><a data-toggle="tip"
													data-original-title="Opening KM"><c:out
															value="${TripCol.TRIP_OPEN_KM}" /></a></th>
												<th>Closing KM:</th>
												<th><a data-toggle="tip"
													data-original-title="closing KM"> <c:out
															value="${TripCol.TRIP_CLOSE_KM}" /></a></th>
											</tr>
											<tr>
												<th>Created By:</th>
												<th><c:out value="${TripCol.CREATEDBY}" /></th>
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
												<td colspan="4"><c:if
														test="${TripCol.TRIP_CLOSE_STATUS_ID == 2}">
														<table class="table table-bordered table-striped">
															<thead>
																<tr class="breadcrumb">

																	<th class="fit ar">Closed By</th>
																	<th class="fit ar">(Income - Expense)</th>
																	<th class="fit ar">Closed Date</th>
																	<th class="fit ar">Status</th>
																</tr>
															</thead>
															<tbody>
																<tr data-object-id="" class="ng-scope">

																	<td class="fit ar"><c:out
																			value="${TripCol.LASTMODIFIEDBY}" /></td>
																	<td class="fit ar"><c:out
																			value="${TripCol.TOTAL_BALANCE}" /></td>
																	<td class="fit ar"><c:out
																			value="${TripCol.lASTUPDATED}" /></td>
																	<td class="fit ar"><c:choose>

																			<c:when
																				test="${TripCol.TRIP_CLOSE_STATUS_ID == 2}">
																				<span class="label label-pill label-success"><c:out
																						value="${TripCol.TRIP_CLOSE_STATUS}" /></span>
																			</c:when>
																			<c:otherwise>
																				<span class="label label-pill label-warning"><c:out
																						value="${TripCol.TRIP_CLOSE_STATUS}" /></span>
																			</c:otherwise>
																		</c:choose></td>
																</tr>
																<tr data-object-id="" class="ng-scope">
																	<td colspan="2">
																		<h4 style="text-align: right;">Total Payment :</h4>
																	</td>
																	<td>
																		<h4>
																			<i class="fa fa-inr"></i> ${TripCol.TOTAL_BALANCE}
																		</h4>
																	</td>
																</tr>
															</tbody>
														</table>
													</c:if></td>
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
							<li><sec:authorize
									access="hasAuthority('ADD_EXPENSE_TRIPSHEET')">
									<a class="btn btn-warning btn-sm"
										href="addColExpense.in?ID=${TripCol.TRIPCOLLID}"> <span
										class="fa fa-plus"></span> Expense
									</a>
								</sec:authorize></li>
							<li class="divider"></li>
							<li><sec:authorize
									access="hasAuthority('ADD_INCOME_TRIPSHEET')">
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
							<li><sec:authorize access="hasAuthority('EDIT_TRIPSHEET')">
									<a class="btn btn-info btn-sm"
										href="reopenTripCol.in?ID=${TripCol.TRIPCOLLID}"
										class="confirmation"
										onclick="return confirm('Are you sure? Re-open sheet ')">
										<span class="fa fa-rocket"> </span> Re-Open
									</a>
								</sec:authorize></li>
						</c:when>
					</c:choose>
				</ul>
			</div>
		</div>
		<div class="row">
			<small class="text-muted"><b>Created by :</b> <c:out
					value="${TripCol.CREATEDBY}" /></small> | <small class="text-muted"><b>Created
					date: </b> <c:out value="${TripCol.CREATED}" /></small> | <small
				class="text-muted"><b>Last updated by :</b> <c:out
					value="${TripCol.LASTMODIFIEDBY}" /></small> | <small class="text-muted"><b>Last
					updated date:</b> <c:out value="${TripCol.lASTUPDATED}" /></small>
		</div>
	</section>
	<!-- <script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/printTripsheet.js" />"></script> -->
</div>