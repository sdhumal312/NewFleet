<%@ include file="taglib.jsp"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<style>
.box-partheader {
	border: none;
	position: relative
}

.partboxheader {
	float: left;
	margin: 0 auto;
	width: 100%
}

.box-partheader .partAction {
	float: left;
	width: 20%;
	padding: 3px
}

.box-partheader .partInDate {
	float: left;
	width: 40%;
	padding: 3px
}

.box-partheader .partId {
	float: right;
	width: 30%;
	padding: 3px
}

.box-partheader .partInAmout {
	float: left;
	width: 70%;
	padding: 3px
}

.partInAmout dl {
	margin-bottom: 5px
}

.partInDate-header {
	padding: 2px;
	font-weight: 700;
	font-size: 10px;
	margin: 0
}

.partDetails dd, .partDetails dl {
	margin-bottom: 5px
}

.partInDate-body {
	padding: 2px;
	font-weight: 400;
	font-size: 9px
}

.partDetails {
	padding: 3px;
	margin-top: 3px
}

.partDetails dl {
	padding: 3px
}

.partAction h3 {
	font-size: 40px;
	font-weight: 700
}

.partInAmout-dl dt {
	float: left;
	width: 40%;
	overflow: hidden;
	clear: left;
	text-align: right;
	text-overflow: ellipsis;
	white-space: nowrap
}

.partInAmout-dl dd {
	margin-left: 44%;
	margin-bottom: 4px
}
</style>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/tabs.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/NewInventory/1.in"/>">New Inventory</a> / <span
						id="NewVehi">Show Inventory</span>
				</div>
				<div class="col-md-off-5">
					<div class="col-md-3">
						<form action="<c:url value="/searchAllInventory.in"/>"
								method="post">
								<div class="input-group">
									<input class="form-text" id="vehicle_registrationNumber"
										name="SearchAllInv" type="text" required="required"
										placeholder="Part NO/Name"> <span
										class="input-group-btn">
										<button type="submit" name="search" id="search-btn"
											class="btn btn-success btn-sm" data-toggle="modal"
											data-target="#processing-modal">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
							</form>
					</div>
					<a class="btn btn-link" href="<c:url value="/NewInventory/1.in"/>">Cancel</a>
					<sec:authorize access="hasAuthority('ADD_INVENTORY')">
						<a href="addInventory.in" class="btn btn-success btn-sm"><span
							class="fa fa-plus"> Create Inventory</span></a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('TRANSFER_INVENTORY')">
						<a
							href="transferInventoryHistory.in?Inventory_all_id=${InventoryAll.inventory_all_id}"
							class="btn btn-default btn-sm">Transfer History Part</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('TRANSFER_INVENTORY')">
						<a
							href="<c:url value="/YTIHISTORY/1.in"/>"
							class="btn btn-warning btn-sm">Your Transfer History</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_INVENTORY')">
						<a href="<c:url value="/InventoryQRscan.in"/>"
							class="btn btn-success"><span class="fa fa-search">
								Scan Again</span></a>
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
							href="${pageContext.request.contextPath}/getPartImage/${InventoryAll.part_photoid}.in"
							class="zoom" data-title="Part Photo" data-footer=""
							data-type="image" data-toggle="lightbox"> <img
							src="${pageContext.request.contextPath}/getPartImage/${InventoryAll.part_photoid}.in"
							class="img-rounded" alt="Cinque Terre" width="100" height="100" />
						</a>
					</div>
					<div class="pull-left1">
						<h3 class="secondary-header-title">
							<a href="showMasterParts.in?partid=${InventoryAll.partid}"
								data-toggle="tip" data-original-title="Click Part Info"> <c:out
									value="${InventoryAll.partnumber}" /> - <c:out
									value="${InventoryAll.partname}" /> - <c:out
									value="${InventoryAll.category}" /> - <c:out
									value="${InventoryAll.all_quantity}"/>${InventoryAll.convertToStr} </a>
						</h3>
						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li><span class="fa fa-bars" aria-hidden="true"
									data-toggle="tip" data-original-title="Part Category">
										Category :</span> <span class="text-muted"><c:out
											value="${InventoryAll.category}" /></span></li>
								<li><span class="fa fa-bars" aria-hidden="true"
									data-toggle="tip" data-original-title="Part Quantity">
										Quantity :</span> <span class="text-muted"><c:out
											value="${InventoryAll.all_quantity}" /> ${InventoryAll.convertToStr} </span></li>
							</ul>
						</div>
					</div>
				</sec:authorize>
			</div>
		</div>
	</section>
	<c:if test="${param.NoAuthen eq true}">
		<div class="alert alert-danger">
			<button class="close" data-dismiss="alert" type="button">x</button>
			No Authentication to access this Receive Part, Edit part &amp; Delete
			Your Location is different.
		</div>
	</c:if>
	<c:if test="${param.deleteInventory eq true}">
		<div class="alert alert-success">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This Inventory Quantity Deleted Successfully.
		</div>
	</c:if>
	<c:if test="${param.saveTransferInventory eq true}">
		<div class="alert alert-success">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This Inventory Quantity Transfer Successfully.
		</div>
	</c:if>
	<c:if test="${param.vendorApproval eq true}">
		<div class="alert alert-success">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This Inventory Is Under <span>${param.vendorPaymentStatus}</span> Status Hence You Can Not Edit Or Delete The Inventory.
		</div>
	</c:if>
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_INVENTORY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_INVENTORY')">
			<div class="row1" style="padding: 25px;">
				<div class="">
					<c:if test="${!empty InventoryLocation}">
						<c:forEach items="${InventoryLocation}" var="InventoryLocation">
							<h4>
								<c:out value="${InventoryLocation.location}  "></c:out>
								= <span class="label label-success">${InventoryLocation.location_quantity} ${InventoryLocation.convertToStr} 
									<c:out value=" ${InventoryLocation.parttype}  " />
								</span>
							</h4>
							<div class="row">
								<c:if test="${!empty Inventory}">
									<c:forEach items="${Inventory}" var="Inventory">
								<input type="hidden" id="idd" value="${Inventory.inventory_id}">
										<c:if test="${Inventory.location  == InventoryLocation.location}">
											<div class="col-md-3 col-sm-4 col-xs-12">
											<c:if test="${showSubLocation}">
												<center><h5>
													<c:out value="${Inventory.subLocation}  "></c:out>
												</h5>
												</center>
											</c:if>
												<div class="box box-partheader">
													<div class="partboxheader">
														<div class="partAction">
															<div class="description-block">
																<div class="btn-group">
																	<a class="btn btn-default btn-sm dropdown-toggle"
																		data-toggle="dropdown" href="#"> <span
																		class="fa fa-ellipsis-v"></span>
																	</a>
																	<%-- <ul class="dropdown-menu pull-right">

																		<li><sec:authorize
																				access="hasAuthority('EDIT_INVENTORY')">
																				<a
																					href="editInventory.in?inventory_id=${Inventory.inventory_id}"
																					class="confirmation"
																					onclick="return confirm('Are you sure you Want to Edit Part?')">
																					<span class="fa fa-pencil"></span> Edit Inventory
																				</a>
																			</sec:authorize></li> 
																		<li><sec:authorize
																				access="hasAuthority('DELETE_INVENTORY')">
																				<a
																					href="deleteInventory.in?inventory_id=${Inventory.inventory_id}"
																					class="confirmation"
																					onclick="return confirm('Are you sure you Want to delete Part?')">
																					<span class="fa fa-trash"></span> Delete
																				</a>
																			</sec:authorize></li>
																	</ul>--%>
																</div>
															</div>
														</div>
														<div class="partInDate">
															<div class="description-block">
																<h6 class="partInDate-header">Invoice Date</h6>
																<span class="partInDate-body"><c:out
																		value="${Inventory.invoice_date}" /></span>
																<c:if test="${REQPID != 0}">
																	<sec:authorize
																		access="hasAuthority('TRANSFER_INVENTORY')">
																		<c:if test="${REQPID != null}">
																			<a class="btn btn-default btn-sm"
																				href="transferInventory.in?INVID=${Inventory.inventory_id}&REQPID=${REQPID}"
																				class="confirmation"
																				onclick="return confirm('Are you sure you Want to Transfer Part?')">
																				<span class="fa fa-exchange"></span>
																			</a>
																		</c:if>
																	</sec:authorize>
																</c:if>
															</div>

														</div>
														<div class="partId">
															<div class="description-block">
																<h6 class="partInDate-header">Part ID</h6>
																<span class="partInDate-body"><a
																	href="showDetailsInventory.in?inventory_id=${Inventory.inventory_id}"
																	data-toggle="tip"
																	data-original-title="Click Inventory INFO"><c:out
																			value="${Inventory.inventory_id}" /></a></span>
															</div>
														</div>
													</div>
													<div class="partDetails">
														<dl>
															<dt>Part Name :</dt>
															<dd>
																<a
																	href="showDetailsInventory.in?inventory_id=${Inventory.inventory_id}"><c:out
																		value="${Inventory.partnumber} - " /> <c:out
																		value="${Inventory.partname}" /></a>
															</dd>
															<dt>Make :</dt>
															<dd>
																<c:out value="${Inventory.make}" />
															</dd>
															<%-- <dt>Location :</dt>
															<dd>
																<c:out value="${Inventory.location}" />
															</dd> --%>
															<dt>Vendor :</dt>
															<dd>
																<c:choose>
																	<c:when test="${Inventory.vendor_id !=0}">
																		<a
																			href="${Inventory.vendorTypeId}/ShowVendor.in?vendorId=${Inventory.vendor_id}&page=1"
																			target="_blank"><c:out
																				value="${Inventory.vendor_name} - " /> <c:out
																				value="${Inventory.vendor_location}" /></a>
																	</c:when>
																	<c:otherwise>
																		<c:out value="${Inventory.vendor_name}  -  " />
																		<c:out value="${Inventory.vendor_location}" />
																	</c:otherwise>
																</c:choose>
															</dd>
															<dt>PO-NO : </dt>
															<dd>
																	<c:choose>
																		<c:when test="${Inventory.purchaseorder_Number != null && Inventory.purchaseorder_Number != 0}">
																		<abbr data-toggle="tip" data-original-title="PO Number">
																			<a
																				href="<c:url value="/PurchaseOrders_Parts.in?ID=${Inventory.purchaseorder_id}"/>"
																				data-toggle="tip"
																				data-original-title="Click Purchase Order Info"><c:out
																					value="PO-${Inventory.purchaseorder_Number}   -  " /></a>
																			</abbr>
																		</c:when>
																		<c:otherwise>
																			<c:out
																				value="PO-${Inventory.purchaseorder_Number}   -  " />
																		</c:otherwise>
																	</c:choose>
															</dd>
															<dt>Invoice No :</dt>
															<dd>
																 <abbr data-toggle="tip"
																	data-original-title="Invoice Number"><%-- <c:out
																		value="${Inventory.invoice_number}" /> --%>
																	<a
																	href="<c:url value="/showInvoice.in?Id=${Inventory.partInvoiceId}"/>"
																	data-toggle="tip"
																	data-original-title="Click For Part Invoice Info"><c:out
																		value="${Inventory.invoice_number}" /></a>	
																		
																</abbr>
															</dd>
														</dl>
													</div>
													<div class="box-footer">
														<div class="partboxheader">
															<div class="partInAmout">
																<dl class="partInAmout-dl">
																	<dt>In-Amount :</dt>
																	<dd>
																		<abbr data-toggle="tip"
																			data-original-title="Invoice Amount"> <i
																			class="fa fa-inr"></i> 
																				 <fmt:formatNumber value="${Inventory.invoice_amount}" maxFractionDigits="2"/>
																		</abbr>
																	</dd>
																	<dt>Unit Price :</dt>
																	<dd>
																		<i class="fa fa-inr"></i>
																		<c:out value=" ${Inventory.unitprice}" />
																	</dd>
																	<dt>Discount :</dt>
																	<dd>
																		<c:out value="${Inventory.discount} %" />
																	</dd>
																	<dt>GST :</dt>
																	<dd>
																		<c:out value="${Inventory.tax} %" />
																	</dd>
																	<dt>Total :</dt>
																	<dd>
																		<i class="fa fa-inr"></i>
																		<fmt:formatNumber value=" ${Inventory.total}" maxFractionDigits="2" />
																	</dd>
																</dl>
															</div>
															<div class="partAction">
																<h4>
																	<c:out value="${Inventory.quantity}" />
																</h4>
																<c:out value="${Inventory.convertToStr}" />
																<c:out value="${Inventory.unittype}" />
															</div>
														</div>
													</div>
												</div>
											</div>
										</c:if>
									</c:forEach>
								</c:if>
							</div>
						</c:forEach>
					</c:if>
				</div>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabsShowVehicle.js" />"></script>
</div>