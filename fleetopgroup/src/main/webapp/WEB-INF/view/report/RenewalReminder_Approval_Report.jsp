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
					/ <a href="<c:url value="/RRR.in"/>">Renewal Reminder Report</a> /<span>Renewal
						Reminder Report</span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('advanceTable', 'RR Approval Report')">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
					</button>
					<a href="<c:url value="/Report"/>">Cancel</a>

				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_RENEWAL')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		
		<sec:authorize access="hasAuthority('VIEW_AP_RR_REPORT')">
						<!-- Approval  Renewal Reminder  -->
						<div class="panel box box-success">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#RRCompFour">Approval Renewal Reminder Report </a>
								</h4>
							</div>
							<!-- <div id="RRCompFour" class="panel-collapse collapse"> -->
								<div class="box-body">
									<div class="form-horizontal ">
										<!-- Show Group Search Fuel Range -->
										<form action="RRApprovalReport" method="post">
											<!-- vehicle Group Service -->
											<div class="row1">
												<label class="L-size control-label"> Group : <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<input type="hidden" id="VehicleGroupRRG"
														name="vehicleGroupId" style="width: 100%;"
														placeholder="Please Enter 2 or more Group Name" />
												</div>
											</div>

											<!-- Renewal Type Select -->
											<div class="row1">
												<label class="L-size control-label">Renewal Type <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<input type="hidden" id="from2" name="renewalTypeId"
														style="width: 100%;"
														placeholder="Please Enter 2 or more Renewal Name" />
												</div>
											</div>
											<!-- Renewal Sub Type Select -->
											<div class="row1">
												<label class="L-size control-label">Renewal Sub Type
													<abbr title="required">*</abbr>
												</label>

												<div class="I-size">
													<select style="width: 100%;" name="renewal_Subid" id='to2'
														required>
													</select>
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
														<input type="text" id="RenewalComRangeRRG"
															class="form-text" name="RR_COMPLIANCE_DATE"
															required="required"
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
																class="btn btn-success" onclick="return validateApprovalRenewalReminderReport();">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>

										</form>
										<!-- end Show Group Search RR Range -->
									</div>
							<!-- 	</div> -->
							</div>
						</div>
					</sec:authorize>

		<sec:authorize access="hasAuthority('VIEW_RENEWAL')">
			<div id="div_print">

				<div id="div_print">
					<c:if test="${!empty renewal}">
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
												<table style="width: 95%">
													<tbody>
														<tr>
															<td align="center"><span class="text-bold">
																	Renewal Reminder Report <c:out value=" ${SEARCHDATE}" />
															</span></td>
														</tr>
													</tbody>
												</table>
												<table id="advanceTable" style="width: 95%"
													class="table table-hover table-bordered table-striped">
													<thead>
														<tr>
															<th>ID</th>
															<th>Vehicle Name</th>
															<th>Renewal Name</th>
															<th>Validity From</th>
															<th>Validity To</th>
															<th>Receipt NO</th>
															<th>Amount</th>
															<th>Paid By</th>
														</tr>
													</thead>
													<tbody>


														<c:forEach items="${renewal}" var="renewal">
															<tr data-object-id="" class="ng-scope">
																<td><a target="_blank"
																	href="showRenewalReminder.in?renewal_id=${renewal.renewal_id}"
																	data-toggle="tip"
																	data-original-title="Click Renewal Details"><c:out
																			value="RR-${renewal.renewal_R_Number}" /> </a></td>
																<td><c:out value="${renewal.vehicle_registration}" /></td>

																<td><c:out value="${renewal.renewal_type}" />-<c:out
																		value="${renewal.renewal_subtype}" /></td>

																<td><c:out value="${renewal.renewal_from}" /></td>
																<td><c:out value="${renewal.renewal_to}" /></td>
																<td><a target="_blank"
																	href="showRenewalReminder.in?renewal_id=${renewal.renewal_id}"
																	data-toggle="tip"
																	data-original-title="Click Renewal Details"><c:out
																			value="${renewal.renewal_receipt}" /> </a></td>
																<td><c:out value="${renewal.renewal_Amount}" /></td>
																<td><c:out value="${renewal.renewal_paidby}" /></td>

															</tr>

														</c:forEach>


														<tr class="workorder_repair_totals">
															<th class="text-right" colspan="6"><b>Total
																	Amount :</b></th>

															<td><i class="fa fa-inr"></i> ${RRAmount}</td>
															<td></td>
														</tr>
													</tbody>
												</table>
											</div>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js" />"></script>
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
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/ValidateApprovalRenewalReminderReport.js"/>"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		});
	</script>
</div>