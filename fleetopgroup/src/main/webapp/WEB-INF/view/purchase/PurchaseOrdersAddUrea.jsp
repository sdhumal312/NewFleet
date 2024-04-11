<%@ include file="taglib.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<style>
 .stock-color-blue{
 		color: blue;
 } 
 .stock-color-red{
 		color: red;
 } 
</style>
<div class="content-wrapper">
  <section class="content-header">
	<div class="box">
		<div class="box-body">
			<div class="row">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> / 
					
					<a href="<c:url value="/newPurchaseOrders/1.in"/>">REQUISITION</a> /
					<c:if test="${poConfiguration.requisitionApproved}">
						<a href="<c:url value="/PurchaseOrders_APPROVED/1.in"/>">REQUISITION APPROVED</a>/
					</c:if>
					<a href="<c:url value="/PurchaseOrders_ORDERED/1.in"/>">ORDERED</a> /
					<a href="<c:url value="/PurchaseOrders_RECEIVED/1.in"/>">RECEIVED</a> /
					<a href="<c:url value="/PurchaseOrders_COMPLETED/1.in"/>">COMPLETED</a> /
					<span>Add Purchase Order</span>
				</div>
					<div class="col-md-off-5">
						<div class="col-md-3">
							<form action="<c:url value="/searchPurchaseOrderShow.in"/>" method="post">
								<div class="input-group">
									<span class="input-group-addon"> 
										<span aria-hidden="true">PO-</span>
									</span> 
									<input class="form-text" id="vehicle_registrationNumber" name="Search" type="number" min="1" required="required" placeholder="Po-NO eg:74654">
									<span class="input-group-btn">
										<button type="submit" name="search" id="search-btn" class="btn btn-success btn-sm">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
							</form>
						</div>
					</div>
					<div class="pull-right">
						<c:if test="${poConfiguration.makeApproval}">
							<button class="btn btn-info btn-sm" onclick="makeApproval();">
								Make Approval
							</button>
						</c:if>
						<c:if test="${configuration.VendorInputFieldInPOAddPart}">
							<button class="btn btn-danger btn-sm" onclick="addVendorInPO();">
								Add Vendor
							</button>
						</c:if>
						<c:if test="${poConfiguration.tallyIntegrationRequired}">
							<button class="btn btn-warning btn-sm" onclick="addTallyCompanyInPO();">
								Add Tally Company
							</button>
						</c:if>
						<a class="btn btn-success" href="<c:url value="/newPurchaseOrders/1.in"/>">Cancel</a>
					</div>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="box">
			<sec:authorize access="!hasAuthority('VIEW_PURCHASE')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_PURCHASE')">
				<div class="row">
					<div class="col-md-5">
						<h4>Purchase Order ${PurchaseOrder.purchaseorder_Number }</h4>
						<h4 align="center">
							<a href="ShowVendor.in?vendorId=${PurchaseOrder.purchaseorder_vendor_id}" data-toggle="tip" data-original-title="Click Vendor Info"> 
								<c:out value="${PurchaseOrder.purchaseorder_vendor_name}" /><br>
								<c:out value="${PurchaseOrder.purchaseorder_vendor_location}"/>
							</a>
						</h4>
					</div>
					<div class="col-md-6">
						<div class="row">
							<input type="hidden" id="statues" name="statues" value="${PurchaseOrder.purchaseorder_status}">
							<input type="hidden" id="previousRate" name="previousRate" value="${poConfiguration.previousPOpartRate}">
							
							<input type="hidden" id="fromTransfer"  value="${fromTransfer}">
							<input type="hidden" id="partFound"  value="${partFound}">
								<input type="hidden" id="transactionId" value="${rTransactionId}">
								<input type="hidden" id="transactionName"  value="${rTransactionName}">
								<input type="hidden" id="rQuantity" value="${rQuantity}">
							<div id="work-order-statuses">
								<div id="work-order-statuses">
									<a data-disable-with="..." data-method="post" data-remote="true" rel="nofollow"> 
										<span id="status-open" class="status-led"> 
											<i class="fa fa-circle"></i>
											<div class="status-text" style="color: black;">REQUISITION</div>
										</span>
									</a> 
									<a data-method="post" data-remote="true" rel="nofollow"> 
										<span id="status-in-progress" class="status-led"> 
											<i class="fa fa-circle"></i>
											<div class="status-text" style="color: black;">ORDERED</div>
										</span>
									</a> 
									<a data-disable-with="..." data-method="post" data-remote="true" rel="nofollow">
										<span id="status-on-hold" class="status-led"> 
											<i class="fa fa-circle"></i>
											<div class="status-text" style="color: black;">RECEIVED</div>
										</span> 
									</a>
									<button type="button" class="btn btn-default" data-toggle="modal" data-target="#addPurchaseOrderDocument" data-whatever="@mdo">
										<i class="fa fa-upload"></i> Upload
									</button>

									<sec:authorize access="hasAuthority('DOWNLOND_PURCHASE')">
										<a style="width: 10%" href="PrintPurchaseOrder?id=${PurchaseOrder.purchaseorder_id}" target="_blank" class="btn btn-default ">
											<i class="fa fa-print"></i> Print
										</a>
									</sec:authorize>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-3">
						<dl class="dl-horizontal">
							<dt>PO-Type :</dt>
							<dd>
								<c:out value="${PurchaseOrder.purchaseorder_type}" />
							</dd>
							<dt>Date Opened :</dt>
							<dd>
								<c:out value="${PurchaseOrder.purchaseorder_created_on}" />
							</dd>
							<dt>Date Required :</dt>
							<dd>
								<c:out value="${PurchaseOrder.purchaseorder_requied_on}" />
							</dd>
							<c:if test="${poConfiguration.showQuoteNumber}">
							<dt>Quote No :</dt>
							<dd>
								<c:out value="${PurchaseOrder.purchaseorder_quotenumber}" />
							</dd>
							</c:if>
							<c:if test="${poConfiguration.WorkOrderNo}">
							<dt>WO / Indent No :</dt>
							<dd>
								<c:out value="${PurchaseOrder.purchaseorder_workordernumber}" /> /
								<c:out value="${PurchaseOrder.purchaseorder_indentno}" />
							</dd>
							</c:if>
							<dt>Terms :</dt>
							<dd>
								<c:out value="${PurchaseOrder.purchaseorder_terms}" />
							</dd>
						</dl>
					</div>
					<div class="col-md-4">
						<dl class="dl-horizontal">
							<dt>Vendor :</dt>
							<dd>
								<c:out value="${PurchaseOrder.purchaseorder_vendor_name}" />
							</dd>
							<dt>Vendor GST :</dt>
							<dd>
								<c:out value="${PurchaseOrder.vendorGstNumber}" />
							</dd>
							<dt>Buyer Name:</dt>
							<dd>
								<c:out value="${PurchaseOrder.purchaseorder_buyer}" />
							</dd>
							<dt>Buyer Address :</dt>
							<dd>
								<c:out value="${PurchaseOrder.purchaseorder_buyeraddress}" />
							</dd>
							<c:if test="${poConfiguration.showBuyerGstNumber}">
								<dt>Buyer GST :</dt>
								<dd>
									<c:out value="${PurchaseOrder.buyerGstNumber}" />
								</dd>
							</c:if>
							<dt>Ship via :</dt>
							<dd>
								<c:out value="${PurchaseOrder.purchaseorder_shipvia}" />
							</dd>
							<c:if test="${poConfiguration.tallyIntegrationRequired}">
								<dt>Tally Company :</dt>
								<dd>
									<c:out value="${PurchaseOrder.tallyCompanyName}" />
								</dd>
							</c:if>	
						</dl>
					</div>
					<div class="col-md-3">
						<dl class="dl-horizontal">
							<dt>Ship To:</dt>
							<dd>
								<c:out value="${PurchaseOrder.purchaseorder_shiplocation}" />
							</dd>
							<dt>Ship Address:</dt>
							<dd>
								<c:out value="${PurchaseOrder.purchaseorder_shiplocation_address}" />
							</dd>
							<dt>Contact:</dt>
							<dd>
								<c:out value="${PurchaseOrder.purchaseorder_shiplocation_contact} - " />
								<c:out value="${PurchaseOrder.purchaseorder_shiplocation_mobile}" />
							</dd>
							<dt>Notes :</dt>
							<dd style="word-wrap: break-word">
								<c:out value="${PurchaseOrder.purchaseorder_notes}" />
							</dd>
						</dl>
					</div>
				</div>
				<fieldset>
					<div class="modal fade" id="addPurchaseOrderDocument" role="dialog">
						<div class="modal-dialog">
							<div class="modal-content">
								<form method="post" action="uploadPurchaseOrderDocument.in" enctype="multipart/form-data">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal" aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title">PurchaseOrder Document</h4>
									</div>
									<div class="modal-body">
										<div class="form-horizontal ">
											<div class="row">
												<div class="L-size">
													<label class="L-size control-label"> Document Name:<abbr title="required">*</abbr>
													</label>
												</div>
												<div class="I-size">
													<select name="purchaseorder_document" class="form-text">
														<option value="quotation">Quotation</option>
														<option value="invoice">Invoice</option>
													</select>
												</div>
											</div>
											<br>
											<div class="row">
												<div class="L-size">
													<label class="L-size control-label"> Browse:<abbr title="required">*</abbr>
													</label>
												</div>
												<div class="I-size">
													<input type="file" accept="image/png, image/jpeg, image/gif, application/pdf "
														name="input-file-preview" required="required" />
												</div>
											</div>
											<br />
										</div>
									</div>
									<div class="modal-footer">
										<button type="submit" class="btn btn-primary">
											<span>Upload</span>
										</button>
										<button type="button" class="btn btn-default"
											data-dismiss="modal">
											<span>Cancel</span>
										</button>
									</div>
								</form>
							</div>
						</div>
					</div>
					<div class="row">
						<c:if test="${deleteFristParts}">
							<div class="alert alert-danger">
								<button class="close" data-dismiss="alert" type="button">x</button>
								Should be Delete First Task Parts and Technician
							</div>
						</c:if>
						<div class="col-md-11">
							<div class="table-responsive">
								<input type="hidden" id="purchaseOrderId" value="${PurchaseOrder.purchaseorder_id}">
								<input type="hidden" id="purchaseOrderTypeId" value="${PurchaseOrder.purchaseorder_typeId}">
								<input type="hidden" id="ureaInvoiceToDetailsId">
								<input type="hidden" id="shipLocationId" value="${PurchaseOrder.purchaseorder_shiplocation_id}">
								<input type="hidden" id="subTotalPOCost" value="${PurchaseOrder.purchaseorder_subtotal_cost}">
								<input type="hidden" id="VendorInputFieldInPOAddPart" value="${configuration.VendorInputFieldInPOAddPart}">
								<input type="hidden" id="updatedVendorId" value="${PurchaseOrder.purchaseorder_vendor_id}">
								<input type="hidden" id="updatedVendorName" value="${PurchaseOrder.purchaseorder_vendor_name}">
								<input type="hidden" id="showTallyCompany" value="${poConfiguration.tallyIntegrationRequired}"/>
								<input type="hidden" id="updatedTallyCompanyId" value="${PurchaseOrder.tallyCompanyId}">
								<input type="hidden" id="purchaseorderToPartId" >
								<input type="hidden" id="unique-one-time-token" name="unique-one-time-token" value="${accessToken}">
								<input type="hidden" id="validateDoublePost" name="validateDoublePost" value="true">
								<input type="hidden" id="addVehicleDetailsInPOAddPart"  value="${configuration.addVehicleDetailsInPOAddPart}">
								<input type="hidden" id="editPartDetails"  value="${poConfiguration.editPartDetails}">
								<input type="hidden" id="purcahseOrderTotal">
								<input type="hidden" id="oldQuantity" >
								<input type="hidden" id="oldUnitPrice" >
								<input type="hidden" id="oldGst" >
								<input type="hidden" id="makeApproval" value="${poConfiguration.makeApproval}">
								<input type="hidden" id="companyId" value="${companyId}">
								<input type="hidden" id="pendingApproval" value="false">
								<input type="hidden" id="approvedApproval" value="false">
								<input type="hidden" id="rejectedApproval" value="false">
								<input type="hidden" id="transferApproval" value="false">
								<table class="table">
									<thead>
										<tr class="breadcrumb">
											<th class="fit"></th>
											<th>Urea</th>
											<th class="fit ar">Qty</th>
											<th class="fit ar">Each</th>
											<th class="fit ar">Dis</th>
											<th class="fit ar">GST</th>
											<th class="fit ar">Total</th>
											<c:if test="${poConfiguration.makeApproval}">
												<th class="fit">Status</th>
											</c:if>
											<th class="fit">Actions</th>
										</tr>
									</thead>
									<tbody id="ureaPurchaseOrdersDetails">
									
									</tbody>
									<tfoot>
										<tr class="breadcrumb">
											<th colspan="8">
												<!-- <a data-toggle="modal" data-target="#addUrea" data-whatever="@mdo" >Add Urea</a> -->
												<a id="showAddUreaModal">Add Urea</a>		
											</th>
										</tr>
									</tfoot>
								</table>
							</div>
							<c:if test="${poConfiguration.makeApproval}">
							<div id="rejectedPart" class="table-responsive">
							<h4>
								<span class="label label-danger">Rejected Part</span>
							</h4>
								<table class="table">
									<thead>
										<tr class="breadcrumb">
											<th class="fit"></th>
											<th>Urea</th>
											<th class="fit ar">Qty</th>
											<th class="fit ar">Each</th>
											<th class="fit ar">Dis</th>
											<th class="fit ar">GST</th>
											<th class="fit ar">Total</th>
											<th class="fit">Actions</th>
										</tr>
									</thead>
									<tbody id="ureaPurchaseRejectDetails">
									
									</tbody>
									
								</table>
							</div>
							<div id="transferPart" class="table-responsive">
								<h4>
									<span class="label label-danger">Branch Transfer</span>
								</h4>
								<table class="table">
									<thead>
										<tr class="breadcrumb">
											<th class="fit"></th>
											<th>Urea</th>
											<th class="fit ar">Qty</th>
											<th class="fit ar">Each</th>
											<th class="fit ar">Dis</th>
											<th class="fit ar">GST</th>
											<th class="fit ar">Total</th>
											<th class="fit">Actions</th>
										</tr>
									</thead>
									<tbody id="ureaPurchaseTransferDetails">
									
									</tbody>
									
								</table>
							</div>
							</c:if>
						</div>
						<div class="modal fade" id="addUrea" role="dialog">
							<div class="modal-dialog">
								<!-- Modal content-->
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title">Add Urea </h4>
									</div>
									<div class="modal-body">
										<div class="row1" >
											<label class="L-size control-label" for="manufacurer">Urea Manufacturer
											</label>
											<div class="I-size">
												<input type="hidden" id="ureaManufacturerId" style="width: 100%;"
													placeholder="Please Enter 2 or more  Name" />
											</div>
										</div>
										<br>
										<br>
										<div class="row1" style="padding-top: 1%;">
											<div class=" col-sm-9 col-md-7" >
												<label id="stockName" class="control-label stock-color-red" for="manufacurer"><c:out value="${PurchaseOrder.purchaseorder_shiplocation}" /> Stock Details  </label>
											</div>
											<div class=" col-sm-9 col-md-2" >
												<div class="I-size">
													<input type="text" readonly="readonly" class="form-text" id="stockId" style="width: 100%;" />
												</div>
											</div>
										</div>
										<br>
										<div class="row1" style="padding-top: 1%;">
											<div class=" col-sm-9 col-md-7" >
												<label id="otherStockName" class="control-label stock-color-red" for="manufacurer">Other Locations Stock Details  </label>
											</div>
											<div class=" col-sm-9 col-md-2" >
												<div class="I-size">
														<input type="text" readonly="readonly" class="form-text" id="otherLocationstock" style="width: 100%;" />
												</div>
											</div>
										</div>
									
									<div class="row" style="padding-top: 4%;">
											<label class="control-label"></label>
											<div class=" col-md-9">
												<div class="col-md-1">
													<label class="control-label">Liter</label>
												</div>
												<div class="col-md-2">
													<label class="control-label">Unit Price</label>
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
										<div class="row">
											<label class="control-label" for="quantity"> </label>
											<div class="col-md-9">
												<div class="col-md-1">
													<input type="text" class="form-text"
														name="unitprice_many" id="quantity" maxlength="7"
														min="0.0" placeholder="Liter" required="required" 
														data-toggle="tip" data-original-title="enter Unit Price"
														onkeypress="return isNumberKeyWithDecimal(event,this.id)" onpaste="return false"
														onkeyup="javascript:sumthere('quantity','unitprice', 'discount', 'tax', 'totalCost');"
														ondrop="return false;"> <span id="unitpriceIcon"
														class=""></span>
													<div id="unitpriceErrorMsg" class="text-danger"></div>
												</div>
												<div class="col-md-2">
													<input type="text" class="form-text"
														name="unitprice_many" id="unitprice" maxlength="5"
														min="0.0" placeholder="Unit Cost" required="required"
														data-toggle="tip" data-original-title="enter Unit Price" 
														onkeypress="return isNumberKeyWithDecimal(event,id);" onpaste="return false"
														onkeyup="javascript:sumthere('quantity','unitprice', 'discount', 'tax', 'totalCost');"
														ondrop="return false;"> <span id="unitpriceIcon"
														class=""></span>
													<div id="unitpriceErrorMsg" class="text-danger"></div>
												</div>

												<div class="col-md-1">
													<input type="text" class="form-text" name="discount_many"
														min="0.0" id="discount" maxlength="5"
														placeholder="Discount" required="required" 
														data-toggle="tip" data-original-title="enter Discount"
														onkeypress="return isNumberKeyWithDecimal(event,this.id)" onpaste="return false"
														onkeyup="javascript:sumthere('quantity','unitprice', 'discount', 'tax', 'totalCost');"
														ondrop="return false;"><span id="discountIcon"
														class=""></span>
													<div id="discountErrorMsg" class="text-danger"></div>
												</div>
												<div class="col-md-1">
													<input type="text" class="form-text" name="tax_many"
														id="tax" maxlength="5" min="0.0" placeholder="GST"
														required="required" data-toggle="tip" 
														data-original-title="enter GST" onpaste="return false"
														onkeypress="return isNumberKeyWithDecimal(event,this.id)"
														onkeyup="javascript:sumthere('quantity','unitprice', 'discount', 'tax', 'totalCost');"
														ondrop="return false;"><span id="taxIcon"
														class=""></span>
													<div id="taxErrorMsg" class="text-danger"></div>
												</div>
												<div class="col-md-2">
													<input type="text" class="form-text" maxlength="8"
														value="0.0" min="0.0" name="totalCost" id="totalCost" readonly="readonly"
														data-toggle="tip" data-original-title="Total Cost"
														onkeypress="return isNumberKey(event,this);"
														ondrop="return false;">
												</div>
											</div>
										</div>
												<div class="help-block" id="lastPOUreaDetails">

															</div>
										<!-- <div class="row1">
											<div class="input_fields_wrap">
												<button class="add_field_button btn btn-info"
													data-toggle="tip"
													data-original-title="Click add one more part">
													<i class="fa fa-plus"></i> Add More
												</button>
											</div>
										</div> -->
									</div>
									<br>
									<div class="modal-footer">
										<button type="submit"  id="saveUreaToPurchaseOrder" class="btn btn-primary">
											<span><spring:message code="label.master.Save" /></span>
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
						<div class="col-md-11">
							<div class="row">
								<div class="col-md-7 ">
									<sec:authorize access="hasAuthority('ORDERED_PURCHASE')">
										<div id="sentToPurchase"  class="form-horizontal hide">
											<div class="row1">
												<label class="L-size control-label" for="issue_description"> Remarks :</label>
												<div class="I-size">
													<textarea class="text optional form-text" 
														id="purchaseOrderRemark" name="purchaseorder_orderd_remark"
														rows="3" maxlength="500">
		                                 			</textarea>
		                                 			<c:if test="${poConfiguration.showSaveRemarkButton}">
				                                			 <input style="margin-top:5px;" class="btn btn-success" 
				                                			 type="button" value="SaveRemark" id="saveRemark" onclick="SavePoRemark()">
				                                	</c:if>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Advance Amount :<abbr title="required">*</abbr> </label>
												<div class="col-md-3">
													<input type="text" class="form-text" onpaste="return false" required="required" id="purchaseOrderAdvanceCost"
														onkeypress="return isNumberKeyWithDecimal(event,this.id)" value="0" ondrop="return false;"> 
														<label class="error" id="errorAmount" style="display: none"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Order By: <abbr title="required">*</abbr> </label>
												<div class="I-size">
													<input type="text" class="form-text" readonly="readonly" required="required" value="${CreatedBy}" ondrop="return false;"> 
													<label class="error" id="errorOrderdby" style="display: none"> </label>
												 	<input type="hidden" name="purchaseorder_orderdbyId" value="${CreatedById}" />
												</div>
											</div>
											<fieldset class="form-actions">
												<div class="row">
													<div class="col-md-10 col-md-offset-2">
														<!-- <input class="btn btn-success" type="submit" id="sentToPurchaseOrder" value="Sent PurchaseOrder"> --> 
														<button type="submit"  id="sentToPurchaseOrder" class="btn btn-success">
															<span><spring:message code="Sent PurchaseOrder" /></span>
														</button>
														<a class="btn btn-link" href="<c:url value="/newPurchaseOrders/1.in"/>">Cancel</a>
													</div>
												</div>
											</fieldset>
										</div>
									</sec:authorize>
								</div>
								<div class="col-md-3">
									<table class="table">
										<tbody>
											<c:if test="${poConfiguration.eachTotalCost}">
												<tr class="row">
													<th class="key">SubTotal :</th>
													<td class="value"><i class="fa fa-inr"></i>
														${totalEachCost}</td>
												</tr>
											</c:if>
											<c:if test="${!poConfiguration.eachTotalCost}">
												<tr class="row">
													<th class="key">SubTotal :</th>
													<td class="value"><i class="fa fa-inr"></i>
														${PurchaseOrder.purchaseorder_subtotal_cost}</td>
												</tr>
											</c:if>
											
											<tr class="row">
												<th class="key">Freight :</th>
												<td class="value"><a id="freight" data-remote="true"
													href="javascript:toggle2Freight('Purchase_order-freight','freight');">
														<i class="fa fa-inr"></i>
												</a> ${PurchaseOrder.purchaseorder_freight}


													<div class="popup-edit hide-on-escape"
														id="Purchase_order-freight">
														<form accept-charset="UTF-8"
															action="PurchaseOrdersUpdate_freight.in" method="post"
															novalidate="novalidate">

															<div class="row1">
																<label class="control-label">Freight Cost</label>
																<div class="input float optional work_order_tax_cost">
																	<input name="purchaseorder_id" type="hidden"
																		required="required"
																		value="${PurchaseOrder.purchaseorder_id}"> <input
																		class="form-text" name="purchaseorder_freight"
																		type="number" required="required"
																		value="${PurchaseOrder.purchaseorder_freight}">
																</div>
															</div>
															<div class="row1">
																<input class="btn btn-success" name="commit"
																	type="submit" value="Apply">
															</div>
														</form>

													</div></td>
											</tr>
											
											<c:if test="${poConfiguration.totalGstCost}">
												<tr class="row">
													<th class="key">GST Cost :</th>
													<td class="value"><i class="fa fa-inr"></i>
													<fmt:formatNumber type="number" pattern="#.##" value="${PurchaseOrder.purchaseorder_totaltax_cost}" />
													</td>
												</tr>
											</c:if>
											<c:if test="${!poConfiguration.totalGstCost}">
											<tr class="row">
												<th class="key">GST cost :</th>
												<td class="value"><a id="tax_cost" data-remote="true"
													href="javascript:toggle2Tax('work_order-tax_cost','tax_cost');">
														<i class="fa fa-inr"></i>
												</a> ${PurchaseOrder.purchaseorder_totaltax_cost}


													<div class="popup-edit hide-on-escape"
														id="work_order-tax_cost">
														<form accept-charset="UTF-8"
															action="PurchaseOrdersUpdate_Taxcost.in" method="post"
															novalidate="novalidate">
															
															<div class="row1">
																<label class="control-label">GST Cost</label>
																<div class="input float optional work_order_tax_cost">
																	<input name="purchaseorder_id" type="hidden"
																		required="required"
																		value="${PurchaseOrder.purchaseorder_id}"> <input
																		class="form-text" required="required"
																		name="purchaseorder_totaltax_cost" type="number"
																		value="${PurchaseOrder.purchaseorder_totaltax_cost}">
																</div>
															</div>
															<div class="row1">
																<input class="btn btn-success" name="commit"
																	type="submit" value="Apply">
															</div>
														</form>

													</div></td>
											</tr>
											</c:if>
											<c:if test="${poConfiguration.totalDiscountCost}">
												<tr class="row">
													<th class="key">Discount cost :</th>
													<td id="totalDiscountCost" class="value"><i class="fa fa-inr"></i>
														${TotalDiscountCost}</td>
												</tr>
											</c:if>
											<c:if test="${poConfiguration.finalTotalCost}">
											<tr class="row">
												<th class="key"><a>Total :</a></th>
												<td class="value"><a><i class="fa fa-inr"></i>
														${finalCost}</a></td>
											</tr>
											</c:if>
											<c:if test="${!poConfiguration.finalTotalCost}">
											<tr class="row">
												<th class="key"><a>Total :</a></th>
												<td class="value"><a><i class="fa fa-inr"></i>
														${PurchaseOrder.purchaseorder_totalcost}</a></td>
											</tr>
											</c:if>
											
											
											<tr class="row">
												<th class="key">Advance Paid :</th>
												<td class="value"><i class="fa fa-inr"></i>
													${PurchaseOrder.purchaseorder_advancecost}</td>
											</tr>
											<c:if test="${poConfiguration.finalBalanceCost}">
											<tr class="row">
												<th class="key">Balance :</th>
												<td class="value"><i class="fa fa-inr"></i>
													${balanceCost}</td>
											</tr>
											</c:if>
											<c:if test="${!poConfiguration.finalBalanceCost}">
											<tr class="row">
												<th class="key">Balance :</th>
												<td class="value"><i class="fa fa-inr"></i>
													${PurchaseOrder.purchaseorder_balancecost}</td>
											</tr>
											</c:if>
											
										</tbody>
									</table>
								</div>
								<!-- <table class="table">
									<tfoot>
										<tr class="breadcrumb">
											<th colspan="6"><a href=""><i class="fa fa-plus"></i>
											</a></th>
										</tr>
									</tfoot>
								</table> -->
							</div>
						</div>
					</div>
				</fieldset>
			</sec:authorize>
		</div>
		<div class="row">
			<small class="text-muted"><b>Created by :</b>  <c:out value="${PurchaseOrder.createdBy}" /></small> | 
			<small class="text-muted"><b>Created date: </b> <c:out value="${PurchaseOrder.created}" /></small> | 
			<small class="text-muted"><b>Last updated by :</b> <c:out value="${PurchaseOrder.lastModifiedBy}" /></small> | 
			<small class="text-muted"><b>Last updated date:</b> <c:out value="${PurchaseOrder.lastupdated}" /></small>
		</div>
	</section>
</div>

<div class="modal fade" id="makeApprovalMoadal" role="dialog">
	<div class="modal-dialog" style="width: 95%;">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="JobType">Make Approval</h4>
			</div>
			<div class="modal-body">
				<div class="box">
					<div class="box-body">
						<div class="table-bordered table-responsive text-center">
					<table class="table table-bordered" style="border: 1px solid #ddd !important;">
					<thead>
						<tr class="breadcrumb">
							<th>Urea</th>
							<th class="fit ar">Request Quantity</th>
							<th class="fit ar" style="width: 10%;">Ordered Quantity</th>
							<th class="fit ar" style="width: 10%;">Total Cost</th>
							<th class="fit ar">Vendor</th>
							<th class="fit ar" style="text-align: center;">Action</th>
							<th class="fit ar">Remark</th>  
						</tr>
					</thead>
					<tbody id="makeApprovalTable">
						
					</tbody>

					</table>
				</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="submit" class="btn btn-success" onclick="createPurchaseOrderPartApproval();" > Complete Approval
				</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<span id="Close"><spring:message code="label.master.Close" /></span>
				</button>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="locationPartDetailsModal" role="dialog">
	<div class="modal-dialog" style="width: 90%;">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<div class="pull-left">
					<h4>Location Quantity</h4>
				</div>
				<input type="hidden" id="purchaseOrderToPartId" >
			</div>
			<div class="modal-body">
				<div class="box">
					<div class="box-body">
						<div class="row1" style="padding-top: 1%;">
											<div class=" col-sm-9 col-md-7" >
												<label id="stockName" class="control-label stock-color-red" for="manufacurer"><c:out value="${PurchaseOrder.purchaseorder_shiplocation}" /> Stock Details  </label>
											</div>
											<div class=" col-sm-9 col-md-2" >
												<div class="I-size">
													<input type="text" readonly="readonly" class="form-text" id="approvalStockId" style="width: 100%;" />
												</div>
											</div>
										</div>
										<br>
										<div class="row1" style="padding-top: 1%;">
											<div class=" col-sm-9 col-md-7" >
												<label id="otherStockName" class="control-label stock-color-red" for="manufacurer">Other Locations Stock Details  </label>
											</div>
											<div class=" col-sm-9 col-md-2" >
												<div class="I-size">
														<input type="text" readonly="readonly" class="form-text" id="approvalOtherLocationstock" style="width: 100%;" />
												</div>
											</div>
										</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="submit" class="btn btn-success" id="continuePO" data-dismiss="modal" > Close
				</button>
				
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="vendorDetails" role="dialog">
	<div class="modal-dialog" style="width: 650px;">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="JobType">Vendor Details</h4>
			</div>
			<div class="modal-body">
				<div class="box">
					<div class="box-body">
						<div class="row1" id="grpselectVendor" class="form-group">
							<label class="L-size control-label" for="selectVendor">Vendor
								<abbr title="required">*</abbr>
							</label>
							<div class="I-size" >
								<input type="hidden" id="allTypeOfVendorId" style="width: 100%;"
									required="required" placeholder="Please Select Vendor Name" />
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="submit" id="saveVendor" class="btn btn-primary"> Update Vendor
				</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<span id="Close"><spring:message code="label.master.Close" /></span>
				</button>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="tallyCompanyDetails" role="dialog">
	<div class="modal-dialog" style="width: 650px;">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="JobType">Tally Company Details</h4>
			</div>
			<div class="modal-body">
				<div class="box">
					<div class="box-body">
						<div class="row1" id="grpmanufacturer" class="form-group">
						<label class="L-size control-label" for="manufacurer">Tally Company Name :<abbr title="required">*</abbr></label>
							<div class="I-size">
								<input type="hidden" id="tallyCompanyId" name="tallyCompanyId" style="width: 100%;" value="0"
								  placeholder="Please Enter Tally Company Name" />
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="submit" id="saveTally" class="btn btn-primary"> Update Tally Company
				</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<span id="Close"><spring:message code="label.master.Close" /></span>
				</button>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="vehicleDetails" role="dialog">
	<div class="modal-dialog" style="width: 850px;">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="JobType">vehicle Details</h4>
			</div>
			<div class="modal-body">
				<div class="box">
					<div class="box-body">
						<div class="row1" id="grpselectVendor" class="form-group">
							<div class="col-md-5">
								<label class="L-size control-label" for="selectVendor">Total Qty
									<abbr title="required">*</abbr>
								</label>
								<div class="I-size" >
									<input type="text" class="form-text" style="width: 100%;" id="totalQty"  readonly="readonly"/>
								</div>
							</div>	
							<div class="col-md-4">
							<label class="L-size control-label" for="selectVendor">Remaining Qty
									<abbr title="required">*</abbr>
								</label>
								<div class="I-size" >
									<input type="text" class="form-text" style="width: 80%;" id="remainingQty"  readonly="readonly"/>
								</div>
							</div>
						</div>
						<br>
						<br>
						<div class="row1"  class="form-group">
							<div class="col-md-5">
								<label class="L-size control-label" for="selectVendor">Vehicle
									<abbr title="required">*</abbr>
								</label>
								<div class="I-size" >
									<input type="hidden" class="select"  style="width: 100%;" name="vehicle" id=vid />
								</div>
							</div>
							<div class="col-md-4">
								<label class="L-size control-label" for="selectVendor">Part Qty
									<abbr title="required">*</abbr>
								</label>
								<div class="I-size" >
									<input type="text" class="form-text"  style="width: 80%;" name="partQuantity"  id=partQty 
									onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="validatePartQuantity();"/>
								</div>
							</div>
							<div class="input_fields_wrap1">
								<button class="add_field_button1 btn btn-success" id="add_field_button1">
									<i class="fa fa-plus"></i>
								</button>
							</div>
						</div>
							<div class="row1">
								<table class="table">
									<thead>
										<tr>
											<th class="fit ar">Sr No</th>
											<th class="fit ar">Vehicle</th>
											<th class="fit ar">Part Quantity</th>
											<th class="fit ar">Action</th>
										</tr>
									</thead>
									<tbody id="vehicleTable"> </tbody>
			
								</table>
							</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="submit" id="saveVehiclePart" onclick="saveVehiclePart();" class="btn btn-primary"> Add Vehicle
				</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<span id="Close"><spring:message code="label.master.Close" /></span>
				</button>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="editPartDetailsModal" role="dialog">
	<div class="modal-dialog" style="width: 850px;">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="JobType">Urea Details</h4>
			</div>
			<div class="modal-body">
				<div class="box">
					<div class="box-body">
						<div class="row1" id="grpselectVendor" class="form-group">
							<label class="L-size control-label" for="selectVendor">Manufacturer Name
								<abbr title="required">*</abbr>
							</label>
							<div class="I-size" >
								<input type="hidden" name="partid" id="editUreaManufacturerId"
									required="required" style="width: 100%;"
									required="required"
									placeholder="Please Enter 2 or more Part Name" /> <span
									id="PurchaseOrders_idIcon" class=""></span>
								<div id="PurchaseOrders_idErrorMsg" class="text-danger"></div>
							</div>
						</div>
						<br>
						<div class="row">
							<label class="L-size control-label stock-color-red" id="editStockName" for="manufacurer"><c:out value="${PurchaseOrder.purchaseorder_shiplocation}" /> Stock Details  </label>
							<div class="I-size" >
								<input type="text" readonly="readonly" class="form-text" id="editStockId" style="width: 50%;" />
							</div>
						</div>
						<br>
						<div class="row" >
							<label class="L-size control-label stock-color-red" id="editOtherStockName" for="manufacurer">Other Locations Stock Details  </label>
							<div class="I-size" >
								<input type="text" readonly="readonly" class="form-text" id="editOtherLocationStockInfo" style="width: 50%;" />
							</div>
						</div>
						<br>
						<div class="row1" id="grpselectVendor" class="form-group">
							<div class="col-md-1">
								<label class=" control-label" for="selectVendor">Quantity
							</label>
							</div>
							<div class="col-md-2">
								<label class=" control-label" for="selectVendor">Unit Cost
							</label>
							</div>
							<div class="col-md-2">
								<label class=" control-label" for="selectVendor">Discount
							</label>
							</div>
							<div class="col-md-2">
								<label class=" control-label" for="selectVendor">GST
							</label>
							</div>
							<div class="col-md-2">
								<label class=" control-label" for="selectVendor">Total
							</label>
							</div>
						</div>
						<br>
						<br>
						<div class="row1" id="grpselectVendor" class="form-group">
							<div class="col-md-1">
								<input type="text" class="form-text" placeholder="Qty"  id="editQuantity"
									maxlength="10" onpaste="return false" onkeypress="return isNumberKeyWithDecimal(event,this.id)"
									onkeyup="javascript:sumthere('editQuantity', 'editUnitPrice', 'editDiscount', 'editGST', 'editTotalCost');"
									min="0.00"> 
							</div>
							<div class="col-md-2">
								<input type="text" class="form-text" placeholder="Unit Price"  id="editUnitPrice"
									maxlength="10" onpaste="return false" onkeypress="return isNumberKeyWithDecimal(event,this.id)"
									onkeyup="javascript:sumthere('editQuantity', 'editUnitPrice', 'editDiscount', 'editGST', 'editTotalCost');"
									min="0.00"> 
							</div>
							<div class="col-md-2">
								<div class="input-group">
									<input type="text" class="form-text" placeholder="Discount" onpaste="return false"
										id="editDiscount" maxlength="5" onkeypress="return isNumberKeyWithDecimal(event,this.id);"
										onkeyup="javascript:sumthere('editQuantity', 'editUnitPrice', 'editDiscount', 'editGST', 'editTotalCost');"
										min="0.0"> <span class="input-group-addon">%</span>
								</div>
							</div>
							<div class="col-md-2">
								<div class="input-group">
									<input type="text" class="form-text" placeholder="GST" onpaste="return false"
										id="editGST" maxlength="5" onkeypress="return isNumberKeyWithDecimal(event,this.id);"
										onkeyup="javascript:sumthere('editQuantity', 'editUnitPrice', 'editDiscount', 'editGST', 'editTotalCost');"
										min="0.0"> <span class="input-group-addon">%</span>
								</div>
							</div>
							<div class="col-md-2">
								<input type="text" id="editTotalCost"  data-toggle="tip"
									data-original-title="Total cost" class="form-text"
									required="required" id="tatalcost" readonly="readonly">
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="submit" id="saveVehiclePart" onclick="updatePurchaseOrderPartDetails();" class="btn btn-primary"> Update Urea
				</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<span id="Close"><spring:message code="label.master.Close" /></span>
				</button>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						var input_Statues = document.getElementById('statues').value;
						var wrapperStatues = $("#work-order-statuses"); //Fields wrapper
						switch (input_Statues) {
						case "REQUISITION":
							document.getElementById('status-open').className = 'status-led-open';

							break;
						case "ORDERED":
							document.getElementById('status-in-progress').className = 'status-led-in-progress';

							break;

						case "ONHOLD":
							document.getElementById('status-on-hold').className = 'status-led-on-hold';

							break;

						case "COMPLETED":
							document.getElementById('status-ReOpen').style.display = 'block';

							break;
						}

					});

</script>

<script type="text/javascript">
	$(function() {
		$('[data-toggle="popover"]').popover()
	})
</script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/PO/PurchaseOrdersValidate.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/PO/PurchaseOrdersUrea.js" />"></script>		
		