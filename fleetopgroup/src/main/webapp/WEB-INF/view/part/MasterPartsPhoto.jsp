<%@ include file="taglib.jsp"%>
<link rel='stylesheet' id='style'
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/photoView/photofream.css"/>"
	type='text/css' media='all' />
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/newMasterParts/1.in"/>">Master Parts</a> / <a
						href="<c:url value="/showMasterParts.in?partid=${MasterParts.partid}"/>"><c:out
							value="${MasterParts.partnumber}" /></a> / <span> Add Photo</span>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_PARTS')">
						<a class="btn btn-success" data-toggle="modal"
							data-target="#myModalPhoto"> <i class="fa fa-plus"></i> Add
							Part Photo
						</a>
					</sec:authorize>
					<a class="btn btn-info"
						href="<c:url value="/newMasterParts/1.in"/>">Cancel</a>
				</div>
			</div>
			<sec:authorize access="!hasAuthority('VIEW_PARTS')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_PARTS')">
				<div class="box-body">
					<div class="pull-left">
						<a
							href="${pageContext.request.contextPath}/getPartImage/${MasterParts.part_photoid}.in"
							class="zoom" data-title="Driver Photo" data-footer=""
							data-type="image" data-toggle="lightbox"> <img
							src="${pageContext.request.contextPath}/getPartImage/${MasterParts.part_photoid}.in"
							class="img-rounded" alt="Cinque Terre" width="100" height="100" />
						</a>
					</div>
					<div class="pull-left1">
						<h3 class="secondary-header-title">
							<a href="showMasterParts.in?partid=${MasterParts.partid}"> <c:out
									value="${MasterParts.partnumber}" /> - <c:out
									value="${MasterParts.partname}" /> - <c:out
									value="${MasterParts.category}" /></a>
						</h3>

						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Part Name"> </span> <span
									class="text-muted"><c:out
											value="${MasterParts.partname}" /></span></li>

								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="category"> </span> <span
									class="text-muted"><c:out
											value="${MasterParts.category}" /></span></li>

								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Make"> </span> <span
									class="text-muted"><c:out value="${MasterParts.make}" /></span></li>

							</ul>
						</div>
					</div>
				</div>
			</sec:authorize>
		</div>
	</section>
	<!-- Modal -->
	<div class="modal fade" id="myModalPhoto" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<form method="post" action="uploadMasterPartsPhoto.in"
					enctype="multipart/form-data">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Upload Part Photo</h4>
					</div>
					<div class="modal-body">
						<div class="form-horizontal">
							<div class="row1">
								<div class="L-size">
									<input type="hidden" name="partid"
										value="${MasterParts.partid}" /> <label
										class="L-size control-label">Title/Photo Name</label>
								</div>
								<div class="I-size">
									<input type="text" name="part_photoname" class="form-text"
										maxlength="25" onkeypress="return IsDriverPhotoName(event);"
										ondrop="return false;"> <label class="error"
										id="errorPhotoName" style="display: none"> </label>
								</div>
							</div>
							<div class="row">
								<div class="L-size"></div>
								<label class="L-size control-label"></label>
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
		<sec:authorize access="!hasAuthority('ADD_PARTS')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('ADD_PARTS')">
			<div class="row">
				<div class="col-md-9 col-sm-9 col-xs-12">
					<c:if test="${param.addMasterPartsPhoto eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Part Photo Uploaded successfully .
						</div>
					</c:if>
					<c:if test="${param.setProfilePhoto  eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Part Profile Photo Uploaded Successfully .
						</div>
					</c:if>

					<c:if test="${param.alreadyProfilePhoto eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Image is A Profile Photo, can not be Deleted.
						</div>
					</c:if>

					<c:if test="${param.Delete eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							The Driver Photo Deleted Successfully .
						</div>
					</c:if>
					<c:if test="${param.dangerProfile eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Part Profile Photo was Not Uploaded.
						</div>
					</c:if>
					<c:if test="${param.danger eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Part Photo was Not created.
						</div>
					</c:if>
					<div class="row">
						<div class="main-body">
							<c:if test="${!empty MasterPartsPhoto}">
								<c:forEach items="${MasterPartsPhoto}" var="MasterPartsPhoto">
									<article class="col-xs-12 col-sm-4 col-md-2">

										<div class="panel panel-default">

											<div class="panel-body">
												<a
													href="${pageContext.request.contextPath}/getPartImage/${MasterPartsPhoto._id}.in"
													title="${MasterPartsPhoto.lastupdated}" class="zoom"
													data-title="Part Photo" data-footer="" data-type="image"
													data-toggle="lightbox"> <img
													src="${pageContext.request.contextPath}/getPartImage/${MasterPartsPhoto._id}.in"
													alt="${MasterPartsPhoto.lastupdated}" /> <span
													class="overlay"><i class="fa fa-fullscreen"></i></span>
												</a>
											</div>
											<div class="panel-footer">
												<h4>
													<a href="#" title="${MasterPartsPhoto.lastupdated}">${MasterPartsPhoto.part_photoname}</a>
												</h4>
												<span class="pull-right"> 
												 <c:if test="${MasterPartsPhoto._id == MasterParts.part_photoid }">
												 	<a href="setRemovePartPhoto.in?part_photoid=${MasterPartsPhoto._id}"
													class="fa fa-user"> Remove Profile</a> 
												 </c:if>
												  <c:if test="${MasterPartsPhoto._id != MasterParts.part_photoid }">
													<a href="setPartPhoto.in?part_photoid=${MasterPartsPhoto._id}"
														class="fa fa-user"> Set Profile</a> 
												 </c:if>
													
													<sec:authorize
														access="hasAuthority('DELETE_PARTS_PHOTO')">
														<a
															href="deleteMasterPartsPhoto.in?part_photoid=${MasterPartsPhoto._id}"
															class="fa fa-trash"> Delete</a>
													</sec:authorize>
												</span>
											</div>
										</div>
									</article>
								</c:forEach>
							</c:if>
						</div>
					</div>
				</div>
				<div class="col-md-2 col-sm-2 col-xs-12">
					<%@include file="MasterPartsSideMenu.jsp"%>
				</div>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/DR/DriverMasterValidate.js" />"></script>
	<script type='text/javascript'
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/photoView/ekko-lightbox.js" />"></script>
</div>