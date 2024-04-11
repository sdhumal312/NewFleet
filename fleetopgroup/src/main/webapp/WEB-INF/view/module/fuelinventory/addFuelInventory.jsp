<%@ include file="../../taglib.jsp"%>
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
					/ <span id="NewVehi">Create Tyre Inventory</span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/TyreInventory/1.in"/>">Cancel</a>
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
		<sec:authorize access="!hasAuthority('ADD_TYRE_INVENTORY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('ADD_TYRE_INVENTORY')">
			<div class="row">
				<div class="col-md-offset-1 col-md-9">
					<form id="formTyreInventory"
						action="<c:url value="/saveTyreInventory.in"/>" method="post"
						enctype="multipart/form-data" name="formTyreInventory" role="form"
						class="form-horizontal" novalidate="novalidate">
						<div class="form-horizontal ">
									<input type="hidden" id="unique-one-time-token" name="unique-one-time-token" value="${accessToken}">
									<input type="hidden" id="validateDoublePost" name="validateDoublePost" value="true">
							<fieldset>
								<legend>Search Tyre Manufacturer &amp; Models </legend>
								<div class="box">
									<input type="hidden" id="showSubLocation" value="${showSubLocation}">
									<input type="hidden" id="mainLocationIds" value="${mainLocationIds}">
									<input type="hidden" id="validateSubLocation" >
									<div class="box-body">
										<div class="panel panel-success">
											<div class="panel-body">
												
												<div class="row1" class="form-group">
													<div  id="grpmanufacturer">
														<label class="L-size control-label" for="manufacurer">Tyre
															Manufacturer :<abbr title="required">*</abbr>
														</label>
														<div class="col-md-3">
															<input type="hidden" id="manufacurer"
																name="TYRE_MANUFACTURER_ID" style="width: 100%;"
																placeholder="Please Enter 2 or more Tyre Manufacturer Name" />
															<span id="manufacturerIcon" class=""></span>
															<div id="manufacturerErrorMsg" class="text-danger"></div>
														</div>
													</div>
													
													<div  id="grptyreModel" class="form-group">
														<label class="L-size control-label" for="tyremodel">Tyre
															Model :<abbr title="required">*</abbr>
														</label>
														<div class="col-md-3">
															<select style="width: 100%;" id="tyremodel"
																name=TYRE_MODEL_ID>
															</select> <span id="tyreModelIcon" class=""></span>
															<div id="tyreModelErrorMsg" class="text-danger"></div>
														</div>
													</div>
												</div>
												
												<div class="row1" class="form-group">
													<div id="grptyreSize">
														<label class="L-size control-label" for="tyreSize">Tyre
															Size :<abbr title="required">*</abbr>
														</label>
														<div class="col-md-3">
															<input type="hidden" id="tyreSize" name="TYRE_SIZE_ID"
																style="width: 100%;"
																placeholder="Please select Tyre Size" /> <span
																id="tyreSizeIcon" class=""></span>
															<div id="tyreSizeErrorMsg" class="text-danger"></div>
														</div>
													</div>	
													
													<input type="hidden" id="discountTaxTypId" value="${configuration.discountTaxTypeId}">
													<input type="hidden" id="finalDiscountTaxTypId" name="finalDiscountTaxTypId">
													<label class="L-size control-label" for="payMethod">Discount/GST
														Type :<abbr title="required">*</abbr>
													</label>
													<div class="col-md-3">
														<div class="">
															<div class="btn-group"  >
																<label id="percentId" class="btn btn-default  btn-sm " onclick="selectDiscountTaxType(1, 0);">
																	Percentage
																</label> 
																<label id="amountId" class="btn btn-default  btn-sm" onclick="selectDiscountTaxType(2, 0);"> 
																	Amount
																</label>
															</div>
														</div>
													</div>
													
												</div>
										
												<div class="row1">
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
												<div class="row1" id="grpquantity" class="form-group">
													<label class="L-size control-label" for="quantity">
													</label>
													<div class="col-md-9">
														<div class="col-md-1">
															<input type="text" class="form-text" name="quantity_many"
																min="0.0" id="quantity" maxlength="4"
																placeholder="ex: 23.78" required="required"
																data-toggle="tip" onpaste="return false"
																data-original-title="enter Tyre Quantity"
																onkeypress="return isNumberKey(event,this);"
																onkeyup="javascript:sumthereTypeWise('quantity', 'unitprice', 'discount', 'tax', 'tatalcost', 0);"
																ondrop="return false;"> <span id="quantityIcon"
																class=""></span>
															<div id="quantityErrorMsg" class="text-danger"></div>
														</div>
														<div class="col-md-2">
															<input type="text" class="form-text" onpaste="return false"
																name="unitprice_many" id="unitprice" maxlength="7"
																min="0.0" placeholder="Unit Cost" required="required"
																data-toggle="tip" data-original-title="enter Unit Price"
																onkeypress="return isNumberKeyWithDecimal(event,this.id)"
																onkeyup="javascript:sumthereTypeWise('quantity', 'unitprice', 'discount', 'tax', 'tatalcost', 0);"
																ondrop="return false;"> <span id="unitpriceIcon"
																class=""></span>
															<div id="unitpriceErrorMsg" class="text-danger"></div>
														</div>

														<div class="col-md-1">
															<input type="text" class="form-text" name="discount_many"
																min="0.0" id="discount" maxlength="5" onpaste="return false"
																placeholder="Discount"  value="0"
																data-toggle="tip" data-original-title="enter Discount"
																onkeypress="return isNumberKeyWithDecimal(event,this.id)"
																onkeyup="javascript:sumthereTypeWise('quantity', 'unitprice', 'discount', 'tax', 'tatalcost', 0);"
																ondrop="return false;"><span id="discountIcon"
																class=""></span>
															<div id="discountErrorMsg" class="text-danger"></div>

														</div>
														<div class="col-md-1">
															<input type="text" class="form-text" name="tax_many"
																id="tax" maxlength="5" min="0.0" placeholder="GST"
																 data-toggle="tip" value="0"
																data-original-title="enter GST" onpaste="return false"
																onkeypress="return isNumberKeyWithDecimal(event,this.id)"
																onkeyup="javascript:sumthereTypeWise('quantity', 'unitprice', 'discount', 'tax', 'tatalcost', 0);"
																ondrop="return false;"><span id="taxIcon"
																class=""></span>
															<div id="taxErrorMsg" class="text-danger"></div>
														</div>
														<div class="col-md-2">
															<input type="text" class="form-text" maxlength="8" name="tatalcost"
																value="0.0" min="0.0" id="tatalcost" readonly="readonly"
																data-toggle="tip" data-original-title="Total Cost"
																onkeypress="return isNumberKey(event,this);"
																ondrop="return false;">
														</div>
													</div>
													<br> <label class="error" id="errorINEACH"
														style="display: none"></label>

												</div>
												<div class="row1">
													<div class="input_fields_wrap">
														<button class="add_field_button btn btn-info"
															data-toggle="tip"
															data-original-title="Click add one more part">
															<i class="fa fa-plus"></i> Add More Model
														</button>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</fieldset>
							<fieldset>
								<legend>Tyre Inventory Details</legend>
								<div class="box">
									<div class="box-body">
									
										<c:if test="${configuration.showTyreStatusAtAdd}">			
											<div class="row1">
												<label class="L-size control-label">Status of Tyre
													<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<select class="form-text" name="STATUS_OF_TYRE"
														id="STATUS_OF_TYRE" required="required">
														<option value="0">New</option>
														<option value="1">Retread</option>
													</select>
												</div>
											</div>
										</c:if>
										
										<div class="row1">	
											<div id="grppartLocation" class="form-group">
												<label class="L-size control-label" for="warehouselocation">Warehouse location :<abbr title="required">*</abbr>
												</label>
												<div class="col-md-3">
													<input type="hidden" name="WAREHOUSE_LOCATION_ID"
														id="warehouselocation" style="width: 100%;"
														required="required" onchange="showSubLocationDropDown();"
														placeholder="Please Enter 2 or more location Name" /> <span
														id="partLocationIcon" class=""></span>
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
											<label class="L-size control-label"> Tyre Vendor :<abbr
												title="required">*</abbr></label>
											<div class="col-md-3" id="vendorSelect">
												<input type="hidden" id="selectVendor" name="VENDOR_ID"
													style="width: 100%;" required="required" value="0"
													placeholder="Please Select Vendor Name" /> <label
													class="error" id="errorVendorSelect"> </label>
											</div>
											
											<label class="L-size control-label">Modes of Payment
												<abbr title="required">*</abbr>
											</label>
											<div class="col-md-3">
												<select class="form-text" name="PAYMENT_TYPE_ID"
													id="renPT_option" required="required">
													<option value="1">Cash</option>
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
											
											<label class="L-size control-label">PO Number :</label>
											<div class="col-md-3">
												<input type="text" class="form-text" name="PO_NUMBER" onpaste="return false" 
													maxlength="25" onkeypress="return IsPonumber(event);"
													ondrop="return false;"> <label class="error"
													id="errorPonumber" style="display: none"> </label>
											</div>
										</div>
										
										<div class="row1">
											<label class="L-size control-label">Invoice Number:</label>
											<div class="col-md-3">
												<input type="text" class="form-text" name="INVOICE_NUMBER"
													maxlength="25" onkeypress="return IsInvoicenumber(event);"
													ondrop="return false;"> <label class="error"
													id="errorInvoicenumber" style="display: none"> </label>
											</div>
											
											<div id="grpinvoiceDate" class="form-group">
												<label class="L-size control-label" for="invoiceDate">Invoice
													Date :<abbr title="required">*</abbr>
												</label>
												<div class="col-md-3">
													<div class="input-group input-append date" id="opendDate">
														<input type="text" class="form-text" name="INVOICE_DATE"
															id="invoiceDate" required="required"
															data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
															class="input-group-addon add-on"> <span
															class="fa fa-calendar"></span>
														</span>
													</div>
													<span id="invoiceDateIcon" class=""></span>
													<div id="invoiceDateErrorMsg" class="text-danger"></div>
												</div>
											</div>
										</div>
										
										<div class="row1">
											<label class="L-size control-label">Invoice Amount:</label>
											<div class="col-md-3">
												<input type="text" class="form-text" name="INVOICE_AMOUNT"
													value="0" id="invoiceAmount" maxlength="25" onpaste="return false"
													onkeypress="return isNumberKeyWithDecimal(event,this.id)"
													ondrop="return false;"> <label class="error"
													id="errorInvoicenumber" style="display: none"> </label>
											</div>
											
											<input type="hidden" id="tallyIntegrationRequired" value="${configuration.tallyIntegrationRequired}">
											<c:if test="${configuration.tallyIntegrationRequired}">
												<div  id="grpmanufacturer" class="form-group">
												<label class="L-size control-label" for="manufacurer">Tally Company Name :<abbr title="required">*</abbr></label>
													<div class="col-md-3">
														<input type="hidden" id="tallyCompanyId" name="tallyCompanyId" style="width: 100%;" value="0"
														  placeholder="Please Enter Tally Company Name" />
													</div>
												</div>
											</c:if>
										</div>
										
										<c:if test="${configuration.addTyreDocument}">			
										<fieldset >
										<legend>Document</legend>
										<div class="box">
											<div class="box-body">
												<div class="row1">
													<label class="L-size control-label" for="fuel_partial">Part
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
										</c:if>
										<c:if test="${!configuration.addTyreDocument}">	
											<input class="hide" type="file" name="input-file-preview">
										</c:if>
										<div class="row1">
											<label class="L-size control-label">Description :</label>
											<div class="I-size">
												<textarea class="form-text" name="DESCRIPTION" rows="3"
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
										<button type="submit" class="btn btn-success" id="saveTyreInventory" onclick="return validateTyreInventorySave();">Create
											Inventory</button>
										<a class="btn btn-link"
											href="<c:url value="/TyreInventory/1.in"/>">Cancel</a>
									</div>
								</div>
							</fieldset>
						</div>
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
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>