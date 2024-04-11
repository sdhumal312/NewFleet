<%@ include file="../../taglib.jsp"%>
<%@page import="org.springframework.security.core.authority.SimpleGrantedAuthority"%>
<%@page import="org.springframework.security.core.GrantedAuthority"%>
<%@page import="java.util.Collection"%>
<%@page import="org.springframework.web.servlet.ModelAndView"%>
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<div class="content-wrapper">
	<section class="panel panel-success">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="open" >Home</a>/ <a href="TyreInventory.in/1">New Tyre Expenses</a>
				</div>
			</div>
		</div>
	</section>
	
<div class="content" >
	<div class="row" id="addTyreDiv">
		<div class="col-md-offset-1 col-md-9">
			<form method="POST" enctype="multipart/form-data" id="fileUploadForm">	
				<div class="form-horizontal ">
					<fieldset>
						<legend>Add Tyre Expense Details</legend>
						<div class="box">
							<div class="box-body">
								<div class="panel panel-success">
									<div class="panel-body">
										<div class="row1" id="tyreNum" class="form-group">
											<label class="L-size control-label" for="manufacurer">Tyre Number :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="hidden" id="tyreId"
													name="tyreNumber" style="width: 100%;"
													placeholder="Please Enter 2 or more Cloth Types Name" />
											</div>
										</div>
										<div class="row1" id="" class="form-group">
											<label class="L-size control-label" for="manufacurer">Tyre Expense :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="hidden" id="tyreExpenseId"  
												name="tyreExpenseName"  style="width: 100%;" 
												/>
											</div>
										</div>
									<div class="row1" id="grpinvoiceDate" class="form-group">
										<label class="L-size control-label" for="invoiceDate">Expense Date :<abbr title="required">*</abbr>
										</label>
										<div class="I-size">
											<div class="input-group input-append date" id="opendDate">
												<input type="text" class="form-text" name="tyreExpenseDate"
													id="tyreExpenseDateId" required="required" readonly="readonly"
													data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
													class="input-group-addon add-on"> <span
													class="fa fa-calendar"></span>
												</span>
											</div>
											<span id="expenseDateIcon" class=""></span>
											<div id="expenseDateErrorMsg" class="text-danger"></div>
										</div>
									</div>

									<div class="row1">
											<label class="L-size control-label"></label>
											<div class="col-md-9">
												<div class="col-md-3">
													<label class="control-label">Tyre Expense Amount</label>
												</div>
												<div class="col-md-1">
													<label class="control-label">Discount</label>
												</div>
												<div class="col-md-1">
													<label class="control-label">GST</label>
												</div>
												<div class="col-md-3">
													<label class="control-label">Total</label>
												</div>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label" for="quantity"> </label>
											<div class="col-md-9">
												<div class="col-md-3">
													<input type="text" class="form-text"
														name="unitprice_many" id="unitprice" maxlength="7"
														min="0.0" placeholder="Unit Cost" required="required"
														data-toggle="tip" data-original-title="enter Unit Price"
														onkeypress="return isNumberKeyWithDecimal(event,this.id);"
														onkeyup="javascript:sumthere('unitprice', 'discount', 'tax', 'totalCost');"
														ondrop="return false;"> <span id="unitpriceIcon"
														class=""></span>
													<div id="unitpriceErrorMsg" class="text-danger"></div>
												</div>

												<div class="col-md-1">
													<input type="text" class="form-text" name="discount_many" value="0"
														min="0.0" id="discount" maxlength="5" 
														placeholder="Discount" required="required"
														data-toggle="tip" data-original-title="enter Discount"
														onkeypress="return isNumberKeyWithDecimal(event,this.id);"
													
														onkeyup="javascript:sumthere('unitprice', 'discount', 'tax', 'totalCost');"
														ondrop="return false;"><span id="discountIcon"
														class=""></span>
													<div id="discountErrorMsg" class="text-danger"></div>
												</div>
												<div class="col-md-1">
													<input type="text" class="form-text" name="tax_many" value="0"
														id="tax" maxlength="5" min="0.0" placeholder="GST"
														required="required" data-toggle="tip" 
														data-original-title="enter GST"
												      onkeypress="return isNumberKeyWithDecimal(event,this.id);" 
														onkeyup="javascript:sumthere('unitprice', 'discount', 'tax', 'totalCost');"
												   		ondrop="return false;"><span id="taxIcon"
														class=""></span>
													<div id="taxErrorMsg" class="text-danger"></div>
												</div>
												<div class="col-md-3">
													<input type="text" class="form-text" maxlength="8"
														value="0.0" min="0.0" name="totalCost" id="totalCost" readonly="readonly"
														data-toggle="tip" data-original-title="Total Cost"
														onkeypress="return isNumberKey(event,this);"
														ondrop="return false;">
												</div>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label" id="Type">Description
												:</label>
											<div class="I-size">
												<input type="text" class="form-text" id="descriptionId"
													maxlength="249" name="description"
													placeholder="Enter description" />
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Vendor :</label>
											<div class="I-size">
												<input type="hidden" id="selectVendor" name="vendorName"
													style="width: 100%;" 
													placeholder="Please Select Vendor Name" />
											</div>
										</div>
										<div class="row1">
										<label class="L-size control-label" id="Type">Tyre Document</label>
											<div class="I-size">
												<input type="file" name="input-file-preview" id="tyreExpenseDocument" /> 
												<span id="tyreExpenseDocumentIcon" class=""></span>
												<div id="tyreExpenseDocumentIconErr" class="text-danger"></div>
											</div>
										</div>
										<div class="row1">
											<div class="input_fields_wrap">
												<button class="add_field_button btn btn-info"
													data-toggle="tip"
													data-original-title="Click add one more part">
													<i class="fa fa-plus"></i> Add More
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
								<button type="submit" id="submit" class="btn btn-success">Add Tyre Expense</button>
								<a class="btn btn-link" href="TyreInventory/1">Cancel</a>
							</div>
						</div>
					</fieldset>
				</div>
			</form>
		</div>
	</div>
</div>

</div>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IT/AddTyreExpenseDetails.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>

