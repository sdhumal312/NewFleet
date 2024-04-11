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
						href="<c:url value="showVehicle.in?vid=${vehicle.vid}"/>"><c:out
							value="${vehicle.vehicle_registration}" /></a> / <span>New
						Vehicle Service Entries</span>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_SERVICE_ENTRIES')">
						<a class="btn btn-success btn-sm" href="createServiceEntries?issue=0,0">
							<i class="fa fa-plus"></i> Create Service Entries
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
								  <span class="info-box-icon bg-green" id="iconContainer">
								        <i class="fa fa-bus"></i>
								  </span>
							        <img src="${pageContext.request.contextPath}/getImageVehicle/${vehicle.vehicle_photoid}.in"
							        class="img-rounded" alt=" " width="100" height="100" id="vehicleImage" onerror="hideImageOnError(this)" />
						</a>
					</div>
					<div class="pull-left1">
						<h3 class="secondary-header-title">
							<a href="showVehicle.in?vid=${vehicle.vid}"> <c:out
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
			<sec:authorize access="!hasAuthority('VIEW_SERVICE_ENTRIES')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_SERVICE_ENTRIES')">
				<div class="col-md-9 col-sm-9 col-xs-12">
					<c:if test="${param.saveService eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Service Created successfully .
						</div>
					</c:if>
					<c:if test="${param.updateService eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Service Updated successfully .
						</div>
					</c:if>

					<c:if test="${param.deleteService eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Service Deleted successfully .
						</div>
					</c:if>
					<c:if test="${param.danger eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Service Not Create successfully .
						</div>
					</c:if>
					<c:if test="${param.alreadyCreatedWorkOrder eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Service Reminder was already created. Please Wait for
							Complete Work Order.
						</div>
					</c:if>
					<div class="row">
						<div class="main-body">
							<c:if test="${!empty ServiceEntries}">
								<div class="box">
									<div class="box-body">
										<table id="FuelTable" class="table table-hover table-striped">
											<thead>
												<tr>
													<th class="fit">Id</th>
													<th>Vendor</th>
													<th class="fit ar">Invoice Number</th>
													<th class="fit ar">Job Number</th>
													<th class="fit ar">Cost</th>
													<th class="fit ar">Paid By</th>
													<th class="fit ar">Document</th>
													<th class="fit ar">Actions</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${ServiceEntries}" var="ServiceEntries">
													<tr data-object-id="" class="ng-scope">
														<td class="fit"><a
															href="ServiceEntriesParts.in?SEID=${ServiceEntries.serviceEntries_id}"><c:out
																	value="S-${ServiceEntries.serviceEntries_Number}" /></a></td>

														<td><c:out value="${ServiceEntries.vendor_name}" /><br>
															<c:out value="${ServiceEntries.vendor_location}" /></td>

														<td class="fir ar"><c:out
																value="${ServiceEntries.invoiceNumber}" /></td>
														<td class="fir ar"><c:out
																value="${ServiceEntries.jobNumber}" /></td>

														<td class="fir ar"><c:out
																value="${ServiceEntries.totalservice_cost}" /></td>
														<td class="fir ar"><c:out
																value="${ServiceEntries.service_paidby}" /></td>

														<td><c:choose>
																<c:when
																	test="${ServiceEntries.service_document == true}">
																	<sec:authorize
																		access="hasAuthority('DOWNLOAD_SERVICE_ENTRIES')">
																		<a
																			href="${pageContext.request.contextPath}/download/serviceDocument/${ServiceEntries.service_document_id}.in">
																			<span class="fa fa-download"> Doc</span>
																		</a>
																	</sec:authorize>
																</c:when>
															</c:choose></td>

														<td class="fir ar">
															<div class="btn-group">
																<a class="btn btn-default btn-sm dropdown-toggle"
																	data-toggle="dropdown" href="#"> <span
																	class="fa fa-ellipsis-v"></span>
																</a>

																<ul class="dropdown-menu pull-right">
																	<c:choose>
																		<c:when
																			test="${ServiceEntries.serviceEntries_status != 'COMPLETE'}">

																			<li><sec:authorize
																					access="hasAuthority('EDIT_SERVICE_ENTRIES')">
																					<a
																						href="serviceEntriesEditAjax.in?SEID=${ServiceEntries.serviceEntries_id}">
																						<i class="fa fa-edit"></i> Edit
																					</a>
																				</sec:authorize></li>
																			<li><sec:authorize
																					access="hasAuthority('DELETE_SERVICE_ENTRIES')">
																					<a
																						href="#" onclick="deleteServiceEntry(${ServiceEntries.serviceEntries_id})"
																						class="confirmation"
																						onclick="return confirm('Are you sure? Delete ')">
																						<span class="fa fa-trash"></span> Delete
																					</a>
																				</sec:authorize></li>
																		</c:when>
																		<c:otherwise>
																			<li><span class="label label-warning"><i
																					class="fa fa-dot-circle-o"></i> <c:out
																						value="SERVICE COMPLETED" /></span></li>
																		</c:otherwise>
																	</c:choose>
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
							<c:if test="${empty ServiceEntries}">
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
<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/SE/ViewServiceEntries.js" />"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
