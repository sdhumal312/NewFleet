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
						href="<c:url value="/newTripSheetEntries.in"/>">TripSheets</a> / <a
						href="<c:url value="/TripSheetReport.in"/>">TripSheets Report</a>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/newTripSheetEntries.in"/>">Cancel</a>
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
							<table id="ReportTripSheetTable"
								class="table table-bordered table-striped">
								<thead>
									<tr>
										<th class="fit">TS-Id</th>
										<th>Vehicle</th>
										<th>Group</th>
										<th>Route</th>
										<th>Trip Date</th>
										<th class="fit ar">Advance</th>
										<th class="fit ar">Expense</th>
										<th class="fit ar">Income</th>
										<th class="fit ar">A/C Balance</th>
										<th class="fit ar">Booking-Ref:</th>
										<th class="fir ar">Status</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${!empty TripSheet}">
										<c:forEach items="${TripSheet}" var="TripSheet">

											<tr data-object-id="" class="ng-scope">
												<td class="fit"><a target="_blank" 
													href="showTripSheet.in?tripSheetID=${TripSheet.tripSheetID}"><c:out
															value="TS-${TripSheet.tripSheetNumber}" /></a></td>
												<td><a target="_blank" 
													href="showTripSheet.in?tripSheetID=${TripSheet.tripSheetID}"
													data-toggle="tip" data-original-title="Click Details">
														<c:out value="${TripSheet.vehicle_registration}" />

												</a></td>
												<td><c:out value="${TripSheet.vehicle_Group}" /></td>
												<td><c:out value="${TripSheet.routeName}" /></td>

												<td><c:out value="${TripSheet.tripOpenDate}" /></td>
												<td class="fir ar"><i class="fa fa-inr"> </i> <c:out
														value="${TripSheet.tripTotalAdvance}" /></td>
												<td class="fir ar"><i class="fa fa-inr"> </i> <c:out
														value="${TripSheet.tripTotalexpense}" /></td>
												<td class="fir ar"><i class="fa fa-inr"> </i> <c:out
														value="${TripSheet.tripTotalincome}" /></td>
												<td class="fir ar"><i class="fa fa-inr"> </i> <c:out
														value="${TripSheet.closeACCTripAmount}" /></td>

												<td class="fir ar"><c:out
														value="${TripSheet.tripBookref}" /></td>

												<td class="fir ar"><c:choose>

														<c:when test="${TripSheet.tripStutesId == 1}">
															<span class="label label-pill label-warning"><c:out
																	value="${TripSheet.tripStutes}" /></span>
														</c:when>
														<c:when test="${TripSheet.tripStutesId == 2}">
															<span class="label label-pill label-danger"><c:out
																	value="${TripSheet.tripStutes}" /></span>
														</c:when>
														<c:when test="${TripSheet.tripStutesId == 3}">
															<span class="label label-pill label-success"><c:out
																	value="${TripSheet.tripStutes}" /></span>
														</c:when>
														<c:otherwise>
															<span class="label label-pill label-info"><c:out
																	value="${TripSheet.tripStutes}" /></span>
														</c:otherwise>
													</c:choose></td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
								<tfoot>
									<tr>
										<th class="fit ar" colspan="5">Total Amount:</th>
										<th class="fir ar"><b><i class="fa fa-inr"></i>
												${TripAdvanceTotal} </b></th>
										<th class="fir ar"><b><i class="fa fa-inr"></i>
												${TripExpenseTotal} </b></th>
										<th class="fir ar"><b><i class="fa fa-inr"></i>
												${TripIncomeTotal} </b></th>
										<th class="fir ar"><b><i class="fa fa-inr"></i>
												${TripBanlanceTotal} </b></th>
										<th class="fit " colspan="2"></th>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/Triplanguage.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/dataTables.buttons.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.print.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.flash.min.js"/>"></script>
</div>