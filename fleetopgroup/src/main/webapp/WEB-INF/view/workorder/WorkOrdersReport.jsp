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
						href="<c:url value="/viewWorkOrder.in"/>">Work Orders</a> / <span
						id="AllVehicl">Open Work Orders</span>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_WORKORDER')">
						<a class="btn btn-success btn-sm" href="createWorkOrder.in?issue=0,0"> <span
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
	<!-- Main content -->
	<section class="content">
		<br />
		<sec:authorize access="!hasAuthority('VIEW_WORKORDER')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_WORKORDER')">
			<div class="row">
				<div class="col-md-3 col-sm-6 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-file-text-o"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Total Work Orders</span> <span
								class="info-box-number">${WorkOrderCount}</span>
						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-6 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Work Orders</span>
							<form action="<c:url value="/searchWorkOrder.in"/>" method="post">
								<div class="input-group">
									<input class="form-text" id="vehicle_registrationNumber"
										name="Search" type="text" required="required"
										placeholder="WO-NO, Vehicle"> <span
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
			<c:if test="${saveWorkOrder}">
				<div class="alert alert-success">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Work Order Created successfully.
				</div>
			</c:if>
			<c:if test="${deleteWorkOrder}">
				<div class="alert alert-success">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Work Order Deleted successfully.
				</div>
			</c:if>
			<c:if test="${param.danger eq true}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Driver Already Updated.
				</div>
			</c:if>
			<!-- alert in delete messages -->
			<c:if test="${updateIssue}">
				<div class="alert alert-success">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Work Order Updated successfully.
				</div>
			</c:if>
			<c:if test="${param.deletedanger eq true}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Driver Not Deleted.
				</div>
			</c:if>

			<!-- Tabs of driver list showing tabs -->
			<div class="main-tabs">
				<ul class="nav nav-pills">
					<!-- <li class=""><a href="ShowClosedWork Order.in">Work Today </a></li> -->
					<li role="presentation" class="active"><a
						href="viewWorkOrder.in">Open</a></li>
					<li class=""><a href="WorkOrdersINPROCESS/1.in">In Process</a></li>
					<li class=""><a href="WorkOrdersONHOLD/1.in">On Hold</a>
					<li class=""><a href="WorkOrdersCOMPLETE/1.in">Closed </a></li>
				</ul>
			</div>
			<br>
			<div class="row">
				<div class="main-body">
					<div class="box">
						<div class="box-body">
							<table id="WorkOrdersTable"
								class="table table-bordered table-striped">
								<thead>
									<tr>
										<th class="fit ar">ID</th>
										<th class="fit ar">Start</th>
										<th class="fit ar">Due</th>
										<th>Vehicle</th>
										<th class="fit ar">Assigned to</th>
										<th class="fit ar">Progress</th>
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
															value="${WorkOrder.workorders_Number}" />

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
												<td class="fit ar"><c:out value="${WorkOrder.progress}" /></td>
												<td class="fit ar"><c:out value="${WorkOrder.priority}" /></td>
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
																		href="deleteWorkOrder.in?workorders_id=${WorkOrder.workorders_id}&vid=${WorkOrder.vehicle_vid}"
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
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/WO/WorkOrderslanguage.js" />"></script>
</div>
