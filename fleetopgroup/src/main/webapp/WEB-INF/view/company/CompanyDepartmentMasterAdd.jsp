<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">

<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="row">
					<div class="pull-left">
						<a href="<c:url value="/open"/>"><spring:message
								code="label.master.home" /></a> / <a
							href="<c:url value="/masterCompany/1.in"/>">Company</a> / <small><span
							id="NewVehicle">Create New Master Branch</span></small>
					</div>
					<div class="pull-right">
						<a class="btn btn-link"
							href="<c:url value="/showMasterCompany?CID=${Company.company_id_encode}"/>">
							<span id="AddVehicle"> Cancel</span>
						</a>
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
		<div class="main">
			<div class="main-body">
				<sec:authorize access="!hasAuthority('MASTER_COM_ADD_PRIVILEGE')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('MASTER_COM_ADD_PRIVILEGE')">
					<div class="row">
						<div class="box">
							<div class="box-body">
								<form id="formBranch"
									action="<c:url value="/saveMasterDepartment.in"/>"
									method="post" enctype="multipart/form-data" name="formBranch"
									role="form" class="form-horizontal">
									<div class="form-horizontal ">
										<fieldset>
											<legend id="Identification">Department Basic Details
											</legend>
											<div class="row1" id="grpbranchCompany" class="form-group">
												<div class="I-size">
													<input type="hidden" name="company_id_encode"
														value="${Company.company_id_encode}"> <span
														id="branchCompanyIcon" class=""></span>
													<div id="branchCompanyErrorMsg" class="text-danger"></div>
												</div>
											</div>

											<div class="row1" id="grpbranchName" class="form-group">
												<label class="L-size control-label">Department Name<abbr
													title="required">*</abbr>
												</label>
												<div class="I-size">
													<input class="form-text" id="depart_name"
														name="depart_name" type="text" required="required"
														maxlength="150" placeholder="Enter Department Name"
														onkeypress="return IsDepartName(event);"
														ondrop="return false;"> <label class="error"
														id="errorDepartName" style="display: none"> </label>
												</div>
											</div>
											<div class="row1" id="grpbranchCode" class="form-group">
												<label class="L-size control-label">Department Code
												</label>
												<div class="I-size">
													<input class="form-text" id="depart_code"
														name="depart_code" type="text" required="required"
														maxlength="10" placeholder="Enter Department Code"
														onkeypress="return IsDepartCode(event);"
														ondrop="return false;"> <label class="error"
														id="errorDepartCode" style="display: none"> </label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Department HOD </label>
												<div class="I-size">
													<input class="form-text" id="depart_hod" name="depart_hod"
														type="text" required="required" maxlength="100"
														placeholder="Enter Department Code"
														onkeypress="return IsDepartHOD(event);"
														ondrop="return false;"> <label class="error"
														id="errorDepartHOD" style="display: none"> </label>
												</div>
											</div>

											<div class="row1">
												<label class="L-size control-label">Department
													Description </label>
												<div class="I-size">
													<textarea class="text optional form-text"
														name="depart_description" rows="3" maxlength="200"
														onkeypress="return IsDepartDescription(event);"
														ondrop="return false;"> 
				                                </textarea>
													<label class="error" id="errorDepartDescription"
														style="display: none"> </label>
												</div>
											</div>
										</fieldset>
										<fieldset class="form-actions">
											<div class="pull-left">

												<button type="submit" class="btn btn-success">Create
													Department</button>
												<a class="btn btn-link"
													href="<c:url value="/showMasterCompany?CID=${Company.company_id_encode}"/>"><span
													id="Cancel">Cancel</span></a>
											</div>

										</fieldset>
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
			$("#tagPicker").select2({
				closeOnSelect : !1
			})
		});
	</script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/BR/DepartmentValidate.js" />"></script>
</div>