<%@ include file="taglib.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<div class="content-wrapper">
	<section class="content-header">
	<div class="box">
		<div class="box-body">
			<div class="row">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/newPurchaseOrders/1.in"/>">REQUISITION</a> /
					<c:if test="${poConfiguration.requisitionApproved}">
						<a href="<c:url value="/PurchaseOrders_APPROVED/1.in"/>">REQUISITION APPROVED</a>/
					</c:if>
					<a href="<c:url value="/PurchaseOrders_ORDERED/1.in"/>">ORDERED</a>
					/ <a href="<c:url value="/PurchaseOrders_RECEIVED/1.in"/>">RECEIVED</a>
					/ <a href="<c:url value="/PurchaseOrders_COMPLETED/1.in"/>">COMPLETED</a>
					/ <span>Received Purchase Order</span>
				</div>
					<div class="col-md-off-5">
						<div class="col-md-3">
							<form action="<c:url value="/searchPurchaseOrderShow.in"/>"
								method="post">
								<div class="input-group">
									<span class="input-group-addon"> <span
										aria-hidden="true">PO-</span></span> <input class="form-text"
										id="vehicle_registrationNumber" name="Search" type="number"
										min="1" required="required" placeholder="Po-NO eg:74654">
									<span class="input-group-btn">
										<button type="submit" name="search" id="search-btn"
											class="btn btn-success btn-sm">
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
						<!-- <div class="pull-left"> -->
						<h4>Purchase Order ${PurchaseOrder.purchaseorder_Number }</h4>
						<h4 align="center">
							<a
								href="ShowVendor.in?vendorId=${PurchaseOrder.purchaseorder_vendor_id}"
								data-toggle="tip" data-original-title="Click Vendor Info"> <c:out
									value="${PurchaseOrder.purchaseorder_vendor_name}" /><br>
								<c:out value="${PurchaseOrder.purchaseorder_vendor_location}" />
							</a>
						</h4>
					</div>
					<div class="col-md-6">
						<div class="row">
							<input type="hidden" id="statues" name="statues"
								value="${PurchaseOrder.purchaseorder_status}">
							<div id="work-order-statuses">
								<div id="work-order-statuses">
									<a data-disable-with="..." data-method="post"
										data-remote="true" rel="nofollow"> <span
										id="status-on-hold" class="status-led"> <i
											class="fa fa-circle"></i>
											<div class="status-text">COMPLETED</div>
									</span></a>
									
									<sec:authorize access="hasAuthority('REOPEN_PURCHASE')">
									<c:if test="${PurchaseOrder.purchaseorder_vendor_approvalID <= 0 }">
										<a href="PurchaseOrders_Reopen.in?ID=${PurchaseOrder.purchaseorder_id}"
											data-disable-with="..." data-method="post" data-remote="true"
											rel="nofollow"><span id="status-open" class="status-led">
												<i class="fa fa-circle"></i>
												<div class="status-text">RE-OPEN</div>
										</span> </a>
									</c:if>
									</sec:authorize>
									
									<h4>Closed on ${PurchaseOrder.purchaseorder_complete_date}</h4>
									<sec:authorize access="hasAuthority('DOWNLOND_PURCHASE')">
										<a style="width: 10%"
											href="PrintPurchaseOrder?id=${PurchaseOrder.purchaseorder_id}"
											target="_blank" class="btn btn-default "><i
											class="fa fa-print"></i> Print</a>
									</sec:authorize>
									<c:choose>
										<c:when test="${PurchaseOrder.purchaseorder_document == true}">
											<sec:authorize access="hasAuthority('DOWNLOND_PURCHASE')">

												<a class="btn btn-default" style="width: 13%"
													href="${pageContext.request.contextPath}/download/PurchaseorderDocument/${PurchaseOrder.purchaseorder_document_id}.in">
													<span class="fa fa-download"> Quotation</span>
												</a>
												<a class="btn btn-default" style="width: 13%"
													href="${pageContext.request.contextPath}/download/PurchaseorderDocument/${PurchaseOrder.purchaseorder_document_id}.in">
													<span class="fa fa-download"> Invoice</span>
												</a>
											</sec:authorize>
										</c:when>
									</c:choose>
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
								<c:out value="${PurchaseOrder.purchaseorder_workordernumber}" />
								/
								<c:out value="${PurchaseOrder.purchaseorder_indentno}" />
							</dd>
							</c:if>
							<dt>Terms :</dt>
							<dd>
								<c:out value="${PurchaseOrder.purchaseorder_terms}" />
							</dd>
							<c:if test="${PurchaseOrder.purchaseorder_vendor_approvalID > 0 }">
								<dt>Approval Number :</dt>
								<dd>
								<a onclick="openApproval(${PurchaseOrder.purchaseorder_vendor_paymodeId},${PurchaseOrder.purchaseorder_vendor_approvalID});"> A-${PurchaseOrder.vendorApprovalNumber}/${PurchaseOrder.purchaseorder_vendor_paymode} </a>
								</dd>
							</c:if>

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
								<c:out
									value="${PurchaseOrder.purchaseorder_shiplocation_address}" />
							</dd>
							<dt>Contact:</dt>
							<dd>
								<c:out
									value="${PurchaseOrder.purchaseorder_shiplocation_contact} - " />
								<c:out
									value="${PurchaseOrder.purchaseorder_shiplocation_mobile}" />
							</dd>
							<dt>Notes :</dt>
							<dd style="word-wrap: break-word">
								<c:out value="${PurchaseOrder.purchaseorder_notes}" />
							</dd>
						</dl>


					</div>
				</div>

				<fieldset>
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
								This Purchase Order Received Successfully To Inventory.
							</div>
						</c:if>
						<div class="panel panel-success">
							<div class="panel-body">
								<div class="col-md-11">
									<input type="hidden" id="purchaseOrderId" value="${PurchaseOrder.purchaseorder_id}">
									<div class="box box-success">
										<h4>
											<span class="label label-success">Order Battery</span>
										</h4>
										<div class="box-body no-padding">
											<div class="table-responsive">
												<table class="table" style="margin-bottom: 0px">
													<thead>
														<tr class="breadcrumb">
															<th class="fit">No</th>
															<th>Battery</th>
															<th>Capacity</th>
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
													<tbody>
														<c:if test="${!empty PurchaseOrderPart}">
															<%
																Integer hitsCount = 1;
															%>
															<c:forEach items="${PurchaseOrderPart}"
																var="PurchaseOrderPart">
																<c:if test="${PurchaseOrderPart.approvalPartStatusId == 2}">
																<tr data-object-id="" class="ng-scope">
																	<td class="fit">
																		<%
																			out.println(hitsCount);
																						hitsCount += 1;
																		%>
																	</td>
																	<!-- Tast table to assing part value table -->
																	<td><c:out
																			value="${PurchaseOrderPart.TYRE_MANUFACTURER} -" />
																		<c:out value="${PurchaseOrderPart.TYRE_MODEL}" /><br>
																		<c:choose>
																			<c:when
																				test="${PurchaseOrderPart.INVENTORY_TYRE_QUANTITY != 0.0}">
																				<samp style="color: red;">
																					Already You have
																					${PurchaseOrderPart.INVENTORY_TYRE_QUANTITY} Tyre
																					in stock
																					(${PurchaseOrderPart.INVENTORY_TYRE_NEW_RT}). <a
																						href="showAvailableTyre.in?size=${PurchaseOrderPart.TYRE_SIZE}"
																						target="_blank"><i class="fa fa-external-link"></i></a>
																				</samp>
																			</c:when>
																			<c:otherwise>
																				<samp style="color: green;">You don't have in
																					stock</samp>
																			</c:otherwise>
																		</c:choose></td>
																	<td><c:out value="${PurchaseOrderPart.TYRE_SIZE}" /></td>


																	<td class="fit ar">${PurchaseOrderPart.quantity}</td>

																	<td class="fit ar"><a>${PurchaseOrderPart.received_quantity}</a></td>
																	
																	<td class="fit ar"> <i class="fa fa-inr"></i> ${PurchaseOrderPart.finalReceivedAmount}</td>
																	<td class="fit ar">${PurchaseOrderPart.parteachcost}</td>
																	<td class="fit ar">${PurchaseOrderPart.discount}%</td>
																	<td class="fit ar">${PurchaseOrderPart.tax}%</td>
																	<td class="fit ar"><i class="fa fa-inr"></i>
																		${PurchaseOrderPart.totalcost}</td>
																	<c:if test="${configuration.showVehicleDetailsInPO}">
																		<td class="fit ar">
																			<div class="btn-group">
																				<a class="btn-sm dropdown-toggle"
																					data-toggle="dropdown" href="#"> <span
																					class="fa fa-cog"></span> <span class="caret"></span>
																				</a>
																				<ul class="dropdown-menu pull-right">
																					<li><a href="#"
																						onclick="showPOVehicleDetails('${PurchaseOrderPart.purchaseorderto_partid}')">Show
																							Vehicle</a></li>
		
																				</ul>
																			</div>
																		</td>
																	</c:if>	
																</tr>
																</c:if>
															</c:forEach>
														</c:if>
													</tbody>
												</table>
											</div>
											<c:if test="${poConfiguration.makeApproval}">
							<div id="rejectedPart" class="table-responsive">
							<h4>
								<span class="label label-danger">Rejected Battery</span>
							</h4>
								<table class="table">
									<thead>
										<tr class="breadcrumb">
											<th class="fit"></th>
											<th>Battery</th>
											<th>Capacity</th>
											<th class="fit ar">Qty</th>
											<th class="fit ar">Each</th>
											<th class="fit ar">Dis</th>
											<th class="fit ar">GST</th>
											<th class="fit ar">Total</th>
											<th class="fit">Actions</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty PurchaseOrderPart}">
										<input type="hidden" id="PurchaseOrderPart" value="${PurchaseOrderPart}">
											<c:forEach items="${PurchaseOrderPart}" var="PurchaseOrderPart">
												<c:if test="${PurchaseOrderPart.approvalPartStatusId == 3}">
												<input type="hidden" id="retectedPurchaseOrderPart" value="true">
												<tr data-object-id="" class="ng-scope">
													<td class="fit"><c:if
															test="${PurchaseOrderTask.mark_complete == 1}">
															<h4>
																<i class="fa fa-check" style="color: green"></i>
															</h4>
														</c:if> <c:if test="${PurchaseOrderTask.mark_complete != 1}">
															<h4>
																<i class="fa fa-wrench" style="color: red"></i>
															</h4>
														</c:if></td>
													<!-- Tast table to assing part value table -->
													<td><c:out
															value="${PurchaseOrderPart.TYRE_MANUFACTURER} -" />
														<c:out value="${PurchaseOrderPart.TYRE_MODEL}" /><br>
														<c:choose>
															<c:when
																test="${PurchaseOrderPart.INVENTORY_TYRE_QUANTITY != 0.0}">
															</c:when>
															<c:otherwise>
															<samp style="color: green;">You don't have in stock</samp>
															</c:otherwise>
														</c:choose></td>
													<td><c:out value="${PurchaseOrderPart.TYRE_SIZE}" /></td>

													<td class="fit ar">${PurchaseOrderPart.quantity}</td>
													<td class="fit ar">${PurchaseOrderPart.parteachcost}</td>
													<td class="fit ar">${PurchaseOrderPart.discount}%</td>
													<td class="fit ar">${PurchaseOrderPart.tax}%</td>
													<td class="fit ar"><i class="fa fa-inr"></i>
														${PurchaseOrderPart.totalcost}</td>


												</tr>
												</c:if>
											</c:forEach>
										</c:if>
										<c:if test="${empty PurchaseOrderPart}">
											<tr data-object-id="" class="ng-scope">
												<td colspan="8">
													<h5 align="center">Purchase Order is Empty</h5>
												</td>
											</tr>
										</c:if>
									</tbody>
								</table>
							</div>
							<div id="transferPart" class="table-responsive">
							<h4>
								<span class="label label-primary">Branch Transfer</span>
							</h4>
								<table class="table">
									<thead>
										<tr class="breadcrumb">
											<th class="fit"></th>
											<th>Battery</th>
											<th>Capacity</th>
											<th class="fit ar">Qty</th>
											<th class="fit ar">Each</th>
											<th class="fit ar">Dis</th>
											<th class="fit ar">GST</th>
											<th class="fit ar">Total</th>
											<th class="fit">Actions</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty PurchaseOrderPart}">
										<input type="hidden" id="PurchaseOrderPart" value="${PurchaseOrderPart}">
											<c:forEach items="${PurchaseOrderPart}" var="PurchaseOrderPart">
												<c:if test="${PurchaseOrderPart.approvalPartStatusId == 4}">
													<input type="hidden" id="transferPurchaseOrderPart" value="true">
												<tr data-object-id="" class="ng-scope">
													<td class="fit"><c:if
															test="${PurchaseOrderTask.mark_complete == 1}">
															<h4>
																<i class="fa fa-check" style="color: green"></i>
															</h4>
														</c:if> <c:if test="${PurchaseOrderTask.mark_complete != 1}">
															<h4>
																<i class="fa fa-wrench" style="color: red"></i>
															</h4>
														</c:if></td>
													<!-- Tast table to assing part value table -->
													<td><c:out
															value="${PurchaseOrderPart.TYRE_MANUFACTURER} -" />
														<c:out value="${PurchaseOrderPart.TYRE_MODEL}" /><br>
														<c:choose>
															<c:when
																test="${PurchaseOrderPart.INVENTORY_TYRE_QUANTITY != 0.0}">
															</c:when>
															<c:otherwise>
															<samp style="color: green;">You don't have in stock</samp>
															</c:otherwise>
														</c:choose></td>
													<td><c:out value="${PurchaseOrderPart.TYRE_SIZE}" /></td>

													<td class="fit ar">${PurchaseOrderPart.quantity}</td>
													<td class="fit ar">${PurchaseOrderPart.parteachcost}</td>
													<td class="fit ar">${PurchaseOrderPart.discount}%</td>
													<td class="fit ar">${PurchaseOrderPart.tax}%</td>
													<td class="fit ar"><i class="fa fa-inr"></i>
														${PurchaseOrderPart.totalcost}</td>

													
												</tr>
												</c:if>
											</c:forEach>
										</c:if>
										<c:if test="${empty PurchaseOrderPart}">
											<tr data-object-id="" class="ng-scope">
												<td colspan="8">
													<h5 align="center">Purchase Order is Empty</h5>
												</td>
											</tr>
										</c:if>
									</tbody>
								</table>
							</div>
							</c:if>
										</div>
									</div>

									<div class="box box-success">
										<h4>
											<span class="label label-danger">Returned Battery</span>
										</h4>
										<div class="box-body no-padding">
											<div class="table-responsive">
												<table class="table" style="margin-bottom: 0px">
													<thead>
														<tr class="breadcrumb">
															<th class="fit">No</th>
															<th>Battery</th>
															<th>Capacity</th>
															<th>Remark</th>
															<th class="fit ar">Return_Qty</th>
															<th class="fit ar">Each_Cost</th>
															<th class="fit ar">Dis</th>
															<th class="fit ar">GST</th>
															<th class="fit ar">Total</th>
														</tr>
													</thead>
													<tbody>
														<c:if test="${!empty PurchaseOrderDebitNote}">
															<%
																Integer hitsCount = 1;
															%>
															<c:forEach items="${PurchaseOrderDebitNote}"
																var="PurchaseOrderDebitNote">
																<tr data-object-id="" class="ng-scope">
																	<td class="fit">
																		<%
																			out.println(hitsCount);
																						hitsCount += 1;
																		%>
																	</td>
																	<!-- Tast table to assing part value table -->
																	<td><c:out
																			value="${PurchaseOrderDebitNote.manufacturerName}" /><br>
																		<c:out value="${PurchaseOrderDebitNote.batteryType}" /></td>
																	<td><c:out
																			value="${PurchaseOrderDebitNote.batteryCapacity}" /></td>
																	<td><c:out
																			value="${PurchaseOrderDebitNote.received_quantity_remark}" /></td>


																	<td class="fit ar"><a style="color: red;">
																			${PurchaseOrderDebitNote.notreceived_quantity}</a></td>

																	<td class="fit ar">${PurchaseOrderDebitNote.parteachcost}</td>
																	<td class="fit ar">${PurchaseOrderDebitNote.discount}
																		%</td>
																	<td class="fit ar">${PurchaseOrderDebitNote.tax}%</td>
																	<td class="fit ar"><i class="fa fa-inr"></i>
																		${PurchaseOrderDebitNote.total_return_cost}</td>

																</tr>
															</c:forEach>
														</c:if>
														<c:if test="${empty PurchaseOrderDebitNote}">
															<tr data-object-id="" class="ng-scope">
																<td colspan="8">
																	<h5 align="center">Purchase Order Debit Note is
																		Empty</h5>
																</td>
															</tr>
														</c:if>

													</tbody>
												</table>
											</div>
										</div>
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
											<div class="box box-success">
												<div class="box-body no-padding">
													<table class="table table-striped" style="table-layout: fixed; width: 100%">
														<tbody>
															<tr class="row">
																<th class="key">Invoice NO :</th>
																<td class="value"><c:out
																		value="${PurchaseOrder.purchaseorder_invoiceno}" /></td>
															</tr>
															<tr class="row">
																<th class="key">Invoice Date :</th>
																<td class="value"><c:out
																		value="${PurchaseOrder.purchaseorder_invoice_date}" /></td>
															</tr>

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
																		value="${PurchaseOrder.purchaseorder_orderd_remark}" /></td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
										</div>
										<div class="col-md-5">
											<div class="box box-success">
												<div class="box-body no-padding">
													<table class="table">
														<tbody>
															<tr class="row">
																<th class="key">Total Ordered Qty :</th>
																<td class="value"><span class="label label-success"><c:out
																			value="${TotalOrdered}" /></span></td>
															</tr>
															<tr class="row">
																<th class="key">Total Received Qty :</th>
																<td class="value"><span class="label label-info"><c:out
																			value="${TotalRecevied}" /></span></td>
															</tr>
															<tr class="row">
																<th class="key">Total Return Qty :</th>
																<td class="value"><span class="label label-danger"><fmt:formatNumber pattern="#.##"
																			value="${TotalOrdered - TotalRecevied}" /></span></td>
															</tr>

														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div>
								</div>

								<div class="col-md-3">
									<div class="panel panel-info">
										<table class="table">
											<tbody>
											<c:if test="${poConfiguration.eachTotalCost}">
												<tr class="row">
													<th class="key">Ordered Sub Total :</th>
													<td class="value"><i class="fa fa-inr"></i>
														${totalEachCost}</td>
												</tr>
											</c:if>
											<c:if test="${!poConfiguration.eachTotalCost}">
											<tr class="row">
													<th class="key">Ordered Sub Total :</th>
													<td class="value"><i class="fa fa-inr"></i>
														${PurchaseOrder.purchaseorder_subtotal_cost}</td>
												</tr>
											</c:if>	
												<tr class="row">
													<th class="key">Freight :</th>
													<td class="value"><i class="fa fa-inr"></i>
														${PurchaseOrder.purchaseorder_freight}</td>
												</tr>
												
												<tr class="row">
													<th class="key">GST Cost :</th>
													<td class="value"><i class="fa fa-inr"></i>
														${PurchaseOrder.purchaseorder_totaltax_cost}</td>
												</tr>
												<c:if test="${poConfiguration.totalDiscountCost}">
												<tr class="row">
													<th class="key">Discount cost :</th>
													<td id="totalDiscountCost" class="value"><i class="fa fa-inr"></i>
														${TotalDiscountCost}</td>
												</tr>
											</c:if>
											<c:if test="${poConfiguration.finalTotalCost}">
												<tr class="row">
													<th class="key"><a>Total Ordered:</a></th>
													<td class="value"><a><i class="fa fa-inr"></i>
															${finalCost}</a></td>
												</tr>
											</c:if>
											<c:if test="${!poConfiguration.finalTotalCost}">
												<tr class="row">
													<th class="key"><a>Total Ordered:</a></th>
													<td class="value"><a><i class="fa fa-inr"></i>
															${PurchaseOrder.purchaseorder_totalcost}</a></td>
												</tr>
											</c:if>
												<tr class="row">
													<th class="key">( - ) Debit Note :</th>
													<%-- <td class="value"><a style="color: red;"><i
															class="fa fa-inr"></i>
															${PurchaseOrder.purchaseorder_total_debitnote_cost}</a></td> --%>
													<td>
													<a style="color: red;"><i
															class="fa fa-inr"></i>
															<fmt:formatNumber type="number" maxFractionDigits="2" value="${PurchaseOrder.purchaseorder_total_debitnote_cost}" /></a>
													</td>		
															
												</tr>
												<tr class="row">
													<th class="key">( - ) Advance Paid :</th>
													<td class="value"><a style="color: red;"><i
															class="fa fa-inr"></i>
															${PurchaseOrder.purchaseorder_advancecost}</a></td>
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
												<td class="value"><i class="fa fa-inr"></i>
													${TotalReceivedPartCost}</td>
											</tr>
											
											</tbody>
										</table>
									</div>
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
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						var input_Statues = document.getElementById('statues').value;
						var wrapperStatues = $("#work-order-statuses"); //Fields wrapper
						switch (input_Statues) {

						case "COMPLETED":
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
<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>