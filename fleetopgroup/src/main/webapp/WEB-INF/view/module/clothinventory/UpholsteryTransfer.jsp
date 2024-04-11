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
			<div class="box-header">
				<div class="pull-left">
					<a href="open">Home</a> / <a
						href="ClothInventory.in">New Upholstery Inventory</a>
					/ <span>Upholstery Transfer Details</span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/ClothInventory.in"/>">Cancel</a>
				</div>
			</div>
			<sec:authorize access="!hasAuthority('TRANSFER_MULTI_TYRE')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
		</div>
	</section>
	
	<section class="content">
		<sec:authorize access="!hasAuthority('TRANSFER_INVENTORY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('TRANSFER_INVENTORY')">
			<div class="row">
				<div class="col-md-offset-1 col-md-9 col-sm-9 col-xs-12">
						<div class="form-horizontal ">
							<fieldset>
								<legend>Transfer Upholstery Inventory</legend>
								<div class="box">
									<div class="box-body">
										<div class="row1">
											<label class="L-size control-label">Transfer From :<abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input type="hidden" name="fromLocation"
													id="fromLocationID" style="width: 100%;" 
													placeholder="Please Enter From Location" />
											</div>
										</div>
										
										<div class="row1">
											<label class="L-size control-label">Transfer
												To:<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="hidden" name="toLocation"
													id="toLocationId" style="width: 100%;" 
													placeholder="Please Enter To Location" />
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="issue_vehicle_id">Transfer
												Recevied By :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">

												<input class="" placeholder="Subscribe users" id="transferReceivedById"
													type="hidden" style="width: 100%"
													name="transferReceivedBy"
													onkeypress="return Isservice_subscribeduser(event);"
													required="required" ondrop="return false;"> <span
													id="subscribedIcon" class=""></span>
												<div id="subscribedErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Transfer via :</label>
											<div class="I-size">
												<select style="width: 100%;" name="transferVia"
													id="transferViaId" required="required">
													<option value="1">AIR</option>
													<option value="2">COURIER</option>
													<option value="3">EXPEDITED</option>
													<option value="4">GROUND</option>
													<option value="5">NEXT DAY</option>
													<option value="6">NONE</option>
												</select>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Transfer Reason :</label>
											<div class="I-size">
												<textarea class="form-text" name="transferReason" rows="3"
													id="transferReasonId" 
													maxlength="150" >
				                                </textarea>
												<label class="error" id="errorVendorRemark"
													style="display: none"> </label>
											</div>
										</div>
									</div>
								</div>
							</fieldset>
							
							<fieldset>
							   <div class="box">
									<div class="box-body">
										<div class="panel panel-success">
											<div class="panel-body">
												<div class="row1">
														<label class="L-size control-label">Upholstery Name :<abbr
														title="required">*</abbr> </label>
														<div class="I-size">
															<input type="hidden" id="clothTypeId_0" name="clothType"
																style="width: 100%;" 
																placeholder="Please Enter Upholstery Name" />
														</div>
												</div>
												<div class="row1" class="form-group">
														<label class="L-size control-label">Types of Upholstery
															<abbr title="required">*</abbr>
														</label>
														<div class="I-size">
															<select style="width: 100%;" name="stockType"
																id="stockTypeId_0" required="required" onchange="validateInspectionParameter(this);">
																<option value="1">NEW/FRESH</option>
																<option value="2">OLD/USED</option>
															</select>
														</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Current Stock
														Qty:
													</label>
													<div class="I-size">
														<input type="text" class="form-text"
															name="stockQty"	id="stockQty_0" 
															style="width: 15%;" readonly="readonly" />
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Transfer Stock
														Qty:<abbr title="required">*</abbr>
													</label>
													<div class="I-size">
														<input type="text" class="form-text"
															name="transferStockQty"	id="transferStockQty_0"
															onkeypress="return isNumberKeyWithDecimal(event,this.id)"
															style="width: 15%;" onkeyup="validateQty(0);" />
													</div>
												</div>
											</div>
										</div>
										<div class="row1">
											<div class="input_fields_wrap">
												<button class="add_field_button btn btn-info"
													data-toggle="tip"
													data-original-title="Click add one more upholstery type">
													<i class="fa fa-plus"></i> Add More
												</button>
											</div>
										</div>
									</div>
								</div>		
							</fieldset>	
									
							<fieldset class="form-actions">
											<div class="row1">
												<div class="col-md-10 col-md-offset-2">
													<input class="btn btn-success" name="commit" type="submit"
														value="Transfer Upholstery Inventory" onclick="saveUpholsteryTransfer();"> 
														<a href="<c:url value="/ClothInventory.in"/>">Cancel</a>
												</div>
											</div>
							</fieldset>
						</div>
				</div>
			</div>
		</sec:authorize>
	</section>
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>  
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript" 
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/clothinventory/UpholsteryTransfer.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>