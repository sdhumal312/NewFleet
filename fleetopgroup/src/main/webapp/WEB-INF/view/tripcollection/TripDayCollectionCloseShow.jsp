<%@ include file="taglib.jsp"%>
<style>
.closeAmount td {
	text-align: right;
}

.closeGroupAmount td {
	text-align: right;
	font-size: 15px;
	font-weight: bold;
}

.actualkm {
	width: 0.8%;
	float: left;
}
</style>
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
									href="<c:url value="/closeTripCol/1.in"/>">Close Trip</a> /
								Show Daily TripCollection
							</div>
							<div class="pull-right">
								<sec:authorize access="hasAuthority('ADD_TRIPSHEET')">
									<a class="btn btn-success btn-sm"
										href="<c:url value="/addTripCol.in"/>"> <span
										class="fa fa-plus"></span> Create TripCollection
									</a>
								</sec:authorize>
								<%-- <sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
									<a href="showTripSheetCol.in?id=${TripCol.TRIPCOLLID}"
										target="_blank" class="btn btn-default"><i
										class="fa fa-print"></i> Print</a>
								</sec:authorize> --%>
							</div>
						</div>
						<c:if test="${param.alreadyClosed eq true}">
							<div class="alert alert-success">
								<button class="close" data-dismiss="alert" type="button">x</button>
								This Trip Collection already Closed Successfully.
							</div>
						</c:if>
						<c:if test="${param.CloseSuccess eq true}">
							<div class="alert alert-success">
								<button class="close" data-dismiss="alert" type="button">x</button>
								This Trip Daily Collection Closed successfully .
							</div>
						</c:if>
						<div id="div_printTripsheet">
							<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET')">
								<spring:message code="message.unauth"></spring:message>
							</sec:authorize>
							<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
								<div class="row">
									<table class="table  table-striped">
										<thead>
											<tr>
												<th colspan="4">
													<h4 align="center">
														<c:out value="BUS OPERATORS - ${SEARCHDATE}" />

													</h4>
												</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td colspan="4">
													<table class="table table-bordered table-striped">
														<thead>
															<tr class="breadcrumb">
																<th class="fit">No</th>
																<th class="fit ar">Group</th>
																<th class="fit ar">Bus Name</th>
																<th class="fit ar">Driver/Conductor</th>
																<th class="fit ar">Collection</th>
																<th class="fit ar">Expense</th>
																<th class="fit ar">Diesel</th>
																<th class="fit ar">Balance</th>
																<th class="fit ar">Singl</th>
																<th class="fit ar">Run Bus</th>
																<th class="fit ar">AV Collection</th>
															</tr>
														</thead>
														<tbody>
															<%
																Integer hitsCount = 1;
															%>
															<c:if test="${!empty TripGroupCol}">

																<c:forEach items="${TripGroupCol}" var="TripGroupCol">
																	<c:choose>
																		<c:when
																			test="${TripGroupCol.TRIP_CLOSE_STATUS == 'TOTAL:'}">
																			<tr data-object-id="" class="closeGroupAmount">

																				<td colspan="2" class="fit ar"><c:out
																						value="${TripGroupCol.VEHICLE_GROUP}" /></td>
																				<td class="fit ar"><c:out
																						value="${TripGroupCol.TRIP_CLOSE_STATUS}" /><br>
																					<c:out value="${TripGroupCol.TRIP_ROUTE_NAME}" /></td>
																				<td class="fit ar"><c:out
																						value="${TripGroupCol.TRIP_DRIVER_NAME}" /><br>
																					<c:out value="${TripGroupCol.TRIP_CONDUCTOR_NAME}" /></td>
																				<td class="fit ar"><c:out
																						value="${TripGroupCol.TOTAL_COLLECTION}" /></td>
																				<td class="fit ar"><c:out
																						value="${TripGroupCol.TOTAL_EXPENSE}" /></td>
																				<td class="fit ar"><c:out
																						value="${TripGroupCol.TOTAL_DIESEL}" /></td>
																				<td class="fit ar"><c:out
																						value="${TripGroupCol.TOTAL_BALANCE}" /></td>
																				<td class="fit ar"><c:out
																						value="${TripGroupCol.TOTAL_SINGL}" /></td>
																				<td class="fit ar"><c:out
																						value="${TripGroupCol.TOTAL_BUS}" /></td>
																				<td class="fit ar"><c:out
																						value="${TripGroupCol.TOTAL_GROUP_COLLECTION}" /></td>
																			</tr>
																		</c:when>
																		<c:otherwise>
																			<tr data-object-id="" class="closeAmount">
																				<td class="fit">
																					<%
																						out.println(hitsCount);
																											hitsCount += 1;
																					%>
																				</td>
																				<td class="fit ar"><c:out
																						value="${TripGroupCol.VEHICLE_GROUP}" /></td>
																				<td class="fit ar"><a target="_blank"
																					href="<c:url value="/showTripCol.in?ID=${TripGroupCol.TRIPGROUPID}"/>"><c:out
																							value="${TripGroupCol.TRIP_CLOSE_STATUS}" /></a><br>
																					<c:out value="${TripGroupCol.TRIP_ROUTE_NAME}" /></td>
																				<td class="fit ar"><c:out
																						value="${TripGroupCol.TRIP_DRIVER_NAME}" /><br>
																					<c:out value="${TripGroupCol.TRIP_CONDUCTOR_NAME}" /></td>
																				<td class="fit ar"><c:out
																						value="${TripGroupCol.TOTAL_COLLECTION}" /></td>
																				<td class="fit ar"><c:out
																						value="${TripGroupCol.TOTAL_EXPENSE}" /></td>
																				<td class="fit ar"><c:out
																						value="${TripGroupCol.TOTAL_DIESEL}" /></td>
																				<td class="fit ar"><c:out
																						value="${TripGroupCol.TOTAL_BALANCE}" /></td>
																				<td class="fit ar"><c:out
																						value="${TripGroupCol.TOTAL_SINGL}" /></td>
																				<td class="fit ar"><c:out
																						value="${TripGroupCol.TOTAL_BUS}" /></td>
																				<td class="fit ar"><c:out
																						value="${TripGroupCol.TOTAL_GROUP_COLLECTION}" /></td>

																			</tr>
																		</c:otherwise>
																	</c:choose>
																	<c:if
																		test="${vehicleGroup.vGroup == TripGroupCol.VEHICLE_GROUP}">

																	</c:if>
																</c:forEach>
															</c:if>
														</tbody>
													</table>
												</td>
											</tr>
											<tr class="closeGroupAmount">
												<td>Total Collection:</td>
												<td><c:out value="${TripDayCol.TOTAL_COLLECTION}" /></td>
												<td colspan="2"></td>
											</tr>
											<tr class="closeGroupAmount">
												<td>Staff Salary + Curency:</td>
												<td><c:out value="${TripDayCol.STAFF_SALARY}" /></td>
												<td colspan="2"></td>
											</tr>
											<tr class="closeGroupAmount">
												<td>Ticket Roll (${TripDayCol.ROLL_NUMBER} X
													${TripDayCol.ROLL_PRICE}):</td>
												<td><c:out value="${TripDayCol.TICKET_ROLL}" /></td>
												<td colspan="2"></td>
											</tr>
											<tr class="closeGroupAmount">
												<td>Mechanic Maintenances</td>
												<td><c:out value="${TripDayCol.MECHANIC_MAINTANCE}" /></td>
												<td colspan="2"></td>
											</tr>
											<tr class="closeGroupAmount">
												<td>INC+F/D+E/S :</td>
												<td><c:out value="${TripDayCol.INSURENCE_MAINTANCE}" /></td>
												<td colspan="2"></td>
											</tr>
											<tr class="closeGroupAmount">
												<td>D/C Bonus :</td>
												<td><c:out value="${TripDayCol.DC_BONUS}" /></td>
												<td colspan="2"></td>
											</tr>
											<tr class="closeGroupAmount">
												<td>GRAND TOTAL :</td>
												<td><c:out value="${TripDayCol.GRAND_TOTAL}" /></td>
												<td colspan="2"></td>
											</tr>
											<tr class="closeGroupAmount">
												<td>NET TOTAL :</td>
												<td><c:out value="${TripDayCol.NET_TOTAL}" /></td>
												<td colspan="2"></td>
											</tr>
											<tr>
												<td colspan="4"></td>
											</tr>
											<tr class="closeGroupAmount">
												<td colspan="2">TOTAL SINGL :</td>
												<td><c:out value="${TripDayCol.TOTAL_RUN_SINGL}" /></td>
												<td></td>
											</tr>
											<tr class="closeGroupAmount">
												<td colspan="2">TOTAL RUNNING BUS :</td>
												<td></td>
												<td><c:out value="${TripDayCol.TOTAL_BUS}" /></td>
											</tr>
											<tr class="closeGroupAmount">
												<td colspan="2">EACH BUS COLLECTION:</td>
												<td><c:out value="${TripDayCol.EACH_BUS_COLLECTION}" /></td>
												<td></td>
											</tr>
											<tr class="closeGroupAmount">
												<td colspan="2">TOTAL SINGLE CUT :</td>
												<td></td>
												<td><c:out value="${TripDayCol.TOTAL_CUT_SINGL}" /></td>
											</tr>
											<tr class="closeGroupAmount">
												<td colspan="2">PER BUS SINGLE COLLECTION :</td>
												<td><c:out value="${TripDayCol.PER_SINGL_COLLECTION}" /></td>
												<td></td>
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


				</ul>
			</div>
		</div>
	</section>

	<!-- <script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/printTripsheet.js" />"></script> -->
</div>