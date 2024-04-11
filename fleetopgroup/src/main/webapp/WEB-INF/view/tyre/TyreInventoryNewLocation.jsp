<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/TyreInventory/1.in"/>">New Tyre Inventory</a>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_TYRE_INVENTORY')">
						<a class="btn btn-default btn-sm" data-toggle="tip"
							data-original-title="Download Import XLSX Format. When Import Don't Remove the header"
							href="${pageContext.request.contextPath}/downloadinventorytyredocument.in">
							<i class="fa fa-download"></i>
						</a>
						<button type="button" class="btn btn-default btn-sm"
							data-toggle="modal" data-target="#addImport" data-whatever="@mdo">
							<span class="fa fa-file-excel-o"> Import</span>
						</button>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_TYRE_INVENTORY')">
						<a class="btn btn-info btn-sm"
							href="<c:url value="/InventoryTyreReport.in"/>"> <span
							class="fa fa-search "></span> Search
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('ADD_TYRE_INVENTORY')">
						<a href="<c:url value="/addTyreInventory.in"/>"
							class="btn btn-success btn-sm"><span class="fa fa-plus">
								Create Tyre Inventory</span></a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('ADD_TYRE_INVENTORY')">
					<a href="<c:url value="/addVendor.in"/>"
							class="btn btn-success btn-sm"><span class="fa fa-plus">
								Create Vendor</span></a>	
						<a href="<c:url value="/TyreExpenseDetails.in"/>"
							class="btn btn-success btn-sm"><span class="fa fa-plus">
								Tyre Expense</span></a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('ADD_TYRE_INVENTORY')">
					<div class="btn-group">
						<a class="btn btn-default btn-sm dropdown-toggle"
							data-toggle="dropdown" href="#"> Filter
						</a>
					<ul class="dropdown-menu pull-right">
						<li>
							<sec:authorize access="hasAuthority('ADD_SOLD_FILTER')">
								<a href="<c:url value="/soldFilter.in"/>" class="btn btn-sm">
								<span class="fa fa-search">Sold Filter</span></a>
							</sec:authorize>
						</li>
						<li>
							<sec:authorize access="hasAuthority('ADD_TYRE_RETREAD')">
								<a href="<c:url value="/RetreadFilter"/>" class="btn btn-sm">
								<span class="fa fa-search">Retread Filter</span></a>
							</sec:authorize>
						</li>
						<li>
							<sec:authorize access="hasAuthority('ADD_TYRE_SCRAP')">
								<a href="<c:url value="/ScrapFilter"/>" class="btn btn-sm">
								<span class="fa fa-search">Scrap Filter</span></a>
							</sec:authorize>
						</li>
					</ul>
					</div>
					</sec:authorize>
					<div class="btn-group">
						<a class="btn btn-default btn-sm dropdown-toggle"
							data-toggle="dropdown" href="#"> Transfer
						</a>
					<ul class="dropdown-menu pull-right">
						<li>
					<sec:authorize access="hasAuthority('TRANSFER_MULTI_TYRE')">
						<a class="btn btn-sm"
							href="<c:url value="/multiTransferTyreInventory.in"/>"> <span
							class="fa fa-exchange"></span>Transfered Multiple Tyre
						</a>
					</sec:authorize>
						</li>
						<li>
					<sec:authorize access="hasAuthority('RECEIVE_MULTI_TYRE')">
						<a class="btn btn-sm"
							href="<c:url value="/receiveMultipleTyreTransfered.in"/>"> <span
							class="fa fa-check-circle "></span> Receive Transfered Tyre
						</a>
					</sec:authorize>
					</li>
					</ul>
					</div>
				</div>
			</div>
		</div>
	</section>

	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_TYRE_INVENTORY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_TYRE_INVENTORY')">
			<div class="row">

				<%-- <div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-list-alt"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Available Tyre</span><span
								class="info-box-number">${TyreQuentity}</span>

						</div>
					</div>
				</div> --%>
				<div class="col-md-4 col-sm-4 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-flag-o"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">${location} Tyre</span> <input
								type="hidden" value="${location}" id="statues"> <span
								class="info-box-number">${TyreQuentity}</span>
						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-4 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Tyre No</span>
							<form action="<c:url value="/searchTyreInventory.in"/>"
								method="post">
								<div class="input-group">
									<input class="form-text" name="Search" type="text"
										required="required" placeholder="Only Tyre No, Size">
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
			</div>
			
			<div class="row">
				<c:if test="${!empty availableCount}">
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-industry"></i></span>
						<div class="info-box-content">
							<a href="#" onclick="getTyreList(1,1,${locationId});">
							<span class="info-box-text">Available Tyre</span> <span
								class="info-box-number">${availableCount}</span>
								</a>
						</div>
					</div>
				</div>
				</c:if>
				<c:if test="${!empty serviceCount}">
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-industry"></i></span>
						<div class="info-box-content">
						<a href="#" onclick="getTyreList(1,2,${locationId});">
							<span class="info-box-text">Tyre In Service</span> <span
								class="info-box-number">${serviceCount}</span>
								</a>
						</div>
					</div> 
				</div>
				</c:if>
				<c:if test="${!empty scrapedCount}">
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-industry"></i></span>
						<div class="info-box-content">
						<a href="#" onclick="getTyreList(1,3,${locationId});">
							<span class="info-box-text">Scraped Tyre</span> <span
								class="info-box-number">${scrapedCount}</span>
								</a>
						</div>
					</div>
				</div>
				</c:if>
			</div>
			<c:if test="${param.ScrapSuccess eq true}">
				<div class="alert alert-success">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Tyre Inventory Scrap update Successfully.
				</div>
			</c:if>

			<c:if test="${param.danger eq true}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Tyre Inventory Already Exists.
				</div>
			</c:if>
			<div class="row">
				<div class="col-md-12 col-sm-12 col-xs-12">
					<%-- <div class="main-tabs">
						<ul class="nav nav-pills">

							<li class="active"><a
								href="<c:url value="/TyreInventoryNew/1"/>">Tyre Inventory </a></li>
							

						</ul>
					</div> --%>
					<div class="main-tabs">
						<ul class="nav nav-pills">
							<li role="presentation" id="All" class=""><a
								href="<c:url value="/TyreInventory/1"/>">TYRE INVOICE</a></li>
							<li role="presentation" id="TYRE" class=""><a
								href="<c:url value="/TyreRetreadNew/1"/>">TYRE RETREAD
									INVOICE</a></li>
							<sec:authorize access="hasAuthority('ADD_SOLD_FILTER')">		
							<li role="presentation" id="sold" class=""><a
								href="<c:url value="/allTyreSoldInvoice"/>">TYRE SOLD INVOICE</a></li>	
							</sec:authorize>		

							<c:if test="${!empty PartLocations}">
								<c:forEach items="${PartLocations}" var="PartLocations">
									<li class="tab-link" id="${PartLocations.partlocation_name}"><a
										class="btn btn-link"
										href="<c:url value="/locationTyreInventory/1.in?loc=${PartLocations.partlocation_id}"/>">
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
										<table id="InventoryTable" class="table">
											<thead>
												<tr>
													<th class="fit ar">Tyre NO</th>
													<th class="fit ar">Manufacturer</th>
													<th class="fit ar">Model</th>
													<th class="fit ar">Tyre Size</th>
													<th class="fit ar">Location</th>
													<c:if test="${showSubLocationForMainLocation}">
													<th class="fit ar">Sub Location</th>
													</c:if>
													<th class="fit ar">Status</th>
													<th class="fit ar">usage</th>
													<!-- <th class="fit ar">Actions</th> -->

												</tr>
											</thead>
											<tbody>
												<c:if test="${!empty Tyre}">
													<c:forEach items="${Tyre}" var="Tyre">
														<tr>
															<td class="fit ar"><a
																href="<c:url value="/showTyreInfo.in?Id=${Tyre.TYRE_ID}"/>"
																data-toggle="tip"
																data-original-title="Click Tyre Inventory INFO"> <c:choose>
																		<c:when test="${Tyre.TYRE_RETREAD_COUNT == 0}">
																			<span class="label label-pill label-success"><c:out
																					value="NT" /></span>
																		</c:when>
																		<c:otherwise>
																			<span class="label label-pill label-warning"><c:out
																					value="${Tyre.TYRE_RETREAD_COUNT}-RT" /></span>
																		</c:otherwise>
																	</c:choose> <c:out value="${Tyre.TYRE_NUMBER}" /></a></td>
															<td class="fit ar"><a
																href="<c:url value="/showTyreInfo.in?Id=${Tyre.TYRE_ID}"/>"
																data-toggle="tip"
																data-original-title="Click Inventory INFO"><c:out
																		value="${Tyre.TYRE_MANUFACTURER}" /> </a></td>
															<td class="fit ar"><c:out value="${Tyre.TYRE_MODEL}" /></td>

															<td class="fit ar"><c:out value="${Tyre.TYRE_SIZE}" /></td>

															<td class="fit ar"><c:out
																	value="${Tyre.WAREHOUSE_LOCATION}" /></td>
															<c:if test="${showSubLocationForMainLocation}">
																<td class="fit ar"><c:out
																	value="${Tyre.subLocation}" /></td>
															</c:if>
															<td class="fit ar"><c:choose>
																	<c:when
																		test="${Tyre.TYRE_ASSIGN_STATUS == 'AVAILABLE'}">
																		<span class="label label-pill label-success"><c:out
																				value="${Tyre.TYRE_ASSIGN_STATUS}" /></span>
																	</c:when>
																	<c:when test="${Tyre.TYRE_ASSIGN_STATUS == 'SCRAPED'}">
																		<span class="label label-pill label-danger"><c:out
																				value="${Tyre.TYRE_ASSIGN_STATUS}" /></span>
																	</c:when>
																	<c:otherwise>
																		<span class="label label-pill label-warning"><c:out
																				value="${Tyre.TYRE_ASSIGN_STATUS}" /></span>
																	</c:otherwise>
																</c:choose></td>

															<td class="fit ar"><c:out
																	value="${Tyre.TYRE_USEAGE}" /></td>
														</tr>
													</c:forEach>
												</c:if>
											</tbody>
										</table>
									</div>
								</div>
							</div>
							<c:url var="firstUrl"
								value="/locationTyreInventory/1.in?loc=${locationId}" />
							<c:url var="lastUrl"
								value="/locationTyreInventory/${deploymentLog.totalPages}.in?loc=${locationId}" />
							<c:url var="prevUrl"
								value="/locationTyreInventory/${currentIndex - 1}.in?loc=${locationId}" />
							<c:url var="nextUrl"
								value="/locationTyreInventory/${currentIndex + 1}.in?loc=${locationId}" />
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
										<c:url var="pageUrl"
											value="/locationTyreInventory/${i}.in?loc=${locationId}" />
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
	
	<div class="content" >
<div class="modal fade" id="tyreModelList" role="dialog">
	<div class="modal-dialog" style="width:750px;">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header" style="text-align: center;">
				<span id="headerData"></span>
			</div>
			<div class="modal-body">
				<table style="width:100%;" id="list1" border="2" ></table>
			</div>
			<div class="text-center">
				<ul id="navigationBar6" class="pagination pagination-lg pager"> </ul>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<span id="Close"><spring:message code="label.master.Close" /></span>
				</button>
			</div>
		</div>
	</div>
</div>
</div>
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
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabsShowVehicle.js" />"></script>
	<script type="text/javascript" 
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IT/viewInventoryTyreList.js"/>"></script>		
</div>