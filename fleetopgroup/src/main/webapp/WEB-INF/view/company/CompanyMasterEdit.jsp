<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="row">
					<div class="pull-left">
						<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
							href="<c:url value="/masterCompany/1.in"/>">Company</a>
					</div>
					<div class="pull-right">
						<a href="<c:url value="/masterCompany/1.in"/>">Cancel</a>
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
							class="img-rounded" alt="Company Logo" width="300" height="100" />
						</a>
					</div>
					<div class="pull-left1">
						<h3 class="secondary-header-title">
							<a> <c:out value="${Company.company_name}" /> - <c:out
									value="${Company.company_type}" />
							</a>
						</h3>
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
	</section>
	<section class="content">
		<div class="row">
			<div class="col-md-offset-1 col-md-9">
				<sec:authorize access="hasAuthority('ADD_COMPANY')">
					<div class="main-body">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h1 id="AddVehicle">Edit Company Details</h1>
							</div>
							<div class="panel-body">
								<form id="formEditCreateCompany"
									action="<c:url value="/updateMasterCompany.in"/>" method="post"
									enctype="multipart/form-data" name="formEditCreateCompany"
									role="form" class="form-horizontal">
									<div class="form-horizontal ">
										<div class="row1" id="grpcompanyName" class="form-group">
											<label class="L-size control-label">PARENT COMPANY
												NAME :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="hidden" class="form-text" name="company_id_encode"
													value="${Company.company_id_encode}" required="required" />
												<!--  -->
												<input type="text" class="form-text" name="company_name"
													placeholder="Enter company_name" maxlength="250"
													id="companyName" value="${Company.company_name}"
													onkeypress="return RouteName(event);"
													ondrop="return false;" /><span id="companyNameIcon"
													class=""></span>
												<div id="companyNameErrorMsg" class="text-danger"></div>
												<label id="errorRouteName" class="error"></label>
											</div>
										</div>
										<div class="row1" id="grpcompanyType" class="form-group">
											<label class="L-size control-label">Company Type :<abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input type="text" class="form-text" name="company_type"
													value="${Company.company_type}" placeholder="Enter ComType"
													maxlength="200" id="companyType"
													onkeypress="return ComType(event);" ondrop="return false;" /><span
													id="companyTypeIcon" class=""></span>
												<div id="companyTypeErrorMsg" class="text-danger"></div>
												<label id="errorComType" class="error"></label>
											</div>
										</div>
										<div class="row1" id="grpcompanyCode" class="form-group">
											<label class="L-size control-label">Company
												Login Code :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="text" class="form-text" name="companyCode"
													placeholder="Enter Company Code" maxlength="20" value="${Company.companyCode}"
													id="companyCode" onkeypress="return RouteName(event);"
													ondrop="return false;" /><span id="companyCodeIcon"
													class=""></span>
												<div id="companyCodeErrorMsg" class="text-danger"></div>
												<label id="errorCompanyCode" class="error"></label>
											</div>
										</div>
										<fieldset>
											<legend>Company Address Details</legend>
											<div class="row1">
												<label class="L-size control-label">Address :</label>
												<div class="I-size">
													<input type="text" class="form-text" name="company_address"
														placeholder="Enter ADDRESS" maxlength="200" id="pcName"
														value="${Company.company_address}"
														onkeypress="return ADDRESS(event);" ondrop="return false;" />
													<label id="errorRouteNo" class="error"></label>
												</div>
											</div>

											<div class="row1">
												<label class="L-size control-label">Country :</label>
												<div class="I-size">
													<select name="company_country"
														class="select2 countries form-text " id="countryId"
														name="region">
														<option value="${Company.company_country}"
															selected="selected">${Company.company_country}</option>
														<option value="">Select Country</option>
													</select> <label id="errorCOUNTRY" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">State :</label>
												<div class="I-size">
													<select name="company_state"
														class="select2 states form-text" id="stateId" size="1">
														<option value="${Company.company_state}">${Company.company_state}</option>
													</select> <label id="errorCITY" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">City :</label>
												<div class="I-size">
													<select name="company_city"
														class="select2 cities form-text" size="1" id="cityId">
														<option value="${Company.company_city}">${Company.company_city}</option>
													</select> <label id="errorCITY" class="error"></label>
												</div>
											</div>

											<div class="row1">
												<label class="L-size control-label">Pin code :</label>
												<div class="I-size">
													<input type="text" class="form-text" name="company_pincode"
														placeholder="Enter pincode" maxlength="6" id="pcName"
														value="${Company.company_pincode}"
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
														placeholder="Enter WEBSITE" maxlength="150" id="pcName"
														value="${Company.company_website}"
														onkeypress="return WEBSITE(event);" ondrop="return false;" />
													<label id="errorWEBSITE" class="error"></label>
												</div>
											</div>

											<div class="row1" id="grpcompanyEmail" class="form-group">
												<label class="L-size control-label">Email :</label>
												<div class="I-size">
													<input type="email" class="form-text" name="company_email"
														placeholder="Enter Email" maxlength="100"
														id="companyEmail" value="${Company.company_email}"
														onkeypress="return Email(event);" ondrop="return false;" />
													<span id="companyEmailIcon" class=""></span>
													<div id="companyEmailErrorMsg" class="text-danger"></div>
													<label id="errorEmail" class="error"></label>
												</div>
											</div>

											<div class="row1" id="grpcompanyMobile" class="form-group">
												<label class="L-size control-label">Mobile Number :</label>
												<div class="I-size">
													<input type="text" class="form-text"
														name="company_mobilenumber" placeholder="Enter mobile"
														maxlength="15" id="companyMobile"
														value="${Company.company_mobilenumber}"
														onkeypress="return mobile(event);" ondrop="return false;" />
													<span id="companyMobileIcon" class=""></span>
													<div id="companyMobileErrorMsg" class="text-danger"></div>
													<label id="errormobile" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Company ESI
													Calculation Days :<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<input type="text" class="form-text"
														value="${Company.company_esi_pf_days}"
														name="company_esi_pf_days" required="required"
														placeholder="Enter number of days" maxlength="50"
														id="pcName" onkeypress="return TANNO(event);"
														ondrop="return false;" /> <label id="errorTANNO"
														class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Company Extra
													Active :<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<select class="form-text" name="company_esi_pf_disable"
														required="required">
														<c:choose>
															<c:when test="${Company.company_esi_pf_disable == 1}">
																<option value="1" selected="selected">OFF</option>
																<option value="0">ON</option>
															</c:when>
															<c:otherwise>
																<option value="0" selected="selected">ON</option>
																<option value="1">OFF</option>
															</c:otherwise>
														</c:choose>

													</select>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Company PAN No :</label>
												<div class="I-size">
													<input type="text" class="form-text" name="company_pan_no"
														value="${Company.company_pan_no}"
														placeholder="Enter TAN no" maxlength="50" id="pcName"
														onkeypress="return TANNO(event);" ondrop="return false;" />
													<label id="errorTANNO" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Company TAN No :</label>
												<div class="I-size">
													<input type="text" class="form-text" name="company_tan_no"
														value="${Company.company_tan_no}"
														placeholder="Enter TAN no" maxlength="50" id="pcName"
														onkeypress="return TANNO(event);" ondrop="return false;" />
													<label id="errorTANNO" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Company GST NO :</label>
												<div class="I-size">
													<input type="text" class="form-text" name="company_tax_no"
														value="${Company.company_tax_no}"
														placeholder="Enter GST NO" maxlength="50" id="pcName"
														onkeypress="return TAXNO(event);" ondrop="return false;" />
													<label id="errorTAXNO" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Company TIN No :</label>
												<div class="I-size">
													<input type="text" class="form-text" name="company_tin_no"
														value="${Company.company_tin_no}"
														placeholder="Enter tin no" maxlength="50" id="pcName"
														onkeypress="return TINNO(event);" ondrop="return false;" />
													<label id="errorTINNO" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Company CIN No :</label>
												<div class="I-size">
													<input type="text" class="form-text" name="company_cin_no"
														value="${Company.company_cin_no}"
														placeholder="Enter cin no" maxlength="50" id="pcName"
														onkeypress="return cinNO(event);" ondrop="return false;" />
													<label id="errorCINNO" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">About US :</label>
												<div class="I-size">
													<textarea class="text optional form-text"
														name="company_abount" rows="4" maxlength="500"
														onkeypress="return RouteRemarks(event);"
														ondrop="return false;">${Company.company_abount} 
				                                </textarea>
													<label id="errorRouteRemarks" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<input type="hidden" class="form-text" name="company_status"
													value="MAINCOMPANY" required="required" /> <input
													type="hidden" class="form-text" name="createdBy"
													value="${Company.createdBy}" required="required" /> <input
													type="hidden" class="form-text" name="lastModifiedBy"
													value="${lastModifiedBy}" required="required" />
											</div>

										</fieldset>
										<div class="form-group">
											<label class="L-size control-label" for="vehicle_theft"></label>
											<div class="col-sm-5">
												<fieldset class="form-actions">
													<button type="submit" class="btn btn-success">Update
														Company</button>
													<a class="btn btn-link"
														href="<c:url value="/masterCompany/1.in"/>">Cancel</a>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/CompanyValidate.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".select2").select2();
		});
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/location.js" />"></script>
</div>