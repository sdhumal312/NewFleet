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
					</a> / Issue Document
				</div>
				<div class="pull-right">

					<sec:authorize access="hasAuthority('VIEW_ISSUES')">
						<a class="btn btn-success" data-toggle="modal"
							data-target="#IssuesDocuemnt"> <i class="fa fa-plus"></i> Add
							Issue Document
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
	<!-- Modal  and create the javaScript call modal -->
	<div class="modal fade" id="IssuesDocuemnt" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<form method="post" action="saveIssuesDocument.in"
					enctype="multipart/form-data">

					<div class="panel panel-default">
						<div class="panel-heading clearfix">
							<h3 class="panel-title">Issue Document</h3>
						</div>
						<input type="hidden" name="ISSUES_ID" value="${Issues.ISSUES_ID}" />

						<div class="panel-body">
							<div class="form-horizontal">

								<div class="row1">
									<div class="L-size">
										<label class="col-md-3">Document Name</label>

									</div>
									<div class="I-size">
										<input type="text" name="ISSUE_DOCUMENTNAME" class="form-text"
											maxlength="148" required="required"
											onkeypress="return IsDriverDocumentName(event);"
											ondrop="return false;"> <label class="error"
											id="errorDocumentName" style="display: none"> </label>
									</div>
								</div>
								<div class="row1">
									<div class="L-size">
										<label class="col-md-3"> Browse: </label>
									</div>
									<div class="I-size">
										<input type="file" accept="image/png, image/jpeg, image/gif"
											name="INPUT_FILE" required="required" />
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="submit" class="btn btn-primary"
									id="js-upload-submit" value="Add Document">Save
									Document</button>
								<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
							</div>

						</div>
					</div>
				</form>
			</div>
		</div>
	</div>

	<!-- Main content -->
	<section class="content-body">
		<div class="row">
			<div class="col-md-9 col-sm-9 col-xs-12">

				<c:if test="${param.saveDocument eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Issue Document Uploaded successfully.
					</div>
				</c:if>
				<c:if test="${param.deleteDocument eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						The Issue Document Removed successfully .
					</div>
				</c:if>
				<c:if test="${param.emptyDocument eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Issue Document file is Empty.
					</div>
				</c:if>
				<c:if test="${param.danger eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Issue Document Not created.
					</div>
				</c:if>
				<div class="row">
					<div class="main-body">
						<div class="main-body">

							<div class="panel panel-default">
								<div class="table-responsive">
									<h2 class="panel-title">Issue Docuemnt</h2>
								</div>

							</div>
						</div>
						<div class="row">
							<sec:authorize access="!hasAuthority('VIEW_ISSUES')">
								<spring:message code="message.unauth"></spring:message>
							</sec:authorize>
							<sec:authorize access="hasAuthority('VIEW_ISSUES')">
								<c:if test="${!empty IssuesDocument}">
									<div class="box">
										<div class="box-body">
											<ul class="mailbox-attachments clearfix">
												<c:forEach items="${IssuesDocument}" var="IssuesDocument">
													<li style="width: 160px;"><span
														class="mailbox-attachment-icon"><i
															class="fa fa-file-pdf-o"></i></span>
														<div class="mailbox-attachment-info">
															<a
																href="${pageContext.request.contextPath}/download/issueDocument/${IssuesDocument.ISSUE_DOCUMENTID_ENCRYPT}"
																class="mailbox-attachment-name"><i
																class="fa fa-paperclip"></i>
																${IssuesDocument.ISSUE_DOCUMENTNAME}</a> <span
																class="mailbox-attachment-size">view.. <a
																href="${pageContext.request.contextPath}/download/issueDocument/${IssuesDocument.ISSUE_DOCUMENTID_ENCRYPT}"
																class="btn btn-default btn-xs pull-right"><i
																	class="fa fa-cloud-download"></i></a> <sec:authorize
																	access="hasAuthority('DELETE_ISSUES')">
																	<a
																		href="deleteIssuesDocument?Id=${IssuesDocument.ISSUES_ID_ENCRYPT}&DID=${IssuesDocument.ISSUE_DOCUMENTID_ENCRYPT}"
																		class="btn btn-default btn-xs pull-right"
																		class="confirmation"
																		onclick="return confirm('Are you sure you want to Delete this file ?')">
																		<i class="fa fa-trash"></i>
																	</a>
																</sec:authorize>
															</span>
														</div></li>
												</c:forEach>
											</ul>
										</div>
									</div>
								</c:if>
								<c:if test="${empty IssuesDocument}">
									<div class="main-body">
										<p class="lead text-muted text-center t-padded">No results
											found</p>

									</div>
								</c:if>
							</sec:authorize>

						</div>
					</div>
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
</div>