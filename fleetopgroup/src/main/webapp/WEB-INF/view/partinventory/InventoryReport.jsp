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
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/NewInventory/1.in"/>">New Inventory</a> / <a
						href="<c:url value="/InventoryReport.in"/>">Inventory Report</a>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/InventoryReport.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_INVENTORY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_INVENTORY')">
			<div class="row">
				<div class="main-body">
					<div class="box">
						<div class="box-body">
							<table id="VendorTable"
								class="table table-bordered table-striped">
								<thead>
									<tr>
										<th class="fit ar">Id</th>
										<th>Part</th>
										<th class="fit ar">Make</th>
										<th class="fit ar">Location</th>
										<th class="fit ar">Quantity</th>
										<th class="fit ar">U_Price</th>
										<th class="fit ar">PO-No</th>
										<th class="fit ar">Invoice No</th>
										<th class="fit ar">Invoice Date</th>
										<th class="fit ar">Invoice Amount</th>
										<th>Vendor</th>
										<th class="fit ar">Part Category</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${!empty Inventory}">
										<c:forEach items="${Inventory}" var="Inventory">
											<tr>
												<td class="fit ar"><a target="_blank"
													href="showDetailsInventory.in?inventory_id=${Inventory.inventory_id}"
													data-toggle="tip"
													data-original-title="Click Inventory INFO"><c:out
															value="PI-${Inventory.inventory_Number}" /></a></td>
												<td><a target="_blank"
													href="showDetailsInventory.in?inventory_id=${Inventory.inventory_id}"
													data-toggle="tip"
													data-original-title="Click Inventory INFO"><c:out
															value="${Inventory.partnumber}" /><br> <c:out
															value="${Inventory.partname}" /></a></td>
												<td class="fit ar"><c:out value="${Inventory.make}" /></td>
												<td class="fit ar"><c:out value="${Inventory.location}" /></td>
												<td class="fit ar"><h5>
														<span class="label label-info"><c:out
																value="${Inventory.quantity}" /> ${Inventory.convertToStr}</span>
													</h5> <c:out value="${Inventory.unittype}" /></td>
												<td class="fit ar"><c:out
														value="${Inventory.unitprice}" /></td>
												<td class="fit ar"><c:if
														test="${Inventory.purchaseorder_id != 0}">
														<a
															href="<c:url value="/PurchaseOrders_Parts.in?ID=${Inventory.purchaseorder_id}"/>"
															data-toggle="tip"
															data-original-title="Click Purchase Order Info"> <c:out
																value="PO-${Inventory.purchaseorder_Number}" /></a>
													</c:if></td>
												<td class="fit ar"><c:out
														value="${Inventory.invoice_number}" /></td>
												<td class="fit ar"><c:out
														value="${Inventory.invoice_date}" /></td>
												<td class="fit ar"><i class="fa fa-inr"></i> <c:out
														value=" ${Inventory.invoice_amount}" /></td>
												<td><c:choose>
														<c:when test="${Inventory.vendor_id !=0}">
															<a target="_blank"
																href="ShowVendor.in?vendorId=${Inventory.vendor_id}"><c:out
																	value="${Inventory.vendor_name}" /><br> <c:out
																	value="${Inventory.vendor_location}" /></a>
														</c:when>
														<c:otherwise>
															<c:out value="${Inventory.vendor_name}" />
															<br>
															<c:out value="${Inventory.vendor_location}" />
														</c:otherwise>
													</c:choose></td>
												<td class="fit ar"><c:out
														value="${Inventory.category}" /></td>	
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
</div>