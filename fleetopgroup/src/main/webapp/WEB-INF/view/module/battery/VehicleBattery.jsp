<%@ include file="../../taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
	<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/module/battery/vehicleBattery.css" />">
</style>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/vehicle/1/1"/>">Vehicle</a>/ <span>New
						Vehicle layoutTyre</span>
				</div>
				<div class="pull-right" id="deleteDiv">
					<sec:authorize access="hasAuthority('DELETE_BATTERY_INVENTORY')">
					<span id="deleteLayout"></span>
					</sec:authorize>
				</div>
			</div>
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_VEHICLE')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
				<input type="hidden" id="oldBatteryMoveConfig" value="${configuration.oldBatteryMove}">
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
						<h3 id="vehicle" class="secondary-header-title">
						</h3>
						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Status"><a
										href="#"><span id="status"></span></a></span></li>
								<li><input id="CurrentOdometer" type="hidden"> <span
									class="fa fa-clock-o" aria-hidden="true" data-toggle="tip"
									data-original-title="Odometer"><a href="#"><span id="Odometer"></span></a></span></li>
								<li><span class="fa fa-bus" aria-hidden="true"
									data-toggle="tip" data-original-title="Type"><a href="#"><span id="Type"></span></a></span></li>
								<li><span class="fa fa-map-marker" aria-hidden="true"
									data-toggle="tip" data-original-title="Location"><a
										href="#"><span id="Location"></span></a></span></li>
								<li><span class="fa fa-users" aria-hidden="true"
									data-toggle="tip" data-original-title="Group"><a
										href="#"><span id="Group"></span></a></span></li>
								<li><span class="fa fa-road" aria-hidden="true"
									data-toggle="tip" data-original-title="Route"><a
										href="#"><span id="Route"></span></a></span></li>
							</ul>
						</div>
					</div>
				</sec:authorize>
			</div>
		</div>
	</section>

	<section class="content-body">

		<div class="row">
			<sec:authorize access="!hasAuthority('ADDEDIT_VEHICLE_TYRE')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('ADDEDIT_VEHICLE_TYRE')">
				<div class="col-md-10 col-sm-12 col-xs-12">
					<c:if test="${param.Success eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Vehicle Battery Layout Created Successfully.
						</div>
					</c:if>
					<c:if test="${param.MountSuccess eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Vehicle Battery Mount Successfully.
						</div>
					</c:if>
					<c:if test="${param.DismountSuccess eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Vehicle Battery DisMount Successfully.
						</div>
					</c:if>
					<c:if test="${param.RemoveAssignTyre eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							Please Dismount All Battery First.
						</div>
					</c:if>
					<c:if test="${param.deleteLayout eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							Battery Layout Deleted Successfully.
						</div>
					</c:if>
					<c:if test="${param.RotationSuccess eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Vehicle Tyre Rotated Successfully.
						</div>
					</c:if>
					
					<div class="row">

						<div class="col-md-6 col-sm-6 col-xs-12">
							<div class="box">
								<div class="box-body">
									<input type="hidden" id="vid" name="vid" value="${vid}">
									<table>
										<tbody>
													<tr>
															<td id="Axle1"style="display: none;">
																<div id="batteryDetails1" style="text-align: center; position: relative; top: 20px; display: none;"></div>
																<div  class="battery" id="battery1">
																	<div style="padding-left: 10px; position: relative; bottom: 20px;" class="fa fa-plus-circle"></div>
																		<a id="mountBattery1" href="#">Mount</a>
																	<div style="padding-left: 10px; position: relative; top: 10px;" class="fa fa-minus-circle"></div>
																</div>
																
															</td>
															<td id="Axle2" style="display: none;">
															<div id="batteryDetails2" style="text-align: center; position: relative; top: 20px; display: none;"></div>
																<div class="battery" id="battery2">
																	<div style="padding-left: 10px; position: relative; bottom: 20px;" class="fa fa-plus-circle"></div>
																	<a id="mountBattery2" href="#">Mount</a>
																	<div style="padding-left: 10px; position: relative; top: 10px;" class="fa fa-minus-circle"></div>
																</div>
															</td>
															<td id="Axle3" style="display: none;">
															<div id="batteryDetails3" style="text-align: center; position: relative; top: 20px; display: none;"></div>
																<div class="battery" id="battery3">
																	<div style="padding-left: 10px; position: relative; bottom: 20px;" class="fa fa-plus-circle"></div>
																	<a id="mountBattery3" href="#">Mount</a>
																	<div style="padding-left: 10px; position: relative; top: 10px;" class="fa fa-minus-circle"></div>
																</div>
															</td>
													</tr>
													<tr>
															<td id="Axle4" style="display: none;">
															<div id="batteryDetails4" style="text-align: center; position: relative; top: 20px; display: none;"></div>
																<div class="battery" id="battery4">
																	<div style="padding-left: 10px; position: relative; bottom: 20px;" class="fa fa-plus-circle"></div>
																	<a id="mountBattery4" href="#">Mount</a>
																	<div style="padding-left: 10px; position: relative; top: 10px;" class="fa fa-minus-circle"></div>
																</div>
															</td>
															<td id="Axle5" style="display: none;">
															<div id="batteryDetails5" style="text-align: center; position: relative; top: 20px; display: none;"></div>
																<div class="battery" id="battery5">
																	<div style="padding-left: 10px; position: relative; bottom: 20px;" class="fa fa-plus-circle"></div>
																	<a id="mountBattery5" href="#">Mount</a>
																	<div style="padding-left: 10px; position: relative; top: 10px;" class="fa fa-minus-circle"></div>
																</div>
															</td>
															<td id="Axle6" style="display: none;">
															<div id="batteryDetails6" style="text-align: center; position: relative; top: 20px; display: none;"></div>
																<div class="battery" id="battery6">
																	<div style="padding-left: 10px; position: relative; bottom: 20px;" class="fa fa-plus-circle"></div>
																	<a id="mountBattery6" href="#">Mount</a>
																	<div style="padding-left: 10px; position: relative; top: 10px;" class="fa fa-minus-circle"></div>
																</div>
															</td>
													</tr>
													<tr>
															<td id="Axle7" style="display: none;">
															<div id="batteryDetails7" style="text-align: center; position: relative; top: 20px; display: none;"></div>
																<div class="battery" id="battery7">
																	<div style="padding-left: 10px; position: relative; bottom: 20px;" class="fa fa-plus-circle"></div>
																	<a id="mountBattery7" href="#">Mount</a>
																	<div style="padding-left: 10px; position: relative; top: 10px;" class="fa fa-minus-circle"></div>
																</div>
															</td>
															<td id="Axle8" style="display: none;">
															<div id="batteryDetails8" style="text-align: center; position: relative; top: 20px; display: none;"></div>
																<div class="battery" id="battery8">
																	<div style="padding-left: 10px; position: relative; bottom: 20px;" class="fa fa-plus-circle"></div>
																	<a id="mountBattery8" href="#">Mount</a>
																	<div style="padding-left: 10px; position: relative; top: 10px;" class="fa fa-minus-circle"></div>
																</div>
															</td>
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
										<div id="BatteryMountSHoW">
											<table class="table table-striped">
												<tbody>
													
												</tbody>
											</table>

										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<%-- <div class="col-md-1 col-sm-2 col-xs-12">
					<%@include file="../../vehicle/VehicleSideMenu.jsp"%>
				</div> --%>
			</sec:authorize>
		</div>
	</section>
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/battery/getBatteryLayOutDetails.js" />"></script>
	<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>  
	
</div>