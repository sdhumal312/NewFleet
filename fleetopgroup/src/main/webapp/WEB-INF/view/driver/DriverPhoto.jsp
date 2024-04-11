<%@ include file="taglib.jsp"%>
<link rel='stylesheet' id='style'
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/photoView/photofream.css"/>" type='text/css'
	media='all' />
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
							value="${driver.driver_Lastname}" /></a> / New Driver Photo
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
					<sec:authorize access="hasAuthority('ADDEDIT_DRIVER_PHOTO')">
						<a class="btn btn-success" data-toggle="modal"
							data-target="#myModalPhoto"> <i class="fa fa-plus"></i> Add
							Driver Photo
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
							class="img-rounded" alt="Driver Profile" width="100" height="100" />
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


	<!-- Modal -->
	<div class="modal fade" id="myModalPhoto" role="dialog">

		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<form method="post" action="uploadDriverPhoto.in"
					enctype="multipart/form-data">

					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Upload Driver Photo</h4>
					</div>
					<div class="modal-body">
						<div class="form-horizontal">
							<div class="row1">
								<div class="L-size">
									<input type="hidden" name="driver_id"
										value="${driver.driver_id}" /> <label class="col-md-3">Title/Photo
										Name</label>
								</div>
								<div class="I-size">
									<input type="text" name="driver_photoname" class="form-text"
										maxlength="25" onkeypress="return IsDriverPhotoName(event);"
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
			<div class="col-md-9 col-sm-9 col-xs-12">


				<c:if test="${param.addDriverPhoto eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Driver Photo was successfully Uploaded.
					</div>
				</c:if>
				<c:if test="${param.updateDriverPhoto eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Driver Photo Updated Successfully .
					</div>
				</c:if>

				<c:if test="${param.deleteDriverPhoto_was_already eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Image is A Profile Photo, can not be Deleted.
					</div>
				</c:if>

				<c:if test="${param.deleteDriverPhoto eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						The Driver Photo Deleted Successfully .
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
						<sec:authorize access="!hasAuthority('ADDEDIT_DRIVER_PHOTO')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('ADDEDIT_DRIVER_PHOTO')">

							<c:if test="${!empty driverPhoto}">
								<c:forEach items="${driverPhoto}" var="driverPhoto">
									<article class="col-xs-12 col-sm-4 col-md-3">

										<div class="panel panel-default">

											<div class="panel-body">
												<a
													href="${pageContext.request.contextPath}/getImage/${driverPhoto._id}.in"
													title="${driverPhoto.driver_uploaddate}" class="zoom"
													data-title="Driver Photo" data-footer="" data-type="image"
													data-toggle="lightbox"> <img
													src="${pageContext.request.contextPath}/getImage/${driverPhoto._id}.in"
													alt="${driverPhoto.driver_uploaddate}" /> <span
													class="overlay"><i class="fa fa-fullscreen"></i></span>
												</a>
											</div>
											<div class="panel-footer">
												<h4>
													<a href="#" title="${driverPhoto.driver_uploaddate}">${driverPhoto.driver_photoname}</a>
												</h4>
												<c:if test="${driver.driverStatusId != 6}">
												<span class="pull-right"><sec:authorize
														access="hasAuthority('ADDEDIT_DRIVER_PHOTO')">
														<input type="hidden" name="driver_id" value="${driver.driver_id}">
														<c:choose>
														<c:when test="${driver.driver_photoid == driverPhoto._id}">
															<a href="removeDriverPhoto.in?driver_id=${driver.driver_id}" class="fa fa-user"> Remove Photo</a>
														</c:when>
														<c:otherwise>
														<a href="setPhoto.in?driver_photoid=${driverPhoto._id}" class="fa fa-user"> Set Photo</a>
														</c:otherwise>
													</c:choose>
													</sec:authorize> <sec:authorize access="hasAuthority('DELETE_DRIVER_PHOTO')">
														<a
															href="deleteDriverPhoto.in?driver_photoid=${driverPhoto._id}&driver_id=${driverPhoto.driver_id}"
															class="fa fa-trash"> Delete</a>
													</sec:authorize> </span>
												</c:if>	
											</div>
										</div>
									</article>

								</c:forEach>
							</c:if>
							<c:if test="${empty driverPhoto}">
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
	<script type='text/javascript'
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/photoView/ekko-lightbox.js"/>"></script>



</div>