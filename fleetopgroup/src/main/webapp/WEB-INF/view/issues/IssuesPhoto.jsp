<%@ include file="taglib.jsp"%>
<link rel='stylesheet' id='style'
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/photoView/photofream.css" />"
	type='text/css' media='all' />
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/issuesOpen.in"/>">Issues</a> / <a
						href="<c:url value="showIssues?Id=${Issues.ISSUES_ID_ENCRYPT}"/>">I- <c:out
							value="${Issues.ISSUES_NUMBER}"></c:out>
					</a> / Issue Comments
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('VIEW_ISSUES')">
						<a class="btn btn-success" data-toggle="modal"
							data-target="#IssuesPhoto"> <i class="fa fa-plus"></i> Add
							Issue Photo
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
								<div class="col-md-8">
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
								<div class="col-md-8">
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
								<div class="col-md-8">
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
	<div class="modal fade" id="IssuesPhoto" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<form method="post" action="saveIssuesPhoto.in"
					enctype="multipart/form-data">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Issue Photo</h4>
					</div>
					<div class="modal-body">
						<div class="form-horizontal">
							<div class="row1">
								<div class="L-size">
									<input type="hidden" name="ISSUES_ID"
										value="${Issues.ISSUES_ID}" /> <label class="col-md-3">Title/Photo
										Name</label>
								</div>
								<div class="I-size">
									<input type="text" name="ISSUE_PHOTONAME" class="form-text"
										required="required" maxlength="248"
										onkeypress="return IsDriverPhotoName(event);"
										ondrop="return false;"> <label class="error"
										id="errorPhotoName" style="display: none"> </label>
								</div>
							</div>
							<div class="row">
								<div class="L-size"></div>
								<label class="L-size"></label>
								<div class="I-size">
									<input type="file" name="INPUT_FILE" id="file" accept="image/*"
										required="required"></input>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary"
							id="js-upload-submit" value="Add Document">Save Photo</button>
						<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<section class="content-body">
		<div class="row">
			<div class="col-md-9 col-sm-9 col-xs-12">
				<c:if test="${param.savePhoto eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Issue Photo was successfully Uploaded.
					</div>
				</c:if>
				<c:if test="${param.deletePhoto eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						The Issue Photo Deleted Successfully .
					</div>
				</c:if>
				<c:if test="${param.danger eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Issue was Not created.
					</div>
				</c:if>
				<div class="row">
					<sec:authorize access="!hasAuthority('VIEW_ISSUES')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_ISSUES')">
						<div class="main-body">
							<c:if test="${!empty IssuesPhoto}">
								<c:forEach items="${IssuesPhoto}" var="IssuesPhoto">
									<article class="col-xs-12 col-sm-4 col-md-3">
										<div class="panel panel-default">
											<div class="panel-body">
												<a
													href="${pageContext.request.contextPath}/getIssuePhoto/${IssuesPhoto.ISSUES_PHOTOID_ENCRYPT}.in"
													title="${IssuesPhoto.ISSUE_UPLOADDATE}" class="zoom"
													data-title="Driver Photo" data-footer="" data-type="image"
													data-toggle="lightbox"> <img
													src="${pageContext.request.contextPath}/getIssuePhoto/${IssuesPhoto.ISSUES_PHOTOID_ENCRYPT}.in"
													alt="${IssuesPhoto.ISSUE_UPLOADDATE}" /> <span
													class="overlay"><i class="fa fa-fullscreen"></i></span>
												</a>
											</div>
											<div class="panel-footer">
												<h4>
													<a href="#" title="${IssuesPhoto.ISSUE_UPLOADDATE}">${IssuesPhoto.ISSUE_PHOTONAME}</a>
												</h4>
												<span class="pull-right"><sec:authorize
														access="hasAuthority('DELETE_ISSUES')">
														<a
															href="deleteIssuesPhoto?PID=${IssuesPhoto.ISSUES_PHOTOID_ENCRYPT}&Id=${IssuesPhoto.ISSUES_ID_ENCRYPT}"
															class="confirmation"
															onclick="return confirm('Are you sure you want to Delete this file ?')"
															class="fa fa-trash"> Delete</a>
													</sec:authorize> </span>
											</div>
										</div>
									</article>
								</c:forEach>
							</c:if>
							<c:if test="${empty IssuesPhoto}">
								<div class="main-body">
									<p class="lead text-muted text-center t-padded">No results
										found</p>
								</div>
							</c:if>
						</div>
					</sec:authorize>
				</div>
			</div>
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
	<script type='text/javascript'
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/photoView/ekko-lightbox.js" />"></script>

</div>