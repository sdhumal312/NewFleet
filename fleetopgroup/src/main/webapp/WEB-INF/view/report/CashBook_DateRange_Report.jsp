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
				<div class="pull-right" id="dateSelectorPrint" style="display: none;">
					<div style="display: inline-block;">
					
						<form action="SRICashBookdateRangeReport" method="post">
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
						<form action="SRICashBookdateRangeReport" method="post">
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
							onclick="advanceTableToExcel('advanceTable', 'CashBook DateRange Report')">
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
			
			<!--SRS Travels Cash Book Date Range Report Start By Dev Yogi-->
								<sec:authorize access="hasAuthority('VIEW_CB_DA_REPORT')">
						<div class="panel box box-warning">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#collapseCashBook">Cash Book Date Range Report</a>
								</h4>
							</div>
							<div id="collapseCashBook" class="panel-collapse collapse">
								<div class="box-body">
									<form action="CashBookdateRangeReport" method="post">
										<div class="form-horizontal ">
											<div class="row1">
												<label class="L-size control-label" for="issue_vehicle_id">Cash
													Book No :<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<input type="hidden" id="CashBookNameList"
														name="CASH_BOOK_ID" style="width: 100%;"
														required="required"
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
													<div class="input-group input-append date" id="renewal_to">
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
							</div>
						</div>
					</sec:authorize>
			
			
			<!--SRS Travels Cash Book Date Range Report End By Dev Yogi-->
			
			
		</sec:authorize>
		
		
		<!-- yy  start-->
							<sec:authorize access="hasAuthority('VIEW_CB_DA_REPORT')">
						<div class="panel box box-warning">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#collapseCashBook">Cash Book Date Range Report</a>
								</h4>
							</div>
							<!-- <div id="collapseCashBook" class="panel-collapse collapse"> -->
								<div class="box-body">
									<form action="SRICashBookdateRangeReport" method="post">
										<div class="form-horizontal ">
											<div class="row1">
												<label class="L-size control-label" for="issue_vehicle_id">Cash
													Book No :<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<input type="hidden" id="CashBookNameList"
														name="CASH_BOOK_ID" style="width: 100%;"
														required="required"
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
													<div class="input-group input-append date" id="renewal_to">
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
																class="btn btn-success" onclick="return validateCashBookDateRangeReport();">
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
		<!-- yy end-->

		<sec:authorize access="hasAuthority('VIEW_CASHBOOK')">
			<div id="div_print">
				<section class="invoice">
					<div class="row invoice-info">
						<div class="col-xs-12">
							<div class="table-responsive">
								<c:if test="${!empty CreditList || !empty DebitList}">
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
														<th colspan="2" style="font-size: 12px;" align="center">${CASHBOOK_NAME}
															Date: ${CASHBOOK_DATE} Cash Book : ${CashBookStatus}</th>
													</tr>
													<tr class="workorder_repair_search_totals">
														<th class="text-right" colspan="1"><b> Opening
																Balance :</b></th>
														<td><i class="fa fa-inr"></i> ${OpenBalance}</td>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td>
															<table style="width: 100%"
																class="table table-hover table-striped">
																<thead>
																	<tr>
																		<th colspan="3">Credit</th>

																	</tr>
																</thead>
																<tbody>
																	<%
																		Integer CreditCount = 1;
																	%>
																	<c:if test="${!empty CreditList}">
																	<c:forEach items="${CreditList}" var="CreditList">
																		<tr>
																			<td>
																				<%
																					out.println(CreditCount);
																								CreditCount += 1;
																				%>
																			</td>
																			<td><a target="_blank"
																				href="<c:url value="/showCashBook.in?Id=${CreditList.CASHID}"/>"><c:out
																						value="C-${CreditList.CASH_NUMBER}" /></a> - <c:out
																					value="${CreditList.CASH_VOUCHER_NO}" /> <br>
																				<ul class="list-inline no-margin">
																					<li><c:out
																							value="${CreditList.CASH_NATURE_PAYMENT}" /> - <c:out
																							value="${CreditList.CASH_PAID_RECEIVED}" /><br>
																							<c:if test="${configuration.viewCashReference}">
																							   <c:out value="${CreditList.CASH_REFERENCENO}" escapeXml="false"/>
																							</c:if>	
																							</li>

																				</ul></td>
																			<td><i class="fa fa-inr"></i> <c:out
																					value=" ${CreditList.CASH_AMT_STR}" /></td>
																		</tr>
																	</c:forEach>
																</tbody>
																<tfoot>
																	<tr>
																		<th class="text-right" colspan="2"><b> Credit
																				Total : </b></th>
																		<td><i class="fa fa-inr"></i> ${CreditTotal}</td>
																	</tr>
																</tfoot>
															</table> </c:if>
														</td>
														<td><c:if test="${!empty DebitList}">
																<table style="width: 100%"
																	class="table table-hover table-striped">
																	<thead>
																		<tr>
																			<th colspan="3">Debit</th>

																		</tr>
																	</thead>
																	<tbody>
																		<%
																		Integer hitsCount = 1;
																	%>
																		<c:forEach items="${DebitList}" var="DebitList">
																			<tr>
																				<td>
																					<%
																					out.println(hitsCount);
																								hitsCount += 1;
																				%>
																				</td>
																				<td><a target="_blank"
																					href="<c:url value="/showCashBook.in?Id=${DebitList.CASHID}"/>"><c:out
																							value="C-${DebitList.CASH_NUMBER}" /></a> - <c:out
																						value="${DebitList.CASH_VOUCHER_NO}" /> <br>
																					<ul class="list-inline no-margin">
																						<li><c:out
																								value="${DebitList.CASH_NATURE_PAYMENT}" /> - <c:out
																								value="${DebitList.CASH_PAID_RECEIVED}" /><br>
																							<c:if test="${configuration.viewCashReference}">	
																								<c:out value="${DebitList.CASH_REFERENCENO} " escapeXml="false"/>	
																							</c:if>	
																						</li>

																					</ul></td>
																				<td><i class="fa fa-inr"></i> <c:out
																						value=" ${DebitList.CASH_AMT_STR}" /></td>
																			</tr>
																		</c:forEach>
																	</tbody>
																	<tfoot>
																		<tr>
																			<th class="text-right" colspan="2"><b> Debit
																					Total : </b></th>

																			<td><i class="fa fa-inr"></i> ${DebitTotal}</td>
																		</tr>
																	</tfoot>
																</table>
																
																</c:if></td>
													</tr>
													<tr>
														<th style="font-size: 12px;" align="center"
															class="text-right" colspan="2"><b> <c:out
																	value=" ${BalanceWorld}" />
														</b></th>
													</tr>
													<tr style="font-size: 12px;" align="center">
														<th class="text-right" colspan="1"><b> Balance :</b></th>
														<td style="font-size: 12px;" align="center"><i
															class="fa fa-inr"></i> ${Balance}</td>
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

	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
			
		});
		var found = '${Found}';
		console.log("Found ", found);
		if(found == 'true' || found == true){
			$('#dateSelectorPrint').show();
		}
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/ValidateCashBookDateRangeReport.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script>

</div>
