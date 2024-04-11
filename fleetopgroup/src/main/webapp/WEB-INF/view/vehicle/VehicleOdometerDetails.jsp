<%@ include file="taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/jquery.dataTables.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/buttons.dataTables.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/vehicle/1/1"/>">Vehicle</a> / <a
						href="<c:url value="showVehicle.in?vid=${vehicle.vid}"/>"><c:out
							value="${vehicle.vehicle_registration}" /></a> / <span>Vehicle
						Odometer History</span>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADDEDIT_VEHICLE_ODOMETER')">
						<c:if test="${vehicle.vStatusId == 1}"></c:if>
						<button type="button" class="btn btn-success btn-sm"
							data-toggle="modal" data-target="#UpdateOdometer"
							data-whatever="@mdo">
							<span class="fa fa-plus"> Update Odometer</span>
						</button>
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
	<div class="modal fade" id="UpdateOdometer" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<form action="updateVehicleodometer.in" method="post"
					name="vehicleStatu" onsubmit="return validateStatus()">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">New Update Meter Reading Or New
							Odometer Reading</h4>
					</div>
					<div class="modal-body">
						<div class="row1">
							<label class="L-size control-label" id="Status">Odometer
								:</label>
							<div class="I-size">

								<input type="hidden" name="vid" value="${vehicle.vid}">
								<input type="hidden" name="vehicle_registration"
									value="${vehicle.vehicle_registration}"> <input
									type="number" class="form-text" name="vehicle_Odometer"
									placeholder="${vehicle.vehicle_Odometer}" /> <label
									id="errorvStatus" class="error"></label>
							</div>
						</div>
						<br />
					</div>
					<div class="modal-footer">
						<input class="btn btn-success"
							onclick="this.style.visibility = 'hidden'" name="commit"
							type="submit" value="Update Vehicle Odometer">

						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span id="Close">Close</span>
						</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<section class="content-body">
		<div class="row">
			<sec:authorize access="!hasAuthority('ADDEDIT_VEHICLE_ODOMETER')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('ADDEDIT_VEHICLE_ODOMETER')">
				<div class="col-md-9 col-sm-9 col-xs-12">
					<c:if test="${param.saveService eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Odometer Created successfully .
						</div>
					</c:if>
					<c:if test="${param.updateService eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Odometer Updated successfully .
						</div>
					</c:if>

					<c:if test="${param.deleteService eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Odometer Deleted successfully .
						</div>
					</c:if>
					<c:if test="${param.danger eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Service Not Create successfully .
						</div>
					</c:if>
					<c:if test="${param.duplicateFuelEntries eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Fuel Entries was Already created..(or) This Duplicate
							Entries Reference Number.
						</div>
					</c:if>

					<div class="row">
						<div class="main-body">
							<c:if test="${!empty VehicleOdmeter}">
								<div class="box">
									<div class="box-body">
										<table id="FuelTable"
											class="table table-bordered table-striped">
											<thead>
												<tr>
													<th>Date</th>
													<th>Odometer</th>
													<th>Actions</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${VehicleOdmeter}" var="VehicleOdmeter">
													<tr data-object-id="" class="ng-scope">
														<td><c:out value="${VehicleOdmeter.voh_date}" /> <br>
															<small><c:out
																	value="${VehicleOdmeter.diffrent_time_day}" /></small></td>
														<td><b><c:out
																	value="${VehicleOdmeter.vehicle_Odometer}" /></b> <br>
															<small><c:out
																	value="${VehicleOdmeter.diffrent_meter_oddmeter}" /></small></td>
														<c:choose>
															<c:when
																test="${VehicleOdmeter.voh_updatelocation == 'Vehicle Entry'}">
																<td><a aria-hidden="true" data-toggle="tip"
																	data-original-title="Associated with ${VehicleOdmeter.voh_updatelocation}"
																	href="showVehicle.in?vid=${VehicleOdmeter.voh_updateId}">
																		<c:out value="${VehicleOdmeter.voh_updatelocation}" />
																</a></td>
															</c:when>
															<c:when
																test="${VehicleOdmeter.voh_updatelocation == 'WorkOrder Entry'}">
																<td><a aria-hidden="true" data-toggle="tip"
																	data-original-title="Associated with ${VehicleOdmeter.voh_updatelocation} # ${VehicleOdmeter.voh_updateId}. Edit this meter entry"
																	href="showWorkOrder?woId=${VehicleOdmeter.voh_updateId}#!">
																		<c:out value="${VehicleOdmeter.voh_updatelocation} #" />
																		<span><c:out
																				value="${VehicleOdmeter.voh_updateNumber}" /></span>
																</a></td>
															</c:when>
															<c:when
																test="${VehicleOdmeter.voh_updatelocation == 'Fuel Entry'}">
																<td><a aria-hidden="true" data-toggle="tip"
																	data-original-title="Associated with ${VehicleOdmeter.voh_updatelocation} # ${VehicleOdmeter.voh_updateId}. Edit this meter entry"
																	href="showFuel.in?FID=${VehicleOdmeter.voh_updateId}">
																		<c:out value="${VehicleOdmeter.voh_updatelocation} #" />
																		<span><c:out
																				value="${VehicleOdmeter.voh_updateNumber}" /></span>		
																</a></td>
															</c:when>
															<c:when
																test="${VehicleOdmeter.voh_updatelocation == 'ISSUES_ENTRY'}">
																<td><a aria-hidden="true" data-toggle="tip"
																	data-original-title="Associated with ${VehicleOdmeter.voh_updatelocation} # ${VehicleOdmeter.voh_updateId}. Edit this meter entry"
																	href="showIssues.in?Id=${VehicleOdmeter.voh_updateIdStr}#!">
																		<c:out value="${VehicleOdmeter.voh_updatelocation} #" />
																		<span><c:out
																				value="${VehicleOdmeter.voh_updateNumber}" /></span>		
																</a></td>
															</c:when>
															<c:when
																test="${VehicleOdmeter.voh_updatelocation == 'TYRE_ENTRY'}">
																<td><a aria-hidden="true" data-toggle="tip"
																	data-original-title="Associated with ${VehicleOdmeter.voh_updatelocation} # ${VehicleOdmeter.voh_updateId}. Edit this meter entry"
																	href="showTyreInfo.in?Id=${VehicleOdmeter.voh_updateId}#!">
																		<c:out value="${VehicleOdmeter.voh_updatelocation} #" />
																		<span><c:out
																				value="${VehicleOdmeter.voh_updateNumber}" /></span>		
																</a></td>
															</c:when>
															<c:when
																test="${VehicleOdmeter.voh_updatelocation == 'Dealer Service Entry'}">
																<td><a aria-hidden="true" data-toggle="tip"
																	data-original-title="Associated with ${VehicleOdmeter.voh_updatelocation} # ${VehicleOdmeter.voh_updateId}. Edit this meter entry"
																	href="showDealerServiceEntries.in?dealerServiceEntriesId=${VehicleOdmeter.voh_updateId}#!">
																		<c:out value="${VehicleOdmeter.voh_updatelocation} #" />
																		<span><c:out
																				value="${VehicleOdmeter.voh_updateNumber}" /></span>		
																</a></td>
															</c:when>
															
																<c:when
																test="${VehicleOdmeter.voh_updatelocation == 'Service Entry'}">
																<td><a aria-hidden="true" data-toggle="tip"
																	data-original-title="Associated with ${VehicleOdmeter.voh_updatelocation} # ${VehicleOdmeter.voh_updateId}. Edit this meter entry"
																	href="showServiceEntryDetails.in?serviceEntryId=${VehicleOdmeter.voh_updateId}#!">
																		<c:out value="${VehicleOdmeter.voh_updatelocation} #" />
																		<span><c:out
																				value="${VehicleOdmeter.voh_updateNumber}" /></span>		
																</a></td>
															</c:when>												
															<c:otherwise>
																<td><a aria-hidden="true" data-toggle="tip"
																	data-original-title="Associated with ${VehicleOdmeter.voh_updatelocation} # ${VehicleOdmeter.voh_updateId}."
																	href="showTripSheet.in?tripSheetID=${VehicleOdmeter.voh_updateId}">
																		<c:out value="${VehicleOdmeter.voh_updatelocation} #" />
																		<span><c:out
																				value="${VehicleOdmeter.voh_updateNumber}" /></span>
																</a></td>
															</c:otherwise>
														</c:choose>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</c:if>
							<c:if test="${empty VehicleOdmeter}">
								<div class="main-body">
									<p class="lead text-muted text-center t-padded">
										<spring:message code="label.master.noresilts" />
									</p>
								</div>
							</c:if>
						</div>
					</div>
				</div>
			</sec:authorize>
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
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/dataTables.buttons.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.print.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.flash.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/SE/Servicelanguage.js" />"></script>

</div>
