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
						href="ClothInventory.in">New Upholstery Inventory</a>
					/ <span>View Upholstery Details</span>
				</div>
				<div class="pull-right">
					<% 
						Collection<GrantedAuthority>  permission = (Collection<GrantedAuthority>)request.getAttribute("permissions");
						if(permission.contains(new SimpleGrantedAuthority("DELETE_CLOTH_INVENTORY"))) {
						%>
					  		<a id="delete" onclick="deleteClothInvoice();" class="btn btn-primary btn-sm" href="#">Delete Upholstery Invoice</a>
					<% } %>
					<a href="ClothInventory.in">Cancel</a>
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
			<sec:authorize access="!hasAuthority('VIEW_CLOTH_TYPES_INVENTORY')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_CLOTH_TYPES_INVENTORY')">
				<div class="row">
					<div class="col-md-7 col-sm-7 col-xs-7">
						<!-- <div class="pull-left"> -->
						<h4>Upholstery Invoice <span id="clothInvoiceNumber"></span></h4>
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
											<th class="key">Upholstery Stock Type :</th>
											<td class="value" id="clothTypeId"></td>
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
											<th class="key">Roundup Amount</th>
											<td class="value" id="invoiceAmount"></td>
										</tr>
										
										<tr class="row">
											<th class="key">Invoice Amount:</th>
											<td class="value" id="totalinvoiceamount"></td>
										</tr>
										</c:if>
										
										
										
										<c:if test="${showSubLocation}">
											<tr class="row">
												<th class="key">Sub location:</th>
												<td class="value" id="subLocation"></td>
											</tr>
										</c:if>
										<tr class="row">
											<th class="key">Vendor Payment Status :</th>
											<td class="value" id="vendorPaymentStatus"></td>
										</tr>
										<tr class="row">
											<th class="key">Approval Number:</th>
											<td class="value" id="approvalNumber"></td>
										</tr>
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
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				<fieldset>
				
				<div class="modal fade" id="saveBatterySerialNumber" role="dialog">
						<div class="modal-dialog modal-lg">
							<div class="modal-content">
								<form 
									name="vehicleType">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title" id="VehicleType">Save Battery Serial
											No</h4>
									</div>
									<div class="modal-body" id="modelBody">
										<br />
									</div>
									<div class="modal-footer">
										<button type="submit" id="serialNoSubmit" class="btn btn-primary">
											Save
										</button>
										<button type="button" class="btn btn-default"
											data-dismiss="modal">
											<span id="Close">Close</span>
										</button>
									</div>
								</form>
							</div>

						</div>
					</div>
				
					<!-- Modal -->
					<div class="modal fade" id="editTyreSerialNumber" role="dialog">
						<div class="modal-dialog">

							<!-- Modal content-->
							<div class="modal-content">
								<form action="updateTyreSerialNo.in" method="post"
									name="vehicleType" onsubmit="return validateType()">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title" id="VehicleType">Edit Tyre Serial
											No</h4>
									</div>
									<div class="modal-body">
										<div class="row">
											<label class="L-size control-label" id="Type">Serial
												No :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="hidden" class="form-text" value=""
													required="required" name="Id" id="tyreId" />
												<!-- invoice Id -->
												<input type="hidden" class="form-text" value=""
													required="required" name="InvoiceID" id="tyreInvoiceId" />
												<!-- Tyre Serial Num -->
												<input type="text" class="form-text" value=""
													id="TyreSerialName" required="required" name="TyreSerialNo"
													maxlength="50" placeholder="Enter Tyre Serial Number" /> <label
													id="errorvType" class="error"></label>
											</div>
										</div>
										<br />
									</div>
									<div class="modal-footer">
										<button type="submit" class="btn btn-primary">
											<span id="Save">Save</span>
										</button>
										<button type="button" class="btn btn-default"
											data-dismiss="modal">
											<span id="Close">Close</span>
										</button>
									</div>
								</form>
							</div>

						</div>
					</div>

					<div class="row">
						<div class="col-md-11 col-sm-11 col-xs-11">
							<div class="table-responsive">
								<table class="table" id="dataTable" style="display: none;">
									<thead>
										<tr class="breadcrumb">
											<th class="fit"></th>
											<th class="fit ar">Upholstery Type</th>
											<th class="fit ar">Qty</th>
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
							</div>
						</div>
					</div>
				</fieldset>
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
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/clothinventory/showClothInventory.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/clothinventory/clothUtility.js"></script>