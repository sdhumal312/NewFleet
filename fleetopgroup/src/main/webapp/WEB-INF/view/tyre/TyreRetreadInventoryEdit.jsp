<%@ include file="taglib.jsp"%>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> / 
					<a href="<c:url value="/TyreRetreadNew/1.in"/>">New Tyre Retread Inventory</a> / Edit Tyre Retread Invoice 
				</div>
				<div class="pull-right">
					<a href="<c:url value="/TyreRetreadNew/1.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('EDIT_INVENTORY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('EDIT_INVENTORY')">
			<div class="row">
				<div class="col-md-offset-1 col-md-9">
					<div class="form-horizontal ">
						<fieldset>
							<legend>TyreRetread Inventory Details</legend>
							<div class="box">
								<div class="box-body">
									<div class="row1" id="grppartLocation" class="form-group">
									<input type="hidden" id="TRID" name="TRID" value="${Retread.TRID}">
										<label class="L-size control-label" for="warehouselocation">Tyre
											Retread Number :<abbr title="required">*</abbr>
										</label>
										<div class="I-size">
											<input type="text" class="form-text"  readonly="readonly" required="required" value="${Retread.TRNUMBER}" placeholder="Please Enter 2 or more location Name" /> 
											<span id="partLocationIcon" class=""></span>
										</div>
									</div>
									
									<div class="row1" id="grppartLocation" class="form-group">
										<label class="L-size control-label" for="warehouselocation">Tyre
											Retread Status :<abbr title="required">*</abbr>
										</label>
										<div class="I-size">
											<input type="text" class="form-text"  readonly="readonly" required="required" value="${Retread.TR_STATUS}" placeholder="Please Enter 2 or more location Name" /> 
											<span id="partLocationIcon" class=""></span>
										</div>
									</div>
									 <div class="row1">
										<label class="L-size control-label">Tyre Vendor :<abbr
											title="required">*</abbr></label>
										<div class="I-size" id="vendorSelect">
											<div class="col-md-9">
												<input type="hidden" id="selectVendor"
													name="TR_VENDOR_ID" value="0" style="width: 100%;"
													required="required"
													placeholder="Please Select Vendor Name" /> <label
													class="error" id="errorVendorSelect"> </label>
											</div>
										</div>
									</div>
									<div class="row1">
									<input type="hidden" id="vendorTypeId" value="${Retread.TR_VENDOR_ID}">
									<input type="hidden" id="vendorTypeName" value="${Retread.TR_VENDOR_NAME}">
										<label class="L-size control-label">Modes of
											Payment <abbr title="required">*</abbr>
										</label>
										<div class="I-size">
											<select class="form-text" name="TR_PAYMENT_TYPE_ID"
												id="renPT_option" required="required"> value="${Retread.TR_PAYMENT_TYPE}"
												<option value="1">Cash</option>
											</select>
										</div>
									</div>
									 <div class="row1">
										<label class="L-size control-label" id="target1"
											for="renPT_option">Enter </label>
										<div class="I-size">
											<input type="text" class="form-text" name="TR_PAYMENT_NUMBER" id="paymentNumber"
												onkeypress="return IsAlphaNumericPaynumber(event);" value="${Retread.TR_PAYMENT_NUMBER}"
												ondrop="return false;" maxlength="25"> <label
												class="error" id="errorPaynumber" style="display: none">
											</label>
										</div>
									</div>
									
									<div class="row1" id="grpinvoiceDate" class="form-group">
									<input type="hidden" id="hiddenRetreadDate" value="${Retread.TR_OPEN_DATE}">
									<input type="hidden" id="hiddenRetreadRequiredDate" value="${Retread.TR_REQUIRED_DATE}">
										<label class="L-size control-label" for="invoiceDate">Retread Date :<abbr title="required">*</abbr> </label>
										<div class="I-size">
											<div class="input-group input-append date" id="paidDate">
												<input type="text" class="form-text" name="INVOICE_DATE"
													id="retreadDate" 
													required="required" data-inputmask="'alias': 'dd-mm-yyyy'"
													data-mask="" /> <span class="input-group-addon add-on">
													<span class="fa fa-calendar"></span>
												</span>
											</div>
											<span id="invoiceDateIcon" class=""></span>
											<div id="invoiceDateErrorMsg" class="text-danger"></div>
										</div>
									</div>
									<div class="row1" id="grpinvoiceDate" class="form-group">
										<label class="L-size control-label" for="invoiceDate">Required Date :<abbr title="required">*</abbr> </label>
										<div class="I-size">
											<div class="input-group input-append date" id="paidDate">
												<input type="text" class="form-text" name="INVOICE_DATE"
													id="requiredRetreadDate" 
													required="required" data-inputmask="'alias': 'dd-mm-yyyy'"
													data-mask="" /> <span class="input-group-addon add-on">
													<span class="fa fa-calendar"></span>
												</span>
											</div>
											<span id="invoiceDateIcon" class=""></span>
											<div id="invoiceDateErrorMsg" class="text-danger"></div>
										</div>
									</div>
									<div class="row1" id="grppartLocation" class="form-group">
										<label class="L-size control-label" for="warehouselocation">Quote No
										</label>
										<div class="I-size">
											<input type="text" class="form-text"  id="quoteNo" required="required" value="${Retread.TR_QUOTE_NO}" placeholder="Please Enter 2 or more location Name" /> 
											<span id="partLocationIcon" class=""></span>
										</div>
									</div>
									<div class="row1" id="grppartLocation" class="form-group">
										<label class="L-size control-label" for="warehouselocation">Manual No
										</label>
										<div class="I-size">
											<input type="text" class="form-text"  id="manualNo" required="required" value="${Retread.TR_MANUAL_NO}" placeholder="Please Enter 2 or more location Name" /> 
											<span id="partLocationIcon" class=""></span>
										</div>
									</div>
									
								</div>
							</div>
						</fieldset>
						<fieldset class="form-actions">
							<div class="row1">
								<div class="col-md-10 col-md-offset-2">
									<button type="submit" class="btn btn-success">Update
										Inventory</button>
									<a class="btn btn-link"
										href="<c:url value="/TyreInventory/1.in"/>">Cancel</a>
								</div>
							</div>
						</fieldset>
					</div>
				</div>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IT/InventoryTyreValidate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IT/InventoryTyreRetreadEdit.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("dd-mm-yyyy", {
				placeholder : "dd-mm-yyyy"
			}), $("[data-mask]").inputmask()
		});
	</script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#location").select2();
			$("#tyremodel").select2();
			$("#tagPicker").select2({
				closeOnSelect : !1
			})
		});
	</script>
	<!-- <script type="text/javascript">
		$(document).ready(function() {
			var VID = $("#Venid").val();
			var Vtext = $("#Venname").val();
			$('#selectVendor').select2('data', {
				id : VID,
				text : Vtext
			});
			var anyTyreNumberAsign		 = ${anyTyreNumberAsign};
			if(anyTyreNumberAsign || ! ${editAllTyreInventory}){
				$('#editTyreInvoice').hide();
			}
			setDropDownToElements('${bpmsn}');
		});
	</script> -->
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>