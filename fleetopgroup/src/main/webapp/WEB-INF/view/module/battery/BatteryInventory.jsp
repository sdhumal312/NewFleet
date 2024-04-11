<%@ include file="../../taglib.jsp"%>
<%@page import="org.springframework.security.core.authority.SimpleGrantedAuthority"%>
<%@page import="org.springframework.security.core.GrantedAuthority"%>
<%@page import="java.util.Collection"%>
<%@page import="org.springframework.web.servlet.ModelAndView"%>
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css">
<style>
td, th {
    padding: 4px;
}
</style>
<div class="content-wrapper">
	<section class="panel panel-success">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
						<a href="open" >Home</a>/ <a href="BatteryInventory.in">New Battery Inventory</a>
				</div>
				<div class="pull-right">
				
			
						<% 
							Collection<GrantedAuthority>  permission = (Collection<GrantedAuthority>)request.getAttribute("permissions");
							if(permission.contains(new SimpleGrantedAuthority("ADD_BATTERY_INVENTORY"))) {
						%>
						<a class="btn btn-default btn-sm" data-toggle="tip"
							data-original-title="Download Import XLSX Format. When Import Don't Remove the header"
							href="${pageContext.request.contextPath}/downloadbatteryinventorydocument.in">
							<i class="fa fa-download"></i>
						</a>
						<button type="button" class="btn btn-default btn-sm"
							data-toggle="modal" data-target="#addImport" data-whatever="@mdo">
							<span class="fa fa-file-excel-o"> Import</span>
						</button>
						<a class="btn btn-success btn-sm" onclick="showHideAddBattery();">
   							 <span class="fa fa-plus">
								Add Battery Inventory</span></a>
  						</a>
  						<a href="<c:url value="/addVendor.in"/>"
							class="btn btn-success btn-sm"><span class="fa fa-plus">
								Create Vendor</span></a>	
  						<% } %>
					<% if(permission.contains(new SimpleGrantedAuthority("ADD_BATTERY_SCRAP"))) {%>
						<a class="btn btn-danger btn-sm" href="ScrapBatteryFilter"><span class="fa fa-search">
								Scrap Filter</span></a>	
									
					<% } if(permission.contains(new SimpleGrantedAuthority("VIEW_BATTERY_INVENTORY"))) { %>	
						<!-- <a class="btn btn-success btn-sm" href="#" onclick="getPageWiseBatteryInvoiceDetails(1)"><span class="fa fa-search">
								View Battery Invoice</span></a> 
						 <a class="btn btn-success btn-sm" href="#" onclick="getPageWiseBatteryInvoiceDetails(1)"><span class="fa fa-search">
								View Battery Invoice</span></a> 
					<!--	 <a class="btn btn-success btn-sm" href="#" onclick="getPageWiseBatteryInvoiceDetails(1)"><span class="fa fa-search">
								View Battery Invoice</span></a> 
						 <a class="btn btn-info btn-sm" href="ScrapFilter"><span class="fa fa-search">
								Search</span></a>	 -->
					<% } %>	
					<%  if(permission.contains(new SimpleGrantedAuthority("TRANSFER_BATTERY"))) { %>
						<a class="btn btn-info btn-sm"
							href="<c:url value="/multiTransferBatteryInventory.in"/>"> <span
							class="fa fa-exchange"></span>Transfer Battery
						</a>
						<a class="btn btn-info btn-sm"
							href="<c:url value="/receiveMultipleBatteryTransfered.in"/>"> <span
							class="fa fa-check-circle"></span>Receive Battery
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
		<% if(permission.contains(new SimpleGrantedAuthority("VIEW_BATTERY_INVENTORY"))) {%>
			<div class="row" id="searchData">
			<div id="countDiv" style="display : none;" class="col-md-4 col-sm-4 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i class="fa fa-cubes"></i></span>
						<div class="info-box-content">
						 <input
								type="hidden" value="${location}" id="statues">
							<span class="info-box-text" id="countId">Total Battery Invoice</span> 
							<span id="totalBattryInvoice" class="info-box-number"></span>
						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Battery Invoice</span>
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
				<div class="col-md-3 col-sm-4 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Battery No</span>
								<div class="input-group">
									<input class="form-text" onkeyup="batterySearchOnEnter(event);" id="searchBattery"  name="Search" type="text"
										required="required" placeholder="Only Battry No">
									<span class="input-group-btn">
										<button type="submit" onclick="searchBatteryDetails(1);" name="search" 
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
		
			<div class="row" id="batteryCount">
				<div id=""  class="col-md-4 col-sm-4 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i class="fa fa-cubes"></i></span>
						<div class="info-box-content">
						 <input type="hidden" value="${location}" id="availableLocation">
							<span class="info-box-text" id="countId">All Available Battery</span> 
							<a href="#"  onclick="getBatteryList(1,1);">
							<span id="allAvailableBatteryCount" class="info-box-number"></span>
							</a>
						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i class="fa fa-cubes"></i></span>
						<div class="info-box-content">
						 <input
								type="hidden" value="${location}" id="inServiceLocation">
							<span class="info-box-text" id="countId">Battery In Service </span> 
							<a href="#" onclick="getBatteryList(1,2);">
							<span id="batteryInServiceCount" class="info-box-number"></span>
							</a>
						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-4 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i class="fa fa-cubes"></i></span>
						<div class="info-box-content">
						 <input
								type="hidden" value="${location}" id="scrapedLoaction">
							<span class="info-box-text" id="countId">Scraped Battery</span>
							<a href="#" onclick="getBatteryList(1,3);"> 
							<span id="scrapedBatteryCount" class="info-box-number"></span>
							</a>
						</div>
					</div>
				</div>
			</div>
	</section>
		<div id="listTab" class="main-tabs" style="display: none;">
						<ul class="nav nav-pills" id="list">
							<li role="presentation" id="All"><a
								href="#" onclick="getPageWiseBatteryInvoiceDetails(1);">Battery Invoice</a></li>
							<c:if test="${!empty PartLocations}">
								<c:forEach items="${PartLocations}" var="PartLocations">
							<input type="hidden" id="locationId" value ="${PartLocations.partlocation_id}">
									<li class="tab-link" id="${PartLocations.partlocation_name}">
									<a class="btn btn-link" href="#" onclick="locationBatteryDetails(${PartLocations.partlocation_id}, 1);" >
											${PartLocations.partlocation_name}</a></li>
								</c:forEach>
							</c:if>
						</ul>
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
			if(!permission.contains(new SimpleGrantedAuthority("ADD_BATTERY_INVENTORY"))) {
		%>
			Unauthorized Access !!
		<% } %>
		<% 
			if(permission.contains(new SimpleGrantedAuthority("ADD_BATTERY_INVENTORY"))) {
		%>
		<br/>
			<form method="POST" enctype="multipart/form-data" id="fileUploadForm">	
			<div class="row hide" id="addBatteryDiv">
				<div class="col-md-offset-1 col-md-9">
					<form id="formTyreInventory"
						class="form-horizontal" novalidate="novalidate">
						<div class="form-horizontal ">
							<fieldset>
								<legend>Search Battery Manufacturer &amp; Types </legend>
								<input type="hidden" id="accessToken" value="${accessToken}">
								<div class="box">
									<div class="box-body">
										<div class="panel panel-success">
											<div class="panel-body">
												<div class="row1" class="form-group">
													<div id="grpmanufacturer">
														<label class="L-size control-label" for="manufacurer">Battery
															Manufacturer :<abbr title="required">*</abbr>
														</label>
														<div class="col-md-3">
														<input type="hidden" id="addBatteryInventoryDocument" value="${configuration.addBatteryInventoryDocument}">
														<input type="hidden" id="downloadBatteryInventoryDocument" value="${configuration.downloadBatteryInventoryDocument}">
														<input type="hidden" id="showSubLocation" value="${showSubLocation}">
														<input type="hidden" id="mainLocationIds" value="${mainLocationIds}">
														<input type="hidden" id="validateSubLocation" >	
														<input type="hidden" id="roundupConfig" value="${configuration.roundupAmount}">
															<input type="hidden" id="manufacurer"
																name="batteryManufacturerId" style="width: 100%;"
																placeholder="Please Enter 2 or more Battery Manufacturer Name" />
															<span id="manufacturerIcon" class=""></span>
															<div id="manufacturerErrorMsg" class="text-danger"></div>
														</div>
													</div>	
													<div id="grptyreModel" class="form-group">
														<label class="L-size control-label" for="tyremodel">Battery
															Model :<abbr title="required">*</abbr>
														</label>
														<div class="col-md-3">
															<select style="width: 100%;" id="batterryTypeId"
															 name="batteryTypeId">
															</select> <span id="tyreModelIcon" class=""></span>
															<div id="tyreModelErrorMsg" class="text-danger"></div>
														</div>
													</div>
												</div>
												<div class="row1" class="form-group">
													<div id="grptyreSize">
														<label class="L-size control-label" for="tyreSize">Battery
															Capacity :<abbr title="required">*</abbr>
														</label>
														<div class="col-md-3">
															<input type="hidden" id="batteryCapacityId" name="batteryCapacityId"
																style="width: 100%;"
																placeholder="Please select Battery Capacity" /> <span
																id="tyreSizeIcon" class=""></span>
															<div id="tyreSizeErrorMsg" class="text-danger"></div>
														</div>
													</div>
													
													<input type="hidden" id="discountTaxTypId" value="${configuration.discountTaxTypeId}">
													<input type="hidden" id="finalDiscountTaxTypId" name="finalDiscountTaxTypId">
													<label class="L-size control-label" for="payMethod">Discount/GST
														Type :<abbr title="required">*</abbr>
													</label>
													<div class="col-md-3">
														<div class="">
															<div class="btn-group"  >
																<label id="percentId" class="btn btn-default  btn-sm active" onclick="selectDiscountTaxType(1, 0);">
																	Percentage
																</label> 
																<label id="amountId" class="btn btn-default  btn-sm" onclick="selectDiscountTaxType(2, 0);"> 
																	Amount
																</label>
															</div>
														</div>
													</div>
												</div>
												
												<!-- <div class="col-md-3">
													<div class="">
														<div class="btn-group"  >
															<button id="percentId" class="btn btn-default  btn-lg active" onclick="selectDiscountTaxType(1);">
																<input type="radio" value="1" name="ola" checked="checked"
																id="percentageTypeId" onchange="selectDiscountTaxType(this);" >Percentage
															</button> 
															<button id="amountId" class="btn btn-default  btn-lg" onclick="selectDiscountTaxType(2);"> 
																<input type="radio" value="2" name="ola" 
																id="amountTypeId" onchange="selectDiscountTaxType(this);" >Amount
															</button>
														</div>
													</div>
												</div> -->
												
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
																data-toggle="tip" onpaste="return false"
																data-original-title="enter Tyre Quantity"
																onkeypress="return isNumberKey(event,this);"
																onkeyup="javascript:sumthereTypeWise('quantity', 'unitprice', 'discount', 'tax', 'tatalcost', 0);"
																ondrop="return false;"> <span id="quantityIcon"
																class=""></span>
															<div id="quantityErrorMsg" class="text-danger"></div>
														</div>
														<div class="col-md-2">
															<input type="text" class="form-text" onpaste="return false"
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
																min="0.0" id="discount" maxlength="5" onpaste="return false"
																placeholder="Discount" required="required"
																data-toggle="tip" data-original-title="enter Discount"
																onkeypress="return isNumberKeyWithDecimal(event,this.id)"
																onkeyup="javascript:sumthereTypeWise('quantity', 'unitprice', 'discount', 'tax', 'tatalcost', 0);"
																ondrop="return false;"><span id="discountIcon"
																class=""></span>
															<div id="discountErrorMsg" class="text-danger"></div>

														</div>
														<div class="col-md-1">
															<input type="text" class="form-text" name="tax_many" onpaste="return false"
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
								<legend>Battery Inventory Details</legend>
								<div class="box">
									<div class="box-body">
										<div class="row1">
											<div id="grppartLocation" class="form-group">
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
											<label class="L-size control-label"> Battery Vendor :<abbr
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
											<label class="L-size control-label">PO Number :</label>
											<div class="col-md-3">
												<input type="text" id="PO_NUMBER" class="form-text" name="PO_NUMBER"
													maxlength="25" onkeypress="return IsPonumber(event);"
													ondrop="return false;"> <label class="error"
													id="errorPonumber" style="display: none"> </label>
											</div>
										
											<label class="L-size control-label">Invoice Number:</label>
											<div class="col-md-3">
												<input type="text" id="INVOICE_NUMBER" class="form-text" name="INVOICE_NUMBER"
													maxlength="25" onkeypress="return IsInvoicenumber(event);"
													ondrop="return false;"> <label class="error"
													id="errorInvoicenumber" style="display: none"> </label>
											</div>
										</div>
										<div class="row1" id="grpinvoiceDate" class="form-group">
											<label class="L-size control-label" for="invoiceDate">Invoice
												Date :<abbr title="required">*</abbr>
											</label>
											<div class="col-md-3">
												<div class="input-group input-append date" id="opendDate">
													<input type="text"  class="form-text" name="INVOICE_DATE"
														id="invoiceDate" required="required"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
												<span id="invoiceDateIcon" class=""></span>
												<div id="invoiceDateErrorMsg" class="text-danger"></div>
											</div>
										<c:if test="${!configuration.roundupAmount}">
											<label class="L-size control-label">Invoice Amount:</label>
											<div class="col-md-3">
												<input type="number" class="form-text" name="INVOICE_AMOUNT"
													value="0" id="invoiceAmount" maxlength="25"
													
													onkeypress="return isNumberKeyWithDecimal(event,this.id)"
													ondrop="return false;"> <label class="error"
													id="errorInvoicenumber" style="display: none"> </label>
											</div>
											</c:if>
										</div>
										<c:if test="${configuration.roundupAmount}">
										<div class="row1">
										<label class="L-size control-label">Invoice Amount:</label>
											<div class="col-md-3">
												<input type="number" class="form-text" name="INVOICE_AMOUNT"
													 id="readOnlyInvoiceAmount" readonly="readonly" maxlength="25"
													onkeypress="return isNumberKeyWithDecimal(event,this.id)"
													ondrop="return false;"> <label class="error"
													id="errorInvoicenumber" style="display: none"> </label>
											</div>
										<label class="L-size control-label">Roundup amount:</label>
											<div class="col-md-3">
												<input type="text" class="form-text" name="INVOICE_AMOUNT"
													 id="roundupTotal" maxlength="10" required="required"
													onkeypress="return isNumberKeyWithDecimal(event,this.id)"
													ondrop="return false;"> <label class="error"
													id="errorInvoicenumber" style="display: none"> </label>
											</div>
										</div>
										</c:if>
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
									<c:if test="${configuration.addBatteryInventoryDocument}">
										<fieldset id="grpfuelDocument">
											<legend>Document</legend>
											<div class="box">
												<div class="box-body">
													<div class="row1">
														<label class="L-size control-label" for="fuel_partial">Battery
															Document : </label>
														<div class="I-size">
															<input type="file" name="input-file-preview" id="batteryDocument" /> 
																<span id="renewalFileIcon" class=""></span>
															<div id="renewalFileErrorMsg" class="text-danger"></div>
															<span class="help-block">Add an optional document</span>
														</div>
													</div>
												</div>
											</div>
										</fieldset>
									</c:if>
									<c:if test="${!configuration.addBatteryInventoryDocument}">
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
								<div class="row1" id="saveBatteryInv">
									<div class="col-md-10 col-md-offset-2">
										<button type="submit" id="submit" class="btn btn-success" onclick="return validateBatteryInventory();">Create
											Battery</button>
										<a class="btn btn-link"
											href="BatteryInventory.in">Cancel</a>
									</div>
								</div>
							</fieldset>
						</div>
					</form>
				</div>
			</div>
				<%@ include file="/WEB-INF/view/payment/PaymentDetails.jsp"%>
			</form>
		<% } %>
	</div>
</div>
<div class="content" >
<div class="modal fade" id="batteryModelList" role="dialog">
	<div class="modal-dialog" style="width:750px;">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header" style="text-align: center;">
				<span id="headerData"></span>
			</div>
			<div class="modal-body">
				<table id="list1" style="width: 100%;" border="2"></table>
			</div>
			<div class="text-center">
				<ul id="navigationBar6" class="pagination pagination-lg pager"> </ul>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<span id="Close"><spring:message code="label.master.Close" /></span>
				</button>
			</div>
		</div>
	</div>
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


<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/battery/batteryUtility.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/battery/addBatteryInventory.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/battery/ViewBatteryList.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/battery/batteryInventoryValidation.js"></script>

