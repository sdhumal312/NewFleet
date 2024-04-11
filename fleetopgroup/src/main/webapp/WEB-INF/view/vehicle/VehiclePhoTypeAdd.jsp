<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addVehiclePhoType.in"/>">New Photo Type</a></small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#addvehicletypes" data-whatever="@mdo">
							<span class="fa fa-plus"> Create Photo Type</span>
						</button>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<c:if test="${param.saveVehiclePhoType eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vehicle Photo Types Created Successfully.
			</div>
		</c:if>
		<c:if test="${param.uploadVehiclePhoType eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vehicle Photo Types Updated Successfully.
			</div>
		</c:if>
		<c:if test="${param.deleteVehiclePhoType eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vehicle Photo Types Deleted Successfully.
			</div>
		</c:if>
		<c:if test="${param.alreadyVehiclePhoType eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vehicle Photo Type Already Exists.
			</div>
		</c:if>
		<div class="modal fade" id="addvehicletypes" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<form action="saveVehPhoType.in" method="post"
						onsubmit="return validatePhoType()">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="VehiclePhoType">New Vehicle
								Photo Type</h4>
						</div>
						
						<div class="modal-body">
						<div class="row1">
								<label class="L-size control-label" id="">Photo
									Type </label>
								<div class="I-size">
									<select class="form-text" id="vehiclePhotoTypeId" name="vehiclePhotoTypeId">
										<option value="1">Vehicle Type</option>
										<option value="2">Vehicle Accident Type</option>
									</select>
								</div>
							</div>
							<br>
							<div class="row1">
								<label class="L-size control-label" id="PhoType">Photo
									Type Name</label>
								<div class="I-size">
									<input type="text" class="form-text" id="vPhoType"
										name="vPhoType" placeholder="Enter PhoType Name" /> <label
										id="errorvPhoType" class="error"></label>
								</div>
							</div>
							<br />
							
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary" onclick="return photoTypeValidate()">
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
									<table id="PhoTypeTable"
										class="table  table-striped">
										<thead>
											<tr>
												<th id="PhoTypeName" class="icon">Vehicle Photo Type Name</th>
												<th id="Usage" class="icon">Vehicle Photo Type</th>
												<th id="Actions" class="icon">Actions</th>
											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty vehiclePhoType}">
												<c:forEach items="${vehiclePhoType}" var="vehiclePhoType">
													<tr>
														<td><c:out value="${vehiclePhoType.vPhoType}" /></td>
														<c:choose>
															<c:when test="${vehiclePhoType.vehiclePhotoTypeId == 1}">
															<td>Vehicle Type</td>
															</c:when>
															<c:otherwise><td>Vehicle Accident Type</td></c:otherwise>
														</c:choose>
														
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
																				href="editVehiclePhoType.in?ptid=${vehiclePhoType.ptid}">
																				<span class="fa fa-pencil"></span> Edit
																			</a>
																		</sec:authorize></li>
																	<li><sec:authorize
																			access="hasAuthority('DELETE_PRIVILEGE')">
																			<a
																				href="deleteVehiclePhoType.in?ptid=${vehiclePhoType.ptid}"
																				class="confirmation"
																				onclick="return confirm('Are you sure you Want to delete Photo Type ?')">
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
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/PhoType.validate.js" />"></script>
</div>