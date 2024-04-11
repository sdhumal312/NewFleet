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
							<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
								href="<c:url value="/newCompany.in"/>">Company</a></small> / <small><a
								href="<c:url value="/newCompany.in"/>"><c:out
										value="${Company.company_name}" /></a></small> / <small>Edit Bank
								Details</small>
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
		</sec:authorize>
	</section>
	<section class="content">
		<div class="row">
			<div class="col-md-offset-1 col-md-9">
				<sec:authorize access="!hasAuthority('ADD_BANK_COMPANY')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('ADD_BANK_COMPANY')">
					<div class="main-body">
						<div class="panel panel-default">
							<div class="panel-body">
								<form action="updateBankCompany" name="vehicleStatu"
									method="post">
									<div class="form-horizontal ">
										<fieldset>
											<legend>Edit Company Bank Details</legend>
											<div class="row1">
												<label class="L-size control-label">BANK NAME :<abbr
													title="required">*</abbr></label>
												<div class="I-size">

													<input type="hidden" class="form-text" name="com_bank_id"
														value="${bank.com_bank_id}" required="required" />
													<!--  -->
													<input type="hidden" class="form-text" name="company_id"
														value="${bank.company_id}" required="required" />
													<!--  -->
													<input type="text" class="form-text" name="com_bank_name"
														value="${bank.com_bank_name}" required="required"
														placeholder="Enter BANK NAME" maxlength="200" id="pcName"
														onkeypress="return ADDRESS(event);" ondrop="return false;" />
													<label id="errorRouteNo" class="error"></label>
												</div>
											</div>

											<div class="row1">
												<label class="L-size control-label">A/C NUMBER : <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<input type="text" class="form-text"
														name="com_bank_account" placeholder="Enter A/C NUMBER"
														maxlength="30" id="pcName" required="required"
														value="${bank.com_bank_account}"
														onkeypress="return DESIGNATION(event);"
														ondrop="return false;" /> <label id="errorCITY"
														class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">A/C TYPE :</label>
												<div class="I-size">
													<input type="text" class="form-text" name="com_bank_actype"
														value="${bank.com_bank_actype}"
														placeholder="Enter A/C TYPE" maxlength="100" id="pcName"
														onkeypress="return CITY(event);" ondrop="return false;" />
													<label id="errorCITY" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">IFSC CORE :<abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<input type="text" class="form-text" name="com_bank_ifsc"
														value="${bank.com_bank_ifsc}"
														placeholder="ENTER IFSC CORE" maxlength="30" id="pcName"
														required="required" onkeypress="return Email(event);"
														ondrop="return false;" /> <label id="errorEmail"
														class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">BSR CODE :</label>
												<div class="I-size">
													<input type="text" class="form-text" name="com_bank_bsr"
														value="${bank.com_bank_bsr}" placeholder="Enter BSR CODE"
														maxlength="30" id="pcName"
														onkeypress="return Email(event);" ondrop="return false;" />
													<label id="errorEmail" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">BRANCH NAME :<abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<input type="text" class="form-text" name="com_bank_branch"
														value="${bank.com_bank_bsr}"
														placeholder="Enter BRANCH NAME" maxlength="200"
														id="pcName" required="required"
														onkeypress="return Email(event);" ondrop="return false;" />
													<label id="errorEmail" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">ADDRESS :</label>
												<div class="I-size">
													<input type="text" class="form-text"
														name="com_bank_address" placeholder="Enter Address"
														maxlength="150" id="pcName"
														value="${bank.com_bank_address}"
														onkeypress="return Email(event);" ondrop="return false;" />
													<label id="errorEmail" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Country :<abbr
													title="required">*</abbr></label>
												<div class="I-size">

													<select style="width: 100%;" required="required"
														name="com_bank_country"
														class="select2 countries form-text " id="countryId">
														<option value="${bank.com_bank_country}"
															selected="selected">${bank.com_bank_country}</option>
														<option value="">Select Country</option>
													</select> <label id="errorCOUNTRY" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">State :<abbr
													title="required">*</abbr></label>
												<div class="I-size">

													<select name="com_bank_state"
														class="select2 states form-text" name="company_state"
														id="stateId">
														<option value="${bank.com_bank_state}" selected="selected">${bank.com_bank_state}</option>
														<option value="">Select State</option>
													</select> <label id="errorCITY" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">City :<abbr
													title="required">*</abbr></label>
												<div class="I-size">

													<select name="com_bank_city"
														class="select2 cities form-text" id="cityId">
														<option value="${bank.com_bank_city}" selected="selected">${bank.com_bank_city}</option>
														<option value="">Select City</option>
													</select> <label id="errorCITY" class="error"></label>
												</div>
											</div>

											<div class="row1">
												<label class="L-size control-label">PIN CODE :</label>
												<div class="I-size">
													<input type="text" class="form-text"
														name="com_bank_pincode" placeholder="Enter PIN CODE"
														maxlength="6" id="pcName" value="${bank.com_bank_pincode}"
														onkeypress="return Email(event);" ondrop="return false;" />
													<label id="errorEmail" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<input type="hidden" class="form-text"
													name="com_bank_status" value="SUBCOMPANY"
													required="required" /> <input type="hidden"
													class="form-text" name="createdBy"
													value="${bank.createdBy}" required="required" />
											</div>
										</fieldset>
										<div class="form-group">
											<label class="L-size control-label" for="vehicle_theft"></label>
											<div class="col-sm-5">
												<fieldset class="form-actions">
													<input class="btn btn-info" name="commit" type="submit"
														value="Update"> <a class="btn btn-link"
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
	<script type="text/javascript">
		$(document).ready(function() {
			$(".select2").select2();
		});
	</script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/location.js" />"></script>
</div>