<%@ include file="taglib.jsp"%>
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
							<a class="btn btn-success btn-sm" href=" <c:url value="/addIssuesDetails.in"/>"> <span
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
		<sec:authorize access="!hasAuthority('VIEW_ISSUES')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_ISSUES')">
			<div class="row">
				<div class="col-md-3 col-sm-5 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-clock-o"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Your Issues</span>  <input
								type="hidden" value="${SelectStatus}" id="statues"><span
								class="info-box-number">${IssuesYou}</span>
						</div>
					</div>
				</div>
				<sec:authorize access="!hasAuthority('VIEW_ALL_ISSUES')">
					<div class="col-md-3 col-sm-3 col-xs-12">
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
					<div class="col-md-3 col-sm-3 col-xs-12">
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
			</div>
			<c:if test="${param.NotFound eq true}">
				<div class="alert alert-warning">
					<button class="close" data-dismiss="alert" type="button">x</button>
					ID Not Available.<br>
				</div>
			</c:if>
			<div class="row">
				<div class="main-body">
					<div class="box">
						<ul class="nav nav-pills">
							<li class=" " id="All"><sec:authorize
									access="hasAuthority('VIEW_ALL_ISSUES')">
									<a href="<c:url value="/issues/1"/>">All  <span
									data-toggle="tip" title="" class="badge bg-yellow"
									data-original-title="${IssuesCount} Issues for you">${IssuesCount}</span></a>
								</sec:authorize></li>
							<li class="" id="YOUR_ISSUE"><a
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
								href="<c:url value="/vehicleBreakDownIssuesList/1"/>">Vehicle Break Down <span
									data-toggle="tip" title="" class="badge bg-yellow"
									data-original-title="${vBreakDownCount} Issues for you">${vBreakDownCount}</span></a></li>
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
					</div>
				</div>
			</div>
			<div class="row">
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
												<th>Vehicle</th>
												<th>Assign To</th>
												<th>TAT</th>
												<th class="fir ar">Status</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${Issues}" var="Issues">
												<tr data-object-id="" class="ng-scope">
													<td class="fit"><a
														href="<c:url value="/showIssues?Id=${Issues.ISSUES_ID_ENCRYPT}"/>"><c:out
																value="I-${Issues.ISSUES_NUMBER}" /></a></td>
													<td class="col-sm-5"><a
														href="<c:url value="/showIssues?Id=${Issues.ISSUES_ID_ENCRYPT}"/>"><strong>
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
																	href="<c:url value="/VehicleIssuesDetails.in?vid=${Issues.ISSUES_VID}" />"
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
																	href="showDriver.in?driver_id=${Issues.ISSUES_DRIVER_ID}"
																	data-toggle="tip"
																	data-original-title="Click Driver Details"> <c:out
																		value="${Issues.ISSUES_DRIVER_NAME}" />
																</a>
															</c:when>
															<c:otherwise>
																<a
																	href="<c:url value="/showBranch.in?branch_id=${Issues.ISSUES_BRANCH_ID}"/>"
																	data-toggle="tip"
																	data-original-title="Click Branch Details"> <c:out
																		value="${Issues.ISSUES_BRANCH_NAME}" />
																</a>
																<br>
																<a
																	href="<c:url value="/showBranch.in?branch_id=${Issues.ISSUES_BRANCH_ID}"/>"
																	data-toggle="tip"
																	data-original-title="Click Branch Details"> <c:out
																		value="${Issues.ISSUES_DEPARTNMENT_NAME}" />
																</a>
															</c:otherwise>
														</c:choose></td>
													<td class="col-sm-3"><small>${Issues.ISSUES_ASSIGN_TO_NAME}</small></td>
													<td class="text-danger">${Issues.ISSUES_DIFFERENCES_DATE}</td>
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

												</tr>
											</c:forEach>
										</tbody>
									</table>

								</div>

							</div>
						</div>
					</div>
				</c:if>
				<c:url var="firstUrl" value="/YourIssues/1" />
				<c:url var="lastUrl" value="/YourIssues/${deploymentLog.totalPages}" />
				<c:url var="prevUrl" value="/YourIssues/${currentIndex - 1}" />
				<c:url var="nextUrl" value="/YourIssues/${currentIndex + 1}" />
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
							<c:url var="pageUrl" value="/YourIssues/${i}" />
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
			</div>
		</sec:authorize>
	</section>
</div>
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
		</script>
