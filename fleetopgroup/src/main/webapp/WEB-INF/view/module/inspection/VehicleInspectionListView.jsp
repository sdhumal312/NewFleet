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
								class="info-box-number">${pendingInspectionList}</span></a>

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
						<c:if test="${!empty inspectionList}">
						
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
											<c:if test="${configInspection.skipInspection}">
											<th>Skip</th>
											</c:if>
											<th>Edit</th>
											<th class="fit ar">Status</th>
										</tr>
									</thead>
									<tbody>

											<c:forEach items="${inspectionList}" var="inspectionList">
											
											<% 
												srNo ++;
											%>

												<tr data-object-id="" class="ng-scope">
													<td class="fit"><% out.print(srNo); %></td>
													<td><a
														href="<c:url value="/showVehicle.in?vid=${inspectionList.vid}"/>"
														data-toggle="tip"
														data-original-title="Click this vehicle Details"> <c:out
																value="${inspectionList.vehicle_registration}" />

													</a></td>
													
													<c:choose>
														<c:when test="${inspectionList.completionDetailsId != null}">
														 <td><a class="btn btn-success"
															href="<c:url value="/viewInspectVehicleDetails?vid=${inspectionList.vid}&ID=${inspectionList.completionDetailsId}"/>"> View

													</a></td>
														</c:when>
														<c:otherwise>
															<td></td>	
														</c:otherwise>
													</c:choose>
													
													<c:choose>
														<c:when test="${inspectionList.inspectionStatusId != 2}">
																
																<td><a class="btn btn-info"
																	href="<c:url value="/inspectVehicle?vid=${inspectionList.vid}&ID=${inspectionList.vehicleInspctionSheetAsingmentId}&DR=${currentDate}"/>"> Inspect
																	</a>
														</c:when>
														<c:otherwise>
																	
																<td>INSPECTED</td>
															
														</c:otherwise>
													</c:choose>
													<c:if test="${configInspection.skipInspection}">
														<c:choose>
															<c:when test="${inspectionList.inspectionStatusId == 0}">
																<td><a class="btn btn-warning" href="#"
																	onclick="skipInspection(${inspectionList.vehicleDailyInspectionId})">
																		&nbsp; Skip &nbsp; </a></td>
															</c:when>
															<c:otherwise>
																<td>--</td>
															</c:otherwise>
														</c:choose>
													</c:if>

													<c:choose>
														<c:when test="${inspectionList.inspectionStatusId != 2}">
																<td>--</td>		
														</c:when>
														<c:otherwise>
															
															<td><a class="btn btn-info"
																			href="<c:url value="/editInspectionDetails?vid=${inspectionList.vid}&ID=${inspectionList.vehicleInspctionSheetAsingmentId}"/>"> Edit
	
																	</a></td>
														</c:otherwise>
													</c:choose>
														
													<td><c:out value="${inspectionList.inspectionStatusName}" /></td>

												</tr>
											</c:forEach>
										</c:if>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<%-- <c:url var="firstUrl" value="/ViewVehicleInspectionList" />
					<c:url var="lastUrl"
						value="/ViewVehicleInspectionList/${deploymentLog.totalPages}" />
					<c:url var="prevUrl" value="/ViewVehicleInspectionList/${currentIndex - 1}" />
					<c:url var="nextUrl" value="/ViewVehicleInspectionList/${currentIndex + 1}" />
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
								<c:url var="pageUrl" value="/ViewVehicleInspectionList/${i}" />
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
			
			
				<div class="modal fade" id="skipRemark" role="dialog">
			<div class="modal-dialog" style="width: 980px;">
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">Inspection Skip Remark</h4>
						</div>
						<div class="modal-body">
						<input type="hidden" id="dailyInspectionId">
							<div class="row1">
											<label class="L-size control-label" for="InRemark">Remark
												</label>
											<div class="I-size">
											<script language="javascript" src="jquery.maxlength.js"></script>
				                                 <textarea class="text optional form-text"
																id="InRemark" name="InRemark"
																rows="3" maxlength="1000"></textarea>
											</div>
										</div>
						</div>
						<div class="modal-footer">
							<button type="submit" onclick="skipInspectionWithRemark();" id="btnSubmit" class="btn btn-primary">
								<span> &nbsp; Skip &nbsp; </span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span>Cancel</span>
							</button>
						</div>
				</div>
			</div>
		</div>
		</sec:authorize>

	</section>
</div>
<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/inspection/vehicleInspectionList.js" />"></script>