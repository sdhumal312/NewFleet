<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<style>
.box-body .affix {
	border-radius: 3px;
	background: #FFF;
	margin-bottom: 5px;
	padding: 5px;
}
</style>
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
<script>
 function validateReport()
{
	
	showMessage('errors','no records found');
		return false;
}  
 </script>
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
					/ <a href="<c:url value="/DR.in"/>">Driver Report</a> / <span>Month
						Wise ESI PF Report</span>
				</div>
				<div class="pull-right">
				<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('advanceTable', 'Month Wise ESI PF Report')">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
					</button>
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
			<sec:authorize access="hasAuthority('VIEW_DR_MO_ES_PF_REPORT')">
					<div class="panel box box-danger">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#collapsedriveresi"> Driver Month wise ESI, PF Date
									Range Report </a>
							</h4>
						</div>
						<!-- <div id="collapsedriveresi" class="panel-collapse collapse"> -->
							<div class="box-body">
								<form action="DriverMonthEsiPFReport" method="post">
									<div class="form-horizontal ">
										<div class="row1">
											<label class="L-size control-label">Date range: <abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<div class="input-group">
													<div class="input-group-addon">
														<i class="fa fa-calendar"></i>
													</div>
													<input type="text" id="rangeFuelMileage2" class="form-text"
														name="TRIP_DATE_RANGE" required="required"
														style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
												</div>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Group: <abbr
												title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="hidden" id="SelectFuelGroup2"
													name="vehicleGroupId" style="width: 100%;"
													required="required" placeholder="Please Select Group"
													value="0" />
												<p class="help-block">Select One Group</p>
											</div>
										</div>
										<!-- <div class="row1">
									<label class="L-size control-label"> Job Title :<abbr
										title="required">*</abbr></label>
									<div class="I-size">
										<input type="hidden" id="AttGroupDriverJob_ID"
											name="DRIVER_JOBTITLE" value="ALL" style="width: 100%;"
											placeholder="Please Enter 2 or more Job Type" />
									</div>
								</div> -->

										<fieldset class="form-actions">
											<div class="row1">
												<label class="L-size control-label"></label>
												<div class="I-size">
													<div class="pull-left">
														<button type="submit" name="commit"
															class="btn btn-success" onclick="return validateDriverMonthWiseESIPFDateRangeReport();">
															<i class="fa fa-search"> Search</i>
														</button>
													</div>
												</div>
											</div>
										</fieldset>
									</div>
								</form>
							</div>
					<!-- 	</div> -->
					</div>
				</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_DRIVER')">
			<div id="div_print">

				<div id="div_print">
					<c:if test="${!empty salary}">
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
											<table id="advanceTable"
												class="table table-hover table-bordered table-striped">
												<caption>Month Wise ESI PF Report - ${Location}
													Date ${dateRange}</caption>
												<thead>
													<tr>
														<th>ID</th>
														<th>Driver Name</th>
														<th>PF NO.</th>
														<th>ESI NO.</th>
														<th>A/C No.</th>
														<th>T.Duty</th>
														<th>Day Salary</th>
														<th>Total Salary</th>
														<th>PF</th>
														<th>ESI</th>
														<c:if test="${company.company_esi_pf_disable == 0}">
															<th>Extra Duty</th>
															<th>Extra Salary</th>
														</c:if>
														<th>Previous Advance</th>
														<th>Penalty</th>
														<th>Advance (Deduct)</th>
														<th>Penalty (Deduct)</th>
														<th>Allowance</th>
														<th>Other</th>
														<th>Total Net Pay</th>

														<th>Advance Balance</th>

													</tr>
												</thead>
												<tbody>

													<%
														Integer hitsCount = 1;
													%>
													<c:forEach items="${salary}" var="salary">
														<tr data-object-id="" class="ng-scope">
															<td class="fit">
																<%
																	out.println(hitsCount);
																				hitsCount += 1;
																%>
															</td>
															<td><c:choose>
																	<c:when test="${salary.SALARY_STATUS == 'OPEN'}">
																		<a target="_blank"
																			href="<c:url value="/PaySalary.in?ID=${salary.DSID}&DID=${salary.DRIVER_ID}"/>"><c:out
																				value="${salary.DRIVER_EMPNUMBER}" /><br> <c:out
																				value="${salary.DRIVER_FIRSTNAME}" /> 
																				<c:out value="  ${salary.DRIVER_LASTNAME}" />
																				 <c:out value="  ${salary.DriverFatherName}" /></a>
																				
																				
																	</c:when>
																	<c:otherwise>
																		<c:out value="${salary.DRIVER_EMPNUMBER}" />
																		<br>
																		<c:out value="${salary.DRIVER_FIRSTNAME}" />
																		<c:out value="${salary.DRIVER_LASTNAME}" />
																		<c:out value="${salary.DriverFatherName}" />
																	</c:otherwise>
																</c:choose></td>
															<td><c:out value="${salary.DRIVER_PFNO}" /></td>
															<td><c:out value="${salary.DRIVER_ESINO}" /></td>
															<td><c:out value="${salary.DRIVER_BANKNUMBER}" /></td>
															<td><c:out value="${salary.TOTAL_WORKINGDAY}" /></td>

															<td><c:out value="${salary.MONTH_PERDAYSALARY}" /></td>
															<td><c:out value="${salary.MONTH_SALARY}" /></td>

															<td><c:out value="${salary.TOTAL_PFAMOUNT}" /></td>
															<td><c:out value="${salary.TOTAL_ESIAMOUNT}" /></td>

															<c:if test="${company.company_esi_pf_disable == 0}">
																<td><c:out value="${salary.TOTAL_EXTRA_WORKINGDAY}" /></td>
																<td><c:out value="${salary.MONTH_EXTRA_SALARY}" /></td>
															</c:if>

															<td><c:out value="${salary.TOTAL_PREVIOUS_ADVANCE}" /></td>

															<td><c:choose>
																	<c:when test="${salary.SALARY_STATUS_ID == 1}">
																		<a target="_blank"
																			href="<c:url value="/PaySalary.in?ID=${salary.DSID}&DID=${salary.DRIVER_ID}"/>">
																			<c:out value="${salary.TOTAL_ADVANCE_PENALTY}" />
																		</a>
																	</c:when>
																	<c:otherwise>
																		<c:out value="${salary.TOTAL_PENALTY}" />
																	</c:otherwise>
																</c:choose></td>
															<td><c:out value="${salary.TOTAL_ADVANCE_DEDUCTION}" /></td>
															<td><c:out value="${salary.TOTAL_PENALTY_DEDUCTION}" /></td>

															<td><c:out value="${salary.TOTAL_ALLOWANCE}" /></td>
															<td><c:out value="${salary.TOTAL_OTHEREXTRA}" /></td>



															<c:choose>
																<c:when test="${company.company_esi_pf_disable == 0}">
																	<c:choose>
																		<c:when test="${salary.SALARY_STATUS_ID == 1}">
																			<td><c:out
																					value="${salary.TOTAL_EXTRA_NETSALARY}" /><br>
																				<c:out value="NOTPAID" /></td>
																		</c:when>
																		<c:otherwise>
																			<td><c:out value="${salary.TOTAL_HANDSALARY}" /></td>
																		</c:otherwise>
																	</c:choose>

																</c:when>
																<c:otherwise>
																	<c:choose>
																		<c:when test="${salary.SALARY_STATUS_ID == 1}">

																			<td><c:out value="${salary.TOTAL_NETSALARY}" /><br>
																				<c:out value="NOTPAID" /></td>
																		</c:when>
																		<c:otherwise>
																			<td><c:out value="${salary.TOTAL_HANDSALARY}" /></td>
																		</c:otherwise>
																	</c:choose>
																</c:otherwise>
															</c:choose>
															<td><c:out value="${salary.TOTAL_ADVANCE_BALANCE}" /></td>
														</tr>
													</c:forEach>
													<tr data-object-id="" class="ng-scope">
														<td colspan="5"></td>
														<td><c:out value="${TotalWorkingDate}" /></td>
														<td></td>
														<td><c:out value="${TotalMONTH_SALARY}" /></td>

														<td><c:out value="${TotalPF}" /></td>
														<td><c:out value="${TotalESI}" /></td>

														<c:if test="${company.company_esi_pf_disable == 0}">
															<td><c:out value="${TotalExtraDuty}" /></td>
															<td><c:out value="${TotalExtraMONTH_SALARY}" /></td>
														</c:if>
														<td><c:out value="${TotalPreviousAdvance}" /></td>
														<td><c:out value="${TotalPenalty}" /></td>
														<td><c:out value="${TotalAdvanceDeduction}" /></td>
														<td><c:out value="${TotalPenaltyDeduction}" /></td>

														<td><c:out value="${TOTAL_ALLOWANCE}" /></td>
														<td><c:out value="${TOTAL_OTHEREXTRA}" /></td>

														<c:choose>
															<c:when test="${company.company_esi_pf_disable == 0}">
																<td><c:out value="${TotalExtraNetPay}" /></td>
															</c:when>
															<c:otherwise>
																<td><c:out value="${TotalNetPay}" /></td>
															</c:otherwise>
														</c:choose>
														<td><c:out value="${TotalAdvanceBalance}" /></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
						</section>
					</c:if>
					<c:if test="${NotFound}">
									<script>								
										$(".invoice").addClass("hide");
										setTimeout(function() {validateReport();}, 500);
									</script>
					</c:if>						
				</div>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script>
					<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/validateReports.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js" />"></script>
		<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/validateReport1.js"/>"></script>	
		
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Report.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>		
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/ValidateDriverMonthWiseESIPFDateRangeReport.js" />"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		});
	</script>
</div>