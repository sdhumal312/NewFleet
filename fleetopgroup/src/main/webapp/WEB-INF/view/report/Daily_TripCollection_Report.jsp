<%@ include file="taglib.jsp"%>
<style>
.closeAmount td {
	text-align: right;
}

.actualkm {
	width: 0.8%;
	float: left;
}
</style>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/TCR.in"/>">Trip Collection Report</a> / <span>Daily
						Trip Collection Report</span>
				</div>
				<div class="pull-right">
					<div style="display: inline-block;">
						<form action="DailyTripCollectionReport" method="post">
							<input type="hidden"
								name="TRIP_DATE" placeholder="dd-mm-yyyy" required="required"
								value="${YESTERDAY}" />
							<button class="btn btn-default" type="submit">
								<span class="fa fa-backward" aria-hidden="true"></span>
							</button>
						</form>
					</div>
					<div style="display: inline-block;">${TRIPCOL_DATE}</div>
					<div style="display: inline-block;">
						<form action="DailyTripCollectionReport" method="post">
							<input type="hidden"
								name="TRIP_DATE" placeholder="dd-mm-yyyy" required="required"
								value="${TOMORROW}" />
							<button class="btn btn-default" type="submit">
								<span class="fa fa-forward" aria-hidden="true"></span>
							</button>
						</form>
					</div>
					<div style="display: inline-block; width: 100px"></div>
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>

					<a href="<c:url value="/Report"/>">Cancel</a>

				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
			<div id="div_print">

				<div id="div_print">

					<section class="invoice">
						<div class="row invoice-info">
							<div class="col-sm-12 col-md-12 col-xs-12"
								style="padding-right: 80px;">
								<div class="table-responsive">
									<table class="table table-hover table-bordered table-striped">
										<tbody>
											<tr>
												<td><c:choose>
														<c:when test="${company.company_id != null}">
															<img
																src="${pageContext.request.contextPath}/downloadlogo/${company.company_id_encode}.in"
																class="img-rounded " alt="Company Logo" width="280"
																height="40" />

														</c:when>
														<c:otherwise>
															<i class="fa fa-globe"></i>
															<c:out value="${company.company_name}" />
														</c:otherwise>
													</c:choose></td>
												<td>Print By: ${company.firstName}_${company.lastName}</td>
											</tr>
											<tr>
												<td colspan="2">Branch :<c:out
														value=" ${company.branch_name}  , " /> Department :<c:out
														value=" ${company.department_name}" />
												</td>
											</tr>
										</tbody>
									</table>
									<div class="row invoice-info">
										<table class="table table-hover table-bordered table-striped">
											<caption>
												Daily Trip Collection Report
												<c:out value=" BUS OPERATORS - ${SEARCHDATE}" />
											</caption>

											<thead>
												<tr>
													<th class="fit">No</th>
													<th>Group</th>
													<th>Bus Name</th>
													<th>Collection</th>
													<th>Expense</th>
													<th>Diesel</th>
													<th>Balance</th>
													<th>Singl</th>
													<th>Run Bus</th>
													<th>AV Collection</th>
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
																<tr>

																	<td colspan="2"
																		style="text-align: right; font-size: 15px; font-weight: bold;"><c:out
																			value="${TripGroupCol.VEHICLE_GROUP}" /></td>
																	<td
																		style="text-align: right; font-size: 15px; font-weight: bold;"><c:out
																			value="${TripGroupCol.TRIP_CLOSE_STATUS}" /></td>
																	<td
																		style="text-align: right; font-size: 15px; font-weight: bold;"><c:out
																			value="${TripGroupCol.TOTAL_COLLECTION}" /></td>
																	<td
																		style="text-align: right; font-size: 15px; font-weight: bold;"><c:out
																			value="${TripGroupCol.TOTAL_EXPENSE}" /></td>
																	<td
																		style="text-align: right; font-size: 15px; font-weight: bold;"><c:out
																			value="${TripGroupCol.TOTAL_DIESEL}" /></td>
																	<td
																		style="text-align: right; font-size: 15px; font-weight: bold;"><c:out
																			value="${TripGroupCol.TOTAL_BALANCE}" /></td>
																	<td
																		style="text-align: center; font-size: 15px; font-weight: bold;"><c:out
																			value="${TripGroupCol.TOTAL_SINGL}" /></td>
																	<td
																		style="text-align: center; font-size: 15px; font-weight: bold;"><c:out
																			value="${TripGroupCol.TOTAL_BUS}" /></td>
																	<td
																		style="text-align: Left; font-size: 15px; font-weight: bold;"><c:out
																			value="${TripGroupCol.TOTAL_GROUP_COLLECTION}" /></td>
																</tr>
															</c:when>
															<c:otherwise>
																<tr data-object-id="">
																	<td class="fit">
																		<%
																			out.println(hitsCount);
																								hitsCount += 1;
																		%>
																	</td>
																	<td><c:out value="${TripGroupCol.VEHICLE_GROUP}" /></td>
																	<td><c:out
																			value="${TripGroupCol.TRIP_CLOSE_STATUS}" /></td>
																	<td style="text-align: right;"><c:out
																			value="${TripGroupCol.TOTAL_COLLECTION}" /></td>
																	<td style="text-align: right;"><c:out
																			value="${TripGroupCol.TOTAL_EXPENSE}" /></td>
																	<td style="text-align: right;"><c:out
																			value="${TripGroupCol.TOTAL_DIESEL}" /></td>
																	<td style="text-align: right;"><c:out
																			value="${TripGroupCol.TOTAL_BALANCE}" /></td>
																	<td style="text-align: center;"><c:out
																			value="${TripGroupCol.TOTAL_SINGL}" /></td>
																	<td><c:out value="${TripGroupCol.TOTAL_BUS}" /></td>
																	<td><c:out
																			value="${TripGroupCol.TOTAL_GROUP_COLLECTION}" /></td>

																</tr>
															</c:otherwise>
														</c:choose>
														<c:if
															test="${vehicleGroup.vGroup == TripGroupCol.VEHICLE_GROUP}">

														</c:if>
													</c:forEach>
													<tr>
														<td colspan="10"></td>
													</tr>
													<tr>
														<td colspan="6">Total Collection:</td>
														<td
															style="text-align: right; font-size: 15px; font-weight: bold;"><c:out
																value="${TripDayCol.TOTAL_COLLECTION}" /></td>
														<td colspan="3"></td>
													</tr>
													<tr>
														<td colspan="6">Staff Salary + Curency:</td>
														<td
															style="text-align: right; font-size: 15px; font-weight: bold;"><c:out
																value="${TripDayCol.STAFF_SALARY}" /></td>
														<td colspan="3"></td>
													</tr>
													<tr>
														<td colspan="6">Ticket Roll
															(${TripDayCol.ROLL_NUMBER} X ${TripDayCol.ROLL_PRICE}):</td>
														<td
															style="text-align: right; font-size: 15px; font-weight: bold;"><c:out
																value="${TripDayCol.TICKET_ROLL}" /></td>
														<td colspan="3"></td>
													</tr>
													<tr>
														<td colspan="6">Mechanic Maintenances</td>
														<td
															style="text-align: right; font-size: 15px; font-weight: bold;"><c:out
																value="${TripDayCol.MECHANIC_MAINTANCE}" /></td>
														<td colspan="3"></td>
													</tr>
													<tr>
														<td colspan="6">INC+F/D+E/S :</td>
														<td
															style="text-align: right; font-size: 15px; font-weight: bold;"><c:out
																value="${TripDayCol.INSURENCE_MAINTANCE}" /></td>
														<td colspan="3"></td>
													</tr>
													<tr>
														<td colspan="6">D/C Bonus :</td>
														<td
															style="text-align: right; font-size: 15px; font-weight: bold;"><c:out
																value="${TripDayCol.DC_BONUS}" /></td>
														<td colspan="3"></td>
													</tr>
													<tr>
														<td colspan="6">GRAND TOTAL :</td>
														<td
															style="text-align: right; font-size: 15px; font-weight: bold;"><c:out
																value="${TripDayCol.GRAND_TOTAL}" /></td>
														<td colspan="3"></td>
													</tr>
													<tr class="closeGroupAmount">
														<td colspan="6">NET TOTAL :</td>
														<td
															style="text-align: right; font-size: 15px; font-weight: bold;"><c:out
																value="${TripDayCol.NET_TOTAL}" /></td>
														<td colspan="3"></td>
													</tr>
													<tr>
														<td colspan="10"></td>
													</tr>
													<tr>
														<td colspan="7">TOTAL SINGL :</td>
														<td
															style="text-align: right; font-size: 15px; font-weight: bold;"><c:out
																value="${TripDayCol.TOTAL_RUN_SINGL}" /></td>
														<td colspan="2"></td>
													</tr>
													<tr>
														<td colspan="7">TOTAL RUNNING BUS :</td>
														<td></td>
														<td
															style="text-align: right; font-size: 15px; font-weight: bold;"><c:out
																value="${TripDayCol.TOTAL_BUS}" /></td>
														<td></td>
													</tr>
													<tr>
														<td colspan="7">EACH BUS COLLECTION:</td>
														<td
															style="text-align: right; font-size: 15px; font-weight: bold;"><c:out
																value="${TripDayCol.EACH_BUS_COLLECTION}" /></td>
														<td colspan="2"></td>

													</tr>
													<tr>
														<td colspan="7">TOTAL SINGLE CUT :</td>
														<td></td>
														<td
															style="text-align: right; font-size: 15px; font-weight: bold;"><c:out
																value="${TripDayCol.TOTAL_CUT_SINGL}" /></td>
														<td></td>
													</tr>
													<tr>
														<td colspan="7">PER BUS SINGLE COLLECTION :</td>
														<td
															style="text-align: right; font-size: 15px; font-weight: bold;"><c:out
																value="${TripDayCol.PER_SINGL_COLLECTION}" /></td>
														<td colspan="2"></td>
													</tr>
												</c:if>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</section>
				</div>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script>
</div>