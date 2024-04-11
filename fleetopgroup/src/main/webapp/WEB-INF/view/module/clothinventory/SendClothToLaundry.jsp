<%@ include file="../../taglib.jsp"%>
<%@page import="org.springframework.security.core.authority.SimpleGrantedAuthority"%>
<%@page import="org.springframework.security.core.GrantedAuthority"%>
<%@page import="java.util.Collection"%>
<%@page import="org.springframework.web.servlet.ModelAndView"%>
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css">
<div class="content-wrapper">
	<section class="panel panel-success">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
						<a href="open" >Home</a>/ <a href="ClothInventory.in">New Upholstery Inventory</a>
				</div>
				<div class="pull-right">
						<% 
						Collection<GrantedAuthority>  permission = (Collection<GrantedAuthority>)request.getAttribute("permissions");
						if(permission.contains(new SimpleGrantedAuthority("ADD_CLOTH_TYPES_INVENTORY"))) {
						%>
						
  						<% } %>
					
				</div>
				<div class="col-md-2">
							<div class="input-group">
								<span class="input-group-addon"> <span aria-hidden="true">CI-</span></span>
								<input class="form-text" id="searchClothInv" onkeyup="searchClothInvOnEnter(event);" name="tripStutes"
									type="number" min="1" required="required"
									placeholder="CI-ID eg:7878" maxlength="20"> <span
									class="input-group-btn">
									<button type="submit" onclick="clothInvoiceSearch();" name="search" id="search-btn"
										class="btn btn-success btn-sm">
										<i class="fa fa-search"></i>
									</button>
								</span>
							</div>
				</div>
				
			</div>
		</div>
	</section>
	<section class="panel panel-success">

		<% if(permission.contains(new SimpleGrantedAuthority("VIEW_BATTERY_INVENTORY"))) {%>
			<div class="row" id="searchData">
			<div id="countDiv" style="display : none;" class="col-md-4 col-sm-4 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i class="fa fa-cubes"></i></span>
						<div class="info-box-content">
						 <input
								type="hidden" value="${location}" id="statues">
							<span class="info-box-text" id="countId">Total Upholstery Invoice</span> 
							<span id="totalClothInvoice" class="info-box-number"></span>
						</div>
					</div>
				</div>
			</div>
		<% } %>
	</section>
		
	<div class="content" >
		
		<% 
			if(!permission.contains(new SimpleGrantedAuthority("ADD_CLOTH_TYPES_INVENTORY"))) {
		%>
			Unauthorized Access !!
		<% } %>
		<% 
			if(permission.contains(new SimpleGrantedAuthority("ADD_CLOTH_TYPES_INVENTORY"))) {
		%>
		<br/>
			<div class="row" id="addClothDiv">
			   <input type="hidden" id="accessToken" value="${accessToken }">
				<div class="col-md-offset-1 col-md-9">
						<div class="form-horizontal ">
							<fieldset>
								<legend>Search Upholstery Types </legend>
								<div class="box">
									<div class="box-body">
										<div class="panel panel-success">
											<div class="panel-body">
											
										<div class="row1" id="grppartLocation" class="form-group">
											<label class="L-size control-label" for="warehouselocation">Warehouse
												location :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="hidden" name="WAREHOUSE_LOCATION_ID"
													id="warehouselocation" style="width: 100%;"
													required="required"
													placeholder="Please Enter 2 or more location Name" /> <span
													id="partLocationIcon" class=""></span>
												<div id="partLocationErrorMsg" class="text-danger"></div>
											</div>
										</div>
										
										<div class="row1">
											<label class="L-size control-label"> Laundry Vendor :<abbr
												title="required">*</abbr></label>
											<div class="I-size" id="vendorSelect">
												<input type="hidden" id="selectVendor" name="VENDOR_ID"
													style="width: 100%;" required="required" value="0"
													placeholder="Please Select Vendor Name" /> <label
													class="error" id="errorVendorSelect"> </label>

											</div>

										</div>
												<div class="row1" id="grpmanufacturer" class="form-group">
													<label class="L-size control-label" for="manufacurer">Upholstery Types :<abbr title="required">*</abbr>
													</label>
													<div class="I-size">
														<input type="hidden" id="clothTypes"
															name="clothTypes" style="width: 100%;"
															placeholder="Please Enter 2 or more Cloth Types Name" />
													</div>
												<input type="hidden" id="clothTypeIds">
											</div>
												
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="col-md-9">
														<div class="col-md-1">
															<label class="control-label">Stock Qty</label>

														</div>
														<div class="col-md-1">
															<label class="control-label">Quantity</label>

														</div>
														<div class="col-md-2">
															<label class="control-label">Unit Cost</label>

														</div>
														<div class="col-md-1">
															<label class="control-label">Discount</label>
														</div>
														<div class="col-md-1">
															<label class="control-label">GST</label>
														</div>
														<div class="col-md-2">
															<label class="control-label">Total</label>
														</div>
													</div>
												</div>
												<div class="row1" id="grpquantity" class="form-group">
													<label class="L-size control-label" for="quantity">
													</label>
													<div class="col-md-9">
														<div class="col-md-1">
															<input type="text" class="form-text" name="stockQuantity"
																min="0.0" id="stockQuantity" maxlength="4"
																placeholder="ex: 23.78" required="required"
																data-toggle="tip" readonly="readonly"
																data-original-title="enter Tyre Quantity"
																onkeypress="return isNumberKey(event,this);"
																onkeyup="javascript:sumthere('quantity', 'unitprice', 'discount', 'tax', 'tatalcost');"
																ondrop="return false;">
														</div>
														<div class="col-md-1">
															<input type="text" class="form-text" name="quantity_many"
																min="0.0" id="quantity" maxlength="4"
																placeholder="ex: 23.78" required="required"
																data-toggle="tip"
																data-original-title="enter Tyre Quantity"
																onkeypress="return isNumberKey(event,this);"
																onblur="validateQuantity();"
																onkeyup="validateQuantity();sumthere('quantity', 'unitprice', 'discount', 'tax', 'tatalcost'); "
																ondrop="return false;"> <span id="quantityIcon"
																class=""></span>
															<div id="quantityErrorMsg" class="text-danger"></div>
														</div>
														<div class="col-md-2">
															<input type="text" class="form-text"
																name="unitprice_many" id="unitprice" maxlength="7"
																min="0.0" placeholder="Unit Cost" required="required"
																data-toggle="tip" data-original-title="enter Unit Price"
																onkeypress="return isNumberKey(event,this);"
																onkeyup="javascript:sumthere('quantity', 'unitprice', 'discount', 'tax', 'tatalcost');"
																ondrop="return false;"> <span id="unitpriceIcon"
																class=""></span>
															<div id="unitpriceErrorMsg" class="text-danger"></div>
														</div>

														<div class="col-md-1">
															<input type="text" class="form-text" name="discount_many"
																min="0.0" id="discount" maxlength="5"
																placeholder="Discount" required="required"
																data-toggle="tip" data-original-title="enter Discount"
															
																	onkeypress="return isNumberKey(event,this);"
																onkeyup="javascript:sumthere('quantity', 'unitprice', 'discount', 'tax', 'tatalcost');"
																ondrop="return false;"><span id="discountIcon"
																class=""></span>
															<div id="discountErrorMsg" class="text-danger"></div>

														</div>
														<div class="col-md-1">
															<input type="text" class="form-text" name="tax_many"
																id="tax" maxlength="5" min="0.0" placeholder="GST"
																required="required" data-toggle="tip"
																data-original-title="enter GST"
																
																	onkeypress="return isNumberKey(event,this);"
																onkeyup="javascript:sumthere('quantity', 'unitprice', 'discount', 'tax', 'tatalcost');"
																ondrop="return false;"><span id="taxIcon"
																class=""></span>
															<div id="taxErrorMsg" class="text-danger"></div>
														</div>
														<div class="col-md-2">
															<input type="text" class="form-text" maxlength="8"
																value="0.0" min="0.0" name="tatalcost" id="tatalcost" readonly="readonly"
																data-toggle="tip" data-original-title="Total Cost"
																onkeypress="return isNumberKey(event,this);"
																ondrop="return false;">
														</div>
													</div>
													<br> <label class="error" id="errorINEACH"
														style="display: none"></label>

												</div>
												<div id="moreParts">
													
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
							<fieldset>
								<legend>Upholstery Inventory Details</legend>
								<div class="box">
									<div class="box-body">
										<div class="row1">
													<label class="L-size control-label">Send
														date :<abbr title="required">*</abbr>
													</label>
													<div class="col-md-3">
														<div class="input-group input-append date" id="opendDate">
															<input type="text" class="form-text" name="TR_OPEN_DATE" 
																required="required" id="sentDate" readonly="readonly"
																data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" />
															<span class="input-group-addon add-on"> <span
																class="fa fa-calendar"></span>
															</span>
														</div>
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label"> Required
														date :</label>
													<div class="col-md-3">
														<div class="input-group input-append date"
															id="requiredDate">
															<input type="text" class="form-text" id="receiveDate"
																name="TR_REQUIRED_DATE" readonly="readonly"
																data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" />
															<span class="input-group-addon add-on"> <span
																class="fa fa-calendar"></span>
															</span>
														</div>
													</div>
												</div>
										<div class="row1">
											<label class="L-size control-label">Modes of Payment
												<abbr title="required">*</abbr>
											</label>
											<div class="I-size" id="paymentDiv">
												<select class="form-text" name="PAYMENT_TYPE_ID"
													id="renPT_option" required="required">
													<option value="1">Cash</option>
												</select>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label" id="target1"
												for="renPT_option">Enter </label>
											<div class="I-size">
												<input type="text" id="PAYMENT_NUMBER" class="form-text" name="PAYMENT_NUMBER"
													onkeypress="return IsAlphaNumericPaynumber(event);"
													ondrop="return false;" maxlength="25"> <label
													class="error" id="errorPaynumber" style="display: none">
												</label>
											</div>
										</div>
										<div class="row1">
													<label class="L-size control-label"> Quote
														No </label>
													<div class="I-size">
														<input class="string required form-text" id="quoteNumber"
															name="quoteNumber" type="text">
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label"> Manual
														No </label>
													<div class="I-size">
														<input class="string required form-text" id="manualNumber"
															name="manualNumber" type="text">
													</div>
												</div>
												
										<input type="hidden" id="tallyIntegrationRequired" value="${configuration.tallyIntegrationRequired}">
										<c:if test="${configuration.tallyIntegrationRequired}">
											<div class="row1" id="grpmanufacturer" class="form-group">
											<label class="L-size control-label" for="manufacurer">Tally Company Name :<abbr title="required">*</abbr></label>
												<div class="I-size">
													<input type="hidden" id="tallyCompanyId" name="tallyCompanyId" style="width: 100%;" value="0"
													  placeholder="Please Enter Tally Company Name" />
												</div>
											</div>
										</c:if>
									
										<div class="row1">
											<label class="L-size control-label">Description :</label>
											<div class="I-size">
												<textarea class="form-text"  name="description" rows="3"
													maxlength="150" id="description" 
													ondrop="return false;"> 
				                                </textarea>
												
											</div>
										</div>
									</div>
								</div>
							</fieldset>
							<fieldset class="form-actions">
								<div class="row1" id="sentLaundry">
									<div class="col-md-10 col-md-offset-2">
										<button type="submit" id="submit" onclick="sendClothToLaundry();" class="btn btn-success">Send
											To Laundry</button>
										<a class="btn btn-link"
											href="ClothInventory.in">Cancel</a>
									</div>
								</div>
							</fieldset>
						</div>
				</div>
			</div>
			<%@ include file="/WEB-INF/view/payment/PaymentDetails.jsp"%>
		<% } %>
	</div>
</div>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/clothinventory/clothUtility.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/clothinventory/sendClothToLaundry.js"></script>
