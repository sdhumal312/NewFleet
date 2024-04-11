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
						href="<c:url value="/vendor/${vendor.vendorId}/${SelectPage}.in"/>">Vendors</a> / <span
						id="NewVehi">Show Vendor</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-link"
						href="<c:url value="/${vendor.vendorId}/ShowVendor.in?vendorId=${vendor.vendorId}&page=${SelectPage}"/>">Cancel</a>
				</div>
			</div>
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_VENDOR')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_VENDOR')">
					<div class="row">
						<div class="col-md-4">
							<h3>
								<a href="<c:url value="/${vendor.vendorId}/ShowVendor.in?vendorId=${vendor.vendorId}&page=${SelectPage}"/>"
									data-toggle="tip" data-original-title="Click Vendor Details">
									<c:out value="${vendor.vendorName}" />
								</a>
							</h3>
						</div>
					</div>
					<div class="secondary-header-title">
						<ul class="breadcrumb">
							<li>Vendor Type : <a data-toggle="tip"
								data-original-title="Vendor Type "> <c:out
										value="${vendor.vendorId}" /></a></li>
							<li>Phone : <a data-toggle="tip" data-original-title="Phone"><c:out
										value="${vendor.vendorPhone}" /></a></li>
							<li>PAN No : <a data-toggle="tip"
								data-original-title="PAN No"><c:out
										value="${vendor.vendorPanNO}" /></a></li>
							<li>GST No : <a data-toggle="tip"
								data-original-title="GST No"> <c:out
										value="${vendor.vendorGSTNO}" /></a></li>

						</ul>
					</div>
				</sec:authorize>
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content-body">
		<sec:authorize access="!hasAuthority('VIEW_VENDOR')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_VENDOR')">
			<div class="row">
				<div class="col-md-9 col-sm-9 col-xs-12">
					<div class="row">
						<form id="formWorkOrder"
							action="<c:url value="/saveVendorPartPrice.in"/>" method="post"
							enctype="multipart/form-data" name="formWorkOrder" role="form"
							class="form-horizontal">
							<fieldset>
								<legend>Select Vender Part Price </legend>
								<div class="form-horizontal ">
									<div class="box">
										<div class="box-body">
											<div class="form-horizontal ">
												<div class="row1" id="grpwoJobType" class="form-group">
													<label class="L-size control-label" for="from">Part
														Name </label>
													<div class="I-size">
														<div class="col-md-8">
															<div class="row">
																<input type="hidden" name="VENDORID"
																	value="${vendor.vendorId}"
																	id="vendorId">
																<!--  -->
																<input type="hidden" name="partid" id="inventory_name"
																	required="required" style="width: 100%;"
																	required="required"
																	placeholder="Please Enter 2 or more Part Name" /> <span
																	id="PurchaseOrders_idIcon" class=""></span>
																<div id="PurchaseOrders_idErrorMsg" class="text-danger"></div>
															</div>
														</div>
													</div>
												</div>
												<div class="row1" id="grpwoJobType" class="form-group">
													<label class="L-size control-label" for="from">Part
														each Price: </label>
													<div class="I-size">
														<div class="col-md-8">
															<!-- this Quantity in one -->
															<input type="hidden" class="form-text" placeholder="Qty"
																name="quantity" required="required" id="quantity"
																readonly="readonly" value="1">
															<!-- this each cost in one -->
															<input type="text" name="parteachcost" class="form-text"
																placeholder="ech cost" required="required"
																id="parteachcost" maxlength="7" data-toggle="tip"
																data-original-title="enter Each Cost"
																
																onkeypress="return isNumberKey(event,this);"
																ondrop="return false;"
																onkeyup="javascript:sumthere('quantity', 'parteachcost','discount', 'tax', 'tatalcost');"
																min="0.00"> <span id="parteachcostIcon" class=""></span>
															<div id="parteachcostErrorMsg" class="text-danger"></div>
														</div>
													</div>
												</div>
												<div class="row1" id="grpwoJobType" class="form-group">
													<label class="L-size control-label" for="from">Part
														Discount :</label>
													<div class="I-size">
														<div class="col-md-8">
															<div class="input-group">
																<input type="text" class="form-text" placeholder="Dis"
																	name="discount" required="required" id="discount"
																	maxlength="5" data-toggle="tip"
																	data-original-title="enter discounr"
																	onkeypress="return isNumberKey(event,this);"
																	ondrop="return false;"
																	onkeyup="javascript:sumthere('quantity', 'parteachcost', 'discount', 'tax', 'tatalcost');"
																	min="0.00"> <span class="input-group-addon">%</span>
															</div>
															<span id="discountIcon" class=""></span>
															<div id="discountErrorMsg" class="text-danger"></div>
														</div>
													</div>
												</div>
												<div class="row1" id="grpwoJobType" class="form-group">
													<label class="L-size control-label" for="from">Part
														GST Percentage: </label>
													<div class="I-size">
														<div class="col-md-8">
															<div class="input-group">
																<input type="text" class="form-text" placeholder="GST"
																	name="tax" required="required" id="tax" maxlength="5"
																	onkeypress="return isNumberKey(event,this);"
																	data-toggle="tip" data-original-title="enter GST"
																	onkeyup="javascript:sumthere('quantity', 'parteachcost', 'discount', 'tax', 'tatalcost');"
																	min="0.0"> <span class="input-group-addon">%</span>
															</div>
															<span id="taxIcon" class=""></span>
															<div id="taxErrorMsg" class="text-danger"></div>
														</div>
													</div>
												</div>
												<div class="row1" id="grpwoJobType" class="form-group">
													<label class="L-size control-label" for="from">Total
													</label>
													<div class="I-size">
														<div class="col-md-4">
															<input type="text" name="totalcost" data-toggle="tip"
																data-original-title="Total cost" class="form-text"
																required="required" id="tatalcost" readonly="readonly">
														</div>
													</div>
												</div>
												<div class="input_fields_wrap">
													<button class="add_field_button btn btn-info"
														data-toggle="tip"
														data-original-title="Please Enter single Job & Sub Job Task">
														<i class="fa fa-plus"></i> Add More Parts
													</button>
												</div>
												<p class="help-block">Please Enter one By one</p>
											</div>
										</div>
									</div>
								</div>
							</fieldset>
							<fieldset class="form-actions">
								<div class="row">
									<div class="col-md-10 col-md-offset-2">
										<button type="submit" class="btn btn-success">Create
											Fixed Price</button>
										<a class="btn btn-link"
											href="<c:url value="/${vendor.vendorId}/ShowVendor.in?vendorId=${vendor.vendorId}&page=${SelectPage}"/>">Cancel</a>
									</div>
								</div>
							</fieldset>
						</form>
					</div>
				</div>
				<div class="col-md-2 col-sm-2 col-xs-12">
					<ul class="nav nav-list">
						<li><sec:authorize access="hasAuthority('ADDEDIT_VENDOR_FIXEDPART')">
								<a href="<c:url value="/${vendor.vendorId}/editVendor.in?vendorId=${vendor.vendorId}&page=${SelectPage}"/>"> Edit
									Vendor </a>
							</sec:authorize></li>
					</ul>
				</div>
			</div>
		</sec:authorize>
		<div class="row">
			<small class="text-muted"><b>Created by :</b> <c:out
					value="${vendor.createdBy}" /></small> | <small class="text-muted"><b>Created
					date: </b> <c:out value="${vendor.created}" /></small> | <small
				class="text-muted"><b>Last updated by :</b> <c:out
					value="${vendor.lastModifiedBy}" /></small> | <small class="text-muted"><b>Last
					updated date:</b> <c:out value="${vendor.lastupdated}" /></small>
		</div>
	</section>
	<!-- <script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabsShowVehicle.js" />"></script>

	 -->

	<!-- get the Inventory Drop Down down -->
	<c:url var="findInventoryURL" value="getInventoryList.in" />
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VA/VendorFixedPriceValidate.js" />"></script>

	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
		<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>  
</div>