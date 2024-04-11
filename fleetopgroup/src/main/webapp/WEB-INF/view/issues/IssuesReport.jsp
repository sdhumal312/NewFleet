<%@ include file="taglib.jsp"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
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
						href="<c:url value="/issuesOpen.in"/>">Issues</a> / <a
						href="<c:url value="/IssuesReport"/>">Issues Report</a> / <span>
						Search Report</span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/IssuesReport"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="row">
			<div class="main-body">
				<sec:authorize access="!hasAuthority('VIEW_ALL_ISSUES')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_ALL_ISSUES')">
					<c:if test="${!empty Issues}">
						<div class="box">
							<div class="box-body">
								<table id="VendorTable" class="table">
									<thead>
										<tr>
											<th class="fit"></th>
											<th>Summary</th>
											<th>Type</th>
											<th>Assign To</th>
											<th class="fir ar">Actions</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${Issues}" var="Issues">
											<tr data-object-id="" class="ng-scope">
												<td class="fit"><a target="_blank"
													href="showIssues?Id=${Issues.ISSUES_ID_ENCRYPT}"><c:out
															value="I-${Issues.ISSUES_NUMBER}" /></a></td>
												<td class="col-sm-5"><a target="_blank"
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
													</c:choose> <br> <small class="text-muted"> Reported on <abbr
														data-toggle="tip"
														data-original-title="${Issues.ISSUES_REPORTED_DATE}"><c:out
																value="${Issues.ISSUES_REPORTED_DATE}" /></abbr>

												</small></td>

												<td><c:choose>
														<c:when test="${Issues.ISSUES_TYPE_ID == 1}">
															<a target="_blank"
																href="showVehicle?vid=${Issues.ISSUES_VID}"
																data-toggle="tip" data-original-title="Click Details">
																<c:out value="${Issues.ISSUES_VEHICLE_REGISTRATION}" />
															</a>
															<br>
															<c:out value="${Issues.ISSUES_VEHICLE_GROUP}" />
														</c:when>
														<c:when test="${Issues.ISSUES_TYPE_ID == 2}">
															<a target="_blank"
																href="showDriver.in?driver_id=${Issues.ISSUES_DRIVER_ID}"
																data-toggle="tip"
																data-original-title="Click Driver Details"> <c:out
																	value="${Issues.ISSUES_DRIVER_NAME}" />
															</a>
														</c:when>
														<c:otherwise>
															<a target="_blank"
																href="showBranch.in?branch_id=${Issues.ISSUES_BRANCH_ID}"
																data-toggle="tip"
																data-original-title="Click Branch Details"> <c:out
																	value="${Issues.ISSUES_BRANCH_NAME}" />
															</a>
															<br>
															<a target="_blank"
																href="showBranch.in?branch_id=${Issues.ISSUES_BRANCH_ID}"
																data-toggle="tip"
																data-original-title="Click Branch Details"> <c:out
																	value="${Issues.ISSUES_DEPARTNMENT_NAME}" />
															</a>
														</c:otherwise>
													</c:choose></td>
												<td class="col-sm-3"><small>${Issues.ISSUES_ASSIGN_TO_NAME}</small></td>
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
					</c:if>
					<c:if test="${empty Issues}">
						<div class="main-body">
							<p class="lead text-muted text-center t-padded">No results
								found</p>
						</div>
					</c:if>
				</sec:authorize>
			</div>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VA/NewVendorlanguage.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/dataTables.buttons.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.print.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.flash.min.js" />"></script>
</div>