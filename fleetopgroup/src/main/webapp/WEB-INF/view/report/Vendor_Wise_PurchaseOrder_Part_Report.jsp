<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/POR.in"/>">Purchase Order Report</a> / <span>Purchase
						Order Report</span>
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
		<sec:authorize access="!hasAuthority('VIEW_PURCHASE')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_PURCHASE')">
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
											<caption>Purchase Order Report</caption>
											<thead>
												<tr>
													<th>No</th>
													<th>PO_ID</th>
													<th>Part</th>
													<th>Qty</th>
													<th>Received</th>
													<th>Each</th>
													<th>Dis</th>
													<th>GST</th>
													<th>Total</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${!empty PurchaseOrderPart}">
													<%
																Integer hitsCount = 1;
															%>
													<c:forEach items="${PurchaseOrderPart}"
														var="PurchaseOrderPart">
														<tr data-object-id="" class="ng-scope">
															<td class="fit">
																<%
																			out.println(hitsCount);
																						hitsCount += 1;
																		%>
															</td>
															<td><a target="_blank"
																href="<c:url value="/PurchaseOrders_Parts.in?ID=${PurchaseOrderPart.purchaseorder_id}"/>">
																	<c:out value="PO-${PurchaseOrderPart.purchaseorder_Number}" />
															</a></td>
															<td><c:out
																	value="${PurchaseOrderPart.purchaseorder_partname}" />
																<c:out
																	value="${PurchaseOrderPart.purchaseorder_partnumber}" /><br>
																<c:choose>
																	<c:when
																		test="${PurchaseOrderPart.inventory_all_quantity != 0.0}">
																		<samp style="color: red;"> Already You have
																			${PurchaseOrderPart.inventory_all_quantity} Quantity
																			in stock . </samp>
																	</c:when>
																	<c:otherwise>
																		<samp style="color: green;">You don't have in
																			stock</samp>
																	</c:otherwise>
																</c:choose></td>
															<td>${PurchaseOrderPart.quantity}</td>

															<td><a>${PurchaseOrderPart.received_quantity}</a></td>

															<td>${PurchaseOrderPart.parteachcost}</td>
															<td>${PurchaseOrderPart.discount}%</td>
															<td>${PurchaseOrderPart.tax}%</td>
															<td><i class="fa fa-inr"></i>
																${PurchaseOrderPart.totalcost}</td>
														</tr>
													</c:forEach>
													<tr class="vehicle_repair_total">
														<th class="text-right" colspan="5"><b> Total
																Amount :</b></th>
														<td>${TotalAmount}</td>
														<td colspan="3">${TotalBalanceAmount}</td>
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