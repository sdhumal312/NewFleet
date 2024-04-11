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
						href="<c:url value="/vehicle/1/1"/>">Vehicle</a> / <a
						href="<c:url value="showVehicle.in?vid=${vehicle.vid}"/>"><c:out
							value="${vehicle.vehicle_registration}" /></a> / <span>New
						Vehicle Photo</span>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADDEDIT_VEHICLE_PHOTO')">
						<a class="btn btn-success btn-sm" data-toggle="modal"
							data-target="#vehicleDocuemnt"> <i class="fa fa-plus"></i>
							Add Vehicle Photo
						</a>
					</sec:authorize>
					<a class="btn btn-link btn-sm"
						href="<c:url value="/vehicle/1/1"/>"> <span
						id="AddVehicle"> Cancel</span>
					</a>
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
							 <span class="info-box-icon bg-green" id="iconContainer">
								        <i class="fa fa-bus"></i>
							 </span>
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
	<!-- Modal  and create the javaScript call modal -->
	<div class="modal fade" id="vehicleDocuemnt" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<form method="post" action="saveVehiclePhoto"
					enctype="multipart/form-data">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Upload Photo Document</h4>
					</div>
					<div class="modal-body">
						<input type="hidden" name="vehid" value="${vehicle.vid}" />
						<div class="row">
							<div class="form-group">
								<label class="col-md-3">PhotoName</label>
								<div class="col-md-6">
									<select name="photoTypeId" class="form-text" style="width: 100%;">
										<c:forEach items="${vehiclephotype}" var="vehiclephotype">
											<option value="${vehiclephotype.ptid}" ><c:out value="${vehiclephotype.vPhoType}" /></option>
										</c:forEach>
									</select> <span class="help-block">Categorize this vehicle.</span>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group">

								<label class="col-md-3">Document</label>
								<div class="col-md-6">
									<input type="file" name="fileUpload" accept="image/*" id="file"></input>
								</div>
							</div>
						</div>
					</div>
					<div class="row1 progress-container">
						<div class="progress progress-striped active">
							<div class="progress-bar progress-bar-success" style="width: 0%">
							 Please wait Vehicle Photo Uploading...</div>
						</div>
					</div>
					<div class="modal-footer">
						<input class="btn btn-success"
							onclick="this.style.visibility = 'hidden'" name="commit"
							type="submit" value="Upload Vehicle Photo" id="myButton"
							data-loading-text="Loading..." class="btn btn-primary"
							autocomplete="off" id="js-upload-submit" value="Add Document"
							data-toggle="modal" data-target="#processing-modal">
						<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
					</div>
					<script>
						$('#myButton').on('click', function() {
							//alert("hi da")
							$(".progress-bar").animate({
								width : "100%"
							}, 500);
							var $btn = $(this).button('loading')
							// business logic...

							$btn.button('reset')
						})
					</script>
				</form>
			</div>
		</div>
	</div>
	<!-- Main content -->
	<section class="content-body">
		<div class="row">
			<div class="col-md-9 col-sm-9 col-xs-12">
				<c:if test="${param.saveVehiclePhoto eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Vehicle Photo was Successfully Created.
					</div>
				</c:if>
				<c:if test="${param.setVehicleProfilePhoto eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Photo Successfully Selected as Vehicle Profile Photo.
					</div>
				</c:if>
				<c:if test="${param.deleteVehiclePhoto eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						The Vehicle Photo was successfully Removed.
					</div>
				</c:if>
				<c:if test="${param.deleteVehiclePhoto_was_already eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Photo was already Profile Photo Will Not Be Removed.
					</div>
				</c:if>
				<c:if test="${param.emptyPhoto eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Photo Upload Error Or Photo Empty.
					</div>
				</c:if>
				<div class="main-body">
					<sec:authorize access="!hasAuthority('ADDEDIT_VEHICLE_PHOTO')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('ADDEDIT_VEHICLE_PHOTO')">
						<c:if test="${!empty vehiclephotoList}">
							<c:forEach items="${vehiclephotoList}" var="vehiclephoto">
								<article class="col-xs-12 col-sm-4 col-md-3">
									<div class="panel panel-default">
										<div class="panel-body">
											<a
												href="${pageContext.request.contextPath}/getImageVehicle/${vehiclephoto._id}.in"
												title="${vehiclephoto.uploaddateOn}" class="zoom"
												data-title="Vehicle Photo" data-footer="" data-type="image"
												data-toggle="lightbox"> <img
												src="${pageContext.request.contextPath}/getImageVehicle/${vehiclephoto._id}.in"
												alt="${vehiclephoto.uploaddateOn}" /> <span class="overlay"><i
													class="fa fa-fullscreen"></i></span>
											</a>
										</div>
										<div class="panel-footer">
											<h4>
												<a href="#" title="${vehiclephoto.uploaddateOn}">${vehiclephoto.filename}</a>
											</h4>
											<span class="pull-right"> <sec:authorize
													access="hasAuthority('DOWNLOND_VEHICLE')">
													<a
														href="${pageContext.request.contextPath}/downloadPhoto/${vehiclephoto._id}.in"
														class="fa fa-download"> Download</a>
												</sec:authorize> <sec:authorize access="hasAuthority('ADDEDIT_VEHICLE_PHOTO')">
													<c:choose>
														<c:when test="${vehiclephoto._id == vehicle.vehicle_photoid}">
															<a href="removeVehiclePhoto.in?id=${vehiclephoto._id}&vehid=${vehiclephoto.vehid}" class="fa fa-user"> Remove Photo</a>
														</c:when>
														<c:otherwise>
															<a href="setVehiclePhoto.in?id=${vehiclephoto._id}&vehid=${vehiclephoto.vehid}" class="fa fa-user"> Set Photo</a>
														</c:otherwise>
													</c:choose>
												</sec:authorize> <sec:authorize access="hasAuthority('DELETE_VEHICLE_PHOTO')">
													<a
														href="deleteVehiclePhoto.in?id=${vehiclephoto._id}&vehid=${vehiclephoto.vehid}"
														class="fa fa-trash" class="confirmation"
														onclick="return confirm('Are you sure? Delete')">
														Delete</a>
												</sec:authorize>
											</span>
										</div>
									</div>
								</article>
							</c:forEach>
						</c:if>
						<c:if test="${empty vehiclephotoList}">
							<div class="main-body">
								<p class="lead text-muted text-center t-padded">
									<spring:message code="label.master.noresilts" />
								</p>

							</div>
						</c:if>
					</sec:authorize>
				</div>
			</div>
			<div class="col-md-2 col-sm-2 col-xs-12">
				<%@include file="VehicleSideMenu.jsp"%>
			</div>
		</div>
	</section>
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
	<script type='text/javascript'
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/photoView/ekko-lightbox.js"/>" ></script>
</div>
