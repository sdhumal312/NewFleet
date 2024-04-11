<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="issuesOpen.in"/>">Issues</a> / <a
						href="<c:url value="showIssues?Id=${Issues.ISSUES_ID_ENCRYPT}"/>">I- <c:out
							value="${Issues.ISSUES_NUMBER}"></c:out>
					</a> / Issue Comments
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('VIEW_ISSUES')">
						<a class="btn btn-success" data-toggle="modal"
							data-target="#myModal"> <i class="fa fa-plus"></i> Add Issue
							Comment
						</a>
					</sec:authorize>
					<a class="btn btn-link" href="showIssues?Id=${Issues.ISSUES_ID_ENCRYPT}">Cancel</a>
				</div>
			</div>
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_ISSUES')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_ISSUES')">
					<c:choose>
						<c:when test="${Issues.ISSUES_TYPE_ID == 1}">
							<div class="row">
								<div class="col-md-8 col-sm-8 col-xs-12">
									<h3 class="secondary-header-title">
										<a href="showVehicle.in?vid=${Issues.ISSUES_VID}"
											data-toggle="tip" data-original-title="Click Vehicle Details">
											<c:out value="${Issues.ISSUES_VEHICLE_REGISTRATION}" />
										</a>
									</h3>
									<div class="secondary-header-title">
										<ul class="breadcrumb">
											<li><c:choose>
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
												</c:choose></li>
											<li><span class="fa fa-user" aria-hidden="true"
												data-toggle="tip" data-original-title="Issues Type">
											</span> <a href=""><c:out value="${Issues.ISSUES_TYPE}" /></a></li>
											<li><span class="fa fa-user" aria-hidden="true"
												data-toggle="tip" data-original-title="Driver Details">
											</span> <a href="showDriver.in?driver_id=${Issues.ISSUES_DRIVER_ID}"><c:out
														value="${Issues.ISSUES_DRIVER_NAME}" /></a></li>
										</ul>
									</div>
								</div>
							</div>
						</c:when>
						<c:when test="${Issues.ISSUES_TYPE_ID == 2}">
							<div class="row">
								<div class="col-md-8 col-sm-8 col-xs-12">
									<h3 class="secondary-header-title">
										<a href="showDriver.in?driver_id=${Issues.ISSUES_DRIVER_ID}"
											data-toggle="tip" data-original-title="Click Driver Details">
											<c:out value="${Issues.ISSUES_DRIVER_NAME}" />
										</a>
									</h3>
									<div class="secondary-header-title">
										<ul class="breadcrumb">
											<li><c:choose>
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
												</c:choose></li>
											<li><span class="fa fa-user" aria-hidden="true"
												data-toggle="tip" data-original-title="Issues Type">
											</span> <a href=""><c:out value="${Issues.ISSUES_TYPE}" /></a></li>
										</ul>
									</div>
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<div class="row">
								<div class="col-md-8 col-sm-8 col-xs-12">
									<h3 class="secondary-header-title">
										<a href="showBranch.in?branch_id=${Issues.ISSUES_BRANCH_ID}"
											data-toggle="tip" data-original-title="Click Branch Details">
											<c:out value="${Issues.ISSUES_BRANCH_NAME}" />
										</a> - <a
											href="showBranch.in?branch_id=${Issues.ISSUES_BRANCH_ID}"
											data-toggle="tip" data-original-title="Click Branch Details">
											<c:out value="${Issues.ISSUES_DEPARTNMENT_NAME}" />
										</a>
									</h3>
									<div class="secondary-header-title">
										<ul class="breadcrumb">
											<li><c:choose>
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
												</c:choose></li>

											<li><span class="fa fa-user" aria-hidden="true"
												data-toggle="tip" data-original-title="Issues Type">
											</span> <a href=""><c:out value="${Issues.ISSUES_TYPE}" /></a></li>
										</ul>
									</div>
								</div>
							</div>
						</c:otherwise>
					</c:choose>
				</sec:authorize>
			</div>
		</div>
	</section>
	<!-- Modal  and create the javaScript call modal -->
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<form method="post" action="saveIssuesComment.in">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Issue Comment</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<input type="hidden" name="ISSUES_ID" value="${Issues.ISSUES_ID}" />
							<label class="l-size">Title/Name</label>
							<div class="I-sze">
								<input type="text" name="ISSUE_TITLE" class="form-text"
									maxlength="25" onkeypress="return IsDriverCommentName(event);"
									ondrop="return false;"> <label class="error"
									id="errorCommentName" style="display: none"> </label>
							</div>
						</div>
						<br>
						<div class="form-group">
							<label for="comment">Comment:</label>
							<textarea class="form-text" rows="5" id="comment"  required="required"
								name="ISSUE_COMMENT" maxlength="900"></textarea>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary"
							id="js-upload-submit" value="Save Comment">Save Comment</button>
						<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<!-- Main content -->
	<section class="content-body">
		<div class="row">
			<div class="col-md-9 col-sm-9 col-xs-12">

				<c:if test="${param.saveComment eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Issue Comment Uploaded Successfully.
					</div>
				</c:if>

				<c:if test="${param.deleteComment  eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						Issue Comment Deleted Successfully .
					</div>
				</c:if>

				<c:if test="${param.danger eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Issue Comment was Not created.
					</div>
				</c:if>

				<div class="row">
					<div class="main-body">
						<div class="main-body">

							<div class="panel panel-default">
								<div class="table-responsive">
									<h2 class="panel-title">Comments</h2>
								</div>

							</div>
						</div>
						<div class="row">

							<ul class="timeline">

								<c:if test="${!empty IssuesComment}">
									<c:forEach items="${IssuesComment}" var="IssuesComment">

										<!-- timeline time label -->
										<li class="time-label"><span class="bg-red">
												${IssuesComment.CREATED_DATE} </span></li>
										<li><i class="fa fa-comments bg-yellow"></i>
											<div class="timeline-item">
												<span class="time"><i class="fa fa-clock-o"></i>
													${IssuesComment.CREATED_DATE_DIFFERENT}</span>
												<h3 class="timeline-header">
													<a data-toggle="tip"
														data-original-title="${IssuesComment.CREATED_EMAIL}"><i
														class="fa fa-user"></i> <c:out
															value="${IssuesComment.CREATEDBY}" /></a> commented on <b><c:out
															value="${IssuesComment.ISSUE_TITLE}" /></b>
												</h3>
												<div class="timeline-body">

													<c:out value="${IssuesComment.ISSUE_COMMENT}" />
												</div>
												<div class="timeline-footer">
													<sec:authorize access="hasAuthority('DELETE_ISSUES')">
														<a class="btn btn-info btn-flat btn-xs"
															href="deleteIssuesComment.in?Id=${IssuesComment.ISSUES_ID_ENCRYPT}&CID=${IssuesComment.ISSUE_COMMENTID_ENCRYPT}">
															<i class="fa fa-trash"> Delete</i>
														</a>
													</sec:authorize>
												</div>
											</div></li>

									</c:forEach>
								</c:if>

								<c:if test="${empty IssuesComment}">
									<div class="main-body">
										<p class="lead text-muted text-center t-padded">
											<spring:message code="label.master.noresilts" />
										</p>

									</div>
								</c:if>
							</ul>


						</div>
					</div>
				</div>
			</div>
			<!-- side issues -->
			<div class="col-md-2 col-sm-2 col-xs-12">
				<ul class="nav nav-list">
					<li class="active"><a href="showIssues?Id=${Issues.ISSUES_ID_ENCRYPT}">Overview</a></li>
					<li><sec:authorize access="hasAuthority('VIEW_ISSUES')">
							<a href="issuesComment?Id=${Issues.ISSUES_ID_ENCRYPT}">Issues Comment
								<span class="pull-right badge bg-aqua">${Comment_Count}</span>
							</a>
						</sec:authorize></li>
					<li><sec:authorize access="hasAuthority('VIEW_ISSUES')">
							<a href="issuesDocument.?Id=${Issues.ISSUES_ID_ENCRYPT}">Issues
								Document <span class="pull-right badge bg-aqua">${Document_Count}</span>
							</a>
						</sec:authorize></li>
					<li><sec:authorize access="hasAuthority('VIEW_ISSUES')">
							<a href="issuesPhoto?Id=${Issues.ISSUES_ID_ENCRYPT}">Issues Photos <span
								class="pull-right badge bg-aqua">${Photo_Count}</span>
							</a>
						</sec:authorize></li>
				</ul>
			</div>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/DR/DriverMasterValidate.js" />"></script>
</div>