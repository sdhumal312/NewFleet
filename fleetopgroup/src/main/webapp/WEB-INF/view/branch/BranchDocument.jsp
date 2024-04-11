<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
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
						/ <span id="NewVehicle">Show Document</span>
					</div>
					<div class="pull-right">
						<sec:authorize access="hasAuthority('ADD_BRANCH')">
							<a class="btn btn-success" data-toggle="modal"
								data-target="#DriverDocuemnt"> <i class="fa fa-plus"></i>
								Add Branch Document
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
	<!-- Modal  and create the javaScript call modal -->
	<div class="modal fade" id="DriverDocuemnt" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<form method="post" action="uploadBranchDocument"
					enctype="multipart/form-data">
					<div class="panel panel-default">
						<div class="panel-heading clearfix">
							<h3 class="panel-title">New Document</h3>
						</div>
						<input type="hidden" name="branch_id" value="${branch.branch_id}" />
						<div class="panel-body">
							<div class="form-horizontal">
								<div class="row1">
									<div class="L-size">
										<label class="col-md-3">Document Name</label>
									</div>
									<div class="I-size">
										<input type="text" name="branch_documentname"
											class="form-text" maxlength="45"
											onkeypress="return IsDriverDocumentName(event);"
											ondrop="return false;"> <label class="error"
											id="errorDocumentName" style="display: none"> </label>
									</div>
								</div>
								<div class="row1">
									<label class="L-size control-label">Validity From &amp;
										To <abbr title="required">*</abbr>
									</label>
									<div class="I-size">
										<div class="input-group input-append date">
											<input type="text" class="form-text" name="branch_docFrom"
												data-inputmask="'mask': ['99-99-9999  to  99-99-9999']"
												data-mask="" required id="reservation" maxlength="26">
											<span class="input-group-addon add-on"><span
												class="fa fa-calendar"></span></span>
										</div>
									</div>
								</div>
								<br> <br>
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
			<div class="col-md-9">
				<c:if test="${param.addbranchDocument eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Branch Document Uploaded successfully.
					</div>
				</c:if>
				<c:if test="${param.deletebranchDocument eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Branch Document Deleted successfully.
					</div>
				</c:if>

				<c:if test="${param.emptyDocument eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Branch Document file is Empty.
					</div>
				</c:if>
				<div class="row">
					<div class="main-body">
						<div class="box">
							<sec:authorize access="!hasAuthority('VIEW_BRANCH')">
								<spring:message code="message.unauth"></spring:message>
							</sec:authorize>
							<sec:authorize access="hasAuthority('VIEW_BRANCH')">
								<div class="box-body">
									<table id="VehicleTable"
										class="table table-hover table-striped">
										<thead>
											<tr>
												<th>Document Name</th>
												<th>From</th>
												<th>To</th>
												<th>Download</th>
												<th>Remove</th>
											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty branchDocument}">
												<c:forEach items="${branchDocument}" var="branchDocument">
													<tr data-object-id="" class="ng-scope">
														<td><c:out
																value="${branchDocument.branch_documentname}" /></td>
														<td><c:out value="${branchDocument.branch_docFrom}" /></td>
														<td><c:out value="${branchDocument.branch_docTo}" /></td>
														<td><sec:authorize access="hasAuthority('VIEW_BRANCH')">
																<a
																	href="${pageContext.request.contextPath}/download/branchDocument/${branchDocument.branch_documentid}.in">
																	<span class="fa fa-download"> Document</span>
																</a>
															</sec:authorize></td>
														<td><sec:authorize access="hasAuthority('ADD_BRANCH')">
																<a
																	href="deleteBranchDocument.in?branch_documentid=${branchDocument.branch_documentid}&branch_id=${branch.branch_id}"
																	class="confirmation"
																	onclick="return confirm('Are you sure you want to Delete this file ?')">
																	<span class="fa fa-trash"></span>
																</a>
															</sec:authorize></td>
													</tr>
												</c:forEach>
											</c:if>
										</tbody>
									</table>
								</div>
							</sec:authorize>
						</div>
					</div>
				</div>
			</div>
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
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepicker.js" />"></script>
	<script>
		$(function() {
			$('#reservation').daterangepicker();
		});
	</script>
	<script type="text/javascript">
	$(document).ready(function() {
	    $("#datemask").inputmask("dd-mm-yyyy", {
	        placeholder: "dd-mm-yyyy"
	    }), $("[data-mask]").inputmask()
	});
	</script>
</div>