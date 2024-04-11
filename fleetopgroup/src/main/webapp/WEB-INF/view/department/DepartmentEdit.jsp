<%@ include file="taglib.jsp"%>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/addDepartment"/>">New Department</a> / <span>Edit
						Department</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-link" href="addDepartment.in">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<div class="col-xs-9">
				<div class="main-body">
					<sec:authorize access="!hasAuthority('ADD_DEPARTMENT')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('ADD_DEPARTMENT')">
						<c:if test="${!empty department}">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h1 id="AddDriver">Edit Department</h1>
								</div>
								<div class="panel-body">
									<form action="updateDepartment" method="post"
										onsubmit="return validateDocTypeUpdate()">
										<div class="form-horizontal ">
											<div class="row">

												<input class="form-text" id="depart_id" name="depart_id"
													type="hidden" required="required"
													value="${department.depart_id}" readonly="readonly">

											</div>
											<div class="row1">
												<label class="L-size control-label">Department Name<abbr
													title="required">*</abbr>
												</label>
												<div class="I-size">
													<input class="form-text" id="depart_name"
														name="depart_name" type="text" required="required"
														maxlength="150" placeholder="Enter Department Name"
														value="${department.depart_name}" readonly="readonly"
														onkeypress="return IsDepartName(event);"
														ondrop="return false;"> <label class="error"
														id="errorDepartName" style="display: none"> </label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Department Code
												</label>
												<div class="I-size">
													<input class="form-text" id="depart_code"
														name="depart_code" type="text" required="required"
														maxlength="10" placeholder="Enter Department Code"
														value="${department.depart_code}"
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
														value="${department.depart_hod}"
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
														ondrop="return false;">${department.depart_description}
				                                </textarea>
													<label class="error" id="errorDepartDescription"
														style="display: none"> </label>
												</div>
											</div>
											<br />
											<div class="form-DocType">
												<label class="L-size control-label" for="Driver_theft"></label>
												<div class="col-sm-5">
													<fieldset class="form-actions">
														<input class="btn btn-info" name="commit" type="submit"
															value="Update"> <a class="btn btn-link"
															href="<c:url value="/addDepartment"/>">Cancel</a>
													</fieldset>
												</div>
											</div>
										</div>
									</form>
								</div>
							</div>
						</c:if>
					</sec:authorize>
					<c:if test="${empty department}">
						<div class="callout callout-danger">
							<h4>Warning!</h4>
							<p>
								The page no data to Show.., Please Don't Change any URL ID or
								Number.. <br> Don't Enter any Extra worlds in URL..
							</p>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</section>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			$(".select2").select2();
			$("#tagPicker").select2({
				closeOnSelect : !1
			})
		});
	</script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/BR/DepartmentValidate.js" />"></script>
</div>