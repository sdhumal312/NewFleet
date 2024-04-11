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
							href="<c:url value="/newCompany"/>">Company</a>
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
						<%-- <li><sec:authorize access="hasAuthority('ADD_COMPANY')">
								<a class="fa fa-sitemap" aria-hidden="true" data-toggle="tip"
									data-original-title="Sub Company"
									href="CreateSubCompany.in?id=${Company.company_id_encode}">
									Create Sub Company</a>
							</sec:authorize></li> --%>

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
						<li><sec:authorize access="hasAuthority('ADD_COMPANY')">
								<a class="fa fa-university" aria-hidden="true" data-toggle="tip"
									data-original-title="Fixed Allowance Details"
									href="FixedComAllow.in?id=${Company.company_id_encode}">
									Create Company Fixed Allowance</a>
							</sec:authorize></li>
						
						<li>
						<%-- <sec:authorize access="hasAuthority('ADD_COMPANY')"> --%>						
								<a class="fa fa-users" aria-hidden="true" data-toggle="tip" id="AddMoreParts" onload="" 
									data-original-title="Email"
									href="javascript:email();">
									Configuring E-mail For Daily Work Status</a>
						<%-- </sec:authorize> --%>
						<sec:authorize access="hasAuthority('ADD_SUB_COMPANY')">
						<a class="fa fa-users" aria-hidden="true" data-toggle="tip"  data-original-title="SubCompany"
							href="<c:url value="/addSubCompanyDetails"/>"> Add Sub Company </a>
						
						</sec:authorize>
						</li>
						
						<div class="modal fade" id="configureEmail" role="dialog">
							<div class="modal-dialog modal-md">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">&times;</button>
										<h4 class="modal-title">Configure E-Mail</h4>
									</div>
									<div class="modal-body">
										<div class="row">
											<label class="L-size control-label">E-mail Id</label>
											<div class="I-size">
												<input type="hidden" value="0" id="configId" /> <input
													type="text" class="form-text" id="emailId"
													onchange="$('#saveEmail').removeAttr('disabled');"
													name="unitCost" placeholder="Enter E-mail Id" /> <label
													id="errorvStatus" class="error"></label>
											</div>
										</div>
									</div>

									<div class="modal-footer">
										<button type="submit" onclick="addEmail()"
											class="btn btn-success"  id="saveEmail">Save</button>
										<button type="button" class="btn btn-default"
											data-dismiss="modal">Close</button>
									</div>

								</div>
							</div>
						</div>
											

					</ul>
				</div>
			</div>
		</c:if>
	</section>
	<section class="content-body">
		<c:if test="${!empty Company}">
		<!-- Company Bank Details  -->
			<ul class="tabs">
				<%-- <li class=" current" data-tab="tab-1"><sec:authorize
						access="hasAuthority('ADD_COMPANY')">Sub Company Details</sec:authorize></li> --%>
				<li class="current" data-tab="tab-1"><sec:authorize
						access="hasAuthority('ADD_DIRECTOR_COMPANY')">Owner/Director</sec:authorize></li>
				<li class="tab-link" data-tab="tab-3"><sec:authorize
						access="hasAuthority('ADD_BANK_COMPANY')">Bank Details</sec:authorize></li>
				<li class="tab-link" data-tab="tab-4"><sec:authorize
						access="hasAuthority('ADD_DIRECTOR_COMPANY')">Fixed Allowance Details</sec:authorize></li>
				<li class="tab-link" id="showSubCompany" data-tab="tab-5"><sec:authorize
						access="hasAuthority('SHOW_SUB_COMPANY')">Sub Companies</sec:authorize></li>
			</ul>
			

<%-- 				<div id="tab-1" class="tab-content2 current">
					<div class="col-sm-11">
						<div class="box">
							<sec:authorize access="!hasAuthority('ADD_COMPANY')">
								<spring:message code="message.unauth"></spring:message>
							</sec:authorize>
							<sec:authorize access="hasAuthority('ADD_COMPANY')">
								<div class="box-body">
									<div class="table-responsive">

										<table class="table table-striped">
											<thead>
												<tr>
													<th>Company Name</th>
													<th class="fit ar">Type</th>
													<th class="fit ar">Phone</th>
													<th id="Type">Email</th>
													<th class="fit ar">PAN</th>
													<th id="Actions" class="fit">Actions</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${!empty SubCompany}">
													<c:forEach items="${SubCompany}" var="SubCompany">
														<tr>
															<td><a
																href="<c:url value="/SubCompany?id=${SubCompany.company_id_encode}"/>"
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
															<td>
																<div class="btn-group">
																	<a class="btn btn-Link dropdown-toggle"
																		data-toggle="dropdown" href="#"> <span
																		class="fa fa-cog"></span> <span class="caret"></span>
																	</a>
																	<ul class="dropdown-menu pull-right">
																		<li><sec:authorize
																				access="hasAuthority('ADD_COMPANY')">
																				<a
																					href="<c:url value="/editSubCompany?id=${SubCompany.company_id_encode}"/>">
																					<span class="fa fa-pencil"></span> Edit
																				</a>
																			</sec:authorize></li>
																		<li><sec:authorize
																				access="hasAuthority('ADD_COMPANY')">
																				<a
																					href="<c:url value="/deleteSubCompany?id=${SubCompany.company_id_encode}"/>"
																					class="confirmation"
																					onclick="return confirm('Are you sure you Want to delete SubCompany?')">
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
				</div> --%>

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
													<c:forEach items="${CompanyDirector}"
														var="CompanyDirector">
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
				<c:if test="${!empty CompanyBank}">
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
			<div id="tab-4" class="tab-content2">
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
													<th>Depot/Group Name</th>
													<th>JobType</th>
													<th>Allowance Days</th>
													<th>Allowance Amount</th>
													<th>Extra/Other Days</th>
													
													<th>Extra/Other Amount</th>
													<th id="Actions" class="fit">Actions</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${!empty CompanyFixed}">
													<c:forEach items="${CompanyFixed}"
														var="CompanyFixed">
														<tr>
															<td class="fit ar"><c:out
																	value="${CompanyFixed.VEHICLEGROUP_NAME}" /></td>
															<td class="fit ar"><c:out
																	value="${CompanyFixed.DRIVER_JOBTYPE_NAME}" /></td>
															<td class="fit ar"><c:out
																	value="${CompanyFixed.FIX_PERDAY_ALLOWANCE}" /></td>

															<td class="fit ar"><c:out
																	value="${CompanyFixed.FIX_PERDAY_ALLOWANCE_AMOUNT}" /></td>
															<td class="fit ar"><c:out
																	value="${CompanyFixed.FIX_EXTRA_DAYS}" /></td>
																	<td class="fit ar"><c:out
																	value="${CompanyFixed.FIX_EXTRA_DAYS_AMOUNT}" /></td>
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
																					href="<c:url value="/deleteFixedAllowance.in?id=${CompanyFixed.COMFIXID}"/>"
																					class="confirmation"
																					onclick="return confirm('Are you sure you Want to delete Company Fixed Allowance?')">
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
			<div id="tab-5" class="tab-content2">
				<div class="col-sm-11">
					<div class="box">
						<sec:authorize access="!hasAuthority('SHOW_SUB_COMPANY')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('SHOW_SUB_COMPANY')">
							<div class="box-body">
								<div class="table-responsive">
									<table class="table">
									<thead>
										<tr>
											<th class="fit ar">Sr No</th>
											<th class="fit ar">Company Name</th>
											<th class="fit ar">WebSite</th>
											<th class="fit ar">Mobile No</th>
											<th class="fit ar">GST No</th>
											<th class="fit ar">Action</th>
										</tr>
									</thead>
									<tbody id="subCompanyTable">
									
									</tbody>
				
									</table>
								</div>
							</div>
						</sec:authorize>
					</div>
				</div>
			</div>
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
			$.post("<c:url value="/user/savePassword"></c:url>",
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
		
		/* function addMail(){
					$('#configureEmail').modal('show');
				} */
	</script>
</div>