<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/jquery.dataTables.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/buttons.dataTables.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/showVehicleBodyMakerList.in"/>">vehicle
							Body Maker</a></small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
						<button type="button" class="btn btn-success"
							onclick="showCreateBodyMaker()">
							<span class="fa fa-plus" id="AddJobType"> Create Body
								Maker</span>
						</button>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="modal fade" id="addBodyMaker" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="headerText">New Vehicle Body
							Maker</h4>
						<input type="hidden" id="editBodyMakerId" value="0">
					</div>
					<div class="modal-body">
						<div class="row1">
							<label class="L-size control-label">Vehicle Body Maker</label>
							<div class="I-size">
								<input type="text" class="form-text" id="bodyMaker"
									name="Job_Type" placeholder="Enter Vehicle Body Maker" />
							</div>
						</div>
						<br />
					</div>
					<div class="modal-footer">
						<button type="button" id="addButton" class="btn btn-primary"
							onclick="submitVehicleBodyMaker();">Submit</button>
						<button type="button" id="updateButton" class="btn btn-primary"
							onclick="updateVehicleBodyMaker();">Update</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span id="Close"><spring:message code="label.master.Close" /></span>
						</button>
					</div>
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
							<input type="hidden" id="companyId" value="${companyId}">
							<div class="box-body">
								<div class="table-responsive">
									<table id="typeTable"
										class="table table-bordered table-striped">
										<thead>
											<tr>
												<th id="TypeName" class="icon">Vehicle Body Maker Name</th>
												<!-- 												<th id="Usage" class="icon">Usage</th> -->
												<th id="Actions" class="icon">Actions</th>
											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty list}">
												<c:forEach items="${list}" var="list">
													<tr>
														<td><c:out value="${list.vehicleBodyMakerName}" /></td>
														<td>
															<div class="btn-group">
																<a class="btn btn-Link dropdown-toggle"
																	data-toggle="dropdown" href="#"> <span
																	class="fa fa-cog"></span> <span class="caret"></span>
																</a>

																<ul class="dropdown-menu pull-right">
																	<li><sec:authorize
																			access="hasAuthority('EDIT_PRIVILEGE')">
																			<a href="javascript:void(0)"
																				onclick="editVehicleBodyMaker(${list.vehicleBodyMakerId},'${list.vehicleBodyMakerName}')">
																				<span class="fa fa-pencil"></span> Edit
																			</a>
																		</sec:authorize></li>
																	<li><sec:authorize
																			access="hasAuthority('DELETE_PRIVILEGE')">
																			<a href="javascript:void(0)" class="confirmation"
																				onclick="deleteVehicleBodyMaker(${list.vehicleBodyMakerId})">
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/dataTables.buttons.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.print.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.flash.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VI/vehicleBodyMaker.js" />"></script>

</div>