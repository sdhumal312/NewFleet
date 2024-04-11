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
						<a href="open" >Home</a>/ <a href="UreaInventory.in">New Urea Inventory</a>
				</div>
				<div class="pull-right">
				<input type="hidden" id="companyId" value="${companyId}">
						<% 
						Collection<GrantedAuthority>  permission = (Collection<GrantedAuthority>)request.getAttribute("permissions");
						if(permission.contains(new SimpleGrantedAuthority("ADD_UREA_INVENTORY"))) {
						%>
						
						<sec:authorize access="hasAuthority('UREA_REQUISITION_TRANSFER')">
							<a class="btn btn-info btn-sm" href="<c:url value="/ureaRequisitionAndTransferDetails.in"/>">Urea Requisition / Transfer </a>
						</sec:authorize>
  						
						
						<a class="btn btn-success btn-sm" onclick="showHideAddCloth();">
   							 <span class="fa fa-plus">
								Add Urea Inventory</span></a>
						<a href="<c:url value="/addVendor.in"/>"
							class="btn btn-success btn-sm"><span class="fa fa-plus">
								Create Vendor</span></a>	  						
  						<% } %>
				</div>	<div class="col-md-2">
							<div class="input-group">
								<span class="input-group-addon"> <span aria-hidden="true">UI-</span></span>
								<input class="form-text" id="searchClothInv" onkeyup="searchUreaInvOnEnter(event);" name="tripStutes"
									type="number" min="1" required="required"
									placeholder="UI-ID eg:7878" maxlength="20"> <span
									class="input-group-btn">
									<button type="submit" onclick="ureaInvoiceSearch();" name="search" id="search-btn"
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
			<div class="modal fade" id="addImport" role="dialog">
				<div class="modal-dialog">
					<div class="modal-content">
						<form method="post" action="<c:url value="/importInventoryBattery.in"/>"
							enctype="multipart/form-data">
							<div class="panel panel-default">
								<div class="panel-heading clearfix">
									<h3 class="panel-title">Import File</h3>
								</div>
								<div class="panel-body">
									<div class="form-horizontal">
										<br>
										<div class="row1">
											<label class="L-size control-label">Warehouse
												location From : </label>
											<div class="I-size">
												<input required="required" type="hidden" name="location"
													id="warehouselocation2" value="0" style="width: 100%;" placeholder="All" />
											</div>
										</div>
										<br/>
										<div class="row1">
											<div class="L-size">
												<label> Import Only xlsx File: </label>
											</div>
											<div class="I-size">
												<input type="file" accept=".xlsx" name="import"
													required="required" />
											</div>
										</div>
									</div>
									<div class="row1 progress-container">
										<div class="progress progress-striped active">
											<div class="progress-bar progress-bar-success"
												style="width: 0%">Upload Your Inventory Battery Entries Please
												wait..</div>
										</div>
									</div>
									<div class="modal-footer">
										<input class="btn btn-success"
											onclick="return validateSelection();" name="commit"
											type="submit" value="Import Inventory Battery files"
											class="btn btn-primary" id="myButton"
											data-loading-text="Loading..." class="btn btn-primary"
											autocomplete="off" id="js-upload-submit" value="Add Document"
											data-toggle="modal">
										<button type="button" class="btn btn-link"
											data-dismiss="modal">Close</button>
									</div>
									<script>
										$('#myButton')
												.on(
														'click',
														function() {
															//alert("hi da")
															$(".progress-bar")
																	.animate(
																			{
																				width : "100%"
																			},
																			2500);
															var $btn = $(this)
																	.button(
																			'loading')
															// business logic...

															$btn
																	.button('reset')
														})
									</script>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		<% if(permission.contains(new SimpleGrantedAuthority("VIEW_UREA_INVENTORY"))) {%>
			<div class="row" id="searchData">
			<div id="countDiv" style="display : none;" class="col-md-4 col-sm-4 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i class="fa fa-cubes"></i></span>
						<div class="info-box-content">
						 <input type="hidden" value="${location}" id="statues">
							<span class="info-box-text" id="countId">Total Urea Invoice</span> 
							<span id="totalClothInvoice" class="info-box-number"></span>
						</div>
					</div>
				</div>
				 <div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Urea Invoice</span>
								<div class="input-group">
									<input id="searchInv" onkeyup="invoiceSearchOnEnter(event);" class="form-text" name="Search" type="text"
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
				
			</div>
		<% } %>
	</section>
		<div id="listTab" class="main-tabs" style="display: none;">
						<ul class="nav nav-pills" id="list">
							<li role="presentation" id="All"><a
								href="#" onclick="getPageWiseUreaInvoiceDetails(1);">Urea Invoice</a></li>
							<li class="tab-link" role="presentation" id="AllStock"><a
								href="#" onclick="getAllUreaStockDetails(1);">ALL</a></li>	
							<c:if test="${!empty PartLocations}">
								<c:forEach items="${PartLocations}" var="PartLocations">
									<li class="tab-link" id="${PartLocations.partlocation_name}"><a
										class="btn btn-link"
										href="#" onclick="locationUreaDetails(${PartLocations.partlocation_id}, 1);" >
											${PartLocations.partlocation_name}</a></li>
								</c:forEach>
							</c:if>
						</ul>
						<input type="hidden" id="downloadUreaInventoryDocument" value="${configuration.downloadUreaInventoryDocument}">
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
			if(!permission.contains(new SimpleGrantedAuthority("ADD_UREA_INVENTORY"))) {
		%>
			Unauthorized Access !!
		<% } %>
		<% 
			if(permission.contains(new SimpleGrantedAuthority("ADD_UREA_INVENTORY"))) {
		%>
		<br/>
			<div class="row hide" id="addClothDiv">
				<div class="col-md-offset-1 col-md-9">
					<form method="POST" enctype="multipart/form-data" id="fileUploadForm">	
						<div class="form-horizontal ">
							<fieldset>
								<input type="hidden" id="showSubLocation" value="${showSubLocation}">
								<input type="hidden" id="mainLocationIds" value="${mainLocationIds}">
								<input type="hidden" id="roundupConfig" value="${configuration.roundupAmount}">
								<input type="hidden" id="manufacturerNonMandatory" value="${configuration.manufacturerMandatory}">
								<legend>Urea Inventory Details </legend>
								<div class="box">
								<input type="hidden" id="accessToken" value="${accessToken}">
									<div class="box-body">
										<div class="panel panel-success">
											<div class="panel-body">
											
												<div class="row1" id="grpmanufacturer" class="form-group">
													<label class="L-size control-label" for="manufacurer">Urea Manufacturer :<abbr title="required">*</abbr>
													</label>
													<div class="col-md-3">
														<input type="hidden" id="ureaManufacturer"
															name="ureaManufacturer" style="width: 100%;"
															placeholder="Please Enter 2 or more Manufacturer Name" />
													</div>
													
													<input type="hidden" id="discountTaxTypId" value="${configuration.discountTaxTypeId}">
													<input type="hidden" id="finalDiscountTaxTypId" name="finalDiscountTaxTypId">
													<label class="L-size control-label" for="payMethod">Discount/GST
														Type :<abbr title="required">*</abbr>
													</label>
													<div class="col-md-3">
														<div class="">
															<div class="btn-group"  >
																<label id="percentId" class="btn btn-default  btn-sm " onclick="selectDiscountTaxType(1, 0);">
																	Percentage
																</label> 
																<label id="amountId" class="btn btn-default  btn-sm" onclick="selectDiscountTaxType(2, 0);"> 
																	Amount
																</label>
															</div>
														</div>
													</div>
													
												</div>
												
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="col-md-9">
														<div class="col-md-1">
															<label class="control-label">Liters</label>

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
															<input type="text" class="form-text" name="quantity_many"
																min="0.0" id="quantity" maxlength="4"
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
															<input type="text" class="form-text"
																name="unitprice_many" id="unitprice" maxlength="10"
																min="0.0" placeholder="Unit Cost" required="required"
																data-toggle="tip" data-original-title="enter Unit Price"
																onkeypress="return isNumberKeyWithDecimal(event,this.id)"
																	onkeyup="javascript:sumthereTypeWise('quantity', 'unitprice', 'discount', 'tax', 'tatalcost', 0);"
																ondrop="return false;"> <span id="unitpriceIcon"
																class=""></span>
															<div id="unitpriceErrorMsg" class="text-danger"></div>
														</div>

														<div class="col-md-1">
															<input type="text" class="form-text" name="discount_many"
																min="0.0" id="discount" maxlength="5"
																placeholder="Discount" required="required"
																data-toggle="tip" data-original-title="enter Discount"
																onkeypress="return isNumberKeyWithDecimal(event,this.id)"
																	onkeyup="javascript:sumthereTypeWise('quantity', 'unitprice', 'discount', 'tax', 'tatalcost', 0);"
																ondrop="return false;"><span id="discountIcon"
																class=""></span>
															<div id="discountErrorMsg" class="text-danger"></div>

														</div>
														<div class="col-md-1">
															<input type="text" class="form-text" name="tax_many"
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
								<legend>Urea Inventory Details</legend>
								<div class="box">
									<div class="box-body">
										
										<div class="row1" id="grppartLocation" class="form-group">
											<div  id="grppartLocation">
										
												<label class="L-size control-label" for="warehouselocation">
												<c:if test="${!configuration.storeLocationLabel}">
												Warehouse location : 
												</c:if> <c:if test="${configuration.storeLocationLabel}">
												Store Location :
												</c:if> 
												<abbr title="required">*</abbr>
												</label>
												<div class="col-md-3">
													<input type="hidden" name="WAREHOUSE_LOCATION_ID"
														id="warehouselocation" style="width: 100%;"
														required="required" onchange="showSubLocationDropDown();"
														placeholder="Please Enter 2 or more location Name" /> <span
														id="partLocationIcon" class=""></span>
													<div id="partLocationErrorMsg" class="text-danger"></div>
												</div>
											</div>
											<div id="subLocation" class="form-group" style="display:none">
												<label class="L-size control-label" for="location">Sub location :<abbr title="required">*</abbr>
												</label>
												<div class="col-md-3">
													<input type="hidden" name="subLocationId"
														id="subLocationId" style="width: 100%;"
														required="required"
														placeholder="Please Enter 2 or more location Name" /> <span
														id="partLocationIcon" class=""></span>
													<div id="partLocationErrorMsg" class="text-danger"></div>
												</div>
											</div>
										</div>
										
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
												<select class="form-text" name="PAYMENT_TYPE_ID"
													id="renPT_option" required="required">
													<option value="1">Cash</option>
												</select>
											</div>
										</div>
										
										
										<div class="row1">
											<label class="L-size control-label" id="target1"
												for="renPT_option">Enter </label>
											<div class="col-md-3">
												<input type="text" id="PAYMENT_NUMBER" class="form-text" name="PAYMENT_NUMBER"
													onkeypress="return IsAlphaNumericPaynumber(event);"
													ondrop="return false;" maxlength="25"> <label
													class="error" id="errorPaynumber" style="display: none">
												</label>
											</div>
											<c:if test="${configuration.showPOnumberField}">
											<label class="L-size control-label">PO Number :</label>
											<div class="col-md-3">
												<input type="text" id="PO_NUMBER" class="form-text" name="PO_NUMBER"
													maxlength="25" 
													onkeypress="return isNumberKey(event,this);"
													ondrop="return false;"> <label class="error"
													id="errorPonumber" style="display: none"> </label>
											</div>
											</c:if>
										</div>
										
										<div class="row1">
											<label class="L-size control-label">Invoice Number:</label>
											<div class="col-md-3">
												<input type="text" id="INVOICE_NUMBER" class="form-text" name="INVOICE_NUMBER"
													maxlength="25" 
													onkeypress="return isNumberKey(event,this);"
													ondrop="return false;"> <label class="error"
													id="errorInvoicenumber" style="display: none"> </label>
											</div>
											
											<div  id="grpinvoiceDate" class="form-group">
												<label class="L-size control-label" for="invoiceDate">Invoice
													Date :<abbr title="required">*</abbr>
												</label>
												<div class="col-md-3">
													<div class="input-group input-append date" id="opendDate">
														<input type="text"  class="form-text" name="INVOICE_DATE"
															id="invoiceDate" required="required" readonly="readonly"
															data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
															class="input-group-addon add-on"> <span
															class="fa fa-calendar"></span>
														</span>
													</div>
													<span id="invoiceDateIcon" class=""></span>
													<div id="invoiceDateErrorMsg" class="text-danger"></div>
												</div>
											</div>

										</div>
										
										<div class="row1">
										
										<c:if test="${!configuration.roundupAmount}">
											<label class="L-size control-label">Invoice Amount:</label>
											<div class="col-md-3">
												<input type="text" class="form-text" name="INVOICE_AMOUNT"
													value="0" id="invoiceAmount" maxlength="10"
													
														onkeypress="return isNumberKeyWithDecimal(event,this.id)"
													ondrop="return false;"> <label class="error"
													id="errorInvoicenumber" style="display: none"> </label>
											</div>
											</c:if>
											
											<c:if test="${configuration.roundupAmount}">
											
											<label class="L-size control-label">Invoice Amount:</label>
											<div class="col-md-3">
												<input type="text" class="form-text"  readonly="readonly"
													value="0" id="readOnlyinvoiceAmount" maxlength="10" 
													
														onkeypress="return isNumberKeyWithDecimal(event,this.id)"
													ondrop="return false;"> <label class="error"
													id="errorInvoicenumber" style="display: none"> </label>
											</div>
											
											
											<label class="L-size control-label">Roundup Amount</label>
											<div class="col-md-3">
												<input type="text" class="form-text" name="INVOICE_AMOUNT" id="roundupTotal"
													  maxlength="10" min="1" required="required"
													
														onkeypress="return isNumberKeyWithDecimal(event,this.id)"
													ondrop="return false;"> <label class="error"
													id="errorInvoicenumber" style="display: none"> </label>
											</div>
											
											
											
											</c:if>
											
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
										
										
										
										<c:if test="${configuration.addUreaInventoryDocument}">
										<fieldset id="grpfuelDocument">
											<legend>Document</legend>
											<div class="box">
												<div class="box-body">
													<div class="row1">
														<label class="L-size control-label" for="fuel_partial">Urea
															Document : </label>
														<div class="I-size">
															<input type="file" name="input-file-preview" id="ureaDocument" /> 
																<span id="renewalFileIcon" class=""></span>
															<div id="renewalFileErrorMsg" class="text-danger"></div>
															<span class="help-block">Add an optional document</span>
														</div>
													</div>
												</div>
											</div>
										</fieldset>
									</c:if>
									<c:if test="${!configuration.addUreaInventoryDocument}">
										<input type="file" class="hide" name="input-file-preview" id="batteryDocument1" /> 
									</c:if>

										<div class="row1">
											<label class="L-size control-label">Description :</label>
											<div class="I-size">
												<textarea class="form-text" name="DESCRIPTION" rows="3"
													maxlength="150" id="DESCRIPTION" onkeypress="return IsVendorRemark(event);"
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
								<div class="row1" id="saveUreaInv">
									<div class="col-md-10 col-md-offset-2">
										<button type="submit" id="submit" class="btn btn-success"  onclick="return validateUreaInventory();">Create
											Urea Inventory</button>
										<a class="btn btn-link"
											href="UreaInventory.in">Cancel</a>
									</div>
								</div>
							</fieldset>
						</div>
						<%@ include file="/WEB-INF/view/payment/PaymentDetails.jsp"%>
					</form>
				</div>
			</div>
			
			<div class="modal fade" id="inService" role="dialog">
			<div class="modal-dialog" style="width:1250px;">
				<!-- Modal content-->
				<div class="modal-content">
					
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="JobType">Vehicles Currently In 
								Service</h4>
						</div>
						<div class="modal-body">
							<div class="box">
								<div class="box-body">
							 
									<div class="table-responsive">
										<input type="hidden" id="startPage" value="${SelectPage}">
										<table id="VendorPaymentTable1" class="table table-hover table-bordered">
										</table>
									</div>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<%-- <button type="submit" class="btn btn-primary" onclick="saveMaxAllowed();">
								<span><spring:message code="label.master.Save" /></span>
							</button> --%>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close"><spring:message
										code="label.master.Close" /></span>
							</button>
						</div>
				</div>
			</div>
		</div>
		
		
		<div class="modal fade" id="addDamageDetails" role="dialog">
			<div class="modal-dialog modal-md" style="width:750px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Add Upholstery Damage Details</h4>
					</div>
					<div class="modal-body">
						<input type="hidden" id="damageNewQuantity">
						<input type="hidden" id="damageUsedQuantity">
						<div class="row">
							<fieldset>
								<legend>Select Upholstery Types </legend>
								<div class="form-horizontal ">
									<div class="box">
										<div class="box-body">
											<div class="form-horizontal ">
											
												<div class="row1" class="form-group">
													<label class="L-size control-label" for="from">Location
														 </label>
													<div class="I-size">
															<div class="row">
																<input type="hidden" name="damagelocationId" id="damagelocationId"
																	required="required" style="width: 100%;"
																	required="required"
																	placeholder="Please Enter 2 or more Location Name" /> 
															</div>
													</div>
												</div>
											
												<div class="row1" id="grpwoJobType" class="form-group">
													<label class="L-size control-label" for="from">Upholstery
														Name </label>
													<div class="I-size">
															<div class="row">
																<input type="hidden" name="damageClothTypes" id="damageClothTypes"
																	required="required" style="width: 100%;"
																	required="required"
																	placeholder="Please Enter 2 or more Cloth Name" /> 
															</div>
													</div>
												</div>
												
											<div class="row1" class="form-group">
												<label class="L-size control-label">Types of Upholstery
													<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<select class="form-text" name="damageTypeOfCloth"
														id="damageTypeOfCloth" required="required">
														<option value="1">NEW/FRESH</option>
														<option value="2">OLD/USED</option>
													</select>
												</div>
											</div>
												<!-- <div class="row1" id="grpwoJobType" class="form-group">
													<label class="L-size control-label" for="from">Stock 
														Qty :</label>
													<div class="I-size">
															<div class="input-group">
																<input type="text" class="form-text" 
																	name="stockQuantity"  id="stockQuantity"
																	readonly="readonly" min="0.00">
															</div>
													</div>
												</div> -->
												
												<div class="row1">
													<label class="L-size control-label"></label>
													<div class="col-md-9">
														<div class="col-md-1">
															<label class="control-label">Stock Qty</label>
														</div>
														<div class="col-md-2">
															<label class="control-label">Previous Dmg Qty</label>
														</div>
														<div class="col-md-2">
															<label class="control-label">Previous Lost Qty</label>
														</div>
													</div>
												</div>	
												
												<div class="row1" id="grpquantity" class="form-group">
													<label class="L-size control-label" for="quantity">
													</label>
													<div class="col-md-9">
														<div class="col-md-1">
															<input type="text" class="form-text" 
																	name="damageStockQuantity"  id="damageStockQuantity"
																	readonly="readonly" min="0.00">
															<div id="quantityErrorMsg" class="text-danger"></div>
														</div>
														<div class="col-md-2">
															<input type="text" class="form-text" 
																	name="previousDmgQuantity"  id="previousDmgQuantity"
																	readonly="readonly" min="0.00">
															<div id="unitpriceErrorMsg" class="text-danger"></div>
														</div>
														<div class="col-md-2">
															<input type="text" class="form-text" 
																	name="previousLostQuantity"  id="previousLostQuantity"
																	readonly="readonly" min="0.00">
															<div id="unitpriceErrorMsg" class="text-danger"></div>
														</div>
													</div>
												</div>	
												
												
												<div class="row1" id="grpwoJobType" class="form-group">
													<label class="L-size control-label" for="from">Damage
														Quantity : <abbr title="required">*</abbr>
													</label>
													<div class="I-size">
															<div class="input-group">
																<input type="number" class="form-text" placeholder="0.0"
																	name="damageQuantity" required="required" id="damageQuantity"
																	maxlength="5" data-toggle="tip" onkeyup="validateQty();"
																	>
															</div>
													</div>
												</div>
												
												<div class="row1" id="grpwoJobType" class="form-group">
													<label class="L-size control-label" for="from">
														Description : 
													</label>
													<div class="I-size">
															<div class="input-group">
																<input type="text" class="form-text" placeholder="Enter Description"
																	name="damageRemark"  id="damageRemark"
																	>
															</div>
													</div>
												</div>
												
											
											</div>
										</div>
									</div>
								</div>
							</fieldset>
					</div>				
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary">
							<span id="Save" onclick="saveClothDamageDetails();">Add Upholstery To Damage</span>
						</button>
						<button type="button" class="btn btn-default"
							data-dismiss="modal">
							<span id="Close">Close</span>
						</button>
					</div>
				</div>
			</div>
		</div>
		
		
		<div class="modal fade" id="addLostDetails" role="dialog">
			<div class="modal-dialog modal-md" style="width:750px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Add Upholstery Lost Details</h4>
					</div>
					<div class="modal-body">
						<input type="hidden" id="lostNewQuantity">
						<input type="hidden" id="lostUsedQuantity">
						<div class="row">
							<fieldset>
								<legend>Select Upholstery Types </legend>
								<div class="form-horizontal ">
									<div class="box">
										<div class="box-body">
											<div class="form-horizontal ">
											
												<div class="row1" class="form-group">
													<label class="L-size control-label" for="from">Location
														 </label>
													<div class="I-size">
															<div class="row">
																<input type="hidden" name="lostlocationId" id="lostlocationId"
																	required="required" style="width: 100%;"
																	required="required"
																	placeholder="Please Enter 2 or more Location Name" /> 
															</div>
													</div>
												</div>
											
												<div class="row1" id="grpwoJobType" class="form-group">
													<label class="L-size control-label" for="from">Upholstery
														Name </label>
													<div class="I-size">
															<div class="row">
																<input type="hidden" name="lostClothTypes" id="lostClothTypes"
																	required="required" style="width: 100%;"
																	required="required"
																	placeholder="Please Enter 2 or more Cloth Name" /> 
															</div>
													</div>
												</div>
												
											<div class="row1" class="form-group">
												<label class="L-size control-label">Types of Upholstery
													<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<select class="form-text" name="lostTypeOfCloth"
														id="lostTypeOfCloth" required="required">
														<option value="1">NEW/FRESH</option>
														<option value="2">OLD/USED</option>
													</select>
												</div>
											</div>
												<!-- <div class="row1" id="grpwoJobType" class="form-group">
													<label class="L-size control-label" for="from">Stock 
														Qty :</label>
													<div class="I-size">
															<div class="input-group">
																<input type="text" class="form-text" 
																	name="stockQuantity"  id="stockQuantity"
																	readonly="readonly" min="0.00">
															</div>
													</div>
												</div> -->
												
												<div class="row1">
													<label class="L-size control-label"></label>
													<div class="col-md-9">
														<div class="col-md-1">
															<label class="control-label">Stock Qty</label>
														</div>
														<div class="col-md-2">
															<label class="control-label">Previous Dmg Qty</label>
														</div>
														<div class="col-md-2">
															<label class="control-label">Previous Lost Qty</label>
														</div>
													</div>
												</div>	
												
												<div class="row1" id="grpquantity" class="form-group">
													<label class="L-size control-label" for="quantity">
													</label>
													<div class="col-md-9">
														<div class="col-md-1">
															<input type="text" class="form-text" 
																	name="availableStockQuantity"  id="availableStockQuantity"
																	readonly="readonly" min="0.00">
															<div id="quantityErrorMsg" class="text-danger"></div>
														</div>
														<div class="col-md-2">
															<input type="text" class="form-text" 
																	name="prevDmgQuantity"  id="prevDmgQuantity"
																	readonly="readonly" min="0.00">
															<div id="unitpriceErrorMsg" class="text-danger"></div>
														</div>
														<div class="col-md-2">
															<input type="text" class="form-text" 
																	name="prevLostQuantity"  id="prevLostQuantity"
																	readonly="readonly" min="0.00">
															<div id="unitpriceErrorMsg" class="text-danger"></div>
														</div>
													</div>
												</div>	
												
												
												<div class="row1" id="grpwoJobType" class="form-group">
													<label class="L-size control-label" for="from">Lost
														Quantity : <abbr title="required">*</abbr>
													</label>
													<div class="I-size">
															<div class="input-group">
																<input type="number" class="form-text" placeholder="0.0"
																	name="lostQuantity" required="required" id="lostQuantity"
																	maxlength="5" data-toggle="tip" onkeyup="valQty();"
																	>
															</div>
													</div>
												</div>
												
												<div class="row1" id="grpwoJobType" class="form-group">
													<label class="L-size control-label" for="from">
														Description : 
													</label>
													<div class="I-size">
															<div class="input-group">
																<input type="text" class="form-text" placeholder="Enter Description"
																	name="lostRemark"  id="lostRemark"
																	>
															</div>
													</div>
												</div>
												
											
											</div>
										</div>
									</div>
								</div>
							</fieldset>
					</div>				
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary">
							<span id="Save" onclick="saveClothLostDetails();">Add Upholstery To Lost</span>
						</button>
						<button type="button" class="btn btn-default"
							data-dismiss="modal">
							<span id="Close">Close</span>
						</button>
					</div>
				</div>
			</div>
		</div>
		
		
		
			
			
		<% } %>
	</div>
</div>

	<script type="text/javascript">
var myInput = document.querySelector('#roundupTotal');
myInput.addEventListener("keyup", function(){
	  myInput.value = myInput.value.replace(/(\.\d{2})\d+/g, '$1');
	});
	
$("#unitprice").keyup(function() {
    if ($(this).val($(this).val().replace(/[^0-9\.]/g, "")), -1 != $(this).val().indexOf(".") && $(this).val().split(".")[1].length > 2) {
        if (isNaN(parseFloat(this.value))) return;
        this.value = parseFloat(this.value).toFixed(2)
    }
    return this
})
	</script>

<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>  
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/ureainventory/addUreaInventory.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/ureainventory/ViewUreaInvoiceList.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/ureainventory/UreaInventoryUtility.js"></script>

