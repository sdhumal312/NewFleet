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
					/ <a href="<c:url value="/CBR.in"/>">Cash Book Report</a> / <span>CashBook
						Date Report</span>
				</div>
				<div class="pull-right">
					<div style="display: inline-block;">
						<form action="CashdateRangeReport" method="post">
							<input type="hidden" name="CASH_BOOK_ID" required="required"
								value="${CASHBOOK_NAME_ID}" /> <input type="hidden"
								name="CASH_DATE" placeholder="dd-mm-yyyy" required="required"
								value="${YESTERDAY}" />
							<button class="btn btn-default" type="submit">
								<span class="fa fa-backward" aria-hidden="true"></span>
							</button>
						</form>
					</div>
					<div style="display: inline-block;">${CASHBOOK_DATE}</div>
					<div style="display: inline-block;">
						<form action="CashdateRangeReport" method="post">
							<input type="hidden" name="CASH_BOOK_ID" required="required"
								value="${CASHBOOK_NAME_ID}" /> <input type="hidden"
								name="CASH_DATE" placeholder="dd-mm-yyyy" required="required"
								value="${TOMORROW}" />

							<button class="btn btn-default" type="submit">
								<span class="fa fa-forward" aria-hidden="true"></span>
							</button>
						</form>
					</div>
					<div style="display: inline-block; width: 100px"></div>
					<sec:authorize access="hasAuthority('DOWNLOAD_CASHBOOK')">
						<button class="btn btn-default" onclick="printDiv('div_print')">
							<span class="fa fa-print"> Print</span>
						</button>
						<button class="btn btn-default btn-sm"
							onclick="advanceTableToExcel('advanceTable', 'CashBook Date Report')">
							<span class="fa fa-file-excel-o"> Export to Excel</span>
						</button>
					</sec:authorize>
					<a href="<c:url value="/Report"/>">Cancel</a>

				</div>

			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_CASHBOOK')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		
							<sec:authorize access="hasAuthority('VIEW_CA_DA_REPORT')">
						<div class="panel box box-warning">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#collapseCash">Cash Date Range Report</a>
								</h4>
							</div>
							<!-- <div id="collapseCash" class="panel-collapse collapse"> -->
								<div class="box-body">
									<form action="CashdateRangeReport" method="post">
										<div class="form-horizontal ">
											<div class="row1">
												<label class="L-size control-label" for="issue_vehicle_id">Cash
													Book No :<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<input type="hidden" id="CashNameList" name="CASH_BOOK_ID"
														style="width: 100%;" required="required"
														placeholder="Please Enter 2 or more Cash Book Name" />
													<p class="help-block">Select One Or More Vehicle</p>
													<label class="error" id="errorVendorName"
														style="display: none"> </label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Date Of Payment
													<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<div class="input-group input-append date"
														id="renewal_from">
														<input type="text" class="form-text" name="CASH_DATE" id="dateOfPayment"
															placeholder="dd-mm-yyyy" required="required"
															data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
															class="input-group-addon add-on"> <span
															class="fa fa-calendar"></span>
														</span>
													</div>
												</div>
											</div>
											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success" onclick="return validateCashDateRangeReport();"
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

		<sec:authorize access="hasAuthority('VIEW_CASHBOOK')">
			<div id="div_print">
				<section class="invoice">
					<div class="row invoice-info">
						<div class="col-xs-12">
							<div class="table-responsive">
								<c:if test="${!empty cashbook}">
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
																CashBook Date Report <c:out
																	value=" Date - ${CASHBOOK_DATE}" />
														</span></td>
													</tr>
												</tbody>
											</table>
											<table id="advanceTable" style="width: 95%"
												class="table table-hover table-bordered table-striped">
												<thead>
													<tr>
														<th colspan="7" style="font-size: 11px;" align="center">${CASHBOOK_NAME}
															- ${CASHBOOK_DATE}</th>
													</tr>
													<tr>
														<th>No</th>
														<th>ID</th>
														<th>Voucher No</th>
														<th>Reference No</th>
														<th>Payment</th>
														<th>CREDIT</th>
														<th>DEBIT</th>
													</tr>

												</thead>
												<tbody>
													<tr>
														<th colspan="4">Cash Book : ${CashBookStatus}</th>
														<th>Opening Balance :</th>
														<th><c:out value=" ${OpenBalance}" /></th>
														<th></th>
													</tr>

													<%
														Integer hitsCount = 1;
													%>
													<c:forEach items="${cashbook}" var="cashbook">
														<tr>
															<td>
																<%
																	out.println(hitsCount);
																				hitsCount += 1;
																%>
															</td>
															<td><c:out value="C-${cashbook.CASH_NUMBER}" /></td>
															<td><c:out value="${cashbook.CASH_VOUCHER_NO}" /></td>
															<td><c:out value="${cashbook.CASH_REFERENCENO}" escapeXml="false"/></td>
															<td><c:out value="${cashbook.CASH_NATURE_PAYMENT}" /></td>
															<c:choose>
																<c:when test="${cashbook.PAYMENT_TYPE_ID == 1}">
																	<td></td>
																	<td><c:out value=" ${cashbook.CASH_AMT_STR}" /></td>
																</c:when>
																<c:otherwise>
																	<td><c:out value=" ${cashbook.CASH_AMT_STR}" /></td>
																	<td></td>
																</c:otherwise>
															</c:choose>
														</tr>
													</c:forEach>
													<tr class="workorder_repair_search_totals">
														<th class="text-right" colspan="5"
															style="font-size: 11px;"><b> Total :</b></th>
														<td style="font-size: 12px;"><c:out
																value=" ${CreditTotal}" /></td>
														<td style="font-size: 12px;"><c:out
																value=" ${DebitTotal}" /></td>
													</tr>


													<tr class="workorder_repair_search_totals">
														<td class="text-right" colspan="7"><b> <c:out
																	value=" ${BalanceWorld}" />
														</b></td>
													</tr>
													<tr class="workorder_repair_search_totals">
														<th class="text-right" colspan="5"><b> Balance :</b></th>
														<td colspan="2"><i class="fa fa-inr"></i> <c:out
																value=" ${Balance}" /></td>
													</tr>
												</tbody>
											</table>
										</div>
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
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/CB/CashBook.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
	<script type="text/javascript">
		$(function() {

			function cb(start, end) {
				$('#reportrange').val(
						start.format('YYYY-MM-DD') + ' to '
								+ end.format('YYYY-MM-DD'));
			}
			cb(moment().subtract(1, 'days'), moment());

			$('#reportrange').daterangepicker(
					{
						ranges : {
							'Today' : [ moment(), moment() ],
							'Yesterday' : [ moment().subtract(1, 'days'),
									moment().subtract(1, 'days') ],
							'Last 7 Days' : [ moment().subtract(6, 'days'),
									moment() ]
						}
					}, cb);

		});
	</script>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/ValidateCashDateRangeReport.js"/>"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
			
		});
	</script>	
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script>

</div>