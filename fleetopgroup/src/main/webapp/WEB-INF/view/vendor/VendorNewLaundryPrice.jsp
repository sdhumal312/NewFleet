<%@ include file="taglib.jsp"%>
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="vendorHome.in">Vendors</a> / <span
						id="NewVehi">Show Vendor</span>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_LAUNDRY_RATE')">
						<a class="btn btn-success btn-sm" onclick="showHideAddView();">
   							 <span class="fa fa-plus">
								Add Vendor Laundry Rate</span></a>
  						</a>
					</sec:authorize>
					<a id="cancelDiv" class="btn btn-link"
											href="">Cancel</a>
				
				</div>
				
			</div>
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_VENDOR')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_VENDOR')">
					<div class="row">
						<input type="hidden" id="vendorId" name="vendorId" value="${vendorId}" />
						<div class="col-md-4">
							<h3 id="vendorLink">
								
							</h3>
						</div>
					</div>
					<div class="secondary-header-title">
						<ul class="breadcrumb">
							<li>Vendor Type : <a data-toggle="tip"
								data-original-title="Vendor Type " id="vendorType"> </a></li>
							<li>Phone : <a data-toggle="tip" id="vendorPhone" data-original-title="Phone"></a></li>
							<li>PAN No : <a data-toggle="tip"
								data-original-title="PAN No" id="vendorPan"></a></li>
							<li>GST No : <a data-toggle="tip"
								data-original-title="GST No" id="vendorGst"> </a></li>

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
			<div class="main-body" id="contentTable">
					<div class="box">
						<div class="box-body">
						 
							<div class="table-responsive">
								<input type="hidden" id="startPage" value="${SelectPage}"> 
								<table id="VendorPaymentTable" class="table table-hover table-bordered">
								
								</table>
							</div>
						</div>
					</div>
					<div class="text-center">
						<ul id="navigationBar" class="pagination pagination-lg pager">
							
						</ul>
					</div>
		</div>
		</sec:authorize>
		
		<div class="modal fade" id="editLaundryRate" role="dialog">
			<div class="modal-dialog modal-md">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Edit Laundry Rate</h4>
					</div>
					<div class="modal-body">
											<div class="row" id="grpwoJobType" class="form-group">
													<label class="L-size control-label" for="from">Cloth
														Name </label>
													<div class="I-size">
														<div class="col-md-8">
															<div class="row">
																<input type="hidden" name="VENDORID"
																	value="${vendor.vendorId}"
																	id="editvendorId">
																		
																<input type="hidden" id="vendorLaundryRateId" />	
																<input type="hidden" id="clothTypesId" />
																<input type="hidden" id="editquantity" value="1">
																<input type="text" name="clothTypes" id="editclothTypes"
																    	onkeypress="return isNumberKey(event)" 
																	required="required" style="width: 100%;" readonly="readonly"
																	class="form-text" /> 
															</div>
														</div>
													</div>
												</div>
												<br/>
												<div class="row" id="grpwoJobType" class="form-group">
													<label class="L-size control-label" for="from">Cloth
														each Price: </label>
													<div class="I-size">
														<div class="col-md-8">
															<input type="text" name="parteachcost" class="form-text"
																placeholder="ech cost" required="required" 
																id="editparteachcost" maxlength="7" data-toggle="tip"
															 onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																data-original-title="enter Each Cost"
																ondrop="return false;"
																onkeyup="javascript:sumthere('editquantity', 'editparteachcost','editdiscount', 'edittax', 'edittatalcost');"
																min="0.00"> 
													</div>
												</div>
												</div>
												<br/>
												<div class="row" id="grpwoJobType" class="form-group">
													<label class="L-size control-label" for="from">Cloth
														Discount :</label>
													<div class="I-size">
														<div class="col-md-8">
															<div class="input-group">
																<input type="text" class="form-text" placeholder="Dis"
																	name="discount" required="required" id="editdiscount"
																	maxlength="5" data-toggle="tip"
																	data-original-title="enter discounr"
																 onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																	ondrop="return false;"
																	onkeyup="javascript:sumthere('editquantity', 'editparteachcost','editdiscount', 'edittax', 'edittatalcost');"
																	min="0.00"> <span class="input-group-addon">%</span>
															</div>
															<span id="discountIcon" class=""></span>
															<div id="discountErrorMsg" class="text-danger"></div>
														</div>
													</div>
												</div>
												<br/>
												<div class="row" id="grpwoJobType" class="form-group">
													<label class="L-size control-label" for="from">Cloth
														GST Percentage: </label>
													<div class="I-size">
														<div class="col-md-8">
															<div class="input-group">
																<input type="text" class="form-text" placeholder="GST"
																	name="tax" required="required" id="edittax" maxlength="5"
																	data-toggle="tip" data-original-title="enter GST"
																   	onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																	onkeyup="javascript:sumthere('editquantity', 'editparteachcost','editdiscount', 'edittax', 'edittatalcost');"
																	min="0.0"> <span class="input-group-addon">%</span>
															</div>
															<span id="taxIcon" class=""></span>
															<div id="taxErrorMsg" class="text-danger"></div>
														</div>
													</div>
												</div>
												<br/>
												<div class="row" id="grpwoJobType" class="form-group">
													<label class="L-size control-label" for="from">Total
													</label>
													<div class="I-size">
														<div class="col-md-4">
															<input type="text" name="totalcost" data-toggle="tip"
																data-original-title="Total cost" class="form-text"
																required="required" id="edittatalcost" readonly="readonly">
														</div>
													</div>
												</div>
												
					</div>

					<div class="modal-footer">
						<button type="submit" onclick="updateLaundryRate()" class="btn btn-success"
							 id="saveEmail">Save</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>

				</div>
			</div>
		</div>
		
		
		
			<div class="row hide" id="addVendorLaundryRate">
				<div class="col-md-9 col-sm-9 col-xs-12">
					<div class="row">
							<fieldset>
								<legend>Select Vender Laundry Price </legend>
								<div class="form-horizontal ">
									<div class="box">
										<div class="box-body">
											<div class="form-horizontal ">
												<div class="row1" id="grpwoJobType" class="form-group">
													<label class="L-size control-label" for="from">Cloth
														Name </label>
													<div class="I-size">
														<div class="col-md-8">
															<div class="row">
																<input type="hidden" name="VENDORID"
																	value="${vendor.vendorId}"
																	id="vendorId">
																<!--  -->
																<input type="hidden" name="clothTypes" id="clothTypes"
																	required="required" style="width: 100%;"
																	required="required"
																	placeholder="Please Enter 2 or more Part Name" /> 
															</div>
														</div>
													</div>
												</div>
												<div class="row1" id="grpwoJobType" class="form-group">
													<label class="L-size control-label" for="from">Cloth
														each Price: </label>
													<div class="I-size">
														<div class="col-md-8">
															<!-- this Quantity in one -->
															<input type="hidden" class="form-text" placeholder="Qty"
																name="quantity" required="required" id="quantity"
																readonly="readonly" value="1">
															<!-- this each cost in one -->
															<input type="text" name="parteachcost" class="form-text"
																placeholder="ech cost" required="required" onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																id="parteachcost" maxlength="7" data-toggle="tip"
																data-original-title="enter Each Cost"
																onkeypress="return isNumberKeyEach(event);"
																ondrop="return false;"
																onkeyup="javascript:sumthere('quantity', 'parteachcost','discount', 'tax', 'tatalcost');"
																min="0.00"> <span id="parteachcostIcon" class=""></span>
															<div id="parteachcostErrorMsg" class="text-danger"></div>
														</div>
													</div>
												</div>
												<div class="row1" id="grpwoJobType" class="form-group">
													<label class="L-size control-label" for="from">Cloth
														Discount :</label>
													<div class="I-size">
														<div class="col-md-8">
															<div class="input-group">
																<input type="text" class="form-text" placeholder="Dis"
																	name="discount" required="required" id="discount" onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																	maxlength="5" data-toggle="tip"
																	data-original-title="enter discounr"
																	onkeypress="return isNumberKeyDis(event);"
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
													<label class="L-size control-label" for="from">Cloth
														GST Percentage: </label>
													<div class="I-size">
														<div class="col-md-8">
															<div class="input-group">
																<input type="text" class="form-text" placeholder="GST"
																	name="tax" required="required" id="tax" maxlength="5"
																	
																	onkeypress="return isNumberKeyWithDecimal(event,this.id);"
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
												
											</div>
										</div>
									</div>
								</div>
							</fieldset>
							<fieldset class="form-actions">
								<div class="row">
									<div class="col-md-10 col-md-offset-2">
										<button type="submit" id="submit" onclick="saveLaundryRate();" class="btn btn-success">Create
											Fixed Price</button>
										<a id="cancel" class="btn btn-link"
											href="">Cancel</a>
									</div>
								</div>
							</fieldset>
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
	
	</section>
</div>

<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/clothinventory/showVendorLaundryPrice.js"></script>
<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>  