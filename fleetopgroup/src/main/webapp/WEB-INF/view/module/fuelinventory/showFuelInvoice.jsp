
<%@ include file="taglib.jsp"%>
<%@page import="java.util.HashMap"%>
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
						href="FuelInventory.in">New Fuel Inventory</a>
					/ <span>View Fuel Details</span>
				</div>
				<div class="pull-right">
					<a onclick="getFuelInvoiceHistory();" class="btn btn-primary btn-sm" href="#">History</a>
					<a onclick="addShortExcessQuantity();" class="btn btn-primary btn-sm" href="#">Update Stock </a>
					<% 
						Collection<GrantedAuthority>  permission = (Collection<GrantedAuthority>)request.getAttribute("permission");
						if(permission.contains(new SimpleGrantedAuthority("DELETE_FUEL_INVENTORY"))) {
						%>
					  		<a id="delete" onclick="deleteFuelInvoice();" class="btn btn-primary btn-sm" href="#">Delete Fuel Invoice</a>
					<% } %>
					<a href="FuelInventory.in">Cancel</a>
				</div>
				
				<div class="col-md-2">
							<div class="input-group">
								<span class="input-group-addon"> <span aria-hidden="true">FI-</span></span>
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
			<sec:authorize access="!hasAuthority('FUEL_INVENTORY')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('FUEL_INVENTORY')">
				<div class="row">
					<div class="col-md-7 col-sm-7 col-xs-7">
						<!-- <div class="pull-left"> -->
						<h4>Fuel Invoice : <span id="fuelInvoiceNumber"></span></h4>
						<input type="hidden" id="fuelInvoiceId" value="${invoiceId}"/>
						<input type="hidden" id="moduleId" value="${invoiceId}"/> 
						<input type="hidden" id="modulePaymentTypeId"/> 
						<input type="hidden" id="moduleIdentifier"/>
						<input type="hidden" id="companyId" value="${companyId}"/>
						<input type="hidden" id="userId" value="${userId}"/>
						<input type="hidden" id="fuel_price" />
						<input type="hidden" id="petrolPumpId" />
						<input type="hidden" id="quantity" />
						<input type="hidden" id="showBalanceStockInFuelInvoice" value="${configuration.showBalanceStockInFuelInvoice}" />
						<input type="hidden" id="showTransferedStatus" value="${configuration.showTransferedStatus}" />
						<input type="hidden" id="balanceStock" />
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
											<th class="key">Discount Type :</th>
											<td class="value" id="discountType"></td>
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
										<tr class="row">
											<th class="key">Invoice Amount:</th>
											<td class="value" id="invoiceAmount"></td>
										</tr>
										
										<c:if test="${configuration.showTransferedStatus}">
										<tr class="row" id="transferedStatusTr">
											<th class="key">Transfered From :</th>
											<td class="value" id="transferedStatus"></td>
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
										<c:if test="${configuration.tallyIntegrationRequired}">
											<tr class="row">
												<th class="key">Tally Company :</th>
												<td class="value" id="tallyCompany"></td>
											</tr>
										</c:if>
										
											<c:if test="${configuration.showTransferedStatus}">
										<tr class="row" id="transferedQtyTr">
											<th class="key">Transfered Qunatity :</th>
											<td class="value" id="transferedQty"></td>
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
											<th class="fit ar">Qty</th>
											<c:if test="${configuration.showBalanceStockInFuelInvoice}">
												<th class="fit ar">BalanceQuantity</th>
												<!-- <th class="fit ar">Updated Stock</th>
												<th class="fit ar">Stock Type</th> -->
											</c:if>
											<th class="fit ar">Each</th>
											<th class="fit ar">Dis</th>
											<th class="fit ar">GST</th>
											<th class="fit ar">Total</th>
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
								
							</div>
						</div>
					</div>
				</fieldset>
				<!-- MODEL START -->
				
				<div class="modal fade" id="configureMorePart" role="dialog">
					<div class="modal-dialog modal-md">
						<div class="modal-content">
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
													<label class="L-size control-label" for="manufacurer">Urea Manufacturer :<abbr title="required">*</abbr>
													</label>
													<div class="I-size">
														<input type="hidden" id="ureaManufacturer"
															name="ureaManufacturer" style="width: 100%;"
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

<div class="modal fade" id="fuelInvoiceHistoryModal">
		<div class="modal-dialog" style="width: 80%;">
			<!-- Modal content-->
			<div class="modal-content">
					<div class="modal-body">
						<div class="row">
							<h4>Fuel Invoice History</h4>
						</div>
					</div>
					<br><br><div class="table-responsive">
					<table class="table">
					<thead>
						<tr>
							<th class="fit ar">No</th>
							<th class="fit ar">Balance Quantity</th>
							<th class="fit ar">Status</th>
							<th class="fit ar">Remark</th>
						</tr>
					</thead>
					<tbody id="fuelInvoiceHistoryTable">
					
					</tbody>

					</table>
				</div><br>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span id="Close">Close</span>
						</button>
					</div>
			</div>
		</div>
	</div>

<div class="modal fade" id="stockInvoiceModal" role="dialog">
	<div class="modal-dialog" style="width: 750px">
		<!-- Modal content-->
		<div class="modal-content">

			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="JobType">
					Fuel Invoice Stock <span id="balanceInvoiceStock"></span>
				</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="L-size btn-group" id="status" data-toggle="buttons">
						<label class="btn btn-default btn-on btn-mg active"
							style="width: 25%;"> <input type="radio" value="1"
							id="activeStatusId" name="stockStatusName" checked="checked">SHORT
						</label> <label class="btn btn-default btn-off btn-mg" style="width: 35%;">
							<input type="radio" value="2" id="inactiveStatusId"
							name="stockStatusName">EXCESS
						</label>
					</div>
					<div class="I-size">
						<input type="number" id="shrotExcessQuantity"
							onkeypress="return isNumberKeyWithDecimal(event,this.id);"
							class="form-text">
					</div>
				</div>

				<br />

				<div class="row">
					<label class="L-size control-label" id="Type">Remark :</label>
					<div class="I-size">
						<input type="text" class="form-text" id="remark" maxlength="249"
							placeholder="Enter description" />
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<input type="button" class="btn btn-primary" value="submit" onclick="updateFuelInvoiceStock();">
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
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/fuelinventory/addFuelInventory.js"></script><!-- For Model -->
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/fuelinventory/showFuelInventory.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/fuelinventory/FuelInventoryUtility.js"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script> 