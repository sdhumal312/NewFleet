<%@ include file="taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/vehicle/1/1"/>">Vehicle</a> / <a
						href="<c:url value="showVehicle.in?vid=${vehicle.vid}"/>"><c:out
							value="${vehicle.vehicle_registration}" /></a> / <span>New
						Vehicle Issues</span>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_VEHICLE')">
						<a class="btn btn-success btn-sm" data-toggle="modal"
							data-target="#VehicleComment"> <i class="fa fa-plus"></i> Add
							Vehicle Comment
						</a>
					</sec:authorize>
					<a class="btn btn-link btn-sm"
						href="showVehicle.in?vid=${vehicle.vid}">Cancel</a>
				</div>
			</div>
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_VEHICLE')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
					<div class="pull-left">
						<a
							href="${pageContext.request.contextPath}/getImageVehicle/${vehicle.vehicle_photoid}.in"
							class="zoom" data-title="Vehicle Photo" data-footer="" 
							data-type="image" data-toggle="lightbox"> 
							<span class="info-box-icon bg-green" id="iconContainer"><i class="fa fa-bus"></i></span>
							<img src="${pageContext.request.contextPath}/getImageVehicle/${vehicle.vehicle_photoid}.in"
							class="img-rounded" alt=" " width="100" height="100" id="vehicleImage" onerror="hideImageOnError(this)" />
						</a>
					</div>
					<div class="pull-left1">
						<h3 class="secondary-header-title">
							<a href="showVehicle.in?vid=${vehicle.vid}"> <c:out
									value="${vehicle.vehicle_registration}" />
							</a>
						</h3>
						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Status"><a
										href="#"><c:out value=" ${vehicle.vehicle_Status}" /></a></span></li>
								<li><span class="fa fa-clock-o" aria-hidden="true"
									data-toggle="tip" data-original-title="Odometer"><a
										href="#"><c:out value=" ${vehicle.vehicle_Odometer}" /></a></span></li>
								<li><span class="fa fa-bus" aria-hidden="true"
									data-toggle="tip" data-original-title="Type"><a href="#"><c:out
												value=" ${vehicle.vehicle_Type}" /></a></span></li>
								<li><span class="fa fa-map-marker" aria-hidden="true"
									data-toggle="tip" data-original-title="Location"><a
										href="#"><c:out value=" ${vehicle.vehicle_Location}" /></a></span></li>
								<li><span class="fa fa-users" aria-hidden="true"
									data-toggle="tip" data-original-title="Group"><a
										href="#"><c:out value=" ${vehicle.vehicle_Group}" /></a></span></li>
								<li><span class="fa fa-road" aria-hidden="true"
									data-toggle="tip" data-original-title="Route"><a
										href="#"><c:out value=" ${vehicle.vehicle_RouteName}" /></a></span></li>
							</ul>
						</div>
					</div>
				</sec:authorize>
			</div>
		</div>
	</section>
	<div class="modal fade" id="VehicleComment" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<form method="post" action="saveVehicleComment.in">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Vehicle Comment</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<input type="hidden" name="VID" value="${vehicle.vid}" /> <label
								class="l-size">Title/Name</label>
							<div class="I-sze">
								<input type="text" name="VEHICLE_TITLE" class="form-text" 
									maxlength="25" onkeypress="return IsDriverCommentName(event);"
									ondrop="return false;"> <label class="error"
									id="errorCommentName" style="display: none"> </label>
							</div>
						</div>
						<br>
						<div class="form-group">
							<label for="comment">Comment:</label>
							<textarea class="form-text" rows="5" id="comment"
								name="VEHICLE_COMMENT" maxlength="900"></textarea>
						</div>
					</div>
					<div class="modal-footer">
						<input class="btn btn-success"
							onclick="this.style.visibility = 'hidden'" name="commit"
							type="submit" value="Save Vehicle Comment">
						
						<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<section class="content-body">
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_VEHICLE')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
				<div class="col-md-9 col-sm-9 col-xs-12">
					<div class="row">
						<c:if test="${param.saveComment eq true}">
							<div class="alert alert-success">
								<button class="close" data-dismiss="alert" type="button">x</button>
								This Vehicle Comment Uploaded Successfully.
							</div>
						</c:if>
						<c:if test="${param.deleteComment  eq true}">
							<div class="alert alert-success">
								<button class="close" data-dismiss="alert" type="button">x</button>
								This Vehicle Comment Deleted Successfully .
							</div>
						</c:if>
						<c:if test="${param.danger eq true}">
							<div class="alert alert-danger">
								<button class="close" data-dismiss="alert" type="button">x</button>
								This Vehicle Comment was Not created.
							</div>
						</c:if>
						<div class="main-body">
							<div class="panel panel-default">
								<div class="table-responsive">
									<h2 class="panel-title">Vehicle Comments</h2>
								</div>
							</div>
						</div>
						<div class="row">
							<ul class="timeline">
								<c:if test="${!empty vehicleComment}">
									<c:forEach items="${vehicleComment}" var="vehicleComment">
										<li class="time-label"><span class="bg-red">
												${vehicleComment.CREATED_DATE} </span></li>
										<li><i class="fa fa-comments bg-yellow"></i>
											<div class="timeline-item">
												<span class="time"><i class="fa fa-clock-o"></i>
													${vehicleComment.CREATED_DATE_DIFFERENT}</span>
												<h3 class="timeline-header">
													<a data-toggle="tip"
														data-original-title="${vehicleComment.CREATED_EMAIL}"><i
														class="fa fa-user"></i> <c:out
															value="${vehicleComment.CREATEDBY}" /></a> commented on <b><c:out
															value="${vehicleComment.VEHICLE_TITLE}" /></b>
												</h3>
												<div class="timeline-body">
													<c:out value="${vehicleComment.VEHICLE_COMMENT}" />
												</div>
												<div class="timeline-footer">
													<sec:authorize access="hasAuthority('DELETE_VEHICLE_COMMENT')">
														<a class="btn btn-info btn-flat btn-xs"
															href="deleteVehicleComment?VId=${vehicleComment.VEHICLE_ID}&CId=${vehicleComment.VEHICLE_COMMENTID}">
															<i class="fa fa-trash"> Delete</i>
														</a>
													</sec:authorize>
												</div>
											</div></li>
									</c:forEach>
								</c:if>
								<c:if test="${empty vehicleComment}">
									<div class="main-body">
										<p class="lead text-muted text-center t-padded">
											<spring:message code="label.master.noresilts" />
										</p>
									</div>
								</c:if>
							</ul>
						</div>
					</div>
				</div>
				<div class="col-md-2 col-sm-2 col-xs-12">
					<%@include file="VehicleSideMenu.jsp"%>
				</div>
			</sec:authorize>
		</div>
	</section>
</div>
	<script type="text/javascript">
		 $(document).ready(function() {
	         var img = $("#vehicleImage");
	         var iconContainer = $("#iconContainer");
	
	         // Check if the image is loaded
	         img.on("load", function() {
	             // If loaded, hide the icon
	             iconContainer.hide();
	         });
	     });
	</script>
	<script type='text/javascript' src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/photoView/ekko-lightbox.js"/>" ></script>
