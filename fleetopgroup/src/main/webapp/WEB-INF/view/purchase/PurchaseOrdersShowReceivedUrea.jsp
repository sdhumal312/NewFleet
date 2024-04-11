<%@ include file="taglib.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
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
						<span>Received Purchase Order</span>
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
						<sec:authorize access="hasAuthority('CHANGE_PO_FROM_RECEIVED_TO_ORDERED')">
							<a class="btn btn-warning btn-sm" onclick="changeUreaPOToOrderedStatus()"
								data-toggle="tip" data-original-title="click this for trip Details">
								 <span class="fa fa-shopping-cart"></span> Change PO To Ordered Status
							</a>
						</sec:authorize>	
						<a class="btn btn-danger" href="<c:url value="/newPurchaseOrders/1.in"/>">Cancel</a>
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
						<!-- <div class="pull-left"> -->
						<h4>Purchase Order - ${PurchaseOrder.purchaseorder_Number }</h4>
						<h4 align="center">
							<a href="ShowVendor.in?vendorId=${PurchaseOrder.purchaseorder_vendor_id}"
								data-toggle="tip" data-original-title="Click Vendor Info"> 
								<c:out value="${PurchaseOrder.purchaseorder_vendor_name}" /><br>
								<c:out value="${PurchaseOrder.purchaseorder_vendor_location}" />
							</a>
						</h4>
					</div>
					<div class="col-md-6">
						<div class="row">
							<input type="hidden" id="statues" name="statues" value="${PurchaseOrder.purchaseorder_status}">
							<div id="work-order-statuses">
								<div id="work-order-statuses">
									<a data-method="post" data-remote="true" rel="nofollow"> .
										<span id="status-in-progress" class="status-led"> 
											<i class="fa fa-circle"></i>
											<div class="status-text" style="color: gray;">ORDERED</div>
										</span>
									</a> 
									<a data-disable-with="..." data-method="post" data-remote="true" rel="nofollow">
										<span id="status-on-hold" class="status-led">
											<i class="fa fa-circle"></i>
											<div class="status-text" style="color: gray;">RECEIVED</div>
										</span> 
									</a> 
									<a href="PurchaseOrders_PartsDebitNote.in?ID=${PurchaseOrder.purchaseorder_id}" data-disable-with="..." data-method="post" data-remote="true" rel="nofollow"> 
										<span id="status-open" class="status-led">
											<i class="fa fa-circle"></i>
											<div class="status-text">RETURN</div>
										</span>
									</a>
									<button type="button" class="btn btn-default" data-toggle="modal" data-target="#addPurchaseOrderDocument" data-whatever="@mdo">
										<i class="fa fa-upload"></i>
									</button>
									<sec:authorize access="hasAuthority('DOWNLOND_PURCHASE')">
										<a style="width: 10%" href="PrintPurchaseOrder?id=${PurchaseOrder.purchaseorder_id}"
											target="_blank" class="btn btn-default ">
											<i class="fa fa-print"></i> Print</a>
									</sec:authorize>
								</div>
								<sec:authorize access="hasAuthority('COMPLETE_PURCHASE')">
									<div id="status-close">
									
									 <button type="submit"  id="completeUreaPurchaseOrder" class="btn btn-primary">Complete</button>
										<%-- <a class="btn btn-success" class="confirmation"
											onclick="return confirm('Are you sure? Complete PurchaseOrder ')"
											href="PurchaseOrders_Complete.in?ID=${PurchaseOrder.purchaseorder_id}">Complete</a> --%>
									</div>
								</sec:authorize>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-3">
						<dl class="dl-horizontal">
							<dt>PO-Type :</dt>
							<dd> <c:out value="${PurchaseOrder.purchaseorder_type}" /> </dd>
							<dt>Date Opened :</dt>
							<dd> <c:out value="${PurchaseOrder.purchaseorder_created_on}" /> </dd>
							<dt>Date Required :</dt>
							<dd> <c:out value="${PurchaseOrder.purchaseorder_requied_on}" /> </dd>
							<c:if test="${poConfiguration.showQuoteNumber}">
							<dt>Quote No :</dt>
							<dd> <c:out value="${PurchaseOrder.purchaseorder_quotenumber}" /> </dd>
							</c:if>
							<c:if test="${poConfiguration.WorkOrderNo}">
							<dt>WO / Indent No :</dt>
							<dd> <c:out value="${PurchaseOrder.purchaseorder_workordernumber}" /> /
								<c:out value="${PurchaseOrder.purchaseorder_indentno}" />
							</dd>
							</c:if>
							<dt>Terms :</dt>
							<dd> <c:out value="${PurchaseOrder.purchaseorder_terms}" /> </dd>

						</dl>

					</div>
					<div class="col-md-4">
						<dl class="dl-horizontal">
							<dt>Vendor :</dt>
							<dd> <c:out value="${PurchaseOrder.purchaseorder_vendor_name}" /> </dd>
							<dt>Vendor GST :</dt>
							<dd> <c:out value="${PurchaseOrder.vendorGstNumber}" /> </dd>
							<dt>Buyer Name:</dt>
							<dd> <c:out value="${PurchaseOrder.purchaseorder_buyer}" /> </dd>
							<dt>Buyer Address :</dt>
							<dd> <c:out value="${PurchaseOrder.purchaseorder_buyeraddress}" /> </dd>
							<c:if test="${poConfiguration.showBuyerGstNumber}">
								<dt>Buyer GST :</dt>
								<dd> <c:out value="${PurchaseOrder.buyerGstNumber}" /> </dd>
							</c:if>
							<dt>Ship via :</dt>
							<dd> <c:out value="${PurchaseOrder.purchaseorder_shipvia}" /> </dd>
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
							<dd> <c:out value="${PurchaseOrder.purchaseorder_shiplocation}" /> </dd>
							<dt>Ship Address:</dt>
							<dd> <c:out value="${PurchaseOrder.purchaseorder_shiplocation_address}" /> </dd>
							<dt>Contact:</dt>
							<dd> <c:out value="${PurchaseOrder.purchaseorder_shiplocation_contact} - " />
								<c:out value="${PurchaseOrder.purchaseorder_shiplocation_mobile}" />
							</dd>
							<dt>Notes :</dt>
							<dd style="word-wrap: break-word"> <c:out value="${PurchaseOrder.purchaseorder_notes}" /> </dd>
						</dl>
					</div>
				</div>
				<input type="hidden"  id="purchaseOrderId" value="${PurchaseOrder.purchaseorder_id}">
				<input type="hidden"  id="purchaseOrderType" value="${PurchaseOrder.purchaseorder_typeId}">
				<input type="hidden"  id="showVehicleDetailsInPO" value="${configuration.showVehicleDetailsInPO}">
				<input type="hidden" id="makeApproval" value="${poConfiguration.makeApproval}">
				<input type="hidden" id="companyId" value="${companyId}">
				<input type="hidden" id="approvedApproval" value="false">
				<input type="hidden" id="rejectedApproval" value="false">
				<input type="hidden" id="transferApproval" value="false">
				<fieldset>
					<!-- Edit Invoice Modal -->
					<div class="modal fade" id="editInvoiceNumber" role="dialog">
						<div class="modal-dialog">
							<!-- Modal content-->
							<div class="modal-content">
								<form action="UpdateInvoicePO.in" method="post"
									onsubmit="return PurchaseOrdersValidate();">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title" id="VehicleType">Edit PO Invoice
											Number</h4>
									</div>
									<div class="modal-body">
										<div class="form-horizontal ">
											<div class="row1">
												<label class="L-size control-label" id="Type">Invoice
													No :<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<!-- Purchase order Id -->
													<input type="hidden" name="purchaseorder_id"
														value="${PurchaseOrder.purchaseorder_id}"
														required="required">
													<!-- Tyre Serial Num -->
													<input type="text" class="form-text" value=""
														id="invoiceno" required="required"
														name="purchaseorder_invoiceno" maxlength="15"
														placeholder="Invoice Number" /> <label id="errorvType"
														class="error"></label>
												</div>
											</div>

											<div class="row1">
												<label class="L-size control-label">Invoice Date: <abbr
													title="required">*</abbr>
												</label>
												<div class="I-size">
													<div class="input-group input-append date"
														id="requiredDate">
														<input type="text" class="form-text" id="invoice_date"
															name="purchaseorder_invoice_date" value=""
															data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
															class="input-group-addon add-on"> <span
															class="fa fa-calendar"></span>
														</span>
													</div>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Received By: <abbr
													title="required">*</abbr>
												</label>
												<div class="I-size">
													<input type="text" class="form-text" readonly="readonly"
														name="purchaseorder_receivedby" required="required"
														value="${CreatedBy}">

												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label" for="issue_description">
													Remarks :</label>
												<div class="I-size">
													<textarea class="text optional form-text" id="initial_note"
														name="purchaseorder_received_remark" rows="2"
														maxlength="199">
				                                                  </textarea>
												</div>
											</div>
										</div>
									</div>
									<div class="modal-footer">
										<button type="submit" class="btn btn-primary">
											<span id="Save"><spring:message
													code="label.master.Save" /></span>
										</button>
										<button type="button" class="btn btn-default"
											data-dismiss="modal">
											<span id="Close"><spring:message
													code="label.master.Close" /></span>
										</button>
									</div>
								</form>
							</div>

						</div>
					</div>
					<div class="modal fade" id="addPurchaseOrderDocument" role="dialog">
						<div class="modal-dialog">
							<div class="modal-content">
								<form method="post" action="uploadPurchaseOrderDocument.in"
									enctype="multipart/form-data">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title">PurchaseOrder Document</h4>
									</div>
									<div class="modal-body">
										<div class="form-horizontal ">
											<input type="hidden" name="purchaseorder_id"
												value="${PurchaseOrder.purchaseorder_id}">
											<div class="row">
												<div class="L-size">
													<label class="L-size control-label"> Document Name:<abbr
														title="required">*</abbr>
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
													<label class="L-size control-label"> Browse:<abbr
														title="required">*</abbr>
													</label>
												</div>
												<div class="I-size">
													<input type="file"
														accept="image/png, image/jpeg, image/gif, application/pdf "
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
						<c:if test="${param.NoAuthen eq true}">
							<div class="alert alert-danger">
								<button class="close" data-dismiss="alert" type="button">x</button>
								No Authentication to access this Purchase Order Location is
								different.
							</div>
						</c:if>
						<c:if test="${param.deleteInventory eq true}">
							<div class="alert alert-danger">
								<button class="close" data-dismiss="alert" type="button">x</button>
								Please delete Part Inventory this PurchaseOrder Invoice before Received Parts  
							</div>
						</c:if>
						<c:if test="${param.SaveInventory eq true}">
							<div class="alert alert-success">
								<button class="close" data-dismiss="alert" type="button">x</button>
								This Purchase Order Received Successfully To Inventory.
							</div>
						</c:if>

						<div class="col-md-11">
							<div class="box box-success">
								<div class="box-body no-padding">
									<div class="table-responsive">
										
										<table class="table ">
											<thead>
												<tr class="breadcrumb">
													<th class="fit"></th>
													<th>Urea</th>
													<th class="fit ar">Qty</th>
													<th class="fit ar">Received</th>
													<th class="fit ar">Received Qty Amount </th>
													<th class="fit ar">Each</th>
													<th class="fit ar">Dis</th>
													<th class="fit ar">GST</th>
													<th class="fit ar">Total</th>
													<c:if test="${configuration.showVehicleDetailsInPO}">
														<th class="fit ar">Action</th>
													</c:if>
												</tr>
											</thead>
											<tbody id="ureaPurchaseOrdersDetails">
											</tbody>
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
							</div>
						</div>
						<div class="col-md-11">
							<div class="row">
								<div class="col-md-8">
									<div class="row">
										<div class="col-md-5">
											<div class="box box-success">
												<div class="box-body no-padding">
													<table class="table table-striped" style="table-layout: fixed; width: 100%">
														<tbody>
															<tr class="row">
																<th class="key">Ordered Date :</th>
																<td class="value"><c:out
																		value="${PurchaseOrder.purchaseorderOrderdate}" /></td>
															</tr>
															<tr class="row">
																<th class="key">Ordered By :</th>
																<td class="value"><c:out
																		value="${PurchaseOrder.purchaseorder_orderdby}" /></td>
															</tr>
															<tr class="row">
																<th class="key">Remarks:</th>
																<td class="value" style="word-wrap: break-word"><c:out
																		value="${PurchaseOrder.purchaseorder_orderd_remark}" /></td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
											<table class="table table-striped">
												<tbody>
													<tr class="row">
														<th class="key">Total Ordered :</th>
														<td class="value"><c:out value="${TotalOrdered}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Total Received :</th>
														<td class="value"><c:out value="${TotalRecevied}" /></td>
													</tr>
												</tbody>
											</table>
										</div>
										<div class="col-md-5">
											<div class="box box-success">
												<div class="box-body no-padding">
													<table class="table table-striped">
														<tbody>
															<!--purchaseorder_orderd_remark  -->  <Original>
															<tr class="row">
																<th class="key">Invoice NO :</th>
																<td class="value"><c:out
																		value="${PurchaseOrder.purchaseorder_invoiceno}" /> <a
																	onclick="javascript:editInvoiceNumberPO('${PurchaseOrder.purchaseorder_invoiceno}',  '${PurchaseOrder.purchaseorder_invoice_date}', '${PurchaseOrder.purchaseorder_received_remark}')"
																	id="editTyreSerialInput"> <span
																		class="fa fa-pencil"></span>
																</a></td>
															</tr>
															<tr class="row">
																<th class="key">Invoice Date :</th>
																<td class="value"><c:out
																		value="${PurchaseOrder.purchaseorder_invoice_date}" /></td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>

											<div class="box box-success">
												<div class="box-body no-padding">
													<table class="table table-striped" style="table-layout: fixed; width: 100%">
														<tbody>
															<tr class="row">
																<th class="key">Received Date :</th>
																<td class="value"><c:out
																		value="${PurchaseOrder.purchaseorderReceivedDate}" /></td>
															</tr>
															<tr class="row">
																<th class="key">Received By :</th>
																<td class="value"><c:out
																		value="${PurchaseOrder.purchaseorder_receivedby}" /></td>
															</tr>
															<tr class="row">
																<th class="key">Remarks:</th>
																<td class="value" style="word-wrap: break-word"><c:out
																		value="${PurchaseOrder.purchaseorder_received_remark}" /></td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div>
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
											<tr class="row">
												<th class="key">Total ReceivedQty Amount :</th>
												<td class="value" ><i class="fa fa-inr"></i>
													<span id="TotalReceivedPartCost"> </span></td>
											</tr>
										</tbody>
									</table>
								</div>
								<table class="table">
									<tfoot>
										<tr class="breadcrumb">
											<th colspan="6"><a href=""><i class="fa fa-plus"></i>
											</a></th>
										</tr>
									</tfoot>
								</table>
							</div>
						</div>
					</div>
				</fieldset>
			</sec:authorize>
		</div>
		<div class="row">
			<small class="text-muted"><b>Created by :</b> <c:out 
					value="${PurchaseOrder.createdBy}" /></small> | <small class="text-muted"><b>Created
					date: </b> <c:out value="${PurchaseOrder.created}" /></small> | <small
				class="text-muted"><b>Last updated by :</b> <c:out
					value="${PurchaseOrder.lastModifiedBy}" /></small> | <small
				class="text-muted"><b>Last updated date:</b> <c:out
					value="${PurchaseOrder.lastupdated}" /></small>
		</div>
	</section>
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
							<div class="row1">
								<table class="table">
									<thead>
										<tr>
											<th class="fit ar">Sr No</th>
											<th class="fit ar">Vehicle</th>
											<th class="fit ar">Part Quantity</th>
										</tr>
									</thead>
									<tbody id="vehicleDetailsTable"> </tbody>
			
								</table>
							</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<span id="Close"><spring:message code="label.master.Close" /></span>
				</button>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/PO/PurchaseOrdersValidate.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
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

						case "RECEIVED":
							document.getElementById('status-on-hold').className = 'status-led-in-progress';

							break;

						case "COMPLETE":
							document.getElementById('status-ReOpen').style.display = 'block';

							break;
						}
					});
</script>
<!-- get the Inventory Drop Down down -->
<c:url var="findInventoryURL" value="getInventoryList.in" />
<script type="text/javascript">
	$(function() {
		$('[data-toggle="popover"]').popover()
	})
	$(document).ready(function() {
		$("#datemask").inputmask("dd-mm-yyyy", {
			placeholder : "dd-mm-yyyy"
		}), $("[data-mask]").inputmask()
	});
	function editInvoiceNumberPO(a, b, c) {
		document.getElementById("invoiceno").value = a, document
				.getElementById("invoice_date").value = b, document
				.getElementById("initial_note").value = c, document
				.getElementById("initial_note").value = c, $(
				"#editInvoiceNumber").modal()
	}
</script>
<!-- get the Inventory Quantity and Unit price -->
<c:url var="findInventoryURL" value="getInventoryQuantityList.in" />
<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/PO/PurchaseOrdersShowReceivedUrea.js" />"></script>		