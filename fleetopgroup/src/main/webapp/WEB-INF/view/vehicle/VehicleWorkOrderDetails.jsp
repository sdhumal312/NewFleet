<%@ include file="taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/vehicle/1/1"/>">Vehicle</a> / <a
						href="<c:url value="/showVehicle.in?vid=${vehicle.vid}"/>"><c:out
							value="${vehicle.vehicle_registration}" /></a> / <span>New
						Vehicle Work Order</span>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_WORKORDER')">
						<a class="btn btn-success btn-sm" href="<c:url value="/createWorkOrder.in?issue=0,0"/>"> <i
							class="fa fa-plus"></i> Create Work Order
						</a>
					</sec:authorize>
					<a class="btn btn-link btn-sm"
						href="showVehicle.in?vid=${vehicle.vid}">Cancel</a>
				</div>
			</div>
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_VEHICLE')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
					<div class="pull-left">
						<a href="${pageContext.request.contextPath}/getImageVehicle/${vehicle.vehicle_photoid}.in"
								class="zoom" data-title="Vehicle Photo" data-footer="" 
								data-type="image" data-toggle="lightbox"> 
								  <span class="info-box-icon bg-green" id="iconContainer"><i class="fa fa-bus"></i></span>
							      <img src="${pageContext.request.contextPath}/getImageVehicle/${vehicle.vehicle_photoid}.in"
							      class="img-rounded" alt=" " width="100" height="100" id="vehicleImage" onerror="hideImageOnError(this)" />
						</a>
					</div>
					<div class="pull-left1">
						<h3 class="secondary-header-title">
							<a href="<c:url value="/showVehicle.in?vid=${vehicle.vid}"/>"> <c:out
									value="${vehicle.vehicle_registration}" />
							</a>
						</h3>
						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Status"><a
										href="#"><c:out value=" ${vehicle.vehicle_Status}" /></a></span></li>
								<li><span class="fa fa-clock-o" aria-hidden="true"
									data-toggle="tip" data-original-title="Odometer"><a
										href="#"><c:out value=" ${vehicle.vehicle_Odometer}" /></a></span></li>
								<li><span class="fa fa-bus" aria-hidden="true"
									data-toggle="tip" data-original-title="Type"><a href="#"><c:out
												value=" ${vehicle.vehicle_Type}" /></a></span></li>
								<li><span class="fa fa-map-marker" aria-hidden="true"
									data-toggle="tip" data-original-title="Location"><a
										href="#"><c:out value=" ${vehicle.vehicle_Location}" /></a></span></li>
								<li><span class="fa fa-users" aria-hidden="true"
									data-toggle="tip" data-original-title="Group"><a
										href="#"><c:out value=" ${vehicle.vehicle_Group}" /></a></span></li>
								<li><span class="fa fa-road" aria-hidden="true"
									data-toggle="tip" data-original-title="Route"><a
										href="#"><c:out value=" ${vehicle.vehicle_RouteName}" /></a></span></li>
							</ul>
						</div>
					</div>
				</sec:authorize>
			</div>
		</div>
	</section>
	<section class="content-body">
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_VEHICLE')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
				<div class="col-md-9 col-sm-9 col-xs-12">
					<c:if test="${param.saveService eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Work Order Created successfully .
						</div>
					</c:if>
					<c:if test="${param.updateService eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Work Order Updated successfully .
						</div>
					</c:if>

					<c:if test="${param.deleteService eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Work Order Deleted successfully .
						</div>
					</c:if>
					<c:if test="${param.danger eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Work Order Not Create successfully .
						</div>
					</c:if>
					<c:if test="${param.alreadyCreatedWorkOrder eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Work Order was already created. Please Wait for Complete
							Work Order.
						</div>
					</c:if>

					<div class="row">
						<div class="main-body">
							<c:if test="${!empty WorkOrder}">
								<div class="box">
									<div class="box-body">
										<table id="FuelTable" class="table table-hover table-striped">
											<thead>
												<tr>
													<th class="fit">W-ID</th>
													<th>Start</th>
													<th>Due</th>
													<th class="fit ar">Odometer</th>

													<th>Assigned to</th>

													<th>Status</th>
													<th class="fit ar">Total Cost</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${WorkOrder}" var="WorkOrder">
													<tr data-object-id="" class="ng-scope">
														<td class="fit"><a target="_blank"
															href="<c:url value="/showWorkOrder?woId=${WorkOrder.workorders_id}"/>"
															data-toggle="tip"
															data-original-title="Click work Order Info"> <c:out
																	value="WO-${WorkOrder.workorders_Number}" />

														</a></td>
														<td><c:out value="${WorkOrder.start_date}" /></td>
														<td><c:out value="${WorkOrder.completed_date}" /></td>

														<td class="fit ar"><c:out
																value="${WorkOrder.vehicle_Odometer}" /></td>



														<td><c:out value="${WorkOrder.assignee}" /></td>
														<td><c:choose>
																<c:when test="${WorkOrder.workorders_status == 'OPEN'}">
																	<span class="label label-pill label-info"><c:out
																			value="${WorkOrder.workorders_status}" /></span>
																</c:when>
																<c:when
																	test="${WorkOrder.workorders_status == 'INPROCESS'}">
																	<span class="label label-pill label-warning"><c:out
																			value="${WorkOrder.workorders_status}" /></span>
																</c:when>
																<c:when
																	test="${WorkOrder.workorders_status == 'ONHOLD'}">
																	<span class="label label-pill label-danger"><c:out
																			value="${WorkOrder.workorders_status}" /></span>
																</c:when>
																<c:otherwise>
																	<span class="label label-pill label-success"><c:out
																			value="${WorkOrder.workorders_status}" /></span>

																</c:otherwise>
															</c:choose></td>
														<td class="fit ar"><c:out
																value="${WorkOrder.totalworkorder_cost}" /></td>

													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
								<c:url var="firstUrl" value="/VehicleWorkOrderDetails/${vehicle.vid}/1" />
								<c:url var="lastUrl"
									value="/VehicleWorkOrderDetails/${vehicle.vid}/${deploymentLog.totalPages}" />
								<c:url var="prevUrl"
									value="/VehicleWorkOrderDetails/${vehicle.vid}/${currentIndex - 1}" />
								<c:url var="nextUrl"
									value="/VehicleWorkOrderDetails/${vehicle.vid}/${currentIndex + 1}" />
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
											<c:url var="pageUrl" value="/VehicleWorkOrderDetails/${vehicle.vid}/${i}" />
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
							</c:if>
							<c:if test="${empty WorkOrder}">
								<div class="main-body">
									<p class="lead text-muted text-center t-padded">
										<spring:message code="label.master.noresilts" />
									</p>

								</div>
							</c:if>
						</div>
					</div>
				</div>
			</sec:authorize>
			<div class="col-md-2 col-sm-2 col-xs-12">
				<%@include file="VehicleSideMenu.jsp"%>
			</div>
		</div>
	</section>
</div>
<script type="text/javascript">
	 $(document).ready(function() {
         var img = $("#vehicleImage");
         var iconContainer = $("#iconContainer");

         // Check if the image is loaded
         img.on("load", function() {
             // If loaded, hide the icon
             iconContainer.hide();
         });
     });
</script>
<script type='text/javascript' src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/photoView/ekko-lightbox.js"/>" ></script>
