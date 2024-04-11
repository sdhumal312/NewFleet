<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="row">
					<div class="pull-left">
						<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
							href="<c:url value="/getDriversList"/>">Driver</a> / <a
							href="<c:url value="/showDriver?driver_id=${driver.driver_id}"/>"><c:out
								value="${driver.driver_firstname} " /> <c:out
								value="${driver.driver_Lastname}" /></a> / <a
							href="<c:url value="/DriverSalary.in?ID=${driver.driver_id}"/>">Driver
							Salary</a>
					</div>


					<div class="col-md-off-5">
						<div class="col-md-3"></div>
						<sec:authorize access="hasAuthority('VIEW_DRIVER')">
							<button class="btn btn-default btn-sm"
								onclick="printDiv('div_printTripsheet')">
								<span class="fa fa-print"> Print</span>
							</button>
						</sec:authorize>
					</div>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<div class="col-md-offset-1 col-md-9 col-sm-8 col-xs-12">
				<div class="box">
					<div class="boxinside">
						<div id="div_printTripsheet">
							<sec:authorize access="!hasAuthority('VIEW_DRIVER')">
								<spring:message code="message.unauth"></spring:message>
							</sec:authorize>
							<sec:authorize access="hasAuthority('VIEW_DRIVER')">

								<section class="invoice">
									<div class="row invoice-info">

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
														<td>Print By:
															${company.firstName}_${company.lastName}</td>
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
												<div class="row invoice-info">
													<table
														class="table table-hover table-bordered table-striped">

														<tbody>
															<tr>
																<td>Salary Date : <c:out
																		value="${salary.SALARY_FROM_DATE}  to  " /> <c:out
																		value="${salary.SALARY_TO_DATE}" />
																</td>
																<td></td>
															</tr>
															<tr>
																<td>Name : <a
																	href="showDriver.in?driver_id=${driver.driver_id}">
																		<c:out value="${driver.driver_firstname}" /> <c:out
																			value="${driver.driver_Lastname}" />
																</a></td>
																<td></td>
															</tr>
															<tr>
																<td>Employee No : <c:out
																		value="${driver.driver_empnumber}" /><br> Driver
																	Group : <c:out value="${driver.driver_group}" /><br>
																	PF No : <c:out value="${driver.driver_pfno}" /><br>
																	ESI No : <c:out value="${driver.driver_esino}" />

																</td>
																<td>No of Working Days : <c:out
																		value="${salary.TOTAL_WORKINGDAY}" /><br> <br>
																	DL No : <c:out value="${driver.driver_dlnumber}" /></td>
															</tr>
															<tr>
																<td>Earning</td>
																<td>Deductions</td>
															</tr>
															<tr>
																<td colspan="2">
																	<table class="table table-hover table-bordered">
																		<tbody>
																			<tr>
																				<td>Basic Salary :</td>
																				<td><c:out value="${salary.MONTH_SALARY}" /></td>
																				<td>ESI :</td>
																				<td><c:out value="${salary.TOTAL_ESIAMOUNT}" />
																			</tr>
																			<tr>
																				<td></td>
																				<td></td>
																				<td>PF :</td>
																				<td><c:out value="${salary.TOTAL_PFAMOUNT}" /></td>
																			</tr>
																			<tr>
																				<td><b>Total Earning :</b></td>
																				<td><b><c:out
																							value="${salary.MONTH_SALARY}" /></b></td>
																				<td><b>Total Deductions :</b></td>
																				<td><b><c:out
																							value="${salary.TOTAL_ESIAMOUNT + salary.TOTAL_PFAMOUNT}" /></b></td>
																			</tr>
																			<tr>
																				<td></td>
																				<td></td>
																				<td><b>Net Pay : <c:out
																							value="${salary.TOTAL_NETSALARY}" /></b></td>
																				<td></td>
																			</tr>
																		</tbody>
																	</table>
																</td>
															</tr>
															<tr>
																<td colspan="2">Information</td>

															</tr>
															<tr>
																<td colspan="2">
																	<table class="table table-hover table-bordered">
																		<tbody>
																			<tr>
																				<td></td>
																				<td>Advance / Penalty deductions :</td>
																				<td><c:out value="${salary.TOTAL_ADVANCE}" /></td>
																				<td></td>
																			</tr>
																			<tr>
																				<td></td>
																				<td>Final Amount :</td>
																				<td><c:out value="${salary.TOTAL_HANDSALARY}" /></td>
																				<td></td>
																			</tr>

																		</tbody>
																	</table>

																</td>
															</tr>
															<tr>
																<td colspan="2"></td>
															</tr>
														</tbody>
													</table>

												</div>
											</div>
										</div>
									</div>
								</section>
							</sec:authorize>
						</div>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js" />"></script>

	</section>
</div>