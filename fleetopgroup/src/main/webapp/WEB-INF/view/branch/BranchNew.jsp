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
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/newBranch.in"/>">Branch Details</a></small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_BRANCH')">
						<a class="btn btn-success" href="<c:url value="/addBranch.in"/>">
							<span class="fa fa-plus" id="AddDriverDocType"> Create
								Branch</span>
						</a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="hasAuthority('VIEW_BRANCH')">
			<div class="row">
				<div class="col-md-3 col-sm-6 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i class="fa fa-usb"
							aria-hidden="true"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Total Branch</span> <span
								class="info-box-number">${TotalBranch}</span>
						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-6 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-anchor" aria-hidden="true"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Active Branch</span> <span
								class="info-box-number">${ActiveBranch}</span>

						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-6 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Branch</span>
							<form action="searchBranch.in" method="post">
								<div class="input-group">
									<input name="userID" type="hidden" value="${user.id}"
										required="required" /> <input class="form-text"
										name="branch_name" placeholder="Branch Name, Code" type="text"
										data-mask="" required="required"> <span
										class="input-group-btn">
										<button type="submit" name="search" id="search-btn"
											class="btn btn-success btn-sm" data-toggle="modal"
											data-target="#processing-modal">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</sec:authorize>

		<c:if test="${param.saveDriverDocType eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Driver Document Types Created Successfully.
			</div>
		</c:if>
		
		<c:if test="${param.already eq true}">
		<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Branch Name AlreadyExists
				</div>
		</c:if>

		<div class="row">
			<div class="main-body">
				<div class="box">
					<sec:authorize access="!hasAuthority('VIEW_BRANCH')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_BRANCH')">
						<div class="box-body">
							<div class="table-responsive">
								<table id="DriverTable"
									class="table table-bordered table-striped">
									<thead>
										<tr>
											<th>Branch Name</th>
											<th>Status</th>
											<th>Contact</th>
											<th>Primary Contact</th>
											<th>Secondary Contact</th>
											<th>Time</th>
											<th>Users</th>
											<th>Actions</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty branch}">
											<c:forEach items="${branch}" var="branch">
												<tr>
													<td><a href="showBranch?branch_id=${branch.branch_id}"><c:out
																value="${branch.branch_name}" /></a></td>
													<td><c:out value="${branch.branch_status}" /></td>
													<td><c:out value="${branch.branch_phonenumber}" /><br>
														<c:out value="${branch.branch_mobilenumber}" /></td>
													<td><c:out value="${branch.owner1_name}" /><br>
														<c:out value="${branch.owner1_mobile}" /></td>
													<td><c:out value="${branch.owner2_name}" /><br>
														<c:out value="${branch.owner2_mobile}" /></td>
													<td><c:out value="${branch.branch_time_from} to " />
														<c:out value="${branch.branch_time_to}" /></td>
													<td><c:out value="${branch.branch_name}" /></td>
													<td>
														<div class="btn-group">
															<a class="btn btn-default dropdown-toggle"
																data-toggle="dropdown" href="#"> <span
																class="fa fa-cog"></span> <span class="caret"></span>
															</a>
															<ul class="dropdown-menu pull-right">
																<li><sec:authorize access="hasAuthority('ADD_BRANCH')">
																		<a href="editBranch?branch_id=${branch.branch_id}">
																			<span class="fa fa-pencil"></span> Edit
																		</a>
																	</sec:authorize></li>
																<li><sec:authorize access="hasAuthority('ADD_BRANCH')">
																		<a
																			href="deleteBranch.in?branch_id=${branch.branch_id}"
																			class="confirmation"
																			onclick="return confirm('Are you sure you Want to delete Branch ?')">
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
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/dataTables.buttons.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.print.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.flash.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/DR/DriverNewlanguage.js" />"></script>
</div>