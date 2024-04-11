<%@ include file="../../taglib.jsp"%>
<%@page import="org.springframework.security.core.authority.SimpleGrantedAuthority"%>
<%@page import="org.springframework.security.core.GrantedAuthority"%>
<%@page import="java.util.Collection"%>
<%@page import="org.springframework.web.servlet.ModelAndView"%>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/tabs.css" />">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css">
<div class="content-wrapper">
<div class="modal" id="showSubLocationUreaDetails" role="dialog">
	<div class="modal-dialog modal-md" style="width:850px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Main Location : &ensp; &nbsp; <span id="mainLocationName"></span></h4>
					<h4 class="modal-title">Part Name : &emsp; &emsp; <span id="ureaManufacturerName"></span></h4>
			</div>
			<div class="modal-body">
				<div class="box">
					<div class="box-body">
						<div class="table-responsive">
							<table class="table">
							<thead>
								<tr>
									<th class="fit ar">Sr No</th>
									<th class="fit ar">Sub Location</th>
									<th class="fit ar">Quantity</th>
								</tr>
							</thead>
							<tbody id="subLocationModelTable">
							
							</tbody>
		
							</table>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default"
					data-dismiss="modal">
					<span id="Close"><spring:message
							code="label.master.Close" /></span>
				</button>
			</div>
		</div>
	</div>
</div>
	<section class="panel panel-success">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
						<a href="open" >Home</a>/ <a href="FuelInventory.in">New Fuel Inventory</a>
				</div>
				<div class="pull-right">
				
						<% 
						Collection<GrantedAuthority>  permission = (Collection<GrantedAuthority>)request.getAttribute("permissions");
						if(permission.contains(new SimpleGrantedAuthority("ADD_FUEL_INVENTORY"))) {
						%>
						
						<a class="btn btn-success btn-sm" onclick="showHideAddCloth();">
   							 <span class="fa fa-plus">
								Add Fuel Inventory</span></a>
  						</a>
  						
  						<% } %>
  						<%
  						if(permission.contains(new SimpleGrantedAuthority("FUEL_TRANSFER"))){
  						%>
				<a class="btn btn-info btn-sm" href="<c:url value="createFuelTransfer.in"/>"><i class="fa fa-exchange" aria-hidden="true"></i>
				 Fuel Transfer </a>
					
					<% } %>
				</div>	
				<!-- <div class="col-md-2">
							<div class="input-group">
								<span class="input-group-addon"> <span aria-hidden="true">FI-</span></span>
								<input class="form-control" id="searchClothInv" onkeyup="searchUreaInvOnEnter(event);" name="tripStutes"
									type="number" min="1" required="required"
									placeholder="UI-ID eg:7878" maxlength="20"> <span
									class="input-group-btn">
									<button type="submit" onclick="ureaInvoiceSearch();" name="search" id="search-btn"
										class="btn btn-success btn-sm">
										<i class="fa fa-search"></i>
									</button>
								</span>
							</div>
				</div> -->
			</div>
		</div>
	</section>
	<section class="panel panel-success">

		<% if(permission.contains(new SimpleGrantedAuthority("FUEL_INVENTORY"))) {%>
			<%-- <div class="row" id="searchData">
			<div id="countDiv" style="display : none;" class="col-md-4 col-sm-4 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i class="fa fa-cubes"></i></span>
						<div class="info-box-content">
						 <input type="hidden" value="${location}" id="statues">
							<span class="info-box-text" id="countId">Total Fuel Invoice</span> 
							<span id="totalClothInvoice" class="info-box-number"></span>
						</div>
					</div>
				</div>
				 <div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Fuel Invoice</span>
								<div class="input-group">
									<input id="searchInv" onkeyup="invoiceSearchOnEnter(event);" class="form-control" name="Search" type="text"
										required="required" placeholder="Only Invoice No"> <span
										class="input-group-btn">
										<button type="submit" name="search" onclick="invoiceSearch(1);" id="invoiceSearch"
											class="btn btn-success btn-sm">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
						</div>
					</div>
				</div>
				
			</div> --%>
		<% } %>
	</section>
		<div id="listTab" class="main-tabs" style="display: none;">
						<ul class="nav nav-pills" id="list">
							<li role="presentation" id="All"><a
								href="#" onclick="getPageWiseFuelInvoiceDetails(1);">Fuel Invoice</a></li>
							
							<c:if test="${!empty petrolPumpList}">
								<c:forEach items="${petrolPumpList}" var="petrolPumpList">
									<li class="tab-link" id="${petrolPumpList.vendorName}"><a
										class="btn btn-link"
										href="#" onclick="getFuelStockDetails(${petrolPumpList.vendorId}, '${petrolPumpList.vendorName}');" >
											${petrolPumpList.vendorName}</a></li>
								</c:forEach>
							</c:if>
						</ul>
						<input type="hidden" id="addFuelInventoryDocument" value="${configuration.addFuelInventoryDocument}">
						<input type="hidden" id="showBalanceStockInFuelInvoice" value="${configuration.showBalanceStockInFuelInvoice}">
						<input type="hidden" id="editFuelInvoice" value="${configuration.editFuelInvoice}">
						
	  </div>
	<div class="content" >
		<div class="main-body">
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
		<% 
			if(!permission.contains(new SimpleGrantedAuthority("ADD_FUEL_INVENTORY"))) {
		%>
			Unauthorized Access !!
		<% } %>
		<% 
			if(permission.contains(new SimpleGrantedAuthority("ADD_FUEL_INVENTORY"))) {
		%>
		<br/>
			<div class="row hide" id="addClothDiv">
				<div class="col-md-offset-1 col-md-9">
						<div class="form-horizontal ">
							<form action="#" id="fileUploadForm">
							<fieldset>
								<legend>Fuel Inventory Details </legend>
								<div class="box">
								<input type="hidden" id="accessToken" value="${accessToken}">
								<input type="hidden" id="companyId" value="${companyId}">
								<input type="hidden" id="userId" value="${userId}">
								<input type="hidden" id="editInvoicePermission" value="${editInvoicePermission}">
								<input type="hidden" id="showTransferedStatus" value="${configuration.showTransferedStatus}">
									<div class="box-body">
										<div class="panel panel-success">
											<div class="panel-body">
											
												<div class="row1" id="grpmanufacturer" class="form-group">
													<label class="L-size control-label" for="manufacurer">Petrol Pump :<abbr title="required">*</abbr>
													</label>
													<div class="col-md-3">
														<input type="hidden" id="petrolPumpId"
															name="petrolPumpId" style="width: 100%;"
															placeholder="Please Enter 2 or more Petrol Pump Name" />
													</div>
													
													<input type="hidden" id="discountTaxTypId" value="${configuration.discountTaxTypeId}">
													<label class="L-size control-label" for="payMethod">Discount/GST
														Type :<abbr title="required">*</abbr>
													</label>
													<div class="col-md-3">
														<div class="">
															<div class="btn-group"  >
																<label id="percentId" class="btn btn-success btn-on btn-sm " onclick="selectDiscountTaxType(1, 0);">
																	Percentage
																</label> 
																<label id="amountId" class="btn btn-default btn-off btn-sm" onclick="selectDiscountTaxType(2, 0);"> 
																	Amount
																</label>
															</div>
														</div>
													</div>
													
												</div>
												
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="col-md-9">
														<div class="col-md-2">
															<label class="control-label">Liters</label>

														</div>
														<div class="col-md-2">
															<label class="control-label">Unit Cost</label>

														</div>
														<div class="col-md-2">
															<label class="control-label">Discount</label>
														</div>
														<div class="col-md-2">
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
														<div class="col-md-2">
															<input type="text" class="form-control" name="quantity_many"
																min="0.0" id="quantity" maxlength="8"
																placeholder="ex: 23.78" required="required"
																data-toggle="tip"
																data-original-title="enter Tyre Quantity"
																onkeypress="return isNumberKeyWithDecimal(event,this.id)"
																onkeyup="javascript:sumthereTypeWise('quantity', 'unitprice', 'discount', 'tax', 'tatalcost', 0);"
																ondrop="return false;"> <span id="quantityIcon"
																class=""></span>
															<div id="quantityErrorMsg" class="text-danger"></div>
														</div>
														<div class="col-md-2">
															<input type="text" class="form-control"
																name="unitprice_many" id="unitprice" maxlength="8"
																min="0.0" placeholder="Unit Cost" required="required"
																data-toggle="tip" data-original-title="enter Unit Price"
																onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																onkeyup="javascript:sumthereTypeWise('quantity', 'unitprice', 'discount', 'tax', 'tatalcost', 0);"
																ondrop="return false;"> <span id="unitpriceIcon"
																class=""></span>
															<div id="unitpriceErrorMsg" class="text-danger"></div>
														</div>

														<div class="col-md-2">
															<input type="text" class="form-control" name="discount_many"
																min="0.0" id="discount" max="99" maxlength="5"
																placeholder="Discount" required="required"
																data-toggle="tip" data-original-title="enter Discount"
																onkeypress="return isNumberKeyWithDecimal(event,this.id)"
																onkeyup="javascript:sumthereTypeWise('quantity', 'unitprice', 'discount', 'tax', 'tatalcost', 0);"
																ondrop="return false;"><span id="discountIcon"
																class=""></span>
															<div id="discountErrorMsg" class="text-danger"></div>

														</div>
														<div class="col-md-2">
															<input type="text" class="form-control" name="tax_many"
																id="tax" maxlength="5" min="0.0" placeholder="GST"
																required="required" data-toggle="tip"
																data-original-title="enter GST"
																onkeypress="return isNumberKeyWithDecimal(event,this.id)"
																onkeyup="javascript:sumthereTypeWise('quantity', 'unitprice', 'discount', 'tax', 'tatalcost', 0);"
																ondrop="return false;"><span id="taxIcon"
																class=""></span>
															<div id="taxErrorMsg" class="text-danger"></div>
														</div>
														<div class="col-md-2">
															<input type="text" class="form-control" maxlength="8"
																value="0.0" min="0.0" name="tatalcost" id="tatalcost" readonly="readonly"
																data-toggle="tip" data-original-title="Total Cost"
																onkeypress="return isNumberKey(event,this);"
																ondrop="return false;">
														</div>
													</div>
													<br> <label class="error" id="errorINEACH"
														style="display: none"></label>

												</div>
												
											</div>
										</div>
									</div>
								</div>
							</fieldset>
							<fieldset>
								<legend>Fuel Inventory Details</legend>
								<div class="box">
									<div class="box-body">
										
										<div class="row1">
											<label class="L-size control-label">Vendor :<abbr
												title="required">*</abbr></label>
											<div class="col-md-3" id="vendorSelect">
												<input type="hidden" id="selectVendor" name="VENDOR_ID"
													style="width: 100%;" required="required" value="0"
													placeholder="Please Select Vendor Name" /> <label
													class="error" id="errorVendorSelect"> </label>
											</div>
											
											<label class="L-size control-label">Modes of Payment
												<abbr title="required">*</abbr>
											</label>
											<div class="col-md-3" id="paymentDiv">
												<select class="form-control" name="PAYMENT_TYPE_ID"
													id="renPT_option" required="required">
													<option value="1">Cash</option>
												</select>
											</div>
										</div>
										
										
										<div class="row1">
											<label class="L-size control-label" id="target1"
												for="renPT_option">Enter </label>
											<div class="col-md-3">
												<input type="text" id="paymentNumber" class="form-control" name="paymentNumber"
													onkeypress="return IsAlphaNumericPaynumber(event);"
													ondrop="return false;" maxlength="25"> <label
													class="error" id="errorPaynumber" style="display: none">
												</label>
											</div>
											
											<label class="L-size control-label">PO Number :</label>
											<div class="col-md-3">
												<input type="text" id="poNumber" class="form-control" name="poNumber"
													maxlength="25" onkeypress="return IsPonumber(event);"
													ondrop="return false;"> <label class="error"
													id="errorPonumber" style="display: none"> </label>
											</div>
										</div>
										
										<div class="row1">
											<label class="L-size control-label">Invoice Number:</label>
											<div class="col-md-3">
												<input type="text" id="invoiceNumber" class="form-control" name="invoiceNumber"
													maxlength="25" onkeypress="return IsInvoicenumber(event);"
													ondrop="return false;"> <label class="error"
													id="errorInvoicenumber" style="display: none"> </label>
											</div>
											
											<div  id="grpinvoiceDate" class="form-group">
												<label class="L-size control-label" for="invoiceDate">Invoice
													Date :<abbr title="required">*</abbr>
												</label>
												<div class="col-md-3">
													<div class="input-group input-append date" id="opendDate">
														<input type="text"  class="form-control" name="INVOICE_DATE"
															id="invoiceDate" required="required"
															data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
															class="input-group-addon add-on"> <span
															class="fa fa-calendar"></span>
														</span>
													</div>
												</div>
											</div>

										</div>
										
										<div class="row1">
											<label class="L-size control-label">Invoice Amount:</label>
											<div class="col-md-3">
												<input type="text" class="form-control" name="INVOICE_AMOUNT"
													value="0" id="invoiceAmount" maxlength="10"
													onkeypress="return isNumberKeyWithDecimal(event,this.id);"
													ondrop="return false;"> <label class="error"
													id="errorInvoicenumber" style="display: none"> </label>
											</div>
											
											<input type="hidden" id="tallyIntegrationRequired" value="${configuration.tallyIntegrationRequired}">
											<c:if test="${configuration.tallyIntegrationRequired}">
												<div id="grpmanufacturer" class="form-group">
												<label class="L-size control-label" for="manufacurer">Tally Company Name :<abbr title="required">*</abbr></label>
													<div class="col-md-3">
														<input type="hidden" id="tallyCompanyId" name="tallyCompanyId" style="width: 100%;" value="0"
														  placeholder="Please Enter Tally Company Name" />
													</div>
												</div>
											</c:if>

										</div>
										
										
										
										<c:if test="${configuration.addFuelInventoryDocument}">
										<fieldset id="grpfuelDocument">
											<legend>Document</legend>
											<div class="box">
												<div class="box-body">
													<div class="row1">
														<label class="L-size control-label" for="fuel_partial">Fuel
															Document : </label>
														<div class="I-size">
															<input type="file" name="input-file-preview" id="fuelDocument" /> 
																<span id="renewalFileIcon" class=""></span>
															<div id="renewalFileErrorMsg" class="text-danger"></div>
															<span class="help-block">Add an optional document</span>
														</div>
													</div>
												</div>
											</div>
										</fieldset>
									</c:if>
									<c:if test="${!configuration.addFuelInventoryDocument}">
										<input type="file" class="hide" name="input-file-preview" id="batteryDocument1" /> 
									</c:if>

										<div class="row1">
											<label class="L-size control-label">Description :</label>
											<div class="I-size">
												<textarea class="form-control" name="description" rows="3"
													maxlength="150" id="description" onkeypress="return IsVendorRemark(event);"
													ondrop="return false;"> 
				                                </textarea>
												<label class="error" id="errorVendorRemark"
													style="display: none"> </label>
											</div>
										</div>
									</div>
								</div>
							</fieldset>
							<fieldset class="form-actions">
								<div class="row1" id="saveFuelInv">
									<div class="col-md-10 col-md-offset-2">
										<button type="submit" id="submit" class="btn btn-success">Create
											Fuel Inventory</button>
										<a class="btn btn-link"
											href="FuelInventory.in">Cancel</a>
									</div>
								</div>
							</fieldset>
							<%@ include file="/WEB-INF/view/payment/PaymentDetails.jsp"%>
							</form>
						</div>
				</div>
			</div>
			
			<div class="modal fade" id="fuelStockModel" role="dialog">
				<div class="modal-dialog" style="width:1250px;">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">Fuel Stock Details</h4>
							<h5 class="modal-title" id="petrolPumpNameInModal"> </h5>
						</div>
						<div class="modal-body">
							<table id="dataFuelTable" style="width: 100%; display: none;" class="table-responsive table">
								
							</table>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">
								<span id="Close">Close</span>
							</button>
						</div>
					</div>
				</div>
			</div>
			
		<% } %>
	</div>
</div>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/fuelinventory/addFuelInventory.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/fuelinventory/ViewFuelInvoiceList.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/fuelinventory/fuelInventoryUtility.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/fuelinventory/validateFuelInventory.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js"></script>
