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
							value="${driver.driver_Lastname}" /></a> / New Driver Comment
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
					<sec:authorize access="hasAuthority('ADDEDIT_DRIVER_COMMENT')">
						<a class="btn btn-success" data-toggle="modal"
							data-target="#myModal"> <i class="fa fa-plus"></i> Add Driver
							Comment
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
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<form method="post" action="saveDriverComment.in">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Upload Driver Comment</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<input type="hidden" name="driver_id" value="${driver.driver_id}" />
							<label class="l-size">Title/Name</label>
							<div class="I-sze">
								<input type="text" name="driver_title" class="form-text"
									maxlength="25" onkeypress="return IsDriverCommentName(event);"
									ondrop="return false;"> <label class="error"
									id="errorCommentName" style="display: none"> </label>
							</div>
						</div>
						<br>
						<div class="form-group">
							<label for="comment">Comment:</label>
							<textarea class="form-text" rows="5" id="comment"
								name="driver_comment" maxlength="250"></textarea>
						</div>
						<input type="hidden" name="createdBy" value="${user}" />

					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary"
							id="js-upload-submit" value="Save Comment">Upload
							Comment</button>
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

				<c:if test="${param.SaveComment eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Driver Comment Uploaded Successfully.
					</div>
				</c:if>

				<c:if test="${param.deleteDriverComment  eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						Driver Comment Deleted Successfully .
					</div>
				</c:if>

				<c:if test="${param.danger eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Driver Comment was Not created.
					</div>
				</c:if>

				<div class="row">
					<sec:authorize access="!hasAuthority('ADDEDIT_DRIVER_COMMENT')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('ADDEDIT_DRIVER_COMMENT')">
						<div class="main-body">
							<div class="main-body">

								<div class="panel panel-default">
									<div class="table-responsive">
										<h2 class="panel-title">Comments</h2>
									</div>

								</div>
							</div>
							<div class="row">
								<c:if test="${!empty driverComment}">
									<ul class="timeline">

										<c:forEach items="${driverComment}" var="driverComment">
											<!-- timeline time label -->
											<li class="time-label"><span class="bg-red">
													${driverComment.creationDate} </span></li>
											<li><i class="fa fa-comments bg-yellow"></i>
												<div class="timeline-item">
													<%-- <span class="time" onload="diff_minutes(${driverComment.created})"><i class="fa fa-clock-o"></i> 27
														mins ago</span> --%>
													<h3 class="timeline-header">
														<a href="#"><i class="fa fa-user"></i> <c:out
																value="${driverComment.createdBy}" /></a> commented on
														<c:out value="${driverComment.driver_title}" />
													</h3>
													<div class="timeline-body">

														<c:out value="${driverComment.driver_comment}" />
													</div>
													<c:if test="${driver.driverStatusId != 6}">
													<div class="timeline-footer">
														<sec:authorize access="hasAuthority('DELETE_DRIVER_COMMENT')">
															<a class="btn btn-info btn-flat btn-xs"
																href="deleteDriverComment.in?driver_commentid=${driverComment.driver_commentid}&driver_id=${driverComment.driver_id}">
																<i class="fa fa-trash"> Delete</i>
															</a>
														</sec:authorize>
													</div>
													</c:if>
												</div></li>

										</c:forEach>
									</ul>

								</c:if>

								<c:if test="${empty driverComment}">
									<div class="main-body">
										<p class="lead text-muted text-center t-padded">
											<spring:message code="label.master.noresilts" />
										</p>

									</div>
								</c:if>


							</div>
						</div>
					</sec:authorize>
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
		
	<!-- <script type="text/javascript">
		$(document).ready(function() {
			var dt1 = 
			var dt2 = new Date();
			var diff =(dt2.getTime() - dt1.getTime()) / 1000;
			 diff /= 60;
			 return Math.abs(Math.round(diff));
		});
	</script>
 -->
</div>