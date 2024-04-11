<%@ include file="../../taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> /
					<a href="<c:url value="/ViewVehicleInspectionList.in"/>"> Vehicle Inspection </a>
				</div>
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_VEHICLE_INSPECTION_MODULE')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_VEHICLE_INSPECTION_MODULE')">
			<div class="row">
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-info"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Todays Inspection Vehicles</span> <a data-toggle="tip"
								data-original-title="Click Inspection Details"
								href="<c:url value="/ViewVehicleInspectionList.in"/>"><span
								class="info-box-number">${todaysCount}</span></a>
						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-info"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Todays Inspected Vehicles</span> <a data-toggle="tip"
								data-original-title="Click Inspected Details"
								href="<c:url value="/ViewVehicleInspectedList.in"/>"><span
								class="info-box-number">${inspectedCount}</span></a>

						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-info"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Todays Pending Inspection Vehicles</span> <a data-toggle="tip"
								data-original-title="Click Inspected Details"
								href="<c:url value="/ViewVehicleInspectionPendingList.in"/>"><span
								class="info-box-number">${pendingInspectionListCount}</span></a>

						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-info"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Todays Saved Inspection Vehicles</span> <a data-toggle="tip"
								data-original-title="Click Inspected Details"
								href="<c:url value="/ViewVehicleInspectionSavedList.in"/>"><span
								class="info-box-number">${inspectedSavedListCount}</span></a>

						</div>
					</div>
				</div>

				<c:if test="${configInspection.skipInspection}">
					<div class="col-md-3 col-sm-3 col-xs-12">
						<div class="info-box">
							<span class="info-box-icon bg-green"><i class="fa fa-info"></i></span>
							<div class="info-box-content">
								<span class="info-box-text">Todays skipped Inspection
									Vehicles</span> <a data-toggle="tip"
									data-original-title="Click Inspected Details"
									href="<c:url value="/ViewSkippedInspectionList.in"/>"><span
									class="info-box-number">${totalSkiped}</span></a>

							</div>
						</div>
					</div>
				</c:if>

				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-info"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Total Missed Inspection Vehicles</span> <a data-toggle="tip"
								data-original-title="Click Inspected Details"
								href="<c:url value="/ViewMissedInspectionList.in"/>"><span
								class="info-box-number">${totalMissedInspectionCount}</span></a>

						</div>
					</div>
				</div>
				
			</div>
		</sec:authorize>
		<sec:authorize access="!hasAuthority('VIEW_VEHICLE_INSPECTION_MODULE')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_VEHICLE_INSPECTION_MODULE')">
			<div class="row">
				<div class="main-body">
				
				
				<c:if test="${param.error eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						Some Error Occurred Please Contact to System Administrator !
					</div>
				</c:if>
				<c:if test="${param.saved eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						Saved Successfully !
					</div>
				</c:if>
				
					<div class="box">
						<div class="box-body">
						<c:if test="${!empty inspectedSavedList}">
						
						<% 
							int srNo = 0;
						%>
						
							<div class="table-responsive">
								<table class="table table-hover table-bordered">
									<thead>
										<tr>
											<th class="fit">Sr No.</th>
											<th>Vehicle</th>
											<th>View</th>
											<th>Inspect</th>
											<th>Edit</th>
											<th class="fit ar">Status</th>
										</tr>
									</thead>
									<tbody>

											<c:forEach items="${inspectedSavedList}" var="inspectedSavedList">
											
											<% 
												srNo ++;
											%>

												<tr data-object-id="" class="ng-scope">
													<td class="fit"><% out.print(srNo); %></td>
													<td><a
														href="<c:url value="/showVehicle.in?vid=${inspectedSavedList.vid}"/>"
														data-toggle="tip"
														data-original-title="Click this vehicle Details"> <c:out
																value="${inspectedSavedList.vehicle_registration}" />

													</a></td>
													
													<c:choose>
														<c:when test="${inspectedSavedList.completionDetailsId != null}">
														 <td><a class="btn btn-success"
															href="<c:url value="/viewInspectVehicleDetails?vid=${inspectedSavedList.vid}&ID=${inspectedSavedList.completionDetailsId}"/>"> View

													</a></td>
														</c:when>
														<c:otherwise>
															<td></td>	
														</c:otherwise>
													</c:choose>
													
													<c:choose>
														<c:when test="${inspectedSavedList.inspectionStatusId != 2}">
																
																<td><a class="btn btn-info"
																	href="<c:url value="/inspectVehicle?vid=${inspectedSavedList.vid}&ID=${inspectedSavedList.vehicleInspctionSheetAsingmentId}&DR=${currentDate}"/>"> Inspect

																	</a></td>	
														</c:when>
														<c:otherwise>
																<td>INSPECTED</td>
															
														</c:otherwise>
													</c:choose>
												
													<c:choose>
														<c:when test="${inspectedSavedList.inspectionStatusId != 2}">
																<td>--</td>		
														</c:when>
														<c:otherwise>
															
															<td><a class="btn btn-info"
																			href="<c:url value="/editInspectionDetails?vid=${inspectedSavedList.vid}&ID=${inspectedSavedList.vehicleInspctionSheetAsingmentId}"/>"> Edit
	
																	</a></td>
														</c:otherwise>
													</c:choose>
													<td><c:out value="${inspectedSavedList.inspectionStatusName}" /></td>
												</tr>
											</c:forEach>
										</c:if>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<%-- <c:url var="firstUrl" value="/ServiceReminder/1" />
					<c:url var="lastUrl"
						value="/ServiceReminder/${deploymentLog.totalPages}" />
					<c:url var="prevUrl" value="/ServiceReminder/${currentIndex - 1}" />
					<c:url var="nextUrl" value="/ServiceReminder/${currentIndex + 1}" />
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
								<c:url var="pageUrl" value="/ServiceReminder/${i}" />
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
					</div> --%>
				</div>
			</div>
		</sec:authorize>

	</section>
</div>