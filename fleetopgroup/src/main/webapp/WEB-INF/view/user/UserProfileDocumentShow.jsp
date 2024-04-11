<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/newUserList/1.in"/>">User List</a> / <a>
						User Profile</a>
				</div>
				<div class="pull-right">

					<sec:authorize access="hasAuthority('ADD_USER')">
						<a class="btn btn-success" data-toggle="modal"
							data-target="#myModalDocument"> <i class="fa fa-plus"></i>
							Add Document
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('EDIT_USER')">
						<a class="btn btn-info"
							href="<c:url value="/editUserProfile?id=${userprofile.user_id}"/>">
							<span class="fa fa-pencil"></span> Edit Profile
						</a>
					</sec:authorize>
					<a class="btn btn-link" href="<c:url value="/newUserList/1.in"/>">Cancel </a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<c:if test="${param.saveUserDocument eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				User Profile Document Updated Successfully
			</div>
		</c:if>
		<c:if test="${param.deleteUserDocument eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				User Profile Document Deleted Successfully
			</div>
		</c:if>
		<c:if test="${param.emptyDocument eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				User Profile Document is empty.
			</div>
		</c:if>
		<sec:authorize access="!hasAuthority('VIEW_USER')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_USER')">
			<div class="row">
				<div class="col-md-3 col-sm-4 col-xs-12">
					<div class="box">
						<div class="box-body">

							<div class="col-md-9 col-sm-10 col-xs-12">
								<c:choose>
									<c:when test="${userprofile.photo_id != null}">
										<a
											href="${pageContext.request.contextPath}/getUserProfileImage/${userprofile.photo_id}.in"
											class="zoom" data-title="User Profile"
											data-footer="${userprofile.firstName}" data-type="image"
											data-toggle="lightbox"> <img
											src="${pageContext.request.contextPath}/getUserProfileImage/${userprofile.photo_id}.in"
											class="img-circle"
											alt="<c:out value="${userprofile.firstName} " />" width="200"
											height="200" align="left" />
										</a>
									</c:when>
									<c:otherwise>
										<img src="resources/images/User-Icon.png" alt="User Profile"
											class="img-circle img-responsive" width="200" height="200"
											align="left" />
									</c:otherwise>
								</c:choose>


							</div>
							<div class="col-md-9 col-sm-9 col-xs-12">

								<h3>
									<c:out value="${userprofile.firstName} " />
									<c:out value="${userprofile.lastName}" />
								</h3>
								<small><cite
									title="${userprofile.city},  ${userprofile.state}, ${userprofile.country}">${userprofile.city}
										${userprofile.state}, ${userprofile.country} <i
										class="fa fa-map-marker"> </i>
								</cite></small>
								<p>
									<i class="fa fa-envelope"></i>
									<c:out value=" ${userprofile.user_email}"></c:out>
									<br /> <i class="fa fa-globe"></i><a> <c:out
											value=" ${userprofile.personal_email}"></c:out></a> <br /> <i
										class="fa fa-gift"></i>
									<c:out value=" ${userprofile.dateofbirth}"></c:out>
								</p>
							</div>
							<div class="col-md-9 col-sm-9 col-xs-12">
								<fieldset>
									<legend>Company </legend>
									<br>
									<h5>
										Company :<a href="showCompany?id=${userprofile.company_id}"><c:out
												value=" ${userprofile.company_name}" /></a>
									</h5>
									<h5>
										Branch :<a href="showCompany?id=${userprofile.branch_id}"><c:out
												value=" ${userprofile.branch_name}" /></a>
									</h5>
									<h5>
										Depart. :
										<c:out value=" ${userprofile.department_name}" />
									</h5>
									<h5>
										Design. :
										<c:out value="${userprofile.designation}" />
									</h5>

								</fieldset>
							</div>
							<div class="col-md-9 col-sm-9 col-xs-12">
								<fieldset>
									<c:if test="${!empty roles}">
										<legend>Role </legend>
										<br>
										<h5>
											<c:forEach items="${roles}" var="roles">
												<h5>
													<c:out value="${roles}" />
													<br>
												</h5>
											</c:forEach>
										</h5>
									</c:if>
								</fieldset>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-6 col-sm-6 col-xs-12">
					<div class="box">
						<div class="box-body">
							<fieldset>
								<legend>Document </legend>
								<br>
								<ul class="mailbox-attachments clearfix">
									<c:if test="${!empty userprofileDocument}">
										<c:forEach items="${userprofileDocument}"
											var="userprofileDocument">
											<li style="width: 160px;"><span
												class="mailbox-attachment-icon"><i
													class="fa fa-file-pdf-o"></i></span>
												<div class="mailbox-attachment-info">
													<a
														href="${pageContext.request.contextPath}/download/UserDocument/${userprofileDocument._id}"
														class="mailbox-attachment-name"><i
														class="fa fa-paperclip"></i>
														${userprofileDocument.documentname}</a> <span
														class="mailbox-attachment-size">view.. <a
														href="${pageContext.request.contextPath}/download/UserDocument/${userprofileDocument._id}"
														class="btn btn-default btn-xs pull-right"><i
															class="fa fa-cloud-download"></i></a> <a
														href="deleteUserDocument.in?id=${userprofileDocument.userprofile_id}&documentid=${userprofileDocument._id}"
														class="btn btn-default btn-xs pull-right"
														class="confirmation"
														onclick="return confirm('Are you sure you want to Delete this file ?')">
															<i class="fa fa-trash"></i>
													</a>
													</span>
												</div></li>
										</c:forEach>
									</c:if>
								</ul>
							</fieldset>
						</div>
					</div>
				</div>
				<!-- Modal -->
				<div class="modal fade" id="myModalDocument" role="dialog">
					<div class="modal-dialog">
						<div class="modal-content">
							<form method="post" action="uploadUserProfileDocument"
								enctype="multipart/form-data">

								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal">&times;</button>
									<h4 class="modal-title">Upload User Document</h4>
								</div>
								<div class="modal-body">
									<div class="form-horizontal">
										<div class="row1">
											<div class="L-size">
												<input type="hidden" name="userprofile_id"
													value="${userprofile.userprofile_id}" /> <label class="col-md-3">Title/Document
													Name</label>
											</div>
											<div class="I-size">
												<input type="text" name="documentname" class="form-text"
													maxlength="40"
													onkeypress="return IsDriverPhotoName(event);"
													required="required" ondrop="return false;"> <label
													class="error" id="errorPhotoName" style="display: none">
												</label>
											</div>
										</div>
										<div class="row">
											<div class="L-size"></div>
											<label class="L-size"></label>
											<div class="I-size">
												<input type="file" name="file" id="file" accept=""
													required="required"></input>
											</div>
										</div>
									</div>
								</div>
								<div class="modal-footer">
									<button type="submit" class="btn btn-primary"
										id="js-upload-submit" value="Add Document">Upload
										Document</button>
									<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="col-md-2 col-sm-1 col-xs-12">
					<ul class="nav nav-list">
						<li class="active"><sec:authorize
								access="hasAuthority('VIEW_USER')">
								<a href="showUserProfile.in?id=${userprofile.user_id}">Overview</a>
							</sec:authorize></li>

						<li><sec:authorize access="hasAuthority('ADD_USER')">
								<a href="showUserProfileDocument.in?id=${userprofile.userprofile_id}">
									Documents</a>
							</sec:authorize></li>
					</ul>
				</div>
			</div>
		</sec:authorize>

	</section>
</div>