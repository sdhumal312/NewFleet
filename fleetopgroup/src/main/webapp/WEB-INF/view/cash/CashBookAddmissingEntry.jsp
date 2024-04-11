<%@ include file="taglib.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/CashBookEntry/1.in"/>">Cash Book</a> / <span
						id="NewVehi">New Entries</span>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_CASHBOOK')">
						<a class="btn btn-warning btn-sm"
							href="<c:url value="/closeCashBookEntry.in"/>"> Close CashBook
							Balance </a>

					</sec:authorize>
					<a href="<c:url value="/CashBookEntry/1.in"/>">Cancel</a>
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
					<c:if test="${param.success eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This CashBook Created Successfully.
						</div>
					</c:if>

					<c:if test="${param.danger eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This CashBook Voucher Number Already Exists
						</div>
					</c:if>
					<c:if test="${param.closeLastDayBalance eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							Please Close ${lastday} Last date Cash Book Balance account...
						</div>
					</c:if>
					<c:if test="${param.closedCB eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This CashBook Date Already Closed Balance.. You can't add
							Entries.
						</div>
					</c:if>
					<c:if test="${param.sequenceNotFound eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							Sequence Not Found Please Contact To System Administrator !
						</div>
					</c:if>
					<form action="<c:url value="/saveMissingCashBookEntry.in"/>"
						method="post" enctype="multipart/form-data" name="formFuel"
						role="form" class="form-horizontal">
						<div class="form-horizontal ">
						<input type="hidden" id="unique-one-time-token" name="unique-one-time-token" value="${accessToken}">
						<input type="hidden" id="validateDoublePost" name="validateDoublePost" value="true">
						<input type="hidden" id="missingCashbookBackDateEnd" value="${missingCashbookBackDateEnd}">
						<input type="hidden" id="missingCashbookBackDateStart" value="${missingCashbookBackDateStart}">
						<input type="hidden" id="allowZeroAmountCashBook"  value="${configuration.allowZeroAmountCashBook}">
						
							<fieldset>
								<legend>Add Missing Cash Book Entries</legend>
								<div class="box">
									<div class="box-body">
										<div class="row1">
											<label class="L-size control-label" for="issue_vehicle_id">Closed
												Cash Book No :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
											<input type="hidden" id="isAutoVoucherNumber"  value="${configuration.isAutoVoucherNumber}">
												<select class="select2" style="width: 100%;"
													name="CASH_BOOK_ID" id="CashBookNumber" required="required">
													<c:if test="${!empty CashBook}">
													<c:forEach items="${CashBook}" var="CashBook">
													    <c:choose>
													    	<c:when test ="${configuration.checkAuthorityForCashBook}">
														    	<c:set var="cleanedCashBookName" value="${fn:replace(CashBook.CASHBOOK_NAME, ' ', '_')}" />
																<c:set var="newCashBookName" value="${fn:replace(cleanedCashBookName, '-', '_')}" />
																<c:set var="authority" value="VIEW_${newCashBookName}" />
															   <sec:authorize access="hasAuthority('${authority}')">
													                 <option value="${CashBook.NAMEID}">
													                 <c:out value="${CashBook.CASHBOOK_NAME}" />
													         	     </option>
													            </sec:authorize>
													    	</c:when>
													        <c:otherwise>
													            <option value="${CashBook.NAMEID}">
													                <c:out value="${CashBook.CASHBOOK_NAME}" />
													            </option>
													        </c:otherwise>
													    </c:choose>
													</c:forEach>
													</c:if>
												</select> 
												<input type="hidden" name="CASH_BOOK_NO" id="CASH_BOOK_NO"/>
												<label class="error" id="errorVendorName"
													style="display: none"> </label>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="issue_vehicle_id"></label>
											<div class="I-size">
												<a href="javascript:click_Debit()"
													class="btn btn-success btn-lg " role="button" id="Debit"
													aria-disabled="true">Debit / Payment </a> <a
													href="javascript:click_Credit()" id="Credit"
													class="btn btn-warning btn-lg " role="button"
													aria-disabled="true">Credit / Receipt</a>
											</div>
										</div>
										<div class="row1">
											<div id="EnterPayment">
												<!-- payment Tyre -->
												<input type="hidden" name="PAYMENT_TYPE_ID"
													required="required" value="1">

											</div>
										</div>
										<div id="HideDibit" style="display: none;">
										
										<c:if test="${configuration.isAutoVoucherNumber }">
											<div class="row1">
												<label class="L-size control-label">Cash Voucher Type
													:<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<input type="checkbox" id="voucherType" value="true" name="voucherType" data-toggle="toggle" data-on="Auto" data-off="Manual">
												</div>
									  		</div>
										</c:if>
										
											<div class="row1">
												<label class="L-size control-label">Cash Voucher No
													:<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<!-- voucher -->
													<input type="text" class="form-text" name="CASH_VOUCHER_NO"
														required="required" maxlength="15" id="CASH_VOUCHER_NO"
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
													<div class="input-group input-append date"
														id="CashPaymentDate">
														<input type="text" class="form-text" name="CASH_DATE" id="dateOfPayment"
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
													<input class="form-text" id="CASH_AMOUNT" name="CASH_AMOUNT"
														type="text" maxlength="8" min="0"
														placeholder="20000" required="required"
														onkeypress="return isNumberKeyWithDecimal(event,id);"
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
													<input class="form-text" id="CASH_PAID_RECEIVED"
														maxlength="240" name="CASH_PAID_RECEIVED" type="text"
														required="required"
														onkeypress="return IsPAIDRECEIVED(event);"
														placeholder="Enter Details" ondrop="return false;">
													<label class="error" id="errorPAIDRECEIVED"
														style="display: none"> </label>
												</div>
											</div>
											<div class="row1" id="paymentDebit" style="display: none;">
												<label class="L-size control-label">Payment Type :<abbr
													title="required">*</abbr>
												</label>
												<div class="I-size">
													<input type="text" id="NatureDebitPayment"
														name="CASH_NATURE_PAYMENT_ID" style="width: 100%;"
														placeholder="Please Enter 2 or more Name" />

												</div>
											</div>
											<div class="row1" id="paymentCredit" style="display: none;">
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
												<label class="L-size control-label">Paid By :<abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<input class="form-text" id="fuel_reference" maxlength="50"
														name="CASH_PAID_BY" value="${PaidBy}" readonly="readonly"
														type="text" required="required" placeholder="Paid By name"
														onkeypress="return IsPAIDBY(event);"
														ondrop="return false;"> <label class="error"
														id="errorPAIDBY" style="display: none"> </label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Reference No :</label>
												<div class="I-size">
													<input class="form-text" id="fuel_reference" maxlength="50"
														name="CASH_REFERENCENO" type="text"
														placeholder="enter reference number"
														onkeypress="return IsPAIDBY(event);"
														ondrop="return false;"> <label class="error"
														id="errorPAIDBY" style="display: none"> </label>
												</div>
											</div>
											<c:if test="${configuration.showGstNoCol}">
												<div class="row1">
													<label class="L-size control-label">GST No :</label>
													<div class="I-size">
														<input class="form-text" id="fuel_GST" maxlength="50"
															name="CASH_GSTNO" type="text"
															placeholder="Enter GST Number"
															onkeypress="return IsPAIDBY(event);"
															ondrop="return false;"> <label class="error"
															id="errorPAIDBY" style="display: none"> </label>
													</div>
												</div>
											</c:if>
											
										</div>
									</div>
								</div>
							</fieldset>
							<fieldset>
								<legend>Document</legend>
								<div class="box">
									<div class="box-body">
										<div class="row1">
											<label class="L-size control-label" for="fuel_partial">CashBook
												Document :</label>
											<div class="I-size">
												<input type="file" name="input-file-preview"
													id="renewalFile" /> <span id="renewalFileIcon" class=""></span>
												<div id="renewalFileErrorMsg" class="text-danger"></div>
												<span class="help-block">Add an optional document</span>
											</div>
										</div>
									</div>
								</div>
							</fieldset>
							<div class="row1" id="saveCashBook">
								<div class="col-md-offset-5 col-md-7">
									<input class="btn btn-success"
										onclick="return validateAddCashBook();" name="commit"
										type="submit" value="Save Missing CashBook Entries"><a
										class="btn btn-info"
										href="<c:url value="/missingAddCashBookEntry.in"/>">Reset</a>
								</div>
							</div>
						</div>
					</form>
				</div>
			</sec:authorize>
		</div>
	</section>
	<script type="text/javascript">
		$(document).ready(function() {
			var missingCashbookBackDateEnd 			= $("#missingCashbookBackDateEnd").val();
			var missingCashbookBackDateStart 		= $("#missingCashbookBackDateStart").val();
			
			 $("#CashPaymentDate").datepicker({
			        autoclose: !0,
			        format: "dd-mm-yyyy",
			        startDate: missingCashbookBackDateEnd,
			        endDate: missingCashbookBackDateStart,
			    });	 
			$('#CASH_BOOK_NO').val($("#CashBookNumber option:selected").text().trim());
			$('#CashBookNumber').on('change', function() {
				$('#CASH_BOOK_NO').val($("#CashBookNumber option:selected").text().trim());
			});
		})
	</script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>	
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
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/CB/CashBookValidation.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>	
</div>
