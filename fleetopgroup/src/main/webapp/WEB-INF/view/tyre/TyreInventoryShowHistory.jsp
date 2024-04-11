<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/TyreInventoryNew/1.in"/>">New Tyre
						Inventory</a> /
					<c:out value="${Tyre.TYRE_IN_NUMBER}" />
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
			<sec:authorize access="!hasAuthority('VIEW_INVENTORY')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_INVENTORY')">
				<div class="box-body">
					<div class="pull-left">
						<a
							href="${pageContext.request.contextPath}/resources/images/TyreWheel.png"
							class="zoom" data-title="Tyre Photo" data-footer=""
							data-type="image" data-toggle="lightbox"> <img
							src="${pageContext.request.contextPath}/resources/images/TyreWheel.png"
							class="img-rounded" alt="Tyre Photo" width="100" height="100" />
						</a>
					</div>
					<div class="pull-left1">
						<h3 class="secondary-header-title">
							<a href="<c:url value="/showTyreInfo.in?Id=${Tyre.TYRE_ID}"/>">
								<c:out value="${Tyre.TYRE_NUMBER}" /> - <c:out
									value="${Tyre.TYRE_MANUFACTURER}" />
							</a>
						</h3>
						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li><c:choose>
										<c:when test="${Tyre.TYRE_ASSIGN_STATUS == 'OPEN'}">
											<small class="label label-info"><c:out
													value="${Tyre.TYRE_ASSIGN_STATUS}" /></small>
										</c:when>
										<c:when test="${Issues.TYRE_ASSIGN_STATUS == 'REJECT'}">
											<small class="label label-danger"><c:out
													value="${Tyre.TYRE_ASSIGN_STATUS}" /></small>
										</c:when>
										<c:when test="${Issues.TYRE_ASSIGN_STATUS == 'RESOLVED'}">
											<small class="label label-warning"><c:out
													value="${Tyre.TYRE_ASSIGN_STATUS}" /></small>
										</c:when>
										<c:otherwise>
											<small class="label label-success"><c:out
													value="${Tyre.TYRE_ASSIGN_STATUS}" /></small>
										</c:otherwise>
									</c:choose></li>
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="TYRE MANUFACTURER">
								</span> <span class="text-muted"><c:out
											value="${Tyre.TYRE_MANUFACTURER}" /></span></li>
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="category"> </span> <span
									class="text-muted"><c:out value="${Tyre.TYRE_MODEL}" /></span></li>

								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Tyre Size"> </span> <span
									class="text-muted"><c:out value="${Tyre.TYRE_SIZE}" /></span></li>
							</ul>
						</div>
					</div>
				</div>
			</sec:authorize>
		</div>
	</section>
	<!-- Main content -->
	<section class="content-body">
		<c:if test="${param.Update eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Master Parts Updated Successfully.
			</div>
		</c:if>
		<sec:authorize access="!hasAuthority('VIEW_PARTS')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_PARTS')">
			<div class="row">
				<div class="col-md-9">
					<c:if test="${!empty TyreHistory}">
						<div class="main-body">
							<div class="box">
								<div class="box-body">
									<div class="table-responsive">
										<table class="table table-hover table-bordered">
											<thead>
												<tr>
													<th>Assign Date</th>
													<th>Tyre No</th>
													<th>Assign Vehicle</th>
													<th>Axle</th>
													<th>Position</th>
													<th>Status</th>
													<th>Odometer</th>
													<th>Useage</th>
													<th>Comment</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${TyreHistory}" var="TyreHistory">
													<tr>
														<td><c:out value="${TyreHistory.TYRE_ASSIGN_DATE}" /></td>
														<td><c:out value="${TyreHistory.TYRE_NUMBER}" /></td>
														<td><a
															href="<c:url value="/VehicleTyreDetails?Id=${TyreHistory.VEHICLE_ID}"/>"
															data-toggle="tip"
															data-original-title="Click vehicle Details"><c:out
																	value="${TyreHistory.VEHICLE_REGISTRATION}" /></a></td>
														<td><c:out value="${TyreHistory.AXLE}" /></td>
														<td><c:out value="${TyreHistory.POSITION}" /></td>
														<td><c:out value="${TyreHistory.TYRE_STATUS}" /></td>
														<td><span class="badge"><c:out
																	value="${TyreHistory.OPEN_ODOMETER}" /></span></td>

														<td class="fit ar"><c:out
																value="${TyreHistory.TYRE_USEAGE}" /></td>
														<td class="fit ar"><c:out
																value="${TyreHistory.TYRE_COMMENT}" /></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</c:if>
					<c:if test="${empty TyreHistory}">
						<div class="main-body">
							<p class="lead text-muted text-center t-padded">
								<spring:message code="label.master.noresilts" />
							</p>

						</div>
					</c:if>
				</div>
				<div class="col-md-2">
					<ul class="nav nav-list">
					 <li class="active"><a
							href="<c:url value="/TyreInventoryLife?ID=${Tyre.TYRE_ID}"/>">View
								Tyre Life Cycle</a></li>
						<li class="active"><a
							href="<c:url value="/TyreInventoryHistory?ID=${Tyre.TYRE_ID}"/>">View
								History</a></li>
						<li class="active"><a
							href="<c:url value="/TyreInventoryNew/1.in"/>">New Tyre
								Inventory</a></li>
					</ul>
				</div>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabsShowVehicle.js" />"></script>
</div>