<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/TCR.in"/>">Trip Collection Report</a>  /  <span>Conductor Jama Collection Report</span>
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
											<caption>Conductor Jama Collection Report</caption>

											<thead>
												<tr>
													<th class="fit">No</th>
													<th>Date</th>
													<th>Group</th>
													<th>Bus Name</th>
													<th>Collection</th>
													<th>Diesel</th>
													<th>Conductor Name</th>
													<th>Conductor Jama</th>
												</tr>
											</thead>
											<tbody>
												<%
													Integer hitsCount = 1;
												%>
												<c:if test="${!empty TripCol}">

													<c:forEach items="${TripCol}" var="TripCol">
														<tr data-object-id="">
															<td class="fit">
																<%
																	out.println(hitsCount);
																				hitsCount += 1;
																%>
															</td>
															<td><c:out value="${TripCol.TRIP_OPEN_DATE}" /></td>
															<td><c:out value="${TripCol.VEHICLE_GROUP}" /></td>
															<td><c:out value="${TripCol.VEHICLE_REGISTRATION}" /></td>
															<td style="text-align: right;"><c:out
																	value="${TripCol.TOTAL_INCOME}" /></td>
															<td style="text-align: right;"><c:out
																	value="${TripCol.TRIP_DIESEL_LITER}" /></td>
															
															<td><c:out value="${TripCol.TRIP_CONDUCTOR_NAME}" /></td>
															<td style="text-align: right;"><c:out
																	value="${TripCol.TRIP_CONDUCTOR_JAMA}" /></td>
														</tr>
													</c:forEach>
													<tr>
														<td colspan="10"></td>
													</tr>
													<tr>
														<td colspan="4" style="text-align: right; font-size: 15px; font-weight: bold;">TOTAL:</td>
														<td
															style="text-align: right; font-size: 15px; font-weight: bold;"><c:out
																value="${TripIncomeTotal}" /></td>
														<td></td>
														<td></td>
														<td
															style="text-align: right; font-size: 15px; font-weight: bold;"><c:out
																value="${ConductorJAMATotal}" /></td>
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