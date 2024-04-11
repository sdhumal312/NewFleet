<%@ include file="taglib.jsp"%>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addDepartment"/>">New Department</a></small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_DEPARTMENT')">
						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#addDepartment" data-whatever="@mdo">
							<span class="fa fa-plus" id="AddDriverDocType"> Create
								Department</span>
						</button>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<c:if test="${param.saveDepartment eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Department Type Created Successfully.
			</div>
		</c:if>
		<c:if test="${param.updateDepartment eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Department Type Updated Successfully.
			</div>
		</c:if>
		<c:if test="${param.deleteDepartment eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Department Type Deleted Successfully.
			</div>
		</c:if>
		<c:if test="${param.alreadyDepartment eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Department Already Exists.
			</div>
		</c:if>
		<div class="modal fade" id="addDepartment" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<form action="saveDepartment.in" method="post"
						>
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">New Department</h4>
						</div>
						<div class="modal-body">
							<div class="form-horizontal ">
							<div class="row1" id="grpbranchName" class="form-group">
							<input type="hidden" value="${companyId}" name="company_id" >
							</div>
							
								<div class="row1">
									<label class="L-size control-label">Department Name<abbr
										title="required">*</abbr>
									</label>
									<div class="I-size">
										<input class="form-text" name="depart_name"
											type="text" required="required" maxlength="150" id="departmentName"
											placeholder="Enter Department Name"
											onkeypress="return IsDepartName(event);"
											ondrop="return false;"> <label class="error"
											id="errorDepartName" style="display: none"> </label>
									</div>
								</div>
								<div class="row1">
									<label class="L-size control-label">Department Code </label>
									<div class="I-size">
										<input class="form-text" id="depart_code" name="depart_code"
											type="text" required="required" maxlength="10"
											placeholder="Enter Department Code"
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
								<br />
							</div>
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary" onclick="return departmentvalidate()">
								<span><spring:message code="label.master.Save" /></span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close"><spring:message
										code="label.master.Close" /></span>
							</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-10">
				<div class="main-body">
					<div class="box">
						<sec:authorize access="!hasAuthority('VIEW_DEPARTMENT')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('VIEW_DEPARTMENT')">
							<div class="box-body">
								<div class="table-responsive">
									<table id="DocTypeTable" class="table table-hover table-striped">
										<thead>
											<tr>
												<th class="icon">Department Name</th>
												<th class="icon">Department code</th>
												<th class="icon">HOD</th>
												<th class="icon">Description</th>
												<th class="icon">Actions</th>
											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty department}">
												<c:forEach items="${department}" var="department">
													<tr>
														<td><c:out value="${department.depart_name}" /></td>
														<td><c:out value="${department.depart_code}" /></td>
														<td><c:out value="${department.depart_hod}" /></td>
														<td><c:out value="${department.depart_description}" /></td>

														<td>
															<div class="btn-group">
																<a class="btn btn-default dropdown-toggle"
																	data-toggle="dropdown" href="#"> <span
																	class="fa fa-cog"></span> <span class="caret"></span>
																</a>
																<ul class="dropdown-menu pull-right">
																	<li><sec:authorize
																			access="hasAuthority('ADD_DEPARTMENT')">
																			<a
																				href="editDepartment.in?depart_id=${department.depart_id}">
																				<span class="fa fa-pencil"></span> Edit
																			</a>
																		</sec:authorize></li>
																	<li><sec:authorize
																			access="hasAuthority('ADD_DEPARTMENT')">
																			<a
																				href="deleteDepartment.in?depart_id=${department.depart_id}"
																				class="confirmation"
																				onclick="return confirm('Are you sure you Want to delete Department Type ?')">
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