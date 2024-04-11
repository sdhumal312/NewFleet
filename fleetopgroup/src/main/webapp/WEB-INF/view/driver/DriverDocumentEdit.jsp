<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/getDriversList"/>">Driver</a> / <a
						href="<c:url value="/showDriver?driver_id=${driver.driver_id}"/>"><c:out
							value="${driver.driver_firstname} " /> <c:out
							value="${driver.driver_Lastname}" /></a> / <a
						href="<c:url value="/ShowDriverDocument.in?driver_id=${driver.driver_id}"/>">Driver
						Document</a> / Edit Driver Document
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('EDIT_DRIVER')">
						<a class="btn btn-default"
							href="editDriver.in?driver_id=${driver.driver_id}"> <i
							class="fa fa-pencil"></i> Edit Driver
						</a>

					</sec:authorize>
					<sec:authorize access="hasAuthority('ADDEDIT_DRIVER_DOCUMENT')">
						<a class="btn btn-success"> <i class="fa fa-plus"></i> Add
							Driver Document
						</a>
					</sec:authorize>
					<a class="btn btn-link"
						href="<c:url value="/ShowDriverDocument.in?driver_id=${driver.driver_id}"/>">Cancel</a>

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
	<!-- Modal  and create the javaScript call modal -->
	<div class="modal fade" id="DriverDocuemnt" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<form method="post" action="uploadDriverDocument.in"
					enctype="multipart/form-data">

					<div class="panel panel-default">
						<div class="panel-heading clearfix">
							<h3 class="panel-title">New Document</h3>
						</div>
						<input type="hidden" name="driver_id" value="${driver.driver_id}" />

						<div class="panel-body">
							<div class="form-horizontal">

								<div class="row1">
									<div class="L-size">
										<label class="col-md-3">Document Name</label>

									</div>
									<div class="I-size">
										<input type="text" name="driver_documentname"
											class="form-text" maxlength="45"
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
											name="input-file-preview" required="required" />
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="submit" class="btn btn-primary"
									id="js-upload-submit" value="Add Document">Upload
									files</button>
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
				<div class="box box-success">
					<div class="box-header">
						<h3 class="box-title">Revise Driver Document</h3>
					</div>
					<div class="box-body no-padding">
						<sec:authorize access="hasAuthority('ADDEDIT_DRIVER_DOCUMENT')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('ADDEDIT_DRIVER_DOCUMENT')">
							<div class="panel-body">
								<div class="form-horizontal">
									<form action="reviseDriverDocument.in" method="post"
										enctype="multipart/form-data">
										<input type="hidden" name="driver_documentid"
											value="${driverdocument.driver_documentid}"> <input
											type="hidden" name="driver_id"
											value="${driverdocument.driver_id}">
										<div class="row1">
											<label class="L-size control-label">Driver Document
												Name</label>
											<div class="I-size">
												<input class="form-text" id="disabledInput" type="text"
													name="driver_documentname"
													value="${driverdocument.driver_documentname}"
													class="form-text" maxlength="45"
													onkeypress="return IsDriverDocumentReviseName(event);"
													ondrop="return false;"> <label class="error"
													id="errorDocumentReviseName" style="display: none">
												</label>

											</div>
										</div>

										<div class="row1">
											<label class="L-size control-label"> File<abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input type="file" name="file" id="file" required="required"></input>
											</div>
										</div>


										<fieldset>
											<div class="L-size control-label"></div>
											<input class="btn btn-primary" type="submit" value="Update">
											<a class="btn btn-link"
												href="ShowDriverDocument.in?driver_id=${driver.driver_id}">Cancel</a>

										</fieldset>

									</form>
								</div>
							</div>
						</sec:authorize>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/DR/DriverMasterValidate.js" />"></script>
</div>
