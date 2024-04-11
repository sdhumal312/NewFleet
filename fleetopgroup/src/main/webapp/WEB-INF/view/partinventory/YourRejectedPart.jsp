<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/NewInventory/1.in"/>">New Inventory</a> / <a
						href="<c:url value="/PartRequisition/1/1.in"/>">Create Part
						Requisition</a>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_REQUISITION_INVENTORY')">
						<a href="<c:url value="/addPartRequisition.in"/>"
							class="btn btn-success btn-sm"><span class="fa fa-plus">
								Create Part Requisition</span></a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('TRANSFER_INVENTORY')">
						<a href="<c:url value="/YTIHISTORY/1.in"/>"
							class="btn btn-warning btn-sm">Your Transfer History</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('ADD_REQUISITION_INVENTORY')">
						<a href="<c:url value="/SearchPartRequisition.in"/>"
							class="btn btn-success btn-sm"><span class="fa fa-plus">
								Serach Part Requisition</span></a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_REQUISITION_INVENTORY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_REQUISITION_INVENTORY')">
			<div class="row">
				<div class="col-md-4 col-sm-4 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-flag-o"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Total Rejected</span> <span
								class="info-box-number">${rejectedCount}</span>
						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Inventory</span>
							<form action="<c:url value="/searchInvenPartReq.in"/>"
								method="post">
								<div class="input-group">
									<span class="input-group-addon"> <span
										aria-hidden="true">IR-</span></span> <input class="form-text"
										id="vehicle_registrationNumber" name="SearchInveReq" type="number"
										min="1" required="required" placeholder="IR-ID">
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
				<div class="main-body">
					<div class="box">
						<div class="box-body">

							<div class="row">
								<ul class="nav nav-tabs" role="tablist">
									<li role="presentation" id="1"><a
										href="<c:url value="/PartRequisition/1/1.in"/>">OPEN</a></li>
									<li role="presentation" id=2><a
										href="<c:url value="/PartRequisition/2/1.in"/>">REQUISTION</a></li>
									<li role="presentation" id="3"><a
										href="<c:url value="/PartRequisition/3/1.in"/>">TRANSFERED</a></li>
									<li role="presentation" id="4"><a
										href="<c:url value="/PartRequisition/4/1.in"/>">COMPLETED</a></li>
									<li role="presentation" id="5"><a
										href="<c:url value="/RejectedPartRequisition/1.in"/>">REJECTED</a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-11">
					<div id="AllInventory" class="tab-content2 current">
						<div class="main-body">
							<div class="box">
								<div class="box-body">
									<div class="table-responsive">
										<table id="InventoryTable" class="table table-hover table-bordered">
											<thead>
												<tr>
													<th class="fit ar">ID</th>
													<th class="fit ar">Location</th>
													<th class="fit ar">Rejected Parts</th>
													<th class="fit ar">Quantity</th>
													<th class="fit ar">Sent_By</th>
													<th class="fit ar">Rejected Date</th>
													<th class="fit ar">Req.Assign</th>
													<th class="fit ar">Status</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${!empty InventoryTransfer}">
													<c:forEach items="${InventoryTransfer}" var="InventoryTransfer">
														<tr>
															<td class="fit ar"><c:out 
																	value="IR-${InventoryTransfer.INVRID_NUMBER }"/></td>
															<td class="fit ar"><c:out
																	value="${InventoryTransfer.transfer_from_location}" /></td>
															<td class="fit ar"><c:out
																	value="${InventoryTransfer.transfer_partname}" /></td>
															<td class="fit ar"><c:out
																	value="${InventoryTransfer.transfer_quantity}" /></td>
															<td class="fit ar"><c:out
																	value="${InventoryTransfer.transfer_by}" /></td>
															<td class="fit ar"><c:out
																	value="${InventoryTransfer.LASTUPDATED_DATE}" /></td>
															<td class="fit ar"><c:out
																	value="${InventoryTransfer.transfer_receivedby}" /></td>
															<td class="fit ar">
															<span class="label label-default label-danger">
															<c:out value="${InventoryTransfer.TRANSFER_STATUS}" /></span>
															</td>
															</tr>
													</c:forEach>
												</c:if>
											</tbody>
										</table>
									</div>
								</div>
							</div>
							<c:url var="firstUrl" value="/RejectedPartRequisition/1" />
							<c:url var="lastUrl"  value="/RejectedPartRequisition/${deploymentLog.totalPages}" />
							<c:url var="prevUrl" value="/RejectedPartRequisition/${currentIndex - 1}" />
							<c:url var="nextUrl" value="/RejectedPartRequisition/${currentIndex + 1}" />
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
										<c:url var="pageUrl" value="/PartRequisition/${SelectStatus}/${i}" />
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
</div>
<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/part/ShowPartRequisition.js" />"></script>