<%@ include file="../../taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/module/battery/vehicleBattery.css" />">

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
						Vehicle Battery Layout</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-link" href="showVehicle.in?vid=${vehicle.vid}">Cancel</a>
				</div>
			</div>
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_VEHICLE')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
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
							This Vehicle Battery Layout Deleted Successfully.
						</div>
					</c:if>
					<div class="row">
						<form method="post" action="saveBatteryLayout.in">
							<div class="col-md-6 col-sm-6 col-xs-12">
								<div class="box">
									<div class="box-body">
										<input type="hidden" name="vid" value="${vehicle.vid}">
										<table>
											<tbody>
												<tr>
													<td id="Axle1" style="display: none">
														<div class="battery">
															<div style="padding-left: 10px; position: relative; bottom: 20px;" class="fa fa-plus-circle"></div>
															<div style="padding-left: 10px; position: relative; top: 25px;" class="fa fa-minus-circle"></div>
														</div>
													</td>
													<td id="Axle2" style="display: none">
														<div class="battery">
														<div style="padding-left: 10px; position: relative; bottom: 20px;" class="fa fa-plus-circle"></div>
														<div style="padding-left: 10px; position: relative; top: 25px;" class="fa fa-minus-circle"></div>
														</div>
													</td>
													<td id="Axle3" style="display: none">
														<div class="battery">
															<div style="padding-left: 10px; position: relative; bottom: 20px;" class="fa fa-plus-circle"></div>
															<div style="padding-left: 10px; position: relative; top: 25px;" class="fa fa-minus-circle"></div>
														</div>
													</td>
												</tr>
												<tr>
													<td id="Axle4" style="display: none">
														<div class="battery">
															<div style="padding-left: 10px; position: relative; bottom: 20px;" class="fa fa-plus-circle"></div>
															<div style="padding-left: 10px; position: relative; top: 25px;" class="fa fa-minus-circle"></div>
														</div>
													</td>
													<td id="Axle5" style="display: none">
														<div class="battery">
															<div style="padding-left: 10px; position: relative; bottom: 20px;" class="fa fa-plus-circle"></div>
															<div style="padding-left: 10px; position: relative; top: 25px;" class="fa fa-minus-circle"></div>
														</div>
													</td>
													<td id="Axle6" style="display: none">
														<div class="battery">
															<div style="padding-left: 10px; position: relative; bottom: 20px;" class="fa fa-plus-circle"></div>
															<div style="padding-left: 10px; position: relative; top: 25px;" class="fa fa-minus-circle"></div>
														</div>
													</td>
												</tr>
												<tr>
													<td id="Axle7" style="display: none">
														<div class="battery">
															<div style="padding-left: 10px; position: relative; bottom: 20px;" class="fa fa-plus-circle"></div>
															<div style="padding-left: 10px; position: relative; top: 25px;" class="fa fa-minus-circle"></div>
														</div>
													</td>
													<td id="Axle8" style="display: none">
														<div class="battery">
															<div style="padding-left: 10px; position: relative; bottom: 20px;" class="fa fa-plus-circle"></div>
															<div style="padding-left: 10px; position: relative; top: 25px;" class="fa fa-minus-circle"></div>
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
											<div class="form-horizontal ">
												<div class="row">
													<label class="L-size control-label">No Of Battery</label>
													<div class="I-size">
														<select class="form-text select2" name="noOfBattery"
															style="width: 100%;" id="AxleSelect">
															<option value="0">0</option>
															<option value="1">1</option>
															<option value="2">2</option>
															<option value="3">3</option>
															<option value="4">4</option>
															<option value="5">5</option>
															<option value="6">6</option>
															<option value="7">7</option>
															<option value="8">8</option>
														</select>
													</div>
												</div>
												<br/>
												<div class="row1" id="grptyreSize" class="form-group">
													<label class="L-size control-label" for="tyreSize">Battery
														Capacity :<abbr title="required">*</abbr>
													</label>
													<div class="I-size">
														<input type="hidden" id="batteryCapacityId" name="batteryCapacityId"
															style="width: 100%;"
															placeholder="Please select Battery Capacity" /> <span
															id="tyreSizeIcon" class=""></span>
														<div id="tyreSizeErrorMsg" class="text-danger"></div>
													</div>
												</div>
												

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
				<div class="col-md-1 col-sm-2 col-xs-12">
					<%@include file="../../vehicle/VehicleSideMenu.jsp"%>
				</div>
			</sec:authorize>
		</div>
	</section>

	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/battery/createBatteryLayout.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>