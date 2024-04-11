<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/tabs.css" />">
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
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/NewInventory/1.in"/>">New Inventory</a></small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_INVENTORY')">
						<a href="addInventory.in" class="btn btn-success"><span
							class="fa fa-plus"> Create Inventory</span></a>
					</sec:authorize>
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
				<div class="col-md-2 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-industry"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Total Inventory</span> <span
								class="info-box-number">${InventoryCount}</span>
						</div>
					</div>
				</div>
				<div class="col-md-2 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-list-alt"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">${CountName} Quantity</span> <input
								type="hidden" value="${CountName}" id="statues">
							<c:forEach items="${InventoryTotalqtyCount}"
								var="InventoryTotalqtyCount">
								<span class="info-box-number">${InventoryTotalqtyCount}</span>
							</c:forEach>
						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Inventory</span>
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
					</div>
				</div>
				<div class="col-md-3 col-sm-4 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Invoice</span>
							<form action="<c:url value="/searchAllInventoryInvoice.in"/>"
								method="post">
								<div class="input-group">
									<input class="form-text" id="vehicle_registrationNumber"
										name="SearchAllInvInvoice" type="text" required="required"
										placeholder="Invoice Number"> <span
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
					</div>
				</div>
			</div>
			<c:if test="${param.saveInventory eq true}">
				<div class="alert alert-success">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Inventory Created Successfully.
				</div>
			</c:if>
			<c:if test="${param.updateInventory eq true}">
				<div class="alert alert-success">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Inventory Updated Successfully.
				</div>
			</c:if>
			<c:if test="${param.deleteInventory eq true}">
				<div class="alert alert-success">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Inventory Quantity Deleted Successfully.
				</div>
			</c:if>
			<c:if test="${deleteInventoryChild}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					Please Delete First From Inventory Location .....
				</div>
			</c:if>
			<c:if test="${param.alreadyInventory eq true}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Inventory Already Exists.
				</div>
			</c:if>
			<c:if test="${param.danger eq true}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Inventory Already Exists.
				</div>
			</c:if>
			<c:if test="${param.dangerLocation eq true}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					Please delete this part inside all location Quantity...
				</div>
			</c:if>
			<c:if test="${param.dangerAllInventory eq true}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Inventory Already Exists.
				</div>
			</c:if>
			<c:if test="${duplicateInventory}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Inventory Already Exists Part Number = ${alreadyInventory} .
				</div>
			</c:if>
			<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12">
					<div class="main-tabs">
						<ul class="nav nav-pills">
							<!-- <li class=""><a href="ShowClosedPurchase Order.in">Purchase Today </a></li> -->
							<li role="presentation" id="All"><a href="NewInventory/1.in">ALL</a></li>
							<c:forEach items="${PartLocations}" var="PartLocations">
								<li class="tab-link" id="${PartLocations.partlocation_name}"><a
									class="btn btn-link"
									href="locationInventory/1.in?loc=${PartLocations.partlocation_id}">
										${PartLocations.partlocation_name}</a></li>
							</c:forEach>
						</ul>
					</div>
					<br>
					<div id="AllInventory" class="tab-content2 current">
						<div class="main-body">
							<div class="box">
								<div class="box-body">
									<div class="table-responsive">
										<table id="InventoryTable_Location"
											class="table table-striped">
											<thead>
												<tr>
													<th>Part</th>
													<!-- <th class="fit ar">Category</th> -->
													<th class="fit ar">Make</th>
													<th class="fit ar">Location</th>
													<th class="fit ar">Quantity</th>
													<th class="fit ar">U_Price</th>
													<th class="fit ar">Disc</th>
													<th class="fit ar">Tax</th>
													<th class="fit ar">Total</th>
													<!-- <th class="fit ar">PO-No</th> -->
													<th class="fit ar">Invoice No</th>
													<th class="fit ar">Invoice Amount</th>
													<th>Vendor</th>
													<th class="fit ar">Created By</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${!empty Inventory}">
													<c:forEach items="${Inventory}" var="Inventory">
														<tr>
															<td><a
																href="showDetailsInventory.in?inventory_id=${Inventory.inventory_id}"
																data-toggle="tip"
																data-original-title="Click Inventory INFO"><c:out
																		value="${Inventory.partnumber}" /><br> <c:out
																		value="${Inventory.partname}" /></a></td>
															<%-- <td class="fit ar"><c:out
																	value="${Inventory.category}" /></td> --%>
															<td class="fit ar"><c:out value="${Inventory.make}" /></td>
															<td class="fit ar"><c:out
																	value="${Inventory.location}" /></td>
															<td class="fit ar"><h5>
																	<span class="label label-info"><c:out
																			value="${Inventory.quantity}" />${Inventory.convertToStr}</span>
																</h5> <c:out value="${Inventory.unittype}" /></td>
															<td class="fit ar"><i class="fa fa-inr"></i> <c:out
																	value=" ${Inventory.unitprice}" /></td>
															<td class="fit ar"><c:out
																	value="${Inventory.discount} %" /></td>
															<td class="fit ar"><c:out value="${Inventory.tax} %" /></td>
															<td class="fit ar"><i class="fa fa-inr"></i> <c:out
																	value=" ${Inventory.total}" /></td>
															<%-- <td class="fit ar"><abbr data-toggle="tip"
																data-original-title="PO Number"><c:out
																		value="PO-${Inventory.ponumber}" /></abbr></td> --%>
															<td class="fit ar"><abbr data-toggle="tip"
																data-original-title="Invoice Number"><c:out
																		value="${Inventory.invoice_number}" /></abbr></td>
															<td class="fit ar"><abbr data-toggle="tip"
																data-original-title="Invoice Amount"> <i
																	class="fa fa-inr"></i> <c:out
																		value=" ${Inventory.invoice_amount}" />
															</abbr></td>
															<td><c:choose>
																	<c:when test="${Inventory.vendor_id !=0}">
																		<a
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
															<td><c:out value="${Inventory.firstName}" /></td>
														</tr>
													</c:forEach>
												</c:if>
											</tbody>
										</table>
									</div>
								</div>
							</div>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IT/Inventorylanguage.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabsShowVehicle.js" />"></script>
</div>