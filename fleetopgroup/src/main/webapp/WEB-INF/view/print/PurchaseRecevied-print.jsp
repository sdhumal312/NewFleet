<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="wrapper">
	<sec:authorize access="!hasAuthority('DOWNLOND_PURCHASE')">
		<spring:message code="message.unauth"></spring:message>
	</sec:authorize>
	<sec:authorize access="hasAuthority('DOWNLOND_PURCHASE')">
		<section class="invoice">
			<!-- title row -->
			<div class="row">
				<div class="col-xs-12">
					<h2 class="page-header">
						<c:choose>
							<c:when test="${company.company_id != null}">
								<img
									src="${pageContext.request.contextPath}/downloadlogo/${company.company_id_encode}.in"
									class="img-rounded" alt="Company Logo" width="280" height="40" />

							</c:when>
							<c:otherwise>
								<i class="fa fa-globe"></i>
								<c:out value="${company.company_name}" />
							</c:otherwise>
						</c:choose>
						<c:if test="${!poConfiguration.KHTCprintConfig}">
							<small class="pull-right"> Print By:
								${company.firstName}_${company.lastName} I.</small>
							<small>Branch :<c:out value=" ${company.branch_name}  , " />
								Department :<c:out value=" ${company.department_name}" />
							</small>
						</c:if>
					</h2>
				</div>
				<!-- /.col -->
			</div>
			<!-- info row -->
			<div class="row invoice-info">
				<h3>Purchase Order ${PurchaseOrder.purchaseorder_Number }</h3>
			</div>
			<!-- info row -->
			<div class="row invoice-info">
				<div class="col-sm-12 col-xs-12 invoice-col">
					<div class="box-body no-padding"><!--latest style added-->
						<table class="table table-bordered table-striped" style="table-layout: fixed; width: 100%" >
							<tbody>
								<tr class="row">
									<th>Date Opened:</th>
									<td><c:out
											value="${PurchaseOrder.purchaseorder_created_on}" /></td>
									<!-- column Two -->
									<th>Vendor :</th>
									<td><c:out value="${PurchaseOrder.purchaseorder_vendor_name}" /></td>
									<th>Vendor GST:</th>
									<td><c:out value="${PurchaseOrder.vendorGstNumber}" /></td>		


								</tr>
								<tr class="row">
									<th>Required :</th>
									<td><c:out
											value="${PurchaseOrder.purchaseorder_requied_on}" /></td>

									<!-- column Two -->
									<c:if test="${!poConfiguration.KHTCprintConfig}">
									<th>Terms :</th>
									<td><c:out value="${PurchaseOrder.purchaseorder_terms}" /></td>
									</c:if>
									
									<c:if test="${poConfiguration.KHTCprintConfig}">
								<th>WorkOrder No:</th>
									<td><c:out value="${PurchaseOrder.purchaseorder_workordernumber}" /></td>
								</c:if>

								</tr>
									<c:if test="${poConfiguration.showQuoteNumber}">
								<c:if test="${!poConfiguration.KHTCprintConfig}">
								<tr class="row">
									<th>Quote No :</th>
									<td><c:out
											value="${PurchaseOrder.purchaseorder_quotenumber}" /></td>
									<!-- column Two -->
									<th>WorkOrder No:</th>
									<td><c:out
											value="${PurchaseOrder.purchaseorder_workordernumber}" /></td>


								</tr>
								</c:if>
								</c:if>
								<c:if test="${!poConfiguration.KHTCprintConfig}">
								<tr class="row">
									<!-- column Two -->
									<th>Buyer :</th>
									<td colspan="5"><c:out
											value="${PurchaseOrder.purchaseorder_buyer}" /><br> <c:out
											value="${PurchaseOrder.purchaseorder_buyeraddress}" /></td>


								</tr>
								</c:if>
								<c:if test="${poConfiguration.showBuyerGstNumber}">
									<tr class="row">
										<th>Buyer GST:</th>
										<td colspan="5">
											<c:out value="${PurchaseOrder.buyerGstNumber}" /><br> 
										</td>
									</tr>
								</c:if>
								<c:if test="${!poConfiguration.KHTCprintConfig}">
								<tr class="row">
									<!-- column Two -->
									<c:if test="${poConfiguration.showShipvia }">
									<th>Ship via:</th>
									<td><c:out value="${PurchaseOrder.purchaseorder_shipvia}" /></td></c:if>

									<!-- column Three -->
									<th>Ship To:</th>
									<td><c:out
											value="${PurchaseOrder.purchaseorder_shiplocation}" /></td>
								</tr>
								<tr class="row">
									<!-- column Two -->
									<c:if test="${poConfiguration.showShipAddress }">
									<th>Ship Address:</th>
									<td colspan="5"><c:out
											value="${PurchaseOrder.purchaseorder_shiplocation_address}" /><br>
										<c:out
											value="${PurchaseOrder.purchaseorder_shiplocation_contact} - " />
										<c:out
											value="${PurchaseOrder.purchaseorder_shiplocation_mobile}" /></td></c:if>
								</tr>
								</c:if>
								<tr class="row">
								<c:if test="${poConfiguration.showNotes  }">
									<th>Notes :</th><!--latest-->
									<td colspan="5" style="word-wrap: break-word"><c:out
											value="${PurchaseOrder.purchaseorder_notes}" /></td></c:if>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<!-- /.row -->
			<br>

			<!-- Table row -->
			<div class="row">
				<div class="col-xs-12 ">
					<div class="table-responsive">
						<table class="table table-bordered table-striped">
							<caption>Order Parts</caption>
							<thead>
								<tr class="breadcrumb">
									<th></th>
									<th>Part</th>
									<c:if test="${PurchaseOrder.purchaseorder_typeId == 2 || PurchaseOrder.purchaseorder_typeId == 3}">
										<th>Size</th>	
									</c:if>
									<th>Qty</th>
									<th>Received</th>
									<th>Each</th>
									<th>Dis</th>
									<th>Tax</th>
									<th>Total</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${!empty PurchaseOrderPart}">
									<c:forEach items="${PurchaseOrderPart}" var="PurchaseOrderPart">
										<tr data-object-id="" class="ng-scope">
											<td><c:if test="${PurchaseOrderTask.mark_complete == 1}">
													<i class="fa fa-check" style="color: green"></i>
												</c:if> <c:if test="${PurchaseOrderTask.mark_complete != 1}">
													<i class="fa fa-wrench" style="color: red"></i>
												</c:if></td>
											<!-- Tast table to assing part value table -->
											<%-- <td><c:out
													value="${PurchaseOrderPart.purchaseorder_partname}" /> <c:out
													value="${PurchaseOrderPart.purchaseorder_partnumber}" /><br>
												<c:choose>
													<c:when
														test="${PurchaseOrderPart.inventory_all_quantity != 0.0}">
														<samp style="color: red;">
															Already You have
															${PurchaseOrderPart.inventory_all_quantity} Quantity in
															stock . 
														</samp>
													</c:when>
													<c:otherwise>
														<samp style="color: green;">You don't have in stock</samp>
													</c:otherwise>
												</c:choose></td> --%>
												
												<td>
												<c:choose>
													<c:when test="${PurchaseOrder.purchaseorder_typeId == 2}">
														<c:out value="${PurchaseOrderPart.TYRE_MANUFACTURER} -" />
														<c:out value="${PurchaseOrderPart.TYRE_MODEL}" /><br>
													</c:when>
													<c:when test="${PurchaseOrder.purchaseorder_typeId == 3}">
														<c:out value="${PurchaseOrderPart.TYRE_MANUFACTURER} -" />
														<c:out value="${PurchaseOrderPart.TYRE_MODEL}" /><br>								
													</c:when>
													<c:when test="${PurchaseOrder.purchaseorder_typeId == 4}">
														<c:out value="${PurchaseOrderPart.clothTypeName}" />
													</c:when>
													<c:otherwise>
														<c:out value="${PurchaseOrderPart.purchaseorder_partname}" /> 
														<c:out value="${PurchaseOrderPart.purchaseorder_partnumber}" /><br>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${PurchaseOrderPart.inventory_all_quantity != 0.0}">
														<samp style="color: red;">
															Already You have ${PurchaseOrderPart.inventory_all_quantity} Quantity in stock .
														</samp>
													</c:when>
													<c:otherwise>
														<samp style="color: green;">You don't have in stock</samp>
													</c:otherwise>
												</c:choose>
												
											</td>
											
											<c:if test="${PurchaseOrder.purchaseorder_typeId == 2 || PurchaseOrder.purchaseorder_typeId == 3}">
												<td>
													<c:choose>
														<c:when test="${PurchaseOrder.purchaseorder_typeId == 2}">
															<c:out value="${PurchaseOrderPart.TYRE_SIZE}"></c:out>
														</c:when>
														<c:when test="${PurchaseOrder.purchaseorder_typeId == 3}">
															<c:out value="${PurchaseOrderPart.TYRE_SIZE}"></c:out>
														</c:when>
													</c:choose>
												</td>
											</c:if>

											<td>${PurchaseOrderPart.quantity}</td>
											<td><a>${PurchaseOrderPart.received_quantity}</a></td>

											<td>${PurchaseOrderPart.parteachcost}</td>
											<td>${PurchaseOrderPart.discount}%</td>
											<td>${PurchaseOrderPart.tax}%</td>
											<td><i class="fa fa-inr"></i>
												${PurchaseOrderPart.totalcost}</td>


										</tr>
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

				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-xs-12 table-responsive">
					<div class="col-sm-7 col-xs-7 invoice-col">
						<div class="box-body no-padding table-responsive">
							<!-- <table class="table"> --><!--Original-->
							<table class="table table-striped" style="table-layout: fixed; width: 50%">
								<tbody>
									<tr class="row">
										<th>Ordered Date :</th>
										<td><c:out
												value="${PurchaseOrder.purchaseorderOrderdate}" /></td>
										<th>Invoice NO :</th>
										<td><c:out
												value="${PurchaseOrder.purchaseorder_invoiceno}" /></td>
									</tr>
									<tr class="row">
										<th>Ordered By :</th>
										<td><c:out
												value="${PurchaseOrder.purchaseorder_orderdby}" /></td>
										<th>Invoice Date :</th>
										<td><c:out
												value="${PurchaseOrder.purchaseorder_invoice_date}" /></td>
									</tr>
									<tr class="row">
										<th>Remarks:</th>
										<td style="word-wrap: break-word"><c:out
												value="${PurchaseOrder.purchaseorder_orderd_remark}" /></td>
										<th>Received Date :</th>
										<td><c:out
												value="${PurchaseOrder.purchaseorderReceivedDate}" /></td>
									</tr>

									<tr class="row">
										<th>Total Ordered :</th>
										<td><c:out value="${TotalOrdered}" /></td>
										<th>Received By :</th>
										<td><c:out
												value="${PurchaseOrder.purchaseorder_receivedby}" /></td>
									</tr>
									<tr class="row">
										<th>Total Received :</th>
										<td><c:out value="${TotalRecevied}" /></td>
										<th>Remarks:</th>
										<td style="word-wrap: break-word"><c:out
												value="${PurchaseOrder.purchaseorder_orderd_remark}" /></td>
									</tr>
								</tbody>
							</table>

						</div>
					</div>
					<div class="col-sm-3 col-xs-3 invoice-col">
						<div class="box-body no-padding">
							<table class="table table-bordered table-striped">
								<tbody>
									<tr class="row">
										<th class="key">SubTotal :</th>
										<td class="value"><i class="fa fa-inr"></i>
											${PurchaseOrder.purchaseorder_subtotal_cost}</td>
									</tr>
									<tr class="row">
										<th class="key">Freight :</th>
										<td class="value"><i class="fa fa-inr"></i>
											${PurchaseOrder.purchaseorder_freight}</td>
									</tr>
									<tr class="row">
										<th class="key">Taxes :</th>
										<td class="value"><i class="fa fa-inr"></i>
											${PurchaseOrder.purchaseorder_totaltax_cost}</td>
									</tr>
									<tr class="row">
										<th class="key"><a>Total :</a></th>
										<td class="value"><a><i class="fa fa-inr"></i>
												${PurchaseOrder.purchaseorder_totalcost}</a></td>
									</tr>
									<tr class="row">
										<th class="key">Advance Paid :</th>
										<td class="value"><i class="fa fa-inr"></i>
											${PurchaseOrder.purchaseorder_advancecost}</td>
									</tr>
									<tr class="row">
										<th class="key">Balance :</th>
										<td class="value"><i class="fa fa-inr"></i>
											${PurchaseOrder.purchaseorder_balancecost}</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</section>
	</sec:authorize>
</div>
<!-- ./wrapper -->