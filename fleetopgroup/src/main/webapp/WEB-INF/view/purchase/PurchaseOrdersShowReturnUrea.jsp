<%@ include file="taglib.jsp"%>
<link rel="stylesheet" href="resources/css/select/select2.min.AJAX.css" />
<link rel="stylesheet" href="resources/css/select/select2.min.css" />
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
									<input class="form-text" id="vehicle_registrationNumber" name="Search" type="number"
										min="1" required="required" placeholder="Po-NO eg:74654">
									<span class="input-group-btn">
										<button type="submit" name="search" id="search-btn" class="btn btn-success btn-sm">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
							</form>
						</div>
						<a href="<c:url value="/newPurchaseOrders/1.in"/>">Cancel</a>
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
						<h4>Purchase Order ${PurchaseOrder.purchaseorder_id }</h4>
						<h4 align="center">
							<a href="ShowVendor.in?vendorId=${PurchaseOrder.purchaseorder_vendor_id}" data-toggle="tip" data-original-title="Click Vendor Info"> 
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
									<a data-method="post" data-remote="true" rel="nofollow"> 
										<span id="status-in-progress" class="status-led"> 
											<i class="fa fa-circle"></i>
											<div class="status-text" style="color: gray;">ORDERED</div>
										</span>
									</a> 
									<a href="PurchaseOrders_Parts.in?ID=${PurchaseOrder.purchaseorder_id}" data-disable-with="..." data-method="post" data-remote="true" rel="nofollow">
										<span id="status-open" class="status-led">
											<i class="fa fa-circle"></i>
											<div class="status-text">RECEIVED</div>
										</span> 
									</a> 
									<a href="PurchaseOrders_PartsDebitNote.in?ID=${PurchaseOrder.purchaseorder_id}" data-disable-with="..." data-method="post" data-remote="true" rel="nofollow"> 
										<span id="status-on-hold" class="status-led"> 
											<i class="fa fa-circle"></i>
											<div class="status-text">RETURN</div>
										</span>
									</a>
									<button type="button" class="btn btn-default" data-toggle="modal" data-target="#addPurchaseOrderDocument" data-whatever="@mdo">
										<i class="fa fa-upload"></i>
									</button>
									<sec:authorize access="hasAuthority('DOWNLOND_PURCHASE')">
										<a style="width: 10%" href="PrintPurchaseReturn?id=${PurchaseOrder.purchaseorder_id}" target="_blank" class="btn btn-default ">
											<i class="fa fa-print"></i> Print</a>
									</sec:authorize>
								</div>
								<sec:authorize access="hasAuthority('COMPLETE_PURCHASE')">
									<div id="status-close">
										<a class="btn btn-success" data-disable-with="..."
											href="PurchaseOrders_Complete.in?ID=${PurchaseOrder.purchaseorder_id}">Complete</a>
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
							<dt>Quote No :</dt>
							<dd> <c:out value="${PurchaseOrder.purchaseorder_quotenumber}" /> </dd>
							<dt>WO / Indent No :</dt>
							<dd> <c:out value="${PurchaseOrder.purchaseorder_workordernumber}" /> /
								<c:out value="${PurchaseOrder.purchaseorder_indentno}" />
							</dd>
							<dt>Terms :</dt>
							<dd> <c:out value="${PurchaseOrder.purchaseorder_terms}" /> </dd>
						</dl>

					</div>
					<div class="col-md-4">
						<dl class="dl-horizontal">
							<dt>Vendor :</dt>
							<dd> <c:out value="${PurchaseOrder.purchaseorder_vendor_name}" /> </dd>
							<dt>Vendor GST:</dt>
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
							<dd> <c:out value="${PurchaseOrder.purchaseorder_notes}" /> </dd>
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
											<input type="hidden" name="purchaseorder_id" value="${PurchaseOrder.purchaseorder_id}">
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
														accept="image/png, image/jpeg, image/gif"
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
						<c:if test="${param.SaveInventory eq true}">
							<div class="alert alert-success">
								<button class="close" data-dismiss="alert" type="button">x</button>
								This Purchase Order Received Part add Successfully To Inventory.
							</div>
						</c:if>
						<div class="col-md-11">
							<div class="box box-success">
								<h4>Debit Note No. ${PurchaseOrder.purchaseorder_id}</h4>
								<div class="box-body no-padding">
									<div class="table-responsive">
									<input type="hidden"  id="purchaseOrderId" value="${PurchaseOrder.purchaseorder_id}">
										<input type="hidden"  id="purchaseOrderType" value="${PurchaseOrder.purchaseorder_typeId}">
										<table class="table ">
											<thead>
												<tr class="breadcrumb">
													<th class="fit">Sr No</th>
													<th class="fit ar">Urea</th>
													<th class="fit ar" >Remark</th>
													<th class="fit ar">Return_Qty</th>
													<th class="fit ar">Each_Cost</th>
													<th class="fit ar">Dis</th>
													<th class="fit ar">GST</th>
													<th class="fit ar">Total</th>
													<th class="fit ar">Action</th>
												</tr>
											</thead>
											<tbody id="ureaPurchaseOrdersReturnDetails">
											</tbody>
										</table>
									</div>
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
													<table class="table table-striped">
														<tbody>
															<tr class="row">
																<th class="key">Ordered Date :</th>
																<td class="value"> <c:out value="${PurchaseOrder.purchaseorder_orderddate}" /></td>
															</tr>
															<tr class="row">
																<th class="key">Ordered By :</th>
																<td class="value"><c:out value="${PurchaseOrder.purchaseorder_orderdby}" /></td>
															</tr>
															<tr class="row">
																<th class="key">Remarks:</th>
																<td class="value"><c:out value="${PurchaseOrder.purchaseorder_orderd_remark}" /></td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
											<table class="table table-striped">
												<tbody>
													<tr class="row">
														<th class="key">Total Ordered Qty :</th>
														<td class="value"><span id="totalOrderQuantity" class="label label-success"> </span></td>
													</tr>
													<tr class="row">
														<th class="key">Total Received Qty :</th>
														<td class="value"><span class="label label-info" id="totalReceivedQuantity"></span></td>
													</tr>
													<tr class="row">
														<th class="key">Total Return Qty :</th>
														<td class="value"><span class="label label-danger" id="totalReturnQuantity"></span></td>
													</tr>

												</tbody>
											</table>
										</div>
										<div class="col-md-5">
											<div class="box box-success">
												<div class="box-body no-padding">
													<table class="table table-striped">
														<tbody>
															<tr class="row">
																<th class="key">Invoice NO :</th>
																<td class="value"><c:out value="${PurchaseOrder.purchaseorder_invoiceno}" /></td>
															</tr>
															<tr class="row">
																<th class="key">Invoice Date :</th>
																<td class="value"><c:out value="${PurchaseOrder.purchaseorder_invoice_date}" /></td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>

											<div class="box box-success">
												<div class="box-body no-padding">
													<table class="table table-striped">
														<tbody>
															<tr class="row">
																<th class="key">Received Date :</th>
																<td class="value"><c:out value="${PurchaseOrder.purchaseorder_received_date}" /></td>
															</tr>
															<tr class="row">
																<th class="key">Received By :</th>
																<td class="value"><c:out value="${PurchaseOrder.purchaseorder_receivedby}" /></td>
															</tr>
															<tr class="row">
																<th class="key">Remarks:</th>
																<td class="value"><c:out value="${PurchaseOrder.purchaseorder_orderd_remark}" /></td>
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
											<tr class="row">
												<th class="key">Total Debit_Note Cost :</th>
												<td class="value"><h4> <i class="fa fa-inr"></i> ${PurchaseOrder.purchaseorder_total_debitnote_cost} </h4></td>
											</tr>

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
			<small class="text-muted"><b>Created by :</b> 
			<c:out value="${PurchaseOrder.createdBy}" /></small> | 
			<small class="text-muted"><b>Created date: </b> <c:out value="${PurchaseOrder.created}" /></small> | 
			<small class="text-muted"><b>Last updated by :</b> <c:out value="${PurchaseOrder.lastModifiedBy}" /></small> | 
			<small class="text-muted"><b>Last updated date:</b> <c:out value="${PurchaseOrder.lastupdated}" /></small>
		</div>
	</section>
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

						case "RECEIVED":
							document.getElementById('status-on-hold').className = 'status-led-on-hold';

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
</script>

<!-- get the Inventory Quantity and Unit price -->
<c:url var="findInventoryURL" value="getInventoryQuantityList.in" />
<script src="resources/js/select/lodash.min.js" /></script>
<script src="resources/js/select/select2.min.AJAX.js" /></script>
<script type="text/javascript" src="resources/js/select2.full.min.js" /></script>
<script type="text/javascript" src="resources/js/PurchaseOrdersValidate.js" /></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/PO/PurchaseOrdersShowReturnUrea.js" />"></script>		