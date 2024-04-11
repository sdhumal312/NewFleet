<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/tabs.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/NewInventory/1.in"/>">New Inventory</a> Low Stock Parts Details</small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_INVENTORY')">
						<a href="<c:url value="/addInventory.in"/>"
							class="btn btn-success btn-sm"><span class="fa fa-plus">
								Create Inventory</span></a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_INVENTORY')">
						<a class="btn btn-info btn-sm"
							href="<c:url value="/InventoryReport.in"/>"> <span
							class="fa fa-search "></span> Search
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('TRANSFER_INVENTORY')">
						<a
							href="<c:url value="/YTIHISTORY/1.in"/>"
							class="btn btn-warning btn-sm">Your Transfer History</a>
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
				<div class="col-md-3 col-sm-4 col-xs-3">
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
			
			<div class="row">
				<div class="col-xs-10">
					 <div class="main-tabs">
					 <input type="hidden" value="${activeLocation}" id="statues">
						<ul class="nav nav-pills">
							<c:if test="${!empty PartLocations}">
							<c:forEach items="${PartLocations}" var="PartLocations">
								<li class="tab-link" id="${PartLocations.partlocation_id}"><a
									class="btn btn-link"
									href="<c:url value="/getLowStockDetails/${PartLocations.partlocation_id}/1.in"/>">
										${PartLocations.partlocation_name}</a></li>
							</c:forEach>
							</c:if>
						</ul>
					</div> 
					<br>

					<div id="AllInventory" class="tab-content2 current">
						<div class="main-body">
							<div class="box">
								<div class="box-body">
									<div class="table-responsive">
										<table id="InventoryTable" class="table table-hover table-bordered">
											<thead>
												<tr>
													<th class="fit ar">Location</th>
													<th class="fit ar">Part_Name</th>
													<th class="fit ar">Quantity</th>
													<th class="fit ar">Low Stock Level</th>
													<th class="fit ar">ReOrder Quantity</th>
													<th class="fit ar">Actions</th>

												</tr>
											</thead>
											<tbody>
												<c:if test="${!empty inventoryLocationList}">
													<c:forEach items="${inventoryLocationList}" var="InventoryAll">
														<tr>
															<td class="fit ar"><c:out value="${InventoryAll.location}" /></td>
															<td class="fit ar"><c:out value="${InventoryAll.partname} - ${InventoryAll.partnumber}" /></td>
															<td class="fit ar"><c:out value="${InventoryAll.location_quantity}" /></td>
															<td class="fit ar"><c:out value="${InventoryAll.lowstocklevel}" /></td>
															<td class="fit ar"><c:out value="${InventoryAll.reorderquantity}" /></td>
													<sec:authorize access="hasAuthority('ADD_PURCHASE')">
														<td>
															<a href="<c:url value="/addPurchaseOrder.in?purchaseorder_shiplocation=${InventoryAll.location}&purchaseorder_shiplocation_id=${InventoryAll.locationId}"/>"
															 target="_blank"	class="btn btn-success btn-sm"><span class="fa fa-plus">
																Create Purchase Order</span></a>
														</td>
													</sec:authorize>
														<%-- 			<td class="fit ar"><c:out
																	value="${InventoryAll.category}" /></td>
													<td class="fit ar"><a
																href="<c:url value="/showInventory.in?inventory_all_id=${InventoryAll.inventory_all_id}"/>"
																data-toggle="tip"
																data-original-title="Click Inventory INFO"><c:out
																		value="${InventoryAll.all_quantity}" /></a></td>

															<td class="fit ar">
																<div class="btn-group">
																	<a class="btn btn-default btn-sm dropdown-toggle"
																		data-toggle="dropdown" href="#"> <span
																	class="fa fa-ellipsis-v"></span>
																	</a>
																	<ul class="dropdown-menu pull-right">
																		<li><sec:authorize
																				access="hasAuthority('VIEW_INVENTORY')">
																				<a
																					href="<c:url value="/showInventory.in?inventory_all_id=${InventoryAll.inventory_all_id}"/>">
																					<span class="fa fa-pencil"></span> Show Quantity
																				</a>
																			</sec:authorize></li>
																		<li><sec:authorize
																				access="hasAuthority('DELETE_INVENTORY')">
																				<a
																					href="<c:url value="/deleteAllInventory.in?inventory_all_id=${InventoryAll.inventory_all_id}"/>"
																					class="confirmation"
																					onclick="return confirm('Are you sure you Want to delete Part?')">
																					<span class="fa fa-trash"></span> Delete
																				</a>
																			</sec:authorize></li>
																	</ul>
																</div>
															</td> --%>
														</tr>
													</c:forEach>
												</c:if>
											</tbody>
										</table>
									</div>
								</div>
							</div>
							<c:url var="firstUrl" value="/getLowStockDetails/${activeLocation}/1" />
							<c:url var="lastUrl"
								value="/getLowStockDetails/${activeLocation}/${deploymentLog.totalPages}" />
							<c:url var="prevUrl" value="/getLowStockDetails/${activeLocation}/${currentIndex - 1}" />
							<c:url var="nextUrl" value="/getLowStockDetails/${activeLocation}/${currentIndex + 1}" />
							<div class="text-center">
								<ul class="pagination pagination-lg pager">
									<c:choose>
										<c:when test="${currentIndex == 1}">
											<li class="disabled"><a href="#">&lt;&lt;</a></li>
											<li class="disabled"><a href="#">&lt;</a></li>
										</c:when>
										<c:otherwise>
											<li><a href="${firstUrl}">&lt;&lt;</a></li>
											<li><a href="${prevUrl}">&lt;</a></li>
										</c:otherwise>
									</c:choose>
									<c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
										<c:url var="pageUrl" value="/getLowStockDetails/${activeLocation}/${i}" />
										<c:choose>
											<c:when test="${i == currentIndex}">
												<li class="active"><a href="${pageUrl}"><c:out
															value="${i}" /></a></li>
											</c:when>
											<c:otherwise>
												<li><a href="${pageUrl}"><c:out value="${i}" /></a></li>
											</c:otherwise>
										</c:choose>
									</c:forEach>
									<c:choose>
										<c:when test="${currentIndex == deploymentLog.totalPages}">
											<li class="disabled"><a href="#">&gt;</a></li>
											<li class="disabled"><a href="#">&gt;&gt;</a></li>
										</c:when>
										<c:otherwise>
											<li><a href="${nextUrl}">&gt;</a></li>
											<li><a href="${lastUrl}">&gt;&gt;</a></li>
										</c:otherwise>
									</c:choose>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabsShowVehicle.js" />"></script>
		<script type="text/javascript">
		$(document).ready(function() {
			var e = document.getElementById("statues").value;
			switch (e) {
			case "ALL":
				document.getElementById("All").className = "active";
				break;
			case e:
				document.getElementById(e).className = "active"
			}
		});
	</script>
</div>