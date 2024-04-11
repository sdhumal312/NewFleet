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
						href="<c:url value="/vehicle/1/1"/>">Vehicle</a> / <a
						href="<c:url value="/VehicleReport"/>">Vehicle Report</a> / <small>Vehicle
						Search Report</small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
						<a href="#" id="exportVehicle" class="btn btn-info btn-sm"> <span
							class="fa fa-file-excel-o"> Export Page</span>
						</a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<div class="main-body">
				<div class="box">
					<sec:authorize access="!hasAuthority('VIEW_VEHICLE')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
						<div class="box-body">
							<div class="table-responsive">
								<table id="VehicleTable"
									class="table table-bordered table-striped">
									<thead>
										<tr>
											<th id="RegNo">Vehicle (RTO) Number</th>
											<th id="Group" class="fit ar">Group</th>
											<th id="Make" class="fit ar">Vehicle Make</th>
									<c:if test="${configuration.showVehicleBodyMaker}">
											<th id="Make" class="fit ar">Vehicle Body Make</th>
									</c:if>
											<th id="Type" class="fit ar">Vehicle Type</th>
											<th id="currentMeter" class="fit ar">Vehicle Odometer</th>
											<c:if test="${!config.hideSomeVehicleReportOptions}">
											<th id="currentMeter" class="fit ar">Current</th>
											</c:if>
											<th id="currentMeter" class="fit ar">Vehicle Chassis No</th>
											<th id="currentMeter" class="fit ar">Vehicle Engine No</th>
											<th id="location" class="fit ar">
											<c:if test="${!config.hideSomeVehicleReportOptions}">
											Location
											</c:if>
											<c:if test="${config.hideSomeVehicleReportOptions}">
											Depot/Hub
											</c:if>
											</th>
											<c:if test="${!config.hideSomeVehicleReportOptions}">
											<th id="Ownership" class="fit ar">Ownership</th>
											</c:if>
											<th id="Status" class="fit ar">Status</th>
											<th id="Actions" class="fit ar">Actions</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty vehicles}">
											<c:forEach items="${vehicles}" var="vehicle">
												<tr>
													<td><a
														href="<c:url value="/showVehicle?vid=${vehicle.vid}"/>"
														target="_blank" data-toggle="tip"
														data-original-title="Click vehicle Details"><c:out
																value="${vehicle.vehicle_registration}" /></a></td>

													<td class="fit ar"><c:out
															value="${vehicle.vehicle_Group}" /></td>
													<td class="fit ar"><c:out
															value="${vehicle.vehicle_maker}" /></td>

													<c:if test="${configuration.showVehicleBodyMaker}">
														<td class="fit ar"><c:out value="${vehicle.bodyMakerName}" /></td>
													</c:if>
													<td class="fit ar"><c:out
															value="${vehicle.vehicle_Type}" /></td>
													<td class="fit ar"><span class="badge"><c:out
																value="${vehicle.vehicle_Odometer}" /></span></td>
																<c:if test="${!config.hideSomeVehicleReportOptions}">
													<td class="fit ar"><c:choose>
															<c:when test="${vehicle.vStatusId == 5}">
																<c:if test="${vehicle.tripSheetID != 0}">
																	<a
																		href="<c:url value="/showTripSheet.in?tripSheetID=${vehicle.tripSheetID}" />"><c:out
																			value="TS-${vehicle.tripSheetNumber}" /></a>
																</c:if>
															</c:when>
															<c:when test="${vehicle.vStatusId == 6}">
																<c:if test="${vehicle.tripSheetID != 0}">
																	<a
																		href="<c:url value="/showWorkOrder?woId=${vehicle.tripSheetID}"/>">
																		<c:out value="WO-${vehicle.workOrder_Number}" />

																	</a>
																</c:if>
															</c:when>
														</c:choose></td>
														</c:if>
														
														<td class="fit ar"><c:out
															value="${vehicle.vehicle_chasis}" /></td>
															<td class="fit ar"><c:out
															value="${vehicle.vehicle_engine}" /></td>

													<td class="fit ar"><c:out
															value="${vehicle.vehicle_Location}" /></td>
													<c:if test="${!config.hideSomeVehicleReportOptions}">
													<td class="fit ar"><c:out
															value="${vehicle.vehicle_Ownership}" /></td>
															</c:if>
													<td class="fit ar"><c:out
															value="${vehicle.vehicle_Status}" /></td>
													<td class="fit"><div class="btn-group">
															<a class="btn btn-default btn-sm dropdown-toggle"
																data-toggle="dropdown" href="#"><span
																class="fa fa-ellipsis-v"></span> </a>
															<ul class="dropdown-menu pull-right">
																<li><sec:authorize
																			access="hasAuthority('EDIT_VEHICLE')">
																			<a
																				href="<c:url value="/editVehicle?vid=${vehicle.vid}"/>">
																				<span class="fa fa-pencil"></span> Edit
																			</a>
																		</sec:authorize></li>
																	<li><sec:authorize
																			access="hasAuthority('DELETE_VEHICLE')">
																			<a
																				href="<c:url value="/deleteVehicle?vid=${vehicle.vid}"/>"
																				class="confirmation"
																				onclick="return confirm('Are you sure? Delete ')">
																				<span class="fa fa-trash"></span> Delete
																			</a>
																		</sec:authorize></li>
															</ul>
														</div></td>
												</tr>
											</c:forEach>
										</c:if>
									</tbody>
								</table>
							</div>
						</div>
					</sec:authorize>
				</div>
			</div>
		</div>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VI/NewVehiclelanguage.js" />"></script>
</div>