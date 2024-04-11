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
						href="<c:url value="/PartRequisition/1/1.in"/>">Part
						Requisition</a> / <span id="NewVehi">New Create Inventory
						Requisition</span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/PartRequisition/1/1.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('ADD_REQUISITION_INVENTORY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('ADD_REQUISITION_INVENTORY')">
			<div class="row">
				<div class="col-md-offset-1 col-md-9">
					<form id="formPartInventory"
						action="<c:url value="/savePartRequisition.in"/>" method="post"
						enctype="multipart/form-data" name="formPartInventory" role="form"
						class="form-horizontal">
						<div class="form-horizontal ">
							<fieldset>
								<legend>Requisition Info</legend>
								<div class="box">
									<div class="box-body">
										<div class="row1">
											<label class="L-size control-label">Requisition
												location : <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<select id="location" class="select2 required"
													style="width: 100%;" name="REQUITED_LOCATION_ID">
													<c:forEach items="${partLocationPermission}"
														var="partLocationPermission">
														<option value="${partLocationPermission.partLocationId}">
															<c:out value="${partLocationPermission.partLocationName}" />
														</option>
													</c:forEach>
												</select> <span id="woLocationIcon" class=""></span>
												<div id="woLocationErrorMsg" class="text-danger"></div>

											</div>
										</div>
										<div class="row1" id="grpwoAssigned" class="form-group">
											<label class="L-size control-label" for="subscribe">Requisition
												Assigned To <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input class="" placeholder="assignee users" id="subscribeID"
													type="hidden" style="width: 100%"
													name="REQUISITION_RECEIVER_ID"
													onkeypress="return Isservice_subscribeduser(event);"
													required="required" ondrop="return false;"> <span
													id="woAssignedIcon" class=""></span>
												<div id="woAssignedErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Reference Number:</label>
											<div class="I-size">
												<input type="text" class="form-text" name="REQUITED_NUMBER"
													maxlength="50" onkeypress="return IsInvoicenumber(event);"
													ondrop="return false;"> <label class="error"
													id="errorInvoicenumber" style="display: none"> </label>
											</div>
										</div>
										<div class="row1" id="grpvehicleNumber" class="form-group">
											<label class="L-size control-label" for="FuelSelectVehicle">Required For Vehicle :</label>
											<div class="I-size">
												<input type="hidden" id="SelectVehicle" name="VID"
													style="width: 100%;" required="required"
													placeholder="Please Enter 2 or more Vehicle Name" /> <span
													id="vehicleNumberIcon" class=""></span>
												<div id="vehicleNumberErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<div class="row1" id="grpinvoiceDate" class="form-group">
											<label class="L-size control-label" for="invoiceDate">Requited
												Date :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="input-group input-append date" id="opendDate">
													<input type="text" class="form-text" name="REQUITED_DATE"
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
											<label class="L-size control-label">Requisition
												Remarks :</label>
											<div class="I-size">
												<textarea class="form-text" name="REQUITED_REMARK" rows="3"
													maxlength="500" onkeypress="return IsVendorRemark(event);"
													ondrop="return false;"> 
				                                </textarea>
												<label class="error" id="errorVendorRemark"
													style="display: none"> </label>
											</div>
										</div>
									</div>
								</div>
							</fieldset>
							<fieldset>
								<legend>Required Parts </legend>
								<div class="box">
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
												<div class="row1" id="grpquantity" class="form-group">
													<label class="L-size control-label" for="quantity">Quantity
														:</label>

													<div class="I-size">
														<input type="text" class="form-text" name="quantity_many"
															min="0.0" id="quantity" maxlength="4"
															placeholder="ex: 23.78" required="required"
															data-toggle="tip"
															data-original-title="enter Part Quantity"
															onkeypress="return isNumberKey(event,this);"
															ondrop="return false;"> <span id="quantityIcon"
															class=""></span>
														<div id="quantityErrorMsg" class="text-danger"></div>

													</div>
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

							<fieldset class="form-actions">
								<div class="row1">
									<div class="col-md-10 col-md-offset-2">
										<button type="submit" class="btn btn-success">Create
											Requisition</button>
										<a class="btn btn-link"
											href="<c:url value="/PartRequisition/1/1.in"/>">Cancel</a>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IT/InventoryReqValidate.js" />"></script>
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
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>