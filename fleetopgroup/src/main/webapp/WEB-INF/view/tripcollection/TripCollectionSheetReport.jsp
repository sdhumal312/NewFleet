<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/jquery.dataTables.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/buttons.dataTables.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/newTripCol.in"/>">TripCollection</a> / <a
						href="<c:url value="/TripColReport.in"/>">TripCollection
						Report</a>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/newTripCol.in"/>">Cancel</a>
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
							<table id="TripSheetTable"
								class="table table-bordered table-striped">
								<thead>
									<tr>
										<th>ID</th>
										<th>Vehicle</th>
										<th>Group</th>
										<th>Route</th>
										<th>Trip Date</th>
										<th>Singl</th>
										<th class="fit ar">Income</th>
										<th class="fit ar">Expense</th>
										<th class="fit ar">Balance</th>
										<th>Status</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${!empty TripCol}">
										<c:forEach items="${TripCol}" var="TripCol">
											<tr data-object-id="" class="ng-scope">
												<td><a target="_blank"
													href="<c:url value="/showTripCol.in?ID=${TripCol.TRIPCOLLID}"/>"><c:out
															value="TS-${TripCol.TRIPCOLLNUMBER}" /></a></td>
												<td><a target="_blank"
													href="<c:url value="/showTripCol.in?ID=${TripCol.TRIPCOLLID}"/>"
													data-toggle="tip" data-original-title="Click Details">
														<c:out value="${TripCol.VEHICLE_REGISTRATION}" />

												</a></td>
												<td><c:out value="${TripCol.VEHICLE_GROUP}" /></td>
												<td><c:out value="${TripCol.TRIP_ROUTE_NAME}" /></td>

												<td><c:out value="${TripCol.TRIP_OPEN_DATE}" /></td>

												<td><c:out value="${TripCol.TRIP_SINGL}" /></td>

												<td class="fir ar"><c:out
														value="${TripCol.TOTAL_INCOME}" /></td>

												<td class="fir ar"><c:out
														value="${TripCol.TOTAL_EXPENSE}" /></td>

												<td class="fir ar"><c:out
														value="${TripCol.TOTAL_BALANCE}" /></td>
												<td><c:choose>

														<c:when test="${TripCol.TRIP_CLOSE_STATUS_ID == 1}">
															<span class="label label-pill label-warning"><c:out
																	value="${TripCol.TRIP_CLOSE_STATUS}" /></span>
														</c:when>
														<c:otherwise>
															<span class="label label-pill label-success"><c:out
																	value="${TripCol.TRIP_CLOSE_STATUS}" /></span>
														</c:otherwise>
													</c:choose></td>

											</tr>
										</c:forEach>
									</c:if>
								</tbody>
								<tfoot>
									<tr>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td><b>Total Amount:</b></td>
										<td class="fir ar"><b> ${TripIncomeTotal} </b></td>
										<td class="fir ar"><b> ${TripExpenseTotal} </b></td>

										<td class="fir ar"><b> ${TripBanlanceTotal} </b></td>

										<td></td>

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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/Triplanguage.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/dataTables.buttons.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.print.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.flash.min.js" />"></script>
</div>