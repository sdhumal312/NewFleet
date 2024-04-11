<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/TCR.in"/>">Trip Collection Report</a>  /  <span>Driver Advance Report</span>
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
		<sec:authorize access="!hasAuthority('VIEW_DRIVER')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_DRIVER')">
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
										<caption>Advance Report</caption>

										<thead>
											<tr>
												<th class="fit">No</th>
												<th>Driver Name</th>
												<th>Advance Date</th>
												<th>Jama Balance</th>
												<th>Advance</th>
												<th>Paid By</th>
												<th>Paid NO</th>
												<th>Remark</th>
											</tr>
										</thead>
										<tbody>
											<%
													Integer hitsCount = 1;
												%>
											<c:if test="${!empty DriverAdvance}">

												<c:forEach items="${DriverAdvance}" var="DriverAdvance">
													<tr data-object-id="">
														<td class="fit">
															<%
																	out.println(hitsCount);
																				hitsCount += 1;
																%>
														</td>
														<td><c:out value="${DriverAdvance.TRIP_DRIVER_NAME}" /></td>
														<td><c:out value="${DriverAdvance.ADVANCE_DATE}" /></td>
														<td><c:out
																value="${DriverAdvance.DRIVER_JAMA_BALANCE}" /></td>
														<td style="text-align: right;"><c:out
																value="${DriverAdvance.DRIVER_ADVANCE_AMOUNT}" /></td>
														<td><c:out value="${TripCol.ADVANCE_PAID_BY}" /></td>
														<td><c:out
																value="${DriverAdvance.ADVANCE_PAID_NUMBER}" /></td>
														<td><c:out
																value="${DriverAdvance.DRIVER_ADVANCE_REMARK}" /></td>

													</tr>

												</c:forEach>
												<tr>
													<td colspan="8"></td>
												</tr>
												<tr>
													<td colspan="4" style="text-align: right; font-size: 15px; font-weight: bold;">TOTAL:</td>
													<td
														style="text-align: right; font-size: 15px; font-weight: bold;"><c:out
															value="${AdvanceTotal}" /></td>
													<td colspan="3"></td>

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
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script>
</div>