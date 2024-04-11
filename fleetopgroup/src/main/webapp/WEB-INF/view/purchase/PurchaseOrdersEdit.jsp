<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/newPurchaseOrders/1.in"/>">Purchase
						Orders</a> / <span>Edit Purchase Order</span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/newPurchaseOrders/1.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<c:if test="${param.success eq true}">
		<div class="alert alert-success">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This Vendor Created Successfully.
		</div>
	</c:if>
	<c:if test="${param.danger eq true}">
		<div class="alert alert-danger">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This Vendor Already Exists
		</div>
	</c:if>
	<section class="content">
		<div class="row">
			<sec:authorize access="!hasAuthority('ADD_PURCHASE')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('ADD_PURCHASE')">
				<div class="col-md-offset-1 col-md-9">
				<input type="hidden" id="poVendorId" value="${PO.purchaseorder_vendor_id}">
				<input type="hidden" id="poVendorName" value="${PO.purchaseorder_vendor_name}">
				<input type="hidden" id="editVendorInPOConfig" value="${purchaseConfig.editVendorInPO}">
				 <input type="hidden" id="purchaseTypeId" value="${PO.purchaseorder_typeId}">
				 <input type="hidden" id="allVendorTypeAutoComplete" value="${vendorConfig.allVendorTypeAutoComplete}"/>
					<form id="formEditPurchaseOrder"
						action="<c:url value="/UpdatePurchaseOrder.in"/>" method="post"
						enctype="multipart/form-data" name="formEditPurchaseOrder" role="form"
						class="form-horizontal">
						<div class="form-horizontal ">
							<fieldset>
								<legend>Purchase Order Info</legend>
								<div class="box">
									<div class="box-body">
										<div class="row1">
											<div class="I-size">
												<input class="string required form-text"
													value="${PO.purchaseorder_id}" name="purchaseorder_id"
													value="" type="hidden">
											<input class="string required form-text"
													value="${PO.purchaseorder_Number}" name="purchaseorder_Number"
													 type="hidden">
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Purchase Order :<abbr
												title="required">*</abbr>
											</label>
											<div class="I-size ">
												<input type="text" value="PO-${PO.purchaseorder_Number}"
													class="form-text" required="required" readonly="readonly">
											</div>
										</div>
										<div class="row1" id="grppoOpendate" class="form-group">
											<label class="L-size control-label" for="poOpendate">Date
												Opened :<abbr title="required">*</abbr>
											</label>
											<div class="col-md-3">
												<div class="input-group input-append date"
													id="TripStartDate">
													<input type="text" class="form-text" id="poOpendate"
														value="${PO.purchaseorder_created_on}"
														name="purchaseorder_created_on" required="required"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
												<span id="poOpendateIcon" class=""></span>
												<div id="poOpendateErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<div class="row1" id="grppoClosedate" class="form-group">
											<label class="L-size control-label" for="poClosedate">Date
												Required :<abbr title="required">*</abbr>
											</label>
											<div class="col-md-3">
												<div class="input-group input-append date" id="requiredDate">
													<input type="text" class="form-text" id="poClosedate"
														value="${PO.purchaseorder_requied_on}"
														name="purchaseorder_requied_on" required="required"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
												<span id="poClosedateIcon" class=""></span>
												<div id="poClosedateErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<c:if test="${purchaseConfig.editVendorInPO}">
											<div class="row1" id="grpselectVendor" class="form-group">
											<label class="L-size control-label" for="selectVendor">Part/Tyre/Battery Vendor :
												<c:if test="${vendorConfig.VendorInputFieldInPOAdd}">
													<abbr title="required">*</abbr>
												</c:if>   
											</label>
											<div class="I-size" id="vendorSelect">
												<input type="hidden" id="selectVendor"
													name="purchaseorder_vendor_id" 
													style="width: 100%;" required="required"
													placeholder="Please Select Vendor Name" />
											</div>
										</div>
										</c:if>
										<c:if test="${!purchaseConfig.editVendorInPO}">
											<div class="row1">
											<label class="L-size control-label">Part/Tyre Vendor
												:<abbr title="required">*</abbr>
											</label>
											<div class="I-size" id="vendorSelect">
											<input type="hidden" name="purchaseorder_vendor_id" value="${PO.purchaseorder_vendor_id}">
												<input type="text" readonly="readonly" class="form-text"
													value="${PO.purchaseorder_vendor_name}"
													style="width: 100%;" required="required" /> <label
													class="error" id="errorVendorSelect"> </label>
											</div>
										</div>
										</c:if>
										
										<div class="row1" id="grppoBuyerAddress" class="form-group">
											<label class="L-size control-label"
												for="purchaseorder_buyeraddress">Buyer Address :<abbr
												title="required">*</abbr>
											</label>
											<div class="I-size">
												<input id="purchaseorder_buyeraddress" class="form-text"
													name="purchaseorder_buyeraddress" type="text"
													value="${PO.purchaseorder_buyeraddress}"><span
													id="poBuyerAddressIcon" class=""></span>
												<div id="poBuyerAddressErrorMsg" class="text-danger"></div>
												<label class="error" id="errorOdometer"
													style="display: none"></label> <label class="error"
													id="errorOdo"></label>

											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="priority">Terms
											</label>
											<div class="I-size">
												<div class="col-md-9" id="paymentDiv">
													<select style="width: 100%;" name="purchaseorder_termsId"
														id="terms" required="required">
														<option value="${PO.purchaseorder_termsId}">${PO.purchaseorder_terms}</option>
														<option value="1">CASH</option>
														<option value="9">C.O.D</option>
														<option value="2">CREDIT</option>
														<option value="3">NEFT</option>
														<option value="4">RTGS</option>
														<option value="5">IMPS</option>
														<option value="6">DD</option>
														<option value="7">CHEQUE</option>
														<option value="8">BANK DRAFT</option>
														<option value="9">COD</option>
														<option value="10">ON ACCOUNT</option>
													</select>
												</div>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Ship via </label>
											<div class="I-size">
												<div class="col-md-9">
													<select style="width: 100%;" name="purchaseorder_shipviaId"
														id="shipvia" required="required">
														<option value="${PO.purchaseorder_shipviaId}">${PO.purchaseorder_shipvia}</option>
														<option value="1">AIR</option>
														<option value="2">COURIER</option>
														<option value="3">EXPEDITED</option>
														<option value="4">GROUND</option>
														<option value="5">NEXT DAY</option>
														<option value="6">NONE</option>
														<c:if test="${purchaseConfig.defaultShipVia}">
														<option value="7">BY ROAD</option>
														</c:if>
													</select>
												</div>
											</div>
										</div>
										<c:choose>
											<c:when test="${purchaseConfig.editShipToLocation && PO.purchaseorder_statusId != 3}">
												<div class="row1">
												<label class="L-size control-label">Ship to </label>
												<div class="I-size">
													<select class="form-control select2" name="purchaseorder_shiplocation_id" id="partlocationId" style="width: 100%;">
													<option value="${PO.purchaseorder_shiplocation_id}"> <c:out value="${PO.purchaseorder_shiplocation}" /> </option>
													<c:forEach items="${PartLocations}" var="PartLocations">
														<option value="${PartLocations.partLocationId}">
															<c:out value="${PartLocations.partLocationName}" />
														</option>
													</c:forEach>
												</select>
												</div>
											</div>
											</c:when>
											<c:otherwise>
												<div class="row1">
												<label class="L-size control-label">Ship to </label>
												<div class="I-size">
												<input type="hidden" name="purchaseorder_shiplocation_id" value="${PO.purchaseorder_shiplocation_id}">
													<input type="text" class="form-text"
														value="${PO.purchaseorder_shiplocation}"
														readonly="readonly" required="required"
														placeholder="Please Select Location Name" /> <label
														class="error" id="errorVendorSelect"> </label>
	
												</div>
											</div>
											</c:otherwise>
										</c:choose>
										<div class="row1">
											<label class="L-size control-label">Buyer Name</label>
											<div class="I-size">
											<c:if test="${configuration.subCompanyInPO}">
												<input type="hidden" id="editSubCompanyId" value="${PO.subCompanyId}">
												<input type="hidden" id="editSubCompanyName" value="${PO.purchaseorder_buyer}">
												<select style="width: 100%;"class="select2" name="subCompanyId"  id="subCompanyId" ></select>
											</c:if>
											<c:if test="${!configuration.subCompanyInPO}">
												<input type="text" readonly="readonly" class="form-text"
													value="${PO.purchaseorder_buyer}" style="width: 100%;"
													required="required" />
											</c:if>		

											</div>
										</div>
										<c:if test="${purchaseConfig.showQuoteNumber}">
										<div class="row1">
											<label class="L-size control-label">Quote Number </label>
											<div class="I-size">
												<input class="string required form-text"
													id="purchaseorder_quotenumber"
													name="purchaseorder_quotenumber"
													value="${PO.purchaseorder_quotenumber}" type="text" maxlength="25">
											</div>
										</div>
										</c:if>
										<c:if test="${purchaseConfig.ManualIndentNo}">
										<div class="row1">
											<label class="L-size control-label">Manual Indent No
											</label>
											<div class="I-size">
												<input class="string required form-text"
													id="purchaseorder_indentno" name="purchaseorder_indentno"
													type="text" value="${PO.purchaseorder_indentno}" maxlength="25">
											</div>
										</div>
										</c:if>
										<c:if test="${purchaseConfig.WorkOrderNo}">
										<div class="row1">
											<label class="L-size control-label">Work Order No </label>
											<div class="I-size">
												<input type="text" class="form-text"
													value="${PO.purchaseorder_workordernumber}"
													name="purchaseorder_workordernumber" style="width: 100%;"
													required="required"
													placeholder="Please Select Location Name" />

											</div>
										</div>
										</c:if>
										<div class="row1">
											<label class="L-size control-label" for="issue_description">
												Notes</label>
											<div class="I-size">
												<textarea class="text optional form-text" id="initial_note"
													name="purchaseorder_notes" rows="3" maxlength="500">${PO.purchaseorder_notes}
				                                 </textarea>
											</div>
										</div>

									</div>
								</div>
							</fieldset>
							<fieldset class="form-actions">
								<div class="row">
									<div class="col-md-10 col-md-offset-2">
										<button type="submit" onclick="return validatePurchase()"; class="btn btn-success">Update
											Purchase Order</button>
										<a class="btn btn-link"
											href="<c:url value="/newPurchaseOrders/1.in"/>">Cancel</a>
									</div>
								</div>
							</fieldset>
						</div>
						<%@ include file="/WEB-INF/view/payment/PaymentDetails.jsp"%>
					</form>
				</div>
			</sec:authorize>
		</div>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/PO/PurchaseOrdersValidate.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("dd-mm-yyyy", {
				placeholder : "dd-mm-yyyy"
			}), $("[data-mask]").inputmask()
		});
	</script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#terms").select2();
			$("#shipvia").select2();
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