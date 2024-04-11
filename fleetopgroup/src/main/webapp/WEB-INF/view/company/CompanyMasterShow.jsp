<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/tabs.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="row">
					<div class="pull-left">
						<a href="<c:url value="/open"/>"><spring:message
								code="label.master.home" /></a> / <a
							href="<c:url value="/masterCompany/1.in"/>">Company</a>
					</div>
					<div class="pull-right">
						<sec:authorize access="hasAuthority('MASTER_COM_EDIT_PRIVILEGE')">
							<a class="btn btn-success btn-sm"
								href="<c:url value="/AddModulePrivileges.in?CID=${Company.company_id_encode}"/>">
								<span class="fa fa-pencil"></span> Edit Module Privileges
							</a>
							<a class="btn btn-success btn-sm"
								href="<c:url value="/addRoleToCompany.in?CID=${Company.company_id_encode}"/>">
								<span class="fa fa-pencil"></span> Edit Feild Privileges
							</a>
							<a class="btn btn-success btn-sm"
								href="<c:url value="/editMasterCompany.in?CID=${Company.company_id_encode}"/>">
								<span class="fa fa-pencil"></span> Edit Company
							</a>
						</sec:authorize>
					</div>
				</div>
			</div>
		</div>
	</section>
	<c:if test="${param.saveCompany eq true}">
		<div class="alert alert-success">
			<button class="close" data-dismiss="alert" type="button">x</button>
			Sub Company Created successfully.
		</div>
	</c:if>
	<c:if test="${param.updateCompany eq true}">
		<div class="alert alert-success">
			<button class="close" data-dismiss="alert" type="button">x</button>
			Company details Updated successfully.
		</div>
	</c:if>

	<c:if test="${param.alreadyCompany eq true}">
		<div class="alert alert-danger">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This Company Already created.
		</div>
	</c:if>
	<c:if test="${param.pSaved eq true}">
		<div class="alert alert-success">
			<button class="close" data-dismiss="alert" type="button">x</button>
			Module Privileges Saved Successfully.
		</div>
	</c:if>

	<section class="content">
		<c:if test="${!empty Company}">
			<div class="row">
				<div class="col-md-9">
					<div class="box">
						<div class="box-body">
							<div class="row">
								<c:if test="${!empty Company}">
									<!-- Show the User Profile -->
									<div class="pull-left">
										<a
											href="${pageContext.request.contextPath}/downloadlogo/${Company.company_id_encode}.in"
											class="zoom" data-title="logo"
											data-footer="${Company.company_name}" data-type="image"
											data-toggle="lightbox"> <img
											src="${pageContext.request.contextPath}/downloadlogo/${Company.company_id_encode}.in"
											class="img-rounded" alt="Company Logo" width="100%;"
											height="100px;" />
										</a>
									</div>
								</c:if>
							</div>
							<div class="row">
								<div class="table-responsive">

									<table class="table table-striped">
										<thead>
											<tr>
												<th>PAN NO</th>
												<th>TAN NO</th>
												<th>GST NO</th>
												<th>TIN NO</th>
												<th>CIN NO</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td><c:out value="${Company.company_pan_no}" /></td>
												<td><c:out value="${Company.company_tan_no}" /></td>
												<td><c:out value="${Company.company_tax_no}" /></td>
												<td><c:out value="${Company.company_tin_no}" /></td>
												<td><c:out value="${Company.company_cin_no}" /></td>
											</tr>
											<tr>
												<td colspan="5">
													<address>
														<c:out value="${Company.company_name}" />
														<br>
														<c:out value="${Company.company_address}" />
														<br>
														<c:out value="${Company.company_city}" />
														,
														<c:out value="${Company.company_state}" />
														,
														<c:out value="${Company.company_country}" />
														, Pin:
														<c:out value="${Company.company_pincode}" />

													</address>
												</td>
											</tr>
											<tr>
												<td colspan="2">Email : <c:out
														value="${Company.company_email}" />
												</td>
												<td colspan="2">WebSite : <a
													href="${Company.company_website}"><c:out
															value="${Company.company_website}" /></a>
												</td>
												<td>Phone : <c:out
														value="${Company.company_mobilenumber}" />
												</td>
											</tr>
											<tr>
												<td colspan="5"><b>About US:</b> <c:out
														value="${Company.company_abount}" /></td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-2">
					<ul class="nav nav-list">
						<li><sec:authorize access="hasAuthority('MASTER_COM_ADD_PRIVILEGE')">
								<c:if test="${empty branch}">
									<a class="fa fa-sitemap" aria-hidden="true" data-toggle="tip"
										data-original-title="Create Branch"
										href="<c:url value="/addMasterBranch.in?CID=${Company.company_id_encode}"/>">
										Step 1 Setup Branch</a>
								</c:if>
								<c:if test="${!empty branch}">
									<span style="color: green;"><i class="fa fa-check"
										aria-hidden="true"></i> Step 1 Setup Branch </span>
								</c:if>
							</sec:authorize></li>

						<li><sec:authorize access="hasAuthority('MASTER_COM_ADD_PRIVILEGE')">
								<c:if test="${empty department}">
									<a class="fa fa-users" aria-hidden="true" data-toggle="tip"
										data-original-title="Create Deportment"
										href="<c:url value="/addMasterDepartment.in?CID=${Company.company_id_encode}"/>">
										Step 2 Setup Deportment</a>
								</c:if>
								<c:if test="${!empty department}">
									<span style="color: green;"><i class="fa fa-check"
										aria-hidden="true"></i> Step 2 Setup Deportment </span>
								</c:if>

							</sec:authorize></li>
						<li><sec:authorize access="hasAuthority('MASTER_COM_ADD_PRIVILEGE')">
								<c:if test="${empty modulePrivileges}">
									<a class="fa fa-university" aria-hidden="true"
										data-toggle="tip" data-original-title="Create Role"
										href="<c:url value="/AddModulePrivileges.in?CID=${Company.company_id_encode}"/>">
										Step 3 Setup Module Privileges</a>
								</c:if>
								<c:if test="${!empty modulePrivileges}">
									<span style="color: green;"><i class="fa fa-check"
										aria-hidden="true"></i> Step 3 Setup Module Privileges</span>
								</c:if>
						</sec:authorize></li>	
						<li><sec:authorize access="hasAuthority('MASTER_COM_ADD_PRIVILEGE')">
								<c:if test="${empty feildPrivileges}">
									<a class="fa fa-university" aria-hidden="true"
										data-toggle="tip" data-original-title="Create Role"
										href="<c:url value="/addRoleToCompany.in?CID=${Company.company_id_encode}"/>">
										Step 4 Setup Company Privileges</a>
								</c:if>
								<c:if test="${!empty feildPrivileges}">
									<span style="color: green;"><i class="fa fa-check"
										aria-hidden="true"></i> Step 4 Setup Company Privileges</span>
								</c:if>
						</sec:authorize></li>
						<li><sec:authorize access="hasAuthority('MASTER_COM_ADD_PRIVILEGE')">
								<c:if test="${empty Role}">
									<a class="fa fa-university" aria-hidden="true"
										data-toggle="tip" data-original-title="Create Role"
										href="<c:url value="/addMasterRole.in?CID=${Company.company_id_encode}"/>">
										Step 5 Setup Super Role</a>
								</c:if>
								<c:if test="${!empty Role}">
									<span style="color: green;"><i class="fa fa-check"
										aria-hidden="true"></i> Step 5 Setup Super Role</span>
								</c:if>
							</sec:authorize></li>
						<li><sec:authorize access="hasAuthority('MASTER_COM_ADD_PRIVILEGE')">
								<c:if test="${empty userprofile}">
									<a class="fa fa-university" aria-hidden="true"
										data-toggle="tip"
										data-original-title="Create User"
										href="<c:url value="/addMasterUser.in?CID=${Company.company_id_encode}"/>">
										Step 6 Setup Super User</a>
								</c:if>
								<c:if test="${!empty userprofile}">
									<span style="color: green;"><i class="fa fa-check"
										aria-hidden="true"></i> Step 6 Setup Super User</span>
								</c:if>
							</sec:authorize></li>

					</ul>
				</div>
			</div>
		</c:if>
	</section>
	<section class="content-body">
		<c:if test="${!empty Company}">
			<!-- Company Bank Details  -->
			<div class="row">
				<ul class="tabs">
					<li class="current" data-tab="tab-1"><sec:authorize
							access="hasAuthority('MASTER_COM_VIEW_PRIVILEGE')">Step 1 Setup Branch <c:if
								test="${!empty branch}">
								<span style="color: green;"><i class="fa fa-check"
									aria-hidden="true"></i> </span>
							</c:if>
						</sec:authorize></li>
					<li class="tab-link" data-tab="tab-2"><sec:authorize
							access="hasAuthority('MASTER_COM_VIEW_PRIVILEGE')">Step 2 Setup Deportment
							<c:if test="${!empty department}">
								<span style="color: green;"><i class="fa fa-check"
									aria-hidden="true"></i> </span>
							</c:if>
						</sec:authorize></li>
					<li class="tab-link" data-tab="tab-3"><sec:authorize
							access="hasAuthority('MASTER_COM_VIEW_PRIVILEGE')">Step 5 Setup Super Role
							<c:if test="${!empty Role}">
								<span style="color: green;"><i class="fa fa-check"
									aria-hidden="true"></i> </span>
							</c:if>
						</sec:authorize></li>
					<li class="tab-link" data-tab="tab-4"><sec:authorize
							access="hasAuthority('MASTER_COM_VIEW_PRIVILEGE')">Step 6 Setup Super User
							<c:if test="${!empty userprofile}">
								<span style="color: green;"><i class="fa fa-check"
									aria-hidden="true"></i> </span>
							</c:if>
						</sec:authorize></li>
				</ul>
				<div id="tab-1" class="tab-content2 current">
					<c:if test="${!empty Company}">
						<div class="col-sm-11">
							<div class="box">
								<sec:authorize access="!hasAuthority('MASTER_COM_VIEW_PRIVILEGE')">
									<spring:message code="message.unauth"></spring:message>
								</sec:authorize>
								<sec:authorize access="hasAuthority('MASTER_COM_VIEW_PRIVILEGE')">
									<div class="box-body">
										<div class="table-responsive">

											<table id="DriverTable"
												class="table table-bordered table-striped">
												<thead>
													<tr>
														<th>Branch Name</th>
														<th>Status</th>
														<th>Contact</th>
														<th>Primary Contact</th>
														<th>Secondary Contact</th>
														<th>Time</th>
													</tr>
												</thead>
												<tbody>
													<c:if test="${!empty branch}">
														<c:forEach items="${branch}" var="branch">
															<tr>
																<td><a href="#"><c:out
																			value="${branch.branch_name}" /></a></td>
																<td><c:out value="${branch.branch_status}" /></td>
																<td><c:out value="${branch.branch_phonenumber}" /><br>
																	<c:out value="${branch.branch_mobilenumber}" /></td>
																<td><c:out value="${branch.owner1_name}" /><br>
																	<c:out value="${branch.owner1_mobile}" /></td>
																<td><c:out value="${branch.owner2_name}" /><br>
																	<c:out value="${branch.owner2_mobile}" /></td>
																<td><c:out value="${branch.branch_time_from} to " />
																	<c:out value="${branch.branch_time_to}" /></td>

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
					</c:if>
				</div>
				<div id="tab-2" class="tab-content2">
					<c:if test="${!empty Company}">
						<div class="col-sm-11">
							<div class="box">
								<sec:authorize access="!hasAuthority('MASTER_COM_VIEW_PRIVILEGE')">
									<spring:message code="message.unauth"></spring:message>
								</sec:authorize>
								<sec:authorize access="hasAuthority('MASTER_COM_VIEW_PRIVILEGE')">
									<div class="box-body">
										<div class="table-responsive">

											<table id="DocTypeTable"
												class="table table-hover table-striped">
												<thead>
													<tr>
														<th class="icon">Department Name</th>
														<th class="icon">Department code</th>
														<th class="icon">HOD</th>
														<th class="icon">Description</th>
													</tr>
												</thead>
												<tbody>
													<c:if test="${!empty department}">
														<c:forEach items="${department}" var="department">
															<tr>
																<td><c:out value="${department.depart_name}" /></td>
																<td><c:out value="${department.depart_code}" /></td>
																<td><c:out value="${department.depart_hod}" /></td>
																<td><c:out value="${department.depart_description}" /></td>

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
					</c:if>
				</div>
				<div id="tab-3" class="tab-content2">
					<c:if test="${!empty Company}">
						<div class="col-sm-11">
							<div class="box">
								<sec:authorize access="!hasAuthority('MASTER_COM_VIEW_PRIVILEGE')">
									<spring:message code="message.unauth"></spring:message>
								</sec:authorize>
								<sec:authorize access="hasAuthority('MASTER_COM_VIEW_PRIVILEGE')">
									<div class="box-body">
										<div class="table-responsive">
											<table id="SubTypeTable"
												class="table table-hover table-striped">
												<thead>
													<tr>
														<th class="icon"><spring:message
																code="label.pages.roles" /></th>
														<th class="icon"><spring:message
																code="label.master.Usage" /></th>
													</tr>
												</thead>
												<tbody>
													<c:if test="${!empty Role}">
														<c:forEach items="${Role}" var="Role">
															<tr>
																<td><c:out value="${Role.name}" /></td>
																<td>
																	<%-- <c:out value="${Role.count_user} - Users" /> --%>
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
					</c:if>
				</div>
				<div id="tab-4" class="tab-content2">
					<c:if test="${!empty Company}">
						<div class="col-sm-11">
							<div class="box">
								<sec:authorize access="!hasAuthority('MASTER_COM_VIEW_PRIVILEGE')">
									<spring:message code="message.unauth"></spring:message>
								</sec:authorize>
								<sec:authorize access="hasAuthority('MASTER_COM_VIEW_PRIVILEGE')">
									<div class="box-body">
										<div class="table-responsive">

											<table id="SubTypeTable" class="table table-hover table-bordered">
												<thead>
													<tr>
														<th>Login Id</th>
														<th class="fit ar">Name</th>
														<th class="fit">Status</th>
													</tr>
												</thead>
												<tbody>
													<c:if test="${!empty userprofile}">
														<c:forEach items="${userprofile}" var="userprofile">
															<tr>
																<td><c:out value="${userprofile.user_email}" /><br>
																	<sec:authorize access="hasAuthority('MASTER_RESET_PWD_PRIVILEGE')">
																		<a
																			href="<c:url value="/resetMasterUserPassword?CID=${Company.company_id_encode}&id=${userprofile.user_id}"/>">Reset
																			Password</a>
																	</sec:authorize></td>
																<td class="fit ar"><c:out
																		value="${userprofile.firstName}  " /> <c:out
																		value="${userprofile.lastName}" /> <br> <span>
																		${userprofile.sex}</span></td>
																<td class="fit">
																		<c:choose>
																			<c:when test="${userprofile.markForDelete}">

																				<span class="label label-danger"><c:out
																						value="In-Active" /></span>

																			</c:when>
																			<c:otherwise>
																				<span class="label label-success"><c:out
																						value="Active" /></span>

																			</c:otherwise>
																		</c:choose></td>
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
					</c:if>
				</div>
			</div>
		</c:if>
		<%-- <c:if test="${empty Company}">
			<div class="col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2">
				<div class="panel panel-default">
					<div class="panel-body">
						<div class="row">
							<div class="col-sm-5">
								<h2 class="model-title">Create Company</h2>
								<p class="model-description">Store detailed records for
									company, Owner/Directors, Bank and other Company types.</p>
								<p class="b-padded">
									<a href="" target="_blank"><i
										class="material-icons md-launch"></i> Learn more </a>
								</p>
							</div>
							<div class="col-sm-3 col-sm-offset-1">
								<div class="plan-upgrade-card text-center">
									<h3>Get Started</h3>
									<div class="row t-padded">
										<div class="col-sm-12">
											<div class="btn-group">
												<a class="btn btn-success" href="CreateCompany.in"><i
													class="icon-plus"></i> Create Company </a>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:if> --%>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabsShowVehicle.js" />"></script>
	<script type="text/javascript">
		function savePass() {
			var pass = $("#pass").val();
			var valid = pass == $("#passConfirm").val();
			if (!valid) {
				$("#error").show();
				return;
			}
			$
					.post(
							"<c:url value="/user/savePassword"></c:url>",
							{
								password : pass
							},
							function(data) {
								window.location.href = "<c:url value="/login.html"></c:url>"
										+ "?message=" + data.message;
							})
					.fail(
							function(data) {
								window.location.href = "<c:url value="/login.html"></c:url>"
										+ "?message="
										+ data.responseJSON.message;
							});
		}
	</script>
</div>