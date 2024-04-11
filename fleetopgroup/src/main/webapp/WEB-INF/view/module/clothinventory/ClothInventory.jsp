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
						<a href="open" >Home</a>/ <a href="ClothInventory.in">New Upholstery Inventory</a>
				</div>
				<div class="pull-right"> 
						<% 
						Collection<GrantedAuthority>  permission = (Collection<GrantedAuthority>)request.getAttribute("permissions");
						if(permission.contains(new SimpleGrantedAuthority("ADD_CLOTH_TYPES_INVENTORY"))) {
						%>
						<%-- <a class="btn btn-default btn-sm" data-toggle="tip"
							data-original-title="Download Import XLSX Format. When Import Don't Remove the header"
							href="${pageContext.request.contextPath}/downloadbatteryinventorydocument.in">
							<i class="fa fa-download"></i>
						</a>
						<button type="button" class="btn btn-default btn-sm"
							data-toggle="modal" data-target="#addImport" data-whatever="@mdo">
							<span class="fa fa-file-excel-o"> Import</span>
						</button> --%>
						<button type="button" class="btn btn-warning btn-sm" data-toggle="modal"
							onclick="damageQty();" data-whatever="@mdo">
							<span class="fa fa-plus" id="AddJobType">Add Damaged Qty</span>
						</button>
						<button type="button" class="btn btn-danger btn-sm" data-toggle="modal"
							onclick="lostQty();" data-whatever="@mdo">
							<span class="fa fa-plus" id="AddJobType">Add Lost Qty</span>
						</button>
						<a href="SendClothToLaundry.in"  class="btn btn-primary btn-sm">
							<span class="fa fa-search">Send To Laundry</span>
						</a>
						<a class="btn btn-success btn-sm" onclick="showHideAddCloth();">
   							 <span class="fa fa-plus">
								Add Upholstery Inventory</span></a>
						<a href="<c:url value="/addVendor.in"/>"
							class="btn btn-success btn-sm"><span class="fa fa-plus">
								Create Vendor</span></a>	  						
  						<a class="btn btn-info btn-sm"  
							href="<c:url value="/upholsteryTransfer.in"/>"> <span
							class="fa fa-exchange"></span>Transfered Upholstery
						</a>
  						<a class="btn btn-default btn-sm"
							href="<c:url value="/upholsteryReceive.in"/>"> <span
							class="fa fa-exchange"></span>Receive Upholstery
						</a>
  						<% } %>
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
			<div class="row" >
			<div id="countDiv" class="col-md-3">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i class="fa fa-cubes"></i></span>
						<div class="info-box-content">
						 <input
								type="hidden" value="${location}" id="statues">
							<span class="info-box-text" id="countId">Upholstery Count</span> 
							<span id="totalClothInvoice" class="info-box-number"></span>
						</div>
					</div>
				</div>
				 <div class="col-md-3">
				 	<div class="info-box">
					<span class="info-box-text">Search Upholstery Invoice</span>
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
				 <div class="col-md-3">
					<div class="info-box">
						<span class="info-box-text">Search By Date</span>
							<div class="input-group">
								<button type="button" class="btn btn-warning btn-sm" data-toggle="modal"
									onclick="showDateRangePopup();" data-whatever="@mdo">
									<span class="fa fa-plus "  id="dateRange"> Serach Assign Upholstery</span>
								</button>
							</div>
						</div>	
					</div>	
					<!-- <div class="col-md-3"> -->
						<div class="info-box">
						<span class="info-box-text">Search Vehicle To Asign</span>
						<div class="input-group">
							<input type="hidden" id="TripSelectVehicle_ID" name="vid"
								style="width: 100%;" required="required" 
								placeholder="Search Vehicle Name" /> <span
								class="input-group-btn">
								<button type="submit" name="search" id="search-btn" onclick="forwardToUpholsteryAsignPage();"
									class="btn btn-success btn-sm">
									<i class="fa fa-search" ></i>
								</button>
							</span>
						</div>
						</div>
					<!-- </div> -->
				</div>
	</section>
		<div id="listTab" class="main-tabs" style="display: none;">
						<ul class="nav nav-pills" id="list">
							<li role="presentation" id="All"><a
								href="#" onclick="getPageWiseClothInvoiceDetails(1);">Upholstery Invoice</a></li>
							<li role="presentation" id="laundry"><a
								href="#" onclick="getClothLaundryDetails(1);">Upholstery Laundry</a></li>	
							<c:if test="${!empty PartLocations}">
								<c:forEach items="${PartLocations}" var="PartLocations">
									<li class="tab-link" id="${PartLocations.partlocation_name}"><a
										class="btn btn-link"
										href="#" onclick="locationClothDetails(${PartLocations.partlocation_id}, 1);" >
											${PartLocations.partlocation_name}</a></li>
								</c:forEach>
							</c:if>
						</ul>
						<input type="hidden" id="downloadClothInventoryDocument" value="${configuration.downloadClothInventoryDocument}">
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
			if(!permission.contains(new SimpleGrantedAuthority("ADD_CLOTH_TYPES_INVENTORY"))) {
		%>
			Unauthorized Access !!
		<% } %>
		<% 
			if(permission.contains(new SimpleGrantedAuthority("ADD_CLOTH_TYPES_INVENTORY"))) {
		%>
		<br/>
			<div class="row hide" id="addClothDiv">
				<div class="col-md-offset-1 col-md-9">
					<form method="POST" enctype="multipart/form-data" id="fileUploadForm">
						<div class="form-horizontal ">
							<fieldset>
								<legend>Search Upholstery Types </legend>
								<input type="hidden" id="showSubLocation" value="${showSubLocation}">
								<input type="hidden" id="mainLocationIds" value="${mainLocationIds}">
								<input type="hidden" id="roundupConfig" name="roundupConfig" value="${configuration.roundupAmount}">
								<div class="box">
								<input type="hidden" id="accessToken" value="${accessToken}">
									<div class="box-body">
										<div class="panel panel-success">
											<div class="panel-body">
											
												<div class="row1" id="grpmanufacturer" class="form-group">
													<label class="L-size control-label" for="manufacurer">Upholstery Types :<abbr title="required">*</abbr>
													</label>
													<div class="col-md-3">
														<input type="hidden" id="clothTypes"
															name="clothTypes" style="width: 100%;"
															placeholder="Please Enter 2 or more Cloth Types Name" />
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
															<input type="text" class="form-text" name="quantity_many"
																min="0.0" id="quantity" maxlength="4"
																placeholder="ex: 23.78" required="required"
																data-toggle="tip"
																data-original-title="enter Tyre Quantity"
																onkeypress="return isNumberKey(event,this);"
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
																onkeypress="return isNumberKeyWithDecimal(event,this.id);"
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
																onkeypress="return isNumberKeyWithDecimal(event,this.id);"
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
																onkeypress="return isNumberKeyWithDecimal(event,this.id);"
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
								<legend>Upholstery Inventory Details</legend>
								<div class="box">
									<div class="box-body">
									
										<div class="row1">
											<label class="L-size control-label">Types of Upholstery
												<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<select class="form-text" name="typeOfCloth"
													id="typeOfCloth" required="required">
													<option value="1">NEW</option>
													<option value="2">OLD</option>
												</select>
											</div>
										</div>
										
										<div class="row1" id="grppartLocation" class="form-group">
											<div  id="grppartLocation">
												<label class="L-size control-label" for="warehouselocation">Warehouse
													location :<abbr title="required">*</abbr>
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
											<label class="L-size control-label"> Upholstery Vendor :<abbr
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
											
											<label class="L-size control-label">PO Number :</label>
											<div class="col-md-3">
												<input type="text" id="PO_NUMBER" class="form-text" name="PO_NUMBER"
													maxlength="25" onkeypress="return IsPonumber(event);"
													ondrop="return false;"> <label class="error"
													id="errorPonumber" style="display: none"> </label>
											</div>
										</div>
										
										<div class="row1">
											<label class="L-size control-label">Invoice Number:</label>
											<div class="col-md-3">
												<input type="text" id="INVOICE_NUMBER" class="form-text" name="INVOICE_NUMBER"
													maxlength="25" onkeypress="return IsInvoicenumber(event);"
													ondrop="return false;"> <label class="error"
													id="errorInvoicenumber" style="display: none"> </label>
											</div>
											
											<div id="grpinvoiceDate" class="form-group">
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
													value="0" id="invoiceAmount" maxlength="25"
													onkeypress="return isNumberKeyWithDecimal(event,this.id);"
													onpaste="return false" 
													ondrop="return false;"> <label class="error"
													id="errorInvoicenumber" style="display: none"> </label>
											</div>
											</c:if>
											
											<c:if test="${configuration.roundupAmount}">
											
											
											<label class="L-size control-label">Invoice Amount:</label>
											<div class="col-md-3">
												<input type="text" class="form-text" 
													value="0" id="viewonlyInvoiceamount" maxlength="25" readonly="readonly"
													onkeypress="return isNumberKeyWithDecimal(event,this.id);"
													onpaste="return false" 
													ondrop="return false;"> <label class="error"
													id="errorInvoicenumber" style="display: none"> </label>
											</div>
											
											<label class="L-size control-label">Roundup Amount</label>
											<div class="col-md-3">
												<input type="text" class="form-text" name="INVOICE_AMOUNT"
													 id="roundupTotal" maxlength="10" required="required"
													onkeypress="return isNumberKeyWithDecimal(event,this.id);"
													onpaste="return false" 
													ondrop="return false;"> <label class="error"
													id="errorInvoicenumber" style="display: none"> </label>
											</div>
											
											</c:if>
											
											<input type="hidden" id="tallyIntegrationRequired" value="${configuration.tallyIntegrationRequired}">
											<c:if test="${configuration.tallyIntegrationRequired}">
												<div  id="grpmanufacturer" class="form-group">
												<label class="L-size control-label" for="manufacurer">Tally Company Name :<abbr title="required">*</abbr></label>
													<div class="col-md-3">
														<input type="hidden" id="tallyCompanyId" name="tallyCompanyId" style="width: 100%;" value="0"
														  placeholder="Please Enter Tally Company Name" />
													</div>
												</div>
											</c:if>

										</div>
									
										<c:if test="${configuration.addClothInventoryDocument}">
											<fieldset id="grpfuelDocument">
												<legend>Document</legend>
												<div class="box">
													<div class="box-body">
														<div class="row1">
															<label class="L-size control-label" for="fuel_partial">Cloth
																Document : </label>
															<div class="I-size">
																<input type="file" name="input-file-preview" id="clothDocument" /> 
																	<span id="renewalFileIcon" class=""></span>
																<div id="renewalFileErrorMsg" class="text-danger"></div>
																<span class="help-block">Add an optional document</span>
															</div>
														</div>
													</div>
												</div>
											</fieldset>
										</c:if>
										<c:if test="${!configuration.addClothInventoryDocument}">
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
								<div class="row1" id="saveUpholstery">
									<div class="col-md-10 col-md-offset-2">
										<button type="submit" id="submit"  class="btn btn-success" onclick="return validateUpholstery();">Create
											Cloth Inventory</button>
										<a class="btn btn-link"
											href="ClothInventory.in">Cancel</a>
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
									 <div class="text-center">
										<ul id="navigationBar2" class="pagination pagination-lg pager">
											
										</ul>
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
								<div class="form-horizontal ">
									<div class="box">
										<div class="box-body">
											<div class="form-horizontal ">
											
												<div class="row1" class="form-group">
													<label class="L-size control-label" for="from">Location
														<abbr title="required">*</abbr> </label>
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
														Name <abbr title="required">*</abbr> </label>
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
																	maxlength="5" data-toggle="tip"  onkeyup="validateQty();" 
																	onblur="validateQty();" onkeypress="return isNumberKeyWithDecimal(event,this.id)" >
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
								<div class="form-horizontal ">
									<div class="box">
										<div class="box-body">
											<div class="form-horizontal ">
											
												<div class="row1" class="form-group">
													<label class="L-size control-label" for="from">Location
														<abbr title="required">*</abbr> </label>
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
														Name <abbr title="required">*</abbr> </label>
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
																	maxlength="5" data-toggle="tip"  onkeyup="valQty();"
																	onblur="valQty();" onkeypress="return isNumberKeyWithDecimal(event,this.id)" >
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
												
												<div class="row1">
													<label class="L-size control-label">Vehicle :
													</label>
													<div class="I-size">
														<input type="hidden" id="ReportSelectVehicle"
															name="repair_vid" style="width: 100%;"
															required="required"
															placeholder="Please Enter Vehicle" />
													</div>
												</div>

												<div class="row1">
													<label class="L-size control-label" for="issue_vehicle_id">Driver
														/ Conductor: <abbr title="required">*</abbr>
													</label>
													<div class="I-size">
														<input type="hidden" id="driverAllList" name="DRIVER_ID"
															style="width: 100%;" value="0"
															placeholder="Please Enter Driver/Conductor Name, NO" />
														<label id="errorDriver2" class="error"></label>
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
		
		<div class="modal fade" id="inDamage" role="dialog">
			<div class="modal-dialog" style="width:1250px;">
				<!-- Modal content-->
				<div class="modal-content">
					
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="JobType">In Damage 
								Details</h4>
						</div>
						<div class="modal-body">
							<div class="box">
								<div class="box-body">
							 
									<div class="table-responsive">
										<input type="hidden" id="startPage" value="${SelectPage}">
										<table id="VendorPaymentTable2" class="table table-hover table-bordered">
										</table>
									</div>
									<div class="text-center">
										<ul id="navigationBar3" class="pagination pagination-lg pager">
											
										</ul>
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
		<div class="modal fade" id="inLost" role="dialog">
			<div class="modal-dialog" style="width:1250px;">
				<!-- Modal content-->
				<div class="modal-content">
					
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="JobType">In Lost 
								Details</h4>
						</div>
						<div class="modal-body">
							<div class="box">
								<div class="box-body">
							 
									<div class="table-responsive">
										<input type="hidden" id="startPage" value="${SelectPage}">
										<table id="VendorPaymentTable3" class="table table-hover table-bordered">
										</table>
									</div>
									<div class="text-center">
										<ul id="navigationBar4" class="pagination pagination-lg pager">
											
										</ul>
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
			
		<div class="modal fade" id="inWashing" role="dialog">
			<div class="modal-dialog" style="width:1250px;">
				<!-- Modal content-->
				<div class="modal-content">
					
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="JobType">In Washing 
								Details</h4>
						</div>
						<div class="modal-body">
							<div class="box">
								<div class="box-body">
							 
									<div class="table-responsive">
										<input type="hidden" id="startPage" value="${SelectPage}">
										<table id="VendorPaymentTable4" class="table table-hover table-bordered">
										</table>
									</div>
									<div class="text-center">
										<ul id="navigationBar5" class="pagination pagination-lg pager">
											
										</ul>
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
		
		<div class="modal fade" id="showAssignUpholsteryDate" role="dialog">
			<div class="modal-dialog" >
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
							<h4 class="modal-title">Search Assign Upholstery By Date</h4>
					</div>
					<div class="modal-body">
						<div class="box">
							<div class="box-body">
									<div class="input-group">
										<div class="input-group-addon">
											<i class="fa fa-calendar"></i>
										</div>
										<input type="text" id="upholsteryAssignDate" class="form-text" name="searchDate" required="required"
											style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%" readonly="readonly">
									</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary" onclick="showUpholsteryAssignedVehicles(1);">
							<span>Show</span>
						</button>
						<button type="button" class="btn btn-default"
							data-dismiss="modal">
							<span id="Close"><spring:message
									code="label.master.Close" /></span>
						</button>
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade" id="setUpholsteryAssignVehicles" role="dialog">
			<div class="modal-dialog" style="width:1250px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="JobType"> Upholstery Assign to Vehicles</h4>
					</div>
					<div class="modal-body">
						<div class="box">
							<div class="box-body">
								<div class="table-responsive">
									<table id="setUpholsteryVehicles" class="table table-hover table-bordered">
									</table>
								</div>
								<div class="text-center">
									<ul id="navigationBar6" class="pagination pagination-lg pager"> </ul>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span id="Close"> <spring:message code="label.master.Close" /></span>
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
if(myInput != null && myInput != undefined){
myInput.addEventListener("keyup", function(){
	  myInput.value = myInput.value.replace(/(\.\d{2})\d+/g, '$1');
	});
}

$("#unitprice").keyup(function() {
    if ($(this).val($(this).val().replace(/[^0-9\.]/g, "")), -1 != $(this).val().indexOf(".") && $(this).val().split(".")[1].length > 2) {
        if (isNaN(parseFloat(this.value))) return;
        this.value = parseFloat(this.value).toFixed(2)
    }
    return this
})


</script>

<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/clothinventory/clothUtility.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/clothinventory/addClothInventory.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/clothinventory/ViewClothList.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/clothinventory/validateClothInventory.js"></script>

<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>

