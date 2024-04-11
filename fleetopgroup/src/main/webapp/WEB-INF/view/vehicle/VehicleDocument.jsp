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
							value="${vehicle.vehicle_registration}" /></a> / <span>
						Vehicle Document</span>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADDEDIT_VEHICLE_DOCUMENT')">
						<a class="btn btn-success btn-sm" data-toggle="modal"
							data-target="#vehicleDocuemnt"> <i class="fa fa-plus"></i>
							Add Vehicle Document
						</a>
					</sec:authorize>
					<a class="btn btn-link btn-sm" href="<c:url value="/vehicle/1/1"/>">
						<span id="AddVehicle"> Cancel</span>
					</a>
				</div>
			</div>
			<sec:authorize access="!hasAuthority('VIEW_VEHICLE')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
				<div class="box-body">
					<div class="pull-left">
						<a
							href="${pageContext.request.contextPath}/getImageVehicle/${vehicle.vehicle_photoid}.in"
							class="zoom" data-title="Amazing Nature"
							data-footer="The beauty of nature" data-type="image"
							data-toggle="lightbox"> <img
							src="${pageContext.request.contextPath}/getImageVehicle/${vehicle.vehicle_photoid}.in"
							class="img-rounded" alt="Cinque Terre" width="100" height="100" />
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
				</div>
			</sec:authorize>
		</div>
	</section>
	<!-- Modal  and create the javaScript call modal -->
	<div class="modal fade" id="vehicleDocuemnt" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<form method="post" action="saveVehicleDocument"
					enctype="multipart/form-data">
					<!-- <form:errors path="*" cssClass="error" /> -->

					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Upload Vehicle Document</h4>
					</div>
					<div class="modal-body">

						<input type="hidden" name="vehid" value="${vehicle.vid}" />
						<div class="row">
							<div class="form-group">
								<label class="col-md-3">DocumentName</label>
								<div class="col-md-6">
									<select name="docTypeId" class="form-text">
										<c:forEach items="${vehicledoctype}" var="vehicledoctype">
											<option value="${vehicledoctype.dtid}"><c:out value="${vehicledoctype.vDocType}" /></option>
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
							<div class="progress-bar progress-bar-success" style="width: 0%">Upload
								Your Driver Entries Please wait..</div>
						</div>
					</div>
					<div class="modal-footer">
						<input class="btn btn-success"
							onclick="this.style.visibility = 'hidden'" name="commit"
							type="submit" value="Vehicle Document" id="myButton"
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
	<section class="content-body">
		<div class="row">
			<div class="col-md-9 col-sm-9 col-xs-12">

				<c:if test="${param.saveVehicleDocument eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Vehicle Document Created Successfully .
					</div>
				</c:if>
				<c:if test="${param.revise eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Driver Document Updated successfully .
					</div>
				</c:if>
				<c:if test="${param.deleteVehicleDocument eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						The Vehicle Document Removed successfully .
					</div>
				</c:if>
				<c:if test="${param.alreadyVehicleDocument eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Document Already Exists.
					</div>
				</c:if>
				<c:if test="${param.emptyDocument eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Document Error Or Empty .
					</div>
				</c:if>
				<div class="row">
					<div class="main-body">
						<sec:authorize access="!hasAuthority('ADDEDIT_VEHICLE_DOCUMENT')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('ADDEDIT_VEHICLE_DOCUMENT')">
							<c:if test="${!empty vehicledocumentList}">
								<div class="box">
									<div class="box-body">
										<h4>
											<span class="label label-success">Vehicle Document</span>
										</h4>
										<div class="table-responsive">
											<table id="VehicleDocument"
												class="table table-hover table-striped">
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
													<c:forEach items="${vehicledocumentList}"
														var="vehicledocument">
														<tr data-object-id="" class="ng-scope">
															<td class="icon">
																	<c:out value="${vehicledocument.name}" />
															</td>
															<td class="icon"><c:out
																	value="${vehicledocument.uploaddate}" /></td>
															<td class="icon"><c:out
																	value="${vehicledocument.filename}" /></td>
															<td class="icon"><sec:authorize
																	access="hasAuthority('DOWNLOND_VEHICLE')">
																	<a
																		href="${pageContext.request.contextPath}/downloaddocument/${vehicledocument.id}.in">
																		<span class="fa fa-download"></span>
																	</a>
																</sec:authorize></td>
															<td class="icon"><sec:authorize
																	access="hasAuthority('ADDEDIT_VEHICLE_DOCUMENT')">
																	<a
																		href="editVehicleDocument.in?id=${vehicledocument.id}">
																		<span class="fa fa-edit"></span>
																	</a>
																</sec:authorize></td>
															<td class="icon"><sec:authorize
																	access="hasAuthority('DELETE_VEHICLE_DOCUMENT')">
																	<a
																		href="deleteVehicleDocument.in?id=${vehicledocument.id}&vehid=${vehicledocument.vehid}"
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
							<c:if test="${empty vehicledocumentList}">
								<div class="main-body">
									<h4>
										<span class="label label-info">Vehicle Document</span>
									</h4>
									<p class="lead text-muted text-center t-padded">
										<spring:message code="label.master.noresilts" />
									</p>
								</div>
							</c:if>
							<c:if test="${!empty renewal}">
								<div class="box">
									<div class="box-body">
										<h4>
											<span class="label label-success">Renewal Document</span>
										</h4>
										<div class="table-responsive">
											<table id="VehicleDocument"
												class="table table-hover table-striped">
												<thead>
													<tr>
														<th class="icon">Renewal Type</th>
														<th class="icon">Renewal SubType</th>
														<!-- <th class="icon">File Name</th> -->
														<th class="icon">Validity From </th>
														<th class="icon">Validity To </th>
														<th class="icon">Download</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${renewal}"
														var="renewal">
														<tr data-object-id="" class="ng-scope">
															<td class="icon">
																	<c:out value="${renewal.renewal_type}" />
															</td>
															<td class="icon"><c:out
																	value="${renewal.renewal_subtype}" /></td>
															<!-- <td></td> -->
															<td class="fit ar"><c:out
																value="${renewal.renewal_from}" /></td>
															<td><c:out value="${renewal.renewal_to}" /><br>
															<i class="fa fa-calendar-check-o"></i> <c:set var="days"
																value="${renewal.renewal_dueDifference}">
															</c:set> <c:choose>
																<c:when test="${fn:contains(days, 'now')}">
																	<span style="color: #06b4ff;"><c:out
																			value="${renewal.renewal_dueDifference}" /></span>
																</c:when>
																<c:when test="${fn:contains(days, 'ago')}">
																	<span style="color: red;"><c:out
																			value="${renewal.renewal_dueDifference}" /></span>

																</c:when>
																<c:otherwise>
																	<span style="color: red;"><c:out
																			value="${renewal.renewal_dueDifference}" /></span>
																</c:otherwise>
															</c:choose></td>			
															<td class="fit"><c:choose>
																<c:when test="${renewal.renewal_document == true}">
																	<sec:authorize
																		access="hasAuthority('DOWNLOND_RENEWAL')">
																		<a
																			href="${pageContext.request.contextPath}/download/RenewalReminder/${renewal.renewal_document_id}.in">
																			<span class="fa fa-download"></span>
																		</a>
																	</sec:authorize>
																</c:when>
															</c:choose></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</c:if>
							<c:if test="${empty renewal}">
								<div class="main-body">
									<h4>
										<span class="label label-info">Renewal Document </span>
									</h4>
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