<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@page import="org.springframework.security.core.authority.SimpleGrantedAuthority"%>
<%@page import="org.springframework.security.core.GrantedAuthority"%>
<%@page import="java.util.Collection"%>
<link rel="stylesheet"
	href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet"
	href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">
<div class="content-wrapper">
	<section class="content">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="open">Home</a> / <a
						href="UreaInventory.in">New Urea Inventory</a>
					/ <span>View Urea Details</span>
				</div>
				<div class="pull-right">
						<input type="hidden" id="companyId" value="${companyId}">
					<% 
						Collection<GrantedAuthority>  permission = (Collection<GrantedAuthority>)request.getAttribute("permissions");
						if(!permission.contains(new SimpleGrantedAuthority("DELETE_UREA_INVENTORY"))) {
						%>
					  		<a id="delete" onclick="deleteUreaInvoice();" class="btn btn-primary btn-sm" href="#">Delete Urea Invoice</a>
					<% } %>
					<a href="UreaInventory.in">Cancel</a>
				</div>
				
				<div class="col-md-2">
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
			<sec:authorize access="!hasAuthority('VIEW_UREA_INVENTORY')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_UREA_INVENTORY')">
				<div class="row">
					<div class="col-md-7 col-sm-7 col-xs-7">
						<!-- <div class="pull-left"> -->
						<h4>Urea Invoice : <span id="clothInvoiceNumber"></span></h4>
						<input type="hidden" id="invoiceId" value="${invoiceId}"/>
						<input type="hidden" id="moduleId" value="${invoiceId}"/> 
						<input type="hidden" id="modulePaymentTypeId"/> 
						<input type="hidden" id="moduleIdentifier"/>
						<h4 id="vendorinfo" align="center">
						</h4>
					</div>
					<div class="col-md-2 col-sm-2 col-xs-2">
						<div class="row">
							<div id="work-order-statuses">
								<div id="work-order-statuses">
									
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-3 col-sm-3 col-xs-11">
						<div class="box box-success">
							<div class="box-body no-padding">
								<table class="table ">
									<tbody>
										<tr class="row">
											<th class="key">Invoice Date :</th>
											<td class="value" id="invoiceDate"></td>
										</tr>
										<tr class="row">
											<th class="key">Payment Type :</th>
											<td class="value" id="paymentType"><span id="paymentTypeSpan"> </span></td>
										</tr>
										<tr class="row">
											<th class="key">WareHouse Location :</th>
											<td class="value" id="wareHouseLocation"></td>
										</tr>
										<tr class="row">
											<th class="key">Vendor Payment Status :</th>
											<td class="value" id="vendorPaymentStatus"></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="col-md-3 col-sm-3 col-xs-11">
						<div class="box box-success">
							<div class="box-body no-padding">
								<table class="table">
									<tbody>
										<tr class="row">
											<th class="key">Invoice Number :</th>
											<td class="value" id="invoiceNumber"></td>
										</tr>
										
										<c:if test="${!configuration.roundupAmount}">
										<tr class="row">
											<th class="key">Invoice Amount:</th>
											<td class="value" id="invoiceAmount"></td>
										</tr>
										</c:if>
										
										<c:if test="${configuration.roundupAmount}">
										<tr class="row">
											<th class="key">Roundup Amount:</th>
											<td class="value" id="invoiceAmount"></td>
										</tr>
											<tr class="row">
											<th class="key">Invoice Amount:</th>
											<td class="value" id="invoiceAmounttotal"></td>
										</tr>
										</c:if>
										
										<c:if test="${showSubLocation}">
											<tr class="row">
												<th class="key">Sub Location:</th>
												<td class="value" id="subLocation"></td>
											</tr>
										</c:if>	
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="col-md-4 col-sm-4 col-xs-11">
						<div class="box box-success">
							<div class="box-body no-padding">
								<table class="table">
									<tbody>
										<tr class="row">
											<th class="key">Payment Number :</th>
											<td class="value" id="paymentNumber"></td>
										</tr>
										<tr class="row">
											<th class="key">PO Number :</th>
											<td class="value" id="poNumber"></td>
										</tr>
										<tr class="row">
											<th class="key">Approval Number:</th>
											<td class="value" id="approvalNumber"></td>
										</tr>
										<c:if test="${configuration.tallyIntegrationRequired}">
											<tr class="row">
												<th class="key">Tally Company :</th>
												<td class="value" id="tallyCompany"></td>
											</tr>
										</c:if>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					
				</div>
				
				
				<fieldset>


					<div class="row">
						<div class="col-md-11 col-sm-11 col-xs-11">
							<div class="table-responsive">
								<table class="table" id="dataTable" style="display: none;">
									<thead>
										<tr class="breadcrumb">
											<th class="fit"></th>
											<th class="fit ar">Manufacturer</th>
											<th class="fit ar">Qty</th>
											<th class="fit ar">Transfer Qty</th>
											<th class="fit ar">Stock Qty</th>
											<th class="fit ar">Each</th>
											<th class="fit ar">Dis</th>
											<th class="fit ar">GST</th>
											<th class="fit ar">Total</th>
											<th class="fit">Action</th>
										</tr>
									</thead>
									<tbody id="batteryAmountBody">
										
									</tbody>

								</table>
							</div>
						</div>
						<br/><br/><br/><br/>
						<div class="col-md-9">
							<div class="row">
								<div class="col-md-8">
									<dl>
										<dd>Description :</dd>
										<dt id="description"></dt>
									</dl>
								</div>
								<div class="col-md-3">
									<table class="table">
										<tbody>
											<tr class="row">
												<th class="key">SubTotal :</th>
												<td class="value"><i class="fa fa-inr"></i>
													<span id="subTotal"></span></td>
											</tr>
											<tr class="row">
												<th class="key"><a>Total :</a></th>
												<td class="value"><a><i class="fa fa-inr"></i>
														<span id="total"></span></a></td>
											</tr>
										</tbody>
									</table>
								</div>
									<a id ="AddMoreParts" onload=""
									onclick ="javascript:AddPart();"
									href="javascript:AddPart();">
									Add More Urea
									</a>
							</div>
						</div>
					</div>
				</fieldset>
				<!-- MODEL START -->
				
				<div class="modal fade" id="configureMorePart" role="dialog">
					<div class="modal-dialog modal-md">
						<div class="modal-content">
						<input type="hidden" id="manufacturerMandatory" value="${configuration.manufacturerMandatory }">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4 class="modal-title">Add Urea</h4>
							</div>
								<form action="addMoreUrea.in" method="post" 
								enctype="multipart/form-data" name="formEditPartInventory"
								role="form" class="form-horizontal" novalidate="novalidate">
								
								  <div class="form-horizontal">
									<fieldset>
										<div class="box">
											<input type="hidden" id="NoOfPartsAllowedToAdd" value="50">
											<input type="hidden" id="addMorePartsAtBottom" value="true">
											<div class="box-body">
												<div class="panel panel-success">
													<div class="panel-body">
												<div class="row1" id="grpmanufacturer" class="form-group">
													<label class="L-size control-label" for="manufacurer">Urea Manufacturer :
													<c:if test="${configuration.manufacturerMandatory}">
													<abbr title="required">*</abbr>
													</c:if>
													</label>
													<div class="I-size">
														<input type="hidden" id="ureaManufacturer"
															name="ureaManufacturer" style="width: 100%;" value="0"
															placeholder="Please Enter 2 or more Manufacturer Name" />
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
																onkeypress="return isNumberKey(event,this);"
																onkeyup="javascript:sumthere('quantity', 'unitprice', 'discount', 'tax', 'tatalcost');"
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
																onkeypress="return isNumberKeyQut(event,this);"
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
																onkeypress="return isNumberKeyQut(event,this);"
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
										
											<input type="hidden" name="InvoiceId" value="${invoiceId}">
											<input type="hidden" name="vendorId" value="${ureaInvoice.vendorId}">
											<input type="hidden" name="locationId" value="${ureaInvoice.wareHouseLocation}">
											
											
										
										<!-- <fieldset class="form-actions"> -->
										<div class="row1">
											<div class="col-md-10 col-md-offset-2">
												<button type="submit" class="btn btn-success" onclick = "return validateAddPart();" >Add
													Part</button>
												<a class="btn btn-link"
													href="<c:url value="/showInvoice?Id=${PartInvoice.partInvoiceId}"/>">Cancel</a>
												</div>
											</div>
										<!-- </fieldset> -->
								
									</div>
								</form>
						</div>
					</div>
				</div>
				<!-- MODEL END -->
				<%@ include file="/WEB-INF/view/payment/PaymentDetails.jsp"%>	
			</sec:authorize>
		</div>
		<div class="row">
			<small class="text-muted"><b>Created by :</b> <span id="createdBy"></span></small> | <small class="text-muted"><b>Created
					date: </b> <span id="createdOn"></span></small> | <small
				class="text-muted"><b>Last updated by :</b> <span id="lastupdatedBy"></span></small> | <small
				class="text-muted"><b>Last updated date:</b><span id="lastUpdated"></span></small>
		</div>
	</section>
</div>

<div class="modal fade" id="transferUreaModal" role="dialog">
		<div class="modal-dialog ">
			<!-- Modal content-->
			<div class="modal-content">
					<div class="modal-body">
						<div class="row">
							<h4>Transfered Urea UT-<span id="transferId"></span></h4>
						</div>
					</div>
					<br><br><br>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span id="Close">Close</span>
						</button>
					</div>
			</div>
		</div>
	</div>


<script
	src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>

<script type="text/javascript">
	$(function() {
		$('[data-toggle="popover"]').popover()
	})
</script>
<script
	src="resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"></script>
<script
	src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/ureainventory/addUreaInventory.js"></script><!-- For Model -->
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/ureainventory/showUreaInventory.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/ureainventory/UreaInventoryUtility.js"></script>