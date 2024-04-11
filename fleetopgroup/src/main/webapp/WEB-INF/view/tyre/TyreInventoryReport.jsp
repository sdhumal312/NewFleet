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
						href="<c:url value="/TyreInventory/1.in"/>">New Tyre Inventory</a>
					/ <span>Search Tyre Inventory</span>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('VIEW_INVENTORY')">
						<a class="btn btn-info btn-sm"
							href="<c:url value="/InventoryTyreReport.in"/>"> <span
							class="fa fa-search "></span> Search
						</a>
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

				<div class="col-md-offset-3 col-md-3 col-sm-4 col-xs-6">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Tyre Invoice</span>
							<form action="<c:url value="/searchInvoiceInventory.in"/>"
								method="post">
								<div class="input-group">
									<input class="form-text" id="vehicle_registrationNumber"
										name="Search" type="text" value="${TyreSearch}"
										required="required" placeholder="Invoice No, Location">
									<span class="input-group-btn">
										<button type="submit" name="search" id="search-btn"
											class="btn btn-success btn-sm">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-4 col-xs-6">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Tyre</span>
							<form action="<c:url value="/searchTyreInventory.in"/>"
								method="post">
								<div class="input-group">
									<input class="form-text" value="${search}" name="Search"
										type="text" required="required"
										placeholder="Only Tyre No, Size"> <span
										class="input-group-btn">
										<button type="submit" name="search" id="search-btn"
											class="btn btn-success btn-sm">
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
				<div class="col-xs-11">
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
																	<c:choose>
																		<c:when test="${Tyre.TYRE_ASSIGN_STATUS_ID == 1}">
																			<li><sec:authorize
																					access="hasAuthority('DELETE_INVENTORY')">
																					<a
																						href="deleteTyreInventory?Id=${Tyre.TYRE_ID}"
																						class="confirmation"
																						onclick="return confirm('Are you sure you Want to delete Part?')">
																						<span class="fa fa-trash"></span> Delete
																					</a>
																				</sec:authorize></li>
																		</c:when>
																		<c:otherwise>
																			<li><sec:authorize
																					access="hasAuthority('DELETE_INVENTORY')">
																						<span class="fa fa-trash"> ${Tyre.TYRE_ASSIGN_STATUS}</span> 
																				</sec:authorize></li>
																		</c:otherwise>
																		</c:choose>
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
							<!-- Tyre Invoice Report -->
							<c:if test="${!empty TyreInvoice}">
								<div class="box">
									<div class="box-body">
										<div class="table-responsive">
											<table id="VendorTable" class="table">
												<thead>
													<tr>
														<th class="fit ar">ID</th>
														<th class="fit ar">Vendor</th>
														<th class="fit ar">Location</th>
														<th class="fit ar">Invoice</th>
														<th class="fit ar">Amount</th>
														<th class="fit ar">Created By</th>														
														<th class="fit ar">Actions</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${TyreInvoice}" var="TyreInvoice">
														<tr>
															<td class="fit ar"><a
																href="showTyreInventory.in?Id=${TyreInvoice.ITYRE_ID}"
																data-toggle="tip"
																data-original-title="Click Tyre Inventory INFO"><c:out
																		value="${TyreInvoice.ITYRE_NUMBER}" /></a></td>
															<td class="fit ar"><a
																href="showTyreInventory.in?Id=${TyreInvoice.ITYRE_ID}"
																data-toggle="tip"
																data-original-title="Click Inventory INFO"><c:out
																		value="${TyreInvoice.VENDOR_NAME}" /> </a></td>
															<td class="fit ar"><c:out
																	value="${TyreInvoice.WAREHOUSE_LOCATION}" /></td>

															<td class="fit ar"><c:out
																	value="${TyreInvoice.INVOICE_NUMBER}" /></td>

															<td class="fit ar"><c:out
																	value="${TyreInvoice.INVOICE_AMOUNT}" /></td>
															<td class="fit ar"><c:out
																	value="${TyreInvoice.firstName}" /></td>		

															<td class="fit ar">
																<div class="btn-group">
																	<a class="btn btn-default btn-sm dropdown-toggle"
																		data-toggle="dropdown" href="#"> <span
																		class="fa fa-cog"></span> <span class="caret"></span>
																	</a>
																	<ul class="dropdown-menu pull-right">
																		<li><sec:authorize
																				access="hasAuthority('VIEW_INVENTORY')">
																				<a
																					href="showTyreInventory.in?Id=${TyreInvoice.ITYRE_ID}">
																					<span class="fa fa-external-link"
																					aria-hidden="true"></span> Show Tyre Invoice
																				</a>
																			</sec:authorize></li>
																		<li><sec:authorize
																				access="hasAuthority('DELETE_INVENTORY')">
																				<a
																					href="deleteAllInventory.in?inventory_all_id=${TyreInvoice.ITYRE_ID}"
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