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

					<a href="<c:url value="/RetreadFilter"/>"
						class="btn btn-info btn-sm"><span class="fa fa-search">
							Retread Filter</span></a> <a href="<c:url value="/ScrapFilter"/>"
						class="btn btn-danger btn-sm"><span class="fa fa-search">
							Scrap Filter</span></a>
					<sec:authorize access="hasAuthority('ADD_TYRE_INVENTORY')">
						<a href="<c:url value="/addTyreInventory.in"/>"
							class="btn btn-success btn-sm"><span class="fa fa-plus">
								Create Tyre Inventory</span></a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_TYRE_INVENTORY')">
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
								href="<c:url value="/TyreRetreadNew/1"/>">TYRE RETREAD INVOICE</a></li>

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
							<c:url var="firstUrl" value="/TyreInventoryNew/1" />
							<c:url var="lastUrl"
								value="/TyreInventoryNew/${deploymentLog.totalPages}" />
							<c:url var="prevUrl"
								value="/TyreInventoryNew/${currentIndex - 1}" />
							<c:url var="nextUrl"
								value="/TyreInventoryNew/${currentIndex + 1}" />
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
										<c:url var="pageUrl" value="/TyreInventoryNew/${i}" />
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
	<script type="text/javascript">$(document).ready(function(){var e=document.getElementById("statues").value;switch(e){case"ALL":document.getElementById("All").className="active";break;case e:document.getElementById(e).className="active"}});</script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabsShowVehicle.js" />"></script>
</div>