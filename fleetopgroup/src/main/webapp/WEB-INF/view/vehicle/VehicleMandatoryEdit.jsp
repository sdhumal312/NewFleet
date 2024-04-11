<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/vehicle/1/1"/>">Vehicle</a> / <a
						href="<c:url value="/showVehicle.in?vid=${vehicle.vid}"/>"><c:out
							value="${vehicle.vehicle_registration}" /></a> / <span>
						Vehicle Mandatory Compliance</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-link btn-sm"
						href="<c:url value="/showVehicle.in?vid=${vehicle.vid}"/>"> <span
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
							class="zoom" data-title="Amazing "
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
				</sec:authorize>
			</div>
		</div>
	</section>

	<!-- Main content -->
	<section class="content-body">
		<div class="row">
			<div class="col-md-9 col-sm-9 col-xs-12">
				<div class="row">
					<div class="main-body">
						<sec:authorize access="!hasAuthority('ADDEDIT_VEHICLE_PURCHASE')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('ADDEDIT_VEHICLE_PURCHASE')">
							<c:if test="${!empty renewalSubType}">

								<form method="post" id="myForm" action="updateVehicleMandatory"
									enctype="multipart/form-data">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">&times;</button>
										<h4 class="modal-title">Edit Vehicle Mandatory Compliance</h4>
									</div>
									<div class="modal-body">
										<input type="hidden" name="VEHID" value="${vehicle.vid}" />
										<input type="hidden" name="VEHICLE_REGISTRATION" value="${vehicle.vehicle_registration}" />
										<!-- this default  -->
										<input type="hidden" name="MANDATORY_NAME" value="" />
										<c:if test="${!empty renewalType}">
											<c:forEach items="${renewalType}" var="renewalType">
												<div class="row">
													<div class="col-md-10">
														<h4>
															<c:out value="${renewalType.renewal_Type}" />
														</h4>
													</div>
												</div>
												<div class="box">
													<div class="box-body">
														<div class="row">
															<c:forEach items="${renewalSubType}" var="renewalSubType">
																<c:if
																	test="${renewalSubType.renewal_id == renewalType.renewal_id}">
																	<div class="col-md-5">
																		<div class="form-group">
																			<div class="col-md-10">
																				<input type="checkbox" name="MANDATORY_ID"
																					value="${renewalSubType.renewal_Subid}" /> <label
																					class="col-md-8"><c:out
																						value=" ${renewalSubType.renewal_SubType}" /></label>
																			</div>
																		</div>
																	</div>
																</c:if>
															</c:forEach>
														</div>
													</div>
												</div>
											</c:forEach>
										</c:if>
										<div class="row">
											<div class="L-size control-label">
												<label> </label>
											</div>
											<div class="I-size">

												<input class="btn btn-success"
													onclick="this.style.visibility = 'hidden'" name="commit"
													type="submit" value="Save Mandatory Compliance"> <input
													type="button" class="btn btn-info" id="btnResetCheckBox"
													value="Reset Check Box" /> <a class="btn btn-link"
													href="<c:url value="/showVehicle.in?vid=${vehicle.vid}"/>">Cancel</a>

											</div>
										</div>
									</div>
								</form>

							</c:if>
							<c:if test="${empty renewalSubType}">
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
	<script type="text/javascript">
$(document).ready(function() {var initValues = ${mandatory};$('#btnResetCheckBox').click(function() {$('#myForm').find(':checkbox[name^="MANDATORY_ID"]').each(function() {$(this).prop("checked",($.inArray($(this).val(),initValues) != -1));});});$('#btnResetCheckBox').trigger('click');});
</script>
</div>