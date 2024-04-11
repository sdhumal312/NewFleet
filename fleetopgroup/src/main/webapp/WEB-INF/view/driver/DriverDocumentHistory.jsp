<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message code="label.master.home" /></a> / <small><a
						href="<c:url value="/getDriversList"/>">Driver</a></small> / <small><a
						href="<c:url value="/showDriver?driver_id=${driver.driver_id}"/>"><c:out
								value="${driver.driver_firstname} " /> <c:out
								value="${driver.driver_Lastname}" /></a> / New Driver Document
						History</small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('EDIT_DRIVER')">
						<a class="btn btn-default"
							href="editDriver.in?driver_id=${driver.driver_id}"> <i
							class="fa fa-pencil"></i> Edit Driver
						</a>

					</sec:authorize>
					<a class="btn btn-link"
						href="showDriver.in?driver_id=${driver.driver_id}">Cancel</a>

				</div>
			</div>
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_DRIVER')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_DRIVER')">
					<!-- Show the User Profile -->
					<div class="pull-left">
						<a
							href="${pageContext.request.contextPath}/getImage/${driver.driver_photoid}.in"
							class="zoom" data-title="Driver Photo" data-footer=""
							data-type="image" data-toggle="lightbox"> <img
							src="${pageContext.request.contextPath}/getImage/${driver.driver_photoid}.in"
							class="img-rounded" alt="Cinque Terre" width="100" height="100" />
						</a>
					</div>
					<div class="pull-left1">
						<h3 class="secondary-header-title">
							<a href="showDriver.in?driver_id=${driver.driver_id}"> <c:out
									value="${driver.driver_firstname}" /> <c:out
									value="${driver.driver_Lastname}" /></a>
						</h3>

						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Job Role"> </span> <span
									class="text-muted"><c:out
											value="${driver.driver_jobtitle}" /></span></li>
								<li><span class="fa fa-user" aria-hidden="true"
									data-toggle="tip" data-original-title="Group Service"> </span>
									<a href=""><c:out value="${driver.driver_group}" /></a></li>
								<li><span class="fa fa-empire" aria-hidden="true"
									data-toggle="tip" data-original-title="Emp Number"> </span> <span
									class="text-muted"><c:out
											value="${driver.driver_empnumber}" /></span></li>
							</ul>
						</div>

					</div>
				</sec:authorize>
			</div>

		</div>
	</section>

	<!-- Main content -->
	<section class="content-body">
		<div class="row">
			<div class="col-md-9 col-sm-9 col-xs-12">
				<c:if test="${param.emptyDocument eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Driver Document was successfully Edited.
					</div>
				</c:if>
				<c:if test="${param.deleteDriverDocumentHis eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						The Driver Document History Deleted successfully.
					</div>
				</c:if>

				<c:if test="${param.danger eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Driver Renewal Reminder was Not created.
					</div>
				</c:if>
				<div class="row">
					<div class="main-body">
						<div class="box">
							<div class="box-body">
								<sec:authorize access="!hasAuthority('ADDEDIT_DRIVER_DOCUMENT')">
									<spring:message code="message.unauth"></spring:message>
								</sec:authorize>
								<sec:authorize access="hasAuthority('ADDEDIT_DRIVER_DOCUMENT')">
									<table id="VehicleTable"
										class="table table-bordered table-striped">
										<thead>
											<tr>
												<th>Document Name</th>
												<th>File Name</th>
												<th>Upload Date</th>
												<th>Download</th>
												<th>Delete</th>
											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty driverDocumentHistory}">
												<c:forEach items="${driverDocumentHistory}"
													var="driverDocumentHistory">
													<tr data-object-id="" class="ng-scope">
														<td class="icon"><c:out
																value="${driverDocumentHistory.driver_docHisname}" /></td>
														<td class="icon"><i></i> <c:out
																value="${driverDocumentHistory.driver_filename}" /></td>
														<td class="icon"><c:out
																value="${driverDocumentHistory.driver_uploaddate}" /></td>
														<td class="icon"><sec:authorize
																access="hasAuthority('DOWNLOND_DRIVER')">
																<a
																	href="${pageContext.request.contextPath}/download/driverDocHis/${driverDocumentHistory.driver_doHisid}.in">
																	<span class="fa fa-download"></span>
																</a>
															</sec:authorize></td>
														<td class="icon"><sec:authorize
																access="hasAuthority('ADDEDIT_DRIVER_DOCUMENT')">
																<a
																	href="deleteDriverDocHis.in?driver_doHisid=${driverDocumentHistory.driver_doHisid}&driver_id=${driverDocumentHistory.driver_id}"
																	class="confirmation"
																	onclick="return confirm('Are you sure? Delete ')">
																	<span class="fa fa-trash"></span>
																</a>
															</sec:authorize></td>
													</tr>
												</c:forEach>
											</c:if>
										</tbody>
									</table>
								</sec:authorize>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- side reminter -->
			<div class="col-md-2 col-sm-2 col-xs-12">
				<ul class="nav nav-list">
					<li class="active"><a
						href="showDriver?driver_id=${driver.driver_id}">Overview</a></li>

					<li class="active"><a
						href="ShowDriverDocHis.in?driver_id=${driver.driver_id}">Documents
							History</a></li>
					<li><sec:authorize access="hasAuthority('ADDEDIT_DRIVER_REMINDER')">
							<a href="ShowDriverReminder.in?driver_id=${driver.driver_id}">Reminders
								<span class="count muted text-muted pull-right">${ReminderCount}</span>
							</a>
						</sec:authorize></li>

					<li><sec:authorize access="hasAuthority('ADDEDIT_DRIVER_DOCUMENT')">
							<a href="ShowDriverDocument.in?driver_id=${driver.driver_id}">Documents
								<span class="count muted text-muted pull-right">${DocumentCount}</span>
							</a>
						</sec:authorize></li>

					<li><sec:authorize access="hasAuthority('ADDEDIT_DRIVER_COMMENT')">
							<a href="ShowDriverComment.in?driver_id=${driver.driver_id}">Comments
								<span class="count muted text-muted pull-right">${CommentCount}</span>
							</a>
						</sec:authorize></li>

					<li><sec:authorize access="hasAuthority('ADDEDIT_DRIVER_PHOTO')">
							<a href="ShowDriverPhoto.in?driver_id=${driver.driver_id}">Photos
								<span class="count muted text-muted pull-right">${PhotoCount}</span>
							</a>
						</sec:authorize></li>

				</ul>
			</div>
		</div>
	</section>
	<script type="text/javascript"
					src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VI/NewVehiclelanguage.js" />"></script>
</div>