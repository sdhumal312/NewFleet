<%@ include file="taglib.jsp"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
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
	<section class="content">
		<sec:authorize access="!hasAuthority('EDIT_INVENTORY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('EDIT_INVENTORY')">
			<div class="row">
				<div class="col-md-offset-1 col-md-9">
					<form id="formEditPartInventory"
						action="<c:url value="/updatePartInventoryInvoice.in"/>" method="post"
						enctype="multipart/form-data" name="formEditPartInventory"
						role="form" class="form-horizontal" novalidate="novalidate">
						<div class="form-horizontal ">
						<fieldset id="editPartInvoice">
						<legend> </legend>
						<input type="hidden" id="anyPartNumberAsign" name=anyPartNumberAsign  value="${anyPartNumberAsign}" />
						<input type="hidden" id="editAllPartInventory" name="editAllPartInventory"  value="${editAllPartInvoiceInventory}" />
						<input type="hidden" id="allowNewVendor" value="${configuration.allowNewVendor}">
						<input type="hidden" id="showSubLocation" value="${showSubLocation}">
						<input type="hidden" id="mainLocationIds" value="${mainLocationIds}">
						<input type="hidden" id="validateSubLocation" >
						<input type="hidden" id="isEditPartInvoice" value="true" >
						<c:if test="${!empty PartInvoiceAmount}">
							<c:forEach items="${PartInvoiceAmount}" var="PartInvoiceAmount">
								<div class="box">
									<div class="box-body">
										<div class="panel panel-success">
											<div class="panel-body">
											<%-- <input type="hidden" id="STATUS_OF_PART" name="STATUS_OF_PART" value="${PartInvoiceAmount.STATUS_OF_TYRE}"> --%>
											<input type="hidden" name="inventory_id" id="inventoryId" value="${PartInvoiceAmount.inventory_id}" />
											<input type="hidden" name="locationId" value="${PartInvoiceAmount.locationId}" />
											<input type="hidden" name="inventory_all_id" value="${PartInvoiceAmount.inventory_all_id}" />
											<input type="hidden" name="inventory_location_id" value="${PartInvoiceAmount.inventory_location_id}" />
											<input type="hidden" name="purchaseorder_id" value="${PartInvoiceAmount.purchaseorder_id}" />
											<input type="hidden" id="roundupConfig" value="${configuration.roundupAmount}" />
												
												<div class="row1" id="grpmanufacturer${PartInvoiceAmount.inventory_id}" class="form-group">
													<label class="L-size control-label" for="searchpart">Search
														Parts Number :<abbr title="required">*</abbr>
													</label> 
													<div class="I-size">
														<input type="hidden" id="${PartInvoiceAmount.inventory_id}" name="partid"
															style="width: 100%;"
															placeholder="Please Enter 2 or more Part Name or Part Number" />
														<span id="inventoryPartIcon" class=""></span>
														<div id="inventoryPartErrorMsg" class="text-danger"></div>
													</div>
												</div>
												
												<div class="row1" id="grptyreModel${PartInvoiceAmount.inventory_id}" class="form-group">
													<label class="L-size control-label" for="manufacturer">Manufacturer
														:<abbr title="required">*</abbr>
													</label>
													<div class="col-md-3">
														<input type="text" class="form-text" name="make" id="manufacturer${PartInvoiceAmount.inventory_id}"
															 maxlength="50"
															onkeypress="return IsManufacturer(event);"
															ondrop="return false;">
														<span id="manufacturerIcon" class=""></span>
														<div id="manufacturerErrorMsg" class="text-danger"></div>
													</div>
												
													<input type="hidden" id="finalDiscountTaxTypId${PartInvoiceAmount.inventory_id}" name="finalDiscountTaxTypId">
													<label class="L-size control-label" for="payMethod">Discount/GST
														Type :<abbr title="required">*</abbr>
													</label>
													<div class="col-md-3">
														<div class="">
															<div class="btn-group"  >
																<label id="percentId${PartInvoiceAmount.inventory_id}" class="btn btn-default  btn-sm" onclick="selectDiscountTaxTypeEdit(1, ${PartInvoiceAmount.inventory_id});">
																	Percentage
																</label> 
																<label id="amountId${PartInvoiceAmount.inventory_id}" class="btn btn-default  btn-sm" onclick="selectDiscountTaxTypeEdit(2, ${PartInvoiceAmount.inventory_id});"> 
																	Amount
																</label>
															</div>
														</div>
													</div>
													
												</div>
											
												<div class="row1" id="lebeleRow${PartInvoiceAmount.inventory_id}">
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
												<div class="row1" id="grpquantity${PartInvoiceAmount.inventory_id}" class="form-group">
													<label class="L-size control-label" for="quantity">
													</label>
													<div class="col-md-9">
														<div class="col-md-1">
															<input type="text" class="form-text" name="quantity_many"
																min="0.0" id="quantity${PartInvoiceAmount.inventory_id}" maxlength="4"
																placeholder="ex: 23.78" required="required"
																data-toggle="tip"
																data-original-title="enter Tyre Quantity"
																onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																onkeyup="javascript:sumthereTypeWiseEdit('quantity${PartInvoiceAmount.inventory_id}', 'unitprice${PartInvoiceAmount.inventory_id}', 'discount${PartInvoiceAmount.inventory_id}', 'tax${PartInvoiceAmount.inventory_id}', 'tatalcost${PartInvoiceAmount.inventory_id}', '${PartInvoiceAmount.inventory_id}' );"
																ondrop="return false;"> <span id="quantityIcon"
																class=""></span>
															<div id="quantityErrorMsg" class="text-danger"></div>
														</div>
														<div class="col-md-2">
															<input type="text" class="form-text"
																name="unitprice_many" id="unitprice${PartInvoiceAmount.inventory_id}" maxlength="10"
																min="0.0" placeholder="Unit Cost" required="required" pattern="^\d*(\.\d{0,2})?$"
																data-toggle="tip" data-original-title="enter Unit Price"
																onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																onkeyup="javascript:sumthereTypeWiseEdit('quantity${PartInvoiceAmount.inventory_id}', 'unitprice${PartInvoiceAmount.inventory_id}', 'discount${PartInvoiceAmount.inventory_id}', 'tax${PartInvoiceAmount.inventory_id}', 'tatalcost${PartInvoiceAmount.inventory_id}', '${PartInvoiceAmount.inventory_id}' );"
																ondrop="return false;"> <span id="unitpriceIcon"
																class=""></span>
															<div id="unitpriceErrorMsg" class="text-danger"></div>
														</div>

														<div class="col-md-1">
															<input type="text" class="form-text" name="discount_many"
																min="0.0" id="discount${PartInvoiceAmount.inventory_id}" maxlength="5"
																placeholder="Discount" required="required"
																data-toggle="tip" data-original-title="enter Discount"
																onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																onkeyup="javascript:sumthereTypeWiseEdit('quantity${PartInvoiceAmount.inventory_id}','unitprice${PartInvoiceAmount.inventory_id}', 'discount${PartInvoiceAmount.inventory_id}', 'tax${PartInvoiceAmount.inventory_id}', 'tatalcost${PartInvoiceAmount.inventory_id}', '${PartInvoiceAmount.inventory_id}' );"
																ondrop="return false;"><span id="discountIcon"
																class=""></span>
															<div id="discountErrorMsg" class="text-danger"></div>

														</div>
														<div class="col-md-1">
															<input type="text" class="form-text" name="tax_many"
																id="tax${PartInvoiceAmount.inventory_id}" maxlength="5" min="0.0" placeholder="GST"
																required="required" data-toggle="tip"
																data-original-title="enter GST"
																onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																onkeyup="javascript:sumthereTypeWiseEdit('quantity${PartInvoiceAmount.inventory_id}', 'unitprice${PartInvoiceAmount.inventory_id}', 'discount${PartInvoiceAmount.inventory_id}', 'tax${PartInvoiceAmount.inventory_id}', 'tatalcost${PartInvoiceAmount.inventory_id}', '${PartInvoiceAmount.inventory_id}' );"
																ondrop="return false;"><span id="taxIcon"
																class=""></span>
															<div id="taxErrorMsg" class="text-danger"></div>
														</div>
														<div class="col-md-2">
															<input type="text" class="form-text" maxlength="8" name="tatalcost"
																value="0.0" min="0.0" id="tatalcost${PartInvoiceAmount.inventory_id}" readonly="readonly"
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
								<legend>Part Info</legend>
								<div class="box">
									<div class="box-body">
										
										<div class="row1">
											<div id="grppartLocation" class="form-group">
												<label class="L-size control-label" for="warehouselocation">Warehouse
													location :<abbr title="required">*</abbr>
												</label>
												<div class="col-md-3">
													<input type="hidden" name="partInvoiceId"
														value="${PartInvoice.partInvoiceId}"> 
														<input type="hidden" id="partInvoiceNumber" name="partInvoiceNumber"
														value="${PartInvoice.partInvoiceNumber}"> <input type="text"
														class="form-text" name="wareHouseLocationStr"
														readonly="readonly" required="required"
														value="${PartInvoice.partLocation}"
														placeholder="Please Enter 2 or more location Name" /> <span
														id="partLocationIcon" class=""></span>
													<div id="partLocationErrorMsg" class="text-danger"></div>
													<input type="hidden"  id="location" name = "wareHouseLocation" value="${PartInvoice.wareHouseLocation}" />
												</div>
												<c:if test="${configuration.roundupAmount}"> 
													
													<div class="col-md-3">
														<label class="L-size control-label" for="invoiceAmount">Invoice Amount:</label>
														<div class="col-md-3">
															<input type="number" class="form-text" id="invoiceAmount" readonly="readonly" style="width: 200px"> 
														</div>
													</div>
												</c:if>

											</div>
											<div id="subLocation" class="form-group" style="display:none">
											<input type="hidden" id="editSubLocationId" value="${PartInvoice.subLocationId}">
											<input type="hidden" id="editSubLocation" value="${PartInvoice.subLocation}">
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
										
										<%-- <div class="row1">
											<label class="L-size control-label">PO Number :</label>
											<div class="I-size">
												<input type="number" class="form-text" name="PO_NUMBER"
													value="${TyreInvoice.PO_NUMBER}" maxlength="25"
													onkeypress="return IsPonumber(event);"
													ondrop="return false;"> <label class="error"
													id="errorPonumber" style="display: none"> </label>
											</div>
										</div> --%>
										
										<div class="row1">
											<label class="L-size control-label">Invoice Number:</label>
											<div class="col-md-3">
												<input type="text" class="form-text" name="invoiceNumber"
													value="${PartInvoice.invoiceNumber}" maxlength="25"
													onkeypress="return IsInvoicenumber(event);"
													ondrop="return false;"> <label class="error"
													id="errorInvoicenumber" style="display: none"> </label>
											</div>
											
											<div id="grpinvoiceDate" class="form-group">
												<label class="L-size control-label" for="invoiceDate">Invoice
													Date :<abbr title="required">*</abbr>
												</label>
												<div class="col-md-3">
													<div class="input-group input-append date" id="paidDate">
														<input type="text" class="form-text" name="invoiceDateOn"
															id="invoiceDateOn" value="${PartInvoice.invoiceDateOn}"
															required="required" data-inputmask="'alias': 'dd-mm-yyyy'"
															data-mask="" /> <span class="input-group-addon add-on">
															
															<%-- <input type="hidden" class="form-text" name="invoiceDate"
															id="invoiceDate" value="${PartInvoice.invoiceDate}"> --%>
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
												<div class="col-md-9">
													<input type="hidden" id="Venid" required="required" 
														value="${PartInvoice.vendorId}" />
													<%-- --%>
													<input type="hidden" id="Venname" required="required"
														value="${PartInvoice.vendorName} - ${PartInvoice.vendorLocation}" />
													<%-- --%>
													<input type="hidden" id="selectVendor" name="vendorIdStr"
														style="width: 100%;" required="required"
														value="${PartInvoice.vendorId}"
														placeholder="${PartInvoice.vendorName}" /> <label
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
														<input class="form-text row1" name="vendorName"
															value="${PartInvoice.vendorName}" maxlength="25"
															type="text" id="enterVendorName"
															placeholder="Enter Part Vendor Name"
															onkeypress="return IsVendorName(event);"
															ondrop="return false;"> <label class="error"
															id="errorVendorName" style="display: none"> </label> <input
															class="form-text row1" name="VENDOR_LOCATION"
															value="${PartInvoice.vendorLocation}" maxlength="25"
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
											<input type="hidden" id="paymentOldId" value="${PartInvoice.paymentTypeId}">
												<select class="form-text" name="paymentTypeId" id="renPT_option"
												 required="required" >
													<option selected="selected"
															value="<c:out value="${PartInvoice.paymentTypeId}"/>">
															<c:out value="${PartInvoice.paymentType}" />
														</option>
													<%-- <option selected="selected" value="${PartInvoice.paymentTypeId}"><c:out value="${PartInvoice.paymentType}" /></option> --%>
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
										<input id="previousInvoice" type="hidden" value="${PartInvoice.invoiceAmount }" >
										<c:if test="${!configuration.roundupAmount}">
											<div class="row1">
												<label class="L-size control-label">Labour Charge :</label>
												<div class="col-md-3">
													<input type="number" class="form-text" name=labourCharge
														id="labourCharge" value="${PartInvoice.labourCharge}" maxlength="25"
														onkeypress="return isNumberKeyWithDecimal(event,this.id);"
														onkeyup="addLabourToInvoice();"
														ondrop="return false;"> <label class="error"
														id="errorInvoicenumber" style="display: none"> </label>
												</div>
												
												<label class="L-size control-label">Invoice Amount:</label>
												<div class="col-md-3">
													<input type="number" class="form-text" name="invoiceAmount"
														id="invoiceAmount" value="${PartInvoice.invoiceAmount}" maxlength="25"
														onkeypress="return isNumberKeyWithDecimal(event,this.id);"
														ondrop="return false;"> <label class="error"
														id="errorInvoicenumber" style="display: none"> </label>
														<input id="previousInvoice" type="hidden" value="${PartInvoice.invoiceAmount }" >
												</div>
											</div>
										</c:if>
										<c:if test="${configuration.roundupAmount}">
										<div class="row1">
												<label class="L-size control-label">Labour Charge :</label>
												<div class="col-md-3">
													<input type="number" class="form-text" name=labourCharge
														id="labourCharge" value="${PartInvoice.labourCharge}" maxlength="25"
														onkeypress="return isNumberKeyWithDecimal(event,this.id);"
														onkeyup="addLabourToInvoice();"
														ondrop="return false;"> <label class="error"
														id="errorInvoicenumber" style="display: none"> </label>
												</div>
												
												<label class="L-size control-label">Roundup Amount:</label>
												<div class="col-md-3">
													<input type="text" class="form-text" name="invoiceAmount"
														id="roundupTotal" value="${PartInvoice.invoiceAmount}" maxlength="25"
														onkeypress="return isNumberKeyWithDecimal(event,this.id);"
														ondrop="return false;"> <label class="error"
														id="errorInvoicenumber" style="display: none"> </label>
												</div>
											</div>
										</c:if>
										<input type="hidden" id="tallyIntegrationRequired" value="${configuration.tallyIntegrationRequired}">
										<c:if test="${configuration.tallyIntegrationRequired}">
											<input type="hidden" id="tallyCompanyIdEdit" value="${PartInvoice.tallyCompanyId}">
											<input type="hidden" id="tallyCompanyNameEdit" value="${PartInvoice.tallyCompanyName}">
											<div class="row1" id="grpmanufacturer" class="form-group">
											<label class="L-size control-label" for="manufacurer">Tally Company Name :<abbr title="required">*</abbr></label>
												<div class="col-md-3">
													<input type="hidden" id="tallyCompanyId" name="tallyCompanyId" style="width: 100%;" value="0"
													  placeholder="Please Enter Tally Company Name" />
												</div>
											</div>
										</c:if>
										
										<c:if test="${configuration.editPartDocument}">
										<fieldset>
											<legend>Upload  Documents</legend>
											<div class="box">
												<div class="box-body">
													<div class="row1"  class="form-group">
														<label class="L-size control-label" for="renewalFile">File :</label>
														<div class="I-size">
															<input type="file" name="input-file-preview" /> 
															<span id="renewalFileIcon" class=""></span>
															<div id="renewalFileErrorMsg" class="text-danger"></div>
														</div>
													</div>
												</div>
											</div>
										</fieldset>
										</c:if>
										<c:if test="${!configuration.editPartDocument}">
										<input class="hide" type="file" name="input-file-preview" /> 
										</c:if>
										<div class="row1">
											<label class="L-size control-label">Description :</label>
											<div class="I-size">
												<textarea class="form-text" name="description" rows="3"
													maxlength="150" onkeypress="return IsVendorRemark(event);"
													ondrop="return false;">${PartInvoice.description} 
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
										<button type="submit" class="btn btn-success" onclick="return validateInventorySave();">Update
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
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IP/EditPartInvoiceValidate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>  
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("dd-mm-yyyy", {
				placeholder : "dd-mm-yyyy"
			}), $("[data-mask]").inputmask()
		
		//	$("#location").select2();
			$("#manufacturer").select2();
			$("#tagPicker").select2({
				closeOnSelect : !1
			})
			
// 			setTimeout(() => {
			$('#renPT_option').val($('#paymentOldId').val());
// 			}, 500);
			
	
			var VID = $("#Venid").val();
			var Vtext = $("#Venname").val();
			$('#selectVendor').select2('data', {
				id : VID,
				text : Vtext
			});
			$('#tallyCompanyId').select2('data', {
				id : $('#tallyCompanyIdEdit').val(),
				text : $('#tallyCompanyNameEdit').val()
			});
			var anyPartNumberAsign		 = ${anyPartNumberAsign};
			var editAllPartInventory	 = $("#editAllPartInventory").val();
			if(anyPartNumberAsign  && editAllPartInventory){
				$('#editPartInvoice').hide();
			} 
			setDropDownToElements('${bpmsn}');
		});
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>