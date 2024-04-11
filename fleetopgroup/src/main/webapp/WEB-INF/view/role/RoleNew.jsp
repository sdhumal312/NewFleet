<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="content-wrapper">
	<section class="content-header">

		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><spring:message
							code="label.master.NewVehicleRole" /></small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_ROLE')">
						<a type="button" class="btn btn-success btn-sm"
							href="<c:url value="/addRole.html?id=${user.id}" />"> <i
							class="fa fa-plus"></i> <spring:message
								code="label.master.AddRole" />
						</a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_ROLE')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_ROLE')">
				<c:if test="${param.saveRole eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						<spring:message code="message.master.SaveRole" />
					</div>
				</c:if>
				<c:if test="${param.uploadRole eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						<spring:message code="message.master.UpdateRole" />
					</div>
				</c:if>
				<c:if test="${param.deleteRole eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						<spring:message code="message.master.DeleteRole" />

					</div>
				</c:if>
				<c:if test="${param.alreadyRole eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						<spring:message code="label.master.NewVehicleRole" />
						<spring:message code="message.master.AlreadyRole" />
					</div>
				</c:if>
				<div class="col-md-offset-1 col-xs-9">
					<div class="main-body">
						<div class="box">
							<div class="box-body">
								<table id="SubTypeTable" class="table table-hover table-striped">
									<thead>
										<tr>
											<th class="icon"><spring:message
													code="label.pages.roles" /></th>
											<th>Count</th>  
											<th class="icon"><spring:message
													code="label.master.Usage" /></th>
											<th class="icon"><spring:message
													code="label.master.Action" /></th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty Role}">
											<c:forEach items="${Role}" var="Role">
												<tr>
													<td><c:out value="${Role.name}" /></td>
													
													<input type="hidden" id="roleId" value="${Role.id}">
													
													<td>
															<c:forEach items="${Role1}" var="Role1">
																<c:if test="${Role.id==Role1.role_id}" var="roleId">
																	<a href="#" onclick="showUserPopup(${Role.id})"><c:out value="${Role1.user_id}" /></a>
																</c:if>
															
															</c:forEach>
															
													</td>												
													
													
													<td> <!--Usage   -->  </td>
													<td>
														<div class="btn-group">
															<a class="btn btn-default btn-sm dropdown-toggle"
																data-toggle="dropdown" href="#"> <span
																class="fa fa-cog"></span> <span class="caret"></span>
															</a>
															<ul class="dropdown-menu pull-right">
																<li><sec:authorize access="hasAuthority('ADD_ROLE')">
																		<a href="viewRole.html?id=${user.id}&RID=${Role.id}"
																			class="confirmation"
																			onclick="return confirm('Are you sure you Want to View Role?')">
																			<span class="fa fa-link"></span> View
																			Role_Permissions
																		</a>
																	</sec:authorize></li>
																<li><sec:authorize access="hasAuthority('ADD_ROLE')">
																		<a href="editRole.html?id=${user.id}&RID=${Role.id}"
																			class="confirmation"
																			onclick="return confirm('Are you sure you Want to Edit Role?')">
																			<span class="fa fa-pencil"></span> Edit
																			Role_Permissions
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
					</div>
				</div>
				
				<!-- POP UP Logic Code START -->
				<div class="modal fade" id="configureUser" role="dialog">
					<div class="modal-dialog modal-md">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4 class="modal-title">Information</h4>
							</div>

							<div class="modal-body">

								<%-- <div class="row">
											<!-- <label class="L-size control-label">Service ID</label> -->
											<div class="I-size">
												<input type="hidden" class="form-text" id="serviceId"
													name="service" value="${Role1.role_id}"
													readonly="true"
													placeholder="Enter Vehicle" /> <label
													id="errorvStatus" class="error"></label>
											</div>
										</div> --%>
										
									<div class="row invoice-info" id="reportHeader" style="font-size: 15px;font-weight: bold;">
										</div>
										<div class="row invoice-info">
											<table id="userInfoDetails" style="width: 95%;"
																class="table-hover table-bordered">
												
											</table>
										</div>	
										
								<br>
							</div>
						</div>
					</div>
				</div>
				<!-- POP UP Logic Code END -->
				
				
				
				
				
				
				
			</sec:authorize>
		</div>	
	</section>
		<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/RolePermissionUserInfo.js"/>"></script>
</div>
