<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
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
																<td>Salary Date : 
																		<c:out value="${salary.SALARY_FROM_DATE}  to  " /> <c:out
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
																		value="${salary.TOTAL_WORKINGDAY}" /> <br> <br>
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
																		<thead>
																			<tr>
																				<th>No</th>
																				<th>Advance Name</th>
																				<th>Advance Date</th>
																				<th>Amount</th>
																				<th>Balance Amount</th>
																			</tr>
																		</thead>
																		<tbody>
																			<%
																				Integer hitsCount = 1;
																			%>
																			<c:if test="${!empty DriverAdvanvce}">
																				<c:forEach items="${DriverAdvanvce}"
																					var="DriverAdvanvce">

																					<tr data-object-id="" class="ng-scope">
																						<td class="fit">
																							<%
																								out.println(hitsCount);
																											hitsCount += 1;
																							%>
																						</td>
																						<td class="icon"><c:choose>
																								<c:when
																									test="${DriverAdvanvce.ADVANCE_NAME == 'ADVANCE'}">
																									<span class="label label-default label-success">
																										<c:out value="${DriverAdvanvce.ADVANCE_NAME}" />
																									</span>
																								</c:when>
																								<c:otherwise>
																									<span class="label label-default label-warning">
																										<c:out value="${DriverAdvanvce.ADVANCE_NAME}" />
																									</span>
																								</c:otherwise>
																							</c:choose></td>
																						<td class="icon"><i></i> <c:out
																								value="${DriverAdvanvce.ADVANCE_DATE}" /></td>
																						<td class="icon"><c:out
																								value="${DriverAdvanvce.ADVANCE_AMOUNT}" /></td>
																						<td class="icon"><c:out
																								value="${DriverAdvanvce.ADVANCE_BALANCE}" /></td>
																						<td class="icon"><c:out
																								value="${DriverAdvanvce.ADVANCE_STATUS}" /></td>
																					</tr>
																				</c:forEach>
																				<tr data-object-id="" class="ng-scope">
																					<td colspan="4">
																					<td><c:out value="${DriverAdvanvceBalance}" /></td>
																					<td></td>
																				</tr>
																			</c:if>
																		</tbody>
																	</table>

																</td>
															</tr>
															<tr>
																<td colspan="2">
																	<div class="box">
																		<div class="box-header">
																			<h3 class="panel-title">Paid Advance Amount</h3>
																		</div>
																		<div class="box-body">
																			<form action="savePaySalary.in" method="post"
																				enctype="multipart/form-data">
																				<div class="panel panel-default">
																					<div class="panel-heading clearfix"></div>

																					<div class="panel-body">
																						<div class="form-horizontal">
																							<fieldset>
																								<input name="DRIVER_ID"
																									value="${driver.driver_id}" type="hidden">
																								<input name="SAID" value="${SAID}" type="hidden">
																							</fieldset>
																							<div class="row1">

																								<div class="col-md-offset-4">
																									<div class="col-md-4">
																										<label id="target" for="grp_option">
																											Total Advance :</label>
																									</div>
																									<div class="col-md-4">
																										<b><c:out value="${OnlyAdvanvceBalance}"></c:out></b>
																									</div>
																								</div>
																							</div>
																							<div class="row1">
																								<div class="col-md-offset-4">
																									<div class="col-md-4">
																										<label id="target" for="grp_option">
																											Total Penalty :</label>
																									</div>
																									<div class="col-md-4">
																										<b><c:out value="${OnlyPenaltyBalance}"></c:out></b>
																									</div>
																								</div>
																							</div>
																							<div class="row1">
																								<div class="col-md-offset-4">
																									<div class="col-md-4">
																										<label id="target" for="grp_option">Total
																											Amount :</label>
																									</div>
																									<div class="col-md-4">
																										<b><c:out value="${DriverAdvanvceBalance}"></c:out></b>
																									</div>
																								</div>
																							</div>
																							<div class="row1">
																								<div class="L-size">
																									<label class="col-md-offset-3" id="target"
																										for="grp_option">Total Net
																										Salary :</label>
																								</div>
																								<div class="I-size">
																									<input type="text" class="form-text"
																										readonly="readonly"
																										value="${salary.TOTAL_NETSALARY}">
																								</div>
																							</div>
																							<div class="row1">
																								<div class="L-size">
																									<label class="col-md-offset-3" id="target"
																										for="grp_option">Deduction Advance
																										Amount :<abbr title="required">*</abbr>
																									</label>
																								</div>
																								<div class="I-size">
																									<input type="number" name="ADVANCE_PAID_AMOUNT"
																										class="form-text" min="0" required="required"
																										placeholder="ADVANCE PAID AMOUNT" value="0"
																										max="${OnlyAdvanvceBalance}">
																								</div>
																							</div>
																							<div class="row1">
																								<div class="L-size">
																									<label class="col-md-offset-3" id="target"
																										for="grp_option">Deduction Penalty
																										Amount :<abbr title="required">*</abbr>
																									</label>
																								</div>
																								<div class="I-size">
																									<input type="number" name="PENALTY_PAID_AMOUNT"
																										class="form-text" min="0" required="required"
																										placeholder="PENALTY PAID AMOUNT" value="0"
																										max="${OnlyPenaltyBalance}">
																								</div>
																							</div>
																							<div class="row1" id="grpfuelDate"
																								class="form-group">
																								<label class="L-size control-label"
																									for="fuelDate">Deduction Date : <abbr
																									title="required">*</abbr>
																								</label>
																								<div class="I-size">
																									<div class="input-group input-append date"
																										id="StartDate">
																										<input type="text" class="form-text"
																											name="PAID_DATE" id="fuelDate"
																											required="required"
																											data-inputmask="'alias': 'dd-mm-yyyy'"
																											data-mask="" /> <span
																											class="input-group-addon add-on"> <span
																											class="fa fa-calendar"></span>
																										</span>
																									</div>
																									<span id="fuelDateIcon" class=""></span>
																									<div id="fuelDateErrorMsg" class="text-danger"></div>
																								</div>
																							</div>
																							<div class="row1">
																								<div class="L-size">
																									<label class="col-md-offset-3" id="target"
																										for="grp_option">Advance Deduction No
																										:<abbr title="required">*</abbr>
																									</label>
																								</div>
																								<div class="I-size">
																									<input type="text" name="ADVANCE_PAID_NUMBER"
																										class="form-text" value="" required="required"
																										placeholder="ADVANCE PAID NUMBER">
																								</div>
																							</div>

																							<div class="row1">
																								<div class="L-size">
																									<label class="col-md-offset-3" id="target"
																										for="grp_option">Advance Deduction By
																										:<abbr title="required">*</abbr>
																									</label>
																								</div>
																								<div class="I-size">
																									<input type="text" class="form-text"
																										name="ADVANCE_RECEIVED_BY" required="required"
																										value="${RECEIVED_BY}" readonly="readonly"
																										placeholder="ADVANCE RECEIVED BY">
																								</div>
																								<input type="hidden" name="ADVANCE_RECEIVED_BY_ID"  value="${RECEIVED_BY_ID}"/>
																							</div>
																							<div class="row1">
																								<div class="L-size">
																									<label class="col-md-offset-3" id="target"
																										for="grp_option">Deduction Remarks:<abbr
																										title="required">*</abbr>
																									</label>
																								</div>
																								<div class="I-size">
																									<textarea rows="3" cols="2" class="form-text"
																										required="required" name="PAID_REMARK"></textarea>
																								</div>
																							</div>

																							<div class="panel-footer">
																								<fieldset>
																									<input class="btn btn-success"
																										data-disable-with="Saving..." name="commit"
																										type="submit" value="Pay Advance"
																										data-toggle="modal"
																										data-target="#processing-modal">
																								</fieldset>

																							</div>
																						</div>
																					</div>
																				</div>
																			</form>
																		</div>
																	</div>
																</td>
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

	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>

</div>