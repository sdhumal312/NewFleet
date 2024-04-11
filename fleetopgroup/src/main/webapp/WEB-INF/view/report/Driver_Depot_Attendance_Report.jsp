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
					/ <a href="<c:url value="/AR.in"/>">Driver Attendance Report</a> /
					<span>Driver Depot Attendance Report</span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('advanceTable', 'Driver Depot Attendance Report')">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
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
		<sec:authorize access="hasAuthority('VIEW_DO_DA_AT_REPORT')">
				<div class="panel box box-primary">
					<div class="box-header with-border">
						<h4 class="box-title">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#GD_ATT_EIGHT"> Depot Wise Driver Attendance Report </a>
						</h4>
					</div>
					<!-- <div id="GD_ATT_EIGHT" class="panel-collapse collapse"> -->
						<div class="box-body">
							<form action="DepotDriverAttReport" method="post">
								<div class="form-horizontal ">
									<div class="row1">
										<label class="L-size control-label">Date range: <abbr
											title="required">*</abbr></label>
										<div class="I-size">
											<div class="input-group">
												<div class="input-group-addon">
													<i class="fa fa-calendar"></i>
												</div>
												<input type="text" id="GTC_daterange" class="form-text"
													name="HALT_DATE_RANGE" required="required"
													style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
											</div>
										</div>
									</div>
									<div class="row1">
										<label class="L-size control-label">Depot: <abbr
											title="required">*</abbr>
										</label>
										<div class="I-size">
											<input type="hidden" id="SelectFuelGroup"
												name="DRIVER_GROUP_ID" style="width: 100%;"
												required="required" placeholder="Please Select Group" />
											<p class="help-block">Select One Depot</p>
										</div>
									</div>

									<div class="row1">
										<label class="L-size control-label"> Job Title :</label>
										<div class="I-size">
											<input type="hidden" id="AttGroupDriverJob_ID"
												name="DRIVER_JOBTITLE" value="-1" style="width: 100%;"
												placeholder="Please Enter 2 or more Job Type" />
										</div>
									</div>
									<fieldset class="form-actions">
										<div class="row1">
											<label class="L-size control-label"></label>

											<div class="I-size">
												<div class="pull-left">
													<button type="submit" name="commit" class="btn btn-success">
														<i class="fa fa-search"> Search</i>
													</button>
												</div>
											</div>
										</div>
									</fieldset>
								</div>
							</form>
						</div>
					<!-- </div> -->
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
											<caption>Depot wise Attendance Report
												${SearchGroup} -${SearchDate}</caption>
											<thead>
												<tr>
													<th class="fit">No</th>
													<th>Emp</th>
													<th>Driver</th>
													<th>Job</th>
													<th>T.DUTY</th>
													<c:if test="${company.company_esi_pf_disable == 0}">
														<th>E.DUTY</th>
														<th>ALL.DUTY</th>
													</c:if>
												</tr>
											</thead>
											<tbody>
												<%
													Integer hitsCount = 1;
												%>
												

													<c:forEach items="${salary}" var="salary">
														<tr data-object-id="">
															<td class="fit">
																<%
																	out.println(hitsCount);
																				hitsCount += 1;
																%>
															</td>
															<td><c:out value="${salary.DRIVER_EMPNUMBER}" /></td>
															<td><c:out value="${salary.DRIVER_FIRSTNAME} " />
															 <c:out value="${salary.DRIVER_LASTNAME}" />
															<c:out value="${salary.driverFatherName} " /> </td>
															<td><c:out value="${salary.DRIVER_JOBTITLE}" /></td>
															<c:if test="${salary.DRIVER_SALARY_TYPE_ID == 1}">
																<td><c:out value="${salary.TOTAL_WORKINGDAY}"></c:out></td>														
															</c:if>
															<c:if test="${salary.DRIVER_SALARY_TYPE_ID == 2}">
																<td><c:out value="${salary.TRIP_SHEET_COUNT}"></c:out></td>														
															</c:if>
															<c:if test="${salary.DRIVER_SALARY_TYPE_ID == 3}">
																<td><c:out value="${salary.TOTAL_KM_USAGE}"></c:out></td>														
															</c:if>
															<c:if test="${company.company_esi_pf_disable == 0}">
																<td><c:out value="${salary.TOTAL_EXTRA_WORKINGDAY}"></c:out></td>
																	<c:if test="${salary.DRIVER_SALARY_TYPE_ID == 1}">
																		<td><a
																			href="GetDriverAttReport.in?DID=${salary.DRIVER_ID}&DATE=${SearchDate}"
																			target="_blank"><c:out
																					value="${salary.TOTAL_ALL_WORKINGDAY}" /></a></td>														
																	</c:if>
																	<c:if test="${salary.DRIVER_SALARY_TYPE_ID == 2}">
																		<td><a
																			href="GetDriverAttReport.in?DID=${salary.DRIVER_ID}&DATE=${SearchDate}"
																			target="_blank"><c:out
																					value="${salary.TRIP_SHEET_COUNT}" /></a></td>
																	</c:if>
																	<c:if test="${salary.DRIVER_SALARY_TYPE_ID == 3}">
																		<td><a
																			href="GetDriverAttReport.in?DID=${salary.DRIVER_ID}&DATE=${SearchDate}"
																			target="_blank"><c:out
																					value="${salary.TOTAL_KM_USAGE}" /></a></td>
																	</c:if>
															</c:if>
														</tr>
													</c:forEach>
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
			<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js" />"></script>
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

	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		});
	</script>
</div>