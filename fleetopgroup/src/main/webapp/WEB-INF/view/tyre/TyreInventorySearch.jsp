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
						href="<c:url value="/TyreInventory/1.in"/>">Tyre Inventory</a> / <a
						href="<c:url value="/InventoryTyreReport.in"/>">Search
						Inventory</a> / <span>Search Tyre Inventory</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-link btn-sm"
						href="<c:url value="/TyreInventory/1.in"/>"> Cancel
					</a>
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
				<div class="col-md-12 col-sm-12 col-xs-12">
					<div id="AllInventory" class="tab-content2 current">
						<div class="main-body">
							<c:if test="${!empty Tyre}">
								<div class="box">
									<div class="box-body">
										<div class="table-responsive">
											<table id="VendorTable" class="table">
												<thead>
													<tr>
														<th class="fit ar">ID</th>
														<th class="fit ar">Tyre NO</th>
														<th class="fit ar">Manufacturer</th>
														<th class="fit ar">Model</th>
														<th class="fit ar">Tyre Size</th>
														<th class="fit ar">Amount</th>
														<th class="fit ar">Location</th>
														<th class="fit ar">Status</th>
														<th class="fit ar">Actions</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${Tyre}" var="Tyre">
														<tr>
															<td class="fit ar"><a target="_blank" 
																href="showTyreInfo.in?Id=${Tyre.TYRE_ID}"
																data-toggle="tip"
																data-original-title="Click Tyre Inventory INFO"><c:out
																		value="T-${Tyre.TYRE_IN_NUMBER}" /></a></td>
															<td class="fit ar"><a target="_blank" 
																href="showTyreInfo.in?Id=${Tyre.TYRE_ID}"
																data-toggle="tip"
																data-original-title="Click Tyre Inventory INFO"><c:out
																		value="${Tyre.TYRE_NUMBER}" /></a></td>
															<td class="fit ar"><a target="_blank" 
																href="showTyreInfo.in?Id=${Tyre.TYRE_ID}"
																data-toggle="tip"
																data-original-title="Click Inventory INFO"><c:out
																		value="${Tyre.TYRE_MANUFACTURER}" /> </a></td>
															<td class="fit ar"><c:out value="${Tyre.TYRE_MODEL}" /></td>

															<td class="fit ar"><c:out value="${Tyre.TYRE_SIZE}" /></td>
															<td class="fit ar"><c:out
																	value="${Tyre.TYRE_AMOUNT}" /></td>
															<td class="fit ar"><c:out
																	value="${Tyre.WAREHOUSE_LOCATION}" /></td>
															<td class="fit ar"><c:out
																	value="${Tyre.TYRE_ASSIGN_STATUS}" /></td>
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
																					href="showInventory.in?inventory_all_id=${Tyre.TYRE_ID}">
																					<span class="fa fa-pencil"></span> Show Quantity
																				</a>
																			</sec:authorize></li>
																		<li><sec:authorize
																				access="hasAuthority('DELETE_INVENTORY')">
																				<a
																					href="deleteAllInventory.in?inventory_all_id=${Tyre.TYRE_ID}"
																					class="confirmation"
																					onclick="return confirm('Are you sure you Want to delete Part?')">
																					<span class="fa fa-trash"></span> Delete
																				</a>
																			</sec:authorize></li>
																	</ul>
																</div>
															</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</c:if>
							<c:if test="${empty Tyre}">
								<div class="main-body">
									<p class="lead text-muted text-center t-padded">
										<spring:message code="label.master.noresilts" />
									</p>

								</div>
							</c:if>
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
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VA/NewVendorlanguage.js" />"></script>
</div>