<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/vendorHome.in"/>">Vendors</a>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/vendorHome.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<div class="col-md-offset-1 col-md-9">
				<div class="form-horizontal ">
					<fieldset>
						<div class="box">
							<div class="box-body">

								<div class="row1">
									<c:if test="${configuration.vehicleGroup}">
										<label class="L-size control-label">Group :<abbr
											title="required">*</abbr></label>
										<div class="I-size">
											<input type="text" name="VEHICLE_GROUP_ID"
											style="width: 100%;" required="required" id="vehicleGroup1"
											value="${vehicleGroup1}" class="form-text"
											readonly="readonly" />									
										</div>
									</c:if>
								</div>


								<input type="hidden" id="vendorPaymentId"
									value="${vendorPaymentId}">
								<div class="row1">
									<label class="L-size control-label">Vendor :<abbr
										title="required">*</abbr></label>
									<div class="I-size">
										<input class="form-text" type="text" id="selectVendor1"
											value="${selectVendor}" style="width: 100%;"
											disabled="disabled" />
									</div>
								</div>
								<div class="row1">
									<label class="L-size control-label">Transaction Type :<abbr
										title="required">*</abbr></label>
									<div class="I-size">
										<select class="form-text" name="transactionTypeId"
											id="transactionTypeId" disabled="disabled">
											<option value="1">CREDIT</option>
											<option value="2">DEBIT</option>
										</select>
									</div>
								</div>
								<div class="row1">
									<label class="L-size control-label">Amount :<abbr
										title="required">*</abbr></label>
									<div class="I-size">
										<input type="number" name="transactionAmount"
											id="transactionAmount" value="0" class="form-text" />
									</div>
								</div>
								<div class="row1">
									<label class="L-size control-label">GST Amount :</label>
									<div class="I-size">
										<input type="number" name="gstAmount" id="gstAmount" value="0"
											class="form-text" />
									</div>
								</div>
								<div class=" invoiceDetails">
									<div class="row1">
										<label class="L-size control-label">Invoice Number :<abbr
											title="required">*</abbr></label>
										<div class="I-size">
											<input type="text" name="invoiceNumber" id="invoiceNumber"
												class="form-text" />
										</div>
									</div>
									<div class="row1" id="grpinvoiceDate" class="form-group">
										<label class="L-size control-label" for="invoiceDate">Invoice
											Date :<abbr title="required">*</abbr>
										</label>
										<div class="I-size">
											<div class="input-group input-append date" id="invoice_Date">
												<input type="text" class="form-text" name="invoice_date"
													id="invoiceDate" data-inputmask="'alias': 'dd-mm-yyyy'"
													data-mask="" /> <span class="input-group-addon add-on">
													<span class="fa fa-calendar"></span>
												</span>
											</div>
										</div>
									</div>
								</div>
								<div class=" paymentDetails">
									<div class="row1">
										<label class="L-size control-label">Payment Type :</label>
										<div class="I-size">
											<select class="form-text" name="paymentTypeId"
												id="paymentTypeId" onclick="showHideChequeDetails()">
												<option value="1">CASH</option>
												<option value="7">CHEQUE</option>
											</select>
										</div>
									</div>
									<div class="cashVoucherDetails">
										<div class="row1">
											<label class="L-size control-label">Cash Voucher
												Number:</label>
											<div class="I-size">
												<input type="text" name="cashVoucherNumber"
													id="cashVoucherNumber" class="form-text" />
											</div>
										</div>
										<div class="row1" id="grpinvoiceDate" class="form-group">
											<label class="L-size control-label" for="invoiceDate">Payment
												Date :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input class="form-text" type="text" id="payment_Date"
													style="width: 100%;" disabled="disabled" />
											</div>
										</div>
									</div>
									<div class=" chequeDetails">
										<div class="row1">
											<label class="L-size control-label">Cheque Number:</label>
											<div class="I-size">
												<input type="text" name="chequeNumber" id="chequeNumber"
													class="form-text" />
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Cheque Date :</label>
											<div class="I-size">
												<div class="input-group input-append date" id="datePicker">
													<input type="text" class="form-text" name="cheque_date"
														id="chequeDate" data-inputmask="'alias': 'dd-mm-yyyy'"
														data-mask="" /> <span class="input-group-addon add-on">
														<span class="fa fa-calendar"></span>
													</span>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</fieldset>
					<fieldset class="form-actions">
						<div class="row1">
							<div class="col-md-10 col-md-offset-2">
								<button type="button" class="btn btn-success"
									onclick="updateVendorPayment();">Update Vendor Payment</button>
								<a class="btn btn-link" href="<c:url value="/vendorHome.in"/>">Cancel</a>
							</div>
						</div>
					</fieldset>
				</div>
			</div>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VP/VendorPaymentEdit.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<%-- <script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Report.js"/>"></script> --%>
	<script type="text/javascript">
		$(document).ready(function() {
			getVendorPaymentDetailsById($("#vendorPaymentId").val());	
		});
	</script>
</div>