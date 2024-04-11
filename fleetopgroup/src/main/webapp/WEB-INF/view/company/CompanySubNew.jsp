<%@ include file="taglib.jsp"%>
<!-- <script type="text/javascript" src="resources/js/Print/printTripsheet.js" />"></script> -->
<div class="content-wrapper">
	<div class="col-md-offset-1 col-md-9">

		<section class="content-header">
			<div class="box">
				<div class="box-body">
					<div class="row">
						<div class="pull-left">
							<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
								href="<c:url value="/vehicle"/>">Company</a></small>
						</div>
						<div class="pull-right">
							<sec:authorize access="hasAuthority('EDIT_PRIVILEGE')">
								<a class="btn btn-success btn-sm"
									href="<c:url value="/editCompany.in?company_id=${Company.company_id}"/>">
									<span class="fa fa-pencil"></span> Edit Company
								</a>
							</sec:authorize>
						</div>
					</div>
				</div>
			</div>
		</section>
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
						<li><sec:authorize access="hasAuthority('ADD_COMPANY')">
								<a class="fa fa-sitemap" aria-hidden="true" data-toggle="tip"
									data-original-title="Sub Company"
									href="CreateSubCompany.in?id=${Company.company_id_encode}">
									Create Sub Company</a>
							</sec:authorize></li>

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
		<c:if test="${!empty SubCompany}">
			<section class="content">
				<div class="row">
					<div class="col-md-6">
						<div class="box">
							<sec:authorize access="!hasAuthority('VIEW_PRIVILEGE')">
								<spring:message code="message.unauth"></spring:message>
							</sec:authorize>
							<sec:authorize access="hasAuthority('VIEW_PRIVILEGE')">
								<div class="box-body">
									<div class="table-responsive">

										<table class="table">
											<caption>Sub Company List</caption>
											<thead>
												<tr>
													<th class="fit">ID</th>
													<th>Sub Company Name</th>
													<th class="fit ar">Type</th>
													<th class="fit ar">Phone</th>
													<th id="Type">Email</th>
													<th class="fit ar">PAN</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${!empty SubCompany}">
													<c:forEach items="${SubCompany}" var="SubCompany">
														<tr>
															<td class="fit ar"><a
																href="<c:url value="/showVehicle?vid=${SubCompany.company_id}"/>"
																data-toggle="tip"
																data-original-title="Click this vehicle Details"> <c:out
																		value="V-${SubCompany.company_id}" /></a></td>
															<td><a
																href="<c:url value="/showVehicle?vid=${SubCompany.company_id}"/>"
																data-toggle="tip"
																data-original-title="Click vehicle Details"><c:out
																		value="${SubCompany.company_name}" /></a></td>
															<td class="fit ar"><c:out
																	value="${SubCompany.company_type}" /></td>
															<td class="fit ar"><c:out
																	value="${SubCompany.company_mobilenumber}" /></td>
															<td class="fit ar"><c:out
																	value="${SubCompany.company_email}" /></td>

															<td class="fit ar"><c:out
																	value="${SubCompany.company_pan_no}" /></td>

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
					<div class="col-md-5">
						<div class="box">
							<sec:authorize access="!hasAuthority('VIEW_PRIVILEGE')">
								<spring:message code="message.unauth"></spring:message>
							</sec:authorize>
							<sec:authorize access="hasAuthority('VIEW_PRIVILEGE')">
								<div class="box-body">
									<div class="table-responsive">

										<table class="table">
											<caption>Sub Company List</caption>
											<thead>
												<tr>
													<th class="fit">ID</th>
													<th>Sub Company Name</th>
													<th class="fit ar">Type</th>
													<th class="fit ar">Phone</th>
													<th id="Type">Email</th>
													<th class="fit ar">PAN</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${!empty SubCompany}">
													<c:forEach items="${SubCompany}" var="SubCompany">
														<tr>
															<td class="fit ar"><a
																href="<c:url value="/showVehicle?vid=${SubCompany.company_id}"/>"
																data-toggle="tip"
																data-original-title="Click this vehicle Details"> <c:out
																		value="V-${SubCompany.company_id}" /></a></td>
															<td><a
																href="<c:url value="/showVehicle?vid=${SubCompany.company_id}"/>"
																data-toggle="tip"
																data-original-title="Click vehicle Details"><c:out
																		value="${SubCompany.company_name}" /></a></td>
															<td class="fit ar"><c:out
																	value="${SubCompany.company_type}" /></td>
															<td class="fit ar"><c:out
																	value="${SubCompany.company_mobilenumber}" /></td>
															<td class="fit ar"><c:out
																	value="${SubCompany.company_email}" /></td>

															<td class="fit ar"><c:out
																	value="${SubCompany.company_pan_no}" /></td>

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
			</section>
		</c:if>

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
	</div>
</div>