<%@ include file="taglib.jsp"%>
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
						Vehicle Purchase</span>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADDEDIT_VEHICLE_PURCHASE')">
						<a class="btn btn-success btn-sm" data-toggle="modal"
							data-target="#vehiclePurchase"> <i class="fa fa-plus"></i>
							Add Vehicle Purchase
						</a>
					</sec:authorize>
					<a class="btn btn-link btn-sm" href="<c:url value="/vehicle/1/1"/>">
						<span id="AddVehicle"> Cancel</span>
					</a>
				</div>
			</div>
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_VEHICLE')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
					<div class="pull-left">
						<a href="${pageContext.request.contextPath}/getImageVehicle/${vehicle.vehicle_photoid}.in"
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
	<div class="modal fade" id="vehiclePurchase" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<form method="post" action="saveVehiclePurchase"
					enctype="multipart/form-data">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Upload Vehicle Document</h4>
					</div>
					<div class="modal-body">
						<input type="hidden" name="vehid" value="${vehicle.vid}" />
						<div class="row">
							<div class="form-group">
								<label class="col-md-3">Purchase Document Name</label>
								<div class="col-md-6">
									<select name="vPurchaseTypeId" class="form-text" style="width: 100%;">
										<c:forEach items="${vehiclepurchasetype}"
											var="vehiclepurchasetype">
											<option value="${vehiclepurchasetype.ptid}"><c:out
													value="${vehiclepurchasetype.vPurchaseInfoType}" /></option>
										</c:forEach>
									</select> <span class="help-block">Categorize this vehicle.</span>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group">
								<label class="col-md-3">Document</label>
								<div class="col-md-6">
									<input type="file" name="fileUpload" id="file"></input>
								</div>
							</div>
						</div>
					</div>
					<div class="row1 progress-container">
						<div class="progress progress-striped active">
							<div class="progress-bar progress-bar-success" style="width: 0%">
							 Please wait Vehicle Purchase Doc Uploading...</div>
						</div>
					</div>
					<div class="modal-footer">
						<input class="btn btn-success"
							onclick="this.style.visibility = 'hidden'" name="commit"
							type="submit" value="Upload Vehicle Document" id="myButton"
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
				<c:if test="${param.saveVehiclePurchase eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Vehicle Purchase Info Created Successfully.
					</div>
				</c:if>
				<c:if test="${param.revise eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Vehicle Purchase Updated successfully .
					</div>
				</c:if>
				<c:if test="${param.deleteVehiclePurchase eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						The Vehicle Purchase Info Removed successfully .
					</div>
				</c:if>

				<c:if test="${param.alreadyVehiclePurchase eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Vehicle Purchase Not created.
					</div>
				</c:if>

				<c:if test="${param.emptyDocument eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Vehicle Purchase Document Empty.
					</div>
				</c:if>

				<div class="row">
					<div class="main-body">
						<sec:authorize access="!hasAuthority('ADDEDIT_VEHICLE_PURCHASE')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('ADDEDIT_VEHICLE_PURCHASE')">
							<c:if test="${!empty vehiclepurchaseList}">

								<div class="box">
									<div class="box-body">
										<div class="table-responsive">
											<table id="VehicleTable"
												class="table table-bordered table-striped">
												<thead>
													<tr>
														<th class="icon">Document Name</th>
														<th class="icon">Upload Date</th>
														<th class="icon">File Name</th>
														<th class="icon">Download</th>
														<th class="icon">Edit</th>
														<th class="icon">Remove</th>

													</tr>
												</thead>
												<tbody>
													<c:forEach items="${vehiclepurchaseList}"
														var="vehiclepurchase">
														<tr data-object-id="" class="ng-scope">
															<td class="icon">
																	<c:out value="${vehiclepurchase.name}" />
															</td>
															<td class="icon"><c:out
																	value="${vehiclepurchase.uploaddate}" /></td>
															<td class="icon"><c:out
																	value="${vehiclepurchase.filename}" /></td>
															<td class="icon"><a
																href="${pageContext.request.contextPath}/downloadPurchase/${vehiclepurchase.id}.in">
																	<span class="fa fa-download"></span>
															</a></td>
															<td class="icon"><sec:authorize
																	access="hasAuthority('ADDEDIT_VEHICLE_PURCHASE')">
																	<a
																		href="editVehiclePurchase.in?id=${vehiclepurchase.id}">
																		<span class="fa fa-edit"></span>
																	</a>
																</sec:authorize></td>
															<td class="icon"><sec:authorize
																	access="hasAuthority('DELETE_VEHICLE_PURCHASE')">
																	<a
																		href="deleteVehiclePurchase.in?id=${vehiclepurchase.id}&vehid=${vehiclepurchase.vehid}"
																		class="confirmation"
																		onclick="return confirm('Are you sure? Delete ')">
																		<span class="fa fa-trash"></span>
																	</a>
																</sec:authorize></td>
														</tr>
													</c:forEach>

												</tbody>
											</table>
										</div>
									</div>
								</div>
							</c:if>
							<c:if test="${empty vehiclepurchaseList}">
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
			<div class="col-md-2 col-sm-2 col-xs-12">
				<%@include file="VehicleSideMenu.jsp"%>
			</div>
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
