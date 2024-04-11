<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/jquery.dataTables.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/buttons.dataTables.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/newPurchaseOrders/1.in"/>">Purchase
						Orders</a>
				</div>
				<div class="pull-right">
					<a class="btn btn-link"
						href="<c:url value="/newPurchaseOrders/1.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.min.js" />"></script>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/dataTables.buttons.min.js" />"></script>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.print.min.js" />"></script>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.flash.min.js" />"></script>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VA/NewVendorlanguage.js" />"></script>
		<sec:authorize access="!hasAuthority('VIEW_PURCHASE')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_PURCHASE')">
			<div class="row">
				<div class="main-body">
					<div class="box">
						<div class="box-body">
							<table id="VendorTable"
								class="table table-bordered table-striped">
								<thead>
									<tr>
										<th class="fit ar">ID</th>
										<th class="fit ar">Date Opened</th>
										<th class="fit ar">Date Required</th>
										<th>Vendor</th>
										<th class="fit ar">Buyer</th>
										<th class="fit ar">Terms</th>
										<th class="fit ar">location</th>
										<th class="fit ar">Document</th>
										<th class="actions" class="icon">Actions</th>
									</tr>
								</thead>
								<tbody>

									<c:if test="${!empty PurchaseOrder}">
										<c:forEach items="${PurchaseOrder}" var="PurchaseOrder">
											<tr data-object-id="" class="ng-scope">
												<td class="fit ar"><a target="_blank"
													href="PurchaseOrders_Parts.in?ID=${PurchaseOrder.purchaseorder_id}"
													data-toggle="tip"
													data-original-title="Click Purchase Order Info"> <c:out
															value="PO-${PurchaseOrder.purchaseorder_Number}" />

												</a></td>
												<td class="fit ar"><c:out
														value="${PurchaseOrder.purchaseorder_created_on}" /></td>
												<td class="fit ar"><c:out
														value="${PurchaseOrder.purchaseorder_requied_on}" /></td>

												<td><a target="_blank"
													href="PurchaseOrders_Parts.in?ID=${PurchaseOrder.purchaseorder_id}"
													data-toggle="tip"
													data-original-title="Click Purchase Order Info"> <c:out
															value="${PurchaseOrder.purchaseorder_vendor_name}" />
												</a></td>
												<td class="fit ar"><c:out
														value="${PurchaseOrder.purchaseorder_buyer}" /></td>
												<td class="fit ar"><c:out
														value="${PurchaseOrder.purchaseorder_terms}" /></td>

												<td class="fit ar"><c:out
														value="${PurchaseOrder.purchaseorder_shiplocation}" /></td>
												<td><sec:authorize
														access="hasAuthority('DOWNLOND_PURCHASE')">
														<a
															href="${pageContext.request.contextPath}/download/PurchaseorderDocument/${PurchaseOrder.purchaseorder_id}.in">
															<span class="fa fa-download"> Doc</span>
														</a>
													</sec:authorize></td>
												<td class="icon"><c:if
														test="${PurchaseOrder.purchaseorder_status == 'REQUISITION'}">
														<div class="btn-group ">
															<a class="btn btn-default btn-sm dropdown-toggle"
																data-toggle="dropdown" href="#"> <span
																class="fa fa-cog"></span> <span class="caret"></span>
															</a>

															<ul class="dropdown-menu pull-right">
																<li><sec:authorize
																		access="hasAuthority('DELETE_PURCHASE')">
																		<a
																			href="deletePurchaseOrder.in?Purchaseorder_id=${PurchaseOrder.purchaseorder_id}"
																			class="confirmation"
																			onclick="return confirm('Are you sure? Delete ')">
																			<span class="fa fa-trash"></span> Delete
																		</a>
																	</sec:authorize></li>

															</ul>
														</div>
													</c:if></td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</sec:authorize>
	</section>
</div>