<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_COMPANY')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_COMPANY')">
					<div class="row">
						<div class="pull-left">
							<a href="<c:url value="/open"/>"><spring:message
									code="label.master.home" /></a> / <small><a
								href="<c:url value="/newCompany.in"/>">Company</a></small> / <small><a
								href="<c:url value="/newCompany.in"/>"><c:out
										value="${Company.company_name}" /></a></small> / <small>Create
								Director Company</small>
						</div>
						<div class="pull-right">
							<a class="btn btn-link" href="<c:url value="/newCompany.in"/>">Cancel</a>
						</div>
					</div>
					<c:if test="${!empty Company}">
						<!-- Show the User Profile -->
						<div class="pull-left">
							<a
								href="${pageContext.request.contextPath}/downloadlogo/${Company.company_id_encode}.in"
								class="zoom" data-title="logo"
								data-footer="${Company.company_name}" data-type="image"
								data-toggle="lightbox"> <img
								src="${pageContext.request.contextPath}/downloadlogo/${Company.company_id_encode}.in"
								class="img-rounded" alt="Company Logo" width="900px;"
								height="100px;" />
							</a>
						</div>
						<div class="pull-left">


							<div class="secondary-header-title">
								<ul class="breadcrumb">
									<li><span class="fa fa-sitemap" aria-hidden="true"
										data-toggle="tip" data-original-title="PAN Number"><a>
												<c:out value="${Company.company_pan_no}" />
										</a></span></li>

									<li><span class="fa fa-users" aria-hidden="true"
										data-toggle="tip" data-original-title="TAN Number"> <a
											href="#"> <c:out value="${Company.company_tan_no}" /></a>
									</span></li>

									<li><span class="fa fa-university" aria-hidden="true"
										data-toggle="tip" data-original-title="TIN Number"> <a
											href="#"> <c:out value="${Company.company_tin_no}" /></a></span></li>

									<li><span class="fa fa-university" aria-hidden="true"
										data-toggle="tip" data-original-title="CIN Number"> <a
											href="#"> <c:out value="${Company.company_cin_no}" /></a></span></li>

								</ul>
							</div>
						</div>
					</c:if>
				</sec:authorize>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<div class="col-md-offset-1 col-md-9">
				<sec:authorize access="!hasAuthority('ADD_DIRECTOR_COMPANY')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('ADD_DIRECTOR_COMPANY')">
					<div class="main-body">
						<div class="panel panel-default">
							<div class="panel-body">
								<form action="saveDirectorCompany" name="vehicleStatu"
									method="post" enctype="multipart/form-data">
									<div class="form-horizontal ">
										<fieldset>
											<legend>Create Company Directors</legend>
											<div class="row1">
												<label class="L-size control-label">DIRECTORS NAME :
													<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<input type="hidden" class="form-text" name="company_id"
														value="${Company.company_id}" required="required" /> <input
														type="text" class="form-text" name="com_directors_name"
														placeholder="Enter DIRECTORS NAME:" maxlength="150"
														id="pcName" onkeypress="return ADDRESS(event);"
														required="required" ondrop="return false;" /> <label
														id="errorRouteNo" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">DESIGNATION :<abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<input type="text" class="form-text" name="com_designation"
														placeholder="Enter DESIGNATION" maxlength="150"
														required="required" id="pcName"
														onkeypress="return DESIGNATION(event);"
														ondrop="return false;" /> <label id="errorCITY"
														class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">CONTACT NUMBER :<abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<input type="text" class="form-text"
														name="com_directors_mobile" required="required"
														placeholder="Enter CONTACT NUMBER" maxlength="15"
														id="pcName" onkeypress="return CITY(event);"
														ondrop="return false;" /> <label id="errorCITY"
														class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Email :</label>
												<div class="I-size">
													<input type="email" class="form-text"
														name="com_directors_email" placeholder="Enter Email"
														maxlength="50" id="pcName"
														onkeypress="return Email(event);" ondrop="return false;" />
													<label id="errorEmail" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<input type="hidden" class="form-text"
													name="com_directors_status" value="MAINCOMPANY"
													required="required" /> <input type="hidden"
													class="form-text" name="createdBy" value="${createdBy}"
													required="required" />
											</div>
										</fieldset>
										<div class="form-group">
											<label class="L-size control-label" for="vehicle_theft"></label>
											<div class="col-sm-5">
												<fieldset class="form-actions">
													<input class="btn btn-info" name="commit" type="submit"
														value="Save"> <a class="btn btn-link"
														href="<c:url value="/newCompany.in"/>">Cancel</a>
												</fieldset>
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</sec:authorize>
			</div>
		</div>
	</section>
</div>