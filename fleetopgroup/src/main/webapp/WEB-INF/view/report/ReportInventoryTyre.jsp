<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/TyreInventory/1"/>">New Tyre Inventory</a>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/TyreInventory/1"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_INVENTORY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_INVENTORY')">
			<div class="row">
				<div class="col-md-offset-1 col-md-9">
					<fieldset>
						<legend>Inventory Tyre Search</legend>
						<div class="row">
							<div class="box box-success">
								<div class="box-header"></div>
								<div class="box-body no-padding">
									<form action="ReportInventoryTyre.in" method="post">
										<div class="form-horizontal ">
											<fieldset>
												<div class="row1">
													<label class="L-size control-label">Tyre
														Manufacturer : </label>
													<div class="I-size">
														<input type="text" id="manufacurer"
															name="TYRE_MANUFACTURER_ID" style="width: 100%;"
															placeholder="Please Enter 2 or more Tyre Manufacturer Name" />
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Tyre Model : </label>
													<div class="I-size">
														<input type="text" id="tyremodel" name="TYRE_MODEL_ID"
															style="width: 100%;"
															placeholder="Please select Tyre Model" />

													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Tyre Size : </label>
													<div class="I-size">
														<input type="text" id="tyreSize" name="TYRE_SIZE_ID"
															style="width: 100%;"
															placeholder="Please select Tyre Size" />
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Warehouse
														location : </label>
													<div class="I-size">
														<input type="text" name="WAREHOUSE_LOCATION_ID"
															id="warehouselocation" style="width: 100%;"
															placeholder="Please Enter 2 or more location Name" />
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Invoice Number:</label>
													<div class="I-size">
														<input type="text" class="form-text" name="INVOICE_NUMBER"
															maxlength="25"
															onkeypress="return IsInvoicenumber(event);"
															ondrop="return false;"> <label class="error"
															id="errorInvoicenumber" style="display: none"> </label>
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Vendor :</label>
													<div class="I-size" id="vendorSelect">
														<input type="hidden" id="selectVendor" name="VENDOR_ID"
															style="width: 100%;"
															placeholder="Please Select Vendor Name" /> <label
															class="error" id="errorVendorSelect"> </label>
													</div>
												</div>
											</fieldset>
											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>
													<div class="I-size">
														<div class="pull-left">
															<input class="btn btn-success"
																	onclick="this.style.visibility = 'hidden'"
																	name="commit" type="submit" value="Search All">
															<a href="<c:url value="/TyreInventory/1"/>"
																class="btn btn-info"> <span id="Can">Cancel</span>
															</a>
														</div>
													</div>
												</div>
											</fieldset>
										</div>
									</form>
								</div>
							</div>
						</div>
					</fieldset>
				</div>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/ReportInventoryTyre.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#category, #make, #location ").select2({
				placeholder : "Please select "
			}), $("#tagPicker").select2({
				closeOnSelect : !1
			});
		});
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>