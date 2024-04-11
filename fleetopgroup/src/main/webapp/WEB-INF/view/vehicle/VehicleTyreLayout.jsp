<%@ include file="taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">

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
						<a class="btn btn-success" data-toggle="modal"
							data-target="#VehicleComment"> <i class="fa fa-plus"></i>
							Create Chassis Layout
						</a>
					</sec:authorize>
					<a class="btn btn-link" href="showVehicle.in?vid=${vehicle.vid}">Cancel</a>
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

	<section class="content-body">

		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_VEHICLE')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
				<div class="col-md-10 col-sm-12 col-xs-12">
					<c:if test="${param.deleteSuccess eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Vehicle Chassis Layout Deleted Successfully.
						</div>
					</c:if>
					<div class="row">
						<form method="post" action="saveTyreLayout.in">
							<div class="col-md-6 col-sm-6 col-xs-12">
								<div class="box">
									<div class="box-body">
										<input type="hidden" name="VID" value="${vehicle.vid}">
										<input type="hidden" name="VEHICLE_REGIS"
											value="${vehicle.vehicle_registration}">
										<table>
											<tbody>
												<tr id="Axle1" style="display: none">
													<td class="tyreSize">
														<div class="tyre"></div> <input type="hidden"
														name="POSITION" value="LO-1">
													</td>
													<td class="steelSize">
														<div class="tyreSide"></div>
													</td>
													<td class="tyreSize">
														<div id="Dual1_LI"></div>
														<div class="steelNone" id="Single1_LI"
															style="display: block;"></div>
													</td>
													<td class="steelSize2">
														<div class="Steel"></div>
														<div class="SteelDown"></div>
													</td>
													<td class="tyreSize">
														<div id="Dual1_RI"></div>
														<div class="steelNone" id="Single1_RI"
															style="display: block;"></div>
													</td>
													<td class="steelSize">
														<div class="tyreSide"></div>
													</td>
													<td class="tyreSize">
														<div class="tyre"></div> <input type="hidden"
														name="POSITION" value="RO-1">
													</td>
													<td class="tyreCheckbox"><input type="checkbox"
														value="" name="" id="AxleDual1">Duals</td>
												</tr>
												<tr id="Axle1_Stepney" style="display: none">
													<td class="tyreSize"></td>
													<td class="steelSize"></td>
													<td class="tyreSize"></td>
													<td class="steelSize2">
														<div id="Stepney"></div>
													</td>
													<td class="tyreSize"></td>
													<td class="steelSize"></td>
													<td class="tyreSize"></td>
													<td class="tyreCheckbox"><input type="checkbox"
														id="Axle1Stepney" value="" name="">Stepney</td>
												</tr>
												<tr id="Axle2" style="display: none">
													<td class="tyreSize">
														<div class="tyre"></div> <input type="hidden"
														name="POSITION" value="LO-2">
													</td>
													<td class="steelSize">
														<div class="tyreSide"></div>
													</td>
													<td class="tyreSize">
														<div id="Dual2_LI"></div>
														<div class="steelNone" id="Single2_LI"
															style="display: block;"></div>
													</td>
													<td class="steelSize2">
														<div class="tyreSteel"></div>
													</td>
													<td class="tyreSize">
														<div id="Dual2_RI"></div>
														<div class="steelNone" id="Single2_RI"
															style="display: block;"></div>
													</td>
													<td class="steelSize">
														<div class="tyreSide"></div>
													</td>
													<td class="tyreSize">
														<div class="tyre"></div> <input type="hidden"
														name="POSITION" value="RO-2">
													</td>
													<td class="tyreCheckbox"><input type="checkbox"
														id="AxleDual2" name="">Duals</td>
												</tr>
												<tr id="Axle3" style="display: none">
													<td class="tyreSize">
														<div class="tyre"></div> <input type="hidden"
														name="POSITION" value="LO-3">
													</td>
													<td class="steelSize">
														<div class="tyreSide"></div>
													</td>
													<td class="tyreSize">
														<div id="Dual3_LI"></div>
														<div class="steelNone" id="Single3_LI"
															style="display: block;"></div>
													</td>
													<td class="steelSize2">
														<div class="tyreSteel"></div>
													</td>
													<td class="tyreSize">
														<div id="Dual3_RI"></div>
														<div class="steelNone" id="Single3_RI"
															style="display: block;"></div>
													</td>
													<td class="steelSize">
														<div class="tyreSide"></div>
													</td>
													<td class="tyreSize">
														<div class="tyre"></div> <input type="hidden"
														name="POSITION" value="RO-3">
													</td>
													<td class="tyreCheckbox"><input type="checkbox"
														id="AxleDual3" name="">Duals</td>
												</tr>
												<tr id="Axle4" style="display: none">
													<td class="tyreSize">
														<div class="tyre"></div> <input type="hidden"
														name="POSITION" value="LO-4">
													</td>
													<td class="steelSize">
														<div class="tyreSide"></div>
													</td>
													<td class="tyreSize">
														<div id="Dual4_LI"></div>
														<div class="steelNone" id="Single4_LI"
															style="display: block;"></div>
													</td>
													<td class="steelSize2">
														<div class="tyreSteel"></div>
													</td>
													<td class="tyreSize">
														<div id="Dual4_RI"></div>
														<div class="steelNone" id="Single4_RI"
															style="display: block;"></div>
													</td>
													<td class="steelSize">
														<div class="tyreSide"></div>
													</td>
													<td class="tyreSize">
														<div class="tyre"></div> <input type="hidden"
														name="POSITION" value="RO-4">
													</td>
													<td class="tyreCheckbox"><input type="checkbox"
														id="AxleDual4" name="">Duals</td>
												</tr>
												<tr id="Axle5" style="display: none">
													<td class="tyreSize">
														<div class="tyre"></div> <input type="hidden"
														name="POSITION" value="LO-5">
													</td>
													<td class="steelSize">
														<div class="tyreSide"></div>
													</td>
													<td class="tyreSize">
														<div id="Dual5_LI"></div>
														<div class="steelNone" id="Single5_LI"
															style="display: block;"></div>
													</td>
													<td class="steelSize2">
														<div class="tyreSteel"></div>
													</td>
													<td class="tyreSize">
														<div id="Dual5_RI"></div>
														<div class="steelNone" id="Single5_RI"
															style="display: block;"></div>
													</td>
													<td class="steelSize">
														<div class="tyreSide"></div>
													</td>
													<td class="tyreSize">
														<div class="tyre"></div> <input type="hidden"
														name="POSITION" value="RO-5">
													</td>
													<td class="tyreCheckbox"><input type="checkbox"
														id="AxleDual5" name="">Duals</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
							</div>
							<div class="col-md-5 col-sm-4 col-xs-12">
								<div class="row">
									<div class="panel panel-default">
										<div class="panel-body">
											<div class="form-horizontal ">
												<div class="row" style="padding-bottom: 6px;">
													<label class="L-size control-label" style="font-size: 15px;padding-top: 2px;">Axles</label>
													<div class="I-size">
														<select class="form-text select2" name="AXLE"
															style="width: 100%;" id="AxleSelect">
															<option value="0">Axle 0</option>
															<option value="1">Axle 1</option>
															<option value="2">Axle 2</option>
															<option value="3">Axle 3</option>
															<option value="4">Axle 4</option>
															<option value="5">Axle 5</option>
														</select>
													</div>
												</div>
												<br>
												<fieldset>
													<legend id="Classification">Front Tyres</legend>
													<div class="row1">
														<div class="col-md-5 col-sm-4 col-xs-6">
														<label class=" control-label"><span>Front Size:</span><abbr title="required">*</abbr></label>
																<input type="text" id="tyreSize" name="TYRE_FRONT_SIZE_ID"
																	style="width: 100%;" required="required"
																	placeholder="Please select Tyre Size" />
														</div>
														<div class="col-md-4 col-sm-4 col-xs-6">
														<label class=" control-label"><span>Front Pressure:</span><abbr title="required">*</abbr></label>
															<input type="text" class="form-text" id="tyreFrontPressure" onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																name="TYRE_FRONT_PRESSURE" required="required" maxlength="6"
																style="width: 100%;" />
														</div>
														
													</div>
												</fieldset>
												<fieldset>
													<legend id="Classification">Rear Tyres</legend>
													<div class="row1">
														<div class="col-md-5 col-sm-4 col-xs-6">
															<label class=" control-label"><span>Rear Size:</span><abbr title="required">*</abbr></label>
															<input type="text" id="tyreSizeTwo" name="TYRE_REAR_SIZE_ID" style="width: 100%;" required="required"
																placeholder="Please select Tyre Size" />
														</div>
														<div class="col-md-4 col-sm-4 col-xs-6">
															<label class=" control-label"><span>Rear Pressure:</span><abbr title="required">*</abbr></label>
															<input type="text" class="form-text" id="tyreRearPressure" onkeypress="return isNumberKeyWithDecimal(event,this.id);"
															name="TYRE_REAR_PRESSURE" required="required" maxlength="6"   
																style="width: 100%;" />
														</div>
													</div>
												</fieldset>
												<fieldset class="form-actions">
													<div class="pull-left">
														<button type="submit" name="commit"
															class="btn btn-success"
															data-loading-text="<i class='fa fa-spinner fa-spin'></i> Processing Vehicle..">
															<span id="submit">Create Layout</span>
														</button>
														<a class=" btn btn-info"
															href="showVehicle.in?vid=${vehicle.vid}"><span
															id="Cancel">Cancel</span></a>

													</div>

												</fieldset>
											</div>
										</div>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
				<%-- <div class="col-md-1 col-sm-2 col-xs-12" style="width: 12%;">
					<%@include file="VehicleSideMenu.jsp"%>
				</div> --%>
			</sec:authorize>
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
	<script type='text/javascript' src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/photoView/ekko-lightbox.js"/>" ></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VI/TyreLayoutCode.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>
</div>
