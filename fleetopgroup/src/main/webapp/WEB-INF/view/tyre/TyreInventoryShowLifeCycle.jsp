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
				<div class="col-md-offset-2 col-md-7 col-sm-7 col-xs-12 ">
					<c:if test="${!empty TyreLife}">
						<div class="main-body">
							<div class="box">
								<div class="box-body">
									<div class="table-responsive">
										<table class="table">
											<thead>
												<tr class="breadcrumb">
													<th class="fit ar">Date</th>
													<th class="fit ar">Tyre Life</th>
													<th class="fit ar">Tyre Cost</th>
													<th class="fit ar">Tyre Usage</th>
													<th class="fit ar">Km</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${!empty TyreLife}">
													<c:forEach items="${TyreLife}" var="TyreLife">
														<tr data-object-id="" class="ng-scope">
															<td><c:out value="${TyreLife.TYRE_RECEIVED_DATE}"></c:out>
															</td>
															<td class="fit ar"><c:out
																	value="${TyreLife.LIFE_PERIOD}"></c:out></td>
															<td class="fit ar"><i class="fa fa-inr"></i> <c:out
																	value="${TyreLife.TYRE_LIFE_COST}"></c:out></td>
															<td class="fit ar"><c:out
																	value="${TyreLife.TYRE_LIFE_USAGE} km"></c:out></td>
															<td class="fit ar"><i class="fa fa-inr"></i> <c:out
																	value="${TyreLife.TYRE_KM_COST}"></c:out></td>

														</tr>
													</c:forEach>
													<tr data-object-id="" class="ng-scope">
														<td colspan="2" class="fit"></td>
														<td class="fit ar"><i class="fa fa-inr"></i> <c:out
																value="${TotalCost.TYRE_LIFE_COST}"></c:out></td>
														<td class="fit ar"><c:out
																value="${TotalCost.TYRE_LIFE_USAGE} km"></c:out></td>
														<td class="fit ar"><i class="fa fa-inr"></i> <c:out
																value="${TotalCost.TYRE_KM_COST}"></c:out></td>

													</tr>
												</c:if>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</c:if>
					<c:if test="${empty TyreLife}">
						<div class="main-body">
							<p class="lead text-muted text-center t-padded">
								<spring:message code="label.master.noresilts" />
							</p>

						</div>
					</c:if>
				</div>
				<div class="col-md-2 col-sm-2 col-xs-12">
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
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabsShowVehicle.js" />"></script>
</div>