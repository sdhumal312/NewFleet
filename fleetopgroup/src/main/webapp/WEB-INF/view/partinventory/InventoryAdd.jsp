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
						href="<c:url value="/NewInventory/1.in"/>">New Inventory</a> / <span
						id="NewVehi">New Create Inventory</span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/NewInventory/1.in"/>">Cancel</a>
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
		<sec:authorize access="!hasAuthority('ADD_INVENTORY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('ADD_INVENTORY')">
			<div class="row">
				<div class="col-md-offset-1 col-md-9">
					<form id="formPartInventory"
						action="<c:url value="/saveInventory.in"/>" method="post"
						enctype="multipart/form-data" name="formPartInventory" role="form"
						class="form-horizontal">
						<div class="form-horizontal ">
							<fieldset>
								<legend>Search Parts </legend>
								<div class="box">
									<input type="hidden" id="NoOfPartsAllowedToAdd" value="${NoOfPartsAllowedToAdd}">
									<input type="hidden" id="addMorePartsAtBottom" value="${addMorePartsAtBottom}">
									<input type="hidden" id="allowNewVendor" value="${configuration.allowNewVendor}">
									<input type="hidden" id="showSubLocation" value="${showSubLocation}">
									<input type="hidden" id="mainLocationIds" value="${mainLocationIds}">
									<input type="hidden" id="validateSubLocation" >
									<input type="hidden" id="unique-one-time-token" name="unique-one-time-token" value="${accessToken}">
									<input type="hidden" id="validateDoublePost" name="validateDoublePost" value="true">
									<input type="hidden" id="roundupConfig" value="${configuration.roundupAmount}" />
									<div class="box-body">
										<div class="panel panel-success">
											<div class="panel-body">
												<div class="row1" id="grpinventoryPart" class="form-group">
													<label class="L-size control-label" for="searchpart">Search
														Parts Number :<abbr title="required">*</abbr>
													</label>
													<div class="I-size">
														<input type="hidden" id="searchpart" name="partid_many"
															style="width: 100%;"
															placeholder="Please Enter 2 or more Part Name or Part Number" />
														<span id="inventoryPartIcon" class=""></span>
														<div id="inventoryPartErrorMsg" class="text-danger"></div>
													</div>
												</div>
												
												<div class="row1" id="grpmanufacturer" class="form-group">
													<label class="L-size control-label" for="manufacturer">Manufacturer
														:<abbr title="required">*</abbr>
													</label>
													<div class="col-md-3">
														<input type="text" class="form-text" name="make_many"
															id="manufacturer" maxlength="50"
															onkeypress="return IsManufacturer(event);"
															ondrop="return false;"> <span
															id="manufacturerIcon" class=""></span>
														<div id="manufacturerErrorMsg" class="text-danger"></div>
														<label class="error" id="errorManufacturer"
															style="display: none"> </label>
													</div>
														
													<input type="hidden" id="discountTaxTypId" value="${configuration.discountTaxTypeId}">
													<input type="hidden" id="finalDiscountTaxTypId" name="finalDiscountTaxTypId">
													<label class="L-size control-label" for="payMethod">Discount/GST
														Type :<abbr title="required">*</abbr>
													</label>
													<div class="col-md-3">
														<div class="">
															<div class="btn-group"  >
																<label id="percentId" class="btn btn-default  btn-sm active" onclick="selectDiscountTaxType(1, 0);">
																	Percentage
																</label> 
																<label id="amountId" class="btn btn-default  btn-sm" onclick="selectDiscountTaxType(2, 0);"> 
																	Amount
																</label>
															</div>
														</div>
													</div>
												</div>
												
												<div class="row1" id="grpquantity" class="form-group">
													<label class="L-size control-label" for="quantity">Quantity
														:</label>
													<div class="col-md-9">
														<div class="col-md-1">
															<input type="text" class="form-text" name="quantity_many"
																min="0.0" id="quantity" maxlength="8"
																placeholder="ex: 23.78" required="required"
																data-toggle="tip"
																data-original-title="enter Part Quantity"
																onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																onkeyup="javascript:sumthereTypeWise('quantity', 'unitprice', 'discount', 'tax', 'tatalcost', 0);"
																ondrop="return false;"> <span id="quantityIcon"
																class=""></span>
															<div id="quantityErrorMsg" class="text-danger"></div>

														</div>
														<div class="col-md-2">
															<input type="text" class="form-text"
																name="unitprice_many" id="unitprice" maxlength="10"
																min="0.0" placeholder="Unit Cost" required="required"
																data-toggle="tip" data-original-title="enter Unit Price"
																onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																onkeyup="javascript:sumthereTypeWise('quantity', 'unitprice', 'discount', 'tax', 'tatalcost', 0);"
																ondrop="return false;"> <span id="unitpriceIcon"
																class=""></span>
															<div id="unitpriceErrorMsg" class="text-danger"></div>
														</div>

														<div class="col-md-1">
															<input type="text" class="form-text" name="discount_many"
																min="0.0" id="discount" maxlength="5"
																placeholder="Discount" required="required"
																data-toggle="tip" data-original-title="enter Discount"
																onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																onkeyup="javascript:sumthereTypeWise('quantity', 'unitprice', 'discount', 'tax', 'tatalcost', 0);"
																ondrop="return false;"> <span id="discountIcon"
																class=""></span>
															<div id="discountErrorMsg" class="text-danger"></div>

														</div>
														<div class="col-md-1">
															<input type="text" class="form-text" name="tax_many"
																id="tax" maxlength="5" min="0.0" placeholder="GST"
																required="required" data-toggle="tip"
																data-original-title="enter GST"
														        onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																onkeyup="javascript:sumthereTypeWise('quantity', 'unitprice', 'discount', 'tax', 'tatalcost', 0);"
																ondrop="return false;"> <span id="taxIcon"
																class=""></span>
															<div id="taxErrorMsg" class="text-danger"></div>
														</div>
														<div class="col-md-2">
															<input type="text" class="form-text" maxlength="10"
																value="0.0" min="0.0" id="tatalcost" readonly="readonly" name="tatalcost"
																data-toggle="tip" data-original-title="Total Cost"
																onkeypress="return isNumberKey(event,this);"
																ondrop="return false;">
														</div>
													</div>

													<br> <label class="error" id="errorINEACH"
														style="display: none"></label>

												</div>
												<div id="moreParts">
													
												</div>
												<div class="row1">
													<div class="input_fields_wrap">
														<button class="add_field_button btn btn-info"
															data-toggle="tip"
															data-original-title="Click add one more part">
															<i class="fa fa-plus"></i> Add More Parts
														</button>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</fieldset>
							<fieldset>
								<legend>Part Info</legend>
								<div class="box">
									<div class="box-body">
										
										<div class="row1">
											<div  id="grppartLocation" class="form-group">
												<label class="L-size control-label" for="location">Warehouse
													location :<abbr title="required">*</abbr>
												</label>
												<div class="col-md-3">
													<select class="select2" name="locationId" id="location" onchange="showSubLocationDropDown();" 
														style="width: 100%;">
														<option value="">-- Please select --></option>
														<c:forEach items="${PartLocationPermission}"
															var="PartLocationPermission">
															<option value="${PartLocationPermission.partLocationId}"><c:out
																	value="${PartLocationPermission.partLocationName}" />
															</option>
														</c:forEach>
													</select> <input type='hidden' id='locationText' name="location"
														value='' /> <span id="partLocationIcon" class=""></span>
													<div id="partLocationErrorMsg" class="text-danger"></div>
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
											<label class="L-size control-label">Invoice Number:</label>
											<div class="col-md-3">
												<input type="text" class="form-text" name="invoice_number"
													maxlength="25" 
													ondrop="return false;"> <label class="error"
													id="errorInvoicenumber" style="display: none"> </label>
											</div>
											
											<div  id="grpinvoiceDate" class="form-group">
												<label class="L-size control-label" for="invoiceDate">Invoice
													Date :<abbr title="required">*</abbr>
												</label>
												<div class="col-md-3">
													<div class="input-group input-append date" id="opendDate">
														<input type="text" class="form-text" name="invoice_date" 
															id="invoiceDate" data-inputmask="'alias': 'dd-mm-yyyy'"
															onkeypress="return isNumberKey(event,this);"
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
											<label class="L-size control-label">Vendor :<abbr
												title="required">*</abbr></label>
											<div class="col-md-3" id="vendorSelect">
												<input type="hidden" id="selectVendor" name="vendorIdStr"
													style="width: 100%;" required="required" 
													placeholder="Please Select Vendor Name" /> <label
													class="error" id="errorVendorSelect"> </label>
											</div>
											
											<label class="L-size control-label">Modes of Payment
												<abbr title="required">*</abbr>
											</label>
											<div class="col-md-3" id="paymentDiv">
												<select class="form-text" name="PAYMENT_TYPE_ID"
													id="renPT_option" required="required">
													<option value="1">Cash</option>
													<option value="2">CREDIT</option>
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
											<label class="L-size control-label" id="target1"
												for="renPT_option">Enter </label>
											<div class="col-md-3">
												<input type="text" class="form-text" name="PAYMENT_NUMBER"
													onkeypress="return IsAlphaNumericPaynumber(event);"
													ondrop="return false;" maxlength="25"> <label
													class="error" id="errorPaynumber" style="display: none">
												</label>
											</div>
											
											<label class="L-size control-label">Labour Charge:</label>
											<div class="col-md-3">
												<input type="number" class="form-text" name="labourCharge"
													id="labourCharge" maxlength="25"
													onkeypress="return isNumberKeyWithDecimal(event,this.id);"
														onkeyup="addLabourToInvoice();"
													ondrop="return false;"> <label class="error"
													id="errorInvoicenumber" style="display: none"> </label>
											</div>
											</div>
											
											
										<div class="row1">
											<label class="L-size control-label">Invoice Amount:</label>
											<c:if test="${configuration.roundupAmount}">
											<div class="col-md-3">
												<input type="number" class="form-text" 
													id="previousInvoice" maxlength="25" readonly="readonly" 
													onkeypress="return isNumberKeyWithDecimal(event,this.id);"
													ondrop="return false;"> 
													
											</div>
											</c:if>
											<c:if test="${!configuration.roundupAmount}">
											<div class="col-md-3">
												<input type="text" class="form-text" name="invoice_amount"
													id="invoiceAmount" maxlength="25"
													onkeypress="return isNumberKeyWithDecimal(event,this.id);"
													ondrop="return false;"> <label class="error"
													id="errorInvoicenumber" style="display: none"> </label>
													<input id="previousInvoice" type="hidden" >
											</div>
											</c:if>
											
											<input type="hidden" id="tallyIntegrationRequired" value="${configuration.tallyIntegrationRequired}">
											<c:if test="${configuration.tallyIntegrationRequired}">
												<div id="grpmanufacturer" class="form-group">
												<label class="L-size control-label" for="manufacurer">Tally Company Name :<abbr title="required">*</abbr></label>
													<div class="col-md-3">
														<input type="hidden" id="tallyCompanyId" name="tallyCompanyId" style="width: 100%;" value="0"
														  placeholder="Please Enter Tally Company Name" />
													</div>
												</div>
											</c:if>
											<c:if test="${configuration.roundupAmount}">
												<label class="L-size control-label">RoundUp Amount:</label>
												<div class="col-md-3">
													<input type="text" class="form-text" name="invoice_amount"
														id="roundupTotal" maxlength="10" min="0.0"
														placeholder="Roundup Amount" required="required"
														data-toggle="tip"
														data-original-title="Enter roundup Amount"
														onkeypress="return isNumberKeyWithDecimal(event,this.id);"
														ondrop="return false;"> <label class="error"
														id="errorInvoicenumber" style="display: none"> </label>

												</div>
											</c:if>
											
										</div>
										
										<c:if test="${configuration.addPartDocument}">
										<fieldset>
										<legend>Document</legend>
											<div class="box">
												<div class="box-body">
													<div class="row1">
														<label class="L-size control-label" for="fuel_partial">Part
															Document :</label>
														<div class="I-size">
															<input type="file" name="input-file-preview" /> 
															<span class="help-block">Add an optional document</span>
														</div>
													</div>
												</div>
											</div>
										</fieldset>
										</c:if>
										<c:if test="${!configuration.addPartDocument}">
											<input class="hide" type="file" name="input-file-preview" >
										</c:if>
										<div class="row1">
											<label class="L-size control-label">Description :</label>
											<div class="I-size">
												<textarea class="form-text" name="description" rows="3"
													maxlength="150" onkeypress="return IsVendorRemark(event);"
													ondrop="return false;"> 
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
										<button type="submit" class="btn btn-success" id="saveInventory" onclick="return validateInventorySave();">Create
											Inventory</button>
										<a class="btn btn-link"
											href="<c:url value="/NewInventory/1.in"/>">Cancel</a>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IT/InventoryValidate.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("dd-mm-yyyy", {
				placeholder : "dd-mm-yyyy"
			}), $("[data-mask]").inputmask()
		});
	</script>
	<script type="text/javascript">
		$(document).ready(
				function() {
					$("#location").select2();
					$("#tagPicker").select2({
						closeOnSelect : !1
					});

					$("#location").change(
							function() {
								$("#locationText").val(
										$("#location").find(":selected").text()
												.trim());
							});
				});
	</script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>  
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>