<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/bootstrap/bootstrap-float-label.min.css" />">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">
<style>
.spanStyle {
color: #2e74e6;
font-size: 18px;
}
</style>
<div class="modal fade" id="paymentDetails" role="dialog">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Payment Details</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
				<input type="hidden" id="bankPaymentTypeId" name="bankPaymentType">
				<input type="hidden" id="allowBankPaymentDetails" name="allowBankPaymentDetails" value="0">
									 <div class="row">
									 	<div class="col col-md-5">
										 <label class="has-float-label">
										   <input type="hidden" id="bankAccountId"
																name="bankAccountId" style="line-height: 30px;font-size: 15px;height: 35px; width: 80%"
																placeholder="Please Enter 2 or more Bank details" />
										    
										    <span class="spanStyle">Company Account Number<abbr title="required">*</abbr></span>
										  </label>
									</div>
									 <div class="col col-md-5">
										 <label class="has-float-label">
										    <input type="text" class="form-text form-control" name="transactionNumber" id="bankTransactionNumber"
										     style="width: 78%"
										     >
										    <span class="spanStyle">Transaction/Reference No</span>
										  </label>
										</div>
									</div>
									<br><br>
									<div class="row">
											<div class="col col-md-5">
										 <label class="has-float-label">
										    <input type="text" class="form-text form-control" name="payerName" id="payerName"
										     style="width: 78%"
										     >
										    <span class="spanStyle">Party Name </span>
										  </label>
										</div>
											<div class="col col-md-4">
										  <label class="has-float-label">
										    <div class="input-group input-append date" id="maxTodayDate">
													<input type="text" class="form-text	form-control invoiceDate" name="transactionDateStr" readonly="readonly"
														id="bankTransactionDate" required="required"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> 
														<span  class="  input-group-addon add-on btn btn-sm"><em class="fa fa-calendar"></em></span>
												</div>
										    <span class="spanStyle">Paid Date <abbr title="required">*</abbr></span>
										  </label>
									</div>
										</div>
										<br><br>
									<div class="row" id="chequeDiv">
										<div class="col col-md-5">
										 <label class="has-float-label">
										    <input type="text" class="form-text form-control" name="partyAccountNumber" id="partyAccountNumber"
										     style="width: 78%"
										     >
										    <span class="spanStyle">Party A/c No </span>
										  </label>
										</div>
										
										<div class="col col-md-5">
										 <label class="has-float-label">
										    <input type="text" class="form-text form-control" name="chequeGivenBy" id="chequeGivenBy"
										     style="width: 78%">
										    <span class="spanStyle">Cheque Given By </span>
										  </label>
										</div>
										</div>
										
											<br><br>
									<div class="row" id="upiDiv">
										<div class="col col-md-5">
										 <label class="has-float-label">
										    <input type="text" class="form-text form-control" name="upiId" id="upiId"
										     style="width: 78%"
										     >
										    <span class="spanStyle" >UPI id </span>
										  </label>
										</div>
											<div class="col col-md-5">
										 <label class="has-float-label">
										    <input type="text" class="form-text form-control" name="mobileNumber" id="mobileNumber"
										     style="width: 78%"
										     >
										    <span class="spanStyle" >Mobile No. </span>
										  </label>
										</div>
									 
									 </div>
									 <br><br>
									 <div class="row">
									 <div class="col col-md-5">
										 <label class="has-float-label">
										<select id="partyBankId" name="partyBankId" class="form-text form-control selectType select2"  style="width: 81%;" ></select>
										 <span class="spanStyle">Party Bank </span>
										  </label>
									 </div>
									 <div class="col col-md-4">
										  <label class="has-float-label">
										    <div class="input-group input-append date" id="maxTodayDate2">
													<input type="text" class="form-text	form-control invoiceDate" name="chequeTransactionDateStr" readonly="readonly"
														id="chequeTransactionDate" required="required"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> 
														<span  class="  input-group-addon add-on btn btn-sm"><em class="fa fa-calendar"></em></span>
												</div>
										    <span class="spanStyle">Cheque/Transaction Date</span>
										  </label>
									</div>
				</div>
				<div class="modal-footer" >
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" id="addButton">Add</button>
				</div>
			</div>
		</div>
	</div>
	</div>
	
	<script>

</script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/payment.js" />"></script>