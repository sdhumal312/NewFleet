<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/CashBook/1.in"/>">Cash Book</a> / <span
						id="NewVehi">New Entries</span>
				</div>
				<div class="col-md-off-5">
						<div class="col-md-3">
							<form action="<c:url value="/searchCashBookShow.in"/>"
								method="post">
								<div class="input-group">
									<span class="input-group-addon"> <span
										aria-hidden="true">C-</span></span> <input class="form-text"
										id="vehicle_registrationNumber" name="Search" type="number"
										min="1" required="required" placeholder="C-ID eg:455">
									<span class="input-group-btn">
										<button type="submit" name="search" id="search-btn"
											class="btn btn-success btn-sm">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
							</form>
						</div>
					<sec:authorize access="hasAuthority('CASHBOOK_VOUCHER_NO_UPDATE')">
						<a class="btn btn-success" data-toggle="modal" data-target="#editTaskRemark" data-whatever="@mdo">Edit Voucher No.</a>
						<div class="modal fade" id="editTaskRemark" role="dialog">
							<div class="modal-dialog">
								<div class="modal-content">
										<div class="panel panel-default">
											<div class="panel-heading clearfix">
												<h3 class="panel-title">Edit Cash Voucher No</h3>
											</div>
											<div class="panel-body">
												<div class="form-horizontal">
													<br>
													<div class="row1">
														<div class="L-size">
															<label> Voucher No : </label>
														</div>
														<div class="I-size">
															<input type="text" class="form-text" name="voucherNo" id="updatedVoucherNo"
																value="<c:out value="${CashBook.CASH_VOUCHER_NO}"></c:out>" />
														</div>
													</div>
												</div>
												<div class="modal-footer">
													<input class="btn btn-success"
														onclick="editVoucherNo(${CashBook.CASHID})" name="commit"
														value="Edit Voucher No" id="editVoucherBtn"
														data-dismiss="modal" disabled="disabled">
													<button type="button" class="btn btn-link"
														data-dismiss="modal">Close</button>
												</div>
											</div>
										</div>
								</div>
							</div>
						</div>
					</sec:authorize>
					<sec:authorize access="hasAuthority('DOWNLOAD_CASHBOOK')">
						<c:choose>
							<c:when test="${CashBook.CASH_DOCUMENT == true}">

								<a class="btn btn-default"
									href="${pageContext.request.contextPath}/download/CashDocument/${CashBook.CASH_DOCUMENT_ID}.in">
									<span class="fa fa-download"> Doc</span>
								</a>
							</c:when>
						</c:choose>

						<button class="btn btn-default" onclick="printDiv('div_print')">
							<span class="fa fa-print"> Print</span>
						</button>
					</sec:authorize>
					<a href="<c:url value="/CashBook/1.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<c:if test="${param.success eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This CashBook ID ( C-${successID} ) Created Successfully .
			</div>
		</c:if>
		<sec:authorize access="hasAuthority('ADD_CASHBOOK')">
			<c:choose>
				<c:when test="${CashBook.CASH_APPROVAL_STATUS_ID == 1 }">
					<div class="box">
						<div class="box-header">
							<h4 class="box-title">Cash Book Approval</h4>
						</div>
						<div class="box-body">
							<form action="approvalCashBook.in" method="post"
								name="vehicleStatu" onsubmit="return validateStatus()">


								<input type="hidden" class="form-text" name="CASHID"
									value="${CashBook.CASHID}" /> <input type="hidden"
									class="form-text" name="CASH_BOOK_NO"
									value="${CashBook.CASH_BOOK_NO}" />
								<input type="hidden" class="form-text" name="CASH_BOOK_ID"
									value="${CashBook.CASH_BOOK_ID}" />

								<div class="row">
									<label class="L-size control-label">Approved/Rejected
										Comment : </label>

									<div class="I-size">
										<textarea rows="2" cols="5" class="form-text" maxlength="150"
											required="required" name="CASH_APPROVALCOMMENT"
											placeholder="Enter Comment">Approved By</textarea>
										<label id="errorvStatus" class="error"></label>
									</div>
								</div>
								<div class="row">
									<div class="col-md-offset-5">
										<input type="submit"
											onclick="this.style.visibility = 'hidden'"
											class="btn btn-success" value="APPROVED" />
									</div>
								</div>
							</form>
						</div>
					</div>
				</c:when>
			</c:choose>
		</sec:authorize>

		<sec:authorize access="!hasAuthority('VIEW_CASHBOOK')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_CASHBOOK')">
			<div class="row">
				<div class="col-sm-12 col-md-12 col-xs-12">
					<div class="box box-success">

						<div class="box-body no-padding">
							<div id="div_print">
								<div class="row invoice-info">
									<table class="table table-hover table-striped table-bordered">
										<thead>
											<tr>
												<th colspan="2"><h4>
														<b>CashBook ID : <c:out value="C-${CashBook.CASH_NUMBER}" /></b>
													</h4></th>
												<th colspan="2"><c:choose>
														<c:when
															test="${CashBook.CASH_APPROVAL_STATUS_ID == 2}">
															<h2>
																<span class="label label-success"><i
																	class="fa fa-check-square-o"></i> <c:out
																		value=" ${CashBook.CASH_APPROVAL_STATUS}" /></span>
															</h2>
														</c:when>
														<c:otherwise>
															<h3>
																<span class="label label-danger"><i
																	class="fa fa-dot-circle-o"></i> <c:out
																		value="${CashBook.CASH_APPROVAL_STATUS}" /></span>
															</h3>
														</c:otherwise>
													</c:choose></th>
											</tr>
										</thead>
										<tbody>
											<tr>

												<td class="text-right">Cash Book Name :</td>
												<td colspan="2"><b><c:out
															value="${CashBook.CASH_BOOK_NO}" /></b></td>

											</tr>
											<tr>

												<td class="text-right">Voucher No :</td>
												<input type="hidden" id="cashVoucherNo" value="${CashBook.CASH_VOUCHER_NO}">
												<td id="cashVoucherTD">
													<b><c:out value="${CashBook.CASH_VOUCHER_NO}" /></b>
												</td>
												<td>Date :<b><c:out value="${CashBook.CASH_DATE}" /></b></td>
											</tr>
											<tr>

												<td class="text-right">Debit / Credit :</td>
												<td colspan="2"><b><c:out
															value="${CashBook.CASH_PAYMENT_TYPE}" /></b></td>

											</tr>
											<tr>

												<td class="text-right">Paid To / Received From :</td>
												<td colspan="2"><b><c:out
															value="${CashBook.CASH_PAID_RECEIVED}" /></b></td>

											</tr>
											<tr>

												<td class="text-right">Payment :</td>
												<td colspan="2"><b><c:out
															value="${CashBook.CASH_NATURE_PAYMENT}" /> - <c:out
															value="${CashBook.CASH_PAID_BY}" /></b></td>

											</tr>
											<tr>

												<td class="text-right">Reference No :</td>
												<td colspan="2"><b><c:out
															value="${CashBook.CASH_REFERENCENO}" /></b></td>

											</tr>
											<tr>

												<td class="text-right">GST No :</td>
												<td colspan="2"><b><c:out
															value="${CashBook.CASH_GSTNO}" /></b></td>

											</tr>
											<tr>

												<td class="text-right"
													class="workorder_repair_search_totals">Rupees In Words
													:</td>
												<td colspan="2"><b><c:out
															value="${CashBook.CASH_AMOUNT_WORLD}" /></b></td>

											</tr>
											<tr>
												<td class="text-right">Approval Status :</td>
												<td colspan="2"><c:choose>
														<c:when
															test="${CashBook.CASH_APPROVAL_STATUS_ID == 2}">
															<span class="label label-success"><c:out
																	value="${CashBook.CASH_APPROVAL_STATUS}" /></span>
														</c:when>
														<c:otherwise>
															<span class="label label-danger"><c:out
																	value="${CashBook.CASH_APPROVAL_STATUS}" /></span>
														</c:otherwise>
													</c:choose></td>
											</tr>
											<tr>
												<td class="text-right">Approved By :</td>
												<td colspan="2"><c:out
														value="${CashBook.CASH_APPROVALBY}" /></td>
											</tr>
											<tr>
												<td class="text-right">Approved Date :</td>
												<td colspan="2"><c:out
														value="${CashBook.CASH_APPROVALDATE}" /></td>
											</tr>
											<tr>
												<td class="text-right">Approved Comment :</td>
												<td colspan="2"><c:out
														value="${CashBook.CASH_APPROVALCOMMENT}" /></td>
											</tr>
										</tbody>
										<tfoot>
											<tr class="workorder_repair_search_totals">
												<th class="text-right" colspan="1"><b> Total Amount
														:</b></th>

												<td><i class="fa fa-inr"></i> ${CashBook.CASH_AMT_STR}</td>
											</tr>
										</tfoot>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<small class="text-muted"><b>Created by :</b> <c:out
						value="${CashBook.CREATEDBY}" /></small> | <small class="text-muted"><b>Created
						date: </b> <c:out value="${CashBook.CREATED_DATE}" /></small> | <small
					class="text-muted"><b>Last updated by :</b> <c:out
						value="${CashBook.LASTMODIFIEDBY}" /></small> | <small class="text-muted"><b>Last
						updated date:</b> <c:out value="${CashBook.LASTUPDATED_DATE}" /></small>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js" />"></script>
	<script type="text/javascript">
		$('#Approval').on('click', function() {
			$(this).prop('disabled', true);
		});
	</script>
	<script type="text/javascript">
	jQuery(document).ready(function($) {
		$("#updatedVoucherNo").keydown(function(event) {
			if($("#updatedVoucherNo").val() != $("#cashVoucherNo").val()) {
				$('#editVoucherBtn').prop("disabled", false);						
			} else {
				$('#editVoucherBtn').prop("disabled", true);												
			}
		});

		$("#updatedVoucherNo").keyup(function(event) {					
			if($("#updatedVoucherNo").val() != $("#cashVoucherNo").val()) {
				$('#editVoucherBtn').prop("disabled", false);						
			} else {
				$('#editVoucherBtn').prop("disabled", true);												
			}
		});
	});
	
	 function editVoucherNo(cashBookId) {
		console.log("Cash Id : " + cashBookId);
		showLayer();
		var jsonObject			= new Object();

		jsonObject["updatedVoucherNo"]	= $("#updatedVoucherNo").val();
		jsonObject["cashBookId"]			= cashBookId;

		console.log("jsonObject : " , jsonObject);
		$.ajax({
			url: "cashBookWS/updateCashVoucherNoByCashId.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				console.log("Data : " , data);
				if(data.alreadyExist == true) {
					showMessage("error","Voucher no already exist!");					
				} else if(data.updated == true) {
					showMessage("success","Voucher no updated successfully!");
					$("#cashVoucherTD").html($("#updatedVoucherNo").val());
					$("#cashVoucherNo").val($("#updatedVoucherNo").val());
				}
			},
			error: function (e) {
			}
		});
		setTimeout(function(){ hideLayer(); }, 500);
	}
	</script>
</div>