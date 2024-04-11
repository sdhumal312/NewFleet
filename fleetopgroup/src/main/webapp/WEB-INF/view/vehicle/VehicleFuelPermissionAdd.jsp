<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						<%-- href="<c:url value="/addVehicleFuel"/>">New --%>
						href="<c:url value="/addVehicleFuel"/>">New
							Vehicle Fuel</a></small>
				</div>
				
			</div>
		</div>
	</section>
	<section class="content">
		<c:if test="${param.saveVehicleFuel eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vehicle Fuel Types Created Successfully.
			</div>
		</c:if>
		<c:if test="${param.uploadVehicleFuel eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vehicle Fuel Types Updated Successfully.
			</div>
		</c:if>
		<c:if test="${param.deleteVehicleFuel eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vehicle Fuel Types Deleted Successfully.
			</div>
		</c:if>
		<c:if test="${param.alreadyVehicleFuel eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vehicle Fuel Types Already Exists.
			</div>
		</c:if>
		<div class="modal fade" id="addvehicletypes" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<form action="saveVehFuel.in" method="post" name="vehicleStatu"
						onsubmit="return validateFuel()">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="VehicleFuel"> Vehicle Fuel Type</h4>
						</div>
						<div class="modal-body">
							<div class="row1">
								<label class="L-size control-label" id="Fuel">Fuel Type</label>
								<div class="I-size">
									<input type="text" class="form-text" id="vFuel" name="VFuel"
										placeholder="Enter Fuel Name" /> <label id="errorvFuel"
										class="error"></label>
								</div>
							</div>
							<br />
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary">
								<span><spring:message code="label.master.Save" /></span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close"><spring:message
										code="label.master.Close" /></span>
							</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-9">
				<div class="main-body">
					<div class="box">
						<sec:authorize access="!hasAuthority('VIEW_PRIVILEGE')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('VIEW_PRIVILEGE')">
							<div class="box-body">
							<div class="dropdown-header fit" style="font-weight: bold;font-size : 25px; text-align: center;"> Activated Vehicle Fuel Type </div>
								<div class="table-responsive">
									<table id="FuelTable"
										class="table table-bordered table-striped">
										<thead>
											<tr>
												<th id="FuelName" class="icon">FuelName</th>
												<th id="Usage" class="icon">Usage</th>
												<th id="Actions" class="icon">Actions</th>

											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty vehiclefuelPermission}">
												<c:forEach items="${vehiclefuelPermission}" var="vehiclefuel">
													<tr>
														<td><c:out value="${vehiclefuel.vFuel}" /></td>
														<td><a href="#.." rel="facebox"> vehicles</a></td>
														<td>
															<div class="btn-group">
															  <a
																				href="<c:url value="/deleteVehicleFuelPermission?fid=${vehiclefuel.fid}"/>"
																				class="confirmation btn btn-success"
																				onclick="return confirm('Are you sure you Want to Add Fuel This Type?')">
																				 DEACTIVATE
																			</a>
															</div>
														</td>
													</tr>
												</c:forEach>
											</c:if>
										</tbody>
									</table>
									
								<div class="dropdown-header fit" style="font-weight: bold;font-size : 25px; text-align: center;"> All Available Vehicle Fuel Type </div>	
									<table id="FuelTablePermission"
										class="table table-bordered table-striped">
										<thead>
											<tr>
												<th id="FuelName" class="icon">FuelName</th>
												
												<th id="Actions" class="icon">Actions</th>

											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty vehiclefuel}">
												<c:forEach items="${vehiclefuel}" var="vehiclefuel">
													<tr>
														<td><c:out value="${vehiclefuel.vFuel}" /></td>
														
														<td>
															<div class="btn-group">
															  <a
																				href="<c:url value="/saveVehFuelPermission?fid=${vehiclefuel.fid}"/>"
																				class="confirmation btn btn-success"
																				onclick="return confirm('Are you sure you Want to Add Fuel This Type?')">
																				 ACTIVATE
																			</a>
															</div>
														</td>
													</tr>
												</c:forEach>
											</c:if>
										</tbody>
									</table>
									
									
								</div>
							</div>
						</sec:authorize>
					</div>
				</div>
			</div>			
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/FU/Fuel.validate.js" />"></script>
</div>