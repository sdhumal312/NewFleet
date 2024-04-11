<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/tabs.css" />">
	<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/vehicle/${SelectStatus}/${SelectPage}"/>">New
						Vehicle</a> / <span><c:out
							value="${vehicle.vehicle_registration}" /></span>
				</div>
				<div class="pull-right">
				
				<input type="hidden" value="${vehicle.vid}" id="vid" />
				<input type="hidden" value="${vehicle.vehicle_registration}" id="vehicleNumber" />
				<input type="hidden" value="${configDriverSalary.showBusIdDetails}" id="showBusIdDetails" />
				<input type="hidden" value="${tollConfig.multipleAccountForSameVendor}" id="multipleAccountForSameVendor" />
				
					<!--exp-->
					<c:if test="${configDriverSalary.showBusIdDetails}">
						<button type="button" class="btn btn-success btn-sm" data-toggle="modal"
							data-target="#addBusDetails" data-whatever="@mdo">
							<span class="fa fa-plus" id="AddJobType"> Add Bus Details </span>
						</button>
					</c:if>	
					<c:if test="${tollConfig.multipleAccountForSameVendor}">
						<button type="button" class="btn btn-success btn-sm" data-toggle="modal"
							data-target="#LinkICICITOll" data-whatever="@mdo">
							<span class="fa fa-plus" id="AddJobType">Link Toll Account </span>
						</button>
					</c:if>	
					<c:if test="${configuration.multipleAccountForSameVendor}">
						<button type="button" class="btn btn-success btn-sm" data-toggle="modal"
							data-target="#LinkGPSAccount" data-whatever="@mdo">
							<span class="fa fa-plus" id="AddJobType">Link GPS Account </span>
						</button>
					</c:if>	
					
					<c:if test="${configDriverSalary.showDriverMonthlySalary}">
						<c:if test="${checkVehicleStatus}">
							<button type="button" class="btn btn-success btn-sm" data-toggle="modal"
								 data-whatever="@mdo" onclick="getDriverMonthlySalaryInfo()";>
								<span class="fa fa-plus" id="AddJobType">Add Salary</span>		
							</button>
						 </c:if>
					 </c:if>
					 
					<c:if test="${configDriverSalary.showDriverMonthlyBhatta}">
						<c:if test="${checkVehicleStatus}">
							 <button type="button" class="btn btn-success btn-sm" data-toggle="modal"
										 data-whatever="@mdo" onclick="getDriverMonthlyBhattaInfo()";>
										<span class="fa fa-plus" id="AddJobType">Add Bhatta</span>		
							</button>
						</c:if> 
					 </c:if>
					 
					 <c:if test="${configDriverSalary.allowVehicleAgentPayment && vehicle.vehicleOwnerShipId == 4}">
							<%-- <button type="button" class="btn btn-success btn-sm" data-toggle="modal"
								 data-whatever="@mdo">
								<span class="fa fa-plus" id="AddJobType"><a href="VehicleAgentPayment.in?Id=${vehicle.vid }">Vehicle Agent Payment</a></span>		
							</button> --%>
							<a class="btn btn-success btn-sm" href="VehicleAgentPayment.in?Id=${vehicle.vid}"> <span
								class="fa fa-plus"></span> Vehicle Agent Payment
							</a>
					 </c:if>
					 

					<div class="modal fade" id="addManufacturer" role="dialog">
						<div class="modal-dialog">
							<!-- Modal content-->
							<div class="modal-content">

								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<h4 class="modal-title" id="JobType">Add Monthly Driver Salary</h4>
								</div>

								<div class="modal-body">
									<div class="row">
										<label class="L-size control-label" id="Type">
										Enter Amount<abbr title="required">*</abbr>
										</label>
										<div class="I-size">
											<input type="number" class="form-text" required="required"
												maxlength="150" name="amountForDriver" id="amountForDriver"
												placeholder="Enter Amount" />
										</div>
									</div>
									
									<br />
								</div>
								<div class="modal-footer">
									<button type="submit" class="btn btn-primary"
										onclick="addDriverMonthlySalary();">
										<span><spring:message code="label.master.Save" /></span>
									</button>
									<button type="button" class="btn btn-default"
										data-dismiss="modal">
										<span id="Close"><spring:message
												code="label.master.Close" /></span>
									</button>
								</div>
							</div>
						</div>
					</div>
					<!--exp-->
						
					
					<!-- driver bhatta -->
					 <div class="modal fade" id="modalDriverBhatta" role="dialog">
						<div class="modal-dialog">
							<!-- Modal content-->
							<div class="modal-content">

								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<h4 class="modal-title" id="JobType">Add Monthly Driver Bhatta</h4>
								</div>

								<div class="modal-body">
									<div class="row">
										<label class="L-size control-label" id="Type">
										Enter Amount<abbr title="required">*</abbr>
										</label>
										<div class="I-size">
											<input type="number" class="form-text" required="required"
												maxlength="150" name="bhattaForDriver" id="bhattaForDriver"
												placeholder="Enter Amount" />
										</div>
									</div>
									
									<br/>
								</div>
								<div class="modal-footer">
									<button type="submit" class="btn btn-primary"
										onclick="addDriverMonthlyBhatta();">
										<span><spring:message code="label.master.Save" /></span>
									</button>
									<button type="button" class="btn btn-default"
										data-dismiss="modal">
										<span id="Close"><spring:message
												code="label.master.Close" /></span>
									</button>
								</div>
							</div>
						</div>
					</div> 
					<!-- driver bhatta -->
					
					
					<c:choose>
						<c:when test="${vehicle.vStatusId == 1}">
							<sec:authorize access="hasAuthority('EDIT_VEHICLE')">
								<a class="btn btn-success btn-sm"
									href="<c:url value="/editVehicle.in?vid=${vehicle.vid}"/>">
									<span class="fa fa-pencil"></span> Edit Vehicle
								</a>
							</sec:authorize>
						</c:when>
						<c:when test="${vehicle.vStatusId == 8}">
							<sec:authorize access="hasAuthority('EDIT_VEHICLE')">
								<a class="btn btn-success btn-sm"
									href="<c:url value="/editVehicle.in?vid=${vehicle.vid}"/>">
									<span class="fa fa-pencil"></span> Edit Vehicle
								</a>
							</sec:authorize>
						</c:when>
					</c:choose>
					<sec:authorize access="hasAuthority('DOWNLOND_VEHICLE')">
						<a href="PrintVehicle?id=${vehicle.vid}" target="_blank"
							class="btn btn-default btn-sm"><i class="fa fa-print"></i>
							Print</a>
					</sec:authorize>
					<a class="btn btn-link btn-sm"
						href="<c:url value="/vehicle/${SelectStatus}/${SelectPage}"/>">
						<span id="AddVehicle"> Cancel</span>
					</a>
				</div>
			</div>
		</div>
	</section>
	<sec:authorize access="!hasAuthority('VIEW_VEHICLE')">
		<spring:message code="message.unauth"></spring:message>
	</sec:authorize>
	<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
		<section class="content-header" style="padding: 0px 15px 0;">
			<div class="box">
				<div class="box-body">
					<div class="row">
						<!-- Show the User Profile -->
						<div class="col-md-1 col-sm-2 col-xs-2">
							<a href="${pageContext.request.contextPath}/getImageVehicle/${vehicle.vehicle_photoid}.in"
								class="zoom" data-title="Vehicle Photo" data-footer="" 
								data-type="image" data-toggle="lightbox"> 
								  <span class="info-box-icon bg-green" id="iconContainer">
								        <i class="fa fa-bus"></i>
								  </span>
							      <img src="${pageContext.request.contextPath}/getImageVehicle/${vehicle.vehicle_photoid}.in"
							         class="" alt=" " width="100" height="100" id="vehicleImage" onerror="hideImageOnError(this)" />
							</a>
						</div>
						<div class="col-md-10 col-sm-8 col-xs-7">
							<h3 class="secondary-header-title">
								<a
									href="<c:url value="/${SelectStatus}/${SelectPage}/showVehicle.in?vid=${vehicle.vid}"/>">
									<c:out value="${vehicle.vehicle_registration}" />
								</a>
							</h3>

							<div class="secondary-header-title">
							 <input type="hidden" id="showTollIdFeild" value="${showTollIdFeild}">
							 <input type="hidden" id="allowGPSIntegration" value="${allowGPSIntegration}">
							 <input type="hidden" id="vehicleGPSId" value="${vehicle.vehicleGPSId}">
							 <input type="hidden" id="companyId" value="${companyId}">
								<ul class="breadcrumb">
									<li><span class="fa fa-black-tie" aria-hidden="true"
										data-toggle="tip" data-original-title="Status"><a
											href="#"><c:out value=" ${vehicle.vehicle_Status}" /></a></span></li>
											<c:if test="${configDriverSalary.addVehicleStatusRemark }">
											<li><span  aria-hidden="true"
										data-toggle="tip" data-original-title="Status"><a
											href="javascript:void(0)" onclick="getStatusChangeHistory(${vehicle.vid},2)">(Status History)</a></span></li>
									</c:if>
									<li><span class="fa fa-clock-o" aria-hidden="true"
										data-toggle="tip" data-original-title="Odometer"><a
											href="#"><c:out value=" ${vehicle.vehicle_Odometer}" /></a></span></li>
									<li><span class="fa fa-bus" aria-hidden="true"
										data-toggle="tip" data-original-title="Type"><a
											href="#"><c:out value=" ${vehicle.vehicle_Type}" /></a></span></li>
									<li><span class="fa fa-map-marker" aria-hidden="true"
										data-toggle="tip" data-original-title="Location"><a
											href="#"><c:out value=" ${vehicle.vehicle_Location}" /></a></span></li>
									<li><span class="fa fa-users" aria-hidden="true"
										data-toggle="tip" data-original-title="Group"><a
											href="#"><c:out value=" ${vehicle.vehicle_Group}" /></a></span></li>
									<li><span class="fa fa-road" aria-hidden="true"
										data-toggle="tip" data-original-title="Route"><a
											href="#"><c:out value=" ${vehicle.vehicle_RouteName}" /></a></span></li>
									<li><span class="fa fa-tripadvisor" aria-hidden="true"
										data-toggle="tip" data-original-title="Current Trip"><c:choose>
												<c:when test="${vehicle.tripSheetID != 0}">
													<a
														href="showTripSheet.in?tripSheetID=${vehicle.tripSheetID}"><c:out
															value="TS-${vehicle.tripSheetID}" /></a>
												</c:when>
											</c:choose></span></li>
									<li id="gpsLocationRow" style="display: none;"><span class="fa fa-map-marker" aria-hidden="true"
										data-toggle="tip" data-original-title="GPS Location" id="gpsLocationTag"></span></li>		

								</ul>
							</div>
						</div>
					</div>

				</div>
			</div>
		</section>
		<section class="content-body">

			<c:if test="${param.SaveSuccess eq true}">
				<div class="alert alert-success">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Vehicle Created successfully.
				</div>
			</c:if>
			<c:if test="${uploadVehicle}">
				<div class="alert alert-success">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Vehicle Updated successfully.
				</div>
			</c:if>
			<c:if test="${deleteVehicle}">
				<div class="alert alert-success">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Vehicle Deleted successfully.
				</div>
			</c:if>
			<c:if test="${alreadyVehicle}">
				<div class="alert alert-danger">
					<button class="close" data-dismiss="alert" type="button">x</button>
					This Vehicle Already created.
				</div>
			</c:if>
			<div class="row">
				<sec:authorize access="!hasAuthority('VIEW_VEHICLE')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
					<div class="col-md-9 col-sm-9 col-xs-12">
						<div class="main-body">

							<ul class="tabs">
								<li class=" current" data-tab="tab-1">Details</li>
								<li class="tab-link" data-tab="tab-2">Specifications</li>
								<!-- <li class="tab-link" data-tab="tab-3">Engine</li> -->
								<li class="tab-link" data-tab="tab-4">Fluids/Odometer</li>
								<li class="tab-link" data-tab="tab-5">Owner</li>
								<!-- <li class="tab-link" data-tab="tab-6">Loan/Lease</li> -->
							</ul>

							<div id="tab-1" class="tab-content2 current">
								<div class="box box-success">
									<div class="box-body no-padding">
										<p>Last 3 months Expense</p>
										<div class="row divider text-center">
											<div class="col-md-2 col-sm-2 col-xs-2 emphasis">
												<h4>
													<strong><a href="javascript:void(0)" onclick="getExpenseDetails(${vehicle.vid},1)"> ${LastRRCost}</a></strong>
												</h4>
												<p>
													<small>compliance</small>
												</p>
											</div>
											<div class="col-md-2 col-sm-2 col-xs-2 emphasis">
												<h4>
													<strong><a href="javascript:void(0)" onclick="getExpenseDetails(${vehicle.vid},2)">${LastFECost}</a> </strong>
												</h4>
												<p>
													<small>Fuel</small>
												</p>
											</div>
											<div class="col-md-2 col-sm-2 col-xs-2 emphasis">
												<h4>
													<strong>
													<sec:authorize access="hasAuthority('VIEW_DEALER_SERVICE_ENTRIES')">
													<a href="javascript:void(0)" onclick="getExpenseDetails(${vehicle.vid},4)">
													${LastSECost}
													</a>
													</sec:authorize>
														<sec:authorize access="!hasAuthority('VIEW_DEALER_SERVICE_ENTRIES')">
														${LastSECost}
														</sec:authorize>
													</strong>
												</h4>
												<p>
													<small>Service</small>
												</p>
											</div>
											<div class="col-md-2 col-sm-2 col-xs-2  emphasis">
												<h4>
													<strong>${LastTSCost}</strong>
												</h4>
												<p>
													<small>Trip Expense</small>
												</p>
											</div>
											<div class="col-md-2 col-sm-2 col-xs-2  emphasis">
												<h4>
													<strong><a href="javascript:void(0)" onclick="getExpenseDetails(${vehicle.vid},3)"> ${LastWOCost}</a></strong>
												</h4>
												<p>
													<small>WorkOrder</small>
												</p>
											</div>

										</div>
									</div>
								</div>

								<div class="row">
									<div class="col-md-5 col-sm-5 col-xs-12">
										<div class="box box-success">
											<div class="box-header">
												<h3 class="box-title">Vehicle Basic Details</h3>
											</div>
											<div class="box-body no-padding">
												<table class="table table-striped">
													<tbody>
														<tr class="row">
															<th class="key">Registration Number :</th>
															<td class="value"><c:out
																	value="${vehicle.vehicle_registration}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Chassis No :</th>
															<td class="value"><c:out
																	value="${vehicle.vehicle_chasis}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Engine No :</th>
															<td class="value"><c:out
																	value="${vehicle.vehicle_engine}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Manufacture Year :</th>
															<td class="value"><c:out
																	value="${vehicle.vehicle_year}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Vehicle Maker :</th>
															<td class="value"><c:out
																	value="${vehicle.vehicle_maker}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Vehicle Model:</th>
															<td class="value"><c:out
																	value="${vehicle.vehicle_Model}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Vehicle RegistrationState :</th>
															<td class="value"><c:out
																	value="${vehicle.vehicle_registrationState}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Vehicle Registered Date :</th>
															<td class="value"><c:out
																	value="${vehicle.vehicle_RegisterDate}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Vehicle Registered up to:</th>
															<td class="value"><c:out
																	value="${vehicle.vehicle_Registeredupto}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Vehicle GPS ID:</th>
															<td class="value"><c:out
																	value="${vehicle.vehicleGPSId}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Toll ID:</th>
															<td class="value"><c:out
																	value="${vehicle.vehicleTollId}" /></td>
														</tr>
														<tr class="row">
															<th class="key">GPS Vendor:</th>
															<td class="value"><c:out
																	value="${vehicle.gpsVendorName}" /></td>
														</tr>
														<c:if test="${vehicle.mobileNumber != null}">
															<tr class="row">
																<th class="key">Mobile Number:</th>
																<td class="value"><c:out
																		value="${vehicle.mobileNumber}" /></td>
															</tr>
														</c:if>
														<c:if test="${vehicle.ledgerName != null}">
															<tr class="row">
																<th class="key">Ledger Name:</th>
																<td class="value"><c:out
																		value="${vehicle.ledgerName}" /></td>
															</tr>
														</c:if>
														<c:if test="${tollConfig.multipleAccountForSameVendor}">
															<tr class="row">
																<th class="key">ICICI Fasttag Id:</th>
																<td class="value"><c:out
																		value="${customer.customerId}" /></td>
															</tr>
														</c:if>
														<c:if test="${configuration.multipleAccountForSameVendor}">
															<tr class="row">
																<th class="key">SMTC GPS A/C:</th>
																<td class="value"><c:out
																		value="${customer.userName}" /></td>
															</tr>
														</c:if>
														<!--exp-->
														<c:if test="${configDriverSalary.showDriverMonthlySalary}">
															<c:if test="${checkVehicleStatus}">
																<tr class="row">
																	<th class="key">Driver Monthly Salary:</th>
																	<td class="value"><c:out
																			value="${vehicle.driverMonthlySalary}" /></td>
																</tr>
															</c:if>
														</c:if>
														<!--exp-->
													</tbody>
												</table>
											</div>
										</div>
										<div class="box box-success">
											<div class="box-header">
												<h3 class="box-title">Classification</h3>
											</div>
											<div class="box-body no-padding">
												<table class="table table-striped">
													<tbody>
														<tr class="row">
															<th class="key">Vehicle Type :</th>
															<td class="value"><c:out
																	value="${vehicle.vehicle_Type}" /></td>
														</tr>
														<c:if test="${vehicle.vehicleBodyMakerId >0}">
														<tr class="row">
															<th class="key">Body Maker :</th>
															<td class="value"><c:out
																	value="${vehicle.bodyMakerName}" /></td>
														</tr>
														</c:if>
														<tr class="row">
															<th class="key">Vehicle Status :</th>
															<td class="value"><c:out
																	value="${vehicle.vehicle_Status}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Vehicle Group :</th>
															<td class="value"><c:out
																	value="${vehicle.vehicle_Group}" /></td>
														</tr>
														<c:if test="${configuration.showRoute}">
														<tr class="row">
															<th class="key">Route / Factory Name:</th>
															<td class="value"><c:out
																	value="${vehicle.vehicle_RouteName}" /></td>
														</tr>
														</c:if>
														<tr class="row">
															<th class="key">Vehicle Meter Unit :</th>
															<td class="value"><c:out
																	value="${vehicle.vehicle_MeterUnit}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Vehicle Fuel Unit:</th>
															<td class="value"><c:out
																	value="${vehicle.vehicle_FuelUnit}" /></td>
														</tr>

													</tbody>
												</table>
											</div>
										</div>
									</div>
									<div class="col-md-5 col-sm-5 col-xs-12">

										<div class="box box-success">
											<div id="container"
												style="min-width: 310px; height: 400px; margin: 0 auto">
												<span class="loading ng-hide" id="loading"> <img
													alt="Loading" class="loading-img"
													src="<c:url value="/resources/QhyvOb0m3EjE7A4/images/ajax-loader.gif" />">
												</span>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div id="tab-2" class="tab-content2">
								<div class="row">
									<div class="col-md-5 col-sm-5 col-xs-12">
										<div class="box box-success">
											<div class="box-header">
												<h3 class="box-title">Specification</h3>
											</div>
											<div class="box-body no-padding">
												<table class="table table-striped">
													<tbody>
														<c:if test="${configuration.showVehicleColor}">
															<tr class="row">
																<th class="key">Color :</th>
																<td class="value"><c:out
																		value="${vehicle.vehicle_Color}" /></td>
															</tr>
														</c:if>
														<c:if test="${configuration.showClassOfVehicle}">
														<tr class="row">
															<th class="key">Class :</th>
															<td class="value"><c:out
																	value="${vehicle.vehicle_Class}" /></td>
														</tr>
														</c:if>
														<c:if test="${configuration.showVehicleBody}">
														<tr class="row">
															<th class="key">Body :</th>
															<td class="value"><c:out
																	value="${vehicle.vehicle_body}" /></td>
														</tr>
														</c:if>
														<c:if test="${configuration.showACType}">
														<tr class="row">
															<th class="key">A/C Type :</th>
															<td class="value"><c:out
																	value="${vehicle.vehicle_actype}" /></td>
														</tr>
													</c:if>
													</tbody>
												</table>
											</div>
										</div>
										<div class="box box-success">
											<div class="box-header">
												<h3 class="box-title">Capacity</h3>
											</div>
											<div class="box-body no-padding">
												<table class="table table-striped">
													<tbody>
													<c:if test="${configuration.showCylinder}">
														<tr class="row">
															<th class="key">Cylinders :</th>
															<td class="value"><c:out
																	value="${vehicle.vehicle_Cylinders}" /></td>
														</tr>
														</c:if>
														<c:if test="${configuration.showCubicCapacity}">
														<tr class="row">
															<th class="key">Cubic Capacity:</th>
															<td class="value"><c:out
																	value="${vehicle.vehicle_CubicCapacity}" /></td>
														</tr>
														</c:if>
														<c:if test="${configuration.showPower}">
														<tr class="row">
															<th class="key">Power :</th>
															<td class="value"><c:out
																	value="${vehicle.vehicle_Power}" /></td>
														</tr>
														</c:if>
														<c:if test="${configuration.showWheelBase}">
														<tr class="row">
															<th class="key">WheelBase :</th>
															<td class="value"><c:out
																	value="${vehicle.vehicle_wheelBase}" /></td>
														</tr>
														</c:if>
													</tbody>
												</table>
											</div>
										</div>

									</div>
									<div class="col-md-5 col-sm-5 col-xs-12">
										<div class="box box-success">
											<div class="box-header">
												<h3 class="box-title">Weight</h3>
											</div>
											<div class="box-body no-padding">
												<table class="table table-striped">
													<tbody>
													<c:if test="${configuration.showSeatCapacity}">
														<tr class="row">
															<th class="key">SeatCapacity :</th>
															<td class="value"><c:out
																	value="${vehicle.vehicle_SeatCapacity}" /></td>
														</tr>
														</c:if>
														<tr class="row">
															<th class="key">UnladenWeight :</th>
															<td class="value"><c:out
																	value="${vehicle.vehicle_UnladenWeight}" /></td>
														</tr>
														<tr class="row">
															<th class="key">LadenWeight :</th>
															<td class="value"><c:out
																	value="${vehicle.vehicle_LadenWeight}" /></td>
														</tr>

													</tbody>
												</table>
											</div>
										</div>
									</div>
								</div>

							</div>
							<div id="tab-3" class="tab-content2"></div>
							<div id="tab-4" class="tab-content2">
								<div class="row">
									<div class="col-md-5 col-sm-5 col-xs-12">
										<div class="box box-success">
											<div class="box-header">
												<h3 class="box-title">Fuel</h3>
											</div>
											<div class="box-body no-padding">
												<table class="table table-striped">
													<tbody>
														<tr class="row">
															<th class="key">Vehicle Fuel :</th>
															<td class="value"><c:out
																	value="${vehicle.vehicle_Fuel}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Vehicle Fuel Tank :</th>
															<td class="value"><c:out
																	value="${vehicle.vehicle_FuelTank1}" /></td>
														</tr>
														<tr class="row">
															<th class="key">Expected Mileage :</th>
															<td class="value"><c:out
																	value="${vehicle.vehicle_ExpectedMileage}" /></td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>
										<div class="box box-success">
											<div class="box-header">
												<h3 class="box-title">Oil</h3>
											</div>
											<div class="box-body no-padding">
												<table class="table table-striped">
													<tbody>
														<tr class="row">
															<th class="key">Vehicle Oil Capacity:</th>
															<td class="value"><c:out
																	value="${vehicle.vehicle_Oil}" /></td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>
										
									</div>

									<div class="col-md-5 col-sm-5 col-xs-12">
										<div class="box box-success">
											<div class="box-header">
												<h3 class="box-title">Current OdoMeter</h3>
											</div>
											<div class="box-body no-padding">
												<table class="table table-striped">
													<tbody>
														<tr class="row">
															<th class="key">Vehicle OdoMeter :</th>
															<td class="value">
																<h4 id="vehicleOddometer">
																	<c:out value="${vehicle.vehicle_Odometer}" />
																</h4>
															</td>
														</tr>
														<tr class="row" id="gpsOddometerRow" style="display: none;">
															<th class="key">GPS Odometer :</th>
															<td class="value">
																<h4 id="gpsOddometer">
																	
																</h4>
															</td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>
										<div class="box box-success">
											<div class="box-header">
												<h3 class="box-title">Expected (Allow Maximum ) OdoMeter</h3>
											</div>
											<div class="box-body no-padding">
												<table class="table table-striped">
													<tbody>
														<tr class="row">
															<th class="key">Allow Maximum Odometer :</th>
															<td class="value">
																<h4 id="vehicleExpectedOddometer">
																	<c:out value="${vehicle.vehicle_ExpectedOdameter}" />
																</h4>
															</td>
														</tr>
														
													</tbody>
												</table>
											</div>
										</div>
										
									</div>
								</div>
							</div>
							<div id="tab-5" class="tab-content2">
								<div class="row">
									<div class="col-md-5 col-sm-5 col-xs-12">
										<div class="box box-success">
											<div class="box-header">
												<h3 class="box-title">Owner Details</h3>
											</div>
											<div class="box-body no-padding">
												<table class="table table-striped">
													<tbody>
														<tr class="row">
															<th class="key">Ownership :</th>
															<td class="value"><c:out
																	value="${vehicle.vehicle_Ownership}" /></td>
														</tr>
														<c:if test="${configuration.showVehicleLocation }">
															<tr class="row">
																<th class="key">Location :</th>
																<td class="value"><c:out
																		value="${vehicle.vehicle_Location}" /></td>
															</tr>
														</c:if>
													</tbody>
												</table>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<small class="text-muted"><b>Created by :</b> <c:out
									value="${vehicle.createdBy}" /></small> | <small class="text-muted"><b>Created
									date: </b> <c:out value="${vehicle.created}" /></small> | <small
								class="text-muted"><b>Last updated by :</b> <c:out
									value="${vehicle.lastModifiedBy}" /></small> | <small
								class="text-muted"><b>Last updated date:</b> <c:out
									value="${vehicle.lastupdated}" /></small>
						</div>
					</div>
				</sec:authorize>
				<!-- side reminter -->
				<div class="col-md-2 col-sm-2 col-xs-12">
					<%@include file="VehicleSideMenu.jsp"%>
				</div>
			</div>
		</section>
		
		<div class="modal fade" id="addBusDetails" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="JobType">Add Bus Details
								Details</h4>
						</div>
						<div class="modal-body">
							
							
							<div class="row">
								<label class="L-size control-label" id="Type"> Vehicle Name :
								<abbr title="required">*</abbr>
								</label>
								<div class="I-size">
									<input type="text" class="form-text" readonly="readonly"
										name="vname" id="vname"
										value="${vehicle.vehicle_registration}" />
								</div>
							</div>
							
							<br/>
							
							<div class="row">
								<label class="L-size control-label" id="Type">Bus Id :
								<abbr title="required">*</abbr>
								</label>
								<div class="I-size">
									<input type="number" class="form-text" id="busId"
										name="busId" required="required"
										placeholder="Enter Bus Id" />
								</div>
							</div>
							
							<br/>
							
							<div class="row">
								<label class="L-size control-label" id="Type">Device Id :
								<abbr title="required">*</abbr>
								</label>
								<div class="I-size">
									<input type="number" class="form-text" id="deviceId"
										name="deviceId" required="required"
										placeholder="Enter Device Id" />
								</div>
							</div>
							
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary" onclick="saveBusDetails();">
								<span><spring:message code="label.master.Save" /></span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close"><spring:message
										code="label.master.Close" /></span>
							</button>
						</div>
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="LinkICICITOll" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="JobType">Link ICICI Toll</h4>
						</div>
						<div class="modal-body">
							
							
							<div class="row">
								<label class="L-size control-label" id="Type"> Vehicle Name :
								<abbr title="required">*</abbr>
								</label>
								<div class="I-size">
									<input type="text" class="form-text" readonly="readonly"
										name="vname" id="vname"
										value="${vehicle.vehicle_registration}" />
								</div>
							</div>
							
							<br/>
							<div class="row">
											<label class="L-size control-label" for="issue_vehicle_id">Customer Id
												: </label>
											<div class="I-size" id="customerSelect">
												<select class="form-text select2" name="vehicleTollDetailsId"
													id="vehicleTollDetailsId" style="width: 100%;">
													<c:if test="${customer.vehicleTollDetailsId != null }">
														<option value="${customer.vehicleTollDetailsId}">
															${customer.customerId}
														</option>
													</c:if>
														<option value="0">
															Please select
														</option>
													<c:forEach items="${customerList}" var="customerList">
														<option value="${customerList.vehicleTollDetailsId}">
															<c:out value="${customerList.customerId}" />
														</option>
													</c:forEach>
												</select>
											</div>
							</div>							
							
						
							<br/>
							
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary" onclick="linkVehicleToll();">
								<span><spring:message code="label.master.Save" /></span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close"><spring:message
										code="label.master.Close" /></span>
							</button>
						</div>
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="LinkGPSAccount" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="JobType">Link GPS Account</h4>
						</div>
						<div class="modal-body">
							
							
							<div class="row">
								<label class="L-size control-label" id="Type"> Vehicle Name :
								<abbr title="required">*</abbr>
								</label>
								<div class="I-size">
									<input type="text" class="form-text" readonly="readonly"
										name="vname" id="vname"
										value="${vehicle.vehicle_registration}" />
								</div>
							</div>
							
							<br/>
							<div class="row">
											<label class="L-size control-label" for="issue_vehicle_id">GPS Accounts
												: </label>
											<div class="I-size" id="vehicleGPSCredentialSelect">
												<select class="form-text select2" name="vehicleGPSCredentialId"
													id="vehicleGPSCredentialId" style="width: 100%;">
													<c:if test="${customer.vehicleGPSCredentialId != null }">
														<option value="${customer.vehicleGPSCredentialId}">
															${customer.userName}
														</option>
													</c:if>
														<option value="0">
															Please select
														</option>
													<c:forEach items="${credentialList}" var="credentialList">
														<option value="${credentialList.vehicleGPSCredentialId}">
															<c:out value="${credentialList.userName}" />
														</option>
													</c:forEach>
												</select>
											</div>
							</div>							
							
						
							<br/>
							
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary" onclick="linkVehicleGPSAC();">
								<span><spring:message code="label.master.Save" /></span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close"><spring:message
										code="label.master.Close" /></span>
							</button>
						</div>
				</div>
			</div>
		</div>
		<div class="modal fade" id="statusChangeDetails" role="dialog">
			<div class="modal-dialog" style="width: 980px;">
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h3 class="modal-title">Status Change Details :</h3>
						</div>
						<div class="modal-body">
						
						<div class="main-body">
					<div class="box">
						<div class="box-body">
							<div class="table-responsive">
								<table id="taskTable" class="table table-hover table-bordered">
								</table>
							</div>
						</div>
					</div>
					
						<div class="box">
						<div class="box-body">
							<div class="table-responsive">
								<table id="soldTable" class="table table-hover">
								</table>
							</div>
						</div>
					</div>
				</div>
						
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span>Cancel</span>
							</button>
						</div>
				</div>
			</div>
		</div>
		
			<div class="modal fade" id="showDetails" role="dialog">
			<div class="modal-dialog" style="width: 980px;">
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h3 class="modal-title">Vehicle Expense Details :</h3>
						</div>
						<div class="modal-body">
						
						<div class="main-body">
					<div class="box">
						<div class="box-body">
							<div class="table-responsive">
								<table id="showDetailsTable" class="table table-hover table-bordered">
								</table>
							</div>
						</div>
					</div>
				</div>
						
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span>Cancel</span>
							</button>
						</div>
				</div>
			</div>
		</div>
		
	</sec:authorize>
	
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabsShowVehicle.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VI/VehicleShowlanguage.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/gps/VehicleGPSDetails.js" />"></script>
	<script type='text/javascript'
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/photoView/ekko-lightbox.js"/>" ></script>
	<script type="text/javascript">
		//window.onload =getVehicleOdometerGraph(v);
		$(document).ready(function() {
			v = ${vehicle.vid};
			getVehicleOdometerGraph(v);
			
			  var img = $("#vehicleImage");
		         var iconContainer = $("#iconContainer");
	
		         // Check if the image is loaded
		         img.on("load", function() {
		             // If loaded, hide the icon
		             iconContainer.hide();
		         });
		});
		 
	</script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/charts/highcharts.js" />"></script>
	<!--  <script type="text/javascript"  src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/charts/exporting.js" />"></script> -->
</div>
