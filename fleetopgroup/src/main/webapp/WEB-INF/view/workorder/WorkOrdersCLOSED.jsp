<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/viewWorkOrder.in"/>">Work Orders</a> / <span
						id="AllVehicl">Open Work Orders</span>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_WORKORDER')">
						<a class="btn btn-success btn-sm"
							href="<c:url value="/createWorkOrder.in?issue=0,0"/>"> <span
							class="fa fa-plus"></span> Create Work Order
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_WORKORDER')">
						<a class="btn btn-info btn-sm"
							href="<c:url value="/WorkOrdersReport.in"/>"> <span
							class="fa fa-search "></span> Search
						</a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<br />
		<sec:authorize access="!hasAuthority('VIEW_WORKORDER')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_WORKORDER')">
			<div class="row">
				<div class="col-md-4 col-sm-5 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-file-text-o"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Closed Work Orders</span> <span
								class="info-box-number">${WorkOrderCount}</span>
						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Work Orders</span>
							<form action="<c:url value="/searchWorkOrderShow.in"/>"
								method="post">
								<div class="input-group">
									<span class="input-group-addon"> <span
										aria-hidden="true">WO-</span></span> <input class="form-text"
										id="vehicle_registrationNumber" name="Search" type="number"
										min="1" required="required" placeholder="WO-NO eg:6878">
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
									<li role="presentation"><a
										href="<c:url value="/viewWorkOrder.in"/>">OPEN</a></li>
									<li role="presentation"><a
										href="<c:url value="/WorkOrdersINPROCESS/1.in"/>">IN
											PROCESS</a></li>
									<li role="presentation"><a
										href="<c:url value="/WorkOrdersONHOLD/1.in"/>">ON HOLD</a></li>
									<li role="presentation" class="active"><a
										href="<c:url value="/WorkOrdersCOMPLETE/1.in"/>">COMPLETED</a></li>
								</ul>
							</div>
						</div>
					</div>
					<c:if test="${!empty WorkOrder}">
						<div class="box">
							<div class="box-body">
								<table id="WorkOrdersTable"
									class="table table-hover table-striped">
									<thead>
										<tr>
											<th>Work Order ID</th>
											<th>Start</th>
											<th>Due</th>
											<th>Vehicle</th>
											<th>Assigned to</th>
											<th>Location</th>
											<th>Doc</th>
											<th class="actions" class="icon">Actions</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${WorkOrder}" var="WorkOrder">

											<tr data-object-id="" class="ng-scope">
												<td><a
													href="<c:url value="/showWorkOrder?woId=${WorkOrder.workorders_id}"/>"
													data-toggle="tip"
													data-original-title="Click work Order Info"> <c:out
															value="WO-${WorkOrder.workorders_Number}" />

												</a></td>
												<td><c:out value="${WorkOrder.start_date}" /></td>
												<td><c:out value="${WorkOrder.due_date}" /></td>

												<td><a
													href="<c:url value="/VehicleWorkOrderDetails/${WorkOrder.vehicle_vid}/1.in"/>"
													data-toggle="tip" data-original-title="Click Vehicle Info">
														<c:out value="${WorkOrder.vehicle_registration}" />
												</a></td>
												<td><c:out value="${WorkOrder.assignee}" /></td>
												<td><c:out value="${WorkOrder.workorders_location}" /></td>
												<td><c:choose>
														<c:when test="${WorkOrder.workorders_document == true}">
															<sec:authorize
																access="hasAuthority('DOWNLOAD_WORKORDER')">
																<a
																	href="${pageContext.request.contextPath}/download/workorderDocument/${WorkOrder.workorders_id}.in">
																	<span class="fa fa-download"> Doc</span>
																</a>
															</sec:authorize>
														</c:when>
													</c:choose></td>
												<td class="icon">
													<div class="btn-group">
														<a class="btn btn-default btn-sm dropdown-toggle"
															data-toggle="dropdown" href="#"> <span
															class="fa fa-ellipsis-v"></span>
														</a>

														<ul class="dropdown-menu pull-right">
															<li><sec:authorize
																	access="hasAuthority('DELETE_WORKORDER')">
																	<a
																		href="<c:url value="/deleteWorkOrder.in?workorders_id=${WorkOrder.workorders_id}&vid=${WorkOrder.vehicle_vid}"/>"
																		class="confirmation"
																		onclick="return confirm('Are you sure? Delete ')">
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
					</c:if>
					<c:url var="firstUrl" value="/WorkOrdersCOMPLETE/1" />
					<c:url var="lastUrl"
						value="/WorkOrdersCOMPLETE/${deploymentLog.totalPages}" />
					<c:url var="prevUrl"
						value="/WorkOrdersCOMPLETE/${currentIndex - 1}" />
					<c:url var="nextUrl"
						value="/WorkOrdersCOMPLETE/${currentIndex + 1}" />
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
								<c:url var="pageUrl" value="/WorkOrdersCOMPLETE/${i}" />
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
		</sec:authorize>
	</section>
</div>
