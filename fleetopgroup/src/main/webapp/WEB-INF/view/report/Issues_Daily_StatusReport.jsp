<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<style>
.box-body .affix {
	border-radius: 3px;
	background: #FFF;
	margin-bottom: 5px;
	padding: 5px;
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
					/ <a href="<c:url value="/IR.in"/>">Issues Report</a>  / <span>Daily Issues Report</span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('advanceTable', 'Daily Issues Report')">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
					</button>
					<a href="<c:url value="/Report"/>">Cancel</a>

				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_ISSUES')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		
					<sec:authorize access="hasAuthority('VIEW_IS_DA_ST_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#IssuesOne"> Issues Daily Status Report  </a>
								</h4>
							</div>
							<!-- <div id="IssuesOne" class="panel-collapse collapse"> -->
								<div class="box-body">
									<form action="IssuesDailyStatusReport" method="post">
										<div class="form-horizontal ">
											<!--  Issues Type -->
											<div class="row1">
												<label class="L-size control-label">Issues Type :<abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<select name="ISSUES_TYPE_ID" id="IssuesType"
														style="width: 100%;">
														<option value="1">Vehicle Issue</option>
														<option value="2">Driver Issue</option>
														<option value="3">Refund Issue</option>
														<c:if test="${configuration.customerIssue}">
															<option value="4">Customer Issue</option>
														</c:if>
														<option value="5">Other Issue</option>
														<option value="6">Vehicle BreakDown</option>
														<option value="-1">ALL</option>
													</select>
												</div>
											</div>
											<c:if test="${configuration.showIssuesLabelOption}">
											<div class="row1">
											<label class="L-size control-label">Labels</label>
											<div class="I-size">
												<select class="form-text" name="ISSUES_LABELS_ID" id="priority"
													required="required">
													<option value="-1">ALL</option>
													<option value="1">NORMAL</option>
													<c:if test="${!configuration.hideHighStatus}">
														<option value="2">HIGH</option>
													</c:if>
													<c:if test="${!configuration.hideLowStatus}">
														<option value="3">LOW</option>
													</c:if>
													<option value="4">URGENT</option>
													<option value="5">VERY URGENT</option>
													<c:if test="${showBreakdownSelection}">
														<option value="6">BREAKDOWN</option>													
													</c:if>	
												</select>
											</div>
										</div>
										</c:if>
										<c:if test="${!configuration.showIssuesLabelOption}">
										<input type="hidden" name="ISSUES_LABELS_ID" id="priority" value="0" >
										</c:if>
											<!-- Issues Status -->
											<div class="row1">
												<label class="L-size control-label">Issues Status :</label>
												<div class="I-size">
													<select name="ISSUES_STATUS_ID" id="fuel_vendor_paymode"
														style="width: 100%;">
														<option value="-1">ALL</option>
														<option value="1">OPEN</option>
														<option value="3">WOCREATED</option>
														<option value="4">RESOLVED</option>
														<option value="5">REJECT</option>
														<option value="2">CLOSED</option>
													</select>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Date range: <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<div class="input-group">
														<div class="input-group-addon">
															<i class="fa fa-calendar"></i>
														</div>
														<input type="text" id="reportrange" class="form-text"
															name="ISSUES_DATE_RANGE" required="required"
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
																class="btn btn-success">
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
		<sec:authorize access="hasAuthority('VIEW_ISSUES')">
			<div id="div_print">

				<div id="div_print">

					<section class="invoice">
						<div class="row invoice-info">
							<div class="col-sm-12 col-md-12 col-xs-12"
								style="padding-right: 80px;">
								<div class="table-responsive">
									<c:if test="${!empty Issues}">
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
											<table id="advanceTable" class="table table-hover table-bordered table-striped">
												<caption>Issues Report</caption>

												<thead>
													<tr>
														<th>ID</th>
														<th>Summary</th>
														<th>Vehicle</th>
														<th>Assign To</th>
														<th>TAT</th>
														<th>Status</th>
													</tr>
												</thead>
												<tbody>

													<c:forEach items="${Issues}" var="Issues">
														<tr data-object-id="" class="ng-scope">
															<td><a target="_blank"
																href="<c:url value="/showIssues?Id=${Issues.ISSUES_ID_ENCRYPT}" />"><c:out
																		value="I-${Issues.ISSUES_NUMBER}" /></a></td>
															<td class="col-sm-5"><a target="_blank"
																href="<c:url value="/showIssues?Id=${Issues.ISSUES_ID_ENCRYPT}" />"><strong>
																		<c:out value="${Issues.ISSUES_SUMMARY}" />
																</strong> </a> <c:choose>
																	<c:when test="${Issues.ISSUES_LABELS == 'NORMAL'}">
																		<small class="label label-primary"><c:out
																				value="${Issues.ISSUES_LABELS}" /></small>
																	</c:when>
																	<c:when test="${Issues.ISSUES_LABELS == 'HIGH'}">
																		<small class="label label-info"><c:out
																				value="${Issues.ISSUES_LABELS}" /></small>
																	</c:when>
																	<c:when test="${Issues.ISSUES_LABELS == 'LOW'}">
																		<small class="label label-default"><c:out
																				value="${Issues.ISSUES_LABELS}" /></small>
																	</c:when>
																	<c:when test="${Issues.ISSUES_LABELS == 'URGENT'}">
																		<small class="label label-warning"><c:out
																				value="${Issues.ISSUES_LABELS}" /></small>
																	</c:when>
																	<c:otherwise>
																		<small class="label label-danger"><c:out
																				value="${Issues.ISSUES_LABELS}" /></small>
																	</c:otherwise>
																</c:choose> <br> <small class="text-muted"> Reported
																	on <abbr data-toggle="tip"
																	data-original-title="${Issues.ISSUES_REPORTED_DATE}"><c:out
																			value="${Issues.ISSUES_REPORTED_DATE}" /></abbr>
															</small></td>
															<td><c:choose>
																	<c:when test="${Issues.ISSUES_TYPE_ID == 1 || Issues.ISSUES_TYPE_ID == 6}">

																		<c:out value="${Issues.ISSUES_VEHICLE_REGISTRATION}" />
																		<br>
																		<c:out value="${Issues.ISSUES_VEHICLE_GROUP}" />
																	</c:when>
																	<c:when test="${Issues.ISSUES_TYPE_ID == 2}">
																		<c:out value="${Issues.ISSUES_DRIVER_NAME}" />
																	</c:when>
																	<c:otherwise>
																		<c:out value="${Issues.ISSUES_BRANCH_NAME}" />
																		<br>
																		<c:out value="${Issues.ISSUES_DEPARTNMENT_NAME}" />
																	</c:otherwise>
																</c:choose></td>
															<td class="col-sm-3"><small>${Issues.ISSUES_ASSIGN_TO_NAME}</small></td>
															<td class="text-danger">${Issues.ISSUES_DIFFERENCES_DATE}</td>
															<td><c:choose>
																	<c:when test="${Issues.ISSUES_STATUS_ID == 2}">
																		<small class="label label-success"><c:out
																				value="${Issues.ISSUES_STATUS}" /></small>
																	</c:when>
																	<c:when test="${Issues.ISSUES_STATUS_ID == 5}">
																		<small class="label label-danger"><c:out
																				value="${Issues.ISSUES_STATUS}" /></small>
																	</c:when>
																	<c:when test="${Issues.ISSUES_STATUS_ID == 1}">
																		<small class="label label-info"><c:out
																				value="${Issues.ISSUES_STATUS}" /></small>
																	</c:when>
																	<c:otherwise>
																		<small class="label label-warning"><c:out
																				value="${Issues.ISSUES_STATUS}" /></small>
																	</c:otherwise>
																</c:choose></td>

														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</c:if>
									
									<c:if test="${NotFound}">
									<script>								
										$(".invoice").addClass("hide");
										setTimeout(function() {validateReport();}, 500);
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Report.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"/>"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		});
	</script>
		

</div>