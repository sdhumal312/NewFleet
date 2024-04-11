<%@page import="java.util.HashMap"%>
<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.in"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/addVehicleTyresize.in"/>">Vehicle Tyre
						Size</a>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
						<%-- <c:if test="${user eq 'admin' }"> --%>
							<button type="button" class="btn btn-success" data-toggle="modal"
								data-target="#addvehicletypes" data-whatever="@mdo">
								<span class="fa fa-plus"> Create Tyre Size</span>
							</button>
						<%-- </c:if> --%>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
	<c:if test="${validateTyreSizeId eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Already In used Hence You Can Not Delete Tyre Size.
			</div>
		</c:if>
		<c:if test="${param.saveVehicleTyreSize eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vehicle Tyre Size Created Successfully.
			</div>
		</c:if>
		<c:if test="${param.updateVehicleTyreSize eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vehicle Tyre Size Updated Successfully.
			</div>
		</c:if>
		<c:if test="${param.deleteTyreSize eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vehicle Tyre Size Deleted Successfully.
			</div>
		</c:if>
		<c:if test="${param.alreadyVehicleTyreSize eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vehicle Tyre Size Already Exists.
			</div>
		</c:if>
		<!-- Modal  and create the javaScript call modal -->
		<div class="modal fade" id="addvehicletypes" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<form action="saveTyreSize.in" method="post" name="vehicleType"
						onsubmit="return validateType()">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="VehicleType">Vehicle Tyre Size</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<label class="L-size control-label" id="Type">Tyre Size
									: <abbr title="required">*</abbr>
								</label>
								<div class="I-size">
									<input type="text" min="0" id="tyreSizeId" max="1000" class="form-text"
										required="required" name="TYRE_SIZE"
										placeholder="Enter Tyre Size" /> <label id="errorvType"
										class="error"></label>
								</div>
							</div>
							<div class="row">
								<label class="L-size control-label">Remarks</label>
								<div class="I-size">
									<input type="text" class="form-text" id="dJobType"
										name="TYRE_SIZE_DESCRITION" placeholder="Enter Remarks" /> <label
										id="errordJobType" class="error"></label>
								</div>
							</div>
							<br />
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary" onclick="return validateTyreSize()">
								<span id="Save" ><spring:message code="label.master.Save" /></span>
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
								<div class="box-body">
									<div class="table-responsive">
										<c:if test="${!empty tyreSize}">
											
											<table id="typeTable" class="table table-striped">
												<thead>
													<tr>
														<th id="TypeName" class="icon">Tyre Size</th>
														<th id="Usage" class="icon">Remarks</th>
														<c:if test="${!configuration.vehicleTyreSizeMasterCommon}">
															<th id="Actions" class="icon">Actions</th>
														</c:if>

													</tr>
												</thead>
												<tbody>
													<c:forEach items="${tyreSize}" var="tyreSize">
														<tr>
															<td><c:out value="${tyreSize.TYRE_SIZE}" /></td>
															<td><c:out value="${tyreSize.TYRE_SIZE_DESCRITION}" /></td>
															<c:if test="${!configuration.vehicleTyreSizeMasterCommon}">
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
																					href="<c:url value="/editVehicleTyreSize?Id=${tyreSize.TS_ID}"/>">
																					<span class="fa fa-pencil"></span> Edit
																				</a>
																			</sec:authorize></li>
																		<li><sec:authorize
																				access="hasAuthority('DELETE_PRIVILEGE')">
																				<a
																					href="<c:url value="/deleteTyreSize?Id=${tyreSize.TS_ID}"/>"
																					class="confirmation"
																					onclick="return confirm('Are you sure you Want to delete Size ?')">
																					<span class="fa fa-trash"></span> Delete
																				</a>
																			</sec:authorize></li>
																	</ul>
																</div>
															</td>
															</c:if>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</c:if>
										<c:if test="${empty tyreSize}">
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
					</div>
				</div>
			</div>			
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/Type.validate.js" />"></script>
</div>