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
							value="${driver.driver_Lastname}" /></a> / New Driver Document
				</div>
				<div class="pull-right">
				<c:if test="${driver.driverStatusId != 6}">
					<c:if test="${driver.driverStatusId != 2}">
					<sec:authorize access="hasAuthority('EDIT_DRIVER')">
						<a class="btn btn-success btn-sm"
							href="editDriver.in?driver_id=${driver.driver_id}"> <i
							class="fa fa-pencil"></i> Edit Driver
						</a>
					</sec:authorize>
					</c:if>
					<c:if test="${driver.driverStatusId == 2}">
					<sec:authorize access="hasAuthority('INACTIVE_DRIVER_EDIT')">
						<a class="btn btn-success btn-sm"
							href="editDriver.in?driver_id=${driver.driver_id}"> <i
							class="fa fa-pencil"></i> Edit Driver
						</a>
					</sec:authorize>
						</c:if>
					<sec:authorize access="hasAuthority('ADDEDIT_DRIVER_DOCUMENT')">
						<a class="btn btn-success" data-toggle="modal"
							data-target="#DriverDocuemnt"> <i class="fa fa-plus"></i> Add
							Driver Document
						</a>
					</sec:authorize>
					</c:if>
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

				<c:if test="${param.addDriverDocument eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Driver Document Uploaded successfully.
					</div>
				</c:if>
				<c:if test="${param.updateDriverDocument eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Driver Document Updated successfully.
					</div>
				</c:if>
				<c:if test="${param.updateDriverDocumentHis eq true}">
					<div class="alert alert-info">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Driver Document History Uploaded successfully.
					</div>
				</c:if>
				<c:if test="${deleteDriverDocument}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						The Driver Document Removed successfully .
					</div>
				</c:if>
				<c:if test="${param.emptyDocument eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Driver Document file is Empty.
					</div>
				</c:if>
				<c:if test="${param.danger eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Driver Document Not created.
					</div>
				</c:if>
				<div class="row">
					<div class="main-body">
						<sec:authorize access="!hasAuthority('ADDEDIT_DRIVER_DOCUMENT')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('ADDEDIT_DRIVER_DOCUMENT')">
							<c:if test="${!empty driverDocument}">

								<div class="box">
									<div class="box-body">
										<table id="VehicleTable"
											class="table table-hover table-striped">
											<thead>
												<tr>
													<th>Document Name</th>
													<th>File Name</th>
													<th>Download</th>
													<th>Edit</th>
													<th>Remove</th>

												</tr>
											</thead>
											<tbody>

												<c:forEach items="${driverDocument}" var="driverDocument">


													<tr data-object-id="" class="ng-scope">

														<td><c:out
																value="${driverDocument.driver_documentname}" /></td>
														<td><i></i> <c:out
																value="${driverDocument.driver_filename}" /></td>

														<td><sec:authorize
																access="hasAuthority('DOWNLOND_DRIVER')">
																<a
																	href="${pageContext.request.contextPath}/download/driverDocument/${driverDocument.driver_documentid}.in">
																	<span class="fa fa-download"> Document</span>
																</a>
															</sec:authorize></td>
														<td><c:if test="${driver.driverStatusId != 6}"><sec:authorize
																access="hasAuthority('ADDEDIT_DRIVER_DOCUMENT')">
																<a
																	href="editDriverDocument.in?driver_documentid=${driverDocument.driver_documentid}">
																	<span class="fa fa-pencil"> Edit</span>
																</a>
															</sec:authorize></c:if></td>
														<td><c:if test="${driver.driverStatusId != 6}"><sec:authorize
																access="hasAuthority('DELETE_DRIVER_DOCUMENT')">
																<a
																	href="deleteDriverDocument.in?driver_documentid=${driverDocument.driver_documentid}&driver_id=${driverDocument.driver_id}"
																	class="confirmation"
																	onclick="return confirm('Are you sure you want to Delete this file ?')">
																	<span class="fa fa-trash"></span>
																</a>
															</sec:authorize></c:if></td>

													</tr>

												</c:forEach>
											</tbody>

										</table>
									</div>
								</div>
							</c:if>
							<c:if test="${empty driverDocument}">
								<div class="main-body">
									<p class="lead text-muted text-center t-padded">
										<spring:message code="label.master.noresilts" />
									</p>

								</div>
							</c:if>
						</sec:authorize>
					</div>
				</div>
			</div>
			<!-- side reminter -->
			<div class="col-md-2 col-sm-2 col-xs-12">
				<%@include file="DriverSideMenu.jsp"%>
			</div>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/DR/DriverMasterValidate.js" />"></script>
</div>