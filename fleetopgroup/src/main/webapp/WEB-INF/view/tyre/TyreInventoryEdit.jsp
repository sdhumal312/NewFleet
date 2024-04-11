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
						href="<c:url value="/TyreInventory/1.in"/>">New Tyre Inventory</a>
					/
					<c:out value="${TyreInvoice.ITYRE_NUMBER}" />
					/ Edit Tyre Invoice <span id="NewVehi">Create Tyre Inventory</span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/TyreInventory/1.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<c:if test="${param.duplicateInvoiceNumber eq true}">
		<div class="alert alert-danger">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This Invoice Number Already Exists
		</div>
	</c:if>
	<section class="content">
		<sec:authorize access="!hasAuthority('EDIT_INVENTORY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('EDIT_INVENTORY')">
			<div class="row">
				<div class="col-md-offset-1 col-md-9">
					<form id="formEditTyreInventory"
						action="<c:url value="/updateTyreInventory.in"/>" method="post"
						enctype="multipart/form-data" name="formEditTyreInventory"
						role="form" class="form-horizontal" novalidate="novalidate">
						<div class="form-horizontal ">
						<fieldset id="editTyreInvoice">
						<legend>Tyre Manufacturer &amp; Models </legend>
						<input type="hidden" id="anyTyreNumberAsign" name="anyTyreNumberAsign"  value="${anyTyreNumberAsign}" />
						<input type="hidden" id="editAllTyreInventory" name="editAllTyreInventory"  value="${editAllTyreInventory}" />
						<input type="hidden" id="showSubLocation" value="${showSubLocation}">
						<input type="hidden" id="mainLocationIds" value="${mainLocationIds}">
						<input type="hidden" id="validateSubLocation" >
						<input type="hidden" id="editMainLocationId" value="${TyreInvoice.WAREHOUSE_LOCATION_ID}"> 
						<input type="hidden" id="editMainLocation" value="${TyreInvoice.WAREHOUSE_LOCATION}"> 
						<input type="hidden" id="editSubLocationId" value="${TyreInvoice.subLocationId}"> 
						<input type="hidden" id="editSubLocation" value="${TyreInvoice.subLocation}"> 
						<input type="hidden" id="roundupConfig" value="${configuration.roundupAmount}" />
						<c:if test="${!empty TyreAmount}">
							<c:forEach items="${TyreAmount}" var="TyreAmount">
								<div class="box">
									<div class="box-body">
										<div class="panel panel-success">
											<div class="panel-body">
											<input type="hidden" id="STATUS_OF_TYRE" name="STATUS_OF_TYRE" value="${TyreInvoice.STATUS_OF_TYRE}">
											<input type="hidden" name="TYRE_AMOUNT_ID" value="${TyreAmount.ITYRE_AMD_ID}" />
												<div class="row1" id="grpmanufacturer${TyreAmount.ITYRE_AMD_ID}" class="form-group">
													<label class="L-size control-label" for="manufacurer">Tyre
														Manufacturer :<abbr title="required">*</abbr>
													</label>
													<div class="I-size">
														<input type="hidden" id="${TyreAmount.ITYRE_AMD_ID}"
															name="TYRE_MANUFACTURER_ID" style="width: 100%;"
															placeholder="Please Enter 2 or more Tyre Manufacturer Name" />
														<span id="manufacturerIcon" class=""></span>
														<div id="manufacturerErrorMsg" class="text-danger"></div>
													</div>
												</div>
												<div class="row1" id="grptyreModel${TyreAmount.ITYRE_AMD_ID}" class="form-group">
													<label class="L-size control-label" for="tyremodel">Tyre
														Model :<abbr title="required">*</abbr>
													</label>
													<div class="I-size">
														<select style="width: 100%;" id="model${TyreAmount.ITYRE_AMD_ID}" name="TYRE_MODEL_ID">
														</select> <span id="tyreModelIcon" class=""></span>
														<div id="tyreModelErrorMsg" class="text-danger"></div>
													</div>
												</div>
												<div class="row1" id="grptyreSize${TyreAmount.ITYRE_AMD_ID}" class="form-group">
													<label class="L-size control-label" for="tyreSize">Tyre
														Size :<abbr title="required">*</abbr>
													</label>
													<div class="I-size">
														<input type="hidden" id="editTyreSize${TyreAmount.ITYRE_AMD_ID}" name="TYRE_SIZE_ID"
															style="width: 100%;"
															placeholder="Please select Tyre Size" /> <span
															id="tyreSizeIcon" class=""></span>
														<div id="tyreSizeErrorMsg" class="text-danger"></div>
													</div>
												</div>
												<div class="row1" id="lebeleRow${TyreAmount.ITYRE_AMD_ID}">
													<label class="L-size control-label"></label>

													<div class="col-md-9">
														<div class="col-md-1">
															<label class="control-label">Quantity</label>

														</div>
														<div class="col-md-2">
															<label class="control-label">Unit Cost</label>

														</div>
														<div class="col-md-1">
															<label class="control-label">Discount</label>
														</div>
														<div class="col-md-1">
															<label class="control-label">GST</label>
														</div>
														<div class="col-md-2">
															<label class="control-label">Total</label>
														</div>
													</div>
												</div>
												<div class="row1" id="grpquantity${TyreAmount.ITYRE_AMD_ID}" class="form-group">
													<label class="L-size control-label" for="quantity">
													</label>
													<div class="col-md-9">
														<div class="col-md-1">
															<input type="text" class="form-text" name="quantity_many"
																min="0.0" id="quantity${TyreAmount.ITYRE_AMD_ID}" maxlength="4"
																placeholder="ex: 23.78" required="required"
																data-toggle="tip"
																data-original-title="enter Tyre Quantity"
																onkeypress="return isNumberKey(event,this);"
																onkeyup="javascript:sumthere('quantity${TyreAmount.ITYRE_AMD_ID}', 'unitprice${TyreAmount.ITYRE_AMD_ID}', 'discount${TyreAmount.ITYRE_AMD_ID}', 'tax${TyreAmount.ITYRE_AMD_ID}', 'tatalcost${TyreAmount.ITYRE_AMD_ID}');"
																ondrop="return false;"> <span id="quantityIcon"
																class=""></span>
															<div id="quantityErrorMsg" class="text-danger"></div>
														</div>
														<div class="col-md-2">
															<input type="text" class="form-text"
																name="unitprice_many" id="unitprice${TyreAmount.ITYRE_AMD_ID}" maxlength="10"
																min="0.0" placeholder="Unit Cost" required="required" pattern="^\d*(\.\d{0,2})?$"
																data-toggle="tip" data-original-title="enter Unit Price"
																onkeypress="return isNumberKeyWithDecimal(event,id);"
																onkeyup="javascript:sumthere('quantity${TyreAmount.ITYRE_AMD_ID}', 'unitprice${TyreAmount.ITYRE_AMD_ID}', 'discount${TyreAmount.ITYRE_AMD_ID}', 'tax${TyreAmount.ITYRE_AMD_ID}', 'tatalcost${TyreAmount.ITYRE_AMD_ID}');"
																ondrop="return false;"> <span id="unitpriceIcon"
																class=""></span>
															<div id="unitpriceErrorMsg" class="text-danger"></div>
														</div>

														<div class="col-md-1">
															<input type="text" class="form-text" name="discount_many"
																min="0.0" id="discount${TyreAmount.ITYRE_AMD_ID}" maxlength="5"
																placeholder="Discount" required="required"
																data-toggle="tip" data-original-title="enter Discount"
																onkeypress="return isNumberKeyWithDecimal(event,id);"
																onkeyup="javascript:sumthere('quantity${TyreAmount.ITYRE_AMD_ID}', 'unitprice${TyreAmount.ITYRE_AMD_ID}', 'discount${TyreAmount.ITYRE_AMD_ID}', 'tax${TyreAmount.ITYRE_AMD_ID}', 'tatalcost${TyreAmount.ITYRE_AMD_ID}');"
																ondrop="return false;"><span id="discountIcon"
																class=""></span>
															<div id="discountErrorMsg" class="text-danger"></div>

														</div>
														<div class="col-md-1">
															<input type="text" class="form-text" name="tax_many"
																id="tax${TyreAmount.ITYRE_AMD_ID}" maxlength="5" min="0.0" placeholder="GST"
																required="required" data-toggle="tip"
																data-original-title="enter GST"
																onkeypress="return isNumberKeyWithDecimal(event,id);"
																onkeyup="javascript:sumthere('quantity${TyreAmount.ITYRE_AMD_ID}', 'unitprice${TyreAmount.ITYRE_AMD_ID}', 'discount${TyreAmount.ITYRE_AMD_ID}', 'tax${TyreAmount.ITYRE_AMD_ID}', 'tatalcost${TyreAmount.ITYRE_AMD_ID}');"
																ondrop="return false;"><span id="taxIcon"
																class=""></span>
															<div id="taxErrorMsg" class="text-danger"></div>
														</div>
														<div class="col-md-2">
															<input type="text" class="form-text" maxlength="8" name="tatalcost"
																value="0.0" min="0.0" id="tatalcost${TyreAmount.ITYRE_AMD_ID}" readonly="readonly"
																data-toggle="tip" data-original-title="Total Cost"
																onkeypress="return isNumberKey(event,this);"
																ondrop="return false;">
														</div>
													</div>
													<br> 
<%-- 													<div class="row1">
													<div class="input_fields_wrap">
														<button onclick="removeModel(${TyreAmount.ITYRE_AMD_ID});" class="remove_field"
															data-toggle="tip"
															data-original-title="Remove">
															<font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font>
														</button>
													</div>
												</div>
 --%>
												</div>
											</div>
										</div>
									</div>
								</div>
								</c:forEach>
							</c:if>	
							
							</fieldset>
						
							<fieldset>
								<legend>Tyre Inventory Details</legend>
								<div class="box">
									<div class="box-body">
										
										<div class="row1" class="form-group">
											<div  id="grppartLocation">
												<label class="L-size control-label" for="warehouselocation">Warehouse
													location :<abbr title="required">*</abbr>
												</label>
												<div class="col-md-3">
													<input type="hidden" name="ITYRE_ID" value="${TyreInvoice.ITYRE_ID}"> 
													<input type="hidden" id="ITYRE_NUMBER" name="ITYRE_NUMBER" value="${TyreInvoice.ITYRE_NUMBER}"> 
													<%-- <input type="text" class="form-text" name="WAREHOUSE_LOCATION" readonly="readonly" required="required"
														value="${TyreInvoice.WAREHOUSE_LOCATION}" placeholder="Please Enter 2 or more location Name" /> 
														<span id="partLocationIcon" class=""></span>
													<div id="partLocationErrorMsg" class="text-danger"></div> --%>
													<input type="hidden"  readOnly = "readonly"  style="width: 100%;" id="warehouselocation" name = "WAREHOUSE_LOCATION_ID" />
												</div>
											</div>
											
											<div id="subLocation" class="form-group" style="display:none">
												<label class="L-size control-label" for="location">Sub location :<abbr title="required">*</abbr>
												</label>
												<div class="col-md-3">
													<input type="hidden" name="subLocationId"
														id="subLocationId" style="width: 100%;"
														required="required" 
														placeholder="Please Enter 2 or more location Name" /> <span
														id="partLocationIcon" class=""></span>
													<div id="partLocationErrorMsg" class="text-danger"></div>
												</div>
											</div>
										</div>
										
										<div class="row1">
											<label class="L-size control-label">Vendor :<abbr
												title="required">*</abbr></label>
											<div class="col-md-3" id="vendorSelect">
												<div class="col-md-9">
													<input type="hidden" id="Venid" required="required"
														value="${TyreInvoice.VENDOR_ID}" />
													<%-- --%>
													<input type="hidden" id="Venname" required="required"
														value="${TyreInvoice.VENDOR_NAME} - ${TyreInvoice.VENDOR_LOCATION}" />
													<%-- --%>
													<input type="hidden" id="selectVendor" name="VENDOR_ID"
														style="width: 100%;" required="required"
														value="${TyreInvoice.VENDOR_ID}"
														placeholder="${TyreInvoice.VENDOR_NAME}" /> <label
														class="error" id="errorVendorSelect"> </label>
												</div>
												<div class="col-md-1">
													<a class=" btn btn-link "
														onclick="visibility('vendorEnter', 'vendorSelect');">
														<i class="fa fa-plus"> New</i>
													</a>
												</div>
											</div>
											<div id="vendorEnter" class="contact_Hide">
												<div class="col-md-3">
													<div class="col-md-9">
														<input class="form-text row1" name="VENDOR_NAME"
															value="${TyreInvoice.VENDOR_NAME}" maxlength="25"
															type="text" id="enterVendorName"
															placeholder="Enter Part Vendor Name"
															onkeypress="return IsVendorName(event);"
															ondrop="return false;"> <label class="error"
															id="errorVendorName" style="display: none"> </label> <input
															class="form-text row1" name="VENDOR_LOCATION"
															value="${TyreInvoice.VENDOR_LOCATION}" maxlength="25"
															type="text" id="enterVendorLocation"
															placeholder="Enter Part Vendor Location"
															onkeypress="return IsVendorLocation(event);"
															ondrop="return false;"> <label class="error"
															id="errorVendorLocation" style="display: none"> </label>
													</div>
													<div class="col-md-1">
														<a class=" btn btn-link col-sm-offset-1"
															onclick="visibility('vendorEnter', 'vendorSelect');">
															<i class="fa fa-minus"> Select</i>
														</a>

													</div>

												</div>
											</div>
											
											<label class="L-size control-label">Modes of Payment
												<abbr title="required">*</abbr>
											</label>
											<div class="col-md-3" id="paymentDiv">
												<select class="form-text" name="PAYMENT_TYPE_ID" id="renPT_option1" required="required">
													<option  selected="selected" value="${TyreInvoice.PAYMENT_TYPE_ID}">${TyreInvoice.PAYMENT_TYPE} </option>
													<option value="1">Cash</option>
													<option value="2">CREDIT</option>
													<option value="11">UPI</option>
													<option value="3">NEFT</option>
													<option value="4">RTGS</option>
													<option value="5">IMPS</option>
													<option value="6">DD</option>
													<option value="7">CHEQUE</option>
													<option value="10">ON ACCOUNT</option>
												</select>
											</div>
											
										</div>
										
										<div class="row1">
											<label class="L-size control-label">Invoice Number:</label>
											<div class="col-md-3">
												<input type="text" class="form-text" name="INVOICE_NUMBER"
													value="${TyreInvoice.INVOICE_NUMBER}" maxlength="25"
													onkeypress="return IsInvoicenumber(event);"
													ondrop="return false;"> <label class="error"
													id="errorInvoicenumber" style="display: none"> </label>
											</div>
											
											<div  id="grpinvoiceDate" class="form-group">
												<label class="L-size control-label" for="invoiceDate">Invoice
													Date :<abbr title="required">*</abbr>
												</label>
												<div class="col-md-3">
													<div class="input-group input-append date" id="paidDate">
														<input type="text" class="form-text" name="INVOICE_DATE"
															id="invoiceDate" value="${TyreInvoice.INVOICE_DATE}"
															required="required" data-inputmask="'alias': 'dd-mm-yyyy'"
															data-mask="" /> <span class="input-group-addon add-on">
															<span class="fa fa-calendar"></span>
														</span>
													</div>
													<span id="invoiceDateIcon" class=""></span>
													<div id="invoiceDateErrorMsg" class="text-danger"></div>
												</div>
											</div>
											
										</div>
										
										
										<div class="row1">
										
										<label class="L-size control-label">PO Number :</label>
											<div class="col-md-3">
												<input type="number" class="form-text" name="PO_NUMBER"
													value="${TyreInvoice.PO_NUMBER}" maxlength="25"
													onkeypress="return IsPonumber(event);"
													ondrop="return false;"> <label class="error"
													id="errorPonumber" style="display: none"> </label>
											</div>
										
										
										<c:if test="${!configuration.roundupAmount}">
										
											<label class="L-size control-label">Invoice Amount:</label>
											<div class="col-md-3">
												<input type="number" class="form-text" name="INVOICE_AMOUNT"
													id="invoiceAmount" value="${TyreInvoice.INVOICE_AMOUNT}" maxlength="25"
													 onkeypress="return isNumberKeyWithDecimal(event,id);"
													ondrop="return false;" > 
													<label class="error"
													id="errorInvoicenumber" style="display: none"> </label>
											</div>
											</c:if>
											
											
										</div>
										
										<c:if test="${configuration.roundupAmount}">
										
										<div class="row1">
										
										
											<label class="L-size control-label">Invoice Amount:</label>
											<div class="col-md-3">
												<input type="number" class="form-text" 
													id="invoiceAmount" value="${TyreInvoice.INVOICE_AMOUNT}" readonly="readonly" maxlength="25"
													 onkeypress="return isNumberKeyWithDecimal(event,id);"
													ondrop="return false;" > 
													<label class="error"
													id="errorInvoicenumber" style="display: none"> </label>
											</div>
											
											
											<label class="L-size control-label">Roundup Amount:</label>
											<div class="col-md-3">
												<input type="text" class="form-text" id="roundupTotal" name="INVOICE_AMOUNT"
													 value="${TyreInvoice.INVOICE_AMOUNT}" maxlength="10" required="required"
													 onkeypress="return isNumberKeyWithDecimal(event,id);"
													ondrop="return false;" > 
													<label class="error"
													id="errorInvoicenumber" style="display: none"> </label>
											</div>
										
										
										
										</div>
										</c:if>
										
										
										<input type="hidden" id="tallyIntegrationRequired" value="${configuration.tallyIntegrationRequired}">
										<c:if test="${configuration.tallyIntegrationRequired}">
											<div class="row1" class="form-group">
											<input type="hidden" id="tallyCompanyIdEdit" value="${TyreInvoice.tallyCompanyId}">
											<input type="hidden" id="tallyCompanyNameEdit" value="${TyreInvoice.tallyCompanyName}">
											<label class="L-size control-label" for="tally name">Tally Company Name :<abbr title="required">*</abbr></label>
												<div class="col-md-3">
													<input type="hidden" id="tallyCompanyId" name="tallyCompanyId" style="width: 100%;" value="0"
													  placeholder="Please Enter Tally Company Name" />
												</div>
											</div>
										</c:if>
										<c:if test="${configuration.editTyreDocument}">			
										<fieldset>
											<legend>Upload  Documents</legend>
											<div class="box">
												<div class="box-body">
													<div class="row1" id="grprenewalFile" class="form-group">
														<label class="L-size control-label" for="renewalFile">File
															:</label>
														<div class="I-size">
															<input type="file" id="renewalFile"
																name="input-file-preview" /> <span id="renewalFileIcon"
																class=""></span>
															<div id="renewalFileErrorMsg" class="text-danger"></div>

														</div>
													</div>
												</div>
											</div>
										</fieldset>
										</c:if>
										<c:if test="${!configuration.editTyreDocument}">	
											<input class="hide" type="file" name="input-file-preview">
										</c:if>
										<div class="row1">
											<label class="L-size control-label">Description :</label>
											<div class="I-size">
												<textarea class="form-text" name="DESCRIPTION" rows="3"
													maxlength="150" onkeypress="return IsVendorRemark(event);"
													ondrop="return false;">${TyreInvoice.DESCRIPTION} 
				                                </textarea>
												<label class="error" id="errorVendorRemark"
													style="display: none"> </label>
											</div>
										</div>
									</div>
								</div>
							</fieldset>
							<fieldset class="form-actions">
								<div class="row1">
									<div class="col-md-10 col-md-offset-2">
										<button type="submit" class="btn btn-success" onclick="return validateTyreInvoiceEdit();">Update
											Inventory</button>
										<a class="btn btn-link"
											href="<c:url value="/TyreInventory/1.in"/>">Cancel</a>
									</div>
								</div>
							</fieldset>
						</div>
							<%@ include file="/WEB-INF/view/payment/PaymentDetails.jsp"%>
					</form>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IT/InventoryTyreEdit.js" />"></script>
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
	<script type="text/javascript">
		$(document).ready(function() {
			var VID = $("#Venid").val();
			var Vtext = $("#Venname").val();
			$('#selectVendor').select2('data', {
				id : VID,
				text : Vtext
			});
			var anyTyreNumberAsign		 = ${anyTyreNumberAsign};
			if(!${editAllTyreInventory}){
				$('#editTyreInvoice').hide();
			}
			setDropDownToElements('${bpmsn}');
			setTimeout(function(){ 
				$('#tallyCompanyId').select2('data', {
	                id : $('#tallyCompanyIdEdit').val(),
	                text : $('#tallyCompanyNameEdit').val()
	            });
			}, 200);

		});
		
		
		var myInput = document.querySelector('#roundupTotal');
		myInput.addEventListener("keyup", function(){
 		  myInput.value = myInput.value.replace(/(\.\d{2})\d+/g, '$1');
 		});
		
		$(document).on('keydown', 'input[pattern]', function(e){
			  var input = $(this);
			  var oldVal = input.val();
			  var regex = new RegExp(input.attr('pattern'), 'g');

			  setTimeout(function(){
			    var newVal = input.val();
			    if(!regex.test(newVal)){
			      input.val(oldVal); 
			    }
			  }, 0);
			});
		
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>