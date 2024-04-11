<%@ include file="taglib.jsp"%>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> / <a
						href="<c:url value="/NewInventory/1.in"/>">New Inventory</a>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/NewInventory/1.in"/>">Cancel</a>
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
						<legend>Inventory Search</legend>
						<div class="row">
							<div class="box box-success">
								<div class="box-header"></div>
								<div class="box-body no-padding">
									<form action="ReportInventory.in" method="post">
										<div class="form-horizontal ">
											<fieldset>
												<div class="row1">
												<input type="hidden" id="showSubLocation" value="${showSubLocation}">
												<input type="hidden" id="mainLocationIds" value="${mainLocationIds}">
												<input type="hidden" id="validateSubLocation" >
													<label class="L-size control-label">Search Parts
														Number : </label>
													<div class="I-size">
														<input type="hidden" id="searchpart" name="partid"
															style="width: 100%;" required="required" value="0"
															placeholder="Please Enter 2 or more Part Name or Part Number" />
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Category :</label>
													<div class="I-size">
														<select class="select2" name="category" id="category"
															style="width: 100%;">
															<option value=""><!-- Please select --></option>
															<c:forEach items="${PartCategories}" var="PartCategories">
																<option value="${PartCategories.pcid}"><c:out
																		value="${PartCategories.pcName}" /></option>
															</c:forEach>
														</select>
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Manufacturer :</label>
													<div class="I-size">
														<select class=" select2" name="make" style="width: 100%;"
															id="make">
															<option value=""><!-- Please select --></option>
															<c:forEach items="${PartManufacturer}"
																var="PartManufacturer">

																<option value="${PartManufacturer.pmid}"><c:out
																		value="${PartManufacturer.pmName}" /></option>
															</c:forEach>
														</select> <label id="errormake" class="error"></label>
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Warehouse
														location : </label>
													<div class="I-size">
														<select class="select2" name="locationId" id="location" 
														onchange="showSubLocationDropDown();"	style="width: 100%;">
															<option value=""></option>
															<c:forEach items="${PartLocationPermission}" var="PartLocationPermission">
																<option value="${PartLocationPermission.partLocationId}"><c:out
																		value="${PartLocationPermission.partLocationName}" />
																</option>
															</c:forEach>
														</select>
													</div>
												</div>
												<div class="row1" id="subLocation" class="form-group" style="display:none">
													<label class="L-size control-label" for="location">Sub location :<abbr title="required">*</abbr>
													</label>
													<div class="I-size">
														<input type="hidden" name="subLocationId"
															id="subLocationId" style="width: 100%;"
															required="required"
															placeholder="Please Enter 2 or more location Name" /> <span
															id="partLocationIcon" class=""></span>
														<div id="partLocationErrorMsg" class="text-danger"></div>
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Invoice Number:</label>
													<div class="I-size">
														<input type="text" class="form-text" name="invoice_number"
															maxlength="25"
															onkeypress="return IsInvoicenumber(event);"
															ondrop="return false;"> <label class="error"
															id="errorInvoicenumber" style="display: none"> </label>
													</div>

												</div>
												<div class="row1">
													<label class="L-size control-label">Vendor :</label>
													<div class="I-size" id="vendorSelect">
														<input type="hidden" id="selectVendor2" name="vendor_id"
															style="width: 100%;" value="0"
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
															<a href="<c:url value="/NewInventory/1.in"/>"
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
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Reportlanguage.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Report.validate.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#category, #make, #location ").select2({
				placeholder : "Please select "
			}), $("#tagPicker").select2({
				closeOnSelect : !1
			});
		});
	</script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>
