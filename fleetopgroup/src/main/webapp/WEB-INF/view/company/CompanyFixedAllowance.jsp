<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="https://goself.xyz/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
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
								Fixed Allowance</small>
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
				<sec:authorize access="!hasAuthority('ADD_BANK_COMPANY')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('ADD_BANK_COMPANY')">
					<div class="main-body">
						<div class="panel panel-default">
							<div class="panel-body">
								<form action="saveCompanyFixedAllowance" name="vehicleStatu" method="post">
									<div class="form-horizontal ">
										<fieldset>
											<legend>Create Company Fixed Allowance</legend>
											<div class="row1">
												<div class="form-group">
													<div class="I-size">
														<input type="hidden" class="form-text" name="COMPANY_ID" required="required"
														value="${Company.company_id}"/>
													</div>
												</div>
											</div>
											<div class="row1">
												<div class="form-group">
													<label class="L-size string required control-label">Depot/Group Name :</label>
													<div class="I-size">
														<select class=" select2" name="VEHICLEGROUP_ID"  style="width: 100%;" required="required">
															<c:forEach items="${vehiclegroup}" var="vehiclegroup">
																<option value="${vehiclegroup.gid}"><c:out value="${vehiclegroup.vGroup}" /></option>
															</c:forEach>
														</select>
													</div>
												</div>
											</div>
											<div class="row1">
												<div class="form-group">
													<label class="L-size string required control-label">Job
														Title :</label>
													<div class="I-size">
														<select class=" select2" name="DRIVER_JOBTYPE_ID" style="width: 100%;" required="required">
															<c:forEach items="${driverJobType}" var="driverJobType">
																<option value="${driverJobType.driJobId}"><c:out
																		value="${driverJobType.driJobType}" /></option>
															</c:forEach>
														</select> <label class="error" id="errorJob" style="display: none">
														</label>
													</div>
												</div>
											</div>

											<div class="row1">
												<label class="L-size control-label">Per Day Allowance :<abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<input type="text" class="form-text"
														name="FIX_PERDAY_ALLOWANCE_AMOUNT" placeholder="Per Day Allowance"
														maxlength="30" id="pcName" required="required"
														onkeypress="return DESIGNATION(event);"
														ondrop="return false;" /> <label id="errorCITY"
														class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Fixed Maximum Extra/Other Days :</label>
												<div class="I-size">
													<input type="text" class="form-text" name="FIX_EXTRA_DAYS" required="required"
														placeholder="Fixed Maximum Extra/Other Days" maxlength="100" id="pcName"
														onkeypress="return CITY(event);" ondrop="return false;" />
													<label id="errorCITY" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Fixed Extra/Other Amount :<abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<input type="text" class="form-text" name="FIX_EXTRA_DAYS_AMOUNT"
														placeholder="Fixed Extra/Other Amount" maxlength="30" id="pcName"
														required="required" onkeypress="return Email(event);"
														ondrop="return false;" /> <label id="errorEmail"
														class="error"></label>
												</div>
											</div>
											
										</fieldset>
										<div class="form-group">
											<label class="L-size control-label" for="vehicle_theft"></label>
											<div class="col-sm-5">
												<fieldset class="form-actions">
													<input class="btn btn-info" name="commit" type="submit"
														value="Create Fixed Allowance"> <a class="btn btn-link"
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
	<script
		src="https://goself.xyz/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".select2").select2();
		});
	</script>
	<script src="https://goself.xyz/QhyvOb0m3EjE7A4/js/location.js"></script>
</div>