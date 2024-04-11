<%@ include file="taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<style>
.tipTyre, li, ul {
	vertical-align: baseline
}

li, ul {
	margin: 0;
	padding: 0;
	border: 0;
	font-size: 100%
}

ol, ul {
	list-style: none
}

.container .tipTyre {
	text-align: left
}

.tipTyre {
	position: relative;
	display: inline-block;
	zoom: 1
}

.tipTyre li a, .tipTyre li a:hover:after {
	display: block
}

.tipTyre div {
	float: left;
	line-height: 16px;
	font-size: 12px;
	font-weight: 700;
	color: #fff;
	text-shadow: 0 1px rgba(0, 0, 0, .4)
}

.tipTyre:hover ul {
	padding: 4px 0 6px;
	visibility: visible;
	opacity: 1
}

.tipTyre ul {
	visibility: hidden;
	opacity: 0;
	position: absolute;
	bottom: 100%;
	background: #FFF;
	border: 1px solid;
	border-color: #777 #777 #666;
	border-radius: 2px;
	-webkit-transition: .2s ease-out;
	-moz-transition: .2s ease-out;
	-o-transition: .2s ease-out;
	transition: .2s ease-out;
	-webkit-transition-property: opacity, padding, visibility;
	-moz-transition-property: opacity, padding, visibility;
	-o-transition-property: opacity, padding, visibility;
	transition-property: opacity, padding, visibility;
	background-image: -webkit-linear-gradient(top, #FFF, #FFF);
	background-image: -moz-linear-gradient(top, #FFF, #FFF);
	background-image: -o-linear-gradient(top, #FFF, #FFF);
	background-image: linear-gradient(to bottom, #FFF, #FFF);
	-webkit-box-shadow: inset 0 0 0 1px rgba(255, 255, 255, .9), 0 1px 2px
		rgba(0, 0, 0, .1);
	box-shadow: inset 0 0 0 1px rgba(255, 255, 255, .9), 0 1px 2px
		rgba(0, 0, 0, .1)
}

.tipTyre li:first-child:after, .tipTyre ul:after, .tipTyre ul:before {
	content: '';
	display: block;
	position: absolute;
	left: 15px;
	width: 0;
	height: 0;
	border: 7px outset transparent
}

.tipTyre ul:before {
	bottom: -14px;
	border-top: 7px solid #555
}

.tipTyre li:first-child:after {
	bottom: -13px;
	border-top: 7px solid #fff
}

.tipTyre ul:after {
	bottom: -12px;
	border-top: 7px solid #FFF
}

.tipTyre li {
	padding: 0 12px;
	font-size: 11px;
	color: #838ca2;
	text-shadow: 0 1px #fff
}

.tipTyre li.sep {
	margin-top: 4px;
	padding-top: 4px;
	border-top: 1px solid #b4bbce;
	-webkit-box-shadow: inset 0 1px rgba(255, 255, 255, .6);
	box-shadow: inset 0 1px rgba(255, 255, 255, .6)
}

.tipTyre li a {
	position: relative;
	margin: 0 -24px;
	padding: 0 20px 0 12px;
	color: #313a4f;
	text-decoration: none;
	border: 1px solid transparent
}

.tipTyre li a:hover {
	color: #fff;
	text-shadow: 0 1px rgba(0, 0, 0, .3);
	background: #00a65a;
	border-color: #00a65a;
	background-image: -webkit-linear-gradient(top, #00a65a, #00a65a);
	background-image: -moz-linear-gradient(top, #00a65a, #00a65a);
	background-image: -o-linear-gradient(top, #00a65a, #00a65a);
	background-image: linear-gradient(to bottom, #00a65a, #00a65a)
}

.tipTyre li a:after {
	content: '';
	display: none;
	position: absolute;
	top: 50%;
	right: 5px;
	margin-top: -4px;
	width: 0;
	height: 0;
	border: 4px solid transparent;
	border-left-color: #9facd1;
	border-left-color: rgba(255, 255, 255, .4)
}

.tyrePos {
	top: 10px;
	color: #000
}

.tyrePosAssign {
	top: 10px;
	color: #FFF
}
.labelPadding {
	padding-bottom: 5%;
}
.divWidth{
	width: 100%;
}
</style>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/vehicle/1/1"/>">Vehicle</a> / <a
						href="<c:url value="/showVehicle.in?vid=${vehicle.vid}"/>"><c:out
							value="${vehicle.vehicle_registration}" /></a> / <span>New
						Vehicle layoutTyre</span>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('DELETE_VEHICLE_TYRE')">
						<a class="btn btn-warning btn-sm"
							href="<c:url value="/deleteVehicleTyreLayout?Id=${vehicle.vid}"/>"
							class="confirmation"
							onclick="return confirm('Please Remove All Mounted Tyres Frist')">
							<span class="fa fa-trash"></span> Delete Chassis layout
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
								<li><input id="CurrentOdometer" type="hidden"
									value="${vehicle.vehicle_Odometer}"> <span
									class="fa fa-clock-o" aria-hidden="true" data-toggle="tip"
									data-original-title="Odometer"><a href="#"><c:out
												value=" ${vehicle.vehicle_Odometer}" /></a></span></li>
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
			<sec:authorize access="!hasAuthority('ADDEDIT_VEHICLE_TYRE')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('ADDEDIT_VEHICLE_TYRE')">
				<div class="col-md-10 col-sm-12 col-xs-12">
					<c:if test="${param.Success eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Vehicle Chassis Layout Created Successfully.
						</div>
					</c:if>
					<c:if test="${param.MountSuccess eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Vehicle Tyre Mount Successfully.
						</div>
					</c:if>
					<c:if test="${param.DismountSuccess eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Vehicle Tyre DisMount Successfully.
						</div>
					</c:if>
					<c:if test="${param.RemoveAssignTyre eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							Please Dismount All Tyres First.
						</div>
					</c:if>
					<c:if test="${param.RotationSuccess eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Vehicle Tyre Rotated Successfully.
						</div>
					</c:if>
					<c:if test="${param.TyreSize eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							Only Same Tyre Size only you can Rotation .
						</div>
					</c:if>
					<div class="row">

						<div class="col-md-6 col-sm-6 col-xs-12">
							<div class="box">
								<div class="box-body">
									<input type="hidden" name="VID" value="${vehicle.vid}">
									<input type="hidden" name="VEHICLE_REGIS"
										value="${vehicle.vehicle_registration}">
									<%-- <input type="hidden" 
										value="${vehicle.TYRE_FRONT_SIZE}">
										<input type="hidden" 
										value="${vehicle.TYRE_REAR_SIZE}"> --%>
									<table>
										<tbody>
											<c:if test="${!empty tyrelayout}">
												<c:forEach items="${tyrelayout}" var="tyrelayout">
													<c:if test="${tyrelayout.AXLE == 1}">
														<tr id="Axle1">

															<td class="layoutSize">
																<div id="LO-1">
																	<span class="loading ng-hide" id="loading"> <img
																		alt="Loading" class="loading-img"
																		src="resources/images/ajax-loader.gif" />">
																	</span>
																</div>
															</td>
															<td class="layoutSteelSide">
																<div class="layoutTyreSide"></div>
															</td>
															<td class="layoutSize">
																<div id="LI-1">
																	<div class="steelNone" id="Single1_LI"
																		style="display: block;"></div>
																</div>
															</td>
															<td class="layoutSteelSize2">
																<div class="layoutSteel"></div>
																<div class="layoutSteelDown"></div>
															</td>
															<td class="layoutSize">
																<div id="RI-1">
																	<div class="steelNone" id="Single1_RI"
																		style="display: block;"></div>
																</div>
															</td>
															<td class="layoutSteelSide">
																<div class="layoutTyreSide"></div>
															</td>
															<td class="layoutSize">
																<div id="RO-1">
																	<span class="loading ng-hide" id="loading"> <img
																		alt="Loading" class="loading-img"
																		src="resources/images/ajax-loader.gif" />">
																	</span>
																</div>
															</td>
														</tr>
														<tr id="Axle1_Stepney">
															<td class="layoutSize"></td>
															<td class="layoutSteelSide"></td>
															<td class="layoutSize"></td>
															<td class="layoutSteelSize2">
																<div id="ST-1"></div>
															</td>
															<td class="layoutSize"></td>
															<td class="layoutSteelSide"></td>
															<td class="layoutSize"></td>
														</tr>
													</c:if>
													<c:if test="${tyrelayout.AXLE == 2}">

														<tr id="Axle2">
															<td class="layoutSize">
																<div id="LO-2">
																	<span class="loading ng-hide" id="loading"> <img
																		alt="Loading" class="loading-img"
																		src="resources/images/ajax-loader.gif" />">
																	</span>
																</div>
															</td>
															<td class="layoutSteelSide">
																<div class="layoutTyreSide"></div>
															</td>
															<td class="layoutSize">
																<div id="LI-2">
																	<div class="steelNone" id="Single2_LI"
																		style="display: block;"></div>
																</div>
															</td>
															<td class="layoutSteelSize2">
																<div class="layoutTyreSteel"></div>
															</td>
															<td class="layoutSize">
																<div id="RI-2">
																	<div class="steelNone" id="Single2_RI"
																		style="display: block;"></div>
																</div>
															</td>
															<td class="layoutSteelSide">
																<div class="layoutTyreSide"></div>
															</td>
															<td class="layoutSize">
																<div id="RO-2">
																	<span class="loading ng-hide" id="loading"> <img
																		alt="Loading" class="loading-img"
																		src="resources/images/ajax-loader.gif" />">
																	</span>
																</div>
															</td>
														</tr>
													</c:if>
													<c:if test="${tyrelayout.AXLE == 3}">
														<tr id="Axle3">
															<td class="layoutSize">
																<div id="LO-3">
																	<span class="loading ng-hide" id="loading"> <img
																		alt="Loading" class="loading-img"
																		src="resources/images/ajax-loader.gif" />">
																	</span>
																</div>
															</td>
															<td class="layoutSteelSide">
																<div class="layoutTyreSide"></div>
															</td>
															<td class="layoutSize">
																<div id="LI-3">
																	<div class="steelNone" id="Single3_LI"
																		style="display: block;"></div>
																</div>
															</td>
															<td class="layoutSteelSize2">
																<div class="layoutTyreSteel"></div>
															</td>
															<td class="layoutSize">
																<div id="RI-3">
																	<div class="steelNone" id="Single3_RI"
																		style="display: block;"></div>
																</div>
															</td>
															<td class="layoutSteelSide">
																<div class="layoutTyreSide"></div>
															</td>
															<td class="layoutSize">
																<div id="RO-3">
																	<span class="loading ng-hide" id="loading"> <img
																		alt="Loading" class="loading-img"
																		src="resources/images/ajax-loader.gif" />">
																	</span>
																</div>
															</td>
														</tr>
													</c:if>
													<c:if test="${tyrelayout.AXLE == 4}">
														<tr id="Axle4">
															<td class="layoutSize">
																<div id="LO-4">
																	<span class="loading ng-hide" id="loading"> <img
																		alt="Loading" class="loading-img"
																		src="resources/images/ajax-loader.gif" />">
																	</span>
																</div>
															</td>
															<td class="layoutSteelSide">
																<div class="layoutTyreSide"></div>
															</td>
															<td class="layoutSize">
																<div id="LI-4">
																	<div class="steelNone" id="Single4_LI"
																		style="display: block;"></div>
																</div>
															</td>
															<td class="layoutSteelSize2">
																<div class="layoutTyreSteel"></div>
															</td>
															<td class="layoutSize">
																<div id="RI-4">
																	<div class="steelNone" id="Single4_RI"
																		style="display: block;"></div>
																</div>
															</td>
															<td class="layoutSteelSide">
																<div class="layoutTyreSide"></div>
															</td>
															<td class="layoutSize">
																<div id="RO-4">
																	<span class="loading ng-hide" id="loading"> <img
																		alt="Loading" class="loading-img"
																		src="resources/images/ajax-loader.gif" />">
																	</span>
																</div>
															</td>

														</tr>
													</c:if>
													<c:if test="${tyrelayout.AXLE == 5}">
														<tr id="Axle5">
															<td class="layoutSize">
																<div id="LO-5">
																	<span class="loading ng-hide" id="loading"> <img
																		alt="Loading" class="loading-img"
																		src="resources/images/ajax-loader.gif" />">
																	</span>
																</div>
															</td>
															<td class="layoutSteelSide">
																<div class="layoutTyreSide"></div>
															</td>
															<td class="layoutSize">
																<div id="LI-5">
																	<div class="steelNone" id="Single5_LI"
																		style="display: block;"></div>
																</div>
															</td>
															<td class="layoutSteelSize2">
																<div class="layoutTyreSteel"></div>
															</td>
															<td class="layoutSize">

																<div id="RI-5">
																	<div class="steelNone" id="Single5_RI"
																		style="display: block;"></div>
																</div>
															</td>
															<td class="layoutSteelSide">
																<div class="layoutTyreSide"></div>
															</td>
															<td class="layoutSize">
																<div id="RO-5">
																	<span class="loading ng-hide" id="loading"> <img
																		alt="Loading" class="loading-img"
																		src="resources/images/ajax-loader.gif" />">
																	</span>
																</div>
															</td>
														</tr>
													</c:if>
												</c:forEach>
											</c:if>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<div class="col-md-5 col-sm-4 col-xs-12">
							<div class="row">
								<div class="panel panel-default">
									<div class="panel-body">
										<div id="TyreSHoW">
											<span class="loading ng-hide" id="loading"> <img
												alt="Loading" class="loading-img"
												src="resources/images/ajax-loader.gif" />">
											</span> <span>Please Click Tyre Details...</span>
											<table class="table table-striped">
												<tbody>
													<tr class="row">
														<th class="key">Serial No:</th>
														<td class="value"></td>
													</tr>
													<tr class="row">
														<th class="key">Tyre Axle:</th>
														<td class="value"></td>
													</tr>
													<tr class="row">
														<th class="key">Tyre Position:</th>
														<td class="value"></td>
													</tr>
													<tr class="row">
														<th class="key">Tyre Size:</th>
														<td class="value"></td>
													</tr>
													<tr class="row">
														<th class="key">Tyre Pressure:</th>
														<td class="value"></td>
													</tr>
													<tr class="row">
														<th class="key">Brand:</th>
														<td class="value"></td>
													</tr>
													<tr class="row">
														<th class="key">Model:</th>
														<td class="value"></td>
													</tr>
													<tr class="row">
														<th class="key">Price:</th>
														<td class="value"></td>
													</tr>
													<tr class="row">
														<th class="key">Run miles:</th>
														<td class="value"></td>
													</tr>
												</tbody>
											</table>

										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			<%-- 	<div class="col-md-1 col-sm-2 col-xs-12" style="width: 12%;">
					<%@include file="VehicleSideMenu.jsp"%>
				</div> --%>
			</sec:authorize>
		</div>
	</section>
	<script>
		$(document).ready(function() {
			if(${vehicleTyreAssignment} == true || ${vehicleTyreAssignment} == "true"){
				$("#ownerInfo").hide();
				$("#mandatoryCompliance").hide();
				$("#breakDownCalendar").hide();
			}
		});
	</script>
	<script type="text/javascript">
		var tyreLayout = new Array();
		<c:forEach items="${tyrelayoutPosition}" var="ppl">
		var layout = new Object();
		layout.LP_ID = '${ppl.LP_ID}';
		layout.AXLE = '${ppl.AXLE}';
		layout.POSITION = '${ppl.POSITION}';
		layout.TYRE_SIZE = '${ppl.TYRE_SIZE}';
		layout.TYRE_PRESSURE = '${ppl.TYRE_PRESSURE}';
		layout.TYRE_ID = '${ppl.TYRE_ID}';
		layout.TYRE_SERIAL_NO = '${ppl.TYRE_SERIAL_NO}';
		layout.TYRE_ASSIGNED = '${ppl.TYRE_ASSIGNED}';
		layout.TYRE_SIZE_ID = '${ppl.TYRE_SIZE_ID}';

		tyreLayout.push(layout);
		</c:forEach>
		for (var i = 0; i < tyreLayout.length; i++) {
			/*  alert(peopleList[i].name);
			 alert(peopleList[i].position); */
			if (tyreLayout[i].TYRE_ASSIGNED == 'true') {
				$("#" + tyreLayout[i].POSITION)
						.html(
								'<div   class="layoutTyreAssign tipTyre"><span class="tyrePosAssign">'
										+ tyreLayout[i].POSITION
										+ '</span><ul>'
										+ '<li><a onclick="javascript:DisMountTyreDetails(\''
										+ tyreLayout[i].LP_ID
										+ '\', \''
										+ tyreLayout[i].AXLE
										+ '\', \''
										+ tyreLayout[i].POSITION
										+ '\', \''
										+ tyreLayout[i].TYRE_SIZE
										+ '\', \''
										+ tyreLayout[i].TYRE_PRESSURE
										+ '\', \''
										+ tyreLayout[i].TYRE_ID
										+ '\', \''
										+ tyreLayout[i].TYRE_SERIAL_NO
										+ '\');">Dismount</a></li>'
										+ '<li><a onclick="javascript:RotationTyreDetails(\''
										+ tyreLayout[i].LP_ID
										+ '\', \''
										+ tyreLayout[i].POSITION
										+ '\', \''
										+ tyreLayout[i].TYRE_SIZE
										+ '\', \''
										+ tyreLayout[i].TYRE_SIZE_ID
										+ '\', \''
										+ tyreLayout[i].TYRE_ID
										+ '\', \''
										+ tyreLayout[i].TYRE_SERIAL_NO
										+ '\');">Rotation</a></li>'
										+ '<li><a onclick="javascript:getTyreAssign(\''
										+ tyreLayout[i].TYRE_ID + '\', \''
										+ tyreLayout[i].AXLE + '\', \''
										+ tyreLayout[i].POSITION + '\', \''
										+ tyreLayout[i].TYRE_PRESSURE
										+ '\', \''
										+ tyreLayout[i].TYRE_ASSIGNED
										+ '\');">Details</a></li>'

										+ '</ul></div>');
			} else {
				$("#" + tyreLayout[i].POSITION)
						.html(
								'<div   class="layoutTyre tipTyre"><span class="tyrePos">'
										+ tyreLayout[i].POSITION
										+ '</span><ul>'
										+ '<li><a onclick="javascript:MountTyreDetails(\''
										+ tyreLayout[i].LP_ID
										+ '\', \''
										+ tyreLayout[i].AXLE
										+ '\', \''
										+ tyreLayout[i].POSITION
										+ '\', \''
										+ tyreLayout[i].TYRE_SIZE
										+ '\', \''
										+ tyreLayout[i].TYRE_SIZE_ID
										+ '\', \''
										+ tyreLayout[i].TYRE_PRESSURE
										+ '\');">Mount</a></li>'
										+ '<li><a onclick="javascript:getTyreDetails(\''
										+ tyreLayout[i].LP_ID + '\', \''
										+ tyreLayout[i].AXLE + '\', \''
										+ tyreLayout[i].POSITION + '\', \''
										+ tyreLayout[i].TYRE_SIZE + '\', \''
										+ tyreLayout[i].TYRE_SIZE_ID + '\', \''
										+ tyreLayout[i].TYRE_PRESSURE
										+ '\');">Details</a></li>'

										+ '</ul></div>');
			}

		}
	</script>
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
		$(function() {
			$('[data-toggle="popover"]').popover({
				html : true
			})
		})

		function ShowOld(position_Id, Axle, position, test) {
			alert("Hi <br>\n" + "position_Id=" + position_Id + "\n Axle ="
					+ Axle + "\n position =" + position);
		}

		function toggleSingle(e) {
			var n = document.getElementById(e);
			"block" == n.style.display ? (n.style.display = "none")
					: (n.style.display = "block")

		}

		function getTyreDetails(position_Id, Axle, position, tyreSize, tyreSizeId, pressure) {
			var TyreDetails = '<table class="table table-striped">'
					+ '											<tbody>' + '												<tr class="row">'
					+ '													<th class="key">Serial No:</th>'
					+ '													<td class="value"></td>'
					+ '												</tr>' + '												<tr class="row">'
					+ '													<th class="key">Tyre Axle:</th>'
					+ '													<td class="value">'
					+ Axle
					+ '</td>'
					+ '												</tr>'
					+ '												<tr class="row">'
					+ '													<th class="key">Tyre Position:</th>'
					+ '													<td class="value">'
					+ position
					+ '</td>'
					+ '												</tr>'
					+ '												<tr class="row">'
					+ '													<th class="key">Tyre Size:</th>'
					+ '													<td class="value">'
					+ tyreSize
					+ '</td>'
					+ '												</tr>'
					+ '												<tr class="row">'
					+ '													<th class="key">Tyre Pressure:</th>'
					+ '													<td class="value">'
					+ pressure
					+ '</td>'
					+ '												</tr>'
					+ '												<tr class="row">'
					+ '													<th class="key">Brand:</th>'
					+ '													<td class="value"></td>'
					+ '												</tr>'
					+ '												<tr class="row">'
					+ '													<th class="key">Model:</th>'
					+ '													<td class="value"></td>'
					+ '												</tr>'
					+ '												<tr class="row">'
					+ '													<th class="key">Price:</th>'
					+ '													<td class="value"></td>'
					+ '												</tr>'
					+ '												<tr class="row">'
					+ '													<th class="key">Run miles:</th>'
					+ '													<td class="value"></td>'
					+ '												</tr>'
					+ '											</tbody>'
					+ '										</table>';

			$("#TyreSHoW").html(TyreDetails);

		}

		function MountTyreDetails(position_Id, Axle, position, tyreSize, tyreSizeId,
				pressure) {
			var currentDate = new Date();
			var MountTyre = '<form action="<c:url value="/Tyremount.in"/>" method="post">'
					+ '<div class="form-horizontal ">'
					+ '	<fieldset>'
					+ '		<legend id="Identification">Tyre Mount</legend>'
					+ '			<div class="row1">'
					+ '				<div class="col-md-4 col-sm-4 col-xs-6">'
					+ '					<label class="L-size control-label" style="width: 91%;"><span>Select Tyre Inventory :</span><abbr title="required">*</abbr></label>'
					+ '				</div>'
					+ '				<div class="col-md-6 col-sm-4 col-xs-6">'
					+ '					<div class="I-size divWidth">'
					+ '						<input type="hidden" id="inventoryTyre" name="TYRE_ID" style="width: 100%;" placeholder="Please Enter 2 or more Tyre No" />'
					+ '					</div>'
					+ '				</div>'
					+ '			</div>'
					+ '			<div class="row1">'
					+ '				<input name="VID" type="hidden" value="${vehicle.vid}" required="required" />'
					+ '				<input name="VEHICLE_REGIS" type="hidden" value="${vehicle.vehicle_registration}" required="required" />'
					+ '				<!-- position id -->'
					+ '				<input name="POSITION_ID" type="hidden" value="'+position_Id+'" required="required" />'
					+ '			</div>'
					+ '			<div class="row1">'
					+ '       		<div class="col-md-5 col-sm-4 col-xs-6">'
					+ '					<label class="L-size control-label labelPadding"><span>Position:</span><abbr title="required">*</abbr></label> <br>'
					+ '						<div class="I-size divWidth ">'
					+ '							<input name="POSITION_AXLE" type="text" class="form-text" readonly="readonly" value="'+position+'" required="required" />'
					+ '						</div>'
					+ '				</div>'
					+ ' 			<div class="col-md-5 col-sm-4 col-xs-6">'
					+ '					<label class="L-size control-label labelPadding"><span>Pressure:</span><abbr title="required">*</abbr></label>'
					+ '						<div class="I-size divWidth ">'
					+ '							<input name="PRESSURE" type="text" class="form-text" readonly="readonly" value="'+pressure+'" required="required" />'
					+ '						</div>'
					+ '				</div>'
					+ '			</div>'
					+ '			<div class="row1">'
					+ '       		<div class="col-md-5 col-sm-4 col-xs-6">'
					+ '					<label class="L-size control-label labelPadding" style="width: 35%;"><span>Tyre-Size:</span><abbr title="required">*</abbr></label>'
					+ '					<div class="I-size divWidth ">'
					+ '						<input name="TYRE_SIZE" id="TyreSIZE" type="text" class="form-text"  readonly="readonly" value="'+tyreSize+'" required="required" />'
					+ '						<input name="TYRE_SIZE_ID" id="TyreSIZEID" type="hidden" class="form-text" readonly="readonly" value="'+tyreSizeId+'" required="required" />'
					+ '					</div>'
					+ '				</div>'
					+ ' 			<div class="col-md-5 col-sm-4 col-xs-6">'
					+ '					<label class="L-size control-label labelPadding"><span>Odometer:</span><abbr title="required">*</abbr></label>'
					+ '					<div class="I-size divWidth">'
					+ '						<input name="TYRE_ODOMETER" type="number" class="form-text" value="${vehicle.vehicle_Odometer}" required="required" onkeypress="return isNumberKey(event)" />'
					+ '					</div>'
					+ '				</div>'
					+ '			</div>'
					+ '			<div class="row1">'
					+ '       		<div class="col-md-5 col-sm-4 col-xs-6">'
					+ '					<label class="L-size control-label labelPadding" style="width: 41%;" >Mount Date:<abbr title="required">*</abbr> </label>'
					+ '					<div class="I-size divWidth ">'
					+ '   				<input type="hidden" id="todayDate"/>'							
					+ '						<div class="input-group input-append date" id="TyreMount">'
					+ '							<input type="text" class="form-text" name="MOUNTED_DATE" readonly="readonly" required="required"'
					+ '								data-inputmask="\'alias\': \'dd-mm-yyyy\'" data-mask="" />'
					+ '							<span class="input-group-addon add-on"> <span class="fa fa-calendar"></span> </span>'
					+ '						</div>'
					+ '					</div>'
					+ '				</div>'
					+ ' 			<div class="col-md-5 col-sm-4 col-xs-6">'
					+ '				<label class="L-size control-label labelPadding" style="width: 61%;"><span>Mounted Remark:</span><abbr title="required">*</abbr></label>'
					+ '					<div class="I-size divWidth">'
					+ '						<input name="COMMENT" type="text" class="form-text" required="required" />'
					+ '					</div>'
					+ '				</div>'
					+ '			</div>'
					+ '			<div class="row1" style="padding-top: 5%;margin-left: 12%;">'
					+ '    			<div class="col-md-5 col-sm-4 col-xs-6">'
					+ '  				<input class="btn btn-success" type="submit" value="Assign  Tyre">'		
					+ '				</div>'
					+ '    			<div class="col-md-5 col-sm-4 col-xs-6">'
					+ '  				<a class="btn btn-default" href="#">Cancel</a>'	
					+ '				</div>'
					+ '        	</div>'	
					+ '		<\/fieldset>'
					+ '	</div>'
					+ '<\/form>'
					+ '<script type="text/javascript"> $(document).ready(function(){'
					+ '		var a=$("#TyreSIZEID").val();'
					+ '  	var todayDate =	"'+currentDate+'"; '	
					+ '		$("#inventoryTyre").select2({minimumInputLength:2,minimumResultsForSearch:10,'
					+ '		ajax:{url:"getTyreMountList.in?Size="+a,dataType:"json",type:"POST",'
					+ ' 	contentType:"application/json",quietMillis:50,data:function(a){return{term:a}},results:function(a){return{results:$.map(a,function(a){'
					+ '		return{text:a.TYRE_NUMBER,slug:a.slug,id:a.TYRE_ID}})}}}}),$("#datemask").inputmask("dd-mm-yyyy",{placeholder:"dd-mm-yyyy"}),$("[data-mask]").inputmask()},$("#TyreMount").datepicker({autoclose:!0,todayHighlight:!0,format:"dd-mm-yyyy",endDate: "currentDate",maxDate: todayDate}));'
					+ ' <\/script>';

			$("#TyreSHoW").html(MountTyre);

		}

		function DisMountTyreDetails(position_Id, Axle, position, tyreSize,
				pressure, TYRE_ID, TYRE_SERIAL_NO) {
			var DisMountTyre = '<form action="<c:url value="/TyreDismount.in"/>" method="post">'
					+ '<div class="form-horizontal ">'
					+ '	<fieldset>'
					+ '		<legend id="Identification">Tyre DisMount</legend>'
					+ '			<div class="row1">'
					+ '				<div class="col-md-4 col-sm-4 col-xs-6">'
					+ '					<label class="L-size control-label" style="width: 62%;"><span>Selected Tyre :</span><abbr title="required">*</abbr></label>'
					+ '				</div>'
					+ '				<div class="col-md-6 col-sm-4 col-xs-6">'
					+ '					<div class="I-size divWidth">'
					+ ' 					<input name="TYRE_ID" id="TyreSIZE" type="hidden" class="form-text" readonly="readonly" value="'+TYRE_ID+'" required="required" />'
					+ '						<input name="TYRE_SERIAL_NO" id="TyreSIZE" type="text" class="form-text" readonly="readonly" value="'+TYRE_SERIAL_NO+'" required="required" style="box-shadow: 2px 5px grey;font-weight: bold;" />'
					+ '					</div>'
					+ '				</div>'
					+ '			</div>'
					+ '			<div class="row1">'
					+ '				<input name="VID" type="hidden" value="${vehicle.vid}" required="required" />'
					+ '				<input name="VEHICLE_REGIS" type="hidden" value="${vehicle.vehicle_registration}" required="required" />'
					+ '				<!-- position id -->'
					+ '				<input name="POSITION_ID" type="hidden" value="'+position_Id+'" required="required" />'
					+ '			</div>'
					+ '			<div class="row1">'
					+ '       		<div class="col-md-5 col-sm-4 col-xs-6">'
					+ '					<label class="L-size control-label labelPadding"><span>Position:</span><abbr title="required">*</abbr></label> <br>'
					+ '						<div class="I-size divWidth ">'
					+ '							<input name="POSITION_AXLE" type="text" class="form-text" readonly="readonly" value="'+position+'" required="required" />'
					+ '						</div>'
					+ '				</div>'
					+ ' 			<div class="col-md-5 col-sm-4 col-xs-6">'
					+ '					<label class="L-size control-label labelPadding"><span>Pressure:</span><abbr title="required">*</abbr></label>'
					+ '						<div class="I-size divWidth ">'
					+ '							<input name="PRESSURE" type="text" class="form-text" readonly="readonly" value="'+pressure+'" required="required" />'
					+ '						</div>'
					+ '				</div>'
					+ '			</div>'
					+ '			<div class="row1">'
					+ '       		<div class="col-md-5 col-sm-4 col-xs-6">'
					+ '					<label class="L-size control-label labelPadding" style="width: 35%;"><span>Tyre-Size:</span><abbr title="required">*</abbr></label>'
					+ '					<div class="I-size divWidth ">'
					+ '						<input name="TYRE_SIZE" id="TyreSIZE" type="text" class="form-text"  readonly="readonly" value="'+tyreSize+'" required="required" />'
					+ '					</div>'
					+ '				</div>'
					+ ' 			<div class="col-md-5 col-sm-4 col-xs-6">'
					+ '					<label class="L-size control-label labelPadding" style="width: 69%;" ><span>Dismount Odometer:</span><abbr title="required">*</abbr></label>'
					+ '					<div class="I-size divWidth">'
					+ '						<input name="TYRE_ODOMETER" type="number" class="form-text" value="${vehicle.vehicle_Odometer}" required="required"  onkeypress="return isNumberKey(event)" />'
					+ '					</div>'
					+ '				</div>'
					+ '			</div>'
					+ '			<div class="row1">'
					+ '       		<div class="col-md-5 col-sm-4 col-xs-6">'
					+ '					<label class="L-size control-label labelPadding" style="width: 52%;" >Dismount Date:<abbr title="required">*</abbr> </label>'
					+ '					<div class="I-size divWidth ">'
					+ '   				<input type="hidden" id="todayDate"/>'							
					+ '						<div class="input-group input-append date" id="TyreMount">'
					+ '							<input type="text" class="form-text" name="MOUNTED_DATE"  readonly="readonly" required="required"'
					+ '								data-inputmask="\'alias\': \'dd-mm-yyyy\'" data-mask="" />'
					+ '							<span class="input-group-addon add-on"> <span class="fa fa-calendar"></span> </span>'
					+ '						</div>'
					+ '					</div>'
					+ '				</div>'
					+ ' 			<div class="col-md-5 col-sm-4 col-xs-6">'
					+ '				<label class="L-size control-label labelPadding" style="width: 70%;"><span>Dismounted Remark:</span><abbr title="required">*</abbr></label>'
					+ '					<div class="I-size divWidth">'
					+ '						<input name="COMMENT" type="text" class="form-text" required="required" />'
					+ '					</div>'
					+ '				</div>'
					+ '			</div>'
					+ '			<div class="row1" style="padding-top: 5%;margin-left: 12%;">'
					+ '    			<div class="col-md-5 col-sm-4 col-xs-6">'
					+ '  				<input class="btn btn-success" type="submit" value="Dismount Tyre">'		
					+ '				</div>'
					+ '    			<div class="col-md-5 col-sm-4 col-xs-6">'
					+ '  				<a class="btn btn-default" href="#">Cancel</a>'	
					+ '				</div>'
					+ '        	</div>'	
					+ '		<\/fieldset>'
					+ '	</div>'
					+ ' <\/form>'
					+ '	<script type="text/javascript">'
					+ '	$(document).ready(function(){var a=$("#TyreSIZE").val(); $("#datemask").inputmask("dd-mm-yyyy",{placeholder:"dd-mm-yyyy"}),$("[data-mask]").inputmask()},$("#TyreMount").datepicker({autoclose:!0,todayHighlight:!0,format:"dd-mm-yyyy"}));'
					+ ' <\/script>';

			$("#TyreSHoW").html(DisMountTyre);

		}

		function RotationTyreDetails(position_Id, position, tyreSize, tyreSizeId, TYRE_ID,
				TYRE_SERIAL_NO, TYRE_ASSIGNED) {
			
			var DisMountTyre = '<form action="<c:url value="/TyreRotation.in"/>" method="post">'
				+ '<div class="form-horizontal ">'
				+ '	<fieldset>'
				+ '		<legend id="Identification">Tyre Rotation</legend>'
				+ '			<div class="row1">'
				+ '				<input name="VID" type="hidden" value="${vehicle.vid}" required="required" />'
				+ '				<input name="VEHICLE_REGIS" type="hidden" value="${vehicle.vehicle_registration}" required="required" />'
				+ '				<!-- position id -->'
				+ '				<input name="POSITION_ID" type="hidden" value="'+position_Id+'" required="required" />'
				+ '				<!-- Tyre id -->'
				+ '				<input name="TYRE_ID" type="hidden" value="'+TYRE_ID+'" required="required" />'
				+ '				<!-- TYRE_ASSIGNED id -->'
				+ '				<input name="TYRE_ASSIGNED" type="hidden" value="'+TYRE_ASSIGNED+'" required="required" />'
				+ '							<input name="TYRE_SIZE_ID" id="TyreSIZEID" type="hidden" class="form-text" readonly="readonly" value="'+tyreSizeId+'"'
				+ '			</div>'
				+ '			<div class="row1">'
				+ '       		<div class="col-md-5 col-sm-4 col-xs-6">'
				+ '					<label class="control-label labelPadding"><span>Tyre Size:</span><abbr title="required">*</abbr></label> <br>'
				+ '						<div class="I-size divWidth ">'
				+ ' 						<input name="TYRE_SIZE" id="TyreSIZE" type="text" class="form-text" readonly="readonly" value="'+tyreSize+'" required="required" />'
				+ '						</div>'
				+ '				</div>'
				+ ' 			<div class="col-md-5 col-sm-4 col-xs-6">'
				+ '					<label class="control-label labelPadding"><span>Position From:</span><abbr title="required">*</abbr></label>'
				+ '						<div class="I-size divWidth ">'
				+ '							<input name="POSITION_AXLE_FROM" type="text" class="form-text" readonly="readonly" value="'+position+'" required="required" />'
				+ '						</div>'
				+ '				</div>'
				+ '			</div>'
				+ '			<div class="row1">'
				+ '       		<div class="col-md-5 col-sm-4 col-xs-6">'
				+ '					<label class="control-label labelPadding" style="width: 40%;"><span>Position To:</span><abbr title="required">*</abbr></label>'
				+ '					<div class="I-size divWidth ">'
				+ '						<select name="ROTATION_TO" id="RotationTyreTo" style="width: 100%;" required="required">'
				+ '							<option value="LO-1">LO-1</option>'
				+ '							<option value="LI-1">LI-1</option>'
				+ '							<option value="RI-1">RI-1</option>'
				+ '							<option value="RO-1">RO-1</option>'
				+ '							<option value="ST-2">ST-1</option>'
				+ '							<option value="LO-2">LO-2</option>'
				+ '							<option value="LI-2">LI-2</option>'
				+ '							<option value="RI-2">RI-2</option>'
				+ '							<option value="RO-2">RO-2</option>'
				+ '							<option value="LO-3">LO-3</option>'
				+ '							<option value="LI-3">LI-3</option>'
				+ '							<option value="RI-3">RI-3</option>'
				+ '							<option value="RO-3">RO-3</option>'
				+ '							<option value="LO-4">LO-4</option>'
				+ '							<option value="LI-4">LI-4</option>'
				+ '							<option value="RI-4">RI-4</option>'
				+ '							<option value="RO-4">RO-4</option>'
				+ '							<option value="LO-5">LO-5</option>'
				+ '							<option value="LI-5">LI-5</option>'
				+ '							<option value="RI-5">RI-5</option>'
				+ '							<option value="RO-5">RO-5</option>'
				+ '						</select>'
				+ '					</div>'
				+ '				</div>'
				+ ' 			<div class="col-md-5 col-sm-4 col-xs-6">'
				+ '					<label class="control-label labelPadding" style="width: 69%;" ><span>Rotation Odometer:</span><abbr title="required">*</abbr></label>'
				+ '					<div class="I-size divWidth">'
				+ '						<input name="TYRE_ODOMETER" type="number" class="form-text" value="${vehicle.vehicle_Odometer}" required="required" />'
				+ '					</div>'
				+ '				</div>'
				+ '			</div>'
				+ '			<div class="row1">'
				+ '       		<div class="col-md-5 col-sm-4 col-xs-6">'
				+ '					<label class="control-label labelPadding" style="width: 52%;" >Rotation Date:<abbr title="required">*</abbr> </label>'
				+ '					<div class="I-size divWidth ">'
				+ '						<div class="input-group input-append date" id="TyreMount">'
				+ '							<input type="text" class="form-text" name="MOUNTED_DATE" required="required" data-inputmask="\'alias\': \'dd-mm-yyyy\'" data-mask="" />'
				+ '							<span class="input-group-addon add-on"> <span class="fa fa-calendar"></span> </span>'
				+ '						</div>'
				+ '					</div>'
				+ '				</div>'
				+ ' 			<div class="col-md-5 col-sm-4 col-xs-6">'
				+ '				<label class="control-label labelPadding" style="width: 70%;"><span>Rotation Remark:</span><abbr title="required">*</abbr></label>'
				+ '					<div class="I-size divWidth">'
				+ '						<input name="COMMENT" type="text" class="form-text" required="required" />'
				+ '					</div>'
				+ '				</div>'
				+ '			</div>'
				+ '			<div class="row1" style="padding-top: 5%;margin-left: 12%;">'
				+ '    			<div class="col-md-5 col-sm-4 col-xs-6">'
				+ '  				<input class="btn btn-success" type="submit" value="Rotation Tyre">'		
				+ '				</div>'
				+ '    			<div class="col-md-5 col-sm-4 col-xs-6">'
				+ '  				<a class="btn btn-default" href="#">Cancel</a>'	
				+ '				</div>'
				+ '        	</div>'	
				+ '		<\/fieldset>'
				+ '	</div>'
				+ ' <\/form>'
				+ '	<script type="text/javascript">'
				+ '	$(document).ready(function(){var a=$("#TyreSIZE").val();$("#RotationTyreTo").select2(),$("#datemask").inputmask("dd-mm-yyyy",{placeholder:"dd-mm-yyyy"}),$("[data-mask]").inputmask()},$("#TyreMount").datepicker({autoclose:!0,todayHighlight:!0,format:"dd-mm-yyyy"}));'
				+ ' <\/script>';

			$("#TyreSHoW").html(DisMountTyre);

		}

		function getTyreAssign(Tyre_id, Axle, position, pressure) {
			var currentOdometer = document.getElementById('CurrentOdometer').value;
			$
					.getJSON(
							"getTyreAssignDetails.in",
							{
								position : Tyre_id,
								ajax : "true",
								dataType : "json",
								type : "POST",
								contentType : "application/json",
							},
							function(e) {
								var TyreDetails = '<table class="table table-striped">'
										+ '											<tbody>'
										+ '												<tr class="row">'
										+ '													<th class="key">Serial No:</th>'
										+ '													<td class="value"><a href="<c:url value="/showTyreInfo.in?Id='
										+ e.TYRE_ID
										+ '"/>">'
										+ e.TYRE_NUMBER
										+ '</a></td>'
										+ '												</tr>'
										+ '												<tr class="row">'
										+ '													<th class="key">Tyre Axle:</th>'
										+ '													<td class="value">'
										+ Axle
										+ '</td>'
										+ '												</tr>'
										+ '												<tr class="row">'
										+ '													<th class="key">Tyre Position:</th>'
										+ '													<td class="value">'
										+ position
										+ '</td>'
										+ '												</tr>'
										+ '												<tr class="row">'
										+ '													<th class="key">Tyre Size:</th>'
										+ '													<td class="value">'
										+ e.TYRE_SIZE
										+ '</td>'
										+ '												</tr>'
										+ '												<tr class="row">'
										+ '													<th class="key">Tyre Pressure:</th>'
										+ '													<td class="value">'
										+ pressure
										+ '</td>'
										+ '												</tr>'
										+ '												<tr class="row">'
										+ '													<th class="key">Brand:</th>'
										+ '													<td class="value">'
										+ e.TYRE_MANUFACTURER
										+ '</td>'
										+ '												</tr>'
										+ '												<tr class="row">'
										+ '													<th class="key">Model:</th>'
										+ '													<td class="value">'
										+ e.TYRE_MODEL
										+ '</td>'
										+ '												</tr>'
										+ '												<tr class="row">'
										+ '													<th class="key">Price:</th>'
										+ '													<td class="value">'
										+ e.TYRE_AMOUNT
										+ '</td>'
										+ '												</tr>'
										+ '												<tr class="row">'
										+ '													<th class="key">Tyre Total Runing Km:</th>'
										+ '													<td class="value">'
										+ e.TYRE_USEAGE
										+ '</td>'
										+ '												</tr>'
										+ '												<tr class="row">'
										+ '													<th class="key">Mounted Odometer:</th>'
										+ '													<td class="value"><b>'
										+ e.OPEN_ODOMETER
										+ '</b></td>'
										+ '												</tr>'
										+ '												<tr class="row">'
										+ '													<th class="key">Date of Mounted :</th>'
										+ '													<td class="value">'
										+ e.TYRE_ASSIGN_DATE
										+ '</td>'
										
										+ '</b></td>'
										+ '												</tr>'
										+ '												<tr class="row">'
										+ '													<th class="key">Mounted Remark:</th>'
										+ '													<td class="value">'
										+ e.remark
										+ '</td>'
										+ '												</tr>'
										+ '												</tr>'
										+ '											</tbody>'
										+ '										</table>';

								$("#TyreSHoW").html(TyreDetails);

							})

		}
	</script>
	
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
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/vehicle/VehicleTyreAsign.js" />"></script>	
	<script type='text/javascript'
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/photoView/ekko-lightbox.js"/>" ></script>		
</div>
