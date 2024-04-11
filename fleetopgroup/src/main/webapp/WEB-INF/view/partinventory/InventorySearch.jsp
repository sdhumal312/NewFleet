<%@ include file="taglib.jsp"%>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/tabs.css" />">
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
				<div class="col-md-6 col-sm-6 col-xs-12">
					<ul class="tabs">
						<li class="tab-link" data-tab="AllInventory" data-toggle="modal"
							data-target="#processing-modal"><a class="btn btn-link"
							href="NewInventory.in"> All </a></li>
						<c:forEach items="${PartLocations}" var="PartLocations">
							<c:if
								test="${!PartLocations.plName == InventoryLocationHeadername}">
								<li class="current" data-tab="Location" data-toggle="modal"
									data-target="#processing-modal"><a class="btn btn-link"
									href="locationInventory.in?location=${InventoryLocationHeadername}">
										${InventoryLocationHeadername}</a></li>
							</c:if>
							<li class="tab-link" data-tab="${PartLocations.plName}"
								data-toggle="modal" data-target="#processing-modal"><a
								class="btn btn-link"
								href="locationInventory.in?location=${PartLocations.plName}">
									${PartLocations.plName}</a></li>
						</c:forEach>
					</ul>
				</div>
				<div class="col-md-3 col-sm-6 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Inventory</span>
							<form action="searchInventory.in" method="post">
								<div class="input-group">
									<input class="form-text" id="vehicle_registrationNumber"
										name="partnumber" type="text" required="required"
										placeholder="Part NO/Name, Location"> <span
										class="input-group-btn">
										<button type="submit" name="search" id="search-btn"
											class="btn btn-success" data-toggle="modal"
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
					<div class="main-body">
						<h4>Search Inventory Result</h4>
						<div class="box">
							<div class="box-body">
								<table id="InventoryTable"
									class="table table-bordered table-striped">
									<thead>
										<tr>
											<th>Part NO/Name</th>
											<th class="fit ar">Category</th>
											<th class="fit ar">Manufacturer</th>
											<th class="fit ar">Location</th>
											<th class="fit ar">Cost</th>
											<th class="fit ar">Quantity</th>
											<th id="Actions" class="icon">Actions</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty Inventory}">
											<c:forEach items="${Inventory}" var="Inventory">
												<tr>

													<td><a
														href="showInventory.in?inventoryid=${Inventory.inventoryid}"
														data-toggle="tip"
														data-original-title="Click Inventory INFO"
														data-toggle="tip" data-original-title="click Part Info"><c:out
																value="${Inventory.partnumber}" /> <br> <c:out
																value="${Inventory.partname}" /></a></td>
													<td class="fit ar"><c:out
															value="${Inventory.category}" /></td>
													<td class="fit ar"><c:out value="${Inventory.make}" /></td>
													<td class="fit ar"><c:out
															value="${Inventory.location}" /></td>
													<td class="fit ar"><i class="fa fa-inr"> </i> <c:out
															value=" ${Inventory.unitprice}" /> <abbr
														data-toggle="tip" data-original-title="Type"> <c:out
																value="${Inventory.unittype}" /></abbr></td>
													<td class="fit ar"><c:out
															value="${Inventory.quantity}" /></td>

													<td class="icon">
														<div class="btn-group">
															<a class="btn btn-default dropdown-toggle"
																data-toggle="dropdown" href="#"> <span
																class="fa fa-cog"></span> <span class="caret"></span>
															</a>
															<ul class="dropdown-menu pull-right">
																<li><sec:authorize
																		access="hasAuthority('VIEW_INVENTORY')">
																		<a
																			href="editInventory.in?inventoryid=${Inventory.inventoryid}">
																			<span class="fa fa-pencil"></span> Edit
																		</a>
																	</sec:authorize></li>
																<li><sec:authorize
																		access="hasAuthority('DELETE_INVENTORY')">
																		<a
																			href="deleteInventory.in?inventoryid=${Inventory.inventoryid}"
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
										</c:if>
									</tbody>
								</table>
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
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IT/Inventorylanguage.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabsShowVehicle.js" />"></script>

</div>