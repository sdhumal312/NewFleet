<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/vendor/${vendor.vendorTypeId}/1.in"/>">Vendors</a> / <span
						id="NewVehi">Show Vendor</span>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_VENDOR')">
						<a class="btn btn-success" data-toggle="modal"
							data-target="#VendorDocuemnt"> <i class="fa fa-plus"></i> Add
							Vendor Document
						</a>
					</sec:authorize>
					<a class="btn btn-link"
						href="<c:url value="/${vendor.vendorTypeId}/ShowVendor.in?vendorId=${vendor.vendorId}&page=${SelectPage}"/>">Cancel</a>
				</div>


			</div>
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_VENDOR')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_VENDOR')">
					<div class="row">
						<div class="col-md-4">
							<h3>
								<a
									href="<c:url value="/${vendor.vendorTypeId}/ShowVendor.in?vendorId=${vendor.vendorId}&page=${SelectPage}"/>"
									data-toggle="tip" data-original-title="Click Vendor Details">
									<c:out value="${vendor.vendorName}" />
								</a>
							</h3>
						</div>
					</div>
					<div class="secondary-header-title">
						<ul class="breadcrumb">
							<li>Vendor Type : <a data-toggle="tip"
								data-original-title="Vendor Type "> <c:out
										value="${vendor.vendorType}" /></a></li>
							<li>Phone : <a data-toggle="tip" data-original-title="Phone"><c:out
										value="${vendor.vendorPhone}" /></a></li>
							<li>PAN No : <a data-toggle="tip"
								data-original-title="PAN No"><c:out
										value="${vendor.vendorPanNO}" /></a></li>
							<li>GST No : <a data-toggle="tip"
								data-original-title="GST No"> <c:out
										value="${vendor.vendorGSTNO}" /></a></li>

						</ul>
					</div>
				</sec:authorize>
			</div>

		</div>
	</section>

	<!-- Modal  and create the javaScript call modal -->
	<div class="modal fade" id="VendorDocuemnt" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<form method="post" action="uploadVendorDocument.in"
					enctype="multipart/form-data">

					<div class="panel panel-default">
						<div class="panel-heading clearfix">
							<h3 class="panel-title">New Vendor Document</h3>
						</div>
						<input type="hidden" name="VENDORID" value="${vendor.vendorId}" />

						<div class="panel-body">
							<div class="form-horizontal">

								<div class="row1">
									<div class="L-size">
										<label class="col-md-3">Document Name</label>

									</div>
									<div class="I-size">
										<input type="text" name="VENDOR_DOCNAME" class="form-text"
											maxlength="45"
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

				<c:if test="${param.SaveVenDoc eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Vendor Document Uploaded successfully.
					</div>
				</c:if>
				<c:if test="${param.updateDocument eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Vendor Document Updated successfully.
					</div>
				</c:if>
				<c:if test="${param.deleteDocument eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						The Vendor Document Removed successfully .
					</div>
				</c:if>
				<c:if test="${param.emptyDocument eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Vendor Document file is Empty.
					</div>
				</c:if>
				<div class="row">
					<div class="main-body">
						<sec:authorize access="!hasAuthority('VIEW_VENDOR')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('VIEW_VENDOR')">
							<c:if test="${!empty vendorDoc}">

								<div class="box">
									<div class="box-body">
										<table id="VehicleTable"
											class="table table-hover table-striped">
											<thead>
												<tr>
													<th>Document Name</th>
													<th>File Name</th>
													<th>Download</th>
													<th>Remove</th>

												</tr>
											</thead>
											<tbody>

												<c:forEach items="${vendorDoc}" var="vendorDoc">


													<tr data-object-id="" class="ng-scope">

														<td><c:out value="${vendorDoc.VENDOR_DOCNAME}" /></td>
														<td><c:out value="${vendorDoc.VENDOR_FILENAME}" /></td>

														<td><a
															href="${pageContext.request.contextPath}/download/vendorDocument/${vendorDoc._id}.in">
																<span class="fa fa-download"> Document</span>
														</a></td>
														<td><sec:authorize access="hasAuthority('DELETE_VENDOR')">
																<a
																	href="deleteVendorDocument.in?VDID=${vendorDoc._id}&vendorId=${vendorDoc.VENDORID}"
																	class="confirmation"
																	onclick="return confirm('Are you sure you want to Delete this file ?')">
																	<span class="fa fa-trash"></span>
																</a>
															</sec:authorize></td>

													</tr>

												</c:forEach>
											</tbody>

										</table>
									</div>
								</div>
							</c:if>
							<c:if test="${empty vendorDoc}">
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
				<ul class="nav nav-list">
					<li><sec:authorize access="hasAuthority('EDIT_VENDOR')">
							<a
								href="<c:url value="/${vendor.vendorTypeId}/editVendor.in?vendorId=${vendor.vendorId}&page=${SelectPage}"/>">
								Edit Vendor </a>
						</sec:authorize></li>

				</ul>
			</div>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/DR/DriverMasterValidate.js" />"></script>
</div>