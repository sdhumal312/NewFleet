<%@ include file="taglib.jsp"%>
<link rel='stylesheet' id='style'
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/photoView/photofream.css"/>"
	type='text/css' media='all' />
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="row">
					<div class="pull-left">
						<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
							href="<c:url value="/newBranch.in"/>">Branch Details</a> / <a
							href="<c:url value="/showBranch.in?branch_id=${branch.branch_id}"/>">${branch.branch_name}</a>
						/ <span id="NewVehicle">Show Photo</span>
					</div>
					<div class="pull-right">
						<sec:authorize access="hasAuthority('ADD_BRANCH')">
							<a class="btn btn-success" data-toggle="modal"
								data-target="#myModalPhoto"> <i class="fa fa-plus"></i> Add
								Branch Photo
							</a>
						</sec:authorize>
						<sec:authorize access="hasAuthority('ADD_BRANCH')">
							<a class="btn btn-info"
								href="editBranch?branch_id=${branch.branch_id}"> <i
								class="fa fa-pencil"></i> Edit Branch
							</a>
						</sec:authorize>

						<a class="btn btn-default" href="<c:url value="/newBranch.in"/>">
							<span id="AddVehicle"> Cancel</span>
						</a>
					</div>
				</div>
				<sec:authorize access="!hasAuthority('VIEW_BRANCH')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_BRANCH')">

					<div class="pull-left">
						<h3 class="secondary-header-title">
							<a href="showBranch.in?branch_id=${branch.branch_id}"> <c:out
									value="${branch.branch_name}" /> - <c:out
									value="${branch.branch_code}" /></a>
						</h3>
						<span> Company Name :<a
							href="showCompany.in?company_id=${branch.company_id}"> <c:out
									value="${branch.company_name}" /></a></span>
						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Company Name"> </span> <span
									class="text-muted"><c:out value="${branch.company_name}" /></span></li>
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="email"> </span> <span
									class="text-muted"><c:out value="${branch.branch_email}" /></span></li>

								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Phone Number"> </span> <span
									class="text-muted"><c:out
											value="${branch.branch_phonenumber}" /></span></li>
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Phone Number"> </span> <span
									class="text-muted"><c:out
											value="${branch.branch_mobilenumber}" /></span></li>
							</ul>
						</div>
					</div>
				</sec:authorize>
			</div>
		</div>
	</section>

	<!-- Modal -->
	<div class="modal fade" id="myModalPhoto" role="dialog">

		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<form method="post" action="uploadBranchPhoto"
					enctype="multipart/form-data">

					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Upload Branch Photo</h4>
					</div>
					<div class="modal-body">
						<div class="form-horizontal">
							<div class="row1">
								<div class="L-size">
									<label class="col-md-3">Title/Photo Name</label>
								</div>
								<div class="I-size">
									<input type="hidden" name="branch_id"
										value="${branch.branch_id}" /> <input type="text"
										name="branch_photoname" class="form-text" maxlength="25"
										onkeypress="return IsDriverPhotoName(event);"
										ondrop="return false;"> <label class="error"
										id="errorPhotoName" style="display: none"> </label>
								</div>
							</div>
							<div class="row">
								<div class="L-size"></div>
								<label class="L-size"></label>
								<div class="I-size">
									<input type="file" name="file" id="file" accept="image/*"></input>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary"
							id="js-upload-submit" value="Add Document">Upload files</button>
						<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- Main content -->
	<section class="content-body">
		<div class="row">
			<div class="col-md-9">
				<c:if test="${param.addbranchPhoto eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Branch Photo was successfully Uploaded.
					</div>
				</c:if>
				<c:if test="${param.deletebranchPhoto eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						The Branch Photo Deleted Successfully .
					</div>
				</c:if>
				<c:if test="${param.emptyDocument eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Branch Photo file is Empty.
					</div>
				</c:if>
				<div class="row">
					<sec:authorize access="!hasAuthority('VIEW_BRANCH')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_BRANCH')">
						<div class="main-body">
							<c:if test="${!empty branchPhoto}">
								<c:forEach items="${branchPhoto}" var="branchPhoto">
									<article class="col-xs-12 col-sm-6 col-md-3">

										<div class="panel panel-default">

											<div class="panel-body">
												<a
													href="${pageContext.request.contextPath}/BranchImage/${branchPhoto.branch_photoid}.in"
													title="${branchPhoto.branch_uploaddate}" class="zoom"
													data-title="Branch Photo" data-footer="" data-type="image"
													data-toggle="lightbox"> <img
													src="${pageContext.request.contextPath}/BranchImage/${branchPhoto.branch_photoid}.in"
													alt="${branchPhoto.branch_uploaddate}" /> <span
													class="overlay"><i class="fa fa-fullscreen"></i></span>
												</a>
											</div>
											<div class="panel-footer">
												<h4>
													<a href="#" title="${branchPhoto.branch_uploaddate}">${branchPhoto.branch_photoname}</a>
												</h4>
												<span class="pull-right"> <sec:authorize
														access="hasAuthority('ADD_BRANCH')">
														<a
															href="deleteBranchPhoto?branch_photoid=${branchPhoto.branch_photoid}&branch_id=${branch.branch_id}"
															class="confirmation"
															onclick="return confirm('Are you sure you want to Delete this Photo ?')"
															class="fa fa-trash"> Delete</a>
													</sec:authorize>
												</span>
											</div>
										</div>
									</article>

								</c:forEach>
							</c:if>
						</div>
					</sec:authorize>
				</div>

			</div>
			<!-- side reminter -->
			<div class="col-md-2">
				<ul class="nav nav-list">
					<li class="active"><a
						href="showBranch.in?branch_id=${branch.branch_id}">Overview</a></li>
					<li><sec:authorize access="hasAuthority('VIEW_BRANCH')">
							<a href="ShowbranchDocument?branch_id=${branch.branch_id}">Document
								<span class="count muted text-muted pull-right label label-info">${DocumentCount}</span>
							</a>
						</sec:authorize></li>
					<li><sec:authorize access="hasAuthority('VIEW_BRANCH')">
							<a href="ShowBranchPhoto?branch_id=${branch.branch_id}">Photos
								<span class="count muted text-muted pull-right label label-info">${PhotoCount}</span>
							</a>
						</sec:authorize></li>
				</ul>
			</div>
		</div>
	</section>
	<script type='text/javascript'
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/photoView/ekko-lightbox.js"/>"></script>
</div>