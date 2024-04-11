<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/jquery.dataTables.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/buttons.dataTables.css"/>">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/newTripDaily.in"/>">TripCollection</a> / <a
						href="<c:url value="/TripDailyReport.in"/>">TripCollection
						Report</a>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/newTripDaily.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
				<div class="main-body">
					<h4>TripSheets Report</h4>
					<div class="box">
						<div class="box-body">
							<table id="TripDailyTable" class="display nowrap"  class="table table-bordered table-striped">
								<thead>
									<tr>
										<th class="fit">No</th>
										<th class="fit ar">Bus NO</th>
										<th class="fit ar">DRIVER NAME/COND NAME</th>
										<th class="fit ar">STA.KM-END.KM</th>
										<th class="fit ar">Total.KM</th>
										<th class="fit ar">DIESEL</th>
										<!-- <th class="fit ar">KMPL</th> -->
										<th class="fit ar">PSNGR</th>
										<th class="fit ar">RFID</th>
										<th class="fit ar">COLLECTION</th>
										<th class="fit ar">EXPENSE</th>
										<th class="fit ar">OT</th>
										<th class="fit ar">BALANCE</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${!empty TripDaily}">
										<%
											Integer hitsCount = 1;
										%>
										<c:forEach items="${TripDaily}" var="TripDaily">
											<tr data-object-id="" class="ng-scope">
												<td class="fit">
													<%
														out.println(hitsCount);
																	hitsCount += 1;
													%>
												</td>
												<td><a target="_blank"
													href="<c:url value="/showTripDaily.in?ID=${TripDaily.TRIPDAILYID}"/>"><c:out
															value="${TripDaily.VEHICLE_REGISTRATION}" /></a><br>
												<c:out value="${TripDaily.TRIP_ROUTE_NAME}" /></td>

												<td class="fit ar"><c:out
														value="${TripDaily.TRIP_DRIVER_NAME}" /><br>
												<c:out value="${TripDaily.TRIP_CONDUCTOR_NAME}" /></td>
												<td class="fit ar"><c:out
														value="${TripDaily.TRIP_OPEN_KM}  - " /><c:out
														value="${TripDaily.TRIP_CLOSE_KM}" /></td>
												<td class="fit ar"><c:out
														value="${TripDaily.TRIP_USAGE_KM}" /></td>
												<td class="fit ar"><c:out
														value="${TripDaily.TRIP_DIESEL}" /></td>
												<%-- <td class="fit ar"><c:out
														value="${TripDaily.TRIP_DIESELKMPL}" /></td> --%>
												<td class="fit ar"><c:out
														value="${TripDaily.TRIP_TOTALPASSNGER}" /></td>
												<td class="fit ar"><c:out
														value="${TripDaily.TRIP_RFIDPASS}" /></td>
												<td class="fit ar"><c:out
														value="${TripDaily.TOTAL_INCOME}" /></td>
												<td class="fit ar"><c:out
														value="${TripDaily.TOTAL_EXPENSE}" /></td>
												<td class="fit ar"><c:out
														value="${TripDaily.TRIP_OVERTIME}" /></td>
												<td class="fit ar"><c:out
														value="${TripDaily.TOTAL_BALANCE}" /></td>
											</tr>

										</c:forEach>
									</c:if>
								</tbody>
								<tfoot>
									<tr>
										<td colspan="4" style="text-align: right;"><c:out value="Total :" /></td>
										<td class="fit ar"><c:out value="${TotalUsageKM}" /></td>
										<td class="fit ar"><c:out value="${TotalDiesel}" /></td>
										<td class="fit ar"><c:out value="${TotalPassenger}" /></td>
										<td class="fit ar"><c:out value="${TotalRFID}" /></td>
										<td class="fit ar"><c:out value="${TotalCollection}" /></td>
										<td class="fit ar"><c:out value="${TotalExpense}" /></td>
										<td class="fit ar"><c:out value="${TotalOT}" /></td>
										<td class="fit ar"><c:out value="${TotalBalance}" /></td>
									</tr>

								</tfoot>
							</table>
						</div>
					</div>
				</div>
			</sec:authorize>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/dataTables.buttons.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.print.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.flash.min.js"/>"></script>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/Triplanguage.js"/>"></script>

</div>