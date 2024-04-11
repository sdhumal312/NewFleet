<%@ include file="taglib.jsp"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
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
						Vehicle Issues</span>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_ISSUES')">
						<a class="btn btn-success btn-sm" href="addIssuesDetails.in"> <i
							class="fa fa-plus"></i> Create Issues
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
			<sec:authorize access="!hasAuthority('VIEW_ISSUES')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_ISSUES')">
				<div class="col-md-9 col-sm-9 col-xs-12">
					<div class="row">

						<c:if test="${!empty Issues}">
							<div class="main-body">
								<div class="box">
									<div class="box-body">
										<div class="table-responsive">

											<table id="FuelTable" class="table table-hover table-bordered">
												<thead>
													<tr>
														<th class="fit"></th>
														<th>Summary</th>
														<th>Odometer</th>
														<th>Driver</th>
														<th>Assign To</th>
														<th>Status</th>
														<th>Print</th>
														<th class="fir ar">Actions</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${Issues}" var="Issues">
														<tr data-object-id="" class="ng-scope">
															<td class="fit"><a
																href="showIssues?Id=${Issues.ISSUES_ID_ENCRYPT}"><c:out
																		value="I-${Issues.ISSUES_NUMBER}" /></a></td>
															<td class="col-sm-5"><a
																href="showIssues?Id=${Issues.ISSUES_ID_ENCRYPT}"><strong>
																		<c:out value="${Issues.ISSUES_SUMMARY}" />
																</strong> </a> <c:choose>
																	<c:when test="${Issues.ISSUES_LABELS == 'NORMAL'}">
																		<small class="label label-primary"><c:out
																				value="${Issues.ISSUES_LABELS}" /></small>
																	</c:when>
																	<c:when test="${Issues.ISSUES_LABELS == 'HIGH'}">
																		<small class="label label-info"><c:out
																				value="${Issues.ISSUES_LABELS}" /></small>
																	</c:when>
																	<c:when test="${Issues.ISSUES_LABELS == 'LOW'}">
																		<small class="label label-default"><c:out
																				value="${Issues.ISSUES_LABELS}" /></small>
																	</c:when>
																	<c:when test="${Issues.ISSUES_LABELS == 'URGENT'}">
																		<small class="label label-warning"><c:out
																				value="${Issues.ISSUES_LABELS}" /></small>
																	</c:when>
																	<c:otherwise>
																		<small class="label label-danger"><c:out
																				value="${Issues.ISSUES_LABELS}" /></small>
																	</c:otherwise>
																</c:choose> <br> <small class="text-muted"> Reported
																	on <abbr data-toggle="tip"
																	data-original-title="${Issues.ISSUES_REPORTED_DATE}"><c:out
																			value="${Issues.ISSUES_REPORTED_DATE}" /></abbr>
															</small></td>
															<td><a
																href="showIssues?Id=${Issues.ISSUES_ID_ENCRYPT}"
																data-toggle="tip"
																data-original-title="Click Odo meter Details"> <c:out
																		value="${Issues.ISSUES_ODOMETER}" />
															</a></td>
															<td><a
																href="showDriver.in?driver_id=${Issues.ISSUES_DRIVER_ID}"
																data-toggle="tip"
																data-original-title="Click Driver Details"> <c:out
																		value="${Issues.ISSUES_DRIVER_NAME}" />
															</a></td>
															<td class="col-sm-3"><small>${Issues.ISSUES_ASSIGN_TO_NAME}</small></td>
															
															<td><c:choose>
																	<c:when test="${Issues.ISSUES_STATUS_ID == 1}">
																		<small class="label label-info"><c:out
																				value="${Issues.ISSUES_STATUS}" /></small>
																	</c:when>
																	<c:when test="${Issues.ISSUES_STATUS_ID == 5}">
																		<small class="label label-danger"><c:out
																				value="${Issues.ISSUES_STATUS}" /></small>
																	</c:when>
																	<c:when test="${Issues.ISSUES_STATUS == 'RESOLVED'}">
																		<small class="label label-warning"><c:out
																				value="${Issues.ISSUES_STATUS}" /></small>
																	</c:when>
																	<c:otherwise>
																		<small class="label label-success"><c:out
																				value="${Issues.ISSUES_STATUS}" /></small>
																	</c:otherwise>
																</c:choose></td>
																<td><a data-toggle="tooltip" data-placement="top" title="Print" target="_blank" href="showIssuePrint.in?issueId=${Issues.ISSUES_ID_ENCRYPT}"><i class="fa fa-print" aria-hidden="true"></i></a> </td>
															<c:choose>
														<c:when test="${Issues.ISSUES_STATUS_ID != 1 }">
															<td>
																<small class="label label-warning">
																<c:out value="${Issues.ISSUES_STATUS}" /></small>
															</td>
														</c:when>
														
														<c:otherwise>
															<c:choose>
																<c:when test="${fn:contains(Issues.ISSUES_ASSIGN_TO, email) || Issues.CREATEDBYID == createdById || operateAllIssuePermission == true }">
																	<td>
																		<div class="btn-group">
																			<a class="btn btn-default btn-sm dropdown-toggle"
																				data-toggle="dropdown" href="#"> <span
																				class="fa fa-ellipsis-v"></span>
																			</a>
																			<ul class="dropdown-menu pull-right">
																				<li><sec:authorize
																						access="hasAuthority('EDIT_ISSUES')">
																						<a
																							href="<c:url value="/editIssuesDetails?Id=${Issues.ISSUES_ID_ENCRYPT}"/>">
																							<span class="fa fa-pencil"></span> Edit
																						</a>
																					</sec:authorize></li>
																				<li><sec:authorize
																						access="hasAuthority('DELETE_ISSUES')">
																						<a
																							href="<c:url value="/deleteIssues?Id=${Issues.ISSUES_ID_ENCRYPT}"/>"
																							class="confirmation"
																							onclick="return confirm('Are you sure? Delete ')">
																							<span class="fa fa-trash"></span> Delete
																						</a>
																					</sec:authorize></li>
																			</ul>
																		</div>
																	</td>
																</c:when>
																<c:otherwise>
																	<td></td>
																</c:otherwise>
															</c:choose>
														</c:otherwise>
													</c:choose>

														</tr>
													</c:forEach>
												</tbody>
											</table>

										</div>

									</div>
								</div>
							</div>
						</c:if>
						<c:if test="${empty Issues}">
							<div class="main-body">
								<p class="lead text-muted text-center t-padded">
									<spring:message code="label.master.noresilts" />
								</p>
							</div>
						</c:if>
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
	
