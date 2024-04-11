<%@ include file="../../taglib.jsp"%>
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
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> / 
					<a href="UreaInventory.in">New Urea Inventory</a> / ${ureaInvoiceDto.ureaInvoiceId} / Edit Urea Invoice
				</div>
				<div class="pull-right">
					<a href="UreaInventory.in">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<%-- <sec:authorize access="!hasAuthority('EDIT_CLOTH_INVENTORY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize> --%>
		<%-- <sec:authorize access="hasAuthority('EDIT_CLOTH_INVENTORY')"> --%>
			<div class="row">
				<div class="col-md-offset-1 col-md-9">
					<form id="formEditTyreInventory"
						action="<c:url value="/updateUreaInventory.in"/>" method="post"
						enctype="multipart/form-data" name="formEditTyreInventory"
						role="form" class="form-horizontal" novalidate="novalidate">
						<div class="form-horizontal ">
							<fieldset>
								<legend>Urea Invoice Details</legend>
								<div class="box">
									<div class="box-body">
										
										<div class="row1" id="grppartLocation" class="form-group">
											<div  id="grppartLocation">
												<label class="L-size control-label" for="warehouselocation">Warehouse
													location :<abbr title="required">*</abbr>
												</label>
												<div class="col-md-3">
													<input type="hidden" name="ureaInvoiceId" value="${ureaInvoiceDto.ureaInvoiceId}"> 
													<input type="hidden" id="warehouselocation" name="wareHouseLocation" value="${ureaInvoiceDto.wareHouseLocation}"> 
													<input type="text" class="form-text" name="batteryLocation" readonly="readonly" required="required"
														   value="${ureaInvoiceDto.locationName}" placeholder="Please Enter 2 or more location Name" /> 
													<span id="partLocationIcon" class=""></span>
													<div id="partLocationErrorMsg" class="text-danger"></div>
												</div>
											</div>
											<input type="hidden" id="showSubLocation" value="${showSubLocation}">
											<input type="hidden" id="mainLocationIds" value="${mainLocationIds}">
											<div class="row1" id="subLocation" class="form-group" style="display:none">
												<input type="hidden" id="editSubLocationId" value="${ureaInvoiceDto.subLocationId}">
												<input type="hidden" id="editSubLocation" value="${ureaInvoiceDto.subLocation}">
												<input type="hidden" id="roundupConfig" value="${configuration.roundupAmount}" />
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
														value="${ureaInvoiceDto.vendorId}" />
													<%-- --%>
													<input type="hidden" id="Venname" required="required"
														value="${ureaInvoiceDto.vendorName} - ${ureaInvoiceDto.vendorLocation}" />
													<%-- --%>
													<input type="hidden" id="selectVendor" name="vendorId"
														style="width: 100%;" required="required"
														value="${ureaInvoiceDto.vendorId}"
														placeholder="${ureaInvoiceDto.vendorName}" /> <label
														class="error" id="errorVendorSelect"> </label>
												</div>
											</div>
											
											<label class="L-size control-label">Modes of Payment
												<abbr title="required">*</abbr>
											</label>
											<div class="col-md-3" id="paymentDiv">
												<select class="form-text" name="paymentTypeId" id="renPT_option1" required="required">
													<option  selected="selected" value="${ureaInvoiceDto.paymentTypeId}">${ureaInvoiceDto.paymentType} </option>
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
											<label class="L-size control-label">Invoice Number:</label>
											<div class="col-md-3">
												<input type="text" class="form-text" name="invoiceNumber" value="${ureaInvoiceDto.invoiceNumber}" maxlength="25"
													 ondrop="return false;"
														onkeypress="return isNumberKey(event)" > 
													<label class="error" id="errorInvoicenumber" style="display: none"> </label>
											</div>
											
											<div  id="grpinvoiceDate" class="form-group">
												<label class="L-size control-label" for="invoiceDate">Invoice
													Date :<abbr title="required">*</abbr>
												</label>
												<div class="col-md-3">
													<div class="input-group input-append date" id="paidDate">
														<input type="text" class="form-text" name="invoiceDateStr"
															id="invoiceDate" value="${ureaInvoiceDto.invoiceDateStr}"
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
										<c:if test="${configuration.showPOnumberField}">
										<label class="L-size control-label">PO Number :</label>
											<div class="col-md-3">
												<input type="text" class="form-text" name="poNumber" value="${ureaInvoiceDto.poNumber}" maxlength="25"
												       	onkeypress="return isNumberKey(event)" 
													   ondrop="return false;"> <label class="error" id="errorPonumber" style="display: none"> </label>
											</div>
											</c:if>
										<c:if test="${!configuration.roundupAmount}">
											<label class="L-size control-label">Invoice Amount:</label>
											<div class="col-md-3">
												<input type="text" class="form-text" id="ureaInvoiceAmount" name="invoiceAmount"
													value="${ureaInvoiceDto.invoiceAmount}" maxlength="10"
													
													onkeypress="return isNumberKeyWithDecimal(event,this.id)"
													ondrop="return false;"> <label class="error"
													id="errorInvoicenumber" style="display: none"> </label>
											</div>
											</c:if>
										</div>
										
										<c:if test="${configuration.roundupAmount}">
										<div class="row1">
											<label class="L-size control-label">Invoice Amount: </label>
											<div class="col-md-3">
												<input type="text" class="form-text" id="ureaInvoiceAmount" 	
													value="${ureaInvoiceDto.totalAmount}"
													 maxlength="10" readonly="readonly"
													onkeypress="return isNumberKeyWithDecimal(event,this.id)"
													ondrop="return false;"> <label class="error"
													id="errorInvoicenumber" style="display: none"> 
												  
													
													</label>
											</div>
												<label class="L-size control-label">Roundup Amount:</label>
											<div class="col-md-3">
												<input type="text" class="form-text" id="roundupTotal" name="invoiceAmount"
													 value="${ureaInvoiceDto.invoiceAmount}"
													 required="required" maxlength="10"
													onkeypress="return isNumberKeyWithDecimal(event,this.id)"
													ondrop="return false;"> <label class="error"
													id="errorInvoicenumber" style="display: none"> </label>
											</div>
										
										</div>
										</c:if>
										
										<input type="hidden" id="tallyIntegrationRequired" value="${configuration.tallyIntegrationRequired}">
										<c:if test="${configuration.tallyIntegrationRequired}">
										   <input type="hidden" id="tallyIdEdit" value="${ureaInvoiceDto.tallyCompanyId}">
										   <input type="hidden" id="tallyNameEdit" value="${ureaInvoiceDto.tallyCompanyName}">
											<div  id="grpmanufacturer" class="form-group">
											<label class="L-size control-label" for="manufacurer">Tally Company Name :<abbr title="required">*</abbr></label>
												<div class="col-md-3">
													<input type="hidden" id="tallyCompanyId" name="tallyCompanyId" style="width: 100%;" value="0"
													  placeholder="Please Enter Tally Company Name" />
												</div>
											</div>
										</c:if>
										
										<c:if test="${configuration.editUreaInventoryDocument}">
										<fieldset id="grpfuelDocument">
											<legend>Document</legend>
											<div class="box">
												<div class="box-body">
													<div class="row1">
														<label class="L-size control-label" for="fuel_partial">Urea
															Document : </label>
														<div class="I-size">
															<input type="file" name="input-file-preview" id="batteryDocument" /> 
																<span id="renewalFileIcon" class=""></span>
															<div id="renewalFileErrorMsg" class="text-danger"></div>
															<span class="help-block">Add an optional document</span>
														</div>
													</div>
												</div>
											</div>
										</fieldset>
									</c:if>
									<c:if test="${!configuration.editUreaInventoryDocument}">
										<input type="file" class="hide" name="input-file-preview" id="batteryDocument1" /> 
									</c:if>
										<div class="row1">
											<label class="L-size control-label">Description :</label>
											<div class="I-size">
												<textarea class="form-text" name="description" rows="3"
													maxlength="150" onkeypress="return IsVendorRemark(event);"
													ondrop="return false;">${ureaInvoiceDto.description} 
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
										<button type="submit" class="btn btn-success" onclick="return validateUrea();">Update
											Invoice</button>
										<a class="btn btn-link"
											href="<c:url value="ClothInventory.in"/>">Cancel</a>
									</div>
								</div>
							</fieldset>
						</div>
						<%@ include file="/WEB-INF/view/payment/PaymentDetails.jsp"%>
					</form>
				</div>
			</div>
		<%-- </sec:authorize> --%>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/clothinventory/editClothInventory.js" />"></script>
		<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script> 
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/ureainventory/validateUreaInventory.js"></script>	
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

			//old vendor name and vendor id data show in edit time
			var VID = $("#Venid").val();
			var Vtext = $("#Venname").val();
			$('#selectVendor').select2('data', {
				id : VID,
				text : Vtext
			});
			$('#tallyCompanyId').select2('data', {
				id : $("#tallyIdEdit").val(),
				text : $("#tallyNameEdit").val()
			});
		});
		
		$("#roundupTotal").keyup(function() {
	        if ($(this).val($(this).val().replace(/[^0-9\.]/g, "")), -1 != $(this).val().indexOf(".") && $(this).val().split(".")[1].length > 2) {
	            if (isNaN(parseFloat(this.value))) return;
	            this.value = parseFloat(this.value).toFixed(2)
	        }
	        return this
	    });
		
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>