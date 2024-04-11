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
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/viewWorkOrder.in"/>">Work Orders</a> / <a
						href="<c:url value="/WorkOrdersReport"/>">Work Orders Report</a>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/WorkOrdersReport"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_WORKORDER')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_WORKORDER')">
			<div class="row">
				<div class="main-body">
					<div class="box">
						<div class="box-body">
							<table id="VendorTable"
								class="table table-bordered table-striped">
								<thead>
									<tr>
										<th class="fit ar">ID</th>
										<th class="fit ar">Start</th>
										<th class="fit ar">Due</th>
										<th>Vehicle</th>
										<th class="fit ar">Assigned to</th>
										<th class="fit ar">Location</th>
										<th class="fit ar">Priority</th>
										<th class="actions" class="icon">Actions</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${!empty WorkOrder}">
										<c:forEach items="${WorkOrder}" var="WorkOrder">
											<tr data-object-id="" class="ng-scope">
												<td class="fit ar"><a target="_blank"
													href="showWorkOrder?woId=${WorkOrder.workorders_id}"
													data-toggle="tip"
													data-original-title="Click work Order Info"> <c:out
															value="WO-${WorkOrder.workorders_Number}" />
												</a></td>
												<td class="fit ar"><c:out
														value="${WorkOrder.start_date}" /></td>
												<td class="fit ar"><c:out value="${WorkOrder.due_date}" /></td>

												<td><a target="_blank"
													href="showWorkOrder?woId=${WorkOrder.workorders_id}"
													data-toggle="tip"
													data-original-title="Click work Order Info"> <c:out
															value="${WorkOrder.vehicle_registration}" />
												</a></td>
												<td class="fit ar"><c:out value="${WorkOrder.assignee}" /></td>
												<td class="fit ar"><c:out
														value="${WorkOrder.workorders_location}" /></td>
												<td class="fit ar"><c:out value="${WorkOrder.priority}" /></td>
												<td class="icon">
													<div class="btn-group">
														<a class="btn btn-default btn-sm dropdown-toggle"
															data-toggle="dropdown" href="#"><span
																	class="fa fa-ellipsis-v"></span>
														</a>

														<ul class="dropdown-menu pull-right">
															<li><sec:authorize
																	access="hasAuthority('DELETE_WORKORDER')">
																	<a
																		href="deleteWorkOrder.in?workorders_id=${WorkOrder.workorders_id}"
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
									</c:if>
								</tbody>
							</table>
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