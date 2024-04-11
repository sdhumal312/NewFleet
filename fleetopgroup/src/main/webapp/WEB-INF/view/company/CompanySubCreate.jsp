<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<sec:authorize access="!hasAuthority('VIEW_COMPANY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_COMPANY')">
			<div class="box">
				<div class="box-body">
					<div class="row">
						<div class="pull-left">
							<a href="<c:url value="/open"/>"><spring:message
									code="label.master.home" /></a> / <small><a
								href="<c:url value="/newCompany.in"/>">Company</a></small> / <small><a
								href="<c:url value="/newCompany.in"/>"><c:out
										value="${Company.company_name}" /></a></small> / <small>Create
								Sub Company</small>
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
								class="img-rounded" alt="Company Logo" width="100%;"
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
				</div>

			</div>
		</sec:authorize>
	</section>
	<section class="content">
		<div class="row">
			<div class="col-md-offset-1 col-md-9">
				<sec:authorize access="!hasAuthority('ADD_COMPANY')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('ADD_COMPANY')">
					<div class="main-body">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h1 id="AddVehicle">Create Sub Company Details</h1>
							</div>
							<div class="panel-body">

								<form id="formCreateCompany"
									action="<c:url value="/saveCompany.in"/>" method="post"
									enctype="multipart/form-data" name="formCreateCompany"
									role="form" class="form-horizontal">
									<div class="form-horizontal ">

										<div class="row1" id="grpcompanyName" class="form-group">
											<label class="L-size control-label">Sub Company Name
												:<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="hidden" class="form-text"
													name="company_parentName" value="${Company.company_name}"
													maxlength="250" required="required" />
												<!--  -->
												<input type="hidden" class="form-text"
													name="company_parent_id" value="${Company.company_id}"
													required="required" />
												<!--  -->
												<input type="text" class="form-text" name="company_name"
													placeholder="Enter company_name" maxlength="250"
													id="companyName" onkeypress="return RouteName(event);"
													ondrop="return false;" /><span id="companyNameIcon"
													class=""></span>
												<div id="companyNameErrorMsg" class="text-danger"></div>
												<label id="errorRouteName" class="error"></label>
											</div>
										</div>
										<div class="row1" id="grpcompanyType" class="form-group">
											<label class="L-size control-label">Sub Company Type
												:<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="text" class="form-text" name="company_type"
													placeholder="Enter ComType" maxlength="200"
													id="companyType" onkeypress="return ComType(event);"
													ondrop="return false;" /><span id="companyTypeIcon"
													class=""></span>
												<div id="companyTypeErrorMsg" class="text-danger"></div>
												<label id="errorComType" class="error"></label>
											</div>
										</div>
										<div class="row" id="grpcompanyLogo" class="form-group">
											<div class="form-group">

												<label class="col-md-3">Logo : <abbr
													title="required">*</abbr></label>
												<div class="col-md-6">
													<input type="file" name="fileUpload" accept="image/*"
														id="fileselect"></input> <span id="companyLogoIcon"
														class=""></span>
													<div id="companyLogoErrorMsg" class="text-danger"></div>

												</div>

											</div>
											<div id="messages"></div>
										</div>

										<fieldset>
											<legend>Sub Company Address Details</legend>
											<div class="row1">
												<label class="L-size control-label">Address:</label>
												<div class="I-size">
													<input type="text" class="form-text" name="company_address"
														placeholder="Enter ADDRESS" maxlength="200" id="pcName"
														onkeypress="return ADDRESS(event);" ondrop="return false;" />
													<label id="errorRouteNo" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Country :</label>
												<div class="I-size">

													<select style="width: 100%;" name="company_country"
														class="select2 countries form-text " id="countryId">
														<option value="">Select Country</option>
													</select> <label id="errorCOUNTRY" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">State :</label>
												<div class="I-size">

													<select name="company_state"
														class="select2 states form-text" name="company_state"
														id="stateId">
														<option value="">Select State</option>
													</select> <label id="errorCITY" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">City :</label>
												<div class="I-size">

													<select name="company_city"
														class="select2 cities form-text" id="cityId">
														<option value="">Select City</option>
													</select> <label id="errorCITY" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Pin code :</label>
												<div class="I-size">
													<input type="text" class="form-text" name="company_pincode"
														placeholder="Enter pincode" maxlength="6" id="pcName"
														onkeypress="return Pincode(event);" ondrop="return false;" />
													<label id="errorPincode" class="error"></label>
												</div>
											</div>
										</fieldset>
										<fieldset>
											<legend>Company </legend>
											<div class="row1">
												<label class="L-size control-label">WebSite :</label>
												<div class="I-size">
													<input type="text" class="form-text" name="company_website"
														value="http://" placeholder="Enter WEBSITE"
														maxlength="150" id="pcName"
														onkeypress="return WEBSITE(event);" ondrop="return false;" />
													<label id="errorWEBSITE" class="error"></label>
												</div>
											</div>

											<div class="row1" id="grpcompanyEmail" class="form-group">
												<label class="L-size control-label">Email :</label>
												<div class="I-size">
													<input type="email" class="form-text" name="company_email"
														placeholder="Enter Email" maxlength="100"
														id="companyEmail" onkeypress="return Email(event);"
														ondrop="return false;" /> <span id="companyEmailIcon"
														class=""></span>
													<div id="companyEmailErrorMsg" class="text-danger"></div>
													<label id="errorEmail" class="error"></label>
												</div>
											</div>

											<div class="row1" id="grpcompanyMobile" class="form-group">
												<label class="L-size control-label"> Mobile Number :</label>
												<div class="I-size">
													<input type="text" class="form-text"
														name="company_mobilenumber" placeholder="Enter mobile"
														maxlength="15" id="companyMobile"
														onkeypress="return mobile(event);" ondrop="return false;" />
													<span id="companyMobileIcon" class=""></span>
													<div id="companyMobileErrorMsg" class="text-danger"></div>
													<label id="errormobile" class="error"></label>
												</div>
											</div>

											<div class="row1">
												<label class="L-size control-label">Sub Company TAN
													No :</label>
												<div class="I-size">
													<input type="text" class="form-text" name="company_tan_no"
														placeholder="Enter TAN no" maxlength="50" id="pcName"
														onkeypress="return TANNO(event);" ondrop="return false;" />
													<label id="errorTANNO" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Sub Company PAN
													No :</label>
												<div class="I-size">
													<input type="text" class="form-text" name="company_pan_no"
														placeholder="Enter GST NO" maxlength="50" id="pcName"
														onkeypress="return TAXNO(event);" ondrop="return false;" />
													<label id="errorTAXNO" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Sub Company TAX
													No :</label>
												<div class="I-size">
													<input type="text" class="form-text" name="company_tax_no"
														placeholder="Enter GST NO" maxlength="50" id="pcName"
														onkeypress="return TAXNO(event);" ondrop="return false;" />
													<label id="errorTAXNO" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Sub Company TIN
													No :</label>
												<div class="I-size">
													<input type="text" class="form-text" name="company_tin_no"
														placeholder="Enter tin no" maxlength="50" id="pcName"
														onkeypress="return TINNO(event);" ondrop="return false;" />
													<label id="errorTINNO" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Sub Company CIN
													No :</label>
												<div class="I-size">
													<input type="text" class="form-text" name="company_cin_no"
														placeholder="Enter cin no" maxlength="50" id="pcName"
														onkeypress="return cinNO(event);" ondrop="return false;" />
													<label id="errorCINNO" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">About US Sub
													Company:</label>
												<div class="I-size">
													<textarea class="text optional form-text"
														name="company_abount" rows="4" maxlength="500"
														onkeypress="return RouteRemarks(event);"
														ondrop="return false;"> 
				                                </textarea>
													<label id="errorRouteRemarks" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<input type="hidden" class="form-text" name="company_status"
													value="SUBCOMPANY" required="required" /> <input
													type="hidden" class="form-text" name="createdBy"
													value="${createdBy}" required="required" />
											</div>

										</fieldset>

										<div class="form-group">
											<label class="L-size control-label" for="vehicle_theft"></label>
											<div class="col-sm-5">
												<fieldset class="form-actions">
													<button type="submit" class="btn btn-success">Create
														Sub Company</button>
													<a class="btn btn-link"
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

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripValidate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/CompanyValidate.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/location.js" />"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			$(".select2").select2();
		});
	</script>

</div>