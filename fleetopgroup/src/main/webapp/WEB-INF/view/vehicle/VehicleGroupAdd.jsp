<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addVehicleGroup?id=${user.id}"/>">New
							Vehicle Group</a></small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#addvehicletypes" data-whatever="@mdo">
							<span class="fa fa-plus" id="AddVehicleGroup"> Add Vehicle
								Group</span>
						</button>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<c:if test="${param.saveVehicleGroup eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vehicle Group Created Successfully.
			</div>
		</c:if>
		<c:if test="${param.updateVehicleGroup eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vehicle Group Updated Successfully.
			</div>
		</c:if>
		<c:if test="${param.deleteVehicleGroup eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vehicle Group Deleted Successfully.
			</div>
		</c:if>
		<c:if test="${param.alreadyVehicleGroup eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vehicle Group Already Exists.
			</div>
		</c:if>
		<div class="modal fade" id="addvehicletypes" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<form action="saveVehGroup.in" method="post"
						onsubmit="return validateGroup()">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="VehicleGroup">Vehicle Group</h4>
						</div>
						<div class="modal-body">
							<div class="row1">
								<label class="L-size control-label" id="Group">Group</label>
								<div class="I-size">
									<input name="userID" type="hidden" value="${user.id}"
										required="required" /> <input name="createdBy" type="hidden"
										value="${user.email}" required="required" /> <input
										type="text" class="form-text" id="vGroup" name="vGroup"
										placeholder="Enter Group Name" /> <label id="errorvGroup"
										class="error"></label>
								</div>
							</div>
							<br />
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary" onclick="return vehicleGroupValidate()">
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
								<div class="table-responsive">
									<table id="groupTable" class="table table-striped">
										<thead>
											<tr>
												<th id="GroupName" class="icon">GroupName</th>
												<th id="Usage" class="icon">Usage</th>
												<th id="Actions" class="icon">Actions</th>
											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty vehiclegroup}">
												<c:forEach items="${vehiclegroup}" var="vehiclegroup">
													<tr>
														<td><c:out value="${vehiclegroup.vGroup}" /></td>
														<td><a href="#.." rel="facebox"> vehicles</a></td>
														<td>
															<div class="btn-group">
																<a class="btn btn-Link dropdown-toggle"
																	data-toggle="dropdown" href="#"> <span
																	class="fa fa-cog"></span> <span class="caret"></span>
																</a>
																<ul class="dropdown-menu pull-right">
																	<li><sec:authorize
																			access="hasAuthority('EDIT_PRIVILEGE')">
																			<a
																				href="<c:url value="/editVehicleGroup?id=${user.id}&gid=${vehiclegroup.gid}"/>">
																				<span class="fa fa-pencil"></span> Edit
																			</a>
																		</sec:authorize></li>
																	<li><sec:authorize
																			access="hasAuthority('DELETE_PRIVILEGE')">
																			<a
																				href="<c:url value="/deleteVehicleGroup?id=${user.id}&gid=${vehiclegroup.gid}"/>"
																				class="confirmation"
																				onclick="return confirm('Are you sure you Want to delete Group Type?')">
																				<span class="fa fa-trash"></span> Delete
																			</a>
																		</sec:authorize></li>
																</ul>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/Group.validate.js" />"></script>
</div>