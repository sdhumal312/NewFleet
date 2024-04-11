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
							value="${vehicle.vehicle_registration}" /></a> / <span>New
						Vehicle Fuel Entries</span>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_FUEL')">
						<a target="_blank" href="<c:url value="/addFuelEntries.in"/>" class="btn btn-success btn-sm">
								 <span class="fa fa-plus"></span>
								Add Fuel Entry
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_FUEL')">

						<a class="btn btn-info btn-sm"
							href="<c:url value="/VehicleFuelGraph.in?vid=${vehicle.vid}"/>">
							<img alt="" src="http://www.animatedgif.net/new/new6_e0.gif"
							height="25px"> Fuel Chart
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
							<a href="<c:url value="/showVehicle.in?vid=${vehicle.vid}"/>">
								<c:out value="${vehicle.vehicle_registration}" />
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
			<sec:authorize access="!hasAuthority('VIEW_FUEL')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_FUEL')">
				<div class="col-md-10 col-sm-10 col-xs-10">
					<c:if test="${param.deleteFuel eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Fuel Data successfully Deleted.
						</div>
					</c:if>
					<c:if test="${param.saveFuel eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Fuel Data Successfully Created.
						</div>
					</c:if>
					<c:if test="${param.duplicateFuelEntries eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Fuel Entries was Already created..(or) This Duplicate
							Entries Reference Number.
						</div>
					</c:if>
					<c:if test="${saveFuel}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Fuel Data Successfully Created.
						</div>
					</c:if>
					<c:if test="${deleteFuel}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Fuel Data Successfully Deleted.
						</div>
					</c:if>
					<c:if test="${importSave}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							Imported Successfully ${CountSuccess} Fuel Data.
						</div>
					</c:if>
					<c:if test="${param.danger eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Fuel was Already Updated.
						</div>
					</c:if>
					<c:if test="${duplicateFuelEntries}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Fuel Entries was Already created..(or) This Duplicate
							Entries Reference Number.
						</div>
					</c:if>
					<div class="row">
						<div class="main-body">
							<c:if test="${!empty fuel}">
								<sec:authorize access="hasAuthority('VIEW_FUEL')">
									<div class="box">
										<div class="box-body">
											<div class="table-responsive">
												<table id="FuelTable" class="table table-striped">
													<thead>
														<tr>
															<th class="col-sm-1">Id</th>
															<th class="col-sm-2">Driver &amp; Vendor</th>
															<th class="col-sm-1">Date</th>
															<th>O(Km)</th>
															<th>C(Km)</th>
															<th>Usage</th>
															<th>Volume</th>
															<th>Amount</th>
															<th class="fit">FE(Km/L)</th>
															<th>Cost</th>
															<th>Doc</th>
															<th class="fit">Actions</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach items="${fuel}" var="fuel">
															<tr data-object-id="" class="ng-scope">
																<td class="col-sm-1"><a
																	href="<c:url value="/showFuel.in?FID=${fuel.fuel_id}"/>"
																	data-toggle="tip"
																	data-original-title="Click Fuel Details"><c:out
																			value="FT-0${fuel.fuel_Number}" /></a></td>
																<td class="col-sm-2"
																	style="font-size: 9px; font-weight: 400;"><a
																	href="<c:url value="/showDriver.in?driver_id=${fuel.driver_id}"/>"
																	data-toggle="tip"
																	data-original-title="Driver empnumber"><c:out
																			value="${fuel.driver_empnumber} " /> <c:out
																			value="${fuel.driver_name}" /> </a><br> <a
																	data-toggle="tip" data-original-title="Vendor Name"><c:out
																			value="${fuel.vendor_name}" />-( <c:out
																			value="${fuel.vendor_location}" /> ) </a></td>
																<td class="col-sm-1"><c:out
																		value="${fuel.fuel_date}" /> <br> <c:choose>
																		<c:when test="${fuel.fuel_TripsheetID != 0 && fuel.fuel_TripsheetNumber != null}">
																			<a target="_blank"
																				href="<c:url value="/getTripsheetDetails.in?tripSheetID=${fuel.fuel_TripsheetID}"/>"
																				data-toggle="tip"
																				data-original-title="Click Tripsheet Details"><c:out
																					value="TS-${fuel.fuel_TripsheetNumber}" /> </a>
																		</c:when>
																		<c:otherwise>
																			<c:out value="TS-${fuel.fuel_TripsheetNumber}" />
																		</c:otherwise>
																	</c:choose></td>
																<td><c:out value="${fuel.fuel_meter_old}" /></td>
																<td><c:out value="${fuel.fuel_meter}" /></td>
																<td><c:out value="${fuel.fuel_usage} km" /></td>
																<td><abbr data-toggle="tip"
																	data-original-title="Liters"><c:out
																			value="${fuel.fuel_liters}" /></abbr> <c:if
																		test="${fuel.fuel_tank_partial==1}">
																		<abbr data-toggle="tip"
																			data-original-title="Partial fuel-up"> <i
																			class="fa fa-adjust"></i>
																		</abbr>
																	</c:if> <br> <c:out value="${fuel.fuel_type}" /></td>
																<td><i class="fa fa-inr"></i> <c:out
																		value="${fuel.fuel_amount}" /> <br> <abbr
																	data-toggle="tip" data-original-title="Price"> <c:out
																			value="${fuel.fuel_price}/liters" />
																</abbr></td>
																<td class="fit"><c:out value="${fuel.fuel_kml} " />
																	<c:if test="${fuel.fuel_kml != null}">
																		<c:choose>
																			<c:when
																				test="${vehicle.vehicle_ExpectedMileage <= fuel.fuel_kml}">
																				<c:choose>
																					<c:when
																						test="${vehicle.vehicle_ExpectedMileage_to >= fuel.fuel_kml}">
																						<abbr data-toggle="tip"
																							data-original-title="Expected Mileage ${vehicle.vehicle_ExpectedMileage} to ${vehicle.vehicle_ExpectedMileage_to}">
																							<i class="fa fa-stop-circle"
																							style="color: #1FB725; font-size: 19px;"></i>
																						</abbr>
																					</c:when>
																					<c:otherwise>
																						<abbr data-toggle="tip"
																							data-original-title="Expected Mileage ${vehicle.vehicle_ExpectedMileage} to ${vehicle.vehicle_ExpectedMileage_to}">
																							<i class="fa fa-chevron-circle-up"
																							style="color: blue; font-size: 19px;"></i>
																						</abbr>
																					</c:otherwise>
																				</c:choose>
																			</c:when>
																			<c:otherwise>
																				<abbr data-toggle="tip"
																					data-original-title="Expected Mileage ${vehicle.vehicle_ExpectedMileage}  to ${vehicle.vehicle_ExpectedMileage_to}">

																					<i class="fa fa-chevron-circle-down"
																					style="color: red; font-size: 19px;"></i>
																				</abbr>
																			</c:otherwise>

																		</c:choose>
																	</c:if></td>
																<td><c:out value="${fuel.fuel_cost} " /> <c:if
																		test="${fuel.fuel_cost != null}">
													/Km
													</c:if></td>
																<td><c:choose>
																		<c:when test="${fuel.fuel_document == true}">
																			<sec:authorize
																				access="hasAuthority('DOWNLOND_RENEWAL')">

																				<a
																					href="${pageContext.request.contextPath}/download/FuelDocument/${fuel.fuel_document_id}.in">
																					<span class="fa fa-download"> Doc</span>
																				</a>
																			</sec:authorize>
																		</c:when>
																	</c:choose></td>
																<td class="fit"><c:choose>
																		<c:when test="${fuel.fuel_TripsheetID != 0 && fuel.fuel_TripsheetNumber != null}">
																				<a target="_blank"
																					href="<c:url value="/getTripsheetDetails.in?tripSheetID=${fuel.fuel_TripsheetID}"/>"
																					data-toggle="tip"
																					data-original-title="Click Tripsheet Details"><c:out
																						value="TS-${fuel.fuel_TripsheetNumber}" /> </a>
																			
																		</c:when>
																		<c:otherwise>
																			<div class="btn-group">
																				<a class="btn btn-default btn-sm dropdown-toggle"
																					data-toggle="dropdown" href="#"> <span
																					class="fa fa-ellipsis-v"></span>
																				</a>
																				<ul class="dropdown-menu pull-right">
																					<li><sec:authorize
																							access="hasAuthority('EDIT_FUEL')">
																							<a
																								href="<c:url value="/FuelEntriesEdit?Id=${fuel.fuel_id}&vid=${fuel.vid}"/>">
																								<i class="fa fa-edit"></i> Edit
																							</a>
																						</sec:authorize></li>
																					<li><sec:authorize
																							access="hasAuthority('DELETE_FUEL')">
																							<a
																								href="<c:url value="/deleteFuel.in?FID=${fuel.fuel_id}"/>">
																								<i class="fa fa-trash"></i> Delete
																							</a>
																						</sec:authorize></li>
																				</ul>
																			</div>
																		</c:otherwise>
																	</c:choose></td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										</div>
									</div>
									<c:url var="firstUrl"
										value="/VehicleFuelDetails/1?vid=${vehicle.vid}" />
									<c:url var="lastUrl"
										value="/VehicleFuelDetails/${deploymentLog.totalPages}?vid=${vehicle.vid}" />
									<c:url var="prevUrl"
										value="/VehicleFuelDetails/${currentIndex - 1}?vid=${vehicle.vid}" />
									<c:url var="nextUrl"
										value="/VehicleFuelDetails/${currentIndex + 1}?vid=${vehicle.vid}" />
									<div class="text-center">
										<ul class="pagination pagination-lg pager">
											<c:choose>
												<c:when test="${currentIndex == 1}">
													<li class="disabled"><a href="#">&lt;&lt;</a></li>
													<li class="disabled"><a href="#">&lt;</a></li>
												</c:when>
												<c:otherwise>
													<li><a href="${firstUrl}">&lt;&lt;</a></li>
													<li><a href="${prevUrl}">&lt;</a></li>
												</c:otherwise>
											</c:choose>
											<c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
												<c:url var="pageUrl"
													value="/VehicleFuelDetails/${i}?vid=${vehicle.vid}" />
												<c:choose>
													<c:when test="${i == currentIndex}">
														<li class="active"><a href="${pageUrl}"><c:out
																	value="${i}" /></a></li>
													</c:when>
													<c:otherwise>
														<li><a href="${pageUrl}"><c:out value="${i}" /></a></li>
													</c:otherwise>
												</c:choose>
											</c:forEach>
											<c:choose>
												<c:when test="${currentIndex == deploymentLog.totalPages}">
													<li class="disabled"><a href="#">&gt;</a></li>
													<li class="disabled"><a href="#">&gt;&gt;</a></li>
												</c:when>
												<c:otherwise>
													<li><a href="${nextUrl}">&gt;</a></li>
													<li><a href="${lastUrl}">&gt;&gt;</a></li>
												</c:otherwise>
											</c:choose>
										</ul>
									</div>
								</sec:authorize>
							</c:if>
							<c:if test="${empty fuel}">
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
			<div class="col-md-1 col-sm-1 col-xs-12">
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
