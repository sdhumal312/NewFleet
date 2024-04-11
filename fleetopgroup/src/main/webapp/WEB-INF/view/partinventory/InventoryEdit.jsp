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
						href="<c:url value="/NewInventory/1.in"/>">New Inventory</a> / <a
						href="<c:url value="/showInventory.in?inventory_all_id=${Inventory.inventory_all_id}"/>">Show
						Inventory</a> / <span id="NewVehi">Edit Inventory</span>
				</div>
				<div class="pull-right">
					<a
						href="<c:url value="/showInventory.in?inventory_all_id=${Inventory.inventory_all_id}"/>">Cancel</a>
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
				<input type="hidden" id="validateSubLocation">
					<form id="formEditPartInventory"
						action="<c:url value="/updateInventory.in"/>" method="post"
						enctype="multipart/form-data" name="formEditPartInventory"
						role="form" class="form-horizontal">
						<div class="form-horizontal ">
							<fieldset>
								<legend>Edit Inventory Parts</legend>
								<div class="box">
									<div class="box-body">
										<div class="row1" id="grpinventoryPart" class="form-group">
											<label class="L-size control-label">Part Number :<abbr
												title="required">*</abbr></label>
											<div class="col-md-2">
												<input type="hidden" name="inventory_id"
													value="${Inventory.inventory_id}"> <input
													type="hidden" name="inventory_location_id"
													value="${Inventory.inventory_location_id}"> <input
													type="hidden" name="inventory_all_id"
													value="${Inventory.inventory_all_id}">
												<!--  -->
												<input type="hidden" name="partid" id="Opartid"
													value="${Inventory.partid}"> <input type="text"
													class="form-text" readonly="readonly" name="partnumber"
													required="required" value="${Inventory.partnumber}">
												<input type="hidden" name="inventory_Number"
													value="${Inventory.inventory_Number}"> <input
													type="hidden" name="partTypeId"
													value="${Inventory.partTypeId}">
													<input type="hidden" name="partInvoiceId"
													value="${Inventory.partInvoiceId}">
												<input type="hidden" id="showSubLocation" value="${showSubLocation}">
									<input type="hidden" id="mainLocationIds" value="${mainLocationIds}">	

											</div>

											<label class="col-md-1 control-label">Part Name:<abbr
												title="required">*</abbr>
											</label>
											<div class="col-md-3">
												<input type="hidden" class="form-text" readonly="readonly"
													name="partname" id="Opartname" required="required"
													value="${Inventory.partname}">
												<!--  -->
												<input type="hidden" readonly="readonly" id="searchpart"
													style="width: 100%;"
													placeholder="Please Enter 2 or more Part Name or Part Number" />
											</div>
											<span id="inventoryPartIcon" class=""></span>
											<div id="inventoryPartErrorMsg" class="text-danger"></div>
										</div>
										<div class="row1" id="grpmanufacturer" class="form-group">
											<label class="L-size control-label" for="manufacturer">Manufacturer
												:<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="text" class="form-text" name="make"
													required="required" maxlength="50" id="manufacturer"
													value="${Inventory.make}"
													onkeypress="return IsManufacturer(event);"
													ondrop="return false;"> <span id="manufacturerIcon"
													class=""></span>
												<div id="manufacturerErrorMsg" class="text-danger"></div>
												<label class="error" id="errorManufacturer"
													style="display: none"> </label>
											</div>
										</div>
										<div class="row1" id="grpquantity" class="form-group">
											<label class="L-size control-label" for="quantity">Quantity
												:</label>

											<div class="col-md-9">
												<div class="col-md-1">
													<input type="text" class="form-text" name="quantity"
														min="0.0" id="quantity" maxlength="4"
														value="${Inventory.quantity}" placeholder="ex: 23.78"
														required="required" data-toggle="tip"
														data-original-title="enter Part Quantity"
														onkeypress="return isNumberKey(event,this);"
														onkeyup="javascript:sumthere('quantity', 'unitprice', 'discount', 'tax', 'tatalcost');"
														ondrop="return false;"> <span id="quantityIcon"
														class=""></span>
													<div id="quantityErrorMsg" class="text-danger"></div>
												</div>
												<div class="col-md-2">
													<input type="text" class="form-text" name="unitprice"
														id="unitprice" maxlength="7" min="0.0"
														value="${Inventory.unitprice}" placeholder="Unit Cost"
														required="required" data-toggle="tip"
														data-original-title="enter Unit Price"
														onkeypress="return isNumberKey(event,this);"
														onkeyup="javascript:sumthere('quantity', 'unitprice', 'discount', 'tax', 'tatalcost');"
														ondrop="return false;"> <span id="unitpriceIcon"
														class=""></span>
													<div id="unitpriceErrorMsg" class="text-danger"></div>
												</div>
												<div class="col-md-1">
													<input type="text" class="form-text" name="discount"
														min="0.0" id="discount" maxlength="5"
														value="${Inventory.discount}" placeholder="Discount"
														required="required" data-toggle="tip"
														data-original-title="enter Discount"
														onkeypress="return isNumberKeyQut(event,this);"
														onkeyup="javascript:sumthere('quantity', 'unitprice', 'discount', 'tax', 'tatalcost');"
														ondrop="return false;"> <span id="discountIcon"
														class=""></span>
													<div id="discountErrorMsg" class="text-danger"></div>
												</div>
												<div class="col-md-1">
													<input type="text" class="form-text" name="tax" id="tax"
														maxlength="5" min="0.0" value="${Inventory.tax}"
														placeholder="GST" required="required" data-toggle="tip"
														data-original-title="enter GST"
														onkeypress="return isNumberKeyQut(event,this);"
														onkeyup="javascript:sumthere('quantity', 'unitprice', 'discount', 'tax', 'tatalcost');"
														ondrop="return false;"> <span id="taxIcon"
														class=""></span>
													<div id="taxErrorMsg" class="text-danger"></div>
												</div>
												<div class="col-md-2">
													<input type="text" class="form-text" maxlength="8"
														min="0.0" id="tatalcost" readonly="readonly"
														value="${Inventory.total}" data-toggle="tip"
														data-original-title="Total Cost"
														onkeypress="return isNumberKey(event,this);"
														ondrop="return false;">
												</div>
											</div>
											<br> <label class="error" id="errorINEACH"
												style="display: none"></label>
										</div>
										<br>
										<div class="row1" id="grppartLocation" class="form-group">
											<label class="L-size control-label" for="location">Warehouse
												location :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<select class="select2" name="locationId" id="location"
													style="width: 100%;" required="required">
													<option value="${Inventory.locationId}"><c:out
															value="${Inventory.location}" />
													</option>
												</select> <input type="hidden" name="location"
													value="${Inventory.location}"> <span
													id="partLocationIcon" class=""></span>
												<div id="partLocationErrorMsg" class="text-danger"></div>
											</div>
										</div>
											<div class="row1" id="subLocation" class="form-group" style="display:none">
											<label class="L-size control-label" for="location">Sub location :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<select class="select2 form-text" name="subLocationId" id="subLocationId" style="width: 100%;">
													<option value="${Inventory.subLocationId}"><c:out value="${Inventory.subLocation}" /></option>
													<c:forEach items="${subLocation}" var="subLocation">
														<option value="${subLocation.partLocationId}">
															<c:out value="${subLocation.partLocationName}" />
														</option>
													</c:forEach>
												</select> <input type='hidden' id='locationText' name="location"
													value='' /> <span id="partLocationIcon" class=""></span>
												<div id="partLocationErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">PO Number :</label>
											<div class="I-size">
												<input type="text" class="form-text" name="purchaseorder_id"
													readonly="readonly" maxlength="25"
													value="${Inventory.purchaseorder_id}"
													onkeypress="return IsPonumber(event);"
													ondrop="return false;"> <label class="error"
													id="errorPonumber" style="display: none"> </label>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Invoice :</label>
											<div class="I-size">
												<input type="text" class="form-text" name="invoice_number"
													maxlength="25" value="${Inventory.invoice_number}"
													onkeypress="return IsInvoicenumber(event);"
													ondrop="return false;"> <label class="error"
													id="errorInvoicenumber" style="display: none"> </label>
											</div>
										</div>
										<div class="row1" id="grpinvoiceDate" class="form-group">
											<label class="L-size control-label" for="invoiceDate">Invoice
												Date :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="input-group input-append date" id="requiredDate">
													<input type="text" class="form-text" name="invoice_date"
														required="required" value="${Inventory.invoice_date}"
														id="invoiceDate" data-inputmask="'alias': 'dd-mm-yyyy'"
														data-mask="" /> <span class="input-group-addon add-on">
														<span class="fa fa-calendar"></span>
													</span>
												</div>
												<span id="invoiceDateIcon" class=""></span>
												<div id="invoiceDateErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Invoice Amount:</label>
											<div class="I-size">
												<input type="number" class="form-text" id="invoiceAmount" name="invoice_amount"
													value="${Inventory.invoice_amount}" maxlength="25"
													onkeypress="return IsInvoicenumber(event);"
													ondrop="return false;"> <label class="error"
													id="errorInvoicenumber" style="display: none"> </label>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Vendor :<abbr
												title="required">*</abbr></label>
											<div class="I-size" id="vendorSelect">
												<input type="hidden" id="OLDVendorID" required="required"
													value="${Inventory.vendor_id}" /> <input type="hidden"
													id="OLDVendorName" required="required"
													value="${Inventory.vendor_name} - ${Inventory.vendor_location}" />
												<input type="hidden" id="selectVendor" name="Vendor_id"
													style="width: 100%;" required="required"
													value="${Inventory.vendor_id}"
													placeholder="${Inventory.vendor_name} - ${Inventory.vendor_location}" />
												<label class="error" id="errorVendorSelect"> </label>

											</div>

										</div>
										<div class="row1">
											<label class="L-size control-label">Description :</label>
											<div class="I-size">
												<textarea class="form-text" name="description" rows="3"
													maxlength="150" onkeypress="return IsVendorRemark(event);"
													ondrop="return false;">${Inventory.description}
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
										<a class="btn btn-info"
											href="<c:url value="/showInventory.in?inventory_all_id=${Inventory.inventory_all_id}"/>">Cancel</a>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IT/InventoryValidate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabsShowVehicle.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript">
		$('#contactTwo').hide();
	</script>
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
			$("#tagPicker").select2({
				closeOnSelect : !1
			})
		});
	</script>
	<script type="text/javascript">
		$(document).ready(function() {
			var h = $("#Opartid").val(), i = $("#Opartname").val();
			$("#searchpart").select2("data", {
				id : h,
				text : i
			});
			var k = $("#OLDVendorID").val(), l = $("#OLDVendorName").val();
			$("#selectVendor").select2("data", {
				id : k,
				text : l
			});
		});
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>