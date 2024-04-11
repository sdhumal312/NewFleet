<%@ include file="taglib.jsp"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/tabs.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/NewInventory/1.in"/>">New Inventory</a> / <span
						id="NewVehi">Show Inventory</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-link" href="<c:url value="/NewInventory/1.in"/>">Cancel</a>
					<sec:authorize access="hasAuthority('ADD_INVENTORY')">
						<a href="addInventory.in" class="btn btn-success"><span
							class="fa fa-plus"> Create Inventory</span></a>
					</sec:authorize>
				</div>
			</div>
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_INVENTORY')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_INVENTORY')">
					<div class="pull-left">
						<a
							href="${pageContext.request.contextPath}/getPartImage/${Inventory.part_photoid}.in"
							class="zoom" data-title="Part Photo" data-footer=""
							data-type="image" data-toggle="lightbox"> <img
							src="${pageContext.request.contextPath}/getPartImage/${Inventory.part_photoid}.in"
							class="img-rounded" alt="Cinque Terre" width="100" height="100" />
						</a>
					</div>
					<div class="pull-left1">
						<h3 class="secondary-header-title">
							<a href="showMasterParts.in?partid=${Inventory.partid}"
								data-toggle="tip" data-original-title="Click Part Info"> <c:out
									value="${Inventory.partnumber}" /> - <c:out
									value="${Inventory.partname}" /> - <c:out
									value="${Inventory.category}" /> - <fmt:formatNumber maxFractionDigits="2"
									value="${Inventory.quantity}" /></a>
						</h3>
						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Part Make"> </span> <span
									class="text-muted"><c:out value="${Inventory.make}" /></span></li>
								<li><span class="fa fa-bars" aria-hidden="true"
									data-toggle="tip" data-original-title="Part Category"> </span>
									<span class="text-muted"><c:out
											value="${Inventory.category}" /></span></li>
								<li><span class="fa fa-map-marker" aria-hidden="true"
									data-toggle="tip" data-original-title="Part Location"> </span>
									<span class="text-muted"><c:out
											value="${Inventory.location}" /></span></li>
							</ul>
						</div>
					</div>
				</sec:authorize>
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content-body">
		<sec:authorize access="!hasAuthority('VIEW_INVENTORY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_INVENTORY')">
			<div class="row">
				<div class="col-md-9 col-sm-9 col-xs-12">
					<div class="main-body">
						<ul class="tabs">
							<li class=" current" data-tab="tab-1">Details</li>
							<li class="tab-link" data-tab="tab-2">Activity</li>
						</ul>
						<div id="tab-1" class="tab-content2 current">
							<div class="row">
								<div class="col-md-6 col-sm-5 col-xs-12">
									<div class="box box-success">
										<div class="box-header">
											<h3 class="box-title">Part Information</h3>
										</div>
										<div class="box-body no-padding">
											<table class="table table-striped">
												<tbody>
													<tr class="row">
														<th class="key">Part Number :</th>
														<td class="value" style="width: 2432452px;"><c:out
																value="${Inventory.partnumber}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Part Name :</th>
														<td class="value"><c:out
																value="${Inventory.partname}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Category :</th>
														<td class="value"><c:out
																value="${Inventory.category}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Manufacturer :</th>
														<td class="value"><c:out value="${Inventory.make}" /></td>
													</tr>
											</table>
										</div>
									</div>

									<div class="box box-success">
										<div class="box-header">
											<h3 class="box-title">Vendor Details</h3>
										</div>
										<div class="box-body no-padding">
											<table class="table table-striped">
												<tbody>
													<tr class="row">
														<th class="key">Vendor Name :</th>
														<td class="value"><c:choose>
																<c:when test="${Inventory.vendor_id !=0}">
																	<a href="${Inventory.vendorTypeId}/ShowVendor.in?vendorId=${Inventory.vendor_id}&page=1" target="_blank"><c:out
																			value="${Inventory.vendor_name}" /></a>
																</c:when>
																<c:otherwise>
																	<c:out value="${Inventory.vendor_name}" />

																</c:otherwise>
															</c:choose></td>
													</tr>
													<tr class="row">
														<th class="key">Vendor Location :</th>
														<td class="value"><c:out
																value="${Inventory.vendor_location}" /></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
									<div class="box box-success">
										<div class="box-header">
											<h3 class="box-title">Description</h3>
										</div>
										<div class="box-body no-padding">
											<table class="table table-striped">
												<tbody>
													<tr class="row">

														<td><c:out value="${Inventory.description}" /></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
								<div class="col-md-5 col-sm-5 col-xs-12">
									<div class="box box-success">
										<div class="box-header">
											<h3 class="box-title">Details</h3>
										</div>
										<div class="box-body no-padding">
											<table class="table table-striped">
												<tbody>
													<tr class="row">
														<th class="key">Unit Cost :</th>
														<td class="value"><i class="fa fa-inr"></i> <c:out
																value=" ${Inventory.unitprice}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Discount :</th>
														<td class="value"><c:out
																value="${Inventory.discount} %" /></td>
													</tr>
													<tr class="row">
														<th class="key">GST :</th>
														<td class="value"><c:out value="${Inventory.tax} %" /></td>
													</tr>
													<tr class="row">
														<th class="key">Total :</th>
														<td class="value"><i class="fa fa-inr"></i> <c:out
																value=" ${Inventory.total}" /></td>
													</tr>
													<tr class="row">
														<th class="key">PO Number :</th>
														<td class="value"><c:choose>
																		<c:when test="${Inventory.purchaseorder_Number != null && Inventory.purchaseorder_Number != 0}">
																		<abbr data-toggle="tip" data-original-title="PO Number">
																			<a
																				href="<c:url value="/PurchaseOrders_Parts.in?ID=${Inventory.purchaseorder_id}"/>"
																				data-toggle="tip"
																				data-original-title="Click Purchase Order Info"><c:out
																					value="PO-${Inventory.purchaseorder_Number}" /></a>
																			</abbr>
																		</c:when>
																		<c:otherwise>
																			<c:out
																				value="PO-${Inventory.purchaseorder_Number}" />
																		</c:otherwise>
																	</c:choose></td>
													</tr>
													<tr class="row">
														<th class="key">Invoice Number :</th>
														<td class="value"><c:out
																value="${Inventory.invoice_number}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Invoice Date :</th>
														<td class="value"><c:out
																value="${Inventory.invoice_date}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Invoice Amount :</th>
														<td class="value"><c:out
																value="${Inventory.invoice_amount}" /></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
									<div class="box box-success">
										<div class="box-header">
											<h3 class="box-title">${Inventory.location}</h3>
										</div>
										<div class="box-body no-padding">
											<table class="table table-striped">
												<tbody>
													<tr class="row">
														<th class="key">Quantity :</th>
														<td class="value"><h4>
																<span class="label label-success"><fmt:formatNumber maxFractionDigits="2"
																		value="${Inventory.quantity}" /> <c:out
																		value="  ${Inventory.unittype}" /></span>
															</h4></td>
													</tr>
													<c:forEach items="${PartLocationRow}" var="PartLocationRow">

														<tr class="row">
															<th class="key">Aisle :</th>
															<td class="value"><c:out
																	value="${PartLocationRow.aisle}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Row :</th>
															<td class="value"><c:out
																	value="${PartLocationRow.row}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Bin :</th>
															<td class="value"><c:out
																	value="${PartLocationRow.bin}" /></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div id="tab-2" class="tab-content2">
							<div class="row"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<small class="text-muted"><b>Created by :</b> <c:out
						value="${Inventory.createdBy}" /></small> | <small class="text-muted"><b>Created
						date: </b> <c:out value="${Inventory.created}" /></small> | <small
					class="text-muted"><b>Last updated by :</b> <c:out
						value="${Inventory.lastModifiedBy}" /></small> | <small
					class="text-muted"><b>Last updated date:</b> <c:out
						value="${Inventory.lastupdated}" /></small>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabsShowVehicle.js" />"></script>
</div>