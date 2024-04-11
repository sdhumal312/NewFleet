<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addPartLocations.in"/>">New Part
							Locations</a></small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
						<a href="createPartLocations.in" class="btn btn-success"><span
							class="fa fa-plus" id="AddPartLocations"> Create Part
								Locations</span></a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<c:if test="${param.savePartLocations eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Part Locations Created Successfully.
			</div>
		</c:if>
		<c:if test="${param.updatePartLocations eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Part Locations Updated Successfully.
			</div>
		</c:if>
		<c:if test="${param.deletePartLocations eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Part Locations Deleted Successfully.
			</div>
		</c:if>
		<c:if test="${param.alreadyPartLocations eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Part Locations Already Exists.
			</div>
		</c:if>
		<c:if test="${param.danger eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Part Locations Not Created Successfully.
			</div>
		</c:if>
		<c:if test="${param.deleteInside eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Part Location name you can't delete. this used in Part Inventory, Tyre Inventory,
				WorkOrders ,PO etc... .
			</div>
		</c:if>
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
									<table id="PartLocationsTable"
										class="table table-hover table-striped">
										<thead>
											<tr>
												<th class="icon">Name</th>
												<c:if test="${configuration.subPartLocationTypeNeeded}">
													<th class="icon">Location Type</th>
												</c:if>
												<th class="icon">Description</th>
												<th id="Usage" class="icon">Usage</th>
												<th id="Actions" class="icon">Actions</th>

											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty PartLocations}">
												<c:forEach items="${PartLocations}" var="PartLocations">
													<tr>
														<td><a
															href="showPartLocations.in?partlocation_id=${PartLocations.partlocation_id}"><c:out
																	value="${PartLocations.partlocation_name}" /></a></td>
														<c:if test="${configuration.subPartLocationTypeNeeded}">
															<c:choose>
																<c:when test="${PartLocations.partLocationType == 1}">
																	<td>
																		<c:out value="Main Location"/>
																	</td>
																</c:when>
																<c:otherwise>
																	<td>
																		<c:out value="Sub Location"/>
																	</td>	
																</c:otherwise>
															</c:choose>
														</c:if>			
														<td><a
															href="showPartLocations.in?partlocation_id=${PartLocations.partlocation_id}"><c:out
																	value="${PartLocations.partlocation_description}" /></a></td>
														<td><a href="#.." rel="facebox"> Part</a></td>
														<td>
															<div class="btn-group">
																<a class="btn btn-default dropdown-toggle"
																	data-toggle="dropdown" href="#"> <span
																	class="fa fa-cog"></span> <span class="caret"></span>
																</a>
																<ul class="dropdown-menu pull-right">
																	<li><sec:authorize
																			access="hasAuthority('EDIT_PRIVILEGE')">
																			<a
																				href="editPartLocations.in?partlocation_id=${PartLocations.partlocation_id}">
																				<span class="fa fa-pencil"></span> Edit
																			</a>
																		</sec:authorize></li>
																	<li><sec:authorize
																			access="hasAuthority('DELETE_PRIVILEGE')">
																			<a
																				href="deletePartLocations.in?partlocation_id=${PartLocations.partlocation_id}"
																				class="confirmation"
																				onclick="return confirm('Are you sure you Want to delete Part Locations?')">
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/Parts.validate.js" />"></script>
</div>