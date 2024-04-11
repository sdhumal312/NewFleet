<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/jquery.dataTables.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/buttons.dataTables.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addJobType.in"/>">New Job Type</a></small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_JOB_TYPE')">
						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#addJobtypes" data-whatever="@mdo">
							<span class="fa fa-plus" id="AddJobType"> Create Job Type</span>
						</button>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<c:if test="${param.saveJobType eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Job Type Created Successfully.
			</div>
		</c:if>
		<c:if test="${param.updateJobType eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Job Type Updated Successfully.
			</div>
		</c:if>
		<c:if test="${param.deleteJobType eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Job Type Deleted Successfully.
			</div>
		</c:if>
		<c:if test="${param.alreadyJobType eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Job Type Already Exists.
			</div>
		</c:if>
		<div class="modal fade" id="addJobtypes" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<form action="saveJobType.in" method="post"
						onsubmit="return validateReTypeUpdate()">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="JobType">New Job Type</h4>
						</div>
						<div class="modal-body">
							<div class="row1">
								<label class="L-size control-label">Job Type</label>
								<div class="I-size">
									<input type="text" class="form-text" id="ReTypeUpdate" 
										name="Job_Type" placeholder="Enter Renewal Type" /> <label
										id="errorEditReType" class="error"></label>
								</div>
							</div>
							<br />
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary" onclick="return jobTypeValidate()">
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
			<div class="col-xs-9">
				<div class="main-body">
					<div class="box">
						<sec:authorize access="!hasAuthority('VIEW_PRIVILEGE')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('VIEW_PRIVILEGE')">
							<div class="box-body">
								<div class="table-responsive">
									<table id="typeTable"
										class="table table-bordered table-striped">
										<thead>
											<tr>
												<th id="TypeName" class="icon">Type Name</th>
												<th id="Usage" class="icon">Usage</th>
												<th id="Actions" class="icon">Actions</th>
											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty JobType}">
												<c:forEach items="${JobType}" var="JobType">
													<tr>
														<td><c:out value="${JobType.job_Type}" /></td>
														<td><a href="#.." rel="facebox"> Jobs</a></td>
														<td>
														<c:if test="${JobType.companyId > 0 || userCompanyId == 4}">
															<div class="btn-group">
																<a class="btn btn-Link dropdown-toggle"
																	data-toggle="dropdown" href="#"> <span
																	class="fa fa-cog"></span> <span class="caret"></span>
																</a>
																
																	<ul class="dropdown-menu pull-right">
																		<li><sec:authorize
																				access="hasAuthority('EDIT_PRIVILEGE')">
																				<a href="editJobTypes.in?Job_id=${JobType.job_id}">
																					<span class="fa fa-pencil"></span> Edit
																				</a>
																			</sec:authorize></li>
																		<li><sec:authorize
																				access="hasAuthority('DELETE_PRIVILEGE')">
																				<a href="deleteJobType.in?Job_id=${JobType.job_id}"
																					class="confirmation"
																					onclick="return confirm('Are you sure you Want to delete  Type ?')">
																					<span class="fa fa-trash"></span> Delete
																				</a>
																			</sec:authorize></li>
																	</ul>
															</div>
														</c:if>
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
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/JobTypeValidate.js" />"></script>
		
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/dataTables.buttons.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.print.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.flash.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/JobTypelanguage.js" />"></script>
	
</div>