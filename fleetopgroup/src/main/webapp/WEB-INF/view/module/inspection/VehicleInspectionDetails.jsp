<%@ include file="taglib.jsp"%>
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
						Vehicle Inspection Entries</span>
				</div>
				<div class="pull-right">
					
					<a class="btn btn-link btn-sm" href="<c:url value="/vehicle/1/1"/>">
						<span id="AddVehicle"> Cancel</span>
					</a>
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
							<a href="<c:url value="/showVehicle.in?vid=${vehicle.vid}"/>">
								<c:out value="${vehicle.vehicle_registration}" />
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
			<sec:authorize access="!hasAuthority('VIEW_VEHICLE_INSPECTION_MODULE')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_VEHICLE_INSPECTION_MODULE')">
				<div class="col-md-10 col-sm-10 col-xs-10">
					<c:if test="${param.deleteFuel eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Fuel Data successfully Deleted.
						</div>
					</c:if>
					<c:if test="${param.saveFuel eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Fuel Data Successfully Created.
						</div>
					</c:if>
					<c:if test="${param.duplicateFuelEntries eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Fuel Entries was Already created..(or) This Duplicate
							Entries Reference Number.
						</div>
					</c:if>
					<c:if test="${saveFuel}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Fuel Data Successfully Created.
						</div>
					</c:if>
					<c:if test="${deleteFuel}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Fuel Data Successfully Deleted.
						</div>
					</c:if>
					<c:if test="${importSave}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							Imported Successfully ${CountSuccess} Fuel Data.
						</div>
					</c:if>
					<c:if test="${param.danger eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Fuel was Already Updated.
						</div>
					</c:if>
					<c:if test="${duplicateFuelEntries}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Fuel Entries was Already created..(or) This Duplicate
							Entries Reference Number.
						</div>
					</c:if>
					<div class="row">
						<div class="main-body">
							<c:if test="${!empty inspectionList}">
								<sec:authorize access="hasAuthority('VIEW_VEHICLE_INSPECTION_MODULE')">
									<div class="box">
										<div class="box-body">
											<div class="table-responsive">
												<table id="FuelTable" class="table table-striped">
													<thead>
														<tr>
															<th>Inspection Date</th>
															<th>Inspection Complete On</th>
															<th>Insspection By</th>
															<th>View</th>
															
														</tr>
													</thead>
													<tbody>
														<c:forEach items="${inspectionList}" var="inspectionList">
															<tr data-object-id="" class="ng-scope">
																
																<td><c:out value="${inspectionList.inspectionDateStr}" /></td>
																<td><c:out value="${inspectionList.completionDateTimeStr}" /></td>
																<td><c:out value="${inspectionList.inspectedBy}" /></td>
																 <td><a class="btn btn-success"
																		href="<c:url value="/viewInspectVehicleDetails?vid=${inspectionList.vid}&ID=${inspectionList.completionDetailsId}"/>"> View
																</a></td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										</div>
									</div>
									<c:url var="firstUrl"
										value="/ViewVehicleInspection/1?vid=${vehicle.vid}" />
									<c:url var="lastUrl"
										value="/ViewVehicleInspection/${deploymentLog.totalPages}?vid=${vehicle.vid}" />
									<c:url var="prevUrl"
										value="/ViewVehicleInspection/${currentIndex - 1}?vid=${vehicle.vid}" />
									<c:url var="nextUrl"
										value="/ViewVehicleInspection/${currentIndex + 1}?vid=${vehicle.vid}" />
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
												<c:url var="pageUrl"
													value="/ViewVehicleInspection/${i}?vid=${vehicle.vid}" />
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
								</sec:authorize>
							</c:if>
							<c:if test="${empty fuel}">
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
			<div class="col-md-1 col-sm-1 col-xs-12">
				<%@include file="../../vehicle/VehicleSideMenu.jsp"%>
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
	
