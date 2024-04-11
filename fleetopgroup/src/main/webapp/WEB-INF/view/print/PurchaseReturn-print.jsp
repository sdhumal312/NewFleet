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
									class="img-rounded" alt="Company Logo" width="160" height="40" />

							</c:when>
							<c:otherwise>
								<i class="fa fa-globe"></i>
							</c:otherwise>
						</c:choose>
						<c:out value="${company.company_name}" />
						<small class="pull-right"> Print By: ${company.user_email}
							I.</small> <small>Branch :<c:out
								value=" ${company.branch_name}  , " /> Department :<c:out
								value=" ${company.department_name}" /></small>
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
						<table class="table table-bordered table-striped" style="table-layout: fixed; width: 100%">
							<tbody>
								<tr class="row">
									<th>Date Opened:</th>
									<td><c:out
											value="${PurchaseOrder.purchaseorder_created_on}" /></td>
									<!-- column Two -->
									<th>Vendor :</th>
									<td><c:out
											value="${PurchaseOrder.purchaseorder_vendor_name}" /></td>
									<th>Vendor GST :</th>
									<td><c:out
											value="${PurchaseOrder.vendorGstNumber}" /></td>		


								</tr>
								<tr class="row">
									<th>Required :</th>
									<td><c:out
											value="${PurchaseOrder.purchaseorder_created_on}" /></td>

									<!-- column Two -->
									<th>Terms :</th>
									<td><c:out value="${PurchaseOrder.purchaseorder_terms}" /></td>


								</tr>
								<tr class="row">
									<th>Quote No :</th>
									<td><c:out
											value="${PurchaseOrder.purchaseorder_quotenumber}" /></td>
									<!-- column Two -->
									<th>WorkOrder No:</th>
									<td><c:out
											value="${PurchaseOrder.purchaseorder_workordernumber}" /></td>


								</tr>
								<tr class="row">
									<!-- column Two -->
									<th>Buyer :</th>
									<td colspan="5"><c:out
											value="${PurchaseOrder.purchaseorder_buyer}" /><br> <c:out
											value="${PurchaseOrder.purchaseorder_buyeraddress}" /></td>


								</tr>
								<c:if test="${poConfiguration.showBuyerGstNumber}">
									<tr class="row">
										<th>Buyer GST:</th>
										<td colspan="5">
											<c:out value="${PurchaseOrder.buyerGstNumber}" /><br> 
										</td>
									</tr>
								</c:if>
								<tr class="row">
									<!-- column Two -->
									<th>Ship via:</th>
									<td><c:out value="${PurchaseOrder.purchaseorder_shipvia}" /></td>

									<!-- column Three -->
									<th>Ship To:</th>
									<td><c:out
											value="${PurchaseOrder.purchaseorder_shiplocation}" /></td>
								</tr>
								<tr class="row">
									<!-- column Two -->
									<th>Ship Address:</th>
									<td colspan="5"><c:out
											value="${PurchaseOrder.purchaseorder_shiplocation_address}" /><br>
										<c:out
											value="${PurchaseOrder.purchaseorder_shiplocation_contact} - " />
										<c:out
											value="${PurchaseOrder.purchaseorder_shiplocation_mobile}" /></td>
								</tr>
								<tr class="row">
									<th>Notes :</th><!--latest  -->
									<td colspan="5" style="word-wrap: break-word"><c:out
											value="${PurchaseOrder.purchaseorder_notes}" /></td>
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
							<caption>Debit Note No.
								${PurchaseOrder.purchaseorder_id}</caption>
							<thead>
							<thead>
								<tr class="breadcrumb">
									<th class="fit"></th>
									<th>Part</th>
									<th>Remark</th>
									<th class="fit ar">Return_Qty</th>
									<th class="fit ar">Each_Cost</th>
									<th class="fit ar">Dis</th>
									<th class="fit ar">Tax</th>
									<th class="fit ar">Total</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${!empty PurchaseOrderDebitNote}">
									<c:forEach items="${PurchaseOrderDebitNote}"
										var="PurchaseOrderDebitNote">
										<tr data-object-id="" class="ng-scope">
											<td class="fit"><c:choose>
													<c:when
														test="${PurchaseOrderPart.quantity == PurchaseOrderPart.received_quantity}">
														<h4>
															<i class="fa fa-check" style="color: green;"></i>
														</h4>
													</c:when>
													<c:otherwise>
														<h4>
															<i class="fa fa-wrench" style="color: red"></i>
														</h4>
													</c:otherwise>
												</c:choose></td>
											<!-- Tast table to assing part value table -->
											<td><c:out
													value="${PurchaseOrderDebitNote.purchaseorder_partname}" />
												<c:out
													value="${PurchaseOrderDebitNote.purchaseorder_partnumber}" /></td>
											<td><c:out
													value="${PurchaseOrderDebitNote.received_quantity_remark}" /></td>


											<td class="fit ar"><a style="color: red;">
													${PurchaseOrderDebitNote.notreceived_quantity}</a></td>

											<td class="fit ar">${PurchaseOrderDebitNote.parteachcost}</td>
											<td class="fit ar">${PurchaseOrderDebitNote.discount}%</td>
											<td class="fit ar">${PurchaseOrderDebitNote.tax}%</td>
											<td class="fit ar"><i class="fa fa-inr"></i>
												${PurchaseOrderDebitNote.total_return_cost}</td>

										</tr>
									</c:forEach>
								</c:if>
								<c:if test="${empty PurchaseOrderDebitNote}">
									<tr data-object-id="" class="ng-scope">
										<td colspan="8">
											<h5 align="center">Purchase Order Debit Note is Empty</h5>
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
					<div class="col-sm-3 col-xs-3 invoice-col">
						<div class="box-body no-padding table-responsive">
							<!-- <table class="table"> --><!--Original-->
							<table class="table table-striped" style="table-layout: fixed; width: 50%">
								<tbody>
									<tr class="row">
										<th class="key">Ordered Date :</th>
										<td class="value"><c:out
												value="${PurchaseOrder.purchaseorder_orderddate}" /></td>
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
					</div>
					
					<br>
					
					<div class="col-sm-4 col-xs-4 invoice-col">
						<div class="box-body no-padding table-responsive">
							<!-- <table class="table"> --> <!--Original-->
							<table class="table table-striped" style="table-layout: fixed; width: 50%">
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
												value="${PurchaseOrder.purchaseorder_received_date}" /></td>
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
					<div class="col-sm-3 col-xs-3 invoice-col">
						<div class="box-body no-padding">
							<table class="table table-bordered table-striped">
								<tbody>
									<tr class="row">
										<th class="key">Total Debit_Note Cost :</th>
										<td class="value"><h4>
												<i class="fa fa-inr"></i>
												${PurchaseOrder.purchaseorder_total_debitnote_cost}
											</h4></td>
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