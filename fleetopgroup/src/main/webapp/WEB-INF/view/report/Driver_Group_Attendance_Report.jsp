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
					/ <a href="<c:url value="/AR.in"/>">Driver Attendance Report</a> / <span>Driver
						Group Attendance Report</span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('advanceTable', 'Driver Group Attendance Report')">
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
		
		
		<!--Group Wise Driver Attendance Report By Dev Yogi Start -->
				<sec:authorize access="hasAuthority('VIEW_GO_DR_AT_REPORT')">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#GD_ATT_ONE"> Group Wise Driver Attendance Report </a>
							</h4>
						</div>
						<!-- <div id="GD_ATT_ONE" class="panel-collapse collapse"> -->
							<div class="box-body">
								<form action="GroupDriverAttReport" method="post">
									<div class="form-horizontal ">
										<!-- vehicle Select -->
										<div class="row1">
											<label class="L-size control-label">Group: <abbr
												title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="hidden" id="SelectFuelGroup"
													name="DRIVER_GROUP_ID" style="width: 100%;"
													required="required" placeholder="Please Select Group" />
												<p class="help-block">Select One Group</p>
											</div>
										</div>
										<!-- Driver Job Title -->
										<div class="row1">
											<label class="L-size control-label"> Job Title :</label>
											<div class="I-size">
												<input type="hidden" id="AttGroupDriverJob_ID"
													name="DRIVER_JOBTITLE" value="-1" style="width: 100%;"
													placeholder="Please Enter 2 or more Job Type" />
											</div>
										</div>
										<!-- Date range -->
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
										<fieldset class="form-actions">
											<div class="row1">
												<label class="L-size control-label"></label>

												<div class="I-size">
													<div class="pull-left">
														<button type="submit" name="commit"
															class="btn btn-success" onclick="return validateGroupWiseDriverAttendanceReport();"
															>
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
			<!--Group Wise Driver Attendance Report By Dev Yogi Start -->
		<sec:authorize access="hasAuthority('VIEW_DRIVER')">
			<div id="div_print">

				<div id="div_print">

					<section class="invoice">
						<div class="row invoice-info">
							<div class="col-sm-12 col-md-12 col-xs-12"
								style="padding-right: 80px;">
								<div class="table-responsive">
									<c:if test="${!empty DA}">
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
												<caption>Driver wise Group Halt Report
													-${SearchDate}</caption>
												<thead>
													<tr>
														<th class="fit">No</th>
														<th>Driver</th>
														<th>Group</th>
														<th>Attendance</th>
														<th>Total Income</th>
														<c:if test="${configuration.IncTripExpenseInDriverSalary}">
														<th>Salary</th>
														<th>Trip Income</th>
														</c:if>
													</tr>
												</thead>
												<tbody>
													<%
													Integer hitsCount = 1;
												%>
													<c:forEach items="${DA}" var="DA">
														<tr data-object-id="">
															<td class="fit">
																<%
																	out.println(hitsCount);
																				hitsCount += 1;
																%>
															</td>
															<td><c:out value="${DA.DRIVER_NAME}" /></td>
															<td><c:out value="${DA.POINT_TYPE}" /></td>
															<c:if test="${DA.driverSalaryTypeId ==0 }">
																<td><a
																	href="GetDriverAttReport.in?DID=${DA.DRIVERID}&DATE=${SearchDate}"
																	target="_blank"><c:out
																			value="${DA.TRIPSHEETID} days" /></a></td>
															</c:if>
															<c:if test="${DA.driverSalaryTypeId ==1 }">
																<td><a
																	href="GetDriverAttReport.in?DID=${DA.DRIVERID}&DATE=${SearchDate}"
																	target="_blank"><c:out
																			value="${DA.TRIPSHEETID} days" /></a></td>
															</c:if>
															<c:if test="${DA.driverSalaryTypeId ==2 }">
																<td><a
																	href="GetDriverAttReport.in?DID=${DA.DRIVERID}&DATE=${SearchDate}"
																	target="_blank"><c:out
																			value="${DA.tripSheetCount} trip" /></a></td>
															</c:if>
															<c:if test="${DA.driverSalaryTypeId ==3 }">
																<td><a
																	href="GetDriverAttReport.in?DID=${DA.DRIVERID}&DATE=${SearchDate}"
																	target="_blank"><c:out
																			value="${DA.totalKMUsage} km" /></a></td>
															</c:if>
															<td><c:out value="${DA.DRIVER_POINT}"></c:out></td>
															<c:if test="${configuration.IncTripExpenseInDriverSalary}">
															<td><c:out value="${DA.perDaySalary}"></c:out></td>
															<td><c:out value="${DA.expenseAmount}"></c:out></td>
															</c:if>
														</tr>
													</c:forEach>
													<%-- <tr>
														<td colspan="4"></td>
													</tr>
													<tr>
														<td colspan="3"
															style="text-align: right; font-size: 15px; font-weight: bold;">TOTAL
															POINT :</td>
														<td
															style="text-align: left; font-size: 15px; font-weight: bold;"><c:out
																value="${TotalPoint}" /></td>
													</tr> --%>
												</tbody>
											</table>
										</div>
									</c:if>

									<c:if test="${NotFound}">
										<script>
											$(".invoice").addClass("hide");
											setTimeout(function() {
												validateReport();
											}, 500);
										</script>
									</c:if>

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
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/ValidateGroupWiseDriverAttendanceReport.js" />"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		});
	</script>
</div>