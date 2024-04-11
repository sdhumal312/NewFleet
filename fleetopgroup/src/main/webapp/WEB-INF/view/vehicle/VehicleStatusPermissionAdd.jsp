<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addVehicleStatus?id=${user.id}"/>"><spring:message
								code="" /></a></small>
				</div>
				<%-- <div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#addvehicletypes" data-whatever="@mdo">
							<span class="fa fa-plus"> <spring:message
									code="label.master.AddVehicleStatus" /></span>
						</button>
					</sec:authorize>
				</div> --%>
			</div>
		</div>
	</section>

	<section class="content">
		<c:if test="${param.saveVehicleStatus eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				<spring:message code="message.master.SaveVehicleStatus" />
			</div>
		</c:if>
		<c:if test="${param.updateVehicleStatus eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				<spring:message code="message.master.UpdateVehicleStatus" />
			</div>
		</c:if>
		<c:if test="${param.deleteVehicleStatus eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				<spring:message code="message.master.DeleteVehicleStatus" />

			</div>
		</c:if>
		<c:if test="${param.alreadyVehicleStatus eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				<spring:message code="message.master.AlreadyVehicleStatus" />

			</div>
		</c:if>
		<c:if test="${param.emptyVehicleStatus eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				<h4>Warning!</h4>
				<p>
					The page no data to Show.., Please Don't Change any URL ID or
					Number.. <br> Don't Enter any Extra worlds in URL..
				</p>
			</div>
		</c:if>
		<div class="modal fade" id="addvehicletypes" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<form action="saveVehStatusPermission.in" method="post" name="vehicleStatu"
						onsubmit="return validateStatus()">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<%-- <h4 class="modal-title">
								<spring:message code="label.master.NewVehicleStatus" />
							</h4> --%>
						</div>
						<div class="modal-body">
							<div class="row1">
								<%-- <label class="L-size control-label"><spring:message
										code="label.master.VehicleStatus" /></label> --%>
								
									<input name="userID" type="hidden" value="${user.id}"
										required="required" /> <input name="createdBy" type="hidden"
										value="${user.email}" required="required" /> 
									<h4 style="text-align: center;">	Are you sure to Add Vehicle Status ? </h4>
							</div>
							<br />
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary">
								<span> OK </span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close">cancel</span>
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
								<div class="table-responsive">
								<div class="dropdown-header fit" style="font-weight: bold;font-size : 25px; text-align: center;"> Activated Vehicle Status </div>
									<table id="StatusTable" class="table table-striped">
										<thead>
											<tr>
												<th id="StatusName" class="fit">Status Name</th>
												<th id="Usage" class="fit">Usage</th>
												<th id="Actions" class="fit">Actions</th>

											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty vehiclestatuspermission}">
												<c:forEach items="${vehiclestatuspermission}" var="vehiclestatus">
													<tr>
														<td><c:out value="${vehiclestatus.vStatus}" /></td>
														<td><a href="#.." rel="facebox"> vehicles</a></td>
														<td>
															<div class="btn-group">
															  <a
																				href="<c:url value="/deleteVehicleStatusPermission?sid=${vehiclestatus.sid}"/>"
																				class="confirmation btn btn-success"
																				onclick="return confirm('Are you sure you Want to Delete this Vehicle Status?')">
																				 DEACTIVATE
																			</a>
															</div>
														</td>
													</tr>
												</c:forEach>
											</c:if>
											<c:if test="${empty vehiclestatus}">
												<tr>
													<td colspan="3">
														<div class="callout callout-info">
															<h4>Empty Vehicle status</h4>
															<p>Click Add Vehicle Status Button.</p>
														</div>

													</td>
												</tr>
											</c:if>
										</tbody>
									</table>
						<div class="dropdown-header fit" style="font-weight: bold;font-size : 25px; text-align: center;"> Available Vehicle Status </div>		
							<table id="AvailableStatusTable" class="table">
										<thead>
											<tr>
												<th id="StatusName" class="fit">Status Name</th>
												<th id="Actions" class="fit">Actions</th>

											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty vehiclestatus}">
												<c:forEach items="${vehiclestatus}" var="vehiclestatus">
													<tr>
														<td><c:out value="${vehiclestatus.vStatus}" /></td>
														<td>
															<div class="btn-group">
															<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
																		    <a id="vehicleStatusId_${vehiclestatus.sid}"
																				href="<c:url value="/saveVehicleStatusPermission?sid=${vehiclestatus.sid}"/>"
																				class="confirmation btn btn-success"
																				onclick="return confirm('Are you sure you Want to Add this Vehicle Status?')">
																				 ACTIVATE
																			</a>
																
															</sec:authorize>
															</div>
														</td>
													</tr>
												</c:forEach>
											</c:if>
											<c:if test="${empty vehiclestatus}">
												<tr>
													<td colspan="3">
														<div class="callout callout-info">
															<h4>Empty Vehicle status</h4>
															<p>Click Add Vehicle Status Button.</p>
														</div>

													</td>
												</tr>
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
	<script type="text/javascript">
		$(document).ready(function() {
			var abc = ${vehiclestatus};
			console.log(abc);
			/* console.log(${vehiclestatus}.length);
			for(var i = 0 ; i < ${vehiclestatus}.length ; i++) {
				console.log(${vehiclestatus}[i]);
			} */
		});
	</script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/status.validate.js" />"></script>
</div>