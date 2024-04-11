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
					/ <a href="<c:url value="/TCR.in"/>">Trip Collection Report</a>  /  <span>Date Range Trip Collection Report</span>
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
											<caption>
												Date Range Trip Collection Report
											</caption>

											<thead>
												<tr>
													<th class="fit">No</th>
													<th>Date</th>
													<th>Collection</th>
													<th>Expense</th>
													<th>Diesel</th>
													<th>Balance</th>
													<th>Singl</th>
													<th>per Single</th>
													<th>per Bus</th>
													<th>Run Bus</th>
													
												</tr>
											</thead>
											<tbody>
												<%
													Integer hitsCount = 1;
												%>
												<c:if test="${!empty TripDayCol}">

													<c:forEach items="${TripDayCol}" var="TripDayCol">
														<tr>
															<td class="fit">
																<%
																	out.println(hitsCount);
																				hitsCount += 1;
																%>
															</td>
															<td
																style="text-align: right; font-size: 15px; font-weight: bold;"><c:out
																	value="${TripDayCol.TRIP_OPEN_DATE}" /></td>
															<td
																style="text-align: right; font-size: 15px; font-weight: bold;"><c:out
																	value="${TripDayCol.TOTAL_COLLECTION}" /></td>
															<td
																style="text-align: right; font-size: 15px; font-weight: bold;"><c:out
																	value="${TripDayCol.TOTAL_EXPENSE}" /></td>
															<td
																style="text-align: right; font-size: 15px; font-weight: bold;"><c:out
																	value="${TripDayCol.TOTAL_DIESEL}" /></td>
															<td
																style="text-align: right; font-size: 15px; font-weight: bold;"><c:out
																	value="${TripDayCol.TOTAL_BALANCE}" /></td>
															<td
																style="text-align: center; font-size: 15px; font-weight: bold;"><c:out
																	value="${TripDayCol.TOTAL_RUN_SINGL}" /></td>
															<td
																style="text-align: center; font-size: 15px; font-weight: bold;"><c:out
																	value="${TripDayCol.PER_SINGL_COLLECTION}" /></td>
															<td
																style="text-align: center; font-size: 15px; font-weight: bold;"><c:out
																	value="${TripDayCol.EACH_BUS_COLLECTION}" /></td>
															<td
																style="text-align: center; font-size: 15px; font-weight: bold;"><c:out
																	value="${TripDayCol.TOTAL_BUS}" /></td>
																	
														</tr>
													</c:forEach>
													<tr>
														<td colspan="10"></td>
													</tr>
													<tr>
														<td colspan="2">TOTAL:</td>
														<td
															style="text-align: right; font-size: 15px; font-weight: bold;"><c:out
																value="${TripIncomeTotal}" /></td>
														<td
															style="text-align: right; font-size: 15px; font-weight: bold;"><c:out
																value="${TripExpenseTotal}" /></td>

														<td></td>
														<td
															style="text-align: right; font-size: 15px; font-weight: bold;"><c:out
																value="${TripBanlanceTotal}" /></td>
														<td colspan="4"></td>
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