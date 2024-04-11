<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/addVehicleTypes/1.in"/>"><spring:message
							code="label.master.NewVehicletype" /></a>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#addvehicletypes" data-whatever="@mdo">
							<span class="fa fa-plus"> <spring:message
									code="label.master.VehicleAddtype" /></span>
						</button>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<c:if test="${param.saveVehicleType eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vehicle Type Created Successfully.
			</div>
		</c:if>
		<c:if test="${param.updateVehicleType eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vehicle Type Updated Successfully.
			</div>
		</c:if>
		<c:if test="${param.deleteType eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vehicle Type Deleted Successfully.
			</div>
		</c:if>
		<c:if test="${param.alreadyVehicleType eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Vehicle Type Already Exists.
			</div>
		</c:if>
		<!-- Modal  and create the javaScript call modal -->
		<div class="modal fade" id="addvehicletypes" role="dialog">
			<div class="modal-dialog modal-md" style="width:750px;">
				<!-- Modal content-->
				<div class="modal-content">
					<form action="<c:url value="../saveVehType"/>" method="post" name="vehicleType"
						onsubmit="return validateType()">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="VehicleType">Vehicle Type</h4>
						</div>
						<div class="modal-body">
							<div class="row1">
								<label class="L-size control-label" id="Type">Type</label>
								<div class="I-size">
									<input name="userID" type="hidden" value="${user.id}"
										required="required" /> <input name="createdBy" type="hidden"
										value="${user.email}" required="required" /> <input
										type="text" class="form-text" id="vtype" name="Vtype"
										placeholder="Enter Type Name" /> <label id="errorvType"
										class="error"></label>
								</div>
							</div>
							<div class="row1">
								<label class="L-size control-label" id="Type">Maximum Allowed Odometer :</label>
								<div class="I-size">
									<input
										type="number" class="form-text" id="maxAllowedOdometer" name="maxAllowedOdometer"
										placeholder="Enter Type Name" onkeypress="return isNumberKey(event,this);"/> <label id="errorvType"
										class="error"></label>
								</div>
							</div>
							<br />
										<!-- <div class="row1" id="grprenewalType" class="form-group">
													<label class="L-size control-label" for="from">Service
														Program <abbr title="required">*</abbr>
													</label>
													<div class="I-size">
														<input type="hidden" id="serviceProgramId" name="serviceProgramId"
															style="width: 100%;"
															placeholder="Please Enter 2 or more Job Name" />
													</div>
										</div> -->
							<br />
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary" onclick="return vehicleTypevalidate()">
								<span id="Save"><spring:message code="label.master.Save" /></span>
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

					<sec:authorize access="!hasAuthority('VIEW_PRIVILEGE')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_PRIVILEGE')">
						<div class="box">
							<div class="box-body">
								<div class="box-body">
									<div class="table-responsive">
										<table class="table table-striped">
											<thead>
												<tr>
													<th id="TypeName" class="icon">Type Name</th>
													<th id="Usage" class="icon">Max Allowed Odometer</th>
												<!-- 	<th id="Usage" class="icon">Service Program</th> -->
													<th id="Actions" class="icon">Actions</th>

												</tr>
											</thead>
											<tbody>
												<c:if test="${!empty vehicletypes}">
													<c:forEach items="${vehicletypes}" var="vehicletypes">
														<tr>
															<td><c:out value="${vehicletypes.vtype}" /></td>
															<td><c:out value="${vehicletypes.maxAllowedOdometer}" /></td>
															<%-- <td><a target="_blank" href="/fleetopgroup/viewServiceProgram?Id=${vehicletypes.serviceProgramId}" rel="facebox">${vehicletypes.programName}</a></td> --%>
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
																						href="<c:url value="/editVehicleTypes?&tid=${vehicletypes.tid}"/>">
																						<span class="fa fa-pencil"></span> Edit
																					</a>
																				</sec:authorize></li>
																			<li><sec:authorize
																					access="hasAuthority('DELETE_PRIVILEGE')">
																					<a
																						href="<c:url value="/deleteVehicleTypes?&tid=${vehicletypes.tid}"/>"
																						class="confirmation"
																						onclick="return confirm('Are you sure you Want to delete Type ?')">
																						<span class="fa fa-trash"></span> Delete
																					</a>
	
																				</sec:authorize></li>
																		</ul>
																</div>
															</td>
														</tr>
													</c:forEach>
												</c:if>
												<c:if test="${empty vehicletypes}">
													<tr>
														<td colspan="3">
															<div class="callout callout-info">
																<h4>Empty Vehicle Type</h4>
																<p>Click to Create Vehicle Type Button.</p>
															</div>

														</td>
													</tr>

												</c:if>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
						<c:url var="firstUrl" value="/addVehicleTypes/1" />
						<c:url var="lastUrl"
							value="/addVehicleTypes/${deploymentLog.totalPages}" />
						<c:url var="prevUrl" value="/addVehicleTypes/${currentIndex - 1}" />
						<c:url var="nextUrl" value="/addVehicleTypes/${currentIndex + 1}" />
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
									<c:url var="pageUrl" value="/addVehicleTypes/${i}" />
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
				</div>
			</div>			
		</div>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>	
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/Type.validate.js" />"></script>
			
			<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>  
	</section>
</div>