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
						href="<c:url value="/vendorHome.in"/>">Vendors</a> / <span
						id="NewVehi">Vendor Payment</span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/vendorHome.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<c:if test="${param.success eq true}">
		<div class="alert alert-success">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This Inventory Created Successfully.
		</div>
	</c:if>
	<c:if test="${param.danger eq true}">
		<div class="alert alert-danger">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This Inventory Already Exists
		</div>
	</c:if>
	<section class="content">
		<div class="row">
				<div class="col-md-offset-1 col-md-9">
						<div class="form-horizontal ">
							<fieldset>
								<div class="box">
									<div class="box-body">

										<div class="row1">

												<c:if test="${Configuration.vehicleGroup}">
													<label class="L-size control-label">Group : <abbr
														title="required">*</abbr>
													</label>
													<div class="I-size">
														<input type="hidden" id="vehicleGroup"
															name="VEHICLE_GROUP_ID" style="width: 100%;"
															required="required"
															placeholder="Please Enter 2 or more Vehicle Name" />
														<p class="help-block">Select One Vehicle Group</p>
													</div>
												</c:if>
											</div>
									
										<div class="row1">
											<label class="L-size control-label">Vendor :<abbr
												title="required">*</abbr></label>
											<div class="I-size" id="vendorSelect">
												<input type="hidden" id="selectVendor" name="Vendor_id"
													style="width: 100%;" value="0"
													placeholder="Please Select Vendor Name" />
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Opening Balance :</label>
											<div class="I-size">
												<input type="number"  name="openingBalance"
													id="openingBalance" class="form-text" disabled="disabled" />
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Transaction Type :<abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<select class="form-text" name="transactionTypeId"
													id="transactionTypeId" onclick="showHidePaymentDetails()">
													<option value="0">-- SELECT TRANSACTION TYPE --</option>
													<option value="1">CREDIT</option>
													<option value="2">DEBIT</option>
												</select>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Amount :<abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input type="text"  name="transactionAmount"  onpaste="return false" 
													id="transactionAmount" value="0" class="form-text"
													maxlength=10 onkeypress="return isNumberKeyWithDecimal(event,this.id)"
													 />
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">GST Amount :</label>
											<div class="I-size">
												<input type="text"  name="gstAmount"  onpaste="return false"
													id="gstAmount" value="0" class="form-text" maxlength=10
													onkeypress="return isNumberKeyWithDecimal(event,this.id)" />
											</div>
										</div>
										<div class="hide invoiceDetails">
											<div class="row1">
												<label class="L-size control-label">Invoice Number :<abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<input type="text"  name="invoiceNumber"
														id="invoiceNumber" class="form-text" />
												</div>
	
											</div>
											<div class="row1" id="grpinvoiceDate" class="form-group">
												<label class="L-size control-label" for="invoiceDate">Invoice
													Date :<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<div class="input-group input-append date" id="opendDate">
														<input type="text" class="form-text" name="invoice_date"
															id="invoiceDate" data-inputmask="'alias': 'dd-mm-yyyy'"
															data-mask="" /> <span class="input-group-addon add-on">
															<span class="fa fa-calendar"></span>
														</span>
													</div>
												</div>
											</div>
										</div>
										<div class="hide paymentDetails">
											<div class="row1">
												<label class="L-size control-label">Payment Type :</label>
												<div class="I-size">
													<select class="form-text" name="paymentTypeId"
														id="paymentTypeId" onclick="showHideChequeDetails()" >
														<option value="1">CASH</option>
														<option value="7">CHEQUE</option>
													</select>
												</div>
											</div>
											<div class="hide cashVoucherDetails"> 
												<div class="row1">
													<label class="L-size control-label">Cash Voucher Number:</label>
													<div class="I-size">
														<input type="text"  name="cashVoucherNumber"
															id="cashVoucherNumber" class="form-text" />
													</div>
												</div>
												<div class="row1" id="grpinvoiceDate" class="form-group">
													<label class="L-size control-label" for="paymentDate">Payment
														Date :<abbr title="required">*</abbr>
													</label>
													<div class="I-size">
														<div class="input-group input-append date" id="paymentDate">
															<input type="text" class="form-text" name="payment_date"
																id="payment_Date" data-inputmask="'alias': 'dd-mm-yyyy'"
																data-mask="" /> <span class="input-group-addon add-on">
																<span class="fa fa-calendar"></span>
															</span>
														</div>
													</div>
												</div>
											</div>
											<div class="hide chequeDetails">
												<div class="row1">
													<label class="L-size control-label">Cheque Number:</label>
													<div class="I-size">
														<input type="text"  name="chequeNumber"
															id="chequeNumber" class="form-text" />
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
										<button type="button" class="btn btn-success" onclick="addVendorPayment();">Add Vendor Payment</button>
										<a class="btn btn-link"
											href="<c:url value="/vendorHome.in"/>">Cancel</a>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VP/VendorPayment.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Report.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("dd-mm-yyyy", {
				placeholder : "dd-mm-yyyy"
			}), $("[data-mask]").inputmask()
		});
	</script>
</div>