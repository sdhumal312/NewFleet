<%@page import="org.fleetopgroup.persistence.dto.CashBookDto"%>
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

					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('advanceTable', 'Daily Trip Collection Report')">
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
																				Collection Month &amp; CashBook Report <c:out
																					value=" - ${SEARCHDATE}" />
																		</span></td>
																	</tr>
																</tbody>
															</table>
														</td>
													</tr>
													<tr>
														<td>
															<table id="advanceTable" style="width: 95%"
																class="table table-hover table-bordered table-striped">
																<thead>
																	<tr>
																		<th>No</th>
																		<th>Date</th>
																		<th>Depot</th>
																		<th>Total.K.M</th>
																		<th>Diesel</th>
																		<th>KMPL</th>
																		<th>PSNGR</th>
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
																	<c:if test="${!empty TDGroupCol}">

																		<c:forEach items="${TDGroupCol}" var="TDGroupCol">

																			<tr data-object-id="" class="closeAmount">
																				<td class="fit">
																					<%
																						out.println(hitsCount);
																									hitsCount += 1;
																					%>
																				</td>
																				<td><c:out value="${TDGroupCol.TRIP_OPEN_DATE}" /></td>
																				<td><c:out value="${TDGroupCol.VEHICLE_GROUP}" /></td>
																				<td><c:out value="${TDGroupCol.TOTAL_USAGE_KM}" /></td>
																				<td><c:out value="${TDGroupCol.TOTAL_DIESEL}" /></td>
																				<td><c:out
																						value="${TDGroupCol.TOTAL_DIESEL_MILAGE}" /></td>

																				<td><c:out
																						value="${TDGroupCol.TOTAL_TOTALPASSNGER}" /></td>
																				<td><c:out value="${TDGroupCol.TOTAL_RFIDPASS}" /></td>
																				<td><c:out
																						value="${TDGroupCol.TOTAL_RFID_AMOUNT}" /></td>
																				<td><c:out
																						value="${TDGroupCol.TOTAL_COLLECTION}" /></td>
																				<td><c:out value="${TDGroupCol.TOTAL_WT}" /></td>
																				<td><c:out
																						value="${TDGroupCol.TOTAL_NET_COLLECTION}" /></td>
																				<td><c:out value="${TDGroupCol.TOTAL_EPK}" /></td>
																				<td><c:out value="${TDGroupCol.TOTAL_EXPENSE}" /></td>
																				<td><c:out value="${TDGroupCol.TOTAL_OVERTIME}" /></td>
																				<td><c:out value="${TDGroupCol.TOTAL_BALANCE}" /></td>

																			</tr>

																		</c:forEach>

																		<tr data-object-id="" class="closeGroupAmount">
																			<td colspan="3"><c:out
																					value="Total ${GROUP_NAME} :" /></td>
																			<td><c:out value="${TotalUsageKM}" /></td>
																			<td><c:out value="${TotalDiesel}" /></td>
																			<td><c:out value="${TotalDieselKMPL}" /></td>
																			<td><c:out value="${TotalPassenger}" /></td>
																			<td><c:out value="${TotalRFID}" /></td>
																			<td><c:out value="${TotalRFIDAmount}" /></td>
																			<td><c:out value="${TotalCollection}" /></td>
																			<td><c:out value="${TotalWT}" /></td>
																			<td><c:out value="${TotalNetCollection}" /></td>
																			<td><c:out value="${TotalEPK}" /></td>
																			<td><c:out value="${TotalExpense}" /></td>
																			<td><c:out value="${TotalOT}" /></td>
																			<td><c:out value="${TotalBalance}" /></td>
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
																		<th>Payment</th>
																		 <th>Date</th>
																		<th>CREDIT</th>
																		<th>DEBIT</th>
																	</tr>

																</thead>
																<tbody>
																	
																	<c:if test="${!empty cashbookMap}">
																			<%
																				Integer hitsCountCash = 1;
																				
																			%>
																		<c:forEach items="${cashbookMap}" var="cashbook">
																			
																		<tr>
																							<td>
																								<%
																									out.println(hitsCountCash);
																									hitsCountCash += 1;
																								%>
																							</td>
																	<%-- <td><a target="_blank"
																			href="<c:url value="/showCashBook.in?Id=${cashbook.CASHID}"/>"><c:out
																		value="C-${cashbook.CASH_NUMBER}" /></a></td> --%>
																							 
																							<c:choose>
																								<c:when test="${cashbook.value.multiplePayment != true}">
																									<td><c:out
																									value="${cashbook.value.CASH_NATURE_PAYMENT}" /></td>
																							
																									<td><c:out value="${cashbook.value.CASH_DATE}" /></td> 
																								</c:when>
																								<c:otherwise>
																								<td><a target="_blank"
																										href="<c:url value="/getPaymentDetails.in?PAYMENT=${cashbook.value.CASH_NATURE_PAYMENT}&from=${CurrentMonth_FromDATE}&to=${CurrentMonth_ToDATE }&CASH_BOOK_ID=${CASH_BOOK_ID}"/>"><c:out
																									value="${cashbook.value.CASH_NATURE_PAYMENT}" />
																																</td>
																									<td>Multiple Payments</td> 
																								</c:otherwise>
																							</c:choose>
																							<c:choose>
																								<c:when test="${cashbook.value.PAYMENT_TYPE_ID == 1}">
																									<td></td>
																									<td><c:out
																											value=" ${cashbook.value.CASH_AMT_STR}" /></td>
																								</c:when>
																								<c:otherwise>
																									<td><c:out
																											value=" ${cashbook.value.CASH_AMT_STR}" /></td>
																									<td></td>
																								</c:otherwise>
																							</c:choose>
																						</tr>	
																			
																		</c:forEach>
																		<tr class="workorder_repair_search_totals">
																				<th class="text-right" colspan="2"><b>
																						Total :</b></th>
																				<td></td>
																				<td><c:out value=" ${CreditTotal}" /></td>
																				<td><c:out value=" ${DebitTotal}" /></td>
																		</tr>
																	</c:if>
																	<tr class="workorder_repair_search_totals">
																		<td class="text-right" colspan="4"><b> <c:out
																					value=" ${TotalBalanceWorld}" />
																		</b></td>
																	</tr>
																	<tr class="workorder_repair_search_totals">
																		<th class="text-right" colspan="3"><b> <c:out
																					value=" ${CurrentMonth_FromDATE} to " /> <c:out
																					value=" ${CurrentMonth_ToDATE}" /> BALANCE AMOUNT:
																		</b></th>
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
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script>
</div>