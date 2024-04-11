<%@ include file="taglib.jsp"%>
<style>
.closeAmount th, td {
	text-align: center;
}

.closeRouteAmount td {
	text-align: center;
	font-weight: bold;
	color: blue;
}

.closeGroupAmount td {
	text-align: center;
	font-weight: bold;
}

.actualkm {
	float: center;
}

.columnDaily {
	text-align: center;
}
</style>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					<sec:authorize access="hasAuthority('IMPORTANT_REPORT')">
						/ <a href="<c:url value="/ImportantReport"/>">Important Report</a>
					</sec:authorize>
					/ <a href="<c:url value="/TDR.in"/>">Trip Collection Report</a> / <span>Daily
						Trip Collection Report</span>
				</div>
				<div class="pull-right">
					<div style="display: inline-block;">
						<form action="DailyTripDailyCashbookReport" method="post">
							<input type="hidden" name="VEHICLEGROUP" required="required"
								value="${GROUP_NAME_ID}" /> <input type="hidden" name="TRIP_DATE"
								placeholder="dd-mm-yyyy" required="required"
								value="${YESTERDAY}" />
							<button class="btn btn-default" type="submit">
								<span class="fa fa-backward" aria-hidden="true"></span>
							</button>
						</form>
					</div>
					<div style="display: inline-block;">${TRIPCOL_DATE}</div>
					<div style="display: inline-block;">
						<form action="DailyTripDailyCashbookReport" method="post">
							<input type="hidden" name="TRIP_DATE" placeholder="dd-mm-yyyy"
								required="required" value="${TOMORROW}" /> <input type="hidden"
								name="VEHICLEGROUP" required="required" value="${GROUP_NAME_ID}" />
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
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('advanceTable', 'Daily Trip Collection Cashbook Report')">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
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

				<section class="invoice">
					<div class="row invoice-info">
						<div class="col-xs-12">
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

								<div id="sorttable-div" align="center" style="font-size: 10px"
									class="table-responsive ">
									<div class="row invoice-info">
										<table id="advanceTable" style="width: 95%">
											<tbody>
												<tr>
													<td>
														<table style="width: 100%">
															<tbody>
																<tr>
																	<td align="center"><span class="text-bold">
																			<c:out value="${GROUP_NAME}" /> Daily Trip
																			Collection &amp; CashBook Report <c:out
																				value=" - ${SEARCHDATE}" />
																	</span></td>
																</tr>
															</tbody>
														</table>
													</td>
												</tr>
												<tr>
													<td>
														<table style="width: 100%"
															class="table table-hover table-bordered table-striped">
															<thead>
																<tr>
																	<th>No</th>
																	<th>Bus Name / Route</th>
																	<th>Driver / Conductor</th>
																	<th>Total.K.M</th>
																	<th>Diesel</th>
																	<th>KMPL</th>
																	<th>PSNGR</th>
																	<th>PASS</th>
																	<th>RFID Pass</th>
																	<th>RFID AMOUNT</th>
																	<th>Collection</th>
																	<th>WT</th>
																	<th>NET Collection</th>
																	<th>EPK</th>
																	<th>Expense</th>
																	<th>OT</th>
																	<th>Balance</th>
																</tr>
															</thead>
															<tbody>
																<%
																	Integer hitsCount = 1;
																%>
																<c:if test="${!empty TDRoute}">

																	<c:forEach items="${TDRoute}" var="TDRoute">
																		<c:choose>
																			<c:when
																				test="${TDRoute.TRIP_CLOSE_STATUS == 'TOTAL:'}">
																				<tr data-object-id="" class="closeRouteAmount">

																					<td colspan="3"><c:out
																							value="${TDRoute.TRIP_CLOSE_STATUS}" /> <c:out
																							value="${TDRoute.TRIP_ROUTE_NAME}" /></td>
																					<td><c:out value="${TDRoute.TOTAL_USAGE_KM}" /></td>
																					<td><c:out value="${TDRoute.TOTAL_DIESEL}" /></td>

																					<td><c:out value="${TDRoute.TOTAL_DIESELKML}" /></td>
																					<td><c:out
																							value="${TDRoute.TOTAL_TOTALPASSNGER}" /></td>
																					<td><c:out
																							value="${TDRoute.TOTAL_PASS_PASSNGER}" /></td>
																					<td><c:out value="${TDRoute.TOTAL_RFIDPASS}" /></td>
																					<td><c:out
																							value="${TDRoute.TOTAL_RFID_AMOUNT}" /></td>
																					<td><c:out value="${TDRoute.TOTAL_COLLECTION}" /></td>
																					<td><c:out value="${TDRoute.TOTAL_WT}" /></td>
																					<td><c:out
																							value="${TDRoute.TOTAL_NET_COLLECTION}" /></td>
																					<td><c:out value="${TDRoute.TOTAL_EPK}" /></td>
																					<td><c:out value="${TDRoute.TOTAL_EXPENSE}" /></td>
																					<td><c:out value="${TDRoute.TOTAL_OVERTIME}" /></td>
																					<td><c:out value="${TDRoute.TOTAL_BALANCE}" /></td>

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
																					<td><a target="_blank"
																						href="showTripDaily.in?ID=${TDRoute.TRIPROUTEID}"><c:out
																								value="${TDRoute.TRIP_CLOSE_STATUS}" /></a><br>
																						<c:out value="${TDRoute.TRIP_ROUTE_NAME}" /></td>
																					<td><c:out value="${TDRoute.TRIP_DRIVER_NAME}" /><br>
																						<c:out value="${TDRoute.TRIP_CONDUCTOR_NAME}" /></td>
																					<td><c:out value="${TDRoute.TOTAL_USAGE_KM}" /></td>
																					<td><c:out value="${TDRoute.TOTAL_DIESEL}" /></td>

																					<td><c:out value="${TDRoute.TOTAL_DIESELKML}" /></td>
																					<td><c:out
																							value="${TDRoute.TOTAL_TOTALPASSNGER}" /></td>
																					<td><c:out
																							value="${TDRoute.TOTAL_PASS_PASSNGER}" /></td>

																					<td><c:out value="${TDRoute.TOTAL_RFIDPASS}" /></td>
																					<td><c:out
																							value="${TDRoute.TOTAL_RFID_AMOUNT}" /></td>

																					<td><c:out value="${TDRoute.TOTAL_COLLECTION}" /></td>
																					<td><c:out value="${TDRoute.TOTAL_WT}" /></td>
																					<td><c:out
																							value="${TDRoute.TOTAL_NET_COLLECTION}" /></td>
																					<td><c:out value="${TDRoute.TOTAL_EPK}" /></td>
																					<td><c:out value="${TDRoute.TOTAL_EXPENSE}" /></td>
																					<td><c:out value="${TDRoute.TOTAL_OVERTIME}" /></td>
																					<td><c:out value="${TDRoute.TOTAL_BALANCE}" /></td>

																				</tr>
																			</c:otherwise>
																		</c:choose>
																	</c:forEach>

																	<tr data-object-id="" class="closeGroupAmount">
																		<td colspan="3"><c:out
																				value="TOTAL  ${TDGroupCol.VEHICLE_GROUP} :" /></td>
																		<td><c:out value="${TDGroupCol.TOTAL_USAGE_KM}" /></td>
																		<td><c:out value="${TDGroupCol.TOTAL_DIESEL}" /></td>

																		<td><c:out
																				value="${TDGroupCol.TOTAL_DIESEL_MILAGE}" /></td>
																		<td><c:out
																				value="${TDGroupCol.TOTAL_TOTALPASSNGER}" /></td>
																		<td><c:out
																				value="${TDGroupCol.TOTAL_PASS_PASSNGER}" /></td>

																		<td><c:out value="${TDGroupCol.TOTAL_RFIDPASS}" /></td>
																		<td><c:out
																				value="${TDGroupCol.TOTAL_RFID_AMOUNT}" /></td>

																		<td><c:out value="${TDGroupCol.TOTAL_COLLECTION}" /></td>
																		<td><c:out value="${TDGroupCol.TOTAL_WT}" /></td>
																		<td><c:out
																				value="${TDGroupCol.TOTAL_NET_COLLECTION}" /></td>
																		<td><c:out value="${TDGroupCol.TOTAL_EPK}" /></td>
																		<td><c:out value="${TDGroupCol.TOTAL_EXPENSE}" /></td>
																		<td><c:out value="${TDGroupCol.TOTAL_OVERTIME}" /></td>
																		<td><c:out value="${TDGroupCol.TOTAL_BALANCE}" /></td>
																	</tr>
																</c:if>
															</tbody>
														</table>
													</td>
												</tr>
												<tr>
													<td align="center">
														<table style="width: 85%">
															<tbody>
																<tr>
																	<td align="center"><span class="text-bold">
																			CashBook Date Report </span></td>
																</tr>
																<tr>
																	<td align="center"><span class="text-bold">
																			${CASHBOOK_NAME}-${CASHBOOK_DATE} </span></td>
																</tr>
															</tbody>
														</table>
													</td>
												</tr>
												<tr>
													<td>
														<table style="width: 75%;"
															class="table table-hover table-bordered table-striped">
															<thead>
																<tr>
																	<th>No</th>
																	<th>ID</th>
																	<th>Reference</th>
																	<th>Payment</th>
																	<th>CREDIT</th>
																	<th>DEBIT</th>
																</tr>

															</thead>
															<tbody>
																<tr>
																	<th colspan="3">Cash Book : ${CashBookStatus}</th>
																	<th>Opening Balance :</th>
																	<th><c:out value=" ${OpenBalance}" /></th>
																	<th></th>
																</tr>
																<c:if test="${!empty cashbook}">
																	<%
																		Integer hitsCountCash = 1;
																	%>
																	<c:forEach items="${cashbook}" var="cashbook">
																		<tr>
																			<td>
																				<%
																					out.println(hitsCountCash);
																								hitsCountCash += 1;
																				%>
																			</td>
																			<td><a target="_blank"
																				href="<c:url value="/showCashBook.in?Id=${cashbook.CASHID}"/>"><c:out
																						value="C-${cashbook.CASH_NUMBER}" /></a></td>
																			<td><c:out
																					value="${cashbook.CASH_PAID_RECEIVED}" /></td>
																			<td><c:out
																					value="${cashbook.CASH_NATURE_PAYMENT}" /></td>
																			<c:choose>
																				<c:when
																					test="${cashbook.PAYMENT_TYPE_ID == 1}">
																					<td></td>
																					<td><c:out value=" ${cashbook.CASH_AMT_STR}" /></td>
																				</c:when>
																				<c:otherwise>
																					<td><c:out value=" ${cashbook.CASH_AMT_STR}" /></td>
																					<td></td>
																				</c:otherwise>
																			</c:choose>
																		</tr>
																	</c:forEach>
																	<tr class="workorder_repair_search_totals">
																		<th class="text-right" colspan="4"><b> Total
																				:</b></th>
																		<td><c:out value=" ${CreditTotal}" /></td>
																		<td><c:out value=" ${DebitTotal}" /></td>
																	</tr>
																</c:if>
																<tr class="workorder_repair_search_totals">
																	<td class="text-right" colspan="6"><b> <c:out
																				value=" ${BalanceWorld}" />
																	</b></td>
																</tr>
																<tr class="workorder_repair_search_totals">
																	<th class="text-right" colspan="4"><b> Balance
																			:</b></th>
																	<td colspan="2"><i class="fa fa-inr"></i> <c:out
																			value=" ${Balance}" /></td>
																</tr>
															</tbody>
														</table>
													</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</section>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script>
</div>