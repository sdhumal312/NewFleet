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
						onclick="advanceTableToExcel('advanceTable', 'Depot Wise Trip Collection Report')">
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
																				 Daily Trip
																				Collection Month &amp; CashBook Report 
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
																		<th>Payment</th>
																		 <th>Date</th>
																		<th>CREDIT</th>
																		<th>DEBIT</th>
																																			</tr>
																</thead>
																<tbody>
																	<%
																		Integer hitsCount = 1;
																	%>
																	<c:if test="${!empty cashbook}">
																	
																		<c:forEach items="${cashbook}" var="cashBook">

																			<tr data-object-id="" class="closeAmount">
																				<td class="fit">
																					<%
																						out.println(hitsCount);
																									hitsCount += 1;
																					%>
																				</td>
																				<td><c:out value="${cashBook.CASH_NATURE_PAYMENT}" /></td>
																				<td><c:out value="${cashBook.CASH_DATE}" /></td>
																				<c:choose>
																								<c:when test="${cashBook.PAYMENT_TYPE_ID == 1}">
																							
																									<td></td>
																									<td><c:out
																											value=" ${cashBook.CASH_AMOUNT}" /></td>
																								</c:when>
																								<c:otherwise>
																								
																									<td><c:out
																											value=" ${cashBook.CASH_AMOUNT}" /></td>
																									<td></td>
																								</c:otherwise>
																				</c:choose>
																				
																			</tr>

																		</c:forEach>

																		<tr data-object-id="" class="closeGroupAmount">
																			<tr class="workorder_repair_search_totals">
																				<th class="text-right" colspan="2"><b>
																						Total :</b></th>
																				<td></td>
																				<td><c:out value=" ${CreditTotal}" /></td>
																				<td><c:out value=" ${DebitTotal}" /></td>
																		</tr>
																		</tr>
																	</c:if>
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