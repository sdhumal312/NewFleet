<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/SRICashBook/1.in"/>">Cash Book</a> / <span
						id="NewVehi">Edit Entries</span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/SRICashBook/1.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<sec:authorize access="!hasAuthority('ADD_CASHBOOK')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('ADD_CASHBOOK')">
				<div class="col-md-offset-1 col-md-9">
					<form action="SRIupdateCashBook.in" method="post">
						<div class="form-horizontal ">
							<fieldset>
								<legend>Cash Book Info</legend>
								<div class="box">
									<div class="box-body">
										<div class="row1">
											<label class="L-size control-label" for="issue_vehicle_id">Cash
												Book No :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<!-- cashbook Id -->
												<input type="hidden" name="CASHID" class="form-text"
													value="${CashBook.CASHID}" required="required"
													readonly="readonly">
												<input type="hidden" name="CASH_NUMBER" class="form-text"
													value="${CashBook.CASH_NUMBER}" required="required"
													readonly="readonly">
												<input type="hidden" name="CASH_BOOK_ID" class="form-text"
													value="${CashBook.CASH_BOOK_ID}" required="required"
													readonly="readonly">
												<!-- cash book name -->
												<input type="text" name="CASH_BOOK_NO" class="form-text"
													value="${CashBook.CASH_BOOK_NO}" required="required"
													readonly="readonly"> <label class="error"
													id="errorVendorName" style="display: none"> </label>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="issue_vehicle_id"></label>
											<div class="I-size">
												<c:choose>
													<c:when test="${CashBook.PAYMENT_TYPE_ID == 1}">
														<a class="btn btn-success btn-lg " role="button"
															id="Debit" aria-disabled="true">Debit / Payment </a>
														<a id="Credit" class="btn btn-warning btn-lg disabled"
															role="button" aria-disabled="true">Credit / Receipt</a>
													</c:when>
													<c:otherwise>
														<a class="btn btn-success btn-lg disabled" role="button"
															id="Debit" aria-disabled="true">Debit / Payment </a>
														<a id="Credit" class="btn btn-warning btn-lg "
															role="button" aria-disabled="true">Credit / Receipt</a>
													</c:otherwise>
												</c:choose>

											</div>
										</div>
										<div class="row1">
											<div id="EnterPayment">
												<!-- payment Tyre -->
												<input type="hidden" name="CASH_PAYMENT_TYPE"
													id="paymentType" class="form-text" readonly="readonly"
													required="required" value="${CashBook.CASH_PAYMENT_TYPE}">
												<input type="hidden" name="PAYMENT_TYPE_ID"
													id="PAYMENT_TYPE_ID" class="form-text" readonly="readonly"
													required="required" value="${CashBook.PAYMENT_TYPE_ID}">

											</div>
										</div>
										<div id="HideDibit">
											<div class="row1">
												<label class="L-size control-label">Cash Voucher No
													:<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<!-- voucher -->
													<input type="text" readonly="readonly" class="form-text"
														name="CASH_VOUCHER_NO" value="${CashBook.CASH_VOUCHER_NO}"
														required="required" maxlength="15"
														placeholder="Enter Voucher No"
														onkeypress="return IsVOUCHER(event);"
														ondrop="return false;"> <label class="error"
														id="errorVOUCHER" style="display: none"> </label>
												</div>
											</div>

											<div class="row1">
												<label class="L-size control-label">Date Of Payment
													<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<div class="input-group input-append date" id="">
														<input type="text" class="form-text" name="CASH_DATE"
															readonly="readonly" value="${CashBook.CASH_DATE}"
															placeholder="dd-mm-yyyy" required="required"
															data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
															class="input-group-addon add-on"> <span
															class="fa fa-calendar"></span>
														</span>
													</div>
													<p class="help-block">ex: Max 3 days only</p>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Amount :<abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<input class="form-text" id="fuel_price" name="CASH_AMOUNT"
														value="${CashBook.CASH_AMT_STR}" type="text" maxlength="8"
														min="0" max="20000" placeholder="20000"
														required="required"
														onkeypress="return isNumberKey(event,this);"
														ondrop="return false;"> <label class="error"
														id="errorPrice" style="display: none"></label>
													<p class="help-block">ex: 5000.00</p>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label" id="paid">Paid
													To :<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<input class="form-text" id="fuel_reference"
														value="${CashBook.CASH_PAID_RECEIVED}" maxlength="240"
														name="CASH_PAID_RECEIVED" type="text" required="required"
														onkeypress="return IsPAIDRECEIVED(event);"
														placeholder="Enter Details" ondrop="return false;">
													<label class="error" id="errorPAIDRECEIVED"
														style="display: none"> </label>
												</div>
											</div>
											<div class="row1" id="paymentDebitEdit"
												style="display: none;">
												<label class="L-size control-label">Payment Type :<abbr
													title="required">*</abbr>
												</label>
												<div class="I-size">
													<input type="text" id="NatureDebitPayment"
														name="CASH_NATURE_PAYMENT_ID" style="width: 100%;"
														placeholder="Please Enter 2 or more Name" />

												</div>
											</div>
											<div class="row1" id="paymentCreditEdit"
												style="display: none;">
												<label class="L-size control-label">Receipt Type :<abbr
													title="required">*</abbr>
												</label>
												<div class="I-size">
													<input type="text" id="NatureCreditPayment"
														name="CASH_NATURE_PAYMENT_ID" style="width: 100%;"
														placeholder="Please Enter 2 or more Name" />

												</div>
											</div>
											<div class="row1">

												<div class="I-size">
													<input type="hidden" class="form-text" id="OldPaymentType"
														value="${CashBook.CASH_NATURE_PAYMENT}" />
													<input type="hidden" class="form-text" id="OldPaymentTypeId"
														value="${CashBook.CASH_NATURE_PAYMENT_ID}" />

												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Paid By :<abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<input class="form-text" id="fuel_reference" maxlength="50"
														name="CASH_PAID_BY" type="text" required="required"
														placeholder="Paid By name"
														value="${CashBook.CASH_PAID_BY}" readonly="readonly"
														onkeypress="return IsPAIDBY(event);"
														ondrop="return false;"> <label class="error"
														id="errorPAIDBY" style="display: none"> </label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Reference No :</label>
												<div class="I-size">
													<input class="form-text" id="fuel_reference" maxlength="50"
														name="CASH_REFERENCENO" value="${CashBook.CASH_REFERENCENO}" type="text"
														placeholder="enter reference number"
														onkeypress="return IsPAIDBY(event);"
														ondrop="return false;"> <label class="error"
														id="errorPAIDBY" style="display: none"> </label>
												</div>
											</div>
											<div class="row1">
												<div class="col-md-offset-5 col-md-7">
													<input class="btn btn-success" name="commit" type="submit"
														value="Update CashBook"> <a class="btn btn-info"
														href="<c:url value="/SRIaddCashBook.in"/>">Cancel</a>
												</div>
											</div>
										</div>
									</div>
								</div>
							</fieldset>
						</div>
					</form>
				</div>
			</sec:authorize>
		</div>
	</section>
	<script type="text/javascript">
	$(document).ready(function(){$("#CashPaymentDate").datepicker({autoclose:!0,todayHighlight:!0,format:"dd-mm-yyyy",startDate:"-3d",endDate:"+3d"})});
	</script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/CB/CashBook.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript">
	$(document).ready(function(){var a=$("#paymentType").val();switch(a){case"DEBIT":document.getElementById("paymentDebitEdit").style.display="block",$("#paymentCreditEdit").html(""),document.getElementById("Credit").className+=" disabled";var b=$("#OldPaymentType").val(); var x = $('#OldPaymentTypeId').val(); $("#NatureDebitPayment").select2("data",{id:x,text:b});break;case"CREDIT":document.getElementById("paymentCreditEdit").style.display="block",$("#paymentDebitEdit").html(""),document.getElementById("Debit").className+=" disabled";var c=$("#OldPaymentType").val(); var y = $('#OldPaymentTypeId').val(); $("#NatureCreditPayment").select2("data",{id:y,text:c})}});
	</script>
</div>