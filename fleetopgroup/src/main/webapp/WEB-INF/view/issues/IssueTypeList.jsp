<%@ include file="taglib.jsp"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<style>
.divScroll {
overflow:scroll;
}
</style>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/issuesOpen.in"/>">Issues</a>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_ISSUES')">
						<c:if test="${configuration.issuesAjax}">
							<a class="btn btn-success btn-sm" href="/addIssuesDetails.in"> <span
								class="fa fa-plus"></span> Create Issue
						</a>
						</c:if>
						<c:if test="${!configuration.issuesAjax}">
							<a class="btn btn-success btn-sm" href="/addIssuesDetails.in"> <span
								class="fa fa-plus"></span> Create Issue
							</a>
						</c:if>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_ISSUES')">
						<a class="btn btn-info btn-sm"
							href="<c:url value="/IssuesReport"/>"> <span
							class="fa fa-search "></span> Search
						</a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content">

		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_ISSUES')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_ISSUES')">
				<div class="col-md-2 col-sm-2 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-clock-o"></i></span>
						<div class="info-box-content" >
							<span class="info-box-text">${SelectStatus } Issues</span> <input
								type="hidden" value="${SelectStatus}" id="statues"> <span
								class="info-box-number">${Count}</span>

						</div>
					</div>
				</div>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_ALL_ISSUES')">
				<div class="col-md-2 col-sm-2 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Your Issues</span>
							<form action="<c:url value="/SearchIYShow.in"/>" method="post">
								<div class="input-group">
									<span class="input-group-addon"> <span
										aria-hidden="true">I-</span></span> <input class="form-text"
										name="Search" type="number" min="1" required="required"
										placeholder="I-ID eg:324"> <span
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
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_ALL_ISSUES')">
				<div class="col-md-2 col-sm-2 col-xs-12">
						<div class="info-box">
							<div class="info-box-center">
								<span class="info-box-text">Search All Issues</span>
								<form action="<c:url value="/SearchIYAllShow.in"/>" method="post">
									<div class="input-group">
										<span class="input-group-addon"> <span
											aria-hidden="true">I-</span></span> <input class="form-text"
											name="Search" type="number" min="1" required="required"
											placeholder="I-ID eg:687" maxlength="20"> <span
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
			</sec:authorize>
				<div class="col-md-4 col-sm-4 col-xs-12">
					<div class="info-box">
					<div class="info-box-content" >
					<ul class="nav nav-pills">
					<li class=" ">
					<a href="#">
										<span class="info-box-text" >Open</span>
										<span class="badge bg-yellow" >${breakDownOpen}</span>
										</a></li>
										<li class=" ">
										<a href="#">
										<span class="info-box-text">Rejected</span>
										<span class="badge bg-yellow" >${breakDownReject}</span></a>	</li>
										<li class=" " style="align-content: center;">	
										<a href="#">		
										<span class="info-box-text">Resolved</span>
										<span class="badge bg-yellow" >${breakDownResolve}</span> </a>	</li>	
										</ul>		
						</div>
						
						
<!-- 						<div class="info-box-content"> -->
<!-- 							<span class="info-box-text">Open</span>  <span -->
<%-- 								class="info-box-number">${breakDownOpen}</span> --%>

<!-- 						</div> -->
					</div>
				</div>
		</div>
		<c:if test="${param.NotFound eq true}">
			<div class="alert alert-warning">
				<button class="close" data-dismiss="alert" type="button">x</button>
				ID Not Available.<br>
			</div>
		</c:if>

		<c:if test="${saveTripSheet}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Issues Created successfully .
			</div>
		</c:if>
		<c:if test="${deleteTripSheet}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Issues Canceled successfully .
			</div>
		</c:if>
		<c:if test="${param.danger eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Issues Already Updated.
			</div>
		</c:if>
		<!-- alert in delete messages -->
		<c:if test="${updateTripSheet}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Issues Updated successfully .
			</div>
		</c:if>
		<c:if test="${dangerTripSheet}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Issues Not Deleted.
			</div>
		</c:if>
		<div class="row">
			<div class="main-body">
				<div class="box">
					<sec:authorize access="!hasAuthority('VIEW_ISSUES')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_ISSUES')">
						<ul class="nav nav-pills">
							<li class=" "><sec:authorize
									access="hasAuthority('VIEW_ALL_ISSUES')">
									<a href="<c:url value="/issues/1"/>">All  <span
									data-toggle="tip" title="" class="badge bg-yellow"
									data-original-title="${IssuesCount} Issues for you">${IssuesCount}</span></a>
								</sec:authorize></li>
							<li class="" id="All"><a
								href="<c:url value="/YourIssues/1"/>">For You <span
									data-toggle="tip" title="" class="badge bg-yellow"
									data-original-title="${IssuesYou} Issues for you">${IssuesYou}</span>
							</a></li>
							<li class="" id="OPEN"><a
								href="<c:url value="/issuesOpen/1"/>">OPEN <span
									data-toggle="tip" title="" class="badge "
									data-original-title="${IssuesOpenCount} Issues for you">${IssuesOpenCount}</span></a></li>
							<li class=" " id="OVERDUE"><a
								href="<c:url value="/issuesOverdue/1"/>">OVERDUE <span
									data-toggle="tip" title="" class="badge bg-red"
									data-original-title="${overDueCount} Issues for you">${overDueCount}</span></a></li>
							<li class=" " id="WOCREATED"><a
								href="<c:url value="/issuesWoCreated/1"/>">WOCREATED <span
									data-toggle="tip" title="" class="badge bg-yellow"
									data-original-title="${woCreatedCount} Issues for you">${woCreatedCount}</span></a></li>
								<sec:authorize access="hasAuthority('ADD_SERVICE_ENTRIES')">
							<li class=" " id="SE_CREATED"><a
								href="<c:url value="/issuesSE_Created/1"/>">SE_CREATED <span
									data-toggle="tip" title="" class="badge bg-yellow"
									data-original-title="${seCreatedCount} Issues for you">${seCreatedCount}</span></a></li>
									</sec:authorize>
							<sec:authorize access="hasAuthority('VIEW_DEALER_SERVICE_ENTRIES')">
							<li class=" " id="DSE_CREATED"><a
								href="<c:url value="/issuesDSE_Created/1"/>">DSE_CREATED <span
									data-toggle="tip" title="" class="badge bg-yellow"
									data-original-title="${dseCount} Issues for you">${dseCount}</span></a></li>
							</sec:authorize>
							<sec:authorize access="hasAuthority('SHOW_VEHICLE_BREAKDOWN')">
								<li class=" " id="Break_Down"><a
									href="<c:url value="/vehicleBreakDownIssuesList/1"/>">Vehicle
										Break Down <span data-toggle="tip" title=""
										class="badge bg-yellow"
										data-original-title="${vBreakDownCount} Issues for you">${vBreakDownCount}</span>
								</a></li>
							</sec:authorize>
							<li class=" " id="RESOLVED"><a
								href="<c:url value="/issuesResolved/1"/>">RESOLVED <span
									data-toggle="tip" title="" class="badge bg-green"
									data-original-title="${resolveCount} Issues for you">${resolveCount}</span></a></li>
							<li class=" " id="REJECT"><a
								href="<c:url value="/issuesReject/1"/>">REJECT <span
									data-toggle="tip" title="" class="badge bg-yellow"
									data-original-title="${rejectCount} Issues for you">${rejectCount}</span></a></li>
							<li class=" " id="CLOSED"><a
								href="<c:url value="/issuesClosed/1"/>">CLOSED <span
									data-toggle="tip" title="" class="badge bg-yellow"
									data-original-title="${closeCount} Issues for you">${closeCount}</span></a></li>
							
							<c:if test="${configuration.createIssueFromHealth}">
								<li class="" id="vehicleCount"></li>
							</c:if>
							

						</ul>
						<input type="hidden" id="createIssueFromHealth" value="${configuration.createIssueFromHealth}">
					</sec:authorize>
				</div>
			</div>
		</div>
		<c:if test="${param.deletesuccess eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Driver Deleted successfully .
			</div>
		</c:if>
		<c:if test="${param.notAssign eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Issue Not Assign you. you can't edit &amp; delete..
			</div>
		</c:if>
		<c:if test="${param.deletedanger eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Issue Not Assign you. you can't edit &amp; delete. .
			</div>
		</c:if>
		<c:if test="${param.SE eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				 You Have To Delete The SE First Hence You Can Delete The Issue 
				<%--  <a href="<c:url value="/showServiceEntryDetails?serviceEntryId=${param.SEID}"/>"> Service Entries </a> --%>
			</div>
		</c:if>
		<c:if test="${param.WO eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				 You Have To Delete The WO First Hence You Can Delete The Issue 
				<%--  <a href="<c:url value="/showWorkOrder?woId=${param.WOID}"/>"> WorkOrders </a> --%>
			</div>
		</c:if>
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_ISSUES')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_ISSUES')">
				<c:if test="${!empty Issues}">
					<div class="main-body">
						<div class="box">
							<div class="box-body">
								<div class="table-responsive">

									<table id="TripSheetTable" class="table">
										<thead>
											<tr>
												<th class="fit"></th>
												<th>Summary</th>
												<th>Vehicle/Driver</th>
												<th>Assign To</th>
												<th>Status</th>
												<th>Analysis</th>
												<th>TAT</th>
												<th class="fir ar">Actions</th>
											</tr>
										</thead>
										<tbody>

											<c:forEach items="${Issues}" var="Issues">

												<tr data-object-id="" class="ng-scope">
													<td class="fit"><a
														href="/showIssues.in?Id=${Issues.ISSUES_ID_ENCRYPT}"><c:out
																value="I-${Issues.ISSUES_NUMBER}" /></a></td>
													<td class="col-sm-5"><a
														href="/showIssues?Id=${Issues.ISSUES_ID_ENCRYPT}"><strong>
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
														</c:choose> <br> <small class="text-muted"> Reported on
															<abbr data-toggle="tip"
															data-original-title="${Issues.ISSUES_REPORTED_DATE}"><c:out
																	value="${Issues.ISSUES_REPORTED_DATE}" /></abbr>

													</small></td>

													<td><c:choose>
															<c:when test="${Issues.ISSUES_TYPE_ID == 1 || Issues.ISSUES_TYPE_ID == 6}">
																<a
																	href="/VehicleIssuesDetails.in?vid=${Issues.ISSUES_VID}"
																	data-toggle="tip" data-original-title="Click Details">
																	<c:out value="${Issues.ISSUES_VEHICLE_REGISTRATION}" />
																</a>
																	<c:if test="${configuration.showVehicleGroup }">
																	<br>
																	<c:out value="${Issues.ISSUES_VEHICLE_GROUP}" />
																</c:if>
																<c:if test="${configuration.showVehicleTypeAndMaker }">
																	<br>
																	<c:out value="${Issues.vehicleType}" />
																	<br>
																	<c:out value="${Issues.vehicleMaker}" />
																</c:if>
															</c:when>
															<c:when test="${Issues.ISSUES_TYPE_ID == 2}">
																<a
																	href="/showDriver.in?driver_id=${Issues.ISSUES_DRIVER_ID}"
																	data-toggle="tip"
																	data-original-title="Click Driver Details"> <c:out
																		value="${Issues.ISSUES_DRIVER_NAME}" />
																</a>
															</c:when>
															<c:otherwise>
																<a
																	href="/showBranch.in?branch_id=${Issues.ISSUES_BRANCH_ID}"
																	data-toggle="tip"
																	data-original-title="Click Branch Details"> <c:out
																		value="${Issues.ISSUES_BRANCH_NAME}" />
																</a>
																<br>
																<a
																	href="/showBranch.in?branch_id=${Issues.ISSUES_BRANCH_ID}"
																	data-toggle="tip"
																	data-original-title="Click Branch Details"> <c:out
																		value="${Issues.ISSUES_DEPARTNMENT_NAME}" />
																</a>
															</c:otherwise>
														</c:choose></td>


													<td class="col-sm-3"><small>${Issues.ISSUES_ASSIGN_TO_NAME}</small></td>
													<td><c:choose>
															<c:when test="${Issues.ISSUES_STATUS_ID == 2}">
																<small class="label label-success"><c:out
																		value="${Issues.ISSUES_STATUS}" /></small>
															</c:when>
															<c:when test="${Issues.ISSUES_STATUS_ID == 5}">
																<small class="label label-danger"><c:out
																		value="${Issues.ISSUES_STATUS}" /></small>
															</c:when>
															<c:when test="${Issues.ISSUES_STATUS_ID == 1}">
																<small class="label label-info"><c:out
																		value="${Issues.ISSUES_STATUS}" /></small>
															</c:when>
															<c:otherwise>
																<small class="label label-warning"><c:out
																		value="${Issues.ISSUES_STATUS}" /></small>
															</c:otherwise>
														</c:choose></td>
														<c:choose>
															<c:when test="${Issues.issueAnalysis == true }">
																<td class="text-success">Analysis Done</td>
															</c:when>
															<c:otherwise>
																<td class="text-danger">Analysis Pending</td>
															</c:otherwise>
														</c:choose>
													<td class="text-danger">${Issues.ISSUES_DIFFERENCES_DATE}</td>
													<c:choose>
														<c:when test="${Issues.ISSUES_STATUS_ID != 1 }">
															<td>
																<small class="label label-warning">
																<c:out value="${Issues.ISSUES_STATUS}" /></small>
															</td>
														</c:when>
														<c:otherwise>
															<c:choose>
																<c:when test="${fn:contains(Issues.ISSUES_ASSIGN_TO, email) || Issues.CREATEDBYID == createdById || operateAllIssuePermission == true}">
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
				
				<!--pagination  -->	
								
				<c:url var="firstUrl" value="/vehicleBreakDownIssuesList/1" />
				<c:url var="lastUrl" value="/vehicleBreakDownIssuesList/${deploymentLog.totalPages}" />
				<c:url var="prevUrl" value="/vehicleBreakDownIssuesList/${currentIndex - 1}" />
				<c:url var="nextUrl" value="/vehicleBreakDownIssuesList/${currentIndex + 1}" />
				
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
							<c:url var="pageUrl" value="/${vehicleBreakDownIssuesList}/${i}" />
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

				<c:if test="${empty Issues}">
					<div class="main-body">
						<p class="lead text-muted text-center t-padded">No results
							found</p>

					</div>
				</c:if>
			</sec:authorize>
		</div>
		<div class="modal fade" id="vehicleHealthId" role="dialog">
			<div class="modal-dialog divScroll" style="width: 980px;height: 200px;">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4>Vehicle Health Data</h4>
					</div>

					<div class="modal-body" style="">
						<table id="docTable" style="width: 100%;"
							class="table-responsive table ">
							<thead>
								<tr>
									<th>Vehicle No</th>
									<th>Date/Time</th>
									<th>Status</th>
									<th></th>
								</tr>
							</thead>

							<tbody id="vehicleHealthDetals">
							</tbody>

						</table>
					</div>
				</div>
			</div>
		</div>
	</section>
	<script type="text/javascript">
		$(document).ready(function() {
			var e = document.getElementById("statues").value;
			switch (e) {
			case "ALL":
				document.getElementById("All").className = "active";
				break;
			case e:
				document.getElementById(e).className = "active"
			}
		});
		
		function showIssueStatus(status,flag){
			if(status == 3 && flag == 1){
				showMessage('info','This Issue  Under Workorders Status Hence You Can Not Edit The Issue ')
			}else if(status == 6 && flag == 1){
				showMessage('info','This Issue  Under Service Entries Status Hence You Can Not Edit The Issue ')
			}else if(status == 3 && flag == 2){
				showMessage('info','This Issue  Under Workorders Status Hence You Can Not Delete The Issue ')
			}else if(status == 6 && flag == 2){
				showMessage('info','This Issue  Under Service Entries Status Hence You Can Not Delete The Issue ')
			}
			
			
		}
	</script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/issues/issuesCommon.js"></script>
</div>