<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/tabs.css" />">
<div class="content-wrapper">
	<sec:authorize access="!hasAuthority('VIEW_COMPANY')">
		<spring:message code="message.unauth"></spring:message>
	</sec:authorize>
	<sec:authorize access="hasAuthority('VIEW_COMPANY')">
		<section class="content-header">
			<div class="box">
				<div class="box-body">
					<div class="row">
						<div class="pull-left">
							<a href="<c:url value="/open"/>"><spring:message
									code="label.master.home" /></a> / <small><a
								href="<c:url value="/newCompany"/>">Company</a></small>
						</div>
						<div class="pull-right">
							<sec:authorize access="hasAuthority('ADD_COMPANY')">
								<a class="btn btn-success btn-sm"
									href="<c:url value="/editCompany.in?id=${Company.company_id_encode}"/>">
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
		<c:if test="${param.deleteSubCompany eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Company details Deleted successfully.
			</div>
		</c:if>
		<c:if test="${param.deleteDirectorCompany eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Company Owner/Director details Deleted successfully.
			</div>
		</c:if>
		<c:if test="${param.deleteBankCompany eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Company Bank details Deleted successfully.
			</div>
		</c:if>
		<c:if test="${param.alreadyCompany eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Company Already created.
			</div>
		</c:if>
		<c:if test="${param.deleteCompanyDetails eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Remove company Bank &amp; Directors Details...
			</div>
		</c:if>
		<section class="content">
			<div class="row">
				<div class="col-md-9">
					<c:if test="${!empty Company}">
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
					</c:if>
				</div>
				<div class="col-md-2">
					<ul class="nav nav-list">
						<li><sec:authorize access="hasAuthority('ADD_BANK_COMPANY')">
								<a class="fa fa-users" aria-hidden="true" data-toggle="tip"
									data-original-title="Owner/Directors Details"
									href="DirectorCompany.in?id=${Company.company_id_encode}">
									Create Owner/Directors</a>
							</sec:authorize></li>
						<li><sec:authorize access="hasAuthority('ADD_DIRECTOR_COMPANY')">
								<a class="fa fa-university" aria-hidden="true" data-toggle="tip"
									data-original-title="Bank Details"
									href="BankCompany.in?id=${Company.company_id_encode}">
									Create Bank Details</a>
							</sec:authorize></li>

					</ul>
				</div>
			</div>
		</section>

	</sec:authorize>
	<section class="content-body">

		<!-- Company Bank Details  -->
		<div class="row">
			<ul class="tabs">
				<li class=" current" data-tab="tab-1">Owner/Director</li>
				<li class="tab-link" data-tab="tab-3">Bank Details</li>
			</ul>

			<div id="tab-1" class="tab-content2 current">
				<c:if test="${!empty Company}">
					<div class="col-sm-11">
						<div class="box">
							<sec:authorize access="!hasAuthority('ADD_DIRECTOR_COMPANY')">
								<spring:message code="message.unauth"></spring:message>
							</sec:authorize>
							<sec:authorize access="hasAuthority('ADD_DIRECTOR_COMPANY')">
								<div class="box-body">
									<div class="table-responsive">

										<table class="table table-striped">
											<thead>
												<tr>
													<th>Director Name</th>
													<th>Designation</th>
													<th>Phone</th>
													<th>Email</th>
													<th id="Actions" class="fit">Actions</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${!empty CompanyDirector}">
													<c:forEach items="${CompanyDirector}" var="CompanyDirector">
														<tr>
															<td class="fit ar"><c:out
																	value="${CompanyDirector.com_directors_name}" /></td>
															<td class="fit ar"><c:out
																	value="${CompanyDirector.com_designation}" /></td>
															<td class="fit ar"><c:out
																	value="${CompanyDirector.com_directors_mobile}" /></td>

															<td class="fit ar"><c:out
																	value="${CompanyDirector.com_directors_email}" /></td>
															<td>
																<div class="btn-group">
																	<a class="btn btn-Link dropdown-toggle"
																		data-toggle="dropdown" href="#"> <span
																		class="fa fa-cog"></span> <span class="caret"></span>
																	</a>
																	<ul class="dropdown-menu pull-right">
																		<li><sec:authorize
																				access="hasAuthority('ADD_DIRECTOR_COMPANY')">
																				<a
																					href="<c:url value="/editDirectorCompany?id=${CompanyDirector.com_directors_id}"/>">
																					<span class="fa fa-pencil"></span> Edit Director
																				</a>
																			</sec:authorize></li>
																		<li><sec:authorize
																				access="hasAuthority('ADD_DIRECTOR_COMPANY')">
																				<a
																					href="<c:url value="/deleteDirectorCompany?id=${CompanyDirector.com_directors_id}"/>"
																					class="confirmation"
																					onclick="return confirm('Are you sure you Want to delete Owner/Dirctor Company?')">
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
				</c:if>
			</div>
			<div id="tab-3" class="tab-content2">
				<c:if test="${!empty Company}">
					<div class="col-sm-11">
						<div class="box">
							<sec:authorize access="!hasAuthority('ADD_BANK_COMPANY')">
								<spring:message code="message.unauth"></spring:message>
							</sec:authorize>
							<sec:authorize access="hasAuthority('ADD_BANK_COMPANY')">
								<div class="box-body">
									<div class="table-responsive">
										<table class="table table-striped">
											<thead>
												<tr>
													<th>Bank Name</th>
													<th class="fit ar">A/C NO</th>
													<th class="fit ar">A/C Type</th>
													<th class="fit ar">IFCS CODE</th>
													<th class="fit ar">BSR CODE</th>
													<th class="fit ar">Branch Name</th>
													<th>Address</th>
													<th id="Actions" class="fit">Actions</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${!empty CompanyBank}">
													<c:forEach items="${CompanyBank}" var="CompanyBank">
														<tr>
															<td><c:out value="${CompanyBank.com_bank_name}" /></td>
															<td><c:out value="${CompanyBank.com_bank_account}" /></td>
															<td class="fit ar"><c:out
																	value="${CompanyBank.com_bank_actype}" /></td>
															<td class="fit ar"><c:out
																	value="${CompanyBank.com_bank_ifsc}" /></td>
															<td class="fit ar"><c:out
																	value="${CompanyBank.com_bank_bsr}" /></td>

															<td class="fit ar"><c:out
																	value="${CompanyBank.com_bank_branch}" /></td>
															<td><c:out value="${CompanyBank.com_bank_address}" />
																, <c:out value="${CompanyBank.com_bank_city}" /><br>
																<c:out value="${CompanyBank.com_bank_state}" />,<c:out
																	value="${CompanyBank.com_bank_country}" />, Pin:<c:out
																	value="${CompanyBank.com_bank_pincode}" /></td>
															<td>
																<div class="btn-group">
																	<a class="btn btn-Link dropdown-toggle"
																		data-toggle="dropdown" href="#"> <span
																		class="fa fa-cog"></span> <span class="caret"></span>
																	</a>
																	<ul class="dropdown-menu pull-right">
																		<li><sec:authorize
																				access="hasAuthority('ADD_BANK_COMPANY')">
																				<a
																					href="<c:url value="/editBankCompany?id=${CompanyBank.com_bank_id}"/>">
																					<span class="fa fa-pencil"></span> Edit
																				</a>
																			</sec:authorize></li>
																		<li><sec:authorize
																				access="hasAuthority('ADD_BANK_COMPANY')">
																				<a
																					href="<c:url value="/deleteBankCompany?id=${CompanyBank.com_bank_id}"/>"
																					class="confirmation"
																					onclick="return confirm('Are you sure you Want to delete Status?')">
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
				</c:if>
			</div>

		</div>


		<c:if test="${empty Company}">
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
		</c:if>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabsShowVehicle.js" />"></script>
</div>