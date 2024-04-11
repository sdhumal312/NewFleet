<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/TCR.in"/>">Trip Collection Report</a> / <span>Driver
						Jama Collection Report</span>
				</div>
				<div class="pull-right">
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
											<caption>Total Driver Balance Report</caption>

											<thead>
												<tr>
													<th style="font-size: 10px; width: 3%;">No</th>
													<th style="font-size: 10px; width: 6%;">Date</th>
													<th style="font-size: 10px; width: 14%;">Group</th>
													<th style="font-size: 10px; width: 13%;">Route</th>
													<th style="font-size: 10px; width: 6%;">TripID</th>
													<th style="font-size: 10px; width: 10%;">Bus Name</th>
													<th style="font-size: 10px; width: 15%;">Driver Name</th>
													<th style="font-size: 10px; width: 10%; text-align: center;">Driver Jama</th>
													<th style="font-size: 10px; width: 10%; text-align: center;">Driver Advance</th>
												</tr>
											</thead>
											<tbody>
												<%
													Integer hitsCount = 1;
												%>
												<c:if test="${!empty TripCol}">

													<c:forEach items="${TripCol}" var="TripCol">
														<tr data-object-id="">
															<td style="font-size: 10px; width: 3%;">
																<%
																	out.println(hitsCount);
																				hitsCount += 1;
																%>
															</td>
															<td style="font-size: 10px; width: 6%;"><c:out value="${TripCol.TRIP_OPEN_DATE}" /></td>
															<td style="font-size: 10px; width: 14%;"><c:out value="${TripCol.VEHICLE_GROUP}" /></td>
															<td style="font-size: 10px; width: 13%;"><c:out value="${TripCol.TRIP_ROUTE_NAME}" /></td>
															<td style="font-size: 10px; width: 6%;"><c:out value="TS-${TripCol.TRIPCOLLNUMBER}" /></td>
															<td style="font-size: 10px; width: 10%;"><c:out value="${TripCol.VEHICLE_REGISTRATION}" /></td>
															<td style="font-size: 10px; width: 15%;"><c:out value="${TripCol.TRIP_DRIVER_NAME}" /></td>
															<td style="font-size: 10px; width: 10%; text-align: center;"><c:out
																	value="${TripCol.TRIP_DRIVER_JAMA}" /></td>
															<td style="font-size: 10px; width: 10%; text-align: center;"><c:out
																	value="${TripCol.TOTAL_BALANCE}" /></td>
														</tr>

													</c:forEach>
													<tr>
														<td colspan="9"></td>
													</tr>
													<tr>
														<td colspan="7"
															style="text-align: right; font-size: 15px; font-weight: bold;">TOTAL:</td>
														<td
															style="text-align: center; font-size: 15px; font-weight: bold;"><c:out
																value="${DriverJAMATotal}" /></td>
														<td
															style="text-align: center; font-size: 15px; font-weight: bold;"><c:out
																value="${AdvanceTotal}" /></td>

													</tr>
													<tr>
														<td colspan="6"
															style="text-align: right; font-size: 15px; font-weight: bold;">Balance
															: <c:out value="${DriverBalance}" />
														</td>
														<td></td>
														<td></td>
														<td></td>
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